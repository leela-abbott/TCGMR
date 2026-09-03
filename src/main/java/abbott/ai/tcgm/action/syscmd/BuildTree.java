package abbott.ai.tcgm.action.syscmd;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.action.form.*;
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
 * @author Jim Watkins
 * @version 1.0
 */

public class BuildTree extends TCGMAction
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
			if( this.isSessionValid(request) & this.isModelSelected(request) )
			{
				String modelName;
				errors.clear();
				TCGMState state = this.getState(request);
				modelName = state.getCurrentModelName();

					UserToken ut = this.getUserToken(request);
					ProcessMngr pm = new ProcessMngr();
					JobInstance p = new JobInstance(JobDefinition.BUILD_ASR_TREE);
					p.setModelIdInt( state.getCurrentModelId() );
					p.setJobStatus(JobInstance.JobStatus.Immediate);
					p.setDesc(p.getJobDef().getJobName() + "-" + modelName);
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

	public BuildTree()
	{
		super();
	}
}