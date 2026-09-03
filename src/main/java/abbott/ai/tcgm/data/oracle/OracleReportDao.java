package abbott.ai.tcgm.data.oracle;

import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.UserToken;
import java.sql.*;
import java.util.*;
import java.io.*;
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

public class OracleReportDao extends OracleDao implements ReportDao
{
	 private String QRY_REPORT_INSTANCES = "SELECT * FROM " + this.schema + ".VW_REPORT_INSTANCE_DTL";
	private ReportDefinition searchObject = null;

	public OracleReportDao(UserToken ut)
	{
		this.userToken = ut;
		this.setEntityTable("REPORT");
	}

	public OracleReportDao(UserToken ut, Connection conn)
	{
		this.userToken = ut;
		this.setEntityTable("REPORT");
		this._conn = conn;
	}

	public String[] getRestrictCols(String reportId) {
		String[] cols = null;
		return cols;
	}

	public boolean hasData(String fileName, String whereClause) throws TCGMException {
		String methodName = "hasData(String sql)";
		String parmList = "FileName: " + fileName + " WhereClause: " + whereClause;

		long count = -1;
		String sql = this.SELECT_COUNT + fileName + whereClause;
		try
		{
			this.logger.debug("hasData(Count Query): " + sql);

			this.initRS(sql,TCGMConstants.JDBC_ROWSET);

			rs.execute();
			while (rs.next())
			{
				count = rs.getLong(DBConst.COL_COUNT);
			}
			return (count > 0);
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
		}
	}

	public ReportDefinition getReportById(String reportId) throws TCGMException {
		String query = "SELECT * FROM " + this.getEntity() + " WHERE " + DBConst.COL_REPORT_ID + " = " + reportId;
		return (ReportDefinition) this.getVO(query).firstElement();
	}

	public Vector getAffiliatesBySectorId(String sectorId) throws TCGMException {
		
		String area   = sectorId.substring(0,2);
		String region = sectorId.substring(2,4);
		String sector = sectorId.substring(4,6);
		
		String query = "SELECT DISTINCT " +  DBConst.COL_AFF + " FROM " + DBConst.TABLE_T_AFFILIATE + " WHERE " + DBConst.COL_AREA  + " = " + area +
		                "AND " + DBConst.COL_REGION + " = " + region + " AND " + DBConst.COL_SECTOR + " = " + sector;
		return this.getAffVO(query); 
	}

	public Vector getAllRGMAffReports() throws TCGMException {
		
		String query = "SELECT DISTINCT " +  DBConst.COL_REPORT_ID + " FROM " + DBConst.TABLE_REPORT + " WHERE " + 
		                DBConst.COL_REPORT_USE_CASE  + " = '" + TCGMConstants.REPORT_CONSTANT_RGM_A  + "'";
		return this.getRGMAffReportsVO(query); 
	}

	public Vector getAllAffIdsForThisRun(String columnName, String viewName, String datasetId) throws TCGMException {
		
		String query = "SELECT DISTINCT " +  columnName + " FROM " + viewName + " WHERE " + 
						DBConst.COL_DATASET_TABLE_ID  + " = '" + datasetId  + "'";
		return this.getAllAffIdsForThisRunVO(query, columnName); 
	}

	public int runReportDirect(String reportId, String modelId) throws TCGMException {
		String methodName = "runReportDirect(String reportId)";
		String parameterList = "reportId: " + reportId;
		ReportDefinition reportDef = this.getReportById(reportId);
		String preCmdName = reportDef.getPreCmd();

		String sql = "{ call TCGM." + preCmdName + " ( ?, ? ) ";
		CallableStatement cs = null;
		Connection conn = null;
		try {
			conn = this.getConnection();
			cs = conn.prepareCall(sql);
			cs.setString(1, modelId);
			cs.setString(2, "-1");
			cs.execute();
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

		// get back datasettableid and pass back.
		return 0;
	}

	public File createReportExtractFile(ReportDefinition report) throws TCGMException {
		File file = null;


		return file;
	}


	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateData object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(ReportDefinition searchObject) throws TCGMException
	{
		String methodName = "getRS(ReportDefinition)";
		this.setSearchObject(searchObject);
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException
	{
		String methodName = "getRS()";
		String query = "SELECT * FROM " + this.getEntity() + this.genWhereClause();
		return this.getRS(query);
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
			this.logger.debug("\nOracleReportDao - QUERY: " + query);

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
	 * @param searchObject RateData object with search criteria
	 * @return Vector of RateData objects
	 * @throws TCGMException
	 */
	public Vector getVO(ReportDefinition searchObject) throws TCGMException
	{
		String methodName="getVO(ReportDefinition)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of Report objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException
	{
		String methodName = "getVO";

		Vector vec = new Vector();

		try
		{
			this.getRS();

			while (rs.next())
			{
				vec.add(this.getReportFromCurrentRow(rs));
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
				vec.add(this.getReportFromCurrentRow(rs));
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
	 *
	 * @return Vector of Affiliate Id's
	 * @throws TCGMException
	 */
	private Vector getAffVO(String query) throws TCGMException
	{
		String methodName = "getAffVO";

		Vector vec = new Vector();

		try
		{
			this.getRS(query);

			while (rs.next())
			{
				vec.add(rs.getString(DBConst.COL_AFF));
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
	 *
	 * @return Vector of RGM Report Id's
	 * @throws TCGMException
	 */
	private Vector getRGMAffReportsVO(String query) throws TCGMException
	{
		String methodName = "getRGMAffReportsVO";

		Vector vec = new Vector();

		try
		{
			this.getRS(query);

			while (rs.next())
			{
				vec.add(rs.getString(DBConst.COL_REPORT_ID));
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
	 *
	 * @return Vector of Affiliate Id's that have data for the current run
	 * @throws TCGMException
	 */
	private Vector getAllAffIdsForThisRunVO(String query, String columnName) throws TCGMException
	{
		String methodName = "getAllAffIdsForThisRunVO";

		Vector vec = new Vector();

		try
		{
			this.getRS(query);

			while (rs.next())
			{
				vec.add(rs.getString(columnName));
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
	private ReportDefinition getReportFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getReportFromCurrentRow(RowSet)";

		ReportDefinition report = new ReportDefinition();

		try
		{
			report.setIdInt( rs.getInt(DBConst.COL_REPORT_ID) );
			report.setName( rs.getString(DBConst.COL_REPORT_NAME) );
			report.setDesc( rs.getString(DBConst.COL_REPORT_DESC) );
			report.setPreCmd( rs.getString(DBConst.COL_REPORT_PRE_CMD) );
			report.setDisplayName( rs.getString(DBConst.COL_REPORT_DISPLAY_NAME) );
			report.setParmKeys(this.getParmKeys( rs.getInt(DBConst.COL_REPORT_ID) ) );
			report.setUseCase( rs.getString(DBConst.COL_REPORT_USE_CASE) );
			report.setSearchPath( rs.getString(DBConst.COL_REPORT_SEARCH_PATH) );
			report.setOutputFormat(rs.getString(DBConst.COL_OUTPUT_FORMAT));
			report.setReportVersions(rs.getString(DBConst.COL_REPORT_VERSIONS));
			if (rs.getString(DBConst.COL_GENERATE_EMPTY_REPORT).equalsIgnoreCase("Y"))
			{
				report.setGenerateEmptyReport(true);
			}
			else
			{
				report.setGenerateEmptyReport(false);
			}
			
			return report;
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

	protected String getClobAsString(Clob clob) throws SQLException, TCGMException {

		  if (clob.length() > Integer.MAX_VALUE ) {
			  throw new TCGMException(className, "getClobAsString", "Clob too large to use as String");
		  }
		  int len = (int) clob.length();
		  StringBuffer buffer = new StringBuffer( len );
		  buffer.append(clob.getSubString(1, len));
		  return buffer.toString();
	}


	/*****************************************************************************************/
	/**
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_REPORT_ID,searchObject.getId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
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

	public void runProcedure(String procName) throws TCGMException {
		String call = "{CALL " + this.schema + "." + procName + "}";


	}

	private String[] getParmKeys(int reportId) throws SQLException, TCGMException {
		ArrayList parmKeys = new ArrayList();

		Connection con = this.getConnection();
		ResultSet rs = con.createStatement().executeQuery("SELECT rptparm_name FROM " + this.schema + ".vw_report_parms WHERE report_id = " + reportId);

		while ( rs.next() ) parmKeys.add( rs.getString(1).trim() );

		rs.close();
		con.close();

		if (parmKeys.size() > 0)
			return (String[]) parmKeys.toArray(new String[0]);
		else
			return null;
	}

	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject RateData
	 */
	private void setSearchObject(ReportDefinition searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private ReportDefinition getSearchObject()
	{
		return this.searchObject;
	}
}