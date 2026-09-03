package abbott.ai.tcgm.helpers;

import java.util.*;
import java.sql.*;
import javax.sql.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.data.oracle.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateExMngr implements TCGMMngr
{
	public String className = null;

	/**
	 * Default COnstructor
	 */
	public RateExMngr()
	{
		className = this.className;
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateEx object with search criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getRateEx(UserToken userToken,RateEx searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExDao rateExDao = daoFactory.getRateExDao(userToken,searchObject);
		return rateExDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateEx object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getRateEx(UserToken userToken,RateEx searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExDao rateExDao = daoFactory.getRateExDao(userToken,searchObject,pagingFilter);
		return rateExDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateEx object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getRateEx(UserToken userToken,RateEx searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExDao rateExDao = daoFactory.getRateExDao(userToken,searchObject,pagingFilter,sortObject);
		return rateExDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of RateEx objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each RateEx to an RateExTran
	 * 3.  Call the rateExTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject RateEx object
	 * @param actionCode action code for rateEx tran
	 * @throws TCGMException
	 */
//	A.Winter 7/18/05 - get method boolean	
	public boolean addAllRateExToTrans(UserToken userToken,RateEx searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllRateExToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExDao rateExDao = daoFactory.getRateExDao(userToken,searchObject);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);
// A.Winter 7/18/05 
        boolean res = true;
        
		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = rateExDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				RateEx rateEx = rateExDao.getRateExFromCurrentRow(rs);
				RateExTran rateExTran = this.convertRateExToTran(rateEx,actionCode);
				res = rateExTranDao.insert(rateExTran,conn);
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
	 * @param rateExList Vector of RateEx objects
	 * @param actionCode action code for RateExTran
	 * @throws TCGMException
	 */
//	A.Winter 7/18/05 - made method boolean	
	public boolean addSelectedRateExToTrans(UserToken userToken,Vector rateExList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedRateExToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		Vector rateExTranList = null;
		Vector selectedRateExList = this.getSelectedRateEx(rateExList);

		rateExTranList = convertRateExListToTran(selectedRateExList,actionCode);

//		A.Winter - made result as boolean
		boolean res = rateExTranDao.insert(rateExTranList);
		return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rateExList Vector of RateEx objects
	 * @param actionCode action code for RateExTran
	 * @return Vector
	 */
	public Vector convertRateExListToTran(Vector rateExList,String actionCode)
	{
		String methodName = "convertRateExListToTran(Vector,String)";

		Vector rateExTranList = new Vector();

		for(int i = 0; i < rateExList.size(); i++)
		{
			rateExTranList.add(convertRateExToTran((RateEx)rateExList.elementAt(i),actionCode) );
		}
		return rateExTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param rateEx RateEx object
	 * @param actionCode action code for RateExTran
	 * @return RateExTran
	 */
	public RateExTran convertRateExToTran(RateEx rateEx,String actionCode)
	{
		String methodName = "convertRateExToTran(RateEx,String)";

		RateExTran rateExTran = new RateExTran();
		rateExTran.setRateEx( rateEx );
		rateExTran.setActionCode(actionCode);
		rateExTran.getRateEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		rateExTran.setBegPeriod(rateEx.getBegPeriod());
		rateExTran.setEndPeriod(rateEx.getEndPeriod());
		rateExTran.setBpfRate(rateEx.getBpfRate());
		rateExTran.setBppRate(rateEx.getBppRate());
		rateExTran.setCostfRate(rateEx.getCostfRate());
		rateExTran.setCostpRate(rateEx.getCostpRate());
		rateExTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return rateExTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getRateExTran(UserToken userToken,RateExTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken,searchObject);
		return rateExTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getRateExTran(UserToken userToken,RateExTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken,searchObject,pagingFilter);
		return rateExTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getRateExTran(UserToken userToken,RateExTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken,searchObject,pagingFilter,sortObject);
		return rateExTranDao.getVO();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateEx object with search criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,RateEx searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExDao rateExDao = daoFactory.getRateExDao(userToken,searchObject);
		return rateExDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,RateExTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken,searchObject);
		return rateExTranDao.getCount();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject RateExTran
	 * @throws TCGMException
	 */
	public void deleteAllRateExTran(UserToken userToken,RateExTran searchObject) throws TCGMException,
																						TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllRateExTran(UserToken,RateExTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		rateExTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param rateExTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedRateExTran(UserToken userToken,Vector rateExTranList) throws TCGMException
	{
		String methodName = "deleteSelectedRateExTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		Vector selectedRateExTranList = this.getSelectedRateExTran(rateExTranList);

		rateExTranDao.delete(selectedRateExTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateExTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedRateExTran(UserToken userToken,Vector rateExTranList) throws TCGMException
	{
		String methodName = "saveRateExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		Vector selectedRateExTranList = this.getSelectedRateExTran(rateExTranList);

		rateExTranDao.update(selectedRateExTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateExTranList Vector
	 * @throws TCGMException
	 */
	public void publishSelectedRateExTran(UserToken userToken,Vector rateExTranList) throws TCGMException
	{
		String methodName = "publishRateExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		Vector selectedRateExTranList = this.getSelectedRateExTran(rateExTranList);

		for(int i = 0; i<selectedRateExTranList.size(); i++)
		{
			((RateExTran)selectedRateExTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
		}

		rateExTranDao.update(selectedRateExTranList);
	}

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @throws TCGMException
	 */
	public void publishAllRateExTran(UserToken userToken,RateExTran searchObject) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodNaem = "publishAllRateExTran(UserToken,RateExTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken,searchObject);

		rateExTranDao.publishAll(searchObject);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param rateExTran RateExTran
	 * @throws TCGMException
	 */
	public boolean addNewRateExTran(UserToken userToken,RateExTran rateExTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewRateExTran(UserToken,RateExTran)";
		// Date Modified: 12/14/2005
		// Modified By  : Udaya B Aravapalli. 
		boolean dup = true; //Based on the existing code dup =true means there are no duplicates. :) 		

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		dup = rateExTranDao.insert(rateExTran,null);
		return dup;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject RateExTran
	 * @param newVals RateExTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,RateExTran searchObject,RateExTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,RateExTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);
		rateExTranDao.massUpdate(searchObject,newVals);
	}
	/**
	 * Used to do a mass update by creating trans records from existing rateEx records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of RateEx
	 * 2.  Create an rateExTran and put the rateEx from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,RateEx searchObject,RateExTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,RateEx,RateExTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExDao rateExDao = daoFactory.getRateExDao(userToken,searchObject);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);
		RowSet rs = rateExDao.getRS();

		try
		{
			Connection conn = SQLUtil.openConnection();

			while(rs.next())
			{
				RateExTran rateExTran = new RateExTran();
				rateExTran.setRateEx(rateExDao.getRateExFromCurrentRow(rs));
				rateExTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					rateExTran.setActionCode(TCGMUtil.getNewValue(rateExTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
					rateExTran.setActionCode(TCGMUtil.getNewValue(rateExTran.getActionCode(),newVals.getActionCode()));
				}

				rateExTran.setPublishFlag(TCGMUtil.getNewValue(rateExTran.getPublishFlag(),newVals.getPublishFlag()));
				rateExTran.getRateEx().setRptAff(TCGMUtil.getNewValue(rateExTran.getRateEx().getRptAff(),newVals.getRateEx().getRptAff()));
				rateExTran.getRateEx().getRptProduct().setInvCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getRptProduct().getInvCode(),newVals.getRateEx().getRptProduct().getInvCode()));
				rateExTran.getRateEx().getRptProduct().setList(TCGMUtil.getNewValue(rateExTran.getRateEx().getRptProduct().getList(),newVals.getRateEx().getRptProduct().getList()));
				rateExTran.getRateEx().getRptProduct().setLabel(TCGMUtil.getNewValue(rateExTran.getRateEx().getRptProduct().getLabel(),newVals.getRateEx().getRptProduct().getLabel()));
				rateExTran.getRateEx().getRptProduct().setSize(TCGMUtil.getNewValue(rateExTran.getRateEx().getRptProduct().getSize(),newVals.getRateEx().getRptProduct().getSize()));
				rateExTran.getRateEx().getRptProduct().setPack(TCGMUtil.getNewValue(rateExTran.getRateEx().getRptProduct().getPack(),newVals.getRateEx().getRptProduct().getPack()));

				rateExTran.getRateEx().setSupAff(TCGMUtil.getNewValue(rateExTran.getRateEx().getSupAff(),newVals.getRateEx().getSupAff()));
				rateExTran.getRateEx().getSupProduct().setInvCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getSupProduct().getInvCode(),newVals.getRateEx().getSupProduct().getInvCode()));
				rateExTran.getRateEx().getSupProduct().setList(TCGMUtil.getNewValue(rateExTran.getRateEx().getSupProduct().getList(),newVals.getRateEx().getSupProduct().getList()));
				rateExTran.getRateEx().getSupProduct().setLabel(TCGMUtil.getNewValue(rateExTran.getRateEx().getSupProduct().getLabel(),newVals.getRateEx().getSupProduct().getLabel()));
				rateExTran.getRateEx().getSupProduct().setSize(TCGMUtil.getNewValue(rateExTran.getRateEx().getSupProduct().getSize(),newVals.getRateEx().getSupProduct().getSize()));
				rateExTran.getRateEx().getSupProduct().setPack(TCGMUtil.getNewValue(rateExTran.getRateEx().getSupProduct().getPack(),newVals.getRateEx().getSupProduct().getPack()));

				rateExTran.getRateEx().setEndAff(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndAff(),newVals.getRateEx().getEndAff()));
				rateExTran.getRateEx().getEndProduct().setInvCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndProduct().getInvCode(),newVals.getRateEx().getEndProduct().getInvCode()));
				rateExTran.getRateEx().getEndProduct().setList(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndProduct().getList(),newVals.getRateEx().getEndProduct().getList()));
				rateExTran.getRateEx().getEndProduct().setLabel(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndProduct().getLabel(),newVals.getRateEx().getEndProduct().getLabel()));
				rateExTran.getRateEx().getEndProduct().setSize(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndProduct().getSize(),newVals.getRateEx().getEndProduct().getSize()));
				rateExTran.getRateEx().getEndProduct().setPack(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndProduct().getPack(),newVals.getRateEx().getEndProduct().getPack()));

//				rateExTran.getRateEx().setBegPeriod(TCGMUtil.getNewValue(rateExTran.getRateEx().getBegPeriod(),newVals.getRateEx().getBegPeriod()));
//				rateExTran.getRateEx().setEndPeriod(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndPeriod(),newVals.getRateEx().getEndPeriod()));
//
//				rateExTran.getRateEx().setBpfRate(TCGMUtil.getNewValue(rateExTran.getRateEx().getBpfRate(),newVals.getRateEx().getBpfRate()));
//				rateExTran.getRateEx().setBppRate(TCGMUtil.getNewValue(rateExTran.getRateEx().getBppRate(),newVals.getRateEx().getBppRate()));
//				rateExTran.getRateEx().setCostfRate(TCGMUtil.getNewValue(rateExTran.getRateEx().getCostfRate(),newVals.getRateEx().getCostfRate()));
//				rateExTran.getRateEx().setCostpRate(TCGMUtil.getNewValue(rateExTran.getRateEx().getCostpRate(),newVals.getRateEx().getCostpRate()));
//
//				rateExTranDao.insert(rateExTran,conn);

				// Set the currency codes once per each search object record traversed otherwise the value for each existing record could
				// get changed below in the for loops when the "set" methods are called

// 12-01-03 There is no currency code with this EXRATE (RateEx)
//				String bpCurCode = rateExTran.getRateEx().getBpCurCode();
//				String costCurCode = rateExTran.getRateEx().getCostCurCode();

				// 11-11-03 Setup a flag so I can know when the user entered a new price or a new cost
				boolean newBpfRate = false;    // User entered a new bill price
				boolean newBppRate = false;    // User entered a new bill price
				boolean newCostfRate = false;  // user entered a new cost price
				boolean newCostpRate = false;  // user entered a new cost price
				boolean firstTime = true;      // Flag is used to write price & cost new values only once per loop
				if ( !(newVals.getRateEx().getBpfRate().equals(null))  &&
					 !(newVals.getRateEx().getBpfRate().equals(""))    &&
					 !(newVals.getRateEx().getBpfRate().equals(" ")) )
				{
					newBpfRate = true;
				}
				if ( !(newVals.getRateEx().getBppRate().equals(null))  &&
					 !(newVals.getRateEx().getBppRate().equals(""))    &&
					 !(newVals.getRateEx().getBppRate().equals(" ")) )
				{
					newBppRate = true;
				}
				if ( !(newVals.getRateEx().getCostfRate().equals(null))  &&
					 !(newVals.getRateEx().getCostfRate().equals(""))    &&
					 !(newVals.getRateEx().getCostfRate().equals(" ")) )
				{
					newCostfRate = true;
				}
				if ( !(newVals.getRateEx().getCostpRate().equals(null))  &&
					 !(newVals.getRateEx().getCostpRate().equals(""))    &&
					 !(newVals.getRateEx().getCostpRate().equals(" ")) )
				{
					newCostpRate = true;
				}
				boolean changeInPrices = false;
				if (newBpfRate || newBppRate || newCostfRate || newCostpRate)
				{
					changeInPrices = true;
				}
				int i = 0;
				for(i = 1; i <= TCGMConstants.MAX_PERIODS-1; i++)
				{
					// Duplicate the existing period price and cost values for the range not specified by the user
					if ((i < Integer.parseInt(newVals.getRateEx().getBegPeriod()))  ||
						(i > Integer.parseInt(newVals.getRateEx().getEndPeriod()))  ||
						(!firstTime)                                                ||
						(!changeInPrices))  // 11-25-03 Added ts changeInPirce condition to allow ts same loop to process non-price changes because
					{                       // multiple records needed to be written out for non-price changes as well.
						int saveBegPeriod = 0;
						int saveEndPeriod = 0;
						String saveCostfRate = "";
						String saveCostpRate = "";
						String saveBpfRate = "";
						String saveBppRate = "";
						String costfRate = "";
						String costpRate = "";
						String bpfRate = "";
						String bppRate = "";
						boolean firstTimeWritingPrices = true;
						saveBegPeriod = i;
						while(  (  (i < Integer.parseInt(newVals.getRateEx().getBegPeriod())) ||
								   (i > Integer.parseInt(newVals.getRateEx().getEndPeriod())) &&
								   (i <= TCGMConstants.MAX_PERIODS - 1)  )                    ||
								(  (!changeInPrices)                                          &&  // 11-25-03 Added ts 2nd "&&" condition
								   (i <= TCGMConstants.MAX_PERIODS - 1)  )  )                     // to fix the non-price mass update changes
						{
							costfRate = rateExTran.getRateEx().getCostfRates(i-1).getPeriod();
							costpRate = rateExTran.getRateEx().getCostpRates(i-1).getPeriod();
							bpfRate = rateExTran.getRateEx().getBpfRates(i-1).getPeriod();
							bppRate = rateExTran.getRateEx().getBppRates(i-1).getPeriod();

							if( (saveBpfRate.equals(bpfRate)  && saveBppRate.equals(bppRate) &&
								 saveCostfRate.equals(costfRate) && saveCostpRate.equals(costpRate)) ||
								 firstTimeWritingPrices )
							{
								saveCostfRate = costfRate;
								saveCostpRate = costpRate;
								saveBpfRate = bpfRate;
								saveBppRate = bppRate;
								saveEndPeriod = i;
							}
							else
							{
								rateExTran.getRateEx().setBegPeriod(Integer.toString(saveBegPeriod));
								rateExTran.getRateEx().setEndPeriod(Integer.toString(saveEndPeriod));
								rateExTran.getRateEx().setBpfRate(saveBpfRate);
								rateExTran.getRateEx().setBppRate(saveBppRate);
								rateExTran.getRateEx().setCostfRate(saveCostfRate);
								rateExTran.getRateEx().setCostpRate(saveCostpRate);
//								rateExTran.getRateEx().setBpCurCode(bpCurCode);
//								rateExTran.getRateEx().setCostCurCode(costCurCode);
								rateExTranDao.insert(rateExTran,conn);
								saveCostfRate = costfRate;
								saveCostpRate = costpRate;
								saveBpfRate = bpfRate;
								saveBppRate = bppRate;
								saveBegPeriod = i;
								saveEndPeriod = i;
							}
							i++;
							firstTimeWritingPrices = false;
						}
						rateExTran.getRateEx().setBegPeriod(Integer.toString(saveBegPeriod));
						rateExTran.getRateEx().setEndPeriod(Integer.toString(saveEndPeriod));
						rateExTran.getRateEx().setBpfRate(bpfRate);
						rateExTran.getRateEx().setBppRate(bppRate);
						rateExTran.getRateEx().setCostfRate(costfRate);
						rateExTran.getRateEx().setCostpRate(costpRate);
//						rateExTran.getRateEx().setBpCurCode(bpCurCode);
//						rateExTran.getRateEx().setCostCurCode(costCurCode);
						rateExTranDao.insert(rateExTran,conn);
						i--; // Reposition main loop loop counter for return to outer for loop
					}
					else if ( firstTime && ((newBpfRate == true) || (newCostfRate == true) ||
											(newBppRate == true) || (newCostpRate == true)) ) // A Price/Cost was changed
					{
						int j = Integer.parseInt(newVals.getRateEx().getBegPeriod());
						int k = Integer.parseInt(newVals.getRateEx().getEndPeriod());
						for(;j <= k; j++)
						{
							String costf = rateExTran.getRateEx().getCostfRates(j-1).getPeriod();
							String costp = rateExTran.getRateEx().getCostpRates(j-1).getPeriod();
							String pricef = rateExTran.getRateEx().getBpfRates(j-1).getPeriod();
							String pricep = rateExTran.getRateEx().getBppRates(j-1).getPeriod();
							rateExTran.getRateEx().setBegPeriod(Integer.toString(j));
							rateExTran.getRateEx().setEndPeriod(Integer.toString(j));
							rateExTran.getRateEx().setBpfRate(TCGMUtil.getNewValue(pricef, newVals.getRateEx().getBpfRate()));
							rateExTran.getRateEx().setBppRate(TCGMUtil.getNewValue(pricep, newVals.getRateEx().getBppRate()));
							rateExTran.getRateEx().setCostfRate(TCGMUtil.getNewValue(costf, newVals.getRateEx().getCostfRate()));
							rateExTran.getRateEx().setCostpRate(TCGMUtil.getNewValue(costp, newVals.getRateEx().getCostpRate()));
//							rateExTran.getRateEx().setBpCurCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getBpCurCode(),newVals.getRateEx().getBpCurCode()));
//							rateExTran.getRateEx().setCostCurCode(costCurCode);
							rateExTranDao.insert(rateExTran,conn);
							i = j;  // advance main loop counter
						}
						// After I write the new price & cost values, turn off firstTime until loop starts over again;
						firstTime = false;
					}
//					else if ( firstTime && ((newBillPrice == false) && (newCostPrice == true)) ) // Cost Price was changed
//					{
//						int j = Integer.parseInt(newVals.getRateEx().getBegPeriod());
//						int k = Integer.parseInt(newVals.getRateEx().getEndPeriod());
//						for(; j <= k; j++)
//						{
//							String cost = rateExTran.getRateEx().getCostPeriodValues(j-1).getPeriod();
//							String price = rateExTran.getRateEx().getBpPeriodValues(j-1).getPeriod();
//							rateExTran.getRateEx().setBegPeriod(Integer.toString(j));
//							rateExTran.getRateEx().setEndPeriod(Integer.toString(j));
//							rateExTran.getRateEx().setBillPrice(price);
//							rateExTran.getRateEx().setCostPrice(TCGMUtil.getNewValue(cost, newVals.getRateEx().getCostPrice()));
//							rateExTran.getRateEx().setBpCurCode(rateExTran.getRateEx().getBpCurCode());
//							rateExTran.getRateEx().setCostCurCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getCostCurCode(),newVals.getRateEx().getCostCurCode()));
//							rateExTranDao.insert(rateExTran,conn);
//							i = j;  // advance main loop counter
//						}
//						// After I write the new price & cost values, turn off firstTime until loop starts over again;
//						firstTime = false;
//					}
//					else if ( firstTime && ((newBillPrice == true) && (newCostPrice == true))  ) // Both Price & Cost were changed
//					{
//						rateExTran.getRateEx().setBegPeriod(TCGMUtil.getNewValue(rateExTran.getRateEx().getBegPeriod(),newVals.getRateEx().getBegPeriod()));
//						rateExTran.getRateEx().setEndPeriod(TCGMUtil.getNewValue(rateExTran.getRateEx().getEndPeriod(),newVals.getRateEx().getEndPeriod()));
//						rateExTran.getRateEx().setBillPrice(TCGMUtil.getNewValue(rateExTran.getRateEx().getBillPrice(),newVals.getRateEx().getBillPrice()));
//						rateExTran.getRateEx().setCostPrice(TCGMUtil.getNewValue(rateExTran.getRateEx().getCostPrice(),newVals.getRateEx().getCostPrice()));
//						rateExTran.getRateEx().setCostCurCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getCostCurCode(),newVals.getRateEx().getCostCurCode()));
//						rateExTran.getRateEx().setBpCurCode(TCGMUtil.getNewValue(rateExTran.getRateEx().getBpCurCode(),newVals.getRateEx().getBpCurCode()));
//						rateExTranDao.insert(rateExTran,conn);
//						i = Integer.parseInt(newVals.getRateEx().getEndPeriod());  // advance main loop counter
//
//						// After I write the new price & cost values, turn off firstTime flag until loop starts over again;
//						firstTime = false;
//					}
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
	 * @param userToken
	 * @param rateExTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copySelectedRateExTran(UserToken userToken,Vector rateExTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copySelectedRateExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		Vector selectedRateExTranList = this.getSelectedRateExTran(rateExTranList);

		rateExTranDao.copy(selectedRateExTranList,copyToModel);
	}
	/**
	 * @param userToken
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copyAllRateExTran(UserToken userToken,RateExTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copyAllRateExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RateExTranDao rateExTranDao = daoFactory.getRateExTranDao(userToken);

		rateExTranDao.copy(searchObject,copyToModel);
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of RateExTran, returns the ones that have the selected flag set to true
	 * @param rateExTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedRateExTran(Vector rateExTranList)
	{
		Vector selectedRateExTran = new Vector();

		for(int i = 0; i < rateExTranList.size(); i++)
		{
			RateExTran rateExTran = (RateExTran)rateExTranList.elementAt(i);

			if(rateExTran.getRateEx().isSelected())
			{
				selectedRateExTran.add(rateExTran);
			}
		}

		return selectedRateExTran;
	}
	/**
	 * Given a vector of RateEx, returns the ones that have the selected flag set to true
	 * @param rateExList Vector
	 * @return Vector
	 */
	public Vector getSelectedRateEx(Vector rateExList)
	{
		Vector selectedRateEx = new Vector();

		for(int i = 0; i < rateExList.size(); i++)
		{
			RateEx rateEx = (RateEx)rateExList.elementAt(i);

			if(rateEx.isSelected())
			{
				selectedRateEx.add(rateEx);
			}
		}

		return selectedRateEx;
	}
}