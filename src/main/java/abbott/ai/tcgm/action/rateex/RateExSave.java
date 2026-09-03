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
public class RateExSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public RateExSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.rateex.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				UserToken userToken = this.getUserToken(request);

				RateExForm rateExForm = (RateExForm)form;//cast the form that was passed in to the correct type for this action

				rateExForm.processCmd(mapping,request);
				boolean duplicate = true;
				RateExMngr rateExMngr = new RateExMngr(); //create the helper class that will handle the processing
				try
				{
					if(rateExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						duplicate =  rateExMngr.addNewRateExTran(userToken,rateExForm.getAddNew());
//						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.rateextran.insert"));
					}
					else if(rateExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						duplicate = rateExMngr.addSelectedRateExToTrans(userToken,rateExForm.getRateExList(),TCGMConstants.ACT_CD_CHG);
//						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.rateextran.insert"));
					}
					else if(rateExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{						
						rateExMngr.massUpdate(userToken,rateExForm.getSearchObject(),rateExForm.getAddNew());
						if(rateExForm.getRateExListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.rateextran.insert"));
						}
					}
					else if(! rateExForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + rateExForm.getCmd());
					}				
					if(duplicate)
					{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.rateextran.insert"));
					}
					else
					{   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					}

					rateExForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					rateExForm.getPagingFilter().setTotalRecordsInSet(rateExMngr.getCount(userToken,rateExForm.getSearchObject()));
					//if the insert is successfull loading the fresh records to show
					if(duplicate){
					rateExForm.setRateExList(rateExMngr.getRateEx(userToken,rateExForm.getSearchObject(),rateExForm.getPagingFilter(),rateExForm.getSortObject()));
					}
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMDuplicateItemException ex)
				{

					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					try
					{
						rateExForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						rateExForm.getPagingFilter().setTotalRecordsInSet(rateExMngr.getCount(userToken,rateExForm.getSearchObject()));
						rateExForm.setRateExList(rateExMngr.getRateEx(userToken,rateExForm.getSearchObject(),rateExForm.getPagingFilter(),rateExForm.getSortObject()));
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