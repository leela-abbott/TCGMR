package abbott.ai.tcgm17.action;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.AppConst; // Ensure AppConst config mappings are pulled in
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;

public class MngReportJobsAction extends TCGMAction implements ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(MngReportJobsAction.class);

    private HttpServletRequest request;
    private Map<String, Object> session;

    private String className = getClass().getName();

    private String cmd;
    private boolean chkRestrict;
    private boolean restrictionEntered;
    private String selReport;
    private String numCopies;
    private String selReportDest;
    private boolean chkImmediate;
    private String CAMUsername;
    private String CAMPassword;
    private List<ReportInstance> reportList = new ArrayList<>();

    public MngReportJobsAction() {
        super();
    }

    public String getReportsUrl() {
        return AppConst.reportsUrl;
    }

    @Override
    public String execute() throws Exception {
        try {
            if (this.isSessionValid(this.request)) {
                
                this.clearActionErrors();

                if (isCmdValid()) {
                    if (this.isChkRestrict() && !this.isRestrictionEntered()) {
                        this.session.put("restrictionForm", this);
                        return TCGMConstants.G_FORWARD_RESTRICTIONS;
                    } else {
                        processCmd();
                        this.resetForm();
                        this.setupForm();
                        return SUCCESS; 
                    }
                } else {
                    this.setupForm();
                    return SUCCESS;
                }
            }
        } catch (TCGMException ex) {
            this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            return TCGMConstants.G_FORWARD_EXCEPTION;
        }

        LOG.debug("{} - Default Forward Success", this.className);
        return SUCCESS;
    }

    private void processCmd() throws TCGMException {
        UserToken ut = this.getUserToken(this.request);
        String currentCmd = this.getCmd();

        if (currentCmd == null) {
            return;
        }

        if (currentCmd.equalsIgnoreCase("DELETE")) {
            if (this.selReport != null && this.selReport.contains("-")) {
                String[] tokens = this.selReport.split("-");
                String jobId = tokens[0];
                String reportId = tokens[1];

                new ReportMngr().deleteReportInstanceById(reportId, jobId, ut);
            }
        } else if (currentCmd.equalsIgnoreCase("PRINT")) {
            if (this.selReport != null && this.selReport.contains("-")) {
                String[] tokens = this.selReport.split("-");
                String jobId = tokens[0];
                String reportId = tokens[1];

                ReportInstance report = new ReportMngr().getReportInstanceById(reportId, jobId, ut);

                JobInstance job = new JobInstance(JobDefinition.PRINT_REPORT);
                job.setPrintOutput(true);
                job.addJobParm(JobConstants.PN_REPORT_COPIES, this.getNumCopies());
                job.addJobParm(JobConstants.PN_REPORT_DEST, this.getSelReportDest());
                job.addJobParm(JobConstants.PN_REPORT_ID, reportId);
                job.addJobParm(JobConstants.PN_JOBQUE_ID, jobId);

                job.setModelId(report.getJobInstance().getModelId());
                job.setDatasetTableId(report.getDatasetId());

                if (this.isChkImmediate()) {
                    job.setJobStatus(JobInstance.JobStatus.Immediate);
                } else {
                    job.setJobStatus(JobInstance.JobStatus.Batch);
                }

                job.setDesc("PRINT " + report.getReportInstanceName());
                new ProcessMngr().addJob(job, ut);
                
                String feedbackMessage = getText("success.job.add", Collections.singletonList(job.getJobDef().getJobName()));
                this.addActionMessage(feedbackMessage); 
            }
        }
    }

    private void setupForm() throws TCGMException {
        this.session.put("mngReportJobsForm", this);
    }

    private void resetForm() {
        this.cmd = null;
        this.chkRestrict = false;
        this.restrictionEntered = false;
        this.selReport = null;
        this.numCopies = null;
        this.selReportDest = null;
        this.chkImmediate = false;
    }

    public String getReportDetailArrayString() {
        StringBuilder sb = new StringBuilder("[");
        if (this.getReportList() != null && !this.getReportList().isEmpty()) {
            for (ReportInstance currentInstance : this.getReportList()) {
                sb.append("[");
                sb.append("'<b>Desc:</b> ").append(TCGMUtil.escapeString(currentInstance.getReportDefinition().getDesc())).append("<br>");
                sb.append("<b>Job Desc:</b> ").append(TCGMUtil.escapeString(currentInstance.getJobInstance().getDesc())).append("<br>");
                sb.append("<b>Model:</b> ").append(TCGMUtil.escapeString(currentInstance.getJobInstance().getModel())).append("<br>");
                sb.append("'],");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean isCmdValid() { return this.cmd != null && !this.cmd.isEmpty(); }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getCmd() { return cmd; }
    
    @StrutsParameter
    public void setCmd(String cmd) { this.cmd = cmd; }

    public boolean isChkRestrict() { return chkRestrict; }
    
    @StrutsParameter
    public void setChkRestrict(boolean chkRestrict) { this.chkRestrict = chkRestrict; }

    public boolean isRestrictionEntered() { return restrictionEntered; }
    
    @StrutsParameter
    public void setRestrictionEntered(boolean restrictionEntered) { this.restrictionEntered = restrictionEntered; }

    public String getSelReport() { return selReport; }
    
    @StrutsParameter
    public void setSelReport(String selReport) { this.selReport = selReport; }

    public String getNumCopies() { return numCopies; }
    
    @StrutsParameter
    public void setNumCopies(String numCopies) { this.numCopies = numCopies; }

    public String getSelReportDest() { return selReportDest; }
    
    @StrutsParameter
    public void setSelReportDest(String selReportDest) { this.selReportDest = selReportDest; }

    public boolean isChkImmediate() { return chkImmediate; }
    
    @StrutsParameter
    public void setChkImmediate(boolean chkImmediate) { this.chkImmediate = chkImmediate; }

    public List<ReportInstance> getReportList() { return reportList; }
    
    public void setReportList(List<ReportInstance> reportList) { this.reportList = reportList; }
    
    public String getCAMUsername() { 
        return CAMUsername; 
    }
    
    @StrutsParameter
    public void setCAMUsername(String CAMUsername) { 
        this.CAMUsername = CAMUsername; 
    }

    public String getCAMPassword() { 
        return CAMPassword; 
    }
    
    @StrutsParameter
    public void setCAMPassword(String CAMPassword) { 
        this.CAMPassword = CAMPassword; 
    }
}
