package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;
import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.entities.AnalysisModel;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class CreateAnalysisModel extends TCGMAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		try {
			if (this.isSessionValid(request)) {
				CreateAnalysisModelForm myForm = (CreateAnalysisModelForm) form;
				errors.clear();
				if (myForm != null && !TCGMUtil.isEmpty(myForm.getCmd())) {

					ModelDao md =
						DaoFactory.getDaoFactory(
							DaoFactory.ORACLE).getModelDao(
							SQLUtil.getOracleAdmin(),
							myForm.getModelType());
// While creating model, if it is already deleted or purged should allow the same name to create							

					/* if (md.exists(myForm.getModelName())) {

						errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError("error.model.create.duplicate"));
							
						this.saveErrors(request, errors);

						setupForm(request);
						this.setForward(TCGMConstants.FORWARD_INPUT);
					} else { */
						processCmd(myForm, request);
						this.errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(
								"success.model.create",
								myForm.getModelName()));
						myForm.reset();
					/* } */
					// process specific command

				} else { // gather input
					setupForm(request);
					this.setForward(TCGMConstants.FORWARD_INPUT);
				}
			}
		} catch (NumberFormatException ex) {
			
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.model.create.year_format"));
			this.saveErrors(request, errors);
			try {

				setupForm(request);
			} catch (TCGMException tex) {
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tex);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
			
			this.setForward(TCGMConstants.FORWARD_INPUT);
			
		} catch (TCGMException ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}

		this.logger.debug(
			this.className + " - Forward to " + this.getForward());

		return mapping.findForward(this.getForward());
	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		ModelMngr mm = new ModelMngr();
		DatasetMngr dm = new DatasetMngr();
		int modelId = 0;
		CreateAnalysisModelForm aff = new CreateAnalysisModelForm();

		//Sridevi.K 7/1/05: Code added for only displaying the open models.
		FactorModel fm = new FactorModel();
//		Changed the status to OPEN  from OPENCLOSED
		fm.setStatus(TCGMModel.Status.OPEN);
		aff.setFactorModels(mm.getModels(ut, fm));
		//aff.setFactorModels( mm.getModels(ut, new FactorModel() ) );
		//Sridevi.7/1/05: End of code for displaying only open models.

		modelId = mm.getLastModelCreatedByUser(ut, TCGMModel.Type.ANALYSIS);
        
		 if (modelId > 0)
		 {
			  AnalysisModel anlmodel = new AnalysisModel();
			  anlmodel =  (AnalysisModel)mm.getModelFromId(ut,modelId,TCGMModel.Type.ANALYSIS);
		
			  aff.setModelName(mm.getModelName(ut,anlmodel.getModelIdInt()));
			  aff.setModelDesc(mm.getModelFromId(ut,modelId,TCGMModel.Type.ANALYSIS).getDesc());
			  
			  if (!(anlmodel.getBaseModel() == null))
			  {
				aff.setBaseModelSelected(anlmodel.getBaseModel().getModelId());
			  }
			  aff.setBaseModelPeriod(anlmodel.getBaseModelPeriod());
			  
			  if (!(anlmodel.getAnalysisModel() == null))
			  {
				aff.setAnalysisModelSelected(anlmodel.getAnalysisModel().getModelId());
			  }
			  aff.setAnalysisModelPeriod(anlmodel.getAnalysisModelPeriod());
			  
			  if (!(anlmodel.getAnalysisUnits() == null))
			  {
				aff.setAnalysisUnitsSelected(anlmodel.getAnalysisUnits().getDatasetTableId());
			  }
			  aff.setAnalysisUnitsPeriod(anlmodel.getAnalysisUnitsPeriod());
			  
			  if (!(anlmodel.getVolumeUnits() == null))
			  {
				aff.setVolumeUnitsSelected(anlmodel.getVolumeUnits().getDatasetTableId());
			  }
			  aff.setVolumeUnitsPeriod(anlmodel.getVolumeUnitsPeriod());
			  
			  aff.setMemo(anlmodel.getMemo());
		 }
		

		aff.setUnitSets(dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA));
		request.setAttribute("createAnalysisModelForm", aff);
	}

	private void processCmd(
		CreateAnalysisModelForm myForm,
		HttpServletRequest request)
		throws TCGMException, NumberFormatException {
		UserToken ut = this.getUserToken(request);
		//should correspond to a direct command
		String cmd = myForm.getCmd();

		if (cmd.equals("CREATE_MODEL")) {
			ModelMngr mm = new ModelMngr();
			AnalysisModel am = (AnalysisModel) myForm.getModelFromForm();

			//mm.createModel(this.getUserToken(request), myForm.getModelFromForm() );
			mm.createModel(this.getUserToken(request), am);
			// 9-24-03 Save attributes for model to PARAMETER tbl in database
			mm.updateModelParms(ut, am);

			this.setForward(TCGMConstants.FORWARD_SUCCESS);
		}
	}

	public CreateAnalysisModel() {
		super();
	}

}