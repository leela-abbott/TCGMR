package abbott.ai.tcgm.data.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.Cycle;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.ModelCopyOptions;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.TCGMUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class OracleFactorModelDao extends OracleModelDao
{
	private static Logger myLogger = Logger.getLogger( "OracleFactorModelDao" );
	/**
	 *
	 * @param userToken
	 */
	public OracleFactorModelDao(UserToken userToken)
	{
		this.userToken = userToken;
		this.setEntityTable(DBConst.TABLE_MODEL);
		this.parmTable = schema + ".PARAMETER";
	}

	/**
	 * @param model
	 * @param conn
	 * @throws TCGMException
	 */
	protected void saveModelParms(TCGMModel model) throws TCGMException
	{
		String methodName = "saveModelParms(TCGMModel, Connection)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.FACTOR)
		{
			FactorModel fmodel = (FactorModel) model;
			this.setModelParm(modelId, fmodel.PN_BEG_FACTOR_PERIOD, fmodel.getBegFactorPeriod() );
			this.setModelParm(modelId, fmodel.PN_ACT_UNITS, fmodel.getActualUnitsId() );
			this.setModelParm(modelId, fmodel.PN_PLAN_UNITS, fmodel.getPlanUnitsId() );
			this.setModelParm(modelId, fmodel.PN_RATESET_FACTOR_ACTUAL, fmodel.getRateSetFactorActualId() );
			this.setModelParm(modelId, fmodel.PN_RATESET_COST, fmodel.getRateSetCostId() );
			this.setModelParm(modelId, fmodel.PN_RATESET_REVISION, fmodel.getRateSetRevisionId() );
			// 10-11-2005 Allow multiple R-system selection
			//this.setModelParm(modelId, fmodel.PN_EXPORT_R_SYSTEM, fmodel.getExportRSystem() );
			this.setModelParm(modelId, fmodel.PN_MODEL_YEAR, fmodel.getModelYear() );
			this.setModelParm(modelId, fmodel.PN_MODEL_CYCLE, fmodel.getModelCycle().getName() );
			this.setModelParm(modelId, fmodel.PN_SAVED_FACTOR_DATE, fmodel.getSavedFactorDate() );
			this.setModelParm(modelId, fmodel.PN_CURR_UNITS_NAME, fmodel.getCurrUnitsName() );
            //A.Winter
			this.setModelParm(modelId, fmodel.PN_MEMO, TCGMUtil.replaceAllChars(fmodel.getMemo().trim()));

			// 10-11-2005 Allow Multiple R-systems Selection				
			if(fmodel.getExportRgmSystem().equals("Y"))
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RGM, "Y" );
			else
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RGM, "N" );
						
			if(fmodel.getExportRtcSystem().equals("Y"))
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RTC, "Y" );
			else
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RTC, "N" );

			if(fmodel.getExportRbbSystem().equals("Y"))
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RBB, "Y" );
			else
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RBB, "N" );

			if(fmodel.getExportRblSystem().equals("Y"))
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RBL, "Y" );
			else
				this.setModelParm(modelId, JobConstants.PN_R_SYSTEM_RBL, "N" );
				
			/*if(fmodel.getExportCcsSystem().equals("Y")){			
				this.setModelParm(modelId, JobConstants.PN_R_CCS_SYSTEM, "Y" );
				this.setModelParm(modelId, JobConstants.PN_R_CCS_TYPE, fmodel.getExportCcsType());
			}	
			else{			
				this.setModelParm(modelId, JobConstants.PN_R_CCS_SYSTEM, "N" );
				this.setModelParm(modelId, JobConstants.PN_R_CCS_TYPE, "N");
			}*/	
				
		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}
	}


	/**
	 *
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRow() throws TCGMException, SQLException
	{
		FactorModel model = new FactorModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		//return this.loadModelParms(model);
		/*
		 * loadModelParms method invokes parameter table parm by parm and assigns the valus to 
		 * model object. In the new method fetching all the parms and values at a time and setting
		 * to model object based on required params. It saves lot of DB activity.
		 */
		return this.loadModelParmsAll(model);
	}
	
	/**
	 *
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRowSet() throws TCGMException, SQLException
	{
		FactorModel model = new FactorModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		//return this.loadModelParms(model);
		/*
		 * loadModelParms method invokes parameter table parm by parm and assigns the valus to 
		 * model object. In the new method fetching all the parms and values at a time and setting
		 * to model object based on required params. It saves lot of DB activity.
		 */
		return this.loadModelParmters(model);
	}
	
	/**
	 *
	 * @param model
	 * @return
	 * @throws TCGMException
	 */
	protected TCGMModel loadModelParms(TCGMModel model) throws TCGMException
	{
		String methodName = "loadModelParms(TCGMModel)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.FACTOR)
		{
			FactorModel fmodel = (FactorModel) model;
			fmodel.setBegFactorPeriod(this.getModelParm(modelId, fmodel.PN_BEG_FACTOR_PERIOD) );
			fmodel.setActualUnitsId(this.getModelParm(modelId, fmodel.PN_ACT_UNITS) );
			fmodel.setPlanUnitsId(this.getModelParm(modelId, fmodel.PN_PLAN_UNITS) );
			fmodel.setRateSetFactorActualId(this.getModelParm(modelId, fmodel.PN_RATESET_FACTOR_ACTUAL) );
			fmodel.setRateSetCostId(this.getModelParm(modelId, fmodel.PN_RATESET_COST) );
			fmodel.setRateSetRevisionId(this.getModelParm(modelId, fmodel.PN_RATESET_REVISION) );
			fmodel.setModelYear(this.getModelParm(modelId, fmodel.PN_MODEL_YEAR) );
			fmodel.setSavedFactorDate(this.getModelParm(modelId, fmodel.PN_SAVED_FACTOR_DATE) );
			fmodel.setExportRbbSystem(this.getModelParm(modelId, JobConstants.PN_R_SYSTEM_RBB));
			fmodel.setExportRblSystem(this.getModelParm(modelId, JobConstants.PN_R_SYSTEM_RBL));
			fmodel.setExportRtcSystem(this.getModelParm(modelId, JobConstants.PN_R_SYSTEM_RTC));
			fmodel.setExportRgmSystem(this.getModelParm(modelId, JobConstants.PN_R_SYSTEM_RGM));
            // A.Winter 6/30/05 add memo
		    fmodel.setMemo(this.getModelParm(modelId, fmodel.PN_MEMO));

			// Cycle isn't always specified in test data. Handle it gracefully. Model defaults to Cycle.UNSPECIFIED
			String cycleName = this.getModelParm( modelId, fmodel.PN_MODEL_CYCLE );
			if ( !TCGMUtil.isEmpty(cycleName) )
				fmodel.setModelCycle( Cycle.getObjectFromName( cycleName ) );
			
			return fmodel;
			
		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}
	}


	/**
	 *
	 * @param model
	 * @return
	 * @throws TCGMException
	 */
	protected TCGMModel loadModelParmsAll(TCGMModel model) throws TCGMException
	{
		String methodName = "loadModelParmsAll(TCGMModel)";
		String parameterList = "Model Id: " + model.getModelId();
		int modelId = model.getModelIdInt();
		FactorModel fmodel = (FactorModel) model;
		if (model.getType() == TCGMModel.Type.FACTOR)
		{
				String sql = null;
				ResultSet lrs = null;
				Statement s = null;
				Connection workingConn = null;  // 9-1-05 Fix reconnection problem
	
				try
				{
					workingConn = this.getConnection();
	   
					sql = "SELECT " + DBConst.COL_PARM_NAME +","+ DBConst.COL_PARM_VALUE + " FROM " + this.parmTable +
						  " WHERE " +  DBConst.COL_MODEL_ID + "=" + modelId;
					//s = this.getConnection().createStatement();
		
					s = workingConn.createStatement();

					lrs = s.executeQuery(sql);
										
					while(lrs.next()){
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_BEG_FACTOR_PERIOD))
							fmodel.setBegFactorPeriod(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_ACT_UNITS))
							fmodel.setActualUnitsId(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_PLAN_UNITS))
							fmodel.setPlanUnitsId(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_RATESET_FACTOR_ACTUAL))
							fmodel.setRateSetFactorActualId(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_RATESET_COST))
							fmodel.setRateSetCostId(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_RATESET_REVISION))
							fmodel.setRateSetRevisionId(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_MODEL_YEAR))
							fmodel.setModelYear(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_SAVED_FACTOR_DATE))
							fmodel.setSavedFactorDate(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals( JobConstants.PN_R_SYSTEM_RBB))
							fmodel.setExportRbbSystem(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals( JobConstants.PN_R_SYSTEM_RBL))
							fmodel.setExportRblSystem(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(JobConstants.PN_R_SYSTEM_RTC))
							fmodel.setExportRtcSystem(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(JobConstants.PN_R_SYSTEM_RGM))
							fmodel.setExportRgmSystem(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_MEMO))
							fmodel.setMemo(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_MODEL_CYCLE)){
							// Cycle isn't always specified in test data. Handle it gracefully. Model defaults to Cycle.UNSPECIFIED
							String cycleName = lrs.getString(DBConst.COL_PARM_VALUE) ;
							if ( !TCGMUtil.isEmpty(cycleName) )
								fmodel.setModelCycle( Cycle.getObjectFromName( cycleName ) );
							}
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(JobConstants.PN_R_CCS_SYSTEM))
							fmodel.setExportCcsSystem(lrs.getString(DBConst.COL_PARM_VALUE) );						
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(JobConstants.PN_R_CCS_TYPE))
							fmodel.setExportCcsType(lrs.getString(DBConst.COL_PARM_VALUE) );	
								
												
						}
						   
			}catch(SQLException sqle)
			{
			
				myLogger.error("SQL Error in OracleFactorModelDao.loadModelParmsAll() method: Message - " + sqle.getMessage());
				throw new TCGMException( this.className,methodName, parameterList + "/n SQL:" + sql, sqle.getMessage() );
		
			}
			catch(Exception ex)
			{
				myLogger.error("Attempt to close & re-connect connection failed in OracleModelDao.getModelParm() method: Message - " + ex.getMessage());
				throw new TCGMException( this.className,methodName, parameterList + "/n Exception: ",  ex.getMessage() );
			}
			

			finally
			{
			  SQLUtil.closeResultSet(lrs);
			  SQLUtil.closeStatment(s);
			  SQLUtil.closeConnection(workingConn);
			}
		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}
		//myLogger.debug(fmodel.getModelId());
		return fmodel;
	}
	/**
	 *
	 * @param model
	 * @return
	 * @throws TCGMException
	 */
	private TCGMModel loadModelParmters(TCGMModel model) throws TCGMException
	{
		String methodName = "loadModelParmsAll(TCGMModel)";
		String parameterList = "Model Id: " + model.getModelId();
		int modelId = model.getModelIdInt();
		FactorModel fmodel = (FactorModel) model;
		if (model.getType() == TCGMModel.Type.FACTOR)
		{
				String sql = null;
				ResultSet lrs = null;
				Statement s = null;
				Connection workingConn = null;  // 9-1-05 Fix reconnection problem
	
				try
				{
					workingConn = this.getConnection();
	   
					sql = "SELECT " + DBConst.COL_PARM_NAME +","+ DBConst.COL_PARM_VALUE + " FROM " + this.parmTable +
						  " WHERE " +  DBConst.COL_MODEL_ID + "=" + modelId +" AND "+DBConst.COL_PARM_NAME+" in('CYCLE','YEAR')";
					//s = this.getConnection().createStatement();
		
					s = workingConn.createStatement();

					lrs = s.executeQuery(sql);
										
					while(lrs.next()){
						
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_MODEL_YEAR))
							fmodel.setModelYear(lrs.getString(DBConst.COL_PARM_VALUE) );
						
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(fmodel.PN_MODEL_CYCLE)){
							// Cycle isn't always specified in test data. Handle it gracefully. Model defaults to Cycle.UNSPECIFIED
							String cycleName = lrs.getString(DBConst.COL_PARM_VALUE) ;
							if ( !TCGMUtil.isEmpty(cycleName) )
								fmodel.setModelCycle( Cycle.getObjectFromName( cycleName ) );
							}
						
								
												
						}
						   
			}catch(SQLException sqle)
			{
			
				myLogger.error("SQL Error in OracleFactorModelDao.loadModelParmsAll() method: Message - " + sqle.getMessage());
				throw new TCGMException( this.className,methodName, parameterList + "/n SQL:" + sql, sqle.getMessage() );
		
			}
			catch(Exception ex)
			{
				myLogger.error("Attempt to close & re-connect connection failed in OracleModelDao.getModelParm() method: Message - " + ex.getMessage());
				throw new TCGMException( this.className,methodName, parameterList + "/n Exception: ",  ex.getMessage() );
			}
			

			finally
			{
			  SQLUtil.closeResultSet(lrs);
			  SQLUtil.closeStatment(s);
			  SQLUtil.closeConnection(workingConn);
			}
		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}
		//myLogger.debug(fmodel.getModelId());
		return fmodel;
	}

	/**
	 *
	 * @param model
	 * @param baseModel
	 * @param options
	 * @return
	 * @throws TCGMException
	 */
	public int createModel(TCGMModel model, TCGMModel baseModel, ModelCopyOptions options) throws TCGMException
	{
		String methodName = "createModel";  // 9-6-05 year & cycle has been retain up to this point in baseModel
		int clearBpcs = 0;
		int clearFreeze = 0;
		Connection conn = null;
		Connection connForCopy = null; // 6-3-05 test copy procedure
		CallableStatement cs = null;
		CallableStatement csCopy = null;
		int srcModelId = baseModel.getModelIdInt();
		String modelStatus = null;
		String newModelName = null;
		try
		{
			// Check for an existing model with the same name.
			// Low risk of synchronization problem here.
/*			if (this.exists( model.getName() ))
			{
				throw new TCGMDuplicateItemException( model.getName() );
			}*/
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

			conn = this.getConnection();
			conn.setAutoCommit(false);
			String sql = "{ ? = call " + schema + ".MODEL_CREATE (?, ?) }";
			myLogger.info("Model-create call in OracleFactorModelDao.createModel: " + sql);

			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString( 2, model.getName() );
			cs.setString( 3, model.getDesc() );

			cs.executeUpdate();
			int newModelId = cs.getInt(1);
		
			/* 9-6-05 Set the model id & saved the parms to fix the problem w/ cycle 
			          and years parms not persisting when the user does a create/copy */
			model.setModelIdInt( newModelId );			
			this.saveModelParms(model);		

			// Under all circumstances copy ASR, ASR_TREE.
//			this.copyData(DBConst.TABLE_ASR, srcModelId, newModelId);
//			this.copyData(DBConst.TABLE_ASR_TREE, srcModelId, newModelId);

			// If they don't clear bpc data, go ahead and copy it.
			if ( options.isClearBpcs() ) 
			{
				clearBpcs = 1;
			}

			if ( options.isClearFreezeCost() ) 
			{
				clearFreeze = 1;
			}

			conn.commit();
			conn.setAutoCommit(true);

			// Call MODEL_COPY procedure as a last step.
			String sqlCopy = "{ ? = call " + schema + ".MODEL_COPY(?, ?, ?, ?,?) }";
			myLogger.info("Model-copy call in OracleFactorModelDao.createModel: " + sqlCopy);

			csCopy = conn.prepareCall(sqlCopy);

			csCopy.registerOutParameter(1, Types.INTEGER);
			csCopy.setInt(2, srcModelId );
			csCopy.setInt(3, newModelId);
			csCopy.setInt(4, clearBpcs);
			csCopy.setInt(5, clearFreeze);
			csCopy.setInt(6, options.getKeepBpPeriod());
			myLogger.debug("srcModelId before call to MODEL_COPY = " + srcModelId);
			myLogger.debug("newModelId before call to MODEL_COPY = " + newModelId);
	
			csCopy.execute();
			int newModelIdAfterCopy = csCopy.getInt(1); // Return value is not used

			return newModelId;
		}
		catch(SQLException sqle)
		{
			myLogger.error("sqle: " + sqle);
			try {conn.rollback();}
			catch (SQLException sqlrb){
				logException(className,methodName,sqlrb);
				throw new TCGMException( this.className,methodName, model.toString(), sqle.toString() + ": " + sqlrb.getMessage() );
			}
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, model.toString(), sqle.toString() + ": " + sqle.getMessage() );
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeCS(csCopy);
			SQLUtil.closeConnection(conn);
		}
	}

	private void spreadData(String procName, int modelId, int period) throws TCGMException, SQLException {
		CallableStatement csSpread = this.getConnection().prepareCall("{ call " + schema + "." + procName + " (?, ?, ?, ?) }" );
		csSpread.setInt(1, period );
		csSpread.setInt(2, modelId);
		csSpread.setInt(3, -1);
		csSpread.setString( 4, this.userToken.getUserid() );
		csSpread.execute();
	}


	private void copyData(String tablename, int sourceModelId, int destModelId) throws TCGMException, SQLException {
		String sqlDataCopy =  "{ call " + schema + ".DATA_COPY (?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement csCopyData = this.getConnection().prepareCall(sqlDataCopy);
		csCopyData.setString(1, tablename);
		csCopyData.setString(2, tablename);
		csCopyData.setInt(3, sourceModelId );
		csCopyData.setInt(4, -1 );
		csCopyData.setInt(5, destModelId);
		csCopyData.setInt(6, -1);
		csCopyData.setString(7, this.userToken.getUserid() );
		csCopyData.execute();
	}
}