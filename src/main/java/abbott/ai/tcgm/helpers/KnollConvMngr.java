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
public class KnollConvMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public KnollConvMngr()
	{}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject KnollConv object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getKnollConv(UserToken userToken,KnollConv searchObject,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		KnollConvDao knollConvDao = daoFactory.getKnollConvDao(userToken,searchObject);
		return knollConvDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param sortObject contains sort criteria
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getKnollConv(UserToken userToken,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		KnollConvDao knollConvDao = daoFactory.getKnollConvDao(userToken,sortObject);
		return knollConvDao.getVO();
	}

	/**
	 * @param userToken Contains the user id and password
	 * @param knollConvToEdit KnollConv object
	 * @throws TCGMException
	 */
	public void saveKnollConv(UserToken userToken,KnollConv knollConvToEdit) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		KnollConvDao knollConvDao = daoFactory.getKnollConvDao(userToken);

		knollConvDao.insert(knollConvToEdit);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param knollConvList Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public void deleteKnollConv(UserToken userToken,Vector knollConvList) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		KnollConvDao knollConvDao = daoFactory.getKnollConvDao(userToken);

		Vector selectedKnollConvs = getSelectedKnollConv(knollConvList);

		if(selectedKnollConvs.size() > 0)
		{
			knollConvDao.delete(selectedKnollConvs);
		}
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of KnollConv objects, returns the ones that have the selected flag set to true
	 * @param knollConvList Vector
	 * @return Vector
	 */
	private Vector getSelectedKnollConv(Vector knollConvList)
	{
		Vector selectedKnollConvs = new Vector();

		for(int i = 0; i < knollConvList.size(); i++)
		{
			KnollConv knollConv = (KnollConv)knollConvList.elementAt(i);

			if(knollConv.isSelected())
			{
				selectedKnollConvs.add(knollConv);
			}
		}

		return selectedKnollConvs;
	}
}