package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;

import java.sql.*;
//import javax.sql.*;
import java.util.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public interface DatasetDao extends TCGMDao
{
    /**
     * @param searchObject Dataset
     * @return Vector
     * @throws TCGMException
     */
    public Vector getVO(Dataset searchObject) throws TCGMException;
    /**
     * @param searchObject Dataset
     * @param wildcard boolean
     * @return Vector
     * @throws TCGMException
     */
    public Vector getVO(Dataset searchObject, boolean wildcard) throws TCGMException;
    /**
     * @param datasetId id of the dataset
     * @return Dataset
     * @throws TCGMException
     */
    public Dataset getDatasetById(int datasetId) throws TCGMException;
    /**
     * @param datasetName name of the dataset
     * @return Dataset
     * @throws TCGMException
     */
    public Dataset getDatasetByName(String datasetName) throws TCGMException;
	/**
	 * @param datasetName name of the dataset
	 * @return Dataset
	 * @throws TCGMException
	 */
	// 8-3-05 Add method to select Unit specific dataset
	public Dataset getUnitDatasetByName(String datasetName) throws TCGMException;   
    /**
     * @param tableName name of the table
     * @return Vector
     * @throws TCGMException
     */
    public Vector getDatasetByTableName(String tableName) throws TCGMException;
    /**
     * @param datasetId Dataset Table Id
     * @throws TCGMException
     */
    public void deleteDataset(int datasetId) throws TCGMException;
    /**
     * @param datasetId Dataset Table Id
     * @throws TCGMException
     */
    public void deleteRateSet(int datasetId) throws TCGMException;
    /**
     * @param dataset Dataset
     * @param modelId The model id to use when creating the model_dataset record.
     * @param conn Connection
     * @throws TCGMException
     */
    public int insert(Dataset dataset,int modelId,Connection conn) throws TCGMException;
    /**
     * @param dataset Dataset
     * @param conn Connection
     * @throws TCGMException
     */
    public void update(Dataset dataset,Connection conn) throws TCGMException;

	/**
	 * @param dataset Dataset
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void updateTableName(Dataset dataset,Connection conn) throws TCGMException;
    public boolean exists(int datasetId) throws TCGMException;

    /**
     * @param dataset Dataset
     * @param copyFromDatasetTableId int
     * @throws TCGMException
     */
    public void copyRateSet(Dataset dataset,int copyFromDatasetTableId,int modelId) throws TCGMException;
    
    public HashMap getSalesType() throws TCGMException;
    public HashMap getSalesList(String datasetTableId) throws TCGMException;
}