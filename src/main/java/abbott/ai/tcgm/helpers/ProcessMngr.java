package abbott.ai.tcgm.helpers;

import java.io.File;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ProcessDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.DataFeed;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.process.DaemonMngr;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.process.JobDefinition;
import abbott.ai.tcgm.process.JobInstance;
import abbott.ai.tcgm.process.javajob.JavaJob;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */

public class ProcessMngr implements TCGMMngr
{
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.helpers.ProcessMngr" );

	public ProcessMngr() {}

	public int addJob(JobInstance j, UserToken ut) throws TCGMException
	{
		// validate job object
		if (ut == null || j.getModelId().equals("0") || j.getJobStatus() == null ) // this should be replaced with a more robust validation
		{
			throw new TCGMException ( this.getClass().toString(), "addJob(JobDefinition)", j.toString(), "Invalid parameters" );
		}

		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao( ut );
		return( pd.addJob(j) );
	}

	public void runNextJob()
	{
	}

	public void runJobDirect( String jobQueId, String jobCmdName, String modelId, String datasetTableId ) throws TCGMException {

		UserToken ut = SQLUtil.getOracleAdmin();
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao( ut );
		pd.runJobDirect( jobQueId, jobCmdName, modelId, datasetTableId );
	}

	public void executeJobStep ( String jobStepName, String modelId, String datasetTableId, UserToken userToken, Properties props) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao( userToken );
		pd.executeJobStep( jobStepName, modelId, datasetTableId, props );
	}
	public void executeNewJobStep ( String jobStepName, String modelId, String datasetTableId, UserToken userToken, Properties props) throws TCGMException {
			ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao( userToken );
			pd.executeNewJobStep( jobStepName, modelId, datasetTableId, props );
	}

	public void runPreCmdDirect( String jobQueId, String reportDefId ) {



	}

	public JobInstance createJob(String modelId, String datasetTableId, TCGMProductionForm form) throws TCGMException {
		String jobName = form.getJobName();
		JobInstance job = new JobInstance(JobDefinition.getJobDefFromName(jobName));
		job.getJobDef().populateDetail(); // this call retrives additional detail about the job definition from the database. Specifically we need the desc in this case

		if ( form.isChkImmediate() )
			job.setJobStatus(JobInstance.JobStatus.Immediate);
		else
			job.setJobStatus(JobInstance.JobStatus.Batch);

		job.setModelId( modelId );
		job.setDatasetTableId(datasetTableId); // not used here

		// 10-9-03 Routing steps do not apply on factor model actions
		if (!form.getJobName().equals("MODEL_CLOSE")   &&
			!form.getJobName().equals("MODEL_COMPACT") &&
			!form.getJobName().equals("MODEL_DELETE"))
		{

			// 11-21-05 Replace with code that follows.
			// add routing parameters if necessary
			// if (!form.getSelReportDest().equals("0")) // add routing information
			// {
			//  	job.setPrintOutput(true);
			//		job.addJobParm("REPORT_DEST", form.getSelReportDest() );
			//		job.addJobParm("REPORT_COPIES", form.getNumCopies() );
			// }

			/** 11-21-05 The routing info above is not used w/ ReportNet but could be
			*  leveraged later. We only add the print flag attrs to the job here.
			*  The determination whether to print the report or not is determined
			*  by inspecting the selReportDest attribute of the ReportPrintRequest
			*  in the ReportMngr class, not the printOutput attrs below.
			*/
			if (form.getSelReportDest().equals("GP")) // Generate & Print Report
			{
				job.setPrintOutput(true);
				job.addJobParm("REPORT_DEST", form.getSelReportDest() );
			}
			if (form.getSelReportDest().equals("G")) // Generate Report
			{
				job.setGenerateOutput(true);
				job.addJobParm("REPORT_DEST", form.getSelReportDest() );
			}

			// add restriction string if entered
			if ( !TCGMUtil.isEmpty( form.getRestrictionStr() ) )
			{
				job.addJobParm( JobConstants.PN_RESTRICTIONS, form.getRestrictionStr() );
			}
			else
			{
				// specifically add a single space parameter. This will get pushed into the
				// global pool and override the restriction setting left from any previous reports
				job.addJobParm( JobConstants.PN_RESTRICTIONS, " " );
			}

		}

		return job;
	}
	/*************************************************************************************
	 * @param jobName
	 * @param modelId
	 * @param datasetTableId
	 * @return JobInstance
	 * @throws TCGMException
	 * Added By : Udaya B Aravapalli
	 * Added on : 03/14/2006
	 * This method is being called by the DataFeedMonitor to create a Job.
	 **************************************************************************************/
	public JobInstance createDataTRFRJob(String jobName, String modelId, String datasetTableId) throws TCGMException {
		JobInstance job = new JobInstance(JobDefinition.getJobDefFromName(jobName));
		job.getJobDef().populateDetail(); // this call retrives additional detail about the job definition from the database. Specifically we need the desc in this case

		job.setJobStatus(JobInstance.JobStatus.Immediate);

		job.setModelId( modelId );
		job.setDatasetTableId(datasetTableId); // not used here

		// Set the JobParam specifying to Generate a report.
		job.setGenerateOutput(true);
		job.addJobParm("REPORT_DEST", JobConstants.PN_GENERATE_REPORT );

		// specifically add a single space parameter. This will get pushed into the
		// global pool and override the restriction setting left from any previous reports
		job.addJobParm( JobConstants.PN_RESTRICTIONS, " " );

		return job;
	}

	// Checks to see if a job is already pending for this model. If so, actions like
	// deleting, closing, or compacting the model will be prohibited.
	public boolean isJobPending(UserToken ut, int modelId) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		JobInstance p = new JobInstance();
		p.setModelIdInt(modelId);
	//Modified by Udaya B Aravapalli on 02/17/2006
	//Modified to build a where clause that will involve
	//the JOB_STATUS.
		Vector v = pd.getPendingJobsVO(p);
		if (v.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 *
	 * @param ut
	 * @return
	 * @throws TCGMException
	 */
	public JobInstance getNextImmediateJob(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.getNextImediateJob();
	}

	public JobInstance getNextJob(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.getNextJob();
	}

	public JobDefinition getJobDefinition(String jobName) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(SQLUtil.getOracleAdmin());
		return pd.getJobDefinition(jobName);
	}

	public JobInstance getCurrentJob(UserToken ut) throws TCGMException
	{
		JobInstance job = null;
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		Vector v = pd.getCurrentlyRunningJob();//Job with status 'P' or 'J' or 'R'
		if (!v.isEmpty() ) job = (JobInstance) v.get(0);
		return job;
	}

	public static Vector getAllFinishedJobs(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return (pd.getAllFinishedJobs() );
	}

	/**
	 *
	 * @param ut
	 * @return
	 * @throws TCGMException
	 */
	public Vector getProcessList(UserToken ut, JobInstance searchJob) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.getVO(searchJob);
	}

	public static Vector getAllPendingJobs(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.getAllPendingJobs();
	}

	public Vector getFailedJobs(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.getJobsByStatus(JobInstance.JobStatus.Failed);
	}
	
	public void PublishRecord(int inqReportId, int modelId, String flag, String modelName, UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		pd.PublishRecord(inqReportId, modelId, flag, modelName);
	}
	public void GenerateRecord(int inqReportId, int modelId, String flag, String modelName, UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		pd.GenerateRecord(inqReportId, modelId, flag, modelName);
	}

	/************************************************************
	 * Added by Udaya B Aravapalli on 01/25/2006.
	 * This method will identify all the jobs in the Job Que
	 * with a state of 'P' (Processing).This method is used
	 * to determine all the Jobs that need to be changed to
	 * a status of X(Unknown) as the Process Scheduler has
	 * moved on to the next job.
	 *************************************************************/
	public Vector getAllProcessingJobs(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.getJobsByStatus(JobInstance.JobStatus.Processing);
	}

	/************************************************************
	 * Added by Veerendra K Pesala on 05/02/2006.
	 * This method will identify all the jobs in the Job Que
	 * with a state of 'J' (Jobcomplete).This method is used
	 * to determine all the Jobs that need to be started from
	 * the earlier state.
	 *************************************************************/
	public Vector getAllJobcompleteJobs(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return (pd.getAllJobcompleteJobs() );

	}
	/************************************************************
	 * Added by Veerendra K Pesala on 05/11/2006.
	 * This method will identify all the jobs in the Job Que
	 * with a state of 'J' or 'R' (Jobcomplete).This method is used
	 * to determine all the Jobs that need to be started from
	 * the earlier state when the scheduler restarted.
	 *************************************************************/
	public Vector getAllNonCompleteJobs(UserToken ut) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return (pd.getAllNonCompleteJobs() );

	}	
	

	public void processDataFeed(File file, DataFeed dataFeed) throws TCGMException {
		// use administrative token for this functionality.
		UserToken ut = SQLUtil.getOracleAdmin();
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		//01/26/2006 Udaya B Aravapalli. Need to pass the dataFeed Instance
		// so that we can retrieve all the required values in the DAO.
		pd.processDataFeed(file, dataFeed);
	}

	public String processAffBPCDataFeed(File file, DataFeed dataFeed) throws TCGMException {
		// use administrative token for this functionality.
		UserToken ut = SQLUtil.getOracleAdmin();
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		//01/26/2006 Udaya B Aravapalli. Need to pass the dataFeed Instance
		// so that we can retrieve all the required values in the DAO.
		return pd.processAffBPCDataFeed(file, dataFeed);
	}

	public void moveJobPosition(String jobQId, UserToken ut, int offset) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		pd.moveJobPosition(jobQId, offset);
	}

	public boolean processSystemCompact(UserToken ut) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.processSystemCompact();
	}

	public boolean processModelPurge(UserToken ut) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.processModelPurge();
	}

	/**
	 *
	 * @param ut
	 * @param job
	 * @throws TCGMException
	 */
	public void runJob(UserToken ut, String jobQId) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		JobInstance job = pd.getJobInstanceById(jobQId);
		boolean blnFailed = false;
		String strJobCmd = null;
		/**
		 * Checking for the job status, if it is not in 'J', run the job and go to reporting part
		 * else directly go to reporting part go this method
		 */

		if(!(job.getJobStatus().getName().equalsIgnoreCase(JobInstance.JobStatus.Jobcomplete.getName())
		||job.getJobStatus().getName().equalsIgnoreCase(JobInstance.JobStatus.Reportprocess.getName()))){
			myLogger.debug("Executing Job Current Status: " + job.getJobStatus().getName());
			String javaJobPckg = "abbott.ai.tcgm.process.javajob";
			myLogger.debug("Executing Job with Id: " + jobQId);
			if ( job.getJobDef().getJobType() == JobDefinition.JobType.StoredProcedure )
			{
				try
				{
					strJobCmd = job.getJobDef().getCmdName().trim();
					if(strJobCmd.equals((JobDefinition.ASR_DELETE).getJobName())
					||strJobCmd.equals((JobDefinition.ASR_DLT_PART).getJobName())
					||strJobCmd.equals((JobDefinition.BPC_DELETE).getJobName())){
						//job.setJobParms(pd.getJobParms(job));
						String strUser = job.getJobParms().getProperty(JobConstants.PN_ACTUAL_USER);						
						pd.runJob(job.getJobQueId(), job.getModelId(), strUser);						
					}else{
						pd.runJob(job.getJobQueId(), job.getModelId() );
					}
						String jobStatus = pd.getJobStatus(job.getJobQueId());
						if ((jobStatus!= null) || (!jobStatus.equals("")))
						{
							if (jobStatus.equals(JobInstance.JobStatus.Failed.getCode()))
							{
								blnFailed = true;
								if (!(strJobCmd.equalsIgnoreCase(JobConstants.PN_SYSTEM_COMPACT_JOB) || strJobCmd.equalsIgnoreCase(JobConstants.PN_MODEL_PURGE_JOB)))
								{										
									DaemonMngr dm = DaemonMngr.getInstance();
									dm.stopProcessScheduler();
									dm.stopProcessSchedulerMonitor();
								}

							}
						}
				}
				catch (Exception spex)
				{
					myLogger.error("Failed to execute Stored Proc job, Exception:", spex);
					pd.setJobComplete(jobQId, JobInstance.JobStatus.Failed);
					blnFailed = true;
					if (!(strJobCmd.equalsIgnoreCase(JobConstants.PN_SYSTEM_COMPACT_JOB) || strJobCmd.equalsIgnoreCase(JobConstants.PN_MODEL_PURGE_JOB)))
					{					
						DaemonMngr dm = DaemonMngr.getInstance();
						dm.stopProcessScheduler();
						dm.stopProcessSchedulerMonitor();
					}
				}
		}
		else
		{
			try
			{
				pd.setJobStart(jobQId); // 7-17-03 bd; ts sets the start time and the status to "P" so ta the process daemon will show it as processing
				JavaJob javaJob = (JavaJob) Class.forName( javaJobPckg + "." + job.getJobDef().getCmdName() ).newInstance();
				javaJob.perform(job);
				pd.setJobComplete(jobQId, JobInstance.JobStatus.Complete);
			}
			catch (Exception fex)
			{
				myLogger.error("Failed to execute Java job, Exception:", fex);
				pd.setJobComplete(jobQId, JobInstance.JobStatus.Failed);
				blnFailed = true;
			/************************************************************
			 * Added by Udaya B Aravapalli on 01/25/2006.
			 * If the job fails, we need to stop /discontinue processing
			 * and as such we need to stop the Process Scheduler.We may need
			 * to add an additional step of notifying an Admin / <some one>
			 * later. For now, we will stop the Process Scheduler.
			 *************************************************************/
				DaemonMngr dm = DaemonMngr.getInstance();
				dm.stopProcessScheduler();
				dm.stopProcessSchedulerMonitor();
			}
		}
		}
		// 11-22-05 Generate or generate & print reports if necessary
		if(!blnFailed){
		  if (job.isPrintOutput() || job.isGenerateOutput()){
			//Chnage the job status to 'R'
			pd.setJobComplete(jobQId, JobInstance.JobStatus.Reportprocess);
				try
				{
					new ReportMngr().printReportBundle(job);
					pd.setJobComplete(jobQId, JobInstance.JobStatus.Complete);
				}
				catch(TCGMException e)
				{
					myLogger.error("Exception occured while running the report manager "+e.toString());
					myLogger.error("Setting the job status as 'E', exception occured while running report");
					pd.setJobComplete(jobQId, JobInstance.JobStatus.Exceptionreporting);
				}
		}else{
			//Change the job status to 'C'
			pd.setJobComplete(jobQId, JobInstance.JobStatus.Complete);
		}
	  }

	}

	/**
	 * Method to directly call the generate & print reports.
	 * @param ut
	 * @param job
	 * @throws TCGMException
	 */
	public String printReports(UserToken ut, String jobQId) throws TCGMException
	{
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		JobInstance job = pd.getJobInstanceById(jobQId);
		String strJobStatus = job.getJobStatus().getName();
		if(job.isPrintOutput() || job.isGenerateOutput()){
			if(strJobStatus.equalsIgnoreCase(JobInstance.JobStatus.Exceptionreporting.getName())
			||strJobStatus.equalsIgnoreCase(JobInstance.JobStatus.Complete.getName()))
			{
			try
				{
					new ReportMngr().printReportBundle(job);
					if(strJobStatus.equalsIgnoreCase(JobInstance.JobStatus.Exceptionreporting.getName())){
						pd.setReportComplete(jobQId, JobInstance.JobStatus.Complete);
						strJobStatus = "Complete";
					}
				}
				catch(TCGMException e)
				{
					myLogger.error("Exception occured while running the report manager "+e.toString());
					myLogger.error("Setting the job status as 'E', exception occured while running report");
					//pd.setJobComplete(jobQId, JobInstance.JobStatus.Exceptionreporting);
					strJobStatus = "Report Exception";
				}
			}else{
				strJobStatus = "CHK_JOB_STS";				
			}
		}
			return strJobStatus;
		
	}



	public void deleteJob(UserToken ut, String jobQId) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		ReportMngr rm = new ReportMngr();
		rm.deleteReportInstancesByJobQueId(jobQId, ut);
		// 9-12-03 Added to delete job history
		pd.deleteJobHist(jobQId);
		// 9-12-03 Added to delete job que parameters
		pd.deleteJobQueParms(jobQId);
		pd.deleteJob(jobQId);

	}

	public void retryJob(UserToken ut, String jobQId) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		pd.updateJobStatus(jobQId, JobInstance.JobStatus.Batch);
	}

	public Vector getJobLogs(UserToken ut, String procId)
		throws TCGMException {
		Vector logVector = new Vector();
		ProcessDao pd =
			DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		logVector = pd.selectAplnLog(procId);
		return logVector;

	}

	public Vector getErrorLogs(UserToken ut, String procId)
		throws TCGMException {

		Vector errorVector = new Vector();
		ProcessDao pd =
			DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		errorVector = pd.selectErrorLog(procId);
		return errorVector;
	}

	public void makeJobImmediate(UserToken ut, String jobQId) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		pd.updateJobStatus(jobQId, JobInstance.JobStatus.Immediate);
	}

	public void updateJobStatus(UserToken ut, String jobQId, JobInstance.JobStatus status) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		pd.updateJobStatus(jobQId, status);
	}
	public String sendNetAnl(UserToken ut,String modelId,String modelDesc) throws TCGMException {
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
		return pd.sendNetAnl(modelId,modelDesc);
	}
}
