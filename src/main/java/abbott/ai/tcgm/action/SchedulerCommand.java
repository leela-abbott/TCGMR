package abbott.ai.tcgm.action;

import java.io.IOException;
//import java.lang.InterruptedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
//import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.TCGMModel;
//import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;
//import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.*;
import org.apache.struts.action.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class SchedulerCommand extends TCGMAction
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
		try {
			if ( this.isSessionValid(request) )
			{
				String command = ( request.getParameter("command") == null )? "" : request.getParameter("command").toUpperCase();
				this.logger.debug("Command received by Action: " + command);

				DaemonMngr dm = DaemonMngr.getInstance();
				if (command.equals(TCGMConstants.PS_CMD_START) )
					dm.startProcessScheduler();
				else if (command.equals(TCGMConstants.PS_CMD_STOP) )
					dm.stopProcessScheduler();
				else
					throw new TCGMException("SchedulerCommand", "perform", "Invalid Process Scheduler Command");
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );

   }

	public SchedulerCommand() {
		super();
	}

}