package abbott.ai.tcgm.action;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Provides the login routine for the application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class Login extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public Login()
	{
		super();
	}

	/**
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
		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.login.form.missing","userid"));
			this.setForward(TCGMConstants.G_FORWARD_LOGIN);
		}
		else
		{
			LoginForm loginForm = (LoginForm)form;//cast the form that was passed in to the correct type for this action

			User user = new User(loginForm.getUserid(),loginForm.getPassword());//create the new user object

			LoginMngr loginMngr = new LoginMngr(); //create the helper class that will handle the processing
			try
			{
				if(loginMngr.authenticateUser(user))
				{
					//session.setAttribute(TCGMConstants.SESSION_NAME_USER,user);//place the user object into the session
					//session.setAttribute(TCGMConstants.SESSION_NAME_STATE, new TCGMState() ); // initialize empty state object
					//this.setForward(TCGMConstants.FORWARD_SUCCESS);//forward is defined in TCGMAction
					
					if(loginMngr.authorizeUser(user))
					{
						/*************************************************************
						*	Added by Uday on 02/04/2006 to provide the user(Analyst)
						*   the option to view the maintenance records of any user. Start
						**************************************************************/
						user.setUserlist(loginMngr.getAllUsers(user));
						/*************************************************************
						*	Added by Uday on 02/04/2006 to provide the user(Analyst)
						*   the option to view the maintenance records of any user. End
						**************************************************************/
						//user.setPassword("");
						session.setAttribute(TCGMConstants.SESSION_NAME_USER,user);//place the user object into the session
						session.setAttribute(TCGMConstants.SESSION_NAME_STATE, new TCGMState() ); // initialize empty state object
						//Added by Veerendra on 10/02/2006 to redirect to login screen if the role is not correct 
						if (user.getRole().getAccessLevel() == 4 &&!user.isRptAccess())
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.login.role.failed","userid"));
							this.setForward(TCGMConstants.G_FORWARD_LOGIN);
						}
						else{
							this.setForward("main");
						}
						// Added by Udaya B Aravapalli on 01/12/2006 to restrict access based on roles.-- Start 
//						else if (user.getRole().getAccessLevel() == 3 )
//						{
//							this.setForward(TCGMConstants.G_FORWARD_ADMIN);//forward is defined in TCGMAction
//						}
//						else if (user.getRole().getAccessLevel() == 5)
//						{
//							this.setForward("rptuser");//forward is defined in TCGMAction
//						}
//						else
//						{
//							this.setForward(TCGMConstants.FORWARD_SUCCESS);//forward is defined in TCGMAction
//						}
						
						//Added by Udaya B Aravapalli on 01/12/2006 to restrict access based on roles.-- End 
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.oraclelogin.failed","userid"));
						this.setForward(TCGMConstants.G_FORWARD_LOGIN);
					}
						
				}
				else
				{
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.login.failed","userid"));
					this.setForward(TCGMConstants.G_FORWARD_LOGIN);
				}		
		
			}
			catch(TCGMException tcgme)
			{
				logger.error(tcgme.toString(),tcgme);

				//this is a known error thrown by oracle when an invalid user id / password combo is entered
				if(tcgme.getErrorMessage().indexOf("ORA-01017") > 0)
				{
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.login.failed","userid"));
					this.setForward(TCGMConstants.G_FORWARD_LOGIN);
				}
				else
				{
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			}
		}

		//if errors exist then save them into the request
		if(!errors.empty())
		{
			saveErrors(request,errors);
		}

		//forward to the next page or servlet found in the struts-config mapping
		return mapping.findForward(this.getForward());
	}
}