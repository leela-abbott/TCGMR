package abbott.ai.tcgm.data.oracle;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.data.ProcessDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.DataFeed;
import abbott.ai.tcgm.entities.DayCounter;
import abbott.ai.tcgm.entities.ErrorLogBean;
import abbott.ai.tcgm.entities.LogReportBean;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.exception.TCGMItemNotFoundException;
import abbott.ai.tcgm.exception.TCGMUniqueExpectedException;
import abbott.ai.tcgm.helpers.ReportMngr;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.process.JobDefinition;
import abbott.ai.tcgm.process.JobInstance;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 *
 */

public class OracleProcessDao extends OracleDao implements ProcessDao
{
	private String jobParmTable;
	private String callAddJob = "{? = call TCGM.JOB_QUE_ADD (?, ?, ?, ?, ?, ?,?) }";
	private String callAddParm = "{ call TCGM.JOB_QUE_PARM_ADD (?, ?, ?) }";
	private String SORT_CLAUSE = " ORDER BY JOB_SEQUENCE, CREATE_DATETIME";
	private String jobQueTable = this.schema + ".JOB_QUE";
	private String jobQueHistTable = this.schema + ".JOB_QUE_HISTORY";
	private String jobQueParmTable = this.schema + ".JOB_QUE_PARMS";
	private String TBL_JOB = this.schema + ".JOB";
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.data.oracle.OracleProcessDao" );
	/**
	 *
	 * @param ut
	 */
	public OracleProcessDao(UserToken ut)
	{
		this.userToken = ut;
		this.setEntityTable("VW_JOB_QUE_DETAIL");
		this.jobParmTable = this.schema + ".JOB_QUE_PARMS";
	}

	public OracleProcessDao(UserToken ut, Connection conn)
	{
		this._conn = conn;
		this.userToken = ut;
		this.setEntityTable("VW_JOB_QUE_DETAIL");
		this.jobParmTable = schema + ".JOB_QUE_PARMS";
	}
	/**
	 *
	 * @param job
	 */
	protected void buildSearchList (JobInstance job)
	{
		this.searchList.clear();
		this.searchList.add(new Search(DBConst.COL_MODEL_ID , job.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_JOB_QUE_ID, job.getJobQueId(), TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		if (job.getJobStatus() != null)
		{
			this.searchList.add(new Search(DBConst.COL_JOB_STATUS,job.getJobStatus().getCode(),TCGMConstants.ORACLE_LIKE_COMPARISON,false));
		}
	}

	public JobDefinition getJobDefinition(String jobName) throws TCGMException {
		String methodName = "getJobDefinition(String jobName)";
		String parmList = "jobName: " + jobName;
		String sql = "SELECT * FROM " + this.TBL_JOB + " WHERE " + DBConst.COL_JOB_NAME + "='" + jobName + "'";
		ResultSet rs = null;
		JobDefinition jobDef = null;
		try {
			// 9-23-03 open cursors issue; after this getConnection, I have one new cursor open
			rs = this.getConnection().createStatement().executeQuery(sql);
			int rowCount = 0;
			while ( rs.next() ) {
				rowCount++;
				jobDef = JobDefinition.getJobDefFromName(jobName);
				jobDef.setDesc(rs.getString(DBConst.COL_JOB_DESC) );
				jobDef.setJobTypeString(rs.getString(DBConst.COL_JOB_TYPE) );
				jobDef.setCmdName(rs.getString(DBConst.COL_JOB_CMD) );
			}
			if ( rowCount > 1 ) throw new TCGMUniqueExpectedException(this.className,methodName,jobName, "More than one job database definition found with the same name");
			else if (rowCount < 1 ) throw new TCGMItemNotFoundException(this.className,methodName,jobName, "No job database definition was found with this name");
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parmList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeResultSet(rs);
			// 9-16-03 I think this is where my problem is with closing the connections on the Job Q page
			// 9-23-03 The SQLUtil.closeConnection(this._conn) did not work because this method is commented-out;
			//SQLUtil.closeConnection(this._conn);
			// 9-23-03 I wrote a new method closeConnectionII that actually closes a connection
			SQLUtil.closeConnectionII(this.getConnection());
		}
		return jobDef;

	}

	public void moveJobPosition(String jobQueId, int offset) throws TCGMException {
		String methodName = "moveJobPosition(String jobQueId, int offset)";
		String parameterList = "jobQueId: " + jobQueId + ", offset: " + offset;

		Vector v = this.getAllPendingJobs();
		JobInstance instance = null;
		ListIterator i = v.listIterator();
		while ( i.hasNext() ) {
			instance = (JobInstance) i.next();
			if (instance.getJobQueId().equals(jobQueId) ) {
				this.moveJob(v, instance, offset);
				break;
			}
		}

		this.resequenceJobs(v);
		this.saveJobs(v);

	}

	private void moveJob(Vector v, JobInstance instance, int offset) {
		int currentPos = v.indexOf(instance);
		v.remove(instance);
		int newpos;

		if (offset + currentPos < 0)
			newpos = 0;
		else if (offset + currentPos > v.size() )
			newpos = v.size();
		else
			newpos = offset + currentPos;

		v.insertElementAt(instance, newpos);
	}


	private void resequenceJobs(Vector v) {
		Iterator i = v.iterator();
		JobInstance instance = null;
		int seq = 1;
		while (i.hasNext()) {
			((JobInstance) i.next()).setJobQueSeqInt(seq++);
		}
	}



	private void saveJobs(Vector v) throws TCGMException {
		String methodName = "saveJobs(Vector v)";

		Iterator i = v.iterator();
		while ( i.hasNext() ) {
			this.saveJob((JobInstance) i.next());
		}
	}

	private void saveJob(JobInstance instance) throws TCGMException {
		String methodName = "saveJob(JobInstance instance)";
		String parmList = "Instance: " + instance.toShortString();

		String updateSql = "UPDATE " + this.jobQueTable + " SET " +
						   DBConst.COL_JOB_QUE_SEQ + "=?," +
						   DBConst.COL_JOB_STATUS + "=?" +
						   " WHERE " + DBConst.COL_JOB_QUE_ID + "=?";
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(updateSql);
			ps.setInt(1, Integer.parseInt( instance.getJobQueSeq() ) );
			ps.setString(2, instance.getJobStatus().getCode() );
			ps.setInt(3, Integer.parseInt( instance.getJobQueId() ) );
			ps.execute();
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parmList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closePS(ps);
			SQLUtil.closeConnection(conn);
		}
	}
	/**
	 * New method to get the currently processing job.
	 * It may be in status 'P' or 'J' or 'R'.
	 *
	 */
	public Vector getCurrentlyRunningJob() throws TCGMException
	{
		String methodName = "getAllFinishedJobs()";
		String processing = JobInstance.JobStatus.Processing.getCode();
		String jobcomplete = JobInstance.JobStatus.Jobcomplete.getCode();
		String reportprocess = JobInstance.JobStatus.Reportprocess.getCode();

		String query = "SELECT * FROM " + getEntity() +  " WHERE " + DBConst.COL_JOB_STATUS + "='" + processing
						+ "' OR " + DBConst.COL_JOB_STATUS + "='" + jobcomplete
						+ "' OR " + DBConst.COL_JOB_STATUS + "='" + reportprocess
						+ "' ORDER BY " + DBConst.COL_JOB_QUE_START_TIME + " DESC";

		return( this.getJobsVO(query) );
	}
	public Vector getAllFinishedJobs() throws TCGMException
	{
		String methodName = "getAllFinishedJobs()";
		String complete = JobInstance.JobStatus.Complete.getCode();
		String failed = JobInstance.JobStatus.Failed.getCode();
		String exceptionreporting = JobInstance.JobStatus.Exceptionreporting.getCode();
		String unknown = JobInstance.JobStatus.Unknown.getCode();

		String query = "SELECT * FROM " + getEntity() +  " WHERE (" + DBConst.COL_JOB_STATUS + "='" + complete
					   + "' OR " + DBConst.COL_JOB_STATUS + "='" + failed
					   + "' OR " + DBConst.COL_JOB_STATUS + "='" + exceptionreporting
					   + "' OR " + DBConst.COL_JOB_STATUS + "='" + unknown
					   +"') AND "+ DBConst.COL_JOB_QUE_CREATE_DATETIME+" >= sysdate-"+AppConst.completedJobView
					   + " ORDER BY " + DBConst.COL_JOB_QUE_END_TIME + " DESC";

		return( this.getJobsVct(query) );
	}
	public Vector getAllJobcompleteJobs() throws TCGMException
	{
		String methodName = "getAllJobcompleteJobs()";
		String jobcomplete = JobInstance.JobStatus.Jobcomplete.getCode();
		String query = "SELECT * FROM " + getEntity() +  " WHERE " + DBConst.COL_JOB_STATUS + "='" + jobcomplete + "' ORDER BY " + DBConst.COL_JOB_QUE_END_TIME + " DESC";

		return( this.getJobsVct(query) );
	}

	public Vector getAllNonCompleteJobs() throws TCGMException
		{
			String methodName = "getAllJobcompleteJobs()";
			String jobcomplete = JobInstance.JobStatus.Jobcomplete.getCode();
			String reportprocess = JobInstance.JobStatus.Reportprocess.getCode();
			String query = "SELECT * FROM " + getEntity() +  " WHERE "
				+ DBConst.COL_JOB_STATUS + "='" + jobcomplete
				+ "' OR " + DBConst.COL_JOB_STATUS + "='" + reportprocess
				+ "' ORDER BY " + DBConst.COL_JOB_QUE_END_TIME + " DESC";

			return( this.getJobsVct(query) );
		}
	public Vector getAllPendingJobs() throws TCGMException
	{
		String methodName = "getAllPendingJobs()";
		String batch = JobInstance.JobStatus.Batch.getCode();
		String immediate = JobInstance.JobStatus.Immediate.getCode();

		String query = "SELECT * FROM " + getEntity() +  " WHERE " + DBConst.COL_JOB_STATUS + "='" + batch + "' OR " + DBConst.COL_JOB_STATUS + "='" + immediate + "' ORDER BY " + DBConst.COL_JOB_QUE_SEQ + ", " + DBConst.COL_CREATE_DATETIME;

		return( this.getJobsVct(query) );
	}

	public void setJobStart(String jobQId) throws TCGMException {
		String methodName = "setJobStartTime(String jobQId";
		String parameterList = "jobQId: " + jobQId;
		// Parameters: JobQId, Status Code, Time Code (S for start, other for end), User
		String jobQueUpdateCall = "{call TCGM.JOB_QUE_UPDATE (?, ?, ?, ?) }";
		Connection conn = this.getConnection();
		CallableStatement cs = null;
		try {
			cs = conn.prepareCall(jobQueUpdateCall);
			cs.setInt(1, Integer.parseInt(jobQId) );
			cs.setString(2, JobInstance.JobStatus.Processing.getCode() );
			cs.setString(3, "S"); // update job start time
			cs.setString(4, "TCGM");
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(setJobStart):" + retValue);
		}

		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}

	public void setJobComplete(String jobQId, JobInstance.JobStatus status) throws TCGMException {
		String methodName = "setJobStartTime(String jobQId";
		String parameterList = "jobQId: " + jobQId;
		// Parameters: JobQId, Status Code, Time Code (S for start, other for end), User
		String jobQueUpdateCall = "{call TCGM.JOB_QUE_UPDATE (?, ?, ?, ?) }";
		Connection conn = this.getConnection();
		CallableStatement cs = null;
		try {
			cs = conn.prepareCall(jobQueUpdateCall);
			cs.setInt(1, Integer.parseInt(jobQId) );
			cs.setString(2, status.getCode() );
			cs.setString(3, "E"); // update job end time
			cs.setString(4, "TCGM");
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(setJobComplete):" + retValue);
		}

		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}
	public void setReportComplete(String jobQId, JobInstance.JobStatus status) throws TCGMException {
			String methodName = "setReportComplete(jobQId, status)";
			String parameterList = "jobQId: " + jobQId;
			String updateSql = "UPDATE " + this.jobQueTable + " SET " +
								   DBConst.COL_JOB_STATUS + "=?" +
								   " WHERE " + DBConst.COL_JOB_QUE_ID + "=?";
			PreparedStatement ps = null;
			Connection conn = null;
			try {
				conn = this.getConnection();
				ps = conn.prepareStatement(updateSql);
				ps.setString(1, status.getCode() );
				ps.setInt(2, Integer.parseInt(jobQId) );
				ps.execute();
			}
			catch (SQLException sqe)
			{
				throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
			}
			finally
			{
				SQLUtil.closePS(ps);
				SQLUtil.closeConnection(conn);
			}

		}
	public String getJobStatus(String jobQueId) throws TCGMException
	 {
	   String methodName = "getJobStatus(String)";
	   String parameterList = "Job Que ID: " + jobQueId;
	   String jobStatus="";

	   try
	   {
		 String sql = "SELECT " + DBConst.COL_JOB_STATUS + " FROM " + this.getEntity()
					+ " WHERE " + DBConst.COL_JOB_QUE_ID + " = '" + jobQueId + "'";

		 this.initRS(sql, TCGMConstants.CACHED_ROWSET );
		 this.rs.execute();

		 if ( this.rs.next() )
		 {
		   jobStatus = this.rs.getString(DBConst.COL_JOB_STATUS);
		 }
		 else
		 {
		   throw new TCGMItemNotFoundException(this.className,methodName, parameterList);
		 }

		 return jobStatus;

	   }
	   catch(SQLException sqle)
	   {
		 throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	   }
	   finally
	   {
		 SQLUtil.closeRowSet(rs);
	   }
	 }

	//06/01/2006 Udaya B Aravapalli. This method is added
	// to get the Job Desc. This will be used when creating
	// a Cognos Job.

	public String getJobDesc(String jobQueId) throws TCGMException
	 {
	   String methodName = "getJobDesc(String)";
	   String parameterList = "Job Que ID: " + jobQueId;
	   String jobDesc="";

	   try
	   {
		 String sql = "SELECT " + DBConst.COL_JOB_QUE_DESC +  " FROM "  + this.getEntity()
					+ " WHERE " + DBConst.COL_JOB_QUE_ID   + " = '" + jobQueId + "'";

		 this.initRS(sql, TCGMConstants.CACHED_ROWSET );
		 this.rs.execute();

		 if ( this.rs.next() )
		 {
		   jobDesc = this.rs.getString(DBConst.COL_JOB_QUE_DESC) + " (JobQueId: " + jobQueId + ")";
		 }
		 else
		 {
		   throw new TCGMItemNotFoundException(this.className,methodName, parameterList);
		 }

		 return jobDesc;

	   }
	   catch(SQLException sqle)
	   {
		 throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	   }
	   finally
	   {
		 SQLUtil.closeRowSet(rs);
	   }
	 }

	//01/26/2006 Udaya B Aravapalli. Need to pass the dataFeed Instance
	// so that we can retrieve all the required values in the DAO.
	public void processDataFeed(File file, DataFeed dataFeed) throws TCGMException {
		String procName = dataFeed.getProcessName();
		String methodName = "processDataFeed(File file, String procName)";
		String parameterList = "file: " + file.toString() + ", procName: " + procName;
		String processCall = "{call " + this.schema + "." + procName + "(?, ?, ?) }";
		// Parameters for call -- File directory, File Name, User id
		CallableStatement cs = null;
		Connection conn = null;
		try {
			conn = this .getConnection();
			cs = this.getConnection().prepareCall(processCall);
			logger.debug("Preparing statement: " + processCall);
			cs.setString(1, dataFeed.getDbLoc() ); // Get the path from the database
			logger.debug("Processing directory: " + dataFeed.getDbLoc() );
			cs.setString(2, file.getName() );
			logger.debug("Processing file: " + file.getName() );
			cs.setString(3, this.userToken.getUserid() );
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(processDataFeed):" + retValue);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}

	//03/27/2006 Udaya B Aravapalli. A new method is added
	// to specifically process the AFFBPC DataFeed.
	public String processAffBPCDataFeed(File file, DataFeed dataFeed) throws TCGMException {
		String procName = dataFeed.getProcessName();
		String methodName = "processAffBPCDataFeed(File file, String procName)";
		String parameterList = "file: " + file.toString() + ", procName: " + procName;
		String processCall = "{call " + this.schema + "." + procName + "(?, ?, ?, ?) }";
		// Parameters for call -- File directory, File Name, User id, SupAffID.
		CallableStatement cs = null;
		Connection conn = null;
		String supAffID = null;
		try {
			conn = this.getConnection();
			cs = conn.prepareCall(processCall);
			logger.debug("Preparing statement: " + processCall);
			cs.setString(1, dataFeed.getDbLoc() ); // Get the path from the database
			logger.debug("Processing directory: " + dataFeed.getDbLoc() );
			cs.setString(2, file.getName() );
			logger.debug("Processing file: " + file.getName() );
			cs.setString(3, this.userToken.getUserid() );
			cs.registerOutParameter(4, Types.CHAR);
			boolean retValue = cs.execute();
			supAffID = cs.getString(4).trim();
			logger.debug("Sup Aff in DFM is :" + supAffID + supAffID.length());
			myLogger.debug("Return Boolean Value from Stored Proc(processDataFeed):" + retValue);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
		return supAffID;
	}

	/**
	 *
	 * @param jobQId
	 * @return
	 * @throws TCGMException
	 */
	public JobInstance getJobInstanceById(String jobQId) throws TCGMException
	{
		String methodName = "getJobById(jobQId)";
		String parmList = "Job Que Id: " + jobQId;
		JobInstance job = new JobInstance();
		job.setJobQueId(jobQId);
		Vector v = this.getVO(job);
		if (v.size() == 0)
		{
			throw new TCGMItemNotFoundException(this.className, methodName, parmList);
		}
		else if (v.size() > 1)
		{
			throw new TCGMUniqueExpectedException(this.className, methodName, parmList );
		}
		else
		{
			return (JobInstance) v.firstElement();
		}
	}

	/**
	 *
	 * @param jobQId
	 * @param modelId
	 * @throws TCGMException
	 */
	public void runJob(String jobQId, String modelId) throws TCGMException
	{
		String methodName = "runJob(jobQId, modelId)";
		String parameterList = "jobQId: " + jobQId + ", modelId: " + modelId;
		Connection conn = null;

		String sql = "{call " + schema + ".JOB_CONTROL ( ?, ? ) }";
		CallableStatement cs = null;
		try
		{
			conn = this.getConnection();
			cs = conn.prepareCall(sql);
			cs.setInt(1, Integer.parseInt(jobQId) );
			cs.setInt(2, Integer.parseInt(modelId) );
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(runJob):" + retValue);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}
	/**
	 *
	 * @param jobQId
	 * @param modelId
	 * @param strUser
	 * @throws TCGMException
	 */
	public void runJob(String jobQId, String modelId, String strUser) throws TCGMException
	{
		String methodName = "runJob(jobQId, modelId)";
		String parameterList = "jobQId: " + jobQId + ", modelId: " + modelId+ ", userName: " + strUser;
		Connection conn = null;
			String sql = "{call " + schema + ".JOB_CONTROL ( ?, ?,?,? ) }";
			int intDatasetTableId = -1;
		CallableStatement cs = null;
		try
		{
			conn = this.getConnection();
			cs = conn.prepareCall(sql);
			cs.setInt(1, Integer.parseInt(jobQId) );
			cs.setInt(2, Integer.parseInt(modelId) );
			cs.setInt(3, intDatasetTableId );
			cs.setString(4, strUser);
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(runJob):" + retValue);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}

	// this method is to be used with care, as it loads the global pool with parameters and then calls a job step that normally
	// would be called by high level PL/SQL job command. It is necessary to support the occasional java job that needs some
	// calculation or extraction performed before transmitting data.

	public void executeJobStep( String jobStepName, String modelId, String datasetTableId, java.util.Properties props ) throws TCGMException {
		String methodName = "executeJobStep( String jobStepName, String modelId, String datasetTableId, java.util.Properties props )";
		String parameterList = "jobStepName: " + jobStepName + ", modelId: " + modelId + ", datasetTableId: " + datasetTableId + ", Properties: " + props.toString();
		CallableStatement cs = null;
		Connection conn = null;

		try {

			this.pushParameters(props, "-1", "-1"); // set any parameters necessary in the global pool.
//			String sql = "{ call " + this.schema + "." + jobStepName + "(?, ?, ?) };";
			String sql = "{ call " + this.schema + "." + jobStepName + "(?, ?, ?) }";
			conn = this.getConnection();
			cs = conn.prepareCall(sql);
			cs.setInt(1,  Integer.parseInt(modelId) );
			cs.setInt(2,  Integer.parseInt(datasetTableId) );
			cs.setString(3,  this.userToken.getUserid() );
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(executeJobStep):" + retValue);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}
//	this method is to be used with care, as it loads the global pool with parameters and then calls a job step that normally
	 // would be called by high level PL/SQL job command. It is necessary to support the occasional java job that needs some
	 // calculation or extraction performed before transmitting data.
	public void executeNewJobStep( String jobStepName, String modelId, String datasetTableId, java.util.Properties props ) throws TCGMException {
			String methodName = "executeNewJobStep( String jobStepName, String modelId, String datasetTableId, java.util.Properties props )";
			String parameterList = "jobStepName: " + jobStepName + ", modelId: " + modelId + ", datasetTableId: " + datasetTableId + ", Properties: " + props.toString();
			CallableStatement cs = null;
			Connection conn = null;

			try {

				this.pushParameters(props, "-1", "-1"); // set any parameters necessary in the global pool.
//				String sql = "{ call " + this.schema + "." + jobStepName + "(?, ?, ?) };";
				String sql = "{ call " + this.schema + "." + jobStepName + "(?, ?, ?, ?, ?) }";
				conn = this.getConnection();
				cs = conn.prepareCall(sql);
				cs.setInt(1,  Integer.parseInt(modelId) );
				cs.setInt(2,  Integer.parseInt(datasetTableId) );
				cs.setString(3,  (String) props.get( JobConstants.PN_ESSBASE_START_PERIOD ) );
				cs.setString(4,  (String) props.get( JobConstants.PN_ESSBASE_END_PERIOD ) );
				cs.setString(5,  this.userToken.getUserid() );
				boolean retValue = cs.execute();
				myLogger.debug("Return Boolean Value from Stored Proc(executeJobStep):" + retValue);
			}
			catch (SQLException sqe)
			{
				throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
			}
			finally
			{
				SQLUtil.closeCS(cs);
				SQLUtil.closeConnection(conn);
			}
		}

	// Probably no longer necessary.
	public void runJobDirect( String jobQueId, String jobName, String modelId, String datasetTableId ) throws TCGMException {
		String methodName = "runJobDirect(jobQId, jobName, modelId, datasetTableId)";
		String parameterList = "jobQId: " + jobQueId + ", jobDefId: " + jobName +  ", modelId: " + modelId + ", datasetTableId: " + datasetTableId;

		// Declare SQL objects
		Connection conn = null;
		CallableStatement cs = null;
		PreparedStatement ps = null;

		try {
			conn = this.getConnection();

			// First retrieve id of job for jobCmdName
			String psSql = "SELECT " + DBConst.COL_JOB_ID + " FROM " + this.TBL_JOB + " WHERE " + DBConst.COL_JOB_NAME + " ='" + jobName + "'";
			myLogger.debug("Retrieving Job Id, SQL: " + psSql);
			ps = conn.prepareStatement(psSql);
			ResultSet rs = ps.executeQuery();
			int jobId = 0;
			while ( rs.next() )
				jobId = rs.getInt(0);
			rs.close();

			myLogger.debug("Job Id Retrieved, Value: " + jobId);
			// Parameters JobQId, JobDefId, ModelId, DatasetTableId
			String sql = "{call TCGM.JOB_CONTROL_BASIC (?, ?, ?, ?) }";

			cs = conn.prepareCall(sql);
			cs.setInt(1, Integer.parseInt(jobQueId) );
			cs.setInt(2, jobId );
			cs.setInt(3, Integer.parseInt(modelId) );
			cs.setInt(4, Integer.parseInt(datasetTableId) );

			myLogger.debug("Calling job control with parameters: " + parameterList);
			boolean retValue = cs.execute();
			myLogger.debug("Return Boolean Value from Stored Proc(runJobDirect):" + retValue);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closePS(ps);
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}
	}


	/**
	 *
	 * @param status
	 * @return
	 * @throws TCGMException
	 */
	public Vector getJobsByStatus( JobInstance.JobStatus status ) throws TCGMException
	{
		String methodName = "getJobsByStatus(JobDefinition.JobStatus)";
		String parameterList = "Status: " + status;

		JobInstance searchJob = new JobInstance();
		searchJob.setJobStatus(status);
		return( this.getVO(searchJob) );
	}

	/**
	 *
	 * @param jobQId
	 * @throws TCGMException
	 */
	public void deleteJob(String jobQId) throws TCGMException
	{
		String methodName = "deleteJob(long jobQId)";
		String parameterList = "Job Q Id: " + jobQId;
		Statement s = null;
		Connection con = null;
		try
		{
			String sql = "DELETE FROM " + this.jobQueTable + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQId;
			con = this.getConnection();
			s = con.createStatement();
			s.execute(sql);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(con);
		}
	}

// 9-12-03 Added to delete job history
	public void deleteJobHist(String jobQId) throws TCGMException
	{
		String methodName = "deleteJobHist(long jobQId)";
		String parameterList = "Job Q Id: " + jobQId;
		Statement s = null;
		Connection con = null;
		try
		{
			String sql = "DELETE FROM " + this.jobQueHistTable + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQId;
			con = this.getConnection();
			s = con.createStatement();
			s.execute(sql);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(con);
		}
	}

// 9-16-03 Added to delete job history
	public void deleteJobQueParms(String jobQId) throws TCGMException
	{
		String methodName = "deleteJobQueParms(String jobQId)";
		String parameterList = "Job Q Id: " + jobQId;
		Statement s = null;
		Connection con = null;
		try
		{
			String sql = "DELETE FROM " + this.jobQueParmTable + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQId;
			con = this.getConnection();
			s = con.createStatement();
			s.execute(sql);
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(con);
		}
	}
	/**
	 *
	 * @return
	 * @throws TCGMException
	 */
	public JobInstance getNextImediateJob() throws TCGMException
	{
		String methodName = "getNextImediateJob()";
		JobInstance job = null;

		Vector v = this.getJobsByStatus( JobInstance.JobStatus.Immediate );
		if (!v.isEmpty()) job = (JobInstance) v.get(0);
		{
			return job;
		}
	}


	public JobInstance getNextJob() throws TCGMException
	{
		String methodName = "getNextJob()";
		JobInstance job = null;

		Vector v = this.getAllPendingJobs();
		// 8-30-05 Per the ticket to stop the job scheduler if an error is encounter,
		// my change may go right here. This is where I will do my check.
		if (!v.isEmpty()) job = (JobInstance) v.get(0);
		{
			return job;
		}
	}

	private Vector getJobsVO(String query) throws TCGMException {
		String methodName = "getJobsVO(query)";
		String parameterList = "Query: " + query;
		Vector v = new Vector();

		// 9-16-03 Get a Connection object for the getJobInstanceFromCurrentRow() method
		//         This Connection object is used on the call to def.populateDetail( conn ) in
		//         the getJobInstanceFromCurrentRow() method. This chang is required to fix
		//         the problem of too many open cursors because a connection was being initiated
		//         for each job in the Job Q Management page as the result set was being "walked"
		//         via the populateDetail() method. Not only, was too many cursors being opened,
		//         but none of them were being closed.
		//Connection conn = this.getConnection();
		try {
			this.initRS(query, TCGMConstants.CACHED_ROWSET);
			this.rs.execute();
			while ( this.rs.next() )
			{
				v.add ( this.getJobInstanceFromCurrentRow() );

			}
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
			// 9-16-03 DB connection added here to remove any open cursors on the Oracle DB
			//SQLUtil.closeConnection(conn);
		}
		return v;
	}

	// New method getJobsVct(String qry) created to fetch the records related to jobs
	// running, finished, scheduled etc. In this method feching the basic details to
	//display the record details rather than total details, to minimize the response time.
	private Vector getJobsVct(String query) throws TCGMException {
		String methodName = "getJobsVct(query)";
		String parameterList = "Query: " + query;
		Vector v = new Vector();

		try {
			this.initRS(query, TCGMConstants.CACHED_ROWSET);
			this.rs.execute();
			JobInstance job = null;
			TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
			long rawOffset = tz.getRawOffset();
			long time;
			long newTime;
			Date newDate;
			SimpleDateFormat sdf = new SimpleDateFormat(AppConst.getTcgmDateFormat());
			//if (tz.useDaylightTime())
			if(tz.inDaylightTime(new Date()))
			{
				rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
			}
			
			while ( this.rs.next() )
			{
				job = new JobInstance();
				job.setJobQueId(rs.getString(DBConst.COL_JOB_QUE_ID));
				job.setDesc(rs.getString(DBConst.COL_JOB_QUE_DESC) );
				Timestamp timeStamp = this.rs.getTimestamp(DBConst.COL_JOB_QUE_END_TIME);
				
				if (!(timeStamp == null))
				{
					time = timeStamp.getTime();
					newTime = time;
					if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
					{
						newTime += rawOffset;
					}
					newDate = new Date (newTime);
				
					job.setEndTime(sdf.format(newDate));
				}
				else
				{
					job.setEndTime(" ");
				}

				timeStamp = this.rs.getTimestamp(DBConst.COL_JOB_QUE_CREATE_DATETIME);
				if (!(timeStamp == null))
				{
					time = timeStamp.getTime();
					newTime = time;
					if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
					{
						newTime += rawOffset;
					}
					newDate = new Date (newTime);
				
					job.setSubmitTime(sdf.format(newDate));
				}
				else
				{
					job.setSubmitTime(" ");
				}

				job.setJobStatusByCode( this.rs.getString(DBConst.COL_JOB_STATUS));
				job.setUserSubmitted( this.rs.getString(DBConst.COL_JOB_QUE_CREATE_USERNAME));
				//job.setModel(this.rs.getString(DBConst.COL_MODEL_NAME));
				v.add (job);

			}
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
		}
		return v;
	}

	/**
	 *
	 * @param job
	 * @return
	 * @throws TCGMException
	 */
	public Vector getVO(JobInstance job) throws TCGMException
	{
		String methodName = "getJobsVO(JobInstance)";
		String parameterList = "Process: " + job.toString();
		this.buildSearchList(job);

		String sql = "SELECT * FROM " + getEntity() + this.genWhereClause() + this.SORT_CLAUSE;
		return this.getJobsVO(sql);
	}
	/**
	 *
	 * @param job
	 * @return
	 * @throws TCGMException
	 */
	//Added by Udaya B Aravapalli on 02/17/2006
	//Added to build a where clause that will involve
	//the JOB_STATUS.

	public Vector getPendingJobsVO(JobInstance job) throws TCGMException
	{
		String methodName = "getPendingJobsVO(JobInstance)";
		String batch = JobInstance.JobStatus.Batch.getCode();
		String immediate  = JobInstance.JobStatus.Immediate.getCode();
		String processing = JobInstance.JobStatus.Processing.getCode();
		this.buildSearchList(job);
		String query = "SELECT * FROM " + getEntity() + this.genWhereClause() + " AND " +
						DBConst.COL_JOB_STATUS + " in ('" + batch + "' , '"  + immediate + "' , '"  + processing + "') ORDER BY " + DBConst.COL_JOB_QUE_SEQ + ", " + DBConst.COL_CREATE_DATETIME;

		return( this.getJobsVO(query) );
	}

	/**
	 *
	 * @return
	 * @throws SQLException
	 * @throws TCGMException
	 */
	private JobInstance getJobInstanceFromCurrentRow() throws SQLException, TCGMException
	//private JobInstance getJobInstanceFromCurrentRow(Connection conn) throws SQLException, TCGMException
	{
		String methodName = "getJobInstanceFromCurrentRow";
		//String parameterList = "Connection: " + conn.toString();
		String jobName = this.rs.getString(DBConst.COL_JOB_NAME);
		JobDefinition def = null;
		TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
		long rawOffset = tz.getRawOffset();
		long time;
		long newTime;
		Date newDate;
		SimpleDateFormat sdf = new SimpleDateFormat(AppConst.getTcgmDateFormat());
		//if (tz.useDaylightTime())
		if(tz.inDaylightTime(new Date()))
		{
			rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
		}

		try
		{
			def = JobDefinition.getJobDefFromName(jobName);
			// 9-16-03 Use an existing Connection object here. This will prevent multiple connection
			//         objects from being created when all of the jobs in the jobq are being traversed.
			//         This will prevent the "maximum open cursors" error as well.
			def.populateDetail( this.getConnection() );
			//SQLUtil.closeConnection(this.getConnection());
		}
		catch (Exception e)
		{
			myLogger.error("An Exception Occured While getting the JOB Instance" + e.getMessage());
			//throw new TCGMException( this.className, methodName, jobName, e.toString() );
		}
		finally
		{
			// 9-16-03 DB connection added here to remove any open cursors on the Oracle DB
			SQLUtil.closeConnection(this.getConnection());
		}
		JobInstance job = new JobInstance(def);

		job.setModel(this.rs.getString(DBConst.COL_MODEL_NAME));
		job.setModelIdInt( this.rs.getInt(DBConst.COL_MODEL_ID) );
		job.setDesc(this.rs.getString(DBConst.COL_JOB_QUE_DESC) );
		job.setDatasetTableIdInt(this.rs.getInt(DBConst.COL_DATASET_TABLE_ID));
		job.setJobQueId( Long.toString(this.rs.getLong(DBConst.COL_JOB_QUE_ID) ) );
		job.setJobStatusByCode( this.rs.getString(DBConst.COL_JOB_STATUS));
		job.setUserSubmitted( this.rs.getString(DBConst.COL_JOB_QUE_CREATE_USERNAME));

		// 8-6-03 bd; Modified to add the job's end time stamp to the completed job list on the
		//            Job Q Management page. This will help users distinguish between identical job
		//            names on the page.
		Timestamp timeStamp = this.rs.getTimestamp(DBConst.COL_JOB_QUE_END_TIME);

		if (!(timeStamp == null))
		{
			time = timeStamp.getTime();
			newTime = time;
			if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
			{
				newTime += rawOffset;
			}
			newDate = new Date (newTime);
			job.setEndTime(sdf.format(newDate));
		}
		else
		{
			job.setEndTime(" ");
		}
		timeStamp = this.rs.getTimestamp(DBConst.COL_JOB_QUE_START_TIME);
		if (!(timeStamp == null))
		{
			time = timeStamp.getTime();
			newTime = time;
			if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
			{
				newTime += rawOffset;
			}
			newDate = new Date (newTime);
				
			//System.out.println("Date" + sdf.format(newDate));
			job.setStartTime(sdf.format(newDate));
		}
		else
		{
			job.setStartTime(" ");
		}

		if (this.rs.getString(DBConst.COL_JOB_QUE_PRINT).equalsIgnoreCase("N"))
			job.setPrintOutput(false);
		else
			job.setPrintOutput(true);

		if (this.rs.getString(DBConst.COL_JOB_QUE_GENERATE_REPORT).equalsIgnoreCase("N"))
			job.setGenerateOutput(false);
		else
			job.setGenerateOutput(true);

		job.setJobParms( this.getJobParms(job) );


		return job;
	}

	/**
	 *
	 * @param job
	 * @return
	 * @throws TCGMException
	 */
	private Properties getJobParms(JobInstance job) throws TCGMException
	{
		String methodName = "getJobParms(JobDefinition job)";
		String parameterList = "Job: " + job.toShortString();

		Properties parms = new Properties();
		Connection con = null;
		Statement s = null;
		String sql = "SELECT " + DBConst.COL_PARM_NAME + ", " + DBConst.COL_PARM_VALUE + " FROM " + this.jobParmTable + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + job.getJobQueId();

		try
		{
			con = this.getConnection();
			s = con.createStatement();
			ResultSet jobParms = s.executeQuery(sql);
			while ( jobParms.next() )
			{
				parms.put( jobParms.getString(DBConst.COL_PARM_NAME), jobParms.getString(DBConst.COL_PARM_VALUE) );
			}
			jobParms.close();
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(con);
		}

		return parms;
	}

	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	public void updateJobStatus(String jobQId, JobInstance.JobStatus status) throws TCGMException
	{
		String methodName = "updateJobStatus(long jobQId, JobInstance.JobStatus status)";
		String parameterList = "Process Id: " + jobQId + ", Status: " + status;
		Connection c = null;
		Statement s = null;

		try
		{
			c = this.getConnection();
			String sql = "UPDATE " + this.jobQueTable + " SET " + DBConst.COL_JOB_STATUS +
						 "='" + status.getCode() + "' WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQId;
			parameterList += "\nSQL: " + sql;
			s = c.createStatement();
			s.execute(sql);

		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(c);
		}
	}

	/**
	 *
	 * @param job
	 * @throws TCGMException
	 */
	public int addJob(JobInstance job) throws TCGMException
	{
		String methodName = "addJob";
		String parameterList = "Process: " + job.toString();
		Connection conn = null;
		CallableStatement csJob = null;
		CallableStatement csParm = null;
		try
		{
			
			//As per the new requirment every report should have its model desc on the name
			this.initRS("select MODEL_DESC from " + this.schema + ".MODEL where MODEl_ID = " + job.getModelId(), TCGMConstants.CACHED_ROWSET );
			this.rs.execute();
			this.rs.next();
			String strModelDesc = rs.getString("MODEL_DESC");
			
			job.addJobParm("MODEL_DESC",strModelDesc);
			
			// 9-16-03 Troy, removed the dataset_table_id from the JOB table
			//this.initRS("select dataset_table_id, job_id from " + this.schema + ".job where job_name='" + job.getJobDef().getJobName() + "'", TCGMConstants.CACHED_ROWSET );
			this.initRS("select job_id from " + this.schema + ".job where job_name='" + job.getJobDef().getJobName() + "'", TCGMConstants.CACHED_ROWSET );
			this.rs.execute();
			this.rs.next();
			int job_id = rs.getInt("job_id");

			// 9-16-03 The datset_id variable is not being used anywhere in this object. Just
			//         keep this commented out permanently and see if I get any errors
			//int dataset_id = rs.getInt("dataset_table_id");

			//Just copy description from base jobs, may decide to modify on a per job basis later.

			conn = this.getConnection();
			if ( job.isParamaterized() )
			{
				conn.setAutoCommit(false);
			}

			csJob = conn.prepareCall(this.callAddJob);
			csJob.registerOutParameter(1, Types.INTEGER);
			csJob.setInt(2, job_id );
			csJob.setInt(3, Integer.parseInt( job.getModelId() ) );
			csJob.setString(4, job.getJobStatus().getCode() );
			csJob.setString(5, ( job.getDesc().length() > 50 ? job.getDesc().substring(0,50) : job.getDesc() ) );
			csJob.setString(6, job.isPrintOutput() ? "Y" : "N" );
			csJob.setString(7, job.isGenerateOutput() ? "Y" : "N" );
			csJob.setString(8, this.updColDefault(this.userToken.getUserid().trim(),"TCGM") );

			csJob.executeUpdate();
			int jobQId = csJob.getInt(1);

			if ( job.isParamaterized() )
			{
				Properties parms = job.getJobParms();
				Iterator key = parms.keySet().iterator();
				String sKey; String sVal;
				csParm = conn.prepareCall(this.callAddParm);
				while ( key.hasNext() )
				{
					sKey = (String) key.next();
					sVal = (String) parms.get(sKey);

					// Don't add parameters with no value
					if (sVal == null | sVal.equals("")) continue;

					csParm.setInt(1, jobQId);
					csParm.setString(2, sKey);
					csParm.setString(3, sVal);
					csParm.execute();
				}
				conn.commit();
				conn.setAutoCommit(true);
			}

			return jobQId;
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() + ": " + sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(csJob);
			SQLUtil.closeCS(csParm);
			SQLUtil.closeRowSet(rs);
			SQLUtil.closeConnection(conn);
		}
	}
	/**
				 * This method queries the database for a RowSet.  It does NOT close the RowSet.
				 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
				 * @return RowSet
				 * @throws TCGMException
				 */
	public Vector selectAplnLog(String jobid) throws TCGMException {
		String methodName = "selectAplnLog()";
		Vector vec = new Vector();
		String logquery = null;

		logquery =
			this.SELECT_ALL
				+ TCGMConstants.DATASOURCE_SCHEMA_NAME
				+ "."
				+ "APPLICATION_LOG"
				+ " "
				+ " WHERE JOB_QUE_ID= ?  ORDER BY APPLICATION_LOG_ID DESC";

		PreparedStatement ps = null;

		try {

			ps = this.getConnection().prepareStatement(logquery);
			ps.setInt(1, Integer.parseInt(jobid));

			ResultSet result = ps.executeQuery();

			this.logger.debug("OraceAsrTranDao - QUERY: " + logquery);

			while (result.next()) {
				vec.add(this.getAplLogFromCurrentRow(result));
			}
			return vec;

		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(
				this.className,
				methodName,
				sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			SQLUtil.closePS(ps);

			try {
				if (!this.getConnection().isClosed()) {
					SQLUtil.closeConnectionII(this.getConnection());
				}
				if (!this.getConnection().isClosed()) {
					SQLUtil.closeConnection(this.getConnection());
				}

			} catch (SQLException sqle) {
				this.logger.error(
					"Error Closing Connection" + this.className + methodName);
				throw new TCGMException(
					this.className,
					methodName,
					sqle.toString());

			}

		}

	}

	/**
						 * This method will be used to convert the "next()" RowSet ojbect to an ASR object
						 * @param rs RowSet
						 * @return Bpcs
						 * @throws TCGMException
						 */
	public Vector selectErrorLog(String jobid) throws TCGMException {
		String methodName = "selectErrorLog(String jobid)";

		String errorquery = null;

		errorquery =
			this.SELECT_ALL
				+ TCGMConstants.DATASOURCE_SCHEMA_NAME
				+ "."
				+ "ERROR_LOG"
				+ " ";

		errorquery =
			errorquery + " WHERE JOB_QUE_ID=? ORDER BY ERROR_LOG_ID DESC";

		Vector vec = new Vector();

		PreparedStatement ps = null;

		try {

			ps = this.getConnection().prepareStatement(errorquery);
			ps.setInt(1, Integer.parseInt(jobid));

			ResultSet result = ps.executeQuery();

			this.logger.debug("OraceProcessDao - QUERY: " + errorquery);

			while (result.next()) {
				vec.add(this.getErrorLogFromCurrentRow(result));
			}
			return vec;

		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(
				this.className,
				methodName,
				sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			SQLUtil.closePS(ps);

			try {
				if (!this.getConnection().isClosed()) {
					SQLUtil.closeConnectionII(this.getConnection());
				}
				if (!this.getConnection().isClosed()) {
					SQLUtil.closeConnection(this.getConnection());
				}

			} catch (SQLException sqle) {
				this.logger.error(
					"Error Closing Connection" + this.className + methodName);
				throw new TCGMException(
					this.className,
					methodName,
					sqle.toString());

			}

		}
	}

	/**
					 * This method will be used to convert the "next()" RowSet ojbect to an ASR object
					 * @param rs RowSet
					 * @return Bpcs
					 * @throws TCGMException
					 */
	public ErrorLogBean getErrorLogFromCurrentRow(ResultSet result)
		throws TCGMException {
		String methodName = "getErrorLogFromCurrentRow(ResultSet)";

		ErrorLogBean errorBean = new ErrorLogBean();

		try {

			errorBean.setJobqueueid(
				new Integer(result.getInt("JOB_QUE_ID")).toString());

			errorBean.setTableid(
				new Integer(result.getInt(DBConst.COL_DATASET_TABLE_ID))
					.toString());
			errorBean.setModelid(
				new Integer(result.getInt(DBConst.COL_MODEL_ID)).toString());
			errorBean.setMsg(result.getString("ERROR_MSG"));
			errorBean.setStatus(result.getString("PROCEDURE_STEP"));
			errorBean.setProcname(result.getString("PROCEDURE_NAME"));
			errorBean.setCrtdate(result.getDate("CREATE_DATETIME").toString()+"  " +result.getTime("CREATE_DATETIME").toString());

			return errorBean;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		}
	}

	/**
					 * This method will be used to convert the "next()" RowSet ojbect to an ASR object
					 * @param rs RowSet
					 * @return Bpcs
					 * @throws TCGMException
					 */
	public LogReportBean getAplLogFromCurrentRow(ResultSet result)
		throws TCGMException {
		String methodName = "getAplLogFromCurrentRow(ResultSet)";

		LogReportBean rptBean = new LogReportBean();

		try {

			rptBean.setJobqueueid(
							new Integer(result.getInt("JOB_QUE_ID"))
								.toString());
			rptBean.setTableid(
				new Integer(result.getInt(DBConst.COL_DATASET_TABLE_ID))
					.toString());
			rptBean.setModelid(
				new Integer(result.getInt(DBConst.COL_MODEL_ID)).toString());
			rptBean.setMsg(result.getString("PROCEDURE_MSG"));
			rptBean.setStatus(result.getString("PROCEDURE_STATUS"));
			rptBean.setProcname(result.getString("PROCEDURE_NAME"));
			rptBean.setCrtdate(result.getDate("CREATE_DATETIME").toString()+"   "+result.getTime("CREATE_DATETIME").toString());

			return rptBean;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		}
	}

	/**
	 *
	 * @param job
	 * @return
	 * @throws TCGMException
	 */
	private int checkIfPublishedRecordExists(int inqReportId, int modelId) throws TCGMException
	{
		String methodName = "checkIfPublishedRecordExists(int inqReportId, int modelId)";
		String parameterList = "inqReportId: " + inqReportId + " modelId :" + modelId;
		int count = 0;

		Properties parms = new Properties();
		Connection con = null;
		Statement s = null;
		String sql = "SELECT Count(*) Count FROM " + DBConst.TABLE_PUBLISHED_MODELS + " WHERE " + DBConst.COL_MODEL_ID + "=" + modelId
		+ " AND " + DBConst.COL_INQUIRY_REPORT_ID + "=" + inqReportId;

		try
		{
			con = this.getConnection();
			s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if ( rs.next() )
			{
				count = rs.getInt("Count");
			}
			
			rs.close();
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(con);
		}

		return count;
	}	
	/**
	 *
	 * @param job
	 * @return
	 * @throws TCGMException
	 */
	private int checkIfGeneratedRecordExists(int inqReportId, int modelId) throws TCGMException
	{
		String methodName = "checkIfGeneratedRecordExists(int inqReportId, int modelId)";
		String parameterList = "inqReportId: " + inqReportId + " modelId :" + modelId;
		int count = 0;

		Properties parms = new Properties();
		Connection con = null;
		Statement s = null;
		String sql = "SELECT Count(*) Count FROM " + DBConst.TABLE_GENERATED_MODELS + " WHERE " + DBConst.COL_MODEL_ID + "=" + modelId
		+ " AND " + DBConst.COL_INQUIRY_REPORT_ID + "=" + inqReportId;

		try
		{
			con = this.getConnection();
			s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if ( rs.next() )
			{
				count = rs.getInt("Count");
			}
		
			rs.close();
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(con);
		}

		return count;
	}		
	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	private void updatePublishedFlag(int inqReportId, int modelId, String flag) throws TCGMException
	{
		String methodName = "updatePublishedFlag(int inqReportId, int modelId, String flag)";
		String parameterList = "inqReportId: " + inqReportId + " modelId :" + modelId + " flag :" + flag;
		Connection c = null;
		Statement s = null;

		try
		{
			c = this.getConnection();
			String sql = "UPDATE " +DBConst.TABLE_PUBLISHED_MODELS + " SET " + DBConst.COL_PUBLISH_FLAG +
						 "='" + flag + "' , " + DBConst.COL_MODIFY_USERNAME + "='" + this.updColDefault(this.userToken.getUserid().trim(),"TCGM")  
									 + "' , " + DBConst.COL_MODIFY_DATETIME + "= to_date('"+getDateTime()+"','yyyy-mm-dd hh24:mi:ss') " 
									 + " WHERE " + DBConst.COL_MODEL_ID + "=" + modelId
									 + " AND " + DBConst.COL_INQUIRY_REPORT_ID + "=" + inqReportId;
			parameterList += "\nSQL: " + sql;
			s = c.createStatement();
			s.execute(sql);

		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(c);
		}
	}	
	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	private void updateGeneratedFlag(int inqReportId, int modelId, String flag) throws TCGMException
	{
		String methodName = "updateGeneratedFlag(int inqReportId, int modelId, String flag)";
		String parameterList = "inqReportId: " + inqReportId + " modelId :" + modelId + " flag :" + flag;
		Connection c = null;
		Statement s = null;

		try
		{
			c = this.getConnection();
			String sql = "UPDATE " +DBConst.TABLE_GENERATED_MODELS + " SET " + DBConst.COL_GENERATE_FLAG +
						 "='" + flag + "' , " + DBConst.COL_MODIFY_USERNAME + "='" + this.updColDefault(this.userToken.getUserid().trim(),"TCGM")  
									 + "' , " + DBConst.COL_MODIFY_DATETIME + "= to_date('"+getDateTime()+"','yyyy-mm-dd hh24:mi:ss') " 
									 + " WHERE " + DBConst.COL_MODEL_ID + "=" + modelId
									 + " AND " + DBConst.COL_INQUIRY_REPORT_ID + "=" + inqReportId;
			parameterList += "\nSQL: " + sql;
			s = c.createStatement();
			s.execute(sql);

		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(c);
		}
	}		
	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	private void insertPublishRecord(int inqReportId, int modelId, String flag, String modelName) throws TCGMException
	{
		String methodName = "insertPublishRecord(int inqReportId, int modelId, String flag, String modelName)";
		String parameterList = "inqReportId: " + inqReportId + " modelId :" + modelId + " flag :" + flag + " modelName :" + modelName ;
		Connection c = null;
		Statement s = null;

		try
		{
			c = this.getConnection();
			String sql = "INSERT INTO " +DBConst.TABLE_PUBLISHED_MODELS + " ( " + DBConst.COL_INQUIRY_REPORT_ID + "," +
						  DBConst.COL_MODEL_ID + "," + DBConst.COL_PUBLISH_FLAG + "," + DBConst.COL_MODEL_NAME2 + "," +
						  DBConst.COL_CREATE_USERNAME + "," + DBConst.COL_CREATE_DATETIME + "," + DBConst.COL_MODIFY_USERNAME + "," +
						  DBConst.COL_MODIFY_DATETIME + ") values ( " 
						  + inqReportId + "," + modelId + ",'" + flag + "','" + modelName  + "','" + this.updColDefault(this.userToken.getUserid().trim(),"TCGM")  
						  + "', to_date('"+getDateTime()+"','yyyy-mm-dd hh24:mi:ss') "   + ",'" + this.updColDefault(this.userToken.getUserid().trim(),"TCGM") +  "', to_date('"+getDateTime()+"','yyyy-mm-dd hh24:mi:ss'))";
			parameterList += "\nSQL: " + sql;
			s = c.createStatement();
			s.execute(sql);

		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(c);
		}
	}
	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	private void insertGenerateRecord(int inqReportId, int modelId, String flag, String modelName) throws TCGMException
	{
		String methodName = "insertGenerateRecord(int inqReportId, int modelId, String flag, String modelName)";
		String parameterList = "inqReportId: " + inqReportId + " modelId :" + modelId + " flag :" + flag + " modelName :" + modelName ;
		Connection c = null;
		Statement s = null;

		try
		{
			c = this.getConnection();
													  
			String sql = "INSERT INTO " +DBConst.TABLE_GENERATED_MODELS + " ( " + DBConst.COL_INQUIRY_REPORT_ID + "," +
						  DBConst.COL_MODEL_ID + "," + DBConst.COL_GENERATE_FLAG + "," + DBConst.COL_MODEL_NAME2 + "," +
						  DBConst.COL_CREATE_USERNAME + "," + DBConst.COL_CREATE_DATETIME + "," + DBConst.COL_MODIFY_USERNAME + "," +
						  DBConst.COL_MODIFY_DATETIME + ") values ( " 
						  + inqReportId + "," + modelId + ",'" + flag + "','" + modelName  + "','" + this.updColDefault(this.userToken.getUserid().trim(),"TCGM")  
						  + "', to_date('"+getDateTime()+"','yyyy-mm-dd hh24:mi:ss') ,'" + this.updColDefault(this.userToken.getUserid().trim(),"TCGM") +  "', to_date('"+getDateTime()+"','yyyy-mm-dd hh24:mi:ss'))";
			parameterList += "\nSQL: " + sql;
			s = c.createStatement();
			s.execute(sql);

		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}
		finally
		{
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(c);
		}
	}	
	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	public void PublishRecord(int inqReportId, int modelId, String flag, String modelName) throws TCGMException
	{
		int count = 0;
		
		count = checkIfPublishedRecordExists(inqReportId, modelId);
		
		if (count ==0)
		{
			insertPublishRecord(inqReportId, modelId,flag, modelName);
		}
		else
		{
			updatePublishedFlag(inqReportId, modelId, flag);
		}
	}

	/**
	 *
	 * @param jobQId
	 * @param status
	 * @throws TCGMException
	 */
	public void GenerateRecord(int inqReportId, int modelId, String flag, String modelName) throws TCGMException
	{
		int count = 0;
		
		count = checkIfGeneratedRecordExists(inqReportId, modelId);
		
		if (count ==0)
		{
			insertGenerateRecord(inqReportId, modelId,flag, modelName);
		}
		else
		{
			updateGeneratedFlag(inqReportId, modelId, flag);
		}
	}


	/**
	 * @param  userToken	UserToken
	 * @return boolean (processFlag) 
	 * @throws TCGMException
	 * 
	 *  This method will determine if the time is ripe for System Compact
	 *  operation to be performed. 
	 */
	public boolean processSystemCompact() throws TCGMException
	{
		String methodName = "processSystemCompact(UserToken userToken)";
		
		int frequency = 0;
		int diff = 0;
		String parmProcessedDate = null;
		String status = null;  
		boolean processFlag = false;

		Calendar calProcessedDate = Calendar.getInstance();
		Calendar calCurrentDate   = Calendar.getInstance();

		try 
		{
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(SQLUtil.getOracleAdmin(), TCGMModel.Type.FACTOR);
			
		   /******************************************************************************************************
			* This block will get the last succesfull processed System Compact date and also the frequency values
			* from the Parameter table. These parameter values are stored under the global model id (-1).
			*******************************************************************************************************/
			parmProcessedDate = md.getModelParm(-1, JobConstants.PN_LAST_SUCCESSFUL_PROCESSED_DATE_SYSTEM_COMPACT);
			frequency = Integer.parseInt(md.getModelParm(-1, JobConstants.PN_FREQUENCY_SYSTEM_COMPACT));
			status = md.getModelParm(-1, JobConstants.PN_SYSTEM_COMPACT_STATUS);
			
		   /******************************************************************************************************
			* Convert the last succesfull processed System Compact date in String format to Calendar Date.
			*******************************************************************************************************/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateProcessedDate = sdf.parse(parmProcessedDate);
			Date dateCurrentDate   =  new Date();
			
			calProcessedDate.setTime(dateProcessedDate);
			calCurrentDate.setTime(dateCurrentDate);

		   /******************************************************************************************************
			* Get the difference between the last succesfull processed System Compact date and current date.
			*******************************************************************************************************/
			diff = hoursDifference(calProcessedDate, calCurrentDate);
			
		   /******************************************************************************************************
			* If the Date difference is greater than frequency and the status is not 'failed' then the time is ripe
			* for System Compact operation else it is not ready for System Compact operation.
			*******************************************************************************************************/
			if ((diff > frequency) && (!(status.equalsIgnoreCase(JobConstants.PN_STATUS_FAILED))))
			{
				processFlag = true;
			}
			
		 return processFlag;	
		} 
		catch (TCGMException e) 
		{
			logger.error("Exception while processing processSystemCompact():" + this.className + methodName);
			logger.error("Error Message: "  + e.getMessage());
			throw new TCGMException( this.className,methodName, e.toString() );			
		} 
		catch (ParseException pe) 
		{
			logger.error("ParseException while processing processSystemCompact(). check the LAST_SUCCESSFUL_PROCESSED_DATE_SYSTEM_COMPACT" +
						 " value in the parameter table. :" + this.className + methodName);
			logger.error("Error Message: "  + pe.getMessage());
			throw new TCGMException( this.className,methodName, pe.toString() );			
		}
	}

	/**
	 * @param  userToken	UserToken
	 * @return boolean (processFlag) 
	 * @throws TCGMException
	 * 
	 *  This method will determine if the time is ripe for Model Purge
	 *  operation to be performed. 
	 */
	public boolean processModelPurge() throws TCGMException
	{
		String methodName = "processModelPurge(UserToken userToken)";
		
		int frequency = 0;
		int diff = 0;
		String parmProcessedDate = null;
		String status = null;  
		boolean processFlag = false;

		Calendar calProcessedDate = Calendar.getInstance();
		Calendar calCurrentDate   = Calendar.getInstance();

		try 
		{
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(SQLUtil.getOracleAdmin(), TCGMModel.Type.FACTOR);
			
		   /******************************************************************************************************
			* This block will get the last succesfull processed Model Purge date and also the frequency values
			* from the Parameter table. These parameter values are stored under the global model id (-1).
			*******************************************************************************************************/
			parmProcessedDate = md.getModelParm(-1, JobConstants.PN_LAST_SUCCESSFUL_PROCESSED_DATE_MODEL_PURGE);
			frequency = Integer.parseInt(md.getModelParm(-1, JobConstants.PN_FREQUENCY_MODEL_PURGE));
			status = md.getModelParm(-1, JobConstants.PN_MODEL_PURGE_STATUS);
			
		   /******************************************************************************************************
			* Convert the last succesfull processed Model Purge date in String format to Calendar Date.
			*******************************************************************************************************/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateProcessedDate = sdf.parse(parmProcessedDate);
			Date dateCurrentDate   =  new Date();
			
			calProcessedDate.setTime(dateProcessedDate);
			calCurrentDate.setTime(dateCurrentDate);

		   /******************************************************************************************************
			* Get the difference between the last succesfull processed Model Purge date and current date.
			*******************************************************************************************************/
			diff = hoursDifference(calProcessedDate, calCurrentDate);
			
		   /******************************************************************************************************
			* If the Date difference is greater than frequency and the status is not 'failed' then the time is ripe
			* for Model Purge operation else it is not ready for Model Purge operation.
			*******************************************************************************************************/
			if ((diff > frequency) && (!(status.equalsIgnoreCase(JobConstants.PN_STATUS_FAILED))))
			{
				processFlag = true;
			}
			
		 return processFlag;	
		} 
		catch (TCGMException e) 
		{
			logger.error("Exception while processing processSystemCompact():" + this.className + methodName);
			logger.error("Error Message: "  + e.getMessage());
			throw new TCGMException( this.className,methodName, e.toString() );			
		} 
		catch (ParseException pe) 
		{
			logger.error("ParseException while processing processModelPurge(). check the LAST_SUCCESSFUL_PROCESSED_DATE_MODEL_PURGE" +
						 " value in the parameter table. :" + this.className + methodName);
			logger.error("Error Message: "  + pe.getMessage());
			throw new TCGMException( this.className,methodName, pe.toString() );			
		}
	}
	
	/**
	 * @param  lastProcessedDate	Calendar (Date)
	 * @param  currentDate          Calendar (Date)
	 * @return difference           int 
	 * @throws TCGMException
	 * 
	 *  This method will take two Calendar dates as input and will return the
	 *  difference in days between the two Calendar dates.
	 */
	public int hoursDifference(Calendar lastProcessedDate, Calendar currentDate) throws TCGMException
	{
		String methodName = "hoursDifference(Calendar lastProcessedDate, Calendar currentDate)";
		String parameterList = "lastProcessedDate: " + lastProcessedDate + "currentDate :" + currentDate ;
		int difference = 0;

		try 
		{
			difference = DayCounter.hoursUntil(lastProcessedDate, currentDate);
		} 
		catch (RuntimeException e) 
		{
			logger.error("Exception calculating the Date Diff:" + this.className + methodName);
			logger.error(" Parameter List:" + parameterList);
			logger.error("Error Message: "  + e.getMessage());
			throw new TCGMException( this.className,methodName, parameterList, e.toString() );			
		}
		
		return difference;		
	}
	
	//get current date time in 24 hour format
	private String getDateTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()) );
		int secs = calendar.get(Calendar.SECOND);
		int mins = calendar.get(Calendar.MINUTE);

		String dateTime = calendar.get(calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
									  calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
									  mins + ":" + secs;

		return dateTime;									  
	}
	public String sendNetAnl(String modelId,String modelDesc) throws TCGMException{
		String methodName = "sendNetAnl()";		

		Connection conn = SQLUtil.openConnection();
		CallableStatement obj_CallableStatement=null;
		Statement obj_Statement=null;
		ResultSet obj_ResultSet=null;
		String returnValue="";
		try
		{
			
			obj_CallableStatement=conn.prepareCall("{ call staging.shared_data.refresh_mat_view@"+AppConst.ccsTnsHost+"(?,?,?)}");
			obj_CallableStatement.setString(1,"standard_cost_volume");			
			obj_CallableStatement.setString(2,modelId);
			obj_CallableStatement.setString(3,modelDesc);			
			obj_CallableStatement.execute();
			
			obj_Statement=conn.createStatement();
			obj_ResultSet=obj_Statement.executeQuery("select staging.shared_data.get_standard_cost_volume_count@"+AppConst.ccsTnsHost+" from dual");
			if(obj_ResultSet.next())
			{
				returnValue=obj_ResultSet.getString(1);
				if(null == returnValue){
					returnValue="";
				}
				
			}
			
			
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				
				
				if(obj_CallableStatement != null){
					obj_CallableStatement.close();
					obj_CallableStatement=null;
				}
				if(obj_Statement != null){
					obj_Statement.close();
					obj_Statement=null;
				}
				if(obj_ResultSet != null){
					obj_ResultSet.close();
					obj_ResultSet=null;
				}
				
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
		return returnValue;
	}
}
