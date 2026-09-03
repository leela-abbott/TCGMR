package abbott.ai.tcgm.action.form;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.ActiveDirSearchDtlBean;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.User;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Action form for the login page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */

public class ActiveDirSearchForm extends TCGMForm
{
	private String firstName="";
	private String lastName="";
	private String blnSelected;
	private ArrayList activeDirUserList = new ArrayList();
	private String usId="";
	private Vector userList = new Vector();
	private User searchObject = new User();
	private Sort sortObject = DBConst.DEF_SORT_USER;
	
	private String cmd2 = "";
	
	/**
	 * Default Constructor
	 */
	public ActiveDirSearchForm()
	{
	}

	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.firstName = "";
		this.lastName = "";
		for (int i = 0; i < this.getActiveDirUserList().size(); i++)
		{
			this.getActiveDirUserList(i).setBlnSelected(false);
		}		

	}

	/**
	 *
	 * @param mapping
	 * @param request
	 * @return
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_GET))
		{
			
				if(  (this.getFirstName() == null || this.getFirstName().trim().equals(""))
				  && (this.getLastName() == null || this.getLastName().trim().equals("")) && (this.getUsId() == null || this.getUsId().trim().equals("")))
				{
					errors.add("First Name or Last Name",new ActionError("error.usersearch.required.firstorlastname"));
				}
			
		}
		/*if (this.getCmd().equalsIgnoreCase(GPSConstants.BTN_VAL_SELECT))
		{
			boolean selection = false;
			for (int i = 0; i < this.getActiveDirUserList().size(); i++)
			{
				if (this.getActiveDirUserList(i).isBlnSelected())
				{
						selection = true;
				}
			}
			if(!(selection))
			{
				errors.add("Invalid user selection",new ActionError("error.userselect.required.validselection"));
			}
		}*/

		if(errors.empty())
		{
			return null;
		}
		else
		{
			return errors;
		}
	}

	/**
	 * @return Returns the activeDirUserList.
	 */
	public ArrayList getActiveDirUserList() {
		return activeDirUserList;
	}
	/**
	 * @param activeDirUserList The activeDirUserList to set.
	 */
	public void setActiveDirUserList(ArrayList activeDirUserList) {
		this.activeDirUserList = activeDirUserList;
	}
	
	/*****************************************************************************************/
	/**
	 *
	 * @return activeDirUserList size
	 */
	public long getActiveDirUserListSize()
	{
		return this.getActiveDirUserList().size();
	}
	/**
	 * @param asr object to place into vector
	 * @param index position to place asr object
	 */

	public void setActiveDirUserList(ActiveDirSearchDtlBean activeDirSearchDtlBean, int index)
	{
		this.activeDirUserList.set(index,activeDirSearchDtlBean);
	}

	/**
	 * @param index element to return
	 * @return Asr
	 */

	 public ActiveDirSearchDtlBean getActiveDirUserList(int index)
	 {
	 	ActiveDirSearchDtlBean activeDirSearchDtlBean = null;
		if(index >= 0 && index < this.getActiveDirUserList().size())
		{
			activeDirSearchDtlBean = (ActiveDirSearchDtlBean)this.activeDirUserList.get(index);
		}
		return activeDirSearchDtlBean;
	 }
		 // new code

		/**
			 * @param asr object to place into vector
			 * @param index position to place asr object
			 */
			public void setActiveDirUserListItem(ActiveDirSearchDtlBean activeDirSearchDtlBean,int index)
			{
				this.activeDirUserList.set(index, activeDirSearchDtlBean);
			}
			/**
			 * @param index element to return
			 * @return Asr
			 */
			 public ActiveDirSearchDtlBean getActiveDirUserListItem(int index)
			 {
			 	ActiveDirSearchDtlBean activeDirSearchDtlBean = null;
				if(index >= 0 && index < this.getActiveDirUserList().size())
				{
					activeDirSearchDtlBean = (ActiveDirSearchDtlBean)this.activeDirUserList.get(index);
				}
				return activeDirSearchDtlBean;
			 }
		 // new code	
			 
	
	/**
	 * @return Returns the blnSelected.
	 */
	public String getBlnSelected() {
		return blnSelected;
	}
	/**
	 * @param blnSelected The blnSelected to set.
	 */
	public void setBlnSelected(String blnSelected) {
		this.blnSelected = blnSelected;
	}
	/**
	 * @return
	 */
	public String getCmd2() {
		return cmd2;
	}

	/**
	 * @param string
	 */
	public void setCmd2(String string) {
		cmd2 = string;
	}

	

	/**
	 * @return
	 */
	public String getUsId() {
		return usId;
	}

	/**
	 * @param string
	 */
	public void setUsId(String string) {
		usId = string;
	}

}