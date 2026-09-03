package abbott.ai.tcgm.action.affbpc;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import org.apache.struts.action.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.action.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Brian Dennis
 * @version 1.0
 */
public class AffBpcUpdate extends TCGMAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		try
		{
//			if( this.isSessionValid(request) & this.isModelSelected(request) )
			if( this.isSessionValid(request) )
			{
				String modelName;
				errors.clear();
				TCGMState state = this.getState(request);
				modelName = state.getCurrentModelName();

				JobInstance p = new JobInstance(JobDefinition.AFFBPC_LOAD);
				p.setJobStatus(JobInstance.JobStatus.Batch);
				p.setDesc("Affiliate BPC Data Load");

				AffBpcForm affBpcForm = (AffBpcForm)form;

				// add job parameters
				p.addJobParm( "UPDATE_TYPE", affBpcForm.getSelUpdateType());    // Add/Chg
				p.addJobParm( "MODEL_SELECTED", affBpcForm.getModelSelected()); // ModelID
				p.addJobParm( "AMOUNT_TYPE", affBpcForm.getSelAmountType() );   // Cost/Price/*Both

				UserToken ut = this.getUserToken(request);

				// To use the state to set the modelId, the user must have selected a factor model
				//     after sign-in.
				//p.setModelIdInt( state.getCurrentModelId() );

				// 4/28/03 Get the model from the form bean
				p.setModelId(affBpcForm.getModelSelected());

				this.logger.debug("Current ModelId in AffBpcUpdate = " + state.getCurrentModelId());
				ProcessMngr pm = new ProcessMngr();
				pm.addJob(p, ut);
				forward = TCGMConstants.FORWARD_SUCCESS;
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			forward = TCGMConstants.G_FORWARD_EXCEPTION;
		}

		this.logger.debug(this.className + " - Forward to " + forward);
		this.saveErrors(request, this.errors);
		return mapping.findForward(forward) ;

	}

	public AffBpcUpdate()
	{
		super();
	}
}