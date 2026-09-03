package abbott.ai.tcgm.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.MngFactorsForm;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.helpers.RateDataMngr;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.process.JobDefinition;
import abbott.ai.tcgm.process.JobInstance;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class MngFactors extends TCGMAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		try
		{
			if ( this.isSessionValid( request) && this.isModelSelected(request) )
			{
				MngFactorsForm myForm = null;
				myForm = (MngFactorsForm) form;
				UserToken ut = this.getUserToken(request);
				this.errors.clear();

				if ( isCmdValid(myForm) )  {
					// check for restriction still to be entered.
					if ( myForm.isChkRestrict() && !myForm.isRestrictionEntered() ) {
						// also place form object under common restriction attribute for retrieval and population by restriction jsp
						// these changed should be present under the specific form attribute.
						request.getSession().setAttribute("restrictionForm", myForm);
						this.setForward(TCGMConstants.G_FORWARD_RESTRICTIONS);
					}
					else {
						// process specific command
						processCmd(myForm, request);
						myForm.reset();
						this.setupForm(request);
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
				}
				else {
					this.setupForm(request);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}


	private void processCmd(MngFactorsForm myForm, HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String cmd = myForm.getCmd(); // should correspond to a direct command
		TCGMState state = this.getState(request);
		ProcessMngr pm = new ProcessMngr();
		boolean selected = true;
		// All jobs will require some information from the currently selected factor model.
		// create a factor model object from currently logged in model
		ModelMngr mm = new ModelMngr();
		FactorModel fm = (FactorModel) mm.getModelFromId(ut, state.getCurrentModelId(), TCGMModel.Type.FACTOR );

		// 10-31-03 This if chk is another way to implement the setting of the parms
		if (cmd.equalsIgnoreCase("SET_FACTOR_MODEL_PARMS") )
		{
			// update factor model attributes from form
			fm.setBegFactorPeriod( myForm.getSelBegFactorPeriod() );
			fm.setActualUnitsId( myForm.getActualUnitsSelected() );
			fm.setPlanUnitsId( myForm.getPlanUnitsSelected() );
			fm.setRateSetFactorActualId( myForm.getSelFactorRate() );
			fm.setRateSetCostId( myForm.getSelCostRate() );
			// fm.setRateSetPlanPlanId( myForm.getSelRevisionRate() ); 6-25-03 bd; dn't need
			fm.setRateSetRevisionId( myForm.getSelRevisionRate() );
			// 7-18-03 Restored because Jim & Tom said I definitely need this for a later job
			
			// 10-10-05 Check R-System selection to checkbox
			//fm.setExportRSystem( myForm.getSelExportRSystem() );
			if(myForm.getRgmSystemSelected())
				fm.setExportRgmSystem("Y");
			else
				fm.setExportRgmSystem("N");
				
			if(myForm.getRtcSystemSelected())
				fm.setExportRtcSystem("Y");
			else
				fm.setExportRtcSystem("N");

			if(myForm.getRbbSystemSelected())
				fm.setExportRbbSystem("Y");
			else
				fm.setExportRbbSystem("N");

			if(myForm.getRblSystemSelected())
				fm.setExportRblSystem("Y");
			else
				fm.setExportRblSystem("N");
			
			
			fm.setExportCcsType(myForm.getCcsType());
			
			/*if(myForm.getCcsSystemSelected()){
			
				fm.setExportCcsSystem("Y");
				
			}
			else{
				fm.setExportCcsSystem("N");
				fm.setExportCcsType("N");
			}*/
					
				
            fm.setMemo(myForm.getModelLongDesc());  // A.Winter save memo
			// save attributes for model to database
			mm.updateModel(ut, fm);
		}
		else if (cmd.equalsIgnoreCase("COMP_MODEL") )
		{
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
			md.setModelParmII(-1, "TCGM_IPS_PRICE_COMPARISON", state.getCurrentModelIdString());
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.modelcomp.add", state.getCurrentModelName())); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
		//if ( cmd.equalsIgnoreCase("ADD_JOB") ) {
		else if ( cmd.equalsIgnoreCase("ADD_JOB") ) {

			// All jobs will require some information from the currently selected factor model.
			// create a factor model object from currently logged in model
			//ModelMngr mm = new ModelMngr();
			//FactorModel fm = (FactorModel) mm.getModelFromId(ut, state.getCurrentModelId(), TCGMModel.Type.FACTOR );

			// Populate base job parameters and options
			/*JobInstance job = pm.createJob(state.getCurrentModelIdString(), "-1", myForm);
			job.getJobDef().populateDetail();
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + fm.getName() );*/

			// 11-6-03 Note: The way parms are handle here are not consistent and may require a change later.
			//               The CALC_FACTORS job pulls parms from the PARAMETER table and uses them for the
			//               job. So if the user has selected something diff on the page but has not clicked the
			//               SET PARMS button yet, the job will run against the parms in the DB and not against
			//               parms on the screen. And that may be misleading to the user. For the other jobs down the
			//               page here, some are pulling from the page form (myForm) and some are pulling from the DB
			//               via the fm bean.
			String formJobName=myForm.getJobName();
			
			if(formJobName.equalsIgnoreCase("SEND_CCSH"))
			{
				//myForm.setJobName("SEND_ALL_FACTORS");	
			}
			JobInstance job = pm.createJob(state.getCurrentModelIdString(), "-1", myForm);
			job.getJobDef().populateDetail();
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + fm.getName() );

			if (job.getJobDef().equals(JobDefinition.CALCULATE_FACTORS) )
			{
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm( fm.PN_BEG_FACTOR_PERIOD, fm.getBegFactorPeriod() );
				job.addJobParm( fm.PN_ACT_UNITS, fm.getActualUnitsId() );
				job.addJobParm( fm.PN_PLAN_UNITS, fm.getPlanUnitsId() );
				job.addJobParm( fm.PN_RATESET_FACTOR_ACTUAL, fm.getRateSetFactorActualId() );
				job.addJobParm( fm.PN_RATESET_COST, fm.getRateSetCostId() );
				job.addJobParm( fm.PN_RATESET_REVISION, fm.getRateSetRevisionId() );
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
				
				if(myForm.getRgmSystemSelected()){
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "Y" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RGM, "Y");
				}
				else{
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "N" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RGM, "N");
				}
								
				if(myForm.getRtcSystemSelected()){
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "Y" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RTC, "Y");
				}
					
				else{
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "N" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RTC, "N");
				}
				if(myForm.getRbbSystemSelected()){
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "Y" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RBB, "Y");
				}
				else{
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "N" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RBB, "N");
				}
				if(myForm.getRblSystemSelected()){
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "Y" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RBL, "Y" );
				}					
				else{
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "N" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RBL, "N" );
				}
								
				/*if(myForm.getCcsSystemSelected()){
					job.addJobParm(JobConstants.PN_R_CCS_SYSTEM, "Y" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_CCS_SYSTEM, "Y" );
					job.addJobParm(JobConstants.PN_R_CCS_TYPE, myForm.getCcsType() );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_CCS_TYPE, myForm.getCcsType() );
				}					
				else{

					job.addJobParm(JobConstants.PN_R_CCS_SYSTEM, "N" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_CCS_SYSTEM, "N" );
					job.addJobParm(JobConstants.PN_R_CCS_TYPE, "N" );
					md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_CCS_TYPE, "N" );
				}*/
			}
			else if ( job.getJobDef().equals(JobDefinition.APPLY_MAINTENANCE) )
			{
				String rateTranId = Integer.toString(new RateDataMngr().getPendingRateDataTranId(ut));
				job.addJobParm( JobConstants.PN_RATE_DATA_T_ID, rateTranId );
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
				RateDataMngr rdm = new RateDataMngr();
				DatasetMngr dsm = new DatasetMngr();
				int pendingRateSetId = rdm.getPendingRateDataTranId( ut );
				Dataset ds = dsm.getDatasetById(ut, pendingRateSetId );
				if (pendingRateSetId == -1)
				{
					job.addJobParm(JobConstants.PN_RATE_SET, "NO RATESET TRANS" );
				}
				else
				{
					job.addJobParm(JobConstants.PN_RATE_SET, ds.getDatasetName().trim() );	
				}
				
			}
			/* 10-11-2005 Special Note: 
			 * 
			 * The R-System Export parm passing has changed.
			 * The user can now simultaneously select multiple R-systems
			 * to export to on one call to SEND_FACTORS. 
			 * 
			 * In addition, this process may need to be tested thoroughly and changed to a 
			 * java job. That is because we have it set up here as a regular oracle job call
			 * yet all jobs with data transfers were defined as java jobs.
			 * 
			 * Bottom line is I need to work with the back end to see exactly what the 
			 * SEND_FACTORS job is doing. And check the ReportNet architect doc to see if
			 * this job was impacted.
			 * 
			 */
			else if ( job.getJobDef().equals(JobDefinition.SEND_FACTORS) ||
					  job.getJobDef().equals(JobDefinition.SEND_ALL_FACTORS) )
			{
				selected = false;
				if(myForm.getRgmSystemSelected())
				{
					fm.setExportRgmSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "Y" );
					//job.setDesc(job.getDesc() + ", RSys: RGM");
					selected = true;
				}
				else
				{
					fm.setExportRgmSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "N" );
				}
				if(myForm.getRtcSystemSelected())
				{
					fm.setExportRtcSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "Y" );
					//job.setDesc(job.getDesc() + ", RSys: RTC");
					selected = true;
				}
				else
				{
					fm.setExportRtcSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "N" );
				}
				if(myForm.getRbbSystemSelected())
				{
					fm.setExportRbbSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "Y" );
					//job.setDesc(job.getDesc() + ", RSys: RBB");
					selected = true;
				}
				else
				{
					fm.setExportRbbSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "N" );
				}
				if(myForm.getRblSystemSelected())
				{
					fm.setExportRblSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "Y" );
					//job.setDesc(job.getDesc() + ", RSys: RBL");
					selected = true;
				}
				else
				{
					fm.setExportRblSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "N" );
				}
				
				/*if(formJobName.equalsIgnoreCase("SEND_CCSH"))
				{
					job.addJobParm(JobConstants.PN_R_CCS_SYSTEM, "Y" );
					fm.setExportCcsSystem("Y" );
					job.addJobParm(JobConstants.PN_R_CCS_TYPE, myForm.getCcsType() );
					fm.setExportCcsType(myForm.getCcsType() );
					
					fm.setExportRgmSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "N" );
					fm.setExportRtcSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "N" );
					fm.setExportRbbSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "N" );
					fm.setExportRblSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "N" );
					selected = true;
				}else{
					job.addJobParm(JobConstants.PN_R_CCS_SYSTEM, "N" );
					fm.setExportCcsSystem("N" );
					job.addJobParm(JobConstants.PN_R_CCS_TYPE, "N" );
					fm.setExportCcsType("N" );
				}*/
				
				
				if (selected == false)
				{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("failure.checkbox.job.add", job.getJobDef().getJobName())); // not really an error, just feedback
				}
				else
				{
					mm.updateModel(ut, fm);
				}
			
			}
			else if ( job.getJobDef().equals(JobDefinition.SEND_CCSH) )
			{
			
					/*job.addJobParm(JobConstants.PN_R_CCS_SYSTEM, "Y" );
					fm.setExportCcsSystem("Y" );*/
					job.addJobParm(JobConstants.PN_R_CCS_TYPE, myForm.getCcsType() );
					fm.setExportCcsType(myForm.getCcsType() );
					//mm.updateModel(ut, fm);
					mm.setModelParmII(ut,fm,JobConstants.PN_R_CCS_TYPE, myForm.getCcsType());
				

			}

			else if ( job.getJobDef().equals(JobDefinition.ASR_TREE) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_BPCOST) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_EXCEPT) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BP_VS_TPSS_RPT1_PRE) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.DUP_RPT01) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BUILD_ASR_TREE) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			// 10-30-31 SAVE_FACTOR_13
			else if ( job.getJobDef().equals(JobDefinition.SAVE_FACTORS_13) ) {
				//job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			// 9-20-05 Added Hookup for Rate Exceptions
			else if ( job.getJobDef().equals(JobDefinition.PRINT_EXRATE) ) {
				job.addJobParm("DIS_VERSION" , fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_VAL_AD) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_DUB_SUP) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.MISS_FACTOR) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_INVCUR) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BLNDD_SUMMRY) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_BP_FRZ) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_MISM) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.AUDIT_FACTORS) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals("DELETE_AUDIT") ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			if (selected == true)	
			{	
				pm.addJob(job, ut);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName())); // not really an error, just feedback
			}
			else
			{
				this.setForward(TCGMConstants.FORWARD_FAILURE);
			}
			this.saveErrors(request, this.errors);
		}
	}


	private void setupForm(HttpServletRequest request) throws TCGMException {

		UserToken ut = this.getUserToken(request);
		MngFactorsForm myForm = new MngFactorsForm();
		int modelId = this.getState(request).getCurrentModelId();
		FactorModel model = (FactorModel) new ModelMngr().getModelFromId(ut, modelId, TCGMModel.Type.FACTOR);

		myForm.reset(model);
//		A Winter 6/27/05 Add ModelDesc to the form from model or param list
			 ModelMngr mm =  new ModelMngr();
			 String descr = mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, "MEMO");
			 if(descr.equals(""))
			 {
			   myForm.setModelLongDesc(model.getDesc());
			 }  
			 else
			 {
				 myForm.setModelLongDesc(descr);
			 }
	   // A Winter 6/27/05  - end
		// Udaya B Aravapalli. 12/27/2005. This code is added to fix the issue 
		// in the tracker with subject "Display Time and Date Stamp for Save Period 13 Factors".
		String excDate = null;
		excDate = mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, "SVD_FCTR_DT");

		TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
		long rawOffset = tz.getRawOffset();
		long time;
		long newTime;
		Date newDate;
		SimpleDateFormat sdf = new SimpleDateFormat(AppConst.getTcgmDateFormat());
		//if (tz.useDaylightTime())
		if(tz.inDaylightTime(new Date()))
		{
			rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
		}

		
		if (!(excDate == null || excDate.trim().equals("")))
		{
			time = new Date(excDate).getTime();
			newTime = time;
			if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
			{
				newTime += rawOffset;
			}
			newDate = new Date (newTime);
				
			excDate = sdf.format(newDate);
		}
		else
		{
			excDate =  " ";
		}

		excDate = "Last saved: " + excDate;
		myForm.setLastExSavePeriod13FactorsDt(excDate);
			
		DatasetMngr dm = new DatasetMngr();
		myForm.setUnitSets(dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA));
		myForm.setRateSets(dm.getDatasetByTableName(ut, DBConst.TABLE_RATE_DATA));

		// 10-10-05 Check Rsystem selection to checkbox
		//myForm.setSelExportRSystem(model.getExportRSystem());
		 if(model.getExportRgmSystem().equals("Y"))
			myForm.setRgmSystemSelected(true);
		else
			myForm.setRgmSystemSelected(false);
		
		if(model.getExportRtcSystem().equals("Y"))
			myForm.setRtcSystemSelected(true);
		else
			myForm.setRtcSystemSelected(false);
			
		if(model.getExportRbbSystem().equals("Y"))
			myForm.setRbbSystemSelected(true);
		else
			myForm.setRbbSystemSelected(false);
		
		if(model.getExportRblSystem().equals("Y"))
			myForm.setRblSystemSelected(true);
		else
			myForm.setRblSystemSelected(false);
			
		/*if(model.getExportCcsSystem().equals("Y")){
			myForm.setCcsSystemSelected(true);
			myForm.setCcsType(model.getExportCcsType());			
		}					
		else{
			myForm.setCcsSystemSelected(false);
			myForm.setCcsType(model.getExportCcsType());			
		}*/
		myForm.setCcsType(model.getExportCcsType());	
				
		myForm.setSelBegFactorPeriod(model.getBegFactorPeriod());

		request.getSession().setAttribute("mngFactorsForm", myForm);
	}


	public MngFactors() {
		super();
	}

}
