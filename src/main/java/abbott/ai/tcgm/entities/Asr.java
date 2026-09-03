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
public class Asr extends TCGMEntity implements Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the Asr maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;

	private String rptAff = "";
	private String supAff = "";
	private Product rptProduct = new Product();
	private Product supProduct = new Product();
	private String productOrigin = "";
	private String supKey = "";
	private String usage = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Asr()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rptAff String
	 */
	public void setRptAff(String rptAff)
	{
		this.rptAff = rptAff;
	}
	/**
	 *
	 * @return rptAff
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
	 * @param supAff String
	 */
	public void setSupAff(String supAff)
	{
		this.supAff = supAff;
	}
	/**
	 *
	 * @return supAff
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
	 * @param rptProduct Product object
	 */
	public void setRptProduct(Product rptProduct)
	{
		this.rptProduct = rptProduct;
	}
	/**
	 *
	 * @return rptProduct
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
	 * @param supProduct Product Object
	 */
	public void setSupProduct(Product supProduct)
	{
		this.supProduct = supProduct;
	}
	/**
	 *
	 * @return supProduct
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
	 * @param productOrigin String
	 */
	public void setProductOrigin(String productOrigin)
	{
		this.productOrigin = productOrigin;
	}
	/**
	 *
	 * @return productOrigin
	 */
	public String getProductOrigin()
	{
		if(this.productOrigin == null)
		{
			this.productOrigin = "";
		}
		return this.productOrigin.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param supKey String
	 */
	public void setSupKey(String supKey)
	{
		this.supKey = supKey;
	}
	/**
	 * @return supKey
	 */
	public String getSupKey()
	{
		if(this.supKey == null)
		{
			this.supKey = "";
		}
		return this.supKey.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param usage String
	 */
	public void setUsage(String usage)
	{
		this.usage = usage;
	}
	/**
	 *
	 * @return usage
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
	 * @return string representation of the class properties and their values.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());

		sb.append("\nRptAff: ");
		sb.append(this.getRptAff());
		sb.append("\nSupAff: ");
		sb.append(this.getSupAff());
		sb.append("\nProduct Origin: ");
		sb.append(this.getProductOrigin());
		sb.append("\nSupKey: ");
		sb.append(this.getSupKey());
		sb.append("\nUsage: ");
		sb.append(this.getUsage());
		sb.append("\nRpt Prod: ");
		sb.append(this.getRptProduct().toString());
		sb.append("\nSup Prod: ");
		sb.append(this.getSupProduct().toString());
		sb.append("\nSelected: ");
		sb.append(this.isSelected());
		return sb.toString();
	}
}