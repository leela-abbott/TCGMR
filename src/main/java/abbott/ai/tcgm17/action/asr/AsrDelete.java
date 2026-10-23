package abbott.ai.tcgm17.action.asr;

import java.util.Vector;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm17.action.TCGMAction;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.AsrTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;

public class AsrDelete extends TCGMAction {

	private static final long serialVersionUID = 1L;

	/*
	 * Properties migrated from AsrForm.
	 */
	private Asr searchObject = new Asr();

	private Vector<Asr> asrList = new Vector<Asr>();

	private Vector<Asr> asrErrorList = new Vector<Asr>();

	private PagingFilter pagingFilter = new PagingFilter();

	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);

	private AsrTran addNew = new AsrTran();

	private String errs = "";

	private String cmd = "";

	private String focusField = DBConst.ASR_DFT_FOCUS;

	private String rowToCopy = "";

	/*
	 * Constructor
	 */
	public AsrDelete() {
		super();
		initializeObjects();
	}

	@Override
	public String execute() {

		processCmd();

		boolean duplicate = true;

		AsrMngr asrMngr = new AsrMngr();
		//this.getAddNew().getAsr().setModelId(getState(request).getCurrentModelIdString());
		initModelAndDataset( getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

		try {

			if (TCGMConstants.URL_PARM_VAL_DELETE_ALL.equals(cmd)) {

				duplicate = asrMngr.addAllAsrToTrans(getUserToken(), searchObject, TCGMConstants.ACT_CD_DEL);

			} else if (TCGMConstants.URL_PARM_VAL_DELETE_SELECTED.equals(cmd)) {

				duplicate = asrMngr.addSelectedAsrToTrans(getUserToken(), asrList, TCGMConstants.ACT_CD_DEL);

			} else if (!cmd.trim().equals("")) {

				throw new TCGMException(getClass().getName(), "execute", "Invalid command in action");
			}

			if (duplicate) {

				addActionMessage(getText("success.asrtran.insert"));

			} else {

				addActionError(getText("error.duplicate"));
			}

			//initModelAndDataset( getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			pagingFilter.setTotalRecordsInSet(asrMngr.getCount(getUserToken(), searchObject));

			asrList = asrMngr.getAsr(getUserToken(), searchObject, pagingFilter, sortObject);

			return SUCCESS;

		} catch (Exception ex) {

			logger.error(ex.getMessage(), ex);

			addActionError(ex.getMessage());

			return ERROR;
		}
	}

	/*
	 * Make sure nested objects required by the JSP are initialized.
	 */
	private void initializeObjects() {

		if (searchObject == null) {
			searchObject = new Asr();
		}

		if (asrList == null) {
			asrList = new Vector<Asr>();
		}

		if (asrErrorList == null) {
			asrErrorList = new Vector<Asr>();
		}

		if (pagingFilter == null) {
			pagingFilter = new PagingFilter();
		}

		if (sortObject == null) {
			sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
		}

		if (addNew == null) {
			addNew = new AsrTran();
		}

		if (addNew.getAsr() == null) {
			addNew.setAsr(new Asr());
		}

		if (errs == null) {
			errs = "";
		}

		if (cmd == null) {
			cmd = "";
		}

		if (focusField == null || focusField.trim().length() == 0) {

			focusField = DBConst.ASR_DFT_FOCUS;
		}

		if (rowToCopy == null) {
			rowToCopy = "";
		}
	}

	/*
	 * Struts2 prepare method.
	 *
	 * Use this method if your action implements Preparable. It initializes nested
	 * objects before parameter binding.
	 */
	public void prepare() {
		initializeObjects();
	}

	/*
	 * Reset action fields.
	 *
	 * Unlike Struts1, Struts2 does not automatically invoke ActionForm.reset().
	 * Call this method explicitly when required.
	 */
	public void reset() {

		cmd = "";
		errs = "";
		rowToCopy = "";

		searchObject = new Asr();
		addNew = new AsrTran();
		asrErrorList = new Vector<Asr>();

		if (asrList == null) {
			asrList = new Vector<Asr>();
		} else {
			for (Asr asr : asrList) {
				if (asr != null) {
					asr.setSelected(false);
				}
			}
		}

		if (pagingFilter == null) {
			pagingFilter = new PagingFilter();
		}

		if (sortObject == null) {
			sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
		}

		focusField = DBConst.ASR_DFT_FOCUS;

		initializeObjects();
	}

	/*
	 * Process commands previously handled by AsrForm.
	 */
	private void processCmd() {

		initializeObjects();

		String command = getCmd();

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(command)) {

			pagingFilter.setNextpage();

		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(command)) {

			pagingFilter.setPrevPage();

		} else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(command)
				|| TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(command)) {

			pagingFilter.setStartRecord(1);

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(command)) {

			/*
			 * Preserve addNew while clearing the search/filter fields.
			 */
			AsrTran savedAddNew = addNew;

			reset();

			addNew = savedAddNew;

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(command)) {

			addNew = new AsrTran();
			cmd = "";

		} else if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(command)) {

			copySelectedRowToAddNew();

		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(command)) {

			pagingFilter.setDispSelectedRecordPage();
		}

		focusField = DBConst.ASR_DFT_FOCUS;

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(command) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(command)
				|| TCGMConstants.URL_PARM_VAL_SAVE.equals(command)
				|| TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(command)) {

			focusField = "addNew.actionCode";
		}
	}

	/*
	 * Copy an ASR row into addNew.
	 *
	 * A new Asr object is used so modifications to addNew do not unintentionally
	 * modify the original list row.
	 */
	private void copySelectedRowToAddNew() {

		int rowIndex = getRowToCopyInt();

		if (rowIndex < 0 || rowIndex >= getAsrList().size()) {
			return;
		}

		Asr source = getAsrList().get(rowIndex);

		if (source == null) {
			return;
		}

		Asr copiedAsr = new Asr();

		copiedAsr.setRptAff(source.getRptAff());
		copiedAsr.setSupAff(source.getSupAff());
		copiedAsr.setRptProduct(source.getRptProduct());
		copiedAsr.setSupProduct(source.getSupProduct());
		copiedAsr.setProductOrigin(source.getProductOrigin());
		copiedAsr.setSupKey(source.getSupKey());
		copiedAsr.setUsage(source.getUsage());
		copiedAsr.setSelected(false);

		/*
		 * Copy inherited TCGMEntity fields here if they are needed.
		 *
		 * For example:
		 *
		 * copiedAsr.setModelId(source.getModelId());
		 * copiedAsr.setDatasetTableId(source.getDatasetTableId());
		 */

		AsrTran copiedTransaction = new AsrTran();
		copiedTransaction.setAsr(copiedAsr);

		addNew = copiedTransaction;
	}

	/*
	 * Initialize model and dataset values.
	 */
	public void initModelAndDataset(String modelId, String datasetTableId) {

		initializeObjects();

		addNew.getAsr().setModelId(modelId);
		addNew.getAsr().setDatasetTableId(datasetTableId);

		searchObject.setModelId(modelId);
		searchObject.setDatasetTableId(datasetTableId);

		/*
		 * Initialize existing rows too, where required.
		 */
		for (Asr asr : asrList) {

			if (asr != null) {
				asr.setModelId(modelId);
				asr.setDatasetTableId(datasetTableId);
			}
		}

		for (Asr asr : asrErrorList) {

			if (asr != null) {
				asr.setModelId(modelId);
				asr.setDatasetTableId(datasetTableId);
			}
		}
	}

	/*
	 * Struts2 validation method.
	 *
	 * Delete commands normally require either an available search definition or at
	 * least one selected record.
	 */
	@Override
	public void validate() {

		initializeObjects();

		String command = getCmd();

		if (command.length() == 0) {
			return;
		}

		if (TCGMConstants.URL_PARM_VAL_DELETE_SELECTED.equals(command) && !hasSelectedAsr()) {

			addActionError(getText("error.asr.selection.required"));
		}

		if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(command) && getRowToCopyInt() < 0) {

			addActionError(getText("error.asr.row.invalid"));
		}
	}

	/*
	 * Return true when at least one row is selected.
	 */
	private boolean hasSelectedAsr() {

		if (asrList == null || asrList.isEmpty()) {
			return false;
		}

		for (Asr asr : asrList) {

			if (asr != null && asr.isSelected()) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Convert rowToCopy to an integer.
	 */
	public int getRowToCopyInt() {

		String value = getRowToCopy();

		if (value.length() == 0) {
			return -1;
		}

		try {
			return Integer.parseInt(value);

		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	/*
	 * Return ASR list size for JSP usage.
	 */
	public long getAsrListSize() {
		return asrList == null ? 0L : asrList.size();
	}

	/*
	 * Return ASR error-list size for JSP usage.
	 */
	public long getAsrErrorListSize() {
		return asrErrorList == null ? 0L : asrErrorList.size();
	}

	/*
	 * Indexed getter used for Struts2 list parameter binding.
	 *
	 * Example request property: asrListItem[0].selected
	 */
	public Asr getAsrListItem(int index) {

		if (index < 0) {
			return null;
		}

		ensureAsrListCapacity(index);

		return asrList.get(index);
	}

	/*
	 * Indexed setter.
	 *
	 * Important: the Struts2-compatible argument order is: setAsrListItem(int
	 * index, Asr asr)
	 */
	@StrutsParameter(depth = 2)
	public void setAsrListItem(int index, Asr asr) {

		if (index < 0) {
			return;
		}

		ensureAsrListCapacity(index);

		asrList.set(index, asr == null ? new Asr() : asr);
	}

	/*
	 * Ensure a list element exists for the requested index.
	 */
	private void ensureAsrListCapacity(int index) {

		if (asrList == null) {
			asrList = new Vector<Asr>();
		}

		while (asrList.size() <= index) {
			asrList.add(new Asr());
		}
	}

	/*
	 * Optional indexed error-list getter.
	 */
	public Asr getAsrErrorListItem(int index) {

		if (index < 0) {
			return null;
		}

		ensureAsrErrorListCapacity(index);

		return asrErrorList.get(index);
	}

	/*
	 * Optional indexed error-list setter.
	 */
	@StrutsParameter(depth = 2)
	public void setAsrErrorListItem(int index, Asr asr) {

		if (index < 0) {
			return;
		}

		ensureAsrErrorListCapacity(index);

		asrErrorList.set(index, asr == null ? new Asr() : asr);
	}

	private void ensureAsrErrorListCapacity(int index) {

		if (asrErrorList == null) {
			asrErrorList = new Vector<Asr>();
		}

		while (asrErrorList.size() <= index) {
			asrErrorList.add(new Asr());
		}
	}

	/*
	 * Getters and setters
	 */
	@StrutsParameter(depth = 3)
	public Asr getSearchObject() {

		if (searchObject == null) {
			searchObject = new Asr();
		}

		return searchObject;
	}

	@StrutsParameter(depth = 3)
	public void setSearchObject(Asr searchObject) {
		this.searchObject = searchObject == null ? new Asr() : searchObject;
	}

	@StrutsParameter(depth = 3)
	public Vector<Asr> getAsrList() {

		if (asrList == null) {
			asrList = new Vector<Asr>();
		}

		return asrList;
	}

	@StrutsParameter(depth = 3)
	public void setAsrList(Vector<Asr> asrList) {
		this.asrList = asrList == null ? new Vector<Asr>() : asrList;
	}

	public Vector<Asr> getAsrErrorList() {

		if (asrErrorList == null) {
			asrErrorList = new Vector<Asr>();
		}

		return asrErrorList;
	}

	@StrutsParameter(depth = 3)
	public void setAsrErrorList(Vector<Asr> asrErrorList) {
		this.asrErrorList = asrErrorList == null ? new Vector<Asr>() : asrErrorList;
	}

	@StrutsParameter(depth = 2)
	public PagingFilter getPagingFilter() {

		if (pagingFilter == null) {
			pagingFilter = new PagingFilter();
		}

		return pagingFilter;
	}

	@StrutsParameter(depth = 2)
	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter == null ? new PagingFilter() : pagingFilter;
	}

	@StrutsParameter(depth = 2)
	public Sort getSortObject() {

		if (sortObject == null) {
			sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
		}

		return sortObject;
	}

	@StrutsParameter(depth = 2)
	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject == null ? new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC) : sortObject;
	}

	@StrutsParameter(depth = 4)
	public AsrTran getAddNew() {

		if (addNew == null) {
			addNew = new AsrTran();
		}

		if (addNew.getAsr() == null) {
			addNew.setAsr(new Asr());
		}

		return addNew;
	}

	/*
	 * Depth 4 accommodates nested properties such as:
	 *
	 * addNew.asr.rptProduct.productCode addNew.asr.supProduct.productCode
	 */
	@StrutsParameter(depth = 4)
	public void setAddNew(AsrTran addNew) {

		this.addNew = addNew == null ? new AsrTran() : addNew;

		if (this.addNew.getAsr() == null) {
			this.addNew.setAsr(new Asr());
		}
	}

	public String getErrs() {
		return errs == null ? "" : errs.trim();
	}

	public void setErrs(String errs) {
		this.errs = errs == null ? "" : errs;
	}

	public String getCmd() {
		return cmd == null ? "" : cmd.trim();
	}

	public void setCmd(String cmd) {
		this.cmd = cmd == null ? "" : cmd;
	}

	public String getFocusField() {

		if (focusField == null || focusField.trim().length() == 0) {

			focusField = DBConst.ASR_DFT_FOCUS;
		}

		return focusField.trim();
	}

	public void setFocusField(String focusField) {
		this.focusField = focusField == null ? DBConst.ASR_DFT_FOCUS : focusField;
	}

	public String getRowToCopy() {
		return rowToCopy == null ? "" : rowToCopy.trim();
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy == null ? "" : rowToCopy;
	}
	
	
}