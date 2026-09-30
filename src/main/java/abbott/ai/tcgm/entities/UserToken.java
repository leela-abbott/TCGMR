package abbott.ai.tcgm.entities;

import java.io.Serializable;

public class UserToken implements Serializable
{
	private String password="";
	private String userid="";

	/**
	 *
	 * @param pUserId
	 * @param pPassword
	 */
	public UserToken(String pUserId, String pPassword)
	{
		this.userid = pUserId;
		this.password = pPassword;
	}

	/**
	 *
	 * @return
	 */
	public String getPassword()
	{
		return this.password;
	}

	/**
	 *
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 *
	 * @param userid
	 */
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	/**
	 *
	 * @return
	 */
	public String getUserid()
	{
		return this.userid;
	}

	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("User Id: ");
		sb.append(this.getUserid());

		return sb.toString();
	}
}