package abbott.ai.tcgm.process;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;
/**
 *
 * <p>Title: </p>
 * <p>Description: ProcessSchedulerMonitor is an inner class of ProcessEngine.  This
 * class has a thread that gets started and stopped by the user.  Processing
 * by this thread consists of checking for jobs to run in the jobqueue.
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class ProcessSchedulerMonitor extends SelfRunningThread
{
	private Calendar calendar = null;
	private int BATCH_START = 19;
	private int BATCH_END = 5;
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.process.ProcessSchedulerMonitor" );
	

	public ProcessSchedulerMonitor(int sleepInterval)
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
		myLogger.info("ProcessSchedulerMonitor Started.");
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
				
				Thread.currentThread().sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.currentThread().sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.currentThread().sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				this.checkForJob();
				Thread.currentThread().sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.currentThread().sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.currentThread().sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
			}
			catch (java.lang.InterruptedException ex)
			{
				myLogger.error("Process Scheduler Monitor Interupted");
				// no problem, interrupt me.
			}
		}
		myLogger.info("ProcessSchedulerMonitor Exiting.");
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
			
			calendar.setTime(new Date(System.currentTimeMillis()) );
			
			//Deducting 30 secs to current date and time
			int secs = calendar.get(Calendar.SECOND);
			int mins = calendar.get(Calendar.MINUTE);
			if(secs<30){
				mins = mins-1;
			}else{
				secs = secs-30;
			}
			String dateTime = calendar.get(calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
							  calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
							  mins + ":" + secs + ".0";
						   
			Vector processingJobs = pm.getAllProcessingJobs(SQLUtil.getOracleAdmin());
			myLogger.info("Checking for the job with status of Processing at every 5 minutes "  + dateTime);
				
			if(processingJobs.size()==0){
				//Get all the jobs with status Jobcomplete 'J'
				Vector jobcompleteJobs = pm.getAllJobcompleteJobs(SQLUtil.getOracleAdmin());
							JobInstance jobInJStatus = null;
							String strEndTime =dateTime; //initializing with current date and time - 30 secs
							myLogger.debug("Size of the finished jobs vector "  + jobcompleteJobs.size());
							for(int i=0;i<jobcompleteJobs.size();i++){
								jobInJStatus = (JobInstance)(jobcompleteJobs.elementAt(i));
								if(jobInJStatus.getEndTime()!=null&&!jobInJStatus.getEndTime().trim().equals("")&&jobInJStatus.getJobStatus().getCode().equalsIgnoreCase("J")){
									strEndTime = jobInJStatus.getEndTime();
									break;
								}
							}
			
			
							/**
							 * Checking the Current Date and Time minus 30 seconds greater than the End Date and Time.
							 * If so check for the status of the latest job. If there are no processing jobs,
							 * some thing is wrong need to stop and start the prcoess scheduler.
							 * Process Scheduler takes 5 secs to pick next job automatically.
							 */			
							myLogger.debug("Current System Date and Time is "  + dateTime);
							myLogger.debug("Date and Time of Recently completed  job is "  + strEndTime);
							SimpleDateFormat sdfInput = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
							Date dateCurr = sdfInput.parse( dateTime );
							Date dateEndTime = sdfInput.parse( strEndTime );
			
							//
							if(dateCurr.after(dateEndTime)){
							myLogger.debug("current date and time minus 30 secs is greater than Last job End Date and Time.");				
													
				myLogger.info("Checking for the jobs to run by the scheduler at every 5 minutes" + dateTime);
				/*if ( isBatchMode() )
					job = pm.getNextJob(SQLUtil.getOracleAdmin());
				else
					job = pm.getNextImmediateJob(SQLUtil.getOracleAdmin());
								
				if (job != null)
				{
					myLogger.debug("Found job to run when the process scheduler in hung state.");
					myLogger.debug("So stopping and starting the process scheduler");
					DaemonMngr dm = DaemonMngr.getInstance();
					dm.stopProcessScheduler();
					dm.startProcessScheduler();
					myLogger.debug("Process Scheduler is restarted");
				}*/
				myLogger.info("The status of the last ran job is still in 'J' for more than 30 secs.");
				myLogger.info("Considered as hung state, so stopping and starting the process scheduler");
				DaemonMngr dm = DaemonMngr.getInstance();
				dm.stopProcessScheduler();
				dm.startProcessScheduler();
				myLogger.info("Process Scheduler is restarted");
			
			  }
			}
			
		}
		catch (TCGMException te)
		{
			myLogger.error("Exception checking for job");
			myLogger.error("Exception: " + te.toString() );
			
		}catch (Exception ex)
		{
			myLogger.error("Exception converting string to date");
			myLogger.error("Exception: " + ex.toString() );
			
		}
	}

	
	private boolean isBatchMode() {

		// update internal calendar with current time
		calendar.setTime(new Date(System.currentTimeMillis()) );

		String dateTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
					   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ", " +
					   (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE);
		myLogger.debug("Scheduler Date/Time: " + dateTime );

		// currently batch mode is less that 5 am and greater than 7pm, hard coded. Migrate to application constants
		if ( (calendar.get(Calendar.HOUR_OF_DAY) >= this.BATCH_START) ||
			(calendar.get(Calendar.HOUR_OF_DAY) <= this.BATCH_END) )
			return true;
		else
			return false;
	}
}