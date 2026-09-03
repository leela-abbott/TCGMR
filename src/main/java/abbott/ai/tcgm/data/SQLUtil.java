package abbott.ai.tcgm.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.8
 * <p>Class contains helper methods for opening and closing connections to the database</p>
 * <p>Singleton Class
 */
public final class SQLUtil
{
	/*****************************************************************************************/
	private static String className = SQLUtil.class.getName();
	private static SQLUtil instance = null;

	private static String datasourceSchemaName;
	private static String datasourceURL;
	private static UserToken oracleAdmin = null;

	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.data.SQLUtil");
	/*****************************************************************************************/
	/**
	 * Checks to see if an instance of the class has been created and if not creates a
	 * new one before returning it
	 * @param pDatasourceURL The url to be used when getting connections to the datasource
	 * @param pDatasourceSchemaName The name of the database schema
	 * @param pAS400Admin The admin User object for the AS400
	 * @param pOracleAdmin The admin User object for the Oracle
	 * @return Returns an instance of the SQLUtil class
	 */


	private static java.util.HashMap ucMap = new java.util.HashMap();

	public static void init(javax.servlet.ServletConfig sc)
	{
		if(instance == null)
		{
			instance = new SQLUtil();
		}
		instance.datasourceURL = sc.getInitParameter(TCGMConstants.DATASOURCE_URL);
		if(instance.datasourceURL == null || datasourceURL.trim().equals(""))
		{
			logger.error(TCGMConstants.ERROR_DATASOURCE_URL_NOT_FOUND);
		}

		instance.datasourceSchemaName = TCGMConstants.DATASOURCE_SCHEMA_NAME;


		String oracleId = sc.getInitParameter(TCGMConstants.ORACLE_ID);
		if(oracleId == null || oracleId.trim().equals(""))
		{
			logger.error(TCGMConstants.ERROR_ORACLE_ID_NOT_FOUND);
		}

		String oraclePswd = sc.getInitParameter(TCGMConstants.ORACLE_PSWD);
		if(oraclePswd == null || oraclePswd.trim().equals(""))
		{
			logger.error(TCGMConstants.ERROR_ORACLE_PSWD_NOT_FOUND);
		}

		instance.oracleAdmin = new UserToken(oracleId,oraclePswd);


	}
	/**
	 * @return Returns an instance of the SQLUtil class
	 */
	public static SQLUtil getInstance()
	{
		return instance;
		// might consider throwing a TCGMException here if null, since it should only be instantiated during application initialization
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Object containing the user id and password
	 * @return Connection to the database
	 * @throws TCGMException
	 */
	public static Connection openConnection() throws TCGMException
	{

		/***********************
		 * Date       : 12/06/2005
		 * Modified By: Udaya B Aravapalli
		 * 
		 * This Code is added to get the connection from the connection pool. 
		 * The previous code is commented out.
		 */		
		Connection conn = null;
		String methodName = "openConnection";
		try 
		{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/tcgm");
			conn = ds.getConnection();
		} 
		catch (NamingException e) 
		{
			logger.error("Naming Exception Occured while getting the connection.");
			e.printStackTrace();
			throw new TCGMException(className,methodName,"Naming Exception in SQLUtil.openConnection(): " + e.toString());
		}
		catch (SQLException e) 
		{
			logger.error("SQL Exception Occured while getting the connection.");
			e.printStackTrace();
			throw new TCGMException(className,methodName,"SQLException in SQLUtil.openConnection(): " + e.toString());			
		}
		
//		String methodName = "openConnection";
//		Connection conn = (Connection) ucMap.get(oracleAdmin.getUserid() );
		
//
//		// 1st Check for null connections.
//		if (conn == null ) { // need new connection
//			logger.debug("Creating new connection in SQLUtil.openConnection(). Cached connection does not exist");
//			conn = getNewConnection();
//			ucMap.put(oracleAdmin.getUserid(), conn);
//			}
//
//		// 2nd Check if connection is closed.
//		try {
//			if ( conn.isClosed() ) { // need new connection
//				logger.debug("Creating new connection in SQLUtil.openConnection(). Cached connection is closed");
//				conn = null;
//				conn = getNewConnection();
//				ucMap.put(oracleAdmin.getUserid(), conn);
//			}
//		}
//		catch(SQLException sqle)
//		{
//			throw new TCGMException(className,methodName,"SQLException in SQLUtil.openConnection(): " + sqle.toString());
//		}
//
//		// 3rd Check validity by creating a statement and closing it. Lightest database touch I know of.
// 		try 
//		{
//			conn.createStatement().close();
//		}
//		catch (SQLException sql2) 
//		{
//			// somethings wrong...need new connection.
//			logger.debug("Creating new connection. Cached connection is invalid");
//			conn = null;
//			conn = getNewConnection();
//			ucMap.put(oracleAdmin.getUserid(), conn);
//		}

		return conn;
	}

	private static Connection getNewConnection() throws TCGMException {
		String methodName = "getNewConnection()";
		Connection conn = null;

		try {
			OracleDataSource ods = new OracleDataSource();
			ods.setURL( SQLUtil.getDatasourceURL() );
			conn = ods.getConnection( oracleAdmin.getUserid(), oracleAdmin.getPassword() );
		}

		catch(SQLException sqle)
		{
			throw new TCGMException(className,methodName,"SQLException: " + sqle.toString());
		}
		return conn;
	}


    /**
     * @deprecated stubbed out to prevent singleton connection from being closed
     * @param userToken
     */
	public static void closeCachedConnection(UserToken userToken)
	{
//		String methodName = "closeCachedConnection(UserToken ut)";
//
//		/*****************************************
//		 * When a user explicitly logs out of the application, close their cached connection
//		 * if possible. This helps changes in a user profile get picked up upon logout
//		 * rather than waiting for the connection to expire, and minimizes open connections
//		 *
//		 */
//
//		try
//		{
//			Connection conn = (Connection) ucMap.get(userToken.getUserid() );
//			if(conn != null && !conn.isClosed())
//			{
//				logger.debug("Closing cached user connection.");
//				conn.close();
//			}
//		}
//		catch(SQLException sqle)
//		{
//			//catch silently if close fails.
//		}
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param conn Connection to be closed
	 * @throws TCGMException
	 */
	public static void closeConnection(Connection conn)
	{
		String methodName = "closeConnection(Connection conn)";

		/*****************************************
		 * After connection nesting problems continued to occur, the open connection utility was rewritten
		 * to hand back stored connections keyed to a user id if possible. Therefore explicitly closing
		 * connections was no longer desireable. For this reason this body is disabled.
		 *
		 * Options going forward are to remove the close calls throughout the codebase, or perform
		 * some cleanup functionality here.
		 *
		 */
		/***********************
		 * Date       : 12/06/2005
		 * Modified By: Udaya B Aravapalli
		 * 
		 * This Code is UnCommented to close the connection that is acquired.
		 */		
		try
		{
			if(conn != null && !conn.isClosed())
			{
				conn.close();
			}
		}
		catch(SQLException sqle)
		{
			logger.error("SQL Exception Occured while Closing the connection.");
			sqle.printStackTrace();
		}
	}

	public static void closeConnectionII(Connection conn)
	{
		String methodName = "closeConnectionII";

		/*****************************************
		 * 9/24/03
		 * In order to fix the open cursor problem on the Job Q Mangement page, I need to create a 2nd
		 * closeConnection() method here to close connections particular to that page. Although the orignal
		 * closeConnection() method is all commented out, I don't want to restore that method because
		 * many objects still call that dummy method. It's better to just create one for my purposes to
		 * avoid breaking something else elsewhere.
		 *
		 * My other option is to just pass a connection object around for the Job Q Management processing.
		 * Furthermore, there is nothing that precludes this method from being called from other processes
		 * that "truly" need to close a connection. Its just that when this method was created, it was
		 * created to solve the open cursors problem caused on the Job Q Management page.
		 */

		try
		{
			if(conn != null && !conn.isClosed())
			{
				conn.close();
			}
		}
		catch(SQLException sqle)
		{
			//catch silently if close fails.
		}
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param rs RowSet
	 */
	public static void closeRowSet(RowSet rs)
	{
		String methodName = "closeRowSet";

		try
		{
			if(rs != null)
			{
				// These statements make the log very unreadable due to their frequency.
				// Now having gone to a cached connection mechanism, these can be disabled pending further issues.
				// logger.debug("Closing RowSet in SQLUtil->closeRowSet(RowSet)");
				rs.close();
			}
		}
		catch(SQLException sqle)
		{
			//catch silently if close fails.
		}
	}
	/*****************************************************************************************/
	/**
	 * @param rs ResultSet
	 */
	public static void closeResultSet(ResultSet rs)
	{
		String methodName = "closeResultSet";

		try
		{
			if(rs != null)
			{
				rs.close();
			}
		}
		catch(SQLException sqle)
		{
			//catch silently if close fails.
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param cs CallableStatement
	 */
	public static void closeCS(CallableStatement cs)
	{
		String methodName = "closeCS";

		try
		{
			if(cs != null)
			{
				cs.close();
			}
		}
		catch(SQLException sqle)
		{
			//catch silently if close fails.
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param ps PreparedStatement
	 */
	public static void closePS(PreparedStatement ps)
	{
		String methodName = "closePS";

		try
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		catch(SQLException sqle)
		{
			//catch silently if close fails.
		}
	}
	/*****************************************************************************************/
	/**
	 * @param stmnt Statement
	 */
	public static void closeStatment(Statement stmnt)
	{
		String methodName = "closeStatement";

		try
		{
			if(stmnt != null)
			{
				stmnt.close();
			}
		}
		catch(SQLException sqle)
		{
			//catch silently if close fails
		}
	}
	/*****************************************************************************************/
	/**
	 * @param conn Connection
	 */
	public static void rollBack(Connection conn)
	{
		String methodName = "rollBack(Connection)";

		try
		{
			conn.rollback();
		}
		catch(SQLException sqle)
		{
			//catch silently if rollback fails
		}
	}
	/*****************************************************************************************/
	/**
	 * @param conn Connection to Oracle
	 */
	public static void enableAutoCommit(Connection conn)
	{
		String methodName = "setAutoCommit(Connection)";
		try
		{
			conn.setAutoCommit(true);
		}
		catch(SQLException sqle)
		{
			//catch silently if it fails.
		}
	}

	/**
	 *
	 * @return the name of the datasource schema
	 */
	public static String getDatasourceSchemaName() throws TCGMException
	{
		String methodName="getDatasourceSchemaName()";
		if(datasourceSchemaName == null)
			throw new TCGMException(className,methodName,"Datasource Schema Name is not set in SQLUtil");
		else
			return datasourceSchemaName;
	}

	/**
	 *
	 * @return The url to use in making the connection to the datasource
	 */
	public static String getDatasourceURL() throws TCGMException
	{
		String methodName="getDatasourceURL()";
		if(datasourceURL == null)
			throw new TCGMException(className ,methodName,"Datasource is not set in SQLUtil");
		else
			return datasourceURL;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return The admin User object for Oracle
	 */
	public static UserToken getOracleAdmin()
	{
		return oracleAdmin;
	}
	/**
	 *
	 * @param pOracleAdmin The admin User object for the Oracle
	 */
}