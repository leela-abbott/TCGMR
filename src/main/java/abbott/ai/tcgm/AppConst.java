package abbott.ai.tcgm;

//import abbott.ai.tcgm.helpers.*;
import org.apache.log4j.Logger;

import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public final class AppConst
{
	private static Logger myLogger = Logger.getLogger( "AppConst" );
    private static String webInfDir="";
    private static String appRootDir="";
    private static String crystalServer="";
    private static String crystalOdbc="";
    private static String dbTnsName="";
    private static UserToken reportFtpId = null;
    private static UserToken essbaseId = null;
    private static UserToken jobId = null;
	private static String jclPath="";
	
	// 11-17-05 ReportNet Config
	private static String reportNetServiceLocator = "";
	private static String reportNetNameSpace = "";
	private static String reportNetUsername = "";
	private static String reportNetPassword = "";
	private static String reportNetPrinter1 = "";
	private static String reportNetPackageName = "";
	
	private static String reportNetDIVPath      			 = "";
	private static String reportNetHQPath      			 = "";
	private static String reportNetAffiliateReportsPath  = "";
	private static String reportNetAffiliatePath		 = "";
	private static String reportNetSectorPath  			 = "";
	private static String reportNetReportsPath 			 = "";
	private static String reportNetFolderReports		 = "";
	private static String reportNetParmsDatasetTableID	 = "";
	private static String reportNetParmsModelID			 = "";
	private static String reportNetParmsJobQueueID		 = "";
	private static String reportNetParmsAffiliate		 = "";
	private static String reportNetExecutionSequence	 = "";
	
	// 07-11-2006 RSystem Config
	private static String rSystemAddress = "";
	private static String rSystemUsername = "";
	private static String rSystemPassword = "";

	// 10-21-05 LDAP Authentication Parms
	private static String authenticationHost="";
	private static String authenticationBaseDn="";
	private static String authenticationPort="";
	private static String authenticationUserId="";
	private static String authenticationPassword="";
	
	public static String essbaseLibrary = "";
	public static String essbaseHost = "";
	public static String essbaseFilename = "";
	
	//Essbase files export directory and filenames
	public static String essbaseExportDir = "";
	public static String file_ESS_FLEX = "";
	public static String file_ESS_ANLA1A4 = "";
	public static String file_ESS_ANLA2A3 = "";
	
	
	public static String reportLibrary = "";
    public static String reportHost = "";
    
	public static String reportTriggerFile = "";
    public static String reportParmFile = "";
    
	public static String reportsUrl = "";
	
    public static final String className = AppConst.class.getName();
    private static AppConst _instance = null;
    
    //JCL Parameters
    
	public static String jclJobLogLoc="";		//Job log location DEV - R or PROD - Z	
	public static String jclUserId="";		    //Person to be notified USER ID
	public static String jclReadDataSetIden="";//Dataset Identification PROD - AB or DEV - DV
	public static String jclDataSetIden="";	//Dataset Identification PROD - AB or DEV - DV
	public static String jclHostName1="";		//Main Frame Host Name
	public static String jclUserPass1="";		//User ID and Password
	public static String jclHostName2="";		//Essbase Host Name
	public static String jclUserPass2="";		//User ID and Password
	public static String jclImportsLoc="";		//Imports directory location
	public static String jclExportsLoc="";		//Exports directory location
	
	//Date and TimeZone Parameters
	public static String serverTimeZone = "";
	public static String tcgmTimeZone = "";
	public static String tcgmDateFormat = "";
	
	public static String sharelocation = "";
	
	public static String ccslocation = "";
	public static String ccshost = "";
	public static String ccsuser = "";
	public static String ccspassword = "";
	public static String ccsTnsHost = "";
	
	public static String ipslocation = "";
	public static String ipsuser = "";
	public static String ipspassword = "";
    public static String fileUploadDirectory="";
    public static String completedJobView="";
    /**
     * @return AppConst
     * @throws TCGMException
     */
    public static AppConst getInstance() throws TCGMException
    {
        if(_instance == null)
        {
            throw new TCGMException(className, "getInstance()", className + " not Initialized");
        }
        else
            return _instance;
    }
    /*****************************************************************************************/
    /**
     * Default Constructor
     */
    private AppConst()	{	}

    /**
     * @param sc
     */
    public static void init(javax.servlet.ServletConfig sc)
    {
        _instance = new AppConst();
        String realPath = sc.getServletContext().getRealPath("\\");

        // Add \ for weblogic since realpath is returned without an ending slash.
        // This allows this code to run in either Tomcat or Weblogic without error.
		if (!realPath.endsWith("\\") ) realPath = realPath + "\\";

		myLogger.error("realPath: " + realPath);

        _instance.appRootDir = realPath;
        _instance.webInfDir = realPath + "WEB-INF";
        _instance.crystalServer = readParameter(TCGMConstants.AC_CRYSTAL_SERVER, sc);
        _instance.dbTnsName = readParameter(TCGMConstants.AC_DB_TNS_NAME, sc);
        _instance.crystalOdbc = readParameter(TCGMConstants.AC_CRYSTAL_ODBC, sc);
		_instance.jclPath = readParameter(TCGMConstants.AC_JCL_PATH, sc);

		// 10-21-05 LDAP Authentication Parms
		_instance.authenticationHost = readParameter(TCGMConstants.AC_AUTH_HOST, sc);
		_instance.authenticationBaseDn = readParameter(TCGMConstants.AC_AUTH_BASEDN, sc);
		_instance.authenticationPort = readParameter(TCGMConstants.AC_AUTH_PORT, sc);
		_instance.authenticationUserId = readParameter(TCGMConstants.AC_AUTH_USERID, sc);
		_instance.authenticationPassword = readParameter(TCGMConstants.AC_AUTH_PW, sc);		
		
		// 11-17-05 ReportNet Config
		_instance.reportNetServiceLocator = readParameter(TCGMConstants.AC_REPORT_NET_SERVICE_LOCATOR, sc);
		_instance.reportNetNameSpace = readParameter(TCGMConstants.AC_REPORT_NET_NAME_SPACE, sc);
		_instance.reportNetUsername = readParameter(TCGMConstants.AC_REPORT_NET_USERNAME, sc);
		_instance.reportNetPassword = readParameter(TCGMConstants.AC_REPORT_NET_PASSWORD, sc);
		_instance.reportNetPrinter1 = readParameter(TCGMConstants.AC_REPORT_NET_PRINTER1, sc);
		_instance.reportNetPackageName = readParameter(TCGMConstants.AC_REPORT_NET_PACKAGENAME, sc);
		
		_instance.reportNetDIVPath = readParameter(TCGMConstants.AC_REPORT_NET_DIVPATH, sc);
		_instance.reportNetHQPath = readParameter(TCGMConstants.AC_REPORT_NET_HQPATH, sc);
		_instance.reportNetAffiliateReportsPath = readParameter(TCGMConstants.AC_REPORT_NET_AFF_REPORTS_PATH, sc);
		_instance.reportNetAffiliatePath = readParameter(TCGMConstants.AC_REPORT_NET_AFF_PATH, sc);
		_instance.reportNetSectorPath = readParameter(TCGMConstants.AC_REPORT_NET_SECTOR_PATH, sc);
		_instance.reportNetReportsPath = readParameter(TCGMConstants.AC_REPORT_NET_REPORTS_PATH, sc);
		_instance.reportNetFolderReports = readParameter(TCGMConstants.AC_REPORT_NET_FOLDER_REPORTS, sc);
		_instance.reportNetParmsDatasetTableID = readParameter(TCGMConstants.AC_REPORT_NET_PARMS_DATASET_TABLE_ID, sc);
		_instance.reportNetParmsModelID = readParameter(TCGMConstants.AC_REPORT_NET_PARMS_MODEL_ID, sc);
		_instance.reportNetParmsJobQueueID = readParameter(TCGMConstants.AC_REPORT_NET_PARMS_JOB_QUE_ID, sc);
		_instance.reportNetParmsAffiliate = readParameter(TCGMConstants.AC_REPORT_NET_PARMS_AFFILIATE, sc);
		_instance.reportNetExecutionSequence = readParameter(TCGMConstants.AC_REPORT_NET_EXECUTION_SEQUENCE, sc);

		// 07-11-06 RSystem Config
		_instance.rSystemAddress = readParameter(TCGMConstants.AC_R_SYSTEM_ADDRESS, sc);
		_instance.rSystemUsername = readParameter(TCGMConstants.AC_R_SYSTEM_USERNAME, sc);
		_instance.rSystemPassword = readParameter(TCGMConstants.AC_R_SYSTEM_PASSWORD, sc);

        String id = readParameter(TCGMConstants.AC_REPORT_FTP_ID, sc);
        String pswd = readParameter(TCGMConstants.AC_REPORT_FTP_PSWD, sc);
        _instance.reportFtpId = new UserToken(id, pswd);

       //Populating Report Library and Host details.
	   reportLibrary = readParameter(TCGMConstants.AC_REPORT_FTP_LIBRARY, sc);
	   reportHost = readParameter(TCGMConstants.AC_REPORT_FTP_HOST, sc);
	   reportTriggerFile = readParameter(TCGMConstants.AC_REPORT_TRIGGERFILE, sc);
	   reportParmFile = readParameter(TCGMConstants.AC_REPORT_PARMFILE, sc);

        id = readParameter(TCGMConstants.AC_ESSBASE_ID, sc);
        pswd = readParameter(TCGMConstants.AC_ESSBASE_PSWD, sc);
        _instance.essbaseId = new UserToken(id, pswd);
        
        //Populating Essbase Library and Host details.
		essbaseLibrary = readParameter(TCGMConstants.AC_ESSBASE_LIBRARY, sc);
		essbaseHost = readParameter(TCGMConstants.AC_ESSBASE_HOST, sc);
		essbaseFilename = readParameter(TCGMConstants.AC_ESSBASE_FILENAME, sc);
        id = readParameter(TCGMConstants.AC_JOB_ID, sc);
        pswd = readParameter(TCGMConstants.AC_JOB_PSWD, sc);
        _instance.jobId = new UserToken(id, pswd);
        
        //Populating Essbase dir path and file names from web.xml
        _instance.essbaseExportDir = readParameter(TCGMConstants.AC_ESSBASE_EXP_DIR, sc);
        _instance.file_ESS_ANLA1A4 = readParameter(TCGMConstants.AC_FILE_ESS_ANLA1A4, sc);
        _instance.file_ESS_ANLA2A3 = readParameter(TCGMConstants.AC_FILE_ESS_ANLA2A3, sc);
        _instance.file_ESS_FLEX = readParameter(TCGMConstants.AC_FILE_ESS_FLEX, sc);
        //Populating Reports URL from web.xml.
		reportsUrl = readParameter(TCGMConstants.AC_REPORTS_URL, sc);
		
		// JCL Parameters from web.xml file
		_instance.jclJobLogLoc = readParameter(TCGMConstants.JCL_JOB_LOG_LOC, sc);
		_instance.jclUserId = readParameter(TCGMConstants.JCL_USR_ID, sc);
		_instance.jclReadDataSetIden = readParameter(TCGMConstants.JCL_READ_DATASET_IDEN, sc);
		_instance.jclDataSetIden = readParameter(TCGMConstants.JCL_DATASET_IDEN, sc);
		_instance.jclHostName1 = readParameter(TCGMConstants.JCL_HOST_NAME1, sc);
		_instance.jclUserPass1 = readParameter(TCGMConstants.JCL_USER_PASS1, sc);
		_instance.jclHostName2 = readParameter(TCGMConstants.JCL_HOST_NAME2, sc);
		_instance.jclUserPass2 = readParameter(TCGMConstants.JCL_USER_PASS2, sc);
		_instance.jclImportsLoc = readParameter(TCGMConstants.JCL_IMPORTS_LOC, sc);
		_instance.jclExportsLoc = readParameter(TCGMConstants.JCL_EXPORTS_LOC, sc);
		
		//  Date and TimeZone Parameters
		_instance.serverTimeZone = readParameter(TCGMConstants.DT_SERVER_TIMEZONE, sc);
		_instance.tcgmTimeZone   = readParameter(TCGMConstants.DT_TCGM_TIMEZONE, sc);
		_instance.tcgmDateFormat = readParameter(TCGMConstants.DT_TCGM_DATEFORMAT, sc);
		
		_instance.sharelocation = readParameter(TCGMConstants.DT_SHARE_LOCATION, sc);
		
		_instance.ccslocation = readParameter(TCGMConstants.CCS_LOCATION, sc);
		_instance.ccshost = readParameter(TCGMConstants.CCS_HOST, sc);
		_instance.ccsuser = readParameter(TCGMConstants.CCS_USER, sc);
		_instance.ccspassword = readParameter(TCGMConstants.CCS_PASS, sc);
		_instance.ccsTnsHost = readParameter(TCGMConstants.CCS_TNS, sc);
		
		_instance.ipslocation = readParameter(TCGMConstants.IPS_LOCATION, sc);		
		_instance.ipsuser = readParameter(TCGMConstants.IPS_USER, sc);
		_instance.ipspassword = readParameter(TCGMConstants.IPS_PASS, sc);
		_instance.fileUploadDirectory = readParameter(TCGMConstants.FILE_UPLOAD_DIR, sc);
		_instance.completedJobView=readParameter(TCGMConstants.COMPLETED_JOB_VIEW, sc);
		myLogger.error("\nApplication Constants Initialized\n---------------------------------");
		myLogger.error( _instance.toString() );
    }

    private static String readParameter(String parmKey, javax.servlet.ServletConfig sc) {
        String parmValue = sc.getInitParameter(parmKey);
        if(parmValue == null || parmValue.trim().equals(""))
        {
            System.err.println("Parameter '" + parmKey + "' not found in web.xml");
        }
        return parmValue;
    }

    public static UserToken getReportFtpId() {
        return _instance.reportFtpId;
    }
    public static UserToken getEssbaseId() {
        return _instance.essbaseId;
    }
    public static UserToken getJobId() {
        return _instance.jobId;
    }

    /**
     *
     * @return
     */
    public static String getWebInfDir()
    {
        return webInfDir;
    }
    /**
     *
     * @return
     */
    public static String getAppRootDir()
    {
        return appRootDir;
    }
    /**
     *
     * @return
     */
    public static String getCrystalServer()
    {
        return crystalServer;
    }

    public static String getCrystalOdbc()
    {
        return crystalOdbc;
    }
    
	public static String getDbTnsName() {
		return dbTnsName;
	}


	public static String getJclPath() {
		return jclPath;
	}

	public static String getAuthenticationHost() {
		return authenticationHost;
	}
	public static String getAuthenticationBaseDn() {
		return authenticationBaseDn;
	}    
	public static String getAuthenticationPort() {
		return authenticationPort;
	}
	public static String getAuthenticationUserId() {
		return authenticationUserId;
	} 
	public static String getAuthenticationPassword() {
		return authenticationPassword;
	}

	public static String getReportNetServiceLocator() {
		return reportNetServiceLocator;
	}	
	public static String getReportNetNameSpace() {
		return reportNetNameSpace;
	}
	public static String getReportNetUsername() {
		return reportNetUsername;
	}
	public static String getReportNetPassword() {
		return reportNetPassword;
	}
	public static String getReportNetPrinter1() {
		return reportNetPrinter1;
	}	
	public static String getReportNetPackageName() {
		return reportNetPackageName;
	}	

	public static String getRSystemAddress() {
		return rSystemAddress;
	}
	public static String getRSystemPassword() {
		return rSystemPassword;
	}
	public static String getRSystemUsername() {
		return rSystemUsername;
	}

	public static String getServerTimeZone() {
		return serverTimeZone;
	}

	public static String getTcgmTimeZone() {
		return tcgmTimeZone;
	}

	public static String getTcgmDateFormat() {
		return tcgmDateFormat;
	}

    /*****************************************************************************************/
    /**
     * @return
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("appRootDir: ");
        sb.append(this.getAppRootDir());
        sb.append("\ncrystalServer: ");
        sb.append(this.getCrystalServer());
        sb.append("\ncrystalOdbc: ");
        sb.append(this.getCrystalOdbc());
        sb.append("\nwebInfDir: ");
        sb.append(this.getWebInfDir());
        sb.append("\ndbTnsName: ");
        sb.append(this.getDbTnsName());
        sb.append("\nessbaseId: ");
        sb.append(this.essbaseId.toString());
        sb.append("\nreportFtpId: ");
        sb.append(this.reportFtpId.toString());
        sb.append("\njobId: ");
        sb.append(this.jobId.toString());
		sb.append("\njclPath: ");
		sb.append(this.getJclPath());     
		sb.append("\nauthenticationHost: ");
		sb.append(this.getAuthenticationHost());    
		sb.append("\nauthenticationBaseDn: ");
		sb.append(this.getAuthenticationBaseDn());    
		sb.append("\nauthenticationPort: ");
		sb.append(this.getAuthenticationPort());    
		sb.append("\nauthenticationUserId: ");
		sb.append(this.getAuthenticationUserId());    
		sb.append("\nauthenticationPassword: ");
		sb.append(this.getAuthenticationPassword());  
		sb.append("\nreportNetServiceLocator: ");
		sb.append(this.getReportNetServiceLocator());
		sb.append("\nreportNetNameSpace: ");
		sb.append(this.getReportNetNameSpace());  
		sb.append("\nreportNetUsername: ");
		sb.append(this.getReportNetUsername());    
		sb.append("\nreportNetPassword: ");
		sb.append(this.getReportNetPassword());
		sb.append("\nreportNetPrinter1: ");
		sb.append(this.getReportNetPrinter1());
		sb.append("\nreportNetPackageName: ");
		sb.append(this.getReportNetPackageName());

		sb.append("\nreportNetHQPath: ");
		sb.append(this.getReportNetHQPath());
		sb.append("\nreportNetAffiliateReportsPath: ");
		sb.append(this.getReportNetAffiliateReportsPath());
		sb.append("\nreportNetAffiliatePath: ");
		sb.append(this.getReportNetAffiliatePath());
		sb.append("\nreportNetSectorPath: ");
		sb.append(this.getReportNetSectorPath());
		sb.append("\nreportNetReportsPath: ");
		sb.append(this.getReportNetReportsPath());
		sb.append("\nreportNetFolderReports: ");
		sb.append(this.getReportNetFolderReports());
		sb.append("\nreportNetParmsDatasetTableID: ");
		sb.append(this.getReportNetParmsDatasetTableID());
		sb.append("\nreportNetParmsModelID: ");
		sb.append(this.getReportNetParmsModelID());
		sb.append("\nreportNetParmsJobQueID: ");
		sb.append(this.getReportNetParmsJobQueueID());
		sb.append("\nreportNetParmsAffiliate: ");
		sb.append(this.getReportNetParmsAffiliate());
		
		sb.append("\nrSystemAddress: ");
		sb.append(this.getRSystemAddress());
		sb.append("\nrSystemUsername: ");
		sb.append(this.getRSystemUsername());    
		sb.append("\nrSystemPassword: ");
		sb.append(this.getRSystemPassword());

		sb.append("\nrServerTimeZone: ");
		sb.append(this.getServerTimeZone());
		sb.append("\nrTcgmTimeZone: ");
		sb.append(this.getTcgmTimeZone());
		sb.append("\nrTcgmDateFormat: ");
		sb.append(this.getTcgmDateFormat());
		
        return sb.toString();

    }

	/**
	 * @return
	 */
	public static String getReportNetAffiliatePath() {
		return reportNetAffiliatePath;
	}

	/**
	 * @return
	 */
	public static String getReportNetAffiliateReportsPath() {
		return reportNetAffiliateReportsPath;
	}

	/**
	 * @return
	 */
	public static String getReportNetFolderReports() {
		return reportNetFolderReports;
	}

	/**
	 * @return
	 */
	public static String getReportNetHQPath() {
		return reportNetHQPath;
	}

	/**
	 * @return
	 */
	public static String getReportNetParmsAffiliate() {
		return reportNetParmsAffiliate;
	}

	/**
	 * @return
	 */
	public static String getReportNetParmsDatasetTableID() {
		return reportNetParmsDatasetTableID;
	}

	/**
	 * @return
	 */
	public static String getReportNetParmsJobQueueID() {
		return reportNetParmsJobQueueID;
	}

	/**
	 * @return
	 */
	public static String getReportNetParmsModelID() {
		return reportNetParmsModelID;
	}

	/**
	 * @return
	 */
	public static String getReportNetReportsPath() {
		return reportNetReportsPath;
	}

	/**
	 * @return
	 */
	public static String getReportNetSectorPath() {
		return reportNetSectorPath;
	}

	/**
	 * @param sreportNetAffiliatePath String
	 */
	public static void setReportNetAffiliatePath(String sreportNetAffiliatePath) {
		reportNetAffiliatePath = sreportNetAffiliatePath;
	}

	/**
	 * @param sreportNetAffiliateReportsPath String
	 */
	public static void setReportNetAffiliateReportsPath(String sreportNetAffiliateReportsPath) {
		reportNetAffiliateReportsPath = sreportNetAffiliateReportsPath;
	}

	/**
	 * @param sreportNetFolderReports String
	 */
	public static void setReportNetFolderReports(String sreportNetFolderReports) {
		reportNetFolderReports = sreportNetFolderReports;
	}

	/**
	 * @param sreportNetHQPath String
	 */
	public static void setReportNetHQPath(String sreportNetHQPath) {
		reportNetHQPath = sreportNetHQPath;
	}

	/**
	 * @param sreportNetParmsAffiliate String
	 */
	public static void setReportNetParmsAffiliate(String sreportNetParmsAffiliate) {
		reportNetParmsAffiliate = sreportNetParmsAffiliate;
	}

	/**
	 * @param sreportNetParmsDatasetTableID String
	 */
	public static void setReportNetParmsDatasetTableID(String sreportNetParmsDatasetTableID) {
		reportNetParmsDatasetTableID = sreportNetParmsDatasetTableID;
	}

	/**
	 * @param sreportNetParmsJobQueueID String
	 */
	public static void setReportNetParmsJobQueueID(String sreportNetParmsJobQueueID) {
		reportNetParmsJobQueueID = sreportNetParmsJobQueueID;
	}

	/**
	 * @param sreportNetParmsModelID String
	 */
	public static void setReportNetParmsModelID(String sreportNetParmsModelID) {
		reportNetParmsModelID = sreportNetParmsModelID;
	}

	/**
	 * @param sreportNetReportsPath String
	 */
	public static void setReportNetReportsPath(String sreportNetReportsPath) {
		reportNetReportsPath = sreportNetReportsPath;
	}

	/**
	 * @param sreportNetSectorPath String
	 */
	public static void setReportNetSectorPath(String sreportNetSectorPath) {
		reportNetSectorPath = sreportNetSectorPath;
	}

	/**
	 * @return
	 */
	public static String getReportNetExecutionSequence() {
		return reportNetExecutionSequence;
	}

	/**
	 * @param sreportNetExecutionSequence String
	 */
	public static void setReportNetExecutionSequence(String sreportNetExecutionSequence) {
		reportNetExecutionSequence = sreportNetExecutionSequence;
	}

	/**
	 * @return
	 */
	public static String getSharelocation() {
		return sharelocation;
	}

	/**
	 * @param string
	 */
	public static void setSharelocation(String string) {
		sharelocation = string;
	}

	/**
	 * @return
	 */
	public static String getCcshost() {
		return ccshost;
	}

	/**
	 * @return
	 */
	public static String getCcslocation() {
		return ccslocation;
	}

	/**
	 * @return
	 */
	public static String getCcspassword() {
		return ccspassword;
	}

	/**
	 * @return
	 */
	public static String getCcsuser() {
		return ccsuser;
	}

	/**
	 * @return Returns the ccsTnsHost.
	 */
	public static String getCcsTnsHost() {
		return ccsTnsHost;
	}
	/**
	 * @param ccsTnsHost The ccsTnsHost to set.
	 */
	public static void setCcsTnsHost(String ccsTnsHost) {
		AppConst.ccsTnsHost = ccsTnsHost;
	}
	/**
	 * @return Returns the ipslocation.
	 */
	public static String getIpslocation() {
		return ipslocation;
	}
	/**
	 * @param ipslocation The ipslocation to set.
	 */
	public static void setIpslocation(String ipslocation) {
		AppConst.ipslocation = ipslocation;
	}
	/**
	 * @return Returns the ipspassword.
	 */
	public static String getIpspassword() {
		return ipspassword;
	}
	/**
	 * @param ipspassword The ipspassword to set.
	 */
	public static void setIpspassword(String ipspassword) {
		AppConst.ipspassword = ipspassword;
	}
	/**
	 * @return Returns the ipsuser.
	 */
	public static String getIpsuser() {
		return ipsuser;
	}
	/**
	 * @param ipsuser The ipsuser to set.
	 */
	public static void setIpsuser(String ipsuser) {
		AppConst.ipsuser = ipsuser;
	}
	/**
	 * @return Returns the fileUploadDirectory.
	 */
	public static String getFileUploadDirectory() {
		return fileUploadDirectory;
	}
	public static String getReportNetDIVPath() {
		return reportNetDIVPath;
	}
}