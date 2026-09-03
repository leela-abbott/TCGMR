package abbott.ai.tcgm;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 * <p>Used to hold constants for TCGM application</p>
 */
public class TCGMConstants
{
	/**
	 * This is the constant for the parameter in the deployment descripter that will contain
	 * the url of the datasource.
	 */
	public static final String DATASOURCE_URL = "datasourceURL";
	public static final String DATASOURCE_SCHEMA_NAME = "TCGM";


	/**
	 * This is the constant for the parameter in the deployment descripter that will contain
	 * the user id for Oracle
	 */
	public static final String ORACLE_ID = "oracleId";
	/**
	 * This is the constant for the parameter in the deployment descripter that will contain
	 * the password for Oracle
	 */
	public static final String ORACLE_PSWD = "oraclePswd";

	/**
	 * These are constants for the parameter name by wich application constants are
	 * looked up in the web.xml file
	 */
	public static final String AC_CRYSTAL_SERVER = "crystalServer";
	public static final String AC_CRYSTAL_ODBC = "crystalOdbc";
	public static final String AC_DB_TNS_NAME = "dbTnsName";
	public static final String AC_REPORT_FTP_ID = "reportFtpId";
	public static final String AC_REPORT_FTP_PSWD = "reportFtpPswd";
	public static final String AC_REPORT_FTP_LIBRARY = "reportLibrary";
	public static final String AC_REPORT_FTP_HOST = "reportHost";
	public static final String AC_REPORT_TRIGGERFILE = "reportTriggerFile";
	public static final String AC_REPORT_PARMFILE = "reportParmFile";
	public static final String AC_ESSBASE_ID = "essbaseId";
	public static final String AC_ESSBASE_PSWD = "essbasePswd";
	public static final String AC_ESSBASE_LIBRARY = "essbaseLibrary";
	public static final String AC_ESSBASE_HOST = "essbaseHost";
	public static final String AC_ESSBASE_FILENAME = "essbaseFilename";
	public static final String AC_JOB_ID = "jobId";
	public static final String AC_JOB_PSWD = "jobPswd";
	public static final String AC_JCL_PATH = "jclPath";
	public static final String AC_REPORTNET_USERNAME = "reportNetUsername";
	public static final String AC_REPORTNET_PASSWORD = "reportNetPassword";
	public static final String AC_REPORTS_URL = "reportsUrl";
	
	public static final String AC_ESSBASE_EXP_DIR = "essbaseExportDir";
	public static final String AC_FILE_ESS_FLEX = "file_ESS_FLEX";
	public static final String AC_FILE_ESS_ANLA1A4 = "file_ESS_ANLA1A4";
	public static final String AC_FILE_ESS_ANLA2A3 = "file_ESS_ANLA2A3";


	// 10-21-05 LDAP Authenticatin Variables
	public static final String AC_AUTH_HOST = "authenticationHost";
	public static final String AC_AUTH_BASEDN = "authenticationBasedn";
	public static final String AC_AUTH_PORT = "authenticationPort";
	public static final String AC_AUTH_USERID = "authenticationUserId";
	public static final String AC_AUTH_PW = "authenticationPassword";
	
	// 11-17-05 LDAP Authenticatin Variables
	public static final String AC_REPORT_NET_SERVICE_LOCATOR = "reportNetServiceLocator";
	public static final String AC_REPORT_NET_NAME_SPACE = "reportNetNameSpace";
	public static final String AC_REPORT_NET_USERNAME = "reportNetUsername";
	public static final String AC_REPORT_NET_PASSWORD = "reportNetPassword";
	public static final String AC_REPORT_NET_PRINTER1 = "reportNetPrinter1";
	public static final String AC_REPORT_NET_PACKAGENAME = "reportNetPackageName";
	
	public static final String AC_REPORT_NET_DIVPATH = "reportNetDIVPath";
	public static final String AC_REPORT_NET_HQPATH = "reportNetHQPath";
	public static final String AC_REPORT_NET_AFF_REPORTS_PATH = "reportNetAffiliateReportsPath";
	public static final String AC_REPORT_NET_AFF_PATH = "reportNetAffiliatePath";
	public static final String AC_REPORT_NET_SECTOR_PATH = "reportNetSectorPath";
	public static final String AC_REPORT_NET_REPORTS_PATH = "reportNetReportsPath";
	public static final String AC_REPORT_NET_FOLDER_REPORTS = "reportNetFolderReports";
	public static final String AC_REPORT_NET_PARMS_DATASET_TABLE_ID = "reportNetParmsDatasetTableID";
	public static final String AC_REPORT_NET_PARMS_MODEL_ID = "reportNetParmsModelID";
	public static final String AC_REPORT_NET_PARMS_JOB_QUE_ID = "reportNetParmsJobQueueID";
	public static final String AC_REPORT_NET_PARMS_AFFILIATE = "reportNetParmsAffiliate";
	public static final String AC_REPORT_NET_EXECUTION_SEQUENCE = "reportNetExecutionSequence";
	
	public static final String REPORT_CONSTANT_HQ = "HQ";
	public static final String REPORT_CONSTANT_HQ_B = "HQ-B";
	public static final String REPORT_CONSTANT_AFF = "AFF";
	public static final String REPORT_CONSTANT_HQ_I = "HQ-I";
	public static final String REPORT_CONSTANT_AFF_I = "AFF-I";
	public static final String REPORT_CONSTANT_AFF_S = "AFF-S";
	public static final String REPORT_CONSTANT_AFF_A = "AFF-A";
	public static final String REPORT_CONSTANT_RGM_A = "RGM-A";
	public static final String REPORT_CONSTANT_DIV_B = "DIV-B";
	
	public static final String AC_R_SYSTEM_ADDRESS = "RSystem Address";
	public static final String AC_R_SYSTEM_USERNAME = "RSystem Username";
	public static final String AC_R_SYSTEM_PASSWORD = "RSystem Password";
	
	public static final String NONE_SELECTED = "<none>";
	public static final String NONE_SEL_HTML = "&lt;none&gt;";
	public static final String NONE = "none";

	public static final String EXCEPTION_PAGE = "exception.jsp";

	public static final String FORWARD_FAILURE = "failure";
	public static final String FORWARD_ERROR = "error";
	public static final String FORWARD_SUCCESS = "success";
	public static final String APP_FORWARD_SUCCESS = "appsuccess";
	public static final String FORWARD_INVALID = "invalid";
	public static final String FORWARD_INPUT = "input";
	public static final String FORWARD_RESTRICTIONS = "restrictions";
	public static final String FORWARD_ADVANCEDFILTER = "advancedfilter";
	public static final String FORWARD_BATCHMAINT = "batchmaintenance";
	
	public static final String G_FORWARD_LOGIN = "login";
	public static final String G_FORWARD_EXCEPTION = "exception";
	public static final String G_FORWARD_SELECT_MODEL = "selectModel";
	public static final String G_FORWARD_SELECT_RATE_SET = "selectRateSet";
	public static final String G_FORWARD_RESTRICTIONS = "restrictions";
	public static final String G_FORWARD_ADMIN = "admin";
	
	public static final String SESSION_NAME_STATE = "TCGMState";
	public static final String SESSION_NAME_USER = "TCGMUser";
	public static final String SESSION_NAME_EXCEPTION = "TCGMException";

	public static final String SC_NAME_PS_STATUS = "TCGM_PS_STATUS";
	public static final String SC_NAME_PS_CMD = "TCGM_PS_CMD";
	public static final String PS_CMD_START = "START";
	public static final String PS_CMD_STOP = "STOP";
	public static final String PS_STATUS_RUNNING = "Running";
	public static final String PS_STATUS_STOPPED = "Stopped";

	public static final String ORACLE_EQUALS_COMPARISON = "=";
	public static final String ORACLE_LIKE_COMPARISON = "LIKE";
	public static final String ORACLE_IN_COMPARISON = "IN";
	

	/**
	 * This section of constants will be used to define the names of url parameters
	 * For example if the URL is http://www.x.com/openPage.do?cmd=open then we would define
	 * a constant named URL_PARM_CMD if it was http://www.x.com/openPage.do?record=1 we would define
	 * URL_PARM_RECORD = "record".
	 */
	public static final String URL_PARM_CMD = "cmd";

	/**
	 * This section of constants will be used to define the expected values of url parameters
	 * For example if the URL is http://www.x.com/openPage.do?cmd=open then we would define
	 * a constant named URL_PARM_VAL_OPEN if it was http://www.x.com/openPage.do?record=delete we would define
	 * URL_PARM_VAL_DELETE = "delete".
	 */
	public static final String URL_PARM_VAL_NEXT_PAGE = "nextpage";
	public static final String URL_PARM_VAL_PREV_PAGE = "prevpage";
	public static final String URL_PARM_VAL_FETCH = "fetch";
	public static final String URL_PARM_VAL_FILTER = "filter";
	public static final String URL_PARM_VAL_ADV_FILTER = "advancedfilter";
	public static final String URL_PARM_VAL_BATCH_MAINT = "batchmaintenance";
	public static final String URL_PARM_VAL_ADVFILTER = "advFilter";
	public static final String URL_PARM_VAL_CLEAR_FILTER = "clearfilter";
	public static final String URL_PARM_VAL_CLEAR_ADD_NEW = "clearaddnew";
	public static final String URL_PARM_VAL_DELETE_SELECTED = "deleteselected";
	public static final String URL_PARM_VAL_DELETE_ALL = "deleteall";
	public static final String URL_PARM_VAL_UPDATE_SELECTED = "updateselected";
	public static final String URL_PARM_VAL_UPDATE_ALL = "updateall";
	public static final String URL_PARM_VAL_PUBLISH_SELECTED = "publishselected";
	public static final String URL_PARM_VAL_UNPUBLISH_SELECTED = "unpublishselected";
	public static final String URL_PARM_VAL_PUBLISH_ALL = "publishall";
	public static final String URL_PARM_VAL_UNPUBLISH_ALL = "unpublishall";
	public static final String URL_PARM_VAL_SAVE = "save";
	public static final String URL_PARM_VAL_SAVE_ALL = "saveall";
	public static final String URL_PARM_VAL_SAVE_SELECTED = "saveselected";
	public static final String URL_PARM_VAL_BATCH_ENTRY = "batchentry";
	public static final String URL_PARM_VAL_SAVE_BATCH = "savebatch";
	public static final String URL_PARM_VAL_CLEAR_BATCH = "clearbatch";
	public static final String URL_PARM_VAL_CANCEL = "cancel";
	public static final String URL_PARM_VAL_ADD = "add";
	public static final String URL_PARM_VAL_MASS_UPDATE = "massupdate";
	public static final String URL_PARM_VAL_NEW = "new";
	public static final String URL_PARM_VAL_COPY = "copy";
	public static final String URL_PARM_VAL_DELETE = "delete";
	public static final String URL_PARM_VAL_MAINTAIN = "maintain";
	public static final String URL_PARM_VAL_RENAME = "rename";
	public static final String URL_PARM_VAL_COPY_ALL = "copyall";
	public static final String URL_PARM_VAL_COPY_SELECTED = "copyselected";
	public static final String URL_PARM_VAL_COPY_ROW = "copyrow";
	public static final String URL_PARM_VAL_START_JOB = "startjob";
	public static final String URL_PARM_VAL_SELECTED_RECORD_PAGE= "selectedrecordpage";
	public static final String URL_PARM_VAL_EMPTY = "";
	//These are here and not in the oracle dao objects because we can use
	//them for sql server and other dbs if we change
	public static final int CACHED_ROWSET = 1;
	public static final int JDBC_ROWSET = 2;

	public static final String ACT_CD_ADD = "A";
	public static final String ACT_CD_DEL = "D";
	public static final String ACT_CD_CHG = "C";
	public static final String ACT_CD_REV = "R";

	public static final String PROD_ORIG_C = "C";
	public static final String PROD_ORIG_F = "F";
	public static final String PROD_ORIG_K = "K";
	public static final String PROD_ORIG_M = "M";
	public static final String PROD_ORIG_P = "P";
	public static final String PROD_ORIG_R = "R";
	public static final String PROD_ORIG_T = "T";
	public static final String PROD_ORIG_Z = "Z";

	public static final String FLAG_PUBLISHED = "P";
	public static final String FLAG_UNPUBLISHED = "U";

	public static final int MAX_PERIODS = 13;
	public static final int MAX_RECS_TO_RETRIEVE = 25;
	
	public static final String MODEL_STATUS_OPEN     = "OPEN";
	public static final String MODEL_STATUS_CLOSED   = "CLOSED";
	public static final String MODEL_STATUS_DELETED  = "DELETED";
	public static final String MODEL_STATUS_ARCHIVED = "ARCHIVED";
	public static final String MODEL_STATUS_PURGED   = "PURGED";
	
	// These are constants added by Udaya B Aravapalli as a part of the Data Transfer(Export) fix.
	public static final String DT_SEND_TREE_TO_MVS = "SENDTREEMVS";	
	public static final String DT_DELIMITER		   = "::1";
	public static final String DT_COMMA		       = ",";
	public static final String DT_BLANK_SPACE      = " ";
	public static final String DT_INTER_COMP_TRSFR = "INTERCOMPTRSFRPT";
	public static final String DT_EXPORT_RGM_DATA  = "EXPORTRGMDATA";
	public static final String DT_EXPORT_RBL_DATA  = "EXPORTRBLDATA";
	public static final String DT_EXPORT_RBB_DATA  = "EXPORTRBBDATA";
	public static final String DT_EXPORT_RTC_DATA  = "EXPORTRTCDATA";
	public static final String DT_EXPORT_CCS_DATA  = "EXPORTCCSDATA";
	public static final String DT_EXPORT_IPS_DATA  = "EXPORTIPSDATA";
	
	public static final String DT_HYPHEN_DELIMITER = "--";
	public static final String DT_COLON_DELIMITER  = "::";
	public static final String DT_EXT_BPC		   = "bpc";
	public static final String DT_EXT_BPC2		   = "bpc2";
	public static final String DT_PROCESS_SEND_TREE_TO_MVS  = "JobSendTreeMvs";
	public static final String DT_PROCESS_INTER_COMP_TRSFR  = "JobInterCompTrsfRpt";
	public static final String DT_JOB_SEND_FACT_RGM  = "JobSendFactRGM";
	public static final String DT_JOB_SEND_FACT_RBL  = "JobSendFactRBL";
	public static final String DT_JOB_SEND_FACT_RBB  = "JobSendFactRBB";
	public static final String DT_JOB_SEND_FACT_RTC  = "JobSendFactRTC";
	public static final String DT_JOB_SEND_FACT_CCS  = "JobSendCCS";
	public static final String DT_JOB_SEND_PRICING_IPS  = "JobSendPricingIPS";

	//03/28/2006. These constants are added for ASR and BPC screens.
	public static final String LBL_SUP  = "SUP";
	public static final String LBL_RPT  = "RPT";
	public static final String LBL_BP   = "BP";
	public static final String LBL_COST = "COST";
	public static final String LBL_CBP   = "CBP";
	public static final String LBL_CCOST = "CCOST";
	public static final String LBL_BNK = "BNK";
		
	// These can probably go, as in most cases a generic parameter not found error is now returned.
	public static final String ERROR_DATASOURCE_URL_NOT_FOUND = "datasourceURL not found in deployement descriptor as an init param for the SQLUtilInit servlet";
	public static final String ERROR_ORACLE_ID_NOT_FOUND = "The oracleId was not found in the deployment descriptor as an init param for the SQLUtilInit servlet";
	public static final String ERROR_ORACLE_PSWD_NOT_FOUND = "The oraclePswd was not found in the deployment descriptor as an init param for the SQLUtilInit servlet";

	public static final String VALUE_LIST_DELIMITER = "~";

	public static final String BR = "<BR />";
	
	//Constant values to define the process scheduler and process scheduler monitor sleep times
	public static final String PS_SLEEP = "psSleep";
	public static final String PSM_SLEEP = "psmSleep";
	public static final String DFM_SLEEP = "dfmSleep";
	
	//JCL Parameters
	public static final String JCL_JOB_LOG_LOC = "jclJobLogLoc";		
	public static final String JCL_USR_ID = "jclUserId";		
	public static final String JCL_READ_DATASET_IDEN = "jclReadDataSetIden";	
	public static final String JCL_DATASET_IDEN = "jclDataSetIden";	
	public static final String JCL_HOST_NAME1 = "jclHostName1";		
	public static final String JCL_USER_PASS1 = "jclUserPass1";		
	public static final String JCL_HOST_NAME2 = "jclHostName2";		
	public static final String JCL_USER_PASS2 = "jclUserPass2";		
	public static final String JCL_IMPORTS_LOC = "jclImportsLoc";		
	public static final String JCL_EXPORTS_LOC = "jclExportsLoc";	
	
	//Date and TimeZone Parameters
	public static final String  DT_SERVER_TIMEZONE = "Server_TimeZone";
	public static final String  DT_TCGM_TIMEZONE   = "TCGM_TimeZone";
	public static final String  DT_TCGM_DATEFORMAT = "TCGM_DateFormat";
	public static final String  DT_SHARE_LOCATION = "sharelocation";
	public static final String  DT_GMT             = "GMT";
	public static final String  DT_OFFSET          = "3600000";
	
	public static final String STR_SEP = " | ";	
	
	//CCS FTP related parameters
	
	public static final String  CCS_LOCATION = "ccslocation";
	public static final String  CCS_HOST = "ccshost";
	public static final String  CCS_USER = "ccsuser";
	public static final String  CCS_PASS = "ccspassword";
	public static final String  CCS_TNS = "ccs";
	
	public static final String  IPS_LOCATION = "ipslocation";	
	public static final String  IPS_USER = "ipsuser";
	public static final String  IPS_PASS = "ipspassword";
	public static final String  FILE_UPLOAD_DIR = "fileUploadDirectory";
	public static final String  COMPLETED_JOB_VIEW = "completedJobView";
	
	
	//Button Names
	public static final String BTN_VAL_GET = "GET";
	public static final String BTN_VAL_GET_APP = "APPGET";
	public static final String BTN_VAL_VIEW = "VIEW";
	public static final String BTN_VAL_APPVIEW = "APPVIEW";
	public static final String BTN_VAL_ADD = "ADD";
	public static final String BTN_VAL_SAVE = "SAVE";
	public static final String BTN_VAL_REMOVE = "REMOVE";
	public static final String BTN_VAL_BURST = "BURST";
	public static final String BTN_VAL_REGENERATE = "REGENERATE";
	public static final String MAINT_VAL_CREATE = "MAINT_CREATE";
	public static final String MAINT_VAL_FILTER = "FILTER";
	public static final String MAINT_VAL_BURST = "BURSTMAINT";
	public static final String MAINT_VAL_REMOVE = "MAINT_REMOVE";
	public static final String BTN_VAL_CREATION = "CREATION";
	public static final String BTN_VAL_NOTIFICATION = "NOTIFICATION";
	public static final String BTN_VAL_BROWSE = "BROWSE";
	public static final String BTN_VAL_SELECT_APP = "APPSELECT";
	public static final String BTN_VAL_SELECT = "SELECT";
	public static final String FORWARD_USER_MAINT_LIST = "rptUser";
	public static final String FORWARD_APPUSER_MAINT_LIST = "appUser";
	
	public static final String REPORT_CONSTANT_ADD_USERS    = "addUsers";
	public static final String REPORT_CONSTANT_REMOVE_USERS = "removeUsers";
	public static final String REPORT_CONSTANT_ADD_GROUP    = "addGroup";
	
	//Report Roles
	public static final String AREA    		= "Area";
	public static final String AFFILIATE    = "Affiliate";
	public static final String SECTOR    	= "Sector";
	public static final String HQ_CON    	= "HQC";
	public static final String HQ_SUP    	= "HQS";
	public static final String DIVISION    	= "D";
	public static final String ALL_DIVISIONS    	= "DALL";
	public static final String OPEN_ITEMS    	= "Open Items";
	

	public TCGMConstants()
	{
	}
}
