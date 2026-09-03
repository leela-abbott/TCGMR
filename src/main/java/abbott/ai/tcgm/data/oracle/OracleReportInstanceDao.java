package abbott.ai.tcgm.data.oracle;

import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.UserToken;
import java.sql.*;
import java.util.*;
//import java.io.*;
import javax.sql.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.process.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class OracleReportInstanceDao extends OracleDao implements ReportInstanceDao
{
	private String QRY_REPORT_INSTANCE_DTL = "SELECT * FROM " + this.schema + ".VW_REPORT_INSTANCE_DTL";
	private ReportDefinition searchObject = null;

	public OracleReportInstanceDao(UserToken ut) {
		this.setEntityTable("REPORT_INSTANCE");
		this.userToken = ut;
	}

	public void deleteReportInstanceById(String reportId, String jobQueId) throws TCGMException {
		String methodName = "deleteReportInstanceById(String reportId, String jobQueId)";



		String deleteCmd = "DELETE FROM " + getEntity() + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQueId + " AND " + DBConst.COL_REPORT_ID + "=" + reportId;
		Connection conn = null;
		try {
			conn = this.getConnection();
			conn.createStatement().execute(deleteCmd);
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString() + " - SQL: " + deleteCmd);
		}
		finally
		{
			  SQLUtil.closeConnection(conn);
		}
	}


	public Vector getReportInstancesByJobQId(String jobQId) throws TCGMException {
		String query = "SELECT * FROM " + getEntity() + " WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQId;
		return this.getVO(query);
	}
	
	public String getJobQueParmName(String jobQId, String jobParmName) throws TCGMException
		{
			String methodName = "getJobQueParmName(String jobQId, String jobParmName)";
			String parameterList = "Job Que ID: " + jobQId;
			String parmValue = "";
			Connection con = null;
			Statement s = null;
			String sql = "SELECT  DISTINCT " + DBConst.COL_PARM_VALUE + " FROM JOB_QUE_PARMS WHERE " + DBConst.COL_JOB_QUE_ID + "=" + jobQId
			+" AND PARM_NAME= '"+jobParmName+"'";

			try
			{
				con = this.getConnection();
				s = con.createStatement();
				ResultSet jobParms = s.executeQuery(sql);
				while ( jobParms.next() )
				{
					parmValue = jobParms.getString(DBConst.COL_PARM_VALUE);
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

			return parmValue;
		}


	public Vector getReportInstances() throws TCGMException {
		String query = "SELECT * FROM " + getEntity();
		return this.getVO(query);
	}

	public ReportInstance getReportInstanceById(String reportId, String jobQueId) throws TCGMException {
		String query = "SELECT * FROM " + getEntity() +
					   " WHERE " + DBConst.COL_REPORT_ID + "=" + reportId +
					   " AND " + DBConst.COL_JOB_QUE_ID + "=" + jobQueId;
		return (ReportInstance) this.getVO(query).firstElement();
	}

	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @return RowSet
	 * @throws TCGMException
	 */

	private RowSet getRS(String query) throws TCGMException {
		String methodName = "getRS(query)";

		try
		{
			this.logger.debug("\nOracleReportInstanceDao - QUERY: " + query);

			this.initRS(query,TCGMConstants.JDBC_ROWSET);

			rs.execute();
			return rs;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}

	/**
	 *
	 * @return Vector of RateData objects
	 * @throws TCGMException
	 */
	private Vector getVO(String query) throws TCGMException
	{
		String methodName = "getVO";

		Vector vec = new Vector();

		try
		{
			this.getRS(query);

			while (rs.next())
			{
				vec.add(this.getReportInstanceFromCurrentRow(rs));
			}
			return vec;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
		}
	}


	/**
	 * This method will be used to convert the "next()" RowSet ojbect to an Report object
	 * @param rs RowSet
	 * @return Report
	 * @throws TCGMException
	 */
	private ReportInstance getReportInstanceFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getReportInstanceFromCurrentRow(RowSet)";

		ReportInstance ri = new ReportInstance();
		// Three seperate daos required to gather all this stuff

		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(userToken, this.getConnection() );
		ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(userToken, this.getConnection() );
		// DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(userToken, this.getConnection() );

		int reportId; int processId;

		try
		{
			reportId = rs.getInt(DBConst.COL_REPORT_ID);
			processId = rs.getInt(DBConst.COL_JOB_QUE_ID);

			ri.setDatasetId( Integer.toString(rs.getInt( DBConst.COL_DATASET_TABLE_ID) ) );
			ri.setRowCount(rs.getInt(DBConst.COL_ROW_COUNT));
			ri.setJobInstance( pd.getJobInstanceById(Integer.toString(processId)));
			ri.setReportDefinition( rd.getReportById(Integer.toString(reportId)));
			ri.setReportContentId(rs.getString(DBConst.COL_REPORT_CONTENT_ID));

			return ri;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className,methodName,e.toString());
		}
	}


	/*****************************************************************************************/
	/**
	 *
	 * @return string
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.className);
		sb.append(", \n");
		sb.append("User Token,\n");
		sb.append(this.userToken.toString());
		sb.append("\nEntity: ");
		sb.append(this.getEntity());
		sb.append("\nSearch Object: ");
		sb.append((ReportDefinition)this.searchObject);

		return sb.toString();
	}
}