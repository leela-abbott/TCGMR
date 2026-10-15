package abbott.ai.tcgm17.action.affbpc;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.AffBpc;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AffBpcMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class AffBpcMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 442626681614420772L;

	private Map<String, Object> session;

	// AffBpcForm
	private String selAmountType;
	private String selUpdateType;
	private AffBpc searchObject = new AffBpc();
	private Vector<AffBpc> affBpcList = new Vector<>();
	private ArrayList supAffList = new ArrayList();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_AFF_BPC_DEF, DBConst.SORT_ASC);
	private TCGMDataValidation dataVal = new TCGMDataValidation("AFFBPC");
	private String supAffSelected;
	private File theFile; // Struts2 replacement for FormFile
	private String cycle = "";

	// TCGM Form

	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private Vector models = new Vector();
	private Vector closedModels = new Vector();
	private String rowToCopy = "";
	private String showModels = "";
	private TCGMModel.Type modelType;

	@Override
	public void withSession(Map<String, Object> arg0) {
		this.session = session;
	}

	public String execute() {

		try {

			processCmd();

			UserToken userToken = getUserToken();

			AffBpcMngr affBpcMngr = new AffBpcMngr();
			ModelMngr modelMngr = new ModelMngr();

			searchObject.setModelId(DBConst.DEF_MODEL_ID);
			searchObject.setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);

			pagingFilter.setTotalRecordsInSet(affBpcMngr.getCount(userToken, searchObject));

			affBpcList = affBpcMngr.getAffBpc(userToken, searchObject, pagingFilter, sortObject);

			supAffList = affBpcMngr.getSupAff(userToken);

			if (supAffSelected == null) {
				supAffSelected = TCGMConstants.NONE;
			}

			FactorModel fm = new FactorModel();
			fm.setStatus(TCGMModel.Status.OPEN);

			models = modelMngr.getModels(userToken, fm);

			modelSelected = TCGMConstants.NONE;

			return SUCCESS;

		} catch (TCGMException e) {

			addActionError(e.getMessage());

			return ERROR;
		}
	}

	// ProcessCmd
	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {
			pagingFilter.setNextpage();
		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {
			pagingFilter.setPrevPage();
		} else if (TCGMConstants.URL_PARM_VAL_FETCH.equals(cmd)) {
			pagingFilter.setStartRecord(1);
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {
			reset();
		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {
			pagingFilter.setDispSelectedRecordPage();
		}
	}

	// Reset
	public void reset() {

		searchObject = new AffBpc();

		pagingFilter = new PagingFilter();

		supAffSelected = null;

		for (int i = 0; i < affBpcList.size(); i++) {

			AffBpc affBpc = affBpcList.get(i);

			if (affBpc != null) {
				affBpc.setSelected(false);
			}
		}

		cmd = "";
	}

	// Validate
	// TODO
	/*
	 * public void validate() {
	 * 
	 * if (TCGMConstants.URL_PARM_VAL_SAVE_ALL.equals(cmd) ||
	 * TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {
	 * 
	 * dataVal.validateModelCopy(modelSelected, errors); } }
	 */
	
	public long getAffBpcListSize() {
	    return affBpcList.size();
	}

	public AffBpc getAffBpcListItem(int index) {

	    if (index >= 0 && index < affBpcList.size()) {
	        return affBpcList.get(index);
	    }

	    return null;
	}

	public void setAffBpcListItem(AffBpc affBpc, int index) {
	    affBpcList.set(index, affBpc);
	}

	public String getSelAmountType() {
		return selAmountType;
	}

	public void setSelAmountType(String selAmountType) {
		this.selAmountType = selAmountType;
	}

	public String getSelUpdateType() {
		return selUpdateType;
	}

	public void setSelUpdateType(String selUpdateType) {
		this.selUpdateType = selUpdateType;
	}

	@StrutsParameter(depth = 3)
	public AffBpc getSearchObject() {
		return searchObject;
	}

	@StrutsParameter(depth = 3)
	public void setSearchObject(AffBpc searchObject) {
		this.searchObject = searchObject;
	}

	public Vector<AffBpc> getAffBpcList() {
		return affBpcList;
	}

	public void setAffBpcList(Vector<AffBpc> affBpcList) {
		this.affBpcList = affBpcList;
	}

	public ArrayList getSupAffList() {
		return supAffList;
	}

	public void setSupAffList(ArrayList supAffList) {
		this.supAffList = supAffList;
	}

	@StrutsParameter(depth = 1)
	public PagingFilter getPagingFilter() {
		return pagingFilter;
	}

	@StrutsParameter(depth = 1)
	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter;
	}

	@StrutsParameter(depth = 1)
	public Sort getSortObject() {
		return sortObject;
	}

	@StrutsParameter(depth = 1)
	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject;
	}

	public TCGMDataValidation getDataVal() {
		return dataVal;
	}

	public void setDataVal(TCGMDataValidation dataVal) {
		this.dataVal = dataVal;
	}

	public String getSupAffSelected() {
		return supAffSelected;
	}

	public void setSupAffSelected(String supAffSelected) {
		this.supAffSelected = supAffSelected;
	}

	public File getTheFile() {
		return theFile;
	}

	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
	}

	public String getShowModels() {
		return showModels;
	}

	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
	}
	
	//Setter and Getter
	
	

	
	
}
