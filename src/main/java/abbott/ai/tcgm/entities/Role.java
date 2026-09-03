package abbott.ai.tcgm.entities;

import java.io.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: User Role Data Bean</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 * <p>
 * This class has a name value which corresponds to the name of the role
 * The class also has an access level value which tells how much authority the role will have in relation
 * to other roles.
 * A higher number has a higher authority level.
 * </p>
 */
public class Role
{
	private final String name;
	private final int accessLevel;

	public static final Role Operator = new Role("TCGM_OPERATOR",1);
	public static final Role Analyst = new Role("TCGM_ANALYST",2);
	public static final Role Administrator = new Role("TCGM_ADMINISTRATOR",3);
	public static final Role RptAdmin = new Role("TCGM_RPT ADMIN",5);
	public static final Role Dummy = new Role("TCGM_DUMMY",4);
	public static final Role Query = new Role("TCGM_QUERY",6);
	/*****************************************************************************************/
	/**
	 *
	 * @param name role name
	 */
	private Role(String name,int accessLevel)
	{
		this.name = name;
		this.accessLevel=accessLevel;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return role name
	 */
	public String getName()
	{
		return this.name.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return the access level of the role.  Higher levels have a higher authority
	 */
	public int getAccessLevel()
	{
		return this.accessLevel;
	}
	/**
	 * Helper method for jsp pages.
	 * @return
	 */
	public String getAccessLevelString()
	{
		return Integer.toString(this.accessLevel);
	}
	/*****************************************************************************************/
	/**
	 * Checks the value of the passed in role against the existing roles for a match
	 * and if found returns the object.  If not found, it returns a null
	 * @param roleName Name of the role object to get
	 * @return Role object
	 */
	public static Role getRole(String name)
	{
		Role role = null;
		String upperName = name.trim().toUpperCase();

		if(Operator.getName().equals(upperName))
		{
			role = Operator;
		}
		else if(Analyst.getName().equals(upperName))
		{
			role = Analyst;
		}
		else if(Administrator.getName().equals(upperName))
		{
			role = Administrator;
		}
		else if(RptAdmin.getName().equals(upperName))
		{
			role = RptAdmin;
		}
		else if(Query.getName().equals(upperName))
		{
			role = Query;
		}
		return role;
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
}