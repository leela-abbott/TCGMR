package abbott.ai.tcgm17.action;

import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.action.ServletRequestAware;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.process.*;

public class MngAnalysisModelsAction extends TCGMAction implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;

	protected static final Logger logger = LogManager.getLogger(MngAnalysisModelsAction.class);
	protected String className = this.getClass().getName();

	private String extValueVersion;
	private String essbaseVersion;
	private String essbaseYear;
	private String essbaseType;
	private String modelLongDesc;
	private String showAnalModel;

	private String cmd;
	private boolean chkRestrict;
	private boolean restrictionEntered;
	private String modelSelected;
	private Vector models;
	private String jobName;
	private String selReportDest;
	private boolean chkImmediate;
	private ReportRestriction restrictions;
	private TCGMModel.Type modelType = TCGMModel.Type.ANALYSIS;
	private String formHandler = "mngAnalysisModels.action";
	private static Vector<Option> destinationList;
	private String[] operations;

	public MngAnalysisModelsAction() {
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
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			if (this.isSessionValid(req)) {
				clearErrorsAndMessages();

				if (this.getCmd() != null && !TCGMUtil.isEmpty(this.getCmd())
						&& !this.getCmd().equalsIgnoreCase("CANCEL")) {
					if (this.isChkRestrict() && !this.isRestrictionEntered()) {
						session.put("restrictionForm", this);
						return "restrictions";
					} else {
						if (this.getCmd().equalsIgnoreCase("ADD_JOB") && TCGMUtil.isEmpty(this.getModelSelected())) {
							addActionError(getText("error.model.none_selected"));
							this.setupForm(req);
							return SUCCESS;
						}

						processCmd(req);
						if (this.getCmd().equalsIgnoreCase("CLOSE_MODEL")) {
							this.setModelSelected("");
						}
						this.setupForm(req);
						this.reset();
						return SUCCESS;
					}
				} else {
					this.setupForm(req);
					return SUCCESS;
				}
			}
		} catch (TCGMException ex) {
			req.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			return "exception";
		}

		return SUCCESS;
	}

	private void processCmd(HttpServletRequest req) throws TCGMException {
		UserToken ut = this.getUserToken(req);
		String currentCmd = this.getCmd();

		if (currentCmd.equalsIgnoreCase("FETCH_PROD_HEADERS")) {
			MiscJclMngr mjm = new MiscJclMngr();
			mjm.sendProdHeaderLabelJcl();
			addActionMessage(getText("success.headers.production.fetch"));
		} else if (currentCmd.equalsIgnoreCase("FETCH_TEST_HEADERS")) {
			MiscJclMngr mjm = new MiscJclMngr();
			mjm.sendTestHeaderLabelJcl();
			addActionMessage(getText("success.headers.test.fetch"));
		} else if (currentCmd.equalsIgnoreCase("FETCH_PACK_CODES")) {
			MiscJclMngr mjm = new MiscJclMngr();
			mjm.sendPackCodesJcl();
			addActionMessage(getText("success.pack.codes.fetch"));
		} else if (currentCmd.equalsIgnoreCase("ADD_JOB")) {
			Map<String, Object> jobDetails = new HashMap<>();
			jobDetails.put("jobName", getJobName());
			jobDetails.put("SelReportDest", getSelReportDest());
			jobDetails.put("RestrictionStr", getRestrictionStr());
			jobDetails.put("ChkImmediate", isChkImmediate());

			ProcessMngr pm = new ProcessMngr();
			JobInstance job = pm.createJob(this.getModelSelected(), "-1", jobDetails);

			job.getJobDef().populateDetail();
			AnalysisModel model = (AnalysisModel) new ModelMngr().getModelFromId(ut,
					Integer.parseInt(this.getModelSelected()), TCGMModel.Type.ANALYSIS);
			job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + model.getName());

			if (job.getJobDef().equals(job.getJobDef().ANL_CALC)) {
				populateAnalysisCalculationParameters(job, model);
			} else if (job.getJobDef().equals(job.getJobDef().SEND_ANL_FLEX_ESS)) {
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_ESSBASE_YEAR, this.getEssbaseYear());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_YEAR, this.getEssbaseYear());
				job.addJobParm(JobConstants.PN_ESSBASE_VERSION, this.getEssbaseVersion());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_VERSION, this.getEssbaseVersion());
				job.addJobParm(JobConstants.PN_ESSBASE_TYPE, this.getEssbaseType());
				md.setModelParmII(model.getModelIdInt(), JobConstants.PN_ESSBASE_TYPE, this.getEssbaseType());
			} else if ((job.getJobDef().equals(JobDefinition.ANL_REJECTS))
					|| (job.getJobDef().equals(JobDefinition.ANL_DETAIL))) {
				job.addJobParm(AnalysisModel.PN_BASE_TITLE, model.getBaseTitle() != null ? model.getBaseTitle() : "");
				job.addJobParm(AnalysisModel.PN_NEW_TITLE, model.getNewTitle() != null ? model.getNewTitle() : "");
			} else if (isExtendedSummaryJob(job)) {
				populateExtendedSummaryParameters(job, model);
			}

			pm.addJob(job, ut);
			addActionMessage(getText("success.job.add", new String[] { job.getJobDef().getJobName() }));
		}
	}

	private void populateAnalysisCalculationParameters(JobInstance job, AnalysisModel model) {
		job.addJobParm(AnalysisModel.PN_BASE_MODEL, model.getBaseModel().getModelId());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_MODEL, model.getAnalysisModel().getModelId());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_UNITS, model.getAnalysisUnits().getDatasetTableId());
		job.addJobParm(AnalysisModel.PN_VOLUME_UNITS, model.getVolumeUnits().getDatasetTableId());
		job.addJobParm(AnalysisModel.PN_BASE_MODEL_VERSION, model.getBaseVersion());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_MODEL_VERSION, model.getAnalysisModelVersion());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_UNITS_VERSION, model.getAnalysisUnitsVersion());
		job.addJobParm(AnalysisModel.PN_VOLUME_UNITS_VERSION, model.getVolumeUnitsVersion());
		job.addJobParm(AnalysisModel.PN_BASE_MODEL_YEAR, model.getBaseYear());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_MODEL_YEAR, model.getAnalysisModelYear());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_UNITS_YEAR, model.getAnalysisUnitsYear());
		job.addJobParm(AnalysisModel.PN_VOLUME_UNITS_YEAR, model.getVolumeUnitsYear());
		job.addJobParm(AnalysisModel.PN_BASE_MODEL_PERIOD, model.getBaseModelPeriod());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_MODEL_PERIOD, model.getAnalysisModelPeriod());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_UNITS_PERIOD, model.getAnalysisUnitsPeriod());
		job.addJobParm(AnalysisModel.PN_VOLUME_UNITS_PERIOD, model.getVolumeUnitsPeriod());
		job.addJobParm(AnalysisModel.PN_CURRENT_YEAR, model.getCurrentYear());
		job.addJobParm(AnalysisModel.PN_CURRENT_MONTH, model.getCurrentMonth());
		job.addJobParm(AnalysisModel.PN_CURRENT_DAY, model.getCurrentDay());
		job.addJobParm(AnalysisModel.PN_ANALYSIS_SAVE, model.getAnalysisSave());
		job.addJobParm(AnalysisModel.PN_NAME_RATE1, model.getRate1());
		job.addJobParm(AnalysisModel.PN_BASE_TITLE, model.getBaseTitle() != null ? model.getBaseTitle() : "");
		job.addJobParm(AnalysisModel.PN_NEW_TITLE, model.getNewTitle() != null ? model.getNewTitle() : "");
	}

	private void setupForm(HttpServletRequest req) throws TCGMException {
		ModelMngr mm = new ModelMngr();
		UserToken ut = this.getUserToken(req);
		if (ut == null) {
			logger.error("UserToken context could not be resolved. Session may have expired.");
			throw new TCGMException(this.className, "setupForm", "User authorization token is missing or expired.");
		}
		AnalysisModel analModel = new AnalysisModel();
		String currentViewStatus = this.getShowAnalModel();
		this.reset();

		if (currentViewStatus != null && !currentViewStatus.trim().isEmpty()) {
			if (currentViewStatus.equals("closed")) {
				analModel.setStatus(TCGMModel.Status.CLOSED);
				this.setShowAnalModel("closed");
			} else {
				this.setShowAnalModel("open");
			}
		} else {
			this.setShowAnalModel("open");
		}

		this.setModels(mm.getModels(ut, analModel));
		session.put("mngAnalysisModelsForm", this);
		req.getSession().setAttribute("mngAnalysisModelsForm", this);
	}

	private boolean isExtendedSummaryJob(JobInstance job) {
		return (job.getJobDef().equals(JobDefinition.ANL_SAMPEX))
				|| (job.getJobDef().equals(JobDefinition.ANL_PSUM_PUB))
				|| (job.getJobDef().equals(JobDefinition.ANL_XTSM_400))
				|| (job.getJobDef().equals(JobDefinition.ANL_PSUM_400))
				|| (job.getJobDef().equals(JobDefinition.ANL_PUSUMMRY))
				|| (job.getJobDef().equals(JobDefinition.ANL_ARSUMMRY))
				|| (job.getJobDef().equals(JobDefinition.ANL_WSUMMRY))
				|| (job.getJobDef().equals(JobDefinition.ANL_SAMPAR))
				|| (job.getJobDef().equals(JobDefinition.ANL_SAMPW))
				|| (job.getJobDef().equals(JobDefinition.ANL_XSEC_SUM))
				|| (job.getJobDef().equals(JobDefinition.ANL_SAMSEC_SUM))
				|| (job.getJobDef().equals(JobDefinition.ANL_PUSRCSUM))
				|| (job.getJobDef().equals(JobDefinition.ANL_EXSUMMRY))
				|| (job.getJobDef().equals(JobDefinition.ANL_PSSES_AREA));
	}

	private void populateExtendedSummaryParameters(JobInstance job, AnalysisModel model) {
		job.addJobParm(AnalysisModel.PN_ANAL1_VERSION,
				model.getAnalysisVersion() != null ? model.getAnalysisVersion() : "");
		job.addJobParm(AnalysisModel.PN_BASE_HDR_1, model.getBaseHdr1() != null ? model.getBaseHdr1() : "");
		job.addJobParm(AnalysisModel.PN_BASE_HDR_2, model.getBaseHdr2() != null ? model.getBaseHdr2() : "");
		job.addJobParm(AnalysisModel.PN_BASE_HDR_3, model.getBaseHdr3() != null ? model.getBaseHdr3() : "");
		job.addJobParm(AnalysisModel.PN_BASE_HDR_4, model.getBaseHdr4() != null ? model.getBaseHdr4() : "");
		job.addJobParm(AnalysisModel.PN_BASE_TITLE, model.getBaseTitle() != null ? model.getBaseTitle() : "");
		job.addJobParm(AnalysisModel.PN_NEW_HDR_1, model.getNewHdr1() != null ? model.getNewHdr1() : "");
		job.addJobParm(AnalysisModel.PN_NEW_HDR_2, model.getNewHdr2() != null ? model.getNewHdr2() : "");
		job.addJobParm(AnalysisModel.PN_NEW_HDR_3, model.getNewHdr3() != null ? model.getNewHdr3() : "");
		job.addJobParm(AnalysisModel.PN_NEW_HDR_4, model.getNewHdr4() != null ? model.getNewHdr4() : "");
		job.addJobParm(AnalysisModel.PN_NEW_TITLE, model.getNewTitle() != null ? model.getNewTitle() : "");
		job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, model.getUnitHdr1() != null ? model.getUnitHdr1() : "");
		job.addJobParm(AnalysisModel.PN_UNIT_HDR_2, model.getUnitHdr2() != null ? model.getUnitHdr2() : "");
		job.addJobParm(AnalysisModel.PN_UNIT_HDR_3, model.getUnitHdr3() != null ? model.getUnitHdr3() : "");
		job.addJobParm(AnalysisModel.PN_UNIT_HDR_4, model.getUnitHdr4() != null ? model.getUnitHdr4() : "");
		job.addJobParm(AnalysisModel.PN_VOL_HDR_1, model.getVolHdr1() != null ? model.getVolHdr1() : "");
		job.addJobParm(AnalysisModel.PN_VOL_HDR_2, model.getVolHdr2() != null ? model.getVolHdr2() : "");
		job.addJobParm(AnalysisModel.PN_VOL_HDR_3, model.getVolHdr3() != null ? model.getVolHdr3() : "");
		job.addJobParm(AnalysisModel.PN_VOL_HDR_4, model.getVolHdr4() != null ? model.getVolHdr4() : "");
	}

	public String getModelDetailArrayString() {
		String none = TCGMConstants.NONE_SEL_HTML;
		StringBuilder sb = new StringBuilder("[");
		AnalysisModel currentModel;

		if (this.getModels() != null && this.getModels().size() > 0) {
			Vector v = this.getModels();
			Iterator iterator = v.iterator();
			while (iterator.hasNext()) {
				currentModel = (AnalysisModel) iterator.next();
				sb.append("['");
				sb.append("<b>Desc:</b> ").append(TCGMUtil.escapeString(currentModel.getDesc())).append("<br>");
				sb.append("<b>Base Factor Model:</b> ")
						.append(currentModel.getBaseModel() == null ? none : currentModel.getBaseModel().getName())
						.append(", Period: ").append(currentModel.getBaseModelPeriod()).append("<br>");
				sb.append("<b> Analysis Model:</b> ")
						.append(currentModel.getAnalysisModel() == null ? none
								: currentModel.getAnalysisModel().getName())
						.append(", Period: ").append(currentModel.getAnalysisModelPeriod()).append("<br>");
				sb.append("<b>Base Units:</b> ")
						.append(currentModel.getAnalysisUnits() == null ? none
								: currentModel.getAnalysisUnits().getDatasetName())
						.append(", Period: ").append(currentModel.getAnalysisUnitsPeriod()).append("<br>");
				sb.append("<b>Analysis Units:</b> ")
						.append(currentModel.getVolumeUnits() == null ? none
								: currentModel.getVolumeUnits().getDatasetName())
						.append(", Period: ").append(currentModel.getVolumeUnitsPeriod()).append("<br>");

				String temp = currentModel.getMemo() == null ? "" : currentModel.getMemo();
				sb.append("<b> Memo:</b> ").append("".equals(temp) ? none : temp).append("<br>");
				sb.append("'],\n");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public void reset() {
		this.cmd = "";
	}

	protected boolean isSessionValid(HttpServletRequest req) {
		return true;
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

	public String getExtValueVersion() {
		return extValueVersion;
	}

	@StrutsParameter
	public void setExtValueVersion(String extValueVersion) {
		this.extValueVersion = extValueVersion;
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

	public String getModelLongDesc() {
		return modelLongDesc;
	}

	@StrutsParameter
	public void setModelLongDesc(String modelLongDesc) {
		this.modelLongDesc = modelLongDesc;
	}

	public String getShowAnalModel() {
		return showAnalModel;
	}

	@StrutsParameter
	public void setShowAnalModel(String showAnalModel) {
		this.showAnalModel = showAnalModel;
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

	public String getModelSelected() {
		return modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public Vector getModels() {
		return models;
	}

	public void setModels(Vector models) {
		this.models = models;
	}

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
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

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	@StrutsParameter
	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Vector getDestinationList() {
		return destinationList;
	}
}