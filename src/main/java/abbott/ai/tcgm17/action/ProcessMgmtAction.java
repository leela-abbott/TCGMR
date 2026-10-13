package abbott.ai.tcgm17.action;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;

public class ProcessMgmtAction extends TCGMAction implements ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(ProcessMgmtAction.class);

	private HttpServletRequest request;
	private Map<String, Object> session;

	private String className = getClass().getName();

	private List<JobInstance> processList = new ArrayList<>();
	private List<JobInstance> completedJobList = new ArrayList<>();
	private String processSchedulerStatus;
	private String currentProcess;
	private String selProcessId;
	private String selJodDesc; // Typo retained from legacy property name tracking
	private String selJodStatus; // Typo retained from legacy property name tracking
	private List<String> logList = new ArrayList<>();
	private List<String> errorList = new ArrayList<>();

	public ProcessMgmtAction() {
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

				ProcessMngr pm = new ProcessMngr();
				UserToken ut = this.getUserToken(this.request);

				// Populate UI Lists using type-safe List API
				this.setProcessList(pm.getAllPendingJobs(ut));
				this.setCompletedJobList(pm.getAllFinishedJobs(ut));

				JobInstance p = pm.getCurrentJob(ut);
				if (p != null) {
					String curProcess = p.toShortString() + TCGMConstants.STR_SEP + p.getStartTime()
							+ TCGMConstants.STR_SEP + p.getUserSubmitted();
					this.setCurrentProcess(curProcess);
				} else {
					this.setCurrentProcess(TCGMConstants.NONE_SELECTED);
				}

				this.setProcessSchedulerStatus(DaemonMngr.isProcessSchedulerRunning() ? "Running" : "Stopped");

				this.request.setAttribute("processForm", this);

				return SUCCESS;
			}
			return "login";
		} catch (TCGMException ex) {
			LOG.error("Exception caught inside process management execution flow", ex);
			this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			return TCGMConstants.G_FORWARD_EXCEPTION;
		}
	}

	public int getProcessListSize() {
		return (this.processList != null) ? this.processList.size() : 0;
	}

	public int getCompletedJobListSize() {
		return (this.completedJobList != null) ? this.completedJobList.size() : 0;
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<JobInstance> getProcessList() {
		return processList;
	}

	public void setProcessList(List<JobInstance> processList) {
		this.processList = processList;
	}

	public List<JobInstance> getCompletedJobList() {
		return completedJobList;
	}

	public void setCompletedJobList(List<JobInstance> completedJobList) {
		this.completedJobList = completedJobList;
	}

	public String getProcessSchedulerStatus() {
		return processSchedulerStatus;
	}

	public void setProcessSchedulerStatus(String processSchedulerStatus) {
		this.processSchedulerStatus = processSchedulerStatus;
	}

	public String getCurrentProcess() {
		return currentProcess;
	}

	public void setCurrentProcess(String currentProcess) {
		this.currentProcess = currentProcess;
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

	public List<String> getLogList() {
		return logList;
	}

	public void setLogList(List<String> logList) {
		this.logList = logList;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
}
