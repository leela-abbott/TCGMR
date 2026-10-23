package abbott.ai.tcgm17.action;

import java.io.IOException;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.process.*;

public class MiscReportsAction extends TCGMAction {

	private static final long serialVersionUID = 1L;
	private String cmd;
	private boolean chkRestrict;
	private boolean restrictionEntered;
	private String interCoTransferPeriod;
	private String selComparisonModel;
	private String comparisonPercent;
	private String selRoutingStartPeriod;
	private String selRoutingStartYear;
	private String selRoutingEndPeriod;
	private String selRoutingEndYear;
	private Vector factorModels;
	private Vector unitSets;
	private String endRouteUnitsSelected;
	private String begRouteUnitsSelected;
	private String treeFilename;
	private String actualUnitsSelected;

	private Vector reportList;
	private String selReport;
	private static Vector destinationList;
	private String selReportDest;
	private boolean chkImmediate;
	private String[] operations;
	private ReportRestriction restrictions;
	private String numCopies;
	private String formHandler;
	private String jobName;
	private String modelLongDesc = "";
	private String memo = "";

	public MiscReportsAction() {
		super();
		if (destinationList == null) {
			destinationList = new Vector(3);
			destinationList.add(new Option("Run Only", "0"));
			destinationList.add(new Option("Generate Report", "G"));
			destinationList.add(new Option("Generate & Print Report", "GP"));
		}
		this.restrictions = new ReportRestriction();
		this.operations = ReportRestriction.Operations;
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.clearErrors();
		try {
			if (this.isSessionValid(request) && this.isModelSelected(request)) {
				UserToken ut = this.getUserToken(request);
				this.clearErrors();
				if (this.cmd != null && !TCGMUtil.isEmpty(this.cmd) && !this.cmd.equals("CANCEL")) {
					if (this.isChkRestrict() && !this.isRestrictionEntered()) {
						request.getSession().setAttribute("restrictionForm", this);
						this.setForward(TCGMConstants.G_FORWARD_RESTRICTIONS);
					} else {
						processCmd(request);
						this.setupForm(request);
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
				} else {
					this.setupForm(request);
					this.setForward(TCGMConstants.FORWARD_INPUT);
				}
			}
		} catch (TCGMException ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward());
		return this.getForward();
	}

	private void processCmd(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
		FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId),
				TCGMModel.Type.FACTOR);
		if (this.cmd.equalsIgnoreCase("ADD_JOB")) {
			TCGMProductionForm legacyFormAdapter = new TCGMProductionForm() {
				{
					setCmd(MiscReportsAction.this.getCmd());
					setChkRestrict(MiscReportsAction.this.isChkRestrict());
					setRestrictionEntered(MiscReportsAction.this.isRestrictionEntered());
					setInterCoTransferPeriod(MiscReportsAction.this.getInterCoTransferPeriod());
					setSelComparisonModel(MiscReportsAction.this.getSelComparisonModel());
					setComparisonPercent(MiscReportsAction.this.getComparisonPercent());
					setSelRoutingStartPeriod(MiscReportsAction.this.getSelRoutingStartPeriod());
					setSelRoutingStartYear(MiscReportsAction.this.getSelRoutingStartYear());
					setSelRoutingEndPeriod(MiscReportsAction.this.getSelRoutingEndPeriod());
					setSelRoutingEndYear(MiscReportsAction.this.getSelRoutingEndYear());
					setEndRouteUnitsSelected(MiscReportsAction.this.getEndRouteUnitsSelected());
					setBegRouteUnitsSelected(MiscReportsAction.this.getBegRouteUnitsSelected());
					setTreeFilename(MiscReportsAction.this.getTreeFilename());
					setActualUnitsSelected(MiscReportsAction.this.getActualUnitsSelected());
					setReportList(MiscReportsAction.this.getReportList());
					setSelReport(MiscReportsAction.this.getSelReport());
					setDestinationList(MiscReportsAction.this.getDestinationList());
					setSelReportDest(MiscReportsAction.this.getSelReportDest());
					setChkImmediate(MiscReportsAction.this.isChkImmediate());
					setOperations(MiscReportsAction.this.getOperations());
					setRestrictions(MiscReportsAction.this.getRestrictions());
					setNumCopies(MiscReportsAction.this.getNumCopies());
					setFormHandler(MiscReportsAction.this.getFormHandler());
					setJobName(MiscReportsAction.this.getJobName());
					setModelLongDesc(MiscReportsAction.this.getModelLongDesc());
					setMemo(MiscReportsAction.this.getMemo());
					if (MiscReportsAction.this.getFactorModels() != null) {
						setFactorModels(new Vector<>(MiscReportsAction.this.getFactorModels()));
					}
					if (MiscReportsAction.this.getUnitSets() != null) {
						setUnitSets(new Vector<>(MiscReportsAction.this.getUnitSets()));
					}
				}
			};
			ProcessMngr pm = new ProcessMngr();
			JobInstance job = pm.createJob(modelId, "-1", legacyFormAdapter);
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + fm.getName());
			if (job.getJobDef().equals(JobDefinition.PRINT_VAL_AD)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.PRINT_DUB_SUP)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.MISS_FACTOR)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.PRINT_INVCUR)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.BPC_PRT0BP)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.AUDIT_FACTORS)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.BPC_DELETE)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
				job.addJobParm(JobConstants.PN_ACTUAL_USER, ut.getUserid());
			} else if (job.getJobDef().equals(JobDefinition.BLNDD_SUMMRY)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.BPC_PRT0CST)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.PRINT_BP_FRZ)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.PRINT_MISM)) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.BPC_0PLNVSIM)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_MOD_VERSION, modelId);
				job.addJobParm(JobConstants.PN_OLD_VERSION, this.getSelComparisonModel());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_OLD_VERSION, this.getSelComparisonModel());
				ModelMngr mm = new ModelMngr();
				job.addJobParm(JobConstants.PN_MOD_VERSION_NAME, mm.getModelName(ut, Integer.parseInt(modelId)));
				job.addJobParm(JobConstants.PN_OLD_VERSION_NAME,
						mm.getModelName(ut, Integer.parseInt(this.getSelComparisonModel())));
			} else if (job.getJobDef().equals(JobDefinition.ROUTE_EXCEPT)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_ROU_PERIOD_START, this.getSelRoutingStartPeriod());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_PERIOD_START,
						this.getSelRoutingStartPeriod());
				job.addJobParm(JobConstants.PN_ROU_YEAR_START, this.getSelRoutingStartYear());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_START,
						this.getSelRoutingStartYear());
				job.addJobParm(JobConstants.PN_ROU_PERIOD_END, this.getSelRoutingEndPeriod());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_PERIOD_END,
						this.getSelRoutingEndPeriod());
				job.addJobParm(JobConstants.PN_ROU_YEAR_END, this.getSelRoutingEndYear());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_END, this.getSelRoutingEndYear());
				job.addJobParm(JobConstants.PN_ROU_YEAR_ACT_UNITS_BEG, this.getBegRouteUnitsSelected());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_ACT_UNITS_BEG,
						this.getBegRouteUnitsSelected());
				job.addJobParm(JobConstants.PN_ROU_YEAR_ACT_UNITS_END, this.getEndRouteUnitsSelected());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_ROU_YEAR_ACT_UNITS_END,
						this.getEndRouteUnitsSelected());
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
				job.addJobParm(JobConstants.PN_FROM_PERIOD, this.getSelRoutingStartPeriod());
				job.addJobParm(JobConstants.PN_THRU_PERIOD, this.getSelRoutingEndPeriod());
			} else if (job.getJobDef().equals(JobDefinition.BPC_ANALYS)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm("INP_PERIOD", this.getInterCoTransferPeriod());
				md.setModelParmII(Integer.parseInt(modelId), "INP_PERIOD", this.getInterCoTransferPeriod());
				job.addJobParm(JobConstants.PN_TREE_FILENAME, this.getTreeFilename());
				if (this.getTreeFilename() != null) {
					this.setTreeFilename(this.getTreeFilename().toUpperCase());
					if (this.getTreeFilename().length() > 8) {
						this.setTreeFilename(this.getTreeFilename().substring(0, 8));
					}
					int i = 0;
					while (this.getTreeFilename().length() < 8) {
						if (i == 0) {
							this.setTreeFilename(this.getTreeFilename() + TCGMConstants.DT_COMMA);
						} else {
							this.setTreeFilename(this.getTreeFilename() + TCGMConstants.DT_BLANK_SPACE);
						}
						i++;
					}
				} else {
					this.setTreeFilename("        ");
				}
				md.setModelParmII(-1, TCGMConstants.DT_INTER_COMP_TRSFR,
						this.getTreeFilename() + TCGMConstants.DT_HYPHEN_DELIMITER + this.getInterCoTransferPeriod()
								+ TCGMConstants.DT_DELIMITER);
				job.addJobParm(JobConstants.PN_RPT_ACT_UNITS, this.getActualUnitsSelected());
				md.setModelParmII(Integer.parseInt(modelId), JobConstants.PN_RPT_ACT_UNITS,
						this.getActualUnitsSelected());
			} else if (job.getJobDef().equals(JobDefinition.ASR_DELETE)
					|| job.getJobDef().equals(JobDefinition.ASR_DLT_PART)) {
				job.addJobParm(JobConstants.PN_ACTUAL_USER, ut.getUserid());
			}
			pm.addJob(job, ut);
			this.addActionMessage("success.job.add: " + job.getJobDef().getJobName());
		}
	}

	public void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		int modelId = this.getState(request).getCurrentModelId();
		ModelMngr mm = new ModelMngr();
		this.setActualUnitsSelected(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_RPT_ACT_UNITS));
		this.setInterCoTransferPeriod(mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, "INP_PERIOD"));
		this.setSelRoutingStartPeriod(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_PERIOD_START));
		this.setSelRoutingStartYear(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_START));
		this.setSelRoutingEndPeriod(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_PERIOD_END));
		this.setSelRoutingEndYear(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_END));
		this.setBegRouteUnitsSelected(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_ACT_UNITS_BEG));
		this.setEndRouteUnitsSelected(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_ROU_YEAR_ACT_UNITS_END));
		this.setSelComparisonModel(
				mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, JobConstants.PN_OLD_VERSION));
		this.setFactorModels(mm.getModels(ut, new FactorModel()));
		this.setUnitSets(dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA));
		request.getSession(false).setAttribute("MiscReportsActionForm", this);
	}

	public Option[] getMonthListNumber() {
		return new TCGMProductionForm().getMonthListNumber();
	}

	public Option[] getYearList() {
		return new TCGMProductionForm().getYearList();
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public boolean isChkRestrict() {
		return chkRestrict;
	}

	@StrutsParameter
	public void setChkRestrict(boolean chkRestrict) {
		this.chkRestrict = chkRestrict;
	}

	public boolean isRestrictionEntered() {
		return restrictionEntered;
	}

	@StrutsParameter
	public void setRestrictionEntered(boolean restrictionEntered) {
		this.restrictionEntered = restrictionEntered;
	}

	public String getInterCoTransferPeriod() {
		return interCoTransferPeriod;
	}

	@StrutsParameter
	public void setInterCoTransferPeriod(String interCoTransferPeriod) {
		this.interCoTransferPeriod = interCoTransferPeriod;
	}

	public String getSelComparisonModel() {
		return selComparisonModel;
	}

	@StrutsParameter
	public void setSelComparisonModel(String selComparisonModel) {
		this.selComparisonModel = selComparisonModel;
	}

	public String getComparisonPercent() {
		return comparisonPercent;
	}

	@StrutsParameter
	public void setComparisonPercent(String comparisonPercent) {
		this.comparisonPercent = comparisonPercent;
	}

	public String getSelRoutingStartPeriod() {
		return selRoutingStartPeriod;
	}

	@StrutsParameter
	public void setSelRoutingStartPeriod(String selRoutingStartPeriod) {
		this.selRoutingStartPeriod = selRoutingStartPeriod;
	}

	public String getSelRoutingStartYear() {
		return selRoutingStartYear;
	}

	@StrutsParameter
	public void setSelRoutingStartYear(String selRoutingStartYear) {
		this.selRoutingStartYear = selRoutingStartYear;
	}

	public String getSelRoutingEndPeriod() {
		return selRoutingEndPeriod;
	}

	@StrutsParameter
	public void setSelRoutingEndPeriod(String selRoutingEndPeriod) {
		this.selRoutingEndPeriod = selRoutingEndPeriod;
	}

	public String getSelRoutingEndYear() {
		return selRoutingEndYear;
	}

	@StrutsParameter
	public void setSelRoutingEndYear(String selRoutingEndYear) {
		this.selRoutingEndYear = selRoutingEndYear;
	}

	public Vector getFactorModels() {
		return factorModels;
	}

	@StrutsParameter(depth = 1)
	public void setFactorModels(Vector factorModels) {
		this.factorModels = factorModels;
	}

	public Vector getUnitSets() {
		return unitSets;
	}

	@StrutsParameter(depth = 1)
	public void setUnitSets(Vector unitSets) {
		this.unitSets = unitSets;
	}

	public String getEndRouteUnitsSelected() {
		return endRouteUnitsSelected;
	}

	@StrutsParameter
	public void setEndRouteUnitsSelected(String endRouteUnitsSelected) {
		this.endRouteUnitsSelected = endRouteUnitsSelected;
	}

	public String getBegRouteUnitsSelected() {
		return begRouteUnitsSelected;
	}

	@StrutsParameter
	public void setBegRouteUnitsSelected(String begRouteUnitsSelected) {
		this.begRouteUnitsSelected = begRouteUnitsSelected;
	}

	public String getTreeFilename() {
		return treeFilename;
	}

	@StrutsParameter
	public void setTreeFilename(String treeFilename) {
		this.treeFilename = treeFilename;
	}

	public String getActualUnitsSelected() {
		return actualUnitsSelected;
	}

	@StrutsParameter
	public void setActualUnitsSelected(String actualUnitsSelected) {
		this.actualUnitsSelected = actualUnitsSelected;
	}

	public Vector getReportList() {
		return reportList;
	}

	@StrutsParameter(depth = 1)
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

	@StrutsParameter(depth = 1)
	public void setDestinationList(Vector destinationList) {
		MiscReportsAction.destinationList = destinationList;
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

	@StrutsParameter
	public void setOperations(String[] operations) {
		this.operations = operations;
	}

	public ReportRestriction getRestrictions() {
		return restrictions;
	}

	@StrutsParameter
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

	public String getJobName() {
		return jobName;
	}

	@StrutsParameter
	public void setJobName(String jobName) {
		this.jobName = jobName;
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
}
