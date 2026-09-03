package abbott.ai.tcgm.process;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ProcessDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.data.oracle.OracleDao;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;
/**
 *
 * <p>Title: </p>
 * <p>Description: ProcessScheduler is an inner class of ProcessEngine.  This
 * class has a thread that gets started and stopped by the user.  Processing
 * by this thread consists of checking for jobs to run in the jobqueue.
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class ProcessScheduler extends SelfRunningThread
{
	private Calendar calendar = null;
	public static int BATCH_START = 20;
	private static int BATCH_END = 5;
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.process.ProcessScheduler" );
	private String currentProcess = "--NONE--";
	private static boolean batchFlag=true;


	public ProcessScheduler(int sleepInterval)
	{
		super(sleepInterval);
		calendar = Calendar.getInstance();
		this.start();
	}

	/**
	 *
	 */
	protected void runWork()
	{
		myLogger.info("ProcessScheduler Started.");
		while ( this.noStopRequested )
		{
			try
			{

				/**
				 * 8-28-03 bd
				 * I think right here is the place to fix the hangup with the job scheduler. The
				 * problem appears to be that another job can't get submitted while a job is currently
				 * running; Maybe the checkForJob() method should be called by spawning another thread
				 * itself to check for the job.
				 *
				 * Or, basically the fix needs to be that the submission of a job is done via spawning
				 * a new thread. It shouldn't have to wait for the current thread to complete in order
				 * to just submit a new job.
				 */
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
				Thread.currentThread().sleep(this.sleeptime);
			}
			catch (java.lang.InterruptedException ex)
			{
				myLogger.error("Process Scheduler Interupted");
				// no problem, interrupt me.
				myLogger.error("Process Scheduler Monitor stop method invoking");
				DaemonMngr dm = DaemonMngr.getInstance();
				dm.stopProcessSchedulerMonitor();
				myLogger.error("Process Scheduler Monitor stop method executed");
			}
		}
		myLogger.info("ProcessScheduler Exiting.");
	}

	/**
	 *
	 */
	private void checkForJob()
	{
		try
		{
			// myLogger.debug("Checking for job...");
			ProcessMngr pm = new ProcessMngr();
			JobInstance job = null;
			/************************************************************
			 * Added by Udaya B Aravapalli on 01/25/2006.
			 * If there are jobs sitting in the Job Que table with a
			 * Processing state, they need to be chnaged to Unknown
			 * state, because the process Scheduler has moved on to
			 * the next job. We have no more status on this job.
			 *************************************************************/
			Vector processingJobs = pm.getAllProcessingJobs(SQLUtil.getOracleAdmin());
			for (int i=0; i< processingJobs.size();i++)
			{
				myLogger.debug(processingJobs.get(i));
				job = (JobInstance) processingJobs.get(i);
				ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(SQLUtil.getOracleAdmin());
				pd.setJobComplete(job.getJobQueId(), JobInstance.JobStatus.Unknown);
				job = null;
			}
			/************************************************************
			 * Added by Veerendra K Pesala on 05/02/2006.
			 * If there are jobs sitting in the Job Que table with a
			 * Jobcomplete state, they need to be started to process
			 * the reporting part of the job to move to status R or C.
			 *************************************************************/
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

		// update internal calendar with current time
		calendar.setTime(new Date(System.currentTimeMillis()) );

		String dateTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
					   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ", " +
					   (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE);
		myLogger.debug("Scheduler Date/Time: " + dateTime );
		
		// currently batch mode is less that 5 am and greater than 7pm, hard coded. Migrate to application constants
		//added by Rama Goshike to check whether the batchmode to start or not
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
	
	/**
	 * @return Returns the batchFlag.
	 */
	public static boolean isBatchFlag() {
		return batchFlag;
	}
	/**
	 * @param batchFlag The batchFlag to set.
	 */
	public static void setBatchFlag(boolean flag) {
		batchFlag = flag;
	}
}