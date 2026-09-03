package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.*;
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
public class MiscReports extends TCGMAction
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
		this.errors.clear();

		try
		{
			if ( this.isSessionValid( request ) && this.isModelSelected( request ))
			{
				MiscReportsForm myForm = null;
				myForm = (MiscReportsForm) form;
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
						this.setupForm(request);
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
				}
				else {
					this.setupForm(request);
					this.setForward(TCGMConstants.FORWARD_INPUT);
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

	private void processCmd(MiscReportsForm myForm, HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String cmd = myForm.getCmd(); // should correspond to a direct command
		String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
		FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR);

		if ( cmd.equalsIgnoreCase("ADD_JOB") ) {

			ProcessMngr pm = new ProcessMngr();
			JobInstance job = pm.createJob(modelId, "-1", myForm);

			job.setDesc( job.getJobDef().getJobName()  + TCGMConstants.STR_SEP + fm.getName() );

			// Job specific parameter code goes here.
			if ( job.getJobDef().equals(JobDefinition.PRINT_VAL_AD) ) {
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
			else if ( job.getJobDef().equals(JobDefinition.BPC_PRT0BP) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.AUDIT_FACTORS) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BPC_DELETE) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
				job.addJobParm(JobConstants.PN_ACTUAL_USER, ut.getUserid() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BLNDD_SUMMRY) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BPC_PRT0CST) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_BP_FRZ) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.PRINT_MISM) ) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BPC_0PLNVSIM) ) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_MOD_VERSION, modelId );
				job.addJobParm(JobConstants.PN_OLD_VERSION, myForm.getSelComparisonModel() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_OLD_VERSION, myForm.getSelComparisonModel() );
				ModelMngr mm = new ModelMngr();
				job.addJobParm(JobConstants.PN_MOD_VERSION_NAME, mm.getModelName(ut, Integer.parseInt(modelId) ));
				job.addJobParm(JobConstants.PN_OLD_VERSION_NAME, mm.getModelName(ut, Integer.parseInt(myForm.getSelComparisonModel())));

			}
			else if ( job.getJobDef().equals(JobDefinition.ROUTE_EXCEPT) ) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_ROU_PERIOD_START, myForm.getSelRoutingStartPeriod() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_PERIOD_START, myForm.getSelRoutingStartPeriod() );
				job.addJobParm(JobConstants.PN_ROU_YEAR_START, myForm.getSelRoutingStartYear() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_START, myForm.getSelRoutingStartYear() );
				job.addJobParm(JobConstants.PN_ROU_PERIOD_END, myForm.getSelRoutingEndPeriod() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_PERIOD_END, myForm.getSelRoutingEndPeriod() );
				job.addJobParm(JobConstants.PN_ROU_YEAR_END, myForm.getSelRoutingEndYear() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_END, myForm.getSelRoutingEndYear() );
				job.addJobParm(JobConstants.PN_ROU_YEAR_ACT_UNITS_BEG, myForm.getBegRouteUnitsSelected() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_ACT_UNITS_BEG, myForm.getBegRouteUnitsSelected() );
				job.addJobParm(JobConstants.PN_ROU_YEAR_ACT_UNITS_END, myForm.getEndRouteUnitsSelected() );
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_ACT_UNITS_END, myForm.getEndRouteUnitsSelected() );
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
				job.addJobParm(JobConstants.PN_FROM_PERIOD, myForm.getSelRoutingStartPeriod() );
				job.addJobParm(JobConstants.PN_THRU_PERIOD, myForm.getSelRoutingEndPeriod() );
			}
			else if ( job.getJobDef().equals(JobDefinition.BPC_ANALYS) ) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm("INP_PERIOD", myForm.getInterCoTransferPeriod() );
				md.setModelParmII(Integer.parseInt(modelId), "INP_PERIOD", myForm.getInterCoTransferPeriod() );
				job.addJobParm(JobConstants.PN_TREE_FILENAME, myForm.getTreeFilename() );
				
				/********************************************************************************
				  Udaya B Aravapalli -- 12/22/2005
				  The following code will get the Tree File Name entered by the user and
				  insert into the  parameter table with parm_name = 'SENDTREEMVS'.
				 *********************************************************************************/
				
				myForm.setTreeFilename(myForm.getTreeFilename().toUpperCase());
				if(!(myForm.getTreeFilename() == null))
				{
					if (myForm.getTreeFilename().length() > 8)
					{
						myForm.setTreeFilename(myForm.getTreeFilename().substring(0,8));
					}
					int i=0;
					while(myForm.getTreeFilename().length() < 8 )
					{
						 if (i ==0)
						 {
							myForm.setTreeFilename(myForm.getTreeFilename()+ TCGMConstants.DT_COMMA);
						 }
						 else
						 {
							myForm.setTreeFilename(myForm.getTreeFilename()+ TCGMConstants.DT_BLANK_SPACE);
						 }
						 i++;
					}
				}
				else
				{
					myForm.setTreeFilename("        ");
				}
				md.setModelParmII(-1, TCGMConstants.DT_INTER_COMP_TRSFR, myForm.getTreeFilename()+ TCGMConstants.DT_HYPHEN_DELIMITER + myForm.getInterCoTransferPeriod() + TCGMConstants.DT_DELIMITER);
				
				//Setting the new Param Actual Units
				job.addJobParm(JobConstants.PN_RPT_ACT_UNITS, myForm.getActualUnitsSelected());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_RPT_ACT_UNITS, myForm.getActualUnitsSelected());
			}
			else if(job.getJobDef().equals(JobDefinition.ASR_DELETE)
			||job.getJobDef().equals(JobDefinition.ASR_DLT_PART)){
				job.addJobParm(JobConstants.PN_ACTUAL_USER, ut.getUserid() );
			}
			pm.addJob(job, ut);
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName()) ); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
	}


	public void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		MiscReportsForm mrf = new MiscReportsForm();
		int modelId = this.getState(request).getCurrentModelId();
		ModelMngr mm =  new ModelMngr();
		mrf.reset();
		mrf.setActualUnitsSelected(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_RPT_ACT_UNITS));
		mrf.setInterCoTransferPeriod(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, "INP_PERIOD"));
		mrf.setSelRoutingStartPeriod(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_PERIOD_START));
		mrf.setSelRoutingStartYear(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_START));
		mrf.setSelRoutingEndPeriod(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_PERIOD_END));
		mrf.setSelRoutingEndYear(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_END));
		mrf.setBegRouteUnitsSelected(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_ACT_UNITS_BEG));
		mrf.setEndRouteUnitsSelected(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_ACT_UNITS_END));
		mrf.setSelComparisonModel(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_OLD_VERSION));
		mrf.setFactorModels(mm.getModels(ut, new FactorModel() ) );
		mrf.setUnitSets( dm.getDatasetByTableName(ut,DBConst.TABLE_UNIT_DATA) );

		request.getSession(false).setAttribute("miscReportsForm", mrf );
	}

	public MiscReports()
	{
		super();
	}

}