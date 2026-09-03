package abbott.ai.tcgm.helpers;

import java.util.*;

//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class UserMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public UserMngr()
	{	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject User object with search criteria
	 * @return Vector of User objects
	 * @throws TCGMException
	 */
	public Vector getUsers(UserToken userToken,User searchObject,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(userToken,searchObject);
		return userDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject User object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of User objects
	 * @throws TCGMException
	 */
	public Vector getUsers(UserToken userToken,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(userToken,sortObject);
		return userDao.getVO();
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject User
	 * @return User
	 * @throws TCGMException
	 */
	public User getUserById(UserToken userToken,User searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(userToken,searchObject);
		return userDao.getUserById();
	}

	/**
	 * @param userToken
	 * @param userToEdit
	 * @throws TCGMException
	 */
	public void saveUser(UserToken userToken,User userToEdit) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(userToken);

		if(userToEdit.getUserinfoid().equals(""))
		{
			//creating a new record
			userDao.insert(userToEdit);
		}
		else
		{
			//updating an existing record.
			userDao.update(userToEdit);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param userToken
	 * @param userList
	 * @throws TCGMException
	 */
	public int deleteUsers(UserToken userToken,Vector userList) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		UserDao userDao = daoFactory.getUserDao(userToken);
		int adminCode = 0;

		Vector selectedUsers = getSelectedUsers(userList);

		if(selectedUsers.size() > 0)
		{
			for (int i=0; i<selectedUsers.size(); i++)
			{
				if (((User)(selectedUsers.elementAt(i))).getUserid().equalsIgnoreCase(userToken.getUserid()))
				{
					selectedUsers.removeElementAt(i);
					adminCode = 1;
					
				}
			}
			if(selectedUsers.size() > 0) userDao.delete(selectedUsers);
		}
		return adminCode;
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of Users, returns the ones that have the selected flag set to true
	 * @param userList Vector
	 * @return Vector
	 */
	private Vector getSelectedUsers(Vector userList)
	{
		Vector selectedUsers = new Vector();

		for(int i = 0; i < userList.size(); i++)
		{
			User user = (User)userList.elementAt(i);

			if(user.isSelected())
			{
				selectedUsers.add(user);
			}
		}

		return selectedUsers;
	}
}