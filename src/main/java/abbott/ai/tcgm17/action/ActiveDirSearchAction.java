package abbott.ai.tcgm17.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.ActiveDirSearchDtlBean;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ActiveDirSearchMngr;
import abbott.ai.tcgm.helpers.RptUserMngr;
import abbott.ai.tcgm.helpers.UserMngr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ActiveDirSearchAction extends TCGMAction implements ServletRequestAware {

    private static final Logger logger = LogManager.getLogger(ActiveDirSearchAction.class);
    private HttpServletRequest request;

    private String cmd = "";
    private String firstName = "";
    private String lastName = "";
    private String blnSelected;
    private ArrayList<ActiveDirSearchDtlBean> activeDirUserList = new ArrayList<ActiveDirSearchDtlBean>();
    private String usId = "";
    private Vector userList = new Vector();
    private User searchObject = new User();
    private Sort sortObject = DBConst.DEF_SORT_USER;
    private String cmd2 = "";

    private RptUser rptUserSearchObject = new RptUser();
    private ArrayList rptUserList = new ArrayList();
    private RptUser rptUser = new RptUser();
    private String selDesc = "";
    private String category = "";
    private String categoryid = "";
    private String categoryname = "";
    private String affCodeList = "";
    private String secCodeList = "";
    private String areaCodeList = "";
    private String divisionCode = "";
    private boolean blnDivision = false;
    private ArrayList affMaintList = new ArrayList();

    public ActiveDirSearchAction() {
        super();
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String execute() throws Exception {
        if (!this.isSessionValid(request)) {
            return LOGIN;
        }

        if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_VIEW)) {
            return performView("rpt");
        } else if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_APPVIEW)) {
            return performView("app");
        } else if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_GET)) {
            return performGet("rpt");
        } else if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_GET_APP)) {
            return performGet("app");
        } else if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_SELECT_APP)) {
            return performAppUserSelect(blnSelected);
        } else if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_SELECT)) {
            return performUserSelect(blnSelected);
        }

        return SUCCESS;
    }

    private String performView(String action) {
        try {
            UserMngr userMngr = new UserMngr();
            
            if (this.searchObject == null) {
                this.searchObject = new User();
            }
            this.searchObject.setUserid(this.getUsId());
            this.searchObject.setLastName(this.getLastName());
            this.searchObject.setFirstName(this.getFirstName());

            HttpSession session = request.getSession(false);
            UserToken token = null;
            if (session != null && session.getAttribute(TCGMConstants.SESSION_NAME_USER) != null) {
                User loggedInUser = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
                token = new UserToken(loggedInUser.getUserid(), loggedInUser.getPassword());
            } else {
                token = new UserToken("", "");
            }

            User emptyCriteria = new User();
            this.setUserlist(userMngr.getUsers(token, emptyCriteria, this.getSortObject()));
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return ERROR;
        }
        this.setActiveDirUserList(new ArrayList());
        this.reset();

        if (action.equalsIgnoreCase("rpt")) {
            return SUCCESS;
        } else if (action.equalsIgnoreCase("app")) {
            return "appsuccess";
        }
        return SUCCESS;
    }

    private String performGet(String action) {
        ActiveDirSearchMngr activeDirSearchMgr = new ActiveDirSearchMngr();
        try {
            String namesToSortBy[] = { "sn", "givenName", "cn", "mail", "userPrincipalName" };
            boolean sortAscending[] = { true, true, true, true, false };
            ArrayList searchResults = activeDirSearchMgr.getUserList(this.getFirstName(), this.getLastName(), this.getUsId(), namesToSortBy, sortAscending);
            this.setActiveDirUserList(searchResults);
            HttpSession session = request.getSession(true);
            session.setAttribute("ACTIVE_DIR_USER_LIST_SESSION", searchResults);

            UserMngr userMngr = new UserMngr();
            if (this.searchObject == null) {
                this.searchObject = new User();
            }
            this.searchObject.setUserid(this.getUsId());
            this.searchObject.setLastName(this.getLastName());
            this.searchObject.setFirstName(this.getFirstName());

            UserToken token = null;
            if (session != null && session.getAttribute(TCGMConstants.SESSION_NAME_USER) != null) {
                User loggedInUser = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
                token = new UserToken(loggedInUser.getUserid(), loggedInUser.getPassword());
            } else {
                token = new UserToken("", "");
            }

            User emptyCriteria = new User();
            this.setUserlist(userMngr.getUsers(token, emptyCriteria, this.getSortObject()));

            if (action.equalsIgnoreCase("rpt")) {
                return SUCCESS;
            } else if (action.equalsIgnoreCase("app")) {
                return "appsuccess";
            }
        } catch (TCGMException gpse) {
            logger.error(gpse.toString(), gpse);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, gpse);
            return ERROR;
        }
        return SUCCESS;
    }

    private String performUserSelect(String userId) {
        ActiveDirSearchMngr activeDirSearchMgr = new ActiveDirSearchMngr();
        RptUserMngr userMaintMgr = new RptUserMngr();

        RptUser userBean = new RptUser();
        userBean.setAffCode("-1");
        userBean.setSecCode("-1");
        userBean.setAreaCode("-1");
        userBean.setAffiliates(new HashMap());
        userBean.setSectors(new HashMap());
        userBean.setAreas(new HashMap());
        try {
            userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(), TCGMConstants.DIVISION));
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
        }
        ActiveDirSearchDtlBean activeDirSearchDtlBean = activeDirSearchMgr.getSelectedUser(this.getActiveDirUserList(), userId);
        userBean.setUserid(userId);
        userBean.setFirstName(activeDirSearchDtlBean.getFirstName());
        userBean.setLastName(activeDirSearchDtlBean.getLastName());
        userBean.setEmail(activeDirSearchDtlBean.getEmail());
        userBean.setAbtNotesId(activeDirSearchDtlBean.getAbtNotesId());
        userBean.setEmployeeType(activeDirSearchDtlBean.getEmployeeType());
        userBean.setEmpDivision(activeDirSearchDtlBean.getDivision());
        userBean.setActionCode("-1");
        userBean.setSecCode("-1");

        this.setRptUser(userBean);
        request.getSession().setAttribute("RptUser", userBean);
        request.getSession().setAttribute("userForm", this);
        return "rptUser";
    }

    private String performAppUserSelect(String userId) {
        ActiveDirSearchMngr activeDirSearchMgr = new ActiveDirSearchMngr();
        RptUser userBean = new RptUser();
        userBean.setAffCode("-1");
        userBean.setSecCode("-1");
        userBean.setAreaCode("-1");
        userBean.setAffiliates(new HashMap());
        userBean.setSectors(new HashMap());
        userBean.setAreas(new HashMap());

        // FIX: Extract the cached populated user list context out of the session state
        HttpSession session = request.getSession(false);
        ArrayList cachedList = null;
        if (session != null && session.getAttribute("ACTIVE_DIR_USER_LIST_SESSION") != null) {
            cachedList = (ArrayList) session.getAttribute("ACTIVE_DIR_USER_LIST_SESSION");
        } else {
            cachedList = this.getActiveDirUserList();
        }

        ActiveDirSearchDtlBean activeDirSearchDtlBean = activeDirSearchMgr.getSelectedUser(cachedList, userId);
        userBean.setUserid(userId);
        userBean.setFirstName(activeDirSearchDtlBean.getFirstName());
        userBean.setLastName(activeDirSearchDtlBean.getLastName());
        userBean.setEmail(activeDirSearchDtlBean.getEmail());
        userBean.setAbtNotesId(activeDirSearchDtlBean.getAbtNotesId());
        userBean.setEmployeeType(activeDirSearchDtlBean.getEmployeeType());
        userBean.setDivision(activeDirSearchDtlBean.getDivision());
        userBean.setActionCode("-1");
        userBean.setSecCode("-1");

        this.setRptUser(userBean);
        if (session != null) {
            session.setAttribute("RptUser", userBean);
            session.setAttribute("userForm", this);
        }
        return "appUser";
    }

    public void validate() {
        if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_GET)) {
            if ((this.getFirstName() == null || this.getFirstName().trim().equals(""))
                    && (this.getLastName() == null || this.getLastName().trim().equals("")) 
                    && (this.getUsId() == null || this.getUsId().trim().equals(""))) {
                addFieldError("firstName", "First Name or Last Name is required");
            }
        }
        if (cmd.equalsIgnoreCase(TCGMConstants.BTN_VAL_SAVE)) {
            if (this.getRptUser().getUserid() == null || this.getRptUser().getUserid().equals("")) {
                addFieldError("rptUser.userid", "Invalid userId");
            }
        }
    }

    private void reset() {
        this.firstName = "";
        this.lastName = "";
        for (int i = 0; i < this.getActiveDirUserList().size(); i++) {
            this.getActiveDirUserList(i).setBlnSelected(false);
        }
    }

//    protected boolean isSessionValid(HttpServletRequest request) {
//        return true;
//    }

    protected UserToken getUserToken(HttpServletRequest request) {
        return getUserToken();
    }

    public String getCmd() { return cmd; }
    @StrutsParameter
    public void setCmd(String cmd) { this.cmd = cmd; }

    public String getFirstName() { return firstName; }
    @StrutsParameter
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    @StrutsParameter
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBlnSelected() { return blnSelected; }
    
    @StrutsParameter
    public void setBlnSelected(String blnSelected) { this.blnSelected = blnSelected; }

    @StrutsParameter(depth = 2)
    public ArrayList<ActiveDirSearchDtlBean> getActiveDirUserList() { 
        return activeDirUserList; 
    }

    @StrutsParameter(depth = 2)
    public void setActiveDirUserList(ArrayList<ActiveDirSearchDtlBean> activeDirUserList) { 
        this.activeDirUserList = activeDirUserList; 
    }

    public ActiveDirSearchDtlBean getActiveDirUserList(int index) {
        if (index >= 0 && index < this.activeDirUserList.size()) {
            return (ActiveDirSearchDtlBean) this.activeDirUserList.get(index);
        }
        return null;
    }

    public String getUsId() { return usId; }
    @StrutsParameter
    public void setUsId(String usId) { this.usId = usId; }

    public Vector getUserlist() { return userList; }
    @StrutsParameter
    public void setUserlist(Vector userList) { this.userList = userList; }

    public User getSearchObject() { return searchObject; }
    @StrutsParameter
    public void setSearchObject(User searchObject) { this.searchObject = searchObject; }

    public Sort getSortObject() { return sortObject; }
    public void setSortObject(Sort sortObject) { this.sortObject = sortObject; }

    public String getCmd2() { return cmd2; }
    @StrutsParameter
    public void setCmd2(String cmd2) { this.cmd2 = cmd2; }

    public RptUser getRptUserSearchObject() { return rptUserSearchObject; }
    @StrutsParameter
    public void setRptUserSearchObject(RptUser rptUserSearchObject) { this.rptUserSearchObject = rptUserSearchObject; }

    public ArrayList getRptUserList() { return rptUserList; }
    public void setRptUserList(ArrayList rptUserList) { this.rptUserList = rptUserList; }

    public RptUser getRptUser() { return rptUser; }
    @StrutsParameter
    public void setRptUser(RptUser rptUser) { this.rptUser = rptUser; }

    public String getSelDesc() { return selDesc; }
    @StrutsParameter
    public void setSelDesc(String selDesc) { this.selDesc = selDesc; }

    public String getCategory() { return category; }
    @StrutsParameter
    public void setCategory(String category) { this.category = category; }

    public String getCategoryid() { return categoryid; }
    @StrutsParameter
    public void setCategoryid(String categoryid) { this.categoryid = categoryid; }

    public String getCategoryname() { return categoryname; }
    @StrutsParameter
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }

    public String getAffCodeList() { return affCodeList; }
    @StrutsParameter
    public void setAffCodeList(String affCodeList) { this.affCodeList = affCodeList; }

    public String getSecCodeList() { return secCodeList; }
    @StrutsParameter
    public void setSecCodeList(String secCodeList) { this.secCodeList = secCodeList; }

    public String getAreaCodeList() { return areaCodeList; }
    @StrutsParameter
    public void setAreaCodeList(String areaCodeList) { this.areaCodeList = areaCodeList; }

    public String getDivisionCode() { return divisionCode; }
    @StrutsParameter
    public void setDivisionCode(String divisionCode) { this.divisionCode = divisionCode; }

    public boolean isBlnDivision() { return blnDivision; }
    @StrutsParameter
    public void setBlnDivision(boolean blnDivision) { this.blnDivision = blnDivision; }

    public ArrayList getAffMaintList() { return affMaintList; }
    public void setAffMaintList(ArrayList affMaintList) { this.affMaintList = affMaintList; }
}
