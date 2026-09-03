package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.struts.action.*;
//This class does not uses exceptions. So comment this import
//import abbott.ai.tcgm.exception.*;   

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class RateExTranForm extends TCGMForm
{
	private RateExTran searchObject = new RateExTran();
	private Vector rateExTranList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = DBConst.DEF_SORT_ASR_TRAN;
	private RateExTran addNew = new RateExTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("RATEEX");
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
	public RateExTranForm()
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
	 * @return RateExTran search object
	 */
	public RateExTran getSearchObject()
	{
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject RateExTran
	 */
	public void setSearchObject(RateExTran searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of RateExTran objects
	 */
	public Vector getRateExTranList()
	{
		if(this.rateExTranList == null)
		{
			this.setRateExTranList(new Vector());
		}
		return this.rateExTranList;
	}
	/**
	 *
	 * @param rateExTranList vector of RateExTran objects
	 */
	public void setRateExTranList(Vector rateExTranList)
	{
		this.rateExTranList = rateExTranList;
	}
	/**
	 * @param rateExTran object to place into the Vector
	 * @param index position to place the rateExTran object
	 */
	public void setRateExTranList(RateExTran rateExTran,int index)
	{
		this.rateExTranList.setElementAt(rateExTran,index);
	}
	/**
	 * @param index element to return
	 * @return RateExTran object
	 */
	public RateExTran getRateExTranList(int index)
	{
		RateExTran rateExTran = null;
		if(index >= 0 && index < this.getRateExTranList().size())
		{
			rateExTran = (RateExTran)this.rateExTranList.elementAt(index);
		}
		return rateExTran;
	}

// 5-10-05 Begin fix for nested:iterate
// New code for set and get methods of RateExTranList are added here
   /**
	* @param rateExTran object to place into the Vector
	* @param index position to place the rateExTran object
	*/
	public void setRateExTranListItem(RateExTran rateExTran,int index)
	{
	   this.rateExTranList.setElementAt(rateExTran,index);
	}
	/**
	* @param index element to return
	* @return RateExTran object
	*/
	public RateExTran getRateExTranListItem(int index)
	{
		RateExTran rateExTran = null;
		if(index >= 0 && index < this.getRateExTranList().size())
		{
			rateExTran = (RateExTran)this.rateExTranList.elementAt(index);
		}
		return rateExTran;
	}
// New code for set and get methods for RateExTranList ends here
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
	 * @return addNew RateExTran object
	 */
	public RateExTran getAddNew()
	{
		return this.addNew;
	}
	/**
	 *
	 * @param addNew RateExTran object
	 */
	public void setAddNew(RateExTran addNew)
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
	 * @return rateExTranList size
	 */
	public long getRateExTranListSize()
	{
		return this.getRateExTranList().size();
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
		this.addNew.getRateEx().clearMsg();
		//5-24-05 end the code to clear the error message when you leave the page
		
        //ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setSearchObject(new RateExTran());

        //call the setAddNew method to clear the existing record when you go to a different page
		this.setAddNew(new RateExTran()); 
		
		if (this.errors.empty())
			this.setPagingFilter(new PagingFilter());

		//loop through the collection of rateEx trans and reset the selected property to false
		//this can be done for all properties in the rateEx tran if necessary.
		for (int i = 0; i < this.getRateExTranList().size();i++)
		{
			this.getRateExTranList(i).getRateEx().setSelected(false);
			this.getRateExTranList(i).getRateEx().setMsg("");
		}
		// Set the default focus field for the page
		this.setFocusField(DBConst.RATE_EX_DFT_FOCUS);

		if(this.addNew.getBegPeriod() == null || this.addNew.getBegPeriod().equals(""))
		{
			this.addNew.getRateEx().setBegPeriod("1");
		}
		if(this.addNew.getEndPeriod() == null || this.addNew.getEndPeriod().equals(""))
		{
			this.addNew.getRateEx().setEndPeriod("12");
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

		//If save selected then validating list of rateEx tran records
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED) )
		{
			for(int i = 0; i < this.getRateExTranListSize(); i++)
			{
				if(this.getRateExTranList(i).getRateEx().isSelected())
				{
					this.dataVal.validateRateExTran(this.getRateExTranList(i),this.errors,false);
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
			this.dataVal.validateRateExTran(this.getAddNew(),this.errors,false);
		}
		//If mass update then validate add new
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.dataVal.validateRateExTran(this.getAddNew(),this.errors,true);
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
	 * @param rateExTran object with data to be validated
	 * @param errors ActionErrors
	 * @return true if validation passes
	 */
// 3/27/03 Validation moved to TCGMDataValidation
/*	public boolean validateRateExTran(RateExTran rateExTran,ActionErrors errors)
	{
		String methodName = "validateRateExTran(RateExTran,ActionErrors)";
		boolean validate = true;

		String acd = rateExTran.getActionCode();
		if(! (acd.equals(TCGMConstants.ACT_CD_ADD) ||
			  acd.equals(TCGMConstants.ACT_CD_CHG) ||
			  acd.equals(TCGMConstants.ACT_CD_DEL)))
		{
			rateExTran.getRateEx().appendMsg("The action code must be A, C, or D" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getRptAff().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Rpt Aff cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getRptProduct().getInvCode().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Rpt Inv Code cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getRptProduct().getList().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Rpt List cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getRptProduct().getPack().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Rpt Pack cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getSupAff().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Sup Aff cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getSupProduct().getInvCode().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Sup Inv Code cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getSupProduct().getList().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Sup List cannot be blank" + BR);
			validate = false;
		}

		if( rateExTran.getRateEx().getSupProduct().getPack().equals(""))
		{
			rateExTran.getRateEx().appendMsg("Sup Pack cannot be blank" + BR);
			validate = false;
		}

		String pubFlg = rateExTran.getPublishFlag();
		if(! (pubFlg.equals(TCGMConstants.FLAG_PUBLISHED) ||
			  pubFlg.equals(TCGMConstants.FLAG_UNPUBLISHED)))
		{
			rateExTran.getRateEx().appendMsg("Publish flag must be U or P" + BR);
			validate = false;
		}

		if(! validate)
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
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FETCH))
		{
			this.getPagingFilter().setStartRecord(1);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
			//this.reset(mapping,request);
			this.setSearchObject(new RateExTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new RateExTran());
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
			this.setFocusField("rateExTranListItem[0].actionCode");
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
		return "javascript:chgActCmdSubmit(document.rateExTranForm,'add','rateExTranSave.do');";
	}
	/**
	 * @return The HREF code for the mass update button
	 */
	public String getMassBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.rateExTranForm,'massupdate','rateExTranSave.do');";
	}
	/**
	 * @return the HREF code for the clear button in the add section
	 */
	public String getClrBtnHref()
	{
		return "javascript:chgActCmdSubmit(document.rateExTranForm,'clearaddnew','rateExTranMaint.do');";
	}
	/**
	 * @param colName Name of the column to check
	 * @return The HREF code for the sort links
	 */
	public String getSrtHref(String colName)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("javascript:chgSrtSub(document.rateExTranForm,'");
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
		if(! this.getAddNew().getRateEx().getMsg().equals(""))
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

	public String dspEditMsg(int row)
	{
		StringBuffer sb = new StringBuffer("");

		if(! this.getRateExTranList(row).getRateEx().getMsg().equals(""))
		{
			sb.append("<a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getRateExTranList(row).getRateEx().getMsg());
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
	 * @param datasetTableId Dataset Id for Asrs
	 */
	public void initModelAndDataset(String modelId, String datasetTableId)
	{
		this.getAddNew().getRateEx().setModelId(modelId);
		this.getAddNew().getRateEx().setDatasetTableId(datasetTableId);
		this.getSearchObject().getRateEx().setModelId(modelId);
		this.getSearchObject().getRateEx().setDatasetTableId(datasetTableId);
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
