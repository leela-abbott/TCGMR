package abbott.ai.tcgm17.action;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.DataFeedLogDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.DataFeedLogEntry;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DataFeedMngr;

public class MngDataFeedLog extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8942579499755070607L;

	private static final Logger logger = LogManager.getLogger(MngDataFeedLog.class);

	/*
	 * ===================================================== Properties from
	 * MngDataFeedLogForm =====================================================
	 */

	private ArrayList<DataFeedLogEntry> logentrylist = new ArrayList<>(20);

	/*
	 * ===================================================== Properties from
	 * TCGMForm =====================================================
	 */

	private Vector monthList = new Vector();

	private Option[] monthListNumber;
	private Option[] twoDigitMonthListNumber;
	private Option[] twoDigitMonthListNumberWithLabel;
	private Option[] periodMonthList;
	private Option[] yearList;
	private Option[] exportSystemList;

	private String showModels = "";
	private String rowToCopy = "";
	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";

	private Vector models = new Vector();
	private Vector closedModels = new Vector();

	private TCGMModel.Type modelType;
	
	private Map<String, Object> session;

	/*
	 * public MngDataFeedLog() { reset(); }
	 */

	@Override
	public String execute() {

		try {

			if (!isSessionValid()) {
				return LOGIN;
			}

			processCmd();

			return SUCCESS;

		} catch (TCGMException ex) {

			logger.error("Error processing Data Feed Log", ex);

			addActionError(ex.getMessage());

			return ERROR;
		}
	}

	/**
	 * Migrated processCmd()
	 */
	private void processCmd() throws TCGMException {

		UserToken ut = getUserToken();

		String currentCmd = cmd == null ? "" : cmd;

		if ("DELETE_ALL".equalsIgnoreCase(currentCmd)) {

			new DataFeedMngr().clearDataFeedLog();

			addActionMessage("Data Feed Log cleared successfully.");
		}

		reset();
	}

	/**
	 * Migrated reset()
	 */
	public void reset() {

		try {

			cmd = "";

			DataFeedLogDao logdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE)
					.getDataFeedLogDao(SQLUtil.getOracleAdmin());

			logentrylist = logdao.getAllEntities();

		} catch (TCGMException ex) {

			logger.error("Error while resetting form", ex);
		}
	}

	/**
	 * Helper method from form bean
	 */
	public DataFeedLogEntry getLogEntryList(int index) {

		DataFeedLogEntry entry = null;

		if (index >= 0 && index < logentrylist.size()) {

			entry = logentrylist.get(index);
		}

		return entry;
	}

	public void setLogEntryList(int index, DataFeedLogEntry entry) {

		logentrylist.set(index, entry);
	}

	/*
	 * ===================================================== Getters / Setters
	 * =====================================================
	 */

	public ArrayList<DataFeedLogEntry> getLogentrylist() {
		return logentrylist;
	}

	public void setLogentrylist(ArrayList<DataFeedLogEntry> logentrylist) {
		this.logentrylist = logentrylist;
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

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
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

	public Vector getMonthList() {
		return monthList;
	}

	public Option[] getMonthListNumber() {
		return monthListNumber;
	}

	public Option[] getTwoDigitMonthListNumber() {
		return twoDigitMonthListNumber;
	}

	public Option[] getTwoDigitMonthListNumberWithLabel() {
		return twoDigitMonthListNumberWithLabel;
	}

	public Option[] getPeriodMonthList() {
		return periodMonthList;
	}

	public Option[] getYearList() {
		return yearList;
	}

	public Option[] getExportSystemList() {
		return exportSystemList;
	}

	@Override
	public void withSession(Map<String, Object>  session) {
       this.session = session;
	}
}