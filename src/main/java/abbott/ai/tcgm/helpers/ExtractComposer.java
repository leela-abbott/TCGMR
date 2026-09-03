
package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
import java.io.*;
import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.*;
import org.apache.log4j.Logger;
import abbott.ai.tcgm.process.JobConstants;

public final class ExtractComposer extends TCGMStringComposer
{
	private static final String className = "ExtractComposer";
	private static String templateFileString = null;
	private static String classname = "ExtractComposer";
	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.helpers.ExtractComposer");

	private static String SQL_EXTRACT_ASR_TREE = "SELECT * FROM ASR_TREE WHERE MODEL_ID='%3'";

// 9-9-03 All "Send Factor Report" SQL Statements
	private static String SQL_EXTRACT_FACTOR_RPT_COST_SELECT  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_COST_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_FACT_SELECT  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_FACT_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_NOTES_SELECT = " SELECT OUTPUT FROM TCGM.VW_FACRPT_NOTES_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PHANTOM_SELECT = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PHANTOM_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_HDG_SELECT   = " SELECT OUTPUT FROM TCGM.VW_FACRPT_HEADING_AS400_OUTPUT WHERE MODEL_ID = %3 and DATASET_TABLE_ID = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_1  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE1_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_2  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE2_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_3  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE3_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_4  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE4_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_5  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE5_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_6  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE6_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_7  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE7_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_8  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE8_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_9  = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE9_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_10 = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE10_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_11 = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE11_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";
	private static String SQL_EXTRACT_FACTOR_RPT_PE_SELECT_12 = " SELECT OUTPUT FROM TCGM.VW_FACRPT_PE12_AS400_OUTPUT WHERE model_id = %3 AND dataset_table_id = %4; ";

	private static String SQL_EXTRACT_FACTOR_SELECT = " SELECT TRANS || TCGM.AFF_STRIP(END_AFF) || END_INV_CD || END_LIST || END_LABEL || END_SIZE || TCGM.PACK_STRIP(END_PACK) || ' ' || DECODE (TO_NUMBER(MONTH),10,'10',11,'11',12,'12','0' || TRIM(MONTH)) || '00' || ' ' || '0000000' || ' ' || '0000000' || ' ' || '0000000' || ' ' || '0000000' || FACTOR_BCD || '0000000' || '        ' OUTPUT ";
	// 8-8-03 bd; This SQL is wrong; Get the SQL from Paul
	//private static String SQL_EXTRACT_FACTOR_RPT_SELECT = " SELECT TRANS || TCGM.AFF_STRIP(END_AFF) || END_INV_CD || END_LIST || END_LABEL || END_SIZE || TCGM.PACK_STRIP(END_PACK) || ' ' || DECODE (TO_NUMBER(MONTH),10,'10',11,'11',12,'12','0' || TRIM(MONTH)) || '00' || ' ' || '0000000' || ' ' || '0000000' || ' ' || '0000000' || ' ' || '0000000' || FACTOR_BCD || '0000000' || '        ' OUTPUT ";
	//private static String SQL_EXTRACT_FACTOR_RPT_PERIOD_DATA_SELECT = " SELECT * FROM FACTORS_";

	// SQL_ESS_ANLSUM_SELECT is sending data to a comma-delimited file so no need to LPAD
	private static String SQL_ESS_ANL_A2_A3_SELECT = " SELECT ESS_YEAR||','" +
			"||ESS_VERS||','" +
			"||TCGM.AFF_STRIP(ESS_RPT_AFF)||','" +
			"||ESS_GRP||','" +
			"||BPXCHG_VOL||','" +
			"||CSTXHG_VOL " +
			"FROM TCGM.ESS_ANL_SUM " +
			"WHERE MODEL_ID = %3 and DATASET_TABLE_ID = %4; ";
	private static String SQL_ESS_ANL_A1_A4_SELECT = " SELECT ESS_YEAR||','" +
			"||ESS_VERS||','" +
			"||TCGM.AFF_STRIP(ESS_RPT_AFF)||','" +
			"||ESS_GRP||','" +
			"||BPXCHG_VOL||','" +
			"||CSTXHG_VOL||','" +
			"||COST_DIFF " +
			"FROM TCGM.ESS_ANLON_SUM " +
			"WHERE MODEL_ID = %3 and DATASET_TABLE_ID = %4; ";
	private static String SQL_ESS_FLEX_SELECT = " SELECT ESS_YEAR||','" +
			"||ESS_VERS||','" +
			"||TCGM.AFF_STRIP(ESS_RPT_AFF)||','" +
			"||ESS_GRP||','" +
			"||ESS_MRG_TTL " +
			"FROM TCGM.ESS_FLXMRGN_DATA " +
			"WHERE MODEL_ID = %3 and DATASET_TABLE_ID = %4; ";

	private static String ORDER_BY = " ORDER BY ";

	public static final int EXTRACT_ASR_TREE = 1;

	/**
   @roseuid 3D77CA29036F
   */
	private ExtractComposer() {}

		private static String getTemplateFileString() throws TCGMException {
			if (templateFileString == null)
					readTemplateFileString();
			return templateFileString;
		}

// 7-17 bd; essbaseType sd b an int (type of file I'm making)
	public static File getEssbaseExtractFile(String essbaseType, String modelId, String datasetId) throws TCGMException {
		String sql = null;
		String lineWidth = "100";

		if( (essbaseType.equals(JobConstants.PN_ESSBASE_ANALYSIS_2)) || (essbaseType.equals(JobConstants.PN_ESSBASE_ANALYSIS_3)) )
		{
			sql = SQL_ESS_ANL_A2_A3_SELECT;
		}
		else if((essbaseType.equals(JobConstants.PN_ESSBASE_ANALYSIS_1)) || (essbaseType.equals(JobConstants.PN_ESSBASE_ANALYSIS_4)) )
		{
			sql = SQL_ESS_ANL_A1_A4_SELECT;
		}
		else if((essbaseType.equals(JobConstants.PN_ESSBASE_FLEX_1)) || (essbaseType.equals(JobConstants.PN_ESSBASE_FLEX_2)) )
		{
			sql = SQL_ESS_FLEX_SELECT;
		}

		String randomFilename = "extract_data_" + TCGMUtil.getRandomDigitStr(6);

		String extractTemplate = buildExtractTemplate(sql, modelId, datasetId, randomFilename, lineWidth);
		return getExtractFile(extractTemplate, randomFilename);
	}


	private static String buildExtractTemplate(String extractSql, String modelId, String datasetId, String fileName, String lineWidth) throws TCGMException
	{
		String methodName = "buildExtractTemplate(String extractSql, String modelId, String datasetId, String fileName)";
		String parmList = "Model Id: " + modelId + ", dataset id: " + datasetId + ", fileName: " + fileName + ", sql: " + extractSql;
		StringBuffer sb = new StringBuffer( getTemplateFileString() );

		parmReplace(sb, "%1", fileName );
		parmReplace(sb, "%2", extractSql );
		parmReplace(sb, "%3", modelId );
		parmReplace(sb, "%4", datasetId );
		parmReplace(sb, "%5", lineWidth );

		return sb.toString();
	}

	private static File getExtractFile(String template, String fileName) throws TCGMException {

		String methodName = "getExtractFile(String template, String fileName)";
		String parameterList = "template: " + template + ", filename: " + fileName;

		String extractTemplateName = "extract_template_" + TCGMUtil.getRandomDigitStr(6) + ".sql";

		File templateFile = new File(extractTemplateName);
		try {
			FileWriter fw = new FileWriter(templateFile);
			fw.write(template);
			fw.close();
		}
		catch (IOException ioex) {
			throw new TCGMException(className, methodName, parameterList, ioex.toString() );
		}

		logger.debug("Template file written: " + templateFile.getAbsolutePath() );

		File outputFile = null;
		if (executeSpool(templateFile) ) {
			outputFile = new File(fileName.trim() + ".lst");
			logger.debug("Output file: " + outputFile.getAbsolutePath() );
		}
		else {
			throw new TCGMException(className, methodName, parameterList, "Unable to create spool file" );
		}
		logger.debug("Before Delete of template file: " );
		templateFile.delete();
		logger.debug("After Delete of template file: " );
		return outputFile;
	}

//	public static File getReportExtractFile( ReportDefinition reportDef, ReportInstance reportInstance ) throws TCGMException {
//		String methodName = "getReportExtractFile( ReportDefinition reportDef, ReportInstance reportInstance )";
//		String parameterList = "reportDef: " + reportDef.toString() + ", reportInstance: " + reportInstance.toString();
//		String sqlExtract = reportDef.getSelect();
//		// 9-3-03 Restrictions will not be implemented in the UI
//		// String restrictions = reportInstance.getJobInstance().getJobParms().getProperty(JobConstants.PN_RESTRICTIONS);
//		// if (!TCGMUtil.isEmpty(restrictions))
//		//    sqlExtract += " AND " + restrictions; //MURALRS 05/01
//
//		// 7-29-03 Added check to avoid blank ORDER clauses
//		if (!(reportDef.getOrderBy()==null) && !(reportDef.getOrderBy()=="")) {
//		   sqlExtract +=  (ORDER_BY + reportDef.getOrderBy() + ";"); //MURALRS 05/21
//		}
//		else {
//		   sqlExtract +=  ";";
//		}
//
//		String extractString = ExtractComposer.buildExtractTemplate(sqlExtract,	reportInstance.getJobInstance().getModelId(), reportInstance.getDatasetId(), reportDef.getName(), reportDef.getAS400WidthStr() );
//		return getExtractFile(extractString, reportDef.getName());
//	}

// 9-3-03 Restrictions are not to be implemented from the UI
//	public static File getFactorExtractFile( RSystem rsys, String modelId, String restrictions ) throws TCGMException {
	public static File getFactorExtractFile( RSystem rsys, String modelId) throws TCGMException {
		// String methodName = "getFactorExtractFile( RSystem rsys, String modelId, String restrictions )";
		String methodName = "getFactorExtractFile( RSystem rsys, String modelId)";
		// String parameterList = "rsys: " + rsys.toString() + ", Model Id: " + modelId + ", restrictions: " + restrictions;
		String parameterList = "rsys: " + rsys.toString() + ", Model Id: " + modelId;
		String sqlExtract = SQL_EXTRACT_FACTOR_SELECT;
		sqlExtract += " FROM " + SQLUtil.getDatasourceSchemaName() + "." + rsys.toString().toUpperCase() + "_EXPORT WHERE MODEL_ID=%3 ";

		// 9-3-03 Restrictions are not to be implemented from the UI
		// if (!TCGMUtil.isEmpty(restrictions))
		//  	sqlExtract += " AND " + restrictions; //MURALRS 05/01

		sqlExtract += ";"; //MURALRS 04/03

		String fileName = "FACTOR_EXTRACT_" + rsys.toString();
		String extractString = ExtractComposer.buildExtractTemplate(sqlExtract,	modelId, "-1", fileName, "80" );

		return getExtractFile(extractString, fileName);
	}

// 9-3-03 Restrictions will not be implemented from the UI
//	public static File[] getFactorReportExtractFiles( String modelId, String restrictions ) throws TCGMException {
	public static File[] getFactorReportExtractFiles( String modelId, String modelType) throws TCGMException {
//		String methodName = "getFactorReportExtractFile( String modelId, String restrictions )";
		String methodName = "getFactorReportExtractFile( String modelId)";
//		String parameterList = "Model Id: " + modelId + ", restrictions: " + restrictions;
		String parameterList = "Model Id: " + modelId;

		String sqlWhereClause = " WHERE model_id = %3 AND dataset_table_id = %4";
		StringBuffer sbWC = new StringBuffer( sqlWhereClause );
		parmReplace(sbWC, "%3", modelId );
		parmReplace(sbWC, "%4", "-1" );

//		String sqlExtractFact = SQL_EXTRACT_FACTOR_RPT_FACT_SELECT + " FROM TCGM.TEMP_FACTORS_SEND_FACRPT " +
//																	 " WHERE model_id = %3 AND dataset_table_id = %4";
		String sqlExtractFact = SQL_EXTRACT_FACTOR_RPT_FACT_SELECT;
		String sqlExtractCost = SQL_EXTRACT_FACTOR_RPT_COST_SELECT;
		String sqlExtractPe = "";
		String sqlExtractPhn = SQL_EXTRACT_FACTOR_RPT_PHANTOM_SELECT;
		String sqlExtractNotes = SQL_EXTRACT_FACTOR_RPT_NOTES_SELECT;
		String sqlExtractHdg = SQL_EXTRACT_FACTOR_RPT_HDG_SELECT;

		int recCount = 0;
		String fileCode;
		//fileCode = ((FactorModel) new ModelMngr().getModelFromId( SQLUtil.getOracleAdmin(), Integer.parseInt(modelId), TCGMModel.Type.FACTOR)).getModelCycleShortName();
		//fileCode = fileCode.substring(0,1);
		fileCode = modelType;

		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(SQLUtil.getOracleAdmin() );
		File fileList[] = new File[16];

		int i = 0;
		String extractString;

//Cost
		if ( rd.hasData(" TCGM.RES_COST_DET ", sbWC.toString()) ) {
			String ftpTargetCostFileName = "WN" + fileCode + "COST";
			extractString = ExtractComposer.buildExtractTemplate(sqlExtractCost, modelId, "-1", ftpTargetCostFileName, "82" );
			fileList[i] = getExtractFile(extractString, ftpTargetCostFileName);
		}
//Factor
		if ( rd.hasData(" TCGM.TEMP_FACTORS_SEND_FACRPT ", sbWC.toString()) ) {
			String ftpTargetFactFileName = "WN" + fileCode + "FACT";
			extractString = ExtractComposer.buildExtractTemplate(sqlExtractFact, modelId, "-1", ftpTargetFactFileName, "681" );
			++i;
			fileList[i] = getExtractFile(extractString, ftpTargetFactFileName);
		}
//PE
		String ftpTargetPeFileName;
		for(int j = 1; j <= 12; j++)
		{
			if (j < 10)
			{
				ftpTargetPeFileName = "WN" + fileCode + "PE0" + j;
			}
			else
			{
				ftpTargetPeFileName = "WN" + fileCode + "PE" + j;
			}

			switch (j)
			{
				case  1: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_1;  break;
				case  2: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_2;  break;
				case  3: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_3;  break;
				case  4: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_4;  break;
				case  5: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_5;  break;
				case  6: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_6;  break;
				case  7: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_7;  break;
				case  8: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_8;  break;
				case  9: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_9;  break;
				case 10: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_10; break;
				case 11: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_11; break;
				case 12: sqlExtractPe = SQL_EXTRACT_FACTOR_RPT_PE_SELECT_12; break;
			}
			if ( rd.hasData(" TCGM.PE" + j + " ", sbWC.toString()) )
			{
				extractString = ExtractComposer.buildExtractTemplate(sqlExtractPe, modelId, "-1", ftpTargetPeFileName, "681" );
				++i;
				fileList[i] = getExtractFile(extractString, ftpTargetPeFileName);
			}
		}
//Phantom
		if ( rd.hasData(" TCGM.RES_PHNTM ", sbWC.toString()) ) {
			String ftpTargetPhnFileName = "WN" + fileCode + "PHNTM";
			extractString = ExtractComposer.buildExtractTemplate(sqlExtractPhn, modelId, "-1", ftpTargetPhnFileName, "103" );
			++i;
			fileList[i] = getExtractFile(extractString, ftpTargetPhnFileName);
		}
//Notes
		if ( rd.hasData(" TCGM.RES_NOTES ", sbWC.toString()) ) {
			String ftpTargetNotesFileName = "WN" + fileCode + "NOTES";
			extractString = ExtractComposer.buildExtractTemplate(sqlExtractNotes, modelId, "-1", ftpTargetNotesFileName, "304" );
			++i;
			fileList[i] = getExtractFile(extractString, ftpTargetNotesFileName);
		}
//Heading
		if ( rd.hasData(" TCGM.HEADINGS_SEND_FACRPT ", sbWC.toString()) ) {
			String ftpTargetHdgFileName = "WN" + fileCode + "HDG";
			extractString = ExtractComposer.buildExtractTemplate(sqlExtractHdg, modelId, "-1", ftpTargetHdgFileName, "64" );
			++i;
			fileList[i] = getExtractFile(extractString, ftpTargetHdgFileName);
		}

		return fileList;
	}

	private static boolean executeSpool(File file) throws TCGMException {
		String methodName = "executeSpool(File file)";
		String parameterList = "file: " + file.toString();

		boolean success = false;
		Process p = null;
		InputStream inp = null;
		InputStream inpE = null;
		UserToken ut = SQLUtil.getOracleAdmin();
		String tnsName = AppConst.getInstance().getDbTnsName();

		try {
			String cmd = "SQLPLUS " + ut.getUserid() + "/" + ut.getPassword() + "@" + tnsName + " @" + file.getAbsolutePath();
			logger.debug("Preparing to execute spoolfile. Cmd: " + cmd);
			p = Runtime.getRuntime().exec( cmd );
			logger.debug("Waiting after execute of SQL Command: " );
			p.waitFor();
			logger.debug("Execute Spool File Command executed Succesfully: " );
			inp = p.getInputStream();
			inpE = p.getErrorStream();
			int count = inp.available();
			logger.debug("Input Stream Count: "  + count);
			int countE = inpE.available();
			logger.debug("Input Stream CountE: "  + countE);
			String stderr = null;
			String stdout = null;

			if (countE > 0) {
				byte[] bufferE = new byte[countE];
				inpE.read(bufferE);

				stderr = new String(bufferE);
				logger.debug("Standard Error:");
				logger.debug(stderr);
			}

			if (count > 0) {
				byte[] buffer = new byte[count];
				inp.read(buffer);

				stdout = new String(buffer);
				logger.debug("Standard Out:");
				logger.debug(stdout);
			}
			if (p.exitValue() == 0) success = true;
			logger.debug("Success Status: "  + success);
		}
		catch (Exception ex) {
			throw new TCGMException(className, methodName, parameterList, ex.toString() );
		}

		return success;
	}


	private static void readTemplateFileString() throws TCGMException {
		// Load constant strings
		try {
			AppConst ac = AppConst.getInstance();
			String sqldir = ac.getWebInfDir() + File.separatorChar + "sql";

			templateFileString = TCGMUtil.readTextFile(new File(sqldir, "extract_template.sql"), 4096);
		}
		catch (Exception ex) {
			logger.error("Error Finding SQL Definition File: " + ex.toString() );
		}
	}

}