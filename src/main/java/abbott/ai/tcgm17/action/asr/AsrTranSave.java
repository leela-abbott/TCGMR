package abbott.ai.tcgm17.action.asr;

import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.AsrTran;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class AsrTranSave extends TCGMAction implements SessionAware {

	private static final long serialVersionUID = 1L;

	/*
	 * ===================================================== Properties from
	 * AsrTranForm =====================================================
	 */
	private AsrTran searchObject = new AsrTran();
	private Vector<AsrTran> asrTranList = new Vector<>();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
	private AsrTran addNew = new AsrTran();
	private String userSelected;

	/*
	 * ===================================================== Properties inherited
	 * from TCGMForm =====================================================
	 */
	private String rowToCopy = "";
	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private Vector models = new Vector();
	private Vector closedModels = new Vector();

	private String showModels = "";

	private abbott.ai.tcgm.entities.TCGMModel.Type modelType;
	
	private Map<String, Object> session;
	
	Vector<AsrTran> asrTranListExisting = new Vector<>();
	private int asrTranListSize;

	

	/*
	 * ===================================================== Execute Method
	 * =====================================================
	 */
	@Override
	public String execute() {

		String methodName = "execute";

		try {

			if (!isSessionValid()) {
				return LOGIN;
			}

			UserToken userToken = getUserToken();

			if (!isModelSelected()) {
				return TCGMConstants.G_FORWARD_SELECT_MODEL;
			}

			processCmd();

			boolean duplicate = true;

			AsrMngr asrMngr = new AsrMngr();
			ModelMngr modelMngr = new ModelMngr();
			String currentModelId = getState(request).getCurrentModelIdString();
			
			asrTranListExisting = asrMngr.getAsrTran(userToken, searchObject, pagingFilter, sortObject);

			initModelAndDataset(getState().getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID, asrTranListExisting);
			
			this.getAddNew().setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
			

			/*
			 * User Selection Logic
			 */
			if (userSelected == null) {

				if ("".equals(searchObject.getAsr().getCreateLog().getUserName())) {

					searchObject.getAsr().getCreateLog().setUserName(userToken.getUserid());

					userSelected = userToken.getUserid();
				}
			} else if (!"ALL".equalsIgnoreCase(userSelected)) {

				searchObject.getAsr().getCreateLog().setUserName(userSelected);
			}

			if ("ALL".equalsIgnoreCase(userSelected)) {

				searchObject.getAsr().getCreateLog().setUserName(null);

				userSelected = "ALL";
			}

			/*
			 * Command Processing
			 */
			
			
			
			if (TCGMConstants.URL_PARM_VAL_ADD.equals(cmd)) {

				duplicate = asrMngr.addNewAsrTran(userToken, addNew);

			} else if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {

				asrMngr.saveSelectedAsrTran(userToken, asrTranList);

			} else if (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

				asrMngr.massUpdate(userToken, searchObject, addNew);
			} else if (!"".equals(cmd.trim())) {

				throw new TCGMException(className, methodName, "Invalid command in action");
			}

			if (duplicate) {
				addActionMessage(getText("success.asrtran.saved"));
			} else {
				addActionError(getText("error.duplicate.looping"));
			}

			/*
			 * Reload Page Data
			 */
			pagingFilter.setTotalRecordsInSet(asrMngr.getCount(userToken, searchObject));

			asrTranList = asrMngr.getAsrTran(userToken, searchObject, pagingFilter, sortObject);

			models = modelMngr.getModels(userToken, new FactorModel());

			searchObject.getAsr().getCreateLog().setUserName(userToken.getUserid());

			modelSelected = TCGMConstants.NONE;

			return SUCCESS;

		} catch (TCGMDuplicateItemException ex) {

			logger.error(ex.getMessage(), ex);

			addActionError(getText("error.duplicate"));

			try {

				AsrMngr asrMngr = new AsrMngr();

				initModelAndDataset(getState().getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID,asrTranListExisting);

				pagingFilter.setTotalRecordsInSet(asrMngr.getCount(getUserToken(), searchObject));

				asrTranList = asrMngr.getAsrTran(getUserToken(), searchObject, pagingFilter, sortObject);

				return SUCCESS;

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				addActionError(e.getMessage());

				return ERROR;
			}
		} catch (Exception ex) {

			logger.error(ex.getMessage(), ex);

			addActionError(ex.getMessage());

			return ERROR;
		}
	}
	
	public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u =
			(abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}
	
	@Override
	public void validate() {

		if (cmd == null) {
			cmd = "";
		}

		// asrTran.getAsr().getRptProduct().getInvCode()
		  if(this.getAddNew().getAsr().getRptProduct().getInvCode().isEmpty() 
				  || null == this.getAddNew().getAsr().getRptProduct().getInvCode())   {
		  //addActionError(getText("error.asr.form.missing"));
		  //addFieldError("rptUser.userid", getText("error.userMaint.required.userid"));
		    this.getAddNew().getAsr().appendMsg("Rpt Inv Code Cannot be blank");
		  }
		  if(this.getAddNew().getAsr().getSupProduct().getInvCode().isEmpty() 
				  || null == this.getAddNew().getAsr().getSupProduct().getInvCode())   {
		  //addActionError(getText("error.asr.form.missing"));
		  //addFieldError("rptUser.userid", getText("error.userMaint.required.userid"));
		    this.getAddNew().getAsr().appendMsg("Sup Inv Code Cannot be blank");
		  }
		  
		  //save selected
		 
		

		if (TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd) && addNew == null) {

			addActionError(getText("error.asr.form.missing"));
		}
		
		if(!this.getAddNew().getAsr().getMsg().isEmpty()
				&& !cmd.equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_SAVE_SELECTED)) {
			addFieldError("Data Field Error", "Data Field Error");
		}
	}
	

	/*
	 * Helper methods migrated from Form Bean
	 */
	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {

			pagingFilter.setNextpage();
		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {

			pagingFilter.setPrevPage();
		} else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

			pagingFilter.setStartRecord(1);
		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {

			pagingFilter.setDispSelectedRecordPage();
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

			searchObject = new AsrTran();
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

			addNew = new AsrTran();
		}

		focusField = DBConst.DFT_TRAN_FOCUS;

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)
				|| TCGMConstants.URL_PARM_VAL_ADD.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

			focusField = "addNew.actionCode";
		}
	}

	public void initModelAndDataset(String modelId, String datasetTableId, Vector<AsrTran> asrTranListExisting) {

		addNew.getAsr().setModelId(modelId);
		addNew.getAsr().setDatasetTableId(datasetTableId);

		searchObject.getAsr().setModelId(modelId);
		searchObject.getAsr().setDatasetTableId(datasetTableId);
		
		if (asrTranList != null) {
			for (AsrTran asrTran : asrTranList) {
				if (asrTran != null) {
					asrTran.getAsr().setModelId(modelId);
					asrTran.getAsr().setDatasetTableId(datasetTableId);
					asrTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
				}
			}
		}
		
		for (int i = 0; i < asrTranListExisting.size(); i++) {
			asrTranList.get(i).setAsrTranId(asrTranListExisting.get(i).getAsrTranId());
		}
		
	}

	@Override
	public void withSession(Map<String, Object> session) {
      this.session= session;
     }

	@StrutsParameter(depth = 3)
	public AsrTran getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(AsrTran searchObject) {
		this.searchObject = searchObject;
	}
	
	@StrutsParameter(depth = 4)
	public Vector getAsrTranList()
	{
		if(this.asrTranList == null)
		{
			this.setAsrTranList(new Vector());
		}
		return this.asrTranList;
	}
	/**
	 *
	 * @param asrTranList vector of AsrTran objects
	 */
	public void setAsrTranList(Vector asrTranList)
	{
		this.asrTranList = asrTranList;
	}
	
	

	/*
	 * @StrutsParameter(depth = 4) public Vector<AsrTran> getAsrTranList() { return
	 * asrTranList; }
	 * 
	 * @StrutsParameter(depth = 4) public void setAsrTranList(Vector<AsrTran>
	 * asrTranList) { this.asrTranList = asrTranList; }
	 */

	@StrutsParameter(depth =2)
	public PagingFilter getPagingFilter() {
		return pagingFilter;
	}

	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter;
	}

	@StrutsParameter(depth =1)
	public Sort getSortObject() {
		return sortObject;
	}

	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject;
	}

	@StrutsParameter(depth = 4)
	public AsrTran getAddNew() {
		return addNew;
	}

	public void setAddNew(AsrTran addNew) {
		this.addNew = addNew;
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

	public Vector getModels() {
		return models;
	}

	public void setModels(Vector models) {
		this.models = models;
	}

	public Vector getClosedModels() {
		return closedModels;
	}

	public void setClosedModels(Vector closedModels) {
		this.closedModels = closedModels;
	}

	public String getShowModels() {
		return showModels;
	}

	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	public abbott.ai.tcgm.entities.TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(abbott.ai.tcgm.entities.TCGMModel.Type modelType) {
		this.modelType = modelType;
	}
	public int getAsrTranListSize() {
	    if (asrTranList != null) {
	        return asrTranList.size();
	    }
	    return 0;
	}

	public void setAsrTranListSize(int asrTranListSize) {
	    this.asrTranListSize = asrTranListSize;
	}
	
	
}	