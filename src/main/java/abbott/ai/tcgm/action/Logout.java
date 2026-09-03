package abbott.ai.tcgm.action;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

//import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Provides the login routine for the application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class Logout extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public Logout()
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

        // Attempt to close the cached connection
        if ( this.isSessionValid(request) )
            SQLUtil.closeCachedConnection( this.getUserToken(request) );

        // Clear all session information
        request.getSession().invalidate();

        // Set message
		this.errors.clear();
        errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.logout"));
		saveErrors(request,errors);

		// Forward back to the login page.
		return mapping.findForward( TCGMConstants.G_FORWARD_LOGIN );
	}
}