package abbott.ai.tcgm.action;

import java.io.IOException;
//import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
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

public class ProcessMgmt extends TCGMAction
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
				ProcessMngr pm = new ProcessMngr();
				ProcessForm pf = new ProcessForm();
				UserToken ut = this.getUserToken(request);

				pf.setProcessList( pm.getAllPendingJobs( ut ) );
				pf.setCompletedJobList( pm.getAllFinishedJobs( ut ) );

				JobInstance p = pm.getCurrentJob( ut );
				if (p != null){
					String curProcess = p.toShortString() +TCGMConstants.STR_SEP+ p.getStartTime()+TCGMConstants.STR_SEP+p.getUserSubmitted();
					pf.setCurrentProcess( curProcess );	
				}				
				else
					pf.setCurrentProcess(TCGMConstants.NONE_SELECTED);

				pf.setProcessSchedulerStatus( DaemonMngr.isProcessSchedulerRunning() ? "Running" : "Stopped" );

				request.setAttribute("processForm", pf );
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward());
		return mapping.findForward(this.forward);
	}

	public ProcessMgmt() {
		super();
	}

}