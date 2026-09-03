package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Form Bean for the Affiliate BPC Maintenance page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class AffBpcForm extends TCGMForm
{
	private String selAmountType;
	private String selUpdateType;
	private AffBpc searchObject = new AffBpc();
	private Vector affBpcList = new Vector();
	private ArrayList supAffList = new ArrayList();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_AFF_BPC_DEF, DBConst.SORT_ASC);
	private TCGMDataValidation dataVal = new TCGMDataValidation("AFFBPC");
	private ActionErrors errors = new ActionErrors();
	private String supAffSelected;
	private FormFile theFile;
	private String cycle="";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public AffBpcForm()
	{
		super();
	}
	public String getSelUpdateType()
	{
		return selUpdateType;
	}
	public void setSelUpdateType(String selUpdateType)
	{
		this.selUpdateType = selUpdateType;
	}

	public String getSelAmountType()
	{
		return selAmountType;
	}
	public void setSelAmountType(String selAmountType)
	{
		this.selAmountType = selAmountType;
	}

	/**
	 *
	 * @return AffBpc search object
	 */
	/**
	 *
	 * @param searchObject AffBpc
	 */
	public void setSearchObject(AffBpc searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return vector of AffBpc objects
	 */
	public Vector getAffBpcList()
	{
		return this.affBpcList;
	}
	/**
	 *
	 * @param affBpcList vector of AffBpc objects
	 */
	public void setAffBpcList(Vector affBpcList)
	{
		this.affBpcList = affBpcList;
	}
	/**
	 * @param affBpc object to place into vector
	 * @param index position to place affBpc object
	 */
	public void setAffBpcList(AffBpc affBpc,int index)
	{
		this.affBpcList.setElementAt(affBpc,index);
	}
	/**
	 * @param index element to return
	 * @return AffBpc
	 */
	public AffBpc getAffBpcList(int index)
	{
		AffBpc affBpc = null;
		if(index >= 0 && index < this.getAffBpcList().size())
		{
			affBpc = (AffBpc)this.affBpcList.elementAt(index);
		}
		return affBpc;
	}
	
	//7/2/05 Sridevi.K new code for nested-iterate fix in the jsp
	
	/**
		 * @param affBpc object to place into vector
		 * @param index position to place affBpc object
		 */
		public void setAffBpcListItem(AffBpc affBpc,int index)
		{
			this.affBpcList.setElementAt(affBpc,index);
		}
		
		
		/**
		 * @param index element to return
		 * @return AffBpc
		 */
		public AffBpc getAffBpcListItem(int index)
		{
			AffBpc affBpc = null;
			if(index >= 0 && index < this.getAffBpcList().size())
			{
				affBpc = (AffBpc)this.affBpcList.elementAt(index);
			}
			return affBpc;
		}
//	7/2/05 Sridevi.K new code ends here for nested-iterate fix in the jsp
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
	 * @return affBpcList size
	 */
	public long getAffBpcListSize()
	{
		return this.getAffBpcList().size();
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
		this.setSearchObject(new AffBpc());//ok to reset this as a new object.  The values are available in fields on the jsp page.
		this.setPagingFilter(new PagingFilter());
		this.setSupAffSelected(null);

		//loop through the collection of affBpc's and reset the selected property to false
		//this can be done for all properties in the affBpc.
		for (int i = 0; i<this.getAffBpcList().size();i++)
		{
			this.getAffBpcList(i).setSelected(false);
		}
	}
//	/*****************************************************************************************/
//	/**
//	 *
//	 * @param mapping ActionMapping
//	 * @param request HttpServletRequest
//	 * @return ActionErrors
//	 */
//	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
//	{
//		ActionErrors errors = new ActionErrors();
//
//		//put validation code here...
//
//		if(errors.empty())
//		{
//			return null;
//		}
//		else
//		{
//			return errors;
//		}
//	}

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

		//this.dataVal.validateAffBpc(this.getSearchObject(),this.errors,false);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_ALL) ||
		   this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED))
		{
			this.dataVal.validateModelCopy(this.getModelSelected(), this.errors);
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
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FETCH))
		{
			this.getPagingFilter().setStartRecord(1);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
			this.reset(mapping,request);
		}
		// Modified Date: 12/08/2005
		// Modified By  : Udaya B Aravapalli
		// The follwoing code is added to fix the following issue.
		// On the Affiliate BPC Data screen when a user enters a record number and clicks on
		// the Go button the screen is not navigating to the record specified by the user.
		 
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE))
		{
			this.getPagingFilter().setDispSelectedRecordPage();
		}
		
	}
	public AffBpc getSearchObject()
	
	{
		return searchObject;
	}
	/**
	 * @return ArrayList
	 */
	public ArrayList getSupAffList() 
	{
		return supAffList;
	}

	/**
	 * @param ArrayList SupAffList
	 */
	public void setSupAffList(ArrayList SupAffList) {
		supAffList = SupAffList;
	}

	/**
	 * @return String 
	 */
	public String getSupAffSelected() {
		return supAffSelected;
	}

	/**
	 * @param string SupAffSelected
	 */
	public void setSupAffSelected(String SupAffSelected) {
		supAffSelected = SupAffSelected;
	}

	/**
	 * @return Returns the theFile.
	 */
	public FormFile getTheFile() {
		return theFile;
	}
	/**
	 * @param theFile The theFile to set.
	 */
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	/**
	 * @return Returns the cycle.
	 */
	public String getCycle() {
		return cycle;
	}
	/**
	 * @param cycle The cycle to set.
	 */
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
}