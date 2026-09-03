package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM </p>
 * <p>Description: Oracle Specific implementation of the CurrencyCode Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleCurrencyCodeDao extends OracleDao implements CurrencyCodeDao
{
	private CurrencyCode searchObject = null;
	private Sort sortObject = DBConst.DEF_SORT_CURRENCY;
	private final static String SELECT = "SELECT CUR_CD,CUR_NAME, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM ";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param sortObject Sort object
	 */
	public OracleCurrencyCodeDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_CURRENCY);
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject CurrencyCode object
	 * @param sortObject Sort object
	 */
	public OracleCurrencyCodeDao(UserToken userToken,CurrencyCode searchObject,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_CURRENCY);
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject CurrencyCode object
	 */
	public OracleCurrencyCodeDao(UserToken userToken,CurrencyCode searchObject)
	{
		this.setEntityTable(DBConst.TABLE_CURRENCY);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleCurrencyCodeDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_CURRENCY);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject CurrencyCode object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(CurrencyCode searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(CurrencyCode,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject CurrencyCode object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(CurrencyCode searchObject) throws TCGMException
	{
		String methodName = "getRS(CurrencyCode)";
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

		try
		{
			String query = this.SELECT + this.getEntity() + this.genWhereClause() + this.buildSortClause(this.sortObject);

			this.logger.debug("OracleCurrencyCodeDao - QUERY: " + query);

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
	/*****************************************************************************************/
	/**
	 * @param searchObject CurrencyCode object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getVO(CurrencyCode searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(CurrencyCode,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject CurrencyCode object with search criteria
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getVO(CurrencyCode searchObject) throws TCGMException
	{
		String methodName="getVO(CurrencyCode)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of CurrencyCode objects
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
				vec.add(this.getCurrencyCodeFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an CURRENCY object
	 * @param rs RowSet
	 * @return CurrencyCode
	 * @throws TCGMException
	 */
	public CurrencyCode getCurrencyCodeFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getCurrencyCodeFromCurrentRow(RowSet)";

		CurrencyCode currencyCode = new CurrencyCode();

		try
		{
			currencyCode.setCurCode(rs.getString(DBConst.COL_CUR_CD));
			currencyCode.setCurName(rs.getString(DBConst.COL_CUR_NAME));
			currencyCode.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			currencyCode.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			currencyCode.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			currencyCode.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			currencyCode.setNewCurrencyCode(false);

			return currencyCode;
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
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_CUR_CD,searchObject.getCurCode(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CUR_NAME,searchObject.getCurName(),TCGMConstants.ORACLE_LIKE_COMPARISON));
	}

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
		sb.append("\n");
		sb.append(this.sortObject.toString());
		sb.append("\nSearch Object: ");
		sb.append(this.searchObject);

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 * @return CurrencyCode
	 * @throws TCGMException
	 */
	public CurrencyCode getCurrencyByCode() throws TCGMException
	{
		String methodName = "getCurrencyByCode()";

		Vector vec = new Vector();

		vec = this.getVO();

		if(vec.size() == 0)
		{
			throw new TCGMException(className,methodName,"Unable to locate currency record");
		}

		return (CurrencyCode)vec.elementAt(0);
	}
	/*****************************************************************************************/
	/**
	 * @param currencyCodeToInsert CurrencyCode
	 * @throws TCGMException
	 */
	public void insert(CurrencyCode currencyCodeToInsert) throws TCGMException
	{
		String methodName = "insert(CurrencyCode)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.T_CURRENCY " +
			   "(CUR_CD,CUR_NAME,MODIFY_USERNAME,MODIFY_DATETIME) " +
			   "VALUES(?,?,USER,SYSDATE)";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, currencyCodeToInsert.getCurCode());
			ps.setString( 2, currencyCodeToInsert.getCurName());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, currencyCodeToInsert.toString(), sqle.toString());
			}
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException ( className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closePS(ps);
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param currencyCodeToUpdate CurrencyCode
	 * @throws TCGMException
	 */
	public void update(CurrencyCode currencyCodeToUpdate) throws TCGMException
	{
		String methodName = "update(CurrencyCode)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "UPDATE TCGM.T_CURRENCY " +
			   "SET " +
			   "CUR_NAME = ?, " +
			   "MODIFY_USERNAME = USER, " +
			   "MODIFY_DATETIME = SYSDATE " +
			   "WHERE TRIM(UPPER(CUR_CD)) = ?";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, currencyCodeToUpdate.getCurName());
			ps.setString( 2, currencyCodeToUpdate.getCurCode());


			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName,currencyCodeToUpdate.toString(),sqle.toString());
			}
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException ( className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closePS(ps);
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param currencyCodeToDelete Vector
	 * @throws TCGMException
	 */
	public void delete(Vector currencyCodeToDelete) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < currencyCodeToDelete.size();i++)
			{
				this.delete((CurrencyCode)currencyCodeToDelete.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param currencyCodeToDelete CurrencyCode
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(CurrencyCode currencyCodeToDelete,Connection conn) throws TCGMException
	{
		String methodName = "delete(CurrencyCode,Connection)";

		boolean connWasNull = false;

		this.setSearchObject(currencyCodeToDelete);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity() + " WHERE TRIM(UPPER(CUR_CD)) = '" + currencyCodeToDelete.getCurCode() + "' ";

		this.logger.debug("\nSQL: " + sql);

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}

			ps = conn.prepareStatement(sql);

			//ps.setString(1,currencyCodeToDelete.getCurCode());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className, methodName, e.toString());
		}
		finally
		{
			SQLUtil.closePS(ps);
			if(connWasNull)
			{
				//The connection was created within the method and not passed in
				//So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}
	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject CurrencyCode
	 */
	private void setSearchObject(CurrencyCode searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private CurrencyCode getSearchObject()
	{
		return this.searchObject;
	}
}