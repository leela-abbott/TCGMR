package abbott.ai.tcgm.entities;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReportPrintRequest {

    private String reportId;
    private String numCopies;
    private String reportDest;
    private String jobQueId;
    private String restriction;
	private String datasetId;
	private String reportContentId;

    public ReportPrintRequest() {
    }
    public String getReportId() {
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public void setNumCopies(String numCopies) {
        this.numCopies = numCopies;
    }
    public String getNumCopies() {
        return numCopies;
    }
    public void setReportDest(String reportDest) {
        this.reportDest = reportDest;
    }
    public String getReportDest() {
        return reportDest;
    }
    public void setJobQueId(String jobQueId) {
        this.jobQueId = jobQueId;
    }
    public String getJobQueId() {
        return jobQueId;
    }
    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }
    public String getRestriction() {
        return restriction;
    }
	public String getReportContentId() {
		return reportContentId;
	}

	public void setReportContentId(String reportContentId) {
		this.reportContentId = reportContentId;
	}
	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}
	public String getDatasetId() {
		return datasetId;
	}	    
}