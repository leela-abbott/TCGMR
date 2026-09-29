package abbott.ai.tcgm17.action.user;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.TCGMAction;
import abbott.ai.tcgm.entities.Role;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.UserMngr;
import abbott.ai.tcgm.data.DBConst;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Consolidated Struts 7.3.0 Controller handling all User Management operations</p>
 * <p>Company: Abbott Laboratories</p>
 * @version 7.3.0
 */
public class UserMaintAction extends TCGMAction {

    private static final long serialVersionUID = 100L;

    // --- Form Properties (Merged from UserForm) ---
    private String cmd = "";
    private User currUser = new User();
    private User searchObject = new User();
    private Sort sortObject = DBConst.DEF_SORT_USER;
    private User userToEdit = new User();
    private List<User> userlist = new ArrayList<>();

    public UserMaintAction() {
        super();
    }

    private void prepareModelContext() {
        this.setCurrUser(this.getSessionUser());
        if (TCGMConstants.URL_PARM_VAL_CANCEL.equals(this.getCmd())) {
            this.setUserToEdit(new User());
            this.setSearchObject(new User());
        }
    }

    public String execute() {
        if (!this.isSessionValid()) {
            return LOGIN;
        }
        prepareModelContext();
        UserMngr userMngr = new UserMngr();
        try {
            @SuppressWarnings("unchecked")
            List<User> list = (List<User>) userMngr.getUsers(this.getUserToken(), this.getSearchObject(), this.getSortObject());
            this.setUserlist(list != null ? list : new ArrayList<>());
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            if (this.request != null) {
                this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            }
            return "exception";
        }
    }

    public String edit() {
        if (!this.isSessionValid()) {
            return LOGIN;
        }
        prepareModelContext();
        UserMngr userMngr = new UserMngr();
        try {
            @SuppressWarnings("unchecked")
            List<User> list = (List<User>) userMngr.getUsers(this.getUserToken(), this.getSortObject());
            this.setUserlist(list != null ? list : new ArrayList<>());
            this.setUserToEdit(userMngr.getUserById(this.getUserToken(), this.getSearchObject()));
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            if (this.request != null) {
                this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            }
            return "exception";
        }
    }

    // --- Action 3: saveUser Mapping Entry ---
    public String save() {
        if (!this.isSessionValid()) {
            return LOGIN;
        }
        prepareModelContext();
        UserToken userToken = this.getUserToken();
        UserMngr userMngr = new UserMngr();
        try {
            if (this.getUserToEdit().getUserid() != null && this.getUserToEdit().getUserid().equalsIgnoreCase(userToken.getUserid())) {
                addActionError(getText("exception.admin.delete"));
            } else {
                userMngr.saveUser(userToken, this.getUserToEdit());
            }
            this.setUserToEdit(new User());
            this.setSearchObject(new User());
            @SuppressWarnings("unchecked")
            List<User> list = (List<User>) userMngr.getUsers(userToken, this.getSortObject());
            this.setUserlist(list != null ? list : new ArrayList<>());
            return SUCCESS;
        } catch (TCGMDuplicateItemException dupex) {
            addActionError(getText("error.user.create.duplicate"));
            try {
                @SuppressWarnings("unchecked")
                List<User> list = (List<User>) userMngr.getUsers(userToken, this.getSortObject());
                this.setUserlist(list != null ? list : new ArrayList<>());
            } catch (Exception ignored) {}
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            if (this.request != null) {
                this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            }
            return "exception";
        }
    }

    // --- Action 4: deleteUser Mapping Entry ---
    public String delete() {
        if (!this.isSessionValid()) {
            return LOGIN;
        }
        prepareModelContext();
        UserToken userToken = this.getUserToken();
        UserMngr userMngr = new UserMngr();
        try {
            // Pack the elements into a Vector instance to fulfill legacy helper constraints
            java.util.Vector<User> vectorUserList = new java.util.Vector<>(this.getUserlist());
            
            int adminCode = userMngr.deleteUsers(userToken, vectorUserList);
            if (adminCode == 1) {
                addActionError(getText("exception.admin.delete"));
            }
            this.setUserToEdit(new User());
            this.setSearchObject(new User());
            @SuppressWarnings("unchecked")
            List<User> list = (List<User>) userMngr.getUsers(userToken, this.getSortObject());
            this.setUserlist(list != null ? list : new ArrayList<>());
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            addActionError(getText("exception.user.delete"));
            return INPUT;
        }
    }

    // --- Helper UI Validation View Logic ---
    public boolean getUseridEdit() {
        return this.getUserToEdit().getUserinfoid() != null && !this.getUserToEdit().getUserinfoid().trim().equals("");
    }

    public boolean getDspSaveCanBtn() {
        if (this.getCurrUser().getRole().getAccessLevel() == Role.Administrator.getAccessLevel()) {
            return true;
        }
        return this.getUserToEdit().getUserinfoid() != null && !this.getUserToEdit().getUserinfoid().trim().equals("");
    }

    public int getUserListSize() {
        return this.getUserlist().size();
    }

    // --- Getters & Setters with Parameter Security ---
    public String getCmd() {
        return cmd;
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public User getSearchObject() {
        if (this.searchObject == null) this.searchObject = new User();
        return searchObject;
    }

    @StrutsParameter(depth = 1)
    public void setSearchObject(User searchObject) {
        this.searchObject = searchObject;
    }

    public Sort getSortObject() {
        if (this.sortObject == null) this.sortObject = DBConst.DEF_SORT_USER;
        return sortObject;
    }

    @StrutsParameter(depth = 1)
    public void setSortObject(Sort sortObject) {
        this.sortObject = sortObject;
    }

    public User getUserToEdit() {
        if (this.userToEdit == null) this.userToEdit = new User();
        return userToEdit;
    }

    @StrutsParameter(depth = 1)
    public void setUserToEdit(User userToEdit) {
        this.userToEdit = userToEdit;
    }

    public List<User> getUserlist() {
        if (this.userlist == null) this.userlist = new ArrayList<>();
        return userlist;
    }

    @StrutsParameter(depth = 2)
    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
}
