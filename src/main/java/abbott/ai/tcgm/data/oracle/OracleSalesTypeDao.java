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
 * <p>Description: Oracle Specific implementation of the SalesType Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleSalesTypeDao extends OracleDao implements SalesTypeDao
{
	private SalesType searchObject = null;
	private Sort sortObject = new Sort("SLS_TYP","ASC");
	private final static String SELECT = "SELECT CATEGORY, SLS_TYP, DIVISION_CODE, DIVISION_NAME, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM ";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param sortObject Sort object
	 */
	public OracleSalesTypeDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable("T_SALES_TYPE");
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject SalesType object
	 * @param sortObject Sort object
	 */
	public OracleSalesTypeDao(UserToken userToken,SalesType searchObject,Sort sortObject)
	{
		this.setEntityTable("T_SALES_TYPE");
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject SalesType object
	 */
	public OracleSalesTypeDao(UserToken userToken,SalesType searchObject)
	{
		this.setEntityTable("T_SALES_TYPE");
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleSalesTypeDao(UserToken userToken)
	{
		this.setEntityTable("T_SALES_TYPE");
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject SalesType object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(SalesType searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(SalesType,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject SalesType object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(SalesType searchObject) throws TCGMException
	{
		String methodName = "getRS(SalesType)";
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

			this.logger.debug("OracleSalesType - QUERY: " + query);

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
	 * @param searchObject SalesType object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of SalesType objects
	 * @throws TCGMException
	 */
	public Vector getVO(SalesType searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(SalesType,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject SalesType object with search criteria
	 * @return Vector of SalesType objects
	 * @throws TCGMException
	 */
	public Vector getVO(SalesType searchObject) throws TCGMException
	{
		String methodName="getVO(SalesType)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of SalesType objects
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
				vec.add(this.getSalesTypeFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an SalesType object
	 * @param rs RowSet
	 * @return SalesType
	 * @throws TCGMException
	 */
	public SalesType getSalesTypeFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getSalesTypeFromCurrentRow(RowSet)";

		SalesType slsType = new SalesType();

		try
		{
			slsType.setCategory(rs.getString("CATEGORY"));
			slsType.setSlsType(rs.getString("SLS_TYP"));
			slsType.setDivisionCode(rs.getString("DIVISION_CODE"));
			slsType.setDivisionName(rs.getString("DIVISION_NAME"));
			slsType.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			slsType.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			slsType.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			slsType.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			slsType.setNewSlsType(false);

			return slsType;
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
		this.searchList.add(new Search("SLS_TYP",searchObject.getSlsType(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search("CATEGORY",searchObject.getCategory(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search("DIVISION_CODE",searchObject.getDivisionCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search("DIVISION_NAME",searchObject.getDivisionName(),TCGMConstants.ORACLE_LIKE_COMPARISON));	}
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
	 * @return SalesType
	 * @throws TCGMException
	 */
	public SalesType getSalesTypeBySlsType() throws TCGMException
	{
		String methodName = "getSalesTypeBySlsType()";

		Vector vec = new Vector();

		vec = this.getVO();

		if(vec.size() == 0)
		{
			throw new TCGMException(className,methodName,"Unable to locate slsType record");
		}

		return (SalesType)vec.elementAt(0);
	}
	/*****************************************************************************************/
	/**
	 * @param slsTypeToInsert SalesType
	 * @throws TCGMException
	 */
	public void insert(UserToken userToken,SalesType slsTypeToInsert) throws TCGMException
	{
		String methodName = "insert(SalesType)";

		Connection conn = SQLUtil.openConnection();
		
		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.T_SALES_TYPE " +
			   "(CATEGORY, SLS_TYP, DIVISION_CODE, DIVISION_NAME, " +
			   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME) " +
			   "VALUES(?,?,?,?,'" + userToken.getUserid() + "',SYSDATE,'" + userToken.getUserid() + "',SYSDATE)";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, slsTypeToInsert.getCategory());
			ps.setString( 2, slsTypeToInsert.getSlsType());
			ps.setString( 3, slsTypeToInsert.getDivisionCode());
			ps.setString( 4, slsTypeToInsert.getDivisionName());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-02291") >= 0)
			{
				throw new TCGMException(this.className,methodName,"SLS TYPE is Invalid");
			}
			else if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, slsTypeToInsert.toString(), sqle.toString());
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
	 * @param slsTypeToUpdate SalesType
	 * @throws TCGMException
	 */
	public void update(UserToken userToken,SalesType slsTypeToUpdate) throws TCGMException
	{
		String methodName = "update(SalesType)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "UPDATE TCGM.T_SALES_TYPE " +
			   "SET " +
			   "CATEGORY = ?," +
			   "DIVISION_CODE = ?, " +
			   "DIVISION_NAME = ?, " +
			   "MODIFY_USERNAME = '" + userToken.getUserid() + "', " +
			   "MODIFY_DATETIME = SYSDATE " +
			   "WHERE TRIM(UPPER(SLS_TYP)) = ?";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, slsTypeToUpdate.getCategory());
			ps.setString( 2, slsTypeToUpdate.getDivisionCode());
			ps.setString( 3, slsTypeToUpdate.getDivisionName());
			ps.setString( 4, slsTypeToUpdate.getSlsType());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName,slsTypeToUpdate.toString(),sqle.toString());
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
	 * @param slsTypeToDelete Vector
	 * @throws TCGMException
	 */
	public void delete(Vector slsTypeToDelete) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < slsTypeToDelete.size();i++)
			{
				this.delete((SalesType)slsTypeToDelete.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param slsTypeToDelete SalesType
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(SalesType slsTypeToDelete,Connection conn) throws TCGMException
	{
		String methodName = "delete(SalesType,Connection)";

		boolean connWasNull = false;

		this.setSearchObject(slsTypeToDelete);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity() + " WHERE TRIM(UPPER(SLS_TYP)) = '" + slsTypeToDelete.getSlsType() + "' ";

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
	 * @param searchObject SalesType
	 */
	private void setSearchObject(SalesType searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private SalesType getSearchObject()
	{
		return this.searchObject;
	}
}