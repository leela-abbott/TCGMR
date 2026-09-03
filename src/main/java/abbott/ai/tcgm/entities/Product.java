package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class Product implements java.io.Serializable
{
	private String _list = "";
	private String _label = "";
	private String _size = "";
	private String _pack = "";
	private String _invCode = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Product()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getList()
	{
		if(this._list == null)
		{
			this._list = "";
		}
		return this._list.trim().toUpperCase();
	}
	/**
	 *
	 * @param list
	 */
	public void setList(String list)
	{
		this._list = list;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param label
	 */
	public void setLabel(String label)
	{
		this._label = label;
	}
	/**
	 *
	 * @return
	 */
	public String getLabel()
	{
		if(this._label == null)
		{
			this._label = "";
		}
		return this._label.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param size
	 */
	public void setSize(String size)
	{
		this._size = size;
	}
	/**
	 *
	 * @return
	 */
	public String getSize()
	{
		if(this._size == null)
		{
			this._size = "";
		}
		return this._size.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param pack
	 */
	public void setPack(String pack)
	{
		this._pack = pack;
	}
	/**
	 *
	 * @return
	 */
	public String getPack()
	{
		if(this._pack == null)
		{
			this._pack = "";
		}
		return this._pack.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param invCode
	 */
	public void setInvCode(String invCode)
	{
		this._invCode = invCode;
	}
	/**
	 *
	 * @return invCode
	 */
	public String getInvCode()
	{
		if(this._invCode == null)
		{
			this._invCode = "";
		}
		return this._invCode.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return fullId
	 */
	public String getFullId()
	{
		return this.getInvCode() + " " + this.getList() + " " + this.getLabel() + " " + this.getSize() + " " + this.getPack();
	}
	/*****************************************************************************************/
	/**
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("List: ");
		sb.append(this.getList());
		sb.append("\nLabel: ");
		sb.append(this.getLabel());
		sb.append("\nSize: ");
		sb.append(this.getSize());
		sb.append("\nPack: ");
		sb.append(this.getPack());
		sb.append("\nInvCode: ");
		sb.append(this.getInvCode());

		return sb.toString();
	}
}