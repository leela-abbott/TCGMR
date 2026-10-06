package abbott.ai.tcgm17.action.asr;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.AsrTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;
import abbott.ai.tcgm17.action.TCGMAction;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class AsrMaint extends TCGMAction
{
    private static final long serialVersionUID = 1L;
    private static Logger myLogger = LogManager.getLogger("AsrMaint");

    // --- AsrForm attributes inlined ---
    private Asr searchObject = new Asr();
    private List<Asr> asrList = new ArrayList<>();
    private List<Asr> asrErrorList = new ArrayList<>();
    private PagingFilter pagingFilter = new PagingFilter();
    private Sort sortObject = new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);
    private AsrTran addNew = new AsrTran();
    private String errs = "";
    private String cmd = "";
    private String focusField = DBConst.ASR_DFT_FOCUS;
    private String rowToCopy = "";

    public AsrMaint() {
        super();
    }

    public String execute() throws Exception {
        myLogger.debug("Executing perform() method in AsrMaint.");

        this.clearActionErrors();

        if (!this.isSessionValid(request)) {
            return this.getForward();
        }

        if (!this.isModelSelected(request)) {
            return this.getForward();
        }

        processCmd();

        if (cmd != null && cmd.equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_ADV_FILTER)) {
            request.getSession().setAttribute("asrTranAdvFilter", buildSnapshot());
            reset();
            this.setForward(TCGMConstants.FORWARD_ADVANCEDFILTER);
            return this.getForward();
        }

        AsrMngr asrMngr = new AsrMngr();

        try {
            initModelAndDataset(
                this.getState(request).getCurrentModelIdString(),
                DBConst.DEF_DATASET_TABLE_ID);

            if (getErrs().equalsIgnoreCase("on")) {
                asrList = new ArrayList<>(asrErrorList);
                pagingFilter.setTotalRecordsInSet(asrErrorList.size());
                errs = "";
                this.addActionError(getText("error.list"));
            } else {
                pagingFilter.setTotalRecordsInSet(asrMngr.getCount(this.getUserToken(request), searchObject));

                if (getCmd().equalsIgnoreCase("")) {
                    asrList = createEmptyAsrRecs(this.getState(request).getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);
                } else {
                    asrList = new ArrayList<>(asrMngr.getAsr(this.getUserToken(request), searchObject, pagingFilter, sortObject));
                }
                asrErrorList = new ArrayList<>();
            }
            this.setForward(TCGMConstants.FORWARD_SUCCESS);
        } catch (TCGMException ex) {
            this.logger.error(ex.toString(), ex);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
        }

        this.logger.debug(className + " Forward: " + this.getForward());
        return this.getForward();
    }

    // --- AsrForm methods ---

    public void processCmd() {
        if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {
            pagingFilter.setNextpage();
        } else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {
            pagingFilter.setPrevPage();
        } else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)
                || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {
            pagingFilter.setStartRecord(1);
        } else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {
            AsrTran savedAddNew = addNew;
            reset();
            setAddNew(savedAddNew);
        } else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {
            setAddNew(new AsrTran());
            setCmd("");
        } else if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(cmd)) {
            int row = getRowToCopyInt();
            if (row >= 0 && row < asrList.size()) {
                addNew.setAsr(asrList.get(row));
            }
        } else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {
            pagingFilter.setDispSelectedRecordPage();
        }

        setFocusField(DBConst.ASR_DFT_FOCUS);

        if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)
                || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)
                || TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd)
                || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {
            setFocusField("addNew.actionCode");
        }
    }

    public void reset() {
        setCmd("");
        if (addNew != null && addNew.getAsr() != null) {
            addNew.getAsr().clearMsg();
        }
        setSearchObject(new Asr());
        setAddNew(new AsrTran());
        if (asrList != null) {
            for (Asr asr : asrList) {
                if (asr != null) asr.setSelected(false);
            }
        }
        setFocusField(DBConst.ASR_DFT_FOCUS);
    }

    public void initModelAndDataset(String modelId, String datasetTableId) {
        addNew.getAsr().setModelId(modelId);
        addNew.getAsr().setDatasetTableId(datasetTableId);
        searchObject.setModelId(modelId);
        searchObject.setDatasetTableId(datasetTableId);
    }

    private abbott.ai.tcgm.action.form.AsrForm buildSnapshot() {
        abbott.ai.tcgm.action.form.AsrForm snap = new abbott.ai.tcgm.action.form.AsrForm();
        snap.setSearchObject(searchObject);
        snap.setAsrList(new Vector<>(asrList));
        snap.setAsrErrorList(new Vector<>(asrErrorList));
        snap.setPagingFilter(pagingFilter);
        snap.setSortObject(sortObject);
        snap.setAddNew(addNew);
        snap.setErrs(errs);
        snap.setCmd(cmd);
        snap.setFocusField(focusField);
        snap.setRowToCopy(rowToCopy);
        return snap;
    }

    public long getAsrListSize() {
        return asrList == null ? 0 : asrList.size();
    }

    public long getAsrErrorListSize() {
        return asrErrorList == null ? 0 : asrErrorList.size();
    }

    /**
     * List-level getter — satisfies OGNL for:
     *   <s:if test="asrListItem != null && !asrListItem.isEmpty()">
     *   <s:iterator value="asrListItem" ...>
     */
    public List<Asr> getAsrListItem() {
        return asrList;
    }

    @StrutsParameter(depth = 2)
    public void setAsrListItem(List<Asr> asrListItem) {
        this.asrList = asrListItem;
    }

    /** Indexed getter — satisfies Struts2 binding: asrListItem[n].xxx */
    public Asr getAsrListItem(int index) {
        if (asrList == null) return null;
        if (index >= 0 && index < asrList.size()) return asrList.get(index);
        return null;
    }

    /** Indexed setter — satisfies Struts2 binding: asrListItem[n].xxx */
    public void setAsrListItem(int index, Asr asr) {
        if (asrList == null) asrList = new ArrayList<>();
        while (asrList.size() <= index) asrList.add(new Asr());
        asrList.set(index, asr);
    }

    public int getRowToCopyInt() {
        try { return Integer.parseInt(rowToCopy); } catch (Exception e) { return -1; }
    }

    // --- Getters & Setters with @StrutsParameter ---

    public Asr getSearchObject() { return searchObject; }
    @StrutsParameter(depth = 1)
    public void setSearchObject(Asr searchObject) { this.searchObject = searchObject; }

    public List<Asr> getAsrList() { return asrList; }
    @StrutsParameter(depth = 2)
    public void setAsrList(List<Asr> asrList) { this.asrList = asrList; }

    public List<Asr> getAsrErrorList() { return asrErrorList; }
    @StrutsParameter(depth = 2)
    public void setAsrErrorList(List<Asr> asrErrorList) { this.asrErrorList = asrErrorList; }

    public PagingFilter getPagingFilter() { return pagingFilter; }
    @StrutsParameter(depth = 1)
    public void setPagingFilter(PagingFilter pagingFilter) { this.pagingFilter = pagingFilter; }

    public Sort getSortObject() { return sortObject; }
    @StrutsParameter(depth = 1)
    public void setSortObject(Sort sortObject) { this.sortObject = sortObject; }

    public AsrTran getAddNew() { return addNew; }
    @StrutsParameter(depth = 2)
    public void setAddNew(AsrTran addNew) { this.addNew = addNew; }

    public String getErrs() { return errs == null ? "" : errs.trim(); }
    @StrutsParameter
    public void setErrs(String errs) { this.errs = errs; }

    public String getCmd() { return cmd == null ? "" : cmd; }
    @StrutsParameter
    public void setCmd(String cmd) { this.cmd = cmd; }

    public String getFocusField() { return focusField; }
    @StrutsParameter
    public void setFocusField(String focusField) { this.focusField = focusField; }

    public String getRowToCopy() { return rowToCopy; }
    @StrutsParameter
    public void setRowToCopy(String rowToCopy) { this.rowToCopy = rowToCopy; }
}
