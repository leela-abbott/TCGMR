package abbott.ai.tcgm17.action.bpcex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.BpcExTran;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.BpcExMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.http.HttpSession;

public class BpcExTranMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5907326414364785628L;
	private Map<String, Object> session;
	private BpcExTran searchObject = new BpcExTran();
	private List<BpcExTran> bpcExTranList = new ArrayList<>();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_BPC_EXC_DEF, DBConst.SORT_ASC);
	private BpcExTran addNew = new BpcExTran();
	private String userSelected;

	// TCGM Form
	private String rowToCopy = "";
	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private String showModels = "";

	private List models = new ArrayList();
	private List closedModels = new ArrayList();

	private TCGMModel.Type modelType;

	@Override
	public void withSession(Map<String, Object> arg0) {

		this.session = session;

	}

	@Override
	public String execute() {

		try {

			HttpSession session = ServletActionContext.getRequest().getSession();
			String deleteMessage = (String) session.getAttribute("deleteMessage");
			if (deleteMessage != null && !deleteMessage.trim().isEmpty()) {
				addActionMessage(getText("success.bpcextran.delete"));
				session.removeAttribute("deleteMessage");
			}

			processCmd();

			if (TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(cmd)) {

				session.setAttribute("bpcExTranAdvFilter", this);

				reset();

				return "advancedFilter";
			}

			UserToken userToken = getUserToken();

			BpcExMngr bpcExMngr = new BpcExMngr();
			ModelMngr modelMngr = new ModelMngr();

			initModelAndDataset(getState().getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			if (userSelected == null) {

				if (searchObject.getBpcEx().getCreateLog().getUserName().equals("")) {

					searchObject.getBpcEx().getCreateLog().setUserName(userToken.getUserid());

					userSelected = userToken.getUserid();
				}
			} else if (!"ALL".equalsIgnoreCase(userSelected)) {

				searchObject.getBpcEx().getCreateLog().setUserName(userSelected);
			}

			addNew.getBpcEx().setModelIdInt(getState().getCurrentModelId());

			addNew.getBpcEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);

			pagingFilter.setTotalRecordsInSet(bpcExMngr.getCount(userToken, searchObject));

			addNew.getBpcEx().setBegPeriod("1");
			addNew.getBpcEx().setEndPeriod("12");

			bpcExTranList = bpcExMngr.getBpcExTran(userToken, searchObject, pagingFilter, sortObject);

			FactorModel fm = new FactorModel();
			fm.setStatus(TCGMModel.Status.OPEN);

			models = modelMngr.getModels(userToken, fm);

			searchObject.getBpcEx().getCreateLog().setUserName(userToken.getUserid());

			modelSelected = TCGMConstants.NONE;

			return SUCCESS;

		} catch (TCGMException ex) {

			logger.error(ex.getMessage(), ex);

			ServletActionContext.getRequest().setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

			return ERROR;
		}
	}

	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {
			pagingFilter.setNextpage();
		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {
			pagingFilter.setPrevPage();
		} else if (TCGMConstants.URL_PARM_VAL_FETCH.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {
			pagingFilter.setStartRecord(1);
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {
			searchObject = new BpcExTran();
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {
			addNew = new BpcExTran();
		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {
			pagingFilter.setDispSelectedRecordPage();
		}

		focusField = DBConst.DFT_TRAN_FOCUS;

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

			focusField = "addNew.actionCode";
		} else if (TCGMConstants.URL_PARM_VAL_ADD.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

			focusField = "addNew.actionCode";
		}
	}

	public void reset() {

		cmd = "";

		if (addNew != null && addNew.getBpcEx() != null) {
			addNew.getBpcEx().clearMsg();
		}

		searchObject = new BpcExTran();
		addNew = new BpcExTran();

		pagingFilter = new PagingFilter();

		if (bpcExTranList != null) {

			for (BpcExTran tran : bpcExTranList) {

				if (tran != null && tran.getBpcEx() != null) {

					tran.getBpcEx().setSelected(false);
					tran.getBpcEx().setMsg("");
				}
			}
		}

		focusField = DBConst.BPC_EX_DFT_FOCUS;
	}

	public void initModelAndDataset(String modelId, String datasetTableId) {

		addNew.getBpcEx().setModelId(modelId);
		addNew.getBpcEx().setDatasetTableId(datasetTableId);

		searchObject.getBpcEx().setModelId(modelId);
		searchObject.getBpcEx().setDatasetTableId(datasetTableId);
	}
	
	
	//Setter and Getter

	public BpcExTran getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(BpcExTran searchObject) {
		this.searchObject = searchObject;
	}

	public List<BpcExTran> getBpcExTranList() {
		return bpcExTranList;
	}

	public void setBpcExTranList(List<BpcExTran> bpcExTranList) {
		this.bpcExTranList = bpcExTranList;
	}

	public PagingFilter getPagingFilter() {
		return pagingFilter;
	}

	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter;
	}

	public Sort getSortObject() {
		return sortObject;
	}

	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject;
	}

	public BpcExTran getAddNew() {
		return addNew;
	}

	public void setAddNew(BpcExTran addNew) {
		this.addNew = addNew;
	}
	
	public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u =
			(abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}

	public String getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getFocusField() {
		return focusField;
	}

	public void setFocusField(String focusField) {
		this.focusField = focusField;
	}

	public String getReturnLocation() {
		return returnLocation;
	}

	public void setReturnLocation(String returnLocation) {
		this.returnLocation = returnLocation;
	}

	public String getModelSelected() {
		return modelSelected;
	}

	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
	}

	public String getShowModels() {
		return showModels;
	}

	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	public List getModels() {
		return models;
	}

	public void setModels(List models) {
		this.models = models;
	}

	public List getClosedModels() {
		return closedModels;
	}

	public void setClosedModels(List closedModels) {
		this.closedModels = closedModels;
	}

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
	}
	
	
	

}
