package abbott.ai.tcgm.data;

import abbott.ai.tcgm.entities.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public final class DBConst
{
	/** Begin Constants For Table Names. */
	public static final String TABLE_AFFBPC_FILE = "AFFBPC_FILE";
	public static final String TABLE_AFFCSTCUR = "T_AFFCSTCUR";
	public static final String TABLE_ASR = "ASR";
	public static final String TABLE_ASR_T = "ASR_T";
	public static final String TABLE_ASR_TREE = "ASR_TREE";

	public static final String TABLE_BPCOST = "BPCOST";
	public static final String TABLE_BPCOST_T = "BPCOST_T";
	public static final String TABLE_BPCOST_EXCEPTIONS = "EXCEPTIONS";
	public static final String TABLE_BPCOST_EXCEPTIONS_T = "EXCEPTIONS_T";

	//public static final String TABLE_COSTEXCH_DATA = "CSTXCH_9999";
	public static final String TABLE_COSTEXCH_DATA = "CSTXCH_DATA";
	public static final String TABLE_CURRENCY = "T_CURRENCY";

	public static final String TABLE_DATA_FEED = "DATA_FEED";
	public static final String TABLE_DATA_FEED_LOG = "DATA_FEED_LOG";
	public static final String TABLE_DATASET = "DATASET_TABLE";

	public static final String TABLE_KNOLLCONV = "T_KNOLL_CNVT";

	public static final String TABLE_MODEL = "MODEL";
	public static final String TABLE_MODEL_DATASET_TABLE = "MODEL_DATASET_TABLE";

	public static final String TABLE_NOTES = "NOTES";
	public static final String TABLE_NOTES_T = "NOTES_T";

	public static final String TABLE_PRMFG = "T_PR_MFG";
	public static final String TABLE_REPORT = "REPORT";

	public static final String TABLE_RATE_DATA = "RATE_DATA";
	public static final String TABLE_RATE_DATA_T = "RATE_DATA_T";
	public static final String TABLE_RATE_EXCEPTIONS = "EXRATE";
	public static final String TABLE_RATE_EXCEPTIONS_T = "EXRATE_T";
	public static final String TABLE_REVISIONS = "REVISIONS";

	public static final String TABLE_SALES_DATA = "SALES_DATA";

	public static final String TABLE_UNIT_DATA = "UNITS_DATA";
	public static final String TABLE_UNIT_DATA_SAVE = "UNITS_DATA_SAVE";
	public static final String TABLE_USER_INFO = "USER_INFO";
	public static final String TABLE_REPORT_SECURITY = "REPORT_SECURITY";
	public static final String TABLE_JOB = "JOB";
	public static final String TABLE_T_AFFILIATE = "T_AFFILIATE";
	public static final String VW_T_AFFILIATE = "VW_T_AFFILIATES";
	public static final String TABLE_PUBLISHED_MODELS = "PUBLISHED_MODELS";
	public static final String TABLE_GENERATED_MODELS = "GENERATED_MODELS";
	
	/** End Constants For Table Names.	 */

	/** Begin Constants For View Names */
	public static final String VW_BPCOST_EXCEPTIONS_T = "VW_EXCEPTIONS_T";
	public static final String VW_BPCOST_T = "VW_BPCOST_T";
	public static final String VW_BPCOST_T_NOREV = "VW_BPCOST_T_NOREV";
	public static final String VW_BPCOST_T_REV = "VW_BPCOST_T_REV";
	public static final String VW_BPCS_TRAN_NOREV = "VW_BPCS_TRAN_NOREV";
	public static final String VW_BPCS_TRAN_REV = "VW_BPCS_TRAN_REV";

	public static final String VW_RATE_DATA_T = "VW_RATE_DATA_T";
	public static final String VW_RATE_EXCEPTIONS_T = "VW_EXRATE_T";

	/** End Constants for View Names */

	/** Begin Constants For Column Names.	 */
	public static final String COL_DF_DB_LOC = "DATAFEED_DB_LOC";
	public static final String COL_DF_AS_LOC = "DATAFEED_AS_LOC";
	public static final String COL_DF_EXT = "DATAFEED_EXT";
	public static final String COL_DF_PROC = "DATAFEED_PROC";
	public static final String COL_DF_JOB_TYPE = "DATA_FEED_JOB_TYPE";

	public static final String COL_DFL_FILEPATH = "DFL_FILE_PATH";
	public static final String COL_DFL_START_TIME = "DFL_START_TIME";
	public static final String COL_DFL_END_TIME = "DFL_FINISH_TIME";
	public static final String COL_DFL_COMP_MSG = "DFL_COMP_MSG";

	public static final String COL_MODEL_ID = "MODEL_ID";
	public static final String COL_MODEL_STATUS = "MODEL_STATUS";
	public static final String COL_MODEL_TYPE = "MODEL_TYPE";
	public static final String COL_MODEL_NAME = "MODEL";
	public static final String COL_MODEL_DESC = "MODEL_DESC";
	public static final String COL_DATASET_TABLE_ID = "DATASET_TABLE_ID";
	public static final String COL_DATASET_NAME = "DATASET_NAME";
	public static final String COL_TABLE_NAME = "TABLE_NAME";
	public static final String COL_PROD_ORIGIN = "PROD_ORIGIN";
	public static final String COL_MODEL_NAME2 = "MODEL_NAME";
	
	public static final String COL_ROW_COUNT = "ROW_COUNT";
	public static final String COL_REPORT_CONTENT_ID = "REPORT_CONTENT_ID";
	public static final String COL_GENERATE_EMPTY_REPORT = "GENERATE_EMPTY_REPORT";
	public static final String COL_AFF = "AFF";
	public static final String COL_AFF_DESC = "AFF_DESC";

	public static final String COL_AFFILIATE = "AFFILIATE";
	public static final String COL_RPT_AFF = "RPT_AFF";
	public static final String COL_RPT_INV_CD = "RPT_INV_CD";
	public static final String COL_RPT_LABEL = "RPT_LABEL";
	public static final String COL_RPT_SIZE = "RPT_SIZE";
	public static final String COL_RPT_LIST = "RPT_LIST";
	public static final String COL_RPT_PACK = "RPT_PACK";
	public static final String COL_INQUIRY_REPORT_ID = "INQUIRY_REPORT_ID";
		
	/***************Default Sort Order Columns Start ***************************************/
	
	public static final String COL_ASR_DEF     =  "RPT_AFF || RPT_INV_CD || RPT_LIST || RPT_PACK || RPT_LABEL || RPT_SIZE || " +		                                         "SUP_AFF || SUP_INV_CD || SUP_LIST || SUP_PACK || SUP_LABEL || SUP_SIZE " ;
	
	public static final String COL_BPC_DEF     = "RPT_AFF || SUP_AFF || SUP_INV_CD || SUP_LIST || SUP_PACK || SUP_LABEL || SUP_SIZE " ;
	
	public static final String COL_BPC_EXC_DEF = "END_AFF || END_INV_CD || END_LIST || END_PACK || END_LABEL || END_SIZE || " +
												 "RPT_AFF || RPT_INV_CD || RPT_LIST || RPT_PACK || RPT_LABEL || RPT_SIZE || " +
		                                         "SUP_AFF || SUP_INV_CD || SUP_LIST || SUP_PACK || SUP_LABEL || SUP_SIZE " ;

	public static final String COL_NOTES_DEF     = "RPT_AFF || RPT_INV_CD || RPT_LIST || RPT_PACK || RPT_LABEL || RPT_SIZE " ;		                                     

	public static final String COL_AFF_BPC_DEF   = "SUP_INV_CD || SUP_LIST || SUP_PACK || SUP_LABEL || SUP_SIZE || RPT_AFF || SUP_AFF"; 

	/***************Default Sort Order Columns End ***************************************/	                                     
	public static final String COL_NOTE = "NOTE";

	public static final String COL_CNV_AFF = "CNV_AFF";

	public static final String COL_SUP_AFF = "SUP_AFF";
	public static final String COL_SUP_INV_CD = "SUP_INV_CD";
	public static final String COL_SUP_LABEL = "SUP_LABEL";
	public static final String COL_SUP_SIZE = "SUP_SIZE";
	public static final String COL_SUP_LIST = "SUP_LIST";
	public static final String COL_SUP_PACK = "SUP_PACK";

	public static final String COL_END_AFF = "END_AFF";
	public static final String COL_END_INV_CD = "END_INV_CD";
	public static final String COL_END_LABEL = "END_LABEL";
	public static final String COL_END_SIZE = "END_SIZE";
	public static final String COL_END_LIST = "END_LIST";
	public static final String COL_END_PACK = "END_PACK";

	public static final String COL_SUP_KEY = "SUP_KEY";
	public static final String COL_USAGE_FAC = "USAGE_FAC";
	public static final String COL_USAGE_FACTOR = "USAGE_FACTOR";
	public static final String COL_REV_TYPE = "REV_TYPE";
	public static final String COL_BEG_PERIOD = "BEG_PERIOD";
	public static final String COL_END_PERIOD = "END_PERIOD";
	public static final String COL_BP_CUR_CD = "BP_CUR_CD";
	public static final String COL_BILL_PRICE = "BILL_PRICE";
	public static final String COL_COST_PRICE = "COST_PRICE";
	public static final String COL_COST_CUR_CD = "COST_CUR_CD";
	public static final String COL_FREEZE_COST = "FREEZE_COST";
	public static final String COL_CUR_CD = "CUR_CD";

	public static final String COL_CREATE_USERNAME = "CREATE_USERNAME";
	public static final String COL_CREATE_DATETIME = "CREATE_DATETIME";
	public static final String COL_MODIFY_USERNAME = "MODIFY_USERNAME";
	public static final String COL_MODIFY_DATETIME = "MODIFY_DATETIME";
	public static final String COL_DATESTAMP = "DATESTAMP";
	public static final String COL_USERNAME = "USERNAME";
	public static final String COL_USERID = "USER_ID";
	public static final String COL_ACD = "ACD";
	public static final String COL_COUNT = "COUNT";
	public static final String COL_FIRST_NAME = "FIRST_NAME";
	public static final String COL_LAST_NAME = "LAST_NAME";
	public static final String COL_GRANTED_ROLE = "GRANTED_ROLE";
	public static final String COL_EMAIL = "EMAIL";
	public static final String COL_PHONE = "PHONE";
	public static final String COL_LOTUSID = "EMP_NUM";
	public static final String COL_PUBLISH_FLAG = "PUBLISH_FLAG";
	public static final String COL_GENERATE_FLAG = "GENERATE_FLAG";
	public static final String COL_JOB_ID = "JOB_ID";
	public static final String COL_JOB_NAME = "JOB_NAME";
	public static final String COL_JOB_CMD = "JOB_CMD";
	public static final String COL_JOB_TYPE = "JOB_TYPE";
	public static final String COL_JOB_DESC = "JOB_DESC";
	public static final String COL_JOB_QUE_ID = "JOB_QUE_ID";
	public static final String COL_JOB_QUE_DESC = "JOB_QUE_DESC";
	public static final String COL_JOB_QUE_END_TIME = "END_TIME";
	public static final String COL_JOB_QUE_START_TIME = "START_TIME";
	public static final String COL_JOB_QUE_CREATE_DATETIME = "CREATE_DATETIME";
	public static final String COL_JOB_QUE_CREATE_USERNAME = "CREATE_USERNAME";
	public static final String COL_JOB_QUE_SEQ = "JOB_SEQUENCE";
	public static final String COL_JOB_QUE_PRINT = "JOB_QUE_PRINT";
	public static final String COL_JOB_QUE_GENERATE_REPORT = "JOB_QUE_GENERATE_REPORT";
	public static final String COL_JOB_STATUS = "JOB_STATUS";
	public static final String COL_UNIT_ID = "UNIT_ID";
	public static final String COL_UNIT_NAME = "UNIT_NAME";
	public static final String COL_PARM_NAME = "PARM_NAME";
	public static final String COL_PARM_VALUE = "PARM_VALUE";

	public static final String COL_REPORT_ID = "REPORT_ID";
	public static final String COL_REPORT_NAME = "REPORT_NAME";
	public static final String COL_REPORT_DESC = "REPORT_DESC";
	public static final String COL_REPORT_PRE_CMD = "REPORT_PRE_CMD";
	public static final String COL_REPORT_POST_CMD = "REPORT_POST_CMD";
//	public static final String COL_REPORT_TABLE = "REPORT_TABLE";
//	public static final String COL_REPORT_GROUP_BY = "REPORT_GROUP_BY";
//	public static final String COL_REPORT_ORDER_BY = "REPORT_ORDER_BY";
	public static final String COL_REPORT_CRYSTAL_FILE = "REPORT_CRYSTAL_FILE";
	public static final String COL_REPORT_TITLE = "REPORT_TITLE";
	public static final String COL_REPORT_SELECT = "REPORT_SELECT";
	public static final String COL_REPORT_TYPE = "REPORT_TYPE";
	public static final String COL_REPORT_AS400_WIDTH = "REPORT_AS400_W";
	public static final String COL_REPORT_DISPLAY_NAME = "REPORT_DISPLAY_NAME";
	public static final String COL_REPORT_USE_CASE = "REPORT_USE_CASE";
	public static final String COL_REPORT_SEARCH_PATH = "REPORT_SEARCH_PATH";
	public static final String COL_OUTPUT_FORMAT = "OUTPUT_FORMAT";
	public static final String COL_REPORT_VERSIONS = "REPORT_VERSIONS";
	
	
	public static final String COL_REPORT_INSTANCE_ID = "REPORT_INSTANCE_ID";

	public static final String COL_UNIT_DESC = "UNIT_DESC";
	public static final String COL_AFFBPC_ID = "AFFBPC_ID";
	public static final String COL_ASR_T_ID = "ASR_T_ID";
	public static final String COL_NOTES_T_ID = "NOTES_T_ID";
	public static final String COL_BPCOST_T_ID = "BPCOST_T_ID";
	public static final String COL_EXCEPTIONS_T_ID = "EXCEPTIONS_T_ID";
	public static final String COL_RATE_DATA_T_ID = "RATE_DATA_T_ID";
	public static final String COL_EXRATE_T_ID = "EXRATE_T_ID";

	public static final String COL_CUR_NAME = "CUR_NAME";
	public static final String COL_RATE = "RATE";

	public static final String COL_DATASET_DESCRIPTION = "DATASET_DESCRIPTION";

	public static final String COL_BPF_RATE = "BPF_RATE";
	public static final String COL_BPP_RATE = "BPP_RATE";
	public static final String COL_COSTF_RATE = "COSTF_RATE";
	public static final String COL_COSTP_RATE = "COSTP_RATE";

	public static final String COL_USER_INFO_ID = "USER_INFO_ID";
	public static final String COL_DEF = "DEF";
	
	public static final String COL_AREA   = "AREA";
	public static final String COL_REGION = "REGION";
	public static final String COL_SECTOR = "SECTOR";
		
	/** End Constants For Column Names.	 */

	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";
	public static final String SORT_IMG_ASC = "images/upArrow.png";
	public static final String SORT_IMG_DESC = "images/downArrow.png";
	public static final String SORT_IMG_TXT_ASC = "Sorted Ascending";
	public static final String SORT_IMG_TXT_DESC = "Sorted Descending";

	/**
	 * Default sorting variables for tables
	 */
	public static final Sort DEF_SORT_ASR = new Sort(COL_ASR_DEF,SORT_ASC);
	public static final Sort DEF_SORT_ASR_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_BPCS = new Sort(COL_RPT_AFF,SORT_ASC);
	public static final Sort DEF_SORT_BPCS_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_BPC_REV = new Sort(COL_RPT_AFF,SORT_ASC);
	public static final Sort DEF_SORT_BPC_REV_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_BPC_EX = new Sort(COL_END_AFF,SORT_ASC);
	public static final Sort DEF_SORT_BPC_EX_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_BPCS_STAGING = new Sort(COL_RPT_AFF,SORT_ASC);
	public static final Sort DEF_SORT_RATE_DATA = new Sort(COL_CUR_CD,SORT_ASC);
	public static final Sort DEF_SORT_RATE_DATA_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_AFF_BPC = new Sort(COL_AFF_BPC_DEF,SORT_ASC);
	public static final Sort DEF_SORT_RATEEX = new Sort(COL_RPT_AFF,SORT_ASC);
	public static final Sort DEF_SORT_RATEEX_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_USER = new Sort(COL_USERNAME,SORT_ASC);
	public static final Sort DEF_SORT_USERID = new Sort(COL_USERID,SORT_ASC);
	public static final Sort DEF_SORT_CURRENCY = new Sort(COL_CUR_CD,SORT_ASC);
	public static final Sort DEF_SORT_NOTES = new Sort(COL_RPT_AFF,SORT_ASC);
	public static final Sort DEF_SORT_NOTES_TRAN = new Sort(COL_CREATE_DATETIME,SORT_ASC);
	public static final Sort DEF_SORT_AFFCSTCUR = new Sort(COL_AFF,SORT_ASC);
	public static final Sort DEF_SORT_KNOLLCONV = new Sort(COL_SUP_AFF,SORT_ASC);
	public static final Sort DEF_SORT_PRMFG = new Sort(COL_SUP_AFF,SORT_ASC);
		
	public static final String DEF_DATASET_TABLE_ID = "-1";
	public static final String DEF_MODEL_ID = "-1";
	/**
	 * Variables for query types
	 */
	public static final int DB_SELECT = 1;
	public static final int DB_UPDATE = 2;
	public static final int DB_DELETE = 3;
	public static final int DB_INSERT = 4;

	/**
	 * Misc
	 */
	public static final String RATE_MODEL_NAME = "RATE";

	/**
	 *  Default focus field for setting focusing in the JSP page
	 */
	public static final String ASR_DFT_FOCUS = "searchObject.productOrigin";
	public static final String BPCS_DFT_FOCUS = "searchObject.rptAff";
	public static final String BPC_REV_DFT_FOCUS = "searchObject.revType";
	public static final String BPC_EX_DFT_FOCUS = "searchObject.endAff";
	public static final String RATE_EX_DFT_FOCUS = "searchObject.endAff";
	public static final String RATE_DATA_DFT_FOCUS = "searchObject.curCode";
	public static final String NOTES_DFT_FOCUS = "searchObject.rptAff";

	// 4-8-03 Default page positioning for all transaction pages
	public static final String DFT_TRAN_FOCUS = "searchObject.actionCode";

	/**
	 * Default Constructor
	 */
	public DBConst()
	{
	}
}