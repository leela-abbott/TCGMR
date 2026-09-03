package abbott.ai.tcgm.action.form;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MiscReportsForm extends TCGMProductionForm {

	private String interCoTransferPeriod;
	private String selComparisonModel;
	private String comparisonPercent;
	private String selRoutingStartPeriod;
	private String selRoutingStartYear;
	private String selRoutingEndPeriod;
	private String selRoutingEndYear;
	private java.util.Vector factorModels;
	private java.util.Vector unitSets;
	private String endRouteUnitsSelected;
	private String begRouteUnitsSelected;
	private String treeFilename;
	private String actualUnitsSelected;
	
	public MiscReportsForm() {
	}
	public void setInterCoTransferPeriod(String interCoTransferPeriod) {
		this.interCoTransferPeriod = interCoTransferPeriod;
	}
	public String getInterCoTransferPeriod() {
		return interCoTransferPeriod;
	}
	public void setSelComparisonModel(String selComparisonModel) {
		this.selComparisonModel = selComparisonModel;
	}
	public String getSelComparisonModel() {
		return selComparisonModel;
	}
	
	public void setSelRoutingStartPeriod(String selRoutingStartPeriod) {
		this.selRoutingStartPeriod = selRoutingStartPeriod;
	}
	public String getSelRoutingStartPeriod() {
		return selRoutingStartPeriod;
	}
	public void setSelRoutingStartYear(String selRoutingStartYear) {
		this.selRoutingStartYear = selRoutingStartYear;
	}
	public String getSelRoutingStartYear() {
		return selRoutingStartYear;
	}
	public void setSelRoutingEndPeriod(String selRoutingEndPeriod) {
		this.selRoutingEndPeriod = selRoutingEndPeriod;
	}
	public String getSelRoutingEndPeriod() {
		return selRoutingEndPeriod;
	}
	public void setSelRoutingEndYear(String selRoutingEndYear) {
		this.selRoutingEndYear = selRoutingEndYear;
	}
	public String getSelRoutingEndYear() {
		return selRoutingEndYear;
	}
	public void setFactorModels(java.util.Vector factorModels) {
		this.factorModels = factorModels;
	}
	public java.util.Vector getFactorModels() {
		return factorModels;
	}

	public void setUnitSets(java.util.Vector unitSets) {
		this.unitSets = unitSets;
	}
	public java.util.Vector getUnitSets() {
		return unitSets;
	}
	public void setBegRouteUnitsSelected(String begRouteUnitsSelected) {
		this.begRouteUnitsSelected = begRouteUnitsSelected;
	}
	public String getBegRouteUnitsSelected() {
		return begRouteUnitsSelected;
	}
	public void setEndRouteUnitsSelected(String endRouteUnitsSelected) {
		this.endRouteUnitsSelected = endRouteUnitsSelected;
	}
	public String getEndRouteUnitsSelected() {
		return endRouteUnitsSelected;
	}

	public void setTreeFilename(String treeFilename) {
		this.treeFilename = treeFilename;
	}
	public String getTreeFilename() {
		return treeFilename;
	}

	
	public void setActualUnitsSelected(String actualUnitsSelected) {
		this.actualUnitsSelected = actualUnitsSelected;
	}
	public String getActualUnitsSelected() {
		return actualUnitsSelected;
	}
	
}