/*
 * Created on Jun 17, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.helpers;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.data.AsrUsageDao;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AsrUsageMngr implements TCGMMngr{
	
	public String className = null;
	
	private static Logger myLogger = Logger.getLogger("AsrUsageMngr");

		/**
		 * Default COnstructor
		 */
		public AsrUsageMngr()
		{
			
		}
		/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Asr object with search criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getAsr(UserToken userToken,ASRUsage searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken,searchObject);
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
	public Vector getAsr(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken,searchObject,pagingFilter);
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
	public Vector getAsr(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken,searchObject,pagingFilter,sortObject);
		return asrDao.getVO();
	}
	
	/*This below method is added to handel TCGM ASR Suff aff selection*/
	
	public Vector getAsrDataForSuffAffSelected(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter,Sort sortObject,String query,boolean flag) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrDao = daoFactory.getAsrUsageDaoForSuffAff(userToken,searchObject,pagingFilter,sortObject,query,flag);
		return asrDao.getVoForSuffAffSelected(query,flag);
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Asr object with search criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,ASRUsage searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken,searchObject);
		return asrDao.getCount();
	}
	
	public ArrayList getSupAff(UserToken userToken) throws TCGMException
		{
			String methodName = "getSupAff";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken);			

			return asrDao.getSupAff(null);

		}

	
		/*****************************************************************************************/
	/**
	 * Given a vector of AsrTran, returns the ones that have the selected flag set to true
	 * @param asrTranList Vector
	 * @return Vector
	 */
	
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
			ASRUsage asr = (ASRUsage)asrList.elementAt(i);
			
			if(asr.isSelected())
			{		
				this.myLogger.error("ASR is adding");
				selectedAsr.add(asr);
			}
		}
		this.myLogger.error("Size of vector :"+selectedAsr.size());
		return selectedAsr;
	}
	
	/*****************************************************************************************/
		/**
		 * @param userToken UserToken
		 * @param asrTranList Vector
		 * @param copyToModel String
		 * @throws TCGMException
		 */
		public boolean copySelectedAsrTran(UserToken userToken,Vector asrTranList,String copyToModel,String actionCode,String userId) throws TCGMException, TCGMDuplicateItemException
		{
			String methodName = "copySelectedAsrTran(UserToken,Vector)";

			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken);
			Vector selectedAsrTranList = this.getSelectedAsr(asrTranList);
			converToVectorAsr(selectedAsrTranList,actionCode,copyToModel);
			return asrDao.insert(selectedAsrTranList,Integer.parseInt(copyToModel),actionCode,null,userId);
		}
		
		public boolean saveSelectedAsrTran(UserToken userToken,Vector asrTranList,String copyToModel,String actionCode) throws TCGMException, TCGMDuplicateItemException
		{
			String methodName = "saveSelectedAsrTran(UserToken,Vector)";
	
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken);
			Vector selectedAsrTranList = this.getSelectedAsr(asrTranList);
			converToVectorAsr(selectedAsrTranList,actionCode,copyToModel);
			return asrDao.update(selectedAsrTranList);
			 
		}
		/**
		 * @param userToken UserToken
		 * @param searchObject AsrTran
		 * @param copyToModel String
		 * @throws TCGMException
		 */
		public boolean copyAllAsrTran(Vector asrTranList,UserToken userToken,ASRUsage searchObject,String copyToModel,String actionCode,String aff,String userId,String cycleId) throws TCGMException, TCGMDuplicateItemException
		{
			String methodName = "copyAllAsrTran(UserToken,Vector)";

			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken);			
			
			return asrDao.insert(Integer.parseInt(copyToModel),actionCode,aff,null,userId,cycleId);
		}
		
	public Vector converToVectorAsr(Vector  vecAsrusage,String actionCode,String copyToModel){
			
				Vector ret=new Vector();
				for(int i=0;i<vecAsrusage.size();i++){
				  converToAsr((ASRUsage)vecAsrusage.get(i),actionCode,copyToModel);
				}
		return ret;
	}
		
		
	public void converToAsr(ASRUsage asrusage,String actionCode,String copyToModel){			
			if(copyToModel.equals("") || null ==copyToModel){
				copyToModel="-1";
			}
			
		asrusage.setActionCode(actionCode);
		asrusage.setModelId(copyToModel);
		asrusage.setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		   
		}
		
		public void deleteAllAsrTran(UserToken userToken,ASRUsage searchObject,Vector asrTrans,String aff,String cycleId)throws TCGMException, TCGMDuplicateItemException{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
					AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken,searchObject);
					asrDao.delete(new ASRUsage(),null,aff,cycleId);
		}
		
		public void deleteSelectedAsrTran(UserToken userToken,ASRUsage searchObject,Vector asrTrans)throws TCGMException, TCGMDuplicateItemException{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			AsrUsageDao asrDao = daoFactory.getAsrUsageDao(userToken,searchObject);
			Vector selected=getSelectedAsr(asrTrans);
			asrDao.delete(selected);
		}
	
}
