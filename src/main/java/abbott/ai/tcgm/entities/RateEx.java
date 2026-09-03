package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;
import java.io.Serializable;

/**
<p>Title: </p>
<p>Description: </p>
<p>Copyright: Copyright (c) 2002</p>
<p>Company: </p>
@author David Fields
@version 1.0
*/
public class RateEx extends TCGMEntity implements Serializable
{
	private boolean selected = false;

	private String endAff = "";
	private Product endProduct = new Product();

	private String rptAff = "";
	private Product rptProduct = new Product();

	private String supAff = "";
	private Product supProduct = new Product();

	private String begPeriod = "";
	private String endPeriod = "";
	private String bpfRate = "";
	private String costfRate = "";
	private String bppRate = "";
	private String costpRate = "";

	private Period[] bpfRates = new Period[TCGMConstants.MAX_PERIODS];
	private Period[] costfRates = new Period[TCGMConstants.MAX_PERIODS];

	private Period[] bppRates = new Period[TCGMConstants.MAX_PERIODS];
	private Period[] costpRates = new Period[TCGMConstants.MAX_PERIODS];

	/**
	 *
	 */
	public RateEx()
	{

	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Period[] getBpfRates()
	{
// 3-28-03		return this.bpfRates;
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.bpfRates[i] == null)
			{
				this.bpfRates[i] = new Period();
			}
		}
		return this.bpfRates;
	}
	/**
	 *
	 * @param bpfRates
	 */
	public void setBpfRates(Period[] bpfRates)
	{
		this.bpfRates = bpfRates;
	}
	/**
	 *
	 * @return
	 */
	public Period getBpfRates(int index)
	{
		return this.bpfRates[index];
	}
	/**
	 *
	 * @param bpfRates
	 */
	public void setBpfRates(int index,Period bpfRates)
	{
		this.bpfRates[index] = bpfRates;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Period[] getBppRates()
	{
// 3-28-03		return this.bppRates;
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.bppRates[i] == null)
			{
				this.bppRates[i] = new Period();
			}
		}
		return this.bppRates;
	}
	/**
	 *
	 * @param bppRates
	 */
	public void setBppRates(Period[] bppRates)
	{
		this.bppRates = bppRates;
	}
	/**
	 *
	 * @return
	 */
	public Period getBppRates(int index)
	{
		return this.bppRates[index];
	}
	/**
	 *
	 * @param bppRates
	 */
	public void setBppRates(int index,Period bppRates)
	{
		this.bppRates[index] = bppRates;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Period[] getCostfRates()
	{
// 3-28-03		return this.costfRates;
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.costfRates[i] == null)
			{
				this.costfRates[i] = new Period();
			}
		}
		return this.costfRates;
	}
	/**
	 *
	 * @param costfRates
	 */
	public void setCostfRates(Period[] costfRates)
	{
		this.costfRates = costfRates;
	}
	/**
	 *
	 * @return
	 */
	public Period getCostfRates(int index)
	{
		return this.costfRates[index];
	}
	/**
	 *
	 * @param costfRates
	 */
	public void setCostfRates(int index,Period costfRates)
	{
		this.costfRates[index] = costfRates;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Period[] getCostpRates()
	{
// 3-28-3		return this.costpRates;
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.costpRates[i] == null)
			{
				this.costpRates[i] = new Period();
			}
		}
		return this.costpRates;
	}
	/**
	 *
	 * @param costpRates
	 */
	public void setCostpRates(Period[] costpRates)
	{
		this.costpRates = costpRates;
	}
	/**
	 *
	 * @return
	 */
	public Period getCostpRates(int index)
	{
		return this.costpRates[index];
	}
	/**
	 *
	 * @param costpRates
	 */
	public void setCostpRates(int index,Period costpRates)
	{
		this.costpRates[index] = costpRates;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getEndAff()
	{
		if(this.endAff == null)
		{
			this.endAff = "";
		}
		return this.endAff.trim().toUpperCase();
	}
	/**
	 *
	 * @param endAff
	 */
	public void setEndAff(String endAff)
	{
		this.endAff = endAff;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Product getEndProduct()
	{
		if(this.endProduct == null)
		{
			this.endProduct = new Product();
		}
		return this.endProduct;
	}
	/**
	 *
	 * @param endProduct
	 */
	public void setEndProduct(Product endProduct)
	{
		this.endProduct = endProduct;
	}
	/*****************************************************************************************/
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
	 * @param rptAff
	 */
	public void setRptAff(String rptAff)
	{
		this.rptAff = rptAff;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Product getRptProduct()
	{
		if(this.rptProduct == null)
		{
			this.rptProduct = new Product();
		}
		return this.rptProduct;
	}
	/**
	 *
	 * @param rptProduct
	 */
	public void setRptProduct(Product rptProduct)
	{
		this.rptProduct = rptProduct;
	}
	/*****************************************************************************************/
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
	 * @param supAff
	 */
	public void setSupAff(String supAff)
	{
		this.supAff = supAff;
	}
	/*****************************************************************************************/
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
	 * @param supProduct
	 */
	public void setSupProduct(Product supProduct)
	{
		this.supProduct = supProduct;
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
			this.endPeriod = "";
		}
		return this.endPeriod.trim();
	}

	/**
	 *
	 * @param bpfRate
	 */
	public void setBpfRate(String bpfRate)
	{
		this.bpfRate = bpfRate;
	}
	/**
	 *
	 * @return bpfRate
	 */
	public String getBpfRate()
	{
		if(this.bpfRate == null)
		{
			this.bpfRate = "";
		}
		return this.bpfRate.trim();
	}

	/**
	 *
	 * @param costfRate
	 */
	public void setCostfRate(String costfRate)
	{
		this.costfRate = costfRate;
	}
	/**
	 *
	 * @return costfRate
	 */
	public String getCostfRate()
	{
		if(this.costfRate == null)
		{
			this.costfRate = "";
		}
		return this.costfRate.trim();
	}

	/**
	 *
	 * @param bppRate
	 */
	public void setBppRate(String bppRate)
	{
		this.bppRate = bppRate;
	}
	/**
	 *
	 * @return bppRate
	 */
	public String getBppRate()
	{
		if(this.bppRate == null)
		{
			this.bppRate = "";
		}
		return this.bppRate.trim();
	}

	/**
	 *
	 * @param costpRate
	 */
	public void setCostpRate(String costpRate)
	{
		this.costpRate = costpRate;
	}
	/**
	 *
	 * @return costpRate
	 */
	public String getCostpRate()
	{
		if(this.costpRate == null)
		{
			this.costpRate = "";
		}
		return this.costpRate.trim();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return selected
	 */
	public boolean isSelected()
	{
		return this.selected;
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
	 * @param selected boolean
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());

		sb.append("\nEnd Aff: ");
		sb.append(this.getEndAff());
		sb.append("\nEnd Product: ");
		sb.append(this.getEndProduct());

		sb.append("\nRpt Aff: ");
		sb.append(this.getRptAff());
		sb.append("\nRpt Product: ");
		sb.append(this.getRptProduct());

		sb.append("\nSup Aff: ");
		sb.append(this.getSupAff());
		sb.append("\nSup Product: ");
		sb.append(this.getSupProduct());

		sb.append("\nBPF Rates: ");
		sb.append(this.getBpfRates());
		sb.append("\nBPP Rates: ");
		sb.append(this.getBppRates());
		sb.append("\nCOSTF Rates: ");
		sb.append(this.getCostfRates());
		sb.append("\nCOSTP Rates: ");
		sb.append(this.getCostpRates());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());


		return sb.toString();
	}
}