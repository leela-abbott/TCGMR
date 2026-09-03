package abbott.ai.tcgm.helpers;

import java.util.Vector;
//import java.io.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import org.apache.log4j.Logger;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */

public class CostExchDataMngr implements TCGMMngr
{
	private static Logger logger = Logger.getLogger("TCGM.Helpers.CostExchDataMngr");

	public CostExchDataMngr() {
		super();
	}

	public void fetchCostExchData(String setName) throws TCGMException, TCGMInvalidCostExchangeNameException
	{

//		String filename = setName + ".costexch";

		// 9-4-03 Edit check createName for MMYY format
		if(!(TCGMUtil.costExchangeNameIsValid(setName)))
		{
			throw new TCGMInvalidCostExchangeNameException(setName);
		}

		String myjcl = JCLComposer.buildCostExchFetch(setName);
		logger.debug(myjcl);

		RSystem.RGM.sendJCL(myjcl);
		
		/**
		 * Moved the code from Factor Analysis Models screen. For a single
		 * click on Fetch button in the Manage Margin Billed Exch Flex Data screen
		 * submits both JCL's CostExchFetch and FetchPackCodes
		 */ 
		RSystem.RGM.sendJCL(JCLComposer.buildPackCodesFetch());
	}

	public Vector getCostExchSets(UserToken ut) throws TCGMException {
		DatasetDao dsdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
		Dataset searchds = new Dataset();

		searchds.setTableName( DBConst.TABLE_COSTEXCH_DATA );
		return dsdao.getVO(searchds);
	}

}