package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.exception.*;   
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.struts.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class NotesTranForm extends TCGMForm
{
	private NotesTran searchObject = new NotesTran();
	private Vector notesTranList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject =new Sort(DBConst.COL_NOTES_DEF, DBConst.SORT_ASC);
	private NotesTran addNew = new NotesTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("NOTES");
	private ActionErrors errors = new ActionErrors();
	/*************************************************************
	*	Added by Uday on 02/04/2006 to provide the user(Analyst)
	*   the option to view the maintenance records of any user. 
	**************************************************************/
	private String userSelected = null;

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public NotesTranForm()
	{
		super();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return NotesTran search object
	 */
	public NotesTran getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject NotesTran
	 */
	public void setSearchObject(NotesTran searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of NotesTran objects
	 */
	public Vector getNotesTranList()
	{
		if(this.notesTranList == null)
		{
			this.setNotesTranList(new Vector());
		}
		return this.notesTranList;
	}
	/**
	 *
	 * @param notesTranList vector of NotesTran objects
	 */
	public void setNotesTranList(Vector notesTranList)
	{
		this.notesTranList = notesTranList;
	}
	/**
	 * @param notesTran object to place into the Vector
	 * @param index position to place the notesTran object
	 */
	public void setNotesTranList(NotesTran notesTran,int index)
	{
		this.notesTranList.setElementAt(notesTran,index);
	}
	/**
	 * @param index element to return
	 * @return NotesTran object
	 */
	public NotesTran getNotesTranList(int index)
	{
		NotesTran notesTran = null;
		if(index >= 0 && index < this.getNotesTranList().size())
		{
			notesTran = (NotesTran)this.notesTranList.elementAt(index);
		}
		return notesTran;
	}
//	Adding new code for get and set methods of notesTranListItem starts from here	
	  /**
	   * @param notesTran object to place into the Vector
	   * @param index position to place the notesTran object
	   */
	  public void setNotesTranListItem(NotesTran notesTran, int index) {
		
		  this.notesTranList.setElementAt(notesTran,index);
	  }
	
	  /**
	   * @param index element to return
	   * @return NotesTran object
	   */
	  public NotesTran getNotesTranListItem(int index){
		
		  NotesTran notesTran = null;
		  if(index >= 0 && index < this.getNotesTranList().size()) {
			
			  notesTran = (NotesTran)this.notesTranList.elementAt(index);
		  }
		  return notesTran;
	  }	
//	Addition of new code for get and set methods of notesTranListItem ends here


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
	 * @return sortObject
	 */
	public Sort getSortObject()
	{
		return sortObject;
	}
	/**
	 *
	 * @param sortObject Sort object
	 */
	public void setSortObject(Sort sortObject)
	{
		this.sortObject = sortObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return notesTranList size
	 */
	public long getNotesTranListSize()
	{
		return this.getNotesTranList().size();
	}
	/*****************************************************************************************/
	/**
	 * The reset method is called by the action servlet on every request.  The reset
	 * method is intended to set all properties to their default values.  After they are set
	 * to their defaults then they will be populuated with values in the request/session.
	 * For checkboxes it is not possible to detect if they are unchecked because they do
	 * not get posted when unchecked.  This reset method should resetd the selected values to false.
	 * Then when the form bean is re-populated it will set the values that are checked to be true.  This
	 * allows us to know when something has been unchecked.  For collections, you will need to iterate through
	 * the objects and reset the values.  Do not create a new Vector() because it will destroy your
	 * object and the values will not be populated in the vector at all.
	 *
	 * @param mapping Struts Action Mapping
	 * @param request HttpServletRequest
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
         //5-24-05 Always clear Cmd & Error Msgs before initial page display
			 this.setCmd("");
			 this.addNew.getNotes().clearMsg();
		//5-24-05 end of code for clearing the error when the page is changed
		
        //ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new NotesTran());
		
        //Call the setAddNew method to clear the record if you leave the page
		this.setAddNew(new NotesTran()); 
		
		if (this.errors.empty())
			this.setPagingFilter(new PagingFilter());

		//loop through the collection of notes trans and reset the selected property to false
		//this can be done for all properties in the notes tran if necessary.
		for (int i = 0; i<this.getNotesTranList().size();i++)
		{
			this.getNotesTranList(i).getNotes().setSelected(false);
			this.getNotesTranList(i).getNotes().setMsg("");
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

		//If save selected then validating list of notes tran records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
		{
			for(int i = 0; i < this.getNotesTranListSize(); i++)
			{
				if(this.getNotesTranList(i).getNotes().isSelected())
				{
					this.dataVal.validateNotesTran(this.getNotesTranList(i), this.errors, false);
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
					for ( ; it.hasNext() ; ) {
 						
							it.next(); 
							it.remove();
					}
				} 				
			}//end of if(errors.size())
			//New Code ends 05/25/05			
		}//end of if(this.getCmd())
		
		//If save then adding a new record
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD))
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
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE))
		{
			this.getPagingFilter().setDispSelectedRecordPage();
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
			this.setSearchObject(new NotesTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new NotesTran());
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
		this.getSearchObject().getNotes().setModelId(modelId);
		this.getSearchObject().getNotes().setDatasetTableId(datasetTableId);
	}
	/**
	 * @return String
	 */
	public String getUserSelected() 
	{
		return userSelected;
	}

	/**
	 * @param userSelected String
	 */
	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}	
}