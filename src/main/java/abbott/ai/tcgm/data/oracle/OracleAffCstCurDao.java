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
 * <p>Description: Oracle Specific implementation of the AffCstCur Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleAffCstCurDao extends OracleDao implements AffCstCurDao
{
	private AffCstCur searchObject = null;
	private Sort sortObject = DBConst.DEF_SORT_AFFCSTCUR;
	private final static String SELECT = "SELECT AFF,AFF_DESC,CUR_CD, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM ";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param sortObject Sort object
	 */
	public OracleAffCstCurDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_AFFCSTCUR);
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject AffCstCur object
	 * @param sortObject Sort object
	 */
	public OracleAffCstCurDao(UserToken userToken,AffCstCur searchObject,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_AFFCSTCUR);
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject AffCstCur object
	 */
	public OracleAffCstCurDao(UserToken userToken,AffCstCur searchObject)
	{
		this.setEntityTable(DBConst.TABLE_AFFCSTCUR);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleAffCstCurDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_AFFCSTCUR);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject AffCstCur object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffCstCur searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(AffCstCur,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject AffCstCur object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffCstCur searchObject) throws TCGMException
	{
		String methodName = "getRS(AffCstCur)";
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

			this.logger.debug("OracleAffCstCurDao - QUERY: " + query);

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
	 * @param searchObject AffCstCur object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getVO(AffCstCur searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(AffCstCur,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject AffCstCur object with search criteria
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getVO(AffCstCur searchObject) throws TCGMException
	{
		String methodName="getVO(AffCstCur)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of AffCstCur objects
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
				vec.add(this.getAffCstCurFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an AffCstCur object
	 * @param rs RowSet
	 * @return AffCstCur
	 * @throws TCGMException
	 */
	public AffCstCur getAffCstCurFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getAffCstCurFromCurrentRow(RowSet)";

		AffCstCur affCstCur = new AffCstCur();

		try
		{
			affCstCur.setAff(rs.getString(DBConst.COL_AFF));
			affCstCur.setAffDesc(rs.getString(DBConst.COL_AFF_DESC));
			affCstCur.setCurCode(rs.getString(DBConst.COL_CUR_CD));
			affCstCur.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			affCstCur.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			affCstCur.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			affCstCur.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			affCstCur.setNewAffCstCur(false);

			return affCstCur;
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
		this.searchList.add(new Search(DBConst.COL_AFF,searchObject.getAff(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_AFF_DESC,searchObject.getAffDesc(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CUR_CD,searchObject.getCurCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
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
	 * @return AffCstCur
	 * @throws TCGMException
	 */
	public AffCstCur getAffCstCurByAff() throws TCGMException
	{
		String methodName = "getAffCstCurByAff()";

		Vector vec = new Vector();

		vec = this.getVO();

		if(vec.size() == 0)
		{
			throw new TCGMException(className,methodName,"Unable to locate affCstCur record");
		}

		return (AffCstCur)vec.elementAt(0);
	}
	/*****************************************************************************************/
	/**
	 * @param affCstCurToInsert AffCstCur
	 * @throws TCGMException
	 */
	public void insert(UserToken userToken,AffCstCur affCstCurToInsert) throws TCGMException
	{
		String methodName = "insert(AffCstCur)";

		Connection conn = SQLUtil.openConnection();
		
		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.T_AFFCSTCUR " +
			   "(AFF,AFF_DESC,CUR_CD,CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME) " +
			   "VALUES(?,?,?,'" + userToken.getUserid() + "',SYSDATE,'" + userToken.getUserid() + "',SYSDATE)";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, affCstCurToInsert.getAff());
			ps.setString( 2, affCstCurToInsert.getAffDesc());
			ps.setString( 3, affCstCurToInsert.getCurCode());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-02291") >= 0)
			{
				throw new TCGMException(this.className,methodName,"Currency Code is Invalid");
			}
			else if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, affCstCurToInsert.toString(), sqle.toString());
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
	 * @param affCstCurToUpdate AffCstCur
	 * @throws TCGMException
	 */
	public void update(UserToken userToken,AffCstCur affCstCurToUpdate) throws TCGMException
	{
		String methodName = "update(AffCstCur)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "UPDATE TCGM.T_AFFCSTCUR " +
			   "SET " +
			   "AFF_DESC = ?," +
			   "CUR_CD = ?, " +
			   "MODIFY_USERNAME = '" + userToken.getUserid() + "', " +
			   "MODIFY_DATETIME = SYSDATE " +
			   "WHERE TRIM(UPPER(AFF)) = ?";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, affCstCurToUpdate.getAffDesc());
			ps.setString( 2, affCstCurToUpdate.getCurCode());
			ps.setString( 3, affCstCurToUpdate.getAff());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName,affCstCurToUpdate.toString(),sqle.toString());
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
	 * @param affCstCurToDelete Vector
	 * @throws TCGMException
	 */
	public void delete(Vector affCstCurToDelete) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < affCstCurToDelete.size();i++)
			{
				this.delete((AffCstCur)affCstCurToDelete.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param affCstCurToDelete AffCstCur
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(AffCstCur affCstCurToDelete,Connection conn) throws TCGMException
	{
		String methodName = "delete(AffCstCur,Connection)";

		boolean connWasNull = false;

		this.setSearchObject(affCstCurToDelete);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity() + " WHERE TRIM(UPPER(AFF)) = '" + affCstCurToDelete.getAff() + "' ";

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
	 * @param searchObject AffCstCur
	 */
	private void setSearchObject(AffCstCur searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private AffCstCur getSearchObject()
	{
		return this.searchObject;
	}
}