<?php
set_include_path('system'.PATH_SEPARATOR.'templates'.PATH_SEPARATOR.'classes'.PATH_SEPARATOR.'backend');

require_once('JsonEncDec.inc');
require_once('Template.php');
require_once('Database.php');
require_once('RedirectBrowserException.php');
require_once('execute.php');

$tmpl = new Template();

$tmpl->id = $_GET['id'];
$tmpl->seq = "MEEPQSDPSVEPPLSQETFSDLWKLLPENNVLSPLPSQAMDDLMLSPDDIEQWFTEDPGPDEAPRMPEAAPPVAPAPAAPTPAAPAPAPSWPLSSSVPSQKTYQGSYGFRLGFLHSGTAKSVTCTYSPALNKMFCQLAKTCPVQLWVDSTPPPGTRVRAMAIYKQSQHMTEVVRRCPHHERCSDSDGLAPPQHLIRVEGNLRVEYLDDRNTFRHSVVVPYEPPEVGSDCTTIHYNYMCNSSCMGGMNRRPILTIITLEDSSGNLLGRNSFEVRVCACPGRDRRTEEENLRKKGEPHHELPPGSTKRALPNNTSSSPQPKKKPLDGEYFTLQIRGRERFEMFRELNEALELKDAQAGKEPGGSRAHSSHLKSKKGQSTSRHKKLMFKTEGPDSD";
//$tmpl->org = $_GET['organism'].'.model';

$dataString = '{"proteins":[{"data":[{"position":"6","sequence":"*******MEEPQSDPSVEPPLSQET","specificity":"93.50%","score":"0.15","aminoAcid":"S"},{"position":"9","sequence":"****MEEPQSDPSVEPPLSQETFSD","specificity":"94.57%","score":"0.21","aminoAcid":"S"},{"position":"15","sequence":"EPQSDPSVEPPLSQETFSDLWKLLP","specificity":"94.32%","score":"0.20","aminoAcid":"S"},{"position":"18","sequence":"SDPSVEPPLSQETFSDLWKLLPENN","specificity":"79.32%","score":"-0.39","aminoAcid":"T"},{"position":"20","sequence":"PSVEPPLSQETFSDLWKLLPENNVL","specificity":"20.96%","score":"-1.26","aminoAcid":"S"},{"position":"33","sequence":"DLWKLLPENNVLSPLPSQAMDDLML","specificity":"99.12%","score":"0.55","aminoAcid":"S"},{"position":"37","sequence":"LLPENNVLSPLPSQAMDDLMLSPDD","specificity":"91.15%","score":"0.04","aminoAcid":"S"},{"position":"46","sequence":"PLPSQAMDDLMLSPDDIEQWFTEDP","specificity":"99.51%","score":"0.62","aminoAcid":"S"},{"position":"55","sequence":"LMLSPDDIEQWFTEDPGPDEAPRMP","specificity":"92.03%","score":"0.07","aminoAcid":"T"},{"position":"81","sequence":"AAPPVAPAPAAPTPAAPAPAPSWPL","specificity":"90.45%","score":"0.00","aminoAcid":"T"},{"position":"90","sequence":"AAPTPAAPAPAPSWPLSSSVPSQKT","specificity":"84.31%","score":"-0.24","aminoAcid":"S"},{"position":"94","sequence":"PAAPAPAPSWPLSSSVPSQKTYQGS","specificity":"83.26%","score":"-0.28","aminoAcid":"S"},{"position":"95","sequence":"AAPAPAPSWPLSSSVPSQKTYQGSY","specificity":"77.02%","score":"-0.45","aminoAcid":"S"},{"position":"96","sequence":"APAPAPSWPLSSSVPSQKTYQGSYG","specificity":"80.55%","score":"-0.36","aminoAcid":"S"},{"position":"99","sequence":"PAPSWPLSSSVPSQKTYQGSYGFRL","specificity":"67.52%","score":"-0.70","aminoAcid":"S"},{"position":"102","sequence":"SWPLSSSVPSQKTYQGSYGFRLGFL","specificity":"59.31%","score":"-0.88","aminoAcid":"T"},{"position":"106","sequence":"SSSVPSQKTYQGSYGFRLGFLHSGT","specificity":"52.90%","score":"-0.99","aminoAcid":"S"},{"position":"116","sequence":"QGSYGFRLGFLHSGTAKSVTCTYSP","specificity":"46.04%","score":"-1.06","aminoAcid":"S"},{"position":"118","sequence":"SYGFRLGFLHSGTAKSVTCTYSPAL","specificity":"24.12%","score":"-1.23","aminoAcid":"T"},{"position":"121","sequence":"FRLGFLHSGTAKSVTCTYSPALNKM","specificity":"0.68%","score":"-1.65","aminoAcid":"S"},{"position":"123","sequence":"LGFLHSGTAKSVTCTYSPALNKMFC","specificity":"8.74%","score":"-1.38","aminoAcid":"T"},{"position":"125","sequence":"FLHSGTAKSVTCTYSPALNKMFCQL","specificity":"17.08%","score":"-1.29","aminoAcid":"T"},{"position":"127","sequence":"HSGTAKSVTCTYSPALNKMFCQLAK","specificity":"61.42%","score":"-0.83","aminoAcid":"S"},{"position":"140","sequence":"PALNKMFCQLAKTCPVQLWVDSTPP","specificity":"11.52%","score":"-1.34","aminoAcid":"T"},{"position":"149","sequence":"LAKTCPVQLWVDSTPPPGTRVRAMA","specificity":"35.90%","score":"-1.15","aminoAcid":"S"},{"position":"150","sequence":"AKTCPVQLWVDSTPPPGTRVRAMAI","specificity":"81.00%","score":"-0.35","aminoAcid":"T"},{"position":"155","sequence":"VQLWVDSTPPPGTRVRAMAIYKQSQ","specificity":"53.35%","score":"-0.98","aminoAcid":"T"},{"position":"166","sequence":"GTRVRAMAIYKQSQHMTEVVRRCPH","specificity":"0.54%","score":"-1.67","aminoAcid":"S"},{"position":"170","sequence":"RAMAIYKQSQHMTEVVRRCPHHERC","specificity":"15.03%","score":"-1.30","aminoAcid":"T"},{"position":"183","sequence":"EVVRRCPHHERCSDSDGLAPPQHLI","specificity":"75.51%","score":"-0.49","aminoAcid":"S"},{"position":"185","sequence":"VRRCPHHERCSDSDGLAPPQHLIRV","specificity":"79.18%","score":"-0.39","aminoAcid":"S"},{"position":"211","sequence":"GNLRVEYLDDRNTFRHSVVVPYEPP","specificity":"35.90%","score":"-1.15","aminoAcid":"T"},{"position":"215","sequence":"VEYLDDRNTFRHSVVVPYEPPEVGS","specificity":"41.99%","score":"-1.10","aminoAcid":"S"},{"position":"227","sequence":"SVVVPYEPPEVGSDCTTIHYNYMCN","specificity":"16.31%","score":"-1.29","aminoAcid":"S"},{"position":"230","sequence":"VPYEPPEVGSDCTTIHYNYMCNSSC","specificity":"17.08%","score":"-1.29","aminoAcid":"T"},{"position":"231","sequence":"PYEPPEVGSDCTTIHYNYMCNSSCM","specificity":"27.69%","score":"-1.21","aminoAcid":"T"},{"position":"240","sequence":"DCTTIHYNYMCNSSCMGGMNRRPIL","specificity":"5.43%","score":"-1.43","aminoAcid":"S"},{"position":"241","sequence":"CTTIHYNYMCNSSCMGGMNRRPILT","specificity":"0.87%","score":"-1.62","aminoAcid":"S"},{"position":"253","sequence":"SCMGGMNRRPILTIITLEDSSGNLL","specificity":"49.55%","score":"-1.03","aminoAcid":"T"},{"position":"256","sequence":"GGMNRRPILTIITLEDSSGNLLGRN","specificity":"53.00%","score":"-0.98","aminoAcid":"T"},{"position":"260","sequence":"RRPILTIITLEDSSGNLLGRNSFEV","specificity":"54.62%","score":"-0.96","aminoAcid":"S"},{"position":"261","sequence":"RPILTIITLEDSSGNLLGRNSFEVR","specificity":"61.03%","score":"-0.84","aminoAcid":"S"},{"position":"269","sequence":"LEDSSGNLLGRNSFEVRVCACPGRD","specificity":"59.54%","score":"-0.87","aminoAcid":"S"},{"position":"284","sequence":"VRVCACPGRDRRTEEENLRKKGEPH","specificity":"96.82%","score":"0.35","aminoAcid":"T"},{"position":"303","sequence":"KKGEPHHELPPGSTKRALPNNTSSS","specificity":"72.09%","score":"-0.59","aminoAcid":"S"},{"position":"304","sequence":"KGEPHHELPPGSTKRALPNNTSSSP","specificity":"84.29%","score":"-0.24","aminoAcid":"T"},{"position":"312","sequence":"PPGSTKRALPNNTSSSPQPKKKPLD","specificity":"84.35%","score":"-0.24","aminoAcid":"T"},{"position":"313","sequence":"PGSTKRALPNNTSSSPQPKKKPLDG","specificity":"79.12%","score":"-0.40","aminoAcid":"S"},{"position":"314","sequence":"GSTKRALPNNTSSSPQPKKKPLDGE","specificity":"86.27%","score":"-0.17","aminoAcid":"S"},{"position":"315","sequence":"STKRALPNNTSSSPQPKKKPLDGEY","specificity":"93.06%","score":"0.12","aminoAcid":"S"},{"position":"329","sequence":"QPKKKPLDGEYFTLQIRGRERFEMF","specificity":"72.95%","score":"-0.56","aminoAcid":"T"},{"position":"362","sequence":"LKDAQAGKEPGGSRAHSSHLKSKKG","specificity":"83.10%","score":"-0.28","aminoAcid":"S"},{"position":"366","sequence":"QAGKEPGGSRAHSSHLKSKKGQSTS","specificity":"82.37%","score":"-0.31","aminoAcid":"S"},{"position":"367","sequence":"AGKEPGGSRAHSSHLKSKKGQSTSR","specificity":"53.88%","score":"-0.97","aminoAcid":"S"},{"position":"371","sequence":"PGGSRAHSSHLKSKKGQSTSRHKKL","specificity":"38.29%","score":"-1.13","aminoAcid":"S"},{"position":"376","sequence":"AHSSHLKSKKGQSTSRHKKLMFKTE","specificity":"68.69%","score":"-0.67","aminoAcid":"S"},{"position":"377","sequence":"HSSHLKSKKGQSTSRHKKLMFKTEG","specificity":"81.28%","score":"-0.34","aminoAcid":"T"},{"position":"378","sequence":"SSHLKSKKGQSTSRHKKLMFKTEGP","specificity":"60.27%","score":"-0.85","aminoAcid":"S"},{"position":"387","sequence":"QSTSRHKKLMFKTEGPDSD******","specificity":"93.76%","score":"0.16","aminoAcid":"T"},{"position":"392","sequence":"HKKLMFKTEGPDSD***********","specificity":"92.51%","score":"0.10","aminoAcid":"S"}],"header":">sp|P04637|P53_HUMAN Cellular tumor antigen p53 OS=Homo sapiens GN=TP53 PE=1 SV=4
"}]}';

$tmpl->dataArray = json_decode($dataString, true);
//var_export($tmpl->dataArray);
$tmpl->dataArray = $tmpl->dataArray['proteins'];
$tmpl->dataHeader = $tmpl->dataArray[0]['header'];

$HTML = $tmpl->build('result.tmpl');
$tmpl->pageContent = $HTML;

print $tmpl->pageContent;
?>