package abbott.ai.tcgm.entities;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReportDefinition {

    private String id;
    private String desc;
    private String name;
    private String preCmd;
    private String displayName;
	private String useCase; // 11-2-05 Hijacked for RN Publish value
	private String searchPath; // 11-21-05 Added for RN
	private String outputFormat; // 03-13-06 Added for Report Output Format
	private String reportVersions;
	private boolean generateEmptyReport;
	
    private String[] parmKeys;
    public ReportDefinition() {
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setIdInt(int id) {
        this.id = Integer.toString(id);
    }

    public void setDesc(String desc) {
        if (desc==null)
            this.desc = "";
        else
            this.desc = desc.trim();
    }
    public String getDesc() {
        return desc.trim();
    }

    public void setName(String name) {
        if (name==null)
            this.name = "";
        else
            this.name = name.trim();
    }
    public String getName() {
        return name;
    }

    public void setPreCmd(String preCmd) {
        if (preCmd==null)
            this.preCmd = "";
        else
            this.preCmd = preCmd.trim();
    }
    public String getPreCmd() {
        return preCmd;
    }

    public void setDisplayName(String displayName) {
        if (displayName==null)
            this.displayName = "";
        else
            this.displayName = displayName.trim();
    }
    public String getDisplayName() {
        return displayName;
    }


	public void setUseCase(String useCase) {
		if (useCase==null)
			this.useCase = "";
		else
			this.useCase = useCase.trim();
	}
	public String getUseCase() {
		return useCase;
	}

	public void setSearchPath(String searchPath) {
		if (searchPath==null)
			this.searchPath = "";
		else
			this.searchPath = searchPath.trim();
	}
	public String getSearchPath() {
		return searchPath;
	}

    public void setParmKeys(String[] parmKeys) {
        this.parmKeys = parmKeys;
    }
    public String[] getParmKeys() {
        return parmKeys;
    }

	/**
	 * @return outputFormat
	 */
	public String getOutputFormat() {
		return outputFormat;
	}

	/**
	 * @param String outputFormat
	 */
	public void setOutputFormat(String outputFormat) {
		if (outputFormat==null)
			this.outputFormat = "";
		else
			this.outputFormat = outputFormat.trim();
	}

	/**
	 * @return reportVersions
	 */
	public String getReportVersions() {
		return reportVersions;
	}

	/**
	 * @param string reportVersions
	 */
	public void setReportVersions(String reportVersions) {
		this.reportVersions = reportVersions;
	}
	
	public void setIdReportVersions(int reportVersions) {
		this.reportVersions = Integer.toString(reportVersions);
	}
	/**
	 * @return generateEmptyReport boolean
	 */
	public boolean isGenerateEmptyReport() {
		return generateEmptyReport;
	}

	/**
	 * @param generateEmptyReport boolean
	 */
	public void setGenerateEmptyReport(boolean generateEmptyReport) {
		this.generateEmptyReport = generateEmptyReport;
	}

}