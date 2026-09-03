package abbott.ai.tcgm.helpers;

import java.util.Vector;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.UserDao;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;

import com.abbott.ai.security.AuthenticationException;
import com.abbott.ai.security.AuthenticatorException;
import com.abbott.ai.security.IAuthenticator;
import com.abbott.ai.security.ldap.LdapAuthenticator;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class LoginMngr implements TCGMMngr
{
	private static Logger myLogger = Logger.getLogger( "LoginMngr" );
	/**
	 * Default Constructor
	 */
	public LoginMngr()
	{
	}

	/**
	 * To Authenticate user
	 * <ul>
	 *     <li>Connect to oracle with the user id and passord that were passed in with the User object</li>
	 * </ul>
	 * @param user User object that has the id and password from the login screen
	 * @return True if authentication passes
	 * @throws TCGMException
	 */
	public boolean authenticateUser(User user) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(user.getUserToken());
		
		final String AUTH_HOST = AppConst.getInstance().getAuthenticationHost();
		final String AUTH_BASEDN = AppConst.getInstance().getAuthenticationBaseDn();
		final String AUTH_USERID = AppConst.getInstance().getAuthenticationUserId();
		final String AUTH_PASSWORD = AppConst.getInstance().getAuthenticationPassword();
	
		// 10-21-05 LDAP Authentication Test
		IAuthenticator ldapAuth = new LdapAuthenticator(AUTH_HOST, AUTH_BASEDN, AUTH_USERID, AUTH_PASSWORD);
		
		try
		{
			ldapAuth.authenticate(user.getUserid(), user.getPassword());
			myLogger.error("User Authenticated - " + user.getUserToken().getUserid());
			return true;
		}
		// Most likely problem w/ LDAP server or Connecting; Server may be down
		catch(AuthenticatorException authEx) 
		{
			authEx.printStackTrace();
			myLogger.error("Caught Authenticator Exception! - " + user.getUserToken().getUserid() + authEx.getMessage());
			return false;
		} 
		// Most likely invalid username/password combo or user locked out
		catch ( AuthenticationException authEx2) 
		{
			myLogger.error("Caught Authentication Exception! - " + user.getUserToken().getUserid() + authEx2.getMessage());
			return false;
		}		
	}
	
	public boolean authorizeUser(User user) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(user.getUserToken());
		
		userDao.checkRptUser(user);
		
		if(userDao.loadUser(user)||user.isRptAccess())
		{
			myLogger.error("User Authorized - " + user.getUserToken().getUserid());
			return true;
		}
		else
		{
			myLogger.error("User Not Authorization! " + user.getUserToken().getUserid());
			return false;
		}
	}	

	/*************************************************************
	*	Added by Uday on 02/04/2006 to provide the user(Analyst)
	*   the option to view the maintenance records of any user. Start
	**************************************************************/
	public Vector getAllUsers(User user) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(user.getUserToken());
		Vector userList = userDao.getVO();
		Vector userIds = new Vector(userList.size());
		for (int i=0;i<userList.size();i++)
		{
			userIds.add(((User)(userList.get(i))).getUserid());
		}
		return userIds;
	}	

}