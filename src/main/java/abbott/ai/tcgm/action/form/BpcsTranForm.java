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
public class BpcsTranForm extends TCGMForm
{
	private BpcsTran _searchObject = new BpcsTran();
	private Vector bpcsTranList = new Vector();
	private PagingFilter _pagingFilter = new PagingFilter();
	private Sort _sortObject = new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);
	private BpcsTran _addNew = new BpcsTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCS");
	private ActionErrors errors = new ActionErrors();
	/*************************************************************
	*	Added by Uday on 02/04/2006 to provide the user(Analyst)
	*   the oprion to view the maintenance records of any user. 
	**************************************************************/
	private String userSelected = null; 
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public BpcsTranForm()
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
	 * @return BpcsTran search object
	 */
	public BpcsTran getSearchObject()
	{
		return this._searchObject;
	}
	/**
	 *
	 * @param searchObject BpcsTran
	 */
	public void setSearchObject(BpcsTran searchObject)
	{
		this._searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of BpcsTran objects
	 */
	public Vector getBpcsTranList()
	{
		if(this.bpcsTranList == null)
		{
			this.setBpcsTranList(new Vector());
		}
		return this.bpcsTranList;
	}
	/**
	 *
	 * @param ReceiverList vector of BpcsTran objects
	 */
	public void setBpcsTranList(Vector ReceiverList)
	{
		this.bpcsTranList = ReceiverList;
	}
	/**
	 * @param Receiver object to place into the Vector
	 * @param index position to place the Receiver object
	 */
	public void setBpcsTranList(BpcsTran Receiver,int index)
	{
		this.bpcsTranList.setElementAt(Receiver,index);
	}
	/**
	 * @param index element to return
	 * @return BpcsTran object
	 */
	public BpcsTran getBpcsTranList(int index)
	{
		BpcsTran Receiver = null;
		if(index >= 0 && index < this.getBpcsTranList().size())
		{
			Receiver = (BpcsTran)this.bpcsTranList.elementAt(index);
		}
		return Receiver;
	}


// 5-9-05 Begin Fix nested:iterate
// The new methods for get and set of BpcsTranListItem starts from here

	/**
	 * @param Receiver object to place into the Vector
	 * @param index position to place the Receiver object
	 */
	public void setBpcsTranListItem(BpcsTran Receiver,int index)
	{
		this.bpcsTranList.setElementAt(Receiver,index);
	}

	/**
	 * @param index element to return
	 * @return BpcsTran object
	 */
	public BpcsTran getBpcsTranListItem(int index)
	{
		BpcsTran Receiver = null;
		if(index >= 0 && index < this.getBpcsTranList().size())
		{
			Receiver = (BpcsTran)this.bpcsTranList.elementAt(index);
		}
		return Receiver;
	}

 // The new methods for get and set of BpcsTranListItem ends here
 // 5-9-05 End Fix nested:iterate

	/*****************************************************************************************/
	/**
	 *
	 * @return pagingFilter
	 */
	public PagingFilter getPagingFilter()
	{
		return this._pagingFilter;
	}
	/**
	 *
	 * @param pagingFilter PagingFilter
	 */
	public void setPagingFilter(PagingFilter pagingFilter)
	{
		this._pagingFilter = pagingFilter;
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
	 *
	 * @return sortObject
	 */
	public Sort getSortObject()
	{
		return this._sortObject;
	}
	/**
	 *
	 * @param sortObject Sort object
	 */
	public void setSortObject(Sort sortObject)
	{
		this._sortObject = sortObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return ReceiverList size
	 */
	public long getBpcsTranListSize()
	{
		return this.getBpcsTranList().size();
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
		this._addNew.getBpcs().clearMsg();
		// 5-24-05 end of code here for clearing the error when you leave the page
		
		//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new BpcsTran());
		
		//Call setAddNew method to clear the values in the columns. When you leave the page
		this.setAddNew(new BpcsTran()); 
		
		if (this.errors.empty()) {
			this.setPagingFilter(new PagingFilter());
		}		

		//loop through the collection of bpcs trans and reset the selected property to false
		//this can be done for all properties in the bpcs tran if necessary.
		for (int i = 0; i < this.getBpcsTranList().size(); i++)
		{
			this.getBpcsTranList(i).getBpcs().setSelected(false);
			// 4-23-03 Temporary delete to see if this is causing valid error msgs to not display.
			//this.getBpcsTranList(i).getBpcs().setMsg("");
		}
		// Set the default focus field for the page
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

		//If save selected then validating list of bpcs tran records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getBpcsTranListSize(); i++)
			{
				if(this.getBpcsTranList(i).getBpcs().isSelected())
				{
					this.dataVal.validateBpcsTran(this.getBpcsTranList(i),this.errors,false,TCGMConstants.URL_PARM_VAL_SAVE_SELECTED);
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
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD))
		{
			this.dataVal.validateBpcsTran(this.getAddNew(),this.errors,false,TCGMConstants.URL_PARM_VAL_ADD);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateBpcsTran(this.getAddNew(),this.errors,true,TCGMConstants.URL_PARM_VAL_MASS_UPDATE);
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

	/**
	 *
	 * @param Receiver object with data to be validated
	 * @param errors ActionErrors
	 * @return true if validation passes
	 */
/*	public boolean validateBpcsTran(BpcsTran Receiver,ActionErrors errors)
	{
		String methodName = "validateBpcsTran(BpcsTran,ActionErrors)";
		boolean validate = true;

		String pubFlg = Receiver.getPublishFlag();
		if(! (pubFlg.equals(TCGMConstants.FLAG_PUBLISHED) ||
			  pubFlg.equals(TCGMConstants.FLAG_UNPUBLISHED)))
		{
			Receiver.getBpcs().appendMsg("Publish flag must be U or P" + BR);
			validate = false;
		}

		//Begin Validate Beg Period.
		String begStr = Receiver.getBegPeriod().trim();
		boolean begValid = true;
		int begInt = 0;
		try
		{
			begInt = Integer.parseInt(begStr);

			if( begStr.equals("") || begInt < 1 || begInt > 12 )
			{
				begValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			begValid = false;
		}
		if(!begValid)
		{
			Receiver.getBpcs().appendMsg("Beginning Period cannot be blank, must be a value between 1 and 12, and must be less than the ending period." + BR);
			validate = false;
		}
		//End Validate Beg Period.

		//Begin Validate End Period.
		String endStr = Receiver.getEndPeriod().trim();
		int endInt = 0;
		boolean endValid = true;
		try
		{
			endInt = Integer.parseInt(endStr);
			if( endStr.equals("") ||
				endInt < 1 ||
				endInt > 12 ||
				endInt < begInt)
			{
				endValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			endValid = false;
		}
		if(!endValid)
		{
			Receiver.getBpcs().appendMsg("Ending Period cannot be blank, must be a value between 1 and 12, and must be greater than the beginning period." + BR);
			validate = false;
		}
		//End Validate End Period.


		//Begin Validate BillPrice.
		String billPriceStr = Receiver.getBillPrice().trim();
		double billPriceDbl = 0;
		boolean billPriceValid = true;
		try
		{
			billPriceDbl = Double.parseDouble(billPriceStr);
			if( billPriceStr.equals("") || billPriceDbl < 0 )
			{
				billPriceValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			billPriceValid = false;
		}
		if(!billPriceValid)
		{
			Receiver.getBpcs().appendMsg("Bill Price cannot be blank and must be > 0" + BR);
			validate = false;
		}
		//End Validate Bill Price.

		if(Receiver.getBpcs().getBpCurCode().trim().equals(""))
		{
			Receiver.getBpcs().appendMsg("BP Cur Code cannot be blank" + BR);
			validate = false;
		}

		//Begin Validate CostPrice.
		String costPriceStr = Receiver.getCostPrice().trim();
		double costPriceDbl = 0;
		boolean costPriceValid = true;
		try
		{
			costPriceDbl = Double.parseDouble(costPriceStr);
			if( costPriceStr.equals("") || costPriceDbl < 0 )
			{
				costPriceValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			costPriceValid = false;
		}
		if(!costPriceValid)
		{
			Receiver.getBpcs().appendMsg("Cost Price cannot be blank and must be > 0" + BR);
			validate = false;
		}
		//End Validate Cost Price.

		if(Receiver.getBpcs().getCostCurCode().trim().equals(""))
		{
			Receiver.getBpcs().appendMsg("Cost Cur Code cannot be blank"  + BR);
			validate = false;
		}

		if(Receiver.getBpcs().getFreezeCost().trim().equals(""))
		{
			Receiver.getBpcs().appendMsg("Freeze Cost cannot be blank" + BR);
			validate = false;
		}

		if(validate == false)
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
		}

		return validate;
	}
*/
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
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FETCH)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			this.getPagingFilter().setStartRecord(1);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
			//this.reset(mapping,request);
			this.setSearchObject(new BpcsTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new BpcsTran());
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
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("bpcsTranListItem[0].actionCode");
			this.setFocusField("addNew.actionCode");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.setFocusField("addNew.actionCode");
		}
	}


	/*****************************************************************************************************
	 * Some of this code may be more appropriate for the JSP page.  However, I ran into problems
	 * with the size of the page and it was not able to dispaly.  I was getting a lot of "Invalid Branch"
	 * exceptions.  After researching that error on the web, it appeared as if the problem is a size
	 * limitation of the method that is created in the servlet that is created from the jsp.
	 * Because of this I am moving code that would normally go into the JSP into the form bean.
	 *****************************************************************************************************/


	/**
	 * @return onchange code for filter input boxes.
	 */
	public String getFltrChng()
	{
		return "makeFilterDirty('pagingDiv','red','bold');";
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

	/**
	 * @return The HREF code for the add button
	 */
	public String getAddBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.bpcsTranForm,'add','bpcsTranSave.do');";
	}
	/**
	 * @return The HREF code for the mass update button
	 */
	public String getMassBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.bpcsTranForm,'massupdate','bpcsTranSave.do');";
	}
	/**
	 * @return the HREF code for the clear button in the add section
	 */
	public String getClrBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.bpcsTranForm,'clearaddnew','bpcsTranMaint.do');";
	}
	/**
	 * @param colName Name of the column to check
	 * @return The HREF code for the sort links
	 */
	public String getSrtHref(String colName)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("javascript:chgSrtSubEbcdic(document.bpcsTranForm,'");
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

	public String dspAddNewMsg()
	{
		StringBuffer sb = new StringBuffer("");
		if(! this.getAddNew().getBpcs().getMsg().equals(""))
		{
			sb.append(" <a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getAddNew().getBpcs().getMsg());
			sb.append(" ');\" ");
			sb.append(" onmouseout='hideMsgPopup();' > ");
			sb.append(" <img src=\"images/exclamation.png\" />  ");
			sb.append("</a>");
		}

		return sb.toString();
	}

	public String dspEditMsg(int row)
	{
		StringBuffer sb = new StringBuffer("");

		if(! this.getBpcsTranList(row).getBpcs().getMsg().equals(""))
		{
			sb.append("<a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getBpcsTranList(row).getBpcs().getMsg());
			sb.append(" ');\" ");
			sb.append(" onmouseout=\"hideMsgPopup();\" > ");
			sb.append(" <img src=\"images/exclamation.png\" />  ");
			sb.append("</a>");

		}
		return sb.toString();
	}

	/**
	 * Convenience method to set the model id and the dataset table id for the search row and the add new row.
	 * @param modelId Model Id currently selected
	 * @param datasetTableId Dataset Id for Bpcs's
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getBpcs().setModelId(modelId);
		this.getAddNew().getBpcs().setDatasetTableId(datasetTableId);
		this.getSearchObject().getBpcs().setModelId(modelId);
		this.getSearchObject().getBpcs().setDatasetTableId(datasetTableId);
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