package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.*;
import java.io.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class RateExTran extends TCGMTranEntity implements Serializable
{
	private String rateExTranId = "";
	private String begPeriod = "";
	private String endPeriod = "";
	private String bpfRate = "";
	private String costfRate = "";
	private String bppRate = "";
	private String costpRate = "";
	private RateEx rateEx = new RateEx();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public RateExTran()
	{

	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getRateExTranId()
	{
/* 3-28-03		if(this.rateExTranId == null)
		{
			this.rateExTranId = "";
		}
		return this.rateExTranId.trim(); */
		return this.rateExTranId;
	}
	/**
	 *
	 * @param rateExTranId
	 */
	public void setRateExTranId(String rateExTranId)
	{
		if(this.rateExTranId == null)
		{
			this.rateExTranId="";
		}
		this.rateExTranId = rateExTranId;
	}
	/*****************************************************************************************/
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
	public String getBegPeriod()
	{
		if(this.begPeriod == null)
		{
			this.begPeriod = "";
		}
		return this.begPeriod.trim();
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
		return this.endPeriod.trim();
	}
	/*****************************************************************************************/
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
	 * @return
	 */
	public String getBpfRate()
	{
		if(this.bpfRate == null)
		{
			this.bpfRate = "";
		}
		//return this.bpfRate.trim();
		return this.bpfRate;
	}
	/*****************************************************************************************/
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
	 * @return
	 */
	public String getCostfRate()
	{
		if(this.costfRate == null)
		{
			this.costfRate = "";
		}
		//return this.costfRate.trim();
		return this.costfRate;
	}
	/*****************************************************************************************/
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
	 * @return
	 */
	public String getBppRate()
	{
		if(this.bppRate == null)
		{
			this.bppRate = "";
		}
		//return this.bppRate.trim();
		return this.bppRate;
	}
	/*****************************************************************************************/
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
	 * @return
	 */
	public String getCostpRate()
	{
		if(this.costpRate == null)
		{
			this.costpRate = "";
		}
		//return this.costpRate.trim();
		return this.costpRate;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public RateEx getRateEx()
	{
/* 3-28-03		if(this.rateEx == null)
		{
			this.rateEx = new RateEx();
		} */
		return this.rateEx;
	}
	/**
	 *
	 * @param rateEx
	 */
	public void setRateEx(RateEx rateEx)
	{
		this.rateEx = rateEx;
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
		sb.append("\n");
		sb.append(this.getRateEx().toString());
		sb.append("\nBeg Period: ");
		sb.append(this.getBegPeriod());
		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());
		sb.append("\nBPF Rate: ");
		sb.append(this.getBpfRate());
		sb.append("\nBPP Rate: ");
		sb.append(this.getBppRate());
		sb.append("\nCOSTF Rate: ");
		sb.append(this.getCostfRate());
		sb.append("\nCOSTP Rate: ");
		sb.append(this.getCostpRate());
		sb.append("\nRate Ex Tran Id: ");
		sb.append(this.getRateExTranId());

		return sb.toString();
	}
}