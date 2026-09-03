package abbott.ai.tcgm.entities;

import java.util.Date;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Brian A. Dennis
 * @version 1.0
 */
public class Stamp implements java.io.Serializable
{
	private String userName = "";
	private Date date = new Date();

	/**
	 * Default Constructor
	 */
	public Stamp()
	{

	}

	/**
	 *
	 * @param userName
	 * @param date
	 */
	public Stamp(String userName, Date date)
	{
		this.setUserName(userName);
		this.setDate(date);
	}

	/**
	 *
	 * @return
	 */
	public String getUserName()
	{
		if(this.userName == null)
		{
			this.userName = "";
		}
		return this.userName.trim().toUpperCase();
	}

	/**
	 *
	 * @param userName
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Date getDate()
	{
		return this.date;
	}
	/**
	 *
	 * @param date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Date: ");
		sb.append(this.getDate());
		sb.append("\nUser Name: ");
		sb.append(this.getUserName());

		return sb.toString();
	}
}