package abbott.ai.tcgm.data.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.RowSetProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;

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

	protected final String className = this.getClass().getName();
	protected javax.sql.RowSet rs;
	protected UserToken userToken;
	private String entityTable = "";
	private String entityView = "";
	protected static final String schema = TCGMConstants.DATASOURCE_SCHEMA_NAME;
	protected Connection _conn = null;
	public static String strStopStatus = "";
	protected Vector searchList = new Vector();
	protected static Logger logger = null;

	public OracleDao()
	{
		this.logger = LogManager.getLogger(this.getClass());
	}

	public OracleDao(Connection c) {
		this._conn = c;
	}

	protected void setEntityTable(String entityTable)
	{
		this.entityTable = entityTable;
	}

	protected String getEntity()
	{
		return TCGMConstants.DATASOURCE_SCHEMA_NAME + "." + this.entityTable + " ";
	}

	protected void setEntityView(String entityView)
	{
		this.entityView = entityView;
	}

	protected String getEntityView()
	{
		return TCGMConstants.DATASOURCE_SCHEMA_NAME + "." + this.entityView + " ";
	}

	protected void initRS(String sql,int rowSetType) throws TCGMException
	{
		String methodName = "initRS";
		this.rs = null;
		this.rs = this.createRowSet(sql, rowSetType);
	}

	protected RowSet createRowSet(String sql, int rowSetType) throws TCGMException {
		String methodName = "createRowSet(String sql, int rowSetType)";
		
		try {
			if (rowSetType == TCGMConstants.CACHED_ROWSET) {
				Connection conn = null;
				try {
					conn = SQLUtil.openConnection();
					javax.sql.rowset.CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
					crs.setCommand(sql);
					crs.setConcurrency(ResultSet.CONCUR_READ_ONLY);
					crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
					crs.execute(conn);
					return crs;
				} finally {
					SQLUtil.closeConnection(conn);
				}
			} else if (rowSetType == TCGMConstants.JDBC_ROWSET) {
				javax.sql.rowset.JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();
				
				Connection conn = SQLUtil.openConnection();
				jrs.setCommand(sql);
				jrs.setConcurrency(ResultSet.CONCUR_READ_ONLY);
				jrs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
				
				jrs.setUrl(SQLUtil.getDatasourceURL());
				jrs.setUsername(SQLUtil.getOracleAdmin().getUserid());
				jrs.setPassword(SQLUtil.getOracleAdmin().getPassword());
				
				return jrs;
			} else {
				throw new TCGMException(className, methodName, "The RowSet type specified (" + rowSetType + ") is invalid");
			}
		} catch (SQLException sqle) {
			throw new TCGMException(this.className, methodName, sqle.toString());
		} catch (Exception e) {
			throw new TCGMException(this.className, methodName, e.toString());
		}
	}

	protected Connection getConnection() throws TCGMException
	{
		String methodName = "getConnection()";
		try {
			if (this._conn == null || this._conn.isClosed() )
			{
				this._conn = SQLUtil.openConnection();
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		return this._conn;
	}

	protected String buildSortClause(Sort sortObject)
	{
		return (ORDER_BY + sortObject.getSortColumn() + " " + sortObject.getSortOrder());
	}
	
	protected String buildEBCDICSortClause(Sort sortObject)
	{
		return (ORDER_BY + " CONVERT( " +sortObject.getSortColumn() + ",'WE8EBCDIC1047','US7ASCII' ) " + sortObject.getSortOrder());
	}

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

	public String updCol(String currentValue,String newValue)
	{
		String retVal = currentValue;

		if(newValue != null && ! newValue.equals(""))
		{
			retVal = newValue;
		}
		return retVal;
	}

	protected String updColDefault(String curVal,String defVal)
	{
		String retVal = curVal;

		if(curVal == null || curVal.equals(""))
		{
			retVal = defVal;
		}
		return retVal;
	}

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

	public void finalize()
	{
		SQLUtil.closeRowSet(this.rs);
	}

	protected void logException(String className,String methodName,Exception ex)
	{
		this.logger.error(className + ":" + methodName + ":" + ex.toString(),ex);
	}

	protected void setLogger(Logger logger)
	{
		this.logger = logger;
	}

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
	if (props.size() > 0) { 
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
