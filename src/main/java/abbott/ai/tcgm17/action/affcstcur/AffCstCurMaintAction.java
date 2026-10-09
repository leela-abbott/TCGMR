package abbott.ai.tcgm17.action.affcstcur;

import java.util.Vector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.AffCstCur;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.helpers.AffCstCurMngr;
import abbott.ai.tcgm.helpers.CurrencyCodeMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.data.DBConst;

public class AffCstCurMaintAction extends TCGMAction implements ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AffCstCurMaintAction.class);

    private HttpServletRequest request;

    private String cmd = "";
    private AffCstCur searchObject = new AffCstCur();
    private Vector<AffCstCur> affCstCurList = new Vector<>();
    private Sort sortObject = DBConst.DEF_SORT_AFFCSTCUR;
    private AffCstCur affCstCurToEdit = new AffCstCur();
    private Vector<String> curCodes = new Vector<>();

    public AffCstCurMaintAction() {
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    private void processCmd() {
        if (TCGMConstants.URL_PARM_VAL_CANCEL.equals(this.cmd)) {
            this.affCstCurToEdit = new AffCstCur();
            this.searchObject = new AffCstCur();
        }
    }

    @Override
    public String execute() {
        if (!isSessionValid(request)) {
            return "selectModel";
        }

        processCmd();
        AffCstCurMngr affCstCurMngr = new AffCstCurMngr();
        CurrencyCodeMngr currCodeMngr = new CurrencyCodeMngr();
        UserToken userToken = this.getUserToken(request);

        try {
            this.affCstCurList = affCstCurMngr.getAffCstCur(userToken, this.getSearchObject(), this.getSortObject());
            this.curCodes = currCodeMngr.getCurrencyCode(userToken, DBConst.DEF_SORT_CURRENCY);
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return "exception";
        }
    }

    public String delete() {
        if (!isSessionValid(request)) {
            return "selectModel";
        }

        processCmd();
        AffCstCurMngr affCstCurMngr = new AffCstCurMngr();
        CurrencyCodeMngr currCodeMngr = new CurrencyCodeMngr();
        UserToken userToken = this.getUserToken(request);

        try {
            affCstCurMngr.deleteAffCstCurs(userToken, this.getAffCstCurList());
            this.affCstCurToEdit = new AffCstCur();
            this.searchObject = new AffCstCur();
            this.affCstCurList = affCstCurMngr.getAffCstCur(userToken, this.getSortObject());
            this.curCodes = currCodeMngr.getCurrencyCode(userToken, DBConst.DEF_SORT_CURRENCY);
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return "exception";
        }
    }

    public String edit() {
        if (!isSessionValid(request)) {
            return "selectModel";
        }

        processCmd();
        AffCstCurMngr affCstCurMngr = new AffCstCurMngr();
        CurrencyCodeMngr currCodeMngr = new CurrencyCodeMngr();
        UserToken userToken = this.getUserToken(request);

        try {
            this.affCstCurList = affCstCurMngr.getAffCstCur(userToken, this.getSortObject());
            this.affCstCurToEdit = affCstCurMngr.getAffCstCurByAff(userToken, this.getSearchObject());
            this.curCodes = currCodeMngr.getCurrencyCode(userToken, DBConst.DEF_SORT_CURRENCY);
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return "exception";
        }
    }

    public String save() {
        if (!isSessionValid(request)) {
            return "selectModel";
        }

        processCmd();
        AffCstCurMngr affCstCurMngr = new AffCstCurMngr();
        CurrencyCodeMngr currCodeMngr = new CurrencyCodeMngr();
        UserToken userToken = this.getUserToken(request);

        try {
            affCstCurMngr.saveAffCstCur(userToken, this.getAffCstCurToEdit());
            this.affCstCurToEdit = new AffCstCur();
            this.searchObject = new AffCstCur();
            this.affCstCurList = affCstCurMngr.getAffCstCur(userToken, this.getSortObject());
            this.curCodes = currCodeMngr.getCurrencyCode(userToken, DBConst.DEF_SORT_CURRENCY);
            return SUCCESS;
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return "exception";
        }
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    @StrutsParameter(depth = 2)
    public void setSearchObject(AffCstCur searchObject) {
        this.searchObject = searchObject;
    }

    public AffCstCur getSearchObject() {
        if (this.searchObject == null) {
            this.searchObject = new AffCstCur();
        }
        return this.searchObject;
    }

    @StrutsParameter(depth = 2)
    public void setSortObject(Sort sortObject) {
        this.sortObject = sortObject;
    }

    public Sort getSortObject() {
        if (this.sortObject == null) {
            this.sortObject = DBConst.DEF_SORT_AFFCSTCUR;
        }
        return this.sortObject;
    }

    @StrutsParameter(depth = 2)
    public void setAffCstCurList(Vector<AffCstCur> affCstCurList) {
        this.affCstCurList = affCstCurList;
    }

    public Vector<AffCstCur> getAffCstCurList() {
        if (this.affCstCurList == null) {
            this.affCstCurList = new Vector<>();
        }
        return this.affCstCurList;
    }

    @StrutsParameter(depth = 2)
    public void setAffCstCurToEdit(AffCstCur affCstCurToEdit) {
        this.affCstCurToEdit = affCstCurToEdit;
    }

    public AffCstCur getAffCstCurToEdit() {
        if (this.affCstCurToEdit == null) {
            this.affCstCurToEdit = new AffCstCur();
        }
        return this.affCstCurToEdit;
    }

    @StrutsParameter(depth = 2)
    public void setCurCodes(Vector<String> curCodes) {
        this.curCodes = curCodes;
    }

    public Vector<String> getCurCodes() {
        if (this.curCodes == null) {
            this.curCodes = new Vector<>();
        }
        return this.curCodes;
    }

    public AffCstCur getAffCstCurlist(int index) {
        if (index >= 0 && index < this.getAffCstCurList().size()) {
            return this.getAffCstCurList().elementAt(index);
        }
        return null;
    }

    public void setAffCstCurlist(int index, AffCstCur affCstCur) {
        if (index >= 0 && index < this.getAffCstCurList().size()) {
            this.getAffCstCurList().setElementAt(affCstCur, index);
        }
    }

    public int getAffCstCurListSize() {
        return this.getAffCstCurList().size();
    }

    public String getCurCode(int index) {
        if (index >= 0 && index < this.getCurCodes().size()) {
            return this.getCurCodes().elementAt(index);
        }
        return null;
    }

    public void setCurCode(int index, String curCode) {
        if (index >= 0 && index < this.getCurCodes().size()) {
            this.getCurCodes().set(index, curCode);
        }
    }
}
