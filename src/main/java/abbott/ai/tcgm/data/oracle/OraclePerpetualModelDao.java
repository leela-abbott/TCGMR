package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import java.util.Date;
import abbott.ai.tcgm.exception.TCGMException;

import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.TCGMUtil;
import org.apache.struts.action.*;
import org.apache.log4j.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class OraclePerpetualModelDao extends OracleModelDao
{
	private static Logger myLogger = Logger.getLogger( "OraclePerpetualModelDao" );
	/**
	 *
	 * @param userToken
	 */
	protected ActionErrors errors = new ActionErrors();
	public OraclePerpetualModelDao(UserToken userToken)
	{
		this.userToken = userToken;
		this.setEntityTable(DBConst.TABLE_MODEL);
		this.parmTable = this.schema + ".PARAMETER";
	}

	/**
	 *
	 * @param model
	 * @param conn
	 * @throws TCGMException
	 */
	protected void saveModelParms(TCGMModel model) throws TCGMException
	{
		String methodName = "saveModelParms(<Perpetual>TCGMModel, Connection)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.PERPETUAL)
		{
			PerpetualModel pmodel = (PerpetualModel) model;

			this.setModelParm(modelId, pmodel.PN_STARTING_INV_MODEL, pmodel.getStartingModel().getModelId() );
			this.setModelParm(modelId, pmodel.PN_STARTING_INV_UNITS, pmodel.getStartingInvUnits().getDatasetTableId() );
			this.setModelParm(modelId, pmodel.PN_END_INV_USED, pmodel.getUseEndInv() );
			
			//	A.Winter - add parameter 7/7/05		
			this.setModelParm(modelId, pmodel.PN_MEMO, TCGMUtil.replaceAllChars(pmodel.getMemo().trim()) );
		
			if ( !pmodel.getUseEndInv().equalsIgnoreCase("Y"))
			{
				this.setModelParm(modelId, pmodel.PN_END_INV_UNITS, "" );
				this.setModelParm(modelId, pmodel.PN_END_INV_MODEL, "" );
			}
			else
			{
				this.setModelParm(modelId, pmodel.PN_END_INV_MODEL, pmodel.getEndingModel().getModelId() );
				this.setModelParm(modelId, pmodel.PN_END_INV_UNITS, pmodel.getEndingInvUnits().getDatasetTableId() );
			}
		  try 
		  {  // A.Winter
			if(! (pmodel.getCostingModel() == null))
			{          	            
				this.setModelParm(modelId, pmodel.PN_COSTING_MODEL, pmodel.getCostingModel().getModelId() );
				myLogger.info("Cycle Cycle model id = " + pmodel.getCostingModel());
				
				//this.setModelParm(modelId, pmodel.PN_COST_CYCLE_NAME, pmodel.getCostCycleName());
				this.setModelParm(modelId, PerpetualModel.PN_COST_CYCLE_NAME, pmodel.getCostingModel().getName());
				myLogger.info("Cost Cycle name retrieved = " + pmodel.getCostingModel().getName());
			}

			this.setModelParm(modelId, pmodel.PN_END_D56_PERIOD, pmodel.getEndPeriod() );
			this.setModelParm(modelId, pmodel.PN_END_D56_YEAR, pmodel.getEndYear() );
			this.setModelParm(modelId, pmodel.PN_START_D56_PERIOD, pmodel.getStartPeriod() );
			this.setModelParm(modelId, pmodel.PN_START_D56_YEAR, pmodel.getStartYear() );

			if( pmodel.getCurrentYearActualModel()!=null&& (pmodel.getCurrentYearActualModel().getModelId()!=null))  //A.Winter
			{
				//this.setModelParm(modelId, pmodel.PN_CURRENT_YEAR_ACTUAL_MODEL, pmodel.getCurrentYearActualModel().getModelId() );
				this.setModelParm(modelId, pmodel.PN_CURRENT_YEAR_ACTUAL_MODEL, pmodel.getCurrentYearActualModel().getModelId());
			}
			if( pmodel.getCurrentYearActualUnits()!=null)  //A.Winter
			{					
				//this.setModelParm(modelId, pmodel.PN_CURRENT_YEAR_ACTUAL_UNITS, pmodel.getCurrentYearActualUnits().getDatasetTableId() );
				this.setModelParm(modelId, pmodel.PN_CURRENT_YEAR_ACTUAL_UNITS, pmodel.getCurrentYearActualUnits().getDatasetTableId() );
			}
			// Added by Udaya B Aravapalli on 01/17/2006 to check for null value -- Start
			if ((pmodel.getLastYearActualModel()!=null) && (pmodel.getLastYearActualModel().getModelId()!=null))
			{
				//this.setModelParm(modelId, pmodel.PN_LAST_YEAR_ACTUAL_MODEL, pmodel.getLastYearActualModel().getModelId() );
				this.setModelParm(modelId, pmodel.PN_LAST_YEAR_ACTUAL_MODEL, pmodel.getLastYearActualModel().getModelId());
			}
			if ((pmodel.getLastYearActualUnits()!=null) && (pmodel.getLastYearActualUnits().getDatasetTableId()!=null))
			{
				//this.setModelParm(modelId, pmodel.PN_LAST_YEAR_ACTUAL_UNITS, pmodel.getLastYearActualUnits().getDatasetTableId() );
				this.setModelParm(modelId, pmodel.PN_LAST_YEAR_ACTUAL_UNITS, pmodel.getLastYearActualUnits().getDatasetTableId() );
			}
			// Added by Udaya B Aravapalli on 01/17/2006 to check for null value -- End
			if ((pmodel.getStartingModel()!=null) && (pmodel.getStartingModel().getName()!=null))
			{
				this.setModelParm(modelId, pmodel.PN_BEG_INV_CYCLE_NAME, pmodel.getStartingModel().getName() );
			}
			if ((pmodel.getEndingModel() !=null) && (pmodel.getEndingModel().getName()!=null))
			{
				this.setModelParm(modelId, pmodel.PN_END_INV_CYCLE_NAME, pmodel.getEndingModel().getName() );
			}
						
			//	A.Winter - add parameter 7/7/05	
			this.setModelParm(modelId, pmodel.PN_MEMO, TCGMUtil.replaceAllChars(pmodel.getMemo().trim()) );
		  }
		  catch (TCGMException ex) 
		  {
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.perpetual.create"));
		  }
		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}

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
		throw new TCGMException (this.className, "createModel(model, baseModel, options)", "Operation not supported on this object");
	}

	/**
	 *
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRow() throws TCGMException, SQLException
	{
		PerpetualModel model = new PerpetualModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		return this.loadModelParms(model);
	}
	
	/**
	 *
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRowSet() throws TCGMException, SQLException
	{
		PerpetualModel model = new PerpetualModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		return model;
	}
	
	/**
	 *
	 * @param model
	 * @return
	 * @throws TCGMException
	 */
	protected TCGMModel loadModelParms(TCGMModel model) throws TCGMException
	{
		String methodName = "loadModelParms(<Perpetual>TCGMModel)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.PERPETUAL)
		{
			/*
			 * loadModelParms method invokes parameter table parm by parm and assigns the valus to 
			 * model object. In the new method fetching all the parms and values at a time and setting
			 * to model object based on required params. It saves lot of DB activity.
			 */
			PerpetualModel pmodel = (PerpetualModel)loadModelParmsAll(model);//(PerpetualModel) model;
			
			pmodel.setStartingModel( this.getFactorModelParm(modelId, PerpetualModel.PN_STARTING_INV_MODEL) );
			pmodel.setStartingInvUnits( this.getDatasetParm( modelId, pmodel.PN_STARTING_INV_UNITS) );

			pmodel.setCostingModel( this.getFactorModelParm(modelId, pmodel.PN_COSTING_MODEL) );
//			pmodel.setCostingModel( this.getFactorModelParm(modelId, pmodel.PN_COST_CYCLE_NAME) );

			pmodel.setEndingModel( this.getFactorModelParm(modelId, pmodel.PN_END_INV_MODEL) );
			pmodel.setEndingInvUnits( this.getDatasetParm(modelId, pmodel.PN_END_INV_UNITS) );

			pmodel.setCurrentYearActualUnits( this.getDatasetParm( modelId, pmodel.PN_CURRENT_YEAR_ACTUAL_UNITS)  );
			pmodel.setLastYearActualUnits( this.getDatasetParm( modelId, pmodel.PN_LAST_YEAR_ACTUAL_UNITS) );

			pmodel.setCurrentYearActualModel( this.getFactorModelParm( modelId, pmodel.PN_CURRENT_YEAR_ACTUAL_MODEL) );
			pmodel.setLastYearActualModel( this.getFactorModelParm( modelId, pmodel.PN_LAST_YEAR_ACTUAL_MODEL) );

			return pmodel;

		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}
	}
	protected TCGMModel loadModelParmsAll(TCGMModel model) throws TCGMException
	{
		String methodName = "loadModelParmsAll(TCGMModel)";
		String parameterList = "Model Id: " + model.getModelId();
		int modelId = model.getModelIdInt();
		PerpetualModel pmodel = (PerpetualModel) model;
		if (model.getType() == TCGMModel.Type.PERPETUAL)
		{
				String sql = null;
				ResultSet lrs = null;
				Statement s = null;
				Connection workingConn = null;  // 9-1-05 Fix reconnection problem
	
				try
				{
					workingConn = this.getConnection();
					//myLogger.debug("Connection Created");
	
					sql = "SELECT " + DBConst.COL_PARM_NAME +","+ DBConst.COL_PARM_VALUE + " FROM " + this.parmTable +
						  " WHERE " +  DBConst.COL_MODEL_ID + "=" + modelId;
					//s = this.getConnection().createStatement();
	
					s = workingConn.createStatement();
	
					lrs = s.executeQuery(sql);
	
					while(lrs.next()){
						
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_END_INV_USED))
						pmodel.setUseEndInv( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_END_D56_PERIOD))
						pmodel.setEndPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_END_D56_YEAR))
						pmodel.setEndYear( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_START_D56_PERIOD))
						pmodel.setStartPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_START_D56_YEAR))
						pmodel.setStartYear( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_COST_CYCLE_NAME))
						pmodel.setCostCycleName(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_BEG_INV_CYCLE_NAME))
						pmodel.setBegInvCycleName(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_END_INV_CYCLE_NAME))
						pmodel.setEndInvCycleName(lrs.getString(DBConst.COL_PARM_VALUE) );
						// A.Winter - add parameter 7/7/05		
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_MEMO))
						pmodel.setMemo(lrs.getString(DBConst.COL_PARM_VALUE) );
						/*********************************************************************************
						 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
						 * JOB_QUE_PARAMS for PERP_SUMMARY(Costed Summary) job.
						 * This Job/ Button is on Perpetual Model Management screen.
						 *  START  -- ADD Job Que Parameters
						 *********************************************************************************/
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_BEG_INV_HEADER))
						pmodel.setBegInvHdr(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_END_INV_HEADER))
						pmodel.setEndInvHdr(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_COST_HDR_1))
						pmodel.setCostHdr1(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_COST_HDR_1))
						pmodel.setCostHdr1(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_COST_HDR_2))	
						pmodel.setCostHdr2(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_COST_HDR_3))
						pmodel.setCostHdr3(lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(pmodel.PN_COST_HDR_4))
						pmodel.setCostHdr4(lrs.getString(DBConst.COL_PARM_VALUE) );
						/*********************************************************************************
						 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
						 * JOB_QUE_PARAMS for PERP_SUMMARY(Costed Summary) job.
						 * This Job/ Button is on Perpetual Model Management screen.
						 *  END  -- ADD Job Que Parameters
						 *********************************************************************************/

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
		return pmodel;
	}

}