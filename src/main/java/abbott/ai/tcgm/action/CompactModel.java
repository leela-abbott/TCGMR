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
public class CompactModel extends TCGMAction
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

// 10-9-03 Try to force my catch to handle any errors. by commenting out this code
//         It's OK. I got it to work as is. Problem was in the struts-config. I needed a forward to failure
//		   clause with re-direct set to false
				if ( pm.isJobPending(ut, modelIdInt) ) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.model.compact.job_pending") );
					this.saveErrors(request, errors);
					this.setForward(TCGMConstants.FORWARD_FAILURE);
				}
				else
				{
					// 10-8-03 Don't use the model manager but submit a job to batch
					//mm.compactModel(this.getUserToken(request), modelIdInt, modelForm.getModelType() );
					// Set forward as last step below
					//this.setForward(TCGMConstants.FORWARD_SUCCESS);

					// 10-08-03 Submit the Compact Model job to batch.
					String modelId = Integer.toString(modelIdInt);

					//JobInstance job = pm.createJob(modelForm.getModelSelected(), "-1", modelForm);
					modelForm.setJobName("MODEL_COMPACT");
					JobInstance job = pm.createJob(modelId, "-1", modelForm);
					//10-4-05 Add Model Name to Model Compact jobs in the JobQ Management Page
					//job.setDesc( job.getJobDef().getJobName() );
					job.setDesc( job.getJobDef().getJobName() + TCGMConstants.STR_SEP + mm.getModelName(ut, Integer.parseInt(job.getModelId()) ) );

					// 10-9-03 No parms needed
					//job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
					//job.addJobParm(JobConstants.PN_MODEL_TYPE, modelForm.getModelType() );

					pm.addJob(job, ut);

					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName()) ); // not really an error, just feedback
					this.saveErrors(request, this.errors);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);

				}
			}
		}
		catch (TCGMException ex)
		{
			logger.error(ex.toString(),ex);
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward(this.getForward());
	}

	/**
	 * Default Constructor
	 */
	public CompactModel()
	{
		super();
	}
}