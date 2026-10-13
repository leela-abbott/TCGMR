package abbott.ai.tcgm17.action.bpcex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.BpcEx;
import abbott.ai.tcgm.entities.BpcExTran;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.helpers.BpcExMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class BpcExMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7332721783045126308L;
	
	private Map<String, Object> session;

	@Override
	public void withSession(Map<String, Object> arg0) {
		this.session = session;
	}

	// BpcExForm properties

	private BpcEx searchObject = new BpcEx();
	private List<BpcEx> bpcExList = new ArrayList<>();
	private List<BpcEx> bpcExErrorList = new ArrayList<>();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_BPC_EXC_DEF, DBConst.SORT_ASC);
	private BpcExTran addNew = new BpcExTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("BPCEX");
	private String errs = "";

	// TCGMForm
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

	// private ActionErrors errors = new ActionErrors();

	@Override
	public String execute() {

		HttpServletRequest request = ServletActionContext.getRequest();

		//HttpSession session = request.getSession();

		processCmd();

		if (TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(cmd)) {

			session.put("bpcExTranAdvFilter", this);

			reset();

			return "advancedFilter";
		}

		BpcExMngr bpcExMngr = new BpcExMngr();

		try {

			initModelAndDataset(this.getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			if ("on".equalsIgnoreCase(errs)) {

				bpcExList = bpcExErrorList;

				pagingFilter.setTotalRecordsInSet(bpcExErrorList.size());

				errs = "";

				addActionError(getText("error.list"));
			} else {

				pagingFilter.setTotalRecordsInSet(bpcExMngr.getCount(getUserToken(), searchObject));

				if (TCGMConstants.URL_PARM_VAL_EMPTY.equalsIgnoreCase(cmd)) {

					bpcExList = createEmptyBpcExRecs(this.getState(request).getCurrentModelIdString(),
							DBConst.DEF_DATASET_TABLE_ID);
				} else {

					bpcExList = bpcExMngr.getBpcEx(getUserToken(), searchObject, pagingFilter, sortObject);
				}

				bpcExErrorList = new ArrayList<>();
			}

			addNew.getBpcEx().setBegPeriod("1");

			addNew.getBpcEx().setEndPeriod("12");

			return SUCCESS;

		} catch (Exception ex) {

			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

			addActionError(ex.getMessage());

			return ERROR;
		}
	}

	public void reset() {
		// 5-24-05 Always clear Cmd & Error Msgs before initial page display
		this.setCmd("");
		addNew.getBpcEx().clearMsg();
		// 5-24-05 End of code for clear Cmd & Error Msgs before initial page display

		// ok to reset this as a new object. The values are available in fields on the
		// jsp page.
		this.setSearchObject(new BpcEx());

		// To clear the older values call setAddNew
		this.setAddNew(new BpcExTran());

		this.setPagingFilter(new PagingFilter());
		// loop through the collection of bpcExs and reset the selected property to
		// false
		// this can be done for all properties in the bpcEx.
		for (int i = 0; i < this.getBpcExList().size(); i++) {
			bpcExList.get(i).setSelected(false);
		}
		
		/*
		 * for (int i = 0; i < bpcRevList.size(); i++) {
		 * bpcRevList.get(i).setSelected(false); }
		 */
		
		// Reset the default focus field for the page
		this.setFocusField(DBConst.BPC_EX_DFT_FOCUS);

		if (addNew.getBegPeriod() == null || addNew.getBegPeriod().equals("")) {
			addNew.getBpcEx().setBegPeriod("1");
		}
		if (addNew.getEndPeriod() == null || addNew.getEndPeriod().equals("")) {
			addNew.getBpcEx().setEndPeriod("12");
		}

	}

	public long getBpcExListSize() {
		return bpcExList == null ? 0 : bpcExList.size();
	}

	public long getBpcExErrorListSize() {
		return bpcExErrorList == null ? 0 : bpcExErrorList.size();
	}

	public BpcEx getBpcExListItem(int index) {

		if (index >= 0 && index < bpcExList.size()) {

			return bpcExList.get(index);
		}

		return null;
	}

	public void setBpcExListItem(BpcEx bpcEx, int index) {

		bpcExList.set(index, bpcEx);
	}

	public void initModelAndDataset(String modelId, String datasetTableId) {
		this.getAddNew().getBpcEx().setModelId(modelId);
		this.getAddNew().getBpcEx().setDatasetTableId(datasetTableId);
		this.getSearchObject().setModelId(modelId);
		this.getSearchObject().setDatasetTableId(datasetTableId);
	}
	
	public int getRowToCopyInt() {
	    return Integer.parseInt(rowToCopy);
	}

	public void processCmd() {

		if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_NEXT_PAGE)) {
			this.getPagingFilter().setNextpage();
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_PREV_PAGE)) {
			this.getPagingFilter().setPrevPage();
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)
				|| this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER)) {
			this.getPagingFilter().setStartRecord(1);
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER)) {
			// Click on Filter clear should clear the entire form except Entry row if any
			BpcExTran bpcExTran = this.getAddNew();
			this.reset();
			this.setAddNew(bpcExTran);
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW)) {
			this.setAddNew(new BpcExTran());
//			Click on Entry row clear should load the empty AsrList as initial load.
			this.setCmd("");
			
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ROW)) {
			this.getAddNew().setBpcEx(this.bpcExList.get(this.getRowToCopyInt()));
			
			
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE)) {
			this.getPagingFilter().setDispSelectedRecordPage();
		}
		/*****************************************************************************************/
		/**
		 * The following logic implements cursor navigation according to the last
		 * command executed by the user. Based on the button clicked by the user, the
		 * cursor will be position on predetermined fields in order to improve
		 * transaction processing time.
		 */
		this.setFocusField(DBConst.BPC_EX_DFT_FOCUS);
		if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)
				|| this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER)) {
			// this.setFocusField("bpcExListItem[0].endAff");
			this.setFocusField("addNew.actionCode");
		} else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE)
				|| this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE)) {
			this.setFocusField("addNew.actionCode");
		}

	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public BpcEx getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(BpcEx searchObject) {
		this.searchObject = searchObject;
	}

	public List<BpcEx> getBpcExList() {
		return bpcExList;
	}

	public void setBpcExList(List<BpcEx> bpcExList) {
		this.bpcExList = bpcExList;
	}

	public List<BpcEx> getBpcExErrorList() {
		return bpcExErrorList;
	}

	public void setBpcExErrorList(List<BpcEx> bpcExErrorList) {
		this.bpcExErrorList = bpcExErrorList;
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

	public BpcExTran getAddNew() {
		return addNew;
	}

	public void setAddNew(BpcExTran addNew) {
		this.addNew = addNew;
	}

	public TCGMDataValidation getDataVal() {
		return dataVal;
	}

	public void setDataVal(TCGMDataValidation dataVal) {
		this.dataVal = dataVal;
	}

	public String getErrs() {
		return errs;
	}

	public void setErrs(String errs) {
		this.errs = errs;
	}

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
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

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
	}

}
