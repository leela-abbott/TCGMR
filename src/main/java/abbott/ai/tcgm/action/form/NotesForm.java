package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.struts.action.*;

// Exception is not used in this class so remove this import
// import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM</p>   
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */

public class NotesForm extends TCGMForm
{
	private Notes searchObject = new Notes();
	private Vector notesList = new Vector();
	private Vector notesErrorList = new Vector();
	private Sort sortObject =new Sort(DBConst.COL_NOTES_DEF, DBConst.SORT_ASC);
	private PagingFilter pagingFilter = new PagingFilter();
	private TCGMDataValidation dataVal = new TCGMDataValidation("NOTES");
	private ActionErrors errors = new ActionErrors();
	private String errs = "";
	/*****************************************************************************************/
	/**
	 *
	 * @param errs String
	 */
	public void setErrs(String errs)
		{
			this.errs = errs;
		}
	/**
	 * @return errs
	 */
	public String getErrs()
		{
			if(this.errs == null)
			{
				this.errs = "";
			}
			return this.errs.trim();
		}
	/**
	 * This property will be used to hold the data for the mass update, add new row.
	 */
	private NotesTran addNew = new NotesTran();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public NotesForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.NOTES_DFT_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Notes
	 */
	public Notes getSearchObject()
	{
		if(this.searchObject == null)
		{
			this.searchObject = new Notes();
		}
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject Notes
	 */
	public void setSearchObject(Notes searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Sort
	 */
	public Sort getSortObject()
	{
		if(this.sortObject == null)
		{
			this.sortObject = DBConst.DEF_SORT_NOTES;
		}
		return this.sortObject;
	}
	/**
	 *
	 * @param sortObject Sort
	 */
	public void setSortObject(Sort sortObject)
	{
		this.sortObject = sortObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return addNew NotesTran object
	 */
	public NotesTran getAddNew()
	{
		return this.addNew;
	}
	/**
	 *
	 * @param addNew NotesTran object
	 */
	public void setAddNew(NotesTran addNew)
	{
		this.addNew = addNew;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Vector
	 */
	public Vector getNotesList()
	{
		if(this.notesList == null)
		{
			this. notesList = new Vector();
		}
		return this.notesList;
	}
	/**
	*
	* @param NotesList Vector
	*/
	public void setNotesList(Vector notesList)
	{
		this.notesList = notesList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of Notes objects
	 */
	public Vector getNotesErrorList()
	{
		return this.notesErrorList;
	}
	/**
	 *
	 * @param notesErrorList vector of Notes objects
	 */
	public void setNotesErrorList(Vector notesErrorList)
	{
		this.notesErrorList = notesErrorList;
	}
	/**
	 *
	 * @param index int
	 * @param Notes notes
	 */
	public void setNotesList(Notes notes, int index )
	{
		this.getNotesList().setElementAt(notes,index);
	}
	/**
	 *
	 * @param index int
	 * @return Notes
	 */
	public Notes getNotesList(int index)
	{
		Notes notes = null;
		if(index >= 0 && index < this.getNotesList().size())
		{
			notes = (Notes)this.getNotesList().elementAt(index);
		}
		return notes;
	}

// Adding new code for get and set methods of NotesListItem starts here
	/**
	 *
	 * @param index int
	 * @param Notes notes
	 */
	public void setNotesListItem(Notes notes, int index ) {

		this.notesList.setElementAt(notes,index);
	}


	/**
	 *
	 * @param index int
	 * @return notes
	 */
	 public Notes getNotesListItem(int index) {

		Notes notes = null;
		if(index >= 0 && index < this.getNotesList().size()) {

			notes = (Notes)this.getNotesList().elementAt(index);
		}
		return notes;
	 }
//Adding new code for get and set methods for NotesListItem end here.

	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getNotesListSize()
	{
		return this.getNotesList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return asrErrorList size
	 */
	public long getNotesErrorListSize()
	{
		int intSize = 0;
		if(this.getNotesErrorList()!=null){
			return this.getNotesErrorList().size();	
		}else{
			return intSize;
		}
		
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return pagingFilter
	 */
	public PagingFilter getPagingFilter()
	{
		return this.pagingFilter;
	}
	/**
	 *
	 * @param pagingFilter PagingFilter
	 */
	public void setPagingFilter(PagingFilter pagingFilter)
	{
		this.pagingFilter = pagingFilter;
	}
	/*****************************************************************************************/

	/*****************************************************************************************/
	/**
	 * The reset method is called by the action servlet on every request.  The reset
	 * method is intended to set all properties to their default values.  After they are set
	 * to their defaults then they will be populuated with values in the request/session.
	 * For checkboxes it is not possible to detect if they are unchecked because they do
	 * not get posted when unchecked.
	 *
	 * @param mapping Struts Action Mapping
	 * @param request HttpServletRequest
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		// 5-24-05 Always clear Cmd & Error Msgs before initial page display
		this.setCmd("");
		this.addNew.getNotes().clearMsg();
		// 5-24-05 end the code for clearing the error message when you leave the page
		
		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new Notes());
		
		//call setAddNew method to clear the record when you enter another page
		this.setAddNew(new NotesTran());

		if (this.errors.empty())
			this.setPagingFilter(new PagingFilter());
		//loop through the collection of Notes and reset the selected property to false
		//this can be done for all properties in the notes.
		for (int i = 0; i<this.getNotesList().size();i++)
		{
			this.getNotesList(i).setSelected(false);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		this.errors = new ActionErrors();
		this.getAddNew().setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

		//If save selected then validating list of asr records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getNotesListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getNotesList(i).isSelected())
				{
					this.dataVal.validateNotes(this.getNotesList(i),this.errors,false);
				}
			}
			//New Code begins 05/25/05
			if (this.errors.size(ActionErrors.GLOBAL_ERROR)>1)	{
								
				//Get the Iterator for the for the errors
				Iterator it = this.errors.get(ActionErrors.GLOBAL_ERROR);
				if( it!= null )	{
 					
					//Get the first error 
					ActionError actionError = (ActionError)it.next();
 					
					//Remove all other errors 
					for ( ; it.hasNext() ; )	{
 						
						it.next(); 
						it.remove();
					}
				} 				
			}//end of if(errors.size())
			//New Code ends 05/25/05			
		}//end of if(this.getCmd())
			
		//If save then adding a new record
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
		{
			this.dataVal.validateNotesTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateNotesTran(this.getAddNew(),this.errors,true);
		}

		if(this.errors.empty())
		{
			return null;
		}
		else
		{
			return this.errors;
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	public void processCmd(ActionMapping mapping,HttpServletRequest request)
	{
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_NEXT_PAGE))
		{
			this.getPagingFilter().setNextpage();
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_PREV_PAGE))
		{
			this.getPagingFilter().setPrevPage();
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			this.getPagingFilter().setStartRecord(1);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
//			Click on Filter clear should clear the entire form except Entry row if any
			NotesTran notesTran = this.getAddNew();
			this.reset(mapping,request);
			this.setAddNew(notesTran);
			
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new NotesTran());
//			Click on Entry row clear should load the empty AsrList as initial load.
			this.setCmd("");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setNotes(this.getNotesList(this.getRowToCopyInt()));
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE))
		{
			this.getPagingFilter().setDispSelectedRecordPage();
		}

		/*****************************************************************************************/
		/**
		 * The following logic implements cursor navigation according to the last command executed
		 * by the user.  Based on the button clicked by the user, the cursor will be position on
		 * predetermined fields in order to improve transaction processing time.
		 */
		this.setFocusField(DBConst.NOTES_DFT_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("notesListItem[0].rptAff");
			this.setFocusField("addNew.actionCode");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.setFocusField("addNew.actionCode");
		}
	}

	/**
	 * Convenience method to set the model id and the dataset table id for the search row and the add new row.
	 * @param modelId Model Id currently selected
	 * @param datasetTableId Dataset Id for Notess
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getNotes().setModelId(modelId);
		this.getAddNew().getNotes().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}


}