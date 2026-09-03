package abbott.ai.tcgm.data;

import javax.sql.*;
import java.util.*;
//import java.sql.Connection;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.action.form.*;

/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public interface ModelDao extends TCGMDao // extends TCGMDao
{
	/**
	 *
	 * @param model
	 * @return
	 * @throws TCGMException
	 */
   public RowSet getRS(TCGMModel model) throws TCGMException;

   /**
	*
	* @param model
	* @return
	* @throws TCGMException
	*/
   public Vector getVO(TCGMModel model) throws TCGMException;

   /**
	*
	* @param model
	* @throws TCGMException
	*/
   public int createModel(TCGMModel model) throws TCGMException;

   /**
	*
	* @param model
	* @param modelBase
	* @param options
	* @throws TCGMException
	*/
   public int createModel(TCGMModel model, TCGMModel modelBase, ModelCopyOptions options) throws TCGMException;

   /**
	*
	* @param modelId
	* @throws TCGMException
	*/
/* 10-9-03 These methods are now submitted as batch jobs. They no longer need to be
		   defined as DAO objects.
   public void deleteModel(int modelId, TCGMModel.Type modelType) throws TCGMException;
   public void closeModel(int modelId) throws TCGMException;
   public void compactModel(int modelId) throws TCGMException;
*/

   /**
	*
	* @param modelId
	* @return
	* @throws TCGMException
	*/
   public TCGMModel getModel( int modelId ) throws TCGMException;

   /**
	*
	* @param modelId
	* @return
	* @throws TCGMException
	*/
   public TCGMModel getClosedModel( int modelId ) throws TCGMException;

   /**
	* @param model
	* @throws TCGMException
	*/
   public void updateModel( TCGMModel  model) throws TCGMException;

   /**
	* @param model
	* @throws TCGMException
	*/
   public void updateModelStatus( TCGMModel  model) throws TCGMException;
   /**
	* @param model
	* @throws TCGMException
	*/
   public void updateModelParms( TCGMModel  model) throws TCGMException;
   /**
	* @param modelName
	* @return
	* @throws TCGMException
	*/

   // 7-14-03 added by bd to make this routine accessible from the perpetual form;
   public String getModelParm( int modelId , String parmName) throws TCGMException;

   // 10-18-05 added by bd to make this routine accessible when any job is called and parms 
   //          need to be added to the PARAMETER table. Hence, this functionality can take 
   //          place from action objects now.
   public void setModelParmII( int modelId , String parmName, String parmValue) throws TCGMException;

   public int getModelIdByName(String modelName) throws TCGMException;
   public boolean exists(String modelName) throws TCGMException;
   public boolean exists(int modelId) throws TCGMException;
   public String getStringValueFromSql(String sql) throws TCGMException;
   public String getModelStatus(String modelId) throws TCGMException;
   public int getLastModelCreatedByUser(TCGMModel.Type modelType) throws TCGMException;
   public String getModelJobStatus(int modelId,String jobName) throws TCGMException;
   
}