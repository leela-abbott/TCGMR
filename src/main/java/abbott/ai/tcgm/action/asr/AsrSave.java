package abbott.ai.tcgm.action.asr;

import org.apache.log4j.Logger;
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
public class AsrSave extends TCGMAction
{
	private static Logger myLogger = Logger.getLogger( "AsrSave" );
	/**
	 * Default Constructor
	 */
	public AsrSave()
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
		myLogger.debug("Executing perform() method in AsrSave.");

		String methodName = "perform";

		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.asr.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				UserToken userToken = this.getUserToken(request);

				AsrForm asrForm = (AsrForm)form;//cast the form that was passed in to the correct type for this action

				asrForm.processCmd(mapping,request);
				boolean duplicate = true;
				AsrMngr asrMngr = new AsrMngr(); //create the helper class that will handle the processing
				try
				{
					if(asrForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						duplicate = asrMngr.addNewAsrTran(userToken,asrForm.getAddNew());
					}
					else if(asrForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						duplicate = asrMngr.addSelectedAsrToTrans(userToken,asrForm.getAsrList(),TCGMConstants.ACT_CD_CHG);
					}
					else if(asrForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						asrForm.setAsrErrorList(asrMngr.massUpdate(userToken,asrForm.getSearchObject(),asrForm.getAddNew()));
						//duplicate = asrMngr.massUpdate(userToken,asrForm.getSearchObject(),asrForm.getAddNew());
						/*if(asrForm.getAsrListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.asrtran.insert"));
						}*/
					}
					else if(! asrForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + asrForm.getCmd());
					}
					if(duplicate&&asrForm.getAsrErrorList().size()==0||duplicate&&asrForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					
					{
						if(asrForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& asrForm.getAsrErrorList().size()>0) 
						{   
							Vector vct = asrForm.getAsrErrorList();
							int intVctSize = vct.size();
							Vector vctTemp = new Vector();
							for(int i=0;i<intVctSize&&vct.elementAt(i)!=null;i++){
								if(!((Asr)vct.elementAt(i)).getMsg().equalsIgnoreCase("")){
									vctTemp.add((Asr)vct.elementAt(i));
									}
						}
						asrForm.setAsrErrorList(vctTemp);
						asrForm.setAsrList(asrForm.getAsrErrorList());
						}
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.asrtran.insert"));
					}
					else 
					{   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate.looping"));
					}

					// 5-25-05 Temporarily commented out success msg to see where we should place it
					//         In this location, the message is printing everytime, even without
					//         transactions being created.
					//	errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.asrtran.insert"));

					asrForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					//During mass update, if you get errors, you can save by save selected option.
					//During this we need to refresh the page with remaining error records, for this to set up 
					//Paging filter properties getting the records form errorlist object.
					if(asrForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& asrForm.getAsrErrorList().size()>0){
						asrForm.getPagingFilter().setTotalRecordsInSet(asrForm.getAsrErrorList().size());
					}
					else{
						asrForm.getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(userToken,asrForm.getSearchObject()));	
					}
					//if the insert is successfull loading the fresh records to show
					 if(duplicate&&asrForm.getAsrErrorList().size()==0){
						/* Checking for the value of the CMD, if it is SAVE, means its a request from 
						 * Save record and loading the initial load page with empty rows and last entered
						 * values in the add action.
						 */
						if(asrForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE)&&
						   ((asrForm.getAsrList().size()==0) || (asrForm.getAsrListItem(0).getProductOrigin().equalsIgnoreCase("")))){
							asrForm.setAsrList(createEmptyAsrRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
						}else{
							asrForm.setAsrList(asrMngr.getAsr(userToken,asrForm.getSearchObject(),asrForm.getPagingFilter(),asrForm.getSortObject()));
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
						asrForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						asrForm.getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(userToken,asrForm.getSearchObject()));
						asrForm.setAsrList(asrMngr.getAsr(userToken,asrForm.getSearchObject(),asrForm.getPagingFilter(),asrForm.getSortObject()));
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