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
 * <p>Description: Oracle Specific implementation of the KnollConv Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleKnollConvDao extends OracleDao implements KnollConvDao
{
	private KnollConv searchObject = null;
	private Sort sortObject = DBConst.DEF_SORT_KNOLLCONV;
	private final static String SELECT = "SELECT SUP_AFF,CNV_AFF, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM ";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param sortObject Sort object
	 */
	public OracleKnollConvDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_KNOLLCONV);
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject KnollConv object
	 * @param sortObject Sort object
	 */
	public OracleKnollConvDao(UserToken userToken,KnollConv searchObject,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_KNOLLCONV);
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject KnollConv object
	 */
	public OracleKnollConvDao(UserToken userToken,KnollConv searchObject)
	{
		this.setEntityTable(DBConst.TABLE_KNOLLCONV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleKnollConvDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_KNOLLCONV);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject KnollConv object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(KnollConv searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(KnollConv,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject KnollConv object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(KnollConv searchObject) throws TCGMException
	{
		String methodName = "getRS(KnollConv)";
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

			this.logger.debug("OracleKnollConvDao - QUERY: " + query);

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
	 * @param searchObject KnollConv object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getVO(KnollConv searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(KnollConv,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject KnollConv object with search criteria
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getVO(KnollConv searchObject) throws TCGMException
	{
		String methodName="getVO(KnollConv)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of KnollConv objects
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
				vec.add(this.getKnollConvFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an KnollConv object
	 * @param rs RowSet
	 * @return KnollConv
	 * @throws TCGMException
	 */
	public KnollConv getKnollConvFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getKnollConvFromCurrentRow(RowSet)";

		KnollConv knollConv = new KnollConv();

		try
		{
			knollConv.setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			knollConv.setConvAff(rs.getString(DBConst.COL_CNV_AFF));
			knollConv.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			knollConv.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			knollConv.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			knollConv.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return knollConv;
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
		this.searchList.add(new Search(DBConst.COL_CNV_AFF,searchObject.getConvAff(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
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
	 * @param knollConvToInsert KnollConv
	 * @throws TCGMException
	 */
	public void insert(KnollConv knollConvToInsert) throws TCGMException
	{
		String methodName = "insert(KnollConv)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.T_KNOLL_CNVT " +
			   "(SUP_AFF,CNV_AFF,MODIFY_USERNAME,MODIFY_DATETIME) " +
			   "VALUES(?,?,USER,SYSDATE)";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, knollConvToInsert.getSupAff());
			ps.setString( 2, knollConvToInsert.getConvAff());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, knollConvToInsert.toString(), sqle.toString());
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
	 * @param knollConvToDelete Vector
	 * @throws TCGMException
	 */
	public void delete(Vector knollConvToDelete) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < knollConvToDelete.size();i++)
			{
				this.delete((KnollConv)knollConvToDelete.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param knollConvToDelete KnollConv
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(KnollConv knollConvToDelete,Connection conn) throws TCGMException
	{
		String methodName = "delete(KnollConv,Connection)";

		boolean connWasNull = false;

		this.setSearchObject(knollConvToDelete);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity() + " WHERE TRIM(UPPER(SUP_AFF)) = '" + knollConvToDelete.getSupAff() + "' AND TRIM(UPPER(CNV_AFF)) = '" + knollConvToDelete.getConvAff() + "'";

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
	 * @param searchObject KnollConv
	 */
	private void setSearchObject(KnollConv searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private KnollConv getSearchObject()
	{
		return this.searchObject;
	}
}