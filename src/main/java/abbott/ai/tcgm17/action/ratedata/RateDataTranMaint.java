package abbott.ai.tcgm17.action.ratedata;

import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.RateDataTran;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.RateDataMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class RateDataTranMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2180241312902914021L;

	private Map<String, Object> session;
	// Action Properties
	private RateDataTran searchObject = new RateDataTran();
	private Vector rateDataTranList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = DBConst.DEF_SORT_RATE_DATA_TRAN;
	private RateDataTran addNew = new RateDataTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("RATEDATA");
	private String userSelected = null;

	// TCGMForm
	private String cmd = "";
	private String focusField;
	private String returnLocation;
	private String rowToCopy;
	private String modelSelected;
	private Vector models = new Vector();
	private Vector closedModels = new Vector();
	private Option[] monthListNumber;
	private Option[] twoDigitMonthListNumber;
	private Option[] twoDigitMonthListNumberWithLabel;
	private Option[] periodMonthList;
	private Option[] yearList;
	private Option[] exportSystemList;

	@Override
	public void withSession(Map<String, Object> arg0) {
		this.session = session;
	}

	// execute
	@Override
	public String execute() {

		try {

			if (!isRateSetSelected()) {
				return INPUT;
			}

			processCmd();

			UserToken userToken = getUserToken();

			RateDataMngr rateDataMngr = new RateDataMngr();

			ModelMngr modelMngr = new ModelMngr();

			if (userSelected == null) {

				if ("".equals(searchObject.getRateData().getCreateLog().getUserName())) {

					searchObject.getRateData().getCreateLog().setUserName(userToken.getUserid());

					userSelected = userToken.getUserid();
				}

			} else if (!"ALL".equalsIgnoreCase(userSelected)) {

				searchObject.getRateData().getCreateLog().setUserName(userSelected);
			}

			searchObject.getRateData().setDatasetTableIdInt(getState().getCurRateSetTableId());

			initModelAndDataset(getState().getCurrentModelIdString(), Integer.toString(getState().getCurRateSetTableId()));

			addNew.getRateData().setDatasetTableIdInt(getState().getCurRateSetTableId());

			pagingFilter.setTotalRecordsInSet(rateDataMngr.getCount(userToken, searchObject));

			addNew.getRateData().setBegPeriod("1");
			addNew.getRateData().setEndPeriod("12");

			rateDataTranList = rateDataMngr.getRateDataTran(userToken, searchObject, pagingFilter, sortObject);

			return SUCCESS;

		} catch (TCGMException ex) {

			logger.error(ex.toString(), ex);
			addActionError(ex.getMessage());

			return ERROR;
		}
	}

	// processCmd
	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {

			pagingFilter.setNextpage();

		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {

			pagingFilter.setPrevPage();

		} else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)) {

			pagingFilter.setStartRecord(1);

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

			searchObject = new RateDataTran();

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

			addNew = new RateDataTran();

		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {

			pagingFilter.setDispSelectedRecordPage();
		}

		setFocusField(DBConst.DFT_TRAN_FOCUS);

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)) {

			setFocusField("rateDataTranListItem[0].actionCode");

		} else if (TCGMConstants.URL_PARM_VAL_ADD.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

			setFocusField("addNew.actionCode");
		}
	}

	// reset
	public void reset() {

		setCmd("");

		if (addNew != null && addNew.getRateData() != null) {

			addNew.getRateData().clearMsg();
		}

		searchObject = new RateDataTran();

		addNew = new RateDataTran();

		pagingFilter = new PagingFilter();

		for (int i = 0; i < getRateDataTranList().size(); i++) {

			getRateDataTranList(i).getRateData().setSelected(false);

			getRateDataTranList(i).getRateData().setMsg("");
		}

		setFocusField(DBConst.RATE_DATA_DFT_FOCUS);
	}

	public void initModelAndDataset(String modelId, String datasetTableId) {

		addNew.getRateData().setDatasetTableId(datasetTableId);

		searchObject.getRateData().setDatasetTableId(datasetTableId);
	}

	public void initDataset(String datasetTableId) {

		addNew.getRateData().setDatasetTableId(datasetTableId);

		searchObject.getRateData().setDatasetTableId(datasetTableId);
	}

	public long getRateDataTranListSize() {
		return getRateDataTranList().size();
	}

	public RateDataTran getRateDataTranList(int index) {

		RateDataTran tran = null;

		if (index >= 0 && index < getRateDataTranList().size()) {

			tran = (RateDataTran) rateDataTranList.elementAt(index);
		}

		return tran;
	}

	public RateDataTran getRateDataTranListItem(int index) {

		return getRateDataTranList(index);
	}

	public void setRateDataTranListItem(RateDataTran tran, int index) {

		rateDataTranList.setElementAt(tran, index);
	}

	public void setRateDataTranList(RateDataTran tran, int index) {

		rateDataTranList.setElementAt(tran, index);
	}

	public RateDataTran getAddNew() {

		RateDataTran rdTran = addNew;

		if (rdTran != null) {

			if (userSelected != null && !"ALL".equalsIgnoreCase(userSelected)) {

				rdTran.setUserName(userSelected);
			}
		}

		return rdTran;
	}

	public String getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}

	public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u = (abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}

	public RateDataTran getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(RateDataTran searchObject) {
		this.searchObject = searchObject;
	}

	public Vector getRateDataTranList() {
		return rateDataTranList;
	}

	public void setRateDataTranList(Vector rateDataTranList) {
		this.rateDataTranList = rateDataTranList;
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

	public TCGMDataValidation getDataVal() {
		return dataVal;
	}

	public void setDataVal(TCGMDataValidation dataVal) {
		this.dataVal = dataVal;
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

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
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

	public Option[] getMonthListNumber() {
		return monthListNumber;
	}

	public void setMonthListNumber(Option[] monthListNumber) {
		this.monthListNumber = monthListNumber;
	}

	public Option[] getTwoDigitMonthListNumber() {
		return twoDigitMonthListNumber;
	}

	public void setTwoDigitMonthListNumber(Option[] twoDigitMonthListNumber) {
		this.twoDigitMonthListNumber = twoDigitMonthListNumber;
	}

	public Option[] getTwoDigitMonthListNumberWithLabel() {
		return twoDigitMonthListNumberWithLabel;
	}

	public void setTwoDigitMonthListNumberWithLabel(Option[] twoDigitMonthListNumberWithLabel) {
		this.twoDigitMonthListNumberWithLabel = twoDigitMonthListNumberWithLabel;
	}

	public Option[] getPeriodMonthList() {
		return periodMonthList;
	}

	public void setPeriodMonthList(Option[] periodMonthList) {
		this.periodMonthList = periodMonthList;
	}

	public Option[] getYearList() {
		return yearList;
	}

	public void setYearList(Option[] yearList) {
		this.yearList = yearList;
	}

	public Option[] getExportSystemList() {
		return exportSystemList;
	}

	public void setExportSystemList(Option[] exportSystemList) {
		this.exportSystemList = exportSystemList;
	}

	public void setAddNew(RateDataTran addNew) {
		this.addNew = addNew;
	}

	// validate
	/*
	 * @Override public void validate() {
	 * 
	 * addNew.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
	 * 
	 * if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {
	 * 
	 * for (int i = 0; i < getRateDataTranListSize(); i++) {
	 * 
	 * if (getRateDataTranList(i).getRateData().isSelected()) {
	 * 
	 * dataVal.validateRateDataTran(getRateDataTranList(i), this, false); } } } else
	 * if (TCGMConstants.URL_PARM_VAL_ADD.equals(cmd)) {
	 * 
	 * dataVal.validateRateDataTran(addNew, this, false); } else if
	 * (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {
	 * 
	 * dataVal.validateRateDataTran(addNew, this, true); } }
	 */
	
	//Getter and setter
	
	

}
