package abbott.ai.tcgm.entities;

import java.io.Serializable;

public class ActiveDirSearchDtlBean extends TCGMEntity implements Serializable 
{

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String employeeType;
	private boolean blnSelected;
	private String userPrincipalName;
	private String abtNotesId;
	private String division;
	
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
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return Returns the blnSelected.
	 */
	public boolean isBlnSelected() {
		return blnSelected;
	}
	/**
	 * @param blnSelected The blnSelected to set.
	 */
	public void setBlnSelected(boolean blnSelected) {
		this.blnSelected = blnSelected;
	}	
	/**
	 * @return
	 */
	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	/**
	 * @param string
	 */
	public void setUserPrincipalName(String string) {
		userPrincipalName = string;
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
}
