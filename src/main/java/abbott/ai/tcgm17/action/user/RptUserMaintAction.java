package abbott.ai.tcgm17.action.user;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.ServletActionContext;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.action.TCGMAction;
import abbott.ai.tcgm.entities.ActiveAffMaint;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.RptUserMngr;
import abbott.ai.tcgm.helpers.UserMngr;

public class RptUserMaintAction extends TCGMAction {

    private static final long serialVersionUID = 101L;

    private RptUser searchObject = new RptUser();
    private List<RptUser> userList = new ArrayList<>();
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
    private List<ActiveAffMaint> affMaintList = new ArrayList<>();
    private String cmd = "";
    private String cmd2 = "";
    private String hidVal;
    private long userListSize;

    public RptUserMaintAction() {
        super();
    }

    @Override
    public void validate() {
        if (TCGMConstants.BTN_VAL_SAVE.equalsIgnoreCase(this.getCmd())) {
            if (this.getRptUser().getUserid() == null || "".equals(this.getRptUser().getUserid().trim())) {
                addFieldError("rptUser.userid", getText("error.userMaint.required.userid"));
            }
        }
    }

    @Override
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (!this.isSessionValid()) {
            return LOGIN;
        }

        HttpSession session = request.getSession();
        RptUserMngr userMaintMgr = new RptUserMngr();
        
        if (this.getCmd() == null || "".equals(this.getCmd().trim()) || this.getCmd().equalsIgnoreCase("filter")) {
            try {
                this.setUserList(new ArrayList<>());
                RptUser userBean = new RptUser();
                userBean.setAffCode("-1");
                userBean.setSecCode("-1");
                userBean.setAreaCode("-1");
                userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(), TCGMConstants.DIVISION));
                userBean.setAffiliates(new HashMap<>());
                userBean.setSectors(new HashMap<>());
                userBean.setAreas(new HashMap<>());
                this.setRptUser(userBean);

                session.setAttribute("RptUser", userBean);
                session.setAttribute("userForm", this);
                System.out.println("ACTION DIV = " + this.getRptUser().getDiv());
                return "filter";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 1. BTN_VAL_SAVE Action Routing
        if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_SAVE)) {
            try {
                this.getRptUser().setDesc(this.getSelDesc());
                this.getRptUser().setRecipient("CAMID(\"LDAP abbott.corp:u:cn=" + this.getRptUser().getUserid().toLowerCase() + ",ou=users\")");

                if (this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.AREA)) {
                    Map<?, ?> areaList = userMaintMgr.getList(this.getAreaCodeList());
                    for (Object key : areaList.keySet()) {
                        String k = (String) key;
                        String v = (String) areaList.get(k);
                        this.getRptUser().setCode(k);
                        this.getRptUser().setAreaCode(k);
                        this.getRptUser().setDesc(v);
                        userMaintMgr.saveUser(this.getRptUser(), new HashMap<>());
                    }
                } else if (this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.SECTOR)) {
                    Map<?, ?> secList = userMaintMgr.getList(this.getSecCodeList());
                    for (Object key : secList.keySet()) {
                        String k = (String) key;
                        String v = (String) secList.get(k);
                        this.getRptUser().setCode(k);
                        this.getRptUser().setSecCode(k);
                        this.getRptUser().setDesc(v);
                        userMaintMgr.saveUser(this.getRptUser(), new HashMap<>());
                    }
                } else if (this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.HQ_CON) ||
                           this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP) ||
                           this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)) {
                    this.getRptUser().setDivision("All");
                    userMaintMgr.saveUser(this.getRptUser(), new HashMap<>());
                } else if (this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.DIVISION)) {
                    userMaintMgr.saveUser(this.getRptUser(), new HashMap<>());
                } else if (this.getRptUser().getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)) {
                    Map<?, ?> affsList = userMaintMgr.getList(this.getAffCodeList());
                    for (Object key : affsList.keySet()) {
                        String k = (String) key;
                        String v = (String) affsList.get(k);
                        this.getRptUser().setCode(k);
                        this.getRptUser().setAffCode(k);
                        this.getRptUser().setDesc(v);
                        HashMap<String, String> affs = new HashMap<>();
                        affs.put(k, v);
                        userMaintMgr.saveUser(this.getRptUser(), affs);
                    }
                }

                if ("Duplicate Row".equalsIgnoreCase(this.getRptUser().getMsg())) {
                    addActionMessage(getText("success.user.create"));
                } else {
                    addActionMessage(getText("success.user.create"));
                }
                this.getRptUser().setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(), TCGMConstants.DIVISION));
                return SUCCESS;
            } catch (TCGMException e) {
                logger.error(e.toString(), e);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
                addActionError(getText("failure.user.create"));
                return "exception";
            }
        }

        // 2. saveAppUser Action Routing
        else if (this.getCmd().equalsIgnoreCase("saveAppUser")) {
            UserToken userToken = this.getUserToken();
            UserMngr userMngr = new UserMngr();
            try {
                if (this.getRptUser().getUserid() != null && this.getRptUser().getUserid().equalsIgnoreCase(userToken.getUserid())) {
                    addActionError(getText("exception.admin.delete"));
                } else {
                    User userToSave = new User();
                    userToSave.setUserid(this.getRptUser().getUserid());
                    userToSave.setFirstName(this.getRptUser().getFirstName());
                    userToSave.setLastName(this.getRptUser().getLastName());
                    userToSave.setEmail(this.getRptUser().getEmail());
                    userToSave.setAbtNotesId(this.getRptUser().getAbtNotesId());
                    userToSave.setDivision(this.getRptUser().getDivision());
                    userToSave.setEmployeeType(this.getRptUser().getEmployeeType());
                    userToSave.setUserRole(this.getRptUser().getRole());

                    userMngr.saveUser(userToken, userToSave);
                    addActionMessage(getText("success.user.create"));
                }
                return "appSuccess";
            } catch (TCGMDuplicateItemException dupex) {
                addActionError(getText("error.user.create.duplicate"));
                return "appSuccess";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 3. BTN_VAL_REMOVE Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_REMOVE)) {
            try {
               
                ArrayList<RptUser> legacyCastList = new ArrayList<>(this.getUserList());
                userMaintMgr.deleteRptUser(legacyCastList);
                this.setSearchObject(new RptUser());
                
                @SuppressWarnings("unchecked")
                List<RptUser> records = userMaintMgr.getUserList(this.getRptUser(), true);
                this.setUserList(records != null ? records : new ArrayList<>());
                addActionMessage(getText("success.user.remove"));
                return "filter";
            } catch (TCGMException e) {
                logger.error(e.toString(), e);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
                addActionError(getText("failure.user.remove"));
                return "exception";
            }
        }

        // 4. BTN_VAL_REGENERATE Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_REGENERATE)) {
            try {
                @SuppressWarnings("unchecked")
                ArrayList<RptUser> legacyCastList = new ArrayList<>(this.getUserList());
                userMaintMgr.regenerateSelectedUsers(legacyCastList);
                
                @SuppressWarnings("unchecked")
                List<RptUser> records = userMaintMgr.getUserList(this.getRptUser(), true);
                this.setUserList(records != null ? records : new ArrayList<>());
                addActionMessage(getText("success.user.regenerate"));
                return "filter";
            } catch (TCGMException e) {
                logger.error(e.toString(), e);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
                addActionError(getText("failure.user.regenerate"));
                return "exception";
            }
        }

        // 5. BTN_VAL_GET Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_GET)) {
            try {
                this.getRptUser().setDesc(this.getSelDesc());
                @SuppressWarnings("unchecked")
                ArrayList<RptUser> aryList = userMaintMgr.getUserList(this.getRptUser(), true);
                this.setUserList(aryList != null ? aryList : new ArrayList<>());
                session.setAttribute("userForm", this);
                return "filter";
            } catch (TCGMException ex) {
                logger.error(ex.toString(), ex);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
                return "exception";
            }
        }

        // 6. MAINT_VAL_CREATE Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_CREATE)) {
            this.setUserList(new ArrayList<>());
            RptUser userBean = new RptUser();
            userBean.setAffCode("-1");
            userBean.setSecCode("-1");
            userBean.setAreaCode("-1");
            userBean.setAffiliates(new HashMap<>());
            userBean.setSectors(new HashMap<>());
            userBean.setAreas(new HashMap<>());
            this.setRptUser(userBean);
            session.setAttribute("RptUser", userBean);
            session.setAttribute("userForm", this);
            return SUCCESS;
        }

     // 7. MAINT_VAL_FILTER Action Routing (And your default entry routing)
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_FILTER)) {
            try {
                this.setUserList(new ArrayList<>());
                
                // FIX: Directly initialize and populate the ACTION'S class field variable
                this.rptUser = new RptUser(); 
                this.rptUser.setAffCode("-1");
                this.rptUser.setSecCode("-1");
                this.rptUser.setAreaCode("-1");
                this.rptUser.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getBurstDivision(), TCGMConstants.DIVISION));
                this.rptUser.setAffiliates(new HashMap<>());
                this.rptUser.setSectors(new HashMap<>());
                this.rptUser.setAreas(new HashMap<>());
                
                // Sync with legacy session attributes if other legacy code fragments rely on them
                session.setAttribute("RptUser", this.rptUser);
                session.setAttribute("userForm", this);
                return "filter";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 8. BTN_VAL_BURST Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.BTN_VAL_BURST)) {
            try {
                RptUser userBean = new RptUser();
                userBean.setAffCode("-1");
                userBean.setAffiliates(new HashMap<>());
                userBean.setSecCode("-1");
                userBean.setSectors(new HashMap<>());
                userBean.setAreaCode("-1");
                userBean.setAreas(new HashMap<>());
                userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getDivision(), TCGMConstants.DIVISION));
                this.setRptUser(userBean);
                session.setAttribute("RptUser", userBean);
                session.setAttribute("userForm", this);
                return "burst";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 9. MAINT_VAL_BURST Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_BURST)) {
            try {
                userMaintMgr.addToBurstTables(this.getRptUser(), this.getCategoryid(), this.getCategoryname(), this.getCategory(), this.getDivisionCode());
                RptUser userBean = new RptUser();
                userBean.setAffCode("-1");
                userBean.setAffiliates(new HashMap<>());
                userBean.setSecCode("-1");
                userBean.setSectors(new HashMap<>());
                userBean.setAreaCode("-1");
                userBean.setAreas(new HashMap<>());
                userBean.setDiv(TCGMUtil.getSortedMap(userMaintMgr.getDivision(), TCGMConstants.DIVISION));
                this.setRptUser(userBean);
                session.setAttribute("RptUser", userBean);
                session.setAttribute("userForm", this);
                addActionMessage(getText("success.record.create"));
                return "burst";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 10. loadBurst Action Routing
        else if (this.getCmd().equalsIgnoreCase("loadBurst")) {
            try {
                userMaintMgr.loadBurstTables();
                RptUser userBean = new RptUser();
                userBean.setAffCode("-1");
                userBean.setAffiliates(new HashMap<>());
                userBean.setSecCode("-1");
                userBean.setSectors(new HashMap<>());
                userBean.setAreaCode("-1");
                userBean.setAreas(new HashMap<>());
                this.setRptUser(userBean);
                session.setAttribute("RptUser", userBean);
                session.setAttribute("userForm", this);
                addActionMessage(getText("success.record.burst"));
                return "burst";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 11. activeaff Action Routing
        else if (this.getCmd().equalsIgnoreCase("activeaff")) {
            RptUser userBean = new RptUser();
            userBean.setAffCode("-1");
            userBean.setAffiliates(new HashMap<>());
            this.setRptUser(userBean);
            this.setAffMaintList(new ArrayList<>());
            session.setAttribute("RptUser", userBean);
            session.setAttribute("userForm", this);
            return "activeaff";
        }

        // 12. getactiveaff Action Routing
        else if (this.getCmd().equalsIgnoreCase("getactiveaff")) {
            try {
                @SuppressWarnings("unchecked")
                ArrayList<ActiveAffMaint> aryList = userMaintMgr.getAffiliateStatus(this.getRptUser().getDivision(), this.getCategory());
                this.setAffMaintList(aryList != null ? aryList : new ArrayList<>());
                session.setAttribute("userForm", this);
                request.setAttribute("cat", this.getCategory());
                request.setAttribute("div", this.getRptUser().getDivision());
                return "activeaff";
            } catch (TCGMException ex) {
                logger.error(ex.toString(), ex);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
                return "exception";
            }
        }

        // 13. saveactiveaff Action Routing
        else if (this.getCmd().equalsIgnoreCase("saveactiveaff")) {
            try {
                @SuppressWarnings("unchecked")
                ArrayList<ActiveAffMaint> legacyCastList = new ArrayList<>(this.getAffMaintList());
                userMaintMgr.saveAffiliateStatus(this.getRptUser().getDivision(), legacyCastList, this.getCategoryname());
                
                @SuppressWarnings("unchecked")
                ArrayList<ActiveAffMaint> aryList = userMaintMgr.getAffiliateStatus(this.getRptUser().getDivision(), this.getCategory());
                this.setAffMaintList(aryList != null ? aryList : new ArrayList<>());
                request.setAttribute("cat", this.getCategory());
                request.setAttribute("div", this.getRptUser().getDivision());
                session.setAttribute("userForm", this);
                return "activeaff";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 14. MAINT_VAL_REMOVE Action Routing
        else if (this.getCmd().equalsIgnoreCase(TCGMConstants.MAINT_VAL_REMOVE)) {
            this.setUserList(new ArrayList<>());
            this.setRptUser(new RptUser());
            return "filter";
        }
        // 15. usersView Action Routing
        else if (this.getCmd().equalsIgnoreCase("usersView")) {
            return "usersview";
        }

        // 16. users Action Routing
        else if (this.getCmd().equalsIgnoreCase("users")) {
            try {
                User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
                userMaintMgr.generateUsers(user);
                return "users";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 17. datausersView Action Routing
        else if (this.getCmd().equalsIgnoreCase("datausersView")) {
            return "datausersView";
        }

        // 18. datausers Action Routing
        else if (this.getCmd().equalsIgnoreCase("datausers")) {
            try {
                User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
                userMaintMgr.getRptUser(user);
                return "datausers";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 19. recreate Action Routing
        else if (this.getCmd().equalsIgnoreCase("recreate")) {
            try {
                @SuppressWarnings("unchecked")
                ArrayList<RptUser> legacyCastList = new ArrayList<>(this.getUserList());
                userMaintMgr.recreateUser(legacyCastList);
                this.getRptUser().setDesc(this.getSelDesc());
                
                @SuppressWarnings("unchecked")
                ArrayList<RptUser> aryList = userMaintMgr.getUserList(this.getRptUser(), true);
                this.setUserList(aryList != null ? aryList : new ArrayList<>());
                session.setAttribute("userForm", this);
                addActionMessage(getText("success.user.create"));
                return "filter";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 20. recertify Action Routing
        else if (this.getCmd().equalsIgnoreCase("recertify")) {
            try {
                User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
                @SuppressWarnings("unchecked")
                ArrayList<RptUser> legacyCastList = new ArrayList<>(this.getUserList());
                userMaintMgr.recertifyUser(legacyCastList, user.getUserid());
                this.getRptUser().setDesc(this.getSelDesc());
                
                @SuppressWarnings("unchecked")
                ArrayList<RptUser> aryList = userMaintMgr.getUserList(this.getRptUser(), true);
                this.setUserList(aryList != null ? aryList : new ArrayList<>());
                session.setAttribute("userForm", this);
                addActionMessage(getText("success.user.create"));
                return "filter";
            } catch (TCGMException tcgme) {
                logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception";
            }
        }

        // 21. export File Download Handling Block
        else if (this.getCmd().equalsIgnoreCase("export")) {
            HttpServletResponse response = ServletActionContext.getResponse();
            ServletOutputStream out = response.getOutputStream();
            try {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition", "attachment; filename=Export.txt");
                PrintStream fout = new PrintStream(out);
                fout.println("User Id\tFirst Name\tLast Name\tRole\tRole Desc\tCreate Date\tRecertify Date");

                for (RptUser record : this.getUserList()) {
                    if (record.isSelected()) {
                        fout.println(record.getUserid() + "\t" + record.getFirstName() + "\t" +
                                     record.getLastName() + "\t" + record.getRole() + "\t" +
                                     record.getRoleDesc() + "\t" + record.getCreateDate() + "\t" +
                                     record.getRecertifyDate());
                    }
                }
                fout.close();
                return NONE; // Struts 2 file interception bypass marker
            } catch (Exception e) {
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, e);
                return "exception";
            } finally {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            }
        }

        // 22. Reset UI View State Processing
        else if (this.getCmd().equalsIgnoreCase("Reset")) {
            this.getRptUser().setUserid("");
            this.getRptUser().setFirstName("");
            this.getRptUser().setLastName("");
            session.setAttribute("userForm", this);
        }

        return SUCCESS;
    }

    public String getAreaDesc(String areaCode) {
        String areaDesc = "NONE";
        if ("01".equals(areaCode)) areaDesc = "LA";
        else if ("02".equals(areaCode)) areaDesc = "EUR";
        else if ("04".equals(areaCode)) areaDesc = "PAA";
        else if ("05".equals(areaCode)) areaDesc = "CAN";
        else if ("06".equals(areaCode)) areaDesc = "JPN";
        return areaDesc;
    }

    public long getUserListSize() {
    	userListSize =  this.getUserList().size();
    	return userListSize;
    }

    public long getAffMaintListSize() {
        return this.getAffMaintList().size();
    }


    @StrutsParameter(depth = 1)
    public RptUser getSearchObject() {
        if (this.searchObject == null) this.searchObject = new RptUser();
        return searchObject;
    }

    @StrutsParameter(depth = 1)
    public void setSearchObject(RptUser user) {
        this.searchObject = user;
    }

    @StrutsParameter(depth = 2)
    public List<RptUser> getUserList() {
        if (this.userList == null) this.userList = new ArrayList<>();
        return userList;
    }

    @StrutsParameter(depth = 2)
    public void setUserList(List<RptUser> userList) {
        this.userList = userList;
    }

    @StrutsParameter(depth = 2)
    public RptUser getRptUser() {
        if (this.rptUser == null) this.rptUser = new RptUser();
        return rptUser;
    }

    @StrutsParameter(depth = 2)
    public void setRptUser(RptUser rptUser) {
        this.rptUser = rptUser;
    }

    @StrutsParameter
    public String getSelDesc() {
        return selDesc;
    }

    @StrutsParameter
    public void setSelDesc(String selDesc) {
        this.selDesc = selDesc;
    }

    @StrutsParameter
    public String getCategory() {
        return category;
    }

    @StrutsParameter
    public void setCategory(String category) {
        this.category = category;
    }

    @StrutsParameter
    public String getCategoryid() {
        return categoryid;
    }

    @StrutsParameter
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    @StrutsParameter
    public String getCategoryname() {
        return categoryname;
    }

    @StrutsParameter
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    @StrutsParameter
    public String getAffCodeList() {
        return affCodeList;
    }

    @StrutsParameter
    public void setAffCodeList(String affCodeList) {
        this.affCodeList = affCodeList;
    }

    @StrutsParameter
    public String getSecCodeList() {
        return secCodeList;
    }

    @StrutsParameter
    public void setSecCodeList(String secCodeList) {
        this.secCodeList = secCodeList;
    }

    @StrutsParameter
    public String getAreaCodeList() {
        return areaCodeList;
    }

    @StrutsParameter
    public void setAreaCodeList(String areaCodeList) {
        this.areaCodeList = areaCodeList;
    }

    @StrutsParameter
    public String getDivisionCode() {
        return divisionCode;
    }

    @StrutsParameter
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public boolean isBlnDivision() {
        return blnDivision;
    }

    @StrutsParameter
    public void setBlnDivision(boolean blnDivision) {
        this.blnDivision = blnDivision;
    }

    @StrutsParameter(depth = 2)
    public List<ActiveAffMaint> getAffMaintList() {
        if (this.affMaintList == null) this.affMaintList = new ArrayList<>();
        return affMaintList;
    }

    @StrutsParameter(depth = 2)
    public void setAffMaintList(List<ActiveAffMaint> affMaintList) {
        this.affMaintList = affMaintList;
    }

    @StrutsParameter
    public String getCmd() {
        return cmd;
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    
    @StrutsParameter
    public String getCmd2() {
        return cmd2;
    }

    @StrutsParameter
    public void setCmd2(String cmd2) {
        this.cmd2 = cmd2;
    }

    @StrutsParameter
    public String getHidVal() {
        return hidVal;
    }

    @StrutsParameter
    public void setHidVal(String hidVal) {
        this.hidVal = hidVal;
    }

    @StrutsParameter
    public void setUserListSize(long userListSize) {
       // this.userListSize = userListSize;
    }

}