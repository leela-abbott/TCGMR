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
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateExTranPublish extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public RateExTranPublish()
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
		else if(this.isSessionValid(request))
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
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Publish the maintenance records of any user. Start
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

					if (rateExTranForm.getUserSelected().equalsIgnoreCase("ALL"))
					{	
						rateExTranForm.getSearchObject().getRateEx().getCreateLog().setUserName(null);
						rateExTranForm.setUserSelected("ALL");
					}
					
					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Publish the maintenance records of any user. End
					**************************************************************/

					if(rateExTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_PUBLISH_SELECTED) )
					{
						rateExMngr.publishSelectedRateExTran(userToken,rateExTranForm.getRateExTranList());
					}
					else if(rateExTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_PUBLISH_ALL) )
					{
						rateExMngr.publishAllRateExTran(userToken,rateExTranForm.getSearchObject());
					}
					else if(! rateExTranForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.rateextran.published"));

					rateExTranForm.getAddNew().getRateEx().setModelIdInt(this.getState(request).getCurrentModelId());
					rateExTranForm.getAddNew().getRateEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					rateExTranForm.getPagingFilter().setTotalRecordsInSet(rateExMngr.getCount(userToken,rateExTranForm.getSearchObject()));

					rateExTranForm.setRateExTranList(rateExMngr.getRateExTran(userToken,rateExTranForm.getSearchObject(),rateExTranForm.getPagingFilter(),rateExTranForm.getSortObject()));
					rateExTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					rateExTranForm.getSearchObject().getRateEx().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
					rateExTranForm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMUpdateWithBlankUsernameException ex)
				{
					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.update.with.blank.username"));
					try
					{
						rateExTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						rateExTranForm.getPagingFilter().setTotalRecordsInSet(rateExMngr.getCount(userToken,rateExTranForm.getSearchObject()));
						rateExTranForm.setRateExTranList(rateExMngr.getRateExTran(userToken,rateExTranForm.getSearchObject(),rateExTranForm.getPagingFilter(),rateExTranForm.getSortObject()));
						rateExTranForm.getSearchObject().getRateEx().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli						
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
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}
}