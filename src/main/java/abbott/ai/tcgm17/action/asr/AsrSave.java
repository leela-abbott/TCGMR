package abbott.ai.tcgm17.action.asr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.AsrTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AsrSave extends TCGMAction implements SessionAware {

	private static final Logger myLogger = LogManager.getLogger("AsrSave");

	public AsrSave() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1359108248411657526L;

	private Map<String, Object> session;

	// @StrutsParameter(depth = 3)
	private Asr searchObject = new Asr();
	private List<Asr> asrList = new ArrayList<>();
	private Vector<Asr> asrErrorList = new Vector<>();
	private PagingFilter pagingFilter = new PagingFilter();

	// @StrutsParameter(depth = 3)
	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);

	// @StrutsParameter(depth = 4)
	private AsrTran addNew = new AsrTran();

	private String errs = "";
	private String cmd = "";
	private String focusField = DBConst.ASR_DFT_FOCUS;
	private String rowToCopy = "";

	@Override
	public String execute() {

		myLogger.debug("Executing execute() method in AsrSave.");

		final String methodName = "execute";

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		/*
		 * If your TCGMAction base class maintains a legacy errors collection, clear it
		 * here. Otherwise remove this block.
		 */
//		if (this.errors != null) {
//			this.errors.clear();
//		}

		try {
			/*
			 * In Struts2, the action itself replaces the Struts1 AsrForm. Therefore,
			 * checking "form == null" is no longer required.
			 */
			if (!isSessionValid(request)) {
				return TCGMConstants.FORWARD_INVALID;
			}

			if (!isModelSelected(request)) {
				return TCGMConstants.G_FORWARD_SELECT_MODEL;
			}

			UserToken userToken = getUserToken(request);

			/*
			 * Ensure action properties are initialized before processing.
			 */
			if (searchObject == null) {
				searchObject = new Asr();
			}

			if (addNew == null) {
				addNew = new AsrTran();
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

			if (cmd == null) {
				cmd = "";
			}

			/*
			 * Replaces:
			 *
			 * asrForm.processCmd(mapping, request);
			 */
			processCmd();

			boolean duplicate = true;

			AsrMngr asrMngr = new AsrMngr();

			this.getAddNew().setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

			/*
			 * Process the requested command.
			 */
			if (TCGMConstants.URL_PARM_VAL_SAVE.equals(getCmd())) {

				duplicate = asrMngr.addNewAsrTran(userToken, getAddNew());

			} else if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(getCmd())) {

				// duplicate = asrMngr.addSelectedAsrToTrans(userToken, getAsrList(),
				// TCGMConstants.ACT_CD_CHG);
				duplicate = asrMngr.addSelectedAsrToTrans(userToken, new Vector<>(getAsrList()),
						TCGMConstants.ACT_CD_CHG);

			} else if (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(getCmd())) {

				setAsrErrorList(asrMngr.massUpdate(userToken, getSearchObject(), getAddNew()));

			} else if (!getCmd().trim().isEmpty()) {

				throw new TCGMException(getClass().getName(), methodName, "Invalid command in action: " + getCmd());
			}

			/*
			 * Original condition:
			 *
			 * duplicate && asrErrorList.size() == 0 || duplicate && SAVE_SELECTED
			 *
			 * Parentheses are added to make the intended logic explicit.
			 */
			boolean operationSuccessful = duplicate
					&& (getAsrErrorList().isEmpty() || TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(getCmd()));

			if (operationSuccessful) {

				/*
				 * For SAVE_SELECTED, retain only records having an error message. These records
				 * remain on the screen so the user can correct and save them again.
				 */
				if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(getCmd()) && !getAsrErrorList().isEmpty()) {

					Vector<Asr> remainingErrorRecords = new Vector<Asr>();

					for (Asr asr : getAsrErrorList()) {

						if (asr != null && asr.getMsg() != null && !asr.getMsg().trim().isEmpty()) {
							remainingErrorRecords.add(asr);
						}
					}

					setAsrErrorList(remainingErrorRecords);

					/*
					 * Use a new Vector so asrList and asrErrorList do not unexpectedly share the
					 * same mutable collection.
					 */
					setAsrList(new Vector<Asr>(remainingErrorRecords));
				}

				/*
				 * Struts1 used an ActionError for the success message. Struts2 has a separate
				 * action-message collection.
				 */
				addActionMessage(getText("success.asrtran.insert"));

			} else {

				addActionError(getText("error.duplicate.looping"));
			}

			String currentModelId = getState(request).getCurrentModelIdString();

			/*
			 * Populate model and dataset IDs in addNew and searchObject.
			 */
			initModelAndDataset(currentModelId, DBConst.DEF_DATASET_TABLE_ID);

			/*
			 * During SAVE_SELECTED, display the remaining records that still contain
			 * validation or processing errors.
			 */
			if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(getCmd()) && !getAsrErrorList().isEmpty()) {

				getPagingFilter().setTotalRecordsInSet(getAsrErrorList().size());

			} else {

				getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(userToken, getSearchObject()));
			}

			/*
			 * Reload records only when processing succeeded and no error records remain.
			 */
			if (duplicate && getAsrErrorList().isEmpty()) {

				boolean loadEmptyRecords = false;

				if (TCGMConstants.URL_PARM_VAL_SAVE.equalsIgnoreCase(getCmd())) {

					if (getAsrList().isEmpty()) {

						loadEmptyRecords = true;

					} else {

						Asr firstAsr = getAsrListItem(0);

						loadEmptyRecords = firstAsr == null || firstAsr.getProductOrigin() == null
								|| firstAsr.getProductOrigin().trim().isEmpty();
					}
				}

				if (loadEmptyRecords) {

					setAsrList(createEmptyAsrRecs(currentModelId, DBConst.DEF_DATASET_TABLE_ID));

				} else {

					setAsrList(asrMngr.getAsr(userToken, getSearchObject(), getPagingFilter(), getSortObject()));
				}
			}

			return TCGMConstants.FORWARD_SUCCESS;

		} catch (TCGMDuplicateItemException ex) {

			myLogger.error("Duplicate ASR record while executing AsrSave.", ex);

			addActionError(getText("error.duplicate"));

			try {
				UserToken userToken = getUserToken(request);
				AsrMngr asrMngr = new AsrMngr();

				String currentModelId = getState(request).getCurrentModelIdString();

				initModelAndDataset(currentModelId, DBConst.DEF_DATASET_TABLE_ID);

				getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(userToken, getSearchObject()));

				setAsrList(asrMngr.getAsr(userToken, getSearchObject(), getPagingFilter(), getSortObject()));

				return TCGMConstants.FORWARD_SUCCESS;

			} catch (TCGMException reloadException) {

				myLogger.error("Unable to reload ASR records after duplicate item error.", reloadException);

				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, reloadException);

				return TCGMConstants.G_FORWARD_EXCEPTION;
			}

		} catch (TCGMException ex) {

			myLogger.error("TCGM exception while executing AsrSave.", ex);

			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

			return TCGMConstants.G_FORWARD_EXCEPTION;

		} catch (Exception ex) {

			myLogger.error("Unexpected exception while executing AsrSave.", ex);

			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

			return TCGMConstants.G_FORWARD_EXCEPTION;
		}
	}

	// processCmd
	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {
			pagingFilter.setNextpage();
		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {
			pagingFilter.setPrevPage();
		} else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {
			pagingFilter.setStartRecord(1);
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

			AsrTran savedAddNew = addNew;

			reset();

			setAddNew(savedAddNew);
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {
			setAddNew(new AsrTran());
			setCmd("");
		} else if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(cmd)) {

			int row = getRowToCopyInt();

			if (row >= 0 && row < asrList.size()) {
				addNew.setAsr(asrList.get(row));
			}
		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {
			pagingFilter.setDispSelectedRecordPage();
		}

		setFocusField(DBConst.ASR_DFT_FOCUS);

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)
				|| TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

			setFocusField("addNew.actionCode");
		}
	}

	// reset

	public void reset() {

		setCmd("");

		if (addNew != null && addNew.getAsr() != null) {
			addNew.getAsr().clearMsg();
		}

		setSearchObject(new Asr());
		setAddNew(new AsrTran());

		if (asrList != null) {
			for (Asr asr : asrList) {
				if (asr != null) {
					asr.setSelected(false);
				}
			}
		}

		setFocusField(DBConst.ASR_DFT_FOCUS);
	}

	// Helper method

	public long getAsrListSize() {
		return asrList == null ? 0 : asrList.size();
	}

	public long getAsrErrorListSize() {
		return asrErrorList == null ? 0 : asrErrorList.size();
	}

	public Asr getAsrListItem(int index) {
		if (index >= 0 && index < asrList.size()) {
			return asrList.get(index);
		}
		return null;
	}

	public void setAsrListItem(Asr asr, int index) {
		while (asrList.size() <= index) {
			asrList.add(new Asr());
		}
		asrList.set(index, asr);
	}

	public int getRowToCopyInt() {
		try {
			return Integer.parseInt(rowToCopy);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public void validate() {

		if (cmd == null) {
			cmd = "";
		}

		// asrTran.getAsr().getRptProduct().getInvCode()
		  if(this.getAddNew().getAsr().getRptProduct().getInvCode().isEmpty() 
				  || null == this.getAddNew().getAsr().getRptProduct().getInvCode())   {
		  //addActionError(getText("error.asr.form.missing"));
		  //addFieldError("rptUser.userid", getText("error.userMaint.required.userid"));
		    this.getAddNew().getAsr().appendMsg("Rpt Inv Code Cannot be blank");
		  }
		  if(this.getAddNew().getAsr().getSupProduct().getInvCode().isEmpty() 
				  || null == this.getAddNew().getAsr().getSupProduct().getInvCode())   {
		  //addActionError(getText("error.asr.form.missing"));
		  //addFieldError("rptUser.userid", getText("error.userMaint.required.userid"));
		    this.getAddNew().getAsr().appendMsg("Sup Inv Code Cannot be blank");
		  }
		  
		  //save selected
		 
		

		if (TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd) && addNew == null) {

			addActionError(getText("error.asr.form.missing"));
		}
		
		if(!this.getAddNew().getAsr().getMsg().isEmpty()
				&& !cmd.equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)) {
			addFieldError("Data Field Error", "Data Field Error");
		}
	}

	//

	public void initModelAndDataset(String modelId, String datasetTableId) {

		addNew.getAsr().setModelId(modelId);
		addNew.getAsr().setDatasetTableId(datasetTableId);

		searchObject.setModelId(modelId);
		searchObject.setDatasetTableId(datasetTableId);
	}

	@StrutsParameter(depth = 3)
	public Asr getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(Asr searchObject) {
		this.searchObject = searchObject;
	}

	@StrutsParameter(depth = 3)
	public List<Asr> getAsrList() {
		return asrList;
	}

	public void setAsrList(List<Asr> asrList) {
		this.asrList = asrList;
	}

	public Vector<Asr> getAsrErrorList() {
		return asrErrorList;
	}

	public void setAsrErrorList(Vector<Asr> asrErrorList) {
		this.asrErrorList = asrErrorList;
	}

	@StrutsParameter(depth = 2)
	public PagingFilter getPagingFilter() {
		return pagingFilter;
	}

	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter;
	}

	@StrutsParameter(depth = 2)
	public Sort getSortObject() {
		return sortObject;
	}

	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject;
	}

	@StrutsParameter(depth = 4)
	public AsrTran getAddNew() {
		return addNew;
	}

	public void setAddNew(AsrTran addNew) {
		this.addNew = addNew;
	}

	public String getErrs() {
		return errs;
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

	@Override
	public void withSession(Map<String, Object> arg0) {
		this.session = session;
	}

}
