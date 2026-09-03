package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.process.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MngCostExchModels extends TCGMAction
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
			if ( this.isSessionValid( request) )
			{
				MngCostExchModelsForm myForm = null;
				myForm = (MngCostExchModelsForm) form;
				UserToken ut = this.getUserToken(request);
				this.errors.clear();

				if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) && !myForm.getCmd().equals("CANCEL") ) {
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
						//this.setupForm(request);
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
				}
				else {
					this.setupForm(request,myForm);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}

	private void processCmd(MngCostExchModelsForm myForm, HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String cmd = myForm.getCmd(); // should correspond to a direct command

		if ( cmd.equalsIgnoreCase("ADD_JOB") ) {
			// All jobs require modelId
			if (TCGMUtil.isEmpty( myForm.getModelSelected() ) ) {
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.none_selected"));
				this.saveErrors(request, this.errors);
				return;
			}

			ProcessMngr pm = new ProcessMngr();
			JobInstance job = pm.createJob(myForm.getModelSelected(), "-1", myForm);
			ModelMngr mm = new ModelMngr();
			CostExchModel model = (CostExchModel) mm.getModelFromId(ut, Integer.parseInt(myForm.getModelSelected()), CostExchModel.Type.COSTEXCH );
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + model.getName() );
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
						
		    if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP1C) ||
				job.getJobDef().equals(JobDefinition.CXCHG_CSMP2)  ||
				job.getJobDef().equals(JobDefinition.CXCHG_CSMP3)  ||
				job.getJobDef().equals(JobDefinition.CXCHG_CSMP4)  ||
				job.getJobDef().equals(JobDefinition.CXCHG_FLEX5)  ||
				job.getJobDef().equals(JobDefinition.CXC_ANL04E)   ||
				job.getJobDef().equals(JobDefinition.CXCHG_CSTSL1) )
			{
				//job.addJobParm("Not Defined Yet" /* Should replace with JobConstants.PN_CXCH_VALUE */, myForm.getSelCSMAff() );
		    	//  	
		    	//job.addJobParm(CostExchModel.PN_MODEL_DESC, model.getName());
				// B_CXC_VERSN		    	
				if (model.getStartingSalesData() != null && model.getStartingSalesData().getDatasetTableId()!= null)
				{
					job.addJobParm( CostExchModel.PN_START_SALES_SET, model.getStartingSalesData().getDatasetTableId() );
					job.addJobParm( CostExchModel.PN_START_SALES_SET_NAME, model.getStartingSalesData().getDatasetName() );
					
				}		
				else
				{
					job.addJobParm( CostExchModel.PN_START_SALES_SET,""); //set an empty value			
				}
				// B_D56_CXPER
				if (model.getStartPeriod() != null )
				{
					job.addJobParm( CostExchModel.PN_START_PERIOD, model.getStartPeriod() );
				}
				else
				{
					job.addJobParm( CostExchModel.PN_START_PERIOD, "" );					
				}
				// CXCHG_RATE and CXCHG_RATE_DESC 
				if (model.getRateSet() != null && model.getRateSet().getDatasetTableId()!= null)
				{
					job.addJobParm( CostExchModel.PN_RATE_SET, model.getRateSet().getDatasetTableId() );
					job.addJobParm( CostExchModel.PN_RATE_SET_DESC, model.getRateSet().getDatasetName() );
					
				}					
				else
				{
					job.addJobParm( CostExchModel.PN_RATE_SET, "" );
				}
				// E_D56_CXPER
				if (model.getEndPeriod() != null )
				{
					job.addJobParm( CostExchModel.PN_END_PERIOD, model.getEndPeriod() );
				}
				else
				{
					job.addJobParm( CostExchModel.PN_END_PERIOD, "" );					
				}
				// DIS_VERSION
				if(job.getJobDef().equals(JobDefinition.CXCHG_CSMP1C) || 
				   job.getJobDef().equals(JobDefinition.CXCHG_CSMP2)  ||
				   job.getJobDef().equals(JobDefinition.CXCHG_CSMP3)  ||
				   job.getJobDef().equals(JobDefinition.CXC_ANL04E)   ||
				   job.getJobDef().equals(JobDefinition.CXCHG_CSMP4)  ){
					if (model.getName() != null )
					{
						job.addJobParm( CostExchModel.PN_DIS_VERSION, model.getName() );
					}					
					else
					{
						job.addJobParm( CostExchModel.PN_DIS_VERSION, "" );
					}
				}else{
				
					if (model.getDesc() != null )
					{
						job.addJobParm( CostExchModel.PN_DIS_VERSION, model.getDesc() );
					}					
					else
					{
						job.addJobParm( CostExchModel.PN_DIS_VERSION, "" );
					}
				}
				// CXHG_UNITS
				if (model.getCostExchUnits() != null && model.getCostExchUnits().getDatasetTableId() != null)
				{
					job.addJobParm( CostExchModel.PN_CXCHG_UNITS, model.getCostExchUnits().getDatasetTableId() );
				}					
				else
				{
					job.addJobParm( CostExchModel.PN_CXCHG_UNITS, "" );
				}
			}	

			// Ok here is where we would set specific parameters based upon the job requested.

			// This report uses the Detail Metric combobox to select sales or cost data
			if (job.getJobDef().equals(JobDefinition.CXCHG_CSTSL1)) {
				job.addJobParm( JobConstants.PN_CXC_CSTSL1, myForm.getSelDtlMetric() );
			}
			// This report uses the Detail Metric combobox to select sales or cost data
			else if (job.getJobDef().equals(JobDefinition.CXCHG_CSTSL2)) {
				job.addJobParm( JobConstants.PN_CXC_CSTSL2, myForm.getSelDtlMetric() );
			}
			else if (job.getJobDef().equals(JobDefinition.CXCHG_FLEX5)) {
				job.addJobParm( JobConstants.PN_TYP_UPDT, model.getCostExchUnits().getDatasetName().trim() );
			}
			else if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP1C)) {
				job.addJobParm( JobConstants.PN_CXC_SUMFIELD, myForm.getSelCSMAff() );
				job.addJobParm(JobConstants.PN_CXCHG_VERSN, md.getModelParm(-1, JobConstants.PN_CXCHG_VERSN) );
				md.setModelParmII(model.getModelIdInt(),JobConstants.PN_CXC_SUMFIELD, myForm.getSelCSMAff());
			}
			// 9-21-05 Added Hookup for Print Flexed Cst, Sls And Mrgn Smmry By Aff, Prod Line & Grp
			else if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP2)) {
				//commented by Rama.This button will run for both Report & Supply options
				//job.addJobParm( JobConstants.PN_CXC_SUMFLD2, myForm.getSelCSMAff() );
				//md.setModelParmII(model.getModelIdInt(),JobConstants.PN_CXC_SUMFIELD, myForm.getSelCSMAff());
			}
			// 9-21-05 Added Hookup for Print Flexed Cst, Sls And Mrgn Smmry By Prod Line & Grp
			else if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP3) ||
				job.getJobDef().equals(JobDefinition.CXCHG_CSMP3)) {
				//job.addJobParm("Not Defined Yet" /* Should replace with JobConstants.PN_CXCH_VALUE */, myForm.getSelCSMAff() );
			}
			// 9/21/05 Added Hookup for Print Flexed Cst, Sls And Mrgn Summry By Currency Codes
			else if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP4) ||
				job.getJobDef().equals(JobDefinition.CXCHG_CSMP4)) {
				//job.addJobParm("Not Defined Yet" /* Should replace with JobConstants.PN_CXCH_VALUE */, myForm.getSelCSMAff() );
			}		
			else if (job.getJobDef().equals(job.getJobDef().SEND_ANL_FLEX_ESS) ) {
				job.addJobParm(JobConstants.PN_ESSBASE_YEAR, myForm.getEssbaseYear() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_YEAR, myForm.getEssbaseYear() );
				job.addJobParm(JobConstants.PN_ESSBASE_VERSION, myForm.getEssbaseVersion() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_VERSION, myForm.getEssbaseVersion() );
				job.addJobParm(JobConstants.PN_ESSBASE_TYPE, myForm.getEssbaseType() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_TYPE, myForm.getEssbaseType() );
				//2/14/2008 Added Start and End Period				
				job.addJobParm(JobConstants.PN_ESSBASE_START_PERIOD, myForm.getEssbaseStPeriod() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_START_PERIOD, myForm.getEssbaseStPeriod() );
				job.addJobParm(JobConstants.PN_ESSBASE_END_PERIOD, myForm.getEssbaseEndPeriod() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_END_PERIOD, myForm.getEssbaseEndPeriod() );
			}

			pm.addJob(job, ut);			
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName())); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
	}


	private void setupForm(HttpServletRequest request,MngCostExchModelsForm myForm) throws TCGMException {
		ModelMngr mm = new ModelMngr();
		UserToken ut = this.getUserToken(request);
		CostExchModel costModel=new CostExchModel();
		costModel.setStatus(TCGMModel.Status.OPEN);
		MngCostExchModelsForm mcem = new MngCostExchModelsForm();
		mcem.reset();
		if(null!=myForm.getShowCostModel())
		{
			if(myForm.getShowCostModel().equals("closed"))
			{
				costModel.setStatus(TCGMModel.Status.CLOSED);
				mcem.setShowCostModel("closed");
			}else{				
				mcem.setShowCostModel("open");
			}
		}else{
			mcem.setShowCostModel("open");
		}
		
		mcem.setModels(mm.getModels(ut, costModel ) );
		request.getSession().setAttribute("mngCostExchModelsForm", mcem );
	}


	public MngCostExchModels() {
		super();
	}
}