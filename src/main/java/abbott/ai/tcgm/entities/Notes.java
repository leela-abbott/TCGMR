package abbott.ai.tcgm.entities;

import java.io.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class Notes extends TCGMEntity implements Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the Asr maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;

	/**
	 * This is here because I need to know if they are editing an existing or creating a new code
	 */
	private boolean newNotes = true;
	private String rptAff = "";
	private Product rptProduct = new Product();
	private String note = "";

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Notes()
	{

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
		return rptAff.trim().toUpperCase();
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
	/**
	 *
	 * @param notes
	 */
	public void setNote(String note)
	{
		this.note = note;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getNote()
	{
		if(this.note == null)
		{
			this.note = "";

		}
		return this.note.trim();
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
	public boolean isNewNotes()
	{
		return this.newNotes;
	}
	/**
	 *
	 * @param newNotes
	 */
	public void setNewNotes(boolean newNotes)
	{
		this.newNotes = newNotes;
	}
	/**
	 *
	 * @return
	 */
	public boolean getNewNotes()
	{
		return this.newNotes;
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
		sb.append("\nRptAff: ");
		sb.append(this.getRptAff());
		sb.append("\nRpt Prod: ");
		sb.append(this.getRptProduct().toString());
		sb.append("\nNotes: ");
		sb.append(this.getNote());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());
		sb.append("\nNew Notes: ");
		sb.append(this.getNewNotes());

		return sb.toString();
	}
}