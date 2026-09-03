package abbott.ai.tcgm.action;
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
import abbott.ai.tcgm.process.JobInstance;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class DeleteModel extends TCGMAction
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
			if ( this.isSessionValid(request) )
			{
				errors.clear();

				TCGMMngModelsForm modelForm = null;
				modelForm = (TCGMMngModelsForm) form;
				int modelIdInt = Integer.parseInt( modelForm.getModelSelected() );

				ModelMngr mm = new ModelMngr();
				ProcessMngr pm = new ProcessMngr();
				UserToken ut = this.getUserToken(request);

				if ( pm.isJobPending(ut, modelIdInt) )
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.model.delete.job_pending") );
					this.saveErrors(request, errors);
					this.setForward(TCGMConstants.FORWARD_FAILURE);
				}
				else
				{
					String modelId = Integer.toString(modelIdInt);
					TCGMModel model = mm.getModelFromId(ut,modelIdInt,TCGMModel.Type.FACTOR);
					model.setStatus(TCGMModel.Status.DELETED);
					int intCurModel = this.getState(request).getCurrentModelId();
					if(modelIdInt==intCurModel){
						this.getState(request).setCurrentModelName(TCGMConstants.NONE_SELECTED);
					}
					mm.updateModelStatus(ut,model);
					this.saveErrors(request, this.errors);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		logger.debug("DeleteModel - Forward to " + this.getForward() );
		return mapping.findForward(this.getForward());
	}

	/**
	 * Default Constructor
	 */
	public DeleteModel()
	{
		super();
	}
}