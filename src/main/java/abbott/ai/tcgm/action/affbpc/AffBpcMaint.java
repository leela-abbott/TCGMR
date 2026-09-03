package abbott.ai.tcgm.action.affbpc;

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
 * @author David Fields
 * @version 1.0
 */
public class AffBpcMaint extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public AffBpcMaint()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.affBpc.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);
			AffBpcForm affBpcForm = (AffBpcForm)form;//cast the form that was passed in to the correct type for this action

			affBpcForm.processCmd(mapping,request);

			AffBpcMngr affBpcMngr = new AffBpcMngr(); //create the helper class that will handle the processing
			ModelMngr modelMngr = new ModelMngr();

			try
			{
				affBpcForm.getSearchObject().setModelId(DBConst.DEF_MODEL_ID);
				affBpcForm.getSearchObject().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
				// Ask Dave if I need to check for a empty UserName as is done in other transaction forms
				/*				 
				 if(asrTranForm.getSearchObject().getAsr().getCreateLog().getUserName().equals(""))
				{
					asrTranForm.getSearchObject().getAsr().getCreateLog().setUserName(userToken.getUserid());
				}
				*/
				affBpcForm.getPagingFilter().setTotalRecordsInSet(affBpcMngr.getCount(this.getUserToken(request),affBpcForm.getSearchObject()));
				affBpcForm.setAffBpcList(affBpcMngr.getAffBpc(this.getUserToken(request),affBpcForm.getSearchObject(),affBpcForm.getPagingFilter(),affBpcForm.getSortObject()));
				// Added by Udaya B Aravapalli on 01/03/2006. -- Start
				/***************************************************************************
				 * a. Get the distinct Supp Aff Id's and store in the Action Form
				 * b. If the user filters by specifying the Supp Aff , make sure the 
				 *    drop down reflects the same selection.
				 * c. If the user is coming for the first time, then show all the Supp Aff's
				 *    else capture the user selection and show only the records for that
				 *    Supp Aff and the drop down value should indicate the user selection.
				 *****************************************************************************/
				affBpcForm.setSupAffList(affBpcMngr.getSupAff(this.getUserToken(request)));

//				if (!(affBpcForm.getSearchObject().getSupAff() == null))
//				{
//					affBpcForm.setSupAffSelected(affBpcForm.getSearchObject().getSupAff());
//				}
				
				if (affBpcForm.getSupAffSelected() == null)
				{
					affBpcForm.setSupAffSelected(TCGMConstants.NONE);
				}
				// Added by Udaya B Aravapalli on 01/03/2006. -- End
				//Sridevi.K 7/1/05: Code added for only displaying the open models.
				FactorModel fm = new FactorModel();
				fm.setStatus(TCGMModel.Status.OPEN);
				affBpcForm.setModels(modelMngr.getModels(userToken, fm));
				//affBpcForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
				//Sridevi.K 7/1/05: End of code for displaying only open models.				
				
				affBpcForm.setModelSelected(TCGMConstants.NONE);
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
			catch(TCGMException tcgme)
// 8-8-03 bd; Cloned AsrMaint action to follow error processing
//			{
//				this.logger.error(tcgme.toString(),tcgme);
//				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.affBpc.maintenance"));
//				this.forward = TCGMConstants.FORWARD_ERROR;
//			}
			{
				this.logger.error(tcgme.toString(),tcgme);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
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