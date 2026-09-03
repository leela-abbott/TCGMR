package abbott.ai.tcgm.action.ratedata;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class MaintRateSet extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public MaintRateSet()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.mngratesets.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_RATE_SET);
		}
		else if(this.isSessionValid(request))
		{
			MngRateSetsForm rateSetForm = (MngRateSetsForm)form;//cast the form that was passed in to the correct type for this action

			DatasetMngr datasetMngr = new DatasetMngr();
			ModelMngr modelMngr = new ModelMngr();

			try
			{
				Dataset dataset = datasetMngr.getDatasetById(this.getUserToken(request),rateSetForm.getSelectedDatasetTableIdInt());

				TCGMState state = (TCGMState) this.getState(request);

				state.setCurRateSet(dataset.getDatasetName(),dataset.getDatasetTableIdInt());

				TCGMModel model = new TCGMModel(DBConst.RATE_MODEL_NAME,TCGMModel.Type.FACTOR);

				/* 4-11-05 Readme - Rate sets are contained within a Rate model. The Rate model is */
				/*         created only once and never maintained after its creations. Only the   */
				/*         rate sets within the "RATE" model are maintained by the users.         */
				/*         Furthermore, the RATE model itself is not created using the            */
				/*         CREATE_MODEL function because you would have to specify a              */
				/*         Cycle and a Year and these are not specified on a Rate model. You just */
				/*         manually put in a record with the model name of "RATE" in the MODEL    */
				/*         table in Oracle. Any time this record is removed, it must be restored  */
				/*         as described or the "Select or Create Rate Set" screen will error out  */
				/*         when the user maintains a rate set. The error will occur on the java   */
				/*         statement below <getModelIdByName>.                                     */
				int rateModelId = modelMngr.getModelIdByName(this.getUserToken(request),model);
				state.setRateModel(DBConst.RATE_MODEL_NAME,rateModelId);

				session.setAttribute(TCGMConstants.SESSION_NAME_STATE,state);

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
			catch(TCGMException tcgme)
			{
				this.logger.error(tcgme.toString(),tcgme);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.mngratesets.maintain"));
				this.forward = TCGMConstants.FORWARD_FAILURE;
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