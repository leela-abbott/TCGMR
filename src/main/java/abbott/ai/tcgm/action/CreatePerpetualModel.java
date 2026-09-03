package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class CreatePerpetualModel extends TCGMAction
{
int first = 0;
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
		CreatePerpetualModelForm myForm = null;

        try
        {
            if( this.isSessionValid(request))
            {
                myForm = (CreatePerpetualModelForm) form;
                // 9-15-05 I think this is preventing me from seeing the errors
                //errors.clear();
                
                if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) )  
                {
                    // process specific command
					ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(
							SQLUtil.getOracleAdmin(), myForm.getModelType());
//While creating model, if it is already deleted or purged should allow the same name to create
				/*	if (md.exists(myForm.getModelName())) 
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.model.create.duplicate"));
						this.saveErrors(request, errors);
						setupForm(request);
						this.setForward(TCGMConstants.FORWARD_INPUT);
					} else 
					{          */        
						myForm.setErrMessage(""); 
						errors.clear();
	                    processCmd(myForm, request);
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.model.create", myForm.getModelName()));
	                    myForm.reset(); 
						first++;
					//}
                }

                else 
                { 
                    setupForm(request);
					if(first==0 && (myForm!=null))                  
                    {
						 myForm.setErrMessage(""); 
                    }					   
                    first=0;
					errors.clear();
                    this.setForward(TCGMConstants.FORWARD_INPUT);
                }
            }
        }
		catch(TCGMDuplicateItemException exdup)
		{
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.create.duplicate", myForm.getModelName()));
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exdup);
			this.setForward(TCGMConstants.FORWARD_FAILURE);				
		}        
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		//if errors exist then save them into the request
		if(! this.errors.empty())
		{
			this.saveErrors(request,errors);
		} 		
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}

    private void processCmd(CreatePerpetualModelForm myForm, HttpServletRequest request) throws TCGMException,
																								TCGMDuplicateItemException 
	{
        UserToken ut = this.getUserToken(request);
		//should correspond to a direct command
        String cmd = myForm.getCmd(); 

        if (cmd.equals("CREATE_MODEL")) 
        {
        	try
        	{
            	ModelMngr mm = new ModelMngr();           
				mm.createModel(this.getUserToken(request), myForm.getModelFromForm() );
	
				// A.Winter 6/22/05 - in case on error of creating perpetual model,
				// sent error message and redirect to current page
	
				// this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.year"));  
				if(!myForm.getModels().isEmpty())
				{
				   myForm.setErrMessage("No current year actual model exists"); 
				   this.setForward(TCGMConstants.FORWARD_FAILURE);  
				}
            	else
            	{  
					myForm.setErrMessage("");
					this.setForward(TCGMConstants.FORWARD_SUCCESS); 
           	  	}
				//A.Winter 6/22/05 - end
        	}       	
//       	catch(TCGMDuplicateItemException exdup)
//        	{
//				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.create.duplicate"));
//				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exdup);
//				this.setForward(TCGMConstants.FORWARD_FAILURE);					
//        	}     
			catch(TCGMDuplicateItemException exdup)
			{
				throw new TCGMDuplicateItemException("OracleModelDao", "createModel", "Dup Model Names"  ); 				
			}   
        }
       
   }
   private void setupForm(HttpServletRequest request) throws TCGMException {
       UserToken ut = this.getUserToken(request);
       ModelMngr mm = new ModelMngr();
       DatasetMngr dm = new DatasetMngr();
       CreatePerpetualModelForm myForm = new CreatePerpetualModelForm();
	   int modelId = 0;
       
       //Sridevi.K 7/1/05: Code added for only displaying the open models.
       FactorModel fm = new FactorModel();
       //Changed the status to OPEN  from OPENCLOSED
       fm.setStatus(TCGMModel.Status.OPEN);
       myForm.setFactorModels( mm.getModels(ut, fm ) );
       //Sridevi.7/1/05: End of code for displaying only open models.
  	   modelId = mm.getLastModelCreatedByUser(ut, TCGMModel.Type.PERPETUAL);
        
		if (modelId > 0)
		{
			 PerpetualModel perpmodel = new PerpetualModel();
			 perpmodel =  (PerpetualModel)mm.getModelFromId(ut,modelId,TCGMModel.Type.PERPETUAL);
		
			 myForm.setModelName(mm.getModelName(ut,perpmodel.getModelIdInt()));
			 myForm.setModelDesc(mm.getModelFromId(ut,modelId,TCGMModel.Type.PERPETUAL).getDesc());
			 
			 if (!(perpmodel.getStartingModel() == null))
			 {
				myForm.setSelStartingModel(perpmodel.getStartingModel().getModelId());
			 }
			 if (!(perpmodel.getStartingInvUnits() == null))
			 {
				myForm.setSelStartingModelUnits(perpmodel.getStartingInvUnits().getDatasetName());
			 }
			 if (!(perpmodel.getCostingModel() == null))
			 {
				myForm.setSelCostingModel(perpmodel.getCostingModel().getModelId());
			 }
			 
			 if (!(perpmodel.getCurrentYearActualModel() == null))
			 {
				myForm.setSelCurrentYearActualModel(perpmodel.getCurrentYearActualModel().getModelId());
			 }
			 if (!(perpmodel.getCurrentYearActualUnits() == null))
			 {
				myForm.setSelCurrentYearActualUnits(perpmodel.getCurrentYearActualUnits().getDatasetName());
			 }
			 myForm.setSelStartPeriod(perpmodel.getStartPeriod());
			 myForm.setSelStartYear(perpmodel.getStartYear());
			 
			 if (!(perpmodel.getLastYearActualModel() == null))
			 {
				myForm.setSelLastYearActualModel(perpmodel.getLastYearActualModel().getModelId());
			 }
			 if (!(perpmodel.getLastYearActualUnits() == null))
			 {
				myForm.setSelLastYearActualUnits(perpmodel.getLastYearActualUnits().getDatasetName());
			 }
			 
			 myForm.setSelEndPeriod(perpmodel.getEndPeriod());
			 myForm.setSelEndYear(perpmodel.getEndYear());
			 
			 if (!(perpmodel.getEndingModel() == null))
			 {
				myForm.setSelEndingModel(perpmodel.getEndingModel().getModelId());
			 }
			 if (!(perpmodel.getEndingInvUnits() == null))
			 {
				myForm.setSelEndingModelUnits(perpmodel.getEndingInvUnits().getDatasetName());
			 }
			 myForm.setMemo(perpmodel.getMemo());
			 
		}
       
       myForm.setUnits( dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA) );
       request.setAttribute("createPerpetualModelForm", myForm );
    }
	public CreatePerpetualModel() {
		super();
	}

}