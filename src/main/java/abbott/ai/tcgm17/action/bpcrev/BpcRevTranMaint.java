package abbott.ai.tcgm17.action.bpcrev;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.BpcRevTran;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.Option;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.BpcRevMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.http.HttpServletRequest;

public class BpcRevTranMaint extends TCGMAction implements SessionAware {

	private static final long serialVersionUID = 9056652888656580431L;
	

	 private Map<String, Object> session;
	    public BpcRevTranMaint()
		{
			super();
		}
	// ==========================
    // TCGMForm Properties
    // ==========================
	
	private String showModels = "";
    private String rowToCopy = "";
    private String cmd = "";
    private String focusField = "";
    private String returnLocation = "";
    private String modelSelected = "";

    private List models = new ArrayList();
    private List closedModels = new ArrayList();

    private TCGMModel.Type modelType;

    private List monthList = new ArrayList();
    private Option[] monthListNumber;
    private Option[] twoDigitMonthListNumber;
    private Option[] twoDigitMonthListNumberWithLabel;
    private Option[] periodMonthList;
    private Option[] yearList;
    private Option[] exportSystemList;

    // ==========================
    // BpcRevTranForm Properties
    // ==========================

    private BpcRevTran searchObject = new BpcRevTran();
    private List<BpcRevTran> bpcRevTranList =  new ArrayList<BpcRevTran>();
    private PagingFilter pagingFilter = new PagingFilter();
    private Sort sortObject = new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);
    private BpcRevTran addNew = new BpcRevTran();
    private TCGMDataValidation dataVal = new TCGMDataValidation("BPCREV");
    private String userSelected;
    
    @Override
   	public void withSession(Map<String, Object> session) {
   		this.session = session;
   	}

    // ==========================
    // Execute Method
    // ==========================

    public String execute() throws Exception {

        HttpServletRequest request =
                ServletActionContext.getRequest();


        if (session.get("deleteMessage") != null) {
            addActionMessage(getText("success.bpcsTran.delete"));
            session.remove("deleteMessage");
        }
        
         UserToken userToken = this.getUserToken(request);
         TCGMState state = (TCGMState)session.get(TCGMConstants.SESSION_NAME_STATE);

        processCmd();

        if(TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(cmd)) {
            session.put("bpcRevTranAdvFilter",this);
            reset();
            return "advancedFilter";
        }


        BpcRevMngr bpcRevMngr = new BpcRevMngr();
        ModelMngr modelMngr = new ModelMngr();

        try {

            initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);

            if(userSelected == null) {

                if(searchObject.getBpcRev().getCreateLog().getUserName().equals("")) {

                    searchObject.getBpcRev().getCreateLog().setUserName(userToken.getUserid());

                    userSelected =userToken.getUserid();
                }

			} else if (!"ALL".equalsIgnoreCase(userSelected)) {

				searchObject.getBpcRev().getCreateLog().setUserName(userSelected);
			}

			addNew.getBpcRev().setModelIdInt(state.getCurrentModelId());

			addNew.getBpcRev().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);

			pagingFilter.setTotalRecordsInSet(bpcRevMngr.getCount(userToken, searchObject));

			addNew.getBpcRev().setBegPeriod("1");
			addNew.getBpcRev().setEndPeriod("12");

			bpcRevTranList = bpcRevMngr.getBpcRevTran(userToken, searchObject, pagingFilter, sortObject);

			FactorModel fm = new FactorModel();
			fm.setStatus(TCGMModel.Status.OPEN);

			models = modelMngr.getModels(userToken, fm);

			searchObject.getBpcRev().getCreateLog().setUserName(userToken.getUserid());

			modelSelected = TCGMConstants.NONE;

            return SUCCESS;

        } catch (TCGMException ex) {

            addActionError(ex.getMessage());
            session.put(TCGMConstants.SESSION_NAME_EXCEPTION,ex);
            return "failure";
        }
    }
    
    public void initModelAndDataset(
            String modelId,
            String datasetTableId) {

        if (searchObject == null) {
            searchObject = new BpcRevTran();
        }

        if (addNew == null) {
            addNew = new BpcRevTran();
        }

        if (modelId != null && modelId.trim().length() > 0) {

            searchObject.getBpcRev().setModelId(modelId);
            addNew.getBpcRev().setModelId(modelId);

            try {

                int modelIdInt =
                        Integer.parseInt(modelId);

                searchObject.getBpcRev()
                        .setModelIdInt(modelIdInt);

                addNew.getBpcRev()
                        .setModelIdInt(modelIdInt);

            } catch (NumberFormatException e) {
                // ignore
            }
        }

        if (datasetTableId != null) {

            searchObject.getBpcRev()
                    .setDatasetTableId(datasetTableId);

            addNew.getBpcRev()
                    .setDatasetTableId(datasetTableId);
        }
    }
    
    public void reset() {

        searchObject = new BpcRevTran();

        addNew = new BpcRevTran();

        userSelected = null;

        modelSelected = TCGMConstants.NONE;
    }
    

    private void processCmd() {

		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_NEXT_PAGE))
		{
			this.getPagingFilter().setNextpage();
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_PREV_PAGE))
		{
			this.getPagingFilter().setPrevPage();
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FETCH)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			this.getPagingFilter().setStartRecord(1);
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_FILTER))
		{
			//this.reset(mapping,request);
			this.setSearchObject(new BpcRevTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW))
		{
			this.setAddNew(new BpcRevTran());
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE))
		{
			this.getPagingFilter().setDispSelectedRecordPage();
		}
		/*****************************************************************************************/
		/**
		 * The following logic implements cursor navigation according to the last command executed
		 * by the user.  Based on the button clicked by the user, the cursor will be position on
		 * predetermined fields in order to improve transaction processing time.
		 */
		this.setFocusField(DBConst.DFT_TRAN_FOCUS);
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_FILTER)||this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADV_FILTER))
		{
			//this.setFocusField("bpcRevTranListItem[0].actionCode");
			this.setFocusField("addNew.actionCode");
		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_ADD) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MASS_UPDATE))
		{
			this.setFocusField("addNew.actionCode");
		}
	
		
	}
    
    public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u =
			(abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}

	public String getShowModels() {
		return showModels;
	}

	public void setShowModels(String showModels) {
		this.showModels = showModels;
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

	public List getMonthList() {
		return monthList;
	}

	public void setMonthList(List monthList) {
		this.monthList = monthList;
	}

	public Option[] getMonthListNumber() {
		return monthListNumber;
	}

	public void setMonthListNumber(Option[] monthListNumber) {
		this.monthListNumber = monthListNumber;
	}

	public Option[] getTwoDigitMonthListNumber() {
		return twoDigitMonthListNumber;
	}

	public void setTwoDigitMonthListNumber(Option[] twoDigitMonthListNumber) {
		this.twoDigitMonthListNumber = twoDigitMonthListNumber;
	}

	public Option[] getTwoDigitMonthListNumberWithLabel() {
		return twoDigitMonthListNumberWithLabel;
	}

	public void setTwoDigitMonthListNumberWithLabel(Option[] twoDigitMonthListNumberWithLabel) {
		this.twoDigitMonthListNumberWithLabel = twoDigitMonthListNumberWithLabel;
	}

	public Option[] getPeriodMonthList() {
		return periodMonthList;
	}

	public void setPeriodMonthList(Option[] periodMonthList) {
		this.periodMonthList = periodMonthList;
	}

	public Option[] getYearList() {
		return yearList;
	}

	public void setYearList(Option[] yearList) {
		this.yearList = yearList;
	}

	public Option[] getExportSystemList() {
		return exportSystemList;
	}

	public void setExportSystemList(Option[] exportSystemList) {
		this.exportSystemList = exportSystemList;
	}

	public BpcRevTran getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(BpcRevTran searchObject) {
		this.searchObject = searchObject;
	}

	public List<BpcRevTran> getBpcRevTranList() {
		return bpcRevTranList;
	}

	public void setBpcRevTranList(List<BpcRevTran> bpcRevTranList) {
		this.bpcRevTranList = bpcRevTranList;
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

	public BpcRevTran getAddNew() {
		return addNew;
	}

	public void setAddNew(BpcRevTran addNew) {
		this.addNew = addNew;
	}

	public TCGMDataValidation getDataVal() {
		return dataVal;
	}

	public void setDataVal(TCGMDataValidation dataVal) {
		this.dataVal = dataVal;
	}

	public String getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}
	
	//Getter and Setter
	
	    // ... existing properties ...
	    // Helper method for list size
	    public long getBpcRevTranListSize() {
	        return bpcRevTranList == null ? 0 : bpcRevTranList.size();
	    }
	    // Individual item access
	    public BpcRevTran getBpcRevTranList(int index) {
	        if (index >= 0 && index < bpcRevTranList.size()) {
	            return bpcRevTranList.get(index);
	        }
	        return null;
	    }
	    public void setBpcRevTranList(int index, BpcRevTran item) {
	        if (index >= 0 && index < bpcRevTranList.size()) {
	            bpcRevTranList.set(index, item);
	        }
	    }
	    // ... rest of the action class ...
	
	
}