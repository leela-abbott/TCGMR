package abbott.ai.tcgm.action.notes;

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
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class NotesSave extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public NotesSave()
	{
		super();
	}

	/**
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.notes.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				UserToken userToken = this.getUserToken(request);

				NotesForm notesForm = (NotesForm)form;//cast the form that was passed in to the correct type for this action

				notesForm.processCmd(mapping,request);
				boolean duplicate = true;
				NotesMngr notesMngr = new NotesMngr(); //create the helper class that will handle the processing
				try
				{
					if(notesForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
					{
						duplicate = notesMngr.addNewNotesTran(userToken,notesForm.getAddNew());
						//errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.insert"));
					}
					else if(notesForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						duplicate = notesMngr.addSelectedNotesToTrans(userToken,notesForm.getNotesList(),TCGMConstants.ACT_CD_CHG);
						//errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.insert"));
					}
					else if(notesForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
					{
						
						notesForm.setNotesErrorList(notesMngr.massUpdate(userToken,notesForm.getSearchObject(),notesForm.getAddNew()));
						/*notesMngr.massUpdate(userToken,notesForm.getSearchObject(),notesForm.getAddNew());
						if(notesForm.getNotesListSize() > 0)
						{
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.insert"));
						}*/
					}
					else if(! notesForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action: " + notesForm.getCmd());
					}
					if(duplicate&&notesForm.getNotesErrorList().size()==0||duplicate&&notesForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
					{
						if(notesForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& notesForm.getNotesErrorList().size()>0){
							Vector vct = notesForm.getNotesErrorList();
							int intVctSize = vct.size();
							Vector vctTemp = new Vector();
								for(int i=0;i<intVctSize&&vct.elementAt(i)!=null;i++){
									if(!((Notes)vct.elementAt(i)).getMsg().equalsIgnoreCase("")){
										vctTemp.add((Notes)vct.elementAt(i));
									}
								}
							notesForm.setNotesErrorList(vctTemp);
							notesForm.setNotesList(notesForm.getNotesErrorList());
					}
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.notestran.insert"));
					}
					else
					{   
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					}

					notesForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					//During mass update, if you get errors, you can save by save selected option.
					//During this we need to refresh the page with remaining error records, for this to set up 
					//Paging filter properties getting the records form errorlist object.
					if(notesForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)&& notesForm.getNotesErrorList().size()>0){
						notesForm.getPagingFilter().setTotalRecordsInSet(notesForm.getNotesErrorList().size());
					}
					else{
						notesForm.getPagingFilter().setTotalRecordsInSet(notesMngr.getCount(userToken,notesForm.getSearchObject()));	
					}
					//if the insert is successfull loading the fresh records to show
					if(duplicate&&notesForm.getNotesErrorList().size()==0){
					if(notesForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE)&&(notesForm.getNotesList().size()==0 || notesForm.getNotesList(0).getRptAff().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_EMPTY)))
					{
						notesForm.setNotesList(createEmptyNotesRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
					}
					else
					{
						notesForm.setNotesList(notesMngr.getNotes(userToken,notesForm.getSearchObject(),notesForm.getPagingFilter(),notesForm.getSortObject()));
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
						notesForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						notesForm.getPagingFilter().setTotalRecordsInSet(notesMngr.getCount(userToken,notesForm.getSearchObject()));
						notesForm.setNotesList(notesMngr.getNotes(userToken,notesForm.getSearchObject(),notesForm.getPagingFilter(),notesForm.getSortObject()));
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