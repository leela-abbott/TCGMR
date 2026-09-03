package abbott.ai.tcgm.data.oracle;

//import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import org.apache.log4j.Logger;
//import java.util.*;

//import javax.sql.*;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
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
public class OracleCostExchModelDao extends OracleModelDao
{
	private static Logger myLogger = Logger.getLogger( "OracleCostExchModelDao" );
	/**
	 *
	 * @param userToken
	 */
	public OracleCostExchModelDao(UserToken userToken)
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
		String methodName = "saveModelParms(<CostExch>TCGMModel, Connection)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.COSTEXCH)
		{
			CostExchModel cmodel = (CostExchModel) model;

			this.setModelParm(modelId, cmodel.PN_START_SALES_SET, cmodel.getStartingSalesData().getDatasetTableId() );
			this.setModelParm(modelId, cmodel.PN_END_SALES_SET, cmodel.getEndingSalesData().getDatasetTableId() );

			this.setModelParm(modelId, cmodel.PN_RATE_SET, cmodel.getRateSet().getDatasetTableId() );
			this.setModelParm(modelId, cmodel.PN_UNIT_SET, cmodel.getCostExchUnits().getDatasetTableId() );

			this.setModelParm(modelId, cmodel.PN_END_PERIOD, cmodel.getEndPeriod() );
			this.setModelParm(modelId, cmodel.PN_END_YEAR, cmodel.getEndYear() );
			this.setModelParm(modelId, cmodel.PN_START_PERIOD, cmodel.getStartPeriod() );
			this.setModelParm(modelId, cmodel.PN_START_YEAR, cmodel.getStartYear() );
			String strMemo = TCGMUtil.replaceAllChars(cmodel.getMemo().trim());
			//Added By Udaya B Aravapalli on 01/10/2006 to save and display memo
			this.setModelParm(modelId, cmodel.PN_MEMO, strMemo.replaceAll("'","\\\''"));
			//Added By Udaya B Aravapalli on 01/10/2006 to save and display factor model
			if (cmodel.getModelIdSelected() != null)
			{
				this.setModelParm(modelId, cmodel.PN_FACTOR_MODEL, cmodel.getModelIdSelected());
				this.setModelParm(modelId, cmodel.PN_FACTOR_MODEL_NAME, cmodel.getFactorModelName());
			}
			//Added By Udaya B Aravapalli on 01/31/2006 to store the E_CXC_USED Param which is used
			// by the Cxchg_Flex Stored Procedure. This param value is set like this.
			// If the Ending RGM Version is specified by the user then the E_CXC_USED value will
			// be set to 'Y', else it will be set to 'N'.
			if (cmodel.getEndingSalesData().getDatasetTableId() == null || cmodel.getEndingSalesData().getDatasetTableId().equals(""))
			{
				this.setModelParm(modelId, cmodel.PN_END_SALES_SET_FLAG, "N");
			}
			else
			{
				this.setModelParm(modelId, cmodel.PN_END_SALES_SET_FLAG, "Y" );
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
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRow() throws TCGMException, SQLException
	{
		CostExchModel model = new CostExchModel();
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
	 * @return
	 * @throws TCGMException
	 * @throws SQLException
	 */
	public TCGMModel getModelFromCurrentRowSet() throws TCGMException, SQLException
	{
		CostExchModel model = new CostExchModel();
		this.loadModelCommonTraits(model);
		//myLogger.debug("Starting time for model Params load"+new Date().getTime());
		//return this.loadModelParms(model);
		/*
		 * loadModelParms method invokes parameter table parm by parm and assigns the valus to 
		 * model object. In the new method fetching all the parms and values at a time and setting
		 * to model object based on required params. It saves lot of DB activity.
		 */
		
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
		String methodName = "loadModelParms(<CostExch>TCGMModel)";
		int modelId = model.getModelIdInt();
		if (model.getType() == TCGMModel.Type.COSTEXCH)
		{
			CostExchModel cmodel = (CostExchModel) model;
			// get model parameters
			DatasetDao dsdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(this.userToken);
			String setId;

			setId = this.getModelParm(modelId, cmodel.PN_UNIT_SET);
			if ( !setId.equals("") )
			{
				cmodel.setCostExchUnits( dsdao.getDatasetById( Integer.parseInt(setId) ) );
			}

			setId = this.getModelParm(modelId, cmodel.PN_RATE_SET);
			if ( !setId.equals("") )
			{
				cmodel.setRateSet( dsdao.getDatasetById( Integer.parseInt(setId) ) );
			}

			setId = this.getModelParm(modelId, cmodel.PN_START_SALES_SET);
			if ( !setId.equals("") )
			{
				cmodel.setStartingSalesData( dsdao.getDatasetById( Integer.parseInt(setId) ) );
			}

			setId = this.getModelParm(modelId, cmodel.PN_END_SALES_SET);

			if ( !setId.equals("") )
			{
				cmodel.setEndingSalesData( dsdao.getDatasetById( Integer.parseInt(setId) ) );
			}

			cmodel.setEndPeriod( this.getModelParm( modelId, cmodel.PN_END_PERIOD ) );
			cmodel.setEndYear( this.getModelParm( modelId, cmodel.PN_END_YEAR ) );
			cmodel.setStartPeriod( this.getModelParm( modelId, cmodel.PN_START_PERIOD ) );
			cmodel.setStartYear( this.getModelParm( modelId, cmodel.PN_START_YEAR ) );
			// Added By Udaya B Aravapalli on 01/10/2006 to display memo 
			cmodel.setMemo( this.getModelParm( modelId, cmodel.PN_MEMO));
			cmodel.setModelIdSelected( this.getModelParm( modelId, cmodel.PN_FACTOR_MODEL));
			cmodel.setFactorModelName( this.getModelParm( modelId, cmodel.PN_FACTOR_MODEL_NAME));    

			/*********************************************************************************
			 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
			 * JOB_QUE_PARAMS for CXC_ANL12E(Exposure - Country) job.
			 * This Job/ Button is on BP/Cost Exchange Datasets screen.
			 *  START  -- ADD Job Que Parameters
			 *********************************************************************************/

			/*********************************************************************************
			 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
			 * JOB_QUE_PARAMS for CXC_ANL12E(Exposure - Country) job.
			 * This Job/ Button is on BP/Cost Exchange Datasets screen.
			 *  END  -- ADD Job Que Parameters
			 *********************************************************************************/

			return cmodel;

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
		String methodName = "loadModelParms(<CostExch>TCGMModel)";
		String parameterList = "Model Id: " + model.getModelId();
		int modelId = model.getModelIdInt();
		CostExchModel cmodel = (CostExchModel) model;
		DatasetDao dsdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(this.userToken);
		if (model.getType() == TCGMModel.Type.COSTEXCH)
		{
				String sql = null;
				ResultSet lrs = null;
				Statement s = null;
				Connection workingConn = null;  // 9-1-05 Fix reconnection problem
				String setId;
				try
				{
					workingConn = this.getConnection();
	   
					sql = "SELECT " + DBConst.COL_PARM_NAME +","+ DBConst.COL_PARM_VALUE + " FROM " + this.parmTable +
						  " WHERE " +  DBConst.COL_MODEL_ID + "=" + modelId;
					//s = this.getConnection().createStatement();
		
					s = workingConn.createStatement();

					lrs = s.executeQuery(sql);
										
					while(lrs.next()){
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_UNIT_SET)){
							setId = lrs.getString(DBConst.COL_PARM_VALUE);
							if ( !setId.equals("") )
							{
								cmodel.setCostExchUnits( dsdao.getDatasetById( Integer.parseInt(setId) ) );
							}
						}
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_RATE_SET)){
							setId = lrs.getString(DBConst.COL_PARM_VALUE);
							if ( !setId.equals("") )
							{
								cmodel.setRateSet( dsdao.getDatasetById( Integer.parseInt(setId) ) );
							}
						}
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_START_SALES_SET))
						{
							setId = lrs.getString(DBConst.COL_PARM_VALUE);
							if ( !setId.equals("") )
							{
								cmodel.setStartingSalesData( dsdao.getDatasetById( Integer.parseInt(setId) ) );
							}
						}
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_END_SALES_SET)){
							setId = lrs.getString(DBConst.COL_PARM_VALUE);
							if ( !setId.equals("") )
							{
								cmodel.setEndingSalesData( dsdao.getDatasetById( Integer.parseInt(setId) ) );
							}
						}
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_END_PERIOD))
						cmodel.setEndPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_END_YEAR))
						cmodel.setEndYear( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_START_PERIOD))
						cmodel.setStartPeriod( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_START_YEAR))
						cmodel.setStartYear( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_MEMO))
						cmodel.setMemo( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_FACTOR_MODEL))
						cmodel.setModelIdSelected( lrs.getString(DBConst.COL_PARM_VALUE) );
						if(lrs.getString(DBConst.COL_PARM_NAME).equals(cmodel.PN_FACTOR_MODEL_NAME))
						cmodel.setFactorModelName( lrs.getString(DBConst.COL_PARM_VALUE) );
																	
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
		
		return cmodel;
	}
}