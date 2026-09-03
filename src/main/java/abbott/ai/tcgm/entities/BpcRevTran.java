package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.*;
import java.io.Serializable;
/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcRevTran extends TCGMTranEntity implements Serializable
{
	private BpcRev bpcRev = new BpcRev();
	private String revType = "";
	private String begPeriod = "";
	private String endPeriod = "";
	private String billPrice = "";
	private String costPrice = "";
	private String bpcRevTranId = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcRevTran()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return string representation of the class properties and their values.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(this.getBpcRev().toString());
		sb.append("\n");
		sb.append(super.toString());

		sb.append("\nBeg Period: ");
		sb.append(this.getBegPeriod());

		sb.append("\nBill Price: ");
		sb.append(this.getBillPrice());

		sb.append("\nBpcRev Tran Id: ");
		sb.append(this.getBpcRevTranId());

		sb.append("\nCost Price: ");
		sb.append(this.getCostPrice());

		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());

		sb.append("\nRev Type: ");
		sb.append(this.getRevType());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcRev BpcRev object
	 */
	public void setBpcRev(BpcRev bpcRev)
	{
		this.bpcRev = bpcRev;

	}
	/**
	 *
	 * @return bpcRev
	 */
	public BpcRev getBpcRev()
	{
		return bpcRev;
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
			//this.revType = "";
			this.revType = " ";
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
		if(this.billPrice == null)
		{
			this.billPrice = "";
		}
		return this.billPrice.trim();
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
		return this.costPrice.trim();
	}

	/**
	 *
	 * @return
	 */
	public String getBpcRevTranId()
	{
		return bpcRevTranId;
	}

	/**
	 *
	 * @param bpcRevTranId
	 */
	public void setBpcRevTranId(String bpcRevTranId)
	{
		if(this.bpcRevTranId == null)
		{
			this.bpcRevTranId="";
		}
		this.bpcRevTranId = bpcRevTranId;
	}
}