package abbott.ai.tcgm.entities;

import java.io.Serializable;
//import java.io.Serializable;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateDataTran extends TCGMTranEntity implements Serializable
{
	private RateData rateData = new RateData();
	private String begPeriod = "";
	private String endPeriod = "";
	private String rate = "";
	private String rateDataTranId = "";
	private String userName = "";
	/*****************************************************************************************/
	/**
	 *
	 */
	public RateDataTran()
	{

	}

	/**
	 *
	 * @return
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
	 * @param begPeriod
	 */
	public void setBegPeriod(String begPeriod)
	{
		this.begPeriod = begPeriod;
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

	/**
	 *
	 * @return
	 */
	public RateData getRateData()
	{
		return this.rateData;
	}

	/**
	 *
	 * @param rateData
	 */
	public void setRateData(RateData rateData)
	{
		this.rateData = rateData;
	}
	/*****************************************************************************************/
	public String getRate()
	{
		if(this.rate == null)
		{
			this.rate = "";
		}
		return this.rate.trim();
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
	 * @return
	 */
	public String getRateDataTranId()
	{
		return this.rateDataTranId.trim();
	}

	/**
	 *
	 * @param rateDataTranId
	 */
	public void setRateDataTranId(String rateDataTranId)
	{
		if(this.rateDataTranId == null)
		{
			this.rateDataTranId="";
		}
		this.rateDataTranId = rateDataTranId;
	}
	/*****************************************************************************************/
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("\n");
		sb.append(this.getRateData().toString());
		sb.append("\nBeg Period: ");
		sb.append(this.getBegPeriod());
		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());
		sb.append("\nRate: ");
		sb.append(this.getRate());
		sb.append("\nRate Data Tran Id: ");
		sb.append(this.getRateDataTranId());

		return sb.toString();
	}
	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param string
	 */
	public void setUserName(String string) {
		userName = string;
	}

}
