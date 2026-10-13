package abbott.ai.tcgm17.action.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Notes;
import abbott.ai.tcgm.entities.NotesTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.NotesMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class NotesMaint extends TCGMAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5702065278874082201L;

	private Map<String, Object> session;
	// Notes form
	private Notes searchObject = new Notes();
	private List<Notes> notesList = new ArrayList<>();
	private List<Notes> notesErrorList = new ArrayList<>();
	private Sort sortObject = new Sort(DBConst.COL_NOTES_DEF, DBConst.SORT_ASC);
	private PagingFilter pagingFilter = new PagingFilter();
	private NotesTran addNew = new NotesTran();
	private String errs = "";
//TCGMForm
	private String cmd = "";
	private String focusField = "";
	private String rowToCopy = "";
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

	// Execute
	@Override
	public String execute() {

		try {

			processCmd();

			if (TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(cmd)) {

				ServletActionContext.getRequest().getSession().setAttribute("notesTranAdvFilter", this);

				reset();

				return "advancedFilter";
			}

			NotesMngr notesMngr = new NotesMngr();

			initModelAndDataset(getState().getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID);

			if ("on".equalsIgnoreCase(errs)) {

				notesList = notesErrorList;

				pagingFilter.setTotalRecordsInSet(notesErrorList.size());

				errs = "";

				addActionError(getText("error.list"));
			} else {

				pagingFilter.setTotalRecordsInSet(notesMngr.getCount(getUserToken(), searchObject));

				if (TCGMConstants.URL_PARM_VAL_EMPTY.equalsIgnoreCase(cmd)) {

					notesList = new ArrayList<>(
							createEmptyNotesRecs(getState().getCurrentModelIdString(), DBConst.DEF_DATASET_TABLE_ID));
				} else {

					notesList = notesMngr.getNotes(getUserToken(), searchObject, pagingFilter, sortObject);
				}

				notesErrorList.clear();
			}

			return SUCCESS;

		} catch (TCGMException ex) {

			logger.error(ex.getMessage(), ex);

			ServletActionContext.getRequest().setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

			return ERROR;
		}
	}

	// Helper method
	public int getNotesListSize() {

		return notesList == null ? 0 : notesList.size();
	}

	public long getNotesErrorListSize() {

		return notesErrorList == null ? 0 : notesErrorList.size();
	}

	public Notes getNotesListItem(int index) {

		if (notesList == null || index < 0 || index >= notesList.size()) {

			return null;
		}

		return notesList.get(index);
	}

	public void setNotesListItem(Notes notes, int index) {

		while (notesList.size() <= index) {

			notesList.add(new Notes());
		}

		notesList.set(index, notes);
	}

	// Initialization
	public void initModelAndDataset(String modelId, String datasetTableId) {

		addNew.getNotes().setModelId(modelId);

		addNew.getNotes().setDatasetTableId(datasetTableId);

		searchObject.setModelId(modelId);

		searchObject.setDatasetTableId(datasetTableId);
	}

	// reset
	public void reset() {

		cmd = "";

		if (addNew != null && addNew.getNotes() != null) {

			addNew.getNotes().clearMsg();
		}

		searchObject = new Notes();

		addNew = new NotesTran();

		pagingFilter = new PagingFilter();

		if (notesList != null) {

			for (Notes notes : notesList) {

				if (notes != null) {

					notes.setSelected(false);
				}
			}
		}
	}

	// process cmd
	public void processCmd() {

		if (TCGMConstants.URL_PARM_VAL_NEXT_PAGE.equals(cmd)) {

			pagingFilter.setNextpage();
		} else if (TCGMConstants.URL_PARM_VAL_PREV_PAGE.equals(cmd)) {

			pagingFilter.setPrevPage();
		} else if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

			pagingFilter.setStartRecord(1);
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_FILTER.equals(cmd)) {

			NotesTran notesTran = addNew;

			reset();

			addNew = notesTran;
		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

			addNew = new NotesTran();

			cmd = "";
		} else if (TCGMConstants.URL_PARM_VAL_COPY_ROW.equals(cmd)) {

			int row = Integer.parseInt(rowToCopy);

			addNew.setNotes(notesList.get(row));
		} else if (TCGMConstants.URL_PARM_VAL_SELECTED_RECORD_PAGE.equals(cmd)) {

			pagingFilter.setDispSelectedRecordPage();
		}

		focusField = DBConst.NOTES_DFT_FOCUS;

		if (TCGMConstants.URL_PARM_VAL_FILTER.equals(cmd) || TCGMConstants.URL_PARM_VAL_ADV_FILTER.equals(cmd)) {

			focusField = "addNew.actionCode";
		} else if (TCGMConstants.URL_PARM_VAL_SAVE.equals(cmd) || TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {

			focusField = "addNew.actionCode";
		}
	}
	// Setter and Getter

	public Notes getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(Notes searchObject) {
		this.searchObject = searchObject;
	}

	public List<Notes> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<Notes> notesList) {
		this.notesList = notesList;
	}

	public List<Notes> getNotesErrorList() {
		return notesErrorList;
	}

	public void setNotesErrorList(List<Notes> notesErrorList) {
		this.notesErrorList = notesErrorList;
	}

	public Sort getSortObject() {
		return sortObject;
	}

	public void setSortObject(Sort sortObject) {
		this.sortObject = sortObject;
	}

	public PagingFilter getPagingFilter() {
		return pagingFilter;
	}

	public void setPagingFilter(PagingFilter pagingFilter) {
		this.pagingFilter = pagingFilter;
	}

	public NotesTran getAddNew() {
		return addNew;
	}

	public void setAddNew(NotesTran addNew) {
		this.addNew = addNew;
	}

	public String getErrs() {
		return errs;
	}

	public void setErrs(String errs) {
		this.errs = errs;
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

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
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
