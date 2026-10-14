package abbott.ai.tcgm17.action.ratedata;

import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.RateData;
import abbott.ai.tcgm.entities.RateDataTran;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.RateDataMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class RateDataMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private RateData searchObject = new RateData();
	private Vector rateDataList = new Vector();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = DBConst.DEF_SORT_RATE_DATA;
	private RateDataTran addNew = new RateDataTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("RATEDATA");

	// TCGM Form
	private String cmd;
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

	// Action Properties

	@Override
	public void withSession(Map<String, Object> session) {

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

			RateDataMngr rateDataMngr = new RateDataMngr();

			initModelAndDataset(this.getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			pagingFilter.setTotalRecordsInSet(rateDataMngr.getCount(getUserToken(), searchObject));

			rateDataList = rateDataMngr.getRateData(getUserToken(), searchObject, pagingFilter, sortObject);

			addNew.getRateData().setBegPeriod("1");
			addNew.getRateData().setEndPeriod("12");

			return SUCCESS;

		} catch (TCGMException ex) {

			logger.error(ex.toString(), ex);
			addActionError(getText("exception.ratedata.maint"));

			return ERROR;
		}
	}
	
	public int getRowToCopyInt() {
	    return Integer.parseInt(rowToCopy);
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

			reset();

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

			addNew = new RateDataTran();

		} else if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(cmd)) {

			addNew.setRateData(getRateDataList(getRowToCopyInt()));

		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {

			pagingFilter.setDispSelectedRecordPage();
		}

		setFocusField(DBConst.RATE_DATA_DFT_FOCUS);

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)) {

			setFocusField("rateDataListItem[0].curCode");

		} else if (TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

			setFocusField("addNew.actionCode");
		}
	}

	public long getRateDataListSize() {
		return getRateDataList().size();
	}

	public RateData getRateDataList(int index) {

		if (index >= 0 && index < rateDataList.size()) {
			return (RateData) rateDataList.elementAt(index);
		}

		return null;
	}

	public RateData getRateDataListItem(int index) {
		return getRateDataList(index);
	}

	public void setRateDataListItem(RateData rateData, int index) {

		rateDataList.setElementAt(rateData, index);
	}

	public void setRateDataList(RateData rateData, int index) {

		rateDataList.setElementAt(rateData, index);
	}

	public void initModelAndDataset(String modelId, String datasetTableId) {

		addNew.getRateData().setModelId(modelId);
		addNew.getRateData().setDatasetTableId(datasetTableId);

		searchObject.setModelId(modelId);
		searchObject.setDatasetTableId(datasetTableId);
	}

	public void initDataset(String datasetTableId) {

		addNew.getRateData().setDatasetTableId(datasetTableId);

		searchObject.setDatasetTableId(datasetTableId);
	}

	public void reset() {

		setCmd("");

		if (addNew != null && addNew.getRateData() != null) {

			addNew.getRateData().clearMsg();
		}

		searchObject = new RateData();

		addNew = new RateDataTran();

		pagingFilter = new PagingFilter();

		for (int i = 0; i < getRateDataList().size(); i++) {

			getRateDataList(i).setSelected(false);
		}

		setFocusField(DBConst.RATE_DATA_DFT_FOCUS);

		if (addNew.getBegPeriod() == null || "".equals(addNew.getBegPeriod())) {

			addNew.getRateData().setBegPeriod("1");
		}

		if (addNew.getEndPeriod() == null || "".equals(addNew.getEndPeriod())) {

			addNew.getRateData().setEndPeriod("12");
		}
	}

	/*
	 * // Validate
	 * 
	 * @Override public void validate() {
	 * 
	 * TCGMDataValidation dataVal = new TCGMDataValidation("RATEDATA");
	 * 
	 * addNew.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
	 * 
	 * if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {
	 * 
	 * for (int i = 0; i < getRateDataListSize(); i++) {
	 * 
	 * if (getRateDataList(i).isSelected()) {
	 * 
	 * dataVal.validateRateData(getRateDataList(i), this, false); } } } else if
	 * (TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd)) {
	 * 
	 * dataVal.validateRateDataTran(addNew, this, false); } else if
	 * (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {
	 * 
	 * dataVal.validateRateDataTran(addNew, this, true); } }
	 */

	public RateData getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(RateData searchObject) {
		this.searchObject = searchObject;
	}

	public Vector getRateDataList() {
		return rateDataList;
	}

	public void setRateDataList(Vector rateDataList) {
		this.rateDataList = rateDataList;
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

	public RateDataTran getAddNew() {
		return addNew;
	}

	public void setAddNew(RateDataTran addNew) {
		this.addNew = addNew;
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

}
