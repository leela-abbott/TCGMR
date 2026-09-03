package abbott.ai.tcgm.action.form;

import java.util.Vector;

import org.apache.struts.upload.FormFile;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DataTransfersForm extends TCGMProductionForm {

	private String selFactorReportOption;
	private String selNetCostOption;
	private String selProductNetReportOption;
	private String pricingFilename;
	private String treeFilename;
	private String savedUnitsSelected;
	private String currUnitsSelected;
	private java.util.Vector unitSets;
	private java.util.Vector savedSets = new Vector();
	private String sendTreeToMVSPeriod;
	private FormFile theFile;

	public DataTransfersForm() {
	}
	public String getSelFactorReportOption() {
		return selFactorReportOption;
	}
	public void setSelFactorReportOption(String selFactorReportOption) {
		this.selFactorReportOption = selFactorReportOption;
	}
	public void setSelProductNetReportOption(String selProductNetReportOption) {
		this.selProductNetReportOption = selProductNetReportOption;
	}
	public String getSelProductNetReportOption() {
		return selProductNetReportOption;
	}
	public void setPricingFilename(String pricingFilename) {
		this.pricingFilename = pricingFilename;
	}
	public String getPricingFilename() {
		return pricingFilename;
	}

	public void setTreeFilename(String treeFilename) {
		this.treeFilename = treeFilename;
	}
	public String getTreeFilename() {
		return treeFilename;
	}

	public void setSavedUnitsSelected(String savedUnitsSelected) {
		this.savedUnitsSelected = savedUnitsSelected;
	}
	public String getSavedUnitsSelected() {
		return savedUnitsSelected;
	}
	public void setCurrUnitsSelected(String currUnitsSelected) {
		this.currUnitsSelected = currUnitsSelected;
	}
	public String getCurrUnitsSelected() {
		return currUnitsSelected;
	}
	public void setUnitSets(java.util.Vector unitSets) {
		this.unitSets = unitSets;
	}
	public java.util.Vector getUnitSets() {
		return unitSets;
	}
	/**
	 * @return String
	 */
	public String getSelNetCostOption() {
		return selNetCostOption;
	}

	/**
	 * @param string selNetCostOption
	 */
	public void setSelNetCostOption(String selNetCostOption) {
		this.selNetCostOption = selNetCostOption;
	}

	/**
	 * @return 
	 */
	public java.util.Vector getSavedSets() {

		return savedSets;
	}

	/**
	 * @param vector
	 */
	public void setSavedSets(java.util.Vector vector) {
		this.savedSets = vector;
	}
	
	public void setSendTreeToMVSPeriod(String sendTreeToMVSPeriod) {
			this.sendTreeToMVSPeriod = sendTreeToMVSPeriod;
		}
	public String getSendTreeToMVSPeriod() {
			return sendTreeToMVSPeriod;
		}

	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
}