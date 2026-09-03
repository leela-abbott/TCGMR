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
import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class CreateCostExchModel extends TCGMAction
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
			if(this.isSessionValid(request))
			{
                CreateCostExchModelForm myForm = (CreateCostExchModelForm) form;
                errors.clear();
                if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) )  {
                    // process specific command

					ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(
							SQLUtil.getOracleAdmin(), myForm.getModelType());

					// While creating model, if it is already deleted or purged should allow the same name to create							

					/*if (md.exists(myForm.getModelName())) 
					{

						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.model.create.duplicate"));
						this.saveErrors(request, errors);
						setupForm(request);
						this.setForward(TCGMConstants.FORWARD_INPUT);
					} else 
					{ */
						processCmd(myForm, request);
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.model.create", myForm.getModelName()));
						myForm.reset();
					//}                    
                }

                else { // gather input
                    setupForm(request);
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


    private void setupForm(HttpServletRequest request) throws TCGMException {

        UserToken ut = this.getUserToken(request);
        ModelMngr mm = new ModelMngr();
        DatasetMngr dm = new DatasetMngr();
        int modelId = 0;

        CreateCostExchModelForm ccem = new CreateCostExchModelForm();

        ccem.setUnitSets(dm.getDatasetByTableName(ut,DBConst.TABLE_UNIT_DATA));
        ccem.setRateSets( dm.getDatasetByTableName(ut,DBConst.TABLE_RATE_DATA));
        ccem.setSalesData( dm.getDatasetByTableName(ut,DBConst.TABLE_COSTEXCH_DATA));
        //02/01/2006. Added by Udaya B Aravapalli to display Factor Models.
		FactorModel fm = new FactorModel();
//		Changed the status to OPEN  from OPENCLOSED
		fm.setStatus(TCGMModel.Status.OPEN);
		ccem.setModels(mm.getModels(ut, fm));
		ccem.setModelSelected(TCGMConstants.NONE);
        
        modelId = mm.getLastModelCreatedByUser(ut, TCGMModel.Type.COSTEXCH);
        
        if (modelId > 0)
        {
	  		 CostExchModel cem = new CostExchModel();
			 cem =  (CostExchModel)mm.getModelFromId(ut,modelId,TCGMModel.Type.COSTEXCH);
	
			 ccem.setModelName(mm.getModelName(ut,cem.getModelIdInt()));
			 ccem.setModelDesc(mm.getModelFromId(ut,modelId,TCGMModel.Type.COSTEXCH).getDesc());
	
			 if (!(cem.getRateSet() == null))
			 {
				ccem.setSelRateSet(cem.getRateSet().getDatasetTableId());
			 }
			 
			 if (!(cem.getStartingSalesData() == null))
			 {
				ccem.setSelStartingSalesData(cem.getStartingSalesData().getDatasetTableId());
			 }
			 if (!(cem.getEndingSalesData() == null))
			 {
				ccem.setSelEndingSalesData(cem.getEndingSalesData().getDatasetTableId());
			 }
		
			 ccem.setSelStartPeriod(cem.getStartPeriod());
			 ccem.setSelStartYear(cem.getStartYear());
			 ccem.setSelEndPeriod(cem.getEndPeriod());
			 ccem.setSelEndYear(cem.getEndYear());
		
			 ccem.setModelSelected(cem.getModelIdSelected());
		
			 ccem.setMemo(cem.getMemo());
			 if (!(cem.getCostExchUnits() == null))
			 {
				ccem.setSelCostExchUnits(cem.getCostExchUnits().getDatasetTableId());
			 }
        }
         request.setAttribute("createCostExchModelForm", ccem );
    }

    private void processCmd(CreateCostExchModelForm myForm, HttpServletRequest request) throws TCGMException {
        UserToken ut = this.getUserToken(request);
        String cmd = myForm.getCmd(); // should correspond to a direct command

        if (cmd.equals("CREATE_MODEL")) 
        {
            ModelMngr mm = new ModelMngr();
			FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(myForm.getModelSelected()), TCGMModel.Type.FACTOR);
			myForm.setFactorModelName(mm.getModelName(ut, Integer.parseInt(myForm.getModelSelected())));
            mm.createModel(this.getUserToken(request), myForm.getModelFromForm() );
            this.setForward(TCGMConstants.FORWARD_SUCCESS);
        }
   }

	/**
	 * Default Constructor
	 */
	public CreateCostExchModel()
	{
		super();
	}

}