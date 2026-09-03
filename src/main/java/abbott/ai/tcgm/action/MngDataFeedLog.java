package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.process.*;

import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MngDataFeedLog extends TCGMAction
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
            if ( this.isSessionValid( request ) )
            {
                MngDataFeedLogForm myForm = null;
                myForm = (MngDataFeedLogForm) form;
                UserToken ut = this.getUserToken(request);
                this.errors.clear();
                processCmd(myForm, request);
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

    private void processCmd(MngDataFeedLogForm myForm, HttpServletRequest request) throws TCGMException {
        UserToken ut = this.getUserToken(request);
        String cmd = ( myForm == null? "" : myForm.getCmd() ); // should correspond to a direct command

        if ( cmd.equalsIgnoreCase("DELETE_ALL") ) {
          new DataFeedMngr().clearDataFeedLog();
        }

        request.setAttribute("mngDataFeedLogForm", new MngDataFeedLogForm()  );
        this.setForward(TCGMConstants.FORWARD_SUCCESS);
    }

//
//    public void setupForm(HttpServletRequest request) throws TCGMException {
//        DataTransfersForm myForm = new DataTransfersForm();
//        myForm.reset();
//        request.getSession(false).setAttribute("dataTransfersForm", myForm );
//    }

    public MngDataFeedLog()
    {
        super();
    }
}