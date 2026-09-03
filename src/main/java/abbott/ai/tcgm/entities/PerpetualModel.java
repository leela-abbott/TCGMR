package abbott.ai.tcgm.entities;

import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class PerpetualModel extends TCGMModel implements Serializable
{

	public final String PN_COSTING_MODEL = "COSTING_MODEL_ID";


	public static final String PN_COSTING_UNITS = "COSTING_UNITS_ID";


//	public static final String PN_STARTING_MODEL = "STARTING_MODEL_ID";
	public static final String PN_STARTING_INV_MODEL = "ASR_B_INV_CYCLE";
//	public static final String PN_STARTING_UNITS = "STARTING_UNITS_ID";
	public static final String PN_STARTING_INV_UNITS = "UNIT_B_INV_CYCLE";

//	public static final String PN_CURRENT_YEAR_ACTUAL_MODEL = "CURR_ACT_MDL_ID";
	public static final String PN_CURRENT_YEAR_ACTUAL_MODEL = "ASR_T_YEAR_CYCLE";
//	public static final String PN_CURRENT_YEAR_ACTUAL_UNITS = "CURR_ACT_UNITS_ID";
	public static final String PN_CURRENT_YEAR_ACTUAL_UNITS = "UNIT_T_YEAR_CYCLE";
//	public static final String PN_LAST_YEAR_ACTUAL_MODEL = "LAST_ACT_MDL_ID";
	public static final String PN_LAST_YEAR_ACTUAL_MODEL = "ASR_L_YEAR_CYCLE";
//	public static final String PN_LAST_YEAR_ACTUAL_UNITS = "LAST_ACT_UNITS_ID";
	public static final String PN_LAST_YEAR_ACTUAL_UNITS = "UNIT_L_YEAR_CYCLE";
//	public static final String PN_START_YEAR = "START_YEAR";
	public static final String PN_START_D56_YEAR = "B_D56_YEAR";
//	public static final String PN_START_PERIOD = "START_PERIOD";
	public static final String PN_START_D56_PERIOD = "B_D56_PERIOD";
//	public static final String PN_END_YEAR = "END_YEAR";
	public static final String PN_END_D56_YEAR = "E_D56_YEAR";
//	public static final String PN_END_PERIOD = "END_PERIOD";
	public static final String PN_END_D56_PERIOD = "E_D56_PERIOD";
//	public static final String PN_END_INV_UNITS = "END_INV_UNITS_ID";
	public static final String PN_END_INV_UNITS = "UNIT_E_INV_CYCLE";
//	public static final String PN_END_INV_UNITS = "END_INV_UNITS_ID";
	public static final String PN_END_INV_MODEL = "ASR_E_INV_CYCLE";

	public static final String PN_END_INV_USED = "E_INV_USED";
	public static final String PN_COST_CYCLE_NAME = "COST_CYCLE";
	public static final String PN_BEG_INV_CYCLE_NAME = "B_INV_CYCLE";
	public static final String PN_END_INV_CYCLE_NAME = "E_INV_CYCLE";
	//A.Winter
	public static final String PN_MEMO = "MEMO";
	//A.Winter
	// Udaya B Aravapalli on 02/14/2006  (PERP_SUMMARY)
	public static final String PN_COST_HDR_1   = "COST_HDR_1";
	public static final String PN_COST_HDR_2   = "COST_HDR_2";
	public static final String PN_COST_HDR_3   = "COST_HDR_3";
	public static final String PN_COST_HDR_4   = "COST_HDR_4";
	public static final String PN_BEG_INV_HEADER = "B_INV_HEADER";
	public static final String PN_END_INV_HEADER = "E_INV_HEADER";

	private FactorModel startingModel = null;
	private Dataset startingInvUnits = null;

	private FactorModel costingModel = null;
	//private Dataset costingInvUnits = null; // 7-22-03 bd; There are no units associated with the costing model

	private FactorModel endingModel = null;
	private Dataset endingInvUnits = null;

	private FactorModel currentYearActualModel = null;
	private FactorModel lastYearActualModel = null;

	private Dataset currentYearActualUnits = null;
	private Dataset lastYearActualUnits = null;

	private String startYear = "";
	private String startPeriod = "";
	private String endYear = "";
	private String endPeriod = "";

	private String useEndInv = "";
	private String costCycleName = "";
	private String begInvCycleName = "";
	private String endInvCycleName = "";

	//	Udaya B Aravapalli on 02/14/2006  (PERP_SUMMARY)
	private String costHdr1 = "";
	private String costHdr2 = "";
	private String costHdr3 = "";
	private String costHdr4 = "";
	private String begInvHdr = "";
	private String endInvHdr = "";

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public PerpetualModel()
	{
		this.setType(Type.PERPETUAL);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	/**
	 *
	 * @param startingModelId
	 */
	/*****************************************************************************************/
	/**
	 *
	 * @param startingModelName
	 */
	/**
	 *
	 * @return
	 */
	/*****************************************************************************************/
	/**
	 *
	 * @param costingModelId
	 */
	/**
	 *
	 * @return
	 */
	/*****************************************************************************************/
	/**
	 *
	 * @param endingInventoryName
	 */
	/**
	 *
	 * @return
	 */
	/*****************************************************************************************/
	/**
	 *
	 * @param useEndingInventory
	 */
	/**
	 *
	 * @return
	 */
	/*****************************************************************************************/
	/**
	 *
	 * @param startYear
	 */
	public void setStartYear(String startYear)
	{
		this.startYear = startYear;
	}
	/**
	 *
	 * @return
	 */
	public String getStartYear()
	{
		if(this.startYear == null)
		{
			this.startYear = "";
		}
		return this.startYear;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param startPeriod
	 */
	public void setStartPeriod(String startPeriod)
	{
		this.startPeriod = startPeriod;
	}
	/**
	 *
	 * @return
	 */
	public String getStartPeriod()
	{
		if(this.startPeriod == null)
		{
			this.startPeriod = "";
		}
		return this.startPeriod;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param endYear
	 */
	public void setEndYear(String endYear)
	{
		this.endYear = endYear;
	}
	/**
	 *
	 * @return
	 */
	public String getEndYear()
	{
		if(this.endYear == null)
		{
			this.endYear = "";
		}
		return this.endYear;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param endPeriod
	 */
	public void setEndPeriod(String endPeriod)
	{
		this.endPeriod = endPeriod;
	}
	/**
	 *
	 * @return
	 */
	public String getEndPeriod()
	{
		if(this.endPeriod == null)
		{
			this.endPeriod = "";
		}
		return this.endPeriod;
	}
	public Dataset getStartingInvUnits() {
		return startingInvUnits;
	}
	public void setStartingInvUnits(Dataset startingInvUnits) {
		this.startingInvUnits = startingInvUnits;
	}

	public Dataset getEndingInvUnits() {
		return endingInvUnits;
	}
	public void setEndingInvUnits(Dataset endingInvUnits) {
		this.endingInvUnits = endingInvUnits;
	}

	public Dataset getLastYearActualUnits() {
		return lastYearActualUnits;
	}
	public void setLastYearActualUnits(Dataset lastYearActualUnits) {
		this.lastYearActualUnits = lastYearActualUnits;
	}
	public void setLastYearActualModel(FactorModel lastYearActualModel) {
		this.lastYearActualModel = lastYearActualModel;
	}
	public FactorModel getLastYearActualModel() {
		return lastYearActualModel;
	}
	public Dataset getCurrentYearActualUnits() {
		return currentYearActualUnits;
	}
	public void setCurrentYearActualUnits(Dataset currentYearActualUnits) {
		this.currentYearActualUnits = currentYearActualUnits;
	}

	public FactorModel getCurrentYearActualModel() {
		return currentYearActualModel;
	}
	public void setCurrentYearActualModel(FactorModel currentYearActualModel) {
		this.currentYearActualModel = currentYearActualModel;
	}

	public FactorModel getCostingModel() {
		return costingModel;
	}
	public void setCostingModel(FactorModel costingModel) {
		this.costingModel = costingModel;
	}
// 7-22-03 bd; There is no corresponding CostingInvUnits parm with the Costing Model
//	public Dataset getCostingInvUnits() {
//		return costingInvUnits;
//	}
//	public void setCostingInvUnits(Dataset costingInvUnits) {
//		this.costingInvUnits = costingInvUnits;
//	}
	public FactorModel getEndingModel() {
		return endingModel;
	}
	public void setEndingModel(FactorModel endingModel) {
		this.endingModel = endingModel;
	}
	public FactorModel getStartingModel() {
		return startingModel;
	}
	public void setStartingModel(FactorModel startingModel) {
		this.startingModel = startingModel;
	}

	public String getUseEndInv() {
		return useEndInv;
	}
	public void setUseEndInv(String useEndInv) {
		this.useEndInv = useEndInv;
	}

// 7-14-03bd; I added these; they are needed for the header creation.
//            COST_CYCLE, B_INV_CYCLE, E_INV_CYCLE
	public String getCostCycleName() {
		return costCycleName;
	}
	public void setCostCycleName(String costCycleName) {
		this.costCycleName = costCycleName;
	}

	public String getBegInvCycleName() {
		return begInvCycleName;
	}
	public void setBegInvCycleName(String begInvCycleName) {
		this.begInvCycleName = begInvCycleName;
	}

	public String getEndInvCycleName() {
		return endInvCycleName;
	}
	public void setEndInvCycleName(String endInvCycleName) {
		this.endInvCycleName = endInvCycleName;
	}
//************** 7-7-03 bd; I may not need these getter and setters; Jim may have already
// retrieved this attributes. I added these but check to see if I can get rid of them
//	public String getCostingCycleModelId() {
//		return costingCycleModelId;
//	}
//	public void setCostingCycleModelId(String costingCycleModelId) {
//		this.costingCycleModelId = costingCycleModelId;
//	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());

		sb.append("\nStarting Model: ");
		sb.append(this.getStartingModel());

		sb.append("\nCosting Model: ");
		sb.append(this.getCostingModel().toString() );

		sb.append("\nEnding Inventory: ");
		sb.append(this.getEndingInvUnits().toString() );

		sb.append("\nStart Period: ");
		sb.append(this.getStartPeriod());
		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());
		sb.append("\nStart Year: ");
		sb.append(this.getStartYear());
		sb.append("\nEnd Year: ");
		sb.append(this.getEndYear());

		return sb.toString();
	}

	/**
	 * @return String
	 */
	public String getCostHdr1() {
		return costHdr1;
	}

	/**
	 * @return String
	 */
	public String getCostHdr2() {
		return costHdr2;
	}

	/**
	 * @return String
	 */
	public String getCostHdr3() {
		return costHdr3;
	}

	/**
	 * @return String
	 */
	public String getCostHdr4() {
		return costHdr4;
	}

	/**
	 * @param String costHdr1
	 */ 
	public void setCostHdr1(String costHdr1) {
		this.costHdr1 = costHdr1;
	}

	/**
	 * @param String costHdr2
	 */
	public void setCostHdr2(String costHdr2) {
		this.costHdr2 = costHdr2;
	}

	/**
	 * @param String costHdr3
	 */
	public void setCostHdr3(String costHdr3) {
		this.costHdr3 = costHdr3;
	}

	/**
	 * @param String costHdr4
	 */
	public void setCostHdr4(String costHdr4) {
		this.costHdr4 = costHdr4;
	}

	/**
	 * @return String
	 */
	public String getBegInvHdr() {
		return begInvHdr;
	}

	/**
	 * @return String
	 */
	public String getEndInvHdr() {
		return endInvHdr;
	}

	/**
	 * @param String begInvHdr
	 */
	public void setBegInvHdr(String begInvHdr) {
		this.begInvHdr = begInvHdr;
	}

	/**
	 * @param String endInvHdr
	 */
	public void setEndInvHdr(String endInvHdr) {
		this.endInvHdr = endInvHdr;
	}

}