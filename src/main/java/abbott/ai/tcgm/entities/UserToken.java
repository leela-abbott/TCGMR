package abbott.ai.tcgm.entities;

import java.io.Serializable;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 * <p>Class used to hold the user id and password tokens that will be passed into the DAOs</p>
 */

// 9-10-03 Added "implements Serializable" because it appeared that we were losing the user id somehow
//         This could possibly be related to the mass delete and mass publishing problems.
//public class UserToken
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