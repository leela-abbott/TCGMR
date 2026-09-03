package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.struts.action.*;

//This class is not using exception package so remove it
// import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */    
public class RateDataTranForm extends TCGMForm
{
	private RateDataTran searchObject = new RateDataTran();
	private Vector rateDataTranList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = DBConst.DEF_SORT_RATE_DATA_TRAN;
	private RateDataTran addNew = new RateDataTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("RATEDATA");
	private ActionErrors errors = new ActionErrors();
	private String userSelected = null;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public RateDataTranForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			this.setFocusField(DBConst.DFT_TRAN_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return RateDataTran search object
	 */
	public RateDataTran getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject RateDataTran
	 */
	public void setSearchObject(RateDataTran searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of RateDataTran objects
	 */
	public Vector getRateDataTranList()
	{
		if(this.rateDataTranList == null)
		{
			this.setRateDataTranList(new Vector());
		}
		return this.rateDataTranList;
	}
	/**
	 *
	 * @param rateDataTranList vector of RateDataTran objects
	 */
	public void setRateDataTranList(Vector rateDataTranList)
	{
		this.rateDataTranList = rateDataTranList;
	}
	/**
	 * @param rateDataTran object to place into the Vector
	 * @param index position to place the rateDataTran object
	 */
	public void setRateDataTranList(RateDataTran rateDataTran, int index)
	{
		this.rateDataTranList.setElementAt(rateDataTran, index);
	}
	/**
	 * @param index element to return
	 * @return RateDataTran object
	 */
	public RateDataTran getRateDataTranList(int index)
	{
		RateDataTran rateDataTran = null;
		if(index >= 0 && index < this.getRateDataTranList().size())
		{
			rateDataTran = (RateDataTran)this.rateDataTranList.elementAt(index);
		}
		return rateDataTran;
	}

	// Add the new code for get and set methods of the RateDataTranLisItem starting from here
	/**
	 * @param rateDataTran object to place into the Vector
	 * @param index position to place the rateDataTran object
	 */
	public void setRateDataTranListItem(RateDataTran rateDataTran, int index) {

		this.rateDataTranList.setElementAt(rateDataTran, index);
	}

	/**
	 * @param index element to return
	 * @return RateDataTran object
	 */
	public RateDataTran getRateDataTranListItem(int index) {

		RateDataTran rateDataTran = null;
		if(index >= 0 && index < this.getRateDataTranList().size())	{

			rateDataTran = (RateDataTran)this.rateDataTranList.elementAt(index);
		}
		return rateDataTran;
	}
	//Addition of The new code for get and set methods of the RateDataTranLisItem ends here

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
	 * @return addNew RateDataTran object
	 */
	public RateDataTran getAddNew()
	{
		RateDataTran rdTran =  this.addNew;
		if (!(rdTran ==null)) 
		{
			 if ((!(this.getUserSelected()==null))&& (!(this.getUserSelected().equalsIgnoreCase("ALL"))))
			{
				rdTran.setUserName(this.getUserSelected());
			}
		}
		return rdTran;
	}
	/**
	 *
	 * @param addNew RateDataTran object
	 */
	public void setAddNew(RateDataTran addNew)
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
	 * @return rateDataTranList size
	 */
	public long getRateDataTranListSize()
	{
		return this.getRateDataTranList().size();
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
		this.addNew.getRateData().clearMsg();
		//5-24-05 end code to clear the Cmd & Error Msgs before initial page display

		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new RateDataTran());
		
		//Call setAddNew method for clearing the data in the columns when you leave to the other page
		this.setAddNew(new RateDataTran());

		if (this.errors.empty())
			this.setPagingFilter(new PagingFilter());

		//loop through the collection of rateData trans and reset the selected property to false
		//this can be done for all properties in the rateData tran if necessary.
		for (int i = 0; i<this.getRateDataTranList().size();i++)
		{
			this.getRateDataTranList(i).getRateData().setSelected(false);
			this.getRateDataTranList(i).getRateData().setMsg("");
		}
		// Set the default focus field for the page
		this.setFocusField(DBConst.RATE_DATA_DFT_FOCUS);
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

		//If save selected then validating list of rateData tran records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getRateDataTranListSize(); i++)
			{
				if(this.getRateDataTranList(i).getRateData().isSelected())
				{
					this.dataVal.validateRateDataTran(this.getRateDataTranList(i),this.errors,false);
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
	 * @param rateDataTran object with data to be validated
	 * @param errors ActionErrors
	 * @return true if validation passes
	 */
/*	public boolean validateRateDataTran(RateDataTran rateDataTran,ActionErrors errors)
	{
		String methodName = "validateRateDataTran(RateDataTran,ActionErrors)";
		boolean validate = true;

		String acd = rateDataTran.getActionCode();
		if(! (acd.equals(TCGMConstants.ACT_CD_ADD) ||
			  acd.equals(TCGMConstants.ACT_CD_CHG) ||
			  acd.equals(TCGMConstants.ACT_CD_DEL)))
		{
			rateDataTran.getRateData().appendMsg("The action code must be A, C, or D" + BR);
			validate = false;
		}

		String pubFlg = rateDataTran.getPublishFlag();
		if(! (pubFlg.equals(TCGMConstants.FLAG_PUBLISHED) ||
			  pubFlg.equals(TCGMConstants.FLAG_UNPUBLISHED)))
		{
			rateDataTran.getRateData().appendMsg("Publish flag must be U or P" + BR);
			validate = false;
		}

		if(! validate)
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
		}

		return validate;
	} */

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
			//this.reset(mapping,request);
			this.setSearchObject(new RateDataTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new RateDataTran());
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
		this.setFocusField(DBConst.DFT_TRAN_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER))
		{
			this.setFocusField("rateDataTranListItem[0].actionCode");
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
		//this.getAddNew().getRateData().setModelId(modelId);
		this.getAddNew().getRateData().setDatasetTableId(datasetTableId);
		//this.getSearchObject().getRateData().setModelId(modelId);
		this.getSearchObject().getRateData().setDatasetTableId(datasetTableId);
	}

	/**
	 * Convenience method to set the dataset table id for the search row and the add new row.
	 * @param datasetTableId Dataset Id for Asrs
	 */
	public void initDataset(String datasetTableId)
	{
		this.getAddNew().getRateData().setDatasetTableId(datasetTableId);
		this.getSearchObject().getRateData().setDatasetTableId(datasetTableId);
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