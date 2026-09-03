package abbott.ai.tcgm.entities;

import java.io.Serializable;
import java.util.Vector;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class User extends TCGMLog implements Serializable
{
	/**
	 * This property is a display specific property.  It does not get written to or read from the database
	 * We may be able to move it later if we find a better solution.  It is here to allow us to easily
	 * know which rows have been selected on the Asr maintenance pages.  It was the least code intensive
	 * method to deal with this data
	 */
	private boolean selected = false;
	private String firstName="";
	private String lastName="";
	private String phone="";
	private String email="";
	private String userid="";
	private String password="";
	private String userinfoid="";
	private String abtNotesId="";
	private String division="";
	private String employeeType="";
	private String userRole="";
	
	private Role role = Role.Dummy;
	private boolean rptAccess = false;
	/*************************************************************
	*	Added by Uday on 02/04/2006 to provide the user(Analyst)
	*   the option to view the maintenance records of any user. Start
	**************************************************************/
	private Vector userlist = new Vector();	

	/**
	 * Default Constructor
	 */
	public User()
	{

	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userid String
	 * @param password String
	 */
	public User(String userid, String password)
	{
		this.setUserid(userid);
		this.setPassword(password);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return UserToken object
	 */
	public UserToken getUserToken()
	{
		return new UserToken(this.getUserid(), this.getPassword());
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return user's full name
	 */
	public String getFullName()
	{
		return this.getFirstName() + " " + this.getLastName();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String getFirstName()
	{
		if(this.firstName == null)
		{
			this.firstName = "";
		}
		return this.firstName.trim().toUpperCase();
	}
	/**
	 *
	 * @param firstName String
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param lastName String
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	/**
	 *
	 * @return String
	 */
	public String getLastName()
	{
		if(this.lastName == null)
		{
			this.lastName = "";
		}
		return this.lastName.toUpperCase().trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param phone String
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	/**
	 *
	 * @return String
	 */
	public String getPhone()
	{
		if(this.phone == null)
		{
			this.phone = "";
		}
		return this.phone.trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param email String
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	/**
	 *
	 * @return String
	 */
	public String getEmail()
	{
		if(this.email == null)
		{
			this.email = "";
		}
		return this.email.toUpperCase().trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userid String
	 */
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	/**
	 *
	 * @return String
	 */
	public String getUserid()
	{
		if(this.userid == null)
		{
			this.userid = "";
		}
		return this.userid.toUpperCase().trim();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String getPassword()
	{
		if(this.password == null)
		{
			this.password = "";
		}
		return this.password;
	}
	/**
	 *
	 * @param password User's password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param role Role object
	 */
	public void setRole(Role role)
	{
		this.role = role;
	}
	/**
	 *
	 * @return Role object
	 */
	public Role getRole()
	{
		if(this.role == null)
		{
			this.role = Role.Dummy;
		}
		return this.role;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return selected
	 */
	public boolean isSelected()
	{
		return this.selected;
	}
	/**
	 *
	 * @return selected
	 */
	public boolean getSelected()
	{
		return this.selected;
	}
	/**
	 *
	 * @param selected boolean
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String representation of all object data except for password
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("User Id:");
		sb.append(this.getUserid());
		sb.append("\n First Name:");
		sb.append(this.getFirstName());
		sb.append("\n Last Name:");
		sb.append(this.getLastName());
		sb.append("\n Role:");
		sb.append(this.getRole().getName());
		sb.append("\n Phone:");
		sb.append(this.getPhone());
		sb.append("\n Email:");
		sb.append(this.getEmail());
		sb.append("\nSelected: ");
		sb.append(this.getSelected());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String getUserinfoid()
	{
		if(this.userinfoid == null)
		{
			this.userinfoid = "";
		}
		return userinfoid.trim();
	}
	/**
	 *
	 * @param userInfoId String
	 */
	public void setUserinfoid(String userInfoId)
	{
		this.userinfoid = userInfoId;
	}
	/**
	 * @return Vector
	 */
	public Vector getUserlist() {
		return userlist;
	}

	/**
	 * @param userlist Vector
	 */
	public void setUserlist(Vector userlist) {
		this.userlist = userlist;
	}

	/**
	 * @return
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param string
	 */
	public void setUserRole(String string) {
		userRole = string;
	}

	/**
	 * @return
	 */
	public boolean isRptAccess() {
		return rptAccess;
	}

	/**
	 * @param b
	 */
	public void setRptAccess(boolean b) {
		rptAccess = b;
	}

	/**
	 * @return Returns the abtNotesId.
	 */
	public String getAbtNotesId() {
		return abtNotesId;
	}
	/**
	 * @param abtNotesId The abtNotesId to set.
	 */
	public void setAbtNotesId(String abtNotesId) {
		this.abtNotesId = abtNotesId;
	}
	/**
	 * @return Returns the division.
	 */
	public String getDivision() {
		return division;
	}
	/**
	 * @param division The division to set.
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	/**
	 * @return Returns the employeeType.
	 */
	public String getEmployeeType() {
		return employeeType;
	}
	/**
	 * @param employeeType The employeeType to set.
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
}