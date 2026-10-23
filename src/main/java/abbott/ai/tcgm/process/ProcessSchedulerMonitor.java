package abbott.ai.tcgm.process;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;

public class ProcessSchedulerMonitor extends SelfRunningThread
{
	private Calendar calendar = null;
	private int BATCH_START = 19;
	private int BATCH_END = 5;
	private static final Logger myLogger = LogManager.getLogger( "abbott.ai.tcgm.process.ProcessSchedulerMonitor" );
	

	public ProcessSchedulerMonitor(int sleepInterval)
	{
		super(sleepInterval);
		calendar = Calendar.getInstance();
		this.start();
	}

	protected void runWork()
	{
		myLogger.info("ProcessSchedulerMonitor Started.");
		while ( this.noStopRequested && !Thread.currentThread().isInterrupted() )
		{
			try
			{
				Thread.sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				this.checkForJob();
				Thread.sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
				Thread.sleep(this.sleeptime/3);
				myLogger.debug("At every 50 secs, coming out of sleep and going into sleep for 5 minutes");
			}
			catch (java.lang.InterruptedException ex)
			{
				myLogger.error("Process Scheduler Monitor Interupted");
				break;
			}
		}
		myLogger.info("ProcessSchedulerMonitor Exiting.");
	}

	private void checkForJob()
	{
		try
		{
			ProcessMngr pm = new ProcessMngr();
			JobInstance job = null;
			
			calendar.setTime(new Date(System.currentTimeMillis()) );
			
			int secs = calendar.get(Calendar.SECOND);
			int mins = calendar.get(Calendar.MINUTE);
			if(secs<30){
				mins = mins-1;
			}else{
				secs = secs-30;
			}
			String dateTime = calendar.get(Calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
							  calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
							  mins + ":" + secs + ".0";
						   
			Vector processingJobs = pm.getAllProcessingJobs(SQLUtil.getOracleAdmin());
			myLogger.info("Checking for the job with status of Processing at every 5 minutes "  + dateTime);
				
			if(processingJobs.size()==0){
				Vector jobcompleteJobs = pm.getAllJobcompleteJobs(SQLUtil.getOracleAdmin());
							JobInstance jobInJStatus = null;
							String strEndTime =dateTime; 
							myLogger.debug("Size of the finished jobs vector "  + jobcompleteJobs.size());
							for(int i=0;i<jobcompleteJobs.size();i++){
								jobInJStatus = (JobInstance)(jobcompleteJobs.elementAt(i));
								if(jobInJStatus.getEndTime()!=null&&!jobInJStatus.getEndTime().trim().equals("")&&jobInJStatus.getJobStatus().getCode().equalsIgnoreCase("J")){
									strEndTime = jobInJStatus.getEndTime();
									break;
								}
							}
			
							myLogger.debug("Current System Date and Time is "  + dateTime);
							myLogger.debug("Date and Time of Recently completed  job is "  + strEndTime);
							SimpleDateFormat sdfInput = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
							Date dateCurr = sdfInput.parse( dateTime );
							Date dateEndTime = sdfInput.parse( strEndTime );
			
							if(dateCurr.after(dateEndTime)){
							myLogger.debug("current date and time minus 30 secs is greater than Last job End Date and Time.");				
													
				myLogger.info("Checking for the jobs to run by the scheduler at every 5 minutes" + dateTime);
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

		calendar.setTime(new Date(System.currentTimeMillis()) );

		String dateTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
					   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ", " +
					   (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE);
		myLogger.debug("Scheduler Date/Time: " + dateTime );

		if ( (calendar.get(Calendar.HOUR_OF_DAY) >= this.BATCH_START) ||
			(calendar.get(Calendar.HOUR_OF_DAY) <= this.BATCH_END) )
			return true;
		else
			return false;
	}
}
