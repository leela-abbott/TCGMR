package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.*;
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
public class AffBpc extends TCGMEntity implements Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the AffBpc maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;

	private String rptAff = "";
	private String supAff = "";
	private Product supProduct = new Product();
	private String billPrice = "";
	private String bpCurCode = "";
	private String costPrice = "";
	private String costCurCode = "";
	private String affiliate = "";
	private String affiliateGroup = "";

	// Parameter values entered by user to run the job
	private String updateType = "";  // (A)dd or (C)hange
	private String amountType = ""; // Cost / Price / * = both
	private String affBpcId = "";
	private String cycleId="";

	/**
	 *
	 */
	public AffBpc()
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
		return this.rptAff.toUpperCase();
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
		return this.supAff.toUpperCase();
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
		return this.bpCurCode.toUpperCase();
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
		return this.costCurCode.toUpperCase();
	}

	/**
	 *
	 * @param updateType
	 */
	public void setUpdateType(String updateType)
	{
		this.updateType = updateType;
	}

	/**
	 *
	 * @return updateType
	 */
	public String getUpdateType()
	{
		if(this.updateType == null)
		{
			this.updateType = "";
		}
		return this.updateType;
	}

	/**
	 *
	 * @param amountType
	 */
	public void setAmountType(String amountType)
	{
		this.amountType = amountType;
	}

	/**
	 *
	 * @return amountType
	 */
	public String getAmountType()
	{
		if(this.amountType == null)
		{
			this.amountType = "";
		}
		return this.amountType;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return AffBpc Id
	 */
	public String getAffBpcId()
	{
		if(this.affBpcId == null)
		{
			this.affBpcId = "";
		}
		return this.affBpcId.trim();
	}
	/**
	 *
	 * @param affBpcId AffBpc Id
	 */
	public void setAffBpcId(String affBpcId)
	{
		this.affBpcId = affBpcId;
	}

// I think the get(Selected(), setSelected(), and isSeleected() can be removeed because I may not use these attributes in AffBpc
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

		sb.append("\nRptAff: ");
		sb.append(this.getRptAff());
		sb.append("\nSupAff: ");
		sb.append(this.getSupAff());
		sb.append("\n");
		sb.append(this.getSupProduct().toString());
		sb.append("\n");
		sb.append(this.isSelected());
		sb.append("\nAffiliate");
		sb.append(this.getAffiliate());

		return sb.toString();
	}

	/**
	 * @return
	 */
	public String getAffiliate() {
		return affiliate;
	}

	/**
	 * @param string
	 */
	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}

	/**
	 * @return
	 */
	public String getAffiliateGroup() {
		return affiliateGroup;
	}

	/**
	 * @param string
	 */
	public void setAffiliateGroup(String string) {
		affiliateGroup = string;
	}

	/**
	 * @return Returns the cycleId.
	 */
	public String getCycleId() {
		return cycleId;
	}
	/**
	 * @param cycleId The cycleId to set.
	 */
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
}