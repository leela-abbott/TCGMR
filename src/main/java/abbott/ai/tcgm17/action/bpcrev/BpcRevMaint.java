package abbott.ai.tcgm17.action.bpcrev;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.BpcRev;
import abbott.ai.tcgm.entities.BpcRevTran;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.exception.TCGMException;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.BpcRevMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class BpcRevMaint extends TCGMAction implements SessionAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 876772066250591451L;

	/**
	 * Default Constructor
	 */
	public BpcRevMaint()
	{
		super();
	}
	
	 private Map<String, Object> session;
	 
	// ===== this Properties =====
	 private BpcRev searchObject = new BpcRev();

	 private List<BpcRev> bpcRevList = new ArrayList<>();
	 private Vector<BpcRev> bpcRevErrorList = new Vector<>();

	 private PagingFilter pagingFilter = new PagingFilter();

	 private Sort sortObject =
	     new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);

	 private BpcRevTran addNew = new BpcRevTran();

	 private String errs = "";

	 // ===== TCGMForm Properties =====

	 private String rowToCopy = "";
	 private String cmd = "";
	 private String focusField = DBConst.BPC_REV_DFT_FOCUS;
	 private String returnLocation = "";
	 private String modelSelected = "";
	 private String showModels = "";

	 private Vector models = new Vector();
	 private Vector closedModels = new Vector();

	 private Option[] monthListNumber;
	 private Option[] twoDigitMonthListNumber;
	 private Option[] twoDigitMonthListNumberWithLabel;
	 private Option[] periodMonthList;
	 private Option[] yearList;
	 private Option[] exportSystemList;

	 private TCGMModel.Type modelType;

	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public String execute() throws Exception
    {
        HttpServletRequest request = getRequest();
		String methodName = "perform";

		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.clearActionErrors();
		//Object form = session.getAttribute("this");
		/*if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			addActionError(getText("error.bpcrev.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}*/
		if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				//this this = (this)form;//cast the form that was passed in to the correct type for this action

				processCmd();
				if (this!=null && this.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_ADV_FILTER) )  {
						request.getSession().setAttribute("bpcRevTranAdvFilter", this);
						this.reset();
						this.setForward(TCGMConstants.FORWARD_ADVANCEDFILTER);
				}else{				
				BpcRevMngr bpcRevMngr = new BpcRevMngr(); //create the helper class that will handle the processing

				try
				{
					this.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					if(this.getErrs().equalsIgnoreCase("on")){
						this.setBpcRevList(this.getBpcRevErrorList());
						this.getPagingFilter().setTotalRecordsInSet(this.getBpcRevErrorList().size());
						this.setErrs("");
						this.addActionError(getText("error.list"));
					}else{
					this.getPagingFilter().setTotalRecordsInSet(bpcRevMngr.getCount(this.getUserToken(request),this.getSearchObject()));
					
					if (this.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_EMPTY))
					{
						this.setBpcRevList(createEmptyBpcRevRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
					}
					else
					{
						this.setBpcRevList(bpcRevMngr.getBpcRev(this.getUserToken(request),this.getSearchObject(),this.getPagingFilter(),this.getSortObject()));
					}
					this.setBpcRevErrorList(new Vector());
					}
					this.getAddNew().getBpcRev().setBegPeriod("1");
					this.getAddNew().getBpcRev().setEndPeriod("12");
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMException ex)
				{
					this.logger.error(ex.toString(),ex);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			  }
			}
		}
		//if errors exist then save them into the request
		if(! this.getActionErrors().isEmpty())
		{
			// errors are managed by ActionSupport.addActionError()
		}

		//forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return this.getForward();
	}

	@Override
	public void withSession(Map<String, Object> arg0) {
		this.session = session;
		
	}
	
	//Getter and setter
	
	public BpcRev getSearchObject() {
	    return searchObject;
	}

	public void setSearchObject(BpcRev searchObject) {
	    this.searchObject = searchObject;
	}

	public List<BpcRev> getBpcRevList() {
	    return bpcRevList;
	}

	public void setBpcRevList(List<BpcRev> bpcRevList) {
	    this.bpcRevList = bpcRevList;
	}

	public Vector<BpcRev> getBpcRevErrorList() {
	    return bpcRevErrorList;
	}

	public void setBpcRevErrorList(Vector<BpcRev> bpcRevErrorList) {
	    this.bpcRevErrorList = bpcRevErrorList;
	}

	public PagingFilter getPagingFilter() {
	    return pagingFilter;
	}

	public void setPagingFilter(PagingFilter pagingFilter) {
	    this.pagingFilter = pagingFilter;
	}

	public Sort getSortObject() {
	    return sortObject;
	}

	public void setSortObject(Sort sortObject) {
	    this.sortObject = sortObject;
	}

	public BpcRevTran getAddNew() {
	    return addNew;
	}

	public void setAddNew(BpcRevTran addNew) {
	    this.addNew = addNew;
	}

	public String getErrs() {
	    return errs == null ? "" : errs.trim();
	}

	public void setErrs(String errs) {
	    this.errs = errs;
	}

	public String getCmd() {
	    return cmd;
	}

	public void setCmd(String cmd) {
	    this.cmd = cmd;
	}

	public String getRowToCopy() {
	    return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
	    this.rowToCopy = rowToCopy;
	}

	public String getFocusField() {
	    return focusField;
	}

	public void setFocusField(String focusField) {
	    this.focusField = focusField;
	}

	public String getReturnLocation() {
	    return returnLocation;
	}

	public void setReturnLocation(String returnLocation) {
	    this.returnLocation = returnLocation;
	}

	public String getModelSelected() {
	    return modelSelected;
	}

	public void setModelSelected(String modelSelected) {
	    this.modelSelected = modelSelected;
	}

	public String getShowModels() {
	    return showModels;
	}

	public void setShowModels(String showModels) {
	    this.showModels = showModels;
	}

	public Vector getModels() {
	    return models;
	}

	public void setModels(Vector models) {
	    this.models = models;
	}

	public Vector getClosedModels() {
	    return closedModels;
	}

	public void setClosedModels(Vector closedModels) {
	    this.closedModels = closedModels;
	}

	public TCGMModel.Type getModelType() {
	    return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
	    this.modelType = modelType;
	}
	
	//Form Helper methis
	public long getBpcRevListSize() {
	    return bpcRevList == null ? 0 : bpcRevList.size();
	}

	public long getBpcRevErrorListSize() {
	    return bpcRevErrorList == null ? 0 : bpcRevErrorList.size();
	}

	public BpcRev getBpcRevList(int index) {
	    if (index >= 0 && index < bpcRevList.size()) {
	        return bpcRevList.get(index);
	    }
	    return null;
	}

	public BpcRev getBpcRevListItem(int index) {
	    return getBpcRevList(index);
	}

	public void setBpcRevListItem(BpcRev bean, int index) {
	    bpcRevList.set(index, bean);
	}

	public int getRowToCopyInt() {
	    return Integer.parseInt(rowToCopy);
	}
	
//Reset methos
	public void reset() {

	    setCmd("");

	    addNew.getBpcRev().clearMsg();

	    searchObject = new BpcRev();

	    addNew = new BpcRevTran();

	    pagingFilter = new PagingFilter();

	    for (int i = 0; i < bpcRevList.size(); i++) {
	        bpcRevList.get(i).setSelected(false);
	    }

	    focusField = DBConst.BPC_REV_DFT_FOCUS;

	    if (addNew.getBegPeriod() == null
	            || addNew.getBegPeriod().isEmpty()) {
	        addNew.getBpcRev().setBegPeriod("1");
	    }

	    if (addNew.getEndPeriod() == null
	            || addNew.getEndPeriod().isEmpty()) {
	        addNew.getBpcRev().setEndPeriod("12");
	    }
	}
	
	//Process cmd
	public void processCmd() {

	    if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {

	        pagingFilter.setNextpage();

	    } else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {

	        pagingFilter.setPrevPage();

	    } else if (
	            TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)
	            || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

	        pagingFilter.setStartRecord(1);

	    } else if (
	            TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

	        BpcRevTran tmp = addNew;

	        reset();

	        addNew = tmp;

	    } else if (
	            TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

	        addNew = new BpcRevTran();

	        cmd = "";

	    } else if (
	            TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(cmd)) {

	        addNew.setBpcRev(
	                bpcRevList.get(getRowToCopyInt()));

	    } else if (
	            TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE
	                    .equals(cmd)) {

	        pagingFilter.setDispSelectedRecordPage();
	    }

	    focusField = DBConst.BPC_REV_DFT_FOCUS;

	    if (
	        TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)
	        || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

	        focusField = "addNew.actionCode";

	    } else if (
	            TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd)
	            || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

	        focusField = "addNew.actionCode";
	    }
	}
	
	//INitiate model and data set
	
	public void initModelAndDataset(
	        String modelId,
	        String datasetTableId) {

	    addNew.getBpcRev().setModelId(modelId);
	    addNew.getBpcRev().setDatasetTableId(datasetTableId);

	    searchObject.setModelId(modelId);
	    searchObject.setDatasetTableId(datasetTableId);
	}
	




}