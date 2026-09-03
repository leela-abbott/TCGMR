package abbott.ai.tcgm.action.form;

import java.util.*;

import abbott.ai.tcgm.entities.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CreateCostExchModelForm extends TCGMCreateModelForm
{
	private String selStartingSalesData="";
	private String selRateSet="";
	private String selStartYear="";
	private String selEndingSalesData="";
	private Vector rateSets;
	private String selEndYear="";
	private String selStartPeriod="";
	private String selEndPeriod="";
	private Vector unitSets;
	private Vector salesData;
	private String selCostExchUnits="";
	private String memo; // Udaya B Aravapalli 01/09/06 - add memo
	private String modelId;// Udaya B Aravapalli 01/31/06 - add modelId
	private String factorModelName;
	/*****************************************************************************************/
	public CreateCostExchModelForm()
	{
		super();
		this.setModelType(TCGMModel.Type.COSTEXCH);
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String getSelStartingSalesData()
	{
		return selStartingSalesData;
	}
	/**
	 * @param selStartingSalesData
	 */
	public void setSelStartingSalesData(String selStartingSalesData)
	{
		this.selStartingSalesData = selStartingSalesData;
	}
	/*****************************************************************************************/
	/**
	 * @param selRateSet
	 */
	public void setSelRateSet(String selRateSet)
	{
		this.selRateSet = selRateSet;
	}
	/**
	 * @return
	 */
	public String getSelRateSet()
	{
		return selRateSet;
	}
	/*****************************************************************************************/
	/**
	 * @param selStartYear
	 */
	public void setSelStartYear(String selStartYear)
	{
		this.selStartYear = selStartYear;
	}
	/**
	 * @return
	 */
	public String getSelStartYear()
	{
		return selStartYear;
	}
	/*****************************************************************************************/
	/**
	 * @param selEndingSalesData
	 */
	public void setSelEndingSalesData(String selEndingSalesData)
	{
		this.selEndingSalesData = selEndingSalesData;
	}
	/**
	 * @return
	 */
	public String getSelEndingSalesData()
	{
		return selEndingSalesData;
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public TCGMModel getModelFromForm()
	{
		CostExchModel model = new CostExchModel();

		model.setName( this.getModelName() );
		model.setDesc( this.getModelDesc() );
		model.setEndPeriod( this.getSelEndPeriod() );
		model.setEndYear( this.getSelEndYear() );
		model.setStartPeriod( this.getSelStartPeriod() );
		model.setStartYear( this.getSelStartYear() );
		model.setMemo(this.getMemo());
		model.setModelIdSelected(this.getModelSelected());
		model.setFactorModelName(this.getFactorModelName());

		model.getCostExchUnits().setDatasetTableId( this.getSelCostExchUnits() );
		model.getStartingSalesData().setDatasetTableId( this.getSelStartingSalesData() );
		model.getEndingSalesData().setDatasetTableId( this.getSelEndingSalesData() );
		model.getRateSet().setDatasetTableId( this.getSelRateSet() );

		return model;
	}
	/*****************************************************************************************/
	/**
	 * @param rateSets
	 */
	public void setRateSets(Vector rateSets)
	{
		this.rateSets = rateSets;
	}
	/**
	 * @return
	 */
	public Vector getRateSets()
	{
		return rateSets;
	}
	/*****************************************************************************************/
	/**
	 * @param selEndYear
	 */
	public void setSelEndYear(String selEndYear)
	{
		this.selEndYear = selEndYear;
	}
	/**
	 * @return
	 */
	public String getSelEndYear()
	{
		return selEndYear;
	}
	/*****************************************************************************************/
	/**
	 * @param selStartPeriod
	 */
	public void setSelStartPeriod(String selStartPeriod)
	{
		this.selStartPeriod = selStartPeriod;
	}
	/**
	 * @return
	 */
	public String getSelStartPeriod()
	{
		return selStartPeriod;
	}
	/*****************************************************************************************/
	/**
	 * @param selEndPeriod
	 */
	public void setSelEndPeriod(String selEndPeriod)
	{
		this.selEndPeriod = selEndPeriod;
	}
	/**
	 * @return
	 */
	public String getSelEndPeriod()
	{
		return selEndPeriod;
	}
	/*****************************************************************************************/
	/**
	 * @param unitSets
	 */
	public void setUnitSets(Vector unitSets)
	{
		this.unitSets = unitSets;
	}
	/**
	 * @return
	 */
	public Vector getUnitSets()
	{
		return unitSets;
	}
	/*****************************************************************************************/
	/**
	 * @param salesData
	 */
	public void setSalesData(Vector salesData)
	{
		this.salesData = salesData;
	}
	/**
	 * @return
	 */
	public Vector getSalesData()
	{
		return salesData;
	}
	/*****************************************************************************************/
	/**
	 * @param selCostExchUnits
	 */
	public void setSelCostExchUnits(String selCostExchUnits)
	{
		this.selCostExchUnits = selCostExchUnits;
	}
	/**
	 * @return
	 */
	public String getSelCostExchUnits()
	{
		return selCostExchUnits;
	}
	/**
	 * @return String
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param string memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
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
