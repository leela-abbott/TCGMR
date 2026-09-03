/*
 * Created on Jun 18, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.asr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.TCGMAction;
import abbott.ai.tcgm.action.form.AsrUsageForm;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrUsageMngr;
import abbott.ai.tcgm.helpers.ModelMngr;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AsrUsageCopy extends TCGMAction{
	
	public AsrUsageCopy()
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

			HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

			this.errors.clear();

			if(form == null)
			{
				//errors is an ActionErrors object defined in TCGMAction
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.asrusage.form.missing"));
				this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
			}
			else if(this.isSessionValid(request))
			{
				UserToken userToken = this.getUserToken(request);
				boolean flag=false;
				
					AsrUsageForm asrTranForm = (AsrUsageForm)form;//cast the form that was passed in to the correct type for this action

					asrTranForm.processCmd(mapping,request);

					AsrUsageMngr asrMngr = new AsrUsageMngr(); //create the helper class that will handle the processing
					ModelMngr modelMngr = new ModelMngr();

					try
					{
						//asrTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						
						
						if(asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_ALL) )
						{
							String aff="";
							String cycleId="";
							if (asrTranForm.getSupAffSelected()== null || asrTranForm.getSupAffSelected().equals("-1"))
							{
								aff="ALL";
								cycleId="ALL";
							}else{
								cycleId=asrTranForm.getSupAffSelected().substring(0,asrTranForm.getSupAffSelected().indexOf("::"));
								aff=asrTranForm.getSupAffSelected().substring(asrTranForm.getSupAffSelected().indexOf("::")+2,asrTranForm.getSupAffSelected().lastIndexOf("::"));
							}
							flag=asrMngr.copyAllAsrTran(asrTranForm.getAffASRList(),userToken,asrTranForm.getSearchObject(),asrTranForm.getModelSelected(),asrTranForm.getSelUpdateType(),aff,userToken.getUserid(),cycleId);
						}
						else if(asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
						{
							flag=asrMngr.copySelectedAsrTran(userToken,asrTranForm.getAffASRList(),asrTranForm.getModelSelected(),asrTranForm.getSelUpdateType(),userToken.getUserid());
						}
						else if(asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE) )
						{
							flag=asrMngr.saveSelectedAsrTran(userToken,asrTranForm.getAffASRList(),asrTranForm.getModelSelected(),asrTranForm.getSelUpdateType());
						}
						else if(! asrTranForm.getCmd().trim().equals(""))
						{
							throw new TCGMException(className,methodName,"Invalid command in action");
						}
						if(asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_ALL) || asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)){
							if(flag){
								errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.asrusage.copied"));
							}
							else{
								errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.asrusage.copy"));
							}
						}
						if(asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE)){
							if(flag){
								errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.asrusage.update"));
							}
							else{
								errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.asrusage.update"));
							}
						}
						
						if (asrTranForm.getSupAffSelected()== null || asrTranForm.getSupAffSelected().equals("-1"))
						{
							asrTranForm.setSupAffSelected("-1");
						}else{
						asrTranForm.getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(this.getUserToken(request),asrTranForm.getSearchObject()));
						asrTranForm.setAffASRList(asrMngr.getAsr(this.getUserToken(request),asrTranForm.getSearchObject(),asrTranForm.getPagingFilter(),asrTranForm.getSortObject()));
						
						asrTranForm.setSupAffList(asrMngr.getSupAff(this.getUserToken(request)));						
						asrTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));	
						}							
						
						asrTranForm.setModelSelected(TCGMConstants.NONE);
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
					catch(TCGMDuplicateItemException ex)
					{

						this.logger.error(ex.toString(),ex);
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
						try
						{
							//asrTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
							asrTranForm.getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(this.getUserToken(request),asrTranForm.getSearchObject()));
						    asrTranForm.setAffASRList(asrMngr.getAsr(this.getUserToken(request),asrTranForm.getSearchObject(),asrTranForm.getPagingFilter(),asrTranForm.getSortObject()));						    
							asrTranForm.setSupAffList(asrMngr.getSupAff(this.getUserToken(request)));						
							asrTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));							
							this.setForward(TCGMConstants.FORWARD_SUCCESS);
						}
						catch(TCGMException exc)
						{
							this.logger.error(exc.toString(),exc);
							request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exc);
							this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
						}
					}

					catch(TCGMException ex)
					{
						this.logger.error(ex.toString(),ex);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
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
