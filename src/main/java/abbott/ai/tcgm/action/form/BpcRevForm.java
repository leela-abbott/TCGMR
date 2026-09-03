package abbott.ai.tcgm.action.form;

import java.util.*;   
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.struts.action.*;
//Exception is not used in this class so remove this import
//import abbott.ai.tcgm.exception.*;


/**
 * <p>Title: TCGM</p>
 * <p>Description: Form Bean for the BPC Revision Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcRevForm extends TCGMForm
{
	private BpcRev searchObject = new BpcRev();
	private Vector bpcRevList = new Vector();
	private Vector bpcRevErrorList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);
	private BpcRevTran _addNew = new BpcRevTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCREV");
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
	//private BpcRevTran addNew = new BpcRevTran(); 3/12/03 I dn't thk ts is being used.

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcRevForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.BPC_REV_DFT_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return BpcRev search object
	 */
	public BpcRev getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject BpcRev
	 */
	public void setSearchObject(BpcRev searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of BpcRev objects
	 */
	public Vector getBpcRevList()
	{
		return this.bpcRevList;
	}
	/**
	 *
	 * @param bpcRevList vector of BpcRev objects
	 */
	public void setBpcRevList(Vector bpcRevList)
	{
		this.bpcRevList = bpcRevList;
	}
	
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of BpcRev objects
	 */
	public Vector getBpcRevErrorList()
	{
		return this.bpcRevErrorList;
	}
	/**
	 *
	 * @param bpcRevErrorList vector of BpcRev objects
	 */
	public void setBpcRevErrorList(Vector bpcRevErrorList)
	{
		this.bpcRevErrorList = bpcRevErrorList;
	}
	/**
	 * @param bpcRev object to place into vector
	 * @param index position to place bpcRev object
	 */
	public void setBpcRevList(BpcRev bpcRev,int index)
	{
		this.bpcRevList.setElementAt(bpcRev,index);
	}
	/**
	 * @param index element to return
	 * @return BpcRev
	 */
	public BpcRev getBpcRevList(int index)
	{
		BpcRev bpcRev = null;
		if(index >= 0 && index < this.getBpcRevList().size())
		{
			bpcRev = (BpcRev)this.bpcRevList.elementAt(index);
		}
		return bpcRev;
	}

	// 5-9-05 Begin fix for nested:iterate
	// Adding new code for set and get methods for the BpcRevListItem in the BpcRevForm.java
	/**
	 * @param bpcRev object to place into vector
	 * @param index position to place bpcRev object
	 */
	public void setBpcRevListItem(BpcRev bpcRev,int index)
	{
		this.bpcRevList.setElementAt(bpcRev,index);
	}

	/**
	 * @param index element to return
	 * @return BpcRev
	 */
	public BpcRev getBpcRevListItem(int index)
	{
		BpcRev bpcRev = null;
		if(index >= 0 && index < this.getBpcRevList().size())
		{
			bpcRev = (BpcRev)this.bpcRevList.elementAt(index);
		}
		return bpcRev;
	}
	// End the new code for the set and get methods for the BpcRevListItem in the BpcRevForm.java
	// 5-9-05 Begin fix for nested:iterate

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
	 * @return bpcRevList size
	 */
	public long getBpcRevListSize()
	{
		return this.getBpcRevList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return bpcRevErrorList size
	 */
	public long getBpcRevErrorListSize()
	{
		int intSize = 0;
		if(this.getBpcRevErrorList()!=null){
			return this.getBpcRevErrorList().size();	
		}else{
			return intSize;
		}
		
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return addNew BpcRevTran object
	 */
	public BpcRevTran getAddNew()
	{
		return this._addNew;
	}
	/**
	 *
	 * @param addNew BpcRevTran object
	 */
	public void setAddNew(BpcRevTran addNew)
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
		this._addNew.getBpcRev().clearMsg();
		//5-24-05 the code for clearing Cmd & Error Msgs before initial page display

		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new BpcRev());
		
	    //call setAddNew method for clearing the columns of the previous record
		this.setAddNew(new BpcRevTran());
		
		if (this.errors.empty())
		{
			this.setPagingFilter(new PagingFilter());
		}
		//loop through the collection of bpcRev's and reset the selected property to false
		//this can be done for all properties in the bpcRev.
		for (int i = 0; i < this.getBpcRevList().size(); i++)
		{
			this.getBpcRevList(i).setSelected(false);
		}
		// Reset the default focus field for the page
		this.setFocusField(DBConst.BPC_REV_DFT_FOCUS);

		if(this._addNew.getBegPeriod() == null || this._addNew.getBegPeriod().equals(""))
		{
			this._addNew.getBpcRev().setBegPeriod("1");
		}
		if(this._addNew.getEndPeriod() == null || this._addNew.getEndPeriod().equals(""))
		{
			this._addNew.getBpcRev().setEndPeriod("12");
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

		//If save selected then validating list of bpcRev records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getBpcRevListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getBpcRevList(i).isSelected())
				{
					this.dataVal.validateBpcRev(this.getBpcRevList(i),this.errors,false);
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
			this.dataVal.validateBpcRevTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateBpcRevTran(this.getAddNew(),this.errors,true);
			this.getAddNew().getBpcRev().appendMsg(this.dataVal.valRevType(this.getAddNew().getBpcRev().getRevType(),false));
			if(! this.getAddNew().getBpcRev().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			}
			if (this.errors.size(ActionErrors.GLOBAL_ERROR)>1)	
			{
				Iterator it = this.errors.get(ActionErrors.GLOBAL_ERROR);
				if( it!= null )	
				{
 					ActionError actionError = (ActionError)it.next();
					for ( ; it.hasNext() ; )	
					{
						it.next(); 
						it.remove();
					}
				} 				
			}
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
			BpcRevTran bpcRevTran = this.getAddNew();
			this.reset(mapping,request);
			this.setAddNew(bpcRevTran);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new BpcRevTran());
//			Click on Entry row clear should load the empty AsrList as initial load.
			this.setCmd("");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setBpcRev(this.getBpcRevList(this.getRowToCopyInt()));
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
		this.setFocusField(DBConst.BPC_REV_DFT_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("bpcRevListItem[0].rptAff");
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
	 * @param datasetTableId Dataset Id for BpcRevs
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getBpcRev().setModelId(modelId);
		this.getAddNew().getBpcRev().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}
}