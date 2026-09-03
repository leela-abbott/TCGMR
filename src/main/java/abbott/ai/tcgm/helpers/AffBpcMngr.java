package abbott.ai.tcgm.helpers;

import java.util.*;
import java.io.IOException;
import java.sql.*;
import javax.sql.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.data.oracle.*;
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
public class AffBpcMngr implements TCGMMngr
{
	public String className = null;

	/**
	 * Default COnstructor
	 */
	public AffBpcMngr()
	{
		className = this.className;
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getAffBpc(UserToken userToken,AffBpc searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken,searchObject);
		return affBpcDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getAffBpc(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken,searchObject,pagingFilter);
		return affBpcDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getAffBpc(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken,searchObject,pagingFilter,sortObject);
		return affBpcDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of AffBpc objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each AffBpc to an AffBpcTran
	 * 3.  Call the affBpcTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject AffBpc object
	 * @param actionCode action code for affBpc tran
	 * @throws TCGMException
	 */
	public boolean addAllAffBpcToTrans(UserToken userToken,AffBpc searchObject,String actionCode,String modelSelected, String amountType, String transitAff) throws TCGMException
	{
		String methodName = "addAllAffBpcToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken,searchObject);
		//AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = affBpcDao.getRS(searchObject);
		boolean res = true;
		boolean blnFlag = true;

		try
		{
			while (rs.next())
			{
				AffBpc affBpc = affBpcDao.getAffBpcFromCurrentRow(rs);
				//AffBpcTran affBpcTran = this.convertAffBpcToTran(affBpc,actionCode);
				
				BpcsTran bpcsTran = this.convertAffBpcToTran(affBpc,actionCode,modelSelected, amountType);
				
				if(affBpc.getAffiliate().equalsIgnoreCase("0056")&&!bpcsTranDao.isExistinASR(bpcsTran,null)){		
						bpcsTran.getBpcs().setMsg("Record not Exist in ASR");
				  }else{
				//affBpcTranDao.insert(affBpcTran,conn);
				res = bpcsTranDao.insertAffBpc(bpcsTran,conn,transitAff);
				if(!res){
					blnFlag = false;
					}
				 }
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
	 * @param affBpcList Vector of AffBpc objects
	 * @param actionCode action code for AffBpcTran
	 * @throws TCGMException
	 */
	public boolean addSelectedAffBpcToTrans(UserToken userToken,Vector affBpcList,String actionCode,String modelSelected, String amountType, String transitAff) throws TCGMException
	{
		String methodName = "addSelectedAffBpcToTrans";
		boolean res = false;
		boolean duplic = true;
		boolean blnFlag = true;

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		Vector bpcsTranList = null;
		Vector selectedAffBpcList = this.getSelectedAffBpc(affBpcList);
		Connection conn = SQLUtil.openConnection();	
		try{
			bpcsTranList = convertAffBpcListToTran(userToken, selectedAffBpcList,actionCode,modelSelected, amountType);
		
			for(int i = 0; i < bpcsTranList.size();i++)
			{
				BpcsTran bet =(BpcsTran)bpcsTranList.elementAt(i);
				duplic = bpcsTranDao.insertAffBpc(bet,conn,transitAff);
				if(!duplic){
						blnFlag = false;
					 bet.getBpcs().setMsg("Duplicate Row");
					}
				if(bet.getBpcsTranId().equals("DUP"))
				{
				   blnFlag = false;
				}
			}
		
//		bpcsTranDao.insert(bpcsTranList);
		}
		catch(Exception e)
		{
			throw new TCGMException(className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
		return res;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param affBpcList Vector of AffBpc objects
	 * @param actionCode action code for BpcsTran
	 * @return Vector
	 */
	public Vector convertAffBpcListToTran(UserToken userToken, Vector affBpcList,String actionCode,String modelSelected, String amountType) throws TCGMException
	{
		String methodName = "convertAffBpcListToTran(Vector,String)";

		Vector bpcsTranList = new Vector();
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		BpcsTranDao bpcsTranDao = daoFactory.getBpcsTranDao(userToken);

		for(int i = 0; i < affBpcList.size(); i++)
		{
			if(!((AffBpc)affBpcList.elementAt(i)).getAffiliate().equalsIgnoreCase("0056")){
			 bpcsTranList.add(convertAffBpcToTran((AffBpc)affBpcList.elementAt(i),actionCode,modelSelected, amountType) );
			}else if(bpcsTranDao.isExistinASR(convertAffBpcToTran((AffBpc)affBpcList.elementAt(i),actionCode,modelSelected, amountType), null)){
				bpcsTranList.add(convertAffBpcToTran((AffBpc)affBpcList.elementAt(i),actionCode,modelSelected, amountType) );	
			}
		}
		return bpcsTranList;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param affBpc AffBpc object
	 * @param actionCode action code for BpcsTran
	 * @return BpcsTran
	 */
	public BpcsTran convertAffBpcToTran(AffBpc affBpc,String actionCode,String modelSelected, String amountType)
	{
		String methodName = "convertAffBpcToTran(AffBpc,String)";

		Bpcs bpcs = new Bpcs();
		bpcs.setRptAff(affBpc.getRptAff());
		bpcs.setSupAff(affBpc.getSupAff());
		bpcs.setSupProduct(affBpc.getSupProduct());
		if (amountType.equalsIgnoreCase("*") || amountType.equalsIgnoreCase("P"))
		{
			bpcs.setBpCurCode(affBpc.getBpCurCode());
			bpcs.setBillPrice(affBpc.getBillPrice());
		}
		if (amountType.equalsIgnoreCase("*") || amountType.equalsIgnoreCase("C"))
		{
			bpcs.setCostCurCode(affBpc.getCostCurCode());
			bpcs.setCostPrice(affBpc.getCostPrice());
		}
		bpcs.setBegPeriod("1");
		bpcs.setEndPeriod("12");
		bpcs.setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		bpcs.setModelId(modelSelected);

		BpcsTran bpcsTran = new BpcsTran();
		bpcsTran.setBpcs( bpcs );
		bpcsTran.setRevType(" ");
		bpcsTran.setActionCode(actionCode);
		bpcsTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

		return bpcsTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpcTran object with search criteria
	 * @return Vector of AffBpcTran objects
	 * @throws TCGMException
	 */
/*	public Vector getAffBpcTran(UserToken userToken,AffBpcTran searchObject) throws TCGMException
	{
		String methodName = "getAffBpcTran(UserToken,AffBpcTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken,searchObject);

		return affBpcTranDao.getVO();
	} */
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpcTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of AffBpcTran objects
	 * @throws TCGMException
	 */
/*	public Vector getAffBpcTran(UserToken userToken,AffBpcTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		String methodName = "getAffBpcTran(UserToken,AffBpcTran,PagingFilter)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken,searchObject,pagingFilter);

		return affBpcTranDao.getVO();
	} */
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpcTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of AffBpcTran objects
	 * @throws TCGMException
	 */
/*	public Vector getAffBpcTran(UserToken userToken,AffBpcTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		String methodName = "getAffBpcTran(UserToken,AffBpcTran,PagingFilter,Sort)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken,searchObject,pagingFilter,sortObject);

		return affBpcTranDao.getVO();
	} */

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,AffBpc searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken,searchObject);
		return affBpcDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AffBpcTran object with search criteria
	 * @return Vector of AffBpcTran objects
	 * @throws TCGMException
	 */
/*	public long getCount(UserToken userToken,AffBpcTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken,searchObject);
		return affBpcTranDao.getCount();
	} */

	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject AffBpcTran
	 * @throws TCGMException
	 */

	/** 5-13-03 I do need these delete methods because DC wants to have the capacity to delete from the
	 *          affBpc.jsp UI. I want be deleting from the TRAN's however. I will be deleting from
	 *          AFFBPC_FILE.
	 */

/*	public void deleteAllAffBpcTran(UserToken userToken,AffBpcTran searchObject) throws TCGMException
	{
		String methodName = "deleteAllAffBpcTran(UserToken,AffBpcTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken);

		affBpcTranDao.delete(searchObject,null);
	} */
	public void deleteAllAffBpc(UserToken userToken,AffBpc searchObject) throws TCGMException
	{
		String methodName = "deleteAllAffBpc(UserToken,AffBpc)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken);

		affBpcDao.delete(searchObject,null);
	}

	/**
	 *
	 * @param userToken UserToken
	 * @param affBpcList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedAffBpc(UserToken userToken,Vector affBpcList) throws TCGMException
	{
		String methodName = "deleteSelectedAffBpcTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken);

		Vector selectedAffBpcList = this.getSelectedAffBpc(affBpcList);

		affBpcDao.delete(selectedAffBpcList);

	}

	/**
	 * This method retrieves the distinct Supp Aff Id's which will
	 * help the user to see only a specific Supp Aff's records.
	 * @param userToken UserToken
	 * @throws TCGMException
	 */
	public ArrayList getSupAff(UserToken userToken) throws TCGMException
	{
		String methodName = "getSupAff";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken);

		return affBpcDao.getSupAff(null);

	}

	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param affBpcTranList Vector
	 * @throws TCGMException
	 */
// 5-14-03 Attempt to chnage saveSelected to fit my current needs
/*	public void saveSelectedAffBpcTran(UserToken userToken,Vector affBpcTranList) throws TCGMException
	{
		String methodName = "saveAffBpcTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken);

		Vector selectedAffBpcTranList = this.getSelectedAffBpcTran(affBpcTranList);

		affBpcTranDao.update(selectedAffBpcTranList);
	} */

	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param affBpcTran AffBpcTran
	 * @throws TCGMException
	 */
/*	public void addNewAffBpcTran(UserToken userToken,AffBpcTran affBpcTran) throws TCGMException
	{
		String methodName = "addNewAffBpcTran(UserToken,AffBpcTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken);

		affBpcTranDao.insert(affBpcTran,null);
	} */
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject AffBpcTran
	 * @param newVals AffBpcTran
	 * @throws TCGMException
	 */
/*	public void massUpdate(UserToken userToken,AffBpcTran searchObject,AffBpcTran newVals) throws TCGMException
	{
		String methodName = "massUpdate(UserToken,AffBpcTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcTranDao affBpcTranDao = daoFactory.getAffBpcTranDao(userToken);
		affBpcTranDao.massUpdate(searchObject,newVals);
	} */
	/**
	 * Given a vector of AffBpcTran, returns the ones that have the selected flag set to true
	 * @param affBpcTranList Vector
	 * @return Vector
	 */
/*	public Vector getSelectedAffBpcTran(Vector affBpcTranList)
	{
		Vector selectedAffBpcTran = new Vector();

		for(int i = 0; i < affBpcTranList.size(); i++)
		{
			AffBpcTran affBpcTran = (AffBpcTran)affBpcTranList.elementAt(i);

			if(affBpcTran.getAffBpc().isSelected())
			{
				selectedAffBpcTran.add(affBpcTran);
			}
		}

		return selectedAffBpcTran;
	} */

	/**
	 * Given a vector of AffBpc, returns the ones that have the selected flag set to true
	 * @param affBpcList Vector
	 * @return Vector
	 */
	public Vector getSelectedAffBpc(Vector affBpcList)
	{
		Vector selectedAffBpc = new Vector();

		for(int i = 0; i < affBpcList.size(); i++)
		{
			AffBpc affBpc = (AffBpc)affBpcList.elementAt(i);

			if(affBpc.isSelected())
			{
				selectedAffBpc.add(affBpc);
			}
		}

		return selectedAffBpc;
	}
	
	public void upload(UserToken userToken,String fileName,String cycle) throws TCGMException,IOException{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffBpcDao affBpcDao = daoFactory.getAffBpcDao(userToken);
		affBpcDao.upload(fileName,cycle);
	}
}