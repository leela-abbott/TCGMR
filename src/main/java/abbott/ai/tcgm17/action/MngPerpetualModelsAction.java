package abbott.ai.tcgm17.action;

import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MngPerpetualModelsAction extends TCGMAction implements SessionAware, ServletRequestAware {

	protected static final Logger logger = LogManager.getLogger(MngPerpetualModelsAction.class);

	private String cmd;
	private Map<String, Object> session;
	private boolean chkRestrict;
	private boolean restrictionEntered;
	private String showPerpetualModel = "open";
	private String modelSelected;
	private Vector<PerpetualModel> models;

	private boolean runJobImmediate;
	private String reportType;
	private boolean restrictOutput;
	private String jobName;
	private String selReportDest;
	private ReportRestriction restrictions;
	private boolean chkImmediate;
	private static Vector<Option> destinationList;
	private String[] operations;
	private static final long serialVersionUID = 1L;

	public MngPerpetualModelsAction() {
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
				if (this.getCmd() != null && !TCGMUtil.isEmpty(this.getCmd())
						&& !this.getCmd().equalsIgnoreCase("CANCEL")) {
					if (chkRestrict && !this.isRestrictionEntered()) {
						session.put("restrictionForm", this);
						return "restrictions";
					} else {
						if (this.getCmd().equalsIgnoreCase("ADD_JOB") && TCGMUtil.isEmpty(this.getModelSelected())) {
							addActionError(getText("error.model.none_selected"));
							this.setupForm(request);
							return SUCCESS;
						}
						processCmd(request);
						this.setupForm(request);
						this.reset();
						return SUCCESS;
					}
				} else {
					setupForm(request);
					return SUCCESS;
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
		if (cmd.equalsIgnoreCase("ADD_JOB")) {
			if (modelSelected == null || modelSelected.trim().isEmpty()) {
				addActionError(getText("error.model.none_selected", "You must select a model."));
				return;
			}

			ProcessMngr pm = new ProcessMngr();
			ModelMngr mm = new ModelMngr();

			PerpetualModel pmodel = (PerpetualModel) mm.getModelFromId(ut, Integer.parseInt(modelSelected),
					TCGMModel.Type.PERPETUAL);

			Map<String, Object> jobDetails = new HashMap<>();
			jobDetails.put("jobName", getJobName());
			jobDetails.put("SelReportDest", getSelReportDest());
			jobDetails.put("RestrictionStr", getRestrictionStr());
			jobDetails.put("ChkImmediate", isChkImmediate());

			JobInstance job = pm.createJob(modelSelected, "-1", jobDetails);
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + pmodel.getName());

			if (job.getJobDef().equals(JobDefinition.PERP_TOTCALC)) {
				if ((pmodel.getStartingModel() != null) && (pmodel.getStartingModel().getModelId() != null)) {
					job.addJobParm(JobConstants.PN_ASR_BEG_INV_CYCLE, pmodel.getStartingModel().getModelId());
				}
				if ((pmodel.getStartingInvUnits() != null)
						&& (pmodel.getStartingInvUnits().getDatasetTableId() != null)) {
					job.addJobParm(JobConstants.PN_UNIT_BEG_INV_CYCLE,
							pmodel.getStartingInvUnits().getDatasetTableId());
				}
				if ("Y".equalsIgnoreCase(pmodel.getUseEndInv())) {
					job.addJobParm(JobConstants.PN_ASR_END_INV_CYCLE, pmodel.getEndingModel().getModelId());
					job.addJobParm(JobConstants.PN_UNIT_END_INV_CYCLE, pmodel.getEndingInvUnits().getDatasetTableId());
				}
				if ((pmodel.getCurrentYearActualModel() != null)
						&& (pmodel.getCurrentYearActualModel().getModelId() != null)) {
					job.addJobParm(JobConstants.PN_ASR_T_YEAR_CYCLE, pmodel.getCurrentYearActualModel().getModelId());
				}
				if ((pmodel.getCurrentYearActualUnits() != null)
						&& (pmodel.getCurrentYearActualUnits().getDatasetTableId() != null)) {
					job.addJobParm(JobConstants.PN_UNIT_T_YEAR_CYCLE,
							pmodel.getCurrentYearActualUnits().getDatasetTableId());
				}
				if ((pmodel.getLastYearActualModel() != null)
						&& (pmodel.getLastYearActualModel().getModelId() != null)) {
					job.addJobParm(JobConstants.PN_ASR_L_YEAR_CYCLE, pmodel.getLastYearActualModel().getModelId());
				}
				if ((pmodel.getLastYearActualUnits() != null)
						&& (pmodel.getLastYearActualUnits().getDatasetTableId() != null)) {
					job.addJobParm(JobConstants.PN_UNIT_L_YEAR_CYCLE,
							pmodel.getLastYearActualUnits().getDatasetTableId());
				}
				if ((pmodel.getCostingModel() != null) && (pmodel.getCostingModel().getModelId() != null)) {
					job.addJobParm(JobConstants.PN_COST_CYCLE_MID, pmodel.getCostingModel().getModelId());
				}
				if (pmodel.getStartPeriod() != null) {
					job.addJobParm(JobConstants.PN_BEG_D56_PERIOD, pmodel.getStartPeriod());
				}
				if (pmodel.getStartYear() != null) {
					job.addJobParm(JobConstants.PN_BEG_D56_YEAR, pmodel.getStartYear());
				}
				if (pmodel.getEndPeriod() != null) {
					job.addJobParm(JobConstants.PN_END_D56_PERIOD, pmodel.getEndPeriod());
				}
				if (pmodel.getEndYear() != null) {
					job.addJobParm(JobConstants.PN_END_D56_YEAR, pmodel.getEndYear());
				}
				if ((pmodel.getCostingModel() != null) && (pmodel.getCostingModel().getLegacyModelName() != null)) {
					job.addJobParm(JobConstants.PN_COST_CYCLE, pmodel.getCostingModel().getLegacyModelName());
				}
				if ((pmodel.getStartingModel() != null) && (pmodel.getStartingModel().getLegacyModelName() != null)) {
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getLegacyModelName());
				}
				if ("Y".equalsIgnoreCase(pmodel.getUseEndInv())) {
					if ((pmodel.getEndingModel() != null) && (pmodel.getEndingModel().getLegacyModelName() != null)) {
						job.addJobParm(JobConstants.PN_END_INV_CYCLE, pmodel.getEndingModel().getLegacyModelName());
					}
				}
				if (pmodel.getUseEndInv() != null) {
					job.addJobParm(JobConstants.PN_USE_END_INVENTORY, pmodel.getUseEndInv());
				}
				if (pmodel.getDesc() != null) {
					job.addJobParm("DIS_VERSION", pmodel.getDesc());
				}
			} else if (job.getJobDef().equals(JobDefinition.PERP_SUMMARY)
					|| job.getJobDef().equals(JobDefinition.PERP_INVXCPT)) {
				job.addJobParm(JobConstants.PN_BEG_INV_HEADER,
						pmodel.getBegInvHdr() != null ? pmodel.getBegInvHdr() : "");
				job.addJobParm(JobConstants.PN_END_INV_HEADER,
						pmodel.getEndInvHdr() != null ? pmodel.getEndInvHdr() : "");
				job.addJobParm(JobConstants.PN_FROM_PERIOD,
						pmodel.getStartPeriod() != null ? pmodel.getStartPeriod() : "");
				job.addJobParm(JobConstants.PN_THRU_PERIOD, pmodel.getEndPeriod() != null ? pmodel.getEndPeriod() : "");
				job.addJobParm(JobConstants.PN_BEG_D56_YEAR,
						pmodel.getStartYear() != null ? pmodel.getStartYear() : "");
				job.addJobParm(JobConstants.PN_END_D56_YEAR, pmodel.getEndYear() != null ? pmodel.getEndYear() : "");
				job.addJobParm(JobConstants.PN_COST_HDR_1, pmodel.getCostHdr1() != null ? pmodel.getCostHdr1() : "");
				job.addJobParm(JobConstants.PN_COST_HDR_2, pmodel.getCostHdr2() != null ? pmodel.getCostHdr2() : "");
				job.addJobParm(JobConstants.PN_COST_HDR_3, pmodel.getCostHdr3() != null ? pmodel.getCostHdr3() : "");
				job.addJobParm(JobConstants.PN_COST_HDR_4, pmodel.getCostHdr4() != null ? pmodel.getCostHdr4() : "");

				if (pmodel.getStartingModel() != null) {
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getName());
				}
				if (pmodel.getEndingModel() != null) {
					job.addJobParm(JobConstants.PN_END_INV_CYCLE, pmodel.getEndingModel().getName());
				}
			} else if (job.getJobDef().equals(JobDefinition.PERP_DETAIL)) {
				job.addJobParm(JobConstants.PN_BEG_INV_HEADER,
						pmodel.getBegInvHdr() != null ? pmodel.getBegInvHdr() : "");
				job.addJobParm(JobConstants.PN_END_INV_HEADER,
						pmodel.getEndInvHdr() != null ? pmodel.getEndInvHdr() : "");

				if (pmodel.getStartPeriod() != null) {
					job.addJobParm(JobConstants.PN_FROM_PERIOD, pmodel.getStartPeriod());
				} else {
					job.addJobParm(JobConstants.PN_FROM_PERIOD, "");
				}

				if (pmodel.getEndPeriod() != null) {
					job.addJobParm(JobConstants.PN_THRU_PERIOD, pmodel.getEndPeriod());
				} else {
					job.addJobParm(JobConstants.PN_THRU_PERIOD, "");
				}

				if (pmodel.getStartYear() != null) {
					job.addJobParm(JobConstants.PN_BEG_D56_YEAR, pmodel.getStartYear());
				} else {
					job.addJobParm(JobConstants.PN_BEG_D56_YEAR, "");
				}

				if (pmodel.getEndYear() != null) {
					job.addJobParm(JobConstants.PN_END_D56_YEAR, pmodel.getEndYear());
				} else {
					job.addJobParm(JobConstants.PN_END_D56_YEAR, "");
				}

				if (pmodel.getStartingModel() != null) {
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getName());
				}
				if (pmodel.getEndingModel() != null) {
					job.addJobParm(JobConstants.PN_END_INV_CYCLE, pmodel.getEndingModel().getName());
				}
			}

			else if (job.getJobDef().equals(JobDefinition.PPT_EQVXCPT1)) {
				if ((pmodel.getStartingModel() != null) && (pmodel.getStartingModel().getLegacyModelName() != null)) {
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, pmodel.getStartingModel().getLegacyModelName());
				} else {
					job.addJobParm(JobConstants.PN_BEG_INV_CYCLE, "");
				}
			}

			pm.addJob(job, ut);
			addActionMessage(getText("success.job.add", new String[] { job.getJobDef().getJobName() }));
		}
	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		ModelMngr mm = new ModelMngr();
		PerpetualModel perpModel = new PerpetualModel();

		if (this.showPerpetualModel != null && this.showPerpetualModel.equalsIgnoreCase("closed")) {
			perpModel.setStatus(TCGMModel.Status.CLOSED);
			this.showPerpetualModel = "closed";
		} else {
			perpModel.setStatus(TCGMModel.Status.OPEN);
			this.showPerpetualModel = "open";
		}

		this.models = mm.getModels(ut, perpModel);
		request.setAttribute("mngPerpetualModelsForm", this);
		request.getSession().setAttribute("mngPerpetualModelsForm", this);
	}

	public void reset() {
		this.cmd = "";
		this.chkRestrict = false;
		this.restrictionEntered = false;
	}

	public String getModelDetailArrayString() {
		StringBuilder sb = new StringBuilder("[");
		String none = TCGMConstants.NONE_SEL_HTML;

		if (this.models != null && !this.models.isEmpty()) {
			Iterator<PerpetualModel> iterator = this.models.iterator();
			while (iterator.hasNext()) {
				PerpetualModel currentModel = iterator.next();

				sb.append("[");
				sb.append("'<b>Desc:</b> ").append(TCGMUtil.escapeString(currentModel.getDesc())).append("<br>' + ");

				sb.append("'<b>Starting Model:</b> ").append(currentModel.getStartingModel() == null ? none
						: TCGMUtil.escapeString(currentModel.getStartingModel().getName())).append("' + ");
				sb.append("'<b>, Units:</b> ")
						.append(currentModel.getStartingInvUnits() == null ? none
								: TCGMUtil.escapeString(currentModel.getStartingInvUnits().getDatasetName()))
						.append("<br>' + ");

				sb.append("'<b>Current Model:</b> ")
						.append(currentModel.getCurrentYearActualModel() == null ? none
								: TCGMUtil.escapeString(currentModel.getCurrentYearActualModel().getName()))
						.append("' + ");
				sb.append("'<b>, Units:</b> ")
						.append(currentModel.getCurrentYearActualUnits() == null ? none
								: TCGMUtil.escapeString(currentModel.getCurrentYearActualUnits().getDatasetName()))
						.append("<br>' + ");

				sb.append("'<b>Previous Model:</b> ")
						.append(currentModel.getLastYearActualModel() == null ? none
								: TCGMUtil.escapeString(currentModel.getLastYearActualModel().getName()))
						.append("' + ");
				sb.append("'<b>, Units:</b> ")
						.append(currentModel.getLastYearActualUnits() == null ? none
								: TCGMUtil.escapeString(currentModel.getLastYearActualUnits().getDatasetName()))
						.append("<br>' + ");

				sb.append("'<b>Costing Model:</b> ").append(currentModel.getCostingModel() == null ? none
						: TCGMUtil.escapeString(currentModel.getCostingModel().getName())).append("<br>' + ");

				sb.append("'<b>Ending Inventory Model:</b> ").append(currentModel.getEndingModel() == null ? none
						: TCGMUtil.escapeString(currentModel.getEndingModel().getName())).append("' + ");
				sb.append("'<b>Units:</b> ")
						.append(currentModel.getEndingInvUnits() == null ? none
								: TCGMUtil.escapeString(currentModel.getEndingInvUnits().getDatasetName()))
						.append("<br>' + ");

				sb.append("'<b>Start Period:</b> ")
						.append(currentModel.getStartPeriod() != null ? currentModel.getStartPeriod() : "").append(", ")
						.append(currentModel.getStartYear() != null ? currentModel.getStartYear() : "")
						.append("<br>' + ");
				sb.append("'<b>End Period:</b> ")
						.append(currentModel.getEndPeriod() != null ? currentModel.getEndPeriod() : "").append(", ")
						.append(currentModel.getEndYear() != null ? currentModel.getEndYear() : "").append("<br>' + ");

				String temp = (currentModel.getMemo() != null) ? currentModel.getMemo().trim() : "";
				sb.append("'<b> Memo:</b> ").append(temp.isEmpty() ? none : TCGMUtil.escapeString(temp))
						.append("<br>'");
				sb.append("]");
				if (iterator.hasNext()) {
					sb.append(",\n");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	 public void setCmd(String cmd) {
        if (cmd != null && cmd.contains(",")) {
            this.cmd = cmd.split(",")[0].trim();
        } else {
            this.cmd = cmd;
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

	public String getShowPerpetualModel() {
		return showPerpetualModel;
	}

	@StrutsParameter
	public void setShowPerpetualModel(String showPerpetualModel) {
		this.showPerpetualModel = showPerpetualModel;
	}

	public String getModelSelected() {
		return modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public Vector<PerpetualModel> getModels() {
		return models;
	}

	@StrutsParameter
	public void setModels(Vector<PerpetualModel> models) {
		this.models = models;
	}

	public boolean isRunJobImmediate() {
		return runJobImmediate;
	}

	@StrutsParameter
	public void setRunJobImmediate(boolean runJobImmediate) {
		this.runJobImmediate = runJobImmediate;
	}

	public String getReportType() {
		return reportType;
	}

	@StrutsParameter
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public boolean isRestrictOutput() {
		return restrictOutput;
	}

	@StrutsParameter
	public void setRestrictOutput(boolean restrictOutput) {
		this.restrictOutput = restrictOutput;
	}

	public String getJobName() {
		return jobName;
	}

	@StrutsParameter
	public void setJobName(String jobName) {
		this.jobName = (jobName != null && jobName.contains(",")) ? jobName.split(",")[0].trim() : jobName;
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

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
