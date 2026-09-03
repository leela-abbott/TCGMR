//Source file: C:\\DATA\\jbproject\\TCGM\\src\\abbott\\ai\\tcgm\\helpers\\JCLConstants.java

package abbott.ai.tcgm.helpers;

import java.io.*;
//import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.*;
import org.apache.log4j.Logger;

public class JCLConstants
{
	private static JCLConstants _instance = null;
	private static String JCL_UnitFetch;

	private static String JCL_CostExchFetch;

	private static String JCL_EssbaseRTCLoad;
	private static String JCL_EssbaseRGMLoad;
	private static String JCL_BPCRecordHeader;
	private static String JCL_BPCExport; //SendTreeMVS
	private static String JCL_BPCReport; //InterCompanyTransfer
	private static String JCL_SendFactRGM; //Send Factors -RGM
	private static String JCL_SendFactRBL; //Send Factors -RBL
	private static String JCL_SendFactRBB; //Send Factors -RBB
	private static String JCL_SendFactRTC; //Send Factors -RTC

	private static String JCL_HeaderProdFetch;
	private static String JCL_HeaderTestFetch;
	private static String JCL_PackCodeFetch;

	private static String JCL_AsrTreeExportHeader;
	private static String JCL_AsrTreeExportFooter;

	private static String JCL_FactorRGMRecordHeader;
	private static String JCL_FactorRTCRecordHeader;
	private static String JCL_FactorRBBRecordHeader;
	private static String JCL_FactorRBLRecordHeader;

	private static String JCL_FactorRGMRecordFooter;
	private static String JCL_FactorRTCRecordFooter;
	private static String JCL_FactorRBBRecordFooter;
	private static String JCL_FactorRBLRecordFooter;

	private static Logger logger = Logger.getLogger("TCGM.Helpers.JCLConstants");
	public static final String className = JCLConstants.class.getName();
	public static final String UNIT_FILE_NAME_RBB = "ABT.AB.RBB.RBBD20(+0)";
	public static final String UNIT_FILE_NAME_RBL = "ABT.AB.RBL.RBLD20(+0)";
	public static final String UNIT_FILE_NAME_RTC = "ABT.AB.RTC.RTCD20(+0)";
	public static final String UNIT_FILE_NAME_RGM = "ABT.AB.RGM.RGMD20.ORIG(+0)";

	/**
   @roseuid 3D77CA53032B
   */
	private JCLConstants()
	{
		// Load constant strings
		try {
		   AppConst ac = AppConst.getInstance();
		   //String jcldir = ac.getWebInfDir() + File.separatorChar + "jcl";
		   String jcldir = ac.getWebInfDir() + File.separatorChar + "jcl" + File.separatorChar + ac.getJclPath();

			this.JCL_UnitFetch = TCGMUtil.readTextFile(new File(jcldir, "JCLUnitFetch.txt"), new Long(new File(jcldir, "JCLUnitFetch.txt").length()).intValue());

			this.JCL_CostExchFetch = TCGMUtil.readTextFile(new File(jcldir, "JCLCostExchFetch.txt"), new Long(new File(jcldir, "JCLCostExchFetch.txt").length()).intValue());

			this.JCL_EssbaseRTCLoad = TCGMUtil.readTextFile(new File(jcldir, "JCLEssbaseRTCLoad.txt"), new Long(new File(jcldir, "JCLEssbaseRTCLoad.txt").length()).intValue());
			this.JCL_EssbaseRGMLoad = TCGMUtil.readTextFile(new File(jcldir, "JCLSendRGMEssbase.txt"), new Long(new File(jcldir, "JCLSendRGMEssbase.txt").length()).intValue());

			this.JCL_HeaderTestFetch = TCGMUtil.readTextFile(new File(jcldir, "JCLHeaderTestFetch.txt"), new Long(new File(jcldir, "JCLHeaderTestFetch.txt").length()).intValue());
			this.JCL_HeaderProdFetch = TCGMUtil.readTextFile(new File(jcldir, "JCLHeaderProdFetch.txt"), new Long(new File(jcldir, "JCLHeaderProdFetch.txt").length()).intValue());
			this.JCL_PackCodeFetch = TCGMUtil.readTextFile(new File(jcldir, "JCLPackCodeFetch.txt"), new Long(new File(jcldir, "JCLPackCodeFetch.txt").length()).intValue());

		//	this.JCL_FactorRGMRecordHeader = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRGMRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRGMRecordHeader.txt").length()).intValue());
		//	this.JCL_FactorRTCRecordHeader = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRTCRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRTCRecordHeader.txt").length()).intValue());
		//	this.JCL_FactorRBBRecordHeader = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRBBRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRBBRecordHeader.txt").length()).intValue());
		//	this.JCL_FactorRBLRecordHeader = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRBLRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRBLRecordHeader.txt").length()).intValue());

		//	this.JCL_FactorRGMRecordFooter = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRGMRecordFooter.txt"), new Long(new File(jcldir, "JCLFactorRGMRecordFooter.txt").length()).intValue());
		//	this.JCL_FactorRTCRecordFooter = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRTCRecordFooter.txt"), new Long(new File(jcldir, "JCLFactorRTCRecordFooter.txt").length()).intValue());
		//	this.JCL_FactorRBBRecordFooter = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRBBRecordFooter.txt"), new Long(new File(jcldir, "JCLFactorRBBRecordFooter.txt").length()).intValue());
		//	this.JCL_FactorRBLRecordFooter = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRBLRecordFooter.txt"), new Long(new File(jcldir, "JCLFactorRBLRecordFooter.txt").length()).intValue());
			//this.JCL_BPCRecordHeader       = TCGMUtil.readTextFile(new File(jcldir, "JCLBPCRecordHeader.txt"), new Long(new File(jcldir, "JCLBPCRecordHeader.txt").length()).intValue());
			this.JCL_BPCExport		       = TCGMUtil.readTextFile(new File(jcldir, "JCLBPCEXPORT.txt"), new Long(new File(jcldir, "JCLBPCEXPORT.txt").length()).intValue());
			this.JCL_BPCReport		       = TCGMUtil.readTextFile(new File(jcldir, "JCLBPCReport.txt"), new Long(new File(jcldir, "JCLBPCReport.txt").length()).intValue());
			this.JCL_SendFactRGM	       = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRGMRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRGMRecordHeader.txt").length()).intValue());
			this.JCL_SendFactRBL	       = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRBLRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRBLRecordHeader.txt").length()).intValue());
			this.JCL_SendFactRBB	       = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRBBRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRBBRecordHeader.txt").length()).intValue());
			this.JCL_SendFactRTC	       = TCGMUtil.readTextFile(new File(jcldir, "JCLFactorRTCRecordHeader.txt"), new Long(new File(jcldir, "JCLFactorRTCRecordHeader.txt").length()).intValue());

			// these aren't defined yet
			this.JCL_AsrTreeExportHeader = null; // TCGMUtil.readTextFile(new File(jcldir, "JCLAsrTreeExportHeader.txt"), 4096);
			this.JCL_AsrTreeExportFooter = null; // TCGMUtil.readTextFile(new File(jcldir, "JCLAsrTreeExportFooter.txt"), 4096);

		}
		catch (Exception ex) {
			logger.error("Error Finding JCL Definition File: " + ex.toString() );
		}
	}


	public static JCLConstants getInstance()  {
		if(_instance == null)
		{
			_instance = new JCLConstants();
		}

		return _instance;
	}

	public String getJCL_AsrTreeExportHeader() {
		return JCL_AsrTreeExportHeader;
	}
	public String getJCL_AsrTreeExportFooter() {
		return JCL_AsrTreeExportFooter;
	}

	public String getJCL_FactorRBBRecordFooter() {
		return JCL_FactorRBBRecordFooter;
	}
	public String getJCL_FactorRBBRecordHeader() {
		return JCL_FactorRBBRecordHeader;
	}
	public String getJCL_FactorRGMRecordFooter() {
		return JCL_FactorRGMRecordFooter;
	}
	public String getJCL_FactorRGMRecordHeader() {
		return JCL_FactorRGMRecordHeader;
	}
	public String getJCL_FactorRTCRecordFooter() {
		return JCL_FactorRTCRecordFooter;
	}
	public String getJCL_FactorRTCRecordHeader() {
		return JCL_FactorRTCRecordHeader;
	}

	public String getJCL_FactorRBLRecordFooter() {
		return JCL_FactorRBLRecordFooter;
	}
	public String getJCL_FactorRBLRecordHeader() {
		return JCL_FactorRBLRecordHeader;
	}


	public String getJCL_HeaderTestFetch() {
		return JCL_HeaderTestFetch;
	}
	public String getJCL_PackCodeFetch() {
		return JCL_PackCodeFetch;
	}
	public String getJCL_HeaderProdFetch() {
		return JCL_HeaderProdFetch;
	}

	public String getJCL_CostExchFetch() {
		return JCL_CostExchFetch;
	}

	public String getJCL_EssbaseRTCLoad() {
		return JCL_EssbaseRTCLoad;
	}

	public String getJCL_EssbaseRGMLoad() {
		return JCL_EssbaseRGMLoad;
	}

	public String getJCL_BPCRecordHeader() {
		return JCL_BPCRecordHeader;
	}

	public String getJCL_BPCExport() {
		return JCL_BPCExport;
	}

	public String getJCL_BPCReport() {
		return JCL_BPCReport;
	}

	public String getJCL_SendFactRGM() {
		
		return JCL_SendFactRGM;
	}

	public String getJCL_SendFactRBL() {
		return JCL_SendFactRBL;
	}
	public String getJCL_SendFactRBB() {
		return JCL_SendFactRBB;
	}

	public String getJCL_SendFactRTC() {
		return JCL_SendFactRTC;
	}

	public String getJCL_UnitFetch() {
		return JCL_UnitFetch;
	}

}