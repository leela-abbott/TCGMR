package abbott.ai.tcgm17.action;

import java.util.Map;
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

/**
 * Migrated to Struts 2 (v7.3.0), JDK 17, and Log4j2.
 * Processes destructive deletion operations on targeted background queue jobs.
 */
public class DeleteJobAction extends TCGMAction implements ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(DeleteJobAction.class);

    private HttpServletRequest request;
    private Map<String, Object> session;

    private String className = getClass().getName();

    // =========================================================================
    // INCOMING REQUEST PROPERTIES (Annotated for Struts 7.3 Parameter Security)
    // =========================================================================
    private String selProcessId;

    public DeleteJobAction() {
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
                
                String procId = this.getSelProcessId();
                UserToken ut = this.getUserToken(this.request);

                // Execute background deletion logic via process manager
                new ProcessMngr().deleteJob(ut, procId);
                
                return SUCCESS; // Maps to your TCGMConstants.FORWARD_SUCCESS equivalent
            }
            return "login";
        } catch (TCGMException ex) {
            LOG.error("Exception caught inside delete job execution thread flow", ex);
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
}
