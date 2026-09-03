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
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateDataDelete extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public RateDataDelete()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.ratedata.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isRateSetSelected(request))
			{
				TCGMState state = this.getState(request);
				UserToken userToken = this.getUserToken(request);
				boolean duplicate = true;
				RateDataForm rateDataForm = (RateDataForm)form;//cast the form that was passed in to the correct type for this action

				rateDataForm.processCmd(mapping,request);

				RateDataMngr rateDataMngr = new RateDataMngr(); //create the helper class that will handle the processing
				try
				{
					// 4/17/03 When I left on thurs, this line did not fix my -1 problem so I commented it out.
					rateDataForm.getAddNew().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());

					if(rateDataForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_ALL) )
					{
						duplicate = rateDataMngr.addAllRateDataToTrans(this.getUserToken(request),rateDataForm.getSearchObject(),TCGMConstants.ACT_CD_DEL);
					}
					else if(rateDataForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_SELECTED) )
					{
						duplicate = rateDataMngr.addSelectedRateDataToTrans(this.getUserToken(request),rateDataForm.getRateDataList(),TCGMConstants.ACT_CD_DEL);
					}
					else if(! rateDataForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
					if(duplicate)
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.insert"));
					 else   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					
					//rateDataForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					rateDataForm.getSearchObject().setModelIdInt(state.getRateModelId());
					rateDataForm.getSearchObject().setDatasetTableIdInt(state.getCurRateSetTableId());
					rateDataForm.getPagingFilter().setTotalRecordsInSet(rateDataMngr.getCount(this.getUserToken(request),rateDataForm.getSearchObject()));
					rateDataForm.setRateDataList(rateDataMngr.getRateData(this.getUserToken(request),rateDataForm.getSearchObject(),rateDataForm.getPagingFilter(),rateDataForm.getSortObject()));

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