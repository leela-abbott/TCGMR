package abbott.ai.tcgm.entities;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */

public class Layer implements java.io.Serializable
{
	private String number="";
	private String count="";
	private String subNumber="";
	private String subCount="";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Layer()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getNumber()
	{
		if(this.number == null)
		{
			this.number = "";
		}
		return this.number;
	}
	/**
	 *
	 * @param number
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param count
	 */
	public void setCount(String count)
	{
		this.count = count;
	}
	/**
	 *
	 * @return
	 */
	public String getCount()
	{
		if(this.count == null)
		{
			this.count = "";
		}
		return this.count;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param subNumber
	 */
	public void setSubNumber(String subNumber)
	{
		this.subNumber = subNumber;
	}
	/**
	 *
	 * @return
	 */
	public String getSubNumber()
	{
		if(this.subNumber == null)
		{
			this.subNumber = "";
		}
		return this.subNumber;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param subCount
	 */
	public void setSubCount(String subCount)
	{
		this.subCount = subCount;
	}
	/**
	 *
	 * @return
	 */
	public String getSubCount()
	{
		if(this.subCount == null)
		{
			this.subCount = "";
		}
		return this.subCount;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Count: ");
		sb.append(this.getCount());
		sb.append("\nNumber: ");
		sb.append(this.getNumber());
		sb.append("\nSub Count: ");
		sb.append(this.getSubCount());
		sb.append("\nSub Number: ");
		sb.append(this.getSubNumber());

		return sb.toString();
	}
}