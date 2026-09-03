package abbott.ai.tcgm.action;

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
import abbott.ai.tcgm.data.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Gain Joseph
 * @version 1.0
 */
public class LogReportAction extends TCGMAction {
	private static Logger myLogger = Logger.getLogger( "LogReportAction" );
	/**
	 * Default Constructor
	 */
	public LogReportAction() {
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
	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
	    myLogger.debug("Executing perform() method in LogReportAction.");

		String methodName = "perform"; 

		HttpSession session = request.getSession();
		//get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if (form == null) {
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.asr.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		} else if (this.isSessionValid(request)) {
			
				UserToken userToken = this.getUserToken(request);

				ProcessForm procForm = (ProcessForm) form;
				
				String procId= request.getParameter("selProcessId");
				
				  if(procId==null){
					procId=(String) request.getAttribute("selProcessId");
				  }
//				String procId = procForm.getSelProcessId(); 

				boolean duplicate = true;
				UserToken ut = this.getUserToken(request);

				try { 
					 
					procForm.setLogList(new ProcessMngr().getJobLogs(ut, procId));
					procForm.setErrorList(new ProcessMngr().getErrorLogs(ut, procId));
					request.getSession().setAttribute("processForm", procForm);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				} catch (TCGMException ex) {
					this.logger.error(ex.toString(), ex);
					request.setAttribute(
						TCGMConstants.SESSION_NAME_EXCEPTION,
						ex);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			
		} 

		//if errors exist then save them into the request
		if (!this.errors.empty()) {
			this.saveErrors(request, errors);
		}

		//forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		this.setForward(TCGMConstants.FORWARD_SUCCESS);
		return mapping.findForward(this.getForward());
	}
}