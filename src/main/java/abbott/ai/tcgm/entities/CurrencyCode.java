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
public class CurrencyCode extends TCGMLog implements Serializable
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
	private boolean newCurrencyCode = true;
	private String curCode = "";
	private String curName = "";

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public CurrencyCode()
	{

	}
	/*****************************************************************************************/
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
		return curCode.trim().toUpperCase();
	}
	/**
	 *
	 * @param curCode
	 */
	public void setCurCode(String curCode)
	{
		this.curCode = curCode;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getCurName()
	{
		if(this.curName == null)
		{
			this.curName = "";
		}
		return curName.trim().toUpperCase();
	}
	/**
	 *
	 * @param curName
	 */
	public void setCurName(String curName)
	{
		this.curName = curName;
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
	public boolean isNewCurrencyCode()
	{
		return this.newCurrencyCode;
	}
	/**
	 *
	 * @param newCurrencyCode
	 */
	public void setNewCurrencyCode(boolean newCurrencyCode)
	{
		this.newCurrencyCode = newCurrencyCode;
	}
	/**
	 *
	 * @return
	 */
	public boolean getNewCurrencyCode()
	{
		return this.newCurrencyCode;
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
		sb.append("\nCur Code: ");
		sb.append(this.getCurCode());
		sb.append("\nCur Name: ");
		sb.append(this.getCurName());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());
		sb.append("\nNew CurrencyCode: ");
		sb.append(this.getNewCurrencyCode());

		return sb.toString();
	}
}