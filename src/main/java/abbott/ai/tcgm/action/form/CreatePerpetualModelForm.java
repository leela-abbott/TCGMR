package abbott.ai.tcgm.action.form;

import javax.servlet.http.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.TCGMUtil;
import java.util.*;
import org.apache.struts.action.*;
//import abbott.ai.tcgm.process.JobConstants;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CreatePerpetualModelForm extends TCGMCreateModelForm {

	private String selStartingModel;
	private String startingUnits;

	private String selStartPeriod;
	private String selStartYear;

	private String selEndPeriod;
	private String selEndYear;

	private String selCostingModel;
	//private String costingUnits;
	private String selEndingModel;

	// 7-10-03 bd; These 4 values need to be derived
	private String currentYearActualModel;
	private String currentYearActualUnits;
	private String lastYearActualModel;
	private String lastYearActualUnits;

	private String selCurrentYearActualModel;
	private String selCurrentYearActualUnits;
	private String selLastYearActualModel;
	private String selLastYearActualUnits;

	private java.util.Vector units;
	private String selStartingModelUnits;
	private String selEndingModelUnits;
	
	private String modelName = "";

	private String begInvName;
	private String endInvName;
	private String costCycleName;
	
	//A.Winter 6/23/05 - add error message to the form
	private String errMessage ="";  
	private String memo =""; 
	//A.Winter 6/23/05 - add error message to the form
	
	//7-10-03 bd; I am confused as to why ts is here & how its used
	private boolean chkUseCostingModel; 
	
	private ActionErrors errors = new ActionErrors();	
	
	public CreatePerpetualModelForm() {
		super();
		this.setModelType(TCGMModel.Type.PERPETUAL);
	}

/* 
	public String getSelEndingInventory() {
		return selEndingInventory;
	}
*/
	public String getSelEndingModel() {
		return selEndingModel;
	}
	public void setSelEndingModel(String selEndingModel) {
		this.selEndingModel = selEndingModel;
	}
	public String getSelStartingModel() {
		return selStartingModel;
	}
	public void setSelStartingModel(String selStartingModel) {
		this.selStartingModel = selStartingModel;
	}
	public void setSelStartYear(String selStartYear) {
		this.selStartYear = selStartYear;
	}
	public String getSelStartYear() {
		return selStartYear;
	}
	public void setSelCostingModel(String selCostingModel) {
		this.selCostingModel = selCostingModel;
	}
	public String getSelCostingModel() {
		return selCostingModel;
	}
	public TCGMModel getModelFromForm() throws TCGMException {

	  PerpetualModel model = new PerpetualModel();

	  model.setName( this.getModelName() );
	  model.setDesc( this.getModelDesc() );

	  UserToken ut = SQLUtil.getOracleAdmin();
	  DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
	  ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);

	  //7-21-03 bd; from what I cn c there are no units parm for the costing model
	  //model.setCostingInvUnits( dd.getDatasetById(TCGMUtil.deriveUnits(md, modelName) ) );

	  //7-3-03 bd; new attribute (the modelId of the CostingModel) I may not need this however
	  //model.setCostingCycleModelId( this.getSelCostingModel() );

	  //model.setStartingInvUnits( dd.getDatasetById(Integer.parseInt(this.getStartingInvUnits() ) ) );

	  //model.setStartingInvUnits( dd.getDatasetById(TCGMUtil.deriveUnits(model.getModelIdInt()) ) );
	  //model.setStartingInvUnits(model.setStartingModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelCostingModel())).getName() ) );
	  //String startingInvUnitsName = md.getModel(Integer.parseInt(this.getSelStartingModel())).getName();
	  //FactorModel fm = (FactorModel) md.getModel(model.getModelIdInt());
	  //model.setStartingInvUnits( dd.getDatasetByName(TCGMUtil.deriveUnits(md, startingInvUnitsName) ) );
	  model.setStartingModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelStartingModel())));
	  model.setCostingModel((FactorModel) md.getModel(Integer.parseInt(this.getSelCostingModel())));
	  // 8-3-05 Ensure that I select unit specific dataset
	  //model.setStartingInvUnits( dd.getUnitDatasetByName(TCGMUtil.deriveUnits(md, Integer.parseInt(this.getSelStartingModel())) ) );
	  model.setStartingInvUnits(dd.getUnitDatasetByName(this.getSelStartingModelUnits()));

	  //init this parm to "N"
	  model.setUseEndInv("N"); 
	  if ( !this.getSelEndingModel().equalsIgnoreCase("NONE") )
	  {
		  model.setUseEndInv("Y");
		  model.setEndingModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelEndingModel() ) ) );
		  // 8-3-05 Ensure that I select unit specific dataset
		  //model.setEndingInvUnits( dd.getUnitDatasetByName(TCGMUtil.deriveUnits(md, Integer.parseInt(this.getSelEndingModel())) ) );
		  model.setEndingInvUnits( dd.getUnitDatasetByName(this.getSelEndingModelUnits()));
	  }

	  model.setEndPeriod( this.getSelEndPeriod() );
	  model.setEndYear( this.getSelEndYear() );
	  model.setStartPeriod( this.getSelStartPeriod() );
	  model.setStartYear( this.getSelStartYear() );
	  model.setMemo( this.getMemo() ); // A.Winter -7/5/05 - add memo to the model

	  model.setCurrentYearActualModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelCurrentYearActualModel())));
	  model.setCurrentYearActualUnits( dd.getUnitDatasetByName(this.getSelCurrentYearActualUnits()));
	  model.setLastYearActualModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelLastYearActualModel())));
	  model.setLastYearActualUnits(  dd.getUnitDatasetByName(this.getSelLastYearActualUnits()));
	  
	  if ( !this.getSelCostingModel().equalsIgnoreCase("NONE") )
	  {
		  model.setCostingModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelCostingModel() ) ) );
	  }

//	  // Per jim 6-27-03; This section now needs to derive current and last year models and units rather than getting them from user input
//	  // Models can be derived based upon cycle and year
//	  // Units can be derived based upon name
//
//	  Calendar currentDate = Calendar.getInstance();
//	  int year = currentDate.get(currentDate.YEAR);
//  
//		  // A.Winter - skip process if model doesn't exist
////  	  FactorModel modelF = (FactorModel) this.getActualsForYear(md, year);
//// 	  if(! (modelF == null)){
//  
//			model.setCurrentYearActualModel( (FactorModel) this.getActualsForYear(md, year) );
//			//model.setCurrentYearActualUnits( dd.getDatasetByName(TCGMUtil.deriveUnits(md, currentYearActualModelName) ) );
//			// 8-3-05 Ensure that I select unit specific dataset
//			model.setCurrentYearActualUnits( dd.getUnitDatasetByName(TCGMUtil.deriveUnits(md, this.getActualsForYear(md, year).getModelIdInt() ) ) );
//
//			model.setLastYearActualModel( (FactorModel) this.getActualsForYear(md, year - 1) );
//			//model.setLastYearActualUnits( dd.getDatasetByName(TCGMUtil.deriveUnits(md, lastYearActualModelName) ) );
//			// 8-3-05 Ensure that I select unit specific dataset
//			model.setLastYearActualUnits( dd.getUnitDatasetByName(TCGMUtil.deriveUnits(md, this.getActualsForYear(md, year - 1).getModelIdInt() ) ) );
//
//			// 9-13-05 User may not have selecting a costing model & that would cause an error
//			if ( !this.getSelCostingModel().equalsIgnoreCase("NONE") )
//			{
//				model.setCostingModel( (FactorModel) md.getModel(Integer.parseInt(this.getSelCostingModel() ) ) );
//				// 7-22-03 bd; There is not corresponding CostingInvUnits parm for the Perpetual job
//				//model.setCostingInvUnits( dd.getDatasetByName(TCGMUtil.deriveUnits(md, Integer.parseInt(this.getSelCostingModel())) ) );
//			}
////  	 } // A.Winter - end of changes 6/21/05
	  return model;
  }
  
  //7-13-03 bd; Per Jim, My derive routines should really go in the model manager; call it getActualsForYear()

	public TCGMModel getActualsForYear(ModelDao md, int year) throws TCGMException
	{
		String methodName = "getActualsForYear( )";
		String parameterList = "ModelDao: " + md;

		TCGMModel searchModel = new TCGMModel();
		modelName = "ACT" + Integer.toString(year).substring(2,4);
		searchModel.setModelIdInt(md.getModelIdByName(modelName.trim()));

		Vector v = md.getVO(searchModel);
		//Vector v = md.getVO(fm);
		if (v.size() == 0)
		{
			/*
			  throw new TCGMItemNotFoundException(this.className, methodName, parameterList );
			  A.Winter 6/18/05 return null instead thrown exception,
			  if no records found 
			*/
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
	public void setUnits(java.util.Vector units) {
		this.units = units;
	}
	public java.util.Vector getUnits() {
		return units;
	}
	public void setSelEndYear(String selEndYear) {
		this.selEndYear = selEndYear;
	}
	public String getSelEndYear() {
		return selEndYear;
	}
	public void setSelStartPeriod(String selStartPeriod) {
		this.selStartPeriod = selStartPeriod;
	}
	public String getSelStartPeriod() {
		return selStartPeriod;
	}
	public void setSelEndPeriod(String selEndPeriod) {
		this.selEndPeriod = selEndPeriod;
	}
	public String getSelEndPeriod() {
		return selEndPeriod;
	}
	public void setChkUseCostingModel(boolean chkUseCostingModel) {
		this.chkUseCostingModel = chkUseCostingModel;
	}
	public boolean isChkUseCostingModel() {
		return chkUseCostingModel;
	}
	public void setStartingUnits(String startingUnits) {
		this.startingUnits = startingUnits;
	}
	public String getStartingUnits() {
		return startingUnits;
	}
/*  public void setCostingUnits(String costingUnits) {
		this.costingUnits = costingUnits;
	}
	public String getCostingUnits() {
		return costingUnits;
	}
*/
	public void setCurrentYearActualModel(String currentYearActualModel) {
		this.currentYearActualModel = currentYearActualModel;
	}
	public String getCurrentYearActualModel() {
		return currentYearActualModel;
	}
	public void setCurrentYearActualUnits(String currentYearActualUnits) {
		this.currentYearActualUnits = currentYearActualUnits;
	}
	public String getCurrentYearActualUnits() {
		return currentYearActualUnits;
	}
	public void setLastYearActualModel(String lastYearActualModel) {
		this.lastYearActualModel = lastYearActualModel;
	}
	public String getLastYearActualModel() {
		return lastYearActualModel;
	}
	public void setLastYearActualUnits(String lastYearActualUnits) {
		this.lastYearActualUnits = lastYearActualUnits;
	}
	public String getLastYearActualUnits() {
		return lastYearActualUnits;
	}
	/**
	 * @return
	 */
	public String getErrMessage() {
		return errMessage;
	}
	/**
	 * @param string
	 */
	public void setErrMessage(String string) {
		errMessage = string;
	}
	
	//A.Winter
	/**
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param string
	 */
	public void setMemo(String string) {
		memo = string;
	}
	//A.Winter
	
/*	// 9-15-05 I may need to come back and fill this in so the create screen gets reset properly; I'm getting wierd 
	//         stuff on this page after I perform the create action

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		//5-24-05 Always clear Cmd & Error Msgs before initial page display
		this.setCmd("");
		// 5-24-05 End of code for clear Cmd & Error Msgs before initial page display

	}	
*/	

	/*****************************************************************************************/
	/** If we have set "validate" to true in the struts-config file, our elements are validated
	* here before we get to the action class.
	*
	* @param mapping ActionMapping
	* @param request HttpServletRequest
	* @return ActionErrors
	*/
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{

		this.errors = new ActionErrors();
/*	
		// 9-14-05 Use the following to check for existing model
		// OracleModelDao.getModelIdByName(String modelName); if 0 is returned,
		// model name is not in use because it was not found in the model table 

		// 9-14-05 Problem with the line below is that I don't have a ut ref
		//ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
		if (md.getModelIdByName(modelName.trim()) == 0)
		{
			errors.add("modelName - ", new ActionError("errors.model"));
		}
		
		//If save selected then validating list of asr records
*/		

		if(this.errors.empty())
		{
			return null;
		}
		else
		{
			return this.errors;
		}

	}

	/**
	 * @return String
	 */
	public String getSelEndingModelUnits() {
		return selEndingModelUnits;
	}

	/**
	 * @return String
	 */
	public String getSelStartingModelUnits() {
		return selStartingModelUnits;
	}

	/**
	 * @param String selEndingInventoryUnits
	 */
	public void setSelEndingModelUnits(String selEndingModelUnits) {
		this.selEndingModelUnits = selEndingModelUnits;
	}

	/**
	 * @param String selStartingModelUnits
	 */
	public void setSelStartingModelUnits(String selStartingModelUnits) {
		this.selStartingModelUnits = selStartingModelUnits;
	}

	/**
	 * @return String
	 */
	public String getSelCurrentYearActualModel() {
		return selCurrentYearActualModel;
	}

	/**
	 * @return String
	 */
	public String getSelCurrentYearActualUnits() {
		return selCurrentYearActualUnits;
	}

	/**
	 * @return String
	 */
	public String getSelLastYearActualModel() {
		return selLastYearActualModel;
	}

	/**
	 * @return String
	 */
	public String getSelLastYearActualUnits() {
		return selLastYearActualUnits;
	}

	/**
	 * @param String selCurrentYearActualModel
	 */
	public void setSelCurrentYearActualModel(String selCurrentYearActualModel) {
		this.selCurrentYearActualModel = selCurrentYearActualModel;
	}

	/**
	 * @param String selCurrentYearActualUnits
	 */
	public void setSelCurrentYearActualUnits(String selCurrentYearActualUnits) {
		this.selCurrentYearActualUnits = selCurrentYearActualUnits;
	}

	/**
	 * @param String selLastYearActualModel
	 */
	public void setSelLastYearActualModel(String selLastYearActualModel) {
		this.selLastYearActualModel = selLastYearActualModel;
	}

	/**
	 * @param String selLastYearActualUnits
	 */
	public void setSelLastYearActualUnits(String selLastYearActualUnits) {
		this.selLastYearActualUnits = selLastYearActualUnits;
	}

}