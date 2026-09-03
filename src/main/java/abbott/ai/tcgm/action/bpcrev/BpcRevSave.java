package abbott.ai.tcgm.action.bpcrev;

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
public class BpcRevSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcRevSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcrev.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				UserToken userToken = this.getUserToken(request);

				BpcRevForm bpcRevForm = (BpcRevForm)form;//cast the form that was passed in to the correct type for this action

				bpcRevForm.processCmd(mapping,request);
				boolean duplicate = true;
				BpcRevMngr bpcRevMngr = new BpcRevMngr(); //create the helper class that will handle the processing
				try
				{
					if(bpcRevForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						duplicate = bpcRevMngr.addNewBpcRevTran(userToken,bpcRevForm.getAddNew());
						//errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcrevtran.insert"));
					}
					else if(bpcRevForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						duplicate = bpcRevMngr.addSelectedBpcRevToTrans(userToken,bpcRevForm.getBpcRevList(),TCGMConstants.ACT_CD_CHG);
						//errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcrevtran.insert"));
					}
					else if(bpcRevForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						bpcRevForm.setBpcRevErrorList(bpcRevMngr.massUpdate(userToken,bpcRevForm.getSearchObject(),bpcRevForm.getAddNew()));
						//bpcRevMngr.massUpdate(userToken,bpcRevForm.getSearchObject(),bpcRevForm.getAddNew());
						/*if(bpcRevForm.getBpcRevListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcrevtran.insert"));
						}*/
					}
					else if(! bpcRevForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + bpcRevForm.getCmd());
					}				
					
					if(duplicate&&bpcRevForm.getBpcRevErrorList().size()==0||duplicate&&bpcRevForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						if(bpcRevForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& bpcRevForm.getBpcRevErrorList().size()>0){
								Vector vct = bpcRevForm.getBpcRevErrorList();
								int intVctSize = vct.size();
								Vector vctTemp = new Vector();
								for(int i=0;i<intVctSize&&vct.elementAt(i)!=null;i++){
									if(!((BpcRev)vct.elementAt(i)).getMsg().equalsIgnoreCase("")){
										vctTemp.add((BpcRev)vct.elementAt(i));
									}
								}
						bpcRevForm.setBpcRevErrorList(vctTemp);
						bpcRevForm.setBpcRevList(bpcRevForm.getBpcRevErrorList());
						}
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcrevtran.insert"));
					}
					else
					{   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					}

					bpcRevForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					//During mass update, if you get errors, you can save by save selected option.
					//During this we need to refresh the page with remaining error records, for this to set up 
					//Paging filter properties getting the records form errorlist object.
					if(bpcRevForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& bpcRevForm.getBpcRevErrorList().size()>0){
						bpcRevForm.getPagingFilter().setTotalRecordsInSet(bpcRevForm.getBpcRevErrorList().size());
					}
					else{
						bpcRevForm.getPagingFilter().setTotalRecordsInSet(bpcRevMngr.getCount(userToken,bpcRevForm.getSearchObject()));
					}
					//if the insert is successfull loading the fresh records to show
					if(duplicate&&bpcRevForm.getBpcRevErrorList().size()==0){

						if(bpcRevForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE)&&
						   ((bpcRevForm.getBpcRevList().size()==0) || (bpcRevForm.getBpcRevList(0).getRptAff().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_EMPTY))))
						{
							bpcRevForm.setBpcRevList(createEmptyBpcRevRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
						}
						else
						{
							bpcRevForm.setBpcRevList(bpcRevMngr.getBpcRev(userToken,bpcRevForm.getSearchObject(),bpcRevForm.getPagingFilter(),bpcRevForm.getSortObject()));
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
						bpcRevForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						bpcRevForm.getPagingFilter().setTotalRecordsInSet(bpcRevMngr.getCount(userToken,bpcRevForm.getSearchObject()));
						bpcRevForm.setBpcRevList(bpcRevMngr.getBpcRev(userToken,bpcRevForm.getSearchObject(),bpcRevForm.getPagingFilter(),bpcRevForm.getSortObject()));
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