/**
 * Musite
 * Copyright (C) 2010 Digital Biology Laboratory, University Of Missouri
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

package musite.ui;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import musite.MusiteInit;
import musite.Protein;
import musite.Proteins;
import musite.Proteins.ProteinVisitor;
import musite.ResidueAnnotationUtil;

import musite.io.xml.ProteinsXMLReader;
import musite.io.xml.ProteinsXMLWriter;

import musite.ui.task.ReadTask;
import musite.ui.task.WriteTask;
import musite.ui.task.TaskUtil;

import musite.util.FileExtensionsFilter;
import musite.util.FilePathParser;

/**
 *
 * @author Jianjiong Gao
 */
public class ProteinsInfomationFilterDialog extends javax.swing.JDialog {

    /** Creates new form Fasta2XmlDialog */
    public ProteinsInfomationFilterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.ButtonGroup speciesbuttonGroup = new javax.swing.ButtonGroup();
        javax.swing.JPanel originalPanel = new javax.swing.JPanel();
        javax.swing.JPanel originalFilePanel = new javax.swing.JPanel();
        originalFileTextField = new javax.swing.JTextField();
        javax.swing.JButton originalFileButton = new javax.swing.JButton();
        javax.swing.JPanel targetPanel = new javax.swing.JPanel();
        javax.swing.JPanel targetFilePanel = new javax.swing.JPanel();
        targetFileTextField = new javax.swing.JTextField();
        javax.swing.JButton targetFileButton = new javax.swing.JButton();
        javax.swing.JPanel OKPanel = new javax.swing.JPanel();
        OKBtn = new javax.swing.JButton();
        javax.swing.JButton cancelBtn = new javax.swing.JButton();
        javax.swing.JPanel filterPanel = new javax.swing.JPanel();
        javax.swing.JPanel includePanel = new javax.swing.JPanel();
        includeRadioButton = new javax.swing.JRadioButton();
        excludeRadioButton = new javax.swing.JRadioButton();
        fieldPanel = new javax.swing.JPanel();
        accCheckBox = new javax.swing.JCheckBox();
        seqCheckBox = new javax.swing.JCheckBox();
        symbolCheckBox = new javax.swing.JCheckBox();
        organismCheckBox = new javax.swing.JCheckBox();
        descCheckBox = new javax.swing.JCheckBox();
        annotationCheckBox = new javax.swing.JCheckBox();
        disorderCheckBox = new javax.swing.JCheckBox();
        javax.swing.JPanel otherPanel = new javax.swing.JPanel();
        rmvOtherCheckBox = new javax.swing.JCheckBox();
        rmvOtherTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filter proteins");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        originalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Original Musite XML file"));
        originalPanel.setLayout(new java.awt.GridBagLayout());

        originalFilePanel.setLayout(new java.awt.GridBagLayout());

        originalFileTextField.setToolTipText("Please select a FASTA training file");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        originalFilePanel.add(originalFileTextField, gridBagConstraints);

        originalFileButton.setText("Open");
        originalFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                originalFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        originalFilePanel.add(originalFileButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        originalPanel.add(originalFilePanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(originalPanel, gridBagConstraints);

        targetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Save to Musite XML file"));
        targetPanel.setMinimumSize(new java.awt.Dimension(400, 63));
        targetPanel.setPreferredSize(new java.awt.Dimension(500, 63));
        targetPanel.setLayout(new java.awt.GridBagLayout());

        targetFilePanel.setLayout(new java.awt.GridBagLayout());

        targetFileTextField.setToolTipText("Please select a FASTA training file");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        targetFilePanel.add(targetFileTextField, gridBagConstraints);

        targetFileButton.setText("Open");
        targetFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        targetFilePanel.add(targetFileButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        targetPanel.add(targetFilePanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(targetPanel, gridBagConstraints);

        OKBtn.setText("   OK   ");
        OKBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKBtnActionPerformed(evt);
            }
        });
        OKPanel.add(OKBtn);

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        OKPanel.add(cancelBtn);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(OKPanel, gridBagConstraints);

        filterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter protein information"));
        filterPanel.setLayout(new java.awt.GridBagLayout());

        includePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        speciesbuttonGroup.add(includeRadioButton);
        includeRadioButton.setSelected(true);
        includeRadioButton.setText("Retain");
        includePanel.add(includeRadioButton);

        speciesbuttonGroup.add(excludeRadioButton);
        excludeRadioButton.setText("Remobe");
        includePanel.add(excludeRadioButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        filterPanel.add(includePanel, gridBagConstraints);

        fieldPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Protein information"));
        fieldPanel.setLayout(new java.awt.GridBagLayout());

        accCheckBox.setText("Accession");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(accCheckBox, gridBagConstraints);

        seqCheckBox.setText("Sequence");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(seqCheckBox, gridBagConstraints);

        symbolCheckBox.setText("Symbol");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(symbolCheckBox, gridBagConstraints);

        organismCheckBox.setText("Organism");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(organismCheckBox, gridBagConstraints);

        descCheckBox.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(descCheckBox, gridBagConstraints);

        annotationCheckBox.setText("Residue annotation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(annotationCheckBox, gridBagConstraints);

        disorderCheckBox.setText("Disorder");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        fieldPanel.add(disorderCheckBox, gridBagConstraints);

        otherPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        rmvOtherCheckBox.setText("Other:");
        otherPanel.add(rmvOtherCheckBox);

        rmvOtherTextField.setPreferredSize(new java.awt.Dimension(100, 19));
        otherPanel.add(rmvOtherTextField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        fieldPanel.add(otherPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        filterPanel.add(fieldPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(filterPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void originalFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originalFileButtonActionPerformed
        JFileChooser fc = new JFileChooser(MusiteInit.defaultPath);
        ArrayList<String> exts = new ArrayList<String>(1);
        String ext = "xml";
        exts.add(ext);
        fc.setFileFilter(new FileExtensionsFilter(exts,"XML file (.xml)"));
        
        //fc.setAcceptAllFileFilterUsed(true);
        fc.setDialogTitle("Select a Musite XML file...");
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            MusiteInit.defaultPath = file.getParent();

            String filePath = MusiteInit.defaultPath + File.separator + file.getName();
            originalFileTextField.setText(filePath);

            String fileName = FilePathParser.getName(filePath);
            String saveTo = MusiteInit.defaultPath+File.separator+fileName+".filtered.xml";
            targetFileTextField.setText(saveTo);
        }
}//GEN-LAST:event_originalFileButtonActionPerformed

    private void targetFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetFileButtonActionPerformed
        JFileChooser fc;
        String defFile = targetFileTextField.getText();
        if (defFile.length()>0) {
            fc = new JFileChooser(FilePathParser.getDir(defFile));
        } else {
            fc = new JFileChooser(MusiteInit.defaultPath);
        }

        String ext = "xml";
        fc.setSelectedFile(new File(defFile));
        ArrayList<String> exts = new ArrayList<String>(1);
        exts.add(ext);
        fc.setFileFilter(new FileExtensionsFilter(exts,"XML file (.xml)"));
        //fc.setAcceptAllFileFilterUsed(true);
        fc.setDialogTitle("Save to...");
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            MusiteInit.defaultPath = file.getParent();

            String filePath = MusiteInit.defaultPath + File.separator + file.getName();
            targetFileTextField.setText(filePath);
        }
    }//GEN-LAST:event_targetFileButtonActionPerformed

    private void OKBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKBtnActionPerformed
        String dirOriginal = originalFileTextField.getText();
        if (dirOriginal.length()==0) {
            JOptionPane.showMessageDialog(this, "Error: specify a file containing the protein sequences.");
            return;
        }

        String dirXml = targetFileTextField.getText();
        if (dirXml.length()==0) {
            JOptionPane.showMessageDialog(this, "Error: specify the xml file to save.");
            return;
        }

        if (musite.util.IOUtil.fileExist(dirXml)) {
            int ret = JOptionPane.showConfirmDialog(this, "Are you sure to replace the existing xml file?",
                    null, JOptionPane.YES_NO_OPTION);
            if (ret==JOptionPane.NO_OPTION) {
                return;
            }
        }

        //Reading phospho data
        ProteinsXMLReader reader = ProteinsXMLReader.createReader();

        ReadTask<Proteins> readTask = new ReadTask(reader, dirOriginal);
        TaskUtil.execute(readTask);
        if (!readTask.success()) {
            JOptionPane.showMessageDialog(this, "Failed to read the original file");
            return;
        }
        Proteins proteins = readTask.getResultObject();

        // remove sites
        final boolean retain = includeRadioButton.isSelected();
        final Set<String> fields = new HashSet();
        if (this.accCheckBox.isSelected())
            fields.add(Protein.ACCESSION);
        if (this.seqCheckBox.isSelected())
            fields.add(Protein.SEQUENCE);
        if (this.symbolCheckBox.isSelected())
            fields.add(Protein.SYMBOL);
        if (this.descCheckBox.isSelected())
            fields.add(Protein.DESCRIPTION);
        if (this.organismCheckBox.isSelected())
            fields.add(Protein.ORGANISM);
        if (this.annotationCheckBox.isSelected())
            fields.add(ResidueAnnotationUtil.RESIDUE_ANNOTATION);
        if (this.disorderCheckBox.isSelected())
            fields.add(musite.prediction.feature.disorder.DisorderUtil.DISORDER);
        if (this.rmvOtherCheckBox.isSelected())
            fields.add(this.rmvOtherTextField.getText());
        proteins.travelProteins(new ProteinVisitor() {
            public void visit(Protein protein) {
                if (retain)
                    protein.retainInfo(fields);
                else {
                    for (String field : fields)
                        protein.removeInfo(field);
                }
            }
        });

        //Write to xml file
        ProteinsXMLWriter writer = ProteinsXMLWriter.createWriter();
        WriteTask xmlWriteTask = new WriteTask(proteins, writer, dirXml);
        TaskUtil.execute(xmlWriteTask);
        if (!readTask.success()) {
            JOptionPane.showMessageDialog(this, "Failed to write the xml file");
            return;
        }

        JOptionPane.showMessageDialog(this, "Done.");
        
        this.setVisible(false);
        this.dispose();
}//GEN-LAST:event_OKBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        setVisible(false);
        dispose();
}//GEN-LAST:event_cancelBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKBtn;
    private javax.swing.JCheckBox accCheckBox;
    private javax.swing.JCheckBox annotationCheckBox;
    private javax.swing.JCheckBox descCheckBox;
    private javax.swing.JCheckBox disorderCheckBox;
    private javax.swing.JRadioButton excludeRadioButton;
    private javax.swing.JPanel fieldPanel;
    private javax.swing.JRadioButton includeRadioButton;
    private javax.swing.JCheckBox organismCheckBox;
    private javax.swing.JTextField originalFileTextField;
    private javax.swing.JCheckBox rmvOtherCheckBox;
    private javax.swing.JTextField rmvOtherTextField;
    private javax.swing.JCheckBox seqCheckBox;
    private javax.swing.JCheckBox symbolCheckBox;
    private javax.swing.JTextField targetFileTextField;
    // End of variables declaration//GEN-END:variables

}
