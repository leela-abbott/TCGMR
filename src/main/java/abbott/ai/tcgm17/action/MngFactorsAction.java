package abbott.ai.tcgm17.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.ReportPrintRequest;
import abbott.ai.tcgm.entities.ReportRestriction;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.helpers.RateDataMngr;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.process.JobDefinition;
import abbott.ai.tcgm.process.JobInstance;

/**
 * <p>
 * Title: TCGM
 * </p>
 * <p>
 * Description: Migrated to Struts 7.3.0, JDK 17, Tomcat 10 (Strict
 * org.apache.struts2 API)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002, 2026
 * </p>
 * 
 * @version 7.3.0
 */
public class MngFactorsAction extends TCGMAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MngFactorsAction.class);

	// ==========================================
	// Merged Form Fields & UI Variables
	// ==========================================
	private String selBegFactorPeriod;
	private String selFactorRate;
	private String planUnitsSelected;
	private String actualUnitsSelected;
	private Vector<?> unitSets;
	private Vector<?> rateSets;
	private Vector<?> exportSystemSets;
	private String selCostRate;
	private String selRevisionRate;
	private boolean rgmSystemSelected;
	private boolean rtcSystemSelected;
	private boolean rbbSystemSelected;
	private boolean rblSystemSelected;
	private boolean ccsSystemSelected;
	private String ccsType;
	private String lastExSavePeriod13FactorsDt;
	private String modelSelected = "";

	private String cmd;
	private boolean chkRestrict;

	private java.util.Vector reportList;
	private String selReport;
	private static Vector<Option> destinationList;
	private String selReportDest;
	private boolean chkImmediate;
	private String[] operations;

	private ReportRestriction restrictions;
	private String numCopies;
	private String formHandler;
	private String jobName;
	private String modelLongDesc = "";
	private String memo = "";
	private boolean restrictionEntered = false;

	public MngFactorsAction() {
		super();

		if (destinationList == null) {
			destinationList = new Vector<Option>(3);
			destinationList.add(new Option("Run Only", "0"));
			destinationList.add(new Option("Generate Report", "G"));
			destinationList.add(new Option("Generate & Print Report", "GP"));
		}

		this.resetRestrictions();
		this.operations = ReportRestriction.Operations;
	}

	// ==========================================
	// Execution Entry Point
	// ==========================================
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		try {
			if (isSessionValid(request) && isModelSelected(request)) {
				clearActionErrors();
				clearMessages();

				if (isCmdValid()) {
					if (this.isChkRestrict() && !this.isRestrictionEntered()) {
						session.setAttribute("restrictionForm", this);
						return "restrictions";
					} else {
						processCmd(request);
						this.reset();
						this.setupForm(request);
						return SUCCESS;
					}
				} else {
					this.setupForm(request);
					return SUCCESS;
				}
			}
		} catch (TCGMException ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			return ERROR;
		}

		return SUCCESS;
	}

	private boolean isCmdValid() {
		return !TCGMUtil.isEmpty(getCmd()) && !"CANCEL".equals(getCmd());
	}

	private void processCmd(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		TCGMState state = this.getState(request);
		ProcessMngr pm = new ProcessMngr();
		boolean selected = true;
		ModelMngr mm = new ModelMngr();
		FactorModel fm = (FactorModel) mm.getModelFromId(ut, state.getCurrentModelId(), TCGMModel.Type.FACTOR);

		if (cmd == null)
			return;

		if (cmd.equalsIgnoreCase("SET_FACTOR_MODEL_PARMS")) {
			fm.setBegFactorPeriod(this.getSelBegFactorPeriod());
			fm.setActualUnitsId(this.getActualUnitsSelected());
			fm.setPlanUnitsId(this.getPlanUnitsSelected());
			fm.setRateSetFactorActualId(this.getSelFactorRate());
			fm.setRateSetCostId(this.getSelCostRate());
			fm.setRateSetRevisionId(this.getSelRevisionRate());

			fm.setExportRgmSystem(this.getRgmSystemSelected() ? "Y" : "N");
			fm.setExportRtcSystem(this.getRtcSystemSelected() ? "Y" : "N");
			fm.setExportRbbSystem(this.getRbbSystemSelected() ? "Y" : "N");
			fm.setExportRblSystem(this.getRblSystemSelected() ? "Y" : "N");

			fm.setExportCcsType(this.getCcsType());
			fm.setMemo(this.getModelLongDesc());
			mm.updateModel(ut, fm);

		} else if (cmd.equalsIgnoreCase("COMP_MODEL")) {
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
			md.setModelParmII(-1, "TCGM_IPS_PRICE_COMPARISON", state.getCurrentModelIdString());

			// FIXED: Look up success.modelcomp.add message via native resource bundle text
			// resolution mechanics
			String compMsg = getText("success.modelcomp.add", new String[] { state.getCurrentModelName() });
			addActionMessage(compMsg);

		} else if (cmd.equalsIgnoreCase("ADD_JOB")) {
			Map<String, Object> jobDetails = new HashMap<>();
			jobDetails.put("jobName", getJobName());
			jobDetails.put("SelReportDest", getSelReportDest());
			jobDetails.put("RestrictionStr", getRestrictionStr());
			jobDetails.put("ChkImmediate", isChkImmediate());
			JobInstance job = pm.createJob(state.getCurrentModelIdString(), "-1", jobDetails);
			job.getJobDef().populateDetail();
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + fm.getName());

			if (job.getJobDef().equals(JobDefinition.CALCULATE_FACTORS)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(fm.PN_BEG_FACTOR_PERIOD, fm.getBegFactorPeriod());
				job.addJobParm(fm.PN_ACT_UNITS, fm.getActualUnitsId());
				job.addJobParm(fm.PN_PLAN_UNITS, fm.getPlanUnitsId());
				job.addJobParm(fm.PN_RATESET_FACTOR_ACTUAL, fm.getRateSetFactorActualId());
				job.addJobParm(fm.PN_RATESET_COST, fm.getRateSetCostId());
				job.addJobParm(fm.PN_RATESET_REVISION, fm.getRateSetRevisionId());
				job.addJobParm("DIS_VERSION", fm.getDesc());

				String rgmVal = this.getRgmSystemSelected() ? "Y" : "N";
				job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, rgmVal);
				md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RGM, rgmVal);

				String rtcVal = this.getRtcSystemSelected() ? "Y" : "N";
				job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, rtcVal);
				md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RTC, rtcVal);

				String rbbVal = this.getRbbSystemSelected() ? "Y" : "N";
				job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, rbbVal);
				md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RBB, rbbVal);

				String rblVal = this.getRblSystemSelected() ? "Y" : "N";
				job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, rblVal);
				md.setModelParmII(state.getCurrentModelId(), JobConstants.PN_R_SYSTEM_RBL, rblVal);

			} else if (job.getJobDef().equals(JobDefinition.APPLY_MAINTENANCE)) {
				String rateTranId = Integer.toString(new RateDataMngr().getPendingRateDataTranId(ut));
				job.addJobParm(JobConstants.PN_RATE_DATA_T_ID, rateTranId);
				job.addJobParm("DIS_VERSION", fm.getDesc());
				RateDataMngr rdm = new RateDataMngr();
				DatasetMngr dsm = new DatasetMngr();
				int pendingRateSetId = rdm.getPendingRateDataTranId(ut);
				Dataset ds = dsm.getDatasetById(ut, pendingRateSetId);
				if (pendingRateSetId == -1) {
					job.addJobParm(JobConstants.PN_RATE_SET, "NO RATESET TRANS");
				} else {
					job.addJobParm(JobConstants.PN_RATE_SET, ds.getDatasetName().trim());
				}

			} else if (job.getJobDef().equals(JobDefinition.SEND_FACTORS)
					|| job.getJobDef().equals(JobDefinition.SEND_ALL_FACTORS)) {
				selected = false;
				if (this.getRgmSystemSelected()) {
					fm.setExportRgmSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "Y");
					selected = true;
				} else {
					fm.setExportRgmSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RGM, "N");
				}
				if (this.getRtcSystemSelected()) {
					fm.setExportRtcSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "Y");
					selected = true;
				} else {
					fm.setExportRtcSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RTC, "N");
				}

				if (this.getRbbSystemSelected()) {
					fm.setExportRbbSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "Y");
					selected = true;
				} else {
					fm.setExportRbbSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBB, "N");
				}

				if (this.getRblSystemSelected()) {
					fm.setExportRblSystem("Y");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "Y");
					selected = true;
				} else {
					fm.setExportRblSystem("N");
					job.addJobParm(JobConstants.PN_R_SYSTEM_RBL, "N");
				}

				if (!selected) {
					String failureMsg = getText("failure.checkbox.job.add",
							new String[] { job.getJobDef().getJobName() });
					addActionError(failureMsg);
				} else {
					mm.updateModel(ut, fm);
				}

			} else if (job.getJobDef().equals(JobDefinition.SEND_CCSH)) {
				job.addJobParm(JobConstants.PN_R_CCS_TYPE, this.getCcsType());
				fm.setExportCcsType(this.getCcsType());
				mm.setModelParmII(ut, fm, JobConstants.PN_R_CCS_TYPE, this.getCcsType());

			} else if (job.getJobDef().equals(JobDefinition.ASR_TREE)
					|| job.getJobDef().equals(JobDefinition.PRINT_BPCOST)
					|| job.getJobDef().equals(JobDefinition.PRINT_EXCEPT)
					|| job.getJobDef().equals(JobDefinition.BP_VS_TPSS_RPT1_PRE)
					|| job.getJobDef().equals(JobDefinition.DUP_RPT01)
					|| job.getJobDef().equals(JobDefinition.BUILD_ASR_TREE)
					|| job.getJobDef().equals(JobDefinition.PRINT_EXRATE)) {
				job.addJobParm("DIS_VERSION", fm.getDesc());
			} else if (job.getJobDef().equals(JobDefinition.PRINT_VAL_AD)
					|| job.getJobDef().equals(JobDefinition.PRINT_DUB_SUP)
					|| job.getJobDef().equals(JobDefinition.MISS_FACTOR)
					|| job.getJobDef().equals(JobDefinition.PRINT_INVCUR)
					|| job.getJobDef().equals(JobDefinition.BLNDD_SUMMRY)
					|| job.getJobDef().equals(JobDefinition.PRINT_BP_FRZ)
					|| job.getJobDef().equals(JobDefinition.PRINT_MISM)
					|| job.getJobDef().equals(JobDefinition.AUDIT_FACTORS) || job.getJobDef().equals("DELETE_AUDIT")) {
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc());
			}

			if (selected) {
				pm.addJob(job, ut);
				String successMsg = getText("success.job.add", new String[] { job.getJobDef().getJobName() });
				addActionMessage(successMsg);
			} else {
				ServletActionContext.getRequest().setAttribute("forward_override", TCGMConstants.FORWARD_FAILURE);
			}
		}
	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		int modelId = this.getState(request).getCurrentModelId();
		ModelMngr mm = new ModelMngr();
		FactorModel model = (FactorModel) mm.getModelFromId(ut, modelId, TCGMModel.Type.FACTOR);

		this.reset(model);

		String descr = mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, "MEMO");
		if (descr == null || descr.equals("")) {
			this.modelLongDesc = model.getDesc();
		} else {
			this.modelLongDesc = descr;
		}

		String excDate = mm.getModelParmValue(ut, modelId, TCGMModel.Type.FACTOR, "SVD_FCTR_DT");
		TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
		long rawOffset = tz.getRawOffset();
		SimpleDateFormat sdf = new SimpleDateFormat(AppConst.getTcgmDateFormat());

		if (tz.inDaylightTime(new Date())) {
			rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
		}

		if (excDate != null && !excDate.trim().equals("")) {
			@SuppressWarnings("deprecation")
			long time = new Date(excDate).getTime();
			long newTime = time;
			if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT)) {
				newTime += rawOffset;
			}
			excDate = sdf.format(new Date(newTime));
		} else {
			excDate = " ";
		}

		this.setLastExSavePeriod13FactorsDt("Last saved: " + excDate);

		DatasetMngr dm = new DatasetMngr();
		this.setUnitSets(dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA));
		this.setRateSets(dm.getDatasetByTableName(ut, DBConst.TABLE_RATE_DATA));

		this.setRgmSystemSelected("Y".equals(model.getExportRgmSystem()));
		this.setRtcSystemSelected("Y".equals(model.getExportRtcSystem()));
		this.setRbbSystemSelected("Y".equals(model.getExportRbbSystem()));
		this.setRblSystemSelected("Y".equals(model.getExportRblSystem()));

		this.setCcsType(model.getExportCcsType());
		this.setSelBegFactorPeriod(model.getBegFactorPeriod());

		request.getSession().setAttribute("mngFactorsForm", this);
	}

	private void resetRestrictions() {
		restrictions = new ReportRestriction();
	}

	public Vector getReportList() {
		return reportList;
	}

	public void setReportList(Vector reportList) {
		this.reportList = reportList;
	}

	public void setSelReport(String selReport) {
		this.selReport = selReport;
	}

	public String getSelReport() {
		return selReport;
	}

	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
	}

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	public void setChkRestrict(boolean chkRestrict) {
		this.chkRestrict = chkRestrict;
	}

	public boolean isChkRestrict() {
		return chkRestrict;
	}

	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}

	public String getSelReportDest() {
		return selReportDest;
	}

	public void setDestinationList(Vector destinationList) {
		this.destinationList = destinationList;
	}

	public Vector getDestinationList() {
		return destinationList;
	}

	public void setOperations(String[] operations) {
		this.operations = operations;
	}

	public String[] getOperations() {
		return operations;
	}

	public ReportRestriction getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(ReportRestriction restrictions) {
		this.restrictions = restrictions;
	}

	public void setNumCopies(String numCopies) {
		this.numCopies = numCopies;
	}

	public String getNumCopies() {
		return numCopies;
	}

	public String getRestrictionStr() {
		StringBuffer sb = new StringBuffer();
		if (!restrictions.getRptAffRestriction().equals("")) {
			sb.append(" | " + restrictions.getRptAffRestriction());
		}
		if (!restrictions.getSupAffRestriction().equals("")) {
			sb.append(" | " + restrictions.getSupAffRestriction());
		}
		if (!restrictions.getEndAffRestriction().equals("")) {
			sb.append(" | " + restrictions.getEndAffRestriction());
		}
		if (!restrictions.getRptListRestriction().equals("")) {
			sb.append(" | " + restrictions.getRptListRestriction());
		}
		if (!restrictions.getSupListRestriction().equals("")) {
			sb.append(" | " + restrictions.getSupListRestriction());
		}
		if (!restrictions.getEndListRestriction().equals("")) {
			sb.append(" | " + restrictions.getEndListRestriction());
		}
		if (!restrictions.getGroupIdLimiterValues().equals("")) {
			sb.append(" | " + restrictions.getGroupIdLimiterValues());
		}

		if (sb.toString().length() > 2)
			return sb.toString().substring(2);
		else
			return sb.toString();
	}

	public ReportPrintRequest getReportPrintRequest() {
		ReportPrintRequest rpr = new ReportPrintRequest();
		rpr.setReportId(this.getSelReport());
		rpr.setReportDest(this.getSelReportDest());
		rpr.setNumCopies(this.getNumCopies());
		return rpr;
	}

	public void setFormHandler(String formHandler) {
		this.formHandler = formHandler;
	}

	public String getFormHandler() {
		return formHandler;
	}

	public void setJobName(String jobName) {
		if (jobName != null && jobName.contains(",")) {
			this.jobName = jobName.split(",")[0].trim();
		} else {
			this.jobName = jobName;
		}
	}

	public String getJobName() {
		if (jobName != null && jobName.contains(",")) {
			return jobName.split(",")[0].trim();
		}
		return jobName;
	}

	public void reset() {
		logger.debug("Reset being called");
		this.setCmd("");
		this.setJobName("");
		this.setRestrictionEntered(false);
		this.resetRestrictions();
		this.setModelSelected(getModelSelected());
	}

	public void setRestrictionEntered(boolean restrictionEntered) {
		this.restrictionEntered = restrictionEntered;
	}

	public boolean isRestrictionEntered() {
		return restrictionEntered;
	}

	public String getMemo() {
		return memo;
	}

	public String getModelLongDesc() {
		return modelLongDesc;
	}

	public void setSelBegFactorPeriod(String selBegFactorPeriod) {
		this.selBegFactorPeriod = selBegFactorPeriod;
	}

	public String getSelBegFactorPeriod() {
		return selBegFactorPeriod;
	}

	public String getSelFactorRate() {
		return selFactorRate;
	}

	public void setSelFactorRate(String selFactorRate) {
		this.selFactorRate = selFactorRate;
	}

	public void setPlanUnitsSelected(String planUnitsSelected) {
		this.planUnitsSelected = planUnitsSelected;
	}

	public String getPlanUnitsSelected() {
		return planUnitsSelected;
	}

	public void setActualUnitsSelected(String actualUnitsSelected) {
		this.actualUnitsSelected = actualUnitsSelected;
	}

	public String getActualUnitsSelected() {
		return actualUnitsSelected;
	}

	public void setUnitSets(java.util.Vector unitSets) {
		this.unitSets = unitSets;
	}

	public java.util.Vector getUnitSets() {
		return unitSets;
	}

	public void setRateSets(java.util.Vector rateSets) {
		this.rateSets = rateSets;
	}

	public java.util.Vector getRateSets() {
		return rateSets;
	}

	public void setExportSystemSets(java.util.Vector exportSystemSets) {
		this.rateSets = exportSystemSets;
	}

	public java.util.Vector getExportSystemSets() {
		return exportSystemSets;
	}

	public void setSelCostRate(String selCostRate) {
		this.selCostRate = selCostRate;
	}

	public String getSelCostRate() {
		return selCostRate;
	}

	public void setSelRevisionRate(String selRevisionRate) {
		this.selRevisionRate = selRevisionRate;
	}

	public String getSelRevisionRate() {
		return selRevisionRate;
	}

	public boolean isRgmSystemSelected() {
		return this.rgmSystemSelected;
	}

	public void setRgmSystemSelected(boolean rgmSystemSelected) {
		this.rgmSystemSelected = rgmSystemSelected;
	}

	public boolean getRgmSystemSelected() {
		return rgmSystemSelected;
	}

	public boolean isRtcSystemSelected() {
		return this.rtcSystemSelected;
	}

	public void setRtcSystemSelected(boolean rtcSystemSelected) {
		this.rtcSystemSelected = rtcSystemSelected;
	}

	public boolean getRtcSystemSelected() {
		return rtcSystemSelected;
	}

	public boolean isRbbSystemSelected() {
		return this.rbbSystemSelected;
	}

	public void setRbbSystemSelected(boolean rbbSystemSelected) {
		this.rbbSystemSelected = rbbSystemSelected;
	}

	public boolean getRbbSystemSelected() {
		return rbbSystemSelected;
	}

	public boolean isRblSystemSelected() {
		return this.rblSystemSelected;
	}

	public void setRblSystemSelected(boolean rblSystemSelected) {
		this.rblSystemSelected = rblSystemSelected;
	}

	public boolean getRblSystemSelected() {
		return rblSystemSelected;
	}

	public void reset(FactorModel myModel) {
		this.reset();
		this.setSelBegFactorPeriod(myModel.getBegFactorPeriod());
		this.setActualUnitsSelected(myModel.getActualUnitsId());
		this.setPlanUnitsSelected(myModel.getPlanUnitsId());
		this.setSelFactorRate(myModel.getRateSetFactorActualId());
		this.setSelCostRate(myModel.getRateSetCostId());
		this.setSelRevisionRate(myModel.getRateSetRevisionId());
	}

	public String getLastExSavePeriod13FactorsDt() {
		return lastExSavePeriod13FactorsDt;
	}

	public void setLastExSavePeriod13FactorsDt(String string) {
		lastExSavePeriod13FactorsDt = string;
	}

	public boolean isCcsSystemSelected() {
		return ccsSystemSelected;
	}

	public void setCcsSystemSelected(boolean b) {
		ccsSystemSelected = b;
	}

	public boolean getCcsSystemSelected() {
		return this.ccsSystemSelected;
	}

	public String getCcsType() {
		return ccsType;
	}

	public void setCcsType(String string) {
		ccsType = string;
	}

	public void setCmd(String cmd) {
		if (cmd != null && cmd.contains(",")) {
			this.cmd = cmd.split(",")[0].trim();
		} else {
			this.cmd = cmd;
		}
	}
	
	public String getCmd() {
		if (cmd != null && cmd.contains(",")) {
			return cmd.split(",")[0].trim();
		}
		return cmd;
	}

	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public String getModelSelected() {
		if (this.modelSelected == null) {
			this.modelSelected = "";
		}
		return this.modelSelected;
	}
}
