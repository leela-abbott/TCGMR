package abbott.ai.tcgm.action;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
//import java.util.Vector;

//import abbott.ai.tcgm.action.*;
//import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.helpers.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class DefaultForward extends TCGMAction
{
	/**
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		/**
		 * use our tcgm custom actionmapping object because it has the
		 * custom property for loginRequired.  This is set in the struts-config.xml.
		 * The default value is true.
		 */
		TCGMActionMapping tcgmMapping = (TCGMActionMapping)mapping;

		this.forward = TCGMConstants.FORWARD_SUCCESS;
		if(tcgmMapping.getLoginRequired())
		{
			if(! isSessionValid(request) )
			{
				this.forward = TCGMConstants.G_FORWARD_LOGIN;
			}
		}
		return mapping.findForward(this.forward);
	}

	/**
	 * Default Constructor
	 */
	public DefaultForward()
	{
		super();
	}
}