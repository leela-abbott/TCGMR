package abbott.ai.tcgm17.action.bpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.BpcsTran;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.BpcsMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.http.HttpSession;

public class BpcsTranMaint extends TCGMAction implements SessionAware {

    private static final long serialVersionUID = 1L;

    private Map<String, Object> session;

    /* ===== Former Form Properties ===== */

    private BpcsTran searchObject = new BpcsTran();
    private List<BpcsTran> bpcsTranList = new ArrayList<>();
    private PagingFilter pagingFilter = new PagingFilter();
    private Sort sortObject =
            new Sort(DBConst.COL_BPC_DEF, DBConst.SORT_ASC);
    private BpcsTran addNew = new BpcsTran();
    private TCGMDataValidation dataVal =
            new TCGMDataValidation("BPCS");
    private String userSelected;
    private List<FactorModel> models;
    private String modelSelected;
    private String cmd;
    private String deleteMessage;
    private String focusField;
    
    private String dspAddNewMsg;
    private BpcsTran _addNew = new BpcsTran();
    private long bpcsTranListSize;
    
    @Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}
    

    /* ===================================================== */

    public String execute() {

        try {
        	
        	//HttpSession session = request.getSession();

            if (session.get("deleteMessage") != null) {
                addActionMessage(getText("success.bpcsTran.delete"));
                session.remove("deleteMessage");
            }

            
            UserToken userToken = this.getUserToken(request);
           // UserToken userToken = (UserToken)session.get(TCGMConstants.SESSION_NAME_USERTOKEN);

            TCGMState state = (TCGMState)session.get(TCGMConstants.SESSION_NAME_STATE);

            if (state == null) {
            	addActionError(getText("error.bpcsTran.form.missing"));
                return "selectModel";
            }
            
            if (!this.isSessionValid(request)) {
    			return this.getForward();
    		}

    		if (!this.isModelSelected(request)) {
    			return this.getForward();
    		}

            processCmd();

            if (TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(cmd)) {
                session.put("bpcsTranAdvFilter", this);
                reset();
                return "advancedFilter";
            }

            BpcsMngr bpcsMngr = new BpcsMngr();
            ModelMngr modelMngr = new ModelMngr();

            initModelAndDataset(state.getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

            /*
             * User filter logic
             */
            if (userSelected == null) {

                if ("".equals(searchObject.getBpcs().getCreateLog().getUserName())) {
                    searchObject.getBpcs().getCreateLog().setUserName(userToken.getUserid());
                    userSelected = userToken.getUserid();
                }

            } else if (!"ALL".equalsIgnoreCase(userSelected)) {

                searchObject.getBpcs().getCreateLog().setUserName(userSelected);
            }

            addNew.getBpcs().setModelIdInt(state.getCurrentModelId());
            addNew.getBpcs().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
            pagingFilter.setTotalRecordsInSet(bpcsMngr.getCount(userToken,searchObject));

            addNew.getBpcs().setBegPeriod("1");
            addNew.getBpcs().setEndPeriod("12");

            bpcsTranList = bpcsMngr.getBpcsTran(userToken,searchObject,pagingFilter,sortObject);

            FactorModel fm = new FactorModel();
            fm.setStatus(TCGMModel.Status.OPEN);

            models = modelMngr.getModels(userToken,fm);
            searchObject.getBpcs().getCreateLog().setUserName(userToken.getUserid());
            modelSelected = TCGMConstants.NONE;
            return SUCCESS;

        } catch (TCGMException ex) {

            addActionError(ex.getMessage());

            session.put(TCGMConstants.SESSION_NAME_EXCEPTION,ex);

            return "exception";
        }
    }

	private void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {

			pagingFilter.setNextpage();

		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {

			pagingFilter.setPrevPage();

		} else if (TCGMConstants.URL_PARM_VAL_FETCH.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

			pagingFilter.setStartRecord(1);

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

			searchObject = new BpcsTran();

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

			addNew = new BpcsTran();

		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {

			pagingFilter.setDispSelectedRecordPage();
		}
	}

    public void reset() {

        cmd = "";

        searchObject = new BpcsTran();

        addNew = new BpcsTran();

        pagingFilter = new PagingFilter();

        focusField = DBConst.BPCS_DFT_FOCUS;
    }

    public void initModelAndDataset(String modelId,String datasetTableId) {

        addNew.getBpcs().setModelId(modelId);
        addNew.getBpcs().setDatasetTableId(datasetTableId);

        searchObject.getBpcs().setModelId(modelId);
        searchObject.getBpcs().setDatasetTableId(datasetTableId);
    }

	public String getDspAddNewMsg() {
		
		StringBuffer sb = new StringBuffer("");
		if(! this.getAddNew().getBpcs().getMsg().equals(""))
		{
			sb.append(" <a class=\"error\" ");
			sb.append(" href=\"#\" ");
			sb.append(" id=\"anchorAddNew\" ");
			sb.append(" name=\"anchorAddNew\" ");
			sb.append(" onclick=\"return false;\" ");
			sb.append(" onmouseover=\"showMsgPopup('anchorAddNew', '");
			sb.append(this.getAddNew().getBpcs().getMsg());
			sb.append(" ');\" ");
			sb.append(" onmouseout='hideMsgPopup();' > ");
			sb.append(" <img src=\"images/exclamation.png\" />  ");
			sb.append("</a>");
		}

		return sb.toString();
	}

	public void setDspAddNewMsg(String dspAddNewMsg) {
		this.dspAddNewMsg = dspAddNewMsg;
	}
	
	public BpcsTran getAddNew()
	{
		return this._addNew;
	}
	/**
	 *
	 * @param addNew BpcsTran object
	 */
	public void setAddNew(BpcsTran addNew)
	{
		this._addNew = addNew;
	}

	public long getBpcsTranListSize() {
		return bpcsTranList == null ? 0 : bpcsTranList.size();
	}

	public void setBpcsTranListSize(long bpcsTranListSize) {
		this.bpcsTranListSize = bpcsTranListSize;
	}
	
	public BpcsTran getSearchObject() { return searchObject; }
    @StrutsParameter(depth = 1)
    public void setSearchObject(BpcsTran searchObject) { this.searchObject = searchObject; }
   
    public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u =
			(abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}
    
    public String getUserSelected() {
		return userSelected;
	}

	@StrutsParameter
	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}


	public List<FactorModel> getModels() {
		return models;
	}

	@StrutsParameter
	public void setModels(List<FactorModel> models) {
		this.models = models;
	}


	public String getFocusField() {
		return focusField;
	}


	public void setFocusField(String focusField) {
		this.focusField = focusField;
	}
    
	
	
	/*
	 * @Override public void setSession( Map<String, Object> session) {
	 * 
	 * this.session = session; }
	 */

    /* Generate getters/setters for all properties */

}