package abbott.ai.tcgm.entities;

import java.io.Serializable;

/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class TCGMSet extends TCGMLog implements Serializable
{
	private String setId="";
	private String name="";
	private String desc="";
	/*****************************************************************************************/
	public void setSetId(String setId)
	{
		this.setId = setId;
	}
	public String getSetId()
	{
		return this.setId;
	}
	/*****************************************************************************************/
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	/*****************************************************************************************/
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String getDesc()
	{
		return this.desc;
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
		sb.append("\nDesc: ");
		sb.append(this.getDesc());
		sb.append("\nName: ");
		sb.append(this.getName());
		sb.append("\nSet Id: ");
		sb.append(this.getSetId());

		return sb.toString();
	}
}