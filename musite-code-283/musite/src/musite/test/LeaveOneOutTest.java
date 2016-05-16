/**
 * Musite
 * Copyright (C) 2010-2011 Digital Biology Laboratory, University Of Missouri
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package musite.test;

import cytoscape.task.TaskMonitor;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import musite.MusiteInit;
import musite.Proteins;
import musite.Protein;
import musite.PTM;

import musite.prediction.classifier.AverageBoostingBinaryClassifier;
import musite.prediction.classifier.BoostingBinaryClassifier;
import musite.prediction.classifier.BootstrapBinaryClassifier;
import musite.prediction.classifier.FeatureLinearNormalizer;
import musite.prediction.classifier.FrequencyFeatureExtractorTrainingDataChangeHandler;
import musite.prediction.classifier.KNNFeatureExtractorTrainingDataChangeHandler;
import musite.prediction.classifier.SVMLightBinaryClassifier;

import musite.prediction.feature.AminoAcidFrequencyFeatureExtractor;
import musite.prediction.feature.DisorderFeatureExtractor;
import musite.prediction.feature.FeatureExtractor;
import musite.prediction.feature.Instance;
import musite.prediction.feature.InstancesExtractorFromProteins;
import musite.prediction.feature.InstanceFilter;
import musite.prediction.feature.KNNFeatureExtractor;
import musite.prediction.feature.OffsetInstanceFilter;

import musite.prediction.feature.disorder.DisorderUtil;
import musite.prediction.feature.knn.ClosenessCalculator;
import musite.prediction.feature.knn.KNNExtractor;
import musite.prediction.feature.knn.KNNExtractorImpl;
import musite.prediction.feature.knn.UniSimMatrixSequenceClosenessCalculator;
import musite.prediction.feature.knn.MatrixNormalizer;
import musite.prediction.feature.knn.MatrixNormalizerLinear;
import musite.prediction.feature.knn.MatrixUtils;
import musite.prediction.feature.knn.SimilarityMatrix;

import musite.util.AminoAcid;
import musite.util.CrossValidation;
import musite.util.Pair;

/**
 *
 * @author gjj
 */
public class LeaveOneOutTest {

    private TaskMonitor monitor;

    public void setTaskMonitor(TaskMonitor monitor) {
        this.monitor = monitor;
    }

    private void setMonitorStatus(String status) {
        if (monitor!=null) {
            monitor.setStatus(status);
        }
    }

    public void interrupt() {
        
    }

    /**
     * 
     * @param dirFasta
     * @param dirModel
     * @param dirDisorder
     * @param dirDisorderSaveto
     * @param param
     * @throws IOException
     */
    public double[][] test(Proteins proteins, 
                         Proteins negValidData,
                         PTM ptm,
                         Set<AminoAcid> aminoAcids,
                         Properties props,
                         int negSize) throws IOException {

        int maxOffset = 0;

        String svm_option = props.getProperty(MusiteInit.TRAINING_PROPS_SVM_PARAMETERS);
        int nboots = Integer.parseInt(props.getProperty(MusiteInit.TRAINING_PROPS_NO_OF_BOOTS));
        int nclassifiers = Integer.parseInt(props.getProperty(MusiteInit.TRAINING_PROPS_NO_OF_CLASSIFIERS));

        // knn param
        final boolean useKNN = props.getProperty(MusiteInit.TRAINING_PROPS_USE_KNN_FEATURES).equalsIgnoreCase("true");
        final String dirMatrix = MusiteInit.MATRIX_DIR+File.separator+props.getProperty(MusiteInit.TRAINING_PROPS_SUBSTITUTION_MATRIX);
        SimilarityMatrix similarityMatrix = MatrixUtils.readBLASTMatrix(dirMatrix);
        MatrixNormalizer matrixNormalizer = new MatrixNormalizerLinear();
        MatrixUtils.normalizeMatrix(similarityMatrix, matrixNormalizer);
        int knnWindowOffset = Integer.parseInt(props.getProperty(MusiteInit.TRAINING_PROPS_KNN_WINDOW_SIZE))/2;
        if (useKNN && knnWindowOffset>maxOffset) {
            maxOffset = knnWindowOffset;
        }
        String strKs = props.getProperty(MusiteInit.TRAINING_PROPS_KNN_NEIGHBOR_SIZE);
        String[] strs = strKs.split(",");
        int[] Ks = new int[strs.length];
        for (int i=0; i<strs.length; i++) {
            Ks[i] = (int)(Double.parseDouble(strs[i].trim())*2*nboots*0.01);
        }

        // disorder param
        final boolean useDisorder = props.getProperty(MusiteInit.TRAINING_PROPS_USE_DISORDER_FEATURES).equalsIgnoreCase("true");
        String strDisWindows = props.getProperty(MusiteInit.TRAINING_PROPS_DISORDER_WINDOW_SIZES);
        strs = strDisWindows.split(",");
        int[] disWindowOffsets = new int[strs.length];
        for (int i=0; i<strs.length; i++) {
            disWindowOffsets[i] = Integer.parseInt(strs[i].trim())/2;
            if (useDisorder && disWindowOffsets[i]>maxOffset) {
                maxOffset = disWindowOffsets[i];
            }
        }

        // frequency param
        final boolean useFrequency = props.getProperty(MusiteInit.TRAINING_PROPS_USE_FREQUENCY_FEATURES).equalsIgnoreCase("true");
        int frequencyNumAA = Integer.parseInt(props.getProperty(MusiteInit.TRAINING_PROPS_FREQUENCY_FEATURE_NUMBER));
        int frequencyWindow = Integer.parseInt(props.getProperty(MusiteInit.TRAINING_PROPS_FREQUENCY_WINDOW_SIZE))/2;
        if (useFrequency && frequencyWindow>maxOffset) {
            maxOffset = frequencyWindow;
        }

        // filter out proteins without disorder information
        final int minLen = maxOffset*2+1;
        proteins.filterProteins(new Proteins.ProteinFilter() {
            public boolean filter(Protein protein) {
                String seq = protein.getSequence();
                if (seq==null || seq.length()<minLen) {
                    return false;
                }

                if (useDisorder) {
                    List<Double> dis = DisorderUtil.extractDisorder(protein);
                    return dis!=null && !dis.isEmpty();
                }

                return true;
            }
        });

        // padding
        final boolean padding = props.getProperty(MusiteInit.TRAINING_PROPS_PADDING_TERMINALS).equalsIgnoreCase("true");

        // extract instances
        InstancesExtractorFromProteins posInsExtractor = new InstancesExtractorFromProteins(proteins, aminoAcids);
        posInsExtractor.setExtractOption(InstancesExtractorFromProteins.ExtractOption.MODIFIED_SITES, ptm);

        final InstanceFilter insFilter = padding?null:new OffsetInstanceFilter(maxOffset);
        posInsExtractor.setInstanceFilter(insFilter);

        InstancesExtractorFromProteins negInsExtractor = new InstancesExtractorFromProteins(proteins, aminoAcids);
        negInsExtractor.setExtractOption(InstancesExtractorFromProteins.ExtractOption.UNMODIFIED_SITES, ptm);

        System.out.println("Extracting positive instance...");
        setMonitorStatus("Extracting positive instance...");
        List<Instance> instances_positive = posInsExtractor.fetch(-1);
        System.out.println("  "+instances_positive.size()+" instances were extracted");

        System.out.println("Extracting unlabeled instance...");
        setMonitorStatus("Extracting unlabeled instance...");
        List<Instance> instances_unlabeled = negInsExtractor.fetch(-1);
        System.out.println("  "+instances_unlabeled.size()+" instances were extracted");

        List<Instance> instances_unlabeled_valid = null;
        if (negValidData!=null) {
            System.out.println("Extracting unlabeled instance for validation...");
            InstancesExtractorFromProteins insExtractorValid = new InstancesExtractorFromProteins(negValidData, aminoAcids);
            negInsExtractor.setExtractOption(InstancesExtractorFromProteins.ExtractOption.UNMODIFIED_SITES, ptm);
            instances_unlabeled_valid = insExtractorValid.fetch(-1);
            System.out.println("  "+instances_unlabeled_valid.size()+" instances were extracted");
        }

        int npos = instances_positive.size();
        double[] fpr = new double[npos];
        for (int ifold=0; ifold<npos; ifold++) {
            System.out.println(ifold+"/"+npos);

            List<Instance> positive_train = new ArrayList(instances_positive);
            positive_train.remove(ifold);

            List<Instance> positive_control = new ArrayList(1);
            positive_control.add(instances_positive.get(ifold));

//            int npos_train = positive_train.size();
//            int npos_control = positive_control.size();

            List<Instance> negative_train;
            List<Instance> negative_control;

            if (negValidData==null) {
                Pair<List,List> ptt = CrossValidation.seperateTrainingTestSet(instances_unlabeled, negSize);

                negative_train = ptt.getFirst();
                negative_control = ptt.getSecond();
            } else {
                negative_train = instances_unlabeled;
                if (negSize==-1) {
                    negative_control = instances_unlabeled_valid;
                } else {
                    Pair<List,List> ptt = CrossValidation.seperateTrainingTestSet(instances_unlabeled_valid, negSize);
                    negative_control = ptt.getSecond();
                }
            }

//            int nneg_train = negative_train.size();
            int nneg_control = negative_control.size();

//            List<Instance> instances_train = new ArrayList<Instance>(positive_train);
//            instances_train.addAll(negative_train);
//
//            List<Double> labels_train = new ArrayList(npos-1+nneg_train);
//            for (int i=0; i<npos-1; i++) {
//                labels_train.add(1.0);
//            }
//            for (int i=0; i<nneg_train; i++) {
//                labels_train.add(-1.0);
//            }

            List<Instance> instances_control = new ArrayList<Instance>(positive_control);
            instances_control.addAll(negative_control);

            List<Double> labels_control = new ArrayList(1+nneg_control);
            labels_control.add(1.0);

            for (int i=0; i<nneg_control; i++) {
                labels_control.add(-1.0);
            }

            // set up feature extractor
            FeatureExtractor disorderExtractor = new DisorderFeatureExtractor(disWindowOffsets, true);

            ClosenessCalculator closenessCalculator = new UniSimMatrixSequenceClosenessCalculator(similarityMatrix, knnWindowOffset);
            KNNExtractor nnExtractor = new KNNExtractorImpl(closenessCalculator);

            FeatureLinearNormalizer featureNormalizer = new FeatureLinearNormalizer(-1.0,1.0);

            // setup classifier
            BoostingBinaryClassifier boostingClassifier = new AverageBoostingBinaryClassifier();
            for (int ic=0; ic<nclassifiers; ic++) {
                List<FeatureExtractor> extractors = new ArrayList<FeatureExtractor>(3);
                if (useDisorder) {
                    extractors.add(disorderExtractor);
                }

                AminoAcidFrequencyFeatureExtractor frequencyExtractor = new AminoAcidFrequencyFeatureExtractor(frequencyWindow);
                FrequencyFeatureExtractorTrainingDataChangeHandler freTrainingDataChangeHandler = null;
                if (useFrequency) {
                    extractors.add(frequencyExtractor);
                    freTrainingDataChangeHandler = new FrequencyFeatureExtractorTrainingDataChangeHandler(
                            frequencyExtractor, frequencyNumAA, frequencyWindow);
                }

                KNNFeatureExtractorTrainingDataChangeHandler knnTrainingDataChangeHandler = null;
                if (useKNN) {
                    KNNFeatureExtractor knnExtractor = new KNNFeatureExtractor(Ks, nnExtractor);
                    extractors.add(knnExtractor);
                    knnTrainingDataChangeHandler = new KNNFeatureExtractorTrainingDataChangeHandler(knnExtractor);
                }


                //set up classifier
                SVMLightBinaryClassifier svmClassifier = new SVMLightBinaryClassifier(svm_option, featureNormalizer, extractors);

                BootstrapBinaryClassifier bootstrapClassifier = new BootstrapBinaryClassifier(svmClassifier, nboots);

                if (useKNN) {
                    bootstrapClassifier.addTrainingDataChangeHandler(knnTrainingDataChangeHandler);
                }

                if (useFrequency) {
                    bootstrapClassifier.addTrainingDataChangeHandler(freTrainingDataChangeHandler);
                }

                boostingClassifier.addClassifier(bootstrapClassifier);
            }

            System.out.println("Training...");
            setMonitorStatus("Training classifier...");
            boostingClassifier.train(positive_train, negative_train);

            // testing
            System.out.println("Evaluating...");
            setMonitorStatus("Evaluating model...");
            List<Double> prediction = boostingClassifier.classify(instances_control);

            double pred_pos = prediction.get(0);
            int fp=0;
            for (int i=1; i<=nneg_control; i++) {
                if (prediction.get(i)>=pred_pos) {
                    fp++;
                }
            }

            fpr[ifold] = fp*1.0/nneg_control;

            System.out.println("FPR:"+fpr[ifold]);
        }

        TreeMap<Double,Integer> mapFprTp = new TreeMap();
        for (double f : fpr) {
            Integer tp = mapFprTp.get(f);
            if (tp==null) {
                tp = 1;
            } else {
                tp ++;
            }

            mapFprTp.put(f, tp);
        }

        double[][] res = new double[mapFprTp.size()][2];
        int ipos = 0;
        int tp = 0;
        for (Map.Entry<Double,Integer> entry : mapFprTp.entrySet()) {
            res[ipos][0] = entry.getKey();
            tp += entry.getValue();
            res[ipos][1] = tp*1.0/npos;
            ipos++;
        }

        return res;
    }
}
