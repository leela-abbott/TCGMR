package abbott.ai.tcgm.data.oracle;

import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
//import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.process.JobDefinition;
//import java.io.File;


/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class OracleUnitDao extends OracleDao implements UnitDao
{

	/*****************************************************************************************/
	/**
	 * @return
	 * @throws TCGMException
	 */
	public Vector getAllUnitSets() throws TCGMException
	{
		String methodName = "getAllUnitSets()";
		String parameterList = "";

		DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(this.userToken);
		Dataset search = new Dataset(Dataset.DatasetType.UNIT_SET);
		return dd.getVO(search, true);
	}

	/*****************************************************************************************/
	/**
	 * @param unitId
	 * @throws TCGMException
	 */
	public void deleteUnitSet(String unitId) throws TCGMException
	{
		this.deleteUnitSet(Integer.parseInt(unitId));
	}
	/**
	 * @param unitId
	 * @throws TCGMException
	 */
	public void deleteUnitSet(int unitId) throws TCGMException
	{
		DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(this.userToken);
		dd.deleteDataset(unitId);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken
	 */
	public OracleUnitDao(UserToken userToken)
	{
		this.userToken = userToken;
		this.setEntityTable(DBConst.TABLE_DATASET);
	}
}
