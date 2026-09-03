package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;
import java.io.Serializable;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateData extends TCGMEntity implements Serializable
{
	private boolean selected = false;

	private String curCode = "";
	private String begPeriod = "";
	private String endPeriod = "";
	private String rate = "";
	private Period rates[] = new Period[TCGMConstants.MAX_PERIODS];

	//private Period ratePeriodValues[] = new Period[TCGMConstants.MAX_PERIODS];

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public RateData()
	{

	}
	/*****************************************************************************************/
	/**
	 *
	 * @param curCode
	 */
	public void setCurCode(String curCode)
	{
		this.curCode = curCode;
	}

	/**
	 *
	 * @return
	 */
	public String getCurCode()
	{
		if(this.curCode == null)
		{
			this.curCode = "";
		}
		return this.curCode.trim().toUpperCase();
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
	 * @param rate
	 */
	public void setRate(String rate)
	{
		this.rate = rate;
	}

	/**
	 *
	 * @return rate
	 */
	public String getRate()
	{
		if(this.rate == null)
		{
			this.rate = "";
		}
		return this.rate.trim();
	}

	/**
	 * @param rates
	 */
	public void setRates(Period[] rates)
	{
		this.rates = rates;
	}

	/**
	 *
	 * @return
	 */
	public Period[] getRates()
	{
		for(int i = 0; i < TCGMConstants.MAX_PERIODS; i++)
		{
			if( this.rates[i] == null)
			{
				this.rates[i] = new Period();
			}
		}
		return this.rates;
	}

	/**
	 * @param index
	 * @return
	 */
	public Period getRates(int index)
	{
		return this.rates[index];
	}
	/**
	 * @param index
	 */
	public void setRates(int index, Period rate)
	{
		this.rates[index] = rate;
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
	 * @param selected
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	/*****************************************************************************************/

	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("\nCur Code: ");
		sb.append(this.getCurCode());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());

		for(int i = 0; i < this.getRates().length; i++)
		{
			sb.append("\nRate ");
			sb.append(i);
			sb.append(": ");
			sb.append(this.getRates()[i].toString());
		}

		return sb.toString();
	}
}
