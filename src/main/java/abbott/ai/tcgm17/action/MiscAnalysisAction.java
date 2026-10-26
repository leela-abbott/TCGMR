package abbott.ai.tcgm17.action;

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.process.*;

public class MiscAnalysisAction extends TCGMAction implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LogManager.getLogger(MiscAnalysisAction.class);
	private String cmd;
	private String jobName;
	private boolean chkRestrict;
	private boolean restrictionEntered;

	private Vector<?> rateSets;
	private Vector<?> factorModels;
	private Vector<?> unitSets;
	private Vector<String> monthListNumber;

	private String dmSummaryOption;
	private String selScRateSet;
	private String selScSalesId;
	private String selScUnitSet;
	private String selScRatePeriod;
	private String selScFactorModel;

	private String selExUnitSet;
	private String selExPeriod;
	private String selExSalesType;
	private HashMap<?, ?> salesType;
	private String selComparisonModel;
	private String kgaReportOption;

	private String selHedExUnitSet;
	private String selHedExPeriod;
	private String selHedExEndPeriod;

	private String reportNameSuf;
	private String hedgereportNameSuf;
	private HashMap<?, ?> salesList;
	private String[] selSalesList;
	private String autoRun;
	private String selHedExSalesType;
	private String[] selHedSalesList;
	private Map<String, Object> session;
	private String selReportDest;
	private ReportRestriction restrictions;
	private boolean chkImmediate;
	private static Vector<Option> destinationList;
	private String[] operations;
	private String modelSelected;

	public MiscAnalysisAction() {
		super();
		if (destinationList == null) {
			destinationList = new Vector<Option>(3);
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
		try {
			if (this.isSessionValid(request) && this.isModelSelected(request)) {
				this.clearErrorsAndMessages();

				if (cmd != null && !cmd.trim().isEmpty() && !cmd.equalsIgnoreCase("CANCEL")) {
					if (chkRestrict && !restrictionEntered) {
						session.put("restrictionForm", this);
						return "restrictions";
					} else {
						processCmd(request);
						if (this.hasActionErrors()) {
							setupForm(request);
							return INPUT;
						}

						setupForm(request);
						this.reset();
						return SUCCESS;
					}
				} else {
					setupForm(request);
					return INPUT;
				}
			}
		} catch (TCGMException ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			return "exception";
		}
		return SUCCESS;
	}

	private void processCmd(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
		boolean flag = true;

		if ("ADD_JOB".equalsIgnoreCase(cmd)) {
			ModelMngr mm = new ModelMngr();

			TCGMState state = this.getState(request);
			AnalysisModel am = (AnalysisModel) mm.getModelFromId(ut, state.getCurrentModelId(),
					TCGMModel.Type.ANALYSIS);
			ProcessMngr pm = new ProcessMngr();

			if ("SND_NET_ANL".equals(jobName)) {
				String modelDescription = am.getDesc();
				String retVal = "";
				if (modelDescription != null && modelDescription.length() > 50) {
					modelDescription = modelDescription.substring(0, 49);
				}
				retVal = pm.sendNetAnl(ut, am.getModelId(), modelDescription);
				if (retVal.equals("")) {
					this.addActionMessage("success.send.ccs");
				} else {
					this.addActionError("error.send.ccs: " + retVal);
				}
			} else {
				Map<String, Object> jobDetails = new HashMap<>();
				jobDetails.put("jobName", getJobName());
				jobDetails.put("SelReportDest", getSelReportDest());
				jobDetails.put("RestrictionStr", getRestrictionStr());
				jobDetails.put("ChkImmediate", isChkImmediate());
				JobInstance job = pm.createJob(modelId, "-1", jobDetails);

				job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + am.getName());
				DatasetMngr dm = new DatasetMngr();
				String unitSetIdStr = this.selExUnitSet;

				if (job.getJobDef().equals(JobDefinition.KGA_REPORTS)) {
					job.addJobParm(JobConstants.PN_XCHG_UNITS, this.selExUnitSet);
					mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS, this.selExUnitSet);
					job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME,
							dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
					mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME,
							dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, am.getUnitHdr1() != null ? am.getUnitHdr1() : "");
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() != null ? am.getDesc() : "");
				} else if (job.getJobDef().equals(JobDefinition.DFRD_MARGIN)) {
					String safeAutoRun = (this.autoRun != null) ? this.autoRun : "N";
					job.addJobParm("SEND_P34_AUTORUN", safeAutoRun);
					mm.setModelParmII(ut, am, "SEND_P34_AUTORUN", safeAutoRun);

					this.salesType = TCGMUtil.getSortedHashMap(dm.getSalesType(ut));
					this.salesList = TCGMUtil
							.getSortedHashMap(dm.getSalesList(ut, this.selExUnitSet != null ? this.selExUnitSet : ""));

					if (this.selExSalesType == null || "-1".equals(this.selExSalesType)) {
						this.addActionError("No Valid TCGM Sales Types associated with this Units");
						flag = false;
					} else {
						job.addJobParm("DFRD_SLS_TYPE", this.selExSalesType);
						mm.setModelParmII(ut, am, "DFRD_SLS_TYPE", this.selExSalesType);
					}

					StringBuilder slsList = new StringBuilder();
					if (this.selSalesList == null || this.selSalesList.length == 0) {
						this.addActionError("No Valid TCGM Sales List associated with this Units");
						flag = false;
					} else {
						for (String s : this.selSalesList) {
							if (s != null) {
								slsList.append(s).append("|");
							}
						}
						String finalSlsList = slsList.length() > 0 ? slsList.substring(0, slsList.length() - 1) : "";
						job.addJobParm("DFRD_SLS_ID_LIST", finalSlsList);
						mm.setModelParmII(ut, am, "DFRD_SLS_ID_LIST", finalSlsList);
						this.selSalesList = null;
					}

					String safeReportNameSuf = (this.reportNameSuf != null) ? this.reportNameSuf : "";
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, safeReportNameSuf);
					mm.setModelParmII(ut, am, JobConstants.PN_RPT_NAME_SUP, safeReportNameSuf);

					String safeExPeriod = (this.selExPeriod != null) ? this.selExPeriod : "";
					job.addJobParm(JobConstants.PN_INP_PERIOD, safeExPeriod);
					mm.setModelParmII(ut, am, JobConstants.PN_INP_PERIOD, safeExPeriod);

					String safeExUnitSet = (this.selExUnitSet != null) ? this.selExUnitSet : "";
					job.addJobParm(JobConstants.PN_XCHG_UNITS, safeExUnitSet);

					String datasetName = "";
					String titleValue = "Deferred";
					if (unitSetIdStr != null && !unitSetIdStr.trim().isEmpty()) {
						try {
							datasetName = dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName();
							if (datasetName != null) {
								String defErn = datasetName.trim();
								if (defErn.length() >= 4) {
									defErn = defErn.substring(3, 4);
									if ("A".equals(defErn) || "P".equals(defErn) || "U".equals(defErn)) {
										titleValue = "Earned";
									}
								}
							}
						} catch (Exception e) {
							logger.error("Error evaluating dataset definition attributes", e);
						}
					}

					job.addJobParm("DFRD_TITLE", titleValue);
					mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS, safeExUnitSet);
					job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME, datasetName);
					mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME, datasetName);
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() != null ? am.getDesc() : "");
				} else if (job.getJobDef().equals(JobDefinition.DFRD_RPT_COS)) {
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, this.reportNameSuf != null ? this.reportNameSuf : "");
					mm.setModelParmII(ut, am, JobConstants.PN_RPT_NAME_SUP, this.reportNameSuf);
					job.addJobParm(JobConstants.PN_INP_PERIOD, this.selExPeriod);
					mm.setModelParmII(ut, am, JobConstants.PN_INP_PERIOD, this.selExPeriod);
					job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME,
							dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
					mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME,
							dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() != null ? am.getDesc() : "");
				} else if (job.getJobDef().equals(JobDefinition.DFRD_RPT_SRC)
						|| job.getJobDef().equals(JobDefinition.DFRD_RPT_SUM_COS)
						|| job.getJobDef().equals(JobDefinition.DFRD_RPT_SUM_SRC)) {
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, this.reportNameSuf != null ? this.reportNameSuf : "");
					mm.setModelParmII(ut, am, JobConstants.PN_RPT_NAME_SUP, this.reportNameSuf);
					// XCHG_UNITS
					if (this.selExUnitSet != null) {
						job.addJobParm(JobConstants.PN_XCHG_UNITS, this.selExUnitSet);
					} else {
						job.addJobParm(JobConstants.PN_XCHG_UNITS, "");
					}
					// Dataset Name
					job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME,
							dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
					mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME,
							dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
					// INP_PERIOD
					if (this.selExPeriod != null) {
						job.addJobParm(JobConstants.PN_INP_PERIOD, this.selExPeriod);
					} else {
						job.addJobParm(JobConstants.PN_INP_PERIOD, "");
					}
					// DIS_VERSION
					if (am.getDesc() != null) {
						job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc());
					} else {
						job.addJobParm(JobConstants.PN_DIS_VERSION, "");
					}
				}
				/* Exchange Exposure Section */
				else if (job.getJobDef().equals(JobDefinition.XHG_EXPOSURE)
						|| job.getJobDef().equals(JobDefinition.XHG_EXP_DTL)
						|| job.getJobDef().equals(JobDefinition.XHG_ANALYSIS)) {
					unitSetIdStr = this.selHedExUnitSet;

					// Dataset Id (Safe check)
					job.addJobParm("HDGE_UNITS", this.selHedExUnitSet != null ? this.selHedExUnitSet : "");
					if (this.selHedExUnitSet != null) {
						mm.setModelParmII(ut, am, "HDGE_UNITS", this.selHedExUnitSet);
					}

					// Dataset Name (Safe check)
					String datasetName = "";
					if (unitSetIdStr != null && !unitSetIdStr.trim().isEmpty()) {
						try {
							datasetName = dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName();
						} catch (Exception e) {
							logger.error("Error fetching dataset name for ID: " + unitSetIdStr, e);
						}
					}
					job.addJobParm("HDGE_UNITS_NAME", datasetName);
					if (this.selHedExUnitSet != null) {
						mm.setModelParmII(ut, am, "HDGE_UNITS_NAME", datasetName);
					}

					job.addJobParm("HDGE_PERIOD", this.selHedExPeriod != null ? this.selHedExPeriod : "");
					if (this.selHedExPeriod != null) {
						mm.setModelParmII(ut, am, "HDGE_PERIOD", this.selHedExPeriod);
					}

					job.addJobParm("E_HDGE_PERIOD", this.selHedExEndPeriod != null ? this.selHedExEndPeriod : "");
					if (this.selHedExEndPeriod != null) {
						mm.setModelParmII(ut, am, "E_HDGE_PERIOD", this.selHedExEndPeriod);
					}

					if (this.selHedExSalesType == null || "-1".equals(this.selHedExSalesType)) {
						this.addActionError("No Valid TCGM Sales Types associated with this Units");
						flag = false;
					} else {
						job.addJobParm("HDGE_SLS_TYPE", this.selHedExSalesType);
						mm.setModelParmII(ut, am, "HDGE_SLS_TYPE", this.selHedExSalesType);
					}

					StringBuilder slsList = new StringBuilder();
					if (this.selHedSalesList == null || this.selHedSalesList.length == 0) {
						this.addActionError("No Valid TCGM Sales List associated with this Units");
						flag = false;
					} else {
						for (int i = 0; i < this.selHedSalesList.length; i++) {
							slsList.append(this.selHedSalesList[i]).append("|");
						}
						String finalSlsList = slsList.length() > 0 ? slsList.substring(0, slsList.length() - 1) : "";

						job.addJobParm("HDGE_SLS_ID_LIST", finalSlsList);
						mm.setModelParmII(ut, am, "HDGE_SLS_ID_LIST", finalSlsList);
						this.selHedSalesList = null;
					}

					// UNIT_HDR_1 (Safe check)
					if (am.getUnitHdr1() != null) {
						job.addJobParm(JobConstants.PN_UNIT_HDR_1, am.getUnitHdr1());
					} else {
						job.addJobParm(JobConstants.PN_UNIT_HDR_1, "");
					}

					// DIS_VERSION (Safe check)
					String datasetDesc = "";
					if (unitSetIdStr != null && !unitSetIdStr.trim().isEmpty()) {
						try {
							datasetDesc = dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetDesc();
						} catch (Exception e) {
						}
					}
					job.addJobParm(JobConstants.PN_DIS_VERSION, datasetDesc != null ? datasetDesc : "");

					if (this.hedgereportNameSuf != null) {
						job.addJobParm("HDGE_RPT_NAME_SUF", this.hedgereportNameSuf);
					} else {
						job.addJobParm("HDGE_RPT_NAME_SUF", "");
					}
				}
				/* Standard Cost Settings */
				else if (job.getJobDef().equals(JobDefinition.STD_CST_RPT)
						|| job.getJobDef().equals(JobDefinition.STD_VA_CST)
						|| job.getJobDef().equals(JobDefinition.GEN_NET_COST)
						|| job.getJobDef().equals(JobDefinition.STD_VA_COS)) {

					// Safe check for Rate Period selection
					String safeScRatePeriod = (this.selScRatePeriod != null) ? this.selScRatePeriod : "";
					job.addJobParm(JobConstants.PN_NET_RT_PER, safeScRatePeriod);
					if (this.selScRatePeriod != null) {
						mm.setModelParmII(ut, am, JobConstants.PN_NET_RT_PER, this.selScRatePeriod);
					}

					// Safe check for Unit Set selection
					String safeScUnitSet = (this.selScUnitSet != null) ? this.selScUnitSet : "";
					job.addJobParm(JobConstants.PN_NET_UNITS_ID, safeScUnitSet);
					if (this.selScUnitSet != null) {
						mm.setModelParmII(ut, am, JobConstants.PN_NET_UNITS_ID, this.selScUnitSet);
					}

					String netUnitsId = this.selScUnitSet;
					String netUnitsName = "";
					if (netUnitsId != null && !netUnitsId.trim().isEmpty()) {
						try {
							netUnitsName = dm.getDatasetById(ut, Integer.parseInt(netUnitsId)).getDatasetName();
						} catch (Exception e) {
							logger.error("Error fetching standard cost net units dataset name", e);
						}
					}
					job.addJobParm(JobConstants.PN_NET_UNITS, netUnitsName != null ? netUnitsName : "");
					if (netUnitsId != null && netUnitsName != null) {
						mm.setModelParmII(ut, am, JobConstants.PN_NET_UNITS, netUnitsName);
					}

					// Safe check for Factor Model selection
					if (this.selScFactorModel != null) {
						job.addJobParm(JobConstants.PN_NET_BASE,
								this.getState(request).getCurrentModelIdString() != null
										? this.getState(request).getCurrentModelIdString()
										: "");
						job.addJobParm(JobConstants.PN_NET_BASE_NAME,
								this.getState(request).getCurrentModelName() != null
										? this.getState(request).getCurrentModelName()
										: "");
						mm.setModelParmII(ut, am, JobConstants.PN_NET_BASE, this.selScFactorModel);
					} else {
						job.addJobParm(JobConstants.PN_NET_BASE, "");
					}

					// Safe check for Rate Set selection
					String safeScRateSet = (this.selScRateSet != null) ? this.selScRateSet : "";
					job.addJobParm(JobConstants.PN_RATE_2, safeScRateSet);
					if (this.selScRateSet != null) {
						mm.setModelParmII(ut, am, JobConstants.PN_RATE_2, this.selScRateSet);
					}

					// Safe check for Sales ID selection
					String safeScSalesId = (this.selScSalesId != null) ? this.selScSalesId : "";
					job.addJobParm(JobConstants.PN_STDCST_SLSID, safeScSalesId);
					if (this.selScSalesId != null) {
						mm.setModelParmII(ut, am, JobConstants.PN_STDCST_SLSID, this.selScSalesId);
					}

					if ("1".equals(this.selScSalesId)) {
						job.addJobParm(JobConstants.PN_SLSID_TITLE, "Sample");
					} else {
						job.addJobParm(JobConstants.PN_SLSID_TITLE, "Sales");
					}
				}

				// Add Job Execution
				if (flag) {
					pm.addJob(job, ut);
					addActionMessage(getText("success.job.add", new String[] { job.getJobDef().getJobName() }));
				}
			}
		}
	}

	public void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		String jobStatus = "";
		int modelIdInt = Integer.parseInt(((TCGMState) this.getState(request)).getCurrentModelIdString());

		this.unitSets = dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);
		this.rateSets = dm.getDatasetByTableName(ut, DBConst.TABLE_RATE_DATA);

		this.monthListNumber = new Vector<String>();
		for (int i = 1; i <= 12; i++) {
			String monthStr = (i < 10) ? "0" + i : String.valueOf(i);
			this.monthListNumber.add(monthStr);
		}

		FactorModel fm = new FactorModel();
		ModelMngr mm = new ModelMngr();
		fm.setStatus(TCGMModel.Status.OPEN);
		this.factorModels = mm.getModels(ut, fm);

		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.ANALYSIS);

		this.selScSalesId = md.getModelParm(modelIdInt, JobConstants.PN_STDCST_SLSID);
		jobStatus = md.getModelJobStatus(modelIdInt, "DFRD_MARGIN");

		this.selScUnitSet = md.getModelParm(modelIdInt, JobConstants.PN_NET_UNITS_ID);
		this.selExUnitSet = md.getModelParm(modelIdInt, JobConstants.PN_XCHG_UNITS);

		this.salesType = TCGMUtil.getSortedHashMap(dm.getSalesType(ut));
		this.salesList = TCGMUtil.getSortedHashMap(dm.getSalesList(ut, this.selExUnitSet));
		this.selScRatePeriod = md.getModelParm(modelIdInt, JobConstants.PN_NET_RT_PER);
		this.selExPeriod = md.getModelParm(modelIdInt, JobConstants.PN_PERIOD);

		this.selScRateSet = md.getModelParm(modelIdInt, JobConstants.PN_RATE_2);
		this.selScFactorModel = md.getModelParm(modelIdInt, JobConstants.PN_NET_BASE);

		request.getSession(false).setAttribute("jobStatus", jobStatus);
	}

	public boolean isModelSelected(HttpServletRequest req) {
		return true;
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getJobName() {
		return jobName;
	}

	@StrutsParameter
	public void setJobName(String jobName) {
		this.jobName = (jobName != null && jobName.contains(",")) ? jobName.split(",")[0].trim() : jobName;
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

	public Vector<?> getRateSets() {
		return rateSets;
	}

	@StrutsParameter
	public void setRateSets(Vector<?> rateSets) {
		this.rateSets = rateSets;
	}

	public Vector<?> getFactorModels() {
		return factorModels;
	}

	@StrutsParameter
	public void setFactorModels(Vector<?> factorModels) {
		this.factorModels = factorModels;
	}

	public Vector<?> getUnitSets() {
		return unitSets;
	}

	@StrutsParameter
	public void setUnitSets(Vector<?> unitSets) {
		this.unitSets = unitSets;
	}

	public String getDmSummaryOption() {
		return dmSummaryOption;
	}

	@StrutsParameter
	public void setDmSummaryOption(String dmSummaryOption) {
		this.dmSummaryOption = dmSummaryOption;
	}

	public String getSelScRateSet() {
		return selScRateSet;
	}

	@StrutsParameter
	public void setSelScRateSet(String selScRateSet) {
		this.selScRateSet = selScRateSet;
	}

	public String getSelScSalesId() {
		return selScSalesId;
	}

	@StrutsParameter
	public void setSelScSalesId(String selScSalesId) {
		this.selScSalesId = selScSalesId;
	}

	public String getSelScUnitSet() {
		return selScUnitSet;
	}

	@StrutsParameter
	public void setSelScUnitSet(String selScUnitSet) {
		this.selScUnitSet = selScUnitSet;
	}

	public String getSelScRatePeriod() {
		return selScRatePeriod;
	}

	@StrutsParameter
	public void setSelScRatePeriod(String selScRatePeriod) {
		this.selScRatePeriod = selScRatePeriod;
	}

	public String getSelScFactorModel() {
		return selScFactorModel;
	}

	@StrutsParameter
	public void setSelScFactorModel(String selScFactorModel) {
		this.selScFactorModel = selScFactorModel;
	}

	public String getSelExUnitSet() {
		return selExUnitSet;
	}

	@StrutsParameter
	public void setSelExUnitSet(String selExUnitSet) {
		this.selExUnitSet = selExUnitSet;
	}

	public String getSelExPeriod() {
		return selExPeriod;
	}

	@StrutsParameter
	public void setSelExPeriod(String selExPeriod) {
		this.selExPeriod = selExPeriod;
	}

	public String getSelExSalesType() {
		return selExSalesType;
	}

	@StrutsParameter
	public void setSelExSalesType(String selExSalesType) {
		this.selExSalesType = selExSalesType;
	}

	public HashMap<?, ?> getSalesType() {
		return salesType;
	}

	@StrutsParameter
	public void setSalesType(HashMap<?, ?> salesType) {
		this.salesType = salesType;
	}

	public String getSelComparisonModel() {
		return selComparisonModel;
	}

	@StrutsParameter
	public void setSelComparisonModel(String selComparisonModel) {
		this.selComparisonModel = selComparisonModel;
	}

	public String getKgaReportOption() {
		return kgaReportOption;
	}

	@StrutsParameter
	public void setKgaReportOption(String kgaReportOption) {
		this.kgaReportOption = kgaReportOption;
	}

	public String getSelHedExUnitSet() {
		return selHedExUnitSet;
	}

	@StrutsParameter
	public void setSelHedExUnitSet(String selHedExUnitSet) {
		this.selHedExUnitSet = selHedExUnitSet;
	}

	public String getSelHedExPeriod() {
		return selHedExPeriod;
	}

	@StrutsParameter
	public void setSelHedExPeriod(String selHedExPeriod) {
		this.selHedExPeriod = selHedExPeriod;
	}

	public String getSelHedExEndPeriod() {
		return selHedExEndPeriod;
	}

	@StrutsParameter
	public void setSelHedExEndPeriod(String selHedExEndPeriod) {
		this.selHedExEndPeriod = selHedExEndPeriod;
	}

	public String getReportNameSuf() {
		return reportNameSuf;
	}

	@StrutsParameter
	public void setReportNameSuf(String reportNameSuf) {
		this.reportNameSuf = reportNameSuf;
	}

	public String getHedgereportNameSuf() {
		return hedgereportNameSuf;
	}

	@StrutsParameter
	public void setHedgereportNameSuf(String hedgereportNameSuf) {
		this.hedgereportNameSuf = hedgereportNameSuf;
	}

	public HashMap<?, ?> getSalesList() {
		return salesList;
	}

	@StrutsParameter
	public void setSalesList(HashMap<?, ?> salesList) {
		this.salesList = salesList;
	}

	public String[] getSelSalesList() {
		return selSalesList;
	}

	@StrutsParameter
	public void setSelSalesList(String[] selSalesList) {
		this.selSalesList = selSalesList;
	}

	public String getAutoRun() {
		return autoRun;
	}

	@StrutsParameter
	public void setAutoRun(String autoRun) {
		this.autoRun = autoRun;
	}

	public String getSelHedExSalesType() {
		return selHedExSalesType;
	}

	@StrutsParameter
	public void setSelHedExSalesType(String selHedExSalesType) {
		this.selHedExSalesType = selHedExSalesType;
	}

	public String[] getSelHedSalesList() {
		return this.selHedSalesList;
	}

	@StrutsParameter
	public void setSelHedSalesList(String[] selHedSalesList) {
		this.selHedSalesList = selHedSalesList;
	}

	public Vector<String> getMonthListNumber() {
		return monthListNumber;
	}

	@StrutsParameter
	public void setMonthListNumber(Vector<String> monthListNumber) {
		this.monthListNumber = monthListNumber;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getSelReportDest() {
		return selReportDest;
	}

	@StrutsParameter
	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}

	public String getRestrictionStr() {
		StringBuffer sb = new StringBuffer();
		if (restrictions != null) {
			if (restrictions.getRptAffRestriction() != null && !restrictions.getRptAffRestriction().equals("")) {
				sb.append(" | " + restrictions.getRptAffRestriction());
			}
			if (restrictions.getSupAffRestriction() != null && !restrictions.getSupAffRestriction().equals("")) {
				sb.append(" | " + restrictions.getSupAffRestriction());
			}
			if (restrictions.getEndAffRestriction() != null && !restrictions.getEndAffRestriction().equals("")) {
				sb.append(" | " + restrictions.getEndAffRestriction());
			}
			if (restrictions.getRptListRestriction() != null && !restrictions.getRptListRestriction().equals("")) {
				sb.append(" | " + restrictions.getRptListRestriction());
			}
			if (restrictions.getSupListRestriction() != null && !restrictions.getSupListRestriction().equals("")) {
				sb.append(" | " + restrictions.getSupListRestriction());
			}
			if (restrictions.getEndListRestriction() != null && !restrictions.getEndListRestriction().equals("")) {
				sb.append(" | " + restrictions.getEndListRestriction());
			}
			if (restrictions.getGroupIdLimiterValues() != null && !restrictions.getGroupIdLimiterValues().equals("")) {
				sb.append(" | " + restrictions.getGroupIdLimiterValues());
			}
		}
		if (sb.toString().length() > 2) {
			return sb.toString().substring(2);
		} else {
			return sb.toString();
		}
	}

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	@StrutsParameter
	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
	}

	public Vector getDestinationList() {
		return destinationList;
	}

	public String getModelSelected() {
		return modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public void reset() {
		this.cmd = "";
		this.chkRestrict = false;
		this.restrictionEntered = false;
	}
}
