package abbott.ai.tcgm.data.as400;

//import java.io.*;
//import java.net.*;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

//import com.ibm.as400.access.*;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;

// 10-7-03 Added for JobInstance
import abbott.ai.tcgm.process.JobInstance;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AS400ReportQueDao extends AS400ReportDao
{
	private static Logger myLogger = Logger.getLogger( "AS400ReportQueDao" );
	private String reportTriggerFile = AppConst.reportTriggerFile;//"TCGRPHP00";
	private String reportParmFile = AppConst.reportParmFile;//"TCGRPPP00";
	private String triggerEntity = this.library + "." + this.reportTriggerFile;
	private String parmEntity = this.library + "." + this.reportParmFile;
	private String JOB_PARM_TABLE = "JOB_QUE_PARMS";

	private final static int AS400_PARM_LENGTH = 132;

	public AS400ReportQueDao()   {  super();  }

	/**
	 *
	 * @param month
	 * @param year
	 * @param version
	 * @throws TCGMException
	 */
	public void insertReportTriggerRecord(ReportPrintRequest rpr, ReportInstance reportInstance, String targetFileName, String member) throws TCGMException
	{
		String methodName = "insertReportTriggerRecord(ReportPrintRequest rpr, String reportName, String member)";
		String parameterList = "rpr: " + rpr.toString() + ", member: " + member;
		Connection c = null;
		Statement s = null;
	
		// 1st, insert the main trigger record
		try
		{
			c = this.getConnection();
			//c.setAutoCommit(true);
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + this.triggerEntity + " VALUES (?, ?, ?, ?, ?) " );
			ps.setString(1, targetFileName ); // Report     RPHREPORT
			ps.setString(2, rpr.getReportId() ); // Report Id  RPHRPRID
			ps.setString(3, member ); // Report Member  RPHMBR
			ps.setString(4, rpr.getReportDest() ); // Report Destination RPHDST
			ps.setInt(5, Integer.parseInt(rpr.getNumCopies()) ); // Copies RPHCPY#
			ps.execute();
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}

		// 2nd, insert report specific additional parameters
		try
		{
			String[] parmKeys = reportInstance.getReportDefinition().getParmKeys();
			if (parmKeys != null)
			{
				String parmValue = "";
// 10-7-03 Discovered that some of the "report" parameters that are needed get set only after the job has
//         been submitted and the job is running in batch (E.g., ANL_FAC01 sets Analysis Calc parms)
//         So we will call setJobParms() here, although it was called earlier in the process. Calling
//		   setJobParms() here will do the equivalent of updating parms that were set after the job started
				JobInstance job = new JobInstance();
				job = reportInstance.getJobInstance();
				// 10-15-03 When testing, change parameter value here to verify that the getJobParms is truly working dynamically
				job.setJobParms( this.getJobParms(job) );

				Properties parms = reportInstance.getJobInstance().getJobParms();
				s = c.createStatement();
				myLogger.debug(">>>Begin Loop thru report parms!");
				for (int i = 0; i< parmKeys.length; i++) {
					parmValue = parms.getProperty(parmKeys[i]); // this could be inlined but it gets pretty unreadable
					myLogger.info("Inserting Parm Values into " + this.parmEntity);
					myLogger.info("Parm Name = "  + parmKeys[i]);
					myLogger.info("Parm Value = " + parmValue);
					if ( parmValue!=null && !parmValue.equals("") ) 
					{
						String sqlParm = "INSERT INTO " + this.parmEntity + " VALUES ( " +
								"'" + targetFileName + "','" + member + "','" + parmKeys[i] + "','" +
								(parmValue.length() > AS400_PARM_LENGTH ? parmValue.substring(0,AS400_PARM_LENGTH-1) : parmValue) + "')";

						s.execute(sqlParm);
					}
				}
				myLogger.debug(">>>End Loop thru report parms!");
			}
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqe.toString() );
		}

		finally {
			SQLUtil.closeStatment(s);
			SQLUtil.closeConnection(c);
		}
	}

	private Properties getJobParms(JobInstance job) throws TCGMException
	{
		String methodName = "getJobParms(JobDefinition job)";
		String parameterList = "Job: " + job.toShortString();

		Properties parms = new Properties();
// 10-8-03 I am blowing up here with an sql msg saying "JOB_QUE_PARMS in TCGM tupe *FILE not found."
		//String sql = "SELECT " + DBConst.COL_PARM_NAME + ", " + DBConst.COL_PARM_VALUE + " FROM " + JOB_PARM_TABLE + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + job.getJobQueId();

		// 10-15-03 Here we were retrieving all JOB_QUE_PARMS from all jobs.
		//String sql = "SELECT * FROM TCGM.JOB_QUE_PARMS";

		// 10-15-03 Here we are retrieving parms from the specific job we are working with
		//          Also I am going to the JOB_QUE_PARMS table as opposed to the PARAMETER tables. This may have
		//          to change later.
		String sql = "SELECT * FROM TCGM.JOB_QUE_PARMS WHERE JOB_QUE_ID = " + job.getJobQueId();
		myLogger.info("AS400ReportQueDao/getJobParms()/sql = " + sql);
		try
		{
// 10-14-03 My problem with the parms is that I believe I am using an AS400 connection with getConnection()
//          as opposed to an oracle connection; Here is my fix.
			UserToken ut = SQLUtil.getOracleAdmin(); // This returns a UserToken for the oracle admin user
			Connection conn = SQLUtil.openConnection(); // This should return a Connection object to oracle

			//Statement s = this.getConnection().createStatement();
			// 10-8-03 Get an oracle connection, not an AS400 connection
			Statement s = conn.createStatement();

// 10-8-03 This next statement is the actual statement that is causing the problem. I chk'd and it appears that
//         I do have a valid connection.

/* 10-15-03 This still does not work because either it is looking for the parms in the JOB_QUE_PARM table and
			and my parm is balnk (i.e. THRU_PERIOD = blank) or it is going to the parameter table at ther wrong time.
*/
			ResultSet jobParms = s.executeQuery(sql);
			while ( jobParms.next() )
			{
				parms.put( jobParms.getString(DBConst.COL_PARM_NAME), jobParms.getString(DBConst.COL_PARM_VALUE) );
			}
			jobParms.close();
			s.close();
		}
		catch (SQLException sqe)
		{
			throw new TCGMException( this.className, methodName, parameterList, sqe.getMessage() );
		}

		return parms;
	}


}
