package abbott.ai.tcgm.action.currency;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class CurrencyCodeMaint extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public CurrencyCodeMaint()
	{
		super();
	}

	/**
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
		String methodName = "perform";

		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.currency.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
				CurrencyCodeForm currencyCodeForm = (CurrencyCodeForm)form;//cast the form that was passed in to the correct type for this action

				currencyCodeForm.processCmd(mapping,request);

				CurrencyCodeMngr currencyCodeMngr = new CurrencyCodeMngr(); //create the helper class that will handle the processing

				try
				{
					currencyCodeForm.setCurrencylist(currencyCodeMngr.getCurrencyCode(this.getUserToken(request),currencyCodeForm.getSearchObject(),currencyCodeForm.getSortObject()));
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMException tcgme)
				{
					this.logger.error(tcgme.toString(),tcgme);
					//this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.currency.maintenance"));
					//this.forward = TCGMConstants.FORWARD_ERROR;
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);					
				}
		}
		//if errors exist then save them into the request
		if(! this.errors.empty())
		{
			this.saveErrors(request,errors);
		}

		//forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}
}