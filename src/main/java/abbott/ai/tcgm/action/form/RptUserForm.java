package abbott.ai.tcgm.action.form;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.ActiveAffMaint;
import abbott.ai.tcgm.entities.RptUser;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Action form for the login page</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Abbott Laboratories</p>
 * @author 
 * @version 1.0
 */

public class RptUserForm extends TCGMForm
{
	private RptUser searchObject = new RptUser();
	
	private ArrayList userList = new ArrayList();
	
	private RptUser rptUser = new RptUser();
	
	private String selDesc    ="";
	
	private String category = "";
	private String categoryid = "";
	private String categoryname = "";
	
	private String affCodeList= "";
	private String secCodeList= "";
	private String areaCodeList= "";
	private String divisionCode= "";
	private boolean blnDivision=false;
	private ArrayList affMaintList = new ArrayList();
	
	/**
	 * Default Constructor
	 */
	public RptUserForm()
	{
	}

	/**
	 *
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		String str = "";
/*		HashMap obj_HashMap = new HashMap();

		UserBean userBean = (UserBean)request.getSession().getAttribute("GPSUser");
		obj_HashMap.put("-1", "Select Country");
		userBean.setCountries(obj_HashMap);
		request.getSession().setAttribute("GPSUser", userBean);
        
		userBean = new UserBean();
		userBean.setAreaCode("-1");
		userBean.setCountryCode("-1");
		this.setUserBean(userBean);
		this.setUserList(new ArrayList());*/
		
	
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
		
		if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_SAVE))
		{
			if (this.getRptUser().getUserid()== null || this.getRptUser().getUserid().equals(""))
			{
				errors.add("Invalid userId",new ActionError("error.userMaint.required.userid"));
			}
			/*	if (this.getRptUser().getFirstName()== null || this.getRptUser().getFirstName().equals(""))
			{
				errors.add("Invalid firstName",new ActionError("error.userMaint.required.firstname"));
			}
			if (this.getRptUser().getLastName()== null || this.getRptUser().getLastName().equals(""))
			{
				errors.add("Invalid lastName",new ActionError("error.userMaint.required.lastname"));
			}
			if (this.getRptUser().getScope()== null ||this.getRptUser().getScope().equals("")	)
			{
				errors.add("Invalid scope",new ActionError("error.userMaint.required.scope"));
			}
			else if (!(this.getRptUser().getScope().equalsIgnoreCase("DIVISION")))
			{
				if (this.getRptUser().getAreaCode()== null ||this.getRptUser().getAreaCode().equals("")	)
				{
					errors.add("Invalid areacode",new ActionError("error.userMaint.required.area"));
				}
				if (this.getRptUser().getScope().equalsIgnoreCase("COUNTRY"))
				{
					if (this.getRptUser().getCountryCode()== null ||this.getRptUser().getCountryCode().equals("")	)
					{
						errors.add("Invalid countrycode",new ActionError("error.userMaint.required.country"));
					}
					
				}
			}*/
			
		}

	/******************************************************************************
	 * 
	 * Need to put validation code in here
	 * 
	 *******************************************************************************/
		if(errors.empty())
		{
			return null;
		}
		else
		{
			return errors;
		}
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return activeDirUserList size
	 */
	public long getUserListSize()
	{
		return this.getUserList().size();
	}
	/**
	 * @param asr object to place into vector
	 * @param index position to place asr object
	 */

	public void setUserlist(RptUser userBean, int index)
	{
		this.userList.set(index,userBean);
	}

	/**
	 * @param index element to return
	 * @return Asr
	 */

	 public RptUser getUserlist(int index)
	 {
		RptUser userBean = null;
		if(index >= 0 && index < this.getUserList().size())
		{
			userBean = (RptUser)this.userList.get(index);
		}
		return userBean;
	 }

		/**
			 * @param asr object to place into vector
			 * @param index position to place asr object
			 */
			public void setUserItem(RptUser userBean,int index)
			{
				this.userList.set(index, userBean);
			}
			/**
			 * @param index element to return
			 * @return Asr
			 */
			 public RptUser getUserListItem(int index)
			 {
				RptUser userBean = null;
				if(index >= 0 && index < this.getUserList().size())
				{
					userBean = (RptUser)this.userList.get(index);
				}
				return userBean;
			 }
		 // new code	
	/**
	 * @return Returns the notificationList.
	 */
	public ArrayList getUserList() {
		if(this.userList == null)
				{
					this.userList = new ArrayList();
				}
				return this.userList;
	}
	/**
	 * @param notificationList The notificationList to set.
	 */
	public void setUserList(ArrayList userList) {
		this.userList = userList;
	}

	/**
	 * @return Returns the userBean.
	 */
	public RptUser getRptUser() {
		return rptUser;
	}
	/**
	 * @param userBean The userBean to set.
	 */
	public void setRptUser(RptUser rptUser) {
		this.rptUser = rptUser;
	}
	/**
	 * @return
	 */
	public RptUser getSearchObject() {
		return searchObject;
	}

	
	/**
	 * @param user
	 */
	public void setSearchObject(RptUser user) {
		searchObject = user;
	}

	
	/**
	 * @return
	 */
	public String getSelDesc() {
		return selDesc;
	}

	/**
	 * @param string
	 */
	public void setSelDesc(String string) {
		selDesc = string;
	}

	/**
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return
	 */
	public String getCategoryid() {
		return categoryid;
	}

	/**
	 * @return
	 */
	public String getCategoryname() {
		return categoryname;
	}

	/**
	 * @param string
	 */
	public void setCategory(String string) {
		category = string;
	}

	/**
	 * @param string
	 */
	public void setCategoryid(String string) {
		categoryid = string;
	}

	/**
	 * @param string
	 */
	public void setCategoryname(String string) {
		categoryname = string;
	}

	/**
	 * @return
	 */
	public String getAffCodeList() {
		return affCodeList;
	}

	/**
	 * @param string
	 */
	public void setAffCodeList(String string) {
		affCodeList = string;
	}

	/**
	 * @return
	 */
	public String getSecCodeList() {
		return secCodeList;
	}

	/**
	 * @param string
	 */
	public void setSecCodeList(String string) {
		secCodeList = string;
	}

	/**
	 * @return
	 */
	public String getAreaCodeList() {
		return areaCodeList;
	}

	/**
	 * @param string
	 */
	public void setAreaCodeList(String string) {
		areaCodeList = string;
	}

	/**
	 * @return Returns the divisionCode.
	 */
	public String getDivisionCode() {
		return divisionCode;
	}
	/**
	 * @param divisionCode The divisionCode to set.
	 */
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	/**
	 * @return Returns the blnDivision.
	 */
	public boolean isBlnDivision() {
		return blnDivision;
	}
	/**
	 * @param blnDivision The blnDivision to set.
	 */
	public void setBlnDivision(boolean blnDivision) {
		this.blnDivision = blnDivision;
	}
	/**
	 * @return Returns the affMaintList.
	 */
	public ArrayList getAffMaintList() {
		if(this.affMaintList == null)
		{
			this.affMaintList = new ArrayList();
		}
		return affMaintList;
	}
	/**
	 * @param affMaintList The affMaintList to set.
	 */
	public void setAffMaintList(ArrayList affMaintList) {
		this.affMaintList = affMaintList;
	}
	
	public long getAffMaintListSize()
	{
		return this.getAffMaintList().size();
	}
	/**
	 * @param asr object to place into vector
	 * @param index position to place asr object
	 */

	public void setAffMaintlist(ActiveAffMaint userBean, int index)
	{
		this.affMaintList.set(index,userBean);
	}

	/**
	 * @param index element to return
	 * @return Asr
	 */

	 public ActiveAffMaint getAffMaintlist(int index)
	 {
	 	ActiveAffMaint userBean = null;
		if(index >= 0 && index < this.getAffMaintList().size())
		{
			userBean = (ActiveAffMaint)this.affMaintList.get(index);
		}
		return userBean;
	 }

		/**
			 * @param asr object to place into vector
			 * @param index position to place asr object
			 */
			public void setAffMaintItem(ActiveAffMaint userBean,int index)
			{
				this.affMaintList.set(index, userBean);
			}
			/**
			 * @param index element to return
			 * @return Asr
			 */
			 public ActiveAffMaint getAffMaintListItem(int index)
			 {
			 	ActiveAffMaint userBean = null;
				if(index >= 0 && index < this.getAffMaintList().size())
				{
					userBean = (ActiveAffMaint)this.affMaintList.get(index);
				}
				return userBean;
			 }
}