package abbott.ai.tcgm.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ReportMngr;
import org.apache.struts.action.*;
import abbott.ai.tcgm.helpers.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class ViewReport extends TCGMAction
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
            if ( this.isSessionValid( request) )
            {
                UserToken ut = this.getUserToken(request);
                String compKey = request.getParameter("reportId") != null ? request.getParameter("reportId") : "";
                ReportViewer reportViewer = null;

                if (!compKey.equals("")) { //create new report viewer from id

                    // Break up selected report into two part key.
                    StringTokenizer st = new StringTokenizer(compKey, "-");
                    String jobId = st.nextToken();
                    String reportId = st.nextToken();

                    ReportInstance instance = new ReportMngr().getReportInstanceById(reportId, jobId, ut );
                    reportViewer = new ReportViewer(instance, ut);
                    request.getSession().setAttribute("ReportViewer", reportViewer);
                }
                else
                    reportViewer = (ReportViewer) request.getSession().getAttribute("ReportViewer");

                // render the report view
                reportViewer.renderReport(request, response, this.getServlet().getServletContext() );
            }
            else {
                return mapping.findForward(TCGMConstants.FORWARD_FAILURE);
                // this should only be in case of expired sessions.
                // We don't want to display a login page in the mini report window however,
                // so custom error page needed.
            }
        }
        catch (TCGMException ex)
        {
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            return mapping.findForward(TCGMConstants.G_FORWARD_EXCEPTION);
        }
        // this should not forward except in cases of exception.
        return null;
    }

    public ViewReport() {
        super();
    }

}