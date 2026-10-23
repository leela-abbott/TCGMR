package abbott.ai.tcgm.process;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ProcessDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.data.oracle.OracleDao;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;

public class ProcessScheduler extends SelfRunningThread
{
	private Calendar calendar = null;
	public static int BATCH_START = 20;
	private static int BATCH_END = 5;
	private static final Logger myLogger = LogManager.getLogger( "abbott.ai.tcgm.process.ProcessScheduler" );
	private String currentProcess = "--NONE--";
	private static boolean batchFlag=true;

	public ProcessScheduler(int sleepInterval)
	{
		super(sleepInterval);
		calendar = Calendar.getInstance();
		this.start();
	}

	protected void runWork()
	{
		myLogger.info("ProcessScheduler Started.");
		while ( this.noStopRequested && !Thread.currentThread().isInterrupted() )
		{
			try
			{
				try{
							ProcessMngr pm = new ProcessMngr();
		
							Vector processingJobs = pm.getAllProcessingJobs(SQLUtil.getOracleAdmin());
							if( !(processingJobs.size()>0 && OracleDao.strStopStatus.equalsIgnoreCase("D")))
							{
								OracleDao.strStopStatus = "";
								this.checkForJob();								
							}
					}catch (TCGMException te)
					{
	
					}
				Thread.sleep(this.sleeptime);
			}
			catch (java.lang.InterruptedException ex)
			{
				myLogger.error("Process Scheduler Interupted");
				myLogger.error("Process Scheduler Monitor stop method invoking");
				DaemonMngr dm = DaemonMngr.getInstance();
				dm.stopProcessSchedulerMonitor();
				myLogger.error("Process Scheduler Monitor stop method executed");
				break;
			}
		}
		myLogger.info("ProcessScheduler Exiting.");
	}

	private void checkForJob()
	{
		try
		{
			ProcessMngr pm = new ProcessMngr();
			JobInstance job = null;
			Vector processingJobs = pm.getAllProcessingJobs(SQLUtil.getOracleAdmin());
			for (int i=0; i< processingJobs.size();i++)
			{
				myLogger.debug(processingJobs.get(i));
				job = (JobInstance) processingJobs.get(i);
				ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(SQLUtil.getOracleAdmin());
				pd.setJobComplete(job.getJobQueId(), JobInstance.JobStatus.Unknown);
				job = null;
			}
			Vector nonCompletedJobs = pm.getAllNonCompleteJobs(SQLUtil.getOracleAdmin());
			for (int i=0; i< nonCompletedJobs.size();i++)
			{
				myLogger.debug(nonCompletedJobs.get(i));
				job = (JobInstance) nonCompletedJobs.get(i);

				if(job!=null){
					myLogger.info("Found job to move to status 'R' or 'C'.");
					this.currentProcess = job.getDesc();
					myLogger.debug( job.toString() );
					pm.runJob(SQLUtil.getOracleAdmin(), job.getJobQueId() );
					this.currentProcess = "--NONE--";
				}
				job = null;

			}
			if ( isBatchMode() )
			{
				job = pm.getNextJob(SQLUtil.getOracleAdmin());
				
				if ( job == null && pm.processSystemCompact(SQLUtil.getOracleAdmin()))
				{
					createSystemPerformanceJob(JobConstants.PN_SYSTEM_COMPACT_JOB);
					job = pm.getNextJob(SQLUtil.getOracleAdmin());
				}

				if ( job == null && pm.processModelPurge(SQLUtil.getOracleAdmin()))
				{
					createSystemPerformanceJob(JobConstants.PN_MODEL_PURGE_JOB);
					job = pm.getNextJob(SQLUtil.getOracleAdmin());
				}
	
			}
			else
				job = pm.getNextImmediateJob(SQLUtil.getOracleAdmin());

			if (job != null)
			{
				myLogger.info("Found job to run.");
				this.currentProcess = job.getDesc();
				myLogger.debug( job.toString() );
				pm.runJob(SQLUtil.getOracleAdmin(), job.getJobQueId() );
				this.currentProcess = "--NONE--";
			}
		}
		catch (TCGMException te)
		{
			myLogger.error("Exception checking for job");
			myLogger.error("Exception: " + te.toString() );
			this.currentProcess = "--NONE--";
		}
	}

	public String getCurrentProcess() {
		return this.currentProcess;
	}
	public static void setINITBATCH_START(){
		BATCH_START =20;
		setBatchFlag(true);
	}
	public static void setBATCH_START(int batch_start) {
		if(batch_start!=-1)
		{
			BATCH_START = 20 + batch_start;
			setBatchFlag(true);
		}else
		{
			BATCH_START=0;
			setBatchFlag(false);
		}
	}
	
	public int getBatchStart(){
		return BATCH_START;
	}
	private boolean isBatchMode() {

		calendar.setTime(new Date(System.currentTimeMillis()) );

		String dateTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
					   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ", " +
					   (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE);
		myLogger.debug("Scheduler Date/Time: " + dateTime );
		
		if(isBatchFlag())
		{
			if ( (calendar.get(Calendar.HOUR_OF_DAY) >= this.BATCH_START) ||
				(calendar.get(Calendar.HOUR_OF_DAY) <= this.BATCH_END) )
			{	
				setINITBATCH_START();
				return true;				
			}
			else{
				return false;
			}	
		}
		else{			
			if (calendar.get(Calendar.HOUR_OF_DAY) ==6)
			{	
				setINITBATCH_START();
			}
			return false;
		}
		
	}

	private void createSystemPerformanceJob(String jobName) throws TCGMException
	{
		TCGMProductionForm form = new TCGMProductionForm();
		ProcessMngr pm = new ProcessMngr();
		
		form.setJobName(jobName);
		form.setChkImmediate(true);
		form.setSelReportDest("G");
		JobInstance job = pm.createJob("-1", "-1", form);
		job.setDesc(job.getJobDef().getJobName() +  TCGMConstants.STR_SEP + "Storage Management" );
		pm.addJob(job, SQLUtil.getOracleAdmin());
	}
	
	public static boolean isBatchFlag() {
		return batchFlag;
	}
	public static void setBatchFlag(boolean flag) {
		batchFlag = flag;
	}
}
