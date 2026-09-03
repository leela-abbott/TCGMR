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
 * <p>Description: Form Bean for the Bpc Ex Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcExForm extends TCGMForm
{
	private BpcEx searchObject = new BpcEx();
	private Vector bpcExList = new Vector();
	private Vector bpcExErrorList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_BPC_EXC_DEF, DBConst.SORT_ASC);
	private BpcExTran _addNew = new BpcExTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCEX");
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

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcExForm()
	{
		super();

		// Set a default focus for this page.
		if((this.getFocusField() == null) || (this.getFocusField() == ""))
		{
			// Set the default focus field for the page
			this.setFocusField(DBConst.BPC_EX_DFT_FOCUS);
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return BpcEx search object
	 */
	public BpcEx getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject BpcEx
	 */
	public void setSearchObject(BpcEx searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of BpcEx objects
	 */
	public Vector getBpcExList()
	{
		return this.bpcExList;
	}
	/**
	 *
	 * @param bpcExList vector of BpcEx objects
	 */
	public void setBpcExList(Vector bpcExList)
	{
		this.bpcExList = bpcExList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of BpcEx objects
	 */
	public Vector getBpcExErrorList()
	{
		return this.bpcExErrorList;
	}
	/**
	 *
	 * @param bpcExErrorList vector of bpcEx objects
	 */
	public void setBpcExErrorList(Vector bpcExErrorList)
	{
		this.bpcExErrorList = bpcExErrorList;
	}

	/**
	 * @param bpcEx object to place into vector
	 * @param index position to place bpcEx object
	 */
	public void setBpcExList(BpcEx bpcEx,int index)
	{
		this.bpcExList.setElementAt(bpcEx,index);
	}
	/**
	 * @param index element to return
	 * @return BpcEx
	 */
	public BpcEx getBpcExList(int index)
	{
		BpcEx bpcEx = null;
		if(index >= 0 && index < this.getBpcExList().size())
		{
			bpcEx = (BpcEx)this.bpcExList.elementAt(index);
		}
		return bpcEx;
	}


	//new code for set and get BpcExListItem methods start from here
	/**
	 * @param bpcEx object to place into vector
	 * @param index position to place bpcEx object
	 */
	public void setBpcExListItem(BpcEx bpcEx,int index)
	{
		this.bpcExList.setElementAt(bpcEx,index);
	}

	/**
	 * @param index element to return
	 * @return BpcEx
	 */
	public BpcEx getBpcExListItem(int index)
	{
		BpcEx bpcEx = null;
		if(index >= 0 && index < this.getBpcExList().size())
		{
			bpcEx = (BpcEx)this.bpcExList.elementAt(index);
		}
		return bpcEx;
	}

	// new code for set and get BpcExListItem methods end here


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
	 * @return bpcExList size
	 */
	public long getBpcExListSize()
	{
		return this.getBpcExList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return bpcExErrorList size
	 */
	public long getBpcExErrorListSize()
	{
		int intSize = 0;
		if(this.getBpcExErrorList()!=null){
			return this.getBpcExErrorList().size();	
		}else{
			return intSize;
		}
		
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return addNew BpcExTran object
	 */
	public BpcExTran getAddNew()
	{
		return this._addNew;
	}
	/**
	 *
	 * @param addNew BpcExTran object
	 */
	public void setAddNew(BpcExTran addNew)
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
		if(! this.getAddNew().getBpcEx().equals(""))
		{
			sb.append(" <a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getAddNew().getBpcEx().getMsg());
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
		sb.append("javascript:chgSrtSubEbcdic(document.bpcExForm,'");
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


	public String dspEditMsg(int row)
	{
		StringBuffer sb = new StringBuffer("");

		if(! this.getBpcExList(row).getMsg().equals(""))
		{
			sb.append("<a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getBpcExList(row).getMsg());
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
		//5-24-05 Always clear Cmd & Error Msgs before initial page display
		this.setCmd("");
		this._addNew.getBpcEx().clearMsg();
		//5-24-05 End of code for clear Cmd & Error Msgs before initial page display

		
		//ok to reset this as a new object. The values are available in fields on the jsp page.
		this.setSearchObject(new BpcEx());
		
		//To clear the older values call setAddNew 
		this.setAddNew(new BpcExTran());
		
		if(this.errors.empty())
		{
			this.setPagingFilter(new PagingFilter());
		}
		//loop through the collection of bpcExs and reset the selected property to false
		//this can be done for all properties in the bpcEx.
		for (int i = 0; i<this.getBpcExList().size();i++)
		{
			this.getBpcExList(i).setSelected(false);
		}
		// Reset the default focus field for the page
		this.setFocusField(DBConst.BPC_EX_DFT_FOCUS);

		if(this._addNew.getBegPeriod() == null || this._addNew.getBegPeriod().equals(""))
		{
			this._addNew.getBpcEx().setBegPeriod("1");
		}
		if(this._addNew.getEndPeriod() == null || this._addNew.getEndPeriod().equals(""))
		{
			this._addNew.getBpcEx().setEndPeriod("12");
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
			for(int i = 0; i < this.getBpcExListSize(); i++)
			{
				// 4-9-03 Only validate the records that were selected
				if(this.getBpcExList(i).isSelected())
				{
					this.dataVal.validateBpcEx(this.getBpcExList(i),this.errors,false);
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
			this.dataVal.validateBpcExTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateBpcExTran(this.getAddNew(),this.errors,true);
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
			BpcExTran bpcExTran = this.getAddNew();
			this.reset(mapping,request);
			this.setAddNew(bpcExTran);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new BpcExTran());
//			Click on Entry row clear should load the empty AsrList as initial load.
			this.setCmd("");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW))
		{
			this.getAddNew().setBpcEx(this.getBpcExList(this.getRowToCopyInt()));
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
		this.setFocusField(DBConst.BPC_EX_DFT_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("bpcExListItem[0].endAff");
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
		this.getAddNew().getBpcEx().setModelId(modelId);
		this.getAddNew().getBpcEx().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}
}