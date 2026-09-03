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
 * <p>Description: Form Bean for the Rate Data Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateDataForm extends TCGMForm
{
	private RateData searchObject = new RateData();
	private Vector rateDataList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = DBConst.DEF_SORT_RATE_DATA;
	private RateDataTran _addNew = new RateDataTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("RATEDATA");
	private ActionErrors errors = new ActionErrors();

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public RateDataForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.RATE_DATA_DFT_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return RateData search object
	 */
	public RateData getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject RateData
	 */
	public void setSearchObject(RateData searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of RateData objects
	 */
	public Vector getRateDataList()
	{
		return this.rateDataList;
	}
	/**
	 *
	 * @param rateDataList vector of RateData objects
	 */
	public void setRateDataList(Vector rateDataList)
	{
		this.rateDataList = rateDataList;
	}
	/**
	 * @param rateData object to place into vector
	 * @param index position to place rateData object
	 */
	public void setRateDataList(RateData rateData, int index)
	{
		this.rateDataList.setElementAt(rateData,index);
	}
	/**
	 * @param index element to return
	 * @return RateData
	 */
	public RateData getRateDataList(int index)
	{
		RateData rateData = null;
		if(index >= 0 && index < this.getRateDataList().size())
		{
			rateData = (RateData)this.rateDataList.elementAt(index);
		}
		return rateData;
	}

//Addition of new code for set and get methods of RateDataListItem starts from here

	/**
	 * @param rateData object to place into vector
	 * @param index position to place rateData object
	 */
	public void setRateDataListItem(RateData rateData, int index)
	{
		this.rateDataList.setElementAt(rateData, index);
	}


	/**
	 * @param index element to return
	 * @return RateData
	 */
	public RateData getRateDataListItem(int index)
	{
		RateData rateData = null;
		if(index >= 0 && index < this.getRateDataList().size())
		{
			rateData = (RateData)this.rateDataList.elementAt(index);
		}
		return rateData;
	}
//Addition of new code for set and get methods of RateDataListItem ends here

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
		return this.sortObject;
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
	 * @return rateDataList size
	 */
	public long getRateDataListSize()
	{
		return this.getRateDataList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return addNew RateDataTran object
	 */
	public RateDataTran getAddNew()
	{
		return this._addNew;
	}
	/**
	 *
	 * @param addNew RateDataTran object
	 */
	public void setAddNew(RateDataTran addNew)
	{
		this._addNew = addNew;
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
		// 5-24-05 Always clear Cmd & Error Msgs before initial page display
		this.setCmd("");
		this._addNew.getRateData().clearMsg();
		//5-24-05 End the code to clear Cmd & Error Msgs before initial page is displayed
		
		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new RateData());
		
		//call setAddNew method to clear the exiting data when you leave the page 
		this.setAddNew(new RateDataTran());
		
		if (this.errors.empty())
		{
			this.setPagingFilter(new PagingFilter());
		}
		//loop through the collection of rateData's and reset the selected property to false
		//this can be done for all properties in the rateData.
		for (int i = 0; i < this.getRateDataList().size(); i++)
		{
			this.getRateDataList(i).setSelected(false);
		}
		// Reset the default focus field for the page
		this.setFocusField(DBConst.RATE_DATA_DFT_FOCUS);

		if(this._addNew.getBegPeriod() == null || this._addNew.getBegPeriod().equals(""))
		{
			this._addNew.getRateData().setBegPeriod("1");
		}
		if(this._addNew.getEndPeriod() == null || this._addNew.getEndPeriod().equals(""))
		{
			this._addNew.getRateData().setEndPeriod("12");
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

		//If save selected then validating list of rateData records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getRateDataListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getRateDataList(i).isSelected())
				{
					this.dataVal.validateRateData(this.getRateDataList(i),this.errors,false);
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
			this.dataVal.validateRateDataTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateRateDataTran(this.getAddNew(),this.errors,true);
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
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER))
		{
			this.getPagingFilter().setStartRecord(1);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
			this.reset(mapping,request);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new RateDataTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setRateData(this.getRateDataList(this.getRowToCopyInt()));
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
		this.setFocusField(DBConst.RATE_DATA_DFT_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER))
		{
			this.setFocusField("rateDataListItem[0].curCode");
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
	 * @param datasetTableId Dataset Id for RateDatas
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getRateData().setModelId(modelId);
		this.getAddNew().getRateData().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}

	/**
	 * Convenience method to set the the dataset table id for the search row and the add new row.
	 * @param datasetTableId Dataset Id for RateDatas
	 */
	public void initDataset(String datasetTableId)
	{
		this.getAddNew().getRateData().setDatasetTableId(datasetTableId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}
}