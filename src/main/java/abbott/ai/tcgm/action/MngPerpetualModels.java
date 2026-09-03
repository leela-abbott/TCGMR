package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.process.*;
import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MngPerpetualModels extends TCGMAction
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
				MngPerpetualModelsForm myForm = null;
				myForm = (MngPerpetualModelsForm) form;
				UserToken ut = this.getUserToken(request);
				this.errors.clear();

				if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) && !myForm.getCmd().equals("CANCEL") )  {
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

	private void processCmd(MngPerpetualModelsForm myForm, HttpServletRequest request) throws TCGMException {
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
			ModelMngr mm = new ModelMngr();

			// 7-3-03 bd; This next statement is retrieving all of the parameters (attributes) associated
			//            with the Perpetual model the user selected. Make sure I have getters & setters
			//            for all of the parameters I am using with the Perpetual Model. Also note that the
			//            parameters for this model (as defined by this entity) were added to the parameter
			//            table during the create model process of the model mngr. So I have to make sure
			//            any new parameters I need are added in that method
			PerpetualModel pmodel = (PerpetualModel) mm.getModelFromId(ut, Integer.parseInt(myForm.getModelSelected()), TCGMModel.Type.PERPETUAL);

			JobInstance job = pm.createJob(myForm.getModelSelected(), "-1", myForm);
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + pmodel.getName() );

			if (job.getJobDef().equals(JobDefinition.PERP_TOTCALC) ) {
			// Ok, here is where we would set specific parameters based upon the job requested.
			// add job parameters from factor model

				// Starting Model
				if ((pmodel.getStartingModel()!=null) && (pmodel.getStartingModel().getModelId() != null))
				{
					job.addJobParm( JobConstants.PN_ASR_BEG_INV_CYCLE, pmodel.getStartingModel().getModelId() );
				}

				// Starting Units UNIT_B_INV_CYCLE
				if ((pmodel.getStartingInvUnits()!=null) && (pmodel.getStartingInvUnits().getDatasetTableId()!=null))
				{
					job.addJobParm( JobConstants.PN_UNIT_BEG_INV_CYCLE, pmodel.getStartingInvUnits().getDatasetTableId() );
				}

				if ( pmodel.getUseEndInv().equalsIgnoreCase("Y") )
				{
					// Ending Model
					job.addJobParm( JobConstants.PN_ASR_END_INV_CYCLE, pmodel.getEndingModel().getModelId() );
					// Ending Units UNIT_E_INV_CYCLE
					job.addJobParm( JobConstants.PN_UNIT_END_INV_CYCLE, pmodel.getEndingInvUnits().getDatasetTableId() );
				}

				// Current Year Actual Model
				if ((pmodel.getCurrentYearActualModel()!=null) && (pmodel.getCurrentYearActualModel().getModelId()!=null))
				{
					job.addJobParm( JobConstants.PN_ASR_T_YEAR_CYCLE, pmodel.getCurrentYearActualModel().getModelId() );
				}

				// Current Year Actual Units
				if ((pmodel.getCurrentYearActualUnits()!=null) && (pmodel.getCurrentYearActualUnits().getDatasetTableId()!=null))
				{
					job.addJobParm( JobConstants.PN_UNIT_T_YEAR_CYCLE, pmodel.getCurrentYearActualUnits().getDatasetTableId() );
				}

				// Last Year Actual Model
				if ((pmodel.getLastYearActualModel()!=null) && (pmodel.getLastYearActualModel().getModelId()!=null))
				{
					job.addJobParm( JobConstants.PN_ASR_L_YEAR_CYCLE, pmodel.getLastYearActualModel().getModelId() );
				}

				// Last Year Actual Units
				if ((pmodel.getLastYearActualUnits()!=null) && (pmodel.getLastYearActualUnits().getDatasetTableId()!=null))
				{
					job.addJobParm( JobConstants.PN_UNIT_L_YEAR_CYCLE, pmodel.getLastYearActualUnits().getDatasetTableId() );
				}
				// Costing Model
				if ((pmodel.getCostingModel()!=null) && (pmodel.getCostingModel().getModelId()!=null))
				{
					job.addJobParm( JobConstants.PN_COST_CYCLE_MID, pmodel.getCostingModel().getModelId() );
				}
				// Costing Units *** THERE IS NO CORRSPONDING COSTING UNITS ***
				// Starting D.5.6 Period B_D56_PERIOD
				if (pmodel.getStartPeriod()!=null)
				{
					job.addJobParm( JobConstants.PN_BEG_D56_PERIOD, pmodel.getStartPeriod() );
				}
				// Starting D.5.6 Year B_D56_YEAR
				if (pmodel.getStartYear() != null)
				{
					job.addJobParm( JobConstants.PN_BEG_D56_YEAR, pmodel.getStartYear() );
				}
				// Ending D.5.6 Period E_D56_PERIOD
				if (pmodel.getEndPeriod() != null)
				{
					job.addJobParm( JobConstants.PN_END_D56_PERIOD, pmodel.getEndPeriod() );
				}
				// Ending D.5.6 Year E_D56_Year
				if (pmodel.getEndYear() != null)
				{
					job.addJobParm( JobConstants.PN_END_D56_YEAR, pmodel.getEndYear() );
				}
				// Costing Cycle Legacy Name COST_CYCLE
				if ((pmodel.getCostingModel()!= null) && (pmodel.getCostingModel().getLegacyModelName()!=null))
				{
					job.addJobParm( JobConstants.PN_COST_CYCLE, pmodel.getCostingModel().getLegacyModelName() );
				}
				// Beginning Inventory Cycle Name B_INV_CYCLE
				if ((pmodel.getStartingModel()!=null) && (pmodel.getStartingModel().getLegacyModelName()!=null))
				{
					job.addJobParm( JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getLegacyModelName() );
				}
				if ( pmodel.getUseEndInv().equalsIgnoreCase("Y") )
				{
					// Ending Inventory Cycle Name E_INV_CYCLE
					if ((pmodel.getEndingModel()!=null) &&  (pmodel.getEndingModel().getLegacyModelName()!=null))
					{
						job.addJobParm( JobConstants.PN_END_INV_CYCLE, pmodel.getEndingModel().getLegacyModelName() );
					}
				}

				// Use Ending Inventory?   E_INV_CYCLE
				if (pmodel.getUseEndInv() != null)
				{
					job.addJobParm( JobConstants.PN_USE_END_INVENTORY, pmodel.getUseEndInv() );
				}
				if (pmodel.getDesc() != null)
				{
					job.addJobParm("DIS_VERSION" , pmodel.getDesc() );
				}
			}
			// 9-26-05 Added Hookup for Inventory Exceptions
//			else if ( job.getJobDef().equals(JobDefinition.PERP_INVXCPT) ) {
//				// add parms here
//			}
			// PERP_SUMMARY and PERP_INVXCPT have the same JOB QUE Parameters.
			// 9-26-05 Added Hookup for Costed Summary
			else if ( job.getJobDef().equals(JobDefinition.PERP_SUMMARY) ||
					  job.getJobDef().equals(JobDefinition.PERP_INVXCPT) ) {
				// add parms here
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the
				 * JOB_QUE_PARAMS for PERP_SUMMARY(Costed Summary) job.
				 * This Job/ Button is on Perpetual Model Management screen.
				 *  START  -- ADD Job Que Parameters
				 *********************************************************************************/
				// Beginning Inventory Header B_INV_HEADER
				if ((pmodel.getBegInvHdr()!=null) )
				{
					job.addJobParm( JobConstants.PN_BEG_INV_HEADER, pmodel.getBegInvHdr() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_BEG_INV_HEADER, "" );
				}
				// Ending Inventory Header E_INV_HEADER
				if ((pmodel.getEndInvHdr()!=null) )
				{
					job.addJobParm( JobConstants.PN_END_INV_HEADER, pmodel.getEndInvHdr() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_END_INV_HEADER, "" );
				}
				// Starting D.5.6 Period B_D56_PERIOD (FROM_PERIOD in JOB_QUE_PARAMS)
				if (pmodel.getStartPeriod()!=null)
				{
					job.addJobParm( JobConstants.PN_FROM_PERIOD, pmodel.getStartPeriod() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_FROM_PERIOD, "" );
				}
				// Ending D.5.6 Period E_D56_PERIOD (THRU_PERIOD in JOB_QUE_PARAMS)
				if (pmodel.getEndPeriod() != null)
				{
					job.addJobParm( JobConstants.PN_THRU_PERIOD, pmodel.getEndPeriod() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_THRU_PERIOD, "" );
				}
				// Starting D.5.6 Year B_D56_YEAR
				if (pmodel.getStartYear() != null)
				{
					job.addJobParm( JobConstants.PN_BEG_D56_YEAR, pmodel.getStartYear() );
				}
				else
				{
					 job.addJobParm( JobConstants.PN_BEG_D56_YEAR, "" );
				}
				// Ending D.5.6 Year E_D56_Year
				if (pmodel.getEndYear() != null)
				{
					job.addJobParm( JobConstants.PN_END_D56_YEAR, pmodel.getEndYear() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_END_D56_YEAR, "" );
				}
				// COST_HDR_1
				if (pmodel.getCostHdr1() != null)
				{
					job.addJobParm( JobConstants.PN_COST_HDR_1, pmodel.getCostHdr1() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_COST_HDR_1, "" );
				}
				// COST_HDR_2
				if (pmodel.getCostHdr2() != null)
				{
					job.addJobParm( JobConstants.PN_COST_HDR_2, pmodel.getCostHdr2() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_COST_HDR_2, "" );
				}
				// COST_HDR_3
				if (pmodel.getCostHdr3() != null)
				{
					job.addJobParm( JobConstants.PN_COST_HDR_3, pmodel.getCostHdr3() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_COST_HDR_3, "" );
				}
				// COST_HDR_4
				if (pmodel.getCostHdr4() != null)
				{
					job.addJobParm( JobConstants.PN_COST_HDR_4, pmodel.getCostHdr4() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_COST_HDR_4, "" );
				}

				//Adding two more parameters to change the header
				if(pmodel.getStartingModel()!=null){
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getName());
				}
				if(pmodel.getEndingModel()!=null){
					job.addJobParm(JobConstants.PN_END_INV_CYCLE, pmodel.getEndingModel().getName());
				}
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the
				 * JOB_QUE_PARAMS for PERP_SUMMARY(Costed Summary) job.
				 * This Job/ Button is on Perpetual Model Management screen.
				 *  END  -- ADD Job Que Parameters
				 *********************************************************************************/
			}
			// 9-26-05 Added Hookup for Costed Detail Summary
			else if ( job.getJobDef().equals(JobDefinition.PERP_DETAIL) ) {
				// add parms here
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the
				 * JOB_QUE_PARAMS for PERP_DETAIL(Costed Detail) job.
				 * This Job/ Button is on Perpetual Model Management screen.
				 *  START  -- ADD Job Que Parameters
				 *********************************************************************************/
				// Beginning Inventory Header B_INV_HEADER
				if ((pmodel.getBegInvHdr()!=null) )
				{
					job.addJobParm( JobConstants.PN_BEG_INV_HEADER, pmodel.getBegInvHdr() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_BEG_INV_HEADER, "" );
				}
				// Ending Inventory Header E_INV_HEADER
				if ((pmodel.getEndInvHdr()!=null) )
				{
					job.addJobParm( JobConstants.PN_END_INV_HEADER, pmodel.getEndInvHdr() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_END_INV_HEADER, "" );
				}
				// Starting D.5.6 Period B_D56_PERIOD (FROM_PERIOD in JOB_QUE_PARAMS)
				if (pmodel.getStartPeriod()!=null)
				{
					job.addJobParm( JobConstants.PN_FROM_PERIOD, pmodel.getStartPeriod() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_FROM_PERIOD, "" );
				}
				// Ending D.5.6 Period E_D56_PERIOD (THRU_PERIOD in JOB_QUE_PARAMS)
				if (pmodel.getEndPeriod() != null)
				{
					job.addJobParm( JobConstants.PN_THRU_PERIOD, pmodel.getEndPeriod() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_THRU_PERIOD, "" );
				}
				//Starting D.5.6 Year B_D56_YEAR
				if (pmodel.getStartYear() != null)
				 {
					 job.addJobParm( JobConstants.PN_BEG_D56_YEAR, pmodel.getStartYear() );
				 }
				 else
				 {
					  job.addJobParm( JobConstants.PN_BEG_D56_YEAR, "" );
				 }
				 // Ending D.5.6 Year E_D56_Year
				 if (pmodel.getEndYear() != null)
				 {
					 job.addJobParm( JobConstants.PN_END_D56_YEAR, pmodel.getEndYear() );
				 }
		//Adding two more parameters to change the header
				 if(pmodel.getStartingModel()!=null){
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getName());
				 }
				 if(pmodel.getEndingModel()!=null){
					job.addJobParm(JobConstants.PN_END_INV_CYCLE, pmodel.getEndingModel().getName());
				 }
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the
				 * JOB_QUE_PARAMS for PERP_DETAIL(Costed Detail) job.
				 * This Job/ Button is on Perpetual Model Management screen.
				 *  END  -- ADD Job Que Parameters
				 *********************************************************************************/
			}
			// 9-26-05 Added Hookup for Equivalent Unit Exceptions
			else if ( job.getJobDef().equals(JobDefinition.PPT_EQVXCPT1) ) {
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the
				 * JOB_QUE_PARAMS for PPT_EQVXCPT1(Equiv Unit Exceptions) job.
				 * This Job/ Button is on Perpetual Model Management screen.
				 *  START  -- ADD Job Que Parameters
				 *********************************************************************************/
				// Beginning Inventory Cycle Name B_INV_CYCLE
				if ((pmodel.getStartingModel()!=null) && (pmodel.getStartingModel().getLegacyModelName()!=null))
				{
					job.addJobParm( JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getLegacyModelName() );
				}
				else
				{
					job.addJobParm( JobConstants.PN_BEG_INV_CYCLE, "");
				}
				/*********************************************************************************
				 * Added by Udaya B Aravapalli on 02/14/2006 to include the
				 * JOB_QUE_PARAMS for PPT_EQVXCPT1(Equiv Unit Exceptions) job.
				 * This Job/ Button is on Perpetual Model Management screen.
				 *  END  -- ADD Job Que Parameters
				 *********************************************************************************/
			}
			pm.addJob(job, ut);
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName() ) ); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
	}



		private void setupForm(HttpServletRequest request,MngPerpetualModelsForm myForm) throws TCGMException {
			ModelMngr mm = new ModelMngr();
			UserToken ut = this.getUserToken(request);
			PerpetualModel perpModel=new PerpetualModel();
			perpModel.setStatus(TCGMModel.Status.OPEN);
			MngPerpetualModelsForm pmf = new MngPerpetualModelsForm();
			pmf.reset();
			if(null!=myForm.getShowPerpetualModel())
			{
				if(myForm.getShowPerpetualModel().equals("closed"))
				{
					perpModel.setStatus(TCGMModel.Status.CLOSED);
					pmf.setShowPerpetualModel("closed");
				}else{				
					pmf.setShowPerpetualModel("open");
				}
			}else{
				pmf.setShowPerpetualModel("open");
			}
			pmf.setModels( mm.getModels( ut, perpModel ) );
			request.getSession().setAttribute("mngPerpetualModelsForm", pmf);
		}


		public MngPerpetualModels() {
			super();
		}

	}