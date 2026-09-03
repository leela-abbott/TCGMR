//Source file: C:\\DATA\\jbproject\\TCGM\\src\\abbott\\ai\\tcgm\\helpers\\ReportMgr.java

package abbott.ai.tcgm.helpers;

//import java.util.*;
import java.io.*;
//import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.data.oracle.*;
//import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.helpers.AS400;
import org.apache.log4j.*;


public class ExportMngr implements TCGMMngr
{

	protected String name = this.getClass().getName();
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.helpers.ReportMngr" );


	/**
   @roseuid 3D4FDE8D0314
   */
	public ExportMngr() {   }

// 9-3-03 Restrictions will not be implemented from the UI
//	public void exportFactors(RSystem rsys, String modelId, String restrictions) throws TCGMException {
	public void exportFactors(RSystem rsys, String modelId) throws TCGMException {
		String methodName = "exportFactors(RSystem rsys, String modelId)";
		String parmList = "model id: " + modelId + ", RSystem: " + rsys.toString();
		try {
			// 9-3-03 Restrictions are not to be implemented from UI
			//File factorData = ExtractComposer.getFactorExtractFile(rsys, modelId, restrictions);
			File factorData = ExtractComposer.getFactorExtractFile(rsys, modelId);
			String jclString = JCLComposer.buildFactorExportJCL(rsys, TCGMUtil.readTextFile(factorData, 80) );
			rsys.sendJCL(jclString);
			factorData.delete();
		}
		catch (IOException ioex) {
			throw new TCGMException(this.name, methodName, parmList, ioex.getMessage() );
		}
	}

	// 9-3-03 Restrictions will not be implemented from the UI
	// public void exportFactorReport(String modelId, String restrictions) throws TCGMException {
	public void exportFactorReport(String modelId, String modelType) throws TCGMException {
		String methodName = "exportRecentFactorReport(String modelId)";
		String parmList = "model id: " + modelId;

		try {
			// 9-3-03 Restrictions will not be implemented from the UI
			// File[] factorFiles = ExtractComposer.getFactorReportExtractFiles(modelId, restrictions);
			File[] factorFiles = ExtractComposer.getFactorReportExtractFiles(modelId, modelType);
			AS400 as400 = new AS400(AppConst.getReportFtpId() );
			String lib = "AITCGDVFIL";
			String member = "M" + TCGMUtil.getRandomDigitStr(6);
			String fileName, fileNameNoExt;
			int fileCount = 0;
			// for (int i = 1; i <=12; i++) {
			for (int i = 0; i <16; i++) {
				if (factorFiles[i] != null) {
					fileName = factorFiles[i].getName();
					int strIdx = fileName.indexOf(".lst");
					fileNameNoExt = fileName.substring(0, strIdx);
					myLogger.debug("File Name is: " + fileNameNoExt);
					// 9-10-03 Per GM, drop the member appendage; This will be a single member file.
					as400.sendTextFile(factorFiles[i], lib + "/" + fileNameNoExt + "." + member);
					myLogger.debug("Sending Factor file: Lib = " + lib + ", Filename = " + fileNameNoExt + ", Mbr = " + member);
					fileCount++;
				}
			}
// 8-19-03 Per GM, The Heading file is the trigger record. so I don't need to call insertFactorReportTriggerRecord
//			Just make sure I send the header file last.
//			as400.insertFactorReportTriggerRecord(fileCount);
		}
		catch (Exception ex) {
			throw new TCGMException(this.name, methodName, parmList, ex.getMessage() );
		}
	}

	public void exportAsrTree(String modelId) throws TCGMException {
		String methodName = "exportAsrTree(String modelId)";
		String parmList = "model id: " + modelId;
		try {
			File asrData = null; // ExtractComposer.getMiscExtractFile(ExtractComposer.EXTRACT_ASR_TREE, modelId, "-1");
			RSystem rs = RSystem.RGM;
			String jclFile = JCLComposer.buildAsrTreeExportJCL(TCGMUtil.readTextFile(asrData, 4096) );
			rs.sendJCL(jclFile);
			asrData.delete();
		}
		catch (IOException ioex) {
			throw new TCGMException(this.name, methodName, parmList, ioex.toString() );
		}
	}
	
	public void exportMVSTree(String fileName) throws TCGMException {
		String methodName = "exportMVSTree()";
		String parmList = "";
		// 12-15-05 Udaya, get filename from parm table.
		RSystem.RGM.sendJCL(JCLComposer.buildBCPExport(fileName));
	}

	public void exportInterCompTrsfr(String fileName, String period) throws TCGMException {
		String methodName = "exportInterCompTrsfr()";
		String parmList = "";
		// 12-15-05 Udaya, get filename from parm table.
		RSystem.RGM.sendJCL(JCLComposer.buildBPCReport(fileName, period));
	}

	public void sendFactRGM(String user, String pass) throws TCGMException {
		String methodName = "sendFactRGM";
		// 12-28-05 Udaya
		RSystem.RGM.sendJCL(JCLComposer.buildSendFactRGM(user,pass));
	}
	public void sendFactRBL(String user, String pass) throws TCGMException {
		String methodName = "sendFactRBL";
		// 12-28-05 Udaya
		RSystem.RGM.sendJCL(JCLComposer.buildSendFactRBL(user,pass));
	}
	public void sendFactRBB(String user, String pass) throws TCGMException {
		String methodName = "sendFactRBB";
		// 12-28-05 Udaya
		RSystem.RGM.sendJCL(JCLComposer.buildSendFactRBB(user,pass));
	}
	public void sendFactRTC(String user, String pass) throws TCGMException {
		String methodName = "sendFactRTC";
		// 12-28-05 Udaya
		RSystem.RGM.sendJCL(JCLComposer.buildSendFactRTC(user,pass));
	}
	
	public void exportFactCCS(String fileName) throws TCGMException {
			String methodName = "exportFactCCS()";			
			RSystem.RGM.sendFactCCS(fileName);
		}
	
	public void exportPricingIPS(String fileName) throws TCGMException {
		String methodName = "exportFactCCS()";			
		RSystem.RGM.sendFactIPS(fileName);
	}

}