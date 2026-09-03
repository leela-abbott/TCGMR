package abbott.ai.tcgm.action.bpc;

import org.apache.struts.action.*;
//import org.apache.log4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Vector;
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
public class BpcsSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcsSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcs.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				UserToken userToken = this.getUserToken(request);

				BpcsForm bpcsForm = (BpcsForm)form;//cast the form that was passed in to the correct type for this action

				bpcsForm.processCmd(mapping,request);
				boolean duplicate = true;
				BpcsMngr bpcsMngr = new BpcsMngr(); //create the helper class that will handle the processing
				try
				{
					if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						duplicate = bpcsMngr.addNewBpcsTran(userToken,bpcsForm.getAddNew());
//						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcsTran.insert"));
					}
					else if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						duplicate = bpcsMngr.addSelectedBpcsToTrans(userToken,bpcsForm.getBpcsList(),TCGMConstants.ACT_CD_CHG);
//						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcsTran.insert"));
					}
					else if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						bpcsForm.setBpcsErrorList(bpcsMngr.massUpdate(userToken,bpcsForm.getSearchObject(),bpcsForm.getAddNew()));
						//duplicate = bpcsMngr.massUpdate(userToken,bpcsForm.getSearchObject(),bpcsForm.getAddNew());
						/*if(bpcsForm.getBpcsListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcsTran.insert"));
						}*/
					}
					else if(! bpcsForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + bpcsForm.getCmd());
					}
					if(duplicate&&bpcsForm.getBpcsErrorList().size()==0||duplicate&&bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
						{
						if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& bpcsForm.getBpcsErrorList().size()>0){
								Vector vct = bpcsForm.getBpcsErrorList();
								int intVctSize = vct.size();
								Vector vctTemp = new Vector();
								for(int i=0;i<intVctSize&&vct.elementAt(i)!=null;i++){
									if(!((Bpcs)vct.elementAt(i)).getMsg().equalsIgnoreCase("")){
										vctTemp.add((Bpcs)vct.elementAt(i));
									}
								}
								bpcsForm.setBpcsErrorList(vctTemp);
								bpcsForm.setBpcsList(bpcsForm.getBpcsErrorList());
								bpcsForm.getPagingFilter().setTotalRecordsInSet(bpcsForm.getBpcsErrorList().size());	
						}
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcsTran.insert"));
					}
					else 
					{   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					}

					bpcsForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					//During mass update, if you get errors, you can save by save selected option.
					//During this we need to refresh the page with remaining error records, for this to set up 
					//Paging filter properties getting the records form errorlist object.
					if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& bpcsForm.getBpcsErrorList().size()>0){
						bpcsForm.getPagingFilter().setTotalRecordsInSet(bpcsForm.getBpcsErrorList().size());
					}
					else{
						bpcsForm.getPagingFilter().setTotalRecordsInSet(bpcsMngr.getCount(userToken,bpcsForm.getSearchObject()));	
					}
//					if the insert is successfull loading the fresh records to show
					if(duplicate&&bpcsForm.getBpcsErrorList().size()==0){
						/* Checking for the value of the CMD, if it is SAVE, means its a request from 
						 * Save record and loading the initial load page with empty rows and last entered
						 * values in the add action.
						 */
						if(bpcsForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE)&&
							((bpcsForm.getBpcsList().size()==0) || (bpcsForm.getBpcsListItem(0).getRptAff().equalsIgnoreCase("")))){
							bpcsForm.setBpcsList(createEmptyBpcRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
						}else{
							bpcsForm.setBpcsList(bpcsMngr.getBpcs(userToken,bpcsForm.getSearchObject(),bpcsForm.getPagingFilter(),bpcsForm.getSortObject()));
						}
					
					}
					this.setForward(TCGMConstants.FORWARD_SUCCESS);

				}
				catch(TCGMDuplicateItemException ex)
				{

					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					try
					{
						bpcsForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						bpcsForm.getPagingFilter().setTotalRecordsInSet(bpcsMngr.getCount(userToken,bpcsForm.getSearchObject()));
						bpcsForm.setBpcsList(bpcsMngr.getBpcs(userToken,bpcsForm.getSearchObject(),bpcsForm.getPagingFilter(),bpcsForm.getSortObject()));
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