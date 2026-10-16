package abbott.ai.tcgm17.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.struts2.ActionContext;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.process.*;

public class DataTransfersAction extends TCGMAction implements UploadedFilesAware {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(DataTransfersAction.class);

	private String cmd = "";
	private String jobName = "";
	private boolean chkRestrict;
	private boolean restrictionEntered;

	private String selFactorReportOption;
	private String selNetCostOption;
	private String selProductNetReportOption;
	private String pricingFilename;
	private String treeFilename;
	private String savedUnitsSelected;
	private String currUnitsSelected;
	private String sendTreeToMVSPeriod;

	// === Merged Fields from TCGMProductionForm ===
	private Vector reportList;
	private String selReport;
	private static Vector destinationList;
	private String selReportDest;
	private boolean chkImmediate;
	private String[] operations;
	private ReportRestriction restrictions;
	private String numCopies;
	private String formHandler;
	private String modelLongDesc = "";
	private String memo = "";

	// === Merged Fields from TCGMForm ===
	protected static final String BR = "<BR />";
	protected final String className = this.getClass().getName();
	protected Vector monthList = new Vector();
	protected Option[] monthListNumber = null;
	protected Option[] twoDigitMonthListNumber = null;
	protected Option[] twoDigitMonthListNumberWithLabel = null;
	protected Option[] periodMonthList = null;
	protected Option[] yearList = null;
	protected Option[] exportSystemList = null;
	private String showModels = "";
	private String rowToCopy = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private Vector models = new Vector();
	private Vector closedModels = new Vector();
	private abbott.ai.tcgm.entities.TCGMModel.Type modelType;

	private List<Dataset> unitSets = new ArrayList<>();
	private List<Dataset> savedSets = new ArrayList<>();

	private UploadedFile uploadedFile;
	private String uploadedFileContentType;
	private String uploadedFileName;

	protected String forward = TCGMConstants.FORWARD_SUCCESS;

	public DataTransfersAction() {
		super();

		// === Merged Constructor Initialization Logic ===
		if (destinationList == null) {
			destinationList = new Vector(3);
			destinationList.add(new Option("Run Only", "0"));
			destinationList.add(new Option("Generate Report", "G"));
			destinationList.add(new Option("Generate & Print Report", "GP"));
		}
		this.restrictions = new ReportRestriction();
		this.operations = ReportRestriction.Operations;
	}

	public boolean isChkRestrict() {
		return chkRestrict;
	}

	public void setChkRestrict(boolean chkRestrict) {
		this.chkRestrict = chkRestrict;
	}

	public boolean isRestrictionEntered() {
		return restrictionEntered;
	}

	public void setRestrictionEntered(boolean restrictionEntered) {
		this.restrictionEntered = restrictionEntered;
	}

	public String getJobName() {
		return jobName;
	}

	public String getUploadedFileContentType() {
		return uploadedFileContentType;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	@Override
	public String execute() {
		try {
			HttpServletRequest request = ActionContext.getContext().getServletRequest();
			if (this.isSessionValid() && this.isModelSelected()) {
				UserToken ut = this.getUserToken(request);
				String modelName = null;
				String currentJobName = null;

				if (!TCGMUtil.isEmpty(this.cmd)) {
					if (this.chkRestrict && !this.restrictionEntered) {
						request.getSession().setAttribute("restrictionForm", this);
						this.forward = TCGMConstants.G_FORWARD_RESTRICTIONS;
					} else {
						if ("upload".equalsIgnoreCase(this.cmd)) {
							handleFileUpload();
						}

						if (!TCGMUtil.isEmpty(this.jobName)) {
							currentJobName = this.jobName;
							String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
							ModelMngr mm = new ModelMngr();

							if (JobConstants.PN_PUB_FCT_RPT.equals(currentJobName)
									|| JobConstants.PN_UNPUB_FCT_RPT.equals(currentJobName)
									|| JobConstants.PN_PUB_NET_COST.equals(currentJobName)
									|| JobConstants.PN_UNPUB_NET_COST.equals(currentJobName)) {
								modelName = mm.getModelName(ut, Integer.parseInt(modelId));
								publishRecords(currentJobName, Integer.parseInt(modelId), modelName, ut, request);
							}

							if (JobConstants.PN_GEN_FCT_RPT.equals(currentJobName)
									|| JobConstants.PN_UNGEN_FCT_RPT.equals(currentJobName)
									|| JobConstants.PN_GEN_NET_COST.equals(currentJobName)
									|| JobConstants.PN_UNGEN_NET_COST.equals(currentJobName)) {
								modelName = mm.getModelName(ut, Integer.parseInt(modelId));
								generateRecords(currentJobName, Integer.parseInt(modelId), modelName, ut, request);
							}

							if (!(JobConstants.PN_UNGEN_FCT_RPT.equals(currentJobName)
									|| JobConstants.PN_UNGEN_NET_COST.equals(currentJobName)
									|| JobConstants.PN_UNPUB_FCT_RPT.equals(currentJobName)
									|| JobConstants.PN_UNPUB_NET_COST.equals(currentJobName))) {
								processCmd(request);
							}
						}
						this.setupForm(request);
						return SUCCESS;
					}
				} else {
					this.setupForm(request);
					return INPUT;
				}
			}
		} catch (TCGMException ex) {
			ActionContext.getContext().getServletRequest().setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			return TCGMConstants.G_FORWARD_EXCEPTION;
		}
		return SUCCESS;
	}

	private void handleFileUpload() throws TCGMException {
		if (this.uploadedFile != null && this.uploadedFile.getContent() != null) {
			String targetFileName = this.uploadedFileName;
			if (!"tpss_rslt_out_ccs.txt".equalsIgnoreCase(targetFileName)) {
				targetFileName = "tpss_rslt_out_ccs.txt";
			}
			String filePath = AppConst.getFileUploadDirectory();
			File fileToCreate = new File(filePath, targetFileName);

			if (!fileToCreate.exists()) {
				try (FileOutputStream fileOutStream = new FileOutputStream(fileToCreate)) {
					byte[] fileData = java.nio.file.Files
							.readAllBytes(((File) this.uploadedFile.getContent()).toPath());
					fileOutStream.write(fileData);
					fileOutStream.flush();
				} catch (Exception e) {
					throw new TCGMException("DataTransfersAction", "handleFileUpload",
							"File copying operation hit an stream crash.");
				}
			}
			fileToCreate = null;
			this.cmd = "";
			addActionMessage("success.fileupload.copied");
		}
	}

	@SuppressWarnings("unchecked")
	public void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		int modelIdInt = Integer.parseInt(((TCGMState) this.getState(request)).getCurrentModelIdString());
		DatasetMngr dm = new DatasetMngr();
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.ANALYSIS);

		this.unitSets = (List<Dataset>) (List<?>) dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);
		this.savedSets = (List<Dataset>) (List<?>) dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA_SAVE);

		this.savedUnitsSelected = md.getModelParm(modelIdInt, JobConstants.PN_SAVED_UNITS + "::" + ut.getUserid());
		this.currUnitsSelected = md.getModelParm(modelIdInt, JobConstants.PN_CURR_UNITS + "::" + ut.getUserid());

		request.getSession().setAttribute("dataTransfersForm", this);
	}

	private void processCmd(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
		FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId),
				TCGMModel.Type.FACTOR);

		if ("ADD_JOB".equalsIgnoreCase(this.cmd)) {
			ProcessMngr pm = new ProcessMngr();

			TCGMProductionForm legacyFormAdapter = new TCGMProductionForm() {
				{
					setCmd(DataTransfersAction.this.getCmd());
					setJobName(DataTransfersAction.this.getJobName());
					setSelFactorReportOption(DataTransfersAction.this.getSelFactorReportOption());
					setSelNetCostOption(DataTransfersAction.this.getSelNetCostOption());
					setSelProductNetReportOption(DataTransfersAction.this.getSelProductNetReportOption());
					setPricingFilename(DataTransfersAction.this.getPricingFilename());
					setTreeFilename(DataTransfersAction.this.getTreeFilename());
					setSavedUnitsSelected(DataTransfersAction.this.getSavedUnitsSelected());
					setCurrUnitsSelected(DataTransfersAction.this.getCurrUnitsSelected());
					setSendTreeToMVSPeriod(DataTransfersAction.this.getSendTreeToMVSPeriod());

					setSelReport(DataTransfersAction.this.getSelReport());
					setSelReportDest(DataTransfersAction.this.getSelReportDest());
					setChkImmediate(DataTransfersAction.this.isChkImmediate());
					setNumCopies(DataTransfersAction.this.getNumCopies());
					setFormHandler(DataTransfersAction.this.getFormHandler());
					setMemo(DataTransfersAction.this.getMemo());
					setModelLongDesc(DataTransfersAction.this.getModelLongDesc());
					setRestrictions(DataTransfersAction.this.getRestrictions());

					if (DataTransfersAction.this.getUnitSets() != null) {
						setUnitSets(new java.util.Vector<>(DataTransfersAction.this.getUnitSets()));
					}
					if (DataTransfersAction.this.getSavedSets() != null) {
						setSavedSets(new java.util.Vector<>(DataTransfersAction.this.getSavedSets()));
					}
				}
			};

			JobInstance job = pm.createJob(modelId, "-1", legacyFormAdapter);
			ModelMngr mm = new ModelMngr();

			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + fm.getName());

			if (job.getJobDef().equals(JobDefinition.GEN_NET_COST)
					|| job.getJobDef().equals(JobDefinition.GEN_NET_COST_RPT)) {
				job.addJobParm(JobConstants.PN_NET_RT_PER, String.valueOf(TCGMConstants.MAX_PERIODS));
			} else if (job.getJobDef().equals(JobDefinition.GEN_FCT_RPT)
					|| job.getJobDef().equals(JobDefinition.PUB_FCT_RPT)
					|| job.getJobDef().equals(JobDefinition.GEN_FCT_REPORT)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
				job.addJobParm(JobConstants.PN_STD_CST_RATE_EXCHANGE,
						dd.getDatasetById(Integer
								.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_COST)))
								.getDatasetName());
				job.addJobParm(JobConstants.PN_FACTOR_RATE_EXCHANGE,
						dd.getDatasetById(Integer.parseInt(
								md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_FACTOR_ACTUAL)))
								.getDatasetName());
				job.addJobParm(JobConstants.PN_REVISION_RATE_EXCHANGE,
						dd.getDatasetById(Integer
								.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_REVISION)))
								.getDatasetName());
			} else if (job.getJobDef().equals(JobDefinition.SEND_TREE_TO_MVS)) {
				job.addJobParm(JobConstants.PN_TREE_FILENAME, this.treeFilename);
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				this.treeFilename = this.treeFilename.toUpperCase();
				md.setModelParmII(-1, TCGMConstants.DT_SEND_TREE_TO_MVS,
						this.treeFilename + TCGMConstants.DT_DELIMITER);
				job.addJobParm("INP_PERIOD", this.sendTreeToMVSPeriod);
				md.setModelParmII(Integer.parseInt(modelId), "INP_PERIOD", this.sendTreeToMVSPeriod);
			} else if (job.getJobDef().equals(JobDefinition.GEN_FCT_CHGS_RPT)
					|| job.getJobDef().equals(JobDefinition.PUB_FCT_CHGS)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				fm.setSavedFactorDate(md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE));
				job.addJobParm(fm.PN_SAVED_FACTOR_DATE, fm.getSavedFactorDate());
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
				job.addJobParm(JobConstants.PN_MOD_VERSION,
						md.getModelParm(Integer.parseInt(modelId), JobConstants.PN_MOD_VERSION));
			} else if (job.getJobDef().equals(JobDefinition.GEN_TCGM_CHGS_RPT)
					|| job.getJobDef().equals(JobDefinition.PUB_TCGM_CHGS)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);

				if (!TCGMUtil.isEmpty(md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE))) {
					fm.setSavedFactorDate(md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE));
				}

				String modelVersion = md.getModelParm(Integer.parseInt(modelId), JobConstants.PN_MOD_VERSION);
				job.addJobParm(JobConstants.PN_MOD_VERSION, modelVersion != null ? modelVersion : "");
				if (!TCGMUtil.isEmpty(this.savedUnitsSelected)) {
					job.addJobParm(JobConstants.PN_SAVED_UNITS, this.savedUnitsSelected);
					job.addJobParm(JobConstants.PN_SAVED_UNITS_NAME,
							dd.getDatasetById(Integer.parseInt(this.savedUnitsSelected)).getDatasetName());
					mm.setModelParmII(ut, fm, JobConstants.PN_SAVED_UNITS, this.savedUnitsSelected);
					mm.setModelParmII(ut, fm, JobConstants.PN_SAVED_UNITS + "::" + ut.getUserid(),
							this.savedUnitsSelected);
					String sql = JobConstants.PN_SQL_SAVED_UNITS_DATE + " AND dataset_table_id = "
							+ this.savedUnitsSelected;
					try {
						job.addJobParm(JobConstants.PN_SAVED_UNITS_DATE, md.getStringValueFromSql(sql));
					} catch (TCGMException ex) {
						logger.error("Failed to recover explicit units date stamp reference context mapping.", ex);
					}
					mm.setModelParmII(ut, fm, JobConstants.PN_CURR_UNITS, this.currUnitsSelected);
					mm.setModelParmII(ut, fm, JobConstants.PN_CURR_UNITS + "::" + ut.getUserid(),
							this.currUnitsSelected);
				} else {
					job.addJobParm(JobConstants.PN_SAVED_UNITS, "");
					job.addJobParm(JobConstants.PN_SAVED_UNITS_NAME, "");
					job.addJobParm(JobConstants.PN_SAVED_UNITS_DATE, "");
				}

				if (!TCGMUtil.isEmpty(this.currUnitsSelected)) {
					fm.setCurrUnitsName(dd.getDatasetById(Integer.parseInt(this.currUnitsSelected)).getDatasetName());
					job.addJobParm(fm.PN_CURR_UNITS_NAME, fm.getCurrUnitsName());
					job.addJobParm(JobConstants.PN_CURR_UNITS, this.currUnitsSelected);
				} else {
					fm.setCurrUnitsName("");
					job.addJobParm(fm.PN_CURR_UNITS_NAME, "");
					job.addJobParm(JobConstants.PN_CURR_UNITS, "");
				}

				job.addJobParm(fm.PN_SAVED_FACTOR_DATE, fm.getSavedFactorDate() != null ? fm.getSavedFactorDate() : "");
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() != null ? fm.getDesc() : "");
			} else if (job.getJobDef().equals(JobDefinition.GEN_FACTR_SUMMRY)
					|| job.getJobDef().equals(JobDefinition.PUB_FCT_SMRY)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.WRITE_T_PRODUCT_TRNS)) {
				job.addJobParm(JobConstants.PN_T_PRODUCT_FILENAME, "t_product_output.txt");
			} else if (job.getJobDef().equals(JobDefinition.WRITE_AFF_TRNS)) {
				job.addJobParm(JobConstants.PN_AFF_FILENAME, "aff_output.txt");
			}

			pm.addJob(job, ut);
			addActionMessage("success.job.add");
		}
	}

	public void publishRecords(String jobName, int modelId, String modelName, UserToken ut, HttpServletRequest request)
			throws TCGMException {
		ProcessMngr pm = new ProcessMngr();
		int inqReportId = 0;
		String flag = "N";
		if (JobConstants.PN_PUB_FCT_RPT.equals(jobName) || JobConstants.PN_UNPUB_FCT_RPT.equals(jobName)) {
			inqReportId = 1;
			flag = JobConstants.PN_PUB_FCT_RPT.equals(jobName) ? "Y" : "N";
		} else if (JobConstants.PN_PUB_NET_COST.equals(jobName) || JobConstants.PN_UNPUB_NET_COST.equals(jobName)) {
			inqReportId = 2;
			flag = JobConstants.PN_PUB_NET_COST.equals(jobName) ? "Y" : "N";
		}

		try {
			pm.PublishRecord(inqReportId, modelId, flag, modelName, ut);
			addActionMessage("Y".equals(flag) ? "success.publish.model" : "success.unpublish.model");
		} catch (TCGMException e) {
			addActionError("unsuccessful.publish.model");
			throw new TCGMException(e);
		}
	}

	public void generateRecords(String jobName, int modelId, String modelName, UserToken ut, HttpServletRequest request)
			throws TCGMException {
		ProcessMngr pm = new ProcessMngr();
		int inqReportId = 0;
		String flag = "N";
		if (JobConstants.PN_GEN_FCT_RPT.equals(jobName) || JobConstants.PN_UNGEN_FCT_RPT.equals(jobName)) {
			inqReportId = 1;
			flag = JobConstants.PN_GEN_FCT_RPT.equals(jobName) ? "Y" : "N";
		} else if (JobConstants.PN_GEN_NET_COST.equals(jobName) || JobConstants.PN_UNGEN_NET_COST.equals(jobName)) {
			inqReportId = 2;
			flag = JobConstants.PN_GEN_NET_COST.equals(jobName) ? "Y" : "N";
		}

		try {
			pm.GenerateRecord(inqReportId, modelId, flag, modelName, ut);
			addActionMessage("Y".equals(flag) ? "success.generate.model" : "success.ungenerate.model");
		} catch (TCGMException e) {
			addActionError("unsuccessful.generate.model");
			throw new TCGMException(e);
		}
	}

	public Vector setSavedSets(Vector vect) throws TCGMException {
		Dataset dataset = new Dataset();
		String dataSetName = null;
		Vector savedUnitSets = new Vector();

		for (int i = 0; i < vect.size(); i++) {
			dataset = (Dataset) vect.elementAt(i);
			dataSetName = dataset.getDatasetName();
			if (dataSetName != null) {
				if (dataSetName.trim().endsWith("_SAVE") || dataSetName.trim().endsWith("_save")) {
					savedUnitSets.addElement(dataset);
				}
			}
		}
		return savedUnitSets;
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@StrutsParameter
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSelFactorReportOption() {
		return selFactorReportOption;
	}

	@StrutsParameter
	public void setSelFactorReportOption(String selFactorReportOption) {
		this.selFactorReportOption = selFactorReportOption;
	}

	public String getSelNetCostOption() {
		return selNetCostOption;
	}

	@StrutsParameter
	public void setSelNetCostOption(String selNetCostOption) {
		this.selNetCostOption = selNetCostOption;
	}

	public String getSelProductNetReportOption() {
		return selProductNetReportOption;
	}

	@StrutsParameter
	public void setSelProductNetReportOption(String selProductNetReportOption) {
		this.selProductNetReportOption = selProductNetReportOption;
	}

	public String getPricingFilename() {
		return pricingFilename;
	}

	@StrutsParameter
	public void setPricingFilename(String pricingFilename) {
		this.pricingFilename = pricingFilename;
	}

	public String getTreeFilename() {
		return treeFilename;
	}

	@StrutsParameter
	public void setTreeFilename(String treeFilename) {
		this.treeFilename = treeFilename;
	}

	public String getSavedUnitsSelected() {
		return savedUnitsSelected;
	}

	@StrutsParameter
	public void setSavedUnitsSelected(String savedUnitsSelected) {
		this.savedUnitsSelected = savedUnitsSelected;
	}

	public String getCurrUnitsSelected() {
		return currUnitsSelected;
	}

	@StrutsParameter
	public void setCurrUnitsSelected(String currUnitsSelected) {
		this.currUnitsSelected = currUnitsSelected;
	}

	public String getSendTreeToMVSPeriod() {
		return sendTreeToMVSPeriod;
	}

	@StrutsParameter
	public void setSendTreeToMVSPeriod(String sendTreeToMVSPeriod) {
		this.sendTreeToMVSPeriod = sendTreeToMVSPeriod;
	}

	@StrutsParameter(depth = 1)
	public List<Dataset> getUnitSets() {
		return unitSets;
	}

	public void setUnitSets(List<Dataset> unitSets) {
		this.unitSets = unitSets;
	}

	@StrutsParameter(depth = 1)
	public List<Dataset> getSavedSets() {
		return savedSets;
	}

	public void setSavedSets(List<Dataset> savedSets) {
		this.savedSets = savedSets;
	}

	@StrutsParameter
	public void setUpload(UploadedFile file) {
		this.uploadedFile = file;
	}

	@StrutsParameter
	public void setUploadContentType(String contentType) {
		this.uploadedFileContentType = contentType;
	}

	@StrutsParameter
	public void setUploadFileName(String fileName) {
		this.uploadedFileName = fileName;
	}

	@Override
	public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
		if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
			this.uploadedFile = uploadedFiles.get(0);
			this.uploadedFileName = this.uploadedFile.getOriginalName();
			this.uploadedFileContentType = this.uploadedFile.getContentType();
		}
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

	@StrutsParameter
	public void setSelReport(String selReport) {
		this.selReport = selReport;
	}

	public Vector getDestinationList() {
		return destinationList;
	}

	public void setDestinationList(Vector destinationList) {
		DataTransfersAction.destinationList = destinationList;
	}

	public String getSelReportDest() {
		return selReportDest;
	}

	@StrutsParameter
	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	@StrutsParameter
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

	@StrutsParameter
	public void setNumCopies(String numCopies) {
		this.numCopies = numCopies;
	}

	public String getFormHandler() {
		return formHandler;
	}

	@StrutsParameter
	public void setFormHandler(String formHandler) {
		this.formHandler = formHandler;
	}

	public String getModelLongDesc() {
		return modelLongDesc;
	}

	@StrutsParameter
	public void setModelLongDesc(String modelLongDesc) {
		this.modelLongDesc = modelLongDesc;
	}

	public String getMemo() {
		return memo;
	}

	@StrutsParameter
	public void setMemo(String memo) {
		this.memo = memo;
	}

	// === Merged Getters & Setters from TCGMForm Lazy Initializations ===

	public Vector getMonthList() {
		if (monthList == null) {
			monthList = new Vector(12);
			monthList.add(new Option("January", "01"));
			monthList.add(new Option("February", "02"));
			monthList.add(new Option("March", "03"));
			monthList.add(new Option("April", "04"));
			monthList.add(new Option("May", "05"));
			monthList.add(new Option("June", "06"));
			monthList.add(new Option("July", "07"));
			monthList.add(new Option("August", "08"));
			monthList.add(new Option("September", "09"));
			monthList.add(new Option("October", "10"));
			monthList.add(new Option("November", "11"));
			monthList.add(new Option("December", "12"));
		}
		return monthList;
	}

	public Option[] getMonthListNumber() {
		if (monthListNumber == null) {
			monthListNumber = new Option[12];
			String n = null;
			for (int i = 0; i < 12; i++) {
				n = String.valueOf(i + 1);
				monthListNumber[i] = new Option(n, n);
			}
		}
		return monthListNumber;
	}

	public Option[] getTwoDigitMonthListNumber() {
		if (twoDigitMonthListNumber == null) {
			twoDigitMonthListNumber = new Option[12];
			String n = null;
			for (int i = 0; i < 12; i++) {
				n = String.valueOf(i + 1);
				if (i + 1 < 10) {
					twoDigitMonthListNumber[i] = new Option(n, "0" + n);
				} else {
					twoDigitMonthListNumber[i] = new Option(n, n);
				}
			}
		}
		return twoDigitMonthListNumber;
	}

	public Option[] getTwoDigitMonthListNumberWithLabel() {
		if (twoDigitMonthListNumberWithLabel == null) {
			twoDigitMonthListNumberWithLabel = new Option[12];
			String n = null;
			for (int i = 0; i < 12; i++) {
				n = String.valueOf(i + 1);
				if (i + 1 < 10) {
					twoDigitMonthListNumberWithLabel[i] = new Option("CY " + n, "0" + n);
				} else {
					twoDigitMonthListNumberWithLabel[i] = new Option("CY " + n, n);
				}
			}
		}
		return twoDigitMonthListNumberWithLabel;
	}

	public Option[] getPeriodMonthList() {
		if (periodMonthList == null) {
			periodMonthList = new Option[13];
			String n = null;
			for (int i = 0; i < 13; i++) {
				n = String.valueOf(i + 1);
				periodMonthList[i] = new Option(n, n);
			}
		}
		return periodMonthList;
	}

	public Option[] getYearList() {
		if (yearList == null) {
			yearList = new Option[40];
			String n = null;
			for (int i = 0; i <= 39; i++) {
				if (i >= 9) {
					n = "20" + String.valueOf(i + 1);
				} else {
					n = "200" + String.valueOf(i + 1);
				}
				yearList[i] = new Option(n, n);
			}
		}
		return yearList;
	}

	@StrutsParameter
	public void setReturnLocation(String returnLocation) {
		this.returnLocation = returnLocation;
	}

	public String getReturnLocation() {
		return returnLocation != null ? returnLocation : "";
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public String getModelSelected() {
		return modelSelected != null ? modelSelected : "";
	}

	public void setModels(Vector models) {
		this.models = models;
	}

	public Vector getModels() {
		return models != null ? models : new Vector();
	}

	public void setClosedModels(Vector closedModels) {
		this.closedModels = closedModels;
	}

	public Vector getClosedModels() {
		return closedModels != null ? closedModels : new Vector();
	}

	public void setModelType(abbott.ai.tcgm.entities.TCGMModel.Type modelType) {
		this.modelType = modelType;
	}

	public abbott.ai.tcgm.entities.TCGMModel.Type getModelType() {
		return this.modelType;
	}

	public String getRowToCopy() {
		return rowToCopy != null ? rowToCopy : "";
	}

	@StrutsParameter
	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
	}

	public int getRowToCopyInt() {
		return Integer.parseInt(this.getRowToCopy());
	}

	public String getShowModels() {
		return showModels != null ? showModels : "";
	}

	@StrutsParameter
	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	public Option[] getExportSystemList() {
		if (exportSystemList == null) {
			exportSystemList = new Option[4];
			exportSystemList[0] = new Option("RGM", "RGM");
			exportSystemList[1] = new Option("RTC", "RTC");
			exportSystemList[2] = new Option("RBB", "RBB");
			exportSystemList[3] = new Option("RBL", "RBL");
		}
		return exportSystemList;
	}

	public String getFocusField() {
		return focusField != null ? focusField : "";
	}

	@StrutsParameter
	public void setFocusField(String focusField) {
		this.focusField = focusField;
	}
}
