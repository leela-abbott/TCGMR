package abbott.ai.tcgm.data.oracle;

import java.util.*;
import javax.sql.*;

import org.apache.log4j.Logger;

import java.sql.*;

import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;

//import oracle.jdbc.OracleTypes;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: User entity bean</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave FIelds
 * @version 1.0
 */
public class OracleUserDao extends OracleDao implements UserDao
{
	private static Logger myLogger = Logger.getLogger( "OracleUserDao" );
	private final String className = this.getClass().getName();
	private User searchObject = null;
	private Sort sortObject = DBConst.DEF_SORT_USER;
	private final static String SELECT = "SELECT USER_INFO_ID,USERNAME,FIRST_NAME,LAST_NAME,EMAIL,PHONE,EMP_NUM,CREATE_USERNAME," +
							   "CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME,GRANTED_ROLE FROM ";
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param sortObject Sort
	 */
	public OracleUserDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_USER_INFO);
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject User
	 * @param sortObject Sort
	 */
	public OracleUserDao(UserToken userToken,User searchObject,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_USER_INFO);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
	}
	/**
	 *
	 * @param userToken Object with the user id and password
	 * @param searchObject User object with search criteria
	 */
	public OracleUserDao(UserToken userToken,User searchObject)
	{
		this.setEntityTable(DBConst.TABLE_USER_INFO);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}


	/**
	 *
	 * @param userToken Object with the user id and password
	 */
	public OracleUserDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_USER_INFO);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * @param searchObject User object with parameters to search by
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(User searchObject) throws TCGMException
	{
		this.setSearchObject(searchObject);
		return getRS();
	}
	/**
	 *
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException
	{
		String methodName = "getRS()";

		try
		{
			String query = this.SELECT + this.getEntity() + this.genWhereClause() + this.buildSortClause(this.sortObject);

			this.logger.debug("OracleUserDao - QUERY: " + query);

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
	 * @param searchObject User object with paramters to search by
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getVO(User searchObject) throws TCGMException
	{
		this.setSearchObject(searchObject);
		return getVO();
	}
	/**
	 *
	 * @return Vector of User objects
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
				vec.add(this.getUserFromCurrentRow(rs,false));
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
	/*****************************************************************************************/
	/**
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_USERNAME,searchObject.getUserid(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_FIRST_NAME,searchObject.getFirstName(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_LAST_NAME,searchObject.getLastName(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_EMAIL,searchObject.getEmail(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_PHONE,searchObject.getPhone(),TCGMConstants.ORACLE_LIKE_COMPARISON));
	}
	/*****************************************************************************************/
	/**
	 * This method will be used to convert the "next()" RowSet ojbect to a User object
	 * The TCGM Role is retrieved from a view in the SYS schema.  The view will only display roles for
	 * the user that is currently logged in.  Because of this we will not be retrieving the user's role
	 * for maint screens but we will be retrieving it upon login because it needs to be in the session
	 * user object.
	 * @param rs RowSet
	 * @param getRole boolean Determines if the Role column should be retrieved.  It will not always be available.
	 * @return User
	 * @throws TCGMException
	 */
	public User getUserFromCurrentRow(RowSet rs,boolean getRole) throws TCGMException
	{
		String methodName = "getUserFromCurrentRow(RowSet)";

		User user = new User();

		try
		{
			user.setUserinfoid(rs.getString(DBConst.COL_USER_INFO_ID));
			user.setUserid(rs.getString(DBConst.COL_USERNAME));
			user.setFirstName(rs.getString(DBConst.COL_FIRST_NAME));
			user.setLastName(rs.getString(DBConst.COL_LAST_NAME));
			user.setEmail(rs.getString(DBConst.COL_EMAIL));
			user.setPhone(rs.getString(DBConst.COL_PHONE));
			user.setAbtNotesId(rs.getString(DBConst.COL_LOTUSID));
			user.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			user.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			user.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			user.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			user.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			user.setUserRole(rs.getString(DBConst.COL_GRANTED_ROLE).substring(5));



			if(getRole)
			{
				user.setRole(Role.getRole(rs.getString(DBConst.COL_GRANTED_ROLE)));
			}

			return user;
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
	 *
	 * @param user User object
	 * @return true if user was successfully loaded else false
	 * @throws TCGMException
	 */
	public boolean loadUser(User user) throws TCGMException
	{
		String methodName = "loadUser";
		int counter = 0;
// 11-13-05 Temporarily delete and use select below to select from USER_INFO only
//		String sql = "SELECT U.USER_INFO_ID,U.USERNAME,U.FIRST_NAME,U.LAST_NAME,U.EMAIL,U.PHONE,U.CREATE_USERNAME,U.CREATE_DATETIME," +
//			   "U.MODIFY_USERNAME,U.MODIFY_DATETIME,URP.GRANTED_ROLE " +
//			   "FROM " + this.getEntity() + " U, SYS.USER_ROLE_PRIVS URP " +
//			   "WHERE UPPER(U.USERNAME) = UPPER('" + user.getUserid() + "') AND " +
//			   "URP.USERNAME = UPPER('" + user.getUserid() + "') AND " +
//			   "URP.GRANTED_ROLE LIKE 'TCGM%'";

		// 11-13-05 The GRANTED_ROLE field will be added to the USER_INFO table later for
		//          selection. For now, just hard-code the role below.
		String sql = "SELECT USER_INFO_ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PHONE, CREATE_USERNAME, CREATE_DATETIME, " +
			   "MODIFY_USERNAME, MODIFY_DATETIME, GRANTED_ROLE, EMP_NUM " +
			   "FROM " + this.getEntity()  +
			   "WHERE UPPER(USERNAME) = UPPER('" + user.getUserid() + "') ";

		this.logger.debug("loadUser SQL: " + sql);

		this.initRS(sql,TCGMConstants.JDBC_ROWSET);

		try
		{
			myLogger.debug("Executing Resultset statement from OracleUserDao.loadUser()...");
			rs.execute();
			myLogger.debug("Resultset executed OK from OracleUserDao.loadUser()!");
			while(rs.next())
			{
				if(counter > 0) //more than 1 role was found for this user so throw an exception
				{
					throw new TCGMException(className,methodName,"User: " + user.getUserid() + " has more than 1 role assigned.");
				}
				User tempUser = this.getUserFromCurrentRow(rs,true);

				user.setCreateLog(tempUser.getCreateLog());
				user.setEmail(tempUser.getEmail());
				user.setFirstName(tempUser.getFirstName());
				user.setLastName(tempUser.getLastName());
				user.setModifyLog(tempUser.getModifyLog());
				user.setPhone(tempUser.getPhone());
				user.setRole(tempUser.getRole());
				user.setUserinfoid(tempUser.getUserinfoid());

				counter++;
			}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className ,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs); 
		}

		if(counter == 0) //then never went into the loop above and no records were found
		{
			return false;
		}

		return true;
	}
	
	public void checkRptUser(User user) throws TCGMException
	{
		String methodName = "checkRptUser";
		
		String sql = "SELECT COUNT(*)" +
			   " FROM REPORT_USERS"  +
			   " WHERE UPPER(USER_ID) = UPPER('" + user.getUserid() + "') ";

		this.logger.debug("loadUser SQL: " + sql);

		this.initRS(sql,TCGMConstants.JDBC_ROWSET);

		try
		{
			myLogger.debug("Executing Resultset statement from OracleUserDao.loadUser()...");
			rs.execute();
			myLogger.debug("Resultset executed OK from OracleUserDao.loadUser()!");
			if(rs.next())
			{
				if(rs.getInt(1)>0)
				user.setRptAccess(true);
			
			}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className ,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs); 
		}

	}

	/*****************************************************************************************/
	/**
	 * @return User
	 * @throws TCGMException
	 */
	public User getUserById() throws TCGMException
	{
		String methodName = "getUserById(String)";

		Vector vec = new Vector();

		vec = this.getVO();

		if(vec.size() == 0)
		{
			throw new TCGMException(className,methodName,"Unable to locate user id record");
		}

		return (User)vec.elementAt(0);

	}
	/*****************************************************************************************/
	/**
	 * @param userToInsert User
	 * @throws TCGMException
	 */
	public void insert(User userToInsert) throws TCGMException
	{
		String methodName = "insert(User)";

		if (this.exists( userToInsert.getUserid() ))
		   {
		       String errMsg = "A user already exist with the name: " + userToInsert.getUserid();
			   myLogger.error(errMsg);
			   throw new TCGMDuplicateItemException( "OracleUserDao", methodName, errMsg );
		   }
		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.USER_INFO " +
			   "(USER_INFO_ID,USERNAME,FIRST_NAME,LAST_NAME,EMAIL,PHONE,GRANTED_ROLE," +
			   "EMP_DIVISION,EMP_TYPE,EMP_NUM,MODIFY_USERNAME,MODIFY_DATETIME) " +
			   "VALUES(TCGM.USER_INFO_ID_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,USER,SYSDATE)";

		try
		{
			ps = conn.prepareStatement(sql); 

			ps.setString( 1, userToInsert.getUserid());
			ps.setString( 2, userToInsert.getFirstName());
			ps.setString( 3, userToInsert.getLastName());
			ps.setString( 4, userToInsert.getEmail());
			ps.setString( 5, userToInsert.getPhone());
			ps.setString( 6, "TCGM_"+userToInsert.getUserRole());
			ps.setString( 7, userToInsert.getDivision());
			ps.setString( 8, userToInsert.getEmployeeType());
			ps.setString( 9, userToInsert.getAbtNotesId());

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, userToInsert.toString(), sqle.toString());
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
	 * @param userToUpdate User
	 * @throws TCGMException
	 */
	public void update(User userToUpdate) throws TCGMException
	{
		String methodName = "update(User)";

		Connection conn = SQLUtil.openConnection();

		PreparedStatement ps = null;
		String sql = "UPDATE TCGM.USER_INFO " +
			   "SET " +
			   "USERNAME = ?, " +
			   "FIRST_NAME = ?, " +
			   "LAST_NAME = ?, " +
		       "GRANTED_ROLE = ?, " +
			   "EMAIL = ?, " +
			   "PHONE = ?, " +
			   "MODIFY_USERNAME = USER, " +
			   "MODIFY_DATETIME = SYSDATE " +
			   "WHERE USER_INFO_ID = ?";
		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, userToUpdate.getUserid());
			ps.setString( 2, userToUpdate.getFirstName());
			ps.setString( 3, userToUpdate.getLastName());
			ps.setString( 4, "TCGM_"+userToUpdate.getUserRole());
			ps.setString( 5, userToUpdate.getEmail());
			ps.setString( 6, userToUpdate.getPhone());
			ps.setInt( 7, Integer.parseInt(userToUpdate.getUserinfoid()));

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it.
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName,userToUpdate.toString(),sqle.toString());
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
	 * @param usersToDelete Vector
	 * @throws TCGMException
	 */
	public void delete(Vector usersToDelete) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < usersToDelete.size();i++)
			{
				this.delete((User)usersToDelete.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param userToDelete User
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(User userToDelete,Connection conn) throws TCGMException
	{
		String methodName = "delete(User,Connection)";

		boolean connWasNull = false;

		this.setSearchObject(userToDelete);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity() + " WHERE USER_INFO_ID = ? ";

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

			ps.setLong(1,Long.parseLong(userToDelete.getUserinfoid()));

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
	 * @param searchObject User
	 */
	private void setSearchObject(User searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private User getSearchObject()
	{
		return this.searchObject;
	}
	
	/**
	   * @param userid
	   * @return
	   * @throws TCGMException
	   */
	  public boolean exists(String userID) throws TCGMException
	  {
		String methodName = "exists(String)";
	    String parameterList = "userID Name is: " + userID;

		Statement stmnt = null;
		Connection conn = null;
		ResultSet rs    = null;
		String sqlQry = "SELECT USERNAME FROM USER_INFO WHERE USERNAME = '"+userID+"'";
		try
			{
				conn = getConnection();
				stmnt = conn.createStatement();
				rs = stmnt.executeQuery(sqlQry);
				
				if(rs.next()){
				return true;
				}else{
				   return false;
				}
									
			}
		catch (SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, parameterList, sqle.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(stmnt);
			SQLUtil.closeConnection(conn);
		}
	}
}