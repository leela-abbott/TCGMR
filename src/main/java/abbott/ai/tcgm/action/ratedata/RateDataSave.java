package abbott.ai.tcgm.action.ratedata;

import org.apache.log4j.Logger;
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
public class RateDataSave extends TCGMAction
{
	private static Logger myLogger = Logger.getLogger( "RateDataSave" );
	/**
	 * Default Constructor
	 */
	public RateDataSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.ratedata.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isRateSetSelected(request))
			{
				TCGMState state = this.getState(request);
				UserToken userToken = this.getUserToken(request);

				RateDataForm rateDataForm = (RateDataForm)form;//cast the form that was passed in to the correct type for this action

				rateDataForm.processCmd(mapping,request);
				RateDataMngr rateDataMngr = new RateDataMngr(); //create the helper class that will handle the processing
				try
				{
					rateDataForm.getAddNew().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());
					//errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.insert"));

					if(rateDataForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						rateDataMngr.addNewRateDataTran(userToken,rateDataForm.getAddNew());
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.insert"));
					}
					else if(rateDataForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						rateDataMngr.addSelectedRateDataToTrans(userToken,rateDataForm.getRateDataList(),TCGMConstants.ACT_CD_CHG);
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.insert"));
					}
					else if(rateDataForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						rateDataMngr.massUpdate(userToken,rateDataForm.getSearchObject(),rateDataForm.getAddNew());
						if(rateDataForm.getRateDataListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ratedatatran.insert"));
						}
					}
					else if(! rateDataForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + rateDataForm.getCmd());
					}
					

					//rateDataForm.getSearchObject().setModelIdInt(state.getRateModelId());

					// 4/17/03 I copied ts line from BpcsSave but it doesn't work.
					//rateDataForm.initDataset(state.getCurRateSetTableId());
					rateDataForm.getAddNew().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());
					myLogger.debug("RateDataSave: Dataset ID = " + state.getCurRateSetTableId());
					//rateDataForm.getAddNew().setDatasetTableIdInt(state.getCurRateSetTableId());
					//rateDataForm.getSearchObject().setDatasetTableIdInt(state.getCurRateSetTableId());

					rateDataForm.getPagingFilter().setTotalRecordsInSet(rateDataMngr.getCount(userToken,rateDataForm.getSearchObject()));
					rateDataForm.setRateDataList(rateDataMngr.getRateData(userToken,rateDataForm.getSearchObject(),rateDataForm.getPagingFilter(),rateDataForm.getSortObject()));

					//rateDataForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);

					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMDuplicateItemException ex)
				{

					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					try
					{
						rateDataForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						rateDataForm.getPagingFilter().setTotalRecordsInSet(rateDataMngr.getCount(userToken,rateDataForm.getSearchObject()));
						rateDataForm.setRateDataList(rateDataMngr.getRateData(userToken,rateDataForm.getSearchObject(),rateDataForm.getPagingFilter(),rateDataForm.getSortObject()));
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