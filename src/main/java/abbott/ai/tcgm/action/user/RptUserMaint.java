package abbott.ai.tcgm.action.user;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.action.TCGMAction;
import abbott.ai.tcgm.action.form.RptUserForm;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.RptUserMngr;
import abbott.ai.tcgm.helpers.UserMngr;

/*******************************************************************************

* $Abbott: ActiveDirSearch,v 1.0 2007/08/28 11:22:00 $
* Copyright (C) 2007  Abbott International,. All Rights Reserved.
* $name:         NotificationList.java
* $description:  The NotificationList.java is the action class for NotificationList
*				 Maintenance functionality. This single class will handle different actions
*                performed by the user . These are, retrieve the users based on the area
* 				 and country selection, add users and remove users.
******************************************************************************/
public class RptUserMaint extends TCGMAction {

	/**
	 * Default Constructor
	 */
	public RptUserMaint()
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

		HttpSession session = request.getSession();

		String methodName = "perform";

		this.errors.clear();

		if(this.isSessionValid(request)){
			

		RptUserForm userMaintForm = (RptUserForm)form;
		RptUserMngr userMaintMgr = new RptUserMngr();

		/*****************************************************************************
		 * If the user is adding users (who shoulbe be notified), then this
		 * action will be invoked.
		 ******************************************************************************/
		if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_SAVE))
		{

			try
			{
				//Common
				userMaintForm.getRptUser().setDesc(userMaintForm.getSelDesc());
				userMaintForm.getRptUser().setRecipient("CAMID(\"LDAP abbott.corp:u:cn="+userMaintForm.getRptUser().getUserid().toLowerCase()+",ou=users\")");

				if(userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.AREA)){
					
					HashMap areaList = new HashMap();
					areaList = userMaintMgr.getList(userMaintForm.getAreaCodeList());
					Iterator iterate = areaList.keySet().iterator();
						while (iterate.hasNext()){
								String key = (String)iterate.next();
								String value = (String)areaList.get(key);
									userMaintForm.getRptUser().setCode(key);
									userMaintForm.getRptUser().setAreaCode(key);
									userMaintForm.getRptUser().setDesc(value);

								HashMap areaAffs = new HashMap();
								//areaAffs = userMaintMgr.getAllAreaAffs(userMaintForm.getRptUser().getAreaCode(),userMaintForm.getRptUser().getDivision());
								userMaintMgr.saveUser(userMaintForm.getRptUser(), areaAffs);
						}
				}else if(userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){

					HashMap secList = new HashMap();
					secList = userMaintMgr.getList(userMaintForm.getSecCodeList());
					Iterator iterate = secList.keySet().iterator();
						while (iterate.hasNext()){
								String key = (String)iterate.next();
								String value = (String)secList.get(key);
									userMaintForm.getRptUser().setCode(key);
									userMaintForm.getRptUser().setSecCode(key);
									userMaintForm.getRptUser().setDesc(value);

								HashMap secAffs = new HashMap();
								//secAffs = userMaintMgr.getAllSectorAffs(userMaintForm.getRptUser().getSecCode(),userMaintForm.getRptUser().getDivision());

								userMaintMgr.saveUser(userMaintForm.getRptUser(), secAffs);
						}

				}else if(userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.HQ_CON)||
						 userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP)||
						 userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)){

							HashMap hqAffs = new HashMap();
							//hqAffs = userMaintMgr.getAllHQAffs();
							RptUser rptUser = userMaintForm.getRptUser();
							rptUser.setDivision("All");

							userMaintMgr.saveUser(userMaintForm.getRptUser(), hqAffs);

				}else if(userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){

						   HashMap hqAffs = new HashMap();
						   //hqAffs = userMaintMgr.getAllHQAffs(userMaintForm.getRptUser().getDivision());

						   userMaintMgr.saveUser(userMaintForm.getRptUser(), hqAffs);

			   }else if(userMaintForm.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){

			   		HashMap affsList = new HashMap();

			   		affsList = userMaintMgr.getList(userMaintForm.getAffCodeList());
					Iterator iterate = affsList.keySet().iterator();
						while (iterate.hasNext()){
								String key = (String)iterate.next();
								String value = (String)affsList.get(key);
									userMaintForm.getRptUser().setCode(key);
									userMaintForm.getRptUser().setAffCode(key);
									userMaintForm.getRptUser().setDesc(value);	

									//userMaintMgr.saveUser(userMaintForm.getRptUser());
									HashMap affs = new HashMap();
									affs.put(key,value) ;

									userMaintMgr.saveUser(userMaintForm.getRptUser(), affs);
						}
				}

				if(userMaintForm.getRptUser().getMsg().equalsIgnoreCase("Duplicate Row")){
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.create"));//user.create.warning"));
				}else{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.create"));
				}
				userMaintForm.getRptUser().setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(),TCGMConstants.DIVISION));
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
			catch (TCGMException e)
			{
				this.logger.error(e.toString(),e);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("failure.user.create"));
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}

		}
		
		// For saving the TCGM Application Users
		else if (userMaintForm.getCmd().equalsIgnoreCase("saveAppUser"))
		{
				UserToken userToken = this.getUserToken(request);

				RptUser rptUser = userMaintForm.getRptUser();
				
				/*userForm.setCurrUser(this.getSessionUser(request));
				userForm.processCmd(mapping,request); */

				UserMngr userMngr = new UserMngr(); //create the helper class that will handle the processing

				try
				{
					if ((!(rptUser.getUserid() == null)) && 
					       (rptUser.getUserid().equalsIgnoreCase(userToken.getUserid())))
					{
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.admin.delete"));
					}
					else
					{
						// Get the values from RptUser Object & Set them in User Object
						User userToSave = new User();
						userToSave.setUserid(rptUser.getUserid());
						userToSave.setFirstName(rptUser.getFirstName());
						userToSave.setLastName(rptUser.getLastName());
						userToSave.setEmail(rptUser.getEmail());
						userToSave.setAbtNotesId(rptUser.getAbtNotesId());
						userToSave.setDivision(rptUser.getDivision());
						userToSave.setEmployeeType(rptUser.getEmployeeType());
						userToSave.setUserRole(rptUser.getRole());

						userMngr.saveUser(userToken,userToSave);
						
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.create"));
					}
					
					/*userForm.setUserToEdit(new User());
					userForm.setSearchObject(new User());
					userForm.setUserlist(userMngr.getUsers(userToken,userForm.getSortObject()));*/
					this.setForward(TCGMConstants.APP_FORWARD_SUCCESS);
				}
				catch (TCGMDuplicateItemException dupex)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.user.create.duplicate") );
							this.saveErrors(request, errors);
							this.setForward(TCGMConstants.APP_FORWARD_SUCCESS);
						}
				catch(TCGMException tcgme)
				{
					this.logger.error(tcgme.toString(),tcgme);
					//this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.user.save"));
					//this.forward = TCGMConstants.FORWARD_ERROR;
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);	
				}
			
		}
		else if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_REMOVE))
		{
			try
			{
				userMaintMgr.deleteRptUser(userMaintForm.getUserList());
				userMaintForm.setSearchObject(new RptUser());
				userMaintForm.setUserList(userMaintMgr.getUserList(userMaintForm.getRptUser(), true));
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.remove"));
				this.setForward("filter");
			}
			catch(TCGMException e)
			{
				this.logger.error(e.toString(),e);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("failure.user.remove"));
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);		
			}

		}
		else if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_REGENERATE))
		{
			try
			{
				userMaintMgr.regenerateSelectedUsers(userMaintForm.getUserList());
				userMaintForm.reset(mapping,request);
				userMaintForm.setUserList(userMaintMgr.getUserList(userMaintForm.getRptUser(), true));
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.regenerate"));
				this.setForward("filter");
			}
			catch (TCGMException e)
			{
				this.logger.error(e.toString(),e);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("failure.user.regenerate"));
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}

		}

		else if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_GET))
		{
			try
			{
				userMaintForm.getRptUser().setDesc(userMaintForm.getSelDesc());
				ArrayList aryList = userMaintMgr.getUserList(userMaintForm.getRptUser(), true);
				//System.out.println(aryList);
				userMaintForm.setRptUser(userMaintForm.getRptUser());
				userMaintForm.setUserList(aryList);
				//System.out.println(userMaintForm.getUserList());
				session.setAttribute("userForm",userMaintForm);
				this.setForward("filter");
			}
			catch (TCGMException ex)
			{
				this.logger.error(ex.toString(),ex);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
		}

		else if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_CREATE))
		{
			/*try
			{*/
			userMaintForm.setUserList(new ArrayList());
			userMaintForm.reset(mapping,request);
			RptUser userBean = new RptUser();
			userBean.setAffCode("-1");
			//userBean.setAffiliates(TCGMUtil.getSortedMap(userMaintMgr.getAllAffiliates(),TCGMConstants.AFFILIATE));
			userBean.setSecCode("-1");
			//userBean.setSectors(TCGMUtil.getSortedMap(userMaintMgr.getAllSectors(),TCGMConstants.SECTOR));
			userBean.setAreaCode("-1");
			//userBean.setAreas(TCGMUtil.getSortedMap(userMaintMgr.getAllAreas(),TCGMConstants.AREA));
			userBean.setAffiliates(new HashMap());
			userBean.setSectors(new HashMap());
			userBean.setAreas(new HashMap());
			userMaintForm.setRptUser(userBean);
			session.setAttribute("RptUser",userBean);
			session.setAttribute("userForm",userMaintForm);
			this.setForward(TCGMConstants.FORWARD_SUCCESS);
			/*}
			catch(TCGMException tcgme)
			{
				this.logger.error(tcgme.toString(),tcgme);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}*/
		}
		else if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_FILTER))
			{
			try
				{
				userMaintForm.setUserList(new ArrayList());
				userMaintForm.reset(mapping,request);
				RptUser userBean = new RptUser();
				userBean.setAffCode("-1");
				//userBean.setAffiliates(TCGMUtil.getSortedMap(userMaintMgr.getAllAffiliates(),TCGMConstants.AFFILIATE));
				userBean.setSecCode("-1");
				//userBean.setSectors(TCGMUtil.getSortedMap(userMaintMgr.getAllSectors(),TCGMConstants.SECTOR));
				userBean.setAreaCode("-1");
				//userBean.setAreas(TCGMUtil.getSortedMap(userMaintMgr.getAllAreas(),TCGMConstants.AREA));
				//userMaintForm.setRptUser(userBean);
				userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(),TCGMConstants.DIVISION));
				userBean.setAffiliates(new HashMap());
				userBean.setSectors(new HashMap());
				userBean.setAreas(new HashMap());
				session.setAttribute("RptUser",userBean);
				session.setAttribute("userForm",userMaintForm);

				this.setForward("filter");
				}
				catch(TCGMException tcgme)
				{
					this.logger.error(tcgme.toString(),tcgme);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			}else if(userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_BURST)){
				try
				{
				RptUser userBean = new RptUser();
				userBean.setAffCode("-1");
				userBean.setAffiliates(new HashMap());
				userBean.setSecCode("-1");
				userBean.setSectors(new HashMap());
				userBean.setAreaCode("-1");
				userBean.setAreas(new HashMap());
				userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getDivision(),TCGMConstants.DIVISION));
				userMaintForm.setRptUser(userBean);
				session.setAttribute("RptUser",userBean);
				session.setAttribute("userForm",userMaintForm);
				

				this.setForward("burst");
				}
				catch(TCGMException tcgme)
				{
					this.logger.error(tcgme.toString(),tcgme);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			}
			else if(userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_BURST)){

				try
				    {
						userMaintMgr.addToBurstTables(userMaintForm.getRptUser(), userMaintForm.getCategoryid(), userMaintForm.getCategoryname(),userMaintForm.getCategory(),userMaintForm.getDivisionCode());
						RptUser userBean = new RptUser();
						userBean.setAffCode("-1");
						userBean.setAffiliates(new HashMap());
						userBean.setSecCode("-1");
						userBean.setSectors(new HashMap());
						userBean.setAreaCode("-1");
						userBean.setAreas(new HashMap());
						userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getDivision(),TCGMConstants.DIVISION));
						userMaintForm.setRptUser(userBean);
						session.setAttribute("RptUser",userBean);
						session.setAttribute("userForm",userMaintForm);
											
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.record.create"));
						this.setForward("burst");
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}
			}			
			else if(userMaintForm.getCmd().equalsIgnoreCase("loadBurst")){

				try
				    {
						userMaintMgr.loadBurstTables();
						RptUser userBean = new RptUser();
						userBean.setAffCode("-1");
						userBean.setAffiliates(new HashMap());
						userBean.setSecCode("-1");
						userBean.setSectors(new HashMap());
						userBean.setAreaCode("-1");
						userBean.setAreas(new HashMap());
						userMaintForm.setRptUser(userBean);
						session.setAttribute("RptUser",userBean);
						session.setAttribute("userForm",userMaintForm);
											
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.record.burst"));
						this.setForward("burst");
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}
			}
			else if(userMaintForm.getCmd().equalsIgnoreCase("activeaff")){				
				RptUser userBean = new RptUser();
				userBean.setAffCode("-1");
				userBean.setAffiliates(new HashMap());				
				userMaintForm.setRptUser(userBean);
				userMaintForm.setAffMaintList(new ArrayList());
				session.setAttribute("RptUser",userBean);
				session.setAttribute("userForm",userMaintForm);
				this.setForward("activeaff");
				
			}
			else if(userMaintForm.getCmd().equalsIgnoreCase("getactiveaff")){				
				try
				{
					
					ArrayList aryList = userMaintMgr.getAffiliateStatus(userMaintForm.getRptUser().getDivision(),userMaintForm.getCategory());
					
					userMaintForm.setRptUser(userMaintForm.getRptUser());
					userMaintForm.setAffMaintList(aryList);
					
					session.setAttribute("userForm",userMaintForm);
					request.setAttribute("cat",userMaintForm.getCategory());
					request.setAttribute("div",userMaintForm.getRptUser().getDivision());
					this.setForward("activeaff");
				}
				catch (TCGMException ex)
				{
					this.logger.error(ex.toString(),ex);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
				
				
				
			}
			else if(userMaintForm.getCmd().equalsIgnoreCase("saveactiveaff")){
				try{
					userMaintMgr.saveAffiliateStatus(userMaintForm.getRptUser().getDivision(),userMaintForm.getAffMaintList(),userMaintForm.getCategoryname());
					ArrayList aryList = userMaintMgr.getAffiliateStatus(userMaintForm.getRptUser().getDivision(),userMaintForm.getCategory());
					
					userMaintForm.setRptUser(userMaintForm.getRptUser());
					userMaintForm.setAffMaintList(aryList);					
					request.setAttribute("cat",userMaintForm.getCategory());
					request.setAttribute("div",userMaintForm.getRptUser().getDivision());
					session.setAttribute("userForm",userMaintForm);
					this.setForward("activeaff");
				}
				catch(TCGMException tcgme)
				{
					this.logger.error(tcgme.toString(),tcgme);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
				
			}
			else if (userMaintForm.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_REMOVE))
			{
				userMaintForm.setUserList(new ArrayList());
				RptUser userBean = new RptUser();
				userMaintForm.setRptUser(userBean);
				userMaintForm.reset(mapping,request);
				this.setForward("filter");
			}
			else if (userMaintForm.getCmd().equalsIgnoreCase("usersView"))
			{
				this.setForward("usersview");
			}
			else if (userMaintForm.getCmd().equalsIgnoreCase("users"))
			{
				try
					{
						User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
						userMaintMgr.generateUsers(user);
						this.setForward("users");
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}

			}
			else if (userMaintForm.getCmd().equalsIgnoreCase("datausersView"))
			{
				this.setForward("datausersView");
			}
			else if (userMaintForm.getCmd().equalsIgnoreCase("datausers"))
			{
				try
					{
						User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
						userMaintMgr.getRptUser(user);
						this.setForward("datausers");
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}

			}
			else if (userMaintForm.getCmd().equalsIgnoreCase("recreate"))
			{
				try
					{
						
						userMaintMgr.recreateUser(userMaintForm.getUserList());
						userMaintForm.getRptUser().setDesc(userMaintForm.getSelDesc());						
						ArrayList aryList = userMaintMgr.getUserList(userMaintForm.getRptUser(), true);					
						userMaintForm.setRptUser(userMaintForm.getRptUser());
						userMaintForm.setUserList(aryList);						
						session.setAttribute("userForm",userMaintForm);
						this.errors.clear();
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.create"));
						this.setForward("filter");
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}

			}
			else if (userMaintForm.getCmd().equalsIgnoreCase("recertify"))
			{
				try
					{
						User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
						userMaintMgr.recertifyUser(userMaintForm.getUserList(), user.getUserid());
						userMaintForm.getRptUser().setDesc(userMaintForm.getSelDesc());						
						ArrayList aryList = userMaintMgr.getUserList(userMaintForm.getRptUser(), true);					
						userMaintForm.setRptUser(userMaintForm.getRptUser());
						userMaintForm.setUserList(aryList);						
						session.setAttribute("userForm",userMaintForm);
						this.errors.clear();
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.user.create"));
						this.setForward("filter");
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}

			}else if(userMaintForm.getCmd().equalsIgnoreCase("export")){
				ServletOutputStream out = response.getOutputStream();
				try {
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition","attachment; filename=Export.txt");
					PrintStream fout=new PrintStream(out);
					fout.println("User Id\tFirst Name\tLast Name\tRole\tRole Desc\tCreate Date\tRecertify Date");					
					
					for(int i = 0; i < userMaintForm.getUserList().size(); i++){
						RptUser rptUser = (RptUser)userMaintForm.getUserList().get(i);
						if(rptUser.isSelected())
						{
							fout.println(rptUser.getUserid()+ "\t"+rptUser.getFirstName()+ "\t"+rptUser.getLastName()+ "\t"+rptUser.getRole()+ "\t"+rptUser.getRoleDesc()+ "\t"+rptUser.getCreateDate()+ "\t"+rptUser.getRecertifyDate());
						}
					}
					fout.close();			
					
				}
				catch (Exception e) {					
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}  
				finally {
					if (out != null){
						out.flush();
						out.close();
					}
				}
			}
			else if(userMaintForm.getCmd().equalsIgnoreCase("Reset"))
			{
				userMaintForm.getRptUser().setUserid("");
				userMaintForm.getRptUser().setFirstName("");
				userMaintForm.getRptUser().setLastName("");
				session.setAttribute("userForm",userMaintForm);
			}
		}
		if(!errors.empty())
		{
			saveErrors(request,errors);
		}
		return mapping.findForward(this.getForward());
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

/*	public void performSave(NotificationListForm notificationListForm, NotificationListFilterBean searchObject)
	{

	}*/
	public String getAreaDesc(String areaCode){
		 String areaDesc = "NONE";
		 if(areaCode.equals("01")){
			areaDesc = "LA";
		 }else if(areaCode.equals("02")){
			areaDesc = "EUR";
	 	 }else if(areaCode.equals("04")){
			areaDesc = "PAA";
		 }else if(areaCode.equals("05")){
		 	areaDesc = "CAN";
		 }else if(areaCode.equals("06")){
			areaDesc = "JPN";
		 }
		 return areaDesc;
	}

}
