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
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class RateDataMaint extends TCGMAction
{
	private static Logger myLogger = Logger.getLogger( "RateDataMaint" );
	/**
	 * Default Constructor
	 */
	public RateDataMaint()
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
//					rateDataForm.getSearchObject().setModelIdInt(state.getRateModelId());
//					rateDataForm.getSearchObject().setDatasetTableIdInt(state.getCurRateSetTableId());
					myLogger.debug("RateDataSave: Dataset ID = " + state.getCurRateSetTableId());

					rateDataForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),Integer.toString(state.getCurRateSetTableId()) );

					rateDataForm.getPagingFilter().setTotalRecordsInSet(rateDataMngr.getCount(this.getUserToken(request),rateDataForm.getSearchObject()));
					rateDataForm.setRateDataList(rateDataMngr.getRateData(this.getUserToken(request),rateDataForm.getSearchObject(),rateDataForm.getPagingFilter(),rateDataForm.getSortObject()));
					rateDataForm.getAddNew().getRateData().setBegPeriod("1");
					rateDataForm.getAddNew().getRateData().setEndPeriod("12");
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