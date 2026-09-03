package abbott.ai.tcgm.process;

import java.util.Vector;

import abbott.ai.tcgm.data.oracle.OracleDao;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.TCGMConstants;

import abbott.ai.tcgm.data.SQLUtil;

import org.apache.log4j.Logger;

public class DaemonMngr {
    private static DaemonMngr _instance = new DaemonMngr();
    private static DataFeedMonitor dataFeedMonitor = null;
    private static ProcessScheduler processScheduler = null;
    /**
     * Defining one more processor to monitor the processScheduler
     */
	private static ProcessSchedulerMonitor processSchedulerMonitor = null;
    private static int dfmSleep = 5000; //Default value
    private static int psSleep = 5000; //Default value
	private static int psmSleep = 300000; //Default value
	
	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.process.DaemonMngr");

	public static void init(javax.servlet.ServletConfig sc)
	{
		if(_instance == null)
		{
			_instance = new DaemonMngr();
		}
		String psSleep = sc.getInitParameter(TCGMConstants.PS_SLEEP);
		if(psSleep!=null && !psSleep.trim().equals(""))
		{
			_instance.psSleep = Integer.parseInt(psSleep); 
		}
		logger.debug("The value of Process Scheduler Sleep Time is "+ psSleep);
				
		String psmSleep = sc.getInitParameter(TCGMConstants.PSM_SLEEP);
		if(psmSleep!=null && !psmSleep.trim().equals(""))
		{
			_instance.psmSleep = Integer.parseInt(psmSleep); 
		}
		logger.debug("The value of Process Scheduler Monitor Sleep Time is "+ psmSleep);

		String dfmSleep = sc.getInitParameter(TCGMConstants.DFM_SLEEP);
		if(dfmSleep!=null && !dfmSleep.trim().equals(""))
		{
			_instance.dfmSleep = Integer.parseInt(dfmSleep); 
		}
		logger.debug("The value of Data Feed Monitor Sleep Time is "+ dfmSleep);
	}
    
    
    private DaemonMngr() {    }

    public static DaemonMngr getInstance() {
        return _instance;
    }
    
    public static void startProcessScheduler() {
        if (processScheduler == null)
            processScheduler = new ProcessScheduler(psSleep);
    }

    public static void stopProcessScheduler() {
        if (processScheduler != null)
        	/**
        	 * Checking for current running jobs if any with status P during stop scheduler, 
        	 * if exist assign the flag value as 'D'. Check the flag value when startScheduler 
        	 * request triggered from front end to handle the current running job.
        	 */
			try{
					ProcessMngr pm = new ProcessMngr();
				
					Vector processingJobs = pm.getAllProcessingJobs(SQLUtil.getOracleAdmin());
					if( processingJobs.size()>0)
					{
					OracleDao.strStopStatus = "D";
					}
			}catch (TCGMException te)
			{

			}

			processScheduler.stopRequested();
			//SQLUtil.closeConnection(OracleDao.ps_conn.close());
        	processScheduler = null;
    }

    public static boolean isProcessSchedulerRunning() {
        if (processScheduler != null && processScheduler.isAlive())
            return true;
        else
            return false;
    }

    public static boolean isDatafeedMonitorRunning() {
        if (dataFeedMonitor != null && dataFeedMonitor.isAlive())
            return true;
        else
            return false;
    }

    public static void startDataFeedMonitor() throws TCGMException {
        if (dataFeedMonitor == null) {
            dataFeedMonitor = new DataFeedMonitor(dfmSleep);
        }
    }

    public static void stopDataFeedMonitor() {
        if (dataFeedMonitor != null)
            dataFeedMonitor.stopRequested();
        dataFeedMonitor = null;
    }

    public static String getCurrentFeed() {
        if (dataFeedMonitor != null)
            return dataFeedMonitor.getCurrentFeed();
        else return
            "Monitor Not Running";
    }

    public static String getCurrentProcess() {
        if (processScheduler != null)
            return processScheduler.getCurrentProcess();
        else return
            "Scheduler Not Running";
    }

	public static void startProcessSchedulerMonitor() {
		if (processSchedulerMonitor == null)
		processSchedulerMonitor = new ProcessSchedulerMonitor(psmSleep);
	}

	public static void stopProcessSchedulerMonitor() {
		if (processSchedulerMonitor != null)
		processSchedulerMonitor.stopRequested();
		processSchedulerMonitor = null;
	}    
}