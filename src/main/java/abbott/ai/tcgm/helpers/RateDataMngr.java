package abbott.ai.tcgm.helpers;

import java.util.*;
import java.sql.*;
import javax.sql.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.data.oracle.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateDataMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();

	/**
	 * Default COnstructor
	 */
	public RateDataMngr()
	{	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateData object with search criteria
	 * @return Vector of RateData objects
	 * @throws TCGMException
	 */
	public Vector getRateData(UserToken userToken,RateData searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataDao rateDataDao = daoFactory.getRateDataDao(userToken,searchObject);
		return rateDataDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateData object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of RateData objects
	 * @throws TCGMException
	 */
	public Vector getRateData(UserToken userToken,RateData searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataDao rateDataDao = daoFactory.getRateDataDao(userToken,searchObject,pagingFilter);
		return rateDataDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateData object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of RateData objects
	 * @throws TCGMException
	 */
	public Vector getRateData(UserToken userToken,RateData searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataDao rateDataDao = daoFactory.getRateDataDao(userToken,searchObject,pagingFilter,sortObject);
		return rateDataDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of RateData objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each RateData to an RateDataTran
	 * 3.  Call the rateDataTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject RateData object
	 * @param actionCode action code for rateData tran
	 * @throws TCGMException
	 */
	public boolean addAllRateDataToTrans(UserToken userToken,RateData searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllRateDataToTrans";
		boolean res = true;	
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataDao rateDataDao = daoFactory.getRateDataDao(userToken,searchObject);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = rateDataDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				RateData rateData = rateDataDao.getRateDataFromCurrentRow(rs);
				RateDataTran rateDataTran = this.convertRateDataToTran(rateData,actionCode);
				res = rateDataTranDao.insert(rateDataTran,conn);
				if(res == false)
				  break;
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			throw new TCGMException(className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
			SQLUtil.closeConnection(conn);
		}
		return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param rateDataList Vector of RateData objects
	 * @param actionCode action code for RateDataTran
	 * @throws TCGMException
	 */
	public boolean addSelectedRateDataToTrans(UserToken userToken,Vector rateDataList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedRateDataToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		Vector rateDataTranList = null;
		Vector selectedRateDataList = this.getSelectedRateData(rateDataList);

		rateDataTranList = convertRateDataListToTran(selectedRateDataList,actionCode);
		boolean res = rateDataTranDao.insert(rateDataTranList);
		return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rateDataList Vector of RateData objects
	 * @param actionCode action code for RateDataTran
	 * @return Vector
	 */
	public Vector convertRateDataListToTran(Vector rateDataList,String actionCode)
	{
		String methodName = "convertRateDataListToTran(Vector,String)";

		Vector rateDataTranList = new Vector();

		for(int i = 0; i < rateDataList.size(); i++)
		{
			rateDataTranList.add(convertRateDataToTran((RateData)rateDataList.elementAt(i),actionCode) );
		}
		return rateDataTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rateData RateData object
	 * @param actionCode action code for RateDataTran
	 * @return RateDataTran
	 */
	public RateDataTran convertRateDataToTran(RateData rateData,String actionCode)
	{
		String methodName = "convertRateDataToTran(RateData,String)";

		RateDataTran rateDataTran = new RateDataTran();
		rateDataTran.setRateData( rateData );
		rateDataTran.setRate(rateData.getRate());
		rateDataTran.setBegPeriod(rateData.getBegPeriod());
		rateDataTran.setEndPeriod(rateData.getEndPeriod());
		rateDataTran.setActionCode(actionCode);
		// 4-21-03 This is problem tis line right here. I already have the right dataset ID
		//         but here I am resetting back to the default.
		//rateDataTran.getRateData().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);

		// 4-21-03 The statement below is what I was using in my actions to get the datasetID
		//rateDataForm.getAddNew().getRateData().setDatasetTableIdInt(state.getCurRateSetTableId());

		//rateDataTran.getRateData().setDatasetTableId(state.getCurRateSetTableId());

		rateDataTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return rateDataTran;
	}
	/*****************************************************************************************/
	/**
	 * @param userToken contains user id and password
	 * @return int id of pending rate data transactions
	 * @throws
	 *
	 * Per integration issue disovered 4/17, only one set of rate transactions can exist at any one time.
	 * The datasettableid of the rateset with pending transactions is returned
	 * by this method. This method simply pulls any transactions and returns that id of the 1st
	 * transaction. It assumes validation has occurred to prevent multiple transaction sets from
	 * existing.
	 *
	 * In affect dataset table id is no longer used in this process. Alternate cleaner solutions
	 * would include:
	 * 1. Alter the Stored Procedure in APPLY_MAINTENANCE to process rate transactions per the
	 * dataset table id in each row. Remove the validation that limits only one set of pending
	 * transactions.
	 *
	 */

	public int getPendingRateDataTranId(UserToken userToken) throws TCGMException {

		Vector v = this.getRateDataTran(userToken, new RateDataTran() );
		if (v.size() > 0)
			return ( (RateDataTran) v.firstElement()).getRateData().getDatasetTableIdInt();
		else
			return -1;
	}


	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getRateDataTran(UserToken userToken,RateDataTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken,searchObject);
		return rateDataTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getRateDataTran(UserToken userToken,RateDataTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		String methodName = "getRateDataTran(UserToken,RateDataTran,PagingFilter)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken,searchObject,pagingFilter);
		return rateDataTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getRateDataTran(UserToken userToken,RateDataTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		String methodName = "getRateDataTran(UserToken,RateDataTran,PagingFilter,Sort)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken,searchObject,pagingFilter,sortObject);

		return rateDataTranDao.getVO();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateData object with search criteria
	 * @return Vector of RateData objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,RateData searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataDao rateDataDao = daoFactory.getRateDataDao(userToken,searchObject);
		return rateDataDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,RateDataTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken,searchObject);
		return rateDataTranDao.getCount();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject RateDataTran
	 * @throws TCGMException
	 */
	public void deleteAllRateDataTran(UserToken userToken,RateDataTran searchObject) throws TCGMException,
																							TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllRateDataTran(UserToken,RateDataTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		rateDataTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param rateDataTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedRateDataTran(UserToken userToken,Vector rateDataTranList) throws TCGMException
	{
		String methodName = "deleteSelectedRateDataTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		Vector selectedRateDataTranList = this.getSelectedRateDataTran(rateDataTranList);

		rateDataTranDao.delete(selectedRateDataTranList);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateDataTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedRateDataTran(UserToken userToken,Vector rateDataTranList) throws TCGMException
	{
		String methodName = "saveRateDataTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		Vector selectedRateDataTranList = this.getSelectedRateDataTran(rateDataTranList);

		rateDataTranDao.update(selectedRateDataTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateDataTranList Vector
	 * @param copyToModel String
	 * @throws TCGMException
	 */
// 4-21-03 Copy function is not applicable with the Rate Data maintenance
//	public void copySelectedRateDataTran(UserToken userToken,Vector rateDataTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
//	{
//		String methodName = "copySelectedRateDataTran(UserToken,Vector)";
//
//		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
//		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);
//
//		Vector selectedRateDataTranList = this.getSelectedRateDataTran(rateDataTranList);
//
//		rateDataTranDao.copy(selectedRateDataTranList,copyToModel);
//	}
	/**
	 * @param userToken UserToken
	 * @param searchObject RateDataTran
	 * @param copyToModel String
	 * @throws TCGMException
	 */
// 4-21-03 Copy function is not applicable with the Rate Data maintenance
//	public void copyAllRateDataTran(UserToken userToken,RateDataTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
//	{
//		String methodName = "copyAllRateDataTran(UserToken,Vector)";
//
//		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
//		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);
//
//		rateDataTranDao.copy(searchObject,copyToModel);
//	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateDataTranList Vector
	 * @throws TCGMException
	 */
	public void publishSelectedRateDataTran(UserToken userToken,Vector rateDataTranList) throws TCGMException
	{
		String methodName = "publishRateDataTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		Vector selectedRateDataTranList = this.getSelectedRateDataTran(rateDataTranList);

		for(int i = 0; i<selectedRateDataTranList.size(); i++)
		{
			((RateDataTran)selectedRateDataTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
		}

		rateDataTranDao.update(selectedRateDataTranList);
	}

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @throws TCGMException
	 */
	public void publishAllRateDataTran(UserToken userToken,RateDataTran searchObject) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAllRateDataTran(UserToken,RateDataTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken,searchObject);

		rateDataTranDao.publishAll(searchObject);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateDataTran RateDataTran
	 * @throws TCGMException
	 */
	public void addNewRateDataTran(UserToken userToken,RateDataTran rateDataTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewRateDataTran(UserToken,RateDataTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);

		rateDataTranDao.insert(rateDataTran,null);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject RateDataTran
	 * @param newVals RateDataTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,RateDataTran searchObject,RateDataTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,RateDataTran,RateDataTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);
		rateDataTranDao.massUpdate(searchObject,newVals);
	}
	/**
	 * Used to do a mass update by creating trans records from existing rateData records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of RateData
	 * 2.  Create an rateDataTran and put the rateData from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,RateData searchObject,RateDataTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,RateData,RateDataTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateDataDao rateDataDao = daoFactory.getRateDataDao(userToken,searchObject);
		RateDataTranDao rateDataTranDao = daoFactory.getRateDataTranDao(userToken);
		RowSet rs = rateDataDao.getRS();

		try
		{
			Connection conn = SQLUtil.openConnection();

			while(rs.next())
			{
				RateDataTran rateDataTran = new RateDataTran();
				rateDataTran.setRateData(rateDataDao.getRateDataFromCurrentRow(rs));
				rateDataTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					rateDataTran.setActionCode(TCGMUtil.getNewValue(rateDataTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
					rateDataTran.setActionCode(TCGMUtil.getNewValue(rateDataTran.getActionCode(),newVals.getActionCode()));
				}

				rateDataTran.setPublishFlag(TCGMUtil.getNewValue(rateDataTran.getPublishFlag(),newVals.getPublishFlag()));

//				rateDataTran.getRateData().setCurCode(TCGMUtil.getNewValue(rateDataTran.getRateData().getCurCode(),newVals.getRateData().getCurCode()));
//				rateDataTran.getRateData().setBegPeriod(TCGMUtil.getNewValue(rateDataTran.getRateData().getBegPeriod(),newVals.getRateData().getBegPeriod()));
//				rateDataTran.getRateData().setEndPeriod(TCGMUtil.getNewValue(rateDataTran.getRateData().getEndPeriod(),newVals.getRateData().getEndPeriod()));
//				rateDataTran.getRateData().setRate(TCGMUtil.getNewValue(rateDataTran.getRateData().getRate(),newVals.getRateData().getRate()));
//
//				rateDataTranDao.insert(rateDataTran,conn);

				// Set the currency codes once per each search object record traversed otherwise the value for each existing record could
				// get changed below in the for loops when the "set" methods are called
				String curCode = rateDataTran.getRateData().getCurCode();

				// 11-11-03 Setup a flag so I can know when the user entered a new price or a new cost
				boolean newRate = false;  // User entered a new bill price
				boolean firstTime = true;      // Flag is used to write price & cost new values only once per loop
				if ( !(newVals.getRateData().getCurCode().equals(null))  &&
					 !(newVals.getRateData().getCurCode().equals(""))    &&
					 !(newVals.getRateData().getCurCode().equals(" ")) )
				{
					newRate = true;
				}
				boolean changeInPrices = false;
				if (newRate)
				{
					changeInPrices = true;
				}
				int i = 0;
				for(i = 1; i <= TCGMConstants.MAX_PERIODS-1; i++)
				{
					// Duplicate the existing period price and cost values for the range not specified by the user
					if ((i < Integer.parseInt(newVals.getRateData().getBegPeriod()))  ||
						(i > Integer.parseInt(newVals.getRateData().getEndPeriod()))  ||
						(!firstTime)                                               ||
						(!changeInPrices))  // 11-25-03 Added ts changeInPirce condition to allow ts same loop to process non-price changes because
					{                       // multiple records needed to be written out for non-price changes as well.
						int saveBegPeriod = 0;
						int saveEndPeriod = 0;
						String saveRate = "";
						String rate = "";
						boolean firstTimeWritingPrices = true;
						saveBegPeriod = i;
						while(  (  (i < Integer.parseInt(newVals.getRateData().getBegPeriod())) ||
								   (i > Integer.parseInt(newVals.getRateData().getEndPeriod())) &&
								   (i <= TCGMConstants.MAX_PERIODS - 1)  )                   ||
								(  (!changeInPrices)                                         &&  // 11-25-03 Added ts 2nd "&&" condition
								   (i <= TCGMConstants.MAX_PERIODS - 1)  )  )                    // to fix the non-price mass update changes
						{
							rate = rateDataTran.getRateData().getRates(i-1).getPeriod();

							if(saveRate.equals(rate) ||	firstTimeWritingPrices )
							{
								saveRate = rate;
								saveEndPeriod = i;
							}
							else
							{
								rateDataTran.getRateData().setBegPeriod(Integer.toString(saveBegPeriod));
								rateDataTran.getRateData().setEndPeriod(Integer.toString(saveEndPeriod));
								rateDataTran.getRateData().setRate(saveRate);
								rateDataTran.getRateData().setCurCode(curCode);
								rateDataTranDao.insert(rateDataTran,conn);
								saveRate = rate;
								saveBegPeriod = i;
								saveEndPeriod = i;
							}
							i++;
							firstTimeWritingPrices = false;
						}
						rateDataTran.getRateData().setBegPeriod(Integer.toString(saveBegPeriod));
						rateDataTran.getRateData().setEndPeriod(Integer.toString(saveEndPeriod));
						rateDataTran.getRateData().setRate(rate);
						rateDataTran.getRateData().setCurCode(curCode);
						rateDataTranDao.insert(rateDataTran,conn);
						i--; // Reposition main loop loop counter for return to outer for loop
					}
					// 12-2-03 Note: Since there is only one rate to change, I don't need to have a counter to cycle thru the
					//               periods that are changing. If the user wanted to change the periods, I only have to write
					//               one record with the given beg/end periods that the user specified
					else if ( firstTime && newRate == true) // Both Price & Cost were changed
					{
						rateDataTran.getRateData().setBegPeriod(TCGMUtil.getNewValue(rateDataTran.getRateData().getBegPeriod(),newVals.getRateData().getBegPeriod()));
						rateDataTran.getRateData().setEndPeriod(TCGMUtil.getNewValue(rateDataTran.getRateData().getEndPeriod(),newVals.getRateData().getEndPeriod()));
						rateDataTran.getRateData().setRate(TCGMUtil.getNewValue(rateDataTran.getRateData().getRate(),newVals.getRateData().getRate()));
						rateDataTran.getRateData().setCurCode(TCGMUtil.getNewValue(rateDataTran.getRateData().getCurCode(),newVals.getRateData().getCurCode()));
						rateDataTranDao.insert(rateDataTran,conn);
						i = Integer.parseInt(newVals.getRateData().getEndPeriod());  // advance main loop counter

						// After I write the new price & cost values, turn off firstTime flag until loop starts over again;
						firstTime = false;
					}
				}
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(className,methodName,sqle.toString());
		}
	}

	/*****************************************************************************************/
	/**
	 * Given a vector of RateDataTran, returns the ones that have the selected flag set to true
	 * @param rateDataTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedRateDataTran(Vector rateDataTranList)
	{
		Vector selectedRateDataTran = new Vector();

		for(int i = 0; i < rateDataTranList.size(); i++)
		{
			RateDataTran rateDataTran = (RateDataTran)rateDataTranList.elementAt(i);

			if(rateDataTran.getRateData().isSelected())
			{
				selectedRateDataTran.add(rateDataTran);
			}
		}

		return selectedRateDataTran;
	}
	/**
	 * Given a vector of RateData, returns the ones that have the selected flag set to true
	 * @param rateDataList Vector
	 * @return Vector
	 */
	public Vector getSelectedRateData(Vector rateDataList)
	{
		Vector selectedRateData = new Vector();

		for(int i = 0; i < rateDataList.size(); i++)
		{
			RateData rateData = (RateData)rateDataList.elementAt(i);

			if(rateData.isSelected())
			{
				selectedRateData.add(rateData);
			}
		}

		return selectedRateData;
	}
}