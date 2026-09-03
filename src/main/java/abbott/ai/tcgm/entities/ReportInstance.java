package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.process.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReportInstance {

    private ReportDefinition reportDefinition;
    private JobInstance jobInstance;
    private String datasetId;
	private int rowCount;
	private String reportContentId;

    public ReportInstance() {
    }
    public ReportDefinition getReportDefinition() {
        return reportDefinition;
    }
    public void setReportDefinition(ReportDefinition reportDefinition) {
        this.reportDefinition = reportDefinition;
    }
    public JobInstance getJobInstance() {
        return jobInstance;
    }
    public void setJobInstance(JobInstance jobInstance) {
        this.jobInstance = jobInstance;
    }
    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }
    public String getDatasetId() {
        return datasetId;
    }
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getRowCount() {
		return rowCount;
	}    
    public String getReportInstanceId() {
        return jobInstance.getJobQueId() + "-" + reportDefinition.getId();
    }

    public String getReportInstanceName() {
        return jobInstance.getDesc() + " - " + reportDefinition.getDesc() + " for " + jobInstance.getModel();
    }
	public String getReportContentId() {
		return reportContentId;
	}

	public void setReportContentId(String reportContentId) {
		this.reportContentId = reportContentId;
	}

}