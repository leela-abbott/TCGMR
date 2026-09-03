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
import abbott.ai.tcgm.process.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class ChangeJobStatus extends TCGMAction
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
				ProcessForm procForm = (ProcessForm) form;
				String procId = procForm.getSelProcessId();
				String strStatus ="";

				// In this case cmd holds the code of the status to change to.
				String sCode = procForm.getCmd();

				ProcessMngr pm = new ProcessMngr();
				UserToken ut = this.getUserToken(request);

				JobInstance.JobStatus status = JobInstance.JobStatus.getJobByCode(sCode);
				
				if(sCode.equalsIgnoreCase("PRINT_REPORTS")){
					strStatus = pm.printReports(ut, procId);
					if(strStatus.equalsIgnoreCase("Completed")){
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.reports.print"));
					}else if (strStatus.equalsIgnoreCase("CHK_JOB_STS")){
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("check.reports.print"));						
					}else{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("failure.reports.print"));
					}
					
				}
				else{
				
				pm.updateJobStatus(this.getUserToken(request), procId, status);
				
				}

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
//		if errors exist then save them into the request
		  if(! this.errors.empty())
		  {
			  this.saveErrors(request,errors);
		  }
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward(this.getForward());
	}

	/**
	 * Default Constructor
	 */
	public ChangeJobStatus()
	{
		super();
	}
}