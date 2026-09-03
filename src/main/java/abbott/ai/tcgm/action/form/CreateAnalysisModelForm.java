package abbott.ai.tcgm.action.form;
//import javax.servlet.http.*;
//import javax.servlet.*;

import java.util.*;

import org.apache.log4j.Logger;
//import org.apache.struts.action.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.process.JobConstants;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CreateAnalysisModelForm extends TCGMCreateModelForm {
	
	private static Logger myLogger = Logger.getLogger( "CreateAnalysisModelForm" );
	private String baseModelSelected;
	private String analysisModelSelected;
	private String volumeUnitsSelected;
	private String analysisUnitsSelected;
	private java.util.Vector unitSets;
	private String baseModelPeriod;
	private String analysisModelPeriod;
	private String analysisUnitsPeriod;
	private String volumeUnitsPeriod;

	// 7-24-03 bd; I don't know where and/or how these Units and Actuals are used in the Analysis job.
	private String currentYearActualModel;
	private String currentYearActualUnits;
	private String lastYearActualModel;
	private String lastYearActualUnits;
	private String memo; // A.Winter 6/30/05 - add memo

	public CreateAnalysisModelForm() {
		super();
		this.setModelType(TCGMModel.Type.ANALYSIS);
	}
	public String getBaseModelSelected() {
		return baseModelSelected;
	}
	public void setBaseModelSelected(String baseModelSelected) {
		this.baseModelSelected = baseModelSelected;
	}
	public void setAnalysisModelSelected(String analysisModelSelected) {
		this.analysisModelSelected = analysisModelSelected;
	}
	public String getAnalysisModelSelected() {
		return analysisModelSelected;
	}
	public void setVolumeUnitsSelected(String volumeUnitsSelected) {
		this.volumeUnitsSelected = volumeUnitsSelected;
	}
	public String getVolumeUnitsSelected() {
		return volumeUnitsSelected;
	}
	public void setAnalysisUnitsSelected(String analysisUnitsSelected) {
		this.analysisUnitsSelected = analysisUnitsSelected;
	}
	public String getAnalysisUnitsSelected() {
		return analysisUnitsSelected;
	}

	public TCGMModel getModelFromForm() throws TCGMException,NumberFormatException {
		AnalysisModel model = new AnalysisModel();

		model.setName( this.getModelName() );
		model.setDesc( this.getModelDesc() );

		UserToken ut = SQLUtil.getOracleAdmin();
		DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);

		model.setBaseModel( (FactorModel) md.getModel(Integer.parseInt(this.getBaseModelSelected() ) ) );
		model.setBaseModelPeriod( this.getBaseModelPeriod() );
		model.setBaseVersion( md.getModelParm(Integer.parseInt(this.getBaseModelSelected()), "CYCLE") );
		model.setBaseYear( md.getModelParm(Integer.parseInt(this.getBaseModelSelected()), "YEAR") );

		model.setAnalysisModel( (FactorModel) md.getModel(Integer.parseInt(this.getAnalysisModelSelected() ) ) );
		model.setAnalysisModelPeriod( this.getAnalysisModelPeriod() );
		model.setAnalysisModelVersion( md.getModelParm(Integer.parseInt(this.getAnalysisModelSelected()), "CYCLE") );
		model.setAnalysisModelYear( md.getModelParm(Integer.parseInt(this.getAnalysisModelSelected()), "YEAR") );

		model.setAnalysisUnits( dd.getDatasetById(Integer.parseInt(this.getAnalysisUnitsSelected())) );
		model.setAnalysisUnitsPeriod( this.getAnalysisUnitsPeriod() );
		String analysisUnitName = dd.getDatasetById(Integer.parseInt(this.getAnalysisUnitsSelected())).getDatasetName();
		
		model.setAnalysisUnitsYear(TCGMUtil.getUnitYear(analysisUnitName));
		
		model.setAnalysisUnitsVersion(TCGMUtil.getUnitVersion(analysisUnitName));
		
		//A.Winter 6/30/05 add memo to save
		model.setMemo(memo);


		model.setVolumeUnits( dd.getDatasetById(Integer.parseInt(this.getVolumeUnitsSelected() ) ) );
		model.setVolumeUnitsPeriod( this.getVolumeUnitsPeriod() );
		String volumeUnitName = dd.getDatasetById(Integer.parseInt(this.getVolumeUnitsSelected())).getDatasetName();
		model.setVolumeUnitsYear(TCGMUtil.getUnitYear(volumeUnitName));
		model.setVolumeUnitsVersion(TCGMUtil.getUnitVersion(volumeUnitName));

		// 7-24-03 bd; I don't know if we are using Actuals in this job
		//model.setCurrentYearActualModel( (FactorModel) md.getModel(Integer.parseInt(this.getCurrentYearActualModel() ) ) );
		//model.setCurrentYearActualUnits( dd.getDatasetById(Integer.parseInt(this.getCurrentYearActualUnits() ) ) );
		//model.setLastYearActualModel( (FactorModel) md.getModel(Integer.parseInt(this.getLastYearActualModel() ) ) );
		//model.setLastYearActualUnits( dd.getDatasetById(Integer.parseInt(this.getLastYearActualUnits() ) ) );

		Calendar currentDate = Calendar.getInstance();
		int currYY = currentDate.get(currentDate.YEAR);
		model.setCurrentYear(Integer.toString(currYY));
		int currMM = currentDate.get(currentDate.MONTH) + 1;
		model.setCurrentMonth(Integer.toString(currMM));
		int currDD = currentDate.get(currentDate.DAY_OF_MONTH);
		model.setCurrentDay(Integer.toString(currDD));

		model.setAnalysisSave("Y");

//		ModelMngr mm = new ModelMngr();
//		FactorModel fm = (FactorModel) mm.getModelFromId(ut, model.getBaseModel().getModelIdInt(), TCGMModel.Type.FACTOR );
//		fm.setRateSetFactorActualId( myForm.getSelFactorRate() );
// 9-25-03 I think this is a situation where I will need to call my special routine I just wrote that
//         runs some special sql code and returns one string value. This location is an ideal place
//         for this.

		String modelId = model.getBaseModel().getModelId();
		String sql = JobConstants.PN_SQL_RATE1  + modelId;
		try
		{
			model.setRate1(md.getStringValueFromSql(sql)); // Use special SQL call to return a parameter value
		}
		catch(TCGMException ex)
		{
			String errMsg = "Error retrieving RATE_1 parm from base model " + model.getBaseModel().getName() + ".";
			throw new TCGMException("CreateAnalysisModelForm", "getModelFromForm()", errMsg);
		}

		return model;
	}
	public void setUnitSets(java.util.Vector unitSets) {
  
	     this.unitSets = unitSets;
//		remove units that ends with String _SAVE
//	   since they are not required the page and are causing NumberFormatException
		 
		
	}
	public java.util.Vector getUnitSets() {
		return removeSavedUnits();
	}
	public void setBaseModelPeriod(String baseModelPeriod) {
		this.baseModelPeriod = baseModelPeriod;
	}
	public String getBaseModelPeriod() {
		return baseModelPeriod;
	}
	public void setAnalysisModelPeriod(String analysisModelPeriod) {
		this.analysisModelPeriod = analysisModelPeriod;
	}
	public String getAnalysisModelPeriod() {
		return analysisModelPeriod;
	}
	public void setAnalysisUnitsPeriod(String analysisUnitsPeriod) {
		this.analysisUnitsPeriod = analysisUnitsPeriod;
	}
	public String getAnalysisUnitsPeriod() {
		return analysisUnitsPeriod;
	}
	public void setVolumeUnitsPeriod(String volumeUnitsPeriod) {
		this.volumeUnitsPeriod = volumeUnitsPeriod;
	}
	public String getVolumeUnitsPeriod() {
		return volumeUnitsPeriod;
	}



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
	public String getMemo() {
		return memo;
	}

	/**
	 * @param string
	 */
	public void setMemo(String string) {
		memo = string;
	}
	
	/**
	 * Method to remove Dataset units with DatasetName that ends with String _SAVE
	 * since they are not required the page and are causing NumberFormatException  
	 * @param string
	 */
		public Vector removeSavedUnits() {
			
			Vector retVect=new Vector();
			for (int i=0;i<this.unitSets.size();i++){
				
				Dataset dtSetUnit=(Dataset)(this.unitSets.elementAt(i));
					String tblId=dtSetUnit.getDatasetName();
				    myLogger.debug("tblId"+ tblId);
					if(!(tblId.trim().endsWith("save")||tblId.endsWith("SAVE"))){
						retVect.add(dtSetUnit);
				}
				
			}
			
			return retVect;
			
		}
		

}