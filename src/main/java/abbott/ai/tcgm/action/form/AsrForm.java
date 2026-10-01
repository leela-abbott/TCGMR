package abbott.ai.tcgm.action.form;

import java.util.List;
import java.util.Vector;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.AsrTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;

public class AsrForm {

    private Asr searchObject = new Asr();

    private Vector<Asr> asrList = new Vector<>();

    private Vector<Asr> asrErrorList = new Vector<>();

    private PagingFilter pagingFilter = new PagingFilter();

    private Sort sortObject =
        new Sort(DBConst.COL_ASR_DEF, DBConst.SORT_ASC);

    private AsrTran addNew = new AsrTran();

    private String errs = "";

    private String cmd = "";

    private String focusField = DBConst.ASR_DFT_FOCUS;

    private String rowToCopy = "";

    public AsrForm() {
        this.focusField = DBConst.ASR_DFT_FOCUS;
    }

    public Asr getSearchObject() {
        return searchObject;
    }

    public void setSearchObject(Asr searchObject) {
        this.searchObject = searchObject;
    }

    public Vector<Asr> getAsrList() {
        return asrList;
    }

    public void setAsrList(Vector<Asr> list) {
        this.asrList = list;
    }

    public Vector<Asr> getAsrErrorList() {
        return asrErrorList;
    }

    public void setAsrErrorList(Vector<Asr> asrErrorList) {
        this.asrErrorList = asrErrorList;
    }

    public long getAsrListSize() {
        return asrList == null ? 0 : asrList.size();
    }

    public long getAsrErrorListSize() {
        return asrErrorList == null
                ? 0
                : asrErrorList.size();
    }

    public Asr getAsrListItem(int index) {

        if (index >= 0 && index < asrList.size()) {
            return asrList.get(index);
        }

        return null;
    }

    public void setAsrListItem(Asr asr, int index) {

        while (asrList.size() <= index) {
            asrList.add(new Asr());
        }

        asrList.set(index, asr);
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

    public AsrTran getAddNew() {
        return addNew;
    }

    public void setAddNew(AsrTran addNew) {
        this.addNew = addNew;
    }

    public String getErrs() {
        return errs == null ? "" : errs.trim();
    }

    public void setErrs(String errs) {
        this.errs = errs;
    }

    public String getCmd() {
        return cmd == null ? "" : cmd;
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

    public String getRowToCopy() {
        return rowToCopy;
    }

    public void setRowToCopy(String rowToCopy) {
        this.rowToCopy = rowToCopy;
    }

    public int getRowToCopyInt() {

        try {
            return Integer.parseInt(rowToCopy);
        } catch (Exception e) {
            return -1;
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
                if (asr != null) {
                    asr.setSelected(false);
                }
            }
        }

        setFocusField(DBConst.ASR_DFT_FOCUS);
    }

    public void processCmd() {

        if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {

            pagingFilter.setNextpage();

        } else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {

            pagingFilter.setPrevPage();

        } else if (
            TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd)
            || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

            pagingFilter.setStartRecord(1);

        } else if (
            TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

            AsrTran savedAddNew = addNew;

            reset();

            setAddNew(savedAddNew);

        } else if (
            TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

            setAddNew(new AsrTran());
            setCmd("");

        } else if (
            TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(cmd)) {

            int row = getRowToCopyInt();

            if (row >= 0 && row < asrList.size()) {
                addNew.setAsr(asrList.get(row));
            }

        } else if (
            TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {

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

    public void initModelAndDataset(
            String modelId,
            String datasetTableId) {

        addNew.getAsr().setModelId(modelId);
        addNew.getAsr().setDatasetTableId(datasetTableId);

        searchObject.setModelId(modelId);
        searchObject.setDatasetTableId(datasetTableId);
    }
}