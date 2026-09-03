package abbott.ai.tcgm.action.form;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MiscAnalysisForm extends TCGMProductionForm {

	private java.util.Vector rateSets;
	private java.util.Vector factorModels;
	private java.util.Vector unitSets;
	
    //private String selDmUnitSet;
    //private String selDmPeriod;
    //private String selDmSalesId;
    private String dmSummaryOption;
    
    private String selScRateSet;
    private String selScSalesId;
    private String selScUnitSet;
    private String selScRatePeriod;
	private String selScFactorModel;   
	 
    private String selExUnitSet;
	private String selExPeriod; 
	//private String selExSalesId;
	private String selExSalesType;
	private java.util.HashMap salesType;
    private String selComparisonModel;
    private String kgaReportOption;
    
    private String selHedExUnitSet;
	private String selHedExPeriod;
	private String selHedExEndPeriod;
    
    private String reportNameSuf;
    private String hedgereportNameSuf;
    private java.util.HashMap salesList;
    private String[] selSalesList;
    private String autoRun;
    public MiscAnalysisForm() {
    }
    
    public java.util.Vector getUnitSets() {
        return unitSets;
    }
    public void setUnitSets(java.util.Vector unitSets) {
        this.unitSets = unitSets;
    }
//    public void setSelDmUnitSet(String selDmUnitSet) {
//        this.selDmUnitSet = selDmUnitSet;
//    }
//    public String getSelDmUnitSet() {
//        return selDmUnitSet;
//    }
//    public void setSelDmPeriod(String selDmPeriod) {
//        this.selDmPeriod = selDmPeriod;
//    }
//    public String getSelDmPeriod() {
//        return selDmPeriod;
//    }
// 
//    public void setSelDmSalesId(String selDmSalesId) {
//        this.selDmSalesId = selDmSalesId;
//    }
//    public String getSelDmSalesId() {
//        return selDmSalesId;
//    }

  public void setDmSummaryOption(String dmSummaryOption) {
    this.dmSummaryOption = dmSummaryOption;
  }
  public String getDmSummaryOption() {
    return dmSummaryOption;
  }
    public void setRateSets(java.util.Vector rateSets) {
        this.rateSets = rateSets;
    }
    public java.util.Vector getRateSets() {
        return rateSets;
    }
    public void setFactorModels(java.util.Vector factorModels) {
        this.factorModels = factorModels;
    }
    public java.util.Vector getFactorModels() {
        return factorModels;
    }
    public void setSelScRateSet(String selScRateSet) {
        this.selScRateSet = selScRateSet;
    }
    public String getSelScRateSet() {
        return selScRateSet;
    }
    public void setSelScSalesId(String selScSalesId) {
        this.selScSalesId = selScSalesId;
    }
    public String getSelScSalesId() {
        return selScSalesId;
    }
    public void setSelScUnitSet(String selScUnitSet) {
        this.selScUnitSet = selScUnitSet;
    }
    public String getSelScUnitSet() {
        return selScUnitSet;
    }
    public void setSelScRatePeriod(String selScRatePeriod) {
        this.selScRatePeriod = selScRatePeriod;
    }
    public String getSelScRatePeriod() {
        return selScRatePeriod;
    }
	public void setSelExPeriod(String selExPeriod) {
		this.selExPeriod = selExPeriod;
	}
	public String getSelExPeriod() {
		return selExPeriod;
	}      
    public void setSelExUnitSet(String selExUnitSet) {
        this.selExUnitSet = selExUnitSet;
    }
    public String getSelExUnitSet() {
        return selExUnitSet;
    }
	/*public void setSelExSalesId(String selExSalesId) {
		this.selExSalesId = selExSalesId;
	}
	public String getSelExSalesId() {
		return selExSalesId;
	}*/    
    public void setSelComparisonModel(String selComparisonModel) {
        this.selComparisonModel = selComparisonModel;
    }
    public String getSelComparisonModel() {
        return selComparisonModel;
    }
    public void setSelScFactorModel(String selScFactorModel) {
        this.selScFactorModel = selScFactorModel;
    }
    public String getSelScFactorModel() {
        return selScFactorModel;
    }
    public void setKgaReportOption(String kgaReportOption) {
        this.kgaReportOption = kgaReportOption;
    }
    public String getKgaReportOption() {
        return kgaReportOption;
    }
    
   
	/**
	 * @return
	 */
	public String getReportNameSuf() {
		return reportNameSuf;
	}

	/**
	 * @param string
	 */
	public void setReportNameSuf(String string) {
		reportNameSuf = string;
	}

	
	/**
	 * @return Returns the selExSalesType.
	 */
	public String getSelExSalesType() {
		return selExSalesType;
	}
	/**
	 * @param selExSalesType The selExSalesType to set.
	 */
	public void setSelExSalesType(String selExSalesType) {
		this.selExSalesType = selExSalesType;
	}
	/**
	 * @return Returns the salesType.
	 */
	public java.util.HashMap getSalesType() {
		return salesType;
	}
	/**
	 * @param salesType The salesType to set.
	 */
	public void setSalesType(java.util.HashMap salesType) {
		this.salesType = salesType;
	}
	/**
	 * @return Returns the selHedExPeriod.
	 */
	public String getSelHedExPeriod() {
		return selHedExPeriod;
	}
	/**
	 * @param selHedExPeriod The selHedExPeriod to set.
	 */
	public void setSelHedExPeriod(String selHedExPeriod) {
		this.selHedExPeriod = selHedExPeriod;
	}
	/**
	 * @return Returns the selHedExUnitSet.
	 */
	public String getSelHedExUnitSet() {
		return selHedExUnitSet;
	}
	/**
	 * @param selHedExUnitSet The selHedExUnitSet to set.
	 */
	public void setSelHedExUnitSet(String selHedExUnitSet) {
		this.selHedExUnitSet = selHedExUnitSet;
	}
	/**
	 * @return Returns the salesList.
	 */
	public java.util.HashMap getSalesList() {
		return salesList;
	}
	/**
	 * @param salesList The salesList to set.
	 */
	public void setSalesList(java.util.HashMap salesList) {
		this.salesList = salesList;
	}
	/**
	 * @return Returns the selSalesList.
	 */
	public String[] getSelSalesList() {
		return selSalesList;
	}
	/**
	 * @param selSalesList The selSalesList to set.
	 */
	public void setSelSalesList(String[] selSalesList) {
		this.selSalesList = selSalesList;
	}
	/**
	 * @return Returns the hedgereportNameSuf.
	 */
	public String getHedgereportNameSuf() {
		return hedgereportNameSuf;
	}
	/**
	 * @param hedgereportNameSuf The hedgereportNameSuf to set.
	 */
	public void setHedgereportNameSuf(String hedgereportNameSuf) {
		this.hedgereportNameSuf = hedgereportNameSuf;
	}
	/**
	 * @return Returns the selHedExEndPeriod.
	 */
	public String getSelHedExEndPeriod() {
		return selHedExEndPeriod;
	}
	/**
	 * @param selHedExEndPeriod The selHedExEndPeriod to set.
	 */
	public void setSelHedExEndPeriod(String selHedExEndPeriod) {
		this.selHedExEndPeriod = selHedExEndPeriod;
	}
	/**
	 * @return Returns the autoRun.
	 */
	public String getAutoRun() {
		return autoRun;
	}
	/**
	 * @param autoRun The autoRun to set.
	 */
	public void setAutoRun(String autoRun) {
		this.autoRun = autoRun;
	}
	
	private String selHedExSalesType;
	/**
	 * @return Returns the selHedExSalesType.
	 */
	public String getSelHedExSalesType() {
		return selHedExSalesType;
	}
	/**
	 * @param selHedExSalesType The selHedExSalesType to set.
	 */
	public void setSelHedExSalesType(String selHedExSalesType) {
		this.selHedExSalesType = selHedExSalesType;
	}

	private String[] selHedSalesList;
	/**
	 * @return Returns the selHedSalesList.
	 */
	public String[] getSelHedSalesList() {
		return selHedSalesList;
	}
	/**
	 * @param selHedSalesList The selHedSalesList to set.
	 */
	public void setSelHedSalesList(String[] selHedSalesList) {
		this.selHedSalesList = selHedSalesList;
	}

}
