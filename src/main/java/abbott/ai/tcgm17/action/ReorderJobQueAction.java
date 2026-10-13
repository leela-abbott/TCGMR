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
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;

/**
 * Migrated to Struts 2 (v7.3.0), JDK 17, and Log4j2.
 * Handles reordering operations for the active job queue.
 */
public class ReorderJobQueAction extends TCGMAction implements ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(ReorderJobQueAction.class);

    private HttpServletRequest request;
    private Map<String, Object> session;

    private String className = getClass().getName();

    // =========================================================================
    // INCOMING REQUEST PROPERTIES (Annotated for Struts 7.3 Parameter Security)
    // =========================================================================
    private String cmd;
    private String selProcessId;

    public ReorderJobQueAction() {
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

                // Extracted offset parsing matching legacy request context operations
                int offset = (this.getCmd() != null) ? Integer.parseInt(this.getCmd().trim()) : 0;
                String jobQueId = this.getSelProcessId();

                // Execute the reorder queue position manipulation loop on the backend manager
                new ProcessMngr().moveJobPosition(jobQueId, this.getUserToken(this.request), offset);
                
                return SUCCESS; // Maps to your TCGMConstants.FORWARD_SUCCESS equivalent
            }
            return "login";
        } catch (TCGMException ex) {
            LOG.error("Exception caught inside reorder job queue execution thread flow", ex);
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
    public String getCmd() { 
        return cmd; 
    }
    
    @StrutsParameter
    public void setCmd(String cmd) { 
        this.cmd = cmd; 
    }

    public String getSelProcessId() { 
        return selProcessId; 
    }
    
    @StrutsParameter
    public void setSelProcessId(String selProcessId) { 
        this.selProcessId = selProcessId; 
    }
}
