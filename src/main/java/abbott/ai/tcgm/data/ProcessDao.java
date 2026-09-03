package abbott.ai.tcgm.data;

//import javax.sql.*;
import java.io.File;
import java.util.Vector;

import abbott.ai.tcgm.entities.DataFeed;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.process.JobDefinition;
import abbott.ai.tcgm.process.JobInstance;

/**
<p>Title: TCGM Application</p>
<p>Description: </p>
<p>Copyright: Copyright (c) 2002</p>
<p>Company: Abbott International</p>
@author Jim Watkins
@version 1.0
*/
public interface ProcessDao // extends TCGMDao
{

  /**
   *
   * @param job
   * @return String - The jobQId of the job added.
   * @throws TCGMException
   */
  public int addJob(JobInstance job) throws TCGMException;

  /**
   *
   * @param job
   * @return
   * @throws TCGMException
   */
  public Vector getVO(JobInstance job) throws TCGMException;
  
  /**
   *
   * @param job
   * @return
   * @throws TCGMException
   */
  //Added by Udaya B Aravapalli on 02/17/2006
  //Added to build a where clause that will involve
  //the JOB_STATUS.  	
  public Vector getPendingJobsVO(JobInstance job) throws TCGMException;

  /**
   *
   * @return
   * @throws TCGMException
   */
  public JobInstance getNextImediateJob() throws TCGMException;
  public JobInstance getNextJob() throws TCGMException;

  public Vector getJobsByStatus(JobInstance.JobStatus status) throws TCGMException;
  public String getJobDesc(String jobQueId) throws TCGMException;
  /**
   *
   * @param job
   * @throws TCGMException
   */
  public void runJob(String jobQId, String modelId) throws TCGMException;
  public void runJob(String jobQId, String modelId, String strUser) throws TCGMException;
  public void deleteJob(String jobQId) throws TCGMException;
  public void deleteJobHist(String jobQId) throws TCGMException;
  public void deleteJobQueParms(String jobQId) throws TCGMException;
  public void updateJobStatus(String jobQId, JobInstance.JobStatus status) throws TCGMException;
  public JobInstance getJobInstanceById(String jobQId) throws TCGMException;
  public Vector getAllFinishedJobs() throws TCGMException;
  public Vector getCurrentlyRunningJob() throws TCGMException;
  public Vector getAllJobcompleteJobs() throws TCGMException;
  public Vector getAllNonCompleteJobs() throws TCGMException;  
  public Vector getAllPendingJobs() throws TCGMException;
  public String getJobStatus(String jobQueId) throws TCGMException;
  //01/26/2006 Udaya B Aravapalli. Need to pass the dataFeed Instance
  // so that we can retrieve all the required values in the DAO.
  public void processDataFeed(File file, DataFeed dataFeed) throws TCGMException;
  public String processAffBPCDataFeed(File file, DataFeed dataFeed) throws TCGMException;
  public void moveJobPosition(String jobQueId, int offset) throws TCGMException;
  public void runJobDirect( String jobQueId, String jobDefId, String modelId, String datasetTableId ) throws TCGMException;
  public void setJobStart(String jobQueId) throws TCGMException;
  public void setJobComplete(String jobQueId, JobInstance.JobStatus status ) throws TCGMException;
  public void setReportComplete(String jobQueId, JobInstance.JobStatus status ) throws TCGMException;
  public JobDefinition getJobDefinition(String jobName) throws TCGMException;
  public void executeJobStep( String jobStepName, String modelId, String datasetTableId, java.util.Properties props ) throws TCGMException;
  public void executeNewJobStep( String jobStepName, String modelId, String datasetTableId, java.util.Properties props ) throws TCGMException;
  public Vector selectAplnLog(String jobid) throws TCGMException;
  public Vector selectErrorLog(String jobid) throws TCGMException ;
  public void PublishRecord(int inqReportId, int modelId, String flag, String modelName) throws TCGMException;
  public void GenerateRecord(int inqReportId, int modelId, String flag, String modelName) throws TCGMException;
  public boolean processSystemCompact() throws TCGMException;
  public boolean processModelPurge() throws TCGMException;
  public String sendNetAnl(String modelId,String modelDesc) throws TCGMException;
 }