package abbott.ai.tcgm.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RptUser extends TCGMLog implements Serializable
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
	private String userid="";
	private String email="";
	private String abtNotesId;
	private String role="";
	private String empDivision="";
	private String employeeType="";
	private String roleName="";
	private String roleDesc="";
	private String recipient = "";
	private String rptuserinfoid="";
	private String affCode    ="";
	private String secCode    ="";
	private String areaCode    ="";
	private String areaDesc    ="";
	private String division    ="";

	private String code    ="";
	private String desc    ="";

	private HashMap affiliates;
	private HashMap sectors;
	private HashMap areas;
	private HashMap div;
	private Vector userlist = new Vector();
	
	private String affCodeList = "";
	private String secCodeList = "";
	private String areaCodeList = "";
	private String createDate = "";
	private String recertifyDate = "";

	/**
	 * Default Constructor
	 */
	public RptUser()
	{

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
		return this.firstName.trim();
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
		return this.lastName.trim();
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
		return this.userid.trim();
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
		sb.append("\nSelected: ");
		sb.append(this.getSelected());

		return sb.toString();
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
	public String getRecipient() {
		return recipient;
	}


	/**
	 * @param string
	 */
	public void setRecipient(String string) {
		recipient = string;
	}

	/**
	 * @return
	 */
	public String getRptuserinfoid() {
		return rptuserinfoid;
	}

	/**
	 * @param string
	 */
	public void setRptuserinfoid(String string) {
		rptuserinfoid = string;
	}

	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return
	 */
	public String getAffCode() {
		return affCode;
	}

	/**
	 * @return
	 */
	public String getSecCode() {
		return secCode;
	}

	/**
	 * @param string
	 */
	public void setAffCode(String string) {
		affCode = string;
	}

	/**
	 * @param string
	 */
	public void setSecCode(String string) {
		secCode = string;
	}

	/**
	 * @return
	 */
	public HashMap getSectors() {
		return sectors;
	}

	/**
	 * @param map
	 */
	public void setSectors(HashMap map) {
		sectors = map;
	}

	/**
	 * @return
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param string
	 */
	public void setRole(String string) {
		role = string;
	}

	/**
	 * @return
	 */
	public String getAreaCode() {
		return areaCode;
	}


	/**
	 * @param string
	 */
	public void setAreaCode(String string) {
		areaCode = string;
	}


	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param string
	 */
	public void setDesc(String string) {
		desc = string;
	}

	/**
	 * @return
	 */
	public HashMap getAreas() {
		return areas;
	}

	/**
	 * @param map
	 */
	public void setAreas(HashMap map) {
		areas = map;
	}

	/**
	 * @return
	 */
	public String getAreaDesc() {
		return areaDesc;
	}

	/**
	 * @param string
	 */
	public void setAreaDesc(String string) {
		areaDesc = string;
	}

	/**
	 * @return
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param string
	 */
	public void setDivision(String string) {
		division = string;
	}

	/**
	 * @return
	 */
	public String getAffCodeList() {
		return affCodeList;
	}

	/**
	 * @param string
	 */
	public void setAffCodeList(String string) {
		affCodeList = string;
	}

	/**
	 * @return
	 */
	public String getSecCodeList() {
		return secCodeList;
	}

	/**
	 * @param string
	 */
	public void setSecCodeList(String string) {
		secCodeList = string;
	}
	

	/**
	 * @return
	 */
	public HashMap getAffiliates() {
		return affiliates;
	}

	/**
	 * @param map
	 */
	public void setAffiliates(HashMap map) {
		affiliates = map;
	}

	/**
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param string
	 */
	public void setRoleName(String string) {
		roleName = string;
	}

	/**
	 * @return
	 */
	public String getAreaCodeList() {
		return areaCodeList;
	}

	/**
	 * @param string
	 */
	public void setAreaCodeList(String string) {
		areaCodeList = string;
	}

	/**
	 * @return
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param string
	 */
	public void setRoleDesc(String string) {
		roleDesc = string;
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
	/**
	 * @return Returns the empDivision.
	 */
	public String getEmpDivision() {
		return empDivision;
	}
	/**
	 * @param empDivision The empDivision to set.
	 */
	public void setEmpDivision(String empDivision) {
		this.empDivision = empDivision;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRecertifyDate() {
		return recertifyDate;
	}
	public void setRecertifyDate(String recertifyDate) {
		this.recertifyDate = recertifyDate;
	}
	public HashMap getDiv() {
		return div;
	}
	public void setDiv(HashMap div) {
		this.div = div;
	}
}