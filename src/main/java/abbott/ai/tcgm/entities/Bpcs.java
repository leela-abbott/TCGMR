package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;
import java.io.Serializable;
/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Brian A. Dennis
 * @version 1.0
 */
public class Bpcs extends TCGMEntity implements Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the Bpcs maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;

	private String rptAff = "";
	private String supAff = "";
	private Product supProduct = new Product();
	private String freezeCost = "";
	private String bpCurCode = "";
	private String costCurCode = "";

	private String revType = "";
	//private String begPeriod = "1";
	private String begPeriod = "";
	//private String endPeriod = "12";
	private String endPeriod = "";
	private String billPrice = "";
	private String costPrice = "";

	private Period costPeriodValues[] = new Period[TCGMConstants.MAX_PERIODS];
	private Period bpPeriodValues[] = new Period[TCGMConstants.MAX_PERIODS];

	/**
	 *
	 */
	public Bpcs()
	{

	}

	/**
	 *
	 * @param rptAff
	 */
	public void setRptAff(String rptAff)
	{
		this.rptAff = rptAff;
	}

	/**
	 *
	 * @return
	 */
	public String getRptAff()
	{
		if(this.rptAff == null)
		{
			this.rptAff = "";
		}
		return this.rptAff.trim().toUpperCase();
	}

	/**
	 *
	 * @param supAff
	 */
	public void setSupAff(String supAff)
	{
		this.supAff = supAff;
	}

	/**
	 *
	 * @return
	 */
	public String getSupAff()
	{
		if(this.supAff == null)
		{
			this.supAff = "";
		}
		return this.supAff.trim().toUpperCase();
	}

	/**
	 *
	 * @param supProduct
	 */
	public void setSupProduct(Product supProduct)
	{
		this.supProduct = supProduct;
	}

	/**
	 *
	 * @return
	 */
	public Product getSupProduct()
	{
		if(this.supProduct == null)
		{
			this.supProduct = new Product();
		}
		return this.supProduct;
	}

	/**
	 *
	 * @param freezeCost
	 */
	public void setFreezeCost(String freezeCost)
	{
		this.freezeCost = freezeCost;
	}

	/**
	 *
	 * @return
	 */
	public String getFreezeCost()
	{
		if(this.freezeCost == null)
		{
			this.freezeCost = "";
		}
		return this.freezeCost.trim().toUpperCase();
	}

	/**
	 *
	 * @param costCurCode
	 */
	public void setCostCurCode(String costCurCode)
	{
		this.costCurCode = costCurCode;
	}

	/**
	 *
	 * @return
	 */
	public String getCostCurCode()
	{
		if(this.costCurCode == null)
		{
			this.costCurCode = "";
		}
		return this.costCurCode.trim().toUpperCase();
	}

	/**
	 *
	 * @param bpCurCode
	 */
	public void setBpCurCode(String bpCurCode)
	{
		this.bpCurCode = bpCurCode;
	}

	/**
	 *
	 * @return
	 */
	public String getBpCurCode()
	{
		if(this.bpCurCode == null)
		{
			this.bpCurCode = "";
		}
		return this.bpCurCode.trim().toUpperCase();
	}
	/**
	 *
	 * @param revType
	 */
	public void setRevType(String revType)
	{
		this.revType = revType;
	}
	/**
	 *
	 * @return revType
	 */
	public String getRevType()
	{
		if(this.revType == null)
		{
			this.revType = "";
		}
		return this.revType.trim().toUpperCase();
	}

	/**
	 *
	 * @param begPeriod
	 */
	public void setBegPeriod(String begPeriod)
	{
		this.begPeriod = begPeriod;
	}
	/**
	 *
	 * @return begPeriod
	 */
	public String getBegPeriod()
	{
		if(this.begPeriod == null)
		{
			//this.begPeriod = "1";
			this.begPeriod = "";
		}
		return this.begPeriod.trim();
	}

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
	 * @return endPeriod
	 */
	public String getEndPeriod()
	{
		if(this.endPeriod == null)
		{
			//this.endPeriod = "12";
			this.endPeriod = "";
		}
		return this.endPeriod.trim();
	}

	/**
	 *
	 * @param billPrice
	 */
	public void setBillPrice(String billPrice)
	{
		this.billPrice = billPrice;
	}
	/**
	 *
	 * @return billPrice
	 */
	public String getBillPrice()
	{
		// 4-16-03 if check below didn't blank out billPrice field
		//if(this.billPrice == null || this.bpCurCode.equals("") || this.bpCurCode.equals(" "))
		if(this.billPrice == null)
		{
			this.billPrice = "";
		}
		return this.billPrice.trim().toUpperCase();
	}

	/**
	 *
	 * @param costPrice
	 */
	public void setCostPrice(String costPrice)
	{
		this.costPrice = costPrice;
	}
	/**
	 *
	 * @return costPrice
	 */
	public String getCostPrice()
	{
		if(this.costPrice == null)
		{
			this.costPrice = "";
		}
		return this.costPrice.trim().toUpperCase();
	}

	/**
	 *
	 * @return selected
	 */
	public boolean getSelected()
	{
		return this.selected;
	}
	/**
	 *
	 * @param selected
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/**
	 *
	 * @return
	 */
	public boolean isSelected()
	{
		return this.selected;
	}

	/**
	 *
	 * @return
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
	 * @param bpPeriodValues
	 */
	public void setBpPeriodValues(Period[] bpPeriodValues)
	{
		this.bpPeriodValues = bpPeriodValues;
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	public Period getBpPeriodValues(int index)
	{
		return this.bpPeriodValues[index];
	}

	/**
	 *
	 * @param index
	 * @param period
	 */
	public void setBpPeriodValues(int index,Period period)
	{
		this.bpPeriodValues[index] = period;
	}

	/**
	 *
	 * @return
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
	 * @param costPeriodValues
	 */
	public void setCostPeriodValues(Period[] costPeriodValues)
	{
		this.costPeriodValues = costPeriodValues;
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	public Period getCostPeriodValues(int index)
	{
		return this.costPeriodValues[index];
	}

	/**
	 *
	 * @param index
	 * @param period
	 */
	public void setCostPeriodValues(int index,Period period)
	{
		this.costPeriodValues[index] = period;
	}
	/**
	 *
	 * @return string representation of the class properties and their values.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());

		sb.append("\nBP Cur Code: ");
		sb.append(this.getBpCurCode());

		sb.append("\nCost Cur Code: ");
		sb.append(this.getCostCurCode());

		sb.append("\nFreeze Cost: ");
		sb.append(this.getFreezeCost());

		sb.append("\nRptAff: ");
		sb.append(this.getRptAff());
		sb.append("\nSupAff: ");
		sb.append(this.getSupAff());
		sb.append("\nSup Prod: ");
		sb.append(this.getSupProduct().toString());
		sb.append("\n");
		sb.append(this.isSelected());

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