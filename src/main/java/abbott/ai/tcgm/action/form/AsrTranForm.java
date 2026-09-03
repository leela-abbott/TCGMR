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
public class AsrTranForm extends TCGMForm
{
	private AsrTran searchObject = new AsrTran();
	private Vector asrTranList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
	private AsrTran addNew = new AsrTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("ASR");
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
	public AsrTranForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null)	||
		   (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.DFT_TRAN_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return AsrTran search object
	 */
	public AsrTran getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject AsrTran
	 */
	public void setSearchObject(AsrTran searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of AsrTran objects
	 */
	public Vector getAsrTranList()
	{
		if(this.asrTranList == null)
		{
			this.setAsrTranList(new Vector());
		}
		return this.asrTranList;
	}
	/**
	 *
	 * @param asrTranList vector of AsrTran objects
	 */
	public void setAsrTranList(Vector asrTranList)
	{
		this.asrTranList = asrTranList;
	}
	/**
	 * @param asrTran object to place into the Vector
	 * @param index position to place the asrTran object
	 */
	public void setAsrTranList(AsrTran asrTran,int index)
	{
		this.asrTranList.setElementAt(asrTran,index);
	}
	/**
	 * @param index element to return
	 * @return AsrTran object
	 */
	public AsrTran getAsrTranList(int index)
	{
		AsrTran asrTran = null;
		if(index >= 0 && index < this.getAsrTranList().size())
		{
			asrTran = (AsrTran)this.asrTranList.elementAt(index);
		}
		return asrTran;
	}
	
	// Begin the coding for get and set for asrTranListItem	
		/**
		 * @param asrTran object to place into the Vector
		 * @param index position to place the asrTran object
		 */
		public void setAsrTranListItem(AsrTran asrTran,int index)
		{
			this.asrTranList.setElementAt(asrTran,index);
		}
		/**
		 * @param index element to return
		 * @return AsrTran object
		 */
		public AsrTran getAsrTranListItem(int index)
		{
			AsrTran asrTran = null;
			if(index >= 0 && index < this.getAsrTranList().size())
			{
				asrTran = (AsrTran)this.asrTranList.elementAt(index);
			}
			return asrTran;
		}	
	//End the coding for get and set for asrTranListItem.
	 
	
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
	 * @return addNew AsrTran object
	 */
	public AsrTran getAddNew()
	{
		return this.addNew;
	}
	/**
	 *
	 * @param addNew AsrTran object
	 */
	public void setAddNew(AsrTran addNew)
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
	 * @return asrTranList size
	 */
	public long getAsrTranListSize()
	{
		return this.getAsrTranList().size();
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
		this.addNew.getAsr().clearMsg();
		// 5-24-05 End of code for clear Cmd & Error Msgs before initial page display

		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new AsrTran());
		
        //ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setAddNew(new AsrTran()); 

		if(this.errors.empty()){
			
			this.setPagingFilter(new PagingFilter());
		}
		
		//loop through the collection of asr trans and reset the selected property to false
		//this can be done for all properties in the asr tran if necessary.
		for (int i = 0; i<this.getAsrTranList().size();i++)
		{
			this.getAsrTranList(i).getAsr().setSelected(false);
			this.getAsrTranList(i).getAsr().setMsg("");
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

		//If save selected then validating list of asr tran records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
		{
			for(int i = 0; i < this.getAsrTranListSize(); i++)
			{
				if(this.getAsrTranList(i).getAsr().isSelected())
				{
					this.dataVal.validateAsrTran(this.getAsrTranList(i),this.errors,false);
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
			this.dataVal.validateAsrTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateAsrTran(this.getAddNew(),this.errors,true);
		}

		// If copy transaction make sure it isn't into the same model you're currently in
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ALL)
				|| this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_SELECTED) )
		{
			this.dataVal.validateModelCopy(this.getModelSelected(),  (TCGMState) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_STATE), this.errors);
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
			this.setSearchObject(new AsrTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new AsrTran());
		}
		/*****************************************************************************************/
		/**
		 * The following logic implements cursor navigation according to the last command executed
		 * by the user.  Based on the button clicked by the user, the cursor will be position on
		 * predetermined fields in order to improve transaction processing time.
		 */

		this.setFocusField(DBConst.DFT_TRAN_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("asrTranListItem[0].actionCode");
			this.setFocusField("addNew.actionCode");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.setFocusField("addNew.actionCode");
		}
	}

	/**
	 * Convenience method to set the model id and the dataset table id for the search row and the add new row.
	 * @param modelId Model Id currently selected
	 * @param datasetTableId Dataset Id for Asrs
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getAsr().setModelId(modelId);
		this.getAddNew().getAsr().setDatasetTableId(datasetTableId);
		this.getSearchObject().getAsr().setModelId(modelId);
		this.getSearchObject().getAsr().setDatasetTableId(datasetTableId);
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