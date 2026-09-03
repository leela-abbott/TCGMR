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
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>   
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcExTranForm extends TCGMForm
{
	private BpcExTran _searchObject = new BpcExTran();
	private Vector bpcExTranList = new Vector();
	private PagingFilter _pagingFilter = new PagingFilter();
	private Sort _sortObject = new Sort(DBConst.COL_BPC_EXC_DEF, DBConst.SORT_ASC);
	private BpcExTran _addNew = new BpcExTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCEX");
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
	public BpcExTranForm()
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
	 * @return BpcExTran search object
	 */
	public BpcExTran getSearchObject()
	{
		return this._searchObject;
	}
	/**
	 *
	 * @param searchObject BpcExTran
	 */
	public void setSearchObject(BpcExTran searchObject)
	{
		this._searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of BpcExTran objects
	 */
	public Vector getBpcExTranList()
	{
		if(this.bpcExTranList == null)
		{
			this.setBpcExTranList(new Vector());
		}
		return this.bpcExTranList;
	}
	/**
	 *
	 * @param bpcExTranList vector of BpcExTran objects
	 */
	public void setBpcExTranList(Vector bpcExTranList)
	{
		this.bpcExTranList = bpcExTranList;
	}
	/**
	 * @param bpcExTran object to place into the Vector
	 * @param index position to place the bpcExTran object
	 */
	public void setBpcExTranList(BpcExTran bpcExTran,int index)
	{
		this.bpcExTranList.setElementAt(bpcExTran,index);
	}
	/**
	 * @param index element to return
	 * @return BpcExTran object
	 */
	public BpcExTran getBpcExTranList(int index)
	{
		BpcExTran bpcExTran = null;
		if(index >= 0 && index < this.getBpcExTranList().size())
		{
			bpcExTran = (BpcExTran)this.bpcExTranList.elementAt(index);
		}
		return bpcExTran;
	}
//Add new code for the get and set methods of BpcExTranListItem
		/**
		 * @param bpcExTran object to place into the Vector
		 * @param index position to place the bpcExTran object
		 */
		public void setBpcExTranListItem(BpcExTran bpcExTran, int index)
		{
			this.bpcExTranList.setElementAt(bpcExTran,index);
		}


		/**
		 * @param index element to return
		 * @return BpcExTran object
		 */
		public BpcExTran getBpcExTranListItem(int index)
		{
			BpcExTran bpcExTran = null;
			if(index >= 0 && index < this.getBpcExTranList().size())
			{
				bpcExTran = (BpcExTran)this.bpcExTranList.elementAt(index);
			}
			return bpcExTran;
		}
//End the new code for get and set methods for BpcExTranListItem


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
	 * @return bpcExTranList size
	 */
	public long getBpcExTranListSize()
	{
		return this.getBpcExTranList().size();
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
		this.setSearchObject(new BpcExTran());
		
		//call setAddNew to empty the columns
		this.setAddNew(new BpcExTran()); 
		
		if(this.errors.empty()){
			this.setPagingFilter(new PagingFilter());
		}
		
		//loop through the collection of bpcEx trans and reset the selected property to false
		//this can be done for all properties in the bpcEx tran if necessary.
		for (int i = 0; i<this.getBpcExTranList().size();i++)
		{
			this.getBpcExTranList(i).getBpcEx().setSelected(false);
			this.getBpcExTranList(i).getBpcEx().setMsg("");
		}
		// Set the default focus field for the page
		this.setFocusField(DBConst.BPC_EX_DFT_FOCUS);
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

		//If save selected then validating list of bpcsEx tran records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getBpcExTranListSize(); i++)
			{
				if(this.getBpcExTranList(i).getBpcEx().isSelected())
				{
					this.dataVal.validateBpcExTran(this.getBpcExTranList(i),this.errors,false);
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
			this.dataVal.validateBpcExTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateBpcExTran(this.getAddNew(),this.errors,true);
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

//******  This validate coding has been stripped out and moved to TCGMDataValidation

/*	public boolean validateBpcExTran(BpcExTran bpcExTran,ActionErrors errors)
	{
		String methodName = "validateBpcExTran(BpcExTran,ActionErrors)";
		boolean validate = true;

		String pubFlg = bpcExTran.getPublishFlag();
		if(! (pubFlg.equals(TCGMConstants.FLAG_PUBLISHED) ||
			  pubFlg.equals(TCGMConstants.FLAG_UNPUBLISHED)))
		{
			bpcExTran.getBpcEx().appendMsg("Publish flag must be U or P" + BR);
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
			this.setSearchObject(new BpcExTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new BpcExTran());
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
			//this.setFocusField("bpcExTranListItem[0].actionCode");
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
		return "javascript:chgActCmdSubmit(document.bpXTrnFrm,'add','bpcExTranSave.do');";
	}
	/**
	 * @return The HREF code for the mass update button
	 */
	public String getMassBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.bpXTrnFrm,'massupdate','bpcExTranSave.do');";
	}
	/**
	 * @return the HREF code for the clear button in the add section
	 */
	public String getClrBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.bpXTrnFrm,'clearaddnew','bpcExTranMaint.do');";
	}
	/**
	 * @param colName Name of the column to check
	 * @return The HREF code for the sort links
	 */
	public String getSrtHref(String colName)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'");
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
		if(! this.getAddNew().getBpcEx().getMsg().equals(""))
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

	public String dspEditMsg(int row)
	{
		StringBuffer sb = new StringBuffer("");

		if(! this.getBpcExTranList(row).getBpcEx().getMsg().equals(""))
		{
			sb.append("<a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getBpcExTranList(row).getBpcEx().getMsg());
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
	 * @param datasetTableId Dataset Id for BpcEx's
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getBpcEx().setModelId(modelId);
		this.getAddNew().getBpcEx().setDatasetTableId(datasetTableId);
		this.getSearchObject().getBpcEx().setModelId(modelId);
		this.getSearchObject().getBpcEx().setDatasetTableId(datasetTableId);
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