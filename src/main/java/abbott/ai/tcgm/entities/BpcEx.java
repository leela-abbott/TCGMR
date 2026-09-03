package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;
import java.io.Serializable;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class BpcEx extends TCGMEntity implements Serializable
{
	private boolean selected = false;

	private String endAff = "";
	private String rptAff = "";
	private String supAff = "";

	private Product endProduct = new Product();
	private Product rptProduct = new Product();
	private Product supProduct = new Product();

	private String usage = "";
	private String freezeCost = "";

	private String bpCurCode = "";
	private Period bpPeriodValues[] = new Period[TCGMConstants.MAX_PERIODS];

	private String costCurCode = "";
	private Period costPeriodValues[] = new Period[TCGMConstants.MAX_PERIODS];

	//private String begPeriod="1";
	private String begPeriod = "";
	//private String endPeriod="12";
	private String endPeriod = "";
	private String billPrice="";
	private String costPrice = "";
	private String bpcExId = "";

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcEx()
	{

	}
	/*****************************************************************************************/
	/**
	 *
	 * @param begPeriod Beginning Period
	 */
	public void setBegPeriod(String begPeriod)
	{
		this.begPeriod = begPeriod;
	}
	/**
	 *
	 * @return String
	 */
	public String getBegPeriod()
	{
		if(this.begPeriod == null)
		{
			// 4/1/03 this.begPeriod = "1";
			this.begPeriod = "";
		}
		return this.begPeriod.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param endPeriod End Period
	 */
	public void setEndPeriod(String endPeriod)
	{
		this.endPeriod = endPeriod;
	}
	/**
	 *
	 * @return String
	 */
	public String getEndPeriod()
	{
		if(this.endPeriod == null)
		{
			// 4/1/03 this.endPeriod = "12";
			this.endPeriod = "";
		}
		return this.endPeriod.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param billPrice Billing Price
	 */
	public void setBillPrice(String billPrice)
	{
		this.billPrice = billPrice;
	}
	/**
	 *
	 * @return String
	 */
	public String getBillPrice()
	{
		if(this.billPrice == null)
		{
			this.billPrice = "";
		}
		return this.billPrice.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param costPrice Cost Price
	 */
	public void setCostPrice(String costPrice)
	{
		this.costPrice = costPrice;
	}
	/**
	 *
	 * @return String
	 */
	public String getCostPrice()
	{
		if(this.costPrice == null)
		{
			this.costPrice = "";
		}
		return this.costPrice.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String getBpcExId()
	{
		if(this.bpcExId == null)
		{
			this.bpcExId = "";
		}
		return this.bpcExId.trim();
	}
	/**
	 *
	 * @param bpcExTranId Bpc Ex Tran Id
	 */
	public void setBpcExId(String bpcExId)
	{
		this.bpcExId = bpcExId;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param endAff End Affiliate
	 */
	public void setEndAff(String endAff)
	{
		this.endAff = endAff;
	}
	/**
	 *
	 * @return End Affiliate
	 */
	public String getEndAff()
	{
		if(this.endAff == null)
		{
			this.endAff = "";
		}
		return this.endAff.trim().toUpperCase();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param endProduct End Product
	 */
	public void setEndProduct(Product endProduct)
	{
		this.endProduct = endProduct;
	}
	/**
	 *
	 * @return End Product
	 */
	public Product getEndProduct()
	{
		if(this.endProduct == null)
		{
			this.endProduct = new Product();
		}
		return this.endProduct;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rptAff Rpt Affiliate
	 */
	public void setRptAff(String rptAff)
	{
		this.rptAff = rptAff;
	}
	/**
	 *
	 * @return Rpt Affiliate
	 */
	public String getRptAff()
	{
		if(this.rptAff == null)
		{
			this.rptAff = "";
		}
		return this.rptAff.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rptProduct Rpt Product
	 */
	public void setRptProduct(Product rptProduct)
	{
		this.rptProduct = rptProduct;
	}
	/**
	 *
	 * @return Rpt Product
	 */
	public Product getRptProduct()
	{
		if(this.rptProduct == null)
		{
			this.rptProduct = new Product();
		}
		return this.rptProduct;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param supAff Sup Affiliate
	 */
	public void setSupAff(String supAff)
	{
		this.supAff = supAff;
	}
	/**
	 *
	 * @return Sup Affiliate
	 */
	public String getSupAff()
	{
		if(this.supAff == null)
		{
			this.supAff = "";
		}
		return this.supAff.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param supProduct Sup Product
	 */
	public void setSupProduct(Product supProduct)
	{
		this.supProduct = supProduct;
	}
	/**
	 *
	 * @return Sup Product
	 */
	public Product getSupProduct()
	{
		if(this.supProduct == null)
		{
			this.supProduct = new Product();
		}
		return this.supProduct;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param usage Usage Factor
	 */
	public void setUsage(String usage)
	{
		this.usage = usage;
	}
	/**
	 *
	 * @return Usage Factor
	 */
	public String getUsage()
	{
		if(this.usage == null)
		{
			this.usage = "";
		}
		return this.usage.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpCurCode BP Currency Code
	 */
	public void setBpCurCode(String bpCurCode)
	{
		this.bpCurCode = bpCurCode;
	}
	/**
	 *
	 * @return BP Currency Code
	 */
	public String getBpCurCode()
	{
		if(this.bpCurCode == null)
		{
			this.bpCurCode = "";
		}
		return this.bpCurCode.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpPeriodValues BP Period values
	 */
	public void setBpPeriodValues(Period[] bpPeriodValues)
	{
		this.bpPeriodValues = bpPeriodValues;
	}
	/**
	 *
	 * @return BP Period Values
	 */
	public Period[] getBpPeriodValues()
	{
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.bpPeriodValues[i] == null)
			{
				this.bpPeriodValues[i] = new Period();
			}
		}
		return this.bpPeriodValues;
	}
	/**
	 *
	 * @param index Index of array
	 * @param period Period object to set
	 */
	public void setBpPeriodValues(int index,Period period)
	{
		this.bpPeriodValues[index] = period;
	}
	/**
	 *
	 * @param index Array Index
	 * @return BP Period
	 */
	public Period getBpPeriodValues(int index)
	{
/* 4/1/03		if(this.bpPeriodValues[index] == null)
		{
			this.setBpPeriodValues(index,new Period());
		} */
		return this.bpPeriodValues[index];
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param costCurCode Cost Currency Code
	 */
	public void setCostCurCode(String costCurCode)
	{
		this.costCurCode = costCurCode;
	}
	/**
	 *
	 * @return Cost Currency Code
	 */
	public String getCostCurCode()
	{
		if(this.costCurCode == null)
		{
			this.costCurCode = "";
		}
		return this.costCurCode.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param costPeriodValues Cost Period Values
	 */
	public void setCostPeriodValues(Period[] costPeriodValues)
	{
		this.costPeriodValues = costPeriodValues;
	}
	/**
	 *
	 * @return Cost Period Values array
	 */
	public Period[] getCostPeriodValues()
	{
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.costPeriodValues[i] == null)
			{
				this.costPeriodValues[i] = new Period();
			}
		}
		return this.costPeriodValues;
	}
	/**
	 *
	 * @param index Index of array
	 * @param period Period object to set
	 */
	public void setCostPeriodValues(int index,Period period)
	{
		this.costPeriodValues[index] = period;
	}
	/**
	 *
	 * @param index Array Index
	 * @return Cost Period
	 */
	public Period getCostPeriodValues(int index)
	{
/* 4/1/03		if(this.costPeriodValues[index] == null)
		{
			this.setCostPeriodValues(index,new Period());
		} */
		return this.costPeriodValues[index];
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param freezeCost Freeze Cost
	 */
	public void setFreezeCost(String freezeCost)
	{
		this.freezeCost = freezeCost;
	}
	/**
	 *
	 * @return Freeze Cost
	 */
	public String getFreezeCost()
	{
		if(this.freezeCost == null)
		{
			this.freezeCost = "";
		}
		return this.freezeCost.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return true if selected
	 */
	public boolean getSelected()
	{
		return this.selected;
	}
	/**
	 *
	 * @return true if selected
	 */
	public boolean isSelected()
	{
		return this.selected;
	}
	/**
	 *
	 * @param selected flag
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());


		sb.append("\n BP Cur Code: ");
		sb.append(this.getBpCurCode());
		sb.append("\n Cost Cur Code: ");
		sb.append(this.getCostCurCode());
		sb.append("\nEnd Aff: ");
		sb.append(this.getEndAff());
		sb.append("\nEnd Prod: ");
		sb.append(this.getEndProduct().toString());
		sb.append("\nFreeze Cost: ");
		sb.append(this.getFreezeCost());
		sb.append("\nRpt Aff: ");
		sb.append(this.getRptAff());
		sb.append("\nRpt Prod: ");
		sb.append(this.getRptProduct().toString());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());
		sb.append("\nSup Aff: ");
		sb.append(this.getSupAff());
		sb.append("\nSup Prod: ");
		sb.append(this.getSupProduct().toString());
		sb.append("\nUsage: ");
		sb.append(this.getUsage());
		sb.append("\nBeg Period: ");
		sb.append(this.getBegPeriod());
		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());
		sb.append("\nBill Price: ");
		sb.append(this.getBillPrice());
		sb.append("\nCost Price: ");
		sb.append(this.getCostPrice());
		sb.append("\nBPC EX Id: ");
		sb.append(this.getBpcExId());

		for(int i = 0; i < this.getBpPeriodValues().length; i++)
		{
			sb.append("\n BP Period Value ");
			sb.append(i);
			sb.append(": ");
			sb.append(this.getBpPeriodValues()[i].getPeriod());
		}

		for(int i = 0; i < this.getCostPeriodValues().length; i++)
		{
			sb.append("\n cost Period Value ");
			sb.append(i);
			sb.append(": ");
			sb.append(this.getCostPeriodValues()[i].getPeriod());
		}

		return sb.toString();
	}
}