package abbott.ai.tcgm.helpers;

import java.util.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.form.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */

public class DatasetMngr implements TCGMMngr
{
	/**
	 * Default Constructor
	 */
	public DatasetMngr()
	{
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Dataset object with search criteria
	 * @return Vector of Dataset objects
	 * @throws TCGMException
	 */
	public Vector getDataset(UserToken userToken,Dataset searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		return datasetDao.getVO(searchObject);
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param datasetId id of the dataset
	 * @return Dataset object
	 * @throws TCGMException
	 */
	public Dataset getDatasetById(UserToken userToken,int datasetId) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		return datasetDao.getDatasetById(datasetId);
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param datasetName name of the dataset
	 * @return Dataset object
	 * @throws TCGMException
	 */
	public Dataset getDatasetByName(UserToken userToken,String datasetName) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		return datasetDao.getDatasetByName(datasetName);
	}

	/**
	 * @param userToken UserToken
	 * @param tableName Name of the table
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getDatasetByTableName(UserToken userToken,String tableName) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		return datasetDao.getDatasetByTableName(tableName);
	}

	/**
	 * @param userToken UserToken
	 * @param datasetId int
	 * @throws TCGMException
	 */
	public void deleteDatasetById(UserToken userToken, int datasetId) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		datasetDao.deleteDataset(datasetId);
	}
	

	/**
	 * @param userToken UserToken
	 * @param datasetId int
	 * @throws TCGMException
	 */
	public void deleteRateSetById(UserToken userToken, int datasetId) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		datasetDao.deleteRateSet(datasetId);
	}

	/**
	 * This method was created primarily to save rate set data into the dataset table.
	 * I tried to make it generic so that anyone could use it to save any kind of dataset record.
	 *
	 * @param userToken UserToken
	 * @param dataset Dataset
	 * @param modelName name of the model for this dataset
	 * @throws TCGMException
	 */
	public void saveDataset(UserToken userToken,Dataset dataset,String modelName) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);

		int modelId = -1;

		/**
		 * I need to determine if I am doing an insert or an update.
		 * I can assume that if there is not dataset_table_id in the dataset object then
		 * I am doing a create else doing an update.
		 */
		if(dataset.getDatasetTableId().equals(""))
		{
			datasetDao.insert(dataset,modelId,null);
		}
		else
		{
			datasetDao.update(dataset,null);
		}
	}
	
	
	/**
		 * This method was created primarily to Update rate set data into the dataset table.
		 * I tried to make it generic so that anyone could use it to save any kind of dataset record.
		 * Gain 04-01-06
		 * @param userToken UserToken
		 * @param dataset Dataset
		 * @throws TCGMException 
		 */
		public void updateDataset(UserToken userToken,Dataset dataset) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
			datasetDao.update(dataset,null);

		}

	/**
		 * This method was created primarily to Update rate set data into the dataset table.
		 * @param userToken UserToken
		 * @param dataset Dataset
		 * @throws TCGMException 
		 */
		public void updateDatasetTableName(UserToken userToken,Dataset dataset) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
			datasetDao.updateTableName(dataset,null);

		}

	/**
	 * @param userToken UserToken
	 * @param rateSetForm MngRateSetsForm
	 * @param modelName String
	 * @throws TCGMException
	 */
	public void copyRateSet(UserToken userToken,MngRateSetsForm rateSetForm,String modelName) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);

		/**
		 * Based on the design of the application I used the Factor model type to get the modelDao.
		 * ModelDao and OracleModelDao are abstract and so I can't create an instance of them.
		 * I needed to use the getModelIdByName method so I just picked a type at random so I could
		 * get a modelDao that would allow me to call that method.  The choice of Factor as the type
		 * is totally arbitrary and if a better way exists to get this information I would
		 * recommend using it.
		 */
		ModelDao modelDao = daoFactory.getModelDao(userToken,TCGMModel.Type.FACTOR);

		int modelId = modelDao.getModelIdByName(modelName);

		datasetDao.copyRateSet(rateSetForm.getEditRateSet(),rateSetForm.getCopyFromDatasetTableIdInt(),modelId);
	}
	
	public HashMap getSalesType(UserToken userToken) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		return datasetDao.getSalesType();
	}
	
	public HashMap getSalesList(UserToken userToken,String datasetTableId) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		DatasetDao datasetDao = daoFactory.getDatasetDao(userToken);
		return datasetDao.getSalesList(datasetTableId);
	}
}