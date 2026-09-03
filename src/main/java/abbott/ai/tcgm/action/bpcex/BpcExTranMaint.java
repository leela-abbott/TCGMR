package abbott.ai.tcgm.action.bpcex;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcExTranMaint extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcExTranMaint()
	{
		super();
	}
	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		String methodName = "perform";

        //get existing session or create a new one if it doesn't exist
		HttpSession session = request.getSession();

		this.errors.clear();
		/*
		 * Checking for the request coming from Delete Records (AsrTranDelere) 
		 * with success message, if so setting the message to erros in request
		 * scope. Immediately removing from session object.
		 */
		String strDeleteMessage =(String)session.getAttribute("deleteMessage");
		if(strDeleteMessage!=null&&!strDeleteMessage.trim().equalsIgnoreCase("")){
		errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.delete"));
		session.removeAttribute("deleteMessage");			
		}
		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcextran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isModelSelected(request))
			{
				//cast the form that was passed in to the correct type for this action
				BpcExTranForm bpXTrnFrm = (BpcExTranForm)form;
				bpXTrnFrm.processCmd(mapping,request);
				
				if (bpXTrnFrm!=null && bpXTrnFrm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_ADV_FILTER) )  {
						request.getSession().setAttribute("bpcExTranAdvFilter", bpXTrnFrm);
					    bpXTrnFrm.reset(mapping,request);
						this.setForward(TCGMConstants.FORWARD_ADVANCEDFILTER);
				}else{

                //create the helper class that will handle the processing
				BpcExMngr bpcExMngr = new BpcExMngr(); 
				ModelMngr modelMngr = new ModelMngr();
				try
				{
					bpXTrnFrm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					// Username must be set first before using helper methods
					/*************************************************************
					*	Added by Uday on 02/04/2006 to provide the user(Analyst)
					*   the option to view the maintenance records of any user. Start
					**************************************************************/
					if (bpXTrnFrm.getUserSelected() == null)
					{
						if(bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().getUserName().equals(""))
						{
							bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(userToken.getUserid());
							bpXTrnFrm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(bpXTrnFrm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(bpXTrnFrm.getUserSelected());
					}
					/*************************************************************
					*	Added by Uday on 02/04/2006 to provide the user(Analyst)
					*   the option to view the maintenance records of any user. End
					**************************************************************/
					bpXTrnFrm.getAddNew().getBpcEx().setModelIdInt(this.getState(request).getCurrentModelId());
					bpXTrnFrm.getAddNew().getBpcEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					bpXTrnFrm.getPagingFilter().setTotalRecordsInSet(bpcExMngr.getCount(userToken,bpXTrnFrm.getSearchObject()));

					// 3/31/03 Default 1 & 12 for the begin & end periods on the maint transaction page
					bpXTrnFrm.getAddNew().getBpcEx().setBegPeriod("1");
					bpXTrnFrm.getAddNew().getBpcEx().setEndPeriod("12");

					bpXTrnFrm.setBpcExTranList(bpcExMngr.getBpcExTran(userToken,bpXTrnFrm.getSearchObject(),bpXTrnFrm.getPagingFilter(),bpXTrnFrm.getSortObject()));
					
					//06/29/05 Modified by Sridevi.New code for a fix for displaying only the open models
					//Setup emtpy factor model as search object to return all open factor models
					FactorModel fm = new FactorModel();
					fm.setStatus(TCGMModel.Status.OPEN);					
					bpXTrnFrm.setModels(modelMngr.getModels(userToken, fm));
                    //bpXTrnFrm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					//06/29/05 End of new code for the fix for displaying only the open models					
					bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
					bpXTrnFrm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMException ex)
				{
					this.logger.error(ex.toString(),ex);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			  }
			}
		}
		//if errors exist then save them into the request
		if(! this.errors.empty())
		{
			this.saveErrors(request,errors);
		}

		//forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}
}