package abbott.ai.tcgm.helpers;

import java.util.Vector;
//import java.io.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.entities.TCGMModel;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.entities.*;
import org.apache.log4j.Logger;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */

public class UnitMngr implements TCGMMngr
{
	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.helpers.UnitMngr");

	public UnitMngr() {
		super();
	}

	public void fetchUnits(String unitYear, String unitType, String genDataGroup, UserToken ut) throws TCGMException {

		String cyclename =  unitType.trim() + unitYear.trim();
		String filename = cyclename + ".UNITS";
		
		RSystem r = RSystem.getObjFromCycle(cyclename.substring(3,4)); // Extract first 4 char only
		
		String myjcl = JCLComposer.buildUnitFetchJCL(cyclename, r, genDataGroup);
		System.out.println("Start Printing JCL");
		System.out.println(myjcl);
		System.out.println("End Printing JCL");
		logger.debug(myjcl);

		r.sendJCL(myjcl);
	}

	public Vector getUnitSets(UserToken ut) throws TCGMException {
		UnitDao ud = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getUnitDao(ut);
		Vector v = ud.getAllUnitSets();
		return v;
	}

	public void deleteUnitSet(String unitId, UserToken ut) throws TCGMException {
		UnitDao ud = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getUnitDao(ut);
		ud.deleteUnitSet(unitId);
	}
}