package abbott.ai.tcgm.action;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.ProcessForm;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.exception.TCGMException;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;


public class GenerateReport extends TCGMAction
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
			
				String sCode = procForm.getCmd();

				ProcessMngr pm = new ProcessMngr();
				UserToken ut = this.getUserToken(request);
					
				if(sCode.equalsIgnoreCase("PRINT_REPORTS")){
					strStatus = pm.printReports(ut, procId);
					if(strStatus.equalsIgnoreCase("Complete")){
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.reports.print"));
					}else if (strStatus.equalsIgnoreCase("CHK_JOB_STS")){
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("check.reports.print"));						
					}else{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("failure.reports.print"));
					}
					
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
	public GenerateReport()
	{
		super();
	}
}