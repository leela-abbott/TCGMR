package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.*;
import java.io.Serializable;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class BpcExTran extends TCGMTranEntity implements Serializable
{
	private BpcEx bpcEx = new BpcEx();
	//private String begPeriod="1";
	private String begPeriod="";
	//private String endPeriod="12";
	private String endPeriod="";
	private String billPrice="";
	private String costPrice = "";
	private String bpcExTranId = "";
	/**
	 * Default Constructor
	 */
	public BpcExTran()
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
			//this.begPeriod = "1";
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
			//this.endPeriod = "12";
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
	 * @return BpcEx
	 */
	public BpcEx getBpcEx()
	{
		return this.bpcEx;
	}
	/**
	 *
	 * @param bpcEx BpcEx
	 */
	public void setBpcEx(BpcEx bpcEx)
	{
		this.bpcEx = bpcEx;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String getBpcExTranId()
	{
		if(this.bpcExTranId == null)
		{
			this.bpcExTranId = "";
		}
		return this.bpcExTranId.trim();
	}
	/**
	 *
	 * @param bpcExTranId Bpc Ex Tran Id
	 */
	public void setBpcExTranId(String bpcExTranId)
	{
		this.bpcExTranId = bpcExTranId;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n");
		sb.append(super.toString());
		sb.append("\nBeg Period: ");
		sb.append(this.getBegPeriod());
		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());
		sb.append("\nBill Price: ");
		sb.append(this.getBillPrice());
		sb.append("\nCost Price: ");
		sb.append(this.getCostPrice());
		sb.append("\nBPC EX Tran Id: ");
		sb.append(this.getBpcExTranId());
		sb.append("\n");
		sb.append(this.getBpcEx().toString());

		return sb.toString();
	}
}
