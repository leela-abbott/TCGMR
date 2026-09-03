package abbott.ai.tcgm.action.notes;

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
public class NotesTranSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public NotesTranSave()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.notestran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isModelSelected(request))
			{
				NotesTranForm notesTranForm = (NotesTranForm)form;//cast the form that was passed in to the correct type for this action

				notesTranForm.processCmd(mapping,request);

				NotesMngr notesMngr = new NotesMngr(); //create the helper class that will handle the processing
				ModelMngr modelMngr = new ModelMngr();

				try
				{
					notesTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Publish the maintenance records of any user. Start
					**************************************************************/
					if (notesTranForm.getUserSelected() == null)
					{
						if(notesTranForm.getSearchObject().getNotes().getCreateLog().getUserName().equals(""))
						{
							notesTranForm.getSearchObject().getNotes().getCreateLog().setUserName(userToken.getUserid());
							notesTranForm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(notesTranForm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						notesTranForm.getSearchObject().getNotes().getCreateLog().setUserName(notesTranForm.getUserSelected());
					}
					if (notesTranForm.getUserSelected().equalsIgnoreCase("ALL"))
					{	
						notesTranForm.getSearchObject().getNotes().getCreateLog().setUserName(null);
						notesTranForm.setUserSelected("ALL");
					}
					
					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Publish the maintenance records of any user. End
					**************************************************************/

					if(notesTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD) )
					{
						notesMngr.addNewNotesTran(userToken,notesTranForm.getAddNew());
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.saved"));
					}
					else if(notesTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
					{
						notesMngr.saveSelectedNotesTran(userToken,notesTranForm.getNotesTranList());
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.saved"));
					}
					else if(notesTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						notesMngr.massUpdate(userToken,notesTranForm.getSearchObject(),notesTranForm.getAddNew());
						if(notesTranForm.getNotesTranListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.saved"));
						}	
					}
					else if(! notesTranForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action" + notesTranForm.getCmd());
					}
					
					notesTranForm.getPagingFilter().setTotalRecordsInSet(notesMngr.getCount(userToken,notesTranForm.getSearchObject()));
					notesTranForm.setNotesTranList(notesMngr.getNotesTran(userToken,notesTranForm.getSearchObject(),notesTranForm.getPagingFilter(),notesTranForm.getSortObject()));
					notesTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					notesTranForm.getSearchObject().getNotes().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
					notesTranForm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMDuplicateItemException ex)
				{

					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					try
					{
						notesTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						notesTranForm.getPagingFilter().setTotalRecordsInSet(notesMngr.getCount(userToken,notesTranForm.getSearchObject()));
						notesTranForm.setNotesTranList(notesMngr.getNotesTran(userToken,notesTranForm.getSearchObject(),notesTranForm.getPagingFilter(),notesTranForm.getSortObject()));
						notesTranForm.getSearchObject().getNotes().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli						
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