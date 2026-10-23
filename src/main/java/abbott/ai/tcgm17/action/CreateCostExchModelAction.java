package abbott.ai.tcgm17.action;

import java.util.Map;
import java.util.Vector;
import java.util.Calendar;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.*;

public class CreateCostExchModelAction extends TCGMAction implements SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

	private String modelName = "";
	private String modelDesc = "";
	private String selRateSet = "";
	private String selStartPeriod = "";
	private String selStartYear = "";
	private String selStartingSalesData = "";
	private String selEndPeriod = "";
	private String selEndYear = "";
	private String selEndingSalesData = "";
	private String modelSelected = "";
	private String selCostExchUnits = "";
	private String memo = "";
	private String cmd = "";
	private String factorModelName = "";

	private Vector rateSets;
	private Vector salesData;
	private Vector unitSets;
	private Vector factorModels;

	// Pre-initializing Vectors prevents OGNL null collection pointer crashes
	private Vector monthListNumber = new Vector();
	private Vector yearList = new Vector();
	private Map<String, Object> session;
	private HttpServletRequest servletRequest;
	private String errMessage = "";

	public CreateCostExchModelAction() {
		super();
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if (isSessionValid(request)) {
				clearErrorsAndMessages();
				if (request.getSession(false) == null || this.getUserToken(request) == null) {
					return LOGIN;
				}
				if (cmd != null && !cmd.trim().isEmpty()) {
					setErrMessage("");
					processCmd(request);
					request.setAttribute("actionMessage", "Successfully created model: " + modelName);
					reset();
					return SUCCESS;
				} else {
					setupForm(request);
					return INPUT;
				}
			}
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

			try {
				setupForm(request); // Ensure dropdown arrays are populated on fallback error views
			} catch (Exception ignore) {
			}

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

	private void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		ModelMngr mm = new ModelMngr();
		DatasetMngr dm = new DatasetMngr();
		int modelId = 0;

		this.unitSets = dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);
		this.rateSets = dm.getDatasetByTableName(ut, DBConst.TABLE_RATE_DATA);
		this.salesData = dm.getDatasetByTableName(ut, DBConst.TABLE_COSTEXCH_DATA);

		FactorModel fm = new FactorModel();
		fm.setStatus(TCGMModel.Status.OPEN);
		this.factorModels = mm.getModels(ut, fm);
		this.modelSelected = "NONE";

		// Dynamic generation of date lists if helper classes do not fill them natively
		populateDateUtilityLists();

		modelId = mm.getLastModelCreatedByUser(ut, TCGMModel.Type.COSTEXCH);

		if (modelId > 0) {
			CostExchModel cem = (CostExchModel) mm.getModelFromId(ut, modelId, TCGMModel.Type.COSTEXCH);

			this.modelName = mm.getModelName(ut, cem.getModelIdInt());
			this.modelDesc = cem.getDesc();

			if (cem.getRateSet() != null) {
				this.selRateSet = cem.getRateSet().getDatasetTableId();
			}
			if (cem.getStartingSalesData() != null) {
				this.selStartingSalesData = cem.getStartingSalesData().getDatasetTableId();
			}
			if (cem.getEndingSalesData() != null) {
				this.selEndingSalesData = cem.getEndingSalesData().getDatasetTableId();
			}

			this.selStartPeriod = cem.getStartPeriod();
			this.selStartYear = cem.getStartYear();
			this.selEndPeriod = cem.getEndPeriod();
			this.selEndYear = cem.getEndYear();
			this.modelSelected = cem.getModelIdSelected();
			this.memo = cem.getMemo();

			if (cem.getCostExchUnits() != null) {
				this.selCostExchUnits = cem.getCostExchUnits().getDatasetTableId();
			}
		}
	}

	private void populateDateUtilityLists() {
		// Populate Months 01 to 12 map beans if left unassigned
		if (this.monthListNumber == null || this.monthListNumber.isEmpty()) {
			this.monthListNumber = new Vector();
			for (int i = 1; i <= 12; i++) {
				String val = (i < 10) ? "0" + i : String.valueOf(i);
				this.monthListNumber.add(new LabelValueBean(val, val));
			}
		}
		// Populate Years around the current operational year envelope
		if (this.yearList == null || this.yearList.isEmpty()) {
			this.yearList = new Vector();
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			for (int y = currentYear - 5; y <= currentYear + 10; y++) {
				String yr = String.valueOf(y);
				this.yearList.add(new LabelValueBean(yr, yr));
			}
		}
	}

	private void processCmd(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);

		if ("CREATE_MODEL".equals(cmd)) {
			ModelMngr mm = new ModelMngr();
			if (modelSelected != null && !modelSelected.isEmpty() && !"NONE".equals(modelSelected)) {
				this.factorModelName = mm.getModelName(ut, Integer.parseInt(modelSelected));
			}
			mm.createModel(ut, getModelFromForm());
		}
	}

	private TCGMModel getModelFromForm() {
		CostExchModel model = new CostExchModel();
		model.setName(this.modelName);
		model.setDesc(this.modelDesc);
		model.setEndPeriod(this.selEndPeriod);
		model.setEndYear(this.selEndYear);
		model.setStartPeriod(this.selStartPeriod);
		model.setStartYear(this.selStartYear);
		model.setMemo(this.memo);
		model.setModelIdSelected(this.modelSelected);
		model.setFactorModelName(this.factorModelName);

		if (model.getCostExchUnits() != null)
			model.getCostExchUnits().setDatasetTableId(this.selCostExchUnits);
		if (model.getStartingSalesData() != null)
			model.getStartingSalesData().setDatasetTableId(this.selStartingSalesData);
		if (model.getEndingSalesData() != null)
			model.getEndingSalesData().setDatasetTableId(this.selEndingSalesData);
		if (model.getRateSet() != null)
			model.getRateSet().setDatasetTableId(this.selRateSet);

		return model;
	}

	private void reset() {
		this.modelName = "";
		this.modelDesc = "";
		this.memo = "";
		this.cmd = "";
	}

	// --- Inner Utility Bean representing option mapping properties safely ---
	public static class LabelValueBean {
		private String label;
		private String value;

		public LabelValueBean(String label, String value) {
			this.label = label;
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public String getValue() {
			return value;
		}
	}

	// --- Struts Parameters and Value Stack Encapsulated Properties ---
	@StrutsParameter
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@StrutsParameter
	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	@StrutsParameter
	public String getSelRateSet() {
		return selRateSet;
	}

	public void setSelRateSet(String selRateSet) {
		this.selRateSet = selRateSet;
	}

	@StrutsParameter
	public String getSelStartPeriod() {
		return selStartPeriod;
	}

	public void setSelStartPeriod(String selStartPeriod) {
		this.selStartPeriod = selStartPeriod;
	}

	@StrutsParameter
	public String getSelStartYear() {
		return selStartYear;
	}

	public void setSelStartYear(String selStartYear) {
		this.selStartYear = selStartYear;
	}

	@StrutsParameter
	public String getSelStartingSalesData() {
		return selStartingSalesData;
	}

	public void setSelStartingSalesData(String selStartingSalesData) {
		this.selStartingSalesData = selStartingSalesData;
	}

	@StrutsParameter
	public String getSelEndPeriod() {
		return selEndPeriod;
	}

	public void setSelEndPeriod(String selEndPeriod) {
		this.selEndPeriod = selEndPeriod;
	}

	@StrutsParameter
	public String getSelEndYear() {
		return selEndYear;
	}

	public void setSelEndYear(String selEndYear) {
		this.selEndYear = selEndYear;
	}

	@StrutsParameter
	public String getSelEndingSalesData() {
		return selEndingSalesData;
	}

	public void setSelEndingSalesData(String selEndingSalesData) {
		this.selEndingSalesData = selEndingSalesData;
	}

	@StrutsParameter
	public String getModelSelected() {
		return modelSelected;
	}

	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	@StrutsParameter
	public String getSelCostExchUnits() {
		return selCostExchUnits;
	}

	public void setSelCostExchUnits(String selCostExchUnits) {
		this.selCostExchUnits = selCostExchUnits;
	}

	@StrutsParameter
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@StrutsParameter
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getFactorModelName() {
		return factorModelName;
	}

	public void setFactorModelName(String factorModelName) {
		this.factorModelName = factorModelName;
	}

	public Vector getRateSets() {
		return rateSets;
	}

	public void setRateSets(Vector rateSets) {
		this.rateSets = rateSets;
	}

	public Vector getSalesData() {
		return salesData;
	}

	public void setSalesData(Vector salesData) {
		this.salesData = salesData;
	}

	public Vector getUnitSets() {
		return unitSets;
	}

	public void setUnitSets(Vector unitSets) {
		this.unitSets = unitSets;
	}

	public Vector getFactorModels() {
		return factorModels;
	}

	public void setFactorModels(Vector factorModels) {
		this.factorModels = factorModels;
	}

	public Vector getMonthListNumber() {
		return monthListNumber;
	}

	public void setMonthListNumber(Vector monthListNumber) {
		this.monthListNumber = monthListNumber;
	}

	public Vector getYearList() {
		return yearList;
	}

	public void setYearList(Vector yearList) {
		this.yearList = yearList;
	}

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
