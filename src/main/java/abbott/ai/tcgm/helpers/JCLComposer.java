
package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.exception.*;


public final class JCLComposer extends TCGMStringComposer
{
	private static JCLConstants jcl = JCLConstants.getInstance();
	private static String classname = "JCLComposer";

	private JCLComposer()   {   }

	public static String buildAsrTreeExportJCL(String treeData) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_AsrTreeExportHeader() );
		sb.append(treeData + '\n');
		sb.append( jcl.getJCL_AsrTreeExportFooter() );
		return sb.toString();
	}

	public static String buildCostExchFetch(String setName) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_CostExchFetch() );
		parmReplace(sb, "%1", setName);
		return sb.toString();
	}

	public static String buildHeaderTestFetch() {
		StringBuffer sb = new StringBuffer( jcl.getJCL_HeaderTestFetch() );
		return sb.toString();
	}
	public static String buildPackCodesFetch() {
		StringBuffer sb = new StringBuffer( jcl.getJCL_PackCodeFetch() );
		return sb.toString();
	}

	public static String buildHeaderProdFetch() {
		StringBuffer sb = new StringBuffer( jcl.getJCL_HeaderProdFetch() );
		return sb.toString();
	}

	public static String buildEssbaseRTCLoad(String year, String version, String loadType, String sourceFile, String generation) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_EssbaseRTCLoad() );
		parmReplace(sb, "%1", year);
		parmReplace(sb, "%2", version);
		parmReplace(sb, "%3", loadType);
		parmReplace(sb, "%Y", sourceFile);
		parmReplace(sb, "%Z", generation);
		return sb.toString();
	}

	public static String buildEssbaseRGMLoad(String month, String year, String version, String loadTyp) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_EssbaseRGMLoad() );
		parmReplace(sb, "%1", month);
		parmReplace(sb, "%2", year);
		parmReplace(sb, "%3", version);
		parmReplace(sb, "%Y", loadTyp);

		return sb.toString();
	}

	public static String buildBPCRecordHeader(String treeFileName) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_BPCRecordHeader() );
		parmReplace(sb, "%1", treeFileName);
		return sb.toString();
	}

	// SendTreeMVS
	public static String buildBCPExport(String treeFileName) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_BPCExport());
		parmReplace(sb, "%1", treeFileName);
		return sb.toString();
	}
	//Inter Company Transfer
	public static String buildBPCReport(String treeFileName, String period) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_BPCReport());
		parmReplace(sb, "%1", period);
		parmReplace(sb, "%2", treeFileName);
		return sb.toString();
	}
	//Send Factors RGM
	public static String buildSendFactRGM(String user, String pass) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_SendFactRGM());
		parmReplace(sb, "%1", user);
		parmReplace(sb, "%2", pass);
		return sb.toString();
	}

	//Send Factors RBL
	public static String buildSendFactRBL(String user, String pass) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_SendFactRBL());
		parmReplace(sb, "%1", user);
		parmReplace(sb, "%2", pass);
		return sb.toString();
	}

	//Send Factors RBB
	public static String buildSendFactRBB(String user, String pass) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_SendFactRBB());
		parmReplace(sb, "%1", user);
		parmReplace(sb, "%2", pass);
		return sb.toString();
	}

	//Send Factors RTC
	public static String buildSendFactRTC(String user, String pass) {
		StringBuffer sb = new StringBuffer( jcl.getJCL_SendFactRTC());
		parmReplace(sb, "%1", user);
		parmReplace(sb, "%2", pass);
		return sb.toString();
	}

	public static String buildUnitFetchJCL(String cyclename, RSystem rsys, String genDataGroup) throws TCGMException
	{
		String methodName = "buildUnitFetchJCL(String cyclename, RSystem rsys, String genDataGroup)";
		String parmList = "Cyclename: " + cyclename + ", RSystem: " + rsys + ", Generational Data Group: " + genDataGroup;
		StringBuffer sb = new StringBuffer( jcl.getJCL_UnitFetch() );
		String location = null;
		if (rsys == RSystem.RBB)
			location = jcl.UNIT_FILE_NAME_RBB;
		else if (rsys == RSystem.RBL)
			location = jcl.UNIT_FILE_NAME_RBL;
		else if (rsys == RSystem.RGM)
			location = jcl.UNIT_FILE_NAME_RGM;
		else if (rsys == RSystem.RTC)
			location = jcl.UNIT_FILE_NAME_RTC;
		else
			throw new TCGMException(classname, methodName, parmList, "RSystem Not Supported for this operation");
		parmReplace(sb, "%1", rsys.toString() );
		parmReplace(sb, "%2",  cyclename);
		parmReplace(sb, "%3",  genDataGroup); // Generational Data Group
		parmReplace(sb, "%E",  location); //UNIT File Name


// 12-20-05 JCL does not appear to use a 4th parm
//		  if (rsys == RSystem.RBB)
//			  location = jcl.UNIT_LOC_RBB;
//		  else if (rsys == RSystem.RBL)
//			  location = jcl.UNIT_LOC_RBL;
//		  else if (rsys == RSystem.RGM)
//			  location = jcl.UNIT_LOC_RGM;
//		  else if (rsys == RSystem.RTC)
//			  location = jcl.UNIT_LOC_RTC;
//		  else
//			  throw new TCGMException(classname, methodName, parmList, "RSystem Not Supported for this operation");
//
//		  parmReplace(sb, "%4", location);
		return sb.toString();
	}

	public static String buildFactorExportJCL(RSystem rsys, String factorData) throws TCGMException {
		String methodName = "buildFactorExportJCL(RSystem rsys, String factorData)";
		String parmList = "RSystem: " + rsys.toString() + " Factor Data (truncated): " + factorData.substring(0, 100);
		String factorHdr;
		String factorFtr;

		if (rsys == RSystem.RBB) {
			factorHdr = jcl.getJCL_FactorRBBRecordHeader();
			factorFtr = jcl.getJCL_FactorRBBRecordFooter();
		}
		else if (rsys == RSystem.RBL){
			factorHdr = jcl.getJCL_FactorRBLRecordHeader();
			factorFtr = jcl.getJCL_FactorRBLRecordFooter();
		}
		else if (rsys == RSystem.RGM){
			factorHdr = jcl.getJCL_FactorRGMRecordHeader();
			factorFtr = jcl.getJCL_FactorRGMRecordFooter();
		}
		else if (rsys == RSystem.RTC){
			factorHdr = jcl.getJCL_FactorRTCRecordHeader();
			factorFtr = jcl.getJCL_FactorRTCRecordFooter();
		}
		else
			throw new TCGMException(classname, methodName, parmList, "RSystem Not Supported for this operation");

		return ( factorHdr + "\r\n" + factorData + "\r\n" + factorFtr);
	}


}