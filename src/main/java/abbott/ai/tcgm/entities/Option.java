package abbott.ai.tcgm.entities;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class Option implements Serializable
{
	private String label="";
	private String value="";
	/*****************************************************************************************/
	/**
	 *
	 */
	public Option()
	{
	}
	/**
	 *
	 * @param label
	 * @param value
	 */
	public Option(String label, String value)
	{
		this.label = label;
		this.value = value;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String getLabel()
	{
		if(this.label == null)
		{
			this.label = "";
		}
		return this.label;
	}
	/**
	 *
	 * @param label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	/**
	 *
	 * @return
	 */
	public String getValue()
	{
		if(this.value == null)
		{
			this.value = "";
		}
		return this.value;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param oos
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
	}
	/**
	 *
	 * @param ois
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Label: ");
		sb.append(this.getLabel());
		sb.append("\nValue: ");
		sb.append(this.getValue());

		return sb.toString();
	}
}