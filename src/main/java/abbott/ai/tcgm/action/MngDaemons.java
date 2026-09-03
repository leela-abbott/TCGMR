package abbott.ai.tcgm.action;

import java.io.IOException;
//import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
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

public class MngDaemons extends TCGMAction
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
                MngDaemonsForm myForm = (MngDaemonsForm) form;

                UserToken ut = this.getUserToken(request);
                this.errors.clear();

                if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) )  {
//                	 process specific command
            		System.out.println(myForm.getCompactTime());
                    processCmd( myForm.getCmd(), myForm.getCompactTime() );
                    this.setupForm(request);
                    this.setForward(TCGMConstants.FORWARD_SUCCESS);
                }
                else {
                    this.setupForm(request);
                    this.setForward(TCGMConstants.FORWARD_SUCCESS);
                }
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

    private void processCmd(String cmd, String compactTime) throws TCGMException {
        DaemonMngr dm = DaemonMngr.getInstance();

        if ( cmd.equals("START_SCHEDULER") ){
			dm.startProcessScheduler();
			dm.startProcessSchedulerMonitor();
        }
            
        else if ( cmd.equals("STOP_SCHEDULER") ){
			dm.stopProcessScheduler();
			dm.stopProcessSchedulerMonitor();
        }
        else if ( cmd.equalsIgnoreCase("APPLY_ADJUSTMENT") ){
        	logger.debug("User Selected System Compact Time: "+compactTime);
        	ProcessScheduler.setBATCH_START(Integer.parseInt(compactTime));
        }    
        else if (cmd.equals("START_MONITOR") ){
            dm.startDataFeedMonitor();
        }   
        else if (cmd.equals("STOP_MONITOR") ){
            dm.stopDataFeedMonitor();
        }    
        else{
            throw new TCGMException(this.className, "processCmd(cmd)", "Cmd: " + cmd, "Invalid Command");
        }
    }


    private void setupForm(HttpServletRequest request) throws TCGMException {
        ModelMngr mm = new ModelMngr();
        UserToken ut = this.getUserToken(request);

        MngDaemonsForm myForm = new MngDaemonsForm();
        myForm.reset();

        DaemonMngr dm = DaemonMngr.getInstance();


        myForm.setDatafeedMonitorStatus( dm.isDatafeedMonitorRunning() ? "Running" : "Stopped");
        myForm.setProcessSchedulerStatus( dm.isProcessSchedulerRunning() ? "Running" : "Stopped");
        myForm.setCurrentProcess( dm.getCurrentProcess() );
        myForm.setCurrentFeed( dm.getCurrentFeed() );

        request.setAttribute("mngDaemonsForm", myForm);
    }

	public MngDaemons() {
		super();
	}

}