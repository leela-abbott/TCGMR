package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.exception.*;
//import java.io.*;
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
public class Cycle
{
	private final String name;
	private final String longName;

	public static final Cycle ACT = new Cycle("ACT","Actual");
	public static final Cycle PLN = new Cycle("PLN", "Plan");
	public static final Cycle UPD = new Cycle("UPD","Updated Plan");
	public static final Cycle SIM = new Cycle("SIM","Simulation");
	public static final Cycle INV1 = new Cycle("INV1","April Inventory");
	public static final Cycle INV2 = new Cycle("INV2", "Sept Inventory");
	public static final Cycle INV3 = new Cycle("INV3","Nov Inventory");
	public static final Cycle UNSPECIFIED = new Cycle("UNSPECIFIED","Unspecified");

	private Cycle(String name, String longName)
	{
		this.name = name;
		this.longName= longName;
	}


	public static Cycle getObjectFromName(String name) throws TCGMException
	{
		final String cls = "abbott.ai.tcgm.entities.Cycle";
		final String methodName = "getObjectFromName(String)";
		Cycle j = null;
		try {
			j = (Cycle) Class.forName(cls).getDeclaredField( name.toUpperCase() ).get(j);
		}
		catch (Exception e)
		{
			throw new TCGMException( "Cycle", methodName, name, e.toString() );
		}
		return j;
	}


	public String getName()
	{
		return this.name.trim().toUpperCase();
	}

	public String getLongName()
	{
		return this.longName;
	}
}
