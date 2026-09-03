package abbott.ai.tcgm.action.bpcex;

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
public class BpcExSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcExSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcex.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				UserToken userToken = this.getUserToken(request);

				BpcExForm bpcExForm = (BpcExForm)form;//cast the form that was passed in to the correct type for this action

				bpcExForm.processCmd(mapping,request);
				boolean duplicate = true;
				BpcExMngr bpcExMngr = new BpcExMngr(); //create the helper class that will handle the processing
				try
				{
					if(bpcExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						duplicate = bpcExMngr.addNewBpcExTran(userToken,bpcExForm.getAddNew());
//						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.insert"));
					}
					else if(bpcExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						duplicate = bpcExMngr.addSelectedBpcExToTrans(userToken,bpcExForm.getBpcExList(),TCGMConstants.ACT_CD_CHG);
//						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.insert"));
					}
					else if(bpcExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						bpcExForm.setBpcExErrorList(bpcExMngr.massUpdate(userToken,bpcExForm.getSearchObject(),bpcExForm.getAddNew()));
						/*bpcExMngr.massUpdate(userToken,bpcExForm.getSearchObject(),bpcExForm.getAddNew());
						if(bpcExForm.getBpcExListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.insert"));
						}	*/					
					}
					else if(! bpcExForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + bpcExForm.getCmd());
					}
					if(duplicate&&bpcExForm.getBpcExErrorList().size()==0||duplicate&&bpcExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						if(bpcExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& bpcExForm.getBpcExErrorList().size()>0){
						Vector vct = bpcExForm.getBpcExErrorList();
						int intVctSize = vct.size();
						Vector vctTemp = new Vector();
						for(int i=0;i<intVctSize&&vct.elementAt(i)!=null;i++){
							if(!((BpcEx)vct.elementAt(i)).getMsg().equalsIgnoreCase("")){
								vctTemp.add((BpcEx)vct.elementAt(i));
							}
						}
						bpcExForm.setBpcExErrorList(vctTemp);
						bpcExForm.setBpcExList(bpcExForm.getBpcExErrorList());
						}
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.insert"));
					}
					else
					{   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					}
					
					bpcExForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					//During mass update, if you get errors, you can save by save selected option.
					//During this we need to refresh the page with remaining error records, for this to set up 
					//Paging filter properties getting the records form errorlist object.
					if(bpcExForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& bpcExForm.getBpcExErrorList().size()>0){
						bpcExForm.getPagingFilter().setTotalRecordsInSet(bpcExForm.getBpcExErrorList().size());
					}
					else{
						bpcExForm.getPagingFilter().setTotalRecordsInSet(bpcExMngr.getCount(userToken,bpcExForm.getSearchObject()));	
					}
					//if the insert is successfull loading the fresh records to show
					if(duplicate&&bpcExForm.getBpcExErrorList().size()==0){
						if(bpcExForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE)&&
						   ((bpcExForm.getBpcExList().size()==0) || (bpcExForm.getBpcExList(0).getSupAff().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_EMPTY))))
						{
							bpcExForm.setBpcExList(createEmptyBpcExRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
						}
						else
						{
							bpcExForm.setBpcExList(bpcExMngr.getBpcEx(userToken,bpcExForm.getSearchObject(),bpcExForm.getPagingFilter(),bpcExForm.getSortObject()));
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
						bpcExForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						bpcExForm.getPagingFilter().setTotalRecordsInSet(bpcExMngr.getCount(userToken,bpcExForm.getSearchObject()));
						//if the insert is successfull loading the fresh records to show
						if(duplicate){
						bpcExForm.setBpcExList(bpcExMngr.getBpcEx(userToken,bpcExForm.getSearchObject(),bpcExForm.getPagingFilter(),bpcExForm.getSortObject()));
						}
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