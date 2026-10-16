package abbott.ai.tcgm17.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.ReportRestriction;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.process.JobInstance;

public class DataTransfers extends TCGMAction implements SessionAware {

	private static final long serialVersionUID = 5804031209867838610L;

	private Map<String, Object> session;

	// DataTransfer Form

	private String selFactorReportOption;
	private String selNetCostOption;
	private String selProductNetReportOption;

	private String pricingFilename;
	private String treeFilename;

	private String savedUnitsSelected;
	private String currUnitsSelected;
	private Vector unitSets;
	private Vector savedSets = new Vector();
	private String sendTreeToMVSPeriod;
	private File theFile;
	private String theFileFileName;
	private String theFileContentType;

	// TCGMProductionForm
	private Vector reportList;
	private String selReport;

	private boolean chkRestrict;
	private String selReportDest;
	private boolean chkImmediate;
	private  Vector destinationList;

	private String[] operations;
	private ReportRestriction restrictions = new ReportRestriction();
	private String numCopies;
	private String formHandler;

	private String jobName;

	private String modelLongDesc = "";
	private String memo = "";

	private boolean restrictionEntered = false;

	// TCGMForm

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

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;

	}

	// execute

	@Override
	public String execute() {

		try {

			if (!isSessionValid()) {
				return LOGIN;
			}

			if (!isModelSelected()) {
				return INPUT;
			}

			if (cmd != null && cmd.trim().length() > 0) {

				if (chkRestrict && !restrictionEntered) {

					session.put("restrictionForm", this);
					return "restrictions";
				}

				if ("upload".equalsIgnoreCase(cmd)) {

					processUpload();

					addActionMessage(getText("success.fileupload.copied"));
				}

				if (jobName != null) {

					UserToken ut = getUserToken();

					String modelId = getState().getCurrentModelIdString();

					ModelMngr mm = new ModelMngr();

					String modelName = mm.getModelName(ut, Integer.parseInt(modelId));

					if (JobConstants.PN_PUB_FCT_RPT.equals(jobName) || JobConstants.PN_UNPUB_FCT_RPT.equals(jobName)
							|| JobConstants.PN_PUB_NET_COST.equals(jobName)
							|| JobConstants.PN_UNPUB_NET_COST.equals(jobName)) {

						publishRecords(jobName, Integer.parseInt(modelId), modelName, ut);
					}

					if (JobConstants.PN_GEN_FCT_RPT.equals(jobName) || JobConstants.PN_UNGEN_FCT_RPT.equals(jobName)
							|| JobConstants.PN_GEN_NET_COST.equals(jobName)
							|| JobConstants.PN_UNGEN_NET_COST.equals(jobName)) {

						generateRecords(jobName, Integer.parseInt(modelId), modelName, ut);
					}

					if (!JobConstants.PN_UNGEN_FCT_RPT.equals(jobName)
							&& !JobConstants.PN_UNGEN_NET_COST.equals(jobName)
							&& !JobConstants.PN_UNPUB_FCT_RPT.equals(jobName)
							&& !JobConstants.PN_UNPUB_NET_COST.equals(jobName)) {

						processCmd();
					}
				}

				initModelAndDataset();

				return SUCCESS;
			}

			initModelAndDataset();

			return INPUT;

		} catch (Exception e) {

			logger.error("DataTransfersAction", e);

			addActionError(e.getMessage());

			return ERROR;
		}
	}

	// ProcessCmd

	private void processCmd() throws TCGMException {

		UserToken ut = getUserToken();

		String modelId = getState().getCurrentModelIdString();

		FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId),
				TCGMModel.Type.FACTOR);

		if ("ADD_JOB".equalsIgnoreCase(cmd)) {

			ProcessMngr pm = new ProcessMngr();

			JobInstance job = pm.createJob(modelId, "-1",
					new TCGMProductionForm(this.jobName, this.chkImmediate, this.selReportDest));

			// ModelMngr mm = new ModelMngr();

			TCGMState state = getState();

			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + fm.getName());
		}
	}

	// Helper Method

	private void processUpload() throws Exception {

		String fileName = theFileFileName;

		if (!"tpss_rslt_out_ccs.txt".equalsIgnoreCase(fileName)) {

			fileName = "tpss_rslt_out_ccs.txt";
		}

		String uploadDir = AppConst.getFileUploadDirectory();

		File targetFile = new File(uploadDir, fileName);

		if (!targetFile.exists()) {

			try (FileOutputStream fos = new FileOutputStream(targetFile)) {

				java.nio.file.Files.copy(theFile.toPath(), fos);
			}
		}
	}

	// private void processUpload() throws Exception;

	// private void publishRecords(String jobName, int modelId, String modelName,
	// UserToken ut) throws TCGMException;

	private void publishRecords(String jobName, int modelId, String modelName, UserToken ut) throws TCGMException {

		ProcessMngr pm = new ProcessMngr();

		int inqReportId = 0;

		String flag = "N";

		if (JobConstants.PN_PUB_FCT_RPT.equals(jobName) || JobConstants.PN_UNPUB_FCT_RPT.equals(jobName)) {

			inqReportId = 1;

			if (JobConstants.PN_PUB_FCT_RPT.equals(jobName)) {

				flag = "Y";
				addActionMessage(getText("success.publish.model"));

			} else {

				addActionMessage(getText("success.unpublish.model"));
			}

		} else if (JobConstants.PN_PUB_NET_COST.equals(jobName) || JobConstants.PN_UNPUB_NET_COST.equals(jobName)) {

			inqReportId = 2;

			if (JobConstants.PN_PUB_NET_COST.equals(jobName)) {

				flag = "Y";

				addActionMessage(getText("success.publish.model"));

			} else {

				addActionMessage(getText("success.unpublish.model"));
			}
		}

		try {

			pm.PublishRecord(inqReportId, modelId, flag, modelName, ut);

		} catch (TCGMException e) {

			addActionError(getText("unsuccessful.publish.model"));

			throw e;
		}
	}

	private void generateRecords(String jobName, int modelId, String modelName, UserToken ut) throws TCGMException {

		ProcessMngr pm = new ProcessMngr();

		int inqReportId = 0;

		String flag = "N";

		if (JobConstants.PN_GEN_FCT_RPT.equals(jobName) || JobConstants.PN_UNGEN_FCT_RPT.equals(jobName)) {

			inqReportId = 1;

			if (JobConstants.PN_GEN_FCT_RPT.equals(jobName)) {

				flag = "Y";

				addActionMessage(getText("success.generate.model"));

			} else {

				addActionMessage(getText("success.ungenerate.model"));
			}

		} else if (JobConstants.PN_GEN_NET_COST.equals(jobName) || JobConstants.PN_UNGEN_NET_COST.equals(jobName)) {

			inqReportId = 2;

			if (JobConstants.PN_GEN_NET_COST.equals(jobName)) {

				flag = "Y";

				addActionMessage(getText("success.generate.model"));

			} else {

				addActionMessage(getText("success.ungenerate.model"));
			}
		}

		try {

			pm.GenerateRecord(inqReportId, modelId, flag, modelName, ut);

		} catch (TCGMException e) {

			addActionError(getText("unsuccessful.generate.model"));

			throw e;
		}
	}

	// private void generateRecords(String jobName, int modelId, String modelName,
	// UserToken ut) throws TCGMException;

	public Vector setSavedSets(Vector vect) throws TCGMException {

		Dataset dataset;

		Vector savedUnitSets = new Vector();

		for (int i = 0; i < vect.size(); i++) {

			dataset = (Dataset) vect.elementAt(i);

			String datasetName = dataset.getDatasetName();

			if (datasetName != null) {

				if (datasetName.trim().endsWith("_SAVE") || datasetName.trim().endsWith("_save")) {

					savedUnitSets.add(dataset);
				}
			}
		}

		savedSets = savedUnitSets;

		return savedUnitSets;
	}

	// initiDataModel

	private void initModelAndDataset() throws Exception {

		UserToken ut = getUserToken();

		int modelId = Integer.parseInt(getState().getCurrentModelIdString());

		DatasetMngr dm = new DatasetMngr();

		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.ANALYSIS);

		Vector utSets = dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);

		Vector utSavedSets = dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA_SAVE);

		setUnitSets(utSets);

		setSavedSets(utSavedSets);

		setSavedUnitsSelected(md.getModelParm(modelId, JobConstants.PN_SAVED_UNITS + "::" + ut.getUserid()));

		setCurrUnitsSelected(md.getModelParm(modelId, JobConstants.PN_CURR_UNITS + "::" + ut.getUserid()));
		setDestinationList(this.destinationList);
	}

	// reset
	public void reset() {

		cmd = "";
		jobName = "";
		restrictionEntered = false;

		restrictions = new ReportRestriction();

		modelSelected = modelSelected == null ? "" : modelSelected;
	}

	// Getter setter

	public String getSelFactorReportOption() {
		return selFactorReportOption;
	}

	public void setSelFactorReportOption(String selFactorReportOption) {
		this.selFactorReportOption = selFactorReportOption;
	}

	public String getSelNetCostOption() {
		return selNetCostOption;
	}

	public void setSelNetCostOption(String selNetCostOption) {
		this.selNetCostOption = selNetCostOption;
	}

	public String getSelProductNetReportOption() {
		return selProductNetReportOption;
	}

	public void setSelProductNetReportOption(String selProductNetReportOption) {
		this.selProductNetReportOption = selProductNetReportOption;
	}

	public String getPricingFilename() {
		return pricingFilename;
	}

	public void setPricingFilename(String pricingFilename) {
		this.pricingFilename = pricingFilename;
	}

	public String getTreeFilename() {
		return treeFilename;
	}

	public void setTreeFilename(String treeFilename) {
		this.treeFilename = treeFilename;
	}

	public String getSavedUnitsSelected() {
		return savedUnitsSelected;
	}

	public void setSavedUnitsSelected(String savedUnitsSelected) {
		this.savedUnitsSelected = savedUnitsSelected;
	}

	public String getCurrUnitsSelected() {
		return currUnitsSelected;
	}

	public void setCurrUnitsSelected(String currUnitsSelected) {
		this.currUnitsSelected = currUnitsSelected;
	}

	public Vector getUnitSets() {
		return unitSets;
	}

	public void setUnitSets(Vector unitSets) {
		this.unitSets = unitSets;
	}

	public Vector getSavedSets() {
		return savedSets;
	}

	/*
	 * public void setSavedSets(Vector savedSets) { this.savedSets = savedSets; }
	 */

	public String getSendTreeToMVSPeriod() {
		return sendTreeToMVSPeriod;
	}

	public void setSendTreeToMVSPeriod(String sendTreeToMVSPeriod) {
		this.sendTreeToMVSPeriod = sendTreeToMVSPeriod;
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

	public Vector getReportList() {
		return reportList;
	}

	public void setReportList(Vector reportList) {
		this.reportList = reportList;
	}

	public String getSelReport() {
		return selReport;
	}

	public void setSelReport(String selReport) {
		this.selReport = selReport;
	}

	public boolean isChkRestrict() {
		return chkRestrict;
	}

	public void setChkRestrict(boolean chkRestrict) {
		this.chkRestrict = chkRestrict;
	}

	public String getSelReportDest() {
		return selReportDest;
	}

	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
	}

	public String[] getOperations() {
		return operations;
	}

	public void setOperations(String[] operations) {
		this.operations = operations;
	}

	public ReportRestriction getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(ReportRestriction restrictions) {
		this.restrictions = restrictions;
	}

	public String getNumCopies() {
		return numCopies;
	}

	public void setNumCopies(String numCopies) {
		this.numCopies = numCopies;
	}

	public String getFormHandler() {
		return formHandler;
	}

	public void setFormHandler(String formHandler) {
		this.formHandler = formHandler;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getModelLongDesc() {
		return modelLongDesc;
	}

	public void setModelLongDesc(String modelLongDesc) {
		this.modelLongDesc = modelLongDesc;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isRestrictionEntered() {
		return restrictionEntered;
	}

	public void setRestrictionEntered(boolean restrictionEntered) {
		this.restrictionEntered = restrictionEntered;
	}

	public Vector getMonthList() {
		return monthList;
	}

	public void setMonthList(Vector monthList) {
		this.monthList = monthList;
	}

	
	
	public Option[] getMonthListNumber() {
		if (monthListNumber == null) {
			monthListNumber = new Option[12];
			String n = null;
			for (int i = 0; i < 12; i++) {
				n = String.valueOf(i+1);
				monthListNumber[i] = new Option( n, n );
			}
		}
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

	
	public  Vector getDestinationList() {
		return destinationList;
	}

	public  void setDestinationList(Vector destinationList) {
		if (destinationList == null) {
			destinationList = new Vector(3);
			destinationList.add(new Option("Run Only", "0" ) );
			destinationList.add(new Option("Generate Report", "G" ) );
			destinationList.add(new Option("Generate & Print Report", "GP" ) );
		}
		
		this.destinationList = destinationList;
	}

}
