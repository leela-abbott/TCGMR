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


public class LogReportAction extends TCGMAction implements ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    private static final Logger myLogger = LogManager.getLogger(LogReportAction.class);

    private HttpServletRequest request;
    private Map<String, Object> session;

    private String className = getClass().getName();

    private String selProcessId;
    private String selJodDesc;
    private String selJodStatus;
    
    private List<?> logList = new ArrayList<>();
    private List<?> errorList = new ArrayList<>();

    public LogReportAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        myLogger.debug("Executing execute() method in LogReportAction.");

        this.clearActionErrors();

        if (this.isSessionValid(this.request)) {
            UserToken ut = this.getUserToken(this.request);

            String procId = this.getSelProcessId();

            if (procId == null) {
                procId = (String) this.request.getAttribute("selProcessId");
                this.setSelProcessId(procId);
            }

            try {
                ProcessMngr processMngr = new ProcessMngr();
                this.setLogList(processMngr.getJobLogs(ut, procId));
                this.setErrorList(processMngr.getErrorLogs(ut, procId));
                this.session.put("processForm", this);

                this.logger.debug("{} Forward: {}", this.className, SUCCESS);
                return SUCCESS;

            } catch (TCGMException ex) {
                this.logger.error(ex.toString(), ex);
                this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        } else {
            String formMissingMsg = getText("error.asr.form.missing", "Required selection configuration form model data is missing.");
            this.addActionError(formMissingMsg);
            return "selectModel"; 
        }
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getSelProcessId() {
        return selProcessId;
    }

    @StrutsParameter
    public void setSelProcessId(String selProcessId) {
        this.selProcessId = selProcessId;
    }

    public String getSelJodDesc() {
        return selJodDesc;
    }

    @StrutsParameter
    public void setSelJodDesc(String selJodDesc) {
        this.selJodDesc = selJodDesc;
    }

    public String getSelJodStatus() {
        return selJodStatus;
    }

    @StrutsParameter
    public void setSelJodStatus(String selJodStatus) {
        this.selJodStatus = selJodStatus;
    }

    public List<?> getLogList() {
        return logList;
    }

    public void setLogList(List<?> logList) {
        this.logList = logList;
    }

    public List<?> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<?> errorList) {
        this.errorList = errorList;
    }
}
