package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.entities.UserToken;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.data.as400.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.AppConst;
import java.io.*;
import abbott.ai.tcgm.process.JobConstants;

import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class EssbaseMngr implements TCGMMngr {
	private static Logger myLogger = Logger.getLogger( "EssbaseMngr" );
	public static final String ESS_ANALYSIS_TYPE1 = "A1";
	public static final String ESS_ANALYSIS_TYPE2 = "A2";
	public static final String ESS_ANALYSIS_TYPE3 = "A3";
	public static final String ESS_ANALYSIS_TYPE4 = "A4";
	public static final String ESS_FLEX_TYPE1 = "F2";
	public static final String ESS_FLEX_TYPE2 = "F2";

	public EssbaseMngr() {
	}

	public void intiateRGMLoad(String month, String year, String version, String loadTyp) throws TCGMException {
//		AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
//		essdao.initiateRGMLoad(month, year, version);
		String jcl = JCLComposer.buildEssbaseRGMLoad(month, year, version, loadTyp);
		myLogger.info("jcl text = ");
		myLogger.info(jcl); 
		System.out.println("Start of RGM JCL");
		System.out.println(jcl);
		System.out.println("End of RGM JCL");
		RSystem.RGM.sendJCL(jcl);

	}
	public void initiateRTCLoad(String year, String version, String loadType, String sourceFile, String generation) throws TCGMException {
		if(sourceFile.equalsIgnoreCase("Current"))
			sourceFile = "RTCD20";	
		if(sourceFile.equalsIgnoreCase("Backup"))
			sourceFile = "RTCD25";
		String jcl = JCLComposer.buildEssbaseRTCLoad(year, version, loadType, sourceFile, generation);
		myLogger.info("jcl text = ");
		myLogger.info(jcl); 
		System.out.println("Start of RTC JCL");
		System.out.println(jcl);
		System.out.println("End of RTC JCL");
		RSystem.RTC.sendJCL(jcl);
	}
	public void initiateVersionCopy(String year, String fversion, String tversion) throws TCGMException {
		AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
		essdao.initiateVersionCopy(year, fversion, tversion);
	}
	public void initiateALOGLoad(String year, String version) throws TCGMException {
		AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
		essdao.initiateALOGLoad(year, version);
	}

	public void exportEssbaseAnalysis(String modelId, String year, String version, String type) throws TCGMException {
		// 9-3-03 Although retrieving the userToken with this hard-coded approach works, I have
		//        since moved the essbase signon to the web.xml file. At some point, I should
		//        remove the hard-coded password here and try using the AppConst below
		
		// 10-21-05 Removed hard-coding of user token below to test retrieval from config file
		// UserToken as400ut = new UserToken("DTAWHSFTP", "DATAWHSEIN");
		// AS400 as400 = new AS400( as400ut );
		
		// 10-21-05 Add line below in lieu of the hard coding of the Data Whse Profile above
		AS400 as400 = new AS400( AppConst.getInstance().getEssbaseId() );
		
		//File f = ExtractComposer.getEssbaseExtractFile(type, modelId, "-1");
		//String strFileName = "\\\\aiapodiv01d\\ManageData\\EXPORTS\\ESSBASE\\ESS_ANLA2A3.txt";
		String strFileName = JobConstants.ESSBASE_EXP_DIR+JobConstants.ESSBASE_FILE_NAME;
		File f = new File(strFileName);
		long fileLength = f.length();
		// 7-17-03 bd; get the actual name form Bob
		// String filename = "essbasefilename"; 
		//String filename = "RLTCGMDATA";
		String filename = AppConst.essbaseFilename;
		String member = "M" + TCGMUtil.getRandomDigitStr(6);

		// 8-27-03 In production, Bob S will not be expecting a multiple mbr file; 
		//         But having one now facilitates testing
		//as400.sendTextFile(f, "DWPROD" + "/" + filename +  "." + member);
		//as400.sendTextFile(f, "DWPROD" + "/" + filename);
		as400.sendTextFile(f, AppConst.essbaseLibrary + "/" + filename);

		// 8-14-03 Send Trigger
		//AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
		as400.insertAnlFlexEssTriggerRecord(year, version, type);

	}
	
	public void exportEssbaseTProduct(String modelId) throws TCGMException {
		
		AS400 as400 = new AS400( AppConst.getInstance().getEssbaseId() );
	
		String strFileName = JobConstants.ESSBASE_EXP_DIR+JobConstants.ESSBASE_FILE_NAME;
		File f = new File(strFileName);
		long fileLength = f.length();

		String filename = "RLPRDWRK";//AppConst.essbaseFilename;
		String member = "M" + TCGMUtil.getRandomDigitStr(6);

		as400.sendTextFile(f, AppConst.essbaseLibrary + "/" + filename);
		
		as400.insertProductEssTriggerRecord("Y");

	}
	public void exportEssbaseAffiliate(String modelId) throws TCGMException {
		
			AS400 as400 = new AS400( AppConst.getInstance().getEssbaseId() );
	
			String strFileName = JobConstants.ESSBASE_EXP_DIR+JobConstants.ESSBASE_FILE_NAME;
			File f = new File(strFileName);
			long fileLength = f.length();
			String filename = "RLLOCWRK";//AppConst.essbaseFilename;
			String member = "M" + TCGMUtil.getRandomDigitStr(6);

			as400.sendTextFile(f, AppConst.essbaseLibrary + "/" + filename);

			as400.insertAffEssTriggerRecord("Y");

		}

}