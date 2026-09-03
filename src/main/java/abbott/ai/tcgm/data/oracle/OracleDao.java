package abbott.ai.tcgm.data.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import javax.sql.RowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.jdbc.rowset.OracleJDBCRowSet;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Contains common methods and properties that will be extended to all Oracle Dao Objects</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class OracleDao
{
	protected final static String MIDDLE_SELECT_END = " )";
	protected final static String OUTER_SELECT = "SELECT * FROM ( ";
	protected final static String INNER_SELECT = "SELECT * FROM ";
	protected final static String OUTER_WHERE_MIN_BOUND = " ) WHERE RN BETWEEN ";
	protected final static String OUTER_WHERE_MAX_BOUND = " AND ";
	protected final static String SELECT_ALL = "SELECT * FROM ";
	protected final static String SELECT_COUNT = "SELECT COUNT(*) COUNT FROM ";
	protected final static String DELETE_FROM = "DELETE FROM ";
	protected final static String UPDATE = "UPDATE ";
	protected final static String SET_PUBLISHED = " SET PUBLISH_FLAG ='P' ";
	protected final static String SET_UNPUBLISHED = " SET PUBLISH_FLAG ='U' ";
	protected final static String ORDER_BY = " ORDER BY ";
	/** End Constants for SQL */

	protected final String className = this.getClass().getName();//convenience property
	protected javax.sql.RowSet rs;
	protected UserToken userToken;
	private String entityTable = "";
	private String entityView = "";
	protected static final String schema = TCGMConstants.DATASOURCE_SCHEMA_NAME;
	protected Connection _conn = null;
	public static String strStopStatus = "";
	protected Vector searchList = new Vector();
	protected static Logger logger = null;

	/**
	 * Default Constructor
	 */
	public OracleDao()
	{
		this.logger = Logger.getLogger(this.getClass());
	}

	public OracleDao(Connection c) {
		this._conn = c;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param entityTable Name of the database table
	 */
	protected void setEntityTable(String entityTable)
	{
		this.entityTable = entityTable;
	}
	/**
	 *
	 * @return Schema + the name of the database table in the format schema.entityTable
	 */
	protected String getEntity()
	{
		return TCGMConstants.DATASOURCE_SCHEMA_NAME + "." + this.entityTable + " ";
	}
	/*****************************************************************************************/
	/**
	 * @param entityView
	 */
	protected void setEntityView(String entityView)
	{
		this.entityView = entityView;
	}
	/**
	 * @return
	 */
	protected String getEntityView()
	{
		return TCGMConstants.DATASOURCE_SCHEMA_NAME + "." + this.entityView + " ";
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param sql SQL string that rowset will execute
	 * @param rowSetType tells if the rowset will be a cached rowset or a jdbc rowset
	 * @throws TCGMException
	 */
	protected void initRS(String sql,int rowSetType) throws TCGMException
	{
		String methodName = "initRS";
		this.rs = null;
		this.rs = this.createRowSet(sql, rowSetType);
	}



	protected RowSet createRowSet(String sql,int rowSetType) throws TCGMException {
		String methodName = "createRowSet(String sql, int rowSetType)";
		RowSet rst = null;

		try
		{
			switch(rowSetType)
			{
				case TCGMConstants.CACHED_ROWSET:
					// logger.debug("Creating new RowSet in OracleDao/createRowSet(sql, rowSetType)");
					rst = new OracleCachedRowSet();
					break;
				case TCGMConstants.JDBC_ROWSET:
					// logger.debug("Creating new RowSet in OracleDao/createRowSet(sql, rowSetType)");
					rst = new OracleJDBCRowSet();
					break;
				default:
					throw new TCGMException(className,methodName,"The RowSet type specified (" + rowSetType + ") is invalid");
			}
			
			String dbUrl = SQLUtil.getInstance().getDatasourceURL();
			logger.info("Datasource URL = " + dbUrl);

			rst.setUrl(dbUrl);
		
			//rst.setUrl(SQLUtil.getInstance().getDatasourceURL());
			// 11-8-05 * Chgs Rqd for Active Directory Authentication *
			//         Instead of using the password from the userToken here,
			//         use a generic standard userid & password for this access
			//         to the oracle db. We are changing the security model so
			//         that individual users will not have to be in the oracle system.

			//rst.setUsername(this.userToken.getUserid());
			//rst.setPassword(this.userToken.getPassword());
			rst.setUsername(SQLUtil.getInstance().getOracleAdmin().getUserid());
			rst.setPassword(SQLUtil.getInstance().getOracleAdmin().getPassword());
//			rst.setUsername("tcgm");
//			rst.setPassword("tcgm");
			rst.setCommand(sql);
			rst.setConcurrency(rst.CONCUR_READ_ONLY);
			rst.setType(rst.TYPE_SCROLL_INSENSITIVE);
			//rst.setFetchDirection(rst.FETCH_FORWARD);
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
		return rst;
	}
	/*****************************************************************************************/
	/**
	 * @return _conn
	 * @throws TCGMException
	 */
	protected Connection getConnection() throws TCGMException
	{
		String methodName = "getConnection()";
		try {
			if (this._conn == null || this._conn.isClosed() )
			{
				// logger.debug("Creating new connection in OracleDao/getConnection()");
				this._conn = SQLUtil.openConnection();
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		return this._conn;
	}
	/*****************************************************************************************/
	/**
	 * @param sortObject Sort object
	 * @return sort clause
	 */
	protected String buildSortClause(Sort sortObject)
	{
		//return (this.ORDER_BY + sortObject.getSortColumn() + " " + sortObject.getSortOrder());
		return (ORDER_BY + sortObject.getSortColumn() + " " + sortObject.getSortOrder());
	}
	
	/*****************************************************************************************/
	/**
	 * @param sortObject Sort object
	 * @return sort clause
	 */
	protected String buildEBCDICSortClause(Sort sortObject)
	{
		//return (this.ORDER_BY + sortObject.getSortColumn() + " " + sortObject.getSortOrder());
		//return (ORDER_BY + " EBCDIC_SORT( " +sortObject.getSortColumn() + " ) " + sortObject.getSortOrder());
		return (ORDER_BY + " CONVERT( " +sortObject.getSortColumn() + ",'WE8EBCDIC1047','US7ASCII' ) " + sortObject.getSortOrder());
	}

	/**
	 * There is a 1 to 1 relationship between the columnName and sortOrder for any given index
	 * @param sortObject array of sort orders to sort by
	 * @return sort clause
	 */
	protected String buildSortClause(Sort sortObject[])
	{
		StringBuffer sortClause = new StringBuffer();

		sortClause.append(this.ORDER_BY);

		for (int i = 0; i<sortObject.length;i++)
		{
			sortClause.append(" ");
			sortClause.append(sortObject[i].getSortColumn());
			sortClause.append(" ");
			sortClause.append(sortObject[i].getSortOrder());
		}
		return sortClause.toString();
	}
	/*****************************************************************************************/
	/**
	 * If the newValue is not null or blank then return the new value or else return the
	 * existing value
	 * @param currentValue existing value in the object
	 * @param newValue new value to set if not null or blank
	 * @return String
	 */
	public String updCol(String currentValue,String newValue)
	{
		String retVal = currentValue;

		if(newValue != null && ! newValue.equals(""))
		{
			retVal = newValue;
		}
		return retVal;
	}

	/**
	 * Some columns are not allowed to be null but are allowed to have a space
	 * Other columns are not allowed to be null but must be a numeric value.
	 * This method will check the existing value and if null or "" will return the defValue
	 * We could have set the default value in the entities but it was causing problems with the view
	 * portion of the application where we don't necessarily want default values.
	 * @param curVal Existing value in the object
	 * @param defVal Default value in the object
	 * @return String
	 */
	protected String updColDefault(String curVal,String defVal)
	{
		String retVal = curVal;

		if(curVal == null || curVal.equals(""))
		{
			retVal = defVal;
		}
		return retVal;
	}

	/**
	 * This method assumes that the
	 * @return String
	 */
	protected String genWhereClause()
	{
		String whereClause = "";
		for(int i = 0; i<this.searchList.size();i++)
		{
			whereClause += ((Search)this.searchList.elementAt(i)).generateClause(whereClause);
		}

		if (! whereClause.trim().equals("") )
		{
			whereClause = " WHERE " + whereClause;
		}
		return whereClause;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException
	{
		String methodName = "getCount";

		long count = -1;

		try
		{
			String query = this.SELECT_COUNT + this.getEntity() + this.genWhereClause();

			this.logger.debug("Count Query: " + query);

			this.initRS(query,TCGMConstants.JDBC_ROWSET);
			rs.execute();
			while (rs.next())
			{
				count = rs.getLong(DBConst.COL_COUNT);
			}
			return count;
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
	/*****************************************************************************************/
	/**
	 * Close the rs and conn if not null.
	 */
	public void finalize()
	{
		// These statements make the log very unreadable due to their frequency.
		// Now having gone to a cached connection mechanism, these can be disabled pending further issues.
		// logger.debug("Closing rowset and connection through object destruction.");
		SQLUtil.closeRowSet(this.rs);
	}

	protected void logException(String className,String methodName,Exception ex)
	{
		this.logger.error(className + ":" + methodName + ":" + ex.toString(),ex);
	}

	/**
	 *
	 * @param logger Log4j logger object
	 */
	protected void setLogger(Logger logger)
	{
		this.logger = logger;
	}

	/**
	 *
	 * @return Log4j logger
	 */
	protected Logger getLogger()
	{
		return this.logger;
	}

	public void pushParameters( java.util.Properties props, String modelId, String datasetId) throws SQLException, TCGMException {

	Iterator key = props.keySet().iterator();
	String keyName = null;
	String keyValue = null;
	CallableStatement cs = null;
	Connection conn = null;
	String sql = "{ CALL " + this.schema + ".PARM_WRITE(?, ?, ?, ?) }";
	// parm name, parm value, modelId, datasetTable Id, user assumed to caller
	if (props.size() > 0) { // only set this up if parameters exist
	    conn = this.getConnection();
		cs = conn.prepareCall(sql);
		cs.setInt(3, Integer.parseInt(modelId) );
		cs.setInt(4, Integer.parseInt(datasetId) );
	}

	while (key.hasNext() ) {
		keyName = (String) key.next();
		keyValue = props.getProperty(keyName);
		cs.setString(1, keyName);
		cs.setString(2, keyValue);
		cs.execute();
	}

	SQLUtil.closeCS(cs);
	SQLUtil.closeConnection(conn);
	}

	protected int executeUpdateSql(String sql) throws TCGMException {
		String methodName = "executeUpdateSql(String sql)";
		Statement s = null;
		Connection conn = null;
		int returnCount = 0;
		try {
			conn = this.getConnection();
		  s = conn.createStatement();
		  returnCount = s.executeUpdate(sql);
		}
		catch(SQLException sqle)
			{
				logException(className, methodName, sqle);
				throw new TCGMException(this.className, methodName, sql, sqle.getMessage() );
			}
			finally {
			  SQLUtil.closeStatment(s);
			  SQLUtil.closeConnection(conn);
		  }
		return returnCount;
  }
  
  /**
   * Method returns the comparison type based on the supplied string value.
   * If the value contains comma seperated the the comparison type is IN
   * else LIKE 
   */
  protected String comparisonType(String strValue)
  {
	  String strReturn = "";
		  if(strValue.indexOf(",")>0){
			  strReturn = TCGMConstants.ORACLE_IN_COMPARISON;
		  }else{
			  strReturn = TCGMConstants.ORACLE_LIKE_COMPARISON;
		  }
	
	  return 	strReturn;				
	
  }

}
