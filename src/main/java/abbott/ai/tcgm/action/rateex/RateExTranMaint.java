package abbott.ai.tcgm.action.rateex;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author Brian Dennis
 * @version 1.0
 */
public class RateExTranMaint extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public RateExTranMaint()
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
		String methodName = "perform";

		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.rateextran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if( this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isModelSelected(request))
			{
				RateExTranForm rateExTranForm = (RateExTranForm)form;//cast the form that was passed in to the correct type for this action

				rateExTranForm.processCmd(mapping,request);

				RateExMngr rateExMngr = new RateExMngr(); //create the helper class that will handle the processing
				ModelMngr modelMngr = new ModelMngr();

				try
				{
					rateExTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					/*************************************************************
					*	Added by Uday on 02/04/2006 to provide the user(Analyst)
					*   the option to view the maintenance records of any user. Start
					**************************************************************/
					if (rateExTranForm.getUserSelected() == null)
					{
						if(rateExTranForm.getSearchObject().getRateEx().getCreateLog().getUserName().equals(""))
						{
							rateExTranForm.getSearchObject().getRateEx().getCreateLog().setUserName(userToken.getUserid());
							rateExTranForm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(rateExTranForm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						rateExTranForm.getSearchObject().getRateEx().getCreateLog().setUserName(rateExTranForm.getUserSelected());
					}
					/*************************************************************
					*	Added by Uday on 02/04/2006 to provide the user(Analyst)
					*   the option to view the maintenance records of any user. End
					**************************************************************/
					rateExTranForm.getAddNew().getRateEx().setModelIdInt(this.getState(request).getCurrentModelId());
					rateExTranForm.getAddNew().getRateEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					rateExTranForm.getPagingFilter().setTotalRecordsInSet(rateExMngr.getCount(userToken,rateExTranForm.getSearchObject()));

					// 3/31/03 Default 1 & 12 for the begin & end periods on the maint transaction page
					rateExTranForm.getAddNew().getRateEx().setBegPeriod("1");
					rateExTranForm.getAddNew().getRateEx().setEndPeriod("12");

					rateExTranForm.setRateExTranList(rateExMngr.getRateExTran(userToken,rateExTranForm.getSearchObject(),rateExTranForm.getPagingFilter(),rateExTranForm.getSortObject()));
					
					//06/29/05 Modified by Sridevi. New code for a fix for displaying only the open models
					//Setup empty factor model as search object to return all open factor models
					FactorModel fm = new FactorModel();
					fm.setStatus(TCGMModel.Status.OPEN);					
					rateExTranForm.setModels(modelMngr.getModels(userToken, fm));
					//rateExTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					//06/29/05 Modified by Sridevi. End of new code for the fix for displaying only the open models									   
					
					rateExTranForm.getSearchObject().getRateEx().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
					rateExTranForm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMException ex)
				{
					this.logger.error(ex.toString(),ex);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
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