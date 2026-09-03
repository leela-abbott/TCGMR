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
public class BpcRevMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();

	/**
	 * Default Constructor
	 */
	public BpcRevMngr()
	{}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public Vector getBpcRev(UserToken userToken,BpcRev searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevDao bpcRevDao = daoFactory.getBpcRevDao(userToken,searchObject);
		return bpcRevDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public Vector getBpcRev(UserToken userToken,BpcRev searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevDao bpcRevDao = daoFactory.getBpcRevDao(userToken,searchObject,pagingFilter);
		return bpcRevDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public Vector getBpcRev(UserToken userToken,BpcRev searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevDao bpcRevDao = daoFactory.getBpcRevDao(userToken,searchObject,pagingFilter,sortObject);
		return bpcRevDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of BpcRev objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each BpcRev to an BpcRevTran
	 * 3.  Call the bpcRevTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject BpcRev object
	 * @param actionCode action code for bpcRev tran
	 * @throws TCGMException
	 */
//	A.Winter 7/27/05 - changed method to boolean		
	public boolean addAllBpcRevToTrans(UserToken userToken,BpcRev searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllBpcRevToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevDao bpcRevDao = daoFactory.getBpcRevDao(userToken,searchObject);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);
//		A.Winter 7/27/05		
		boolean res = true;	
		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = bpcRevDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				BpcRev bpcRev = bpcRevDao.getBpcRevFromCurrentRow(rs);
				BpcRevTran bpcRevTran = this.convertBpcRevToTran(bpcRev,actionCode);
				res = bpcRevTranDao.insert(bpcRevTran,conn);
//				A.Winter - 7/26/05
				
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
	 * @param bpcRevList Vector of BpcRev objects
	 * @param actionCode action code for BpcRevTran
	 * @throws TCGMException
	 */
//	A.Winter 7/27/05 - changed method to boolean	
	public boolean addSelectedBpcRevToTrans(UserToken userToken,Vector bpcRevList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedBpcRevToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		Vector bpcRevTranList = null;
		Vector selectedBpcRevList = this.getSelectedBpcRev(bpcRevList);

		bpcRevTranList = convertBpcRevListToTran(selectedBpcRevList,actionCode);
//		A.Winter - made result as boolean
		boolean res = bpcRevTranDao.insert(bpcRevTranList);
	    return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcRevList Vector of BpcRev objects
	 * @param actionCode action code for BpcRevTran
	 * @return Vector
	 */
	public Vector convertBpcRevListToTran(Vector bpcRevList,String actionCode)
	{
		String methodName = "convertBpcRevListToTran(Vector,String)";

		Vector bpcRevTranList = new Vector();

		for(int i = 0; i < bpcRevList.size(); i++)
		{
			bpcRevTranList.add(convertBpcRevToTran((BpcRev)bpcRevList.elementAt(i),actionCode) );
		}
		return bpcRevTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcRev BpcRev object
	 * @param actionCode action code for BpcRevTran
	 * @return BpcRevTran
	 */
	public BpcRevTran convertBpcRevToTran(BpcRev bpcRev,String actionCode)
	{
		String methodName = "convertBpcRevToTran(BpcRev,String)";

		BpcRevTran bpcRevTran = new BpcRevTran();
		bpcRevTran.setBpcRev( bpcRev );
		bpcRevTran.setBillPrice(bpcRev.getBillPrice());
		// 4-25-03 Field does not belong in the BpcRev UI
		//bpcRevTran.setCostPrice(bpcRev.getCostPrice());
		bpcRevTran.setBegPeriod(bpcRev.getBegPeriod());
		bpcRevTran.setEndPeriod(bpcRev.getEndPeriod());
		bpcRevTran.setActionCode(actionCode);
		bpcRevTran.getBpcRev().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		bpcRevTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return bpcRevTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcRevTran(UserToken userToken,BpcRevTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken,searchObject);
		return bpcRevTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcRevTran(UserToken userToken,BpcRevTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		String methodName = "getBpcRevTran(UserToken,BpcRevTran,PagingFilter)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken,searchObject,pagingFilter);
		return bpcRevTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcRevTran(UserToken userToken,BpcRevTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		String methodName = "getBpcRevTran(UserToken,BpcRevTran,PagingFilter,Sort)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken,searchObject,pagingFilter,sortObject);

		return bpcRevTranDao.getVO();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,BpcRev searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevDao bpcRevDao = daoFactory.getBpcRevDao(userToken,searchObject);
		return bpcRevDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,BpcRevTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken,searchObject);
		return bpcRevTranDao.getCount();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject BpcRevTran
	 * @throws TCGMException
	 */
	public void deleteAllBpcRevTran(UserToken userToken,BpcRevTran searchObject) throws TCGMException,
																						TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllBpcRevTran(UserToken,BpcRevTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		bpcRevTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param bpcRevTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedBpcRevTran(UserToken userToken,Vector bpcRevTranList) throws TCGMException
	{
		String methodName = "deleteSelectedBpcRevTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		Vector selectedBpcRevTranList = this.getSelectedBpcRevTran(bpcRevTranList);

		bpcRevTranDao.delete(selectedBpcRevTranList);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcRevTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedBpcRevTran(UserToken userToken,Vector bpcRevTranList) throws TCGMException
	{
		String methodName = "saveBpcRevTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		Vector selectedBpcRevTranList = this.getSelectedBpcRevTran(bpcRevTranList);

		bpcRevTranDao.update(selectedBpcRevTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcRevTranList Vector
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copySelectedBpcRevTran(UserToken userToken,Vector bpcRevTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copySelectedBpcRevTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		Vector selectedBpcRevTranList = this.getSelectedBpcRevTran(bpcRevTranList);

		bpcRevTranDao.copy(selectedBpcRevTranList,copyToModel);
	}
	/**
	 * @param userToken UserToken
	 * @param searchObject BpcRevTran
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copyAllBpcRevTran(UserToken userToken,BpcRevTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copyAllBpcRevTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		bpcRevTranDao.copy(searchObject,copyToModel);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcRevTranList Vector
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishSelectedBpcRevTran(UserToken userToken,Vector bpcRevTranList, boolean blnFlag) throws TCGMException
	{
		String methodName = "publishBpcRevTran(UserToken,Vector, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		Vector selectedBpcRevTranList = this.getSelectedBpcRevTran(bpcRevTranList);

		for(int i = 0; i<selectedBpcRevTranList.size(); i++)
		{
			if(blnFlag){
				((BpcRevTran)selectedBpcRevTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
			}else{
				((BpcRevTran)selectedBpcRevTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
			}
		}

		bpcRevTranDao.update(selectedBpcRevTranList);
	}

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U) 
	 */
	public void publishAllBpcRevTran(UserToken userToken,BpcRevTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAllBpcRevTran(UserToken,BpcRevTran, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken,searchObject);

		bpcRevTranDao.publishAll(searchObject, blnFlag);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcRevTran BpcRevTran
	 * @throws TCGMException
	 */
	public boolean addNewBpcRevTran(UserToken userToken,BpcRevTran bpcRevTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewBpcRevTran(UserToken,BpcRevTran";
		
		// Date Modified: 12/14/2005
		// Modified By  : Udaya B Aravapalli. 
		boolean dup = true; //Based on the existing code dup =true means there are no duplicates. :)  
	
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);

		dup = bpcRevTranDao.insert(bpcRevTran,null); //dup = false means there are duplicates.:=)
		
		return dup;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject BpcRevTran
	 * @param newVals BpcRevTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,BpcRevTran searchObject,BpcRevTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,BpcRevTran,BpcRevTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);
		bpcRevTranDao.massUpdate(searchObject,newVals);
	}
	/**
	 * Used to do a mass update by creating trans records from existing bpcRev records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of BpcRev
	 * 2.  Create an bpcRevTran and put the bpcRev from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public Vector massUpdate(UserToken userToken,BpcRev searchObject,BpcRevTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,BpcRev,BpcRevTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcRevDao bpcRevDao = daoFactory.getBpcRevDao(userToken,searchObject);
		BpcRevTranDao bpcRevTranDao = daoFactory.getBpcRevTranDao(userToken);
		RowSet rs = bpcRevDao.getRS();
		Vector bpcRevDupRecList = new Vector();
		boolean result = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection();

			while(rs.next())
			{
				BpcRevTran bpcRevTran = new BpcRevTran();
				BpcRev bpcRevObj = new BpcRev();
				bpcRevObj = bpcRevDao.getBpcRevFromCurrentRow(rs);
				bpcRevTran.setBpcRev(bpcRevObj);
				
				bpcRevTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					bpcRevTran.setActionCode(TCGMUtil.getNewValue(bpcRevTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
					bpcRevTran.setActionCode(TCGMUtil.getNewValue(bpcRevTran.getActionCode(),newVals.getActionCode()));
				}

				bpcRevTran.setPublishFlag(TCGMUtil.getNewValue(bpcRevTran.getPublishFlag(),newVals.getPublishFlag()));
				bpcRevTran.getBpcRev().setRevType(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getRevType(),newVals.getBpcRev().getRevType()));
				bpcRevTran.getBpcRev().setRptAff(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getRptAff(),newVals.getBpcRev().getRptAff()));
				bpcRevTran.getBpcRev().setSupAff(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getSupAff(),newVals.getBpcRev().getSupAff()));
				bpcRevTran.getBpcRev().getSupProduct().setInvCode(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getSupProduct().getInvCode(),newVals.getBpcRev().getSupProduct().getInvCode()));
				bpcRevTran.getBpcRev().getSupProduct().setList(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getSupProduct().getList(),newVals.getBpcRev().getSupProduct().getList()));
				bpcRevTran.getBpcRev().getSupProduct().setLabel(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getSupProduct().getLabel(),newVals.getBpcRev().getSupProduct().getLabel()));
				bpcRevTran.getBpcRev().getSupProduct().setSize(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getSupProduct().getSize(),newVals.getBpcRev().getSupProduct().getSize()));
				bpcRevTran.getBpcRev().getSupProduct().setPack(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getSupProduct().getPack(),newVals.getBpcRev().getSupProduct().getPack()));

				// 12-01-2004 Note: Per DC, cost is never changed in the Revisions page.

				bpcRevTran.getBpcRev().setBegPeriod(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getBegPeriod(),newVals.getBpcRev().getBegPeriod()));
				bpcRevTran.getBpcRev().setEndPeriod(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getEndPeriod(),newVals.getBpcRev().getEndPeriod()));
				bpcRevTran.getBpcRev().setBillPrice(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getBillPrice(),newVals.getBpcRev().getBillPrice()));
				bpcRevTran.getBpcRev().setCostPrice(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getCostPrice(),newVals.getBpcRev().getCostPrice()));
				bpcRevTran.getBpcRev().setCostCurCode(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getCostCurCode(),newVals.getBpcRev().getCostCurCode()));
				bpcRevTran.getBpcRev().setBpCurCode(TCGMUtil.getNewValue(bpcRevTran.getBpcRev().getBpCurCode(),newVals.getBpcRev().getBpCurCode()));

				result = bpcRevTranDao.insert(bpcRevTran,conn);
				if(!result){
						blnFlag = false;
						bpcRevTran.getBpcRev().setMsg("Duplicate Row");
						bpcRevDupRecList.add(bpcRevObj);
					}
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(className,methodName,sqle.toString());
		}
		finally
		{
			if(conn!=null)
			{
				SQLUtil.closeConnection(conn);
			}
		}
		return bpcRevDupRecList;
	}

	/*****************************************************************************************/
	/**
	 * Given a vector of BpcRevTran, returns the ones that have the selected flag set to true
	 * @param bpcRevTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedBpcRevTran(Vector bpcRevTranList)
	{
		Vector selectedBpcRevTran = new Vector();

		for(int i = 0; i < bpcRevTranList.size(); i++)
		{
			BpcRevTran bpcRevTran = (BpcRevTran)bpcRevTranList.elementAt(i);

			if(bpcRevTran.getBpcRev().isSelected())
			{
				selectedBpcRevTran.add(bpcRevTran);
			}
		}

		return selectedBpcRevTran;
	}
	/**
	 * Given a vector of BpcRev, returns the ones that have the selected flag set to true
	 * @param bpcRevList Vector
	 * @return Vector
	 */
	public Vector getSelectedBpcRev(Vector bpcRevList)
	{
		Vector selectedBpcRev = new Vector();

		for(int i = 0; i < bpcRevList.size(); i++)
		{
			BpcRev bpcRev = (BpcRev)bpcRevList.elementAt(i);

			if(bpcRev.isSelected())
			{
				selectedBpcRev.add(bpcRev);
			}
		}

		return selectedBpcRev;
	}
}
