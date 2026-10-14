package abbott.ai.tcgm17.action.ratedata;

import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.RateDataMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class OpenMngRateSets extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3824842278488877739L;

	private static final Logger logger = LogManager.getLogger(OpenMngRateSets.class);
	private Map<String, Object> session;

	/*
	 * ============================================================ Properties from
	 * TCGMForm ============================================================
	 */
	protected Vector monthList = new Vector();
	protected Option[] monthListNumber;
	protected Option[] twoDigitMonthListNumber;
	protected Option[] twoDigitMonthListNumberWithLabel;
	protected Option[] periodMonthList;
	protected Option[] yearList;
	protected Option[] exportSystemList;

	private String showModels = "";
	private String rowToCopy = "";
	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private Vector models = new Vector();
	private Vector closedModels = new Vector();
	private TCGMModel.Type modelType;

	/*
	 * ============================================================ Properties from
	 * MngRateSetsForm ============================================================
	 */
	private Vector rateSetList = new Vector();
	private Dataset editRateSet = new Dataset(Dataset.DatasetType.RATE_SET);
	private String selectedDatasetTableId = "";
	private String copyFromDatasetTableId = TCGMConstants.NONE_SELECTED;
	private Dataset selectedRateSet = new Dataset(Dataset.DatasetType.RATE_SET);

	@Override
	public void withSession(Map<String, Object> session) {

		this.session = session;
	}

	/*
	 * ============================================================ Action Execute
	 * (perform() migration)
	 * ============================================================
	 */
	@Override
	public String execute() {

		DatasetMngr datasetMngr = new DatasetMngr();

		try {

			processCmd();

			editRateSet = new Dataset(Dataset.DatasetType.RATE_SET);
			copyFromDatasetTableId = TCGMConstants.NONE_SELECTED;

			rateSetList = datasetMngr.getDatasetByTableName(getUserToken(), DBConst.TABLE_RATE_DATA);

			return SUCCESS;

		} catch (TCGMException tcgme) {

			logger.error(tcgme.toString(), tcgme);

			addActionError(getText("exception.mngratesets.open"));

			return ERROR;
		}
	}

	/*
	 * ============================================================ processCmd()
	 * from Form Bean ============================================================
	 */
	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_CANCEL.equals(getCmd())) {

			setCopyFromDatasetTableId(TCGMConstants.NONE_SELECTED);

			reset();
		}
	}

	/*
	 * ============================================================ reset() from
	 * Form Bean ============================================================
	 */
	public void reset() {

		this.editRateSet = new Dataset(Dataset.DatasetType.RATE_SET);

		this.cmd = "";
	}

	/*
	 * ============================================================ validate() from
	 * Form Bean ============================================================
	 */
	@Override
	public void validate() {

		if (TCGMConstants.URL_PARM_VAL_SAVE.equals(getCmd())) {

			if (getEditRateSet() == null || getEditRateSet().getDatasetName().trim().length() == 0) {

				addActionError(getText("error.validation.ratesetName"));
			}
		} else if (TCGMConstants.URL_PARM_VAL_DELETE.equals(getCmd())
				|| TCGMConstants.URL_PARM_VAL_MAINTAIN.equals(getCmd())
				|| TCGMConstants.URL_PARM_VAL_RENAME.equals(getCmd())
				|| TCGMConstants.URL_PARM_VAL_COPY.equals(getCmd())) {

			if (getSelectedDatasetTableId().trim().length() == 0) {

				addActionError(getText("error.validation.select.rateset"));
			} else if (TCGMConstants.URL_PARM_VAL_MAINTAIN.equals(getCmd())) {

				try {

					UserToken ut = AppConst.getInstance().getJobId();

					int pendingId = new RateDataMngr().getPendingRateDataTranId(ut);

					int selId = getSelectedDatasetTableIdInt();

					if (pendingId != -1 && pendingId != selId) {

						Dataset ds = new DatasetMngr().getDatasetById(ut, pendingId);

						addActionError(getText("error.validation.select.rateset.pendingtransactions",
								new String[] { ds.getDatasetName() }));
					}

				} catch (TCGMException ex) {

					logger.error("Validation error in OpenMngRateSetsAction", ex);

					addActionError(getText("error.validation.exception"));
				}
			}
		}
	}

	/*
	 * ============================================================ Helper Methods
	 * ============================================================
	 */

	public long getRateSetListSize() {
		return getRateSetList().size();
	}

	public Dataset getRateSetList(int index) {

		if (index >= 0 && index < getRateSetList().size()) {

			return (Dataset) getRateSetList().elementAt(index);
		}

		return null;
	}

	public void setRateSetList(Dataset dataset, int index) {

		this.rateSetList.setElementAt(dataset, index);
	}

	public int getSelectedDatasetTableIdInt() {
		return Integer.parseInt(getSelectedDatasetTableId());
	}

	public int getCopyFromDatasetTableIdInt() {
		return Integer.parseInt(getCopyFromDatasetTableId());
	}

	public int getRowToCopyInt() {
		return Integer.parseInt(getRowToCopy());
	}

	/*
	 * ============================================================ Getters /
	 * Setters ============================================================
	 */

	public Vector getRateSetList() {
		if (rateSetList == null) {
			rateSetList = new Vector();
		}
		return rateSetList;
	}

	public void setRateSetList(Vector rateSetList) {
		this.rateSetList = rateSetList;
	}

	public Dataset getEditRateSet() {
		return editRateSet;
	}

	public void setEditRateSet(Dataset editRateSet) {
		this.editRateSet = editRateSet;
	}

	public String getSelectedDatasetTableId() {

		if ((selectedDatasetTableId == null || selectedDatasetTableId.trim().length() == 0)
				&& getRateSetList().size() > 0) {

			selectedDatasetTableId = getRateSetList(0).getDatasetTableId();
		}

		return selectedDatasetTableId;
	}

	public void setSelectedDatasetTableId(String selectedDatasetTableId) {
		this.selectedDatasetTableId = selectedDatasetTableId;
	}

	public String getCopyFromDatasetTableId() {
		return copyFromDatasetTableId;
	}

	public void setCopyFromDatasetTableId(String copyFromDatasetTableId) {
		this.copyFromDatasetTableId = copyFromDatasetTableId;
	}

	public Dataset getSelectedRateSet() {
		return selectedRateSet;
	}

	public void setSelectedRateSet(Dataset selectedRateSet) {
		this.selectedRateSet = selectedRateSet;
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

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
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

	public Vector getMonthList() {
		return monthList;
	}

	public void setMonthList(Vector monthList) {
		this.monthList = monthList;
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