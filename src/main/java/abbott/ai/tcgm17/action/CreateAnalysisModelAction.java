package abbott.ai.tcgm17.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm17.action.TCGMAction;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.DatasetDao;
import abbott.ai.tcgm.data.ModelDao;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.process.JobConstants;

public class CreateAnalysisModelAction extends TCGMAction implements SessionAware, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CreateAnalysisModelAction.class);

	private HttpServletRequest request;
	private Map<String, Object> session;
	private String cmd;
	private String modelName;
	private String modelDesc;
	private String baseModelSelected;
	private String analysisModelSelected;
	private String volumeUnitsSelected;
	private String analysisUnitsSelected;
	private String baseModelPeriod;
	private String analysisModelPeriod;
	private String analysisUnitsPeriod;
	private String volumeUnitsPeriod;
	private String memo;
	private TCGMModel.Type modelType = TCGMModel.Type.ANALYSIS;

	private java.util.Collection factorModels;
	private java.util.Vector unitSets;
	private java.util.List<Map<String, String>> periodMonthList;

	public CreateAnalysisModelAction() {
		super();
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String execute() {
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			if (this.isSessionValid(req)) {
				clearErrorsAndMessages();

				if (req.getSession(false) == null || this.getUserToken(req) == null) {
					return LOGIN;
				}

				if (!TCGMUtil.isEmpty(cmd) && this.getCmd() != null) {
					return processCmd(req);
				} else {
					setupForm(req);
					return INPUT;
				}
			}
		} catch (NumberFormatException ex) {
			addActionError(getText("error.model.create.year_format", "Invalid year format provided."));
			try {
				setupForm(req);
			} catch (TCGMException tex) {
				req.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tex);
				return "exception";
			}
			return INPUT;

		} catch (TCGMException ex) {
			String errorMsg = (ex.getErrorMessage() != null) ? ex.getErrorMessage() : ex.getMessage();
			addActionError(errorMsg);
			req.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			req.setAttribute("errorMessage", errorMsg);
			req.setAttribute("errorClass",
					ex.getClass() != null ? ex.getClass() : "abbott.ai.tcgm.data.oracle.OracleAnalysisModelDao");
			return "exception";
		}
		return SUCCESS;
	}

	private void setupForm(HttpServletRequest req) throws TCGMException {
		UserToken ut = this.getUserToken(req);
		ModelMngr mm = new ModelMngr();
		DatasetMngr dm = new DatasetMngr();
		this.periodMonthList = new ArrayList<>();
		for (int m = 1; m <= 12; m++) {
			Map<String, String> monthOption = new HashMap<>();
			String valStr = String.valueOf(m);
			monthOption.put("value", valStr);
			monthOption.put("label", valStr);
			this.periodMonthList.add(monthOption);
		}

		FactorModel fm = new FactorModel();
		fm.setStatus(TCGMModel.Status.OPEN);
		this.factorModels = mm.getModels(ut, fm);

		this.modelName = "";
		this.modelDesc = "";
		this.memo = "";

		this.baseModelSelected = "";
		this.analysisModelSelected = "";
		this.analysisUnitsSelected = "";
		this.volumeUnitsSelected = "";
		this.baseModelPeriod = "13";
		this.analysisModelPeriod = "13";
		this.analysisUnitsPeriod = "13";
		this.volumeUnitsPeriod = "13";

		this.unitSets = dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);
	}

	private String processCmd(HttpServletRequest req) throws TCGMException, NumberFormatException {
		UserToken ut = this.getUserToken(req);

		if ("CREATE_MODEL".equals(cmd)) {
			ModelMngr mm = new ModelMngr();
			AnalysisModel am = getModelFromForm(ut);

			mm.createModel(ut, am);
			mm.updateModelParms(ut, am);

			addActionMessage(getText("success.model.create", new String[] { this.modelName }));
			reset();
			return SUCCESS;
		}
		return INPUT;
	}

	private AnalysisModel getModelFromForm(UserToken ut) throws TCGMException, NumberFormatException {
		AnalysisModel model = new AnalysisModel();

		model.setName(this.modelName);
		model.setDesc(this.modelDesc);

		DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);

		model.setBaseModel((FactorModel) md.getModel(Integer.parseInt(this.baseModelSelected)));
		model.setBaseModelPeriod(this.baseModelPeriod);
		model.setBaseVersion(md.getModelParm(Integer.parseInt(this.baseModelSelected), "CYCLE"));
		model.setBaseYear(md.getModelParm(Integer.parseInt(this.baseModelSelected), "YEAR"));

		model.setAnalysisModel((FactorModel) md.getModel(Integer.parseInt(this.analysisModelSelected)));
		model.setAnalysisModelPeriod(this.analysisModelPeriod);
		model.setAnalysisModelVersion(md.getModelParm(Integer.parseInt(this.analysisModelSelected), "CYCLE"));
		model.setAnalysisModelYear(md.getModelParm(Integer.parseInt(this.analysisModelSelected), "YEAR"));

		model.setAnalysisUnits(dd.getDatasetById(Integer.parseInt(this.analysisUnitsSelected)));
		model.setAnalysisUnitsPeriod(this.analysisUnitsPeriod);
		String analysisUnitName = dd.getDatasetById(Integer.parseInt(this.analysisUnitsSelected)).getDatasetName();
		model.setAnalysisUnitsYear(TCGMUtil.getUnitYear(analysisUnitName));
		model.setAnalysisUnitsVersion(TCGMUtil.getUnitVersion(analysisUnitName));

		model.setMemo(this.memo);

		model.setVolumeUnits(dd.getDatasetById(Integer.parseInt(this.volumeUnitsSelected)));
		model.setVolumeUnitsPeriod(this.volumeUnitsPeriod);
		String volumeUnitName = dd.getDatasetById(Integer.parseInt(this.volumeUnitsSelected)).getDatasetName();
		model.setVolumeUnitsYear(TCGMUtil.getUnitYear(volumeUnitName));
		model.setVolumeUnitsVersion(TCGMUtil.getUnitVersion(volumeUnitName));

		Calendar currentDate = Calendar.getInstance();
		model.setCurrentYear(Integer.toString(currentDate.get(Calendar.YEAR)));
		model.setCurrentMonth(Integer.toString(currentDate.get(Calendar.MONTH) + 1));
		model.setCurrentDay(Integer.toString(currentDate.get(Calendar.DAY_OF_MONTH)));

		model.setAnalysisSave("Y");

		String modelId = model.getBaseModel().getModelId();
		String sql = JobConstants.PN_SQL_RATE1 + modelId;
		try {
			model.setRate1(md.getStringValueFromSql(sql));
		} catch (TCGMException ex) {
			String errMsg = "Error retrieving RATE_1 parm from base model " + model.getBaseModel().getName() + ".";
			throw new TCGMException("CreateAnalysisModelAction", "getModelFromForm()", errMsg);
		}

		return model;
	}

	public Vector removeSavedUnits() {
		if (this.unitSets == null) {
			return new Vector();
		}
		Vector retVect = new Vector();
		for (int i = 0; i < this.unitSets.size(); i++) {
			Dataset dtSetUnit = (Dataset) (this.unitSets.elementAt(i));
			String tblId = dtSetUnit.getDatasetName();
			if (tblId != null && !(tblId.trim().endsWith("save") || tblId.endsWith("SAVE"))) {
				retVect.add(dtSetUnit);
			}
		}
		return retVect;
	}

	public void reset() {
		this.modelName = "";
		this.modelDesc = "";
		this.memo = "";
		this.cmd = "";
	}

	// Getters and Setters Mapping Block
	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getModelName() {
		return modelName;
	}

	@StrutsParameter
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	@StrutsParameter
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getBaseModelSelected() {
		return baseModelSelected;
	}

	@StrutsParameter
	public void setBaseModelSelected(String baseModelSelected) {
		this.baseModelSelected = baseModelSelected;
	}

	public String getAnalysisModelSelected() {
		return analysisModelSelected;
	}

	@StrutsParameter
	public void setAnalysisModelSelected(String analysisModelSelected) {
		this.analysisModelSelected = analysisModelSelected;
	}

	public String getVolumeUnitsSelected() {
		return volumeUnitsSelected;
	}

	@StrutsParameter
	public void setVolumeUnitsSelected(String volumeUnitsSelected) {
		this.volumeUnitsSelected = volumeUnitsSelected;
	}

	public String getAnalysisUnitsSelected() {
		return analysisUnitsSelected;
	}

	@StrutsParameter
	public void setAnalysisUnitsSelected(String analysisUnitsSelected) {
		this.analysisUnitsSelected = analysisUnitsSelected;
	}

	public String getBaseModelPeriod() {
		return baseModelPeriod;
	}

	@StrutsParameter
	public void setBaseModelPeriod(String baseModelPeriod) {
		this.baseModelPeriod = baseModelPeriod;
	}

	public String getAnalysisModelPeriod() {
		return analysisModelPeriod;
	}

	@StrutsParameter
	public void setAnalysisModelPeriod(String analysisModelPeriod) {
		this.analysisModelPeriod = analysisModelPeriod;
	}

	public String getAnalysisUnitsPeriod() {
		return analysisUnitsPeriod;
	}

	@StrutsParameter
	public void setAnalysisUnitsPeriod(String analysisUnitsPeriod) {
		this.analysisUnitsPeriod = analysisUnitsPeriod;
	}

	public String getVolumeUnitsPeriod() {
		return volumeUnitsPeriod;
	}

	@StrutsParameter
	public void setVolumeUnitsPeriod(String volumeUnitsPeriod) {
		this.volumeUnitsPeriod = volumeUnitsPeriod;
	}

	public String getMemo() {
		return memo;
	}

	@StrutsParameter
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	@StrutsParameter
	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
	}

	public java.util.Collection getFactorModels() {
		return factorModels;
	}

	@StrutsParameter
	public void setFactorModels(java.util.Collection factorModels) {
		this.factorModels = factorModels;
	}

	public java.util.Vector getUnitSets() {
		return removeSavedUnits();
	}

	@StrutsParameter
	public void setUnitSets(java.util.Vector unitSets) {
		this.unitSets = unitSets;
	}

	public java.util.List<Map<String, String>> getPeriodMonthList() {
		return periodMonthList;
	}

	@StrutsParameter
	public void setPeriodMonthList(java.util.List<Map<String, String>> periodMonthList) {
		this.periodMonthList = periodMonthList;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}
}
