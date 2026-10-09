package abbott.ai.tcgm17.action.currency;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Vector;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Unified Currency Maintenance Action migrated to Struts 7.3.0 & JDK 17</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 3.1
 */
public class CurrencyCodeMaintAction extends TCGMAction {

    private static final long serialVersionUID = 1L;
	
    private CurrencyCode searchObject = new CurrencyCode();
    private Vector currencylist = new Vector();
    private Sort sortObject = DBConst.DEF_SORT_CURRENCY;
    private CurrencyCode currencyCodeToEdit = new CurrencyCode();
    private String cmd = "";

    /**
     * Default Constructor
     */
    public CurrencyCodeMaintAction() {
        super();
    }

    /**
     * Migrated from CurrencyCodeMaint
     */
    @Override
    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            this.processCmd();

            if ("deleteselected".equalsIgnoreCase(this.cmd)) {
                return this.delete();
            }
            
            CurrencyCodeMngr currencyCodeMngr = new CurrencyCodeMngr();
            try {
                this.currencylist = currencyCodeMngr.getCurrencyCode(
                    this.getUserToken(), 
                    this.getSearchObject(), 
                    this.getSortObject()
                );
                return TCGMConstants.FORWARD_SUCCESS;
            } catch (TCGMException tcgme) {
                this.logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        }
        return TCGMConstants.G_FORWARD_SELECT_MODEL;
    }

    /**
     * Migrated from DeleteCurrencyCode
     */
    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken userToken = this.getUserToken();
            this.processCmd();
            CurrencyCodeMngr currencyCodeMngr = new CurrencyCodeMngr();

            try {
                Vector currentListState = this.getCurrencylist();
                if (currentListState == null || currentListState.isEmpty()) {
                    currentListState = currencyCodeMngr.getCurrencyCode(userToken, this.getSortObject());
                }

                if (currentListState != null) {
                    for (int i = 0; i < currentListState.size(); i++) {
                        Object element = currentListState.get(i);
                        if (element instanceof CurrencyCode) {
                            CurrencyCode cc = (CurrencyCode) element;
                            String paramValue = request.getParameter("currencylist[" + i + "].selected");
                            if ("on".equalsIgnoreCase(paramValue) || "true".equalsIgnoreCase(paramValue)) {
                                cc.setSelected(true); 
                            } else {
                                cc.setSelected(false);
                            }
                        }
                    }
                }

                if (currentListState == null) {
                    currentListState = new Vector();
                }

                currencyCodeMngr.deleteCurrencys(userToken, currentListState);
                
                this.currencyCodeToEdit = new CurrencyCode();
                this.searchObject = new CurrencyCode();
                this.currencylist = currencyCodeMngr.getCurrencyCode(userToken, this.getSortObject());
                return TCGMConstants.FORWARD_SUCCESS;
            } catch (Throwable t) {
                this.logger.error("CRITICAL EXCEPTION IN ACTION DELETE: " + t.getMessage(), t);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, t);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        }
        return TCGMConstants.G_FORWARD_SELECT_MODEL;
    }

    /**
     * Migrated from EditCurrencyCode
     */
    public String edit() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken userToken = this.getUserToken();
            this.processCmd();
            CurrencyCodeMngr currencyCodeMngr = new CurrencyCodeMngr();

            try {
                this.currencylist = currencyCodeMngr.getCurrencyCode(userToken, this.getSortObject());
                this.currencyCodeToEdit = currencyCodeMngr.getCurrencyByCode(userToken, this.getSearchObject());
                return TCGMConstants.FORWARD_SUCCESS;
            } catch (TCGMException tcgme) {
                this.logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        }
        return TCGMConstants.G_FORWARD_SELECT_MODEL;
    }

    /**
     * Migrated from SaveCurrencyCode
     */
    public String save() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken userToken = this.getUserToken();
            this.processCmd();
            CurrencyCodeMngr currencyCodeMngr = new CurrencyCodeMngr();

            try {
                currencyCodeMngr.saveCurrencyCode(userToken, this.getCurrencyCodeToEdit());
                this.currencyCodeToEdit = new CurrencyCode();
                this.searchObject = new CurrencyCode();
                this.currencylist = currencyCodeMngr.getCurrencyCode(userToken, this.getSortObject());
                return TCGMConstants.FORWARD_SUCCESS;
            } catch (TCGMException tcgme) {
                this.logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        }
        return TCGMConstants.G_FORWARD_SELECT_MODEL;
    }

    /**
     * Emulates legacy Form-level action commands
     */
    private void processCmd() {
        if (TCGMConstants.URL_PARM_VAL_CANCEL.equals(this.getCmd())) {
            this.currencyCodeToEdit = new CurrencyCode();
            this.searchObject = new CurrencyCode();
        }
    }

    // =========================================================================
    // Struts 7 Security Parameter Injection Handlers
    // =========================================================================

    public String getCmd() {
        return this.cmd;
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public CurrencyCode getSearchObject() {
        if (this.searchObject == null) {
            this.searchObject = new CurrencyCode();
        }
        return this.searchObject;
    }

    @StrutsParameter(depth = 1)
    public void setSearchObject(CurrencyCode searchObject) {
        this.searchObject = searchObject;
    }

    public Sort getSortObject() {
        if (this.sortObject == null) {
            this.sortObject = DBConst.DEF_SORT_CURRENCY;
        }
        return this.sortObject;
    }

    @StrutsParameter(depth = 1)
    public void setSortObject(Sort sortObject) {
        this.sortObject = sortObject;
    }

    public Vector getCurrencylist() {
        if (this.currencylist == null) {
            this.currencylist = new Vector();
        }
        return this.currencylist;
    }

    @StrutsParameter(depth = 2) 
    public void setCurrencylist(Vector currencyList) {
        this.currencylist = currencyList;
    }

    public CurrencyCode getCurrencyList(int index) {
        CurrencyCode currencyCode = null;
        if (index >= 0 && index < this.getCurrencylist().size()) {
            currencyCode = (CurrencyCode) this.getCurrencylist().elementAt(index);
        }
        return currencyCode;
    }

    public void setCurrencyList(int index, CurrencyCode currencyCode) {
        this.getCurrencylist().setElementAt(currencyCode, index);
    }

    public int getCurrencyListSize() {
        return this.getCurrencylist().size();
    }

    public CurrencyCode getCurrencyCodeToEdit() {
        if (this.currencyCodeToEdit == null) {
            this.currencyCodeToEdit = new CurrencyCode();
        }
        return this.currencyCodeToEdit;
    }

    @StrutsParameter(depth = 1)
    public void setCurrencyCodeToEdit(CurrencyCode currencyCodeToEdit) {
        this.currencyCodeToEdit = currencyCodeToEdit;
    }
}
