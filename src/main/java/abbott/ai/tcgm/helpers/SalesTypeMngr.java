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
public class SalesTypeMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public SalesTypeMngr()
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
	public Vector getSalesType(UserToken userToken,SalesType searchObject,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		SalesTypeDao salesTypeDao = daoFactory.getSalesTypeDao(userToken,searchObject);
		return salesTypeDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param sortObject contains sort criteria
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getSalesType(UserToken userToken,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		SalesTypeDao salesTypeDao = daoFactory.getSalesTypeDao(userToken,sortObject);
		return salesTypeDao.getVO();
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffCstCur
	 * @return AffCstCur
	 * @throws TCGMException
	 */
	public SalesType getSalesTypeBySlsType(UserToken userToken,SalesType searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		SalesTypeDao slsTypeDao = daoFactory.getSalesTypeDao(userToken,searchObject);
		return slsTypeDao.getSalesTypeBySlsType();
	}

	/**
	 * @param userToken Contains the user id and password
	 * @param affCstCurToEdit AffCstCur object
	 * @throws TCGMException
	 */
	public void saveSalesType(UserToken userToken, SalesType slsTypeToEdit) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		SalesTypeDao salesTypeDao = daoFactory.getSalesTypeDao(userToken);

		if(slsTypeToEdit.getNewSlsType())
		{
			//creating a new record
			salesTypeDao.insert(userToken,slsTypeToEdit);
		}
		else
		{
			//updating an existing record.
			salesTypeDao.update(userToken,slsTypeToEdit);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param affCstCurList Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public void deleteSalesType(UserToken userToken,Vector slsTypeList) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		SalesTypeDao salesTypeDao = daoFactory.getSalesTypeDao(userToken);

		Vector selectedSalesTypes = getSelectedSalesType(slsTypeList);

		if(selectedSalesTypes.size() > 0)
		{
			salesTypeDao.delete(selectedSalesTypes);
		}
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of SalesType objects, returns the ones that have the selected flag set to true
	 * @param slsTypeList Vector
	 * @return Vector
	 */
	private Vector getSelectedSalesType(Vector slsTypeList)
	{
		Vector selectedSalesTypes = new Vector();

		for(int i = 0; i < slsTypeList.size(); i++)
		{
			SalesType salesType = (SalesType)slsTypeList.elementAt(i);

			if(salesType.isSelected())
			{
				selectedSalesTypes.add(salesType);
			}
		}

		return selectedSalesTypes;
	}
}