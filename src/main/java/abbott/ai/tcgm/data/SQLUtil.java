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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;

public final class SQLUtil
{
	private static String className = SQLUtil.class.getName();
	private static SQLUtil instance = null;
	private static String datasourceSchemaName;
	private static String datasourceURL;
	private static UserToken oracleAdmin = null;
	private static final Logger logger = LogManager.getLogger("abbott.ai.tcgm.data.SQLUtil");
	private static java.util.HashMap ucMap = new java.util.HashMap();

	public static void init(jakarta.servlet.ServletConfig sc)
	{
		if(instance == null)
		{
			instance = new SQLUtil();
		}
		
		String rawUrl = sc.getInitParameter(TCGMConstants.DATASOURCE_URL);
		if(rawUrl != null)
		{
			instance.datasourceURL = rawUrl.trim().replaceAll("\\s+", "");
		}
		
		if(instance.datasourceURL == null || datasourceURL.trim().equals(""))
		{
			logger.error(TCGMConstants.ERROR_DATASOURCE_URL_NOT_FOUND);
		}
		instance.datasourceSchemaName = TCGMConstants.DATASOURCE_SCHEMA_NAME;

		String oracleId = sc.getInitParameter(TCGMConstants.ORACLE_ID);
		if(oracleId != null)
		{
			oracleId = oracleId.trim();
		}
		if(oracleId == null || oracleId.trim().equals(""))
		{
			logger.error(TCGMConstants.ERROR_ORACLE_ID_NOT_FOUND);
		}

		String oraclePswd = sc.getInitParameter(TCGMConstants.ORACLE_PSWD);
		if(oraclePswd != null)
		{
			oraclePswd = oraclePswd.trim();
		}
		if(oraclePswd == null || oraclePswd.trim().equals(""))
		{
			logger.error(TCGMConstants.ERROR_ORACLE_PSWD_NOT_FOUND);
		}
		instance.oracleAdmin = new UserToken(oracleId,oraclePswd);
	}

	public static SQLUtil getInstance()
	{
		return instance;
	}

	public static Connection openConnection() throws TCGMException
	{
		Connection conn = null;
		String methodName = "openConnection";
		DataSource ds = null;
		
		try 
		{
			InitialContext initContext = new InitialContext();
			try 
			{
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				ds = (DataSource) envContext.lookup("jdbc/tcgm");
			} 
			catch (NamingException ne) 
			{
				try 
				{
					ds = (DataSource) initContext.lookup("java:comp/env/jdbc/tcgm");
				} 
				catch (NamingException ne2) 
				{
					ds = (DataSource) initContext.lookup("jdbc/tcgm");
				}
			}
			
			if (ds != null) 
			{
				conn = ds.getConnection();
			} 
			else 
			{
				throw new TCGMException(className, methodName, "DataSource lookup returned null.");
			}
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
		return conn;
	}

	public static void closeCachedConnection(UserToken userToken)
	{
	}

	public static void closeConnection(Connection conn)
	{
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
		try
		{
			if(conn != null && !conn.isClosed())
			{
				conn.close();
			}
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void closeRowSet(RowSet rs)
	{
		try
		{
			if(rs != null)
			{
				rs.close();
			}
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void closeResultSet(ResultSet rs)
	{
		try
		{
			if(rs != null)
			{
				rs.close();
			}
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void closeCS(CallableStatement cs)
	{
		try
		{
			if(cs != null)
			{
				cs.close();
			}
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void closePS(PreparedStatement ps)
	{
		try
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void closeStatment(Statement stmnt)
	{
		try
		{
			if(stmnt != null)
			{
				stmnt.close();
			}
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void rollBack(Connection conn)
	{
		try
		{
			conn.rollback();
		}
		catch(SQLException sqle)
		{
		}
	}

	public static void enableAutoCommit(Connection conn)
	{
		try
		{
			conn.setAutoCommit(true);
		}
		catch(SQLException sqle)
		{
		}
	}

	public static String getDatasourceSchemaName() throws TCGMException
	{
		String methodName="getDatasourceSchemaName()";
		if(datasourceSchemaName == null)
			throw new TCGMException(className,methodName,"Datasource Schema Name is not set in SQLUtil");
		else
			return datasourceSchemaName;
	}

	public static String getDatasourceURL() throws TCGMException
	{
		String methodName="getDatasourceURL()";
		if(datasourceURL == null)
			throw new TCGMException(className ,methodName,"Datasource is not set in SQLUtil");
		else
			return datasourceURL;
	}

	public static UserToken getOracleAdmin()
	{
		return oracleAdmin;
	}
}
