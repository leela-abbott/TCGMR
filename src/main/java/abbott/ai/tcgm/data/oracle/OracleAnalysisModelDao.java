package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import java.util.Date;

import org.apache.log4j.Logger;
//import java.util.*;

//import javax.sql.*;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
//import abbott.ai.tcgm.helpers.*;
//import oracle.jdbc.pool.*;
//import abbott.ai.tcgm.action.form.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class OracleAnalysisModelDao extends OracleModelDao
{
	private static Logger myLogger = Logger.getLogger( "OracleAnalysisModelDao" );
	/**
	 * @param userToken
	 */
	public OracleAnalysisModelDao(UserToken userToken)
	{
		this.userToken = userToken;
		this.setEntityTable(DBConst.TABLE_MODEL);
		this.parmTable = this.schema + ".PARAMETER";
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
		if (model.getType() == TCGMModel.Type.ANALYSIS)
		{
			AnalysisModel amodel = (AnalysisModel) model;
			this.setModelParm(modelId, amodel.PN_ANALYSIS_UNITS, amodel.getAnalysisUnits().getDatasetTableId() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_UNITS_PERIOD, amodel.getAnalysisUnitsPeriod() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_UNITS_VERSION, amodel.getAnalysisUnitsVersion() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_UNITS_YEAR, amodel.getAnalysisUnitsYear() );

			this.setModelParm(modelId, amodel.PN_VOLUME_UNITS, amodel.getVolumeUnits().getDatasetTableId() );
			this.setModelParm(modelId, amodel.PN_VOLUME_UNITS_PERIOD, amodel.getVolumeUnitsPeriod() );
			this.setModelParm(modelId, amodel.PN_VOLUME_UNITS_VERSION, amodel.getVolumeUnitsVersion() );
			this.setModelParm(modelId, amodel.PN_VOLUME_UNITS_YEAR, amodel.getVolumeUnitsYear() );

			this.setModelParm(modelId, amodel.PN_BASE_MODEL, amodel.getBaseModel().getModelId() );
			this.setModelParm(modelId, amodel.PN_BASE_MODEL_PERIOD, amodel.getBaseModelPeriod() );
			this.setModelParm(modelId, amodel.PN_BASE_MODEL_VERSION, amodel.getBaseVersion() );
			this.setModelParm(modelId, amodel.PN_BASE_MODEL_YEAR, amodel.getBaseYear() );

			this.setModelParm(modelId, amodel.PN_ANALYSIS_MODEL, amodel.getAnalysisModel().getModelId() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_MODEL_PERIOD, amodel.getAnalysisModelPeriod() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_MODEL_VERSION, amodel.getAnalysisModelVersion() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_MODEL_YEAR, amodel.getAnalysisModelYear() );

			//this.setModelParm(modelId, amodel.PN_CURRENT_YEAR_ACTUAL_MODEL, amodel.getCurrentYearActualModel().getModelId() );
			//this.setModelParm(modelId, amodel.PN_CURRENT_YEAR_ACTUAL_UNITS, amodel.getCurrentYearActualUnits().getDatasetTableId() );

			//this.setModelParm(modelId, amodel.PN_LAST_YEAR_ACTUAL_MODEL, amodel.getLastYearActualModel().getModelId() );
			//this.setModelParm(modelId, amodel.PN_LAST_YEAR_ACTUAL_UNITS, amodel.getLastYearActualUnits().getDatasetTableId() );

			/////////////////////////////// Misc Parms ////////////////////////////////////////////
			this.setModelParm(modelId, amodel.PN_CURRENT_YEAR, amodel.getCurrentYear() );
			this.setModelParm(modelId, amodel.PN_CURRENT_MONTH, amodel.getCurrentMonth() );
			this.setModelParm(modelId, amodel.PN_CURRENT_DAY, amodel.getCurrentDay() );
			this.setModelParm(modelId, amodel.PN_ANALYSIS_SAVE, amodel.getAnalysisSave() );
			this.setModelParm(modelId, amodel.PN_NAME_RATE1, amodel.getRate1() );
			//A.Winter - 7/1/05 - add save memo
			this.setModelParm(modelId, amodel.PN_MEMO, TCGMUtil.replaceAllChars(amodel.getMemo().trim()));
			
			this.saveModelParams2(model);

		}
		else
		{
			throw new TCGMException( this.className,methodName, model.toString(), "Wrong model type for this operation" );
		}
	}

	/**
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRow() throws TCGMException, SQLException
	{
		AnalysisModel model = new AnalysisModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		return this.loadModelParms(model);
		
	}

	/**
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRowSet() throws TCGMException, SQLException
	{
		AnalysisModel model = new AnalysisModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		return model;
		
	}

	/**
	 * @param model
	 * @return
	 * @throws TCGMException
	 */
	protected TCGMModel loadModelParms(TCGMModel model) throws TCGMException
	{
		String methodName = "loadModelParms(TCGMModel)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.ANALYSIS)
		{
			/*
			 * loadModelParms method invokes parameter table parm by parm and assigns the valus to 
			 * model object. In the new method fetching all the parms and values at a time and setting
			 * to model object based on required params. It saves lot of DB activity.
			 */
			AnalysisModel amodel = (AnalysisModel)loadModelParmsAll(model);//(AnalysisModel) model;
//			///////////////////////////// Unit Sets ////////////////////////////////////////////
		  amodel.setAnalysisUnits( this.getDatasetParm( modelId, amodel.PN_ANALYSIS_UNITS) );
		  amodel.setVolumeUnits( this.getDatasetParm( modelId, amodel.PN_VOLUME_UNITS) );
//			amodel.setCurrentYearActualUnits( this.getDatasetParm( modelId, amodel.PN_CURRENT_YEAR_ACTUAL_UNITS)  );
//			amodel.setLastYearActualUnits( this.getDatasetParm( modelId, amodel.PN_LAST_YEAR_ACTUAL_UNITS) );

//			///////////////////////////// Factor Models ////////////////////////////////////////////
		  amodel.setBaseModel( this.getFactorModelParm( modelId, amodel.PN_BASE_MODEL) );
		  amodel.setAnalysisModel( this.getFactorModelParm( modelId, amodel.PN_ANALYSIS_MODEL) );
//			amodel.setCurrentYearActualModel( this.getFactorModelParm( modelId, amodel.PN_CURRENT_YEAR_ACTUAL_MODEL) );
//			amodel.setLastYearActualModel( this.getFactorModelParm( modelId, amodel.PN_LAST_YEAR_ACTUAL_MODEL) );			

		   return amodel;
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
	AnalysisModel amodel = (AnalysisModel) model;
	if (model.getType() == TCGMModel.Type.ANALYSIS)
	{
			String sql = null;
			ResultSet lrs = null;
			Statement s = null;
			Connection workingConn = null;  // 9-1-05 Fix reconnection problem

			try
			{
				workingConn = this.getConnection();
				myLogger.debug("Connection Created");

				sql = "SELECT " + DBConst.COL_PARM_NAME +","+ DBConst.COL_PARM_VALUE + " FROM " + this.parmTable +
					  " WHERE " +  DBConst.COL_MODEL_ID + "=" + modelId;
				//s = this.getConnection().createStatement();

				s = workingConn.createStatement();

				lrs = s.executeQuery(sql);

				while(lrs.next()){
					
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_MODEL_PERIOD))						
						amodel.setAnalysisModelPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_MODEL_PERIOD))
						amodel.setBaseModelPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_UNITS_PERIOD))
						amodel.setAnalysisUnitsPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOLUME_UNITS_PERIOD))
						amodel.setVolumeUnitsPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
	
	//					   Populate unit and model sets from id's stored as parameters.
	//					   In the case of a null coming back, the parm value will be updated to blank
	//					   This cleans up situations where a model or unit set used in an analysis calculation is deleted
	//					   A more robust solution is to not allow the deletion of an item if it's used in another calculation
	

	//					  ///////////////////////////// Model Version ////////////////////////////////////////////
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_MODEL_VERSION))
						amodel.setBaseVersion( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_MODEL_VERSION))
						amodel.setAnalysisModelVersion( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_UNITS_VERSION))
						amodel.setAnalysisUnitsVersion( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOLUME_UNITS_VERSION))
						amodel.setVolumeUnitsVersion( lrs.getString(DBConst.COL_PARM_VALUE) );
	
	//					  ///////////////////////////// Model Year ////////////////////////////////////////////
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_MODEL_YEAR))
						amodel.setBaseYear( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_MODEL_YEAR))
						amodel.setAnalysisModelYear( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_UNITS_YEAR))
						amodel.setAnalysisUnitsYear( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOLUME_UNITS_YEAR))
						amodel.setVolumeUnitsYear( lrs.getString(DBConst.COL_PARM_VALUE) );
	
	//					  ///////////////////////////// Misc Parms ////////////////////////////////////////////
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_CURRENT_YEAR))
						amodel.setCurrentYear( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_CURRENT_MONTH))
						amodel.setCurrentMonth( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_CURRENT_DAY))
						amodel.setCurrentDay( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANALYSIS_SAVE))
						amodel.setAnalysisSave( lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_NAME_RATE1))
						amodel.setRate1( lrs.getString(DBConst.COL_PARM_VALUE) );
	//					  A.Winter - add new parameter 6/30/05		
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_MEMO))
						amodel.setMemo( lrs.getString(DBConst.COL_PARM_VALUE) );
	//					  A.Winter - add new parameter 6/30/05		
						/*******************************************************************************
						 *  Added by: Udaya B Aravapalli
						 *  Added on: 02/14/1006 
						 *  Parameters for Sample Extended Summary. -- Start
						 *******************************************************************************/
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_ANAL_VERSION))
						amodel.setAnalysisVersion(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_HDR_1))
						amodel.setBaseHdr1(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_HDR_2))
						amodel.setBaseHdr2(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_HDR_3))
						amodel.setBaseHdr3(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_HDR_4))
						amodel.setBaseHdr4(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_BASE_TITLE))			
						amodel.setBaseTitle(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_NEW_HDR_1))			
						amodel.setNewHdr1(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_NEW_HDR_2))
						amodel.setNewHdr2(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_NEW_HDR_3))
						amodel.setNewHdr3(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_NEW_HDR_4))
						amodel.setNewHdr4(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_NEW_TITLE))
						amodel.setNewTitle(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_UNIT_HDR_1))
						amodel.setUnitHdr1(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_UNIT_HDR_2))
						amodel.setUnitHdr2(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_UNIT_HDR_3))
						amodel.setUnitHdr3(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_UNIT_HDR_4))
						amodel.setUnitHdr4(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOL_HDR_1))
						amodel.setVolHdr1(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOL_HDR_2))
						amodel.setVolHdr2(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOL_HDR_3))
						amodel.setVolHdr3(lrs.getString(DBConst.COL_PARM_VALUE) );
					if(lrs.getString(DBConst.COL_PARM_NAME).equals(amodel.PN_VOL_HDR_4))
						amodel.setVolHdr4(lrs.getString(DBConst.COL_PARM_VALUE) );
				/*******************************************************************************
				 *  Added by: Udaya B Aravapalli
				 *  Added on: 02/14/1006 
				 *  Parameters for Sample Extended Summary. -- End
				 *******************************************************************************/
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
	return amodel;
}

	/**
	  * @param model
	  * @return
	  * @throws TCGMException
	  */
	 private void saveModelParams2(TCGMModel model) throws TCGMException
	 {
	   String methodName = "saveModelParams2(TCGMModel)";
	   Connection conn = null;
	   CallableStatement cs = null;
	   try
	   {
		 conn = getConnection();
		 
		 String sql = "{ call " + this.schema + ".ANL_FAC01(?, ?, ?) }";

		 cs = conn.prepareCall(sql);
		 cs.setInt( 1, model.getModelIdInt() );
		 cs.setInt( 2, -1 );
		 cs.setString( 3, this.userToken.getUserid() );
		 cs.executeUpdate();
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
	 * @param baseModel
	 * @param options
	 * @return
	 * @throws TCGMException
	 */
	public int createModel(TCGMModel model, TCGMModel baseModel, ModelCopyOptions options) throws TCGMException
	{
		throw new TCGMException (this.className, "createModel(model, baseModel, options)", "Operation not supported on this object");
	}
}