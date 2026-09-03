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
 * updated - A.Winter 7/11/05
 */
public class AsrMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();

	/**
	 * Default COnstructor
	 */
	public AsrMngr()
	{
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Asr object with search criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getAsr(UserToken userToken,Asr searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrDao asrDao = daoFactory.getAsrDao(userToken,searchObject);
		return asrDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Asr object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getAsr(UserToken userToken,Asr searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrDao asrDao = daoFactory.getAsrDao(userToken,searchObject,pagingFilter);
		return asrDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Asr object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getAsr(UserToken userToken,Asr searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrDao asrDao = daoFactory.getAsrDao(userToken,searchObject,pagingFilter,sortObject);
		return asrDao.getVO();
	}
	/**
	 * 1.  Get a RowSet of Asr objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each Asr to an AsrTran
	 * 3.  Call the asrTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject Asr object
	 * @param actionCode action code for asr tran
	 * @throws TCGMException
	 */
//	A.Winter 7/15/05 - made method boolean
	public boolean addAllAsrToTrans(UserToken userToken,Asr searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllAsrToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrDao asrDao = daoFactory.getAsrDao(userToken,searchObject);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);
		
		boolean res = true;		
		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = asrDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				Asr asr = asrDao.getAsrFromCurrentRow(rs);
				AsrTran asrTran = this.convertAsrToTran(asr,actionCode);
			//	asrTranDao.insert(asrTran,conn);
				res = asrTranDao.insert(asrTran,conn);
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
	 * @param asrList Vector of Asr objects
	 * @param actionCode action code for AsrTran
	 * @throws TCGMException
	 */
	public boolean addSelectedAsrToTrans(UserToken userToken,Vector asrList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedAsrToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		Vector asrTranList = null;
		Vector selectedAsrList = this.getSelectedAsr(asrList);

		asrTranList = convertAsrListToTran(selectedAsrList,actionCode);
	// A.Winter - made result as boolean
		boolean res = asrTranDao.insert(asrTranList);
		return res;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param asrList Vector of Asr objects
	 * @param actionCode action code for AsrTran
	 * @return Vector
	 */
	public Vector convertAsrListToTran(Vector asrList,String actionCode)
	{
		String methodName = "convertAsrListToTran(Vector,String)";

		Vector asrTranList = new Vector();

		for(int i = 0; i < asrList.size(); i++)
		{
			asrTranList.add(convertAsrToTran((Asr)asrList.elementAt(i),actionCode) );
		}
		return asrTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param asr Asr object
	 * @param actionCode action code for AsrTran
	 * @return AsrTran
	 */
	public AsrTran convertAsrToTran(Asr asr,String actionCode)
	{
		String methodName = "convertAsrToTran(Asr,String)";

		AsrTran asrTran = new AsrTran();
		asrTran.setAsr( asr );
		asrTran.setActionCode(actionCode);
		asrTran.getAsr().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		asrTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return asrTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getAsrTran(UserToken userToken,AsrTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken,searchObject);
		return asrTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getAsrTran(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		String methodName = "getAsrTran(UserToken,AsrTran,PagingFilter)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken,searchObject,pagingFilter);
		return asrTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getAsrTran(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		String methodName = "getAsrTran(UserToken,AsrTran,PagingFilter,Sort)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken,searchObject,pagingFilter,sortObject);
		return asrTranDao.getVO();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Asr object with search criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,Asr searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrDao asrDao = daoFactory.getAsrDao(userToken,searchObject);
		return asrDao.getCount();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,AsrTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken,searchObject);
		return asrTranDao.getCount();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject AsrTran
	 * @throws TCGMException
	 */
	public void deleteAllAsrTran(UserToken userToken,AsrTran searchObject) 
			throws TCGMException,TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllAsrTran(UserToken,AsrTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		asrTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param asrTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedAsrTran(UserToken userToken,Vector asrTranList) throws TCGMException
	{
		String methodName = "deleteSelectedAsrTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		Vector selectedAsrTranList = this.getSelectedAsrTran(asrTranList);

		asrTranDao.delete(selectedAsrTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param asrTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedAsrTran(UserToken userToken,Vector asrTranList) throws TCGMException
	{
		String methodName = "saveAsrTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		Vector selectedAsrTranList = this.getSelectedAsrTran(asrTranList);

		asrTranDao.update(selectedAsrTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param asrTranList Vector
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copySelectedAsrTran(UserToken userToken,Vector asrTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copySelectedAsrTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		Vector selectedAsrTranList = this.getSelectedAsrTran(asrTranList);

		asrTranDao.copy(selectedAsrTranList,copyToModel);
	}
	/**
	 * @param userToken UserToken
	 * @param searchObject AsrTran
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copyAllAsrTran(UserToken userToken,AsrTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copyAllAsrTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		asrTranDao.copy(searchObject,copyToModel);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param asrTranList Vector
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishSelectedAsrTran(UserToken userToken,Vector asrTranList, boolean blnFlag) throws TCGMException
	{
		String methodName = "publishAsrTran(UserToken,Vector, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		Vector selectedAsrTranList = this.getSelectedAsrTran(asrTranList);

		for(int i = 0; i<selectedAsrTranList.size(); i++)
		{
			if(blnFlag){
				((AsrTran)selectedAsrTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
			}
			else{
				((AsrTran)selectedAsrTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
			}
			
		}

		asrTranDao.update(selectedAsrTranList);
	}
	/**
	 * @param userToken UserToken
	 * @param searchObject AsrTran
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishAllAsrTran(UserToken userToken,AsrTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAllAsrTran(UserToken,AsrTran, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken,searchObject);

		//asrTranDao.publishAll();
		asrTranDao.publishAll(searchObject, blnFlag);

	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param asrTran AsrTran
	 * @throws TCGMException
	 */
	public boolean addNewAsrTran(UserToken userToken,AsrTran asrTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewAsrTran(UserToken,AsrTran";
		// Date Modified: 12/14/2005
		// Modified By  : Udaya B Aravapalli. 
		boolean dup = true; //Based on the existing code dup =true means there are no duplicates. :) 		

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);

		dup = asrTranDao.insert(asrTran,null);
		return dup;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject AsrTran
	 * @param newVals AsrTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,AsrTran searchObject,AsrTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,AsrTran,AsrTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);
		asrTranDao.massUpdate(searchObject,newVals);
	}
	/**
	 * Used to do a mass update by creating trans records from existing asr records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of Asr
	 * 2.  Create an asrTran and put the asr from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public Vector massUpdate(UserToken userToken,Asr searchObject,AsrTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,Asr,AsrTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrDao asrDao = daoFactory.getAsrDao(userToken,searchObject);
		AsrTranDao asrTranDao = daoFactory.getAsrTranDao(userToken);
		RowSet rs = asrDao.getRS();
		Vector asrErrorRecList = new Vector();
		Vector asrValidRecList = new Vector();
		Vector asrOutRecList = new Vector();
		boolean result = true;
	    boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection();

			while(rs.next())
			{
				AsrTran asrTran = new AsrTran();
				Asr asrObj = new Asr();
				asrObj = asrDao.getAsrFromCurrentRow(rs);
				asrTran.setAsr(asrObj);

				asrTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					asrTran.setActionCode(TCGMUtil.getNewValue(asrTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
					asrTran.setActionCode(TCGMUtil.getNewValue(asrTran.getActionCode(),newVals.getActionCode()));
				}
				asrTran.setPublishFlag(TCGMUtil.getNewValue(asrTran.getPublishFlag(),newVals.getPublishFlag()));
				asrTran.getAsr().setProductOrigin(TCGMUtil.getNewValue(asrTran.getAsr().getProductOrigin(),newVals.getAsr().getProductOrigin()));
				//RPT Aff
				if (newVals.getAsr().getRptAff().equalsIgnoreCase("0" + TCGMConstants.LBL_SUP))
				{
					asrTran.getAsr().setRptAff(asrTran.getAsr().getSupAff());
				}
				else
				{
					asrTran.getAsr().setRptAff(TCGMUtil.getNewValue(asrTran.getAsr().getRptAff(),newVals.getAsr().getRptAff()));
				}
				//Inv Cd
				asrTran.getAsr().getRptProduct().setInvCode(TCGMUtil.getNewValue(asrTran.getAsr().getRptProduct().getInvCode(),newVals.getAsr().getRptProduct().getInvCode()));
				//Rpt Prod List
				if (newVals.getAsr().getRptProduct().getList().equalsIgnoreCase("000" + TCGMConstants.LBL_SUP))
				{
					asrTran.getAsr().getRptProduct().setList(asrTran.getAsr().getSupProduct().getList());
				}
				else
				{
					asrTran.getAsr().getRptProduct().setList(TCGMUtil.getNewValue(asrTran.getAsr().getRptProduct().getList(),newVals.getAsr().getRptProduct().getList()));
				}
				//Rpt Prod Label
				if (newVals.getAsr().getRptProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_SUP))
				{
					asrTran.getAsr().getRptProduct().setLabel(asrTran.getAsr().getSupProduct().getLabel());
				}
				else if(newVals.getAsr().getRptProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					asrTran.getAsr().getRptProduct().setLabel("   ");
				}
				else
				{
					asrTran.getAsr().getRptProduct().setLabel(TCGMUtil.getNewValue(asrTran.getAsr().getRptProduct().getLabel(),newVals.getAsr().getRptProduct().getLabel()));
				}
				//Rpt Prod Size				
				if (newVals.getAsr().getRptProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_SUP))
				{
					asrTran.getAsr().getRptProduct().setSize(asrTran.getAsr().getSupProduct().getSize());
				}
				else if(newVals.getAsr().getRptProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					asrTran.getAsr().getRptProduct().setSize("   ");
				}
				else
				{
					asrTran.getAsr().getRptProduct().setSize(TCGMUtil.getNewValue(asrTran.getAsr().getRptProduct().getSize(),newVals.getAsr().getRptProduct().getSize()));
				}
				//Rpt Prod Pack
				if (newVals.getAsr().getRptProduct().getPack().equalsIgnoreCase("0" + TCGMConstants.LBL_SUP))
				{
					asrTran.getAsr().getRptProduct().setPack(asrTran.getAsr().getSupProduct().getPack());
				}
				else
				{
					asrTran.getAsr().getRptProduct().setPack(TCGMUtil.getNewValue(asrTran.getAsr().getRptProduct().getPack(),newVals.getAsr().getRptProduct().getPack()));
				}
				//Sup Aff				
				if(newVals.getAsr().getSupAff().equalsIgnoreCase("0" + TCGMConstants.LBL_RPT))
				{
					asrTran.getAsr().setSupAff(asrTran.getAsr().getRptAff());
				}
				else
				{
					asrTran.getAsr().setSupAff(TCGMUtil.getNewValue(asrTran.getAsr().getSupAff(),newVals.getAsr().getSupAff()));
				}
				asrTran.getAsr().setSupKey(TCGMUtil.getNewValue(asrTran.getAsr().getSupKey(),newVals.getAsr().getSupKey()));
				//Sup Inv Cd
				asrTran.getAsr().getSupProduct().setInvCode(TCGMUtil.getNewValue(asrTran.getAsr().getSupProduct().getInvCode(),newVals.getAsr().getSupProduct().getInvCode()));
				//Sup List				
				if (newVals.getAsr().getSupProduct().getList().equalsIgnoreCase("000" + TCGMConstants.LBL_RPT))
				{
					asrTran.getAsr().getSupProduct().setList(asrTran.getAsr().getRptProduct().getList());
				}
				else
				{
					asrTran.getAsr().getSupProduct().setList(TCGMUtil.getNewValue(asrTran.getAsr().getSupProduct().getList(),newVals.getAsr().getSupProduct().getList()));
				}
				//Sup Label				
				if(newVals.getAsr().getSupProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_RPT))
				{
					asrTran.getAsr().getSupProduct().setLabel(asrTran.getAsr().getRptProduct().getLabel());
				}
				else if(newVals.getAsr().getSupProduct().getLabel().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					asrTran.getAsr().getSupProduct().setLabel("   ");
				}
				else
				{
					asrTran.getAsr().getSupProduct().setLabel(TCGMUtil.getNewValue(asrTran.getAsr().getSupProduct().getLabel(),newVals.getAsr().getSupProduct().getLabel()));
				}
				//Sup Size			
				if (newVals.getAsr().getSupProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_RPT))
				{
					asrTran.getAsr().getSupProduct().setSize(asrTran.getAsr().getRptProduct().getSize());
				}
				else if(newVals.getAsr().getSupProduct().getSize().equalsIgnoreCase(TCGMConstants.LBL_BNK)){
					asrTran.getAsr().getSupProduct().setSize("   ");
				}
				else
				{
					asrTran.getAsr().getSupProduct().setSize(TCGMUtil.getNewValue(asrTran.getAsr().getSupProduct().getSize(),newVals.getAsr().getSupProduct().getSize()));
				}
				//Sup Pack
				if (newVals.getAsr().getSupProduct().getPack().equalsIgnoreCase("0" + TCGMConstants.LBL_RPT)) 
				{
					asrTran.getAsr().getSupProduct().setPack(asrTran.getAsr().getRptProduct().getPack());
				}
				else
				{
					asrTran.getAsr().getSupProduct().setPack(TCGMUtil.getNewValue(asrTran.getAsr().getSupProduct().getPack(),newVals.getAsr().getSupProduct().getPack()));
				}
				//Usage
				asrTran.getAsr().setUsage(TCGMUtil.getNewValue(asrTran.getAsr().getUsage(),newVals.getAsr().getUsage()));
				
				TCGMDataValidation dataVal = new TCGMDataValidation("ASR");
				boolean validate = true;
				validate = dataVal.validateAsr(asrTran.getAsr(),false);
				// Validating the record before submitting to mass update
				if(!validate){
					asrErrorRecList.add(asrObj);
				}else{
					//asrValidRecList.add(asrTran);
					//Record by record insert
					 result = asrTranDao.insert(asrTran,conn);
					  if(!result){
						  blnFlag = false;
						  asrErrorRecList.add(asrTran.getAsr());
					  }
				}
			}
			//All records insert at a time
			/*asrOutRecList = asrTranDao.insert(asrValidRecList,conn);
			for(int j=0;j<asrOutRecList.size();j++){
				asrErrorRecList.add(((AsrTran)asrOutRecList.elementAt(j)).getAsr());
			}*/
			//Need to send back the asrErrorList object consisting of duplicate records and validation failed records
		}
		catch(SQLException sqle)
		{
			throw new TCGMException (className, methodName, sqle.toString());
		}
		finally
		{
			if(conn!=null)
			{
				SQLUtil.closeConnection(conn);
			}
		}
		return asrErrorRecList;
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of AsrTran, returns the ones that have the selected flag set to true
	 * @param asrTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedAsrTran(Vector asrTranList)
	{
		Vector selectedAsrTran = new Vector();

		for(int i = 0; i < asrTranList.size(); i++)
		{
			AsrTran asrTran = (AsrTran)asrTranList.elementAt(i);

			if(asrTran.getAsr().isSelected())
			{
				selectedAsrTran.add(asrTran);
			}
		}

		return selectedAsrTran;
	}
	/**
	 * Given a vector of Asr, returns the ones that have the selected flag set to true
	 * @param asrList Vector
	 * @return Vector
	 */
	public Vector getSelectedAsr(Vector asrList)
	{
		Vector selectedAsr = new Vector();

		for(int i = 0; i < asrList.size(); i++)
		{
			Asr asr = (Asr)asrList.elementAt(i);

			if(asr.isSelected())
			{
				selectedAsr.add(asr);
			}
		}

		return selectedAsr;
	}
	
	// The below method is added for AsrUsage Upload at 10th May 2024 by Debajyoti
	public void upload(UserToken userToken,String fileName,String cycle) throws TCGMException,IOException{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrUsageDao = daoFactory.getAsrUsageDao(userToken);
		asrUsageDao.upload(fileName,cycle);
	}
	
	public Vector getAsrUsage(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrUsageDao = daoFactory.getAsrUsageDao(userToken,searchObject,pagingFilter,sortObject);
		return asrUsageDao.getVO();
	}
	
	public long getCount(UserToken userToken,ASRUsage searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrUsageDao = daoFactory.getAsrUsageDao(userToken,searchObject);
		return asrUsageDao.getCount();
	}
}