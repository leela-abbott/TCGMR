package abbott.ai.tcgm17.action.bpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.struts2.action.ServletRequestAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.Bpcs;
import abbott.ai.tcgm.entities.BpcsTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.BpcsMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class BpcsMaint extends TCGMAction implements ServletRequestAware {

	private HttpServletRequest request;

	/*
	 * ========================================================== BpcsForm
	 * attributes migrated into Struts 2 Action
	 * ==========================================================
	 */

	private Bpcs searchObject = new Bpcs();
	
	//private Vector bpcsList = new Vector();
	private List<Bpcs> bpcsList = new ArrayList<>();
	private Vector bpcsErrorList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);
	private BpcsTran addNew = new BpcsTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCS");
	private String errs = "";

	/*
	 * These properties were previously inherited from TCGMForm. They should now be
	 * available directly from the Action.
	 */
	private String cmd = "";
	private String focusField = "";
	private String rowToCopy = "";

	/*
	 * ========================================================== Struts 2 execution
	 * ==========================================================
	 */

	@Override
	public String execute() {

		HttpSession session = request.getSession();

		if (!isSessionValid(request)) {
			return TCGMConstants.G_FORWARD_SELECT_MODEL;
		}

		if (!isModelSelected(request)) {
			return TCGMConstants.G_FORWARD_SELECT_MODEL;
		}

		try {

			/*
			 * Replaces:
			 *
			 * bpcsForm.processCmd(mapping, request);
			 */
			processCmd();

			/*
			 * Advanced Filter
			 */
			if (TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(getCmd())) {

				request.getSession().setAttribute("bpcsTranAdvFilter", this);

				reset();

				return TCGMConstants.FORWARD_ADVANCEDFILTER;
			}

			BpcsMngr bpcsMngr = new BpcsMngr();

			/*
			 * Replaces:
			 *
			 * bpcsForm.initModelAndDataset(...)
			 */
			initModelAndDataset(getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			/*
			 * Error records
			 */
			if ("on".equalsIgnoreCase(getErrs())) {

				setBpcsList(getBpcsErrorList());

				setErrs("");

				getPagingFilter().setTotalRecordsInSet(getBpcsErrorList().size());

				addActionError("Error loading BPCS list.");
			} else {

				getPagingFilter().setTotalRecordsInSet(bpcsMngr.getCount(getUserToken(request), getSearchObject()));

				/*
				 * Initial page load.
				 */
				if ("".equalsIgnoreCase(getCmd())) {

					setBpcsList( createEmptyBpcRecs(getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
				} else {

					setBpcsList(bpcsMngr.getBpcs(getUserToken(request), getSearchObject(), getPagingFilter(),getSortObject()));
				}

				setBpcsErrorList(new Vector());
			}

			/*
			 * Default periods.
			 */
			getAddNew().getBpcs().setBegPeriod("1");
			getAddNew().getBpcs().setEndPeriod("12");

			return SUCCESS;

		} catch (TCGMException ex) {

			logger.error(ex.toString(), ex);

			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

			return TCGMConstants.G_FORWARD_EXCEPTION;
		}
	}

	/*
	 * ========================================================== Struts 2
	 * Validation ==========================================================
	 */

	@Override
	public void validate() {

		/*
		 * Validate selected records.
		 */
		if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(getCmd())) {

			for (int i = 0; i < getBpcsListSize(); i++) {

				Bpcs bpcs = getBpcsList(i);

				if (bpcs != null && bpcs.isSelected()) {

					/*
					 * Your existing TCGMDataValidation currently expects Struts 1 ActionErrors.
					 *
					 * Ideally migrate validateBpcs() so it returns messages or throws validation
					 * exceptions, then call addActionError().
					 */
				}
			}
		}

		/*
		 * Save new record validation.
		 */
		else if (TCGMConstants.URL_PARM_VAL_SAVE.equals(getCmd())) {

			/*
			 * Migrate:
			 *
			 * dataVal.validateBpcsTran( getAddNew(), errors, false,
			 * TCGMConstants.URL_PARM_VAL_SAVE);
			 */
		}

		/*
		 * Mass update validation.
		 */
		else if (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(getCmd())) {

			/*
			 * Migrate:
			 *
			 * dataVal.validateBpcsTran( getAddNew(), errors, true,
			 * TCGMConstants.URL_PARM_VAL_MASS_UPDATE);
			 */
		}
	}

	/*
	 * ========================================================== processCmd
	 * migrated from BpcsForm
	 * ==========================================================
	 */

	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(getCmd())) {
			getPagingFilter().setNextpage();
		}

		else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(getCmd())) {

			getPagingFilter().setPrevPage();
		}

		else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(getCmd())
				|| TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(getCmd())) {

			getPagingFilter().setStartRecord(1);
		}

		else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(getCmd())) {

			BpcsTran tempAddNew = getAddNew();
			reset();
			setAddNew(tempAddNew);
		}

		else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(getCmd())) {
			setAddNew(new BpcsTran());
			setCmd("");
		}

		else if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(getCmd())) {
			Bpcs bpcs = getBpcsList(getRowToCopyInt());

			if (bpcs != null) {
				getAddNew().setBpcs(bpcs);
			}
		}

		else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(getCmd())) {
			getPagingFilter().setDispSelectedRecordPage();
		}

		/*
		 * Cursor / focus management.
		 */
		setFocusField(DBConst.BPCS_DFT_FOCUS);

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(getCmd())
				|| TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(getCmd())) {

			setFocusField("addNew.actionCode");
		}

		else if (TCGMConstants.URL_PARM_VAL_SAVE.equals(getCmd())
				|| TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(getCmd())) {

			setFocusField("addNew.actionCode");
		}
	}

	/*
	 * ========================================================== reset migrated
	 * from BpcsForm ==========================================================
	 */

	public void reset() {

		setCmd("");

		if (getAddNew() != null && getAddNew().getBpcs() != null) {

			getAddNew().getBpcs().clearMsg();
		}

		setSearchObject(new Bpcs());

		setAddNew(new BpcsTran());

		if (!hasActionErrors()) {
			setPagingFilter(new PagingFilter());
		}

		/*
		 * Reset selected rows.
		 */
		if (getBpcsList() != null) {

			for (int i = 0; i < getBpcsList().size(); i++) {

				Bpcs bpcs = getBpcsList(i);

				if (bpcs != null) {
					bpcs.setSelected(false);
				}
			}
		}

		setFocusField(DBConst.BPCS_DFT_FOCUS);

		if (getAddNew().getBegPeriod() == null || getAddNew().getBegPeriod().isEmpty()) {

			getAddNew().getBpcs().setBegPeriod("1");
		}

		if (getAddNew().getEndPeriod() == null || getAddNew().getEndPeriod().isEmpty()) {

			getAddNew().getBpcs().setEndPeriod("12");
		}
	}

	/*
	 * ========================================================== Model / Dataset
	 * initialization ==========================================================
	 */

	public void initModelAndDataset(String modelId, String datasetTableId) {

		getAddNew().getBpcs().setModelId(modelId);

		getAddNew().getBpcs().setDatasetTableId(datasetTableId);

		getSearchObject().setModelId(modelId);

		getSearchObject().setDatasetTableId(datasetTableId);
	}

	/*
	 * ========================================================== Mass Update helper
	 * ==========================================================
	 */

	public Bpcs createMassUpdRecs(BpcsTran addNew, Vector bpcsList) {

		for (int i = 0; i < getBpcsListSize(); i++) {

			if (updColValue(addNew.getBpcs().getRptAff())) {

				// Perform update logic.
			}

			/*
			 * Existing validation needs to be migrated from ActionErrors to Struts 2
			 * validation.
			 */
		}

		return null;
	}

	public boolean updColValue(String newVal) {

		return newVal != null && !newVal.isEmpty();
	}

	/*
	 * ========================================================== Collection indexed
	 * access methods ==========================================================
	 */

	public Bpcs getBpcsList(int index) {

		if (bpcsList != null && index >= 0 && index < bpcsList.size()) {

			return (Bpcs) bpcsList.get(index);
		}

		return null;
	}

	public void setBpcsList(Bpcs bpcs, int index) {

		if (bpcsList != null && index >= 0 && index < bpcsList.size()) {

			bpcsList.set(index,bpcs);
		}
	}

	/*
	 * Kept for compatibility with existing JSP property names.
	 */
	public Bpcs getBpcsListItem(int index) {
		return getBpcsList(index);
	}

	public void setBpcsListItem(Bpcs bpcs, int index) {

		setBpcsList(bpcs, index);
	}

	/*
	 * ========================================================== Utility properties
	 * ==========================================================
	 */

	public long getBpcsListSize() {

		return bpcsList == null ? 0 : bpcsList.size();
	}

	public long getBpcsErrorListSize() {

		return bpcsErrorList == null ? 0 : bpcsErrorList.size();
	}

	public int getRowToCopyInt() {

		try {
			return Integer.parseInt(rowToCopy);
		} catch (Exception e) {
			return 0;
		}
	}

	/*
	 * ========================================================== Struts 2
	 * ServletRequestAware
	 * ==========================================================
	 */

	@Override
	public void withServletRequest(HttpServletRequest request) {

		this.request = request;
	}

	/*
	 * ========================================================== Getters / Setters
	 *
	 * Required so Struts 2 OGNL/JSP can access the properties.
	 * ==========================================================
	 */

	public Bpcs getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(Bpcs searchObject) {
		this.searchObject = searchObject;
	}

	public List<Bpcs> getBpcsList() {
		return bpcsList;
	}

	public void setBpcsList(List<Bpcs> bpcsList) {
		this.bpcsList = bpcsList;
	}

	public Vector getBpcsErrorList() {
		return bpcsErrorList;
	}

	public void setBpcsErrorList(Vector bpcsErrorList) {

		this.bpcsErrorList = bpcsErrorList;
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

	public BpcsTran getAddNew() {
		return addNew;
	}

	public void setAddNew(BpcsTran addNew) {
		this.addNew = addNew;
	}

	public String getErrs() {

		if (errs == null) {
			errs = "";
		}

		return errs.trim();
	}

	public void setErrs(String errs) {
		this.errs = errs;
	}

	public String getCmd() {

		return cmd == null ? "" : cmd;
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

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {

		this.rowToCopy = rowToCopy;
	}
}