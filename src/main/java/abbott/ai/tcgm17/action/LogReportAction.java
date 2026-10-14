package abbott.ai.tcgm17.action;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Application and Error Logs Action aligning with Unified Template Flow</p>
 * <p>Company: Abbott Laboratories</p>
 * @version 3.1
 */
public class LogReportAction extends TCGMAction {

    private static final long serialVersionUID = 1L;

    private String selProcessId = "";
    private String selJodDesc = "";
    private String selJodStatus = "";
    
    private Vector logList = new Vector();
    private Vector errorList = new Vector();

    public LogReportAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken ut = this.getUserToken();

            String procId = this.getSelProcessId();
            if (procId == null || procId.trim().isEmpty()) {
                procId = (String) request.getAttribute("selProcessId");
                this.setSelProcessId(procId);
            }

            if (this.getSelJodDesc() == null || this.getSelJodDesc().trim().isEmpty()) {
                this.setSelJodDesc((String) request.getAttribute("selJodDesc"));
            }
            
            if (this.getSelJodStatus() == null || this.getSelJodStatus().trim().isEmpty()) {
                this.setSelJodStatus((String) request.getAttribute("selJodStatus"));
            }

            try {
                ProcessMngr processMngr = new ProcessMngr();
                
                // Fetch and cast logs directly into dedicated Vectors matching the working flow template
                List<?> rawLogs = processMngr.getJobLogs(ut, procId);
                this.logList = (rawLogs != null) ? new Vector(rawLogs) : new Vector();
                
                List<?> rawErrors = processMngr.getErrorLogs(ut, procId);
                this.errorList = (rawErrors != null) ? new Vector(rawErrors) : new Vector();

                return TCGMConstants.FORWARD_SUCCESS;

            } catch (TCGMException ex) {
                this.logger.error(ex.toString(), ex);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        }
        return TCGMConstants.G_FORWARD_SELECT_MODEL;
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

    @StrutsParameter(depth = 3)
    public Vector getLogList() {
        if (this.logList == null) {
            this.logList = new Vector();
        }
        return this.logList;
    }

    @StrutsParameter(depth = 3)
    public void setLogList(Vector logList) {
        this.logList = logList;
    }

    @StrutsParameter(depth = 3)
    public Vector getErrorList() {
        if (this.errorList == null) {
            this.errorList = new Vector();
        }
        return this.errorList;
    }

    @StrutsParameter(depth = 3)
    public void setErrorList(Vector errorList) {
        this.errorList = errorList;
    }

    public int getLogListSize() {
        return this.getLogList().size();
    }

    public int getErrorListSize() {
        return this.getErrorList().size();
    }
}
