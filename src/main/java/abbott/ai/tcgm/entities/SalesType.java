/*
 * Created on Jan 10, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.entities;

/**
 * @author pesalvk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SalesType  extends TCGMLog implements java.io.Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the Asr maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;

	private String category = "";
	private String divisionCode = "";
	private String divisionName = "";
	private String slsType = "";
	private boolean newSlsType = true;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public SalesType()
	{
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
		sb.append("\ncategory: ");
		sb.append(this.getCategory());
		sb.append("\ndivisionCode: ");
		sb.append(this.getDivisionCode());
		sb.append("\ndivisionName: ");
		sb.append(this.getDivisionName());
		sb.append("\nslsType: ");
		sb.append(this.getSlsType());
		sb.append("\nnewSlsType: ");
		sb.append(this.isNewSlsType());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());


		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public boolean isNewSlsType()
	{
		return this.newSlsType;
	}
	/**
	 *
	 * @param newAffCstCur
	 */
	public void setNewSlsType(boolean newSlsType)
	{
		this.newSlsType = newSlsType;
	}
	/**
	 *
	 * @return
	 */
	public boolean getNewSlsType()
	{
		return this.newSlsType;
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
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return
	 */
	public String getDivisionCode() {
		return divisionCode;
	}

	/**
	 * @return
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @return
	 */
	public String getSlsType() {
		return slsType;
	}

	/**
	 * @param string
	 */
	public void setCategory(String string) {
		category = string;
	}

	/**
	 * @param string
	 */
	public void setDivisionCode(String string) {
		divisionCode = string;
	}

	/**
	 * @param string
	 */
	public void setDivisionName(String string) {
		divisionName = string;
	}

	/**
	 * @param string
	 */
	public void setSlsType(String string) {
		slsType = string;
	}

}