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
public class CurrencyCodeMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public CurrencyCodeMngr()
	{}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject CurrencyCode object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getCurrencyCode(UserToken userToken,CurrencyCode searchObject,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		CurrencyCodeDao currencyCodeDao = daoFactory.getCurrencyCodeDao(userToken,searchObject);
		return currencyCodeDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param sortObject contains sort criteria
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getCurrencyCode(UserToken userToken,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		CurrencyCodeDao currencyCodeDao = daoFactory.getCurrencyCodeDao(userToken,sortObject);
		return currencyCodeDao.getVO();
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject CurrencyCode
	 * @return CurrencyCode
	 * @throws TCGMException
	 */
	public CurrencyCode getCurrencyByCode(UserToken userToken,CurrencyCode searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		CurrencyCodeDao currencyCodeDao = daoFactory.getCurrencyCodeDao(userToken,searchObject);
		return currencyCodeDao.getCurrencyByCode();
	}

	/**
	 * @param userToken Contains the user id and password
	 * @param currencyCodeToEdit CurrencyCode object
	 * @throws TCGMException
	 */
	public void saveCurrencyCode(UserToken userToken,CurrencyCode currencyCodeToEdit) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		CurrencyCodeDao currencyCodeDao = daoFactory.getCurrencyCodeDao(userToken);

		if(currencyCodeToEdit.getNewCurrencyCode())
		{
			//creating a new record
			currencyCodeDao.insert(currencyCodeToEdit);
		}
		else
		{
			//updating an existing record.
			currencyCodeDao.update(currencyCodeToEdit);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param currencyList Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public void deleteCurrencys(UserToken userToken,Vector currencyList) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		CurrencyCodeDao currencyCodeDao = daoFactory.getCurrencyCodeDao(userToken);

		Vector selectedCurrencys = getSelectedCurrencyCode(currencyList);

		if(selectedCurrencys.size() > 0)
		{
			currencyCodeDao.delete(selectedCurrencys);
		}
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of CurrencyCode objects, returns the ones that have the selected flag set to true
	 * @param currencyList Vector
	 * @return Vector
	 */
	private Vector getSelectedCurrencyCode(Vector currencyList)
	{
		Vector selectedCurrencys = new Vector();

		for(int i = 0; i < currencyList.size(); i++)
		{
			CurrencyCode currencyCode = (CurrencyCode)currencyList.elementAt(i);

			if(currencyCode.isSelected())
			{
				selectedCurrencys.add(currencyCode);
			}
		}

		return selectedCurrencys;
	}
}