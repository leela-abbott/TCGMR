package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.sql.*;
import org.apache.log4j.Logger;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.*;

import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

//import abbott.ai.tcgm.action.CreatePerpetualModel;
//import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.exception.TCGMException;
//import abbott.ai.tcgm.helpers.*;
/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public abstract class OracleModelDao extends OracleDao implements ModelDao
{
  private static Logger myLogger = Logger.getLogger( "ProcessScheduler" );
  protected String parmTable;
  protected ActionErrors errors = new ActionErrors();
  protected OracleModelDao() {}

  public abstract int createModel(TCGMModel model, TCGMModel baseModel, ModelCopyOptions options) throws TCGMException;
  protected abstract TCGMModel loadModelParms(TCGMModel model) throws TCGMException;
  protected abstract TCGMModel loadModelParmsAll(TCGMModel model) throws TCGMException;
  static long timer = 0;

  /**
   * @param searchModel
   */
  protected void buildSearchList (TCGMModel searchModel)
  {
	this.searchList = new Vector();

	this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchModel.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
	this.searchList.add(new Search(DBConst.COL_MODEL_NAME,searchModel.getName() ));
	this.searchList.add(new Search(DBConst.COL_MODEL_TYPE,searchModel.getType()));
	if (!(searchModel.getStatus()== null))
	{
		this.searchList.add(new Search(DBConst.COL_MODEL_STATUS,searchModel.getStatus().toString(),TCGMConstants.ORACLE_IN_COMPARISON,true));
	}
  }
  /**
   * @param userToken
   */
  public OracleModelDao(UserToken userToken)
  {
	this.userToken = userToken;
	this.setEntityTable(DBConst.TABLE_MODEL);
	this.parmTable = this.schema + ".PARAMETER";
  }

  /**
   * @param searchObject
   * @return
   * @throws TCGMException
   */
  public RowSet getRS(TCGMModel searchObject) throws TCGMException
  {
	String methodName = "getRS";

	if (searchObject.getStatus() == null)
	{
		searchObject.setStatus(TCGMModel.Status.OPEN);
	}
	this.buildSearchList(searchObject);
	String whereClause = this.genWhereClause();
	try
	{
//	  String query = "SELECT * FROM " + getEntity() + " " + whereClause + " AND MODEL_STATUS='OPEN' ORDER BY MODEL";
	  String query = "SELECT * FROM " + getEntity() + " " + whereClause + " ORDER BY MODEL";
	  this.initRS(query,TCGMConstants.CACHED_ROWSET);
	  this.rs.execute();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException(this.className,methodName,sqle.toString());
	}
	return rs;
  }

  /**
   * @param modelId
   * @return
   * @throws TCGMException
   */
  public TCGMModel getModel( int modelId ) throws TCGMException
  {
	String methodName = "getModel( int modelId )";
	String parameterList = "Model Id: " + modelId;
	myLogger.debug("Model Id: " + modelId);

	TCGMModel searchModel = new TCGMModel();
	searchModel.setModelIdInt(modelId);
	searchModel.setStatus(TCGMModel.Status.OPENCLOSED);
	Vector v = new Vector();
	try
	{
	  this.getRS(searchModel);

	  while ( this.rs.next())
	  {
	  	 
		v.add( this.getModelFromCurrentRow() );          // Load vector with Model entity	  	
		//myLogger.info("Ending time for model Params load"+new Date().getTime());
	  }

	}
	catch(SQLException sqle)
	{
	  throw new TCGMException(this.className,methodName,sqle.toString());
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
	
	
	if (v.size() == 0)
	{
// A.Winter - temporary do not throw exception
	//  throw new TCGMItemNotFoundException(this.className, methodName, parameterList );
	myLogger.info("Model Id: " + modelId + " is not OPENED");
	return null;
	}
	else if (v.size() > 1)
	{
	  throw new TCGMException(this.className,methodName, parameterList  );
	}
	else
	{
	  return (TCGMModel) v.firstElement();
	}
  }
  /**
   * @param modelId
   * @return
   * @throws TCGMException
   */
  public TCGMModel getClosedModel( int modelId ) throws TCGMException
  {
	String methodName = "getModel( int modelId )";
	String parameterList = "Model Id: " + modelId;
	myLogger.debug("Model Id: " + modelId);

	TCGMModel searchModel = new TCGMModel();
	searchModel.setModelIdInt(modelId);
	searchModel.setStatus(TCGMModel.Status.CLOSED);
	Vector v = this.getVO(searchModel);
	if (v.size() == 0)
	{
// A.Winter - temporary do not throw exception
	//  throw new TCGMItemNotFoundException(this.className, methodName, parameterList );
	myLogger.info("Model Id: " + modelId + " is not CLOSED");
	return null;
	}
	else if (v.size() > 1)
	{
	  throw new TCGMException(this.className,methodName, parameterList  );
	}
	else
	{
	  return (TCGMModel) v.firstElement();
	}
  }
  /**
   * @return TCGMModel
   * @throws SQLException
   * @throws TCGMException
   */
  protected abstract TCGMModel getModelFromCurrentRow() throws SQLException, TCGMException;
  
  protected abstract TCGMModel getModelFromCurrentRowSet() throws SQLException, TCGMException;

  /**
   *
   * @param model
   * @return
   * @throws SQLException
   * @throws TCGMException
   */
  //protected abstract TCGMModel getModelParms(TCGMModel model) throws SQLException, TCGMException;
  /**
   * @param model
   * @return TCGMModel
   * @throws SQLException
   */
  protected TCGMModel loadModelCommonTraits(TCGMModel model) throws SQLException
  {
	model.setModelIdInt(this.rs.getInt(DBConst.COL_MODEL_ID) );
	model.setName(this.rs.getString(DBConst.COL_MODEL_NAME).trim() );

	// can be null
	if (this.rs.getString(DBConst.COL_MODEL_DESC) != null)
	{
	  model.setDesc(this.rs.getString(DBConst.COL_MODEL_DESC).trim() );
	}
	return model;
  }

  /**
   * @param searchObject
   * @return
   * @throws TCGMException
   */
  public Vector getVO(TCGMModel searchObject) throws TCGMException
  {
	String methodName = "getVO";
	Vector vec = new Vector();
	try
	{
	  this.getRS(searchObject);

	  while ( this.rs.next())
	  {
	  	//below statement commented on 11/27/2012 to stop load all the parameters 
		//vec.add( this.getModelFromCurrentRow() );          // Load vector with Model entity
	  	vec.add( this.getModelFromCurrentRowSet() );
		//myLogger.info("Ending time for model Params load"+new Date().getTime());
	  }

	}
	catch(SQLException sqle)
	{
	  throw new TCGMException(this.className,methodName,sqle.toString());
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
	return vec;
  }

  /**
   * @param model
   * @throws TCGMException
   */
  public void updateModel(TCGMModel model) throws TCGMException
  {
	String methodName = "updateModel(TCGMModel)";
	Connection conn = null;

	try
	{
	  // Check for an existing model before updateing
	  // Low risk of synchronization problem here.
	  if (!this.exists( model.getName() ))
	  {
		throw new TCGMItemNotFoundException( this.className,methodName, model.toString() );
	  }

	  conn = this.getConnection();
	  conn.setAutoCommit(false);
	  updateBaseModel( model ) ;
	  saveModelParms( model );
	  conn.commit();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, model.toString(), sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeConnection(conn);
	}
  }

  /**
   * @param model
   * @throws TCGMException
   */
  public void updateModelStatus(TCGMModel model) throws TCGMException
  {
	String methodName = "updateModelStatus(TCGMModel)";
	Connection conn = null;

	try
	{
	  // Check for an existing model before updateing
	  // Low risk of synchronization problem here.
	  if (!this.exists( model.getName() ))
	  {
		throw new TCGMItemNotFoundException( this.className,methodName, model.toString() );
	  }
	  conn = this.getConnection();
	  conn.setAutoCommit(false);
	  updateBaseModelStatus( model ) ;
	  conn.commit();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, model.toString(), sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeConnection(conn);
	}
  }

  /**
   *
   * @param model
   * @throws TCGMException
   */
  public void updateModelParms(TCGMModel model) throws TCGMException
  {
	String methodName = "updateModelParms(TCGMModel)";
	Connection conn = null;

	try
	{
	  // Check for an existing model before updating
	  // Low risk of synchronization problem here.
	  if (!this.exists( model.getName() ))
	  {
		throw new TCGMItemNotFoundException( this.className,methodName, model.toString() );
	  }

	  conn = this.getConnection();
	  conn.setAutoCommit(false);
	  saveModelParms( model );
	  conn.commit();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, model.toString(), sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeConnection(conn);
	}
  }


  /**
   * @param model
   * @return
   * @throws TCGMException
   */
  public int createModel(TCGMModel model) throws TCGMException
  {
	String methodName = "createModel(TCGMModel)";
	Connection conn = null;
	CallableStatement cs = null;
	String modelStatus = null;
	String newModelName = null;
	try
	{
	  // Check for an existing model with the same name.
	  // Low risk of synchronization problem here.

	  modelStatus = getModelStatusByName(model.getName());

	  if (modelStatus.equalsIgnoreCase(TCGMConstants.MODEL_STATUS_OPEN)    ||
	      modelStatus.equalsIgnoreCase(TCGMConstants.MODEL_STATUS_CLOSED)  ||
		  modelStatus.equalsIgnoreCase(TCGMConstants.MODEL_STATUS_ARCHIVED)   )
	  {
		String errMsg = "A model already exist with the name: " + model.getName();
		myLogger.error(errMsg);
		throw new TCGMException( "OracleModelDao", methodName, errMsg );
	  }

	  if (modelStatus.equalsIgnoreCase(TCGMConstants.MODEL_STATUS_DELETED)||
		  modelStatus.equalsIgnoreCase(TCGMConstants.MODEL_STATUS_PURGED)  )
	  {
		for (int i=1; i<999; i++)
		{
			newModelName = model.getName() + "(" + i + ")";
			if (!(this.exists( newModelName)))
			{	
				updateModelName(model.getName(), newModelName);
				i = 1000;  
			}
		}
	  }

	  conn = getConnection();
	  conn.setAutoCommit(false);

	 String sql = "{ ? = call " + this.schema + ".MODEL_CREATE(?, ?, ?) }";
	 // String sql = "{ ? = call " + this.schema + ".Common.button_model_create(?, ?, ?) }";

	  cs = conn.prepareCall(sql);
	  cs.registerOutParameter(1, Types.INTEGER);
	  cs.setString( 2, model.getName() );
	  cs.setString( 3, model.getDesc() );
	  cs.setString( 4, model.getType().toString() );
	  cs.executeUpdate();

	  int newModelId = cs.getInt(1);
	  model.setModelIdInt( newModelId );
	  this.saveModelParms(model);
	  this.updateModelAuditFields(newModelId);

	  conn.commit();
	  conn.setAutoCommit(true);

	  return newModelId;
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, model.toString(), sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeCS(cs);
	}
  }

  /**
   * @param model
   * @param conn
   * @throws TCGMException
   */
  abstract void saveModelParms (TCGMModel model) throws TCGMException;

  /**
   * @param modelName
   * @return
   * @throws TCGMException
   */
  public int getModelIdByName(String modelName) throws TCGMException
  {
	String methodName = "getModelIdByName(String)";
	String parameterList = "Model Name: " + modelName;
	int modelId=0;

	try
	{
	  String sql = "SELECT " + DBConst.COL_MODEL_ID + " FROM " + this.getEntity()
				 + " WHERE " + DBConst.COL_MODEL_NAME + " = '" + modelName + "'";

	  this.initRS(sql, TCGMConstants.CACHED_ROWSET );
	  this.rs.execute();

	  if ( this.rs.next() )
	  {
		modelId = this.rs.getInt(DBConst.COL_MODEL_ID);
	  }
	  else
// instead exception set error message   - A.Winter 6/17/05
	  {
	//	throw new TCGMItemNotFoundException(this.className,methodName, parameterList);

	modelId = 0;  // pointer that model doesn't exists A.Winter
//	this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.none_selected"));
	  }

	  return modelId;

	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
  }

  /**
	* @param modelType
	* @return
	* @throws TCGMException
	*/
   public int getLastModelCreatedByUser(TCGMModel.Type modelType) throws TCGMException
   {
	 String methodName = "getLastModelCreatedByUser(String)";
	 String parameterList = "Model Type: " + modelType;
	 int modelId=0;

	 try
	 {
		String sql =  "SELECT * FROM ( SELECT * FROM " + this.getEntity() + " WHERE trim(CREATE_USERNAME) = '" + this.userToken.getUserid().trim()  +
					  "' AND MODEL_STATUS = 'OPEN' AND MODEL_TYPE = '" + modelType + "' ORDER BY CREATE_DATETIME DESC) WHERE ROWNUM =1"; 		  

	   this.initRS(sql, TCGMConstants.CACHED_ROWSET );
	   this.rs.execute();

	   if ( this.rs.next() )
	   {
		 modelId = this.rs.getInt(DBConst.COL_MODEL_ID);
	   }
	   else
	   {

		 modelId = 0;  
	   }

	   return modelId;

	 }
	 catch(SQLException sqle)
	 {
	   throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	 }
	 finally
	 {
	   SQLUtil.closeRowSet(rs);
	 }
   }

  /**
   * @param modelName
   * @return String (model_status)
   * @throws TCGMException
   */
  public String getModelStatus(String modelId) throws TCGMException
  {
	String methodName = "getModelStatus(String)";
	String parameterList = "Model ID: " + modelId;
	String modelStatus="";

	try
	{
	  String sql = "SELECT " + DBConst.COL_MODEL_STATUS + " FROM " + this.getEntity()
				 + " WHERE " + DBConst.COL_MODEL_ID + " = '" + modelId + "'";

	  this.initRS(sql, TCGMConstants.CACHED_ROWSET );
	  this.rs.execute();

	  if ( this.rs.next() )
	  {
		modelStatus = this.rs.getString(DBConst.COL_MODEL_STATUS);
	  }
	  else
	  {
		throw new TCGMItemNotFoundException(this.className,methodName, parameterList);
	  }

	  return modelStatus;

	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
  }

  /**
   * @param modelName
   * @return String (model_status)
   * @throws TCGMException
   */
  public void updateModelName(String oldModelName, String newModelName) throws TCGMException
  {
	String methodName = "updateModelName(String oldModelName, String newModelName)";
	String parameterList = "Old Model Name: " + oldModelName + "New Model Name: " + newModelName;
	String modelStatus="";
	PreparedStatement ps = null;
	Connection conn = null;
	String sql = "UPDATE " + this.getEntity() + " SET " + DBConst.COL_MODEL_NAME + " = '" + newModelName + "' " 
			   + " WHERE TRIM(" + DBConst.COL_MODEL_NAME + ") = '" + oldModelName + "'";

	myLogger.info("updateBaseModelStatus(TCGMModel) sql = " + sql);

	try
	{
	    conn = this.getConnection();
		ps =  conn.prepareStatement(sql);
        ps.executeUpdate();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, sqle.toString() + ": " + sqle.getMessage() );
	}		
	finally
	{
	  SQLUtil.closePS(ps);
	  SQLUtil.closeConnection(conn);
	}		
  }

  protected Dataset getDatasetParm(int modelId, String parmName) throws TCGMException {
	  // In the case of a valid parameter id being passed in, but no longer pointing to a valid object
	  // the parameter value is updated to blank ("").
	  // This cleans up situations where a model or unit set used in an analysis calculation is deleted
	  // A more robust solution is to not allow the deletion of an item if it's used in another calculation

	  Dataset d = null;

	  // Look up the initial parameter value from the database. It is expected to be the id of a dataset if it exists.
	  String datasetId = this.getModelParm(modelId, parmName);

	  // Inital validation check for null or emptry strings
	  if (TCGMUtil.isEmpty(datasetId)) return null;

	  // Get Dataset dao to populate unit sets
	  DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(this.userToken, this.getConnection() );

	  // Translate string id into integer, watch for format exceptions
	  try {
		  	int idInt = Integer.parseInt(datasetId);
			try {
				d = dd.getDatasetById(idInt);
	 		}
	  		catch (TCGMItemNotFoundException tex) {
					this.setModelParm(modelId, parmName, "");
	  		}
		 }
	  	catch (NumberFormatException ex) {
		  logger.error("Failed to translate dataset id from string to integer", ex);
	  }

	  return d;
  }

  protected FactorModel getFactorModelParm(int parentModelId, String parmName) throws TCGMException {
	  // In the case of a valid parameter id being passed in, but no longer pointing to a valid object
	  // the parameter value is updated to blank ("").
	  // This cleans up situations where a model or unit set used in an analysis calculation is deleted
	  // A more robust solution is to not allow the deletion of an item if it's used in another calculation

	  String methodName = "getFactorModelParm(int parentModelId, String parmName)";
	  FactorModel m = null;

	  // Look up the initial parameter value from the database. It is expected to be the id of a factor model if it exists.
	  String modelId = this.getModelParm(parentModelId, parmName);

	  // Inital validation check for null or emptry strings
	  if (TCGMUtil.isEmpty(modelId)) return null;

	  // Get Factor Model Dao to populate factor model names
	  ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(this.userToken, TCGMModel.Type.FACTOR);

	  // Translate string id into integer, watch for format exceptions
	  try {
		  int idInt = Integer.parseInt(modelId);

		m = (FactorModel) md.getModel(idInt);
		if(m==null){
			this.setModelParm(parentModelId, parmName, "");
		}
		  /*if ( md.exists(idInt) ) {
			  m = (FactorModel) md.getModel(idInt);
		  }
		  else {
			  this.setModelParm(parentModelId, parmName, "");
		  }*/
	  }

	  catch (NumberFormatException ex) {
		  logger.error("Failed to translate model id from string to integer", ex);
	  }
	  catch(Exception e){
			logException(className,modelId,e);
  			throw new TCGMException( this.className, methodName, modelId, e.getMessage() );
  	  }

	  return m;
	}

  /**
   * @param modelId
   * @param parmName
   * @param parmValue
   * @param conn
   * @throws TCGMException
   */
  protected void setModelParm( int modelId , String parmName, String parmValue) throws TCGMException
  {
	String methodName = "setModelParm(modelId, parmName, parmValue)";
	String parameterList = "Model Id: " + modelId + ", Parm Name: " + parmName + ", Parm Value: " + parmValue;
	String sql = null;
	PreparedStatement ps = null;
	Connection conn = null;

	// Don't persist empty or null parmvalues
	if ( TCGMUtil.isEmpty(parmValue) ) return;

	try
	{
		conn = this.getConnection();
	  if (this.existsParm( modelId, parmName, conn ) )
	  {
		sql = "UPDATE " + this.parmTable + " SET parm_value='" + parmValue + "' where model_id = " + modelId + " AND parm_name='" + parmName + "'";
	  }
	  else
	  {
		sql = "INSERT INTO " + this.parmTable + " (model_id, parm_name, parm_value) VALUES (" + modelId + ",'" + parmName + "', '" + parmValue + "')";
	  }
	  ps = conn.prepareStatement(sql);
	  ps.executeUpdate();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, parameterList + "/nSQL:" + sql, sqle.toString() + ": " + sqle.getMessage() );
	}
	finally {
		SQLUtil.closePS(ps);
	//	SQLUtil.closeConnection(con);
	}
  }

  /**
   * @param modelId
   * @param parmName
   * @return
   * @throws TCGMException
   */
//	protected String getModelParm( int modelId , String parmName) throws TCGMException
  public String getModelParm( int modelId , String parmName) throws TCGMException
  {
	String methodName = "getModelParameter(modelId, parmName, parmValue)";
	String parameterList = "Model Id: " + modelId + ", Parm Name: " + parmName;
	String sql = null;
	ResultSet lrs = null;
	String parm = null;
	Statement s = null;
	Connection workingConn = null;  // 9-1-05 Fix reconnection problem

	try
	{
		workingConn = this.getConnection();
		//myLogger.info("Connection Created");
	  if (this.existsParm( modelId, parmName,  workingConn) )
	  {
		sql = "SELECT " + DBConst.COL_PARM_VALUE + " FROM " + this.parmTable +
			  " WHERE " + DBConst.COL_PARM_NAME + "='" + parmName +"' AND " +  DBConst.COL_MODEL_ID + "=" + modelId;
		//s = this.getConnection().createStatement();

		s = workingConn.createStatement();

		lrs = s.executeQuery(sql);
		lrs.next();
		parm = lrs.getString(DBConst.COL_PARM_VALUE);
	  }
	}
//	catch(SQLException sqle)
//	{
//	  myLogger.error("Error in OracleModelDao.getModelParm() method: Message - " + sqle.getMessage());
//	  throw new TCGMException( this.className,methodName, parameterList + "/nSQL:" + sql, sqle.getMessage() );
//	}

	catch(SQLException sqle)
	{
		try
		{
			if (sqle.getErrorCode() == 2399)
			{
				myLogger.error("SQL Error caught and identified in OracleModelDao.getModelParm() is: *** max connect time exceeded *** " + sqle.getErrorCode());
				logger.debug("Maximum connect time exceeded; closing connection before re-establishing it.");
				workingConn.close(); // 9-1-05 first errors out on this statement
				workingConn = this.getConnection();
				s = workingConn.createStatement();
				lrs = s.executeQuery(sql);
				lrs.next();
				parm = lrs.getString(DBConst.COL_PARM_VALUE);
			}
		}
		catch(SQLException sqle2)
		{
			try
			{
				if (sqle2.getErrorCode() == 1012)
				{
					myLogger.error("SQL Error caught and identified in OracleModelDao.getModelParms() is: *** not logged on *** " + sqle2.getErrorCode());
					logger.debug("User is not logged on; closing connection before re-establishing it.");
					workingConn.close();
					workingConn = this.getConnection();
					s = workingConn.createStatement();
					lrs = s.executeQuery(sql);
					lrs.next();
					parm = lrs.getString(DBConst.COL_PARM_VALUE);
				}
				else
				{
					myLogger.error("SQL Error in OracleModelDao.getModelParm() method: Message - " + sqle2.getMessage());
					throw new TCGMException( this.className,methodName, parameterList + "/n SQL:" + sql, sqle2.getMessage() );
				}
			}
			catch(SQLException sqle3)
			{
				myLogger.error("SQL Error in OracleModelDao.getModelParm() method: Message - " + sqle3.getMessage());
				throw new TCGMException( this.className,methodName, parameterList + "/n SQL:" + sql, sqle3.getMessage() );
			}
		}
		catch(Exception ex)
		{
			myLogger.error("Attempt to close & re-connect connection failed in OracleModelDao.getModelParm() method: Message - " + ex.getMessage());
			throw new TCGMException( this.className,methodName, parameterList + "/n Exception: ",  ex.getMessage() );
		}
	}

	finally
	{
	  SQLUtil.closeResultSet(lrs);
	  SQLUtil.closeStatment(s);
	  SQLUtil.closeConnection(workingConn);
	}

	if (parm == null)
	{
	  return "";
	}
	else
	{
	  return parm;
	}
  }

  // 10-18-05 Same as setModelParm() but accessible from action objects via dao
  public void setModelParmII( int modelId , String parmName, String parmValue) throws TCGMException
  {
	String methodName = "setModelParm(modelId, parmName, parmValue)";
	String parameterList = "Model Id: " + modelId + ", Parm Name: " + parmName + ", Parm Value: " + parmValue;
	String sql = null;
	PreparedStatement ps = null;
	Connection con = null;

	// Don't persist empty or null parmvalues
	if ( TCGMUtil.isEmpty(parmValue) ) return;

	try
	{
		con = this.getConnection();
	  if (this.existsParm( modelId, parmName, con ) )
	  {
		sql = "UPDATE " + this.parmTable + " SET parm_value='" + parmValue + "' where model_id = " + modelId + " AND parm_name='" + parmName + "'";
	  }
	  else
	  {
		sql = "INSERT INTO " + this.parmTable + " (model_id, parm_name, parm_value) VALUES (" + modelId + ",'" + parmName + "', '" + parmValue + "')";
	  }

	  ps = con.prepareStatement(sql);
	  ps.executeUpdate();
	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, parameterList + "/nSQL:" + sql, sqle.toString() + ": " + sqle.getMessage() );
	}
	finally {
		SQLUtil.closePS(ps);
		SQLUtil.closeConnection(con);
	}
  }


  /**
   * @param modelId
   * @param parmName
   * @param conn
   * @return
   * @throws TCGMException
   * @throws SQLException
   */
  //protected boolean existsParm( int modelId, String parmName ) throws TCGMException, SQLException
  public boolean existsParm( int modelId, String parmName, Connection conn ) throws TCGMException, SQLException
  {
	boolean exists = false;
	String methodName = "existsParm( int modelId, String parmName )";
	String parameterList = "Model Id: " + modelId + ", Parm Name: " + parmName;
	ResultSet lrs = null;
	Statement s = null;
	//Connection workingConn = null; // 9-1-05 Fix reconnection problem

	String sql = "SELECT COUNT(*) as COUNT FROM " + this.parmTable + " WHERE " + DBConst.COL_MODEL_ID + "=" + modelId + " AND " +
				 DBConst.COL_PARM_NAME + "='" + parmName + "'";

	// Need to reuse connection object here rather than rs object
	try
	{
	  //s = this.getConnection().createStatement(lrs.TYPE_FORWARD_ONLY, lrs.CONCUR_READ_ONLY);
	  //workingConn = this.getConnection();
	  //s = this.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	  s = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	  lrs = s.executeQuery(sql);
	  lrs.next(); // this better fly since count is an aggregate and shoud always return one row.
	  if ( lrs.getInt("COUNT") > 0 )
	  {
		exists = true;
	  }
	}
// 9-1-05 Original single catch statement before my changes
//	catch(SQLException sqle)
//	{
//		myLogger.error("Error in OracleModelDao.existsParm() method: Message - " + sqle.getMessage());
//		throw new TCGMException( this.className,methodName, parameterList + "/n SQL:" + sql, sqle.getMessage() );
//	}
//	9-1-05 Attempt to fix not logged on error
	catch(SQLException sqle)
	{
		try
		{
			if (sqle.getErrorCode() == 1012)
			{
				myLogger.error("SQL Error caught and identified in OracleModelDao.existsParm() is: *** not logged on *** " + sqle.getErrorCode());
				logger.debug("User is not logged on; closing connection before re-establishing it.");
				//workingConn.close();

				//workingConn = this.getConnection();
				s = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				lrs = s.executeQuery(sql);
				lrs.next(); // this better fly since count is an aggregate and shoud always return one row.
				if ( lrs.getInt("COUNT") > 0 )
				{
				  exists = true;
				}
			}
		}
		catch(SQLException sqle2)
		{
			myLogger.error("SQL Error in OracleModelDao.existsParm() method: Message - " + sqle2.getMessage());
			throw new TCGMException( this.className,methodName, parameterList + "/n SQL:" + sql, sqle2.getMessage() );
		}

		catch(Exception ex)
		{
			myLogger.error("Attempt to close & re-connect connection failed in OracleModelDao.existsParm() method: Message - " + ex.getMessage());
			throw new TCGMException( this.className,methodName, parameterList + "/n Exception: ",  ex.getMessage() );
		}
	}

	finally
	{
	  SQLUtil.closeResultSet(lrs);
	  SQLUtil.closeStatment(s);
	  /*if(workingConn!=null)
	  		workingConn.close();
		SQLUtil.closeConnection(workingConn);*/
		}
	return exists;
  }

  /**
   * this is handled transactionally so connection object is passed in to preserve transactional state.
   * @param model
   * @param conn
   * @throws SQLException
   */
  protected void updateBaseModel(TCGMModel model) throws SQLException, TCGMException
  {
	String sql = "UPDATE " + getEntity() + " SET " +
				 DBConst.COL_MODEL_DESC + "='" + model.getDesc() + "', " +
				 DBConst.COL_MODEL_NAME + "='" + model.getName() + "' WHERE " + DBConst.COL_MODEL_ID + "=" + model.getModelId();

	myLogger.info("updateBaseModel(TCGMModel) sql = " + sql);
	//Connection conn = this.getConnection();
	PreparedStatement ps = this.getConnection().prepareStatement(sql);

	try
	{
	  ps.executeUpdate();
	}
	finally
	{
	  SQLUtil.closePS(ps);
	 // SQLUtil.closeConnection(conn);
	}
  }

  /**
   * This method will chnage the Model Status (Open / Closed)
   * @param model
   * @param conn
   * @throws SQLException
   */
  protected void updateBaseModelStatus(TCGMModel model) throws SQLException, TCGMException
  {
	String sql = "UPDATE " + getEntity() + " SET " +
				 DBConst.COL_MODEL_STATUS + "='" + model.getStatus() +
 				 "' WHERE " + DBConst.COL_MODEL_ID + "=" + model.getModelId();

	myLogger.info("updateBaseModelStatus(TCGMModel) sql = " + sql);
	//Connection conn = this.getConnection();
	PreparedStatement ps =  this.getConnection().prepareStatement(sql);

	try
	{
	  ps.executeUpdate();
	}
	finally
	{
	  SQLUtil.closePS(ps);
	  //SQLUtil.closeConnection(conn);
	}
  }

  /**
   * This method will Update the Model Audit fields (created user name and the modified user name).
   * This method is added as the current functionality always sets these audit fields with "TCGM"
   * irrespective of the actual user.
   * @param modelId
   * @throws SQLException
   */
  protected void updateModelAuditFields(int modelId) throws SQLException, TCGMException
  {
	String sql = "UPDATE " + getEntity() + " SET " +
				 DBConst.COL_CREATE_USERNAME + "='" + this.userToken.getUserid() + "'," +
				 DBConst.COL_MODIFY_USERNAME + "='" + this.userToken.getUserid() +
				 "' WHERE " + DBConst.COL_MODEL_ID + "=" + modelId;

	myLogger.info("updateBaseModelStatus(TCGMModel) sql = " + sql);
	PreparedStatement ps =  this.getConnection().prepareStatement(sql);

	try
	{
	  ps.executeUpdate();
	}
	finally
	{
	  SQLUtil.closePS(ps);
	}
  }

  /**
   * @param modelName
   * @throws TCGMException
   */
/* 10-9-03 This procedure is no longer being called via the model manager; it is submitted as a batch job
  public void closeModel(int modelId) throws TCGMException
  {
	String methodName = "closeModel";
	Connection conn = null;
	String parameterList = "Model ID: " + Integer.toString(modelId);
	CallableStatement cs = null;
	try
	{
	  // Check for model existence.
	  // Low risk of synchronization problem here.
	  if ( this.exists( modelId ) )
	  {
		conn = this.getConnection() ;
		String sql = "{ call " + this.schema + ".MODEL_CLOSE (?) }";

		cs = conn.prepareCall(sql);
		logger.debug("Calleable Statement prepared: " + sql);
		cs.setInt( 1, modelId );

		timer = System.currentTimeMillis();
		cs.executeUpdate();
		logger.debug("The operation took " + (System.currentTimeMillis() - timer) / 1000 + " seconds");
	  }
	  else
	  {
		throw new TCGMException( this.className,methodName, parameterList, "The model doesn't exists" );
	  }
	}
	catch(Exception e)
	{
	  logException(className,methodName,e);
	  throw new TCGMException( this.className,methodName, parameterList, e.getMessage() );
	}
	finally
	{
	  SQLUtil.closeCS(cs);
	}
  }
*/

  /**
   * @param modelName
   * @throws TCGMException
   */
/* 10-9-03 This procedure is no longer being called via the model manager; it is submitted as a batch job
  public void deleteModel(int modelId, TCGMModel.Type mtype) throws TCGMException
  {
	String methodName = "deleteModel";
	Connection conn = null;
	String parameterList = "Model ID: " + Integer.toString(modelId);
	CallableStatement cs = null;
	try
	{
	  // Check for an existing model with the same name.
	  // Low risk of synchronization problem here.
	  if ( this.exists( modelId ) )
	  {
		conn = this.getConnection() ;
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);

		String sql = "{ call " + this.schema + ".MODEL_DELETE (?, ?) }";

		cs = conn.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		logger.debug("Calleable Statement prepared: " + sql);
		cs.setInt( 1, modelId );
		cs.setString(2, mtype.toString().toUpperCase());

		timer = System.currentTimeMillis();
		cs.executeUpdate();
		logger.debug("The operation took " + (System.currentTimeMillis() - timer) / 1000 + " seconds");
		conn.commit();
		conn.setAutoCommit(true);
	  }
	  else
	  {
		throw new TCGMException( this.className,methodName, parameterList, "The model doesn't exists" );
	  }
	}
	catch(Exception e)
	{
	  logException(className,methodName,e);
	  throw new TCGMException( this.className,methodName, parameterList, e.getMessage() );
	}
	finally
	{
	  SQLUtil.closeCS(cs);
	}
  }
*/

/* 10-9-03 This procedure is no longer being called via the model manager; it is submitted as a batch job
  public void compactModel(int modelId) throws TCGMException
  {
	String methodName = "compactModel";
	Connection conn = null;
	String parameterList = "Model ID: " + Integer.toString(modelId);
	CallableStatement cs = null;
	try
	{
	  // Check for model existence.
	  // Low risk of synchronization problem here.
	  if ( this.exists( modelId ) )
	  {
		conn = this.getConnection() ;
		String sql = "{ call " + this.schema + ".MODEL_COMPACT (?) }";

		cs = conn.prepareCall(sql);
		logger.debug("Calleable Statement prepared: " + sql);
		cs.setInt( 1, modelId );

		timer = System.currentTimeMillis();
		cs.executeUpdate();
		logger.debug("The operation took " + (System.currentTimeMillis() - timer) / 1000 + " seconds");
	  }
	  else
	  {
		throw new TCGMException( this.className,methodName, parameterList, "The model doesn't exists" );
	  }
	}
	catch(Exception e)
	{
	  logException(className,methodName,e);
	  throw new TCGMException( this.className,methodName, parameterList, e.getMessage() );
	}
	finally
	{
	  SQLUtil.closeCS(cs);
	}
  }
*/

  /**
   * @param modelName
   * @return
   * @throws TCGMException
   */
  public boolean exists(String modelName) throws TCGMException
  {
	TCGMModel model = new TCGMModel();
	model.setName(modelName);

	if ( this.getCount(model) > 0)
	{
	  return true;
	}
	else
	{
	  return false;
	}
  }

  /**
   * @param modelId
   * @return
   * @throws TCGMException
   */
  public boolean exists(int modelId) throws TCGMException
  {
	TCGMModel model = new TCGMModel();
	model.setModelIdInt(modelId);

	if ( this.getCount(model) > 0)
	{
	  return true;
	}
	else
	{
	  return false;
	}
  }
  /**
   * @param searchObject
   * @return
   * @throws TCGMException
   */
  private int getCount(TCGMModel searchObject) throws TCGMException
  {
	boolean exists = false;
	String methodName = "getCount(TCGMModel searchObject)";
	String parameter_list = "Search Model: " + searchObject.toString();

	int count = 0;
	this.buildSearchList(searchObject);
	String sql = "SELECT COUNT(MODEL) FROM " + getEntity() + this.genWhereClause();
	this.initRS( sql, TCGMConstants.JDBC_ROWSET );
	try
	{
	  this.rs.execute();
	  this.rs.next();
	  count = this.rs.getInt(1);
	}
	catch(Exception e)
	{
	  logException(className,methodName,e);
	  throw new TCGMException( this.className, methodName, parameter_list, e.getMessage() );
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
	return count;
  }

  /**
   * @param modelName
   * @return String (model_status)
   * @throws TCGMException
   */
  public String getModelStatusByName(String modelName) throws TCGMException
  {
	String methodName = "getModelStatusByName(String modelName)";
	String parameterList = "Model Name: " + modelName;
	String modelStatus="NONE";

	try
	{
	  String sql = "SELECT TRIM(" + DBConst.COL_MODEL_STATUS + ") " +  DBConst.COL_MODEL_STATUS + " FROM " + this.getEntity()
				 + " WHERE TRIM(" + DBConst.COL_MODEL_NAME + ") = '" + modelName + "'";

	  this.initRS(sql, TCGMConstants.CACHED_ROWSET );
	  this.rs.execute();

	  if ( this.rs.next() )
	  {
		modelStatus = this.rs.getString(DBConst.COL_MODEL_STATUS);
	  }

	  return modelStatus;

	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
  }
  /**
   *
   * @param sql
   * @return
   * @throws TCGMException
   *
   * 9-19-03
   * This method is used to return a value to its caller from an arbitrary sql SELECT statement that the caller
   * passes in. For example, call to derive a parameter that may be needed for the execution of a given job,
   * assuming that parameter was not setup prior to the job being executed.
   *
   * This method might be better placed in the TCGMUtil object.
   */
  public String getStringValueFromSql(String sql) throws TCGMException
  {
	String methodName = "getStringValueFromSql";
	String parameter_list = "sql: " + sql;

	String sqlResult = " ";
	this.initRS( sql, TCGMConstants.JDBC_ROWSET );
	try
	{
		myLogger.info("Sql statement used in getStringValueFromSql(sql): " + sql);
		this.rs.execute();
		this.rs.next();
		sqlResult = this.rs.getString(1);
	}
	catch(SQLException sqle)
	{
		logException(className,methodName,sqle);
		throw new TCGMException(this.className, methodName, parameter_list, sqle.toString());
	}
	catch(Exception e)
	{
		logException(className,methodName,e);
		throw new TCGMException( this.className, methodName, parameter_list, e.getMessage() );
	}
	finally
	{
		// 9-26-03 Just return a blank if there was an error for those calling methods that need a value
		SQLUtil.closeRowSet(rs);
		return sqlResult;
	}
  }

  public String getModelJobStatus(int modelId,String jobName) throws TCGMException{
  	String methodName = "getModelJobStatus(int modelId,int JobId)";
	String parameterList = "Model Name: " + modelId;
	String jobStatus="N";

	try
	{
	  String sql = "SELECT COMMON.GET_JOB_STATUS("+modelId+",'"+jobName+"') FROM DUAL";

	  this.initRS(sql, TCGMConstants.CACHED_ROWSET );
	  this.rs.execute();

	  if ( this.rs.next() )
	  {
	  	jobStatus = this.rs.getString(1);
	  }

	  return jobStatus;

	}
	catch(SQLException sqle)
	{
	  throw new TCGMException( this.className,methodName, parameterList, sqle.toString() + ": " + sqle.getMessage() );
	}
	finally
	{
	  SQLUtil.closeRowSet(rs);
	}
 }

  
}