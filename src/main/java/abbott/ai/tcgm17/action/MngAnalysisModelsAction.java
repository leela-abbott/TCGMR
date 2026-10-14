package abbott.ai.tcgm17.action;

import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.ServletActionContext;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.TCGMAction;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.process.*;

public class MngAnalysisModelsAction extends TCGMAction {

	private static final long serialVersionUID = 1L;

	protected TCGMActionState mockBaseState = new TCGMActionState();

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
	private List<Map<String, String>> destinationList = new ArrayList<>();

	public MngAnalysisModelsAction() {
		super();
	}

	@Override
	public void validate() {
		if (TCGMUtil.isEmpty(this.getModelSelected())) {
			addActionError(getText("error.model.none_selected", "No model selected."));
		}
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if (this.isSessionValid(request)) {
				this.mockBaseState.clearErrors();

				if (this.getCmd() != null && !TCGMUtil.isEmpty(this.getCmd()) && !this.getCmd().equals("CANCEL")) {
					if (this.isChkRestrict() && !this.isRestrictionEntered()) {
						request.getSession().setAttribute("restrictionForm", this);
						return TCGMConstants.G_FORWARD_RESTRICTIONS;
					} else {
						processCmd(request);
						this.reset();
						return TCGMConstants.FORWARD_SUCCESS;
					}
				} else {
					this.setupForm(request);
					return TCGMConstants.FORWARD_SUCCESS;
				}
			}
		} catch (TCGMException ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			return TCGMConstants.G_FORWARD_EXCEPTION;
		}

		return ERROR;
	}

	private void processCmd(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String currentCmd = this.getCmd();

		if (currentCmd.equalsIgnoreCase("FETCH_PROD_HEADERS")) {
			MiscJclMngr mjm = new MiscJclMngr();
			mjm.sendProdHeaderLabelJcl();
			addActionError(getText("success.headers.production.fetch"));
			this.saveErrors(request);
		} else if (currentCmd.equalsIgnoreCase("FETCH_TEST_HEADERS")) {
			MiscJclMngr mjm = new MiscJclMngr();
			mjm.sendTestHeaderLabelJcl();
			addActionError(getText("success.headers.test.fetch"));
			this.saveErrors(request);
		} else if (currentCmd.equalsIgnoreCase("FETCH_PACK_CODES")) {
			MiscJclMngr mjm = new MiscJclMngr();
			mjm.sendPackCodesJcl();
			addActionError(getText("success.pack.codes.fetch"));
			this.saveErrors(request);
		} else if (currentCmd.equalsIgnoreCase("ADD_JOB")) {
			if (TCGMUtil.isEmpty(this.getModelSelected())) {
				addActionError(getText("error.model.none_selected"));
				this.saveErrors(request);
				return;
			}

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
			} else if ((job.getJobDef().equals(JobDefinition.ANL_SAMPEX))
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
					|| (job.getJobDef().equals(JobDefinition.ANL_PSSES_AREA))) {
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

			pm.addJob(job, ut);
			addActionMessage(getText("success.job.add", new String[] { job.getJobDef().getJobName() }));
			this.saveErrors(request);
		}
	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		ModelMngr mm = new ModelMngr();
		UserToken ut = this.getUserToken(request);
		AnalysisModel analModel = new AnalysisModel();
		this.destinationList.clear();
		this.destinationList.clear();
	    
	    Map<String, String> opt1 = new HashMap<>();
	    opt1.put("value", "G");  // This maps to listKey="value" and is selected by default via value="'G'"
	    opt1.put("label", "Generate Report"); // This maps to listValue="label"
	    this.destinationList.add(opt1);

	    Map<String, String> opt2 = new HashMap<>();
	    opt2.put("value", "P");
	    opt2.put("label", "Printer");
	    this.destinationList.add(opt2);

	    Map<String, String> opt3 = new HashMap<>();
	    opt3.put("value", "F");
	    opt3.put("label", "File");
	    this.destinationList.add(opt3);
		this.reset();
		if (this.getShowAnalModel() != null && !this.getShowAnalModel().trim().isEmpty()) {
			if (this.getShowAnalModel().equals("closed")) {
				analModel.setStatus(TCGMModel.Status.CLOSED);
				this.setShowAnalModel("closed");
			} else {
				this.setShowAnalModel("open");
			}
		} else {
			this.setShowAnalModel("open");
		}
		this.setModels(mm.getModels(ut, analModel));
		request.getSession().setAttribute("mngAnalysisModelsForm", this);
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

				String temp = mockBaseState.fixMemo(currentModel.getMemo());
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

	protected UserToken getUserToken(HttpServletRequest req) {
		if (req != null && req.getSession() != null) {
			return (UserToken) req.getSession().getAttribute("USER_TOKEN");
		}
		return null;
	}

	protected void saveErrors(HttpServletRequest req) {
		if (req == null) {
			return;
		}

		Object lifecycleErrors = req.getAttribute("errors");

		if (lifecycleErrors instanceof java.util.Collection<?>) {
			java.util.Collection<?> errorsCollection = (java.util.Collection<?>) lifecycleErrors;
			for (Object errorObj : errorsCollection) {
				if (errorObj != null) {
					String msgKey = errorObj.toString();
					addActionError(getText(msgKey, msgKey));
				}
			}
			return;
		}

		Object singleError = req.getAttribute("exception");
		if (singleError == null) {
			singleError = req.getAttribute(abbott.ai.tcgm.TCGMConstants.SESSION_NAME_EXCEPTION);
		}

		if (singleError != null) {
			if (singleError instanceof Exception) {
				addActionError(((Exception) singleError).getMessage());
			} else {
				addActionError(singleError.toString());
			}
		}
	}

	public String getExtValueVersion() {
		return extValueVersion;
	}

	public void setExtValueVersion(String extValueVersion) {
		this.extValueVersion = extValueVersion;
	}

	public String getEssbaseVersion() {
		return essbaseVersion;
	}

	public void setEssbaseVersion(String essbaseVersion) {
		this.essbaseVersion = essbaseVersion;
	}

	public String getEssbaseYear() {
		return essbaseYear;
	}

	public void setEssbaseYear(String essbaseYear) {
		this.essbaseYear = essbaseYear;
	}

	public String getEssbaseType() {
		return essbaseType;
	}

	public void setEssbaseType(String essbaseType) {
		this.essbaseType = essbaseType;
	}

	public String getModelLongDesc() {
		return modelLongDesc;
	}

	public void setModelLongDesc(String modelLongDesc) {
		this.modelLongDesc = modelLongDesc;
	}

	public String getShowAnalModel() {
		return showAnalModel;
	}

	public void setShowAnalModel(String showAnalModel) {
		this.showAnalModel = showAnalModel;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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

	@StrutsParameter
	public void setJobName(String jobName) {
		this.jobName = (jobName != null && jobName.contains(",")) ? jobName.split(",")[0].trim() : jobName;
	}

	public String getJobName() {
		return jobName;
	}
	
	 public List<Map<String, String>> getDestinationList() {
	        return this.destinationList;
	    }

	    public void setDestinationList(List<Map<String, String>> destinationList) {
	        this.destinationList = destinationList;
	    }

	@StrutsParameter
	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}

	public String getSelReportDest() {
		return selReportDest;
	}

	@StrutsParameter
	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
	}

	public boolean isChkImmediate() {
		return chkImmediate;
	}

	private static class TCGMActionState {
		public void clearErrors() {
		}

		public String fixMemo(String memo) {
			return memo == null ? "" : memo;
		}
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
}
