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
 * <p>Description: Form Bean for the BPCS Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcsForm extends TCGMForm
{
	private Bpcs searchObject = new Bpcs();
	private Vector bpcsList = new Vector();
	private Vector bpcsErrorList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);
	private BpcsTran _addNew = new BpcsTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCS");
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
	//private BpcsTran addNew = new BpcsTran(); 3/12/03 I dn't thk ts is being used.

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcsForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.BPCS_DFT_FOCUS);
		}
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return Bpcs search object
	 */
	public Bpcs getSearchObject()
	{
		return this.searchObject;
	}

	/**
	 *
	 * @param searchObject Bpcs
	 */
	public void setSearchObject(Bpcs searchObject)
	{
		this.searchObject = searchObject;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return vector of Bpcs objects
	 */
	public Vector getBpcsList()
	{
		return this.bpcsList;
	}

	/**
	 *
	 * @param bpcsList vector of Bpcs objects
	 */
	public void setBpcsList(Vector bpcsList)
	{
		this.bpcsList = bpcsList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of Bpcs objects
	 */
	public Vector getBpcsErrorList()
	{
		return this.bpcsErrorList;
	}
	/**
	 *
	 * @param bpcsErrorList vector of Bpcs objects
	 */
	public void setBpcsErrorList(Vector bpcsErrorList)
	{
		this.bpcsErrorList = bpcsErrorList;
	}
	/**
	 * @param bpcs object to place into vector
	 * @param index position to place bpcs object
	 */
	public void setBpcsList(Bpcs bpcs,int index)
	{
		this.bpcsList.setElementAt(bpcs,index);
	}

	/**
	 * @param index element to return
	 * @return Bpcs
	 */
	public Bpcs getBpcsList(int index)
	{
		Bpcs bpcs = null;
		if(index >= 0 && index < this.getBpcsList().size())
		{
			bpcs = (Bpcs)this.bpcsList.elementAt(index);
		}
		return bpcs;
	}

	//	4-27-05 new code for set/get methods for AsrListItem starts from here (fix nested:iterate)
	/**
	 * @param bpcs object to place into vector
	 * @param index position to place bpcs object
	 */
	public void setBpcsListItem(Bpcs bpcs,int index)
	{
		this.bpcsList.setElementAt(bpcs,index);
	}

	/**
	  * @param index element to return
	  * @return Bpcs
	  */
	public Bpcs getBpcsListItem(int index)
	{
		Bpcs bpcs = null;
		if(index >= 0 && index < this.getBpcsList().size())
		{
			bpcs = (Bpcs)this.bpcsList.elementAt(index);
		}
		return bpcs;
	}
	// 4-27-05 new code for set/get methods for AsrListItem ends here (fix nested:iterate)


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
	 * @return bpcsList size
	 */
	public long getBpcsListSize()
	{
		return this.getBpcsList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return bpcsErrorList size
	 */
	public long getBpcsErrorListSize()
	{
		int intSize = 0;
		if(this.getBpcsErrorList()!=null){
			return this.getBpcsErrorList().size();	
		}else{
			return intSize;
		}
		
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return addNew BpcsTran object
	 */
	public BpcsTran getAddNew()
	{
		return this._addNew;
	}
	/**
	 *
	 * @param addNew BpcsTran object
	 */
	public void setAddNew(BpcsTran addNew)
	{
		this._addNew = addNew;
	}
	/*****************************************************************************************/
	/**
	 * The reset method is called by the action servlet on every request.  The reset
	 * method is intended to set all properties to their default values.  After they are set
	 * to their defaults then they will be populuated with values in the request/session.
	 * For checkboxes it is not possible to detect if they are unchecked because they do
	 * not get posted when unchecked.  This reset method should reseted the selected values to false.
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
		// 5-24-05 Always clear Cmd & Error Msgs before initial page display
		this.setCmd("");
		this._addNew.getBpcs().clearMsg();
		//5-24-05 End of code for clearing the Cmd & Error Msgs before initial page display
		
		//ok to reset this as a new object. The values are available in fields on the jsp page.
		this.setSearchObject(new Bpcs());
		
		//Clear even the columns while going into another page by calling setAddNew
		this.setAddNew(new BpcsTran());
		
		if (this.errors.empty()) {
			
			this.setPagingFilter(new PagingFilter());
		}
		//loop through the collection of bpcs's and reset the selected property to false
		//this can be done for all properties in the bpcs.
		for (int i = 0; i < this.getBpcsList().size(); i++)
		{
			this.getBpcsList(i).setSelected(false);
		}
		// Reset the default focus field for the page
		this.setFocusField(DBConst.BPCS_DFT_FOCUS);

		if(this._addNew.getBegPeriod() == null || this._addNew.getBegPeriod().equals(""))
		{
			this._addNew.getBpcs().setBegPeriod("1");
		}
		if(this._addNew.getEndPeriod() == null || this._addNew.getEndPeriod().equals(""))
		{
			this._addNew.getBpcs().setEndPeriod("12");
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
		
		//If save selected then validating list of bpcs records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getBpcsListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getBpcsList(i).isSelected())
				{
					this.dataVal.validateBpcs(this.getBpcsList(i),this.errors,false);
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
			this.dataVal.validateBpcsTran(this.getAddNew(),this.errors,false,TCGMConstants.URL_PARM_VAL_SAVE);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateBpcsTran(this.getAddNew(),this.errors,true,TCGMConstants.URL_PARM_VAL_MASS_UPDATE);
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

// 4-30-03 Begin new code block to fix mass update edit checking
	public Bpcs createMassUpdRecs(BpcsTran addNew,Vector bpcsList)
	{
		for(int i = 0; i < this.getBpcsListSize(); i++)
		{

			if(updColValue(addNew.getBpcs().getRptAff()))
			{

			}

			this.dataVal.validateBpcs(this.getBpcsList(i),this.errors,false);
		}
		return null;
	}


	public boolean updColValue(String newVal)
	{
		String retVal = newVal;

		if(!(newVal == null || newVal.equals("")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
// 4-30-03 End new code block to fix mass update edit checking
//         Don't do this code here. Perform this additional validate of the merged
//         MassUpdate data after the merge of the addNew and BpcsList vector has taken place.

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
			//Click on Filter clear should clear the entire form except Entry row if any
			BpcsTran bpcsTran = this.getAddNew();
			this.reset(mapping,request);
			this.setAddNew(bpcsTran);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new BpcsTran());
//			Click on Entry row clear should load the empty AsrList as initial load.
			this.setCmd("");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setBpcs(this.getBpcsList(this.getRowToCopyInt()));
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE))
		{
			this.getPagingFilter().setDispSelectedRecordPage();
		}

		/*****************************************************************************************/
		/**
		 * The following logic implements cursor navigation according to the last command executed
		 * by the user.  Based on the button clicked by the user, the cursor will be positioned on
		 * predetermined fields in order to improve transaction processing time.
		 */
		this.setFocusField(DBConst.BPCS_DFT_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("bpcsListItem[0].rptAff");
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
	 * @param datasetTableId Dataset Id for Bpcss
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getBpcs().setModelId(modelId);
		this.getAddNew().getBpcs().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}

}