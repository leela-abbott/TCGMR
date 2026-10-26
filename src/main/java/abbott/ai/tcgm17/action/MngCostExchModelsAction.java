package abbott.ai.tcgm17.action;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.*;

public class MngCostExchModelsAction extends TCGMAction implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LogManager.getLogger(MngCostExchModelsAction.class);

	private String cmd;
	private String modelSelected;
	private String selDtlMetric;
	private String selCSMAff;
	private String essbaseVersion;
	private String essbaseYear;
	private String essbaseType;
	private String essbaseStPeriod;
	private String essbaseEndPeriod;
	private String showCostModel = "open";
	private boolean chkRestrict;
	private boolean restrictionEntered;

	private Vector<CostExchModel> models;
	private Map<String, Object> session;
	private String jobName;
	private String selReportDest;
	private boolean chkImmediate;
	private ReportRestriction restrictions;
	private static Vector<Option> destinationList;
	private String[] operations;

	public MngCostExchModelsAction() {
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
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if (this.isSessionValid(request)) {
				clearErrorsAndMessages();
				String savedMsg = (String) request.getSession().getAttribute("tcgmJobSuccessMessage");
				if (savedMsg != null) {
					addActionMessage(savedMsg);
					request.getSession().removeAttribute("tcgmJobSuccessMessage");
				}
				if (this.getCmd() != null && !TCGMUtil.isEmpty(getCmd()) && !getCmd().equals("CANCEL")) {
					if (chkRestrict && !this.isRestrictionEntered()) {
						session.put("restrictionForm", this);
						return "restrictions";
					} else {
						if (this.getCmd().equalsIgnoreCase("ADD_JOB") && TCGMUtil.isEmpty(this.getModelSelected())) {
							addActionError(getText("error.model.none_selected"));
							this.setupForm(request);
							return INPUT;
						}
					}
					processCmd(request);
					this.setupForm(request);
					resetFormFields();
					return SUCCESS;
				} else {
					this.setupForm(request);
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
		if ("ADD_JOB".equalsIgnoreCase(cmd)) {
			if (TCGMUtil.isEmpty(getModelSelected())) {
				addActionError(getText("error.model.none_selected", "You must select a model."));
				return;
			}

			// FIX: Fallback directly to the raw request parameter if the bean field didn't
			// bind correctly
			String activeJobName = getJobName();
			if (TCGMUtil.isEmpty(activeJobName)) {
				activeJobName = request.getParameter("jobName");
			}

			ProcessMngr pm = new ProcessMngr();
			Map<String, Object> jobDetails = new HashMap<>();
			jobDetails.put("jobName", activeJobName);
			jobDetails.put("SelReportDest", getSelReportDest());
			jobDetails.put("RestrictionStr", getRestrictionStr());
			jobDetails.put("ChkImmediate", isChkImmediate());

			JobInstance job = pm.createJob(getModelSelected(), "-1", jobDetails);
			ModelMngr mm = new ModelMngr();
			CostExchModel model = (CostExchModel) mm.getModelFromId(ut, Integer.parseInt(getModelSelected()),
					CostExchModel.Type.COSTEXCH);
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + model.getName());
			ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
			// Job Parameters Handling Mapping
			if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP1C) || job.getJobDef().equals(JobDefinition.CXCHG_CSMP2)
					|| job.getJobDef().equals(JobDefinition.CXCHG_CSMP3)
					|| job.getJobDef().equals(JobDefinition.CXCHG_CSMP4)
					|| job.getJobDef().equals(JobDefinition.CXCHG_FLEX5)
					|| job.getJobDef().getJobName().equalsIgnoreCase("CXCHG_FLEX")
					|| job.getJobDef().equals(JobDefinition.CXC_ANL04E)
					|| job.getJobDef().equals(JobDefinition.CXCHG_CSTSL1)) {

				if (model.getStartingSalesData() != null && model.getStartingSalesData().getDatasetTableId() != null) {
					job.addJobParm(CostExchModel.PN_START_SALES_SET, model.getStartingSalesData().getDatasetTableId());
					job.addJobParm(CostExchModel.PN_START_SALES_SET_NAME,
							model.getStartingSalesData().getDatasetName());
				} else {
					job.addJobParm(CostExchModel.PN_START_SALES_SET, "");
				}

				if (model.getStartPeriod() != null) {
					job.addJobParm(CostExchModel.PN_START_PERIOD, model.getStartPeriod());
				} else {
					job.addJobParm(CostExchModel.PN_START_PERIOD, "");
				}

				if (model.getRateSet() != null && model.getRateSet().getDatasetTableId() != null) {
					job.addJobParm(CostExchModel.PN_RATE_SET, model.getRateSet().getDatasetTableId());
					job.addJobParm(CostExchModel.PN_RATE_SET_DESC, model.getRateSet().getDatasetName());
				} else {
					job.addJobParm(CostExchModel.PN_RATE_SET, "");
				}

				if (model.getEndPeriod() != null) {
					job.addJobParm(CostExchModel.PN_END_PERIOD, model.getEndPeriod());
				} else {
					job.addJobParm(CostExchModel.PN_END_PERIOD, "");
				}

				if (model.getName() != null) {
					job.addJobParm(CostExchModel.PN_DIS_VERSION, model.getName());
				} else {
					job.addJobParm(CostExchModel.PN_DIS_VERSION, "");
				}

				if (model.getCostExchUnits() != null && model.getCostExchUnits().getDatasetTableId() != null) {
					job.addJobParm(CostExchModel.PN_CXCHG_UNITS, model.getCostExchUnits().getDatasetTableId());
				} else {
					job.addJobParm(CostExchModel.PN_CXCHG_UNITS, "");
				}
			}

			if (job.getJobDef().equals(JobDefinition.CXCHG_CSTSL1)) {
				job.addJobParm(JobConstants.PN_CXC_CSTSL1, getSelDtlMetric());
			} else if (job.getJobDef().equals(JobDefinition.CXCHG_CSTSL2)) {
				job.addJobParm(JobConstants.PN_CXC_CSTSL2, getSelDtlMetric());
			} else if (job.getJobDef().equals(JobDefinition.CXCHG_FLEX5)
					|| job.getJobDef().getJobName().equalsIgnoreCase("CXCHG_FLEX")) {
				job.addJobParm(JobConstants.PN_TYP_UPDT, model.getCostExchUnits().getDatasetName().trim());
			} else if (job.getJobDef().equals(JobDefinition.CXCHG_CSMP1C)) {
				job.addJobParm(JobConstants.PN_CXC_SUMFIELD, getSelCSMAff());
				job.addJobParm(JobConstants.PN_CXCHG_VERSN, md.getModelParm(-1, JobConstants.PN_CXCHG_VERSN));
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_CXC_SUMFIELD, getSelCSMAff());
			} else if (job.getJobDef().equals(JobDefinition.SEND_ANL_FLEX_ESS)) {
				job.addJobParm(JobConstants.PN_ESSBASE_YEAR, getEssbaseYear());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_YEAR, getEssbaseYear());
				job.addJobParm(JobConstants.PN_ESSBASE_VERSION, getEssbaseVersion());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_VERSION, getEssbaseVersion());
				job.addJobParm(JobConstants.PN_ESSBASE_TYPE, getEssbaseType());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_TYPE, getEssbaseType());
				job.addJobParm(JobConstants.PN_ESSBASE_START_PERIOD, getEssbaseStPeriod());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_START_PERIOD, getEssbaseStPeriod());
				job.addJobParm(JobConstants.PN_ESSBASE_END_PERIOD, getEssbaseEndPeriod());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_END_PERIOD, getEssbaseEndPeriod());
			}

			pm.addJob(job, ut);
			addActionMessage(getText("success.job.add", new String[] { job.getJobDef().getJobName() }));
		}
	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		ModelMngr mm = new ModelMngr();
		UserToken ut = this.getUserToken(request);
		CostExchModel costModel = new CostExchModel();

		if ("closed".equals(this.showCostModel)) {
			costModel.setStatus(TCGMModel.Status.CLOSED);
		} else {
			costModel.setStatus(TCGMModel.Status.OPEN);
			this.showCostModel = "open";
		}

		this.models = mm.getModels(ut, costModel);
	}

	public String getModelDetailArrayString() {
		StringBuilder sb = new StringBuilder("[");
		if (models != null && !models.isEmpty()) {
			for (CostExchModel currentModel : models) {
				sb.append("[");
				sb.append("'<b>Desc:</b> ").append(TCGMUtil.escapeString(currentModel.getDesc())).append("<br>");
				sb.append("<b>Rate Set:</b> ")
						.append(!currentModel.getRateSet().getDatasetName().equals("")
								? currentModel.getRateSet().getDatasetName()
								: TCGMConstants.NONE_SEL_HTML)
						.append("<br>");
				sb.append("<b>Starting RGM Ver:</b> ")
						.append(!currentModel.getStartingSalesData().getDatasetName().equals("")
								? currentModel.getStartingSalesData().getDatasetName()
								: TCGMConstants.NONE_SEL_HTML)
						.append("<br>");
				sb.append("<b>Ending RGM Ver:</b> ")
						.append(!currentModel.getEndingSalesData().getDatasetName().equals("")
								? currentModel.getEndingSalesData().getDatasetName()
								: TCGMConstants.NONE_SEL_HTML)
						.append("<br>");
				sb.append("<b>Start Period:</b> ").append(currentModel.getStartPeriod()).append(", ")
						.append(currentModel.getStartYear()).append("<br>");
				sb.append("<b>End Period:</b> ").append(currentModel.getEndPeriod()).append(", ")
						.append(currentModel.getEndYear()).append("<br>");
				sb.append("<b>Cost Exch Units:</b> ")
						.append(!currentModel.getCostExchUnits().getDatasetName().equals("")
								? currentModel.getCostExchUnits().getDatasetName()
								: TCGMConstants.NONE_SEL_HTML)
						.append("<br>");
				sb.append("<b>Memo:</b> ").append(currentModel.getMemo()).append("<br>");
				// Added by Udaya B Aravapalli on 01/31/2006 to display Factor Model.
				sb.append("<b>Factor Model:</b> ").append(currentModel.getFactorModelName()).append("<br>");

				sb.append("'],");
			}
			// Remove the trailing comma from the last element if entries exist
			if (sb.charAt(sb.length() - 1) == ',') {
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private void resetFormFields() {
		this.cmd = "";
	}

// =========================================================================
// Getters and Setters with Struts Parameter Annotations
// =========================================================================

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		if (cmd != null && cmd.contains(",")) {
			String[] parts = cmd.split(",");
			for (String part : parts) {
				if (part != null && !part.trim().isEmpty()) {
					this.cmd = part.trim();
					return;
				}
			}
			this.cmd = parts[0].trim();
		} else {
			this.cmd = (cmd != null) ? cmd.trim() : cmd;
		}
	}

	public String getModelSelected() {
		return modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public String getSelDtlMetric() {
		return selDtlMetric;
	}

	@StrutsParameter
	public void setSelDtlMetric(String selDtlMetric) {
		this.selDtlMetric = selDtlMetric;
	}

	public String getSelCSMAff() {
		return selCSMAff;
	}

	@StrutsParameter
	public void setSelCSMAff(String selCSMAff) {
		this.selCSMAff = selCSMAff;
	}

	public String getEssbaseVersion() {
		return essbaseVersion;
	}

	@StrutsParameter
	public void setEssbaseVersion(String essbaseVersion) {
		this.essbaseVersion = essbaseVersion;
	}

	public String getEssbaseYear() {
		return essbaseYear;
	}

	@StrutsParameter
	public void setEssbaseYear(String essbaseYear) {
		this.essbaseYear = essbaseYear;
	}

	public String getEssbaseType() {
		return essbaseType;
	}

	@StrutsParameter
	public void setEssbaseType(String essbaseType) {
		this.essbaseType = essbaseType;
	}

	public String getEssbaseStPeriod() {
		return essbaseStPeriod;
	}

	@StrutsParameter
	public void setEssbaseStPeriod(String essbaseStPeriod) {
		this.essbaseStPeriod = essbaseStPeriod;
	}

	public String getEssbaseEndPeriod() {
		return essbaseEndPeriod;
	}

	@StrutsParameter
	public void setEssbaseEndPeriod(String essbaseEndPeriod) {
		this.essbaseEndPeriod = essbaseEndPeriod;
	}

	public String getShowCostModel() {
		return showCostModel;
	}

	@StrutsParameter
	public void setShowCostModel(String showCostModel) {
		this.showCostModel = showCostModel;
	}

	public Vector<CostExchModel> getModels() {
		return models;
	}

	public void setModels(Vector<CostExchModel> models) {
		this.models = models;
	}

	public String getJobName() {
		return jobName;
	}

	@StrutsParameter
	public void setJobName(String jobName) {
		if (jobName != null && jobName.contains(",")) {
			String[] parts = jobName.split(",");
			for (int i = parts.length - 1; i >= 0; i--) {
				if (parts[i] != null && !parts[i].trim().isEmpty()) {
					this.jobName = parts[i].trim();
					return;
				}
			}
			this.jobName = "";
		} else {
			this.jobName = (jobName != null) ? jobName.trim() : null;
		}
	}

	public String getSelReportDest() {
		return selReportDest;
	}

	@StrutsParameter
	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	@StrutsParameter
	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
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

	public Vector<Option> getDestinationList() {
		return destinationList;
	}
}
