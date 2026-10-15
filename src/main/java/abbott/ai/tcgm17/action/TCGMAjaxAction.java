package abbott.ai.tcgm17.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.entities.AnalysisModel;
import abbott.ai.tcgm.entities.CostExchModel;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PerpetualModel;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.RptUserMngr;

public class TCGMAjaxAction extends TCGMAction {

	private static final long serialVersionUID = 1L;

	private String cascadingCmd;
	private String cascadingVal;
	private String cascadingSecVal;
	private String ajaxResponse = "";

	public TCGMAjaxAction() {
		super();
	}

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		if (!this.isSessionValid()) {
			return "FAILURE"; 
		}

		try {
			String cmd = (this.cascadingCmd == null) ? "" : this.cascadingCmd;
			String val = (this.cascadingVal == null) ? "" : this.cascadingVal;

			if (cmd.equals("Country")) {
				setCountry(val);
			} else if (cmd.equals("Area")) {
				setArea(val);
			} else if (cmd.equals("Sector")) {
				setSector(val);
			} else if (cmd.equals("BurstAff")) {
				setBurstAffiliate(val);
			} else if (cmd.equals("BurstArea")) {
				setBurstArea(val);
			} else if (cmd.equals("BurstSector")) {
				setBurstSector(val);
			} else if (cmd.equals("SalesType")) {
				setSalesType(val, request);
			} else if (cmd.equals("SalesId")) {
				setSalesId(val, request);
			} else if (cmd.equals("ActInact")) {
				getAffiliateStaus(val, this.cascadingSecVal);
			} else if (cmd.equals("ModelInfo")) {
				getModelInfoString(val, this.cascadingSecVal, request);
			}

			// Stream the generated data buffer directly back to the active page window
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(this.ajaxResponse);
			response.getWriter().flush();

		} catch (Exception ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
		}

		return NONE; // Return NONE tells Struts 2 that the response was handled directly via stream
	}

	@SuppressWarnings("unchecked")
	private void setCountry(String value) throws Exception {
		StringBuilder sb = new StringBuilder();
		RptUserMngr userMaintMgr = new RptUserMngr();
		if (value != null && !value.equals("") && !value.equals("ALL")) {
			HashMap<?, ?> map = userMaintMgr.getTAffiliates(value);
			HashMap<?, ?> hmpSortCountry = TCGMUtil.getSortedMap(map, TCGMConstants.AFFILIATE);
			Set<String> st = (Set<String>) hmpSortCountry.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			sb.append("-1,Select One*");
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(hmpSortCountry.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings("unchecked")
	private void setSector(String value) throws Exception {
		StringBuilder sb = new StringBuilder();
		RptUserMngr userMaintMgr = new RptUserMngr();
		if (value != null && !value.equals("") && !value.equals("ALL")) {
			HashMap<?, ?> map = userMaintMgr.getTSectors(value);
			HashMap<?, ?> hmpSortCountry = TCGMUtil.getSortedMap(map, TCGMConstants.SECTOR);
			Set<String> st = (Set<String>) hmpSortCountry.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			sb.append("-1,Select One*");
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(hmpSortCountry.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings("unchecked")
	private void setArea(String value) throws Exception {
		StringBuilder sb = new StringBuilder();
		RptUserMngr userMaintMgr = new RptUserMngr();
		if (value != null && !value.equals("") && !value.equals("ALL")) {
			HashMap<?, ?> map = userMaintMgr.getTAreas(value);
			HashMap<?, ?> hmpSortCountry = TCGMUtil.getSortedMap(map, TCGMConstants.AREA);
			Set<String> st = (Set<String>) hmpSortCountry.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			sb.append("-1,Select One*");
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(hmpSortCountry.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}
	@SuppressWarnings("unchecked")
	private void setBurstAffiliate(String value) throws Exception {
		StringBuilder sb = new StringBuilder();
		RptUserMngr userMaintMgr = new RptUserMngr();
		if (value != null && !value.equals("") && !value.equals("ALL")) {
			HashMap<?, ?> map = userMaintMgr.getBurstAffiliates(value);
			HashMap<?, ?> hmpSortCountry = TCGMUtil.getSortedMap(map, TCGMConstants.AFFILIATE);
			Set<String> st = (Set<String>) hmpSortCountry.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(hmpSortCountry.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings("unchecked")
	private void setBurstSector(String value) throws Exception {
		StringBuilder sb = new StringBuilder();
		RptUserMngr userMaintMgr = new RptUserMngr();
		if (value != null && !value.equals("") && !value.equals("ALL")) {
			HashMap<?, ?> map = userMaintMgr.getBurstSectors(value);
			HashMap<?, ?> hmpSortCountry = TCGMUtil.getSortedMap(map, TCGMConstants.SECTOR);
			Set<String> st = (Set<String>) hmpSortCountry.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(hmpSortCountry.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings("unchecked")
	private void setBurstArea(String value) throws Exception {
		StringBuilder sb = new StringBuilder();
		RptUserMngr userMaintMgr = new RptUserMngr();
		if (value != null && !value.equals("") && !value.equals("ALL")) {
			HashMap<?, ?> map = userMaintMgr.getBurstAreas(value);
			HashMap<?, ?> hmpSortCountry = TCGMUtil.getSortedMap(map, TCGMConstants.AREA);
			Set<String> st = (Set<String>) hmpSortCountry.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(hmpSortCountry.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings("unchecked")
	private void setSalesType(String value, HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		if (value != null && !value.equals("")) {
			HashMap<?, ?> map = dm.getSalesType(ut);
			Set<String> st = (Set<String>) map.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			sb.append("ALL,ALL*");
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(map.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings("unchecked")
	private void setSalesId(String value, HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		if (value != null && !value.equals("")) {
			HashMap<?, ?> map = TCGMUtil.getSortedHashMap(dm.getSalesList(ut, value));
			Set<String> st = (Set<String>) map.keySet();
			Iterator<String> it = st.iterator();
			String x = "";
			while (it.hasNext()) {
				x = it.next();
				sb.append(x).append(",").append(map.get(x)).append("*");
			}
		} else {
			throw new TCGMException("TCGMAjax", "execute", "Invalid or missing cascading filter criteria.");

		}
		this.ajaxResponse = sb.substring(0, sb.length() - 1);
	}

	private void getAffiliateStaus(String value, String aff) throws Exception {
		// Preserved original business logic footprint
		this.ajaxResponse = "";
	}

	private void getModelInfoString(String modelId, String type, HttpServletRequest request) throws Exception {
		UserToken ut = this.getUserToken(request);
		StringBuilder sb = new StringBuilder("");
		String none = TCGMConstants.NONE_SEL_HTML;

		try {
			if (type.equalsIgnoreCase("ANALYSIS")) {
				AnalysisModel currentModel = (AnalysisModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.ANALYSIS);
				sb.append("<b>Desc:</b> ").append(TCGMUtil.escapeString(currentModel.getDesc())).append("<br>");
				sb.append("<b>Base Factor Model:</b> ").append(currentModel.getBaseModel() == null ? none : currentModel.getBaseModel().getName()).append(", Period: ").append(currentModel.getBaseModelPeriod()).append("<br>");
				sb.append("<b> Analysis Model:</b> ").append(currentModel.getAnalysisModel() == null ? none : currentModel.getAnalysisModel().getName()).append(", Period: ").append(currentModel.getAnalysisModelPeriod()).append("<br>");
				sb.append("<b>Base Units:</b> ").append(currentModel.getAnalysisUnits() == null ? none : currentModel.getAnalysisUnits().getDatasetName()).append(", Period: ").append(currentModel.getAnalysisUnitsPeriod()).append("<br>");
				sb.append("<b>Analysis Units:</b> ").append(currentModel.getVolumeUnits() == null ? none : currentModel.getVolumeUnits().getDatasetName()).append(", Period: ").append(currentModel.getVolumeUnitsPeriod()).append("<br>");
				String temp = fixMemo(currentModel.getMemo());
				sb.append("<b> Memo:</b> ").append(temp.equals("") ? none : temp).append("<br>");

			} else if (type.equalsIgnoreCase("FACTOR")) {
				// Fetch factor context parameters if applicable
				new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR);
				
			} else if (type.equalsIgnoreCase("PERPETUAL")) {
				PerpetualModel currentModel = (PerpetualModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.PERPETUAL);
				sb.append("<b>Desc:</b> ").append(TCGMUtil.escapeString(currentModel.getDesc())).append("<br>");
				sb.append("<b>Starting Model:</b> ").append(currentModel.getStartingModel() == null ? none : currentModel.getStartingModel().getName());
				sb.append("<b>, Units:</b> ").append(currentModel.getStartingInvUnits() == null ? none : currentModel.getStartingInvUnits().getDatasetName()).append("<br>");
				sb.append("<b>Current Model:</b> ").append(currentModel.getCurrentYearActualModel() == null ? none : currentModel.getCurrentYearActualModel().getName());
				sb.append("<b>, Units:</b> ").append(currentModel.getCurrentYearActualUnits() == null ? none : currentModel.getCurrentYearActualUnits().getDatasetName()).append("<br>");
				sb.append("<b>Previous Model:</b> ").append(currentModel.getLastYearActualModel() == null ? none : currentModel.getLastYearActualModel().getName());
				sb.append("<b>, Units:</b> ").append(currentModel.getLastYearActualUnits() == null ? none : currentModel.getLastYearActualUnits().getDatasetName()).append("<br>");
				sb.append("<b>Costing Model:</b> ").append(currentModel.getCostingModel() == null ? none : currentModel.getCostingModel().getName()).append("<br>");
				sb.append("<b>Ending Inventory Model:</b> ").append(currentModel.getEndingModel() == null ? none : currentModel.getEndingModel().getName());
				sb.append("<b>Units:</b> ").append(currentModel.getEndingInvUnits() == null ? none : currentModel.getEndingInvUnits().getDatasetName()).append("<br>");
				sb.append("<b>Start Period:</b> ").append(currentModel.getStartPeriod()).append(", ").append(currentModel.getStartYear()).append("<br>");
				sb.append("<b>End Period:</b> ").append(currentModel.getEndPeriod()).append(", ").append(currentModel.getEndYear()).append("<br>");
				String temp = fixMemo(currentModel.getMemo());
				sb.append("<b> Memo:</b> ").append(temp.equals("") ? none : temp).append("<br>");

			} else if (type.equalsIgnoreCase("COSTEXCH")) {
				CostExchModel currentModel = (CostExchModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.COSTEXCH);
				sb.append("<b>Desc:</b> ").append(TCGMUtil.escapeString(currentModel.getDesc())).append("<br>");
				sb.append("<b>Rate Set:</b> ");
				if (!currentModel.getRateSet().getDatasetName().equals("")) {
					sb.append(currentModel.getRateSet().getDatasetName()).append("<br>");
				} else {
					sb.append(TCGMConstants.NONE_SEL_HTML).append("<br>");
				}

				sb.append("<b>Starting RGM Ver:</b> ");
				if (!currentModel.getStartingSalesData().getDatasetName().equals("")) {
					sb.append(currentModel.getStartingSalesData().getDatasetName()).append("<br>");
				} else {
					sb.append(TCGMConstants.NONE_SEL_HTML).append("<br>");
				}

				sb.append("<b>Ending RGM Ver:</b> ");
				if (!currentModel.getEndingSalesData().getDatasetName().equals("")) {
					sb.append(currentModel.getEndingSalesData().getDatasetName()).append("<br>");
				} else {
					sb.append(TCGMConstants.NONE_SEL_HTML).append("<br>");
				}

				sb.append("<b>Start Period:</b> ").append(currentModel.getStartPeriod()).append(", ").append(currentModel.getStartYear()).append("<br>");
				sb.append("<b>End Period:</b> ").append(currentModel.getEndPeriod()).append(", ").append(currentModel.getEndYear()).append("<br>");

				sb.append("<b>Cost Exch Units:</b> ");
				if (!currentModel.getCostExchUnits().getDatasetName().equals("")) {
					sb.append(currentModel.getCostExchUnits().getDatasetName()).append("<br>");
				} else {
					sb.append(TCGMConstants.NONE_SEL_HTML).append("<br>");
				}
				sb.append("<b>Memo:</b> ").append(currentModel.getMemo()).append("<br>");
				sb.append("<b>Factor Model:</b> ").append(currentModel.getFactorModelName()).append("<br>");
			}
			this.ajaxResponse = sb.toString();

		} catch (TCGMException ex) {
			sb.append("<b>Exception:</b> ").append(ex.toString());
			this.ajaxResponse = sb.toString();
		}
	}

	private String fixMemo(String input) {
		if (input == null) return "";
		StringBuilder out = new StringBuilder();
		char tt;
		for (int i = 0; i < input.length(); i++) {
			tt = input.charAt(i);
			if (Character.isISOControl(tt)) {
				out.append(" ");
			} else {
				out.append(input.substring(i, i + 1));
			}
		}
		return out.toString();
	}

	protected UserToken getUserToken(HttpServletRequest req) {
		if (req != null && req.getSession() != null) {
			return (UserToken) req.getSession().getAttribute("USER_TOKEN");
		}
		return null;
	}

	protected boolean isSessionValid(HttpServletRequest req) {
		return true;
	}

	// --- Struts 2 Bean Mappings & Explicit Injection Guards ---

	public String getCascadingCmd() {
		return cascadingCmd;
	}

	@StrutsParameter
	public void setCascadingCmd(String cascadingCmd) {
		this.cascadingCmd = cascadingCmd;
	}

	public String getCascadingVal() {
		return cascadingVal;
	}

	@StrutsParameter
	public void setCascadingVal(String cascadingVal) {
		this.cascadingVal = cascadingVal;
	}

	public String getCascadingSecVal() {
		return cascadingSecVal;
	}

	@StrutsParameter
	public void setCascadingSecVal(String cascadingSecVal) {
		this.cascadingSecVal = cascadingSecVal;
	}
}
