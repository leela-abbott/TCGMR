package abbott.ai.tcgm.entities;
//import abbott.ai.tcgm.data.*;
/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcsStaging extends TCGMTranEntity implements java.io.Serializable
{
	private Bpcs bpcs = new Bpcs();
	private String revType = "";
	private String begPeriod = "";
	private String endPeriod = "";
	private String billPrice = "";
	private String costPrice = "";
	private String bpcsStagingId = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcsStaging()
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

		sb.append(this.getBpcs().toString());
		sb.append("\n");
		sb.append(super.toString());

		sb.append("\nBeg Period: ");
		sb.append(this.getBegPeriod());

		sb.append("\nBill Price: ");
		sb.append(this.getBillPrice());

		sb.append("\nBpcs Tran Id: ");
		sb.append(this.getBpcsStagingId());

		sb.append("\nCost Price: ");
		sb.append(this.getCostPrice());

		sb.append("\nEnd Period: ");
		sb.append(this.getEndPeriod());

		sb.append("\nRev Period: ");
		sb.append(this.getRevType());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcs Bpcs object
	 */
	public void setBpcs(Bpcs bpcs)
	{
		this.bpcs = bpcs;
	}
	/**
	 *
	 * @return bpcs
	 */
	public Bpcs getBpcs()
	{
		return bpcs;
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
			this.begPeriod = "";
		}
		return this.begPeriod;
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
		return this.endPeriod;
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
		return this.billPrice;
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
		return this.costPrice;
	}

	public String getBpcsStagingId()
	{
		return bpcsStagingId;
	}
	public void setBpcsStagingId(String bpcsStagingId)
	{
		if(this.bpcsStagingId == null)
		{
			this.bpcsStagingId="";
		}
		this.bpcsStagingId = bpcsStagingId;
	}
}