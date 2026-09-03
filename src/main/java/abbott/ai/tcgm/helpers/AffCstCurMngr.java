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
public class AffCstCurMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public AffCstCurMngr()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffCstCur object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getAffCstCur(UserToken userToken,AffCstCur searchObject,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffCstCurDao affCstCurDao = daoFactory.getAffCstCurDao(userToken,searchObject);
		return affCstCurDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param sortObject contains sort criteria
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getAffCstCur(UserToken userToken,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffCstCurDao affCstCurDao = daoFactory.getAffCstCurDao(userToken,sortObject);
		return affCstCurDao.getVO();
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffCstCur
	 * @return AffCstCur
	 * @throws TCGMException
	 */
	public AffCstCur getAffCstCurByAff(UserToken userToken,AffCstCur searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffCstCurDao affCstCurDao = daoFactory.getAffCstCurDao(userToken,searchObject);
		return affCstCurDao.getAffCstCurByAff();
	}

	/**
	 * @param userToken Contains the user id and password
	 * @param affCstCurToEdit AffCstCur object
	 * @throws TCGMException
	 */
	public void saveAffCstCur(UserToken userToken,AffCstCur affCstCurToEdit) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffCstCurDao affCstCurDao = daoFactory.getAffCstCurDao(userToken);

		if(affCstCurToEdit.getNewAffCstCur())
		{
			//creating a new record
			affCstCurDao.insert(userToken,affCstCurToEdit);
		}
		else
		{
			//updating an existing record.
			affCstCurDao.update(userToken,affCstCurToEdit);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param affCstCurList Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public void deleteAffCstCurs(UserToken userToken,Vector affCstCurList) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffCstCurDao affCstCurDao = daoFactory.getAffCstCurDao(userToken);

		Vector selectedAffCstCurs = getSelectedAffCstCur(affCstCurList);

		if(selectedAffCstCurs.size() > 0)
		{
			affCstCurDao.delete(selectedAffCstCurs);
		}
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of AffCstCur objects, returns the ones that have the selected flag set to true
	 * @param affCstCurList Vector
	 * @return Vector
	 */
	private Vector getSelectedAffCstCur(Vector affCstCurList)
	{
		Vector selectedAffCstCurs = new Vector();

		for(int i = 0; i < affCstCurList.size(); i++)
		{
			AffCstCur affCstCur = (AffCstCur)affCstCurList.elementAt(i);

			if(affCstCur.isSelected())
			{
				selectedAffCstCurs.add(affCstCur);
			}
		}

		return selectedAffCstCurs;
	}
}