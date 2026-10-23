package abbott.ai.tcgm.process;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TimeZone;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
	private static final Logger logger = LogManager.getLogger("TCGM.Process.DataFeedMonitor");
	private String currentFeed = "--NONE--";

	public DataFeedMonitor(int sleeptime) throws TCGMException {
		super(sleeptime);
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
			dbFile = new File(dataFeed.getDbLocation(), appFile.getName() );
			UserToken ut = SQLUtil.getOracleAdmin();
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
			String javaJobPckg = "abbott.ai.tcgm.process.javajob";
			if(dataFeed.getJobType().equals("JV") && processFlag) 
			{
				logger.debug(" This is a file in the export directory. Java Job is kicking in..");
				JobInstance instance = new JobInstance();
				JavaJob javaJob = (JavaJob) Class.forName(javaJobPckg + "." + dataFeed.getProcessName()).newInstance();
				if(dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_FACT_CCS)){
					instance.setModel(appFile.getAbsolutePath());	
					javaJob.perform(instance);
					appFile.delete();				
				}else if(dataFeed.getProcessName().equalsIgnoreCase(TCGMConstants.DT_JOB_SEND_PRICING_IPS)) {
					instance.setModel(appFile.getAbsolutePath());	
					javaJob.perform(instance);
					appFile.delete();
				}else{
					instance.setModel(fileName);					
					javaJob.perform(instance);
					appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" ) );					
				}
				dataFeedLogInsertFlag = true;
			}
			else if(dataFeed.getJobType().equals("SP"))
			{
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
					pm.processDataFeed(dbFile, dataFeed);
					appFile.renameTo(new File(appFile.getParent(),  appFile.getName() + ".processed" + TCGMUtil.getRandomDigitStr(4)  ) );
				}
				dataFeedLogInsertFlag = true;
			}
			entry.setCompletionMsg("File Processed Successfully");
		}
		catch (TCGMException tex) {
			StringWriter errors = new StringWriter();
			tex.printStackTrace(new PrintWriter(errors));
			String errorOutput = errors.toString();
			if(errorOutput.equals("")){
				errorOutput=tex.getErrorMessage();
			}
			try{
				if(!toAddress.equals("")){
					sEmail.sendEMail(toAddress,errorOutput,"Error processing file."+dataFeed.getProcessName(),"tcgm.no-reply@abbvie.com","");
				}
			}catch(Exception myEX){}
			logger.error("Error processing data feed: " + dataFeed.toString() + ", Message: " + tex.toString() );
			appFile.renameTo(new File(appFile.getParent(), appFile.getName() + ".failed" + TCGMUtil.getRandomDigitStr(4)  ) );
			entry.setCompletionMsg("Error processing file. Message: " + tex.getErrorMessage() );
		}
		catch (Exception ex) {
			StringWriter errors = new StringWriter();			
			ex.printStackTrace(new PrintWriter(errors));
			String errorOutput = errors.toString();
			try{
				if(!toAddress.equals("")){
					sEmail.sendEMail(toAddress,errorOutput,"Error processing file."+dataFeed.getProcessName(),"tcgm.no-reply@abbvie.com","");
				}
			}catch(Exception myEX){}
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
		while (this.noStopRequested && !Thread.currentThread().isInterrupted()) {
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
				logger.error("Data Feed Monitor Interrupted");
				break;
			}
		}
		logger.debug("Stop monitoring data feeds");
	}
}
