package abbott.ai.tcgm.data.oracle;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.RptUserDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.ActiveAffMaint;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ReportMngr;

/**
 * <p>Title: TCGM</p>
 * <p>Description: RptUser entity bean</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave FIelds
 * @version 1.0
 */
public class OracleRptUserDao extends OracleDao implements RptUserDao
{
	private static Logger myLogger = Logger.getLogger( "OracleRptUserDao" );
	private final String className = this.getClass().getName();
	private RptUser searchObject = null;
	private Sort sortObject = DBConst.DEF_SORT_USER;
	private final static String SELECT = "SELECT RPT_USER_ID,USER_ID,AFFILIATE, AFFILIATE_DESC, FIRST_NAME,LAST_NAME,RECIPIENT,CREATE_USERNAME," +
							   "CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME FROM ";

	//private final String AFFILIATES_SELECT = "select distinct affiliate VALUE, affiliate_desc NAME from affiliate_burst order by affiliate";
	private final String AFFILIATES_SELECT = "select distinct aff VALUE, adesc NAME from affiliate_burst"; // order by substr(adesc,6,80)";
	private final String SECTORS_SELECT = "select distinct sector VALUE, sdesc NAME from sector_burst"; //  order by substr(sdesc,9,80)";
	private final String AREAS_SELECT = "select distinct area VALUE, adesc NAME from area_burst"; //   order by substr(adesc,5,80)";
	private final String AREAAFFS_SELECT = "select distinct aff VALUE, aff||' - '||aff_desc NAME from "+DBConst.VW_T_AFFILIATE+" where area = '";

	private final String HQAFFS_SELECT = "select distinct aff VALUE, aff||' - '||aff_desc NAME from "+DBConst.VW_T_AFFILIATE+"";

	private final String SECAFFS_SELECT = "select distinct aff VALUE, aff||' - '||aff_desc NAME from "+DBConst.VW_T_AFFILIATE+" where area||region||sector = '";
	private final String DIVISION_SELECT="select distinct Division VALUE, Division NAME from T_AREA";
	
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param sortObject Sort
	 */
	public OracleRptUserDao(UserToken userToken,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_REPORT_SECURITY);
		this.userToken = userToken;
		this.sortObject = sortObject;
	}
	/*
	 * @param userToken Object with the user id and password
	 * @param searchObject User object with search criteria
	 */
	public OracleRptUserDao(RptUser searchObject)
	{
		this.setEntityTable(DBConst.TABLE_REPORT_SECURITY);
		this.setSearchObject(searchObject);
	}
	/*
	 * @param userToken Object with the user id and password
	 * @param searchObject User object with search criteria
	 */
	public OracleRptUserDao()
	{
		this.setEntityTable(DBConst.TABLE_REPORT_SECURITY);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject RptUser
	 * @param sortObject Sort
	 */
	public OracleRptUserDao(UserToken userToken,RptUser searchObject,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_REPORT_SECURITY);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
	}
	/**
	 *
	 * @param userToken Object with the user id and password
	 * @param searchObject RptUser object with search criteria
	 */
	public OracleRptUserDao(UserToken userToken,RptUser searchObject)
	{
		this.setEntityTable(DBConst.TABLE_REPORT_SECURITY);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}


	/**
	 *
	 * @param userToken Object with the user id and password
	 */
	public OracleRptUserDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_REPORT_SECURITY);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * @param searchObject RptUser object with parameters to search by
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RptUser searchObject) throws TCGMException
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
			String query = this.SELECT + this.getEntity() + this.genWhereClause() + " ORDER BY USER_ID ASC";

			this.logger.debug("OracleRptUserDao - QUERY: " + query);

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
	 * @param searchObject RptUser object with paramters to search by
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getVO(RptUser searchObject) throws TCGMException
	{
		this.setSearchObject(searchObject);
		return getVO();
	}
	/**
	 *
	 * @return Vector of RptUser objects
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
			this.searchList.add(new Search("upper(" + DBConst.COL_USERID + ")",searchObject.getUserid(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_FIRST_NAME,searchObject.getFirstName(),TCGMConstants.ORACLE_LIKE_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_LAST_NAME,searchObject.getLastName(),TCGMConstants.ORACLE_LIKE_COMPARISON));
			this.searchList.add(new Search("ROLE",getRole(searchObject),TCGMConstants.ORACLE_EQUALS_COMPARISON));
			//this.searchList.add(new Search("ROLE",searchObject.getRole(),TCGMConstants.ORACLE_LIKE_COMPARISON));
			if(searchObject.getRole().equalsIgnoreCase("-1")&&!searchObject.getDivision().equalsIgnoreCase("-1"))
			this.searchList.add(new Search("ROLE",searchObject.getDivision(),TCGMConstants.ORACLE_LIKE_COMPARISON));

	}
	/*****************************************************************************************/
	/**
	 * This method will be used to convert the "next()" RowSet ojbect to a RptUser object
	 * The TCGM Role is retrieved from a view in the SYS schema.  The view will only display roles for
	 * the user that is currently logged in.  Because of this we will not be retrieving the user's role
	 * for maint screens but we will be retrieving it upon login because it needs to be in the session
	 * user object.
	 * @param rs RowSet
	 * @param getRole boolean Determines if the Role column should be retrieved.  It will not always be available.
	 * @return RptUser
	 * @throws TCGMException
	 */
	public RptUser getUserFromCurrentRow(RowSet rs,boolean getRole) throws TCGMException
	{
		String methodName = "getUserFromCurrentRow(RowSet)";

		RptUser user = new RptUser();

		try
		{
			user.setRptuserinfoid(rs.getString(DBConst.COL_USER_INFO_ID));
			user.setUserid(rs.getString(DBConst.COL_USERNAME));
			user.setFirstName(rs.getString(DBConst.COL_FIRST_NAME));
			user.setLastName(rs.getString(DBConst.COL_LAST_NAME));
			user.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			user.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			user.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			user.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));


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
	 * @param user RptUser object
	 * @return true if user was successfully loaded else false
	 * @throws TCGMException
	 */
	public boolean loadUser(RptUser user) throws TCGMException
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
			   "MODIFY_USERNAME, MODIFY_DATETIME, GRANTED_ROLE " +
			   "FROM " + this.getEntity()  +
			   "WHERE UPPER(USERNAME) = UPPER('" + user.getUserid() + "') ";

		this.logger.debug("loadUser SQL: " + sql);

		this.initRS(sql,TCGMConstants.JDBC_ROWSET);

		try
		{
			myLogger.debug("Executing Resultset statement from OracleRptUserDao.loadUser()...");
			rs.execute();
			myLogger.debug("Resultset executed OK from OracleRptUserDao.loadUser()!");
			while(rs.next())
			{
				if(counter > 0) //more than 1 role was found for this user so throw an exception
				{
					throw new TCGMException(className,methodName,"RptUser: " + user.getUserid() + " has more than 1 role assigned.");
				}
				RptUser tempUser = this.getUserFromCurrentRow(rs,true);

				user.setCreateLog(tempUser.getCreateLog());
				user.setFirstName(tempUser.getFirstName());
				user.setLastName(tempUser.getLastName());
				user.setModifyLog(tempUser.getModifyLog());
				user.setRptuserinfoid(tempUser.getRptuserinfoid());

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
	/*****************************************************************************************/
	/**
	 * @return RptUser
	 * @throws TCGMException
	 */
	public RptUser getUserById() throws TCGMException
	{
		String methodName = "getUserById(String)";

		Vector vec = new Vector();

		vec = this.getVO();

		if(vec.size() == 0)
		{
			throw new TCGMException(className,methodName,"Unable to locate user id record");
		}

		return (RptUser)vec.elementAt(0);

	}
	/*****************************************************************************************/
	/**
	 * @param userToInsert RptUser
	 * @throws TCGMException
	 */
	public void insert(RptUser userToInsert) throws TCGMException
	{
		String methodName = "insert(RptUser)";

		if (this.exists( userToInsert.getUserid() ))
		   {
			   String errMsg = "A user already exist with the name: " + userToInsert.getUserid();
			   myLogger.error(errMsg);
			   throw new TCGMDuplicateItemException( "OracleRptUserDao", methodName, errMsg );
		   }
		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.USER_INFO " +
			   "(USER_INFO_ID,USERNAME,FIRST_NAME,LAST_NAME,EMAIL,PHONE,GRANTED_ROLE,MODIFY_USERNAME,MODIFY_DATETIME) " +
			   "VALUES(TCGM.USER_INFO_ID_SEQ.NEXTVAL,?,?,?,?,?,?,USER,SYSDATE)";

		try
		{
			ps = conn.prepareStatement(sql);

			ps.setString( 1, userToInsert.getUserid());
			ps.setString( 2, userToInsert.getFirstName());
			ps.setString( 3, userToInsert.getLastName());
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
	 * @param userToUpdate RptUser
	 * @throws TCGMException
	 */
	public void update(RptUser userToUpdate) throws TCGMException
	{
		String methodName = "update(RptUser)";

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
			ps.setInt( 4, Integer.parseInt(userToUpdate.getRptuserinfoid()));

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
	 * @param userToDelete RptUser
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(RptUser userToDelete) throws TCGMException
	{
		String methodName = "delete(RptUser)";

		Connection conn = null;

		this.setSearchObject(userToDelete);

		PreparedStatement ps = null;

		String sql = deleteQry(userToDelete);
		this.logger.debug("\nSQL: " + sql);

		try
		{
			conn = SQLUtil.openConnection();
			conn.setAutoCommit(false);
			
			RptUser cogosUser = userToDelete;
			
			ArrayList selectedUserList = new ArrayList();
			selectedUserList.add(cogosUser);
			ReportMngr reportMngr = new ReportMngr();
			reportMngr.userMaintenance(selectedUserList, TCGMConstants.REPORT_CONSTANT_REMOVE_USERS);
			
			deleteRptUser(userToDelete, conn);

			//ps = conn.prepareStatement(sql);

			//ps.execute();
			conn.commit();
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
			SQLUtil.closeConnection(conn);

		}
	}

	private String deleteQry(RptUser rptUser)
		{
			String deleteQry="";
			if(rptUser.getRole().equalsIgnoreCase("-1")){
				deleteQry ="";
			}
			else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				if(rptUser.getDivision().equalsIgnoreCase("All")){
					deleteQry = "DELETE FROM REPORT_SECURITY WHERE USER_ID = '"+rptUser.getUserid()+"'"+
									" and affiliate = '"+rptUser.getAffCode()+"' AND ISGROUP='N'";
				}else{
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"'"+
									" and affiliate = '"+rptUser.getAffCode()+"' and division = '"+rptUser.getDivision()+"' AND ISGROUP='N'";
				}
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				if(rptUser.getDivision().equalsIgnoreCase("All")){
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='A' "+
									" and affiliate in (select distinct aff  from "+DBConst.VW_T_AFFILIATE+" where area='"+rptUser.getAreaCode()+"') ";
				}else{
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='A' "+
									" and affiliate in (select distinct aff  from "+DBConst.VW_T_AFFILIATE+" where area='"+rptUser.getAreaCode()+"')"+
									" and division = '"+rptUser.getDivision()+"'";
				}
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				if(rptUser.getDivision().equalsIgnoreCase("All")){
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='S' "+
									" and affiliate in (select distinct aff  from "+DBConst.VW_T_AFFILIATE+" where area||region||sector='"+rptUser.getSecCode()+"') ";
				}else{
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='S' "+
									" and affiliate in (select distinct aff  from "+DBConst.VW_T_AFFILIATE+" where area||region||sector='"+rptUser.getSecCode()+"')"+
									" and division = '"+rptUser.getDivision()+"'";
				}
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP)){
				deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='H'";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_CON)){
				deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='H'";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)){
				deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='H'";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
				if(rptUser.getDivision().equalsIgnoreCase("All")){
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"' AND ISGROUP='D' ";
				}else{
					deleteQry = "DELETE FROM report_security WHERE USER_ID = '"+rptUser.getUserid()+"'"+
									" and division = '"+rptUser.getDivision()+"' AND ISGROUP='D'";
				}
			}

			return deleteQry;
		}
	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject RptUser
	 */
	private void setSearchObject(RptUser searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private RptUser getSearchObject()
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

	/**
	 * This method returns all affiliates present in AFFILIATE_BURST table.
	 * This HashMap contains the AFFILIATE_CODE (name) and AFFILIATE_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	  public HashMap getAllAffiliates(String division) throws TCGMException
		{
			String methodName = "getAllAffiliates()";

			String sql = this.AFFILIATES_SELECT+" WHERE division='"+division+"'";
			HashMap obj_HashMap = new HashMap();
			Connection conn = null;

			try
			{
				conn = getConnection();
				obj_HashMap = this.getListValues(sql, conn);
			}
			catch (TCGMException obj_TCGMException)
			{
				logException(className, methodName, obj_TCGMException);
				throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
			}
			return obj_HashMap;
		}
	/**
	 * This method returns all sectors present in SECTOR_BURST table.
	 * This HashMap contains the SECTOR_CODE (name) and SECTOR_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	public HashMap getAllSectors(String division) throws TCGMException
	{
		String methodName = "getAllSectors()";

		String sql = this.SECTORS_SELECT+" WHERE division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		return obj_HashMap;
	}
	/**
	 * This method returns all areas present in AREA_BURST table.
	 * This HashMap contains the AREA_CODE (name) and AREA_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	public HashMap getAllAreas(String division) throws TCGMException
	{
		String methodName = "getAllAreas()";

		String sql = this.AREAS_SELECT+" WHERE division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		 return obj_HashMap;
	}
	/**
	 * This method returns all affiliates falls in the selected area.
	 * This HashMap contains the AFF_CODE (name) and AFF_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	public HashMap getAllAreaAffs(String areaCode,String division) throws TCGMException
	{
		String methodName = "getAllAreaAffs()";

		String sql = this.AREAAFFS_SELECT+ areaCode +"' AND DIVISION='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}

		return obj_HashMap;
	}

	/**
	 * This method returns all affiliates falls in the selected sector.
	 * This HashMap contains the AFF_CODE (name) and AFF_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	public HashMap getAllSectorAffs(String secCode,String division) throws TCGMException
	{
		String methodName = "getAllSectorAffs()";

		String sql = this.SECAFFS_SELECT+ secCode +"' AND DIVISION='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}

		return obj_HashMap;
	}
	/**
	 * This method returns all affiliates falls in the selected sector.
	 * This HashMap contains the AFF_CODE (name) and AFF_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	public HashMap getAllHQAffs() throws TCGMException
	{
		String methodName = "getAllHQAffs()";

		String sql = this.HQAFFS_SELECT;
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}

		return obj_HashMap;
	}
	
	/**
	 * This method returns all affiliates falls in the selected sector.
	 * This HashMap contains the AFF_CODE (name) and AFF_NAME (value)
	 * pairs which will be used by the drop down .
	 * @return obj_HashMap HashMap
	 */
	public HashMap getAllHQAffs(String division) throws TCGMException
	{
		String methodName = "getAllHQAffs()";

		String sql = this.HQAFFS_SELECT+" WHERE DIVISION='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}

		return obj_HashMap;
	}
	/**
	 * This method builds a name value pairs for drop down values.
	 * The name will be displayed as text on the drop down and the
	 * value will be passed based on the user selection.
	 */
	public HashMap getListValues(String query, Connection obj_Connection) throws TCGMException
	{
		String methodName = "getListValues(String query,, Connection obj_Connection)";
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;

		Vector obj_VectorOptions = new Vector();
		Vector obj_VectorValues = new Vector();
		HashMap obj_HashListValues = new HashMap();

		try
		{
			obj_Statement = obj_Connection.prepareStatement(query);
			obj_ResultSet = obj_Statement.executeQuery(query);

			while (obj_ResultSet.next())
			{
				obj_HashListValues.put(obj_ResultSet.getString("VALUE"),obj_ResultSet.getString("NAME"));
			}

		}
		catch (SQLException obj_SQLException)
		{
			logException(className, methodName, obj_SQLException);
			throw new TCGMException(this.className, methodName, query, obj_SQLException.getMessage());

		}
		finally
		{
			try
			{
				if (obj_ResultSet != null)
				{
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null)
				{
					obj_Statement.close();
					obj_Statement = null;
				}if(obj_Connection!= null){
					obj_Connection.close();
					obj_Connection = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}

		return obj_HashListValues;

	}
	/**
	 * This method will create a record on the database table
	 * and returns an arraylist of  error records.
	 * @param searchObject NotificationListFilterBean
	 * @param notificationList ArrayList
	 * @throws TCGMException
	 */
	public void create(RptUser rptUserToInsert) throws TCGMException
	{
		String methodName = "create(RptUser rptUserToInsert)";
		rptUserToInsert.setMsg("");

		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.REPORT_SECURITY " +
					   "(RPT_USER_ID, USER_ID, AFFILIATE, AFFILIATE_DESC, FIRST_NAME, LAST_NAME, RECIPIENT) " +
					   "VALUES(TCGM.RPT_USER_ID_SEQ.NEXTVAL,?,?,?,?,?,?)";
		try
		{
			//inserting the user record into  report_users table for reporting purpose
			insertRptUser(rptUserToInsert, conn);

			ps = conn.prepareStatement(sql);

						ps.setString( 1, rptUserToInsert.getUserid());
						ps.setString( 2, rptUserToInsert.getCode());
						ps.setString( 3, rptUserToInsert.getDesc());
						ps.setString( 4, rptUserToInsert.getFirstName());
						ps.setString( 5, rptUserToInsert.getLastName());
						ps.setString( 6, rptUserToInsert.getRecipient());

						ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			 {
						rptUserToInsert.setMsg("Duplicate Row");
			}else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}

				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}

	/**
	 * This method will create a record on the database table
	 * and returns an arraylist of  error records.
	 * @param searchObject NotificationListFilterBean
	 * @param notificationList ArrayList
	 * @throws TCGMException
	 */
	public void create(RptUser rptUserToInsert, HashMap map) throws TCGMException
	{
		String methodName = "create(RptUser rptUserToInsert)";
		rptUserToInsert.setMsg("");
		
		RptUser cogosUser = rptUserToInsert;

		Connection conn = SQLUtil.openConnection();

		try
		{
			String strDivisions[] = null;
			/*
			if(rptUserToInsert.getDivision().equalsIgnoreCase("All")){
				strDivisions = new String[]{ "AI", "ANI", "AV", "PNI", "OTH", "EV", "CHX","EPD"};
			}else{
				if(rptUserToInsert.getDivision().equalsIgnoreCase("AV")){
					strDivisions = new String[]{ "AV", "EV", "CHX"};
				}else{
					strDivisions = new String[]{ rptUserToInsert.getDivision()};
				}
			}*/
			strDivisions =getDivisionException(rptUserToInsert.getDivision());
			conn.setAutoCommit(false);
			
			ArrayList selectedUserList = new ArrayList();
			selectedUserList.add(cogosUser);
			
			ReportMngr reportMngr = new ReportMngr();
			reportMngr.userMaintenance(selectedUserList, TCGMConstants.REPORT_CONSTANT_ADD_USERS);
			
				for(int i=0;i<strDivisions.length;i++){
					rptUserToInsert.setDivision(strDivisions[i]);
					//inserting the user record into  report_users table for reporting purpose
					insertRptUser(rptUserToInsert, conn);
					//We don't need to add users to report security table
					/*Iterator iterate = map.keySet().iterator();
					while (iterate.hasNext()){
							String key = (String)iterate.next();
							String value = (String)map.get(key);
							rptUserToInsert.setCode(key);
							rptUserToInsert.setDesc(value);

							insertRptSecurity(rptUserToInsert, conn);
						}*/
				}
	
			conn.commit();
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}

	/**
		 * This method will create a record on the database table
		 * and returns an arraylist of  error records.
		 * @param searchObject NotificationListFilterBean
		 * @param notificationList ArrayList
		 * @throws TCGMException
		 */
		public void insertRptSecurity(RptUser rptUserToInsert, Connection conn) throws TCGMException
		{
			String methodName = "insertRptSecurity(RptUser rptUserToInsert, Connection conn)";
			rptUserToInsert.setMsg("");

			String sql = "INSERT INTO TCGM.report_security " +
						   "(RPT_USER_ID, USER_ID, AFFILIATE, AFFILIATE_DESC, FIRST_NAME, LAST_NAME, RECIPIENT, DIVISION, AFFDIV, ISGROUP) " +
						   "VALUES(TCGM.RPT_USER_ID_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = null;
			String isGroup="";

			if(rptUserToInsert.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				isGroup = "N";
			}else if(rptUserToInsert.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				isGroup = "A";
			}else if(rptUserToInsert.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				isGroup = "S";
			}else if(rptUserToInsert.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
				isGroup = "D";
			}else{
				isGroup = "H";
			}

			try
			{

				ps = conn.prepareStatement(sql);

							ps.setString( 1, rptUserToInsert.getUserid());
							ps.setString( 2, rptUserToInsert.getCode());
							ps.setString( 3, rptUserToInsert.getDesc().trim()+" - "+rptUserToInsert.getDivision());
							ps.setString( 4, rptUserToInsert.getFirstName());
							ps.setString( 5, rptUserToInsert.getLastName());
							ps.setString( 6, rptUserToInsert.getRecipient());
							ps.setString( 7, rptUserToInsert.getDivision());
							ps.setString( 8, rptUserToInsert.getCode()+""+rptUserToInsert.getDivision());
							ps.setString( 9, isGroup);
							ps.execute();


			}
			catch(SQLException sqle)
			{
				//logException(className,methodName,sqle);
				if(sqle.getMessage().startsWith("ORA-00001"))
				 {
							rptUserToInsert.setMsg("Duplicate Row");
				}else
				{
					// Error was some other error
					throw new TCGMException (className, methodName, sqle.toString());
				}

			}
			catch(Exception e)
			{
				logException(className,methodName,e);
				throw new TCGMException(this.className,methodName,e.toString());
			}
			finally
			{
				try
				{
					if (ps != null)
					{
						ps.close();
					}

				}
				catch (SQLException obj_SQLException)
				{
					obj_SQLException.printStackTrace();
				}
			}
		}


	/**
	 * This method will create a record on the database table
	 * @throws TCGMException
	 */
	public void insertRptUser(RptUser rptUserToInsert, Connection conn) throws TCGMException
	{
		String methodName = "inserRptUser(RptUser rptUserToInsert, Connection conn)";
		rptUserToInsert.setMsg("");

		PreparedStatement ps = null;
		String sql = "INSERT INTO TCGM.REPORT_USERS " +
					   "(USER_ID, ROLE, FIRST_NAME, LAST_NAME,ROLE_NAME,ROLE_DESC,EMP_DIVISION,EMP_TYPE,EMP_NUM,EMAIL,ACCESSCREATION_DATETIME) " +
					   "VALUES(?,?,?,?,?,?,?,?,?,?,SYSDATE)";
		try
		{
			ps = conn.prepareStatement(sql);
						ps.setString( 1, rptUserToInsert.getUserid());
						ps.setString( 2, getRole(rptUserToInsert));
						ps.setString( 3, rptUserToInsert.getFirstName());
						ps.setString( 4, rptUserToInsert.getLastName());
						ps.setString( 5, rptUserToInsert.getRole());
						ps.setString( 6, getRoleDesc(rptUserToInsert));
						ps.setString( 7, rptUserToInsert.getDivision());
						ps.setString( 8, rptUserToInsert.getEmployeeType());
						ps.setString( 9, rptUserToInsert.getAbtNotesId());
						ps.setString( 10, rptUserToInsert.getEmail());
						
						ps.execute();
		}

		catch(SQLException sqle)
		{
			//logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			 {
				rptUserToInsert.setMsg("Duplicate Row");
			}else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}

			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}

	/**
		 * This method will delete all records from the database table
		 * @throws TCGMException
		 */
		public void deleteRptUser(RptUser rptUserToDelete, Connection conn) throws TCGMException
		{
			String methodName = "deleteRptUser(RptUser rptUserToDelete, Connection conn)";
			rptUserToDelete.setMsg("");

			PreparedStatement ps = null;
			String sql = "DELETE FROM TCGM.REPORT_USERS WHERE USER_ID ='"+rptUserToDelete.getUserid()+"' AND ROLE ='"+getRole(rptUserToDelete)+"'";
			try
			{
				ps = conn.prepareStatement(sql);
				ps.execute();

			}

			catch(SQLException sqle)
			{
				logException(className,methodName,sqle);
				if(sqle.getMessage().startsWith("ORA-00001"))
				 {

				}else
				{
					// Error was some other error
					throw new TCGMException (className, methodName, sqle.toString());
				}

			}
			catch(Exception e)
			{
				logException(className,methodName,e);
				throw new TCGMException(this.className,methodName,e.toString());
			}
			finally
			{
				try
				{
					if (ps != null)
					{
						ps.close();
					}

				}
				catch (SQLException obj_SQLException)
				{
					obj_SQLException.printStackTrace();
				}
			}
		}

	/**
	 * This method will create a record on the database table
	 * @throws TCGMException
	 */
	public void addToBurstTable_DEFAULTGROUPS(RptUser rptUser, String strCatId, String strCatName) throws TCGMException
	{
		String methodName = "addToBurstTable(RptUser rptUser, String strCatId, String strCatName)";
		rptUser.setMsg("");

		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;


		String sql = "";
		String sqlDistinct = "";
		String sqlDistinctSectors = "";
		String sqlDistinctAffiliates = "";
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				sql = "INSERT INTO TCGM.AREA_BURST_NEW (AREA, AREA_DESC, RECIPIENT, AREADIVISION) VALUES(?,?,?,?)";//'"+strCatId+""+rptUser.getDivision()+"')";
				sqlDistinct = "select distinct area, area_desc  from "+DBConst.VW_T_AFFILIATE+"";
				sqlDistinctSectors = "select distinct area||region||sector, sec_desc    from "+DBConst.VW_T_AFFILIATE+" where sector != '00' and area=?";
				sqlDistinctAffiliates = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+" where area=?";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				sql = "INSERT INTO TCGM.AFFILIATE_BURST_NEW (AFF, AFF_DESC, RECIPIENT, AFFDIVISION) VALUES(?,?,?,?)";
				sqlDistinct = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+"";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				sql = "INSERT INTO TCGM.SECTOR_BURST_NEW (SECTOR, SECTOR_DESC, RECIPIENT, SECTORDIVISION) VALUES(?,?,?,?)";
				sqlDistinct = "select distinct area||region||sector, sec_desc	from "+DBConst.VW_T_AFFILIATE+" where sector != '00'";
				sqlDistinctAffiliates = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+" where area||region||sector=?";
			}

		try
		{
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sqlDistinct);
			ResultSet rs = ps1.executeQuery();
			ReportMngr reportMngr = new ReportMngr();
			String GROUPSEARCHPATH = "";



			while(rs.next()){

			String strDivisions[] = null;

				strDivisions = new String[]{ "AI", "ANI", "AV", "PNI", "OTH", "EV", "CHX","EPD"};

				//reportMngr.addGroup(rptUser, rs.getString(1), rs.getString(2));

				for(int i=0;i<strDivisions.length;i++){

					ArrayList userList = new ArrayList();
					//userList.add("HQS");
					//userList.add("HQC");
					userList.add("D"+strDivisions[i]);

					GROUPSEARCHPATH = "CAMID(\":nTCGM:"+rs.getString(1)+""+strDivisions[i]+"\")";

				    reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
						//ps.setString( 1, getRecipient(rptUser, strCatId, strCatName, strDivisions[i]));
						ps.setString( 1, rs.getString(1));
						ps.setString( 2, rs.getString(1)+" - "+rs.getString(2)+" - "+strDivisions[i]);
						ps.setString( 3, "CAMID(\":nTCGM:"+rs.getString(1)+""+strDivisions[i]+"\")");
						ps.setString( 4, rs.getString(1)+""+strDivisions[i]);
						ps.addBatch();

					userList = null;
				}
			}
			//int [] insertCounts  = ps.executeBatch();
		}
		catch(BatchUpdateException buex)
		{
			 int [] updateCounts = buex.getUpdateCounts();
				for (int i = 0; i < updateCounts.length; i++)
				{
				  if (updateCounts[i] == Statement.EXECUTE_FAILED)
				  {
					rptUser.setMsg("Duplicate Row");
				  }else
				  {
					  // Error was some other error
					  throw new TCGMException (className, methodName, buex.toString());
				  }
				}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			 {

			}else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}

			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}
	/**
	 * This method will create a record on the database table
	 * @throws TCGMException
	 */
	/*public void addToBurstTable(RptUser rptUser, String strCatId, String strCatName) throws TCGMException
	{
		String methodName = "addToBurstTable(RptUser rptUser, String strCatId, String strCatName)";
		rptUser.setMsg("");

		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;


		String sql = "";
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				sql = "INSERT INTO TCGM.AREA_BURST_NEW (AREA, AREA_DESC, RECIPIENT, AREADIVISION) VALUES('"+strCatId+"',?,?,?)";//'"+strCatId+""+rptUser.getDivision()+"')";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				sql = "INSERT INTO TCGM.AFFILIATE_BURST_NEW (AFF, AFF_DESC, RECIPIENT, AFFDIVISION) VALUES('"+strCatId+"',?,?,?)";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				sql = "INSERT INTO TCGM.SECTOR_BURST_NEW (SECTOR, SECTOR_DESC, RECIPIENT, SECTORDIVISION) VALUES('"+strCatId+"',?,?,?)";
			}

		try
		{
			ps = conn.prepareStatement(sql);
			String strDivisions[] = null;
			if(rptUser.getDivision().equalsIgnoreCase("All")){
				strDivisions = new String[]{ "AI", "ANI", "AV", "PNI", "OTH", "EV", "CTX"};
			}else{
				strDivisions = new String[]{ rptUser.getDivision()};
			}
				for(int i=0;i<strDivisions.length;i++){
						//ps.setString( 1, getRecipient(rptUser, strCatId, strCatName, strDivisions[i]));
						ps.setString( 1, strCatId+" - "+strCatName+" - "+strDivisions[i]);
						ps.setString( 2, "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")");
						ps.setString( 3, strCatId+""+strDivisions[i]);
						ps.addBatch();
				}
			int [] insertCounts  = ps.executeBatch();
		}
		catch(BatchUpdateException buex)
		{
			 int [] updateCounts = buex.getUpdateCounts();
				for (int i = 0; i < updateCounts.length; i++)
				{
				  if (updateCounts[i] == Statement.EXECUTE_FAILED)
				  {
					rptUser.setMsg("Duplicate Row");
				  }else
				  {
					  // Error was some other error
					  throw new TCGMException (className, methodName, buex.toString());
				  }
				}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			 {

			}else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}

			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}*/

	/**
	 *
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @return RowSet
	 * @throws TCGMException
	 */
	public ArrayList read(boolean lightVersion) throws TCGMException
	{
		String methodName = "getRS()";

		Connection obj_Connection = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		ArrayList userDetails = new ArrayList();

		try
		{
			String query = "SELECT * FROM REPORT_USERS " +
						   this.genWhereClause();

			//String query = "SELECT * FROM REPORT_USERS  WHERE  FIRST_NAME LIKE '%RAMA%'  AND  ROLE LIKE '%TCGM_AREA%'";

			OracleUserDao.logger.debug("\n\nOracleUserDao - getRS QUERY: \n" + query);

			obj_Connection = SQLUtil.openConnection();
			obj_Statement  = obj_Connection.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(query);

			while (obj_ResultSet.next())
			{
				userDetails.add(this.getUserDtlFromCurrentRow(obj_ResultSet, lightVersion));
			}
			return userDetails;
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
			try
			{
				if (obj_ResultSet != null)
				{
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null)
				{
					obj_Statement.close();
					obj_Statement = null;
				}
				if (obj_Connection != null)
				{
					obj_Connection.close();
					obj_Connection = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				logException(className,methodName,obj_SQLException);
				throw new TCGMException(this.className,methodName,obj_SQLException.toString());
			}
		}

	}

	/**
	 * This method will loop through the given vector and update each PriceDtl object in the collection
	 * based on the Prod_List_Pack_Code
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void update(ArrayList userList) throws TCGMException
	{
	/*	String methodName = "update(ArrayList userList)";
		Connection obj_Connection = null;
		PreparedStatement obj_Update_PreparedStatement = null;
		CallableStatement obj_CallableStatement = null;
		RptUser userBean;

		try
		{
			obj_Connection = SQLUtil.openConnection();
			String query = OracleUserDao.UPDATE_SQL;
			obj_Update_PreparedStatement = obj_Connection.prepareStatement(query);

			for(int i = 0; i < userList.size();i++)
			{
				userBean = (UserBean)userList.get(i);

				obj_Update_PreparedStatement.setString(1, userBean.getRole().getName());
				obj_Update_PreparedStatement.setString(2,userBean.getScope());
				obj_Update_PreparedStatement.setString(3,userBean.getUserid());

				obj_Update_PreparedStatement.addBatch(); // Add each statement to the batch.

			}
			int [] updateCounts  = obj_Update_PreparedStatement.executeBatch();
			obj_Connection.commit();

			obj_CallableStatement = obj_Connection.prepareCall( OracleUserDao.CALLABLE_SQL);
			obj_CallableStatement.execute();

		}
		catch(BatchUpdateException buex)
		{
			System.err.println("Contents of BatchUpdateException:");
			System.err.println(" Update counts: ");
			int [] updateCounts = buex.getUpdateCounts();
			for (int i = 0; i < updateCounts.length; i++) {
			  System.err.println("  Statement " + i + ":" + updateCounts[i]);
			}
			System.err.println(" Message: " + buex.getMessage());
			System.err.println(" SQLSTATE: " + buex.getSQLState());
			System.err.println(" Error code: " + buex.getErrorCode());
			SQLException ex = buex.getNextException();
			while (ex != null) {
			  System.err.println("SQL exception:");
			  System.err.println(" Message: " + ex.getMessage());
			  System.err.println(" SQLSTATE: " + ex.getSQLState());
			  System.err.println(" Error code: " + ex.getErrorCode());
			  ex = ex.getNextException();
			}

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
			try
			{
				if (obj_Update_PreparedStatement != null)
				{
					obj_Update_PreparedStatement.close();
					obj_Update_PreparedStatement = null;
				}
				if (obj_Connection != null)
				{
					obj_Connection.close();
					obj_Connection = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}	*/
	}

	/**
	 * This method will loop through the given arraylist and delete each NotificationListDtlBean in the collection.
	 * based on the user id.
	 * @param searchObject NotificationListFilterBean
	 * @param notificationList ArrayList
	 * @throws TCGMException
	 */
	/*public void delete(ArrayList userList) throws TCGMException
	{
		String methodName = "delete(ArrayList userList)";
		Connection obj_Connection = null;
		PreparedStatement obj_Delete_Overview_PreparedStatement = null;
		PreparedStatement obj_Delete_Detail_PreparedStatement = null;
		RptUser userBean;
		String queryOverview = OracleUserDao.DELETE_SQL_OVERVIEW ;
		String queryDetail = OracleUserDao.DELETE_SQL_DETAIL ;
		try
		{
			obj_Connection = SQLUtil.openConnection();
			obj_Delete_Overview_PreparedStatement = obj_Connection.prepareStatement(queryOverview);
			obj_Delete_Detail_PreparedStatement = obj_Connection.prepareStatement(queryDetail);

			for(int i = 0; i < userList.size();i++)
			{
				userBean = (UserBean)userList.get(i);

				obj_Delete_Overview_PreparedStatement.setString(1,userBean.getUserid());

				obj_Delete_Detail_PreparedStatement.setString(1,userBean.getUserid());

				obj_Delete_Detail_PreparedStatement.execute();
				obj_Delete_Overview_PreparedStatement.execute();
			}
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
			try
			{
				if (obj_Delete_Overview_PreparedStatement != null)
				{
					obj_Delete_Overview_PreparedStatement.close();
					obj_Delete_Overview_PreparedStatement = null;
				}
				if (obj_Delete_Detail_PreparedStatement != null)
				{
					obj_Delete_Detail_PreparedStatement.close();
					obj_Delete_Detail_PreparedStatement = null;
				}
				if (obj_Connection != null)
				{
					obj_Connection.close();
					obj_Connection = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}	*/
	/**
	 * This method will be used to convert the "next()" ResultSet ojbect to an UserBean object
	 * @param rs ResultSet
	 * @return UserBean
	 * @throws GPSException
	 */
	public RptUser getUserDtlFromCurrentRow(ResultSet rs, boolean lightVersion) throws TCGMException
	{
		String methodName = "getUserDtlFromCurrentRow(ResultSet)";
		HashMap obj_HashMap = new HashMap();
		RptUser userBean = new RptUser();

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);

		try
		{
			userBean.setUserid(rs.getString(DBConst.COL_USERID));
			/*userBean.setUserSessionId(rs.getString(DBConst.COL_USER_ID).toUpperCase());*/
			userBean.setFirstName(rs.getString(DBConst.COL_FIRST_NAME));
			userBean.setLastName(rs.getString(DBConst.COL_LAST_NAME));
		/*	userBean.setEmail(rs.getString(DBConst.COL_EMAIL_ID));
			userBean.setCountryCode(rs.getString(DBConst.COL_COUNTRY_CODE));
			userBean.setAreaCode(rs.getString(DBConst.COL_AREA_CODE)); */
			userBean.setRole(rs.getString("ROLE"));
			userBean.setRoleName(rs.getString("ROLE_NAME"));
			userBean.setRoleDesc(rs.getString("ROLE_DESC"));
			userBean.setCreateDate(convertDateFormat(rs.getDate("ACCESSCREATION_DATETIME")));
			userBean.setRecertifyDate(convertDateFormat(rs.getDate("RECERTIFY_DATETIME")));
			
			/*userBean.setScope(rs.getString(DBConst.COL_SCOPE));
			userBean.setOwnCountryRefPriceAccess(rs.getString(DBConst.COL_OWN_COUNTRY_REF_PRICE));
			userBean.setOtherCountryRefPriceAccess(rs.getString(DBConst.COL_OTHER_COUNTRY_REF_PRICE));
			userBean.setSelected(false);
			userBean.setMessage("");
			if (!(lightVersion))
			{
				userBean.setPassword(this.searchObject.getPassword());
			}	*/

			return userBean;
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

	private String getRole(RptUser rptUser)
	{
		String strRole="";
		if(rptUser.getRole().equalsIgnoreCase("-1")){
			strRole ="";
		}
		else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				strRole = rptUser.getAffCode()+""+rptUser.getDivision();
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				strRole =rptUser.getAreaCode()+""+rptUser.getDivision();
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				strRole = rptUser.getSecCode()+""+rptUser.getDivision();
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP)){
			strRole = ""+TCGMConstants.HQ_SUP+"";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_CON)){
			strRole = ""+TCGMConstants.HQ_CON+"";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
			strRole = ""+TCGMConstants.DIVISION+""+rptUser.getDivision();
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)){
			strRole = ""+TCGMConstants.ALL_DIVISIONS+"";
		}

		return strRole;
	}
	
	private String getRoleDesc(RptUser rptUser)
		{
			String strRole="";
			if(rptUser.getRole().equalsIgnoreCase("-1")){
				strRole ="";
			}
			else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
					strRole = rptUser.getDesc()+" - "+rptUser.getDivision();
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
					strRole = rptUser.getDesc()+" - "+rptUser.getDivision();
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
					strRole = rptUser.getDesc()+" - "+rptUser.getDivision();
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP)){
				strRole = "HQ Supervisor";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_CON)){
				strRole = "HQ Consumer";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
				strRole = "Division "+rptUser.getDivision();
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)){
				strRole = "All Divisions";
			}

			return strRole;
		}

	private String getRecipient(RptUser rptUser, String strCatId, String strCatName, String strDivision) throws TCGMException
	{
		String strRecipient="";

		if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				strRecipient = "CAMID(\":nTCGM:"+strCatId+""+strDivision+"\")";
		}

		//	strRecipient = "CAMID(\":AI TCGM:level 2:level 3:Level 4:"+TCGMConstants.HQ_CON+"\")";

		return strRecipient;
	}

	private String getName(String strName){
		String strAreaName = "";
		if(strName.indexOf(" ") > 0){
			StringTokenizer strTokens = new StringTokenizer(strName, " ");

			while(strTokens.hasMoreTokens()){
				strAreaName = strAreaName+strTokens.nextToken().substring(0,1);
			}
		}else{
			strAreaName = strName.substring(0,3);
		}

		return strAreaName;
	}

	/**
	 * This method will create a record on the database table
	 * and adds a group in cognos and adds this group in its associated
	 * sub categories like area under sector and affiliate.
	 * It also adds the default members of the group
	 * @throws TCGMException
	 */
	public void addToBurstTable(RptUser rptUser, String strCatId, String strCatName, String strCat,String division) throws TCGMException
	{
		String methodName = "addToBurstTable(RptUser rptUser, String strCatId, String strCatName, String strCat,String division)";
		rptUser.setMsg("");

		Connection conn = SQLUtil.openConnection();
		
		PreparedStatement ps = null;
		//PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;//for paremt SQL
		PreparedStatement ps5 = null;//for DIV SQL
		boolean burstRec;
		String sql = "";
		//String sqlDistinct = "";
		String sqlDistinctSectors = "";
		String sqlDistinctAffiliates = "";
		String sqlParent = "";
		String val="";
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				sql = "INSERT INTO TCGM.AREA_BURST (AREA, AREA_DESC, RECIPIENT, AREADIVISION, KEY, HQ, ADESC,DIVISION) VALUES(?,?,?,?,TCGM.AREA_BURST_NEW_KEY_SEQ.NEXTVAL,?,?,?)";//'"+strCatId+""+rptUser.getDivision()+"')";
				//sqlDistinct = "select distinct area, area_desc  from "+DBConst.VW_T_AFFILIATE+"";
				sqlDistinctSectors = "select distinct area||region||sector, sector_desc    from "+DBConst.VW_T_AFFILIATE+" where area='"+strCatId+"' and division='"+division+"'";
				sqlDistinctAffiliates = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+" where area='"+strCatId+"' and division='"+division+"'";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				sql = "INSERT INTO TCGM.SECTOR_BURST (SECTOR, SECTOR_DESC, RECIPIENT, SECTORDIVISION, KEY, HQ, SDESC,DIVISION) VALUES(?,?,?,?,TCGM.SECTOR_BURST_NEW_KEY_SEQ.NEXTVAL,?,?,?)";
				//sqlDistinct = "select distinct area||region||sector, sec_desc	from "+DBConst.VW_T_AFFILIATE+"";
				sqlDistinctAffiliates = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+" where area||region||sector='"+strCatId+"' and division='"+division+"'";
				sqlParent = "select distinct area, area_desc from "+DBConst.VW_T_AFFILIATE+" where area||region||sector='"+strCatId+"' and division='"+division+"'";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				sql = "INSERT INTO TCGM.AFFILIATE_BURST (AFF, AFF_DESC, RECIPIENT, AFFDIVISION, KEY, HQ, ADESC, DIVISION,AFFCODE) VALUES(?,?,?,?,TCGM.AFFILIATE_BURST_NEW_KEY_SEQ.NEXTVAL,?,?,?,?)";
				//sqlDistinct = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+"";
				sqlParent = "select distinct area||region||sector, sector_desc from "+DBConst.VW_T_AFFILIATE+" where aff='"+strCatId+"' and division='"+division+"'";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
				sql = "INSERT INTO TCGM.DIVISION_BURST (DIVISION, RECIPIENT, KEY) VALUES(?,?,TCGM.DIVISION_BURST_NEW_KEY_SEQ.NEXTVAL)";
				sqlParent = "select distinct area, area_desc from "+DBConst.VW_T_AFFILIATE+" where division='"+division+"'";
			}
			

		try
		{
			

			ps = conn.prepareStatement(sql);
			//ps1 = conn.prepareStatement(sqlDistinct);

			//ResultSet rs = ps1.executeQuery();

			ArrayList AreaSecList = new ArrayList();

			AreaSecList.add(strCatId);

			ReportMngr reportMngr = new ReportMngr();
			String GROUPSEARCHPATH = "";

				ResultSet rs2 =null;
				ResultSet rs3 = null;
				ResultSet rs4 = null; //for Parenet SQL
				ResultSet rs5 = null; //for Parenet SQL

			for (int j=0; j<AreaSecList.size(); j++)
			{

				if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
					ps2 = conn.prepareStatement(sqlDistinctSectors);
					ps3 = conn.prepareStatement(sqlDistinctAffiliates);
					//ps2.setString( 1, strCatId);
					//ps2.setString( 2, division);
					//ps3.setString( 1, strCatId);
					//ps3.setString( 2, division);
					rs2 = ps2.executeQuery();
					rs3 = ps3.executeQuery();
				}
				if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
					ps3 = conn.prepareStatement(sqlDistinctAffiliates);
					//ps3.setString( 1, strCatId);
					//ps3.setString( 2, division);
  					rs3 = ps3.executeQuery();

					ps4 = conn.prepareStatement(sqlParent);
					//ps4.setString( 1, strCatId);
					//ps4.setString( 2, division);
					rs4 = ps4.executeQuery();
				}
				if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
					ps4 = conn.prepareStatement(sqlParent);
					//ps4.setString( 1, strCatId);
					//ps4.setString( 2, division);
					rs4 = ps4.executeQuery();
				}
				
				if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
					ps5 = conn.prepareStatement(sqlParent);
					//ps4.setString( 1, strCatId);
					//ps4.setString( 2, division);
					rs5 = ps5.executeQuery();
				}

				String strDivisions[] = null;
				strDivisions =getDivisionRollup(division);
				/*
				if(division.equalsIgnoreCase("AI")){
			    	   strDivisions = new String[]{ "AI", "OTH"};
				}else if(division.equalsIgnoreCase("ANI")){
					strDivisions = new String[]{ "ANI", "PNI"};
				}else if(division.equalsIgnoreCase("AV")){
					strDivisions = new String[]{ "AV","EV","CHX"};
				}else if(division.equalsIgnoreCase("EPD")){
					strDivisions = new String[]{ "EPD"};
				}*/
				
				
				String strCategories[] = null;
					   strCategories = new String[]{ "HQ", "NONHQ"};

					/*Adding the group to Cognos Name Space*/
					

					for(int i=0;i<strDivisions.length;i++){
						
						rptUser.setDivision(strDivisions[i]);
						burstRec=checkBurstRecord(rptUser,strCatId,strDivisions[i]);
						if(!burstRec){
						reportMngr.addGroup(rptUser, strCatId, strCatName);
						for(int a=0;a<strCategories.length;a++){
							if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
								ps.setString( 1, strDivisions[i]);
								if("NONHQ".equalsIgnoreCase(strCategories[a])){
									ps.setString( 2, "CAMID(\":nTCGM:D"+strDivisions[i]+"\")");									
								}else{
									ps.setString( 2, "CAMID(\":nTCGM:HQ\")");
									
								}
							}else{
								ps.setString( 1, strCatId);
								ps.setString( 2, strCatId+" - "+strCatName+" - "+strDivisions[i]);
								if("NONHQ".equalsIgnoreCase(strCategories[a])){
									ps.setString( 3, "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")");
									ps.setString( 5, "N");
								}else{
									ps.setString( 3, "CAMID(\":nTCGM:HQ\")");
									ps.setString( 5, "Y");
								}
								ps.setString( 4, strCatId+""+strDivisions[i]);
								ps.setString( 6, strCatId+" - "+strCatName);
								ps.setString( 7, strDivisions[i]);
								if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
									ps.setString( 8, strCatId);
								}
							}	
							addRecordToBurstTables(rptUser, ps);
							//ps.execute();
						}

						if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){

						/*Adding default members of this group*/
						ArrayList userList = new ArrayList();
						userList.add("D"+strDivisions[i]);
	 					GROUPSEARCHPATH = "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")";

						reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
						}
						if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){

							/*Adding default members of this group*/
							ArrayList userList = new ArrayList();
							userList.add("DALL");
							GROUPSEARCHPATH = "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")";

							reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
							}
					}	
					}


			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){

				while(rs5.next()){
					val=rs5.getString(1);
					for(int i=0;i<strDivisions.length;i++){
						try{
							ArrayList userList = new ArrayList();
							userList.add("D"+strDivisions[i]);
							GROUPSEARCHPATH = "CAMID(\":nTCGM:"+val.trim()+""+strDivisions[i]+"\")";

						reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
						userList = null;
						}catch(TCGMException e){
							
						}
					}
				}
			}
			
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){

				while(rs2.next()){
					val=rs2.getString(1);
					for(int i=0;i<strDivisions.length;i++){
						try{
							ArrayList userList = new ArrayList();
							userList.add((String)AreaSecList.get(j)+strDivisions[i]);
							GROUPSEARCHPATH = "CAMID(\":nTCGM:"+val.trim()+""+strDivisions[i]+"\")";

						reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
						userList = null;
						}catch(TCGMException e){
							
						}
					}
				}
			}

			//if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)||rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				while(rs3.next()){
					val=rs3.getString(1);
					for(int i=0;i<strDivisions.length;i++){
						try{
							ArrayList userList = new ArrayList();
							userList.add((String)AreaSecList.get(j)+strDivisions[i]);
							GROUPSEARCHPATH = "CAMID(\":nTCGM:"+val.trim()+""+strDivisions[i]+"\")";

					   reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
					   userList = null;
						}catch(TCGMException e){
							
						}
					}
				}
			  //Adding the default Area of this sector
			  while(rs4.next()){
			  	val=rs4.getString(1);
			 		for(int i=0;i<strDivisions.length;i++){
			 			try{
							  ArrayList userList = new ArrayList();
							  userList.add(val.trim()+strDivisions[i]);
							  GROUPSEARCHPATH = "CAMID(\":nTCGM:"+(String)AreaSecList.get(j)+""+strDivisions[i]+"\")";
	
						 reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
						 userList = null;
			 			}catch(TCGMException e){
							
						}
				  }
			  }
			}
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				while(rs4.next()){
				//Adding the default Area of this sector
					val=rs4.getString(1);
					  for(int i=0;i<strDivisions.length;i++){
					  	try{
							ArrayList userList = new ArrayList();
							userList.add(val.trim()+strDivisions[i]);
							GROUPSEARCHPATH = "CAMID(\":nTCGM:"+(String)AreaSecList.get(j)+""+strDivisions[i]+"\")";

					   reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
					   userList = null;
					  	}catch(TCGMException e){
							
						}
					}
				}

			}
		}
	}
		catch(BatchUpdateException buex)
		{
			 int [] updateCounts = buex.getUpdateCounts();
				for (int i = 0; i < updateCounts.length; i++)
				{
				  if (updateCounts[i] == Statement.EXECUTE_FAILED)
				  {
					rptUser.setMsg("Duplicate Row");
				  }else
				  {
					  // Error was some other error
					  throw new TCGMException (className, methodName, buex.toString());
				  }
				}
		}
		catch(SQLException sqle)
		{
			System.out.println("sqle:"+sqle);
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			 {

			}else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}if (conn != null)
				{
					conn.close();
					conn = null;
				}

			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}

	public void addRecordToBurstTables(RptUser rptUser, PreparedStatement ps) throws TCGMException
		{
			String methodName = "addRecordToBurstTables(RptUser rptUser, PreparedStatement ps)";
			rptUser.setMsg("");

			try
			{
					ps.execute();
			}

			catch(SQLException sqle)
			{
				//logException(className,methodName,sqle);
				if(sqle.getMessage().startsWith("ORA-00001"))
				 {
					rptUser.setMsg("Duplicate Row");
				}else
				{
					// Error was some other error
					throw new TCGMException (className, methodName, sqle.toString());
				}

			}
			catch(Exception e)
			{
				logException(className,methodName,e);
				throw new TCGMException(this.className,methodName,e.toString());
			}

		}

	/**
	 * This method will create a record on the database table
	 * @throws TCGMException
	 */
	public void addToBurstTable_MASS(RptUser rptUser, String strCatId, String strCatName) throws TCGMException
	{
		String methodName = "addToBurstTable(RptUser rptUser, String strCatId, String strCatName)";
		rptUser.setMsg("");

		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;


		String sql = "";
		String sqlDistinct = "";
		String sqlDistinctSectors = "";
		String sqlDistinctAffiliates = "";
			if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				sql = "INSERT INTO TCGM.AREA_BURST_NEW (AREA, AREA_DESC, RECIPIENT, AREADIVISION) VALUES(?,?,?,?)";//'"+strCatId+""+rptUser.getDivision()+"')";
				sqlDistinct = "select distinct area, area_desc  from "+DBConst.VW_T_AFFILIATE+"";
				sqlDistinctSectors = "select distinct area||region||sector, sec_desc    from "+DBConst.VW_T_AFFILIATE+" where sector = '00' and area=?";
				sqlDistinctAffiliates = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+" where area=?";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				sql = "INSERT INTO TCGM.AFFILIATE_BURST_NEW (AFF, AFF_DESC, RECIPIENT, AFFDIVISION) VALUES(?,?,?,?)";
				sqlDistinct = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+"";
			}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				sql = "INSERT INTO TCGM.SECTOR_BURST_NEW (SECTOR, SECTOR_DESC, RECIPIENT, SECTORDIVISION) VALUES(?,?,?,?)";
				sqlDistinct = "select distinct area||region||sector, sec_desc	from "+DBConst.VW_T_AFFILIATE+" where sector = '00'";
				sqlDistinctAffiliates = "select distinct aff, aff_desc  from "+DBConst.VW_T_AFFILIATE+" where area||region||sector=?";
			}

		try
		{
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sqlDistinct);

			ResultSet rs = ps1.executeQuery();

			ArrayList AreaSecList = new ArrayList();

			while(rs.next()){
				AreaSecList.add(rs.getString(1));
			}

			ReportMngr reportMngr = new ReportMngr();
			String GROUPSEARCHPATH = "";

					ResultSet rs2 =null;
					ResultSet rs3 = null;

			String strDivisions[] = null;
			       strDivisions = new String[]{ "AI", "ANI", "AV", "PNI", "OTH", "EV", "CHX","EPD"};


					for (int j=0; j<AreaSecList.size(); j++)
					{

					if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
						ps2 = conn.prepareStatement(sqlDistinctSectors);
						ps3 = conn.prepareStatement(sqlDistinctAffiliates);
						ps2.setString( 1, (String)AreaSecList.get(j));
						ps3.setString( 1, (String)AreaSecList.get(j));

						rs2 = ps2.executeQuery();
						rs3 = ps3.executeQuery();
					}
					if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
						ps3 = conn.prepareStatement(sqlDistinctAffiliates);
						ps3.setString( 1, (String)AreaSecList.get(j));
						rs3 = ps3.executeQuery();
					}

					for(int i=0;i<strDivisions.length;i++){

						ArrayList userList = new ArrayList();

						//userList.add("HQS");
						//userList.add("HQC");
						//userList.add("tst-gps03");
						userList.add("D"+strDivisions[i]);

						GROUPSEARCHPATH = "CAMID(\":nTCGM:"+(String)AreaSecList.get(j)+""+strDivisions[i]+"\")";

							//reportMngr.removeUsersFromGroup(userList, GROUPSEARCHPATH);
					}

				/*	if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){

					while(rs2.next()){


				//reportMngr.addGroup(rptUser, rs.getString(1), rs.getString(2));

				for(int i=0;i<strDivisions.length;i++){

					ArrayList userList = new ArrayList();

							userList.add((String)AreaSecList.get(j)+strDivisions[i]);

					//userList.add("HQS");
					//userList.add("HQC");
					userList.add("D"+strDivisions[i]);

					GROUPSEARCHPATH = "CAMID(\":nTCGM:"+rs2.getString(1)+""+strDivisions[i]+"\")";

						reportMngr.removeUsersFromGroup(userList, GROUPSEARCHPATH);
						//ps.setString( 1, getRecipient(rptUser, strCatId, strCatName, strDivisions[i]));
						//ps.setString( 1, rs.getString(1));
						//ps.setString( 2, rs.getString(1)+" - "+rs.getString(2)+" - "+strDivisions[i]);
						//ps.setString( 3, "CAMID(\":nTCGM:"+rs.getString(1)+""+strDivisions[i]+"\")");
						//ps.setString( 4, rs.getString(1)+""+strDivisions[i]);
						//ps.addBatch();

					userList = null;
				}

			}
			} */

			/* if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)||rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
			while(rs3.next()){


				//reportMngr.addGroup(rptUser, rs.getString(1), rs.getString(2));

				for(int i=0;i<strDivisions.length;i++){

					ArrayList userList = new ArrayList();

							userList.add((String)AreaSecList.get(j)+strDivisions[i]);

					//userList.add("HQS");
					//userList.add("HQC");
					//userList.add("D"+strDivisions[i]);

					GROUPSEARCHPATH = "CAMID(\":nTCGM:"+rs3.getString(1)+""+strDivisions[i]+"\")";

				//--reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);
						//ps.setString( 1, getRecipient(rptUser, strCatId, strCatName, strDivisions[i]));
						//ps.setString( 1, rs.getString(1));
						//ps.setString( 2, rs.getString(1)+" - "+rs.getString(2)+" - "+strDivisions[i]);
						//ps.setString( 3, "CAMID(\":nTCGM:"+rs.getString(1)+""+strDivisions[i]+"\")");
						//ps.setString( 4, rs.getString(1)+""+strDivisions[i]);
						//ps.addBatch();

					userList = null;
				}

			}
			} */
			//int [] insertCounts  = ps.executeBatch();
			}
		}
		catch(BatchUpdateException buex)
		{
			 int [] updateCounts = buex.getUpdateCounts();
				for (int i = 0; i < updateCounts.length; i++)
				{
				  if (updateCounts[i] == Statement.EXECUTE_FAILED)
				  {
					rptUser.setMsg("Duplicate Row");
				  }else
				  {
					  // Error was some other error
					  throw new TCGMException (className, methodName, buex.toString());
				  }
				}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			 {

			}else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}

			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}
	
	public ArrayList getRptUsers() throws TCGMException {
		
		String methodName = "getRptUsers()";

				Connection obj_Connection = null;
				Statement obj_Statement = null;
				ResultSet obj_ResultSet = null;
				ArrayList userDetails = new ArrayList();

				try
				{
					String query = "SELECT DISTINCT USER_ID,FIRST_NAME,LAST_NAME FROM REPORT_USERS ORDER BY USER_ID";

					OracleUserDao.logger.debug("\n\nOracleUserDao - getRS QUERY: \n" + query);

					obj_Connection = SQLUtil.openConnection();
					obj_Statement  = obj_Connection.createStatement();
					obj_ResultSet = obj_Statement.executeQuery(query);
					RptUser rptUser=null;
					while (obj_ResultSet.next())
					{
						rptUser=new RptUser();
						rptUser.setUserid(obj_ResultSet.getString(1));
						rptUser.setFirstName(obj_ResultSet.getString(2));
						rptUser.setLastName(obj_ResultSet.getString(3));
						userDetails.add(rptUser);
						rptUser=null;
					}
					return userDetails;
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
					try
					{
						if (obj_ResultSet != null)
						{
							obj_ResultSet.close();
							obj_ResultSet = null;
						}
						if (obj_Statement != null)
						{
							obj_Statement.close();
							obj_Statement = null;
						}
						if (obj_Connection != null)
						{
							obj_Connection.close();
							obj_Connection = null;
						}
					}
					catch (SQLException obj_SQLException)
					{
						logException(className,methodName,obj_SQLException);
						throw new TCGMException(this.className,methodName,obj_SQLException.toString());
					}
				}
		
	}

	/**
	 * This method will recertify the selected users. It will update the Recertify Date.
	 * @throws TCGMException
	 */
	public void recertifyRptUsers(ArrayList userList, String userName)
			throws TCGMException {

		String methodName = "recertifyRptUsers(ArrayList userList, String userName)";

		String sql = "UPDATE REPORT_USERS SET RECERTIFY_DATETIME = SYSDATE, RECERTIFY_USERNAME = ? " +
						"WHERE USER_ID = ? AND ROLE = ?";

		Connection conn = SQLUtil.openConnection();
		PreparedStatement ps = null;

		try {

			for (int i = 0; i < userList.size(); i++) {

				ps = conn.prepareStatement(sql);
				RptUser userBean = (RptUser) userList.get(i);

				ps.setString(1, userName);
				ps.setString(2, userBean.getUserid());
				ps.setString(3, userBean.getRole());
				ps.execute();

			}

		} catch (SQLException sqle) {

			throw new TCGMException(className, methodName, sqle.toString());

		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException obj_SQLException) {
				obj_SQLException.printStackTrace();
			}
		}

	}
	
	public void loadBurstTable() throws TCGMException{
		String methodName = "loadBurstTable";
		String callLoadBurst = "{ call TCGM.populate_burst_tables }";
		Connection conn = null;
		CallableStatement csLoadJob = null;
		try
		{
			conn = this.getConnection();
			csLoadJob = conn.prepareCall(callLoadBurst);
			csLoadJob.execute();
			
		}catch (SQLException sqe)
			{
				throw new TCGMException( this.className,methodName, "", sqe.toString() + ": " + sqe.getMessage() );
			}
			finally
			{
				SQLUtil.closeCS(csLoadJob);				
				SQLUtil.closeConnection(conn);
			}
	}
	public ArrayList getAffiliateStatus(String division,String active)throws TCGMException{
		String methodName = "getAffiliateStatus(String division,String affCode)";
		String callStatus = "SELECT AFF,DIVISION FROM TCGM.REPORT_AFFILIATE WHERE DIVISION='"+division+"' AND ACTIVE='"+active+"' ORDER BY AFF";
		
		Connection conn = null;		
		Statement csLoadJob = null;
		ResultSet rsAffStatus=null;
		ArrayList arList=new ArrayList();
		ActiveAffMaint affmaint=null;
		try
		{
			conn = this.getConnection();
			csLoadJob = conn.createStatement();
			rsAffStatus=csLoadJob.executeQuery(callStatus);
			
			while(rsAffStatus.next()){
				affmaint=new ActiveAffMaint();
				affmaint.setAff(rsAffStatus.getString(1));
				affmaint.setDivisionCode(rsAffStatus.getString(2));
				arList.add(affmaint);
			}
			
		}catch (SQLException sqe)
			{
				throw new TCGMException( this.className,methodName, "", sqe.toString() + ": " + sqe.getMessage() );
			}
			finally
			{
				SQLUtil.closeResultSet(rsAffStatus);
				SQLUtil.closeStatment(csLoadJob);				
				SQLUtil.closeConnection(conn);
			}
			
		return arList;
	}
	public void saveAffiliateStatus(String division,ArrayList affCode,String status) throws TCGMException{
		String methodName = "saveAffiliateStatus(String division,String affCode,String status)";
		String callStatus = "";
		Connection conn = null;
		
		Statement csLoadJob = null;
		ResultSet rsAffStatus=null;
		String memberKey="";
		String GROUPSEARCHPATH="";
		ActiveAffMaint affmaint=null;
		try
		{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			csLoadJob = conn.createStatement();
			for(int i=0;i<affCode.size();i++)
			{
				memberKey="";
				affmaint=(ActiveAffMaint)affCode.get(i);	
				callStatus = "UPDATE TCGM.REPORT_AFFILIATE SET ACTIVE='"+status+"' WHERE DIVISION='"+affmaint.getDivisionCode()+"' AND AFF='"+affmaint.getAff()+"'";
				csLoadJob.execute(callStatus);			
				rsAffStatus=csLoadJob.executeQuery("SELECT Area,Region,Sector FROM TCGM.REPORT_AFFILIATE_CHANGES WHERE DIVISION='"+affmaint.getDivisionCode()+"' AND AFF='"+affmaint.getAff()+"'");
				
				if(rsAffStatus.next()){
					memberKey=rsAffStatus.getString(1)+rsAffStatus.getString(2)+rsAffStatus.getString(3);				
					  
				}
				String strDivisions[] = null;
				/*if(affmaint.getDivisionCode().equalsIgnoreCase("AI")){
			    	   strDivisions = new String[]{ "AI", "OTH"};
				}else if(affmaint.getDivisionCode().equalsIgnoreCase("ANI")){
					strDivisions = new String[]{ "ANI", "PNI"};
				}else if(affmaint.getDivisionCode().equalsIgnoreCase("AV")){
					strDivisions = new String[]{ "AV","EV","CHX"};
				}else if(affmaint.getDivisionCode().equalsIgnoreCase("EPD")){
					strDivisions = new String[]{ "EPD"};
				}*/
				strDivisions=getDivisionRollup(affmaint.getDivisionCode());
				if(!memberKey.equals(""))
				{
					for(int j=0;j<strDivisions.length;j++){
						ArrayList userList = new ArrayList();
						userList.add(memberKey.trim()+strDivisions[j]);
						GROUPSEARCHPATH = "CAMID(\":nTCGM:"+affmaint.getAff()+""+strDivisions[j]+"\")";
						ReportMngr reportMngr = new ReportMngr();
						if(status.equals("Y"))
						{
						    reportMngr.addUsersToGroup(userList, GROUPSEARCHPATH);	
						}else if(status.equals("N")){
							reportMngr.removeUsersFromGroup(userList, GROUPSEARCHPATH);
						}
						
						userList=null;
					}
				}
			}
			conn.commit();
		}catch (SQLException sqe)
			{
				throw new TCGMException( this.className,methodName, "", sqe.toString() + ": " + sqe.getMessage() );
			}
			finally
			{
				SQLUtil.closeResultSet(rsAffStatus);
				SQLUtil.closeStatment(csLoadJob);				
				SQLUtil.closeConnection(conn);
			}
	}
	
	private String convertDateFormat(Date d){
		if(null==d){
			return "";
		}
		
		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");		
			
		return mdyFormat.format(d);
	}
	
	public HashMap getDivision()throws TCGMException{
		
		String methodName = "getDivision()";

		String sql = this.DIVISION_SELECT;
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		 return obj_HashMap;
	}
	
	public HashMap getTAffiliates(String division) throws TCGMException
	{
		String methodName = "getAllAffiliates()";

		String sql = "select distinct aff VALUE, aff||' - '||aff_desc NAME from "+DBConst.VW_T_AFFILIATE+" WHERE division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		return obj_HashMap;
	}
	
	public HashMap getTSectors(String division) throws TCGMException
	{
		String methodName = "getTSectors(String division)";

		String sql = "select distinct area||region||sector VALUE, area||region||sector||' - '||sector_desc NAME from "+DBConst.VW_T_AFFILIATE+" where division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		return obj_HashMap;
	}
	
	public HashMap getTAreas(String division) throws TCGMException
	{
		String methodName = "getTAreas(String division)";

		String sql = "select distinct area VALUE,area||' - '||area_desc NAME from "+DBConst.VW_T_AFFILIATE+" where division='"+division+"'";;
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		 return obj_HashMap;
	}
	public HashMap getBurstDivision()throws TCGMException{
		
		String methodName = "getBurstDivision()";

		String sql = "select distinct Division VALUE, Division NAME from DIVISION_BURST";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		 return obj_HashMap;
	}
	public HashMap getBurstAffiliates(String division) throws TCGMException
	{
		String methodName = "getBurstAffiliates(String division)";

		//String sql = "select distinct aff VALUE, aff||' - '||adesc NAME from affiliate_burst WHERE division='"+division+"' and sector='"+sector+"'";
		String sql = "select distinct aff VALUE, aff||' - '||adesc NAME from affiliate_burst WHERE division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		return obj_HashMap;
	}
	
	public HashMap getBurstSectors(String division) throws TCGMException
	{
		String methodName = "getBurstSectors(String division)";

		//String sql = "select distinct sector VALUE, sdesc NAME from sector_burst where division='"+division+"'  and substr(sector,0,2)='"+area+"'";
		String sql = "select distinct sector VALUE, sdesc NAME from sector_burst where division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		return obj_HashMap;
	}
	
	public HashMap getBurstAreas(String division) throws TCGMException
	{
		String methodName = "getBurstAreas(String division)";

		String sql = "select distinct Area VALUE, Adesc NAME from AREA_BURST where division='"+division+"'";
		HashMap obj_HashMap = new HashMap();
		Connection conn = null;

		try
		{
			conn = getConnection();
			obj_HashMap = this.getListValues(sql, conn);
		}
		catch (TCGMException obj_TCGMException)
		{
			logException(className, methodName, obj_TCGMException);
			throw new TCGMException(this.className, methodName, obj_TCGMException.getMessage());
		}
		 return obj_HashMap;
	}
	
	public String[] getDivisionRollup(String param)throws TCGMException
	{
		String methodName = "getDivisionRollup(String param)";
		String [] retVal;
		Connection conn = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet=null;
		String sql="Select parm_value from parameter where parm_Name='ROLLUP_DIVISION_"+param+"'";
		try
		{
			conn = getConnection();
			obj_Statement  = conn.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(sql);
			if(obj_ResultSet.next()){
				retVal=obj_ResultSet.getString(1).split(",");
			}else{
				retVal=new String[]{param};
			}
		}catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, "", sqe.toString() + ": " + sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeResultSet(obj_ResultSet);
			SQLUtil.closeStatment(obj_Statement);				
			SQLUtil.closeConnection(conn);
		}
		
		return retVal;
	}
	
	public String[] getDivisionException(String division)throws TCGMException
	{
		String methodName = "getDivisionException(String division)";
		String [] retVal=null;;
		Connection conn = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet=null;
		String rsltValue="";
		String [] tmpVal;
		String sql="Select parm_value from parameter where parm_Name='DIVISION_EXCEPTION'";
		try
		{
			conn = getConnection();
			obj_Statement  = conn.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(sql);
			if(obj_ResultSet.next()){
				
				tmpVal=(obj_ResultSet.getString(1)).split(":");
				for(int i=0;i<tmpVal.length;i++)
				{
										
					if(tmpVal[i].contains(division)){
						retVal=(tmpVal[i].substring(tmpVal[i].indexOf("= ")+1)).split(",");
						break;
					}else{
						retVal=new String[]{division};
					}
				}				
				
			}else{
				retVal=new String[]{division};
			}
		}catch (SQLException sqe)
		{
			throw new TCGMException( this.className,methodName, "", sqe.toString() + ": " + sqe.getMessage() );
		}
		finally
		{
			SQLUtil.closeResultSet(obj_ResultSet);
			SQLUtil.closeStatment(obj_Statement);				
			SQLUtil.closeConnection(conn);
		}
		
		return retVal;
	}
	
	public boolean checkBurstRecord(RptUser userBean, String strCatId, String strCatName) throws TCGMException
	{
		String methodName = "checkBurstRecord(RptUser userBean, String strCatId, String strCatName)";

		Connection obj_Connection = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		String sql =""; 
		int count=0;
		boolean flag =false;
		try
		{
			if(userBean.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
				sql ="select count(*) from area_burst where areadivision='"+strCatId+strCatName+"'";
			}else if(userBean.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
				sql ="select count(*) from sector_burst where sectordivision='"+strCatId+strCatName+"'";
			}else if(userBean.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				sql ="select count(*) from affiliate_burst where affdivision='"+strCatId+strCatName+"'";
			}else if(userBean.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
				sql ="select count(*) from division_burst where division='"+strCatName+"'";
			}
			

			obj_Connection = SQLUtil.openConnection();
			obj_Statement  = obj_Connection.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(sql);

			if (obj_ResultSet.next())
			{
				count=obj_ResultSet.getInt(1);
			}
			
			if(count>0){
				flag=true;
			}
			
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
			try
			{
				if (obj_ResultSet != null)
				{
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null)
				{
					obj_Statement.close();
					obj_Statement = null;
				}
				if (obj_Connection != null)
				{
					obj_Connection.close();
					obj_Connection = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				logException(className,methodName,obj_SQLException);
				throw new TCGMException(this.className,methodName,obj_SQLException.toString());
			}
		}
		return flag;
	}
}