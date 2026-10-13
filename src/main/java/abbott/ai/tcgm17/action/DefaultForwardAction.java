package abbott.ai.tcgm17.action;

import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;

public class DefaultForwardAction extends TCGMAction implements ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(DefaultForwardAction.class);

    private HttpServletRequest request;
    private Map<String, Object> session;
    
    private String className = getClass().getName();
    private boolean loginRequired = true;

    public DefaultForwardAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        LOG.debug("{} - Processing default navigation route mapping lifecycle", this.className);

        // Default forward configuration target matching legacy TCGMConstants.FORWARD_SUCCESS
        String forwardTarget = SUCCESS; 

        if (this.isLoginRequired()) {
            if (!this.isSessionValid(this.request)) {
                // Equivalent to your legacy TCGMConstants.G_FORWARD_LOGIN global forward token
                forwardTarget = "login"; 
            }
        }

        LOG.debug("{} - Routing action execution forward directly to: {}", this.className, forwardTarget);
        return forwardTarget;
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }
}
