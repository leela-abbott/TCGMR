package abbott.ai.tcgm.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.action.form.ActiveDirSearchForm;
import abbott.ai.tcgm.action.form.RptUserForm;
import abbott.ai.tcgm.entities.ActiveDirSearchDtlBean;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ActiveDirSearchMngr;
import abbott.ai.tcgm.helpers.RptUserMngr;
import abbott.ai.tcgm.helpers.UserMngr;
/*******************************************************************************

* $Abbott: ActiveDirSearch,v 1.0 2007/08/28 11:22:00 $
* Copyright (C) 2007  Abbott International,. All Rights Reserved.
* $name:         ActiveDirSearch.java
* $description:  The ActiveDirSearch.java is the action class for Active Directory
*				 Search functionality. This single class will handle different actions
*                performed by the user (Search User(s), Select User(s). Each of these
* 				 actions will be a seperate method .
******************************************************************************/
public class ActiveDirSearch extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public ActiveDirSearch()
	{
		super();
	}

	/**
	 *   Description:
	 *   This method acts as a controller for the different actions that can be
	 *   performed by the user and invokes the perform method associated with that
	 *   action.
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		this.errors.clear();
		
		/*****************************************************************************
		 * Check for user session. If the session does not exist or is timed out
		 * take back the user to the login page.
		 ******************************************************************************/
		if(this.isSessionValid(request))
		{
			
			

		ActiveDirSearchForm activeDirSearchForm = (ActiveDirSearchForm)form;		
		ActiveDirSearchMngr  activeDirSearchMgr = new ActiveDirSearchMngr();

		/*****************************************************************************
		 * If the user is coming for the first time , then this action will be invoked.
		 ******************************************************************************/
		if (activeDirSearchForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_VIEW))
		{
			performView(request, activeDirSearchForm, mapping,"rpt");
		}
		
		// For App User
		if (activeDirSearchForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_APPVIEW))
		{
			performView(request, activeDirSearchForm, mapping,"app" );
		}
		
		/*****************************************************************************
		 * If the user is searching for users, then this action will be invoked.
		 ******************************************************************************/
		else if (activeDirSearchForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_GET))
		{
			performGet(request, activeDirSearchForm, "rpt");
		}
		
		else if (activeDirSearchForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_GET_APP))
		{
			performGet(request, activeDirSearchForm, "app");
		}
		
		
		// For App User Select
		else if (activeDirSearchForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_SELECT_APP))
		{
			performAppUserSelect(request, activeDirSearchForm,activeDirSearchForm.getBlnSelected());

		}
		
		/*****************************************************************************
		 * If the user is selecting user to carry to the notfication list maintenance
		 * screen, then this action will be invoked.
		 ******************************************************************************/
		
		else if (activeDirSearchForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_SELECT))
		{
			performUserSelect(request, activeDirSearchForm,activeDirSearchForm.getBlnSelected());

		}
	  }
		/*****************************************************************************
		 * Check for errors , if any and report back in case of errors.
		 ******************************************************************************/
		if(!errors.empty())
		{
			saveErrors(request,errors);
		}

		return mapping.findForward(this.getForward());
	  }
	

	/**
	 *   Description:
	 *   This is the perform method for view action. This action will be
	 *   performed when the user comes for the first time to this page.
	 *
	 * @param request HttpServletRequest
	 * @param form ActionForm
 	 * @param mapping ActionMapping
	 */
	public void performView(HttpServletRequest request, 
			ActiveDirSearchForm activeDirSearchForm, 
			ActionMapping mapping, String action)
	{
		try{
			UserMngr userMngr = new UserMngr(); 
			activeDirSearchForm.setUserlist(userMngr.getUsers(this.getUserToken(request),activeDirSearchForm.getSearchObject(),activeDirSearchForm.getSortObject()));
				
		}catch(TCGMException tcgme)
		{
			this.logger.error(tcgme.toString(),tcgme);
			//this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.user.maintenance"));
			//this.forward = TCGMConstants.FORWARD_ERROR;
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);						
		}
		activeDirSearchForm.setActiveDirUserList(new ArrayList());
		activeDirSearchForm.reset(mapping,request);
		
		if(action.equalsIgnoreCase("rpt")) {
			this.setForward(TCGMConstants.FORWARD_SUCCESS);
		}else if(action.equalsIgnoreCase("app")) {
			this.setForward(TCGMConstants.APP_FORWARD_SUCCESS);
		}
	}

	/**
	 *   Description:
	 *   This is the perform method for Get action. This action will be
	 *   performed when the user searches for user(s) based on first and
	 *   last names.
	 *
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 */

	public void performGet(HttpServletRequest request, 
			ActiveDirSearchForm activeDirSearchForm, String action)
	{
		ActiveDirSearchMngr  activeDirSearchMgr = new ActiveDirSearchMngr();
		try
		{
			HttpSession session = request.getSession();
			String namesToSortBy[] = { "sn", "givenName", "cn", "mail", "userPrincipalName" };
			boolean sortAscending[] = { true, true, true, true, false };			
			activeDirSearchForm.setActiveDirUserList(activeDirSearchMgr.getUserList(activeDirSearchForm.getFirstName(),
													 activeDirSearchForm.getLastName(),activeDirSearchForm.getUsId(),namesToSortBy, sortAscending));
			
			UserMngr userMngr = new UserMngr(); 
			activeDirSearchForm.setUserlist(userMngr.getUsers(this.getUserToken(request),activeDirSearchForm.getSearchObject(),activeDirSearchForm.getSortObject()));
					
			activeDirSearchForm.setLastName(activeDirSearchForm.getLastName());
			activeDirSearchForm.setFirstName(activeDirSearchForm.getFirstName());
			activeDirSearchForm.setUsId(activeDirSearchForm.getUsId());
			
			request.setAttribute("activeDirSearchForm",activeDirSearchForm);
			
			if(action.equalsIgnoreCase("rpt")) {
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}else if(action.equalsIgnoreCase("app")) {
				this.setForward(TCGMConstants.APP_FORWARD_SUCCESS);
			}

		}
		catch(TCGMException gpse)
		{
			logger.error(gpse.toString(),gpse);

			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, gpse);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
	}

		/**
	 *   Description:
	 *   This is the perform method for Select action. This action will be
	 *   performed when the user selects an user to carry to the notification
	 *   list maintenance screen.
	 *
	 * @param request HttpServletRequest
	 * @param form ActionForm
	 */

	public void performUserSelect(HttpServletRequest request, ActiveDirSearchForm activeDirSearchForm,String userId)
	{

			ArrayList userList = null;
			RptUserForm  userMaintForm= new RptUserForm();
				userMaintForm.setUserList(new ArrayList());
			ActiveDirSearchMngr  activeDirSearchMgr = new ActiveDirSearchMngr();
		    RptUserMngr userMaintMgr = new RptUserMngr();

			RptUser userBean = new RptUser();
			userBean.setAffCode("-1");
			userBean.setSecCode("-1");
			userBean.setAreaCode("-1");
			userBean.setAffiliates(new HashMap());
			userBean.setSectors(new HashMap());
			userBean.setAreas(new HashMap());
			try{
			userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(),TCGMConstants.DIVISION));
			}catch(TCGMException tcgme){
				
			}
			/*try{
				userBean.setAffCode("-1");
				userBean.setAffiliates(TCGMUtil.getSortedMap(userMaintMgr.getAllAffiliates(),TCGMConstants.AFFILIATE));
				userBean.setSecCode("-1");
				userBean.setSectors(TCGMUtil.getSortedMap(userMaintMgr.getAllSectors(),TCGMConstants.SECTOR));
				userBean.setAreaCode("-1");
				userBean.setAreas(TCGMUtil.getSortedMap(userMaintMgr.getAllAreas(),TCGMConstants.AREA));

			}
			catch(TCGMException tcgme)
			{
				this.logger.error(tcgme.toString(),tcgme);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}*/
			ActiveDirSearchDtlBean activeDirSearchDtlBean = activeDirSearchMgr.getSelectedUser(activeDirSearchForm.getActiveDirUserList(),userId);

			userBean.setUserid(activeDirSearchDtlBean.getUserId());

			userBean.setUserid(userId);

			userBean.setFirstName(activeDirSearchDtlBean.getFirstName());
			userBean.setLastName(activeDirSearchDtlBean.getLastName());
			userBean.setEmail(activeDirSearchDtlBean.getEmail());
			userBean.setAbtNotesId(activeDirSearchDtlBean.getAbtNotesId());
			userBean.setEmployeeType(activeDirSearchDtlBean.getEmployeeType());
			userBean.setEmpDivision(activeDirSearchDtlBean.getDivision());
			userBean.setActionCode("-1");
			userBean.setSecCode("-1");

			userMaintForm.setRptUser(userBean);
			request.getSession().setAttribute("RptUser",userBean);
			request.getSession().setAttribute("userForm", userMaintForm);
			this.setForward(TCGMConstants.FORWARD_USER_MAINT_LIST);

	}
	
	public void performAppUserSelect(HttpServletRequest request, ActiveDirSearchForm activeDirSearchForm,String userId)
	{

		ArrayList userList = null;
		RptUserForm  userMaintForm= new RptUserForm();
			userMaintForm.setUserList(new ArrayList());
		ActiveDirSearchMngr  activeDirSearchMgr = new ActiveDirSearchMngr();
	    RptUserMngr userMaintMgr = new RptUserMngr();

		RptUser userBean = new RptUser();
		userBean.setAffCode("-1");
		userBean.setSecCode("-1");
		userBean.setAreaCode("-1");
		userBean.setAffiliates(new HashMap());
		userBean.setSectors(new HashMap());
		userBean.setAreas(new HashMap());
		/*try{
			userBean.setAffCode("-1");
			userBean.setAffiliates(TCGMUtil.getSortedMap(userMaintMgr.getAllAffiliates(),TCGMConstants.AFFILIATE));
			userBean.setSecCode("-1");
			userBean.setSectors(TCGMUtil.getSortedMap(userMaintMgr.getAllSectors(),TCGMConstants.SECTOR));
			userBean.setAreaCode("-1");
			userBean.setAreas(TCGMUtil.getSortedMap(userMaintMgr.getAllAreas(),TCGMConstants.AREA));

		}
		catch(TCGMException tcgme)
		{
			this.logger.error(tcgme.toString(),tcgme);
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}*/
		ActiveDirSearchDtlBean activeDirSearchDtlBean = activeDirSearchMgr.getSelectedUser(activeDirSearchForm.getActiveDirUserList(),userId);

		userBean.setUserid(activeDirSearchDtlBean.getUserId());

		userBean.setUserid(userId);

		userBean.setFirstName(activeDirSearchDtlBean.getFirstName());
		userBean.setLastName(activeDirSearchDtlBean.getLastName());
		userBean.setEmail(activeDirSearchDtlBean.getEmail());
		userBean.setAbtNotesId(activeDirSearchDtlBean.getAbtNotesId());
		userBean.setEmployeeType(activeDirSearchDtlBean.getEmployeeType());
		userBean.setDivision(activeDirSearchDtlBean.getDivision());
		userBean.setActionCode("-1");
		userBean.setSecCode("-1");

		userMaintForm.setRptUser(userBean);
		request.getSession().setAttribute("RptUser",userBean);
		request.getSession().setAttribute("userForm", userMaintForm);
		this.setForward(TCGMConstants.FORWARD_APPUSER_MAINT_LIST);

	}

}