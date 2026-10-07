package abbott.ai.tcgm17.action.asr;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.AsrTran;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;

/**
 * <p>
 * Title: TCGM
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Abbott Laboratories
 * </p>
 * 
 * @author David Fields
 * @version 1.0
 */
public class AsrTranMaint extends TCGMAction {
	private static final long serialVersionUID = 1L;
	private static Logger myLogger = LogManager.getLogger("AsrTranMaint");

	// --- AsrTranForm attributes inlined ---
	private AsrTran searchObject = new AsrTran();
	private List<AsrTran> asrTranList = new ArrayList<>();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
	private AsrTran addNew = new AsrTran();
	private String userSelected = null;

	// --- TCGMForm attributes inlined ---
	private String cmd = "";
	private String focusField = DBConst.DFT_TRAN_FOCUS;
	private String rowToCopy = "";
	private String modelSelected = "";
	private Vector models = new Vector();
	private String showModels = "";

	private final TCGMDataValidation dataVal = new TCGMDataValidation("ASR");

	public AsrTranMaint() {
		super();
	}

	public String execute() throws Exception {
		myLogger.debug("Executing execute() method in AsrTranMaint.");

		this.clearActionErrors();

		String strDeleteMessage = (String) request.getSession().getAttribute("deleteMessage");
		if (strDeleteMessage != null && !strDeleteMessage.trim().equalsIgnoreCase("")) {
			addActionMessage(getText("success.asrtran.delete"));
			request.getSession().removeAttribute("deleteMessage");
		}

		if (!this.isSessionValid(request)) {
			return this.getForward();
		}

		if (!this.isModelSelected(request)) {
			return this.getForward();
		}

		UserToken userToken = this.getUserToken(request);

		processCmd();

		if (cmd != null && cmd.equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_ADV_FILTER)) {
			request.getSession().setAttribute("asrTranAdvFilter", buildSnapshot());
			reset();
			this.setForward(TCGMConstants.FORWARD_ADVANCEDFILTER);
			return this.getForward();
		}

		AsrMngr asrMngr = new AsrMngr();
		ModelMngr modelMngr = new ModelMngr();

		try {
			initModelAndDataset(this.getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			if (userSelected == null) {
				if (searchObject.getAsr().getCreateLog().getUserName().equals("")) {
					searchObject.getAsr().getCreateLog().setUserName(userToken.getUserid());
					userSelected = userToken.getUserid();
				}
			} else if (!userSelected.equalsIgnoreCase("ALL")) {
				searchObject.getAsr().getCreateLog().setUserName(userSelected);
			}

			pagingFilter.setTotalRecordsInSet(asrMngr.getCount(userToken, searchObject));
			asrTranList = new ArrayList<>(asrMngr.getAsrTran(userToken, searchObject, pagingFilter, sortObject));

			FactorModel fm = new FactorModel();
			fm.setStatus(TCGMModel.Status.OPEN);
			models = modelMngr.getModels(userToken, fm);

			searchObject.getAsr().getCreateLog().setUserName(userToken.getUserid());
			modelSelected = TCGMConstants.NONE;

			this.setForward(TCGMConstants.FORWARD_SUCCESS);
		} catch (TCGMException ex) {
			this.logger.error(ex.toString(), ex);
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}

		this.logger.debug(className + " Forward: " + this.getForward());
		return this.getForward();
	}

	// --- Form methods ---

	public void processCmd() {
		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {
			pagingFilter.setNextpage();
		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {
			pagingFilter.setPrevPage();
		} else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {
			pagingFilter.setStartRecord(1);
		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {
			pagingFilter.setDispSelectedRecordPage();
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {
			searchObject = new AsrTran();
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {
			addNew = new AsrTran();
		}

		focusField = DBConst.DFT_TRAN_FOCUS;

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)
				|| TCGMConstants.URL_PARM_VAL_ADD.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {
			focusField = "addNew.actionCode";
		}
	}

	public void reset() {
		cmd = "";
		if (addNew != null && addNew.getAsr() != null) {
			addNew.getAsr().clearMsg();
		}
		searchObject = new AsrTran();
		addNew = new AsrTran();
		pagingFilter = new PagingFilter();
		for (AsrTran t : asrTranList) {
			if (t != null && t.getAsr() != null) {
				t.getAsr().setSelected(false);
				t.getAsr().setMsg("");
			}
		}
	}

	// TODO Calling from where.

//    public void validate() {
//        addNew.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
//
//        if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {
//            for (AsrTran t : asrTranList) {
//                if (t.getAsr().isSelected()) {
//                    dataVal.validateAsrTran(t, null, false);
//                }
//            }
//        } else if (TCGMConstants.URL_PARM_VAL_ADD.equals(cmd)) {
//            dataVal.validateAsrTran(addNew, null, false);
//        } else if (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {
//            dataVal.validateAsrTran(addNew, null, true);
//        } else if (TCGMConstants.URL_PARM_VAL_COPY_ALL.equals(cmd)
//                || TCGMConstants.URL_PARM_VAL_COPY_SELECTED.equals(cmd)) {
//            dataVal.validateModelCopy(modelSelected,
//                this.getState(request), null);
//        }
//    }

	public void initModelAndDataset(String modelId, String datasetTableId) {
		addNew.getAsr().setModelId(modelId);
		addNew.getAsr().setDatasetTableId(datasetTableId);
		searchObject.getAsr().setModelId(modelId);
		searchObject.getAsr().setDatasetTableId(datasetTableId);
	}

	private abbott.ai.tcgm.action.form.AsrTranForm buildSnapshot() {
		abbott.ai.tcgm.action.form.AsrTranForm snap = new abbott.ai.tcgm.action.form.AsrTranForm();
		snap.setSearchObject(searchObject);
		snap.setAsrTranList(new Vector<>(asrTranList));
		snap.setPagingFilter(pagingFilter);
		snap.setSortObject(sortObject);
		snap.setAddNew(addNew);
		snap.setUserSelected(userSelected);
		snap.setCmd(cmd);
		snap.setFocusField(focusField);
		snap.setRowToCopy(rowToCopy);
		snap.setModelSelected(modelSelected);
		snap.setModels(models);
		return snap;
	}

	public long getAsrTranListSize() {
		return asrTranList == null ? 0 : asrTranList.size();
	}

	/**
	 * List-level getter for OGNL: asrTranListItem != null &&
	 * !asrTranListItem.isEmpty()
	 */
	public List<AsrTran> getAsrTranListItem() {
		return asrTranList;
	}

	@StrutsParameter(depth = 2)
	public void setAsrTranListItem(List<AsrTran> asrTranListItem) {
		this.asrTranList = asrTranListItem;
	}

	/** Indexed getter for Struts2 binding: asrTranListItem[n].xxx */
	public AsrTran getAsrTranListItem(int index) {
		if (asrTranList == null)
			return null;
		if (index >= 0 && index < asrTranList.size())
			return asrTranList.get(index);
		return null;
	}

	/** Indexed setter for Struts2 binding: asrTranListItem[n].xxx */
	public void setAsrTranListItem(int index, AsrTran asrTran) {
		if (asrTranList == null)
			asrTranList = new ArrayList<>();
		while (asrTranList.size() <= index)
			asrTranList.add(new AsrTran());
		asrTranList.set(index, asrTran);
	}

	public int getRowToCopyInt() {
		try {
			return Integer.parseInt(rowToCopy);
		} catch (Exception e) {
			return -1;
		}
	}

	// --- Getters & Setters with @StrutsParameter ---

	public AsrTran getSearchObject() {
		return searchObject;
	}

	@StrutsParameter(depth = 3)
	public void setSearchObject(AsrTran searchObject) {
		this.searchObject = searchObject;
	}

	public List<AsrTran> getAsrTranList() {
		return asrTranList;
	}

	@StrutsParameter(depth = 2)
	public void setAsrTranList(List<AsrTran> asrTranList) {
		this.asrTranList = asrTranList;
	}

	public PagingFilter getPagingFilter() {
		return pagingFilter;
	}

	@StrutsParameter(depth = 1)
	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter;
	}

	public Sort getSortObject() {
		return sortObject;
	}

	@StrutsParameter(depth = 1)
	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject;
	}

	public AsrTran getAddNew() {
		return addNew;
	}

	@StrutsParameter(depth = 3)
	public void setAddNew(AsrTran addNew) {
		this.addNew = addNew;
	}

	public String getUserSelected() {
		return userSelected;
	}

	@StrutsParameter
	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}

	public String getCmd() {
		return cmd == null ? "" : cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getFocusField() {
		return focusField == null ? "" : focusField;
	}

	@StrutsParameter
	public void setFocusField(String focusField) {
		this.focusField = focusField;
	}

	public String getRowToCopy() {
		return rowToCopy == null ? "" : rowToCopy;
	}

	@StrutsParameter
	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
	}

	public String getModelSelected() {
		return modelSelected == null ? "" : modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public Vector getModels() {
		return models == null ? new Vector() : models;
	}

	@StrutsParameter(depth = 1)
	public void setModels(Vector models) {
		this.models = models;
	}

	public String getShowModels() {
		return showModels == null ? "" : showModels;
	}

	@StrutsParameter
	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u =
			(abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}
}
