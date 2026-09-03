package abbott.ai.tcgm.action;

import java.io.IOException;
//import java.util.Properties;

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
import abbott.ai.tcgm.*;
import org.apache.struts.action.*;
import abbott.ai.tcgm.process.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MngAnalysisModels extends TCGMAction
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
				MngAnalysisModelsForm myForm = null;
				myForm = (MngAnalysisModelsForm) form;
				UserToken ut = this.getUserToken(request);
				this.errors.clear();

				if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd())&& !myForm.getCmd().equals("CANCEL") )  {
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

	private void processCmd(MngAnalysisModelsForm myForm, HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String cmd = myForm.getCmd(); // should correspond to a direct command

		// utility commands executed immediately rather than being put on the job que via addJob().
		if ( cmd.equalsIgnoreCase("FETCH_PROD_HEADERS") ) 
		{
			MiscJclMngr mjm = new MiscJclMngr();	
			mjm.sendProdHeaderLabelJcl();			
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.headers.production.fetch")); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}

		else if ( cmd.equalsIgnoreCase("FETCH_TEST_HEADERS") ) 
		{
			MiscJclMngr mjm = new MiscJclMngr();	
			mjm.sendTestHeaderLabelJcl();					
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.headers.test.fetch")); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
		else if ( cmd.equalsIgnoreCase("FETCH_PACK_CODES") ) 
		{
			MiscJclMngr mjm = new MiscJclMngr();	
			mjm.sendPackCodesJcl();					
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.pack.codes.fetch")); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
		// typical job commands handled here.
		else if ( cmd.equalsIgnoreCase("ADD_JOB") ) 
		{
			// All jobs require modelId,
			if (TCGMUtil.isEmpty( myForm.getModelSelected() ) ) 
			{
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.none_selected"));
				this.saveErrors(request, this.errors);
				return;
			}

			
			ProcessMngr pm = new ProcessMngr();
			JobInstance job = pm.createJob(myForm.getModelSelected(), "-1", myForm);

			// Build generic description of the job
			job.getJobDef().populateDetail();
			AnalysisModel model = (AnalysisModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(myForm.getModelSelected()), TCGMModel.Type.ANALYSIS  );
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + model.getName() );
			
			// Ok here is where we would set specific parameters based upon the job requested.
			/***********************************************************************************/

			// if job is main calculation push all model parameters...
			if (job.getJobDef().equals(job.getJobDef().ANL_CALC) ) {

				job.addJobParm(model.PN_BASE_MODEL, model.getBaseModel().getModelId() );
				job.addJobParm(model.PN_ANALYSIS_MODEL, model.getAnalysisModel().getModelId() );

				job.addJobParm(model.PN_ANALYSIS_UNITS, model.getAnalysisUnits().getDatasetTableId() );
				job.addJobParm(model.PN_VOLUME_UNITS, model.getVolumeUnits().getDatasetTableId() );

				job.addJobParm(model.PN_BASE_MODEL_VERSION, model.getBaseVersion() );
				job.addJobParm(model.PN_ANALYSIS_MODEL_VERSION, model.getAnalysisModelVersion() );
				job.addJobParm(model.PN_ANALYSIS_UNITS_VERSION, model.getAnalysisUnitsVersion() );
				job.addJobParm(model.PN_VOLUME_UNITS_VERSION, model.getVolumeUnitsVersion() );

				job.addJobParm(model.PN_BASE_MODEL_YEAR, model.getBaseYear() );
				job.addJobParm(model.PN_ANALYSIS_MODEL_YEAR, model.getAnalysisModelYear() );
				job.addJobParm(model.PN_ANALYSIS_UNITS_YEAR, model.getAnalysisUnitsYear() );
				job.addJobParm(model.PN_VOLUME_UNITS_YEAR, model.getVolumeUnitsYear() );

				job.addJobParm(model.PN_BASE_MODEL_PERIOD, model.getBaseModelPeriod() );
				job.addJobParm(model.PN_ANALYSIS_MODEL_PERIOD, model.getAnalysisModelPeriod() );
				job.addJobParm(model.PN_ANALYSIS_UNITS_PERIOD, model.getAnalysisUnitsPeriod() );
				job.addJobParm(model.PN_VOLUME_UNITS_PERIOD, model.getVolumeUnitsPeriod() );

				job.addJobParm(model.PN_CURRENT_YEAR, model.getCurrentYear() );
				job.addJobParm(model.PN_CURRENT_MONTH, model.getCurrentMonth() );
				job.addJobParm(model.PN_CURRENT_DAY, model.getCurrentDay() );

				// 7-25-03 bd; ANL_EXT_VER is Not Used!
				//job.addJobParm(model.PN_ANALYSIS_EXT_VERSION, model.getAnalysisExtVersion() );

				job.addJobParm(model.PN_ANALYSIS_SAVE, model.getAnalysisSave() );  // always = "Y"
				//job.addJobParm(model.PN_NAME_RATE1, model.getBaseModel().getRateSetFactorActualId() );

				// 9-25-03 This is not working; in my CreateAnl...Form, retireve this parm from the
				//          factor model identified as the base model and return the RATE1 value from
				//          the PARAMETER table.
				job.addJobParm(model.PN_NAME_RATE1, model.getRate1() );
				if (model.getBaseTitle()!= null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_TITLE, model.getBaseTitle());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_TITLE, "");
				}
				if (model.getNewTitle() != null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_TITLE, model.getNewTitle());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_TITLE, "");
				}

//				Properties parms = job.getJobParms();
//				pm.executeJobStep("ANL_FAC01", model.getModelId(), "-1", ut, parms);
			}
			// 7-17-03 bd; I only need to add this block for the Essbase job for the parms; If I hd no parms
			// this block wd not b required because the architecture wd already handle the Essbase java job
			else if (job.getJobDef().equals(job.getJobDef().SEND_ANL_FLEX_ESS) ) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_ESSBASE_YEAR, myForm.getEssbaseYear() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_YEAR, myForm.getEssbaseYear() );
				job.addJobParm(JobConstants.PN_ESSBASE_VERSION, myForm.getEssbaseVersion() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_VERSION, myForm.getEssbaseVersion() );
				job.addJobParm(JobConstants.PN_ESSBASE_TYPE, myForm.getEssbaseType() );
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_TYPE, myForm.getEssbaseType() );
			}
			// 9-20-05 Added Hookup for Unable to Analyze
			else if (( job.getJobDef().equals(JobDefinition.ANL_REJECTS))
				   ||( job.getJobDef().equals(JobDefinition.ANL_DETAIL ))) {
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
				 * JOB_QUE_PARAMS for ANL_REJECTS(Unable to Analyze) job
				 * and ANL_DETAIL(Detail Analysis) job.
				 * This Job/ Button is on Factor Analysis Model Management screen.
				 *  START  -- ADD Job Que Parameters
				 *********************************************************************************/
				if (model.getBaseTitle()!= null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_TITLE, model.getBaseTitle());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_TITLE, "");
				}
				if (model.getNewTitle() != null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_TITLE, model.getNewTitle());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_TITLE, "");
				}
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
				 * JOB_QUE_PARAMS for ANL_REJECTS(Unable to Analyze) job
				 * and ANL_DETAIL(Detail Analysis) job.
				 * This Job/ Button is on Factor Analysis Model Management screen.
				 *  END  -- ADD Job Que Parameters
				 *********************************************************************************/
			}

			// 9-20-05 Added Hookup for Sample Extended Summary
			else if (( job.getJobDef().equals(JobDefinition.ANL_SAMPEX) ) 
				  || ( job.getJobDef().equals(JobDefinition.ANL_PSUM_PUB))
				  || ( job.getJobDef().equals(JobDefinition.ANL_XTSM_400))
 				  || ( job.getJobDef().equals(JobDefinition.ANL_PSUM_400))
				  || ( job.getJobDef().equals(JobDefinition.ANL_PUSUMMRY))
				  || ( job.getJobDef().equals(JobDefinition.ANL_ARSUMMRY))
				  || ( job.getJobDef().equals(JobDefinition.ANL_WSUMMRY ))	
				  || ( job.getJobDef().equals(JobDefinition.ANL_SAMPAR))
				  || ( job.getJobDef().equals(JobDefinition.ANL_SAMPW))
				  || ( job.getJobDef().equals(JobDefinition.ANL_XSEC_SUM))
				  || ( job.getJobDef().equals(JobDefinition.ANL_SAMSEC_SUM))	
				  || ( job.getJobDef().equals(JobDefinition.ANL_PUSRCSUM))			
				  || ( job.getJobDef().equals(JobDefinition.ANL_EXSUMMRY))
				  || ( job.getJobDef().equals(JobDefinition.ANL_PSSES_AREA))) { 	
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
				 * JOB_QUE_PARAMS for Job/ Button(s) is on 
				 * Factor Analysis Model Management screen.
				 * START  -- ADD Job Que Parameters
				 *********************************************************************************/
				if (model.getAnalysisVersion()!= null)
				{
					job.addJobParm(AnalysisModel.PN_ANAL1_VERSION, model.getAnalysisVersion() );
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_ANAL1_VERSION, "" );
				}
				if( model.getBaseHdr1()!= null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_1, model.getBaseHdr1());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_1, "");
				}
				if (model.getBaseHdr2()!= null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_2, model.getBaseHdr2());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_2, "");
				}
				if (model.getBaseHdr3() != null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_3, model.getBaseHdr3());	
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_3, "");
				}
				if (model.getBaseHdr4()!= null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_4, model.getBaseHdr4());	
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_HDR_4, "");
				}
				if (model.getBaseTitle()!= null)
				{
					job.addJobParm(AnalysisModel.PN_BASE_TITLE, model.getBaseTitle());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_BASE_TITLE, "");
				}
				if (model.getNewHdr1()!= null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_1, model.getNewHdr1());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_1, "");
				}
				if(model.getNewHdr2()!= null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_2, model.getNewHdr2());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_2, "");
				}
				if (model.getNewHdr3()!= null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_3, model.getNewHdr3());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_3, "");
				}
				if (model.getNewHdr4() != null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_4, model.getNewHdr4());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_HDR_4, "");
				}
				if (model.getNewTitle()!= null)
				{
					job.addJobParm(AnalysisModel.PN_NEW_TITLE, model.getNewTitle());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_NEW_TITLE, "");
				}
				if (model.getUnitHdr1()!=null)
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, model.getUnitHdr1());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, "");
				}
				if (model.getUnitHdr2()!= null)
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_2, model.getUnitHdr2());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_2, "");
				}
				if (model.getUnitHdr3()!= null)
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_3, model.getUnitHdr3());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_3, "");
				}
				if (model.getUnitHdr4()!= null)
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_4, model.getUnitHdr4());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_4, "");
				}
				if (model.getVolHdr1() != null)
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_1, model.getVolHdr1());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_1, "");
				}
				if (model.getVolHdr2() != null)
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_2, model.getVolHdr2());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_2, "");
				}
				if (model.getVolHdr3()!=null)
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_3, model.getVolHdr3());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_3, "");
				}
				if ( model.getVolHdr4() != null)
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_4, model.getVolHdr4());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_VOL_HDR_4, "");
				}
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the 
				 * JOB_QUE_PARAMS for Job/ Button(s) is on 
				 * Factor Analysis Model Management screen.		
 				 * END  -- ADD Job Que Parameters
				 *********************************************************************************/
			}
				  
			pm.addJob(job, ut);
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName())); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
	}

	private void setupForm(HttpServletRequest request,MngAnalysisModelsForm myForm) throws TCGMException {
		ModelMngr mm = new ModelMngr();
		UserToken ut = this.getUserToken(request);
		AnalysisModel analModel=new AnalysisModel();
		MngAnalysisModelsForm aff = new MngAnalysisModelsForm();
		aff.reset();
		if(null!=myForm.getShowAnalModel())
		{
			if(myForm.getShowAnalModel().equals("closed"))
			{
				analModel.setStatus(TCGMModel.Status.CLOSED);
				aff.setShowAnalModel("closed");
			}else{				
				aff.setShowAnalModel("open");
			}
		}else{
			aff.setShowAnalModel("open");
		}
		aff.setModels( mm.getModels( ut, analModel ) );
		request.getSession().setAttribute("mngAnalysisModelsForm", aff);
	}


	public MngAnalysisModels() {
		super();
	}

}