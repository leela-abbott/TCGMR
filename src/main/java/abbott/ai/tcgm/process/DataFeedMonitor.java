package abbott.ai.tcgm.process;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.DataFeed;
import abbott.ai.tcgm.entities.DataFeedLogEntry;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DataFeedMngr;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.helpers.SendEmail;
import abbott.ai.tcgm.process.javajob.JavaJob;

public class DataFeedMonitor extends SelfRunningThread {

	private ArrayList dataFeeds = new ArrayList(5);
	private String location = "";
	private static Logger logger = Logger.getLogger("TCGM.Process.DataFeedMonitor");
	private String currentFeed = "--NONE--";

	public DataFeedMonitor(int sleeptime) throws TCGMException {
		super(sleeptime);
		// Setup datafeeds. A datafeed has three constructor parameters to establish a feed.
		// Location - A String indicating the place to look for the file. Generally read from appconts/web.xml
		// Extension - A String that the filename will end in, for instants "units" for files that end with ".units"
		// Job To Run - The name of a stored procedure to execute
		// This are stored in the database and read in through a DAO. At this time they are read each
		// time the monitor starts up.

		this.dataFeeds = new DataFeedMngr().getAllDataFeeds();
		if (this.dataFeeds == null || this.dataFeeds.size() == 0)
			logger.warn("No datafeeds to process.");
		else
			this.start();
	}

	private void processFeed(DataFeed dataFeed, File appFile) {
		logger.debug("Processing feed for " + appFile.getName() );
		String sendTreeMVSParm = null;
		String fileName = null;
		boolean processFlag = false;
		boolean dataFeedLogInsertFlag = false;
		ProcessMngr pm = new ProcessMngr();
		File dbFile = null;
		DataFeedLogEntry entry = new DataFeedLogEntry();
		entry.setFile(appFile);
		TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
		long rawOffset = tz.getRawOffset();
		SendEmail sEmail=new SendEmail();
		String toAddress="";
		if (tz.useDaylightTime())
		{
			rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
		}
		if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
		{
			entry.setStartTime( new Timestamp(new Timestamp( System.currentTimeMillis()).getTime() - rawOffset));
		}
		else
		{
			entry.setStartTime(new Timestamp( System.currentTimeMillis()));
		}
		try
		{
			// Create a new file object to represent the file as viewed from the database.
			// This is often perceived as a different location since the processing code runs on the db server
			// while the monitoring code runs on the web server.
			dbFile = new File(dataFeed.getDbLocation(), appFile.getName() );
			UserToken ut = SQLUtil.getOracleAdmin();
			/********************************************************************
			 * Modified Date: 12/20/2005
			 * Modified By : Udaya B Aravapalli
			 * This will read the Paramter value(Paramter name: SENDTREEMVS) from
			 * the paramter table. The first 8 characters will be the file name,
			 * the next 2 characters will be the delimiter '::' and the last character
			 * will be a value of 0 or 1. 1 indicates the file is still not ready.
			 * 0 indicates the file is ready.
			 *
			 ********************************************************************/
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
			logger.info("Process Name :" + dataFeed.getProcessName());
			toAddress=md.getModelParm(-1, "SUPPORT_SEND_ERROR_LIST");
			if (dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_PROCESS_SEND_TREE_TO_MVS))
			{
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_SEND_TREE_TO_MVS);
			}
			else if (dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_PROCESS_INTER_COMP_TRSFR))
			{
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_INTER_COMP_TRSFR);
			}
			else if (dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_RGM))
			{
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_EXPORT_RGM_DATA);
			}
			else if (dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_RBL))
			{
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_EXPORT_RBL_DATA);
			}
			else if (dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_RBB))
			{
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_EXPORT_RBB_DATA);
			}
			else if (dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_RTC))
			{
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_EXPORT_RTC_DATA);
			}
			else if(dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_CCS)){
				
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_EXPORT_CCS_DATA);				
			}
			else if(dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_PRICING_IPS)){
				
				sendTreeMVSParm = md.getModelParm(-1, TCGMConstants.DT_EXPORT_IPS_DATA);				
			}

			if (!(sendTreeMVSParm ==null))
			{
				StringTokenizer tokens = new StringTokenizer(sendTreeMVSParm, TCGMConstants.DT_COLON_DELIMITER);
				int tokenCount = 0;
				while(tokens.hasMoreTokens())
				{
					if ((tokenCount == 0))
					{
						fileName = tokens.nextToken();
					}

					if ((tokenCount == 1) && (Integer.parseInt(tokens.nextToken()) == 0))
					{
						processFlag = true;
					}
				tokenCount++;
				}
			}

			// 12-6-05 Separate Import from Export DataFeedMonitor jobs
			String javaJobPckg = "abbott.ai.tcgm.process.javajob";
			if(dataFeed.getJobType().equals("JV") && processFlag) // this is an export and a javajob processes job
			{
				logger.debug(" This is a file in the export directory. Java Job is kicking in..");
				JobInstance instance = new JobInstance();
				// As we have to pass the file name to the Java Job , We are usinng the
				// model field in the JobInstance to do this. This is not pretty. But this
				// is one way we could effectively solve this issue. If we come up with a better
				// idea on how to handle this, this should be changed.
				
				JavaJob javaJob = (JavaJob) Class.forName(javaJobPckg + "." + dataFeed.getProcessName()).newInstance();
				
				if(dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_CCS)){
					instance.setModel(appFile.getAbsolutePath());	
					javaJob.perform(instance);
					//appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" ) );
					appFile.delete();				
				}else if(dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_PRICING_IPS)) {
					
									
					instance.setModel(appFile.getAbsolutePath());	
					javaJob.perform(instance);
					//appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" ) );
					appFile.delete();
				}else{
					instance.setModel(fileName);					
					javaJob.perform(instance);
					appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" ) );					
				}
				
				
				
				
				dataFeedLogInsertFlag = true;
				// 12/8/05 Need to execute the corresponding javajob.
			}
			else if(dataFeed.getJobType().equals("SP"))// "SP" this is an import and a store procedure processes job
			{

				/*********************************************************************************
				 * If the Procedure is "AFFBPC_LOAD" then we need to create a Job so that
				 * the Process Scheduler will run the Job(generate Reports) and call the
				 * Report Manager to do the rest to generate the Actual Report in CRN.
				 *********************************************************************************/
				if ((dataFeed.getProcessName()).equalsIgnoreCase(JobConstants.PN_AFFBPC_LOAD))
				{
					String supAffID = null;
					supAffID = pm.processAffBPCDataFeed(dbFile, dataFeed);
					appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" + TCGMUtil.getRandomDigitStr(4)  ) );

					JobInstance job = pm.createDataTRFRJob(JobConstants.PN_AFFBPC_REPORT, "-1", "-1");
					job.setDesc(job.getJobDef().getJobName()  + " (Affiliate #"+supAffID+")");
					if (supAffID != null)
					{
						job.addJobParm(JobConstants.PN_LOAD_AFF, supAffID);
					}
					pm.addJob(job,SQLUtil.getOracleAdmin());
				}
				else
				{
					//01/26/2006 Udaya B Aravapalli. Need to pass the dataFeed Instance
					// so that we can retrieve all the required values in the DAO.
					pm.processDataFeed(dbFile, dataFeed);
					appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" + TCGMUtil.getRandomDigitStr(4)  ) );
				}
				dataFeedLogInsertFlag = true;
			}

			entry.setCompletionMsg("File Processed Successfully");
		}
		catch (TCGMException tex) {
			StringWriter errors = new StringWriter();
			//tex.printStackTrace();
				tex.printStackTrace(new PrintWriter(errors));
				String errorOutput = errors.toString();
				if(errorOutput.equals("")){
					errorOutput=tex.getErrorMessage();
				}
			try{
				if(!toAddress.equals("")){
					sEmail.sendEMail(toAddress,errorOutput,"Error processing file."+dataFeed.getProcessName(),"tcgm.no-reply@abbvie.com","");
				}
			}catch(Exception myEX){
				
			}
			logger.error("Error processing data feed: " + dataFeed.toString() + ", Message: " + tex.toString() );
			appFile.renameTo(new File(appFile.getParent(), appFile.getName() + ".failed" + TCGMUtil.getRandomDigitStr(4)  ) );
			entry.setCompletionMsg("Error processing file. Message: " + tex.getErrorMessage() );
		}
		catch (Exception ex) {
			//ex.printStackTrace();
				StringWriter errors = new StringWriter();			
				ex.printStackTrace(new PrintWriter(errors));
				String errorOutput = errors.toString();
			try{
				if(!toAddress.equals("")){
					sEmail.sendEMail(toAddress,errorOutput,"Error processing file."+dataFeed.getProcessName(),"tcgm.no-reply@abbvie.com","");
				}
			}catch(Exception myEX){
				
			}
			logger.error("Error processing data feed: " + dataFeed.toString() + ", Message: " + ex.toString() );
			appFile.renameTo(new File(appFile.getParent(), appFile.getName() + ".failed" + TCGMUtil.getRandomDigitStr(4)  ) );
			entry.setCompletionMsg("Error processing file. Message: " + ex.getMessage() );
		}
		if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
		{
			entry.setEndTime( new Timestamp(new Timestamp( System.currentTimeMillis()).getTime() - rawOffset));
		}
		else
		{
			entry.setEndTime(new Timestamp( System.currentTimeMillis()));
		}
		try
		{
		  logger.debug("Important Information Start:");
		  logger.debug("Full File Name:" + entry.getFullFileName());
		  logger.debug("Completion Msg:" + entry.getCompletionMsg());
		  logger.debug("Start Time:"     + entry.getStartTime());
		  logger.debug("End Time:"       + entry.getEndTime());
		  logger.debug("Important Information End:");
			  if(dataFeedLogInsertFlag){
				new DataFeedMngr().writeLogEntry(entry);
			  }
		 }
		catch (TCGMException tex2)
		{
		  logger.error("Error writing to datafeed log: " + dataFeed.toString() + ", Message: " + tex2.getErrorMessage() );
		}
		this.currentFeed = "--NONE--";
	}

	private File getFileFromFeed(DataFeed dataFeed)
	{

		try
		{
			File[] files = dataFeed.getAsLocation().listFiles( dataFeed.getFilenameFilter() );
			if (files!=null && files.length > 0)
				return files[0];
			else
				return null;
		}
		catch(Exception ex)
		{
			logger.error("Error processing data feed: " + dataFeed.toString() + ", Message: " + ex.getMessage() );
			logger.error("DataFeedMonitor.getFileFromFeed * error * !!! ", ex);
			ex.printStackTrace();
			return null;
		}

	}


	public String getCurrentFeed() {
		return this.currentFeed;
	}

	protected void runWork() {
		this.logger.debug("Begin monitoring data feeds");
		while (noStopRequested) {
			try {
				Thread.sleep(this.sleeptime);
				Iterator item = dataFeeds.iterator();
				while (item.hasNext())
				{
					DataFeed dataFeed = (DataFeed) item.next();
					File file = this.getFileFromFeed(dataFeed);
					if (file != null)
						this.processFeed(dataFeed, file);
				}
			}
			catch (InterruptedException iex) {
				// Any caught exceptions should be reasserted for
				// any blocking statements that follow.
				Thread.currentThread().interrupt();
			}
		}
		logger.debug("Stop monitoring data feeds");
	}
}


