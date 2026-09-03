package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.JobConstants;

import org.apache.struts.action.*;
//import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class FetchCostExchData extends TCGMAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
	{
		try
		{
			if( this.isSessionValid(request))
			{
				UserToken ut = this.getUserToken(request);
				errors.clear(); // Remove old messages
				CostExchDataMngr cexm = new CostExchDataMngr();
				MngCostExchDataForm myForm = (MngCostExchDataForm) form;
		
				// 3-6-06 Need to set the CXCHG_VERSN parm here to be the same value as the createName entered
				//        by the user on the screen = MMYY
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				md.setModelParmII(-1, JobConstants.PN_CXCHG_VERSN, myForm.getCreateName());

				cexm.fetchCostExchData(myForm.getCreateName());

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}

		catch(TCGMInvalidCostExchangeNameException ex)
		{
			this.logger.error(ex.toString(),ex);
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalid.costexchg.name"));
			this.setForward(TCGMConstants.FORWARD_SUCCESS);
		}
		catch(TCGMException ex)
		{
			this.logger.error(ex.toString(),ex);
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		//if errors exist then save them into the request
		if(! this.errors.empty())
		{
			this.saveErrors(request,errors);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}

	public FetchCostExchData() {
		super();
	}

}