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
public class BpcsMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();

	/**
	 * Default Constructor
	 */
	public BpcsMngr()
	{}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public Vector getBpcs(UserToken userToken,Bpcs searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsDao bpcsDao = daoFactory.getBpcsDao(userToken,searchObject);
		return bpcsDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public Vector getBpcs(UserToken userToken,Bpcs searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsDao bpcsDao = daoFactory.getBpcsDao(userToken,searchObject,pagingFilter);
		return bpcsDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public Vector getBpcs(UserToken userToken,Bpcs searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsDao bpcsDao = daoFactory.getBpcsDao(userToken,searchObject,pagingFilter,sortObject);
		return bpcsDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of Bpcs objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each Bpcs to an BpcsTran
	 * 3.  Call the bpcsTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject Bpcs object
	 * @param actionCode action code for bpcs tran
	 * @throws TCGMException
	 */
// A.Winter 7/26/05 changer void to boolean	
	public boolean addAllBpcsToTrans(UserToken userToken,Bpcs searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllBpcsToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsDao bpcsDao = daoFactory.getBpcsDao(userToken,searchObject);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);
//	A.Winter 7/26/05		
		boolean res = true;	
		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = bpcsDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				Bpcs bpcs = bpcsDao.getBpcsFromCurrentRow(rs);
				BpcsTran bpcsTran = this.convertBpcsToTran(bpcs,actionCode);
				res = bpcsTranDao.insert(bpcsTran,conn);
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
	 * @param bpcsList Vector of Bpcs objects
	 * @param actionCode action code for BpcsTran
	 * @throws TCGMException
	 */
//	A.Winter 7/26/05 - made method boolean
	public boolean addSelectedBpcsToTrans(UserToken userToken,Vector bpcsList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedBpcsToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		Vector bpcsTranList = null;
		Vector selectedBpcsList = this.getSelectedBpcs(bpcsList);

		bpcsTranList = convertBpcsListToTran(selectedBpcsList,actionCode);
		//		A.Winter - made result as boolean
		boolean res = bpcsTranDao.insert(bpcsTranList);
		return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcsList Vector of Bpcs objects
	 * @param actionCode action code for BpcsTran
	 * @return Vector
	 */
	public Vector convertBpcsListToTran(Vector bpcsList,String actionCode)
	{
		String methodName = "convertBpcsListToTran(Vector,String)";

		Vector bpcsTranList = new Vector();

		for(int i = 0; i < bpcsList.size(); i++)
		{
			bpcsTranList.add(convertBpcsToTran((Bpcs)bpcsList.elementAt(i),actionCode) );
		}
		return bpcsTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param bpcs Bpcs object
	 * @param actionCode action code for BpcsTran
	 * @return BpcsTran
	 */
	public BpcsTran convertBpcsToTran(Bpcs bpcs,String actionCode)
	{
		String methodName = "convertBpcsToTran(Bpcs,String)";

		BpcsTran bpcsTran = new BpcsTran();
		bpcsTran.setBpcs( bpcs );
		bpcsTran.setBillPrice(bpcs.getBillPrice());
		bpcsTran.setCostPrice(bpcs.getCostPrice());
		bpcsTran.setBegPeriod(bpcs.getBegPeriod());
		bpcsTran.setEndPeriod(bpcs.getEndPeriod());
		bpcsTran.setActionCode(actionCode);
		bpcsTran.getBpcs().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		bpcsTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return bpcsTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcsTran(UserToken userToken,BpcsTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken,searchObject);
		return bpcsTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcsTran(UserToken userToken,BpcsTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		String methodName = "getBpcsTran(UserToken,BpcsTran,PagingFilter)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken,searchObject,pagingFilter);
		return bpcsTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getBpcsTran(UserToken userToken,BpcsTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		String methodName = "getBpcsTran(UserToken,BpcsTran,PagingFilter,Sort)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken,searchObject,pagingFilter,sortObject);
		return bpcsTranDao.getVO();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,Bpcs searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsDao bpcsDao = daoFactory.getBpcsDao(userToken,searchObject);
		return bpcsDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,BpcsTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken,searchObject);
		return bpcsTranDao.getCount();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject BpcsTran
	 * @throws TCGMException
	 */
	public void deleteAllBpcsTran(UserToken userToken,BpcsTran searchObject) throws TCGMException,
																					TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllBpcsTran(UserToken,BpcsTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		bpcsTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedBpcsTran(UserToken userToken,Vector bpcsTranList) throws TCGMException
	{
		String methodName = "deleteSelectedBpcsTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		Vector selectedBpcsTranList = this.getSelectedBpcsTran(bpcsTranList);

		bpcsTranDao.delete(selectedBpcsTranList);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedBpcsTran(UserToken userToken,Vector bpcsTranList) throws TCGMException
	{
		String methodName = "saveBpcsTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		Vector selectedBpcsTranList = this.getSelectedBpcsTran(bpcsTranList);

		bpcsTranDao.update(selectedBpcsTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcsTranList Vector
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copySelectedBpcsTran(UserToken userToken,Vector bpcsTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copySelectedBpcsTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		Vector selectedBpcsTranList = this.getSelectedBpcsTran(bpcsTranList);

		bpcsTranDao.copy(selectedBpcsTranList,copyToModel);
	}
	/**
	 * @param userToken UserToken
	 * @param searchObject BpcsTran
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copyAllBpcsTran(UserToken userToken,BpcsTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copyAllBpcsTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		bpcsTranDao.copy(searchObject,copyToModel);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishSelectedBpcsTran(UserToken userToken,Vector bpcsTranList, boolean blnFlag) throws TCGMException
	{
		String methodName = "publishBpcsTran(UserToken,Vector, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		Vector selectedBpcsTranList = this.getSelectedBpcsTran(bpcsTranList);

		for(int i = 0; i<selectedBpcsTranList.size(); i++)
		{
			if(blnFlag){
				((BpcsTran)selectedBpcsTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
			}else{
				((BpcsTran)selectedBpcsTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
			}
		}

		bpcsTranDao.update(selectedBpcsTranList);
	}

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishAllBpcsTran(UserToken userToken,BpcsTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAllBpcsTran(UserToken,BpcsTran, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken,searchObject);

		bpcsTranDao.publishAll(searchObject, blnFlag);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcsTran BpcsTran
	 * @throws TCGMException
	 */
	public boolean addNewBpcsTran(UserToken userToken,BpcsTran bpcsTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewBpcsTran(UserToken,BpcsTran";
		// Date Modified: 12/14/2005
		// Modified By  : Udaya B Aravapalli. 
		boolean dup = true; //Based on the existing code dup =true means there are no duplicates. :) 		

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		dup = bpcsTranDao.insert(bpcsTran,null);
		return dup;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject BpcsTran
	 * @param newVals BpcsTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,BpcsTran searchObject,BpcsTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,BpcsTran,BpcsTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);
		bpcsTranDao.massUpdate(searchObject,newVals);
	}
	/**
	 * Used to do a mass update by creating trans records from existing bpcs records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of Bpcs
	 * 2.  Create an bpcsTran and put the bpcs from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public Vector massUpdate(UserToken userToken,Bpcs searchObject,BpcsTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,Bpcs,BpcsTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsDao bpcsDao = daoFactory.getBpcsDao(userToken,searchObject);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);
		RowSet rs = bpcsDao.getRS();
		Vector bpcsDupRecList = new Vector();
		boolean result = true;
		boolean blnFlag = true;
		Connection conn = null;

		try
		{
			conn = SQLUtil.openConnection();

			while(rs.next())
			{
				BpcsTran bpcsTran = new BpcsTran();
				Bpcs bpcsObj = new Bpcs();
				bpcsObj = bpcsDao.getBpcsFromCurrentRow(rs);
				bpcsTran.setBpcs(bpcsObj);
							
				bpcsTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					bpcsTran.setActionCode(TCGMUtil.getNewValue(bpcsTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
					bpcsTran.setActionCode(TCGMUtil.getNewValue(bpcsTran.getActionCode(),newVals.getActionCode()));
				}

				bpcsTran.setPublishFlag(TCGMUtil.getNewValue(bpcsTran.getPublishFlag(),newVals.getPublishFlag()));
				bpcsTran.getBpcs().setRevType(TCGMUtil.getNewValue(bpcsTran.getBpcs().getRevType(),newVals.getBpcs().getRevType()));
				//RPT Aff
				if (newVals.getBpcs().getRptAff().equalsIgnoreCase("0" + TCGMConstants.LBL_SUP))
				{
					bpcsTran.getBpcs().setRptAff(bpcsTran.getBpcs().getSupAff());
				}
				else
				{
					bpcsTran.getBpcs().setRptAff(TCGMUtil.getNewValue(bpcsTran.getBpcs().getRptAff(),newVals.getBpcs().getRptAff()));
				}
				//SUP Aff
				if (newVals.getBpcs().getSupAff().equalsIgnoreCase("0" + TCGMConstants.LBL_RPT))
				{
					bpcsTran.getBpcs().setSupAff(bpcsTran.getBpcs().getRptAff());
				}
				else
				{
					bpcsTran.getBpcs().setSupAff(TCGMUtil.getNewValue(bpcsTran.getBpcs().getSupAff(),newVals.getBpcs().getSupAff()));
				}
				bpcsTran.getBpcs().getSupProduct().setInvCode(TCGMUtil.getNewValue(bpcsTran.getBpcs().getSupProduct().getInvCode(),newVals.getBpcs().getSupProduct().getInvCode()));
				bpcsTran.getBpcs().getSupProduct().setList(TCGMUtil.getNewValue(bpcsTran.getBpcs().getSupProduct().getList(),newVals.getBpcs().getSupProduct().getList()));
				
				if(newVals.getBpcs().getSupProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					bpcsTran.getBpcs().getSupProduct().setLabel("   ");
				}
				else{
					bpcsTran.getBpcs().getSupProduct().setLabel(TCGMUtil.getNewValue(bpcsTran.getBpcs().getSupProduct().getLabel(),newVals.getBpcs().getSupProduct().getLabel()));
				}
				if(newVals.getBpcs().getSupProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					bpcsTran.getBpcs().getSupProduct().setSize("   ");
				}
				else{
				bpcsTran.getBpcs().getSupProduct().setSize(TCGMUtil.getNewValue(bpcsTran.getBpcs().getSupProduct().getSize(),newVals.getBpcs().getSupProduct().getSize()));
				}
				bpcsTran.getBpcs().getSupProduct().setPack(TCGMUtil.getNewValue(bpcsTran.getBpcs().getSupProduct().getPack(),newVals.getBpcs().getSupProduct().getPack()));
				bpcsTran.getBpcs().setFreezeCost(TCGMUtil.getNewValue(bpcsTran.getBpcs().getFreezeCost(),newVals.getBpcs().getFreezeCost()));

				bpcsTran.getBpcs().setBegPeriod(TCGMUtil.getNewValue(bpcsTran.getBpcs().getBegPeriod(),newVals.getBpcs().getBegPeriod()));
				bpcsTran.getBpcs().setEndPeriod(TCGMUtil.getNewValue(bpcsTran.getBpcs().getEndPeriod(),newVals.getBpcs().getEndPeriod()));
				//Bill Price
				bpcsTran.getBpcs().setBillPrice(TCGMUtil.getNewValue(bpcsTran.getBpcs().getBillPrice(),newVals.getBpcs().getBillPrice()));
				//Cost
				bpcsTran.getBpcs().setCostPrice(TCGMUtil.getNewValue(bpcsTran.getBpcs().getCostPrice(),newVals.getBpcs().getCostPrice()));
				//Cost Curr Code
				bpcsTran.getBpcs().setCostCurCode(TCGMUtil.getNewValue(bpcsTran.getBpcs().getCostCurCode(),newVals.getBpcs().getCostCurCode()));
				//BP Curr Code
				bpcsTran.getBpcs().setBpCurCode(TCGMUtil.getNewValue(bpcsTran.getBpcs().getBpCurCode(),newVals.getBpcs().getBpCurCode()));
			
				result = bpcsTranDao.insert(bpcsTran,conn);
				if(!result){
						blnFlag = false;
					bpcsTran.getBpcs().setMsg("Duplicate Row");
					bpcsDupRecList.add(bpcsObj);
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
		return bpcsDupRecList;
	}

	/*****************************************************************************************/
	/**
	 * Given a vector of BpcsTran, returns the ones that have the selected flag set to true
	 * @param bpcsTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedBpcsTran(Vector bpcsTranList)
	{
		Vector selectedBpcsTran = new Vector();

		for(int i = 0; i < bpcsTranList.size(); i++)
		{
			BpcsTran bpcsTran = (BpcsTran)bpcsTranList.elementAt(i);

			if(bpcsTran.getBpcs().isSelected())
			{
				selectedBpcsTran.add(bpcsTran);
			}
		}

		return selectedBpcsTran;
	}
	/**
	 * Given a vector of Bpcs, returns the ones that have the selected flag set to true
	 * @param bpcsList Vector
	 * @return Vector
	 */
	public Vector getSelectedBpcs(Vector bpcsList)
	{
		Vector selectedBpcs = new Vector();

		for(int i = 0; i < bpcsList.size(); i++)
		{
			Bpcs bpcs = (Bpcs)bpcsList.elementAt(i);

			if(bpcs.isSelected())
			{
				selectedBpcs.add(bpcs);
			}
		}

		return selectedBpcs;
	}
}
