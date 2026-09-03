package abbott.ai.tcgm.entities;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class ActiveAffMaint extends TCGMLog implements java.io.Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the Active Affiliate maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;

	private String aff = "";
	private String affDesc = "";
	private String divisionCode = "";
	private boolean newAffCstCur = true;
	private boolean blnSelected=false;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public ActiveAffMaint()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getAff()
	{
		if(this.aff == null)
		{
			this.aff = "";
		}
		return this.aff.trim().toUpperCase();
	}
	/**
	 *
	 * @param aff
	 */
	public void setAff(String aff)
	{
		this.aff = aff;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getAffDesc()
	{
		if(this.affDesc == null)
		{
			this.affDesc = "";
		}
		return this.affDesc.trim().toUpperCase();
	}
	/**
	 *
	 * @param affDesc
	 */
	public void setAffDesc(String affDesc)
	{
		this.affDesc = affDesc;
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
		sb.append("\nAff: ");
		sb.append(this.getAff());
		sb.append("\nAff Desc: ");
		sb.append(this.getAffDesc());
		sb.append("\nDiv Code: ");
		sb.append(this.getDivisionCode());
		sb.append("\nNew Aff Cst Cur: ");
		sb.append(this.isNewAffCstCur());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());


		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public boolean isNewAffCstCur()
	{
		return this.newAffCstCur;
	}
	/**
	 *
	 * @param newAffCstCur
	 */
	public void setNewAffCstCur(boolean newAffCstCur)
	{
		this.newAffCstCur = newAffCstCur;
	}
	/**
	 *
	 * @return
	 */
	public boolean getNewAffCstCur()
	{
		return this.newAffCstCur;
	}
	/*****************************************************************************************/
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
	public boolean getSelected()
	{
		return this.selected;
	}
	/**
	 * @return Returns the divisionCode.
	 */
	public String getDivisionCode() {
		return divisionCode;
	}
	/**
	 * @param divisionCode The divisionCode to set.
	 */
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	/**
	 * @return Returns the blnSelected.
	 */
	public boolean isBlnSelected() {
		return blnSelected;
	}
	/**
	 * @param blnSelected The blnSelected to set.
	 */
	public void setBlnSelected(boolean blnSelected) {
		this.blnSelected = blnSelected;
	}
}