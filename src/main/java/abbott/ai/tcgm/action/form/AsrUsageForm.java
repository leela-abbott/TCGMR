/*
 * Created on Jun 17, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.form;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AsrUsageForm extends TCGMForm{
	
	private String selAmountType;
	private ArrayList supAffList = new ArrayList();
		private String selUpdateType;
		private ASRUsage searchObject = new ASRUsage();
		private Vector asrList = new Vector();
		private Vector asrErrorList = new Vector();
		private PagingFilter pagingFilter = new PagingFilter();
		private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
		private TCGMDataValidation dataVal = new TCGMDataValidation("ASRUSAGE");
		private ActionErrors errors = new ActionErrors();
		private String errs = "";
	    private String modelSelected="";
	    private String supAffSelected;
	    private FormFile theFile;
	    private String cycle="";
		
	
	
	public AsrUsageForm()
		{
			super();

			// Set a default focus for this page.
			if((this.getFocusField() == null)	||
			   (this.getFocusField() == ""))
			{
				// Set the default focus field for the page
				this.setFocusField(DBConst.ASR_DFT_FOCUS);
			}
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
		public void setSearchObject(ASRUsage searchObject)
		{
			this.searchObject = searchObject;
		}
		/*****************************************************************************************/
		/**
		 *
		 * @return vector of AffBpc objects
		 */
		public Vector getAffASRList()
		{
			return this.asrList;
		}
		/**
		 *
		 * @param affBpcList vector of AffBpc objects
		 */
		public void setAffASRList(Vector affBpcList)
		{
			this.asrList = affBpcList;
		}
		/**
		 * @param affBpc object to place into vector
		 * @param index position to place affBpc object
		 */
		public long getAsrListSize()
		{
			return this.getAffASRList().size();
		}
		public void setAffBpcList(ASRUsage affBpc,int index)
		{
			this.asrList.setElementAt(affBpc,index);
		}
		/**
		 * @param index element to return
		 * @return AffBpc
		 */
		public ASRUsage getAffASRList(int index)
		{
			ASRUsage affBpc = null;
			if(index >= 0 && index < this.getAffASRList().size())
			{
				affBpc = (ASRUsage)this.asrList.elementAt(index);
			}
			return affBpc;
		}
	
		//7/2/05 Sridevi.K new code for nested-iterate fix in the jsp
	
		/**
			 * @param affBpc object to place into vector
			 * @param index position to place affBpc object
			 */
			public void setAffBpcListItem(ASRUsage affBpc,int index)
			{
				this.asrList.setElementAt(affBpc,index);
			}
		
		
			/**
			 * @param index element to return
			 * @return AffBpc
			 */
			public ASRUsage getAffBpcListItem(int index)
			{
				ASRUsage affBpc = null;
				if(index >= 0 && index < this.getAffASRList().size())
				{
					affBpc = (ASRUsage)this.asrList.elementAt(index);
				}
				return affBpc;
			}
//		7/2/05 Sridevi.K new code ends here for nested-iterate fix in the jsp
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
			return this.getAffASRList().size();
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
			this.setSearchObject(new ASRUsage());//ok to reset this as a new object.  The values are available in fields on the jsp page.
			this.setPagingFilter(new PagingFilter());
			this.setSupAffSelected(null);			

			//loop through the collection of affBpc's and reset the selected property to false
			//this can be done for all properties in the affBpc.
			for (int i = 0; i<this.getAffASRList().size();i++)
			{
				this.getAffASRList(i).setSelected(false);
			}
		}
//		/*****************************************************************************************/
//		/**
//		 *
//		 * @param mapping ActionMapping
//		 * @param request HttpServletRequest
//		 * @return ActionErrors
//		 */
//		public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
//		{
//			ActionErrors errors = new ActionErrors();
//
//			//put validation code here...
//
//			if(errors.empty())
//			{
//				return null;
//			}
//			else
//			{
//				return errors;
//			}
//		}

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
		public ASRUsage getSearchObject()
	
		{
			return searchObject;
		}
		/**
		 * @return ArrayList
		 */
		

	/**
	 * @return
	 */
	public String getModelSelected() {
		return modelSelected;
	}

	/**
	 * @param string
	 */
	public void setModelSelected(String string) {
		modelSelected = string;
	}
	
	public long getAsrErrorListSize()
		{
			int intSize = 0;
			if(this.getAsrErrorList()!=null){
				return this.getAsrErrorList().size();	
			}else{
				return intSize;
			}
		
		}

		/**
		 * @return
		 */
		public Vector getAsrErrorList() {
			return asrErrorList;
		}

		/**
		 * @param vector
		 */
		public void setAsrErrorList(Vector vector) {
			asrErrorList = vector;
		}

	/**
	 * @return
	 */
	public String getSupAffSelected() {
		return supAffSelected;
	}

	/**
	 * @param string
	 */
	public void setSupAffSelected(String string) {
		supAffSelected = string;
	}

	/**
	 * @return
	 */
	public ArrayList getSupAffList() {
		return supAffList;
	}

	/**
	 * @param list
	 */
	public void setSupAffList(ArrayList list) {
		supAffList = list;
	}
	
	//The below getTheFile and setTheFile method has been added for AsrUsageUpload.
	public FormFile getTheFile() {
		return theFile;
	}
	/**
	 * @param theFile The theFile to set.
	 */
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	
	public String getCycle() {
		return cycle;
	}
	/**
	 * @param cycle The cycle to set.
	 */
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	
	//The Above getTheFile and setTheFile method has been added for AsrUsageUpload.
	public void initModelAndDataset(String modelId, String datasetTableId)
		{
		
			this.getSearchObject().setModelId(modelId);
			this.getSearchObject().setDatasetTableId(datasetTableId);
		}

}
