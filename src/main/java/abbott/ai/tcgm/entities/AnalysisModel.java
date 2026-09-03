package abbott.ai.tcgm.entities;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class AnalysisModel extends TCGMModel implements Serializable
{
	//An analysis model contains 2 factor models and 2 units.
	public static final String PN_BASE_MODEL_VERSION = "BASE_VERSION";
	public static final String PN_BASE_MODEL_YEAR = "BASE_YEAR";
	//public static final String PN_BASE_MODEL_PERIOD = "BASE_MODEL_PERIOD";
	public static final String PN_BASE_MODEL_PERIOD = "BASE_PERIOD";
	public static final String PN_BASE_MODEL = "BASE_MODEL_ID";

	public static final String PN_ANALYSIS_MODEL_VERSION = "MOD_VERSION";
	public static final String PN_ANALYSIS_MODEL_YEAR = "MOD_YEAR";
	//public static final String PN_ANALYSIS_MODEL_PERIOD = "ANALYSIS_MODEL_PERIOD";
	public static final String PN_ANALYSIS_MODEL_PERIOD = "MOD_PERIOD";
	public static final String PN_ANALYSIS_MODEL = "ANALYSIS_MODEL_ID";

	public static final String PN_ANALYSIS_UNITS_VERSION = "UNIT_VERSION";
	public static final String PN_ANALYSIS_UNITS_YEAR = "UNIT_YEAR";
	public static final String PN_ANALYSIS_UNITS_PERIOD = "UNIT_PERIOD";
	public static final String PN_ANALYSIS_UNITS = "ANALYSIS_UNIT_ID";

	public static final String PN_VOLUME_UNITS_VERSION = "VOL_VERSION";
	public static final String PN_VOLUME_UNITS_YEAR = "VOL_YEAR";
	public static final String PN_VOLUME_UNITS_PERIOD = "VOL_PERIOD";
	public static final String PN_VOLUME_UNITS = "VOL_UNIT_ID";

	public static final String PN_CURRENT_YEAR = "CUR_YY";
	public static final String PN_ANALYSIS_SAVE = "ANALYS_SAV";
	public static final String PN_CURRENT_MONTH = "CUR_MM";
	public static final String PN_CURRENT_DAY = "CUR_DD";
	public static final String PN_ANALYSIS_EXT_VERSION = "ANL_EXT_VER";
	public static final String PN_NAME_RATE1 = "NAME_RATE1";

	// 7-24-03 bd; Jim originally put these Actual parms out here for the Analysis job but I don't see
	//              how they are used; unless they are used in another job later.
	public static final String PN_CURRENT_YEAR_ACTUAL_MODEL = "CURR_ACT_MDL_ID";
	public static final String PN_CURRENT_YEAR_ACTUAL_UNITS = "CURR_ACT_UNITS_ID";
	public static final String PN_LAST_YEAR_ACTUAL_MODEL = "LAST_ACT_MDL_ID";
	public static final String PN_LAST_YEAR_ACTUAL_UNITS = "LAST_ACT_UNITS_ID";
	//	A.Winter 6/30/05
	public static final String PN_MEMO = "MEMO";
	
	/*********************************************************************************
	 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
	 * JOB_QUE_PARAMS for ANL_SAMPEX(Sample Extended Summary button) job.
	 * This Job/ Button is on Factor Analysis Model Management screen.
	 *  START  -- COLUMN NAMES
	 */
	public static final String PN_ANAL_VERSION  = "ANALYSIS_VERSION";
	public static final String PN_ANAL1_VERSION = "ANAL_VERSION";
	public static final String PN_BASE_HDR_1    = "BASE_HDR_1";
	public static final String PN_BASE_HDR_2    = "BASE_HDR_2";
	public static final String PN_BASE_HDR_3    = "BASE_HDR_3";
	public static final String PN_BASE_HDR_4    = "BASE_HDR_4";
	public static final String PN_BASE_TITLE    = "BASE_TITLE";
	public static final String PN_NEW_HDR_1     = "NEW_HDR_1";
	public static final String PN_NEW_HDR_2     = "NEW_HDR_2";
	public static final String PN_NEW_HDR_3     = "NEW_HDR_3";
	public static final String PN_NEW_HDR_4     = "NEW_HDR_4";
	public static final String PN_NEW_TITLE     = "NEW_TITLE";	
	public static final String PN_UNIT_HDR_1    = "UNIT_HDR_1";
	public static final String PN_UNIT_HDR_2    = "UNIT_HDR_2";
	public static final String PN_UNIT_HDR_3    = "UNIT_HDR_3";
	public static final String PN_UNIT_HDR_4    = "UNIT_HDR_4";
	public static final String PN_VOL_HDR_1     = "VOL_HDR_1";
	public static final String PN_VOL_HDR_2     = "VOL_HDR_2";
	public static final String PN_VOL_HDR_3     = "VOL_HDR_3";
	public static final String PN_VOL_HDR_4     = "VOL_HDR_4";
	/*********************************************************************************
	 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
	 * JOB_QUE_PARAMS for ANL_SAMPEX(Sample Extended Summary button) job.
	 * This Job/ Button is on Factor Analysis Model Management screen.
	 *  END   -- COLUMN NAMES
	 */
	/*****************************************************************************************/
	private String baseVersion;
	private String analysisModelVersion;
	private String analysisUnitsVersion;
	private String volumeUnitsVersion;

	private String baseYear;
	private String analysisModelYear;
	private String analysisUnitsYear;
	private String volumeUnitsYear;

	private FactorModel baseModel;
	private FactorModel analysisModel;
	private Dataset analysisUnits;
	private Dataset volumeUnits;

	private String baseModelPeriod;
	private String analysisModelPeriod;
	private String analysisUnitsPeriod;
	private String volumeUnitsPeriod;

	private String currentYear;
	private String currentMonth;
	private String currentDay;

	private String analysisSave;
	private String analysisExtVersion;
	private String rate1;

	// 7-24-03 bd; Jim originally put these Actual parms out here for the Analysis job but I don't see
	//              how they are used; unless they are used in another job later.
	private FactorModel currentYearActualModel;
	private FactorModel lastYearActualModel;
	private Dataset currentYearActualUnits;
	private Dataset lastYearActualUnits;

	/*********************************************************************************
	 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
	 * JOB_QUE_PARAMS for ANL_SAMPEX(Sample Extended Summary button) job.
	 * This Job/ Button is on Factor Analysis Model Management screen.
	 *  START  -- Field declaration
	 */
	private String analysisVersion;
	private String baseHdr1;
	private String baseHdr2;
	private String baseHdr3;
	private String baseHdr4;
	private String baseTitle;		
	private String newHdr1;
	private String newHdr2;
	private String newHdr3;
	private String newHdr4;
	private String newTitle;
	private String unitHdr1;
	private String unitHdr2;
	private String unitHdr3;
	private String unitHdr4;
	private String volHdr1;
	private String volHdr2;
	private String volHdr3;
	private String volHdr4;

	/*********************************************************************************
	 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
	 * JOB_QUE_PARAMS for ANL_SAMPEX(Sample Extended Summary button) job.
	 * This Job/ Button is on Factor Analysis Model Management screen.
	 *  END  -- Field declaration
	 */

	
	/*****************************************************************************************/
	public AnalysisModel()
	{
		this.setType(Type.ANALYSIS);
	}
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("\nAnalysis Units Id: ");
		sb.append(this.getAnalysisUnits().getDatasetTableId());
		sb.append("\nAnalysis Units Name: ");
		sb.append(this.getAnalysisUnits().getDatasetName() );
		sb.append("\nBase Model Id: ");
		sb.append(this.getBaseModel().getModelId() );
		sb.append("\nBase Model Name: ");
		sb.append(this.getBaseModel().getName() );
		sb.append("\nObject Model Id: ");
		sb.append(this.getAnalysisModel().getModelId() );
		sb.append("\nObject Model Name: ");
		sb.append(this.getAnalysisModel().getName() );
		sb.append("\nVolume Units Id: ");
		sb.append(this.getVolumeUnits().getDatasetTableId() );
		sb.append("\nVolume Units Name: ");
		sb.append(this.getVolumeUnits().getDatasetName() );

		return sb.toString();
	}
	public void setAnalysisUnits(Dataset analysisUnits) {
		this.analysisUnits = analysisUnits;
	}
	public Dataset getAnalysisUnits() {
		return analysisUnits;
	}
	public void setBaseModel(FactorModel baseModel) {
		this.baseModel = baseModel;
	}
	public FactorModel getBaseModel() {
		return baseModel;
	}
	public void setAnalysisModel(FactorModel analysisModel) {
		this.analysisModel = analysisModel;
	}
	public FactorModel getAnalysisModel() {
		return analysisModel;
	}
	public void setVolumeUnits(Dataset volumeUnits) {
		this.volumeUnits = volumeUnits;
	}
	public Dataset getVolumeUnits() {
		return volumeUnits;
	}
	public void setAnalysisUnitsPeriod(String analysisUnitsPeriod) {
		this.analysisUnitsPeriod = analysisUnitsPeriod;
	}
	public String getAnalysisUnitsPeriod() {
		return analysisUnitsPeriod;
	}

	public String getAnalysisUnitsVersion() {
		return analysisUnitsVersion;
	}
	public void setAnalysisUnitsVersion(String analysisUnitsVersion) {
		this.analysisUnitsVersion = analysisUnitsVersion;
	}
	public String getAnalysisUnitsYear() {
		return analysisUnitsYear;
	}
	public void setAnalysisUnitsYear(String analysisUnitsYear) {
		this.analysisUnitsYear = analysisUnitsYear;
	}

	public void setVolumeUnitsPeriod(String volumeUnitsPeriod) {
		this.volumeUnitsPeriod = volumeUnitsPeriod;
	}
	public String getVolumeUnitsPeriod() {
		return volumeUnitsPeriod;
	}
	public String getVolumeUnitsVersion() {
		return volumeUnitsVersion;
	}
	public void setVolumeUnitsVersion(String volumeUnitsVersion) {
		this.volumeUnitsVersion = volumeUnitsVersion;
	}
	public String getVolumeUnitsYear() {
		return volumeUnitsYear;
	}
	public void setVolumeUnitsYear(String volumeUnitsYear) {
		this.volumeUnitsYear = volumeUnitsYear;
	}

	public void setBaseModelPeriod(String baseModelPeriod) {
		this.baseModelPeriod = baseModelPeriod;
	}
	public String getBaseModelPeriod() {
		return baseModelPeriod;
	}
	public void setAnalysisModelPeriod(String analysisModelPeriod) {
		this.analysisModelPeriod = analysisModelPeriod;
	}
	public String getAnalysisModelPeriod() {
		return analysisModelPeriod;
	}
	public void setCurrentYearActualModel(FactorModel currentYearActualModel) {
		this.currentYearActualModel = currentYearActualModel;
	}
	public FactorModel getCurrentYearActualModel() {
		return currentYearActualModel;
	}
	public void setLastYearActualModel(FactorModel lastYearActualModel) {
		this.lastYearActualModel = lastYearActualModel;
	}
	public FactorModel getLastYearActualModel() {
		return lastYearActualModel;
	}
	public void setCurrentYearActualUnits(Dataset currentYearActualUnits) {
		this.currentYearActualUnits = currentYearActualUnits;
	}
	public Dataset getCurrentYearActualUnits() {
		return currentYearActualUnits;
	}
	public void setLastYearActualUnits(Dataset lastYearActualUnits) {
		this.lastYearActualUnits = lastYearActualUnits;
	}
	public Dataset getLastYearActualUnits() {
		return lastYearActualUnits;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	public String getCurrentYear() {
		return currentYear;
	}
	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}
	public String getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}
	public String getCurrentDay() {
		return currentDay;
	}
	public void setAnalysisSave(String analysisSave) {
		this.analysisSave = analysisSave;
	}
	public String getAnalysisSave() {
		return analysisSave;
	}
	public void setAnalysisExtVersion(String analysisExtVersion) {
		this.analysisExtVersion = analysisExtVersion;
	}
	public String getAnalysisExtVersion() {
		return analysisExtVersion;
	}
	public void setRate1(String rate1) {
		this.rate1 = rate1;
	}
	public String getRate1() {
		return rate1;
	}

	public void setBaseVersion(String baseVersion) {
		this.baseVersion = baseVersion;
	}
	public String getBaseVersion() {
		return baseVersion;
	}
	public void setAnalysisModelVersion(String analysisModelVersion) {
		this.analysisModelVersion = analysisModelVersion;
	}
	public String getAnalysisModelVersion() {
		return analysisModelVersion;
	}
	public void setBaseYear(String baseYear) {
		this.baseYear = baseYear;
	}
	public String getBaseYear() {
		return baseYear;
	}
	public void setAnalysisModelYear(String analysisModelYear) {
		this.analysisModelYear = analysisModelYear;
	}
	public String getAnalysisModelYear() {
		return analysisModelYear;
	}


	/**
	 * @return analysisVersion
	 */
	public String getAnalysisVersion() {
		return analysisVersion;
	}

	/**
	 * @return baseHdr1
	 */
	public String getBaseHdr1() {
		return baseHdr1;
	}

	/**
	 * @return baseHdr2
	 */
	public String getBaseHdr2() {
		return baseHdr2;
	}

	/**
	 * @return baseHdr3
	 */
	public String getBaseHdr3() {
		return baseHdr3;
	}

	/**
	 * @return baseHdr4
	 */
	public String getBaseHdr4() {
		return baseHdr4;
	}

	/**
	 * @return baseTitle
	 */
	public String getBaseTitle() {
		return baseTitle;
	}

	/**
	 * @return newHdr1
	 */
	public String getNewHdr1() {
		return newHdr1;
	}

	/**
	 * @return newHdr2
	 */
	public String getNewHdr2() {
		return newHdr2;
	}

	/**
	 * @return newHdr3
	 */
	public String getNewHdr3() {
		return newHdr3;
	}

	/**
	 * @return newHdr4
	 */
	public String getNewHdr4() {
		return newHdr4;
	}

	/**
	 * @return newTitle
	 */
	public String getNewTitle() {
		return newTitle;
	}

	/**
	 * @return unitHdr1
	 */
	public String getUnitHdr1() {
		return unitHdr1;
	}

	/**
	 * @return unitHdr2
	 */
	public String getUnitHdr2() {
		return unitHdr2;
	}

	/**
	 * @return unitHdr3
	 */
	public String getUnitHdr3() {
		return unitHdr3;
	}

	/**
	 * @return unitHdr4
	 */
	public String getUnitHdr4() {
		return unitHdr4;
	}

	/**
	 * @return volHdr1
	 */
	public String getVolHdr1() {
		return volHdr1;
	}

	/**
	 * @return volHdr2
	 */ 
	public String getVolHdr2() {
		return volHdr2;
	}

	/**
	 * @return volHdr3
	 */
	public String getVolHdr3() {
		return volHdr3;
	}

	/**
	 * @return volHdr4
	 */
	public String getVolHdr4() {
		return volHdr4;
	}

	/**
	 * @param String analysisVersion
	 */
	public void setAnalysisVersion(String analysisVersion) {
		this.analysisVersion = analysisVersion;
	}

	/**
	 * @param String baseHdr1
	 */
	public void setBaseHdr1(String baseHdr1) {
		this.baseHdr1 = baseHdr1;
	}

	/**
	 * @param String baseHdr2
	 */
	public void setBaseHdr2(String baseHdr2) {
		this.baseHdr2 = baseHdr2;
	}

	/**
	 * @param String baseHdr3
	 */
	public void setBaseHdr3(String baseHdr3) {
		this.baseHdr3 = baseHdr3;
	}

	/**
	 * @param String baseHdr4
	 */
	public void setBaseHdr4(String baseHdr4) {
		this.baseHdr4 = baseHdr4;
	}

	/**
	 * @param String baseTitle
	 */
	public void setBaseTitle(String baseTitle) {
		this.baseTitle = baseTitle;
	}

	/**
	 * @param String newHdr1
	 */
	public void setNewHdr1(String newHdr1) {
		this.newHdr1 = newHdr1;
	}

	/**
	 * @param String newHdr2
	 */
	public void setNewHdr2(String newHdr2) {
		this.newHdr2 = newHdr2;
	}

	/**
	 * @param String newHdr3
	 */
	public void setNewHdr3(String newHdr3) {
		this.newHdr3 = newHdr3;
	}

	/**
	 * @param String newHdr4
	 */
	public void setNewHdr4(String newHdr4) {
		this.newHdr4 = newHdr4;
	}

	/**
	 * @param String newTitle
	 */
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

	/**
	 * @param String unitHdr1
	 */
	public void setUnitHdr1(String unitHdr1) {
		this.unitHdr1 = unitHdr1;
	}

	/**
	 * @param String unitHdr2
	 */
	public void setUnitHdr2(String unitHdr2) {
		this.unitHdr2 = unitHdr2;
	}

	/**
	 * @param String unitHdr3
	 */
	public void setUnitHdr3(String unitHdr3) {
		this.unitHdr3 = unitHdr3;
	}

	/**
	 * @param String unitHdr4
	 */
	public void setUnitHdr4(String unitHdr4) {
		this.unitHdr4 = unitHdr4;
	}

	/**
	 * @param String volHdr1
	 */
	public void setVolHdr1(String volHdr1) {
		this.volHdr1 = volHdr1;
	}

	/**
	 * @param String volHdr2
	 */
	public void setVolHdr2(String volHdr2) {
		this.volHdr2 = volHdr2;
	}

	/**
	 * @param String volHdr3
	 */
	public void setVolHdr3(String volHdr3) {
		this.volHdr3 = volHdr3;
	}

	/**
	 * @param String volHdr4
	 */
	public void setVolHdr4(String volHdr4) {
		this.volHdr4 = volHdr4;
	}

}