package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Form Bean for the ASR Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class AsrForm extends TCGMForm
{
	private static Logger myLogger = Logger.getLogger( "ProcessScheduler" );
	private Asr searchObject = new Asr();
	private Vector asrList = new Vector();
	private Vector asrErrorList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
	private TCGMDataValidation dataVal = new TCGMDataValidation("ASR");
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
	private AsrTran addNew = new AsrTran();
	private ActionErrors errors = new ActionErrors();

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public AsrForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.ASR_DFT_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Asr search object
	 */
	public Asr getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject Asr
	 */
	public void setSearchObject(Asr searchObject)
	{
		this.searchObject = searchObject;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return vector of Asr objects
	 */
	public Vector getAsrList()
	{
		return this.asrList;
	}

	/**
	 *
	 * @param asrList vector of Asr objects
	 */
	public void setAsrList(Vector asrList)
	{
		this.asrList = asrList;
	}
	
	/*****************************************************************************************/
	/**
		 *
		 * @return vector of Asr objects
		 */
		public Vector getAsrErrorList()
		{
			return this.asrErrorList;
		}

		/**
		 *
		 * @param asrList vector of Asr objects
		 */
		public void setAsrErrorList(Vector asrErrorList)
		{
			this.asrErrorList = asrErrorList;
		}

	/**
	 * @param asr object to place into vector
	 * @param index position to place asr object
	 */

	public void setAsrList(Asr asr,int index)
	{
		this.asrList.setElementAt(asr,index);
	}

	/**
	 * @param index element to return
	 * @return Asr
	 */

	 public Asr getAsrList(int index)
	 {
		Asr asr = null;
		if(index >= 0 && index < this.getAsrList().size())
		{
			asr = (Asr)this.asrList.elementAt(index);
		}
		return asr;
	 }
		 // new code

		/**
			 * @param asr object to place into vector
			 * @param index position to place asr object
			 */
			public void setAsrListItem(Asr asr,int index)
			{
				this.asrList.setElementAt(asr,index);
			}
			/**
			 * @param index element to return
			 * @return Asr
			 */
			public Asr getAsrListItem(int index)
			{
				Asr asr = null;
				if(index >= 0 && index < this.getAsrList().size())
				{
					asr = (Asr)this.asrList.elementAt(index);
				}
				return asr;
			}

		 // new code

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
	 * @return asrList size
	 */
	public long getAsrListSize()
	{
		return this.getAsrList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return asrErrorList size
	 */
	public long getAsrErrorListSize()
	{
		int intSize = 0;
		if(this.getAsrErrorList()!=null){
			return this.getAsrErrorList().size();	
		}else{
			return intSize;
		}
		
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
	 * The reset method is called by the action servlet on every request.  The reset
	 * method is intended to set all properties to their default values.  After they are set
	 * to their defaults then they will be populuated with values in the request/session.
	 * For checkboxes it is not possible to detect if they are unchecked because they do
	 * not get posted when unchecked.  This reset method should reset the selected values to false.
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

		//ok to reset this as a new object. The values are available in fields on the jsp page.
		this.setSearchObject(new Asr());

        //ok to reset this as a new object. The values are available in fields on the jsp page.
		this.setAddNew(new AsrTran());

		if (this.errors.empty())
			this.setPagingFilter(new PagingFilter());

		//loop through the collection of asrs and reset the selected property to false
		//this can be done for all properties in the asr.
		for (int i = 0; i < this.getAsrList().size();i++)
		{
			this.getAsrList(i).setSelected(false);
		}
		// Reset the default focus field for the page
		this.setFocusField(DBConst.ASR_DFT_FOCUS);

		myLogger.debug("Leaving reset() method in AsrFrom.");
	}

	/*****************************************************************************************/
	/** If we have set "validate" to true in the struts-config file, our elements are validated
	 * here before we get to the action class.
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
			for(int i = 0; i < this.getAsrListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getAsrList(i).isSelected())
				{
					this.dataVal.validateAsr(this.getAsrList(i),this.errors,false);
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
			this.dataVal.validateAsrTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateAsrTran(this.getAddNew(),this.errors,true);
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
		myLogger.debug("Executing processCmd() method in AsrForm.");
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
			AsrTran asrTran = this.getAddNew();
			this.reset(mapping,request);
			this.setAddNew(asrTran);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new AsrTran());
			//Click on Entry row clear should load the empty AsrList as initial load.
			this.setCmd("");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setAsr(this.getAsrList(this.getRowToCopyInt()));
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
		this.setFocusField(DBConst.ASR_DFT_FOCUS);
		myLogger.info("Value for AsrForm.processCmd().cmd = " + this.getCmd());
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("asrList[0].productOrigin");
			//this.setFocusField("asrList.productOrigin");
			//this.setFocusField("asrListItem[0].productOrigin");
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
	 * @param datasetTableId Dataset Id for Asrs
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getAsr().setModelId(modelId);
		this.getAddNew().getAsr().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}
}