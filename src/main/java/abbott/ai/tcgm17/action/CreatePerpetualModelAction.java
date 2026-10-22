package abbott.ai.tcgm17.action;

import java.util.Vector;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.helpers.*;

public class CreatePerpetualModelAction extends TCGMAction implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

	private int first = 0;
	private String cmd;
	private String errMessage = "";

	private String modelName = "";
	private String modelDesc = "";
	private String selStartingModel;
	private String selStartingModelUnits;
	private String selCurrentYearActualModel;
	private String selCurrentYearActualUnits;
	private String selStartPeriod;
	private String selStartYear;
	private String selLastYearActualModel;
	private String selLastYearActualUnits;
	private String selEndPeriod;
	private String selEndYear;
	private String selCostingModel;
	private String selEndingModel;
	private String selEndingModelUnits;
	private String memo = "";
	private Map<String, Object> session;

	private Vector<FactorModel> factorModels;
	private Vector<Dataset> units;
	private Vector<Option> monthListNumber;
	private Vector<Option> yearList;
	protected Vector monthList = new Vector();

	public CreatePerpetualModelAction() {
		super();
	}

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {

			if (this.isSessionValid(request)) {
				clearErrorsAndMessages();

				if (request.getSession(false) == null || this.getUserToken(request) == null) {
					return LOGIN;
				}

				if (this.getCmd() != null && !TCGMUtil.isEmpty(this.getCmd())) {
					setErrMessage("");
					processCmd(request);

					addActionMessage(getText("success.model.create", "Model created successfully: " + modelName,
							new String[] { modelName }));
					resetFormFields();
					first++;
					return SUCCESS;
				} else {
					setupForm(request);
					if (first == 0) {
						setErrMessage("");
					}
					first = 0;
					return INPUT;
				}
			}

		} catch (TCGMDuplicateItemException exdup) {
			addActionError(getText("error.model.create.duplicate", "Duplicate model name encountered.",
					new String[] { modelName }));
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exdup);
			try {
				setupForm(request);
			} catch (TCGMException tex) {
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tex);
				return "exception";
			}
			return INPUT;
		} catch (TCGMException ex) {
			String errorMsg = (ex.getErrorMessage() != null) ? ex.getErrorMessage() : ex.getMessage();
			addActionError(errorMsg);
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			request.setAttribute("errorMessage", errorMsg);
			String originatingClass = "OracleModelDao";
			String originatingMethod = "createModel(TCGMModel)";
			try {
				java.lang.reflect.Method getClazz = ex.getClass().getMethod("getClassName");
				if (getClazz != null) {
					originatingClass = (String) getClazz.invoke(ex);
				}
				java.lang.reflect.Method getMeth = ex.getClass().getMethod("getMethodName");
				if (getMeth != null) {
					originatingMethod = (String) getMeth.invoke(ex);
				}
			} catch (Exception e) {
				originatingClass = "OracleModelDao";
				originatingMethod = "createModel(TCGMModel)";
			}

			request.setAttribute("errorClass", originatingClass);
			request.setAttribute("errorMethod", originatingMethod);

			return "exception";
		} catch (Exception ex) {
			Throwable rootCause = ex;
			while (rootCause.getCause() != null) {
				rootCause = rootCause.getCause();
			}

			String errorMsg = rootCause.getMessage();
			addActionError(errorMsg);

			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			request.setAttribute("errorMessage", errorMsg);
			request.setAttribute("errorClass", "abbott.ai.tcgm.data.oracle.OracleAnalysisModelDao");
			request.setAttribute("methodName", "createModel(TCGMModel)");

			try {
				setupForm(request);
			} catch (Exception ignore) {
			}

			return "exception";
		}
		return SUCCESS;
	}

	private void processCmd(HttpServletRequest request) throws TCGMException, TCGMDuplicateItemException {
		UserToken ut = this.getUserToken(request);

		if ("CREATE_MODEL".equalsIgnoreCase(cmd)) {
			try {
				ModelMngr mm = new ModelMngr();
				PerpetualModel model = getModelFromFormFields();
				mm.createModel(ut, model);

				if (factorModels != null && !factorModels.isEmpty()) {
					setErrMessage("No current year actual model exists");
				} else {
					setErrMessage("");
				}
			} catch (TCGMDuplicateItemException exdup) {
				throw new TCGMDuplicateItemException("OracleModelDao", "createModel", "Dup Model Names");
			}
		}
	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		ModelMngr mm = new ModelMngr();
		DatasetMngr dm = new DatasetMngr();

		FactorModel fm = new FactorModel();
		fm.setStatus(TCGMModel.Status.OPEN);
		this.factorModels = mm.getModels(ut, fm);

		int modelId = mm.getLastModelCreatedByUser(ut, TCGMModel.Type.PERPETUAL);

		if (modelId > 0) {
			PerpetualModel perpmodel = (PerpetualModel) mm.getModelFromId(ut, modelId, TCGMModel.Type.PERPETUAL);

			this.modelName = mm.getModelName(ut, perpmodel.getModelIdInt());
			this.modelDesc = perpmodel.getDesc();

			if (perpmodel.getStartingModel() != null) {
				this.selStartingModel = String.valueOf(perpmodel.getStartingModel().getModelId());
			}
			if (perpmodel.getStartingInvUnits() != null) {
				this.selStartingModelUnits = perpmodel.getStartingInvUnits().getDatasetName();
			}
			if (perpmodel.getCostingModel() != null) {
				this.selCostingModel = String.valueOf(perpmodel.getCostingModel().getModelId());
			}
			if (perpmodel.getCurrentYearActualModel() != null) {
				this.selCurrentYearActualModel = String.valueOf(perpmodel.getCurrentYearActualModel().getModelId());
			}
			if (perpmodel.getCurrentYearActualUnits() != null) {
				this.selCurrentYearActualUnits = perpmodel.getCurrentYearActualUnits().getDatasetName();
			}
			this.selStartPeriod = perpmodel.getStartPeriod();
			this.selStartYear = perpmodel.getStartYear();

			if (perpmodel.getLastYearActualModel() != null) {
				this.selLastYearActualModel = String.valueOf(perpmodel.getLastYearActualModel().getModelId());
			}
			if (perpmodel.getLastYearActualUnits() != null) {
				this.selLastYearActualUnits = perpmodel.getLastYearActualUnits().getDatasetName();
			}
			this.selEndPeriod = perpmodel.getEndPeriod();
			this.selEndYear = perpmodel.getEndYear();

			if (perpmodel.getEndingModel() != null) {
				this.selEndingModel = String.valueOf(perpmodel.getEndingModel().getModelId());
			}
			if (perpmodel.getEndingInvUnits() != null) {
				this.selEndingModelUnits = perpmodel.getEndingInvUnits().getDatasetName();
			}
			this.memo = perpmodel.getMemo();
		}

		this.units = dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);

		initMonthListNumber();
		initYearList();
	}

	private void initMonthListNumber() {
		if (this.monthListNumber == null) {
			this.monthListNumber = new Vector<Option>(12);
			for (int i = 0; i < 12; i++) {
				String n = String.valueOf(i + 1);
				this.monthListNumber.add(new Option(n, n));
			}
		}
	}

	private void initYearList() {
		if (this.yearList == null) {
			this.yearList = new Vector<Option>(40);
			for (int i = 0; i <= 39; i++) {
				String n;
				if (i >= 9) {
					n = "20" + String.valueOf(i + 1);
				} else {
					n = "200" + String.valueOf(i + 1);
				}
				this.yearList.add(new Option(n, n));
			}
		}
	}

	private PerpetualModel getModelFromFormFields() throws TCGMException {
		PerpetualModel model = new PerpetualModel();

		model.setName((this.modelName == null || this.modelName.trim().equals("")) ? null : this.modelName);
		model.setDesc((this.modelDesc == null || this.modelDesc.trim().equals("")) ? null : this.modelDesc);

		UserToken ut = SQLUtil.getOracleAdmin();
		DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);

		if (this.selStartingModel != null && !this.selStartingModel.trim().equals("")) {
			model.setStartingModel((FactorModel) md.getModel(Integer.parseInt(this.selStartingModel)));
		}
		if (this.selCostingModel != null && !this.selCostingModel.trim().equals("")) {
			model.setCostingModel((FactorModel) md.getModel(Integer.parseInt(this.selCostingModel)));
		}
		if (this.selStartingModelUnits != null && !this.selStartingModelUnits.trim().equals("")) {
			model.setStartingInvUnits(dd.getUnitDatasetByName(this.selStartingModelUnits));
		}

		model.setUseEndInv("N");
		if (this.selEndingModel != null && !this.selEndingModel.equalsIgnoreCase("NONE")
				&& !this.selEndingModel.trim().equals("")) {
			model.setUseEndInv("Y");
			model.setEndingModel((FactorModel) md.getModel(Integer.parseInt(this.selEndingModel)));
			model.setEndingInvUnits(dd.getUnitDatasetByName(this.selEndingModelUnits));
		} else {
			model.setEndingModel(null);
			if (this.selStartingModelUnits != null && !this.selStartingModelUnits.trim().equals("")) {
				model.setEndingInvUnits(dd.getUnitDatasetByName(this.selStartingModelUnits));
			} else {
				model.setEndingInvUnits(new Dataset());
			}
		}

		model.setEndPeriod(this.selEndPeriod);
		model.setEndYear(this.selEndYear);
		model.setStartPeriod(this.selStartPeriod);
		model.setStartYear(this.selStartYear);

		model.setMemo((this.memo == null || this.memo.trim().equals("")) ? null : this.memo);

		if (this.selCurrentYearActualModel != null && !this.selCurrentYearActualModel.trim().equals("")) {
			model.setCurrentYearActualModel(
					(FactorModel) md.getModel(Integer.parseInt(this.selCurrentYearActualModel)));
		}
		if (this.selCurrentYearActualUnits != null && !this.selCurrentYearActualUnits.trim().equals("")) {
			model.setCurrentYearActualUnits(dd.getUnitDatasetByName(this.selCurrentYearActualUnits));
		}
		if (this.selLastYearActualModel != null && !this.selLastYearActualModel.trim().equals("")) {
			model.setLastYearActualModel((FactorModel) md.getModel(Integer.parseInt(this.selLastYearActualModel)));
		}
		if (this.selLastYearActualUnits != null && !this.selLastYearActualUnits.trim().equals("")) {
			model.setLastYearActualUnits(dd.getUnitDatasetByName(this.selLastYearActualUnits));
		}

		return model;
	}

	private void resetFormFields() {
		this.cmd = "";
		this.modelName = "";
		this.modelDesc = "";
		this.memo = "";
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
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

	public String getSelStartingModel() {
		return selStartingModel;
	}

	@StrutsParameter
	public void setSelStartingModel(String selStartingModel) {
		this.selStartingModel = selStartingModel;
	}

	public String getSelStartingModelUnits() {
		return selStartingModelUnits;
	}

	@StrutsParameter
	public void setSelStartingModelUnits(String selStartingModelUnits) {
		this.selStartingModelUnits = selStartingModelUnits;
	}

	public String getSelCurrentYearActualModel() {
		return selCurrentYearActualModel;
	}

	@StrutsParameter
	public void setSelCurrentYearActualModel(String selCurrentYearActualModel) {
		this.selCurrentYearActualModel = selCurrentYearActualModel;
	}

	public String getSelCurrentYearActualUnits() {
		return selCurrentYearActualUnits;
	}

	@StrutsParameter
	public void setSelCurrentYearActualUnits(String selCurrentYearActualUnits) {
		this.selCurrentYearActualUnits = selCurrentYearActualUnits;
	}

	public String getSelStartPeriod() {
		return selStartPeriod;
	}

	@StrutsParameter
	public void setSelStartPeriod(String selStartPeriod) {
		this.selStartPeriod = selStartPeriod;
	}

	public String getSelStartYear() {
		return selStartYear;
	}

	@StrutsParameter
	public void setSelStartYear(String selStartYear) {
		this.selStartYear = selStartYear;
	}

	public String getSelLastYearActualModel() {
		return selLastYearActualModel;
	}

	@StrutsParameter
	public void setSelLastYearActualModel(String selLastYearActualModel) {
		this.selLastYearActualModel = selLastYearActualModel;
	}

	public String getSelLastYearActualUnits() {
		return selLastYearActualUnits;
	}

	@StrutsParameter
	public void setSelLastYearActualUnits(String selLastYearActualUnits) {
		this.selLastYearActualUnits = selLastYearActualUnits;
	}

	public String getSelEndPeriod() {
		return selEndPeriod;
	}

	@StrutsParameter
	public void setSelEndPeriod(String selEndPeriod) {
		this.selEndPeriod = selEndPeriod;
	}

	public String getSelEndYear() {
		return selEndYear;
	}

	@StrutsParameter
	public void setSelEndYear(String selEndYear) {
		this.selEndYear = selEndYear;
	}

	public String getSelCostingModel() {
		return selCostingModel;
	}

	@StrutsParameter
	public void setSelCostingModel(String selCostingModel) {
		this.selCostingModel = selCostingModel;
	}

	public String getSelEndingModel() {
		return selEndingModel;
	}

	@StrutsParameter
	public void setSelEndingModel(String selEndingModel) {
		this.selEndingModel = selEndingModel;
	}

	public String getSelEndingModelUnits() {
		return selEndingModelUnits;
	}

	@StrutsParameter
	public void setSelEndingModelUnits(String selEndingModelUnits) {
		this.selEndingModelUnits = selEndingModelUnits;
	}

	public String getMemo() {
		return memo;
	}

	@StrutsParameter
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Vector<FactorModel> getFactorModels() {
		return factorModels;
	}

	public void setFactorModels(Vector<FactorModel> factorModels) {
		this.factorModels = factorModels;
	}

	public Vector<Dataset> getUnits() {
		return units;
	}

	public void setUnits(Vector<Dataset> units) {
		this.units = units;
	}

	public Vector<Option> getMonthListNumber() {
		return monthListNumber;
	}

	public void setMonthListNumber(Vector<Option> monthListNumber) {
		this.monthListNumber = monthListNumber;
	}

	public Vector<Option> getYearList() {
		return yearList;
	}

	public void setYearList(Vector<Option> yearList) {
		this.yearList = yearList;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	public Vector getMonthList() {
		return monthList;
	}

	public void setMonthList(Vector monthList) {
		this.monthList = monthList;
	}

}
