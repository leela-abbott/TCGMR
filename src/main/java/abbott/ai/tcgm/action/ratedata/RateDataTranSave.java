package abbott.ai.tcgm.action.ratedata;

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
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateDataTranSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public RateDataTranSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.ratedatatran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isRateSetSelected(request))
			{
				TCGMState state = this.getState(request);
				RateDataTranForm rateDataTranForm = (RateDataTranForm)form;//cast the form that was passed in to the correct type for this action

				rateDataTranForm.processCmd(mapping,request);

				RateDataMngr rateDataMngr = new RateDataMngr(); //create the helper class that will handle the processing
				ModelMngr modelMngr = new ModelMngr();

				try
				{
					/*************************************************************
					*	Added by Uday on 04/27/2006 to provide the user(Analyst)
					*   the option to publish the records of any user. Start
					**************************************************************/
					if (rateDataTranForm.getUserSelected() == null)
					{
						if(rateDataTranForm.getSearchObject().getRateData().getCreateLog().getUserName().equals(""))
						{
							rateDataTranForm.getSearchObject().getRateData().getCreateLog().setUserName(userToken.getUserid());
							rateDataTranForm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(rateDataTranForm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						rateDataTranForm.getSearchObject().getRateData().getCreateLog().setUserName(rateDataTranForm.getUserSelected());
					}
					
					if (rateDataTranForm.getUserSelected().equalsIgnoreCase("ALL"))
					{
						rateDataTranForm.getSearchObject().getRateData().getCreateLog().setUserName(null);
						rateDataTranForm.setUserSelected("ALL");
					}
					/*************************************************************
					*	Added by Uday on 04/27/2006 to provide the user(Analyst)
					*   the option to publish the records of any user. End
					**************************************************************/
					if(rateDataTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD) )
					{
						rateDataMngr.addNewRateDataTran(userToken,rateDataTranForm.getAddNew());
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.saved"));
					}
					else if(rateDataTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
					{
						rateDataMngr.saveSelectedRateDataTran(userToken,rateDataTranForm.getRateDataTranList());
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.saved"));
					}
					else if(rateDataTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						rateDataMngr.massUpdate(userToken,rateDataTranForm.getSearchObject(),rateDataTranForm.getAddNew());
						if(rateDataTranForm.getRateDataTranListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.saved"));
						}
					}
					else if(! rateDataTranForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
					
					//rateDataTranForm.getSearchObject().getRateData().setModelIdInt(state.getRateModelId());
					rateDataTranForm.getSearchObject().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());
					//rateDataTranForm.getAddNew().getRateData().setModelIdInt(state.getRateModelId());
					rateDataTranForm.getAddNew().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());
					rateDataTranForm.getPagingFilter().setTotalRecordsInSet(rateDataMngr.getCount(userToken,rateDataTranForm.getSearchObject()));

					rateDataTranForm.setRateDataTranList(rateDataMngr.getRateDataTran(userToken,rateDataTranForm.getSearchObject(),rateDataTranForm.getPagingFilter(),rateDataTranForm.getSortObject()));
					rateDataTranForm.getSearchObject().getRateData().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 --Udaya B Aravapalli
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMDuplicateItemException ex)
				{

					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					try
					{
						rateDataTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						rateDataTranForm.getSearchObject().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());
						rateDataTranForm.getAddNew().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());
						rateDataTranForm.getPagingFilter().setTotalRecordsInSet(rateDataMngr.getCount(userToken,rateDataTranForm.getSearchObject()));
						rateDataTranForm.setRateDataTranList(rateDataMngr.getRateDataTran(userToken,rateDataTranForm.getSearchObject(),rateDataTranForm.getPagingFilter(),rateDataTranForm.getSortObject()));
						rateDataTranForm.getSearchObject().getRateData().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 --Udaya B Aravapalli
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
					catch(TCGMException exc)
					{
						this.logger.error(exc.toString(),exc);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exc);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}
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
		this.logger.debug("RateDataTranSave Forward" + this.getForward());
		return mapping.findForward(this.getForward());
	}
}