package abbott.ai.tcgm.entities;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */

public class KnollConv extends TCGMLog implements java.io.Serializable
{
	private String supAff = "";
	private String convAff = "";
	private boolean selected = false;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public KnollConv()
	{
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String getConvAff()
	{
		if(this.convAff == null)
		{
			this.convAff = "";
		}
		return this.convAff.trim().toUpperCase();
	}
	/**
	 * @param convAff
	 */
	public void setConvAff(String convAff)
	{
		this.convAff = convAff;
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public boolean isSelected()
	{
		return selected;
	}
	/**
	 * @param selected
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	/**
	 * @return
	 */
	public boolean getSelected()
	{
		return this.selected;
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String getSupAff()
	{
		if(this.supAff == null)
		{
			this.supAff = "";
		}
		return this.supAff.trim().toUpperCase();
	}
	/**
	 * @param supAff
	 */
	public void setSupAff(String supAff)
	{
		this.supAff = supAff;
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("\nSup Aff: ");
		sb.append(this.getSupAff());
		sb.append("\nConv Aff: ");
		sb.append(this.getConvAff());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());

		return sb.toString();
	}
}