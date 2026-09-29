package abbott.ai.tcgm.action.form;

import java.util.*;
import jakarta.servlet.http.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.exception.*;

import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */

public class UserForm extends TCGMForm
{
	/**
	 * This property will hold the user from the current session so I can read parameters from the users
	 * session object.
	 */
	private User currUser = new User();

	private User searchObject = new User();
	private Vector userList = new Vector();
	private Sort sortObject = DBConst.DEF_SORT_USER;
	private User userToEdit = new User();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public UserForm()
	{
		super();
	} 
	/*****************************************************************************************/
	/**
	 *
	 * @return User
	 */
	public User getSearchObject()
	{
		if(this.searchObject == null)
		{
			this.searchObject = new User();
		}
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject User
	 */
	public void setSearchObject(User searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Sort
	 */
	public Sort getSortObject()
	{
		if(this.sortObject == null)
		{
			this.sortObject = DBConst.DEF_SORT_USER;
		}
		return this.sortObject;
	}
	/**
	 *
	 * @param sortObject Sort
	 */
	public void setSortObject(Sort sortObject)
	{
		this.sortObject = sortObject;
	}

	/**
	 *
	 * @param index int
	 * @return User
	 */
	public User getUserList(int index)
	{
		User user = null;
		if(index >= 0 && index < this.getUserlist().size())
		{
			user = (User)this.getUserlist().elementAt(index);
		}
		return user;
	}
	/**
	 *
	 * @return Vector
	 */
	public Vector getUserlist()
	{
		if(this.userList == null)
		{
			this.userList = new Vector();
		}
		
		return this.userList;
	}

	/**
	 *
	 * @param userList Vector
	 */
	public void setUserlist(Vector userList)
	{
		this.userList = userList;
	}
	/**
	 *
	 * @param index int
	 * @param user User
	 */
	public void setUserList(int index,User user)
	{
		this.getUserlist().setElementAt(user,index);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getUserListSize()
	{
		return this.getUserlist().size();
	}

	public User getUserToEdit()
	{
		if(this.userToEdit == null)
		{
			this.userToEdit = new User();
		}
		return this.userToEdit;
	}
	/**
	 *
	 * @param userToEdit User
	 */
	public void setUserToEdit(User userToEdit)
	{
		this.userToEdit = userToEdit;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.setSearchObject(new User());//ok to reset this as a new object.  The values are available in fields on the jsp page.
	}

	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if(errors.empty())
		{
			return null;
		}
		else
		{
			return errors;
		}
	}

	public void processCmd(ActionMapping mapping,HttpServletRequest request)
	{
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CANCEL))
		{
			this.setUserToEdit(new User());
			this.setSearchObject(new User());
		}
	}

	public boolean getUseridEdit()
	{
		//if the user info id is blank that means the field should not be disabled so return false.
		if(this.getUserToEdit().getUserinfoid().equals(""))
		{
			return false;
		}
		else // if the user info id is NOT blank then then field should be disabled so return true.
		{
			return true;
		}
	}

	public boolean getDspSaveCanBtn()
	{
		boolean retVal = false;
		//If the user is an administrator, always display.
		if(this.getCurrUser().getRole().getAccessLevel() == Role.Administrator.getAccessLevel())
		{
			retVal = true;
		}
		else if(! this.getUserToEdit().getUserinfoid().equals("")) //if there is a currnent user loaded then return true.
		{
			retVal =  true;
		}
		return retVal;
	}

	public User getCurrUser()
	{
		return this.currUser;
	}

	public void setCurrUser(User currUser)
	{
		this.currUser = currUser;
	}
}