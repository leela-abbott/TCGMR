package abbott.ai.tcgm.entities;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class FactorModel extends TCGMModel implements java.io.Serializable
{
	public static final String PN_BEG_FACTOR_PERIOD = "PERIOD";
	public static final String PN_PLAN_UNITS = "PLAN_UNITS";
	public static final String PN_ACT_UNITS = "ACTUAL_UNITS";
	
	public static final String PN_RATESET_COST = "RATE_1";
	public static final String PN_RATESET_FACTOR_ACTUAL = "RATE_2";
	public static final String PN_RATESET_REVISION = "RATE_3";

	// 12-21-05 Replace EXPORT_R_SYSTEM with 4 distinct R-system parms
	public static final String PN_EXPORT_RBB_SYSTEM = "EXP_RBB_SYSTEM";
	public static final String PN_EXPORT_RBL_SYSTEM = "EXP_RBL_SYSTEM";
	public static final String PN_EXPORT_RTC_SYSTEM = "EXP_RTC_SYSTEM";
	public static final String PN_EXPORT_RGM_SYSTEM = "EXP_RGM_SYSTEM";
	public static final String PN_EXPORT_CCS_SYSTEM = "EXP_CCS_SYSTEM";
	public static final String PN_EXPORT_CCS_TYPE = "EXP_CCS_TYPE";
	
	public static final String PN_MODEL_CYCLE = "CYCLE";
	public static final String PN_MODEL_YEAR = "YEAR";
	public static final String PN_SAVED_FACTOR_DATE = "SVD_FCTR_DT";
	public static final String PN_CURR_UNITS_NAME = "CURRNT_UNITS_NAME";
	//A.Winter
	public static final String PN_MEMO = "MEMO";


	private String begFactorPeriod="Unspecified";
	private String planUnitsId="Unspecified";
	private String actualUnitsId="Unspecified";
	private String rateSetFactorActualId="Unspecified";
	private String rateSetCostId="Unspecified";
	private String rateSetRevisionId="Unspecified";
	
	//private String exportRSystem="Unspecified";
	private String exportRbbSystem="Unspecified";
	private String exportRblSystem="Unspecified";
	private String exportRtcSystem="Unspecified";
	private String exportRgmSystem="Unspecified";
	private String exportCcsSystem="Unspecified";
	private String exportCcsType="Unspecified";
	
	
	private String modelYear="Unspecified";
	private Cycle modelCycle = Cycle.UNSPECIFIED;
	private String savedFactorDate="";
	private String currUnitsName="";

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public FactorModel()
	{
		this.setType(Type.FACTOR);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param begFactorPeriod
	 */
	public void setBegFactorPeriod(String begFactorPeriod)
	{
		this.begFactorPeriod = begFactorPeriod;
	}
	/**
	 *
	 * @return
	 */
	public String getBegFactorPeriod()
	{
		if(this.begFactorPeriod == null)
		{
			this.begFactorPeriod = "";
		}
		return this.begFactorPeriod.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getPlanUnitsId()
	{
		if(this.planUnitsId == null)
		{
			this.planUnitsId = "";
		}
		return this.planUnitsId.trim();
	}
	/**
	 *
	 * @param planUnitsId
	 */
	public void setPlanUnitsId(String planUnitsId)
	{
		this.planUnitsId = planUnitsId;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param actualUnitsId
	 */
	public void setActualUnitsId(String actualUnitsId)
	{
		this.actualUnitsId = actualUnitsId;
	}
	/**
	 *
	 * @return
	 */
	public String getActualUnitsId()
	{
		if(this.actualUnitsId == null)
		{
			this.actualUnitsId = "";
		}
		return this.actualUnitsId.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rateSetId
	 */
	public void setRateSetFactorActualId(String rateSetFactorActualId)
	{
		this.rateSetFactorActualId = rateSetFactorActualId;
	}
	/**
	 *
	 * @return
	 */
	public String getRateSetFactorActualId()
	{
		return rateSetFactorActualId;		
	}
	/***************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());

		sb.append("\nActual Units Id: ");
		sb.append(this.getActualUnitsId());
		sb.append("\nPlan Units Id: ");
		sb.append(this.getPlanUnitsId());

		return sb.toString();
	}
	public void setRateSetCostId(String rateSetCostId) {
		this.rateSetCostId = rateSetCostId;
	}
	public String getRateSetCostId() {
		return rateSetCostId;
	}
	public void setRateSetRevisionId(String rateSetRevisionId) {
		this.rateSetRevisionId = rateSetRevisionId;
	}
	public String getRateSetRevisionId() {
		return rateSetRevisionId;
	}
	
	
	public void setExportRbbSystem(String exportRbbSystem) {
		this.exportRbbSystem = exportRbbSystem;
	}
	public String getExportRbbSystem() {
		return exportRbbSystem;
	}
	public void setExportRblSystem(String exportRblSystem) {
		this.exportRblSystem = exportRblSystem;
	}
	public String getExportRblSystem() {
		return exportRblSystem;
	}	
	public void setExportRtcSystem(String exportRtcSystem) {
		this.exportRtcSystem = exportRtcSystem;
	}
	public String getExportRtcSystem() {
		return exportRtcSystem;
	}
	public void setExportRgmSystem(String exportRgmSystem) {
		this.exportRgmSystem = exportRgmSystem;
	}
	public String getExportRgmSystem() {
		return exportRgmSystem;
	}
	
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelCycle(Cycle modelCycle) {
		this.modelCycle = modelCycle;
	}
	public Cycle getModelCycle() {
		return modelCycle;
	}
	public void setSavedFactorDate(String savedFactorDate) {
		this.savedFactorDate = savedFactorDate;
	}
	public String getSavedFactorDate() {
		if ( (this.savedFactorDate != null) && (this.savedFactorDate != "") )
			return savedFactorDate;
		else
			return " ";
	}
	public void setCurrUnitsName(String currUnitsName) {
		this.currUnitsName = currUnitsName;
	}
	public String getCurrUnitsName() {
		return currUnitsName;
	}
	/* Helper methods to provide cycle name and check for noninitialized cycle parameters */
	public String getModelCycleLongName() {
		if ( this.modelCycle != null )
			return modelCycle.getLongName();
		else
			return "Unspecified";
	}
	public String getModelCycleShortName() {
		if ( this.modelCycle != null )
			return modelCycle.getName();
		else
			return "Unspecified";
	}
	public String getLegacyModelName() {
		return getModelCycleShortName() + getModelYear().substring(2,4);
	}
	/**
	 * @return
	 */
	public String getExportCcsSystem() {
		return exportCcsSystem;
	}

	/**
	 * @param string
	 */
	public void setExportCcsSystem(String string) {
		exportCcsSystem = string;
	}

	/**
	 * @return
	 */
	public String getExportCcsType() {
		return exportCcsType;
	}

	/**
	 * @param string
	 */
	public void setExportCcsType(String string) {
		exportCcsType = string;
	}

}