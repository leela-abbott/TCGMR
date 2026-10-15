package abbott.ai.tcgm17.action.asr;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.helpers.AsrUsageMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class AsrUsageMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5536540739732333707L;
	private Map<String, Object> session;

	// Form Properties
	// TCGMForm properties
	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private String rowToCopy = "";
	private String showModels = "";

	private Vector models = new Vector();
	private Vector closedModels = new Vector();

	// AsrUsageForm properties
	private String selAmountType;
	private String selUpdateType;

	private ASRUsage searchObject = new ASRUsage();

	private Vector asrList = new Vector();
	private Vector asrErrorList = new Vector();

	private PagingFilter pagingFilter = new PagingFilter();

	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);

	private ArrayList supAffList = new ArrayList();

	private String supAffSelected;
	private String cycle = "";

	private File theFile;
	private String theFileFileName;
	private String theFileContentType;

	private TCGMDataValidation dataVal = new TCGMDataValidation("ASRUSAGE");
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,RPT_AFF,RPT_INV_CD, "
			+ "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, "
			+ "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,PROD_ORIGIN,SUP_KEY,USAGE_FACTOR,ID,CYCLE_ID, " + "CREATE_DATETIME "
			+ "FROM ";

	// private ActionMessages errors = new ActionMessages();

	@Override
	public void withSession(Map<String, Object> arg0) {

		this.session = session;
	}

	// execute
	@Override
	public String execute() {

		try {

			processCmd();

			UserToken userToken = getUserToken();

			AsrUsageMngr asrMngr = new AsrUsageMngr();
			ModelMngr modelMngr = new ModelMngr();

			supAffList = asrMngr.getSupAff(userToken);

			searchObject.setModelId(DBConst.DEF_MODEL_ID);
			searchObject.setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);

			if (supAffSelected == null) {

				pagingFilter.setTotalRecordsInSet(asrMngr.getCount(userToken, searchObject));

				asrList = asrMngr.getAsr(userToken, searchObject, pagingFilter, sortObject);
			} else {

				if ("-1".equals(supAffSelected)) {

					pagingFilter.setTotalRecordsInSet(asrMngr.getCount(userToken, searchObject));

					asrList = asrMngr.getAsr(userToken, searchObject, pagingFilter, sortObject);
				} else if (pagingFilter.getDispNextPage() || pagingFilter.getDispPrevPage()) {

					asrDataFetchAfterDelete();
				}
			}

			FactorModel fm = new FactorModel();
			fm.setStatus(TCGMModel.Status.OPEN);

			models = modelMngr.getModels(userToken, fm);

			modelSelected = TCGMConstants.NONE;

			return SUCCESS;

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			addActionError(e.getMessage());

			return ERROR;
		}
	}

	// processCmd
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

	// reset
	public void reset() {

		searchObject = new ASRUsage();

		pagingFilter = new PagingFilter();

		supAffSelected = null;

		for (int i = 0; i < asrList.size(); i++) {

			((ASRUsage) asrList.get(i)).setSelected(false);
		}
	}

	public void initModelAndDataset(String modelId, String datasetTableId) {

		searchObject.setModelId(modelId);
		searchObject.setDatasetTableId(datasetTableId);
	}

	public boolean asrDataFetchAfterDelete() {

		boolean resultFlag = false;

		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String cycleId = supAffSelected.substring(0, supAffSelected.indexOf("::"));

			String aff = supAffSelected.substring(supAffSelected.indexOf("::") + 2, supAffSelected.lastIndexOf("::"));

			String dateFromSuff = supAffSelected.substring(supAffSelected.lastIndexOf("::") + 2);

			String sql = "select count(*) as NUMBER_COUNT " + "from TCGM.AFF_ASR_USAGE_FACTOR_FILE b "
					+ "where b.RPT_AFF='" + aff + "'" + " and b.CYCLE_ID='" + cycleId + "'"
					+ " and trunc(b.CREATE_DATETIME)=" + "to_date('" + dateFromSuff + "','yyyy/mm/dd')";

			String updatedSql = MIDDLE_SELECT_START + "TCGM.AFF_ASR_USAGE_FACTOR_FILE b " + "where b.RPT_AFF='" + aff
					+ "'" + " and b.CYCLE_ID='" + cycleId + "'" + " and trunc(b.CREATE_DATETIME)=" + "to_date('"
					+ dateFromSuff + "','yyyy/mm/dd')";

			connection = SQLUtil.openConnection();

			statement = connection.createStatement();

			resultSet = statement.executeQuery(sql);

			long count = 0;

			while (resultSet.next()) {

				count = resultSet.getLong("NUMBER_COUNT");
			}

			resultFlag = count > 0;

			pagingFilter.setTotalRecordsInSet(count);

			AsrUsageMngr asrMngr = new AsrUsageMngr();

			asrList = asrMngr.getAsrDataForSuffAffSelected(getUserToken(), searchObject, pagingFilter, sortObject,
					updatedSql, true);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			addActionError(e.getMessage());
		} finally {

			SQLUtil.closeResultSet(resultSet);
			SQLUtil.closeConnection(connection);
		}

		return resultFlag;
	}

	public long getAsrListSize() {
		return asrList.size();
	}

	public long getAffBpcListSize() {
		return asrList.size();
	}

	public long getAsrErrorListSize() {

		return asrErrorList == null ? 0 : asrErrorList.size();
	}

	public ASRUsage getAffASRList(int index) {

		if (index >= 0 && index < asrList.size()) {
			return (ASRUsage) asrList.get(index);
		}

		return null;
	}

	public ASRUsage getAffBpcListItem(int index) {

		if (index >= 0 && index < asrList.size()) {
			return (ASRUsage) asrList.get(index);
		}

		return null;
	}

	public void setAffBpcListItem(ASRUsage obj, int index) {

		asrList.set(index, obj);
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
	public ASRUsage getSearchObject() {
		return searchObject;
	}

	
	public void setSearchObject(ASRUsage searchObject) {
		this.searchObject = searchObject;
	}

	public Vector getAsrList() {
		return asrList;
	}

	public void setAsrList(Vector asrList) {
		this.asrList = asrList;
	}

	public Vector getAsrErrorList() {
		return asrErrorList;
	}

	public void setAsrErrorList(Vector asrErrorList) {
		this.asrErrorList = asrErrorList;
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

	public ArrayList getSupAffList() {
		return supAffList;
	}

	public void setSupAffList(ArrayList supAffList) {
		this.supAffList = supAffList;
	}

	public String getSupAffSelected() {
		return supAffSelected;
	}

	public void setSupAffSelected(String supAffSelected) {
		this.supAffSelected = supAffSelected;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public File getTheFile() {
		return theFile;
	}

	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}

	public String getTheFileFileName() {
		return theFileFileName;
	}

	public void setTheFileFileName(String theFileFileName) {
		this.theFileFileName = theFileFileName;
	}

	public String getTheFileContentType() {
		return theFileContentType;
	}

	public void setTheFileContentType(String theFileContentType) {
		this.theFileContentType = theFileContentType;
	}

	public TCGMDataValidation getDataVal() {
		return dataVal;
	}

	public void setDataVal(TCGMDataValidation dataVal) {
		this.dataVal = dataVal;
	}

	// validate
	// TODO
	/*
	 * @Override public void validate() {
	 * 
	 * if (TCGMConstants.URL_PARM_VAL_SAVE_ALL.equals(cmd) ||
	 * TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {
	 * 
	 * dataVal.validateModelCopy( modelSelected, errors);
	 * 
	 * if (!errors.isEmpty()) {
	 * 
	 * addActionError( "Validation failed."); } } }
	 */

	// Setter and Getter

}
