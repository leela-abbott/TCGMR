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
public class BpcExMngr implements TCGMMngr
{
	public String className = null;

	/**
	 * Default COnstructor
	 */
	public BpcExMngr()
	{
		className = this.className;
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getBpcEx(UserToken userToken,BpcEx searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExDao bpcExDao = daoFactory.getBpcExDao(userToken,searchObject);
		return bpcExDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getBpcEx(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExDao bpcExDao = daoFactory.getBpcExDao(userToken,searchObject,pagingFilter);
		return bpcExDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getBpcEx(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExDao bpcExDao = daoFactory.getBpcExDao(userToken,searchObject,pagingFilter,sortObject);
		return bpcExDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of BpcEx objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each BpcEx to an BpcExTran
	 * 3.  Call the bpcExTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject BpcEx object
	 * @param actionCode action code for bpcEx tran
	 * @throws TCGMException
	 */
	public boolean addAllBpcExToTrans(UserToken userToken,BpcEx searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllBpcExToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExDao bpcExDao = daoFactory.getBpcExDao(userToken,searchObject);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);
// A.Winter 7/18/05		
		boolean res = true;	
		
		/**The connection object is usually created inside of the dao.  The problem with this is that I am looping
		 * here and I don't want to open/close the connection every time I create a record.  I will
		 * open the connection 1 time here and then close it when I am done with it.
		 */
		Connection conn = SQLUtil.openConnection();

		RowSet rs = bpcExDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				BpcEx bpcEx = bpcExDao.getBpcExFromCurrentRow(rs);
				BpcExTran bpcExTran = this.convertBpcExToTran(bpcEx,actionCode);
				res = bpcExTranDao.insert(bpcExTran,conn);
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
	 * @param bpcExList Vector of BpcEx objects
	 * @param actionCode action code for BpcExTran
	 * @throws TCGMException
	 */
//	A.Winter 7/15/05 - made method boolean
	public boolean addSelectedBpcExToTrans(UserToken userToken,Vector bpcExList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedBpcExToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		Vector bpcExTranList = null;
		Vector selectedBpcExList = this.getSelectedBpcEx(bpcExList);

		bpcExTranList = convertBpcExListToTran(selectedBpcExList,actionCode);
//		A.Winter - made result as boolean
		boolean res = bpcExTranDao.insert(bpcExTranList);

		return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcExList Vector of BpcEx objects
	 * @param actionCode action code for BpcExTran
	 * @return Vector
	 */
	public Vector convertBpcExListToTran(Vector bpcExList,String actionCode)
	{
		String methodName = "convertBpcExListToTran(Vector,String)";

		Vector bpcExTranList = new Vector();

		for(int i = 0; i < bpcExList.size(); i++)
		{
			bpcExTranList.add(convertBpcExToTran((BpcEx)bpcExList.elementAt(i),actionCode) );
		}
		return bpcExTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcEx BpcEx object
	 * @param actionCode action code for BpcExTran
	 * @return BpcExTran
	 */
	public BpcExTran convertBpcExToTran(BpcEx bpcEx,String actionCode)
	{
		String methodName = "convertBpcExToTran(BpcEx,String)";

		BpcExTran bpcExTran = new BpcExTran();
		bpcExTran.setBpcEx( bpcEx );
		bpcExTran.setBillPrice(bpcEx.getBillPrice());
		bpcExTran.setCostPrice(bpcEx.getCostPrice());
		bpcExTran.setBegPeriod(bpcEx.getBegPeriod());
		bpcExTran.setEndPeriod(bpcEx.getEndPeriod());
		bpcExTran.setActionCode(actionCode);
		bpcExTran.getBpcEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		bpcExTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return bpcExTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcExTran(UserToken userToken,BpcExTran searchObject) throws TCGMException
	{
		String methodName = "getBpcExTran(UserToken,BpcExTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken,searchObject);

		return bpcExTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcExTran(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		String methodName = "getBpcExTran(UserToken,BpcExTran,PagingFilter)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken,searchObject,pagingFilter);

		return bpcExTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcExTran(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		String methodName = "getBpcExTran(UserToken,BpcExTran,PagingFilter,Sort)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken,searchObject,pagingFilter,sortObject);

		return bpcExTranDao.getVO();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,BpcEx searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExDao bpcExDao = daoFactory.getBpcExDao(userToken,searchObject);
		return bpcExDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,BpcExTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken,searchObject);
		return bpcExTranDao.getCount();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject BpcExTran
	 * @throws TCGMException
	 */
	public void deleteAllBpcExTran(UserToken userToken,BpcExTran searchObject) throws TCGMException,
																					  TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllBpcExTran(UserToken,BpcExTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		bpcExTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param bpcExTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedBpcExTran(UserToken userToken,Vector bpcExTranList) throws TCGMException
	{
		String methodName = "deleteSelectedBpcExTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		Vector selectedBpcExTranList = this.getSelectedBpcExTran(bpcExTranList);

		bpcExTranDao.delete(selectedBpcExTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcExTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedBpcExTran(UserToken userToken,Vector bpcExTranList) throws TCGMException
	{
		String methodName = "saveBpcExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		Vector selectedBpcExTranList = this.getSelectedBpcExTran(bpcExTranList);

		bpcExTranDao.update(selectedBpcExTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcExTranList Vector
	 * @throws TCGMException
     * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishSelectedBpcExTran(UserToken userToken,Vector bpcExTranList, boolean blnFlag) throws TCGMException
	{
		String methodName = "publishBpcExTran(UserToken,Vector, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		Vector selectedBpcExTranList = this.getSelectedBpcExTran(bpcExTranList);

		for(int i = 0; i<selectedBpcExTranList.size(); i++)
		{
			if(blnFlag){
				((BpcExTran)selectedBpcExTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
			}else{
				((BpcExTran)selectedBpcExTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
			}
		}

		bpcExTranDao.update(selectedBpcExTranList);
	}
	public void publishAllBpcExTran(UserToken userToken,BpcExTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAllBpcExTran(UserToken,BpcExTran, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken,searchObject);

		bpcExTranDao.publishAll(searchObject, blnFlag);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcExTran BpcExTran
	 * @throws TCGMException
	 */
	public boolean addNewBpcExTran(UserToken userToken,BpcExTran bpcExTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewBpcExTran(UserToken,BpcExTran";
		// Date Modified: 12/14/2005
		// Modified By  : Udaya B Aravapalli. 
		boolean dup = true; //Based on the existing code dup =true means there are no duplicates. :) 		

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		dup = bpcExTranDao.insert(bpcExTran,null);
		return dup;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject BpcExTran
	 * @param newVals BpcExTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,BpcExTran searchObject,BpcExTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,BpcExTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);
		bpcExTranDao.massUpdate(searchObject,newVals);
	}

	/**
	 * Used to do a mass update by creating trans records from existing bpcEx records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of BpcEx
	 * 2.  Create an bpcExTran and put the bpcEx from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public Vector massUpdate(UserToken userToken,BpcEx searchObject,BpcExTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,BpcEx,BpcExTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExDao bpcExDao = daoFactory.getBpcExDao(userToken,searchObject);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);
		RowSet rs = bpcExDao.getRS();
		Vector bpcExDupRecList = new Vector();
		boolean result = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection();

			while(rs.next())
			{
				BpcExTran bpcExTran = new BpcExTran();
				BpcEx bpcExObj = new BpcEx();
				bpcExObj = bpcExDao.getBpcExFromCurrentRow(rs);
				bpcExTran.setBpcEx(bpcExObj);
				bpcExTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					bpcExTran.setActionCode(TCGMUtil.getNewValue(bpcExTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
					bpcExTran.setActionCode(TCGMUtil.getNewValue(bpcExTran.getActionCode(),newVals.getActionCode()));
				}

				bpcExTran.setPublishFlag(TCGMUtil.getNewValue(bpcExTran.getPublishFlag(),newVals.getPublishFlag()));

				bpcExTran.getBpcEx().setRptAff(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getRptAff(),newVals.getBpcEx().getRptAff()));
				bpcExTran.getBpcEx().getRptProduct().setInvCode(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getRptProduct().getInvCode(),newVals.getBpcEx().getRptProduct().getInvCode()));
				bpcExTran.getBpcEx().getRptProduct().setList(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getRptProduct().getList(),newVals.getBpcEx().getRptProduct().getList()));
				if(newVals.getBpcEx().getRptProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					bpcExTran.getBpcEx().getRptProduct().setLabel("   ");
				}
				else{
				bpcExTran.getBpcEx().getRptProduct().setLabel(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getRptProduct().getLabel(),newVals.getBpcEx().getRptProduct().getLabel()));
				}
				if(newVals.getBpcEx().getRptProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					bpcExTran.getBpcEx().getRptProduct().setSize("   ");
				}
				else{
				bpcExTran.getBpcEx().getRptProduct().setSize(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getRptProduct().getSize(),newVals.getBpcEx().getRptProduct().getSize()));
				}
				bpcExTran.getBpcEx().getRptProduct().setPack(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getRptProduct().getPack(),newVals.getBpcEx().getRptProduct().getPack()));

				bpcExTran.getBpcEx().setSupAff(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getSupAff(),newVals.getBpcEx().getSupAff()));
				bpcExTran.getBpcEx().getSupProduct().setInvCode(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getSupProduct().getInvCode(),newVals.getBpcEx().getSupProduct().getInvCode()));
				bpcExTran.getBpcEx().getSupProduct().setList(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getSupProduct().getList(),newVals.getBpcEx().getSupProduct().getList()));
				if(newVals.getBpcEx().getSupProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					bpcExTran.getBpcEx().getSupProduct().setLabel("   ");
				}
				else{
				bpcExTran.getBpcEx().getSupProduct().setLabel(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getSupProduct().getLabel(),newVals.getBpcEx().getSupProduct().getLabel()));
				}
				if(newVals.getBpcEx().getSupProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					bpcExTran.getBpcEx().getSupProduct().setSize("   ");
				}
				else{				
				bpcExTran.getBpcEx().getSupProduct().setSize(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getSupProduct().getSize(),newVals.getBpcEx().getSupProduct().getSize()));
				}
				bpcExTran.getBpcEx().getSupProduct().setPack(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getSupProduct().getPack(),newVals.getBpcEx().getSupProduct().getPack()));

				bpcExTran.getBpcEx().setEndAff(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndAff(),newVals.getBpcEx().getEndAff()));
				bpcExTran.getBpcEx().getEndProduct().setInvCode(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndProduct().getInvCode(),newVals.getBpcEx().getEndProduct().getInvCode()));
				bpcExTran.getBpcEx().getEndProduct().setList(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndProduct().getList(),newVals.getBpcEx().getEndProduct().getList()));
				bpcExTran.getBpcEx().getEndProduct().setLabel(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndProduct().getLabel(),newVals.getBpcEx().getEndProduct().getLabel()));
				bpcExTran.getBpcEx().getEndProduct().setSize(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndProduct().getSize(),newVals.getBpcEx().getEndProduct().getSize()));
				bpcExTran.getBpcEx().getEndProduct().setPack(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndProduct().getPack(),newVals.getBpcEx().getEndProduct().getPack()));

				bpcExTran.getBpcEx().setUsage(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getUsage(),newVals.getBpcEx().getUsage()));
				bpcExTran.getBpcEx().setFreezeCost(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getFreezeCost(),newVals.getBpcEx().getFreezeCost()));
				bpcExTran.getBpcEx().setBpcExId(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getBpcExId(),newVals.getBpcEx().getBpcExId()));

				bpcExTran.getBpcEx().setBegPeriod(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getBegPeriod(),newVals.getBpcEx().getBegPeriod()));
				bpcExTran.getBpcEx().setEndPeriod(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getEndPeriod(),newVals.getBpcEx().getEndPeriod()));
				bpcExTran.getBpcEx().setBillPrice(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getBillPrice(),newVals.getBpcEx().getBillPrice()));
				bpcExTran.getBpcEx().setCostPrice(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getCostPrice(),newVals.getBpcEx().getCostPrice()));
				bpcExTran.getBpcEx().setCostCurCode(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getCostCurCode(),newVals.getBpcEx().getCostCurCode()));
				bpcExTran.getBpcEx().setBpCurCode(TCGMUtil.getNewValue(bpcExTran.getBpcEx().getBpCurCode(),newVals.getBpcEx().getBpCurCode()));
				result = bpcExTranDao.insert(bpcExTran,conn);
				if(!result){
						blnFlag = false;
						bpcExTran.getBpcEx().setMsg("Duplicate Row");
						bpcExDupRecList.add(bpcExObj);
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
		return bpcExDupRecList;
	}
	/*****************************************************************************************/
	/**
	 * @param userToken
	 * @param bpcExTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copySelectedBpcExTran(UserToken userToken,Vector bpcExTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copySelectedBpcExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		Vector selectedBpcExTranList = this.getSelectedBpcExTran(bpcExTranList);

		bpcExTranDao.copy(selectedBpcExTranList,copyToModel);
	}
	/**
	 * @param userToken
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copyAllBpcExTran(UserToken userToken,BpcExTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copyAllBpcExTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcExTranDao bpcExTranDao = daoFactory.getBpcExTranDao(userToken);

		bpcExTranDao.copy(searchObject,copyToModel);
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of BpcExTran, returns the ones that have the selected flag set to true
	 * @param bpcExTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedBpcExTran(Vector bpcExTranList)
	{
		Vector selectedBpcExTran = new Vector();

		for(int i = 0; i < bpcExTranList.size(); i++)
		{
			BpcExTran bpcExTran = (BpcExTran)bpcExTranList.elementAt(i);

			if(bpcExTran.getBpcEx().isSelected())
			{
				selectedBpcExTran.add(bpcExTran);
			}
		}

		return selectedBpcExTran;
	}
	/**
	 * Given a vector of BpcEx, returns the ones that have the selected flag set to true
	 * @param bpcExList Vector
	 * @return Vector
	 */
	public Vector getSelectedBpcEx(Vector bpcExList)
	{
		Vector selectedBpcEx = new Vector();

		for(int i = 0; i < bpcExList.size(); i++)
		{
			BpcEx bpcEx = (BpcEx)bpcExList.elementAt(i);

			if(bpcEx.isSelected())
			{
				selectedBpcEx.add(bpcEx);
			}
		}

		return selectedBpcEx;
	}
}
