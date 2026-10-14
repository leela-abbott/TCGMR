package abbott.ai.tcgm17.action.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.TCGMDataValidation;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.NotesTran;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.NotesMngr;
import abbott.ai.tcgm17.action.TCGMAction;

public class NotesTranMaint extends TCGMAction implements SessionAware {

	private static final long serialVersionUID = 1L;

	/*
	 * ============================================================ Properties
	 * migrated from TCGMForm
	 * ============================================================
	 */
	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";
	private String modelSelected = "";
	private String rowToCopy = "";
	private String showModels = "";
	private Vector models = new Vector();
	private Vector closedModels = new Vector();
	private TCGMModel.Type modelType;

	/*
	 * ============================================================ Properties
	 * migrated from NotesTranForm
	 * ============================================================
	 */
	private NotesTran searchObject = new NotesTran();
	private List<NotesTran> notesTranList = new ArrayList<>();
	private PagingFilter pagingFilter = new PagingFilter();
	private Sort sortObject = new Sort(DBConst.COL_NOTES_DEF, DBConst.SORT_ASC);
	private NotesTran addNew = new NotesTran();
	private TCGMDataValidation dataVal = new TCGMDataValidation("NOTES");
	private String userSelected;
	private Map<String, Object> session;
	
	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	/*
	 * ============================================================ SessionAware
	 * ============================================================
	 */

	/*
	 * ============================================================ Execute (perform
	 * migration) ============================================================
	 */
	@Override
	public String execute() {

		try {

			
			 if (session.get("deleteMessage") != null) {
	                addActionMessage(getText("success.bpcsTran.delete"));
	                session.remove("deleteMessage");
	            }


			//UserToken userToken = (UserToken) session.get(TCGMConstants.SESSION_NAME_USER_TOKEN);
			UserToken userToken = this.getUserToken(request);

			processCmd();

			if (TCGMConstants.URL_PARM_VAL_ADV_FILTER.equalsIgnoreCase(cmd)) {

				session.put("notesTranAdvFilter", this);

				reset();

				return "advancedFilter";
			}

			NotesMngr notesMngr = new NotesMngr();
			ModelMngr modelMngr = new ModelMngr();

			initModelAndDataset(getCurrentModelId(), DBConst.DEF_DATASET_TABLE_ID);

			if (userSelected == null) {

				if ("".equals(searchObject.getNotes().getCreateLog().getUserName())) {

					searchObject.getNotes().getCreateLog().setUserName(userToken.getUserid());

					userSelected = userToken.getUserid();
				}
			} else if (!"ALL".equalsIgnoreCase(userSelected)) {

				searchObject.getNotes().getCreateLog().setUserName(userSelected);
			}

			pagingFilter.setTotalRecordsInSet(notesMngr.getCount(userToken, searchObject));

			notesTranList = notesMngr.getNotesTran(userToken, searchObject, pagingFilter, sortObject);

			FactorModel fm = new FactorModel();
			fm.setStatus(TCGMModel.Status.OPEN);

			models = modelMngr.getModels(userToken, fm);

			searchObject.getNotes().getCreateLog().setUserName(userToken.getUserid());

			modelSelected = TCGMConstants.NONE;

			return SUCCESS;

		} catch (TCGMException ex) {

			addActionError(ex.getMessage());

			return ERROR;
		}
	}

	/*
	 * ============================================================ processCmd()
	 * Migrated from NotesTranForm
	 * ============================================================
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

			searchObject = new NotesTran();

		} else if (TCGMConstants.URL_PARM_VAL_CLEAR_ADD_NEW.equals(cmd)) {

			addNew = new NotesTran();
		}
	}

	/*
	 * ============================================================ reset() Migrated
	 * from NotesTranForm
	 * ============================================================
	 */
	public void reset() {

		cmd = "";

		addNew.getNotes().clearMsg();

		searchObject = new NotesTran();

		addNew = new NotesTran();

		pagingFilter = new PagingFilter();

		for (NotesTran note : notesTranList) {

			note.getNotes().setSelected(false);

			note.getNotes().setMsg("");
		}
	}

	/*
	 * ============================================================
	 * initModelAndDataset()
	 * ============================================================
	 */
	public void initModelAndDataset(String modelId, String datasetTableId) {

		addNew.getNotes().setModelId(modelId);
		addNew.getNotes().setDatasetTableId(datasetTableId);

		searchObject.getNotes().setModelId(modelId);
		searchObject.getNotes().setDatasetTableId(datasetTableId);
	}

	/*
	 * ============================================================ validate()
	 * Migrated from NotesTranForm
	 * ============================================================
	 */
	
	//TODO Need to check validation
//	@Override
//	public void validate() {
//
//		addNew.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
//
//		if (TCGMConstants.URL_PARM_VAL_SAVE_SELECTED.equals(cmd)) {
//
//			for (NotesTran notesTran : notesTranList) {
//
//				if (notesTran.getNotes().isSelected()) {
//					
//					
//					if(! notesTran.getNotes().getMsg().equals(""))
//					{
//						addActionError(getText("error.notes.validation"));
//					}
//
//				}
//			}
//		} else if (TCGMConstants.URL_PARM_VAL_ADD.equals(cmd)) {
//
//			ActionMessages errors = new ActionMessages();
//
//			dataVal.validateNotesTran(addNew, errors, false);
//
//			if (!errors.isEmpty()) {
//				addActionError(getText("error.notes.validation"));
//			}
//		} else if (TCGMConstants.URL_PARM_VAL_MASS_UPDATE.equals(cmd)) {
//
//			ActionMessages errors = new ActionMessages();
//
//			dataVal.validateNotesTran(addNew, errors, true);
//
//			if (!errors.isEmpty()) {
//				addActionError(getText("error.notes.validation"));
//			}
//		}
//	}
	
	public java.util.Vector getUserlist() {
		abbott.ai.tcgm.entities.User u =
			(abbott.ai.tcgm.entities.User) request.getSession().getAttribute("TCGMUser");
		return (u != null) ? u.getUserlist() : new java.util.Vector();
	}

	private String getCurrentModelId() {

		Object state = session.get(TCGMConstants.SESSION_NAME_STATE);

		if (state instanceof TCGMState) {
			return ((TCGMState) state).getCurrentModelIdString();
		}

		return "";
	}

	/*
	 * ============================================================ Generate
	 * getters/setters for all properties
	 * ============================================================
	 */

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public NotesTran getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(NotesTran searchObject) {
		this.searchObject = searchObject;
	}

	public List<NotesTran> getNotesTranList() {
		return notesTranList;
	}

	public void setNotesTranList(List<NotesTran> notesTranList) {
		this.notesTranList = notesTranList;
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

	public NotesTran getAddNew() {
		return addNew;
	}

	public void setAddNew(NotesTran addNew) {
		this.addNew = addNew;
	}

	public String getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}

	public Vector getModels() {
		return models;
	}

	public void setModels(Vector models) {
		this.models = models;
	}

	public String getModelSelected() {
		return modelSelected;
	}

	public void setModelSelected(String modelSelected) {
		this.modelSelected = modelSelected;
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

	public String getRowToCopy() {
		return rowToCopy;
	}

	public void setRowToCopy(String rowToCopy) {
		this.rowToCopy = rowToCopy;
	}

	public String getShowModels() {
		return showModels;
	}

	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	public Vector getClosedModels() {
		return closedModels;
	}

	public void setClosedModels(Vector closedModels) {
		this.closedModels = closedModels;
	}

	public TCGMModel.Type getModelType() {
		return modelType;
	}

	public void setModelType(TCGMModel.Type modelType) {
		this.modelType = modelType;
	}

	public TCGMDataValidation getDataVal() {
		return dataVal;
	}

	public void setDataVal(TCGMDataValidation dataVal) {
		this.dataVal = dataVal;
	}
	
	
	
}