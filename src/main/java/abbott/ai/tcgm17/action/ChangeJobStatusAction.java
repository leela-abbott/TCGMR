package abbott.ai.tcgm17.action;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;

// Correct Struts 7.x Context Packages
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

// Log4j2 Standard Imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.process.JobInstance;

/**
 * Migrated to Struts 2 (v7.3.0), JDK 17, and Log4j2.
 * Merges legacy ChangeJobStatus Action and necessary ProcessForm state tracking parameters.
 */
public class ChangeJobStatusAction extends TCGMAction implements ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(ChangeJobStatusAction.class);

    private HttpServletRequest request;
    private Map<String, Object> session;

    private String className = getClass().getName();

    // =========================================================================
    // INCOMING REQUEST PROPERTIES (Annotated for Struts 7.3 Parameter Security)
    // =========================================================================
    private String selProcessId;
    private String cmd;

    public ChangeJobStatusAction() {
        super();
    }

    // =========================================================================
    // ACTION EXECUTION (Replaces legacy perform method)
    // =========================================================================
    @Override
    public String execute() throws Exception {
        try {
            if (this.isSessionValid(this.request)) {
                this.clearActionErrors();
                this.clearMessages();

                String procId = this.getSelProcessId();
                String sCode = this.getCmd();
                String strStatus = "";

                ProcessMngr pm = new ProcessMngr();
                UserToken ut = this.getUserToken(this.request);

                // Derived the Enum type safety mapping using your internal processing constants engine
                JobInstance.JobStatus status = JobInstance.JobStatus.getJobByCode(sCode);
                
                if (sCode != null && sCode.equalsIgnoreCase("PRINT_REPORTS")) {
                    strStatus = pm.printReports(ut, procId);
                    
                    if (strStatus != null && strStatus.equalsIgnoreCase("Completed")) {
                        this.addActionMessage(getText("success.reports.print", "Reports printed successfully."));
                    } else if (strStatus != null && strStatus.equalsIgnoreCase("CHK_JOB_STS")) {
                        this.addActionMessage(getText("check.reports.print", "Please check the report job status."));
                    } else {
                        this.addActionError(getText("failure.reports.print", "Failed to print reports."));
                    }
                } else {
                    // Update state variables for standard Batch or Immediate codes
                    pm.updateJobStatus(ut, procId, status);
                }

                return SUCCESS; // Maps to your TCGMConstants.FORWARD_SUCCESS equivalent
            }
            return "login";
        } catch (TCGMException ex) {
            LOG.error("Exception caught inside change job status processing execution flow", ex);
            this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            return TCGMConstants.G_FORWARD_EXCEPTION;
        } finally {
            LOG.debug("{} - Forward to {}", this.className, SUCCESS);
        }
    }

    // =========================================================================
    // INTERCEPTOR AWARE INJECTIONS
    // =========================================================================
    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    // =========================================================================
    // PROPERTY GETTERS & SETTERS (Securely Annotated)
    // =========================================================================
    public String getSelProcessId() {
        return selProcessId;
    }

    @StrutsParameter
    public void setSelProcessId(String selProcessId) {
        this.selProcessId = selProcessId;
    }

    public String getCmd() {
        return cmd;
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
