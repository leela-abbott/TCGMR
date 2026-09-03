package abbott.ai.tcgm.helpers;

import java.util.*;

//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class PRMfgMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public PRMfgMngr()
	{	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject PRMfg object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getPRMfg(UserToken userToken,PRMfg searchObject,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		PRMfgDao prMfgDao = daoFactory.getPRMfgDao(userToken,searchObject);
		return prMfgDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param sortObject contains sort criteria
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getPRMfg(UserToken userToken,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		PRMfgDao prMfgDao = daoFactory.getPRMfgDao(userToken,sortObject);
		return prMfgDao.getVO();
	}

	/**
	 * @param userToken Contains the user id and password
	 * @param prMfgToEdit PRMfg object
	 * @throws TCGMException
	 */
	public void savePRMfg(UserToken userToken,PRMfg prMfgToEdit) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		PRMfgDao prMfgDao = daoFactory.getPRMfgDao(userToken);

		prMfgDao.insert(prMfgToEdit);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param prMfgList Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public void deletePRMfg(UserToken userToken,Vector prMfgList) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		PRMfgDao prMfgDao = daoFactory.getPRMfgDao(userToken);

		Vector selectedPRMfgs = getSelectedPRMfg(prMfgList);

		if(selectedPRMfgs.size() > 0)
		{
			prMfgDao.delete(selectedPRMfgs);
		}
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of PRMfg objects, returns the ones that have the selected flag set to true
	 * @param prMfgList Vector
	 * @return Vector
	 */
	private Vector getSelectedPRMfg(Vector prMfgList)
	{
		Vector selectedPRMfgs = new Vector();

		for(int i = 0; i < prMfgList.size(); i++)
		{
			PRMfg prMfg = (PRMfg)prMfgList.elementAt(i);

			if(prMfg.isSelected())
			{
				selectedPRMfgs.add(prMfg);
			}
		}

		return selectedPRMfgs;
	}
}