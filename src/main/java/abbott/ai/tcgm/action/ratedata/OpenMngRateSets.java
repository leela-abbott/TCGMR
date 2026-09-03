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
public class OpenMngRateSets extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public OpenMngRateSets()
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

			rateSetForm.processCmd(mapping,request);

			DatasetMngr datasetMngr = new DatasetMngr();
			try
			{
				rateSetForm.setEditRateSet(new Dataset());
				rateSetForm.setCopyFromDatasetTableId(TCGMConstants.NONE_SELECTED);
				rateSetForm.setRateSetList(datasetMngr.getDatasetByTableName(this.getUserToken(request),DBConst.TABLE_RATE_DATA));
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
			catch(TCGMException tcgme)
			{
				this.logger.error(tcgme.toString(),tcgme);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.mngratesets.open"));
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