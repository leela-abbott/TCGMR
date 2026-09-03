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
 * <p>Description: Oracle Specific implementation of the PRMfg Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OraclePRMfgDao extends OracleDao implements PRMfgDao
{
	private PRMfg searchObject = null;
	private Sort sortObject = DBConst.DEF_SORT_PRMFG;
	private final static String SELECT = "SELECT SUP_AFF, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM ";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param sortObject Sort object
	 */
	public OraclePRMfgDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_PRMFG);
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject PRMfg object
	 * @param sortObject Sort object
	 */
	public OraclePRMfgDao(UserToken userToken,PRMfg searchObject,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_PRMFG);
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject PRMfg object
	 */
	public OraclePRMfgDao(UserToken userToken,PRMfg searchObject)
	{
		this.setEntityTable(DBConst.TABLE_PRMFG);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OraclePRMfgDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_PRMFG);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject PRMfg object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(PRMfg searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(PRMfg,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject PRMfg object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(PRMfg searchObject) throws TCGMException
	{
		String methodName = "getRS(PRMfg)";
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

			this.logger.debug("OraclePRMfgDao - QUERY: " + query);

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
	 * @param searchObject PRMfg object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getVO(PRMfg searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(PRMfg,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject PRMfg object with search criteria
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getVO(PRMfg searchObject) throws TCGMException
	{
		String methodName="getVO(PRMfg)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of PRMfg objects
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
				vec.add(this.getPRMfgFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an PRMfg object
	 * @param rs RowSet
	 * @return PRMfg
	 * @throws TCGMException
	 */
	public PRMfg getPRMfgFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getPRMfgFromCurrentRow(RowSet)";

		PRMfg prMfg = new PRMfg();

		try
		{
			prMfg.setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			prMfg.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			prMfg.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			prMfg.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			prMfg.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return prMfg;
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
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
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
	 * @param prMfgToInsert PRMfg
	 * @throws TCGMException
	 */
	public void insert(PRMfg prMfgToInsert) throws TCGMException
	{
		String methodName = "insert(PRMfg)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.T_PR_MFG " +
			   "(SUP_AFF,MODIFY_USERNAME,MODIFY_DATETIME,DATASET_TABLE_ID) " +
			   "VALUES(?,USER,SYSDATE,-1)";
		try
		{ 
			ps = conn.prepareStatement(sql);

			ps.setString( 1, prMfgToInsert.getSupAff());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, prMfgToInsert.toString(), sqle.toString());
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
	 * @param prMfgToDelete Vector
	 * @throws TCGMException
	 */
	public void delete(Vector prMfgToDelete) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < prMfgToDelete.size();i++)
			{
				this.delete((PRMfg)prMfgToDelete.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param prMfgToDelete PRMfg
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(PRMfg prMfgToDelete,Connection conn) throws TCGMException
	{
		String methodName = "delete(PRMfg,Connection)";

		boolean connWasNull = false;

		this.setSearchObject(prMfgToDelete);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity() + " WHERE TRIM(UPPER(SUP_AFF)) = '" + prMfgToDelete.getSupAff()+ "'";

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
	 * @param searchObject PRMfg
	 */
	private void setSearchObject(PRMfg searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private PRMfg getSearchObject()
	{
		return this.searchObject;
	}
}