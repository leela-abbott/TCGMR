package abbott.ai.tcgm.process;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobConstants {

	// PN are parameter name constants.
	public static final String PN_REPORT_DEST = "REPORT_DEST";
	public static final String PN_REPORT_COPIES = "REPORT_COPIES";
	public static final String PN_RESTRICTIONS = "REPORT_RESTRICTIONS";
	public static final String PN_DIS_VERSION = "DIS_VERSION";
	//public static final String PN_R_SYSTEM = "R_SYSTEM"; 6-25-03 bd; not used in factor calc
	
	public static final String PN_T_PRODUCT_FILENAME = "T_PRODUCT_FILENAME";
	public static final String PN_AFF_FILENAME = "AFF_FILENAME";
	
	// 12-21-05 Replace EXP_R_SYSTEM parm with 4 system specific parms
	//public static final String PN_R_SYSTEM = "EXP_R_SYSTEM";
	public static final String PN_R_SYSTEM_RBB = "EXP_R_SYSTEM_RBB";
	public static final String PN_R_SYSTEM_RBL = "EXP_R_SYSTEM_RBL";
	public static final String PN_R_SYSTEM_RTC = "EXP_R_SYSTEM_RTC";
	public static final String PN_R_SYSTEM_RGM = "EXP_R_SYSTEM_RGM";
	public static final String PN_R_CCS_SYSTEM = "EXP_CCS_SYSTEM";
	public static final String PN_R_CCS_TYPE = "EXP_CCS_TYPE";
	
	public static final String PN_REPORT_ID = "REPORT_ID";
	public static final String PN_JOBQUE_ID = "JOBQUE_ID";
	public static final String PN_MODEL_CYCLE = "CYCLE";
	public static final String PN_MODEL_YEAR = "YEAR";
	
	public static final String PN_RATE_SET = "RATE_SET";

	// Job constants for Perpetual Calculations
	public static final String PN_UNIT_BEG_INV_CYCLE    = "UNIT_B_INV_CYCLE";
	public static final String PN_ASR_BEG_INV_CYCLE     = "ASR_B_INV_CYCLE";
	public static final String PN_UNIT_L_YEAR_CYCLE     = "UNIT_L_YEAR_CYCLE";
	public static final String PN_ASR_L_YEAR_CYCLE      = "ASR_L_YEAR_CYCLE";
	public static final String PN_UNIT_T_YEAR_CYCLE     = "UNIT_T_YEAR_CYCLE";
	public static final String PN_ASR_T_YEAR_CYCLE      = "ASR_T_YEAR_CYCLE";
	public static final String PN_UNIT_END_INV_CYCLE    = "UNIT_E_INV_CYCLE";
	public static final String PN_ASR_END_INV_CYCLE     = "ASR_E_INV_CYCLE";
	public static final String PN_COST_CYCLE_MID        = "COST_CYCLE";
	public static final String PN_BEG_D56_PERIOD        = "B_D56_PERIOD";
	public static final String PN_BEG_D56_YEAR          = "B_D56_YEAR";
	public static final String PN_END_D56_PERIOD        = "E_D56_PERIOD";
	public static final String PN_END_D56_YEAR          = "E_D56_YEAR";
	public static final String PN_USE_END_INVENTORY     = "E_INV_USED";
	public static final String PN_END_INV_CYCLE         = "E_INV_CYCLE";
	public static final String PN_BEG_INV_CYCLE         = "B_INV_CYCLE";
	public static final String PN_COST_CYCLE            = "B_COST_CYCLE";
	public static final String PN_FROM_PERIOD           = "FROM_PERIOD";
	public static final String PN_COST_HDR_1            = "COST_HDR_1";
	public static final String PN_COST_HDR_2            = "COST_HDR_2";
	public static final String PN_COST_HDR_3            = "COST_HDR_3";
	public static final String PN_COST_HDR_4            = "COST_HDR_4";
	public static final String PN_END_INV_HEADER        = "E_INV_HEADER";
	public static final String PN_BEG_INV_HEADER        = "B_INV_HEADER";
	public static final String PN_UNIT_HDR_1            = "UNIT_HDR_1";


	// Job constants for Data Transfers
	public static final String PN_MODEL_TYPE    = "MODEL_TYPE";
	//public static final String PN_SAVED_FACTOR_DATE  = "SVD_FCTR_DT"; // this const is  in FactorModel
	//public static final String PN_SAVED_UNITS  = "SAVED_UNITS";
	public static final String PN_SAVED_UNITS  = "SVD_UNITS";
	public static final String PN_SAVED_UNITS_NAME  = "SVD_UNITS_NAME";
	//public static final String PN_CURR_UNITS  = "CURRENT_UNITS";
	public static final String PN_CURR_UNITS  = "CURRNT_UNITS";
	//public static final String PN_CURR_UNITS_NAME  = "CURRNT_UNITS_NAME"; // this const is  in FactorModel
	public static final String PN_SAVED_UNITS_DATE = "SVD_UNITS_DT";
	public static final String PN_RATE_DATA_T_ID = "RATE_DATA_T";
	public static final String PN_MOD_VERSION = "MOD_VERSION";
	public static final String PN_OLD_VERSION = "OLD_VERSION";
	public static final String PN_MOD_VERSION_NAME = "MOD_VERSION_NAME";
	public static final String PN_OLD_VERSION_NAME = "OLD_VERSION_NAME";
	public static final String PN_STD_CST_RATE_EXCHANGE = "STD_CST_RATE_EXCHANGE";
	public static final String PN_FACTOR_RATE_EXCHANGE = "FACTOR_RATE_EXCHANGE";
	public static final String PN_REVISION_RATE_EXCHANGE = "REVISION_RATE_EXCHANGE";
	
	public static final String PN_PUB_FCT_RPT = "PUB_FCT_RPT";
	public static final String PN_UNPUB_FCT_RPT = "UNPUB_FCT_RPT";
	public static final String PN_PUB_NET_COST = "PUB_NET_COST";
	public static final String PN_UNPUB_NET_COST = "UNPUB_NET_COST";
	
	public static final String PN_GEN_FCT_RPT = "GEN_FCT_RPT";
	public static final String PN_UNGEN_FCT_RPT = "UNGEN_FCT_RPT";
	public static final String PN_GEN_NET_COST = "GEN_NET_COST";
	public static final String PN_UNGEN_NET_COST = "UNGEN_NET_COST";
	

	// Job constants for Analysis jobs - Essbase
	public static final String PN_ESSBASE_VERSION    = "ESS_VERSION";
	public static final String PN_ESSBASE_YEAR       = "ESS_YEAR";
	public static final String PN_ESSBASE_TYPE       = "ESS_TYPE";
	public static final String PN_ESSBASE_START_PERIOD="ESS_START_PERIOD";
	public static final String PN_ESSBASE_END_PERIOD = "ESS_END_PERIOD";

	public static final String PN_ESSBASE_ANALYSIS_1 = "A1";
	public static final String PN_ESSBASE_ANALYSIS_2 = "A2";
	public static final String PN_ESSBASE_ANALYSIS_3 = "A3";
	public static final String PN_ESSBASE_ANALYSIS_4 = "A4";
	public static final String PN_ESSBASE_FLEX_1     = "F1";
	public static final String PN_ESSBASE_FLEX_2     = "F2";
	
	//ESSBASE Jobs related variables Directory Path and File Name
	public static String ESSBASE_EXP_DIR     = "";
	public static String ESSBASE_FILE_NAME     = "";
	

	public static final String PN_BASE_TITLE = "BASE_TITLE";
	public static final String PN_NEW_TITLE = "NEW_TITLE";

	// Job constants for Misc jobs
	public static final String PN_ROU_PERIOD_START         = "B_ROU_PERIOD";
	public static final String PN_ROU_PERIOD_END           = "E_ROU_PERIOD";
	public static final String PN_ROU_YEAR_START           = "B_ROU_YEAR";
	public static final String PN_ROU_YEAR_END             = "E_ROU_YEAR";
	public static final String PN_ROU_YEAR_ACT_UNITS_BEG   = "UNITS_ACT_B_ROU_YEAR";
	public static final String PN_ROU_YEAR_ACT_UNITS_END   = "UNITS_ACT_E_ROU_YEAR";
	public static final String PN_THRU_PERIOD              = "THRU_PERIOD";
	public static final String PN_TREE_FILENAME            = "TREE_FILENAME";
	
	// 1-24-06 Next parm is actually named INP_PERIOD and it is defined in the next section
	//public static final String PN_INTER_CO_TRANSFER_PERIOD = "INPTER_CO_TRANFER_PERIOD";
	
	// Job constants for Misc Analysis - Deferred Margin & Exchange Exposure Settings
	public static final String PN_RPT_NAME_SUP      = "DFRD_RPT_NAME_SUF";
	public static final String PN_INP_PERIOD      = "DFRD_PERIOD";
	public static final String PN_XCHG_UNITS      = "DFRD_UNITS"; // Dataset Id of Units
	public static final String PN_XCHG_UNITS_NAME = "DFRD_UNITS_NAME"; // Unit String Name
	public static final String PN_UNIT_XCHG       = "UNIT_XCHG";  // Dataset Id
	public static final String PN_SALES_ID        = "HDGE_SALES_ID";
	public static final String PN_PERIOD          = "PERIOD";
	
	//Job constants for Misc Reports
	public static final String PN_RPT_ACT_UNITS = "RPT_ACT_UNITS";
		
	// Job constants for Misc Analysis - Standard Cost Settings
	public static final String PN_NET_RT_PER   = "NET_RT_PER";
	public static final String PN_NET_UNITS_ID = "NET_UNITS_ID";
	public static final String PN_STDCST_SLSID = "STDCST_SLSID";
	public static final String PN_EXEXP_SLSID = "SALES_ID";
	public static final String PN_SLSID_TITLE  = "SLSID_TITLE";
	public static final String PN_NET_UNITS    = "NET_UNITS";
	public static final String PN_NET_BASE     = "NET_BASE";
	public static final String PN_NET_BASE_NAME     = "NET_BASE_NAME";
	public static final String PN_RATE_2       = "RATE_2";
		
	// Job constants for BP/Cost Exchange
	public static final String PN_B_CXC_VERSN   = "B_CXC_VERSN";
	public static final String PN_CXC_CSTSL1    = "CXC_CSTSL1";
	public static final String PN_CXC_CSTSL2    = "CXC_CSTSL2";
	public static final String PN_CXC_SUMFIELD  = "CXC_SUMFIELD";
	public static final String PN_CXC_SUMFLD2   = "CXC_SUMFLD2";
	public static final String PN_CXCHG_VERSN   = "CXCHG_VERSN";
	public static final String PN_TYP_UPDT      = "TYP_UPDT";
	
	//	Job constants for DataFeed Monitor Activity.
	public static final String PN_GENERATE_REPORT  = "G";
	public static final String PN_AFFBPC_LOAD   = "AFFBPC_LOAD";
	public static final String PN_AFFBPC_REPORT = "AFFBPC_REPORT";
	public static final String PN_LOAD_AFF      = "LOAD_AFF";			
	public static String PN_SQL_SAVED_UNITS_DATE = " SELECT DECODE (TO_CHAR (MAX (modify_datetime), " +
			"'mm/dd/yyyy hh24:mi:ss'), NULL, ' ', TO_CHAR (MAX (modify_datetime), 'mm/dd/yyyy hh24:mi:ss')) " +
			"FROM tcgm.units_data WHERE sls_typ IN ('10', '30') ";

	public static String PN_SQL_RATE1 = "SELECT PARM_VALUE FROM TCGM.PARAMETER WHERE PARM_NAME = 'RATE_1' AND MODEL_ID =";


	//Job constants for System Compact
	public static final String PN_FREQUENCY_SYSTEM_COMPACT = "FREQUENCY_SYSTEM_COMPACT";
	public static final String PN_LAST_SUCCESSFUL_PROCESSED_DATE_SYSTEM_COMPACT = "LAST_SUCCESSFUL_PROCESSED_DATE_SYSTEM_COMPACT";
	public static final String PN_SYSTEM_COMPACT_STATUS = "SYSTEM_COMPACT_STATUS";
	public static final String PN_SYSTEM_COMPACT_JOB = "SYSTEM_COMPACT";

	//Job constants for Model Purge
	public static final String PN_FREQUENCY_MODEL_PURGE = "FREQUENCY_MODEL_PURGE";
	public static final String PN_LAST_SUCCESSFUL_PROCESSED_DATE_MODEL_PURGE = "LAST_SUCCESSFUL_PROCESSED_DATE_MODEL_PURGE";
	public static final String PN_MODEL_PURGE_STATUS = "MODEL_PURGE_STATUS";
	public static final String PN_MODEL_PURGE_EXPIRATION_DAYS = "MODEL_PURGE_EXPIRATION_DAYS";
	public static final String PN_MODEL_PURGE_JOB = "MODEL_PURGE";
		
	public static final String PN_STATUS_FAILED = "FAILED";
	
	
	//Added to identify the actual user who submitted the job	
	public static final String PN_ACTUAL_USER  = "ACTUAL_USER";

	public JobConstants() {  }
}
