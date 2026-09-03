package abbott.ai.tcgm.entities;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CostExchModel extends TCGMModel implements Serializable
{
	public static final String PN_START_YEAR = "START_YEAR";
	//public static final String PN_START_PERIOD = "START_PERIOD";
	public static final String PN_START_PERIOD = "B_D56_CXPER";
	public static final String PN_END_YEAR = "END_YEAR";
	//public static final String PN_END_PERIOD = "END_PERIOD";
	public static final String PN_END_PERIOD = "E_D56_CXPER";
	//public static final String PN_RATE_SET = "RATE_SET";
	public static final String PN_RATE_SET = "CXCHG_RATE";
	public static final String PN_RATE_SET_DESC = "CXCHG_RATE_DESC";
	public static final String PN_UNIT_SET = "UNIT_SET";
	public static final String PN_START_SALES_SET = "B_CXC_VERSN"; 
	public static final String PN_START_SALES_SET_NAME = "B_CXC_VERSN_NAME";
	public static final String PN_END_SALES_SET = "E_CXC_VERSN";
	public static final String PN_MEMO = "MEMO";
	public static final String PN_FACTOR_MODEL = "CXCHG_FACTOR_MODEL";
	public static final String PN_FACTOR_MODEL_NAME = "CXCHG_FACTOR_MODEL_NAME";
	public static final String PN_END_SALES_SET_FLAG = "E_CXC_USED";
	// Added by Udaya B Aravapalli on 02/17/2006
	public static final String PN_VERSN_SET = "CXCHG_VERSN";
	public static final String PN_DIS_VERSN = "DIS_VERSN";
	public static final String PN_DIS_VERSION = "DIS_VERSION";
	public static final String PN_CXCHG_UNITS = "CXCHG_UNITS";
	public static final String PN_MODEL_DESC = "MODEL_DESC";
	
	private String startYear = "";
	private String startPeriod = "";
	private String endYear = "";
	private String endPeriod = "";
	private Dataset costExchUnits = new Dataset();
	private Dataset endingSalesData = new Dataset();
	private Dataset rateSet = new Dataset();
	private Dataset startingSalesData = new Dataset();
	private String modelIdSelected = "";
	private Dataset versionSet = new Dataset();
	private Dataset disVersion = new Dataset();
	private String factorModelName = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public CostExchModel()
	{
		this.setType(Type.COSTEXCH);
	}
	/*****************************************************************************************/
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
		return this.endPeriod.trim();
	}
	/**
	 *
	 * @param endPeriod
	 */
	public void setEndPeriod(String endPeriod)
	{
		this.endPeriod = endPeriod;
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
		return this.endYear.trim();
	}
	/*****************************************************************************************/
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
		return this.startPeriod.trim();
	}
	/**
	 *
	 * @param startPeriod
	 */
	public void setStartPeriod(String startPeriod)
	{
		this.startPeriod = startPeriod;
	}
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
		return this.startYear.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rateSet
	 */
	public void setRateSet(Dataset rateSet)
	{
		this.rateSet = rateSet;
	}
	/**
	 *
	 * @return
	 */
	public Dataset getRateSet()
	{
		if(this.rateSet == null)
		{
			this.rateSet = new Dataset();
		}
		return this.rateSet;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param startingSalesData
	 */
	public void setStartingSalesData(Dataset startingSalesData)
	{
		this.startingSalesData = startingSalesData;
	}
	/**
	 *
	 * @return
	 */
	public Dataset getStartingSalesData()
	{
		if(this.startingSalesData == null)
		{
			this.startingSalesData = new Dataset();
		}
		return this.startingSalesData;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param endingSalesData
	 */
	public void setEndingSalesData(Dataset endingSalesData)
	{
		this.endingSalesData = endingSalesData;
	}

	/**
	 *
	 * @return
	 */
	public Dataset getEndingSalesData()
	{
		if(this.endingSalesData == null)
		{
			this.endingSalesData = new Dataset();
		}
		return this.endingSalesData;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param costExchUnits
	 */
	public void setCostExchUnits(Dataset costExchUnits)
	{
		this.costExchUnits = costExchUnits;
	}
	/**
	 *
	 * @return
	 */
	public Dataset getCostExchUnits()
	{
		if(this.costExchUnits == null)
		{
			this.costExchUnits = new Dataset();
		}
		return this.costExchUnits;
	}
	/*****************************************************************************************/

	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("\nCost Exch Units: ");
		sb.append(this.getCostExchUnits().toString());
		sb.append("\nStart Period: ");
		sb.append(this.getStartPeriod());
		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());
		sb.append("\nStart Year: ");
		sb.append(this.getStartYear());
		sb.append("\nEnd Year: ");
		sb.append(this.getEndYear());
		sb.append("\nRate Set: ");
		sb.append(this.getRateSet().toString());
		sb.append("\nStarting Sales Data: ");
		sb.append(this.getStartingSalesData().toString());
		sb.append("\nEnding Sales Data: ");
		sb.append(this.getEndingSalesData().toString());
		sb.append("\nfactorModelName: ");
		sb.append(this.getFactorModelName().toString());

		return sb.toString();
	}
	/**
	 * @return String
	 */
	public String getModelIdSelected() {
		return modelIdSelected;
	}

	/**
	 * @param String modelIdSelected
	 */
	public void setModelIdSelected(String modelIdSelected) {
		this.modelIdSelected = modelIdSelected;
	}

	/**
	 * @return factorModelName
	 */
	public String getFactorModelName() {
		return factorModelName;
	}

	/**
	 * @param String factorModelName
	 */
	public void setFactorModelName(String factorModelName) {
		this.factorModelName = factorModelName;
	}
}