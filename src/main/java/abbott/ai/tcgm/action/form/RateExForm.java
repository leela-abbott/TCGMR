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
 * <p>Description: Form Bean for the RateEx Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateExForm extends TCGMForm
{
	private RateEx searchObject = new RateEx();
	private Vector rateExList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = DBConst.DEF_SORT_RATEEX;
	private RateExTran _addNew = new RateExTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("RATEEX");
	private ActionErrors errors = new ActionErrors();

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public RateExForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.RATE_EX_DFT_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return RateEx search object
	 */
	public RateEx getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject RateEx
	 */
	public void setSearchObject(RateEx searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of RateEx objects
	 */
	public Vector getRateExList()
	{
		return this.rateExList;
	}
	/**
	 *
	 * @param rateExList vector of RateEx objects
	 */
	public void setRateExList(Vector rateExList)
	{
		this.rateExList = rateExList;
	}
	/**
	 * @param rateEx object to place into vector
	 * @param index position to place rateEx object
	 */
	public void setRateExList(RateEx rateEx,int index)
	{
		this.rateExList.setElementAt(rateEx,index);
	}
	/**
	 * @param index element to return
	 * @return RateEx
	 */
	public RateEx getRateExList(int index)
	{
		RateEx rateEx = null;
		if(index >= 0 && index < this.getRateExList().size())
		{
			rateEx = (RateEx)this.rateExList.elementAt(index);
		}
		return rateEx;
	}

// 5-10-05 Begin fix for nested:iterate
//	new code for set and get methods for RateDataListItem starts from here
	/**
		 * @param rateEx object to place into vector
		 * @param index position to place rateEx object
		 */
		public void setRateExListItem(RateEx rateEx,int index)
		{
			this.rateExList.setElementAt(rateEx,index);
		}
		/**
		 * @param index element to return
		 * @return RateEx
		 */
		public RateEx getRateExListItem(int index)
		{
			RateEx rateEx = null;
			if(index >= 0 && index < this.getRateExList().size())
			{
				rateEx = (RateEx)this.rateExList.elementAt(index);
			}
			return rateEx;
		}
//	new code for set and end methods for RateDataListItem end here
// 5-10-05 End fix for nested:iterate

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
	 * @return rateExList size
	 */
	public long getRateExListSize()
	{
		return this.getRateExList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return addNew RateExTran object
	 */
	public RateExTran getAddNew()
	{
		return this._addNew;
	}
	/**
	 *
	 * @param addNew RateExTran object
	 */
	public void setAddNew(RateExTran addNew)
	{
		this._addNew = addNew;
	}

	/**
	 *
	 * @return
	 */
	public String dspAddNewMsg()
	{
		StringBuffer sb = new StringBuffer("");
		if(! this.getAddNew().getRateEx().equals(""))
		{
			sb.append(" <a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getAddNew().getRateEx().getMsg());
			sb.append(" ');\" ");
			sb.append(" onmouseout='hideMsgPopup();' > ");
			sb.append(" <img src=\"images/exclamation.png\" />  ");
			sb.append("</a>");
		}

		return sb.toString();
	}

	/**
	 * @param colName Name of the column to check
	 * @return The HREF code for the sort links
	 */
	public String getSrtHref(String colName)
	{
		if (colName == null)
			colName = "";

		StringBuffer sb = new StringBuffer();
		sb.append("javascript:chgSrtSub(document.rateExForm,'");
		sb.append(colName);
		sb.append("');");

		return sb.toString();
	}
	/**
	 * @param sortCol The name of the column to check
	 * @return an <img> tag if the column is the one being used in the current sort.
	 */
	public String dspSort(String sortCol)
	{
		StringBuffer sb = new StringBuffer("");

		if(this.getSortObject().getSortColumn().equals(sortCol))
		{
			sb.append("<img alt=\"");
			sb.append(this.getSortObject().getSortImgAltTxt());
			sb.append("\" src=\"");
			sb.append(this.getSortObject().getSortImg());
			sb.append("\" align=\"center\" />");
		}
		return sb.toString();
	}

	/**
	 *
	 * @param row
	 * @return
	 */
	public String dspEditMsg(int row)
	{
		StringBuffer sb = new StringBuffer("");

		if(! this.getRateExList(row).getMsg().equals(""))
		{
			sb.append("<a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getRateExList(row).getMsg());
			sb.append(" ');\" ");
			sb.append(" onmouseout=\"hideMsgPopup();\" > ");
			sb.append(" <img src=\"images/exclamation.png\" />  ");
			sb.append("</a>");

		}
		return sb.toString();
	}

	/**
	 * @param length Field Len
	 * @return String
	 */
	public String getAutoTab(int length)
	{
		StringBuffer sb = new StringBuffer("return autoTab(this, ");
		sb.append(length);
		sb.append(", event);");
		return sb.toString();
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
		this._addNew.getRateEx().clearMsg();
		//5-24-05 end the code to clear the clear the error when you go to another page
		
		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new RateEx());
		
		//Call setAddNew to clear the record when you go to next page
		this.setAddNew(new RateExTran());

		if (this.errors.empty())
		{
			this.setPagingFilter(new PagingFilter());
		}
		//loop through the collection of rateExs and reset the selected property to false
		//this can be done for all properties in the rateEx.
		for (int i = 0; i < this.getRateExList().size(); i++)
		{
			this.getRateExList(i).setSelected(false);
		}
		// Reset the default focus field for the page
		this.setFocusField(DBConst.RATE_EX_DFT_FOCUS);

		if(this._addNew.getBegPeriod() == null || this._addNew.getBegPeriod().equals(""))
		{
			this._addNew.getRateEx().setBegPeriod("1");
		}
		if(this._addNew.getEndPeriod() == null || this._addNew.getEndPeriod().equals(""))
		{
			this._addNew.getRateEx().setEndPeriod("12");
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

		//If save selected then validating list of rateEx records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getRateExListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getRateExList(i).isSelected())
				{
					this.dataVal.validateRateEx(this.getRateExList(i),this.errors,false);
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
			this.dataVal.validateRateExTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateRateExTran(this.getAddNew(),this.errors,true);
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
			this.setAddNew(new RateExTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setRateEx(this.getRateExList(this.getRowToCopyInt()));
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
		this.setFocusField(DBConst.RATE_EX_DFT_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER))
		{
			this.setFocusField("rateExListItem[0].endAff");
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
	 * @param datasetTableId Dataset Id for RateEx's
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getRateEx().setModelId(modelId);
		this.getAddNew().getRateEx().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}

}