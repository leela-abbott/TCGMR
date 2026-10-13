package abbott.ai.tcgm17.action;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Correct Struts 7.x Context Packages
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.ServletResponseAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

// Log4j2 Standard Imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;

public class ViewReportAction extends TCGMAction implements ServletRequestAware, ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger(ViewReportAction.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private String reportId;

    public ViewReportAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        try {
            if (this.isSessionValid()) {
                UserToken ut = this.getUserToken();
                String compKey = (this.getReportId() != null) ? this.getReportId() : "";
                ReportViewer reportViewer = null;

                if (!compKey.isEmpty()) { 
                    if (compKey.contains("-")) {
                        String[] tokens = compKey.split("-");
                        String jobId = tokens[0];
                        String reportIdKey = tokens[1];

                        ReportInstance instance = new ReportMngr().getReportInstanceById(reportIdKey, jobId, ut);
                        reportViewer = new ReportViewer(instance, ut);
                        this.session.put("ReportViewer", reportViewer);
                    }
                } else {
                    reportViewer = (ReportViewer) this.session.get("ReportViewer");
                }

                if (reportViewer != null) {
                    reportViewer.renderReport(this.request, this.response, this.request.getServletContext());
                }

                return NONE;
            } else {
                return "failure"; 
            }
        } catch (TCGMException ex) {
            LOG.error("Exception encountered while rendering view report", ex);
            this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            return TCGMConstants.G_FORWARD_EXCEPTION;
        }
    }
    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void withServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getReportId() {
        return reportId;
    }

    @StrutsParameter
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
