package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class TCGMState
{
	private String currentModelName = TCGMConstants.NONE_SELECTED;
	private int currentModelId = 0;

	private String curRateSetName = TCGMConstants.NONE_SELECTED;
	private int curRateSetTableId = -999;

	/**
	 * These properties exist because there will be only 1 rate model and I don't want to have
	 * any conflicts by using the currentModelName and currentModelId properties above.  Those other
	 * properties are used by maintenance pages as well as other pages and they check to see if a model
	 * is selected.  If I use them for the rate model they will think that a valid model has been selected
	 * when it might not have been.
	 */
	private String rateModelName = TCGMConstants.NONE_SELECTED;
	private int rateModelId = -999;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMState()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getCurrentModelName()
	{
		if(this.currentModelName == null)
		{
			this.currentModelName = TCGMConstants.NONE_SELECTED;
		}
		return this.currentModelName.trim();
	}
	/**
	 *
	 * @param currentModelName
	 */
	public void setCurrentModelName(String currentModelName)
	{
		this.currentModelName = currentModelName;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public int getCurrentModelId()
	{
		return this.currentModelId;
	}
	/**
	 *
	 * @param currentModelId
	 */
	public void setCurrentModelId(int currentModelId)
	{
		this.currentModelId = currentModelId;
	}
	/**
	 *
	 * @return
	 */
	public String getCurrentModelIdString()
	{
		return Integer.toString(this.currentModelId);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param modelName
	 * @param modelId
	 */
	public void setCurrentModel(String modelName, int modelId)
	{
		this.setCurrentModelName(modelName);
		this.setCurrentModelId(modelId);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public int getCurRateSetTableId()
	{
		return this.curRateSetTableId;
	}
	/**
	 *
	 * @param curRateSetTableId
	 */
	public void setCurRateSetTableId(int curRateSetTableId)
	{
		this.curRateSetTableId = curRateSetTableId;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getCurRateSetName()
	{
		if(this.curRateSetName == null)
		{
			this.curRateSetName = TCGMConstants.NONE_SELECTED;
		}
		return this.curRateSetName.trim();
	}
	/**
	 *
	 * @param curRateSetName
	 */
	public void setCurRateSetName(String curRateSetName)
	{
		this.curRateSetName = curRateSetName;
	}
	/*****************************************************************************************/
	public void setCurRateSet(String curRateSetName,int curRateSetTableId)
	{
		this.setCurRateSetName(curRateSetName);
		this.setCurRateSetTableId(curRateSetTableId);
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public int getRateModelId()
	{
		return this.rateModelId;
	}
	/**
	 *
	 * @param rateModelId
	 */
	public void setRateModelId(int rateModelId)
	{
		this.rateModelId = rateModelId;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getRateModelName()
	{
		if(this.rateModelName == null)
		{
			this.rateModelName = TCGMConstants.NONE_SELECTED;
		}
		return rateModelName.trim();
	}
	/**
	 *
	 * @param rateModelName
	 */
	public void setRateModelName(String rateModelName)
	{
		this.rateModelName = rateModelName;
	}
	/*****************************************************************************************/
	/**
	 * Convenience method
	 * @param rateModelName
	 * @param rateModelId
	 */
	public void setRateModel(String rateModelName, int rateModelId)
	{
		this.setRateModelName(rateModelName);
		this.setRateModelId(rateModelId);
	}
	/*****************************************************************************************/

	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Current Rate Set Name: ");
		sb.append(this.getCurRateSetName());
		sb.append("\nCurrent Rate Set Table Id: ");
		sb.append(this.getCurRateSetTableId());
		sb.append("\nCurrent Model Id: ");
		sb.append(this.getCurrentModelId());
		sb.append("\nCurrent Model Name: ");
		sb.append(this.getCurrentModelName());
		sb.append("\nRate Model Id: ");
		sb.append(this.getRateModelId());
		sb.append("\nRate Model Name: ");
		sb.append(this.getRateModelName());

		return sb.toString();
	}

}