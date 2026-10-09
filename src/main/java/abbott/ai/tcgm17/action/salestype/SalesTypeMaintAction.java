package abbott.ai.tcgm17.action.salestype;

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
 * <p>Description: Unified Sales Type Maintenance Action migrated to Struts 7.3.0 & JDK 17</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 3.1
 */
public class SalesTypeMaintAction extends TCGMAction {

    private static final long serialVersionUID = 1L;

    private SalesType searchObject = new SalesType();
    private Vector slsTypeList = new Vector();
    private Sort sortObject = new Sort("SLS_TYP", "ASC");
    private SalesType slsTypeToEdit = new SalesType();
    private Vector divCodes = new Vector();
    private String cmd = "";

    public SalesTypeMaintAction() {
        super();
    }

    /**
     * Migrated from SalesTypeMaint
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

            SalesTypeMngr salesTypeMngr = new SalesTypeMngr();
            try {
                this.slsTypeList = salesTypeMngr.getSalesType(
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
     * Migrated from DeleteSalesType
     */
    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken userToken = this.getUserToken();
            this.processCmd();
            SalesTypeMngr salesTypeMngr = new SalesTypeMngr();

            try {
                Vector currentListState = this.getSlsTypeList();
                if (currentListState == null || currentListState.isEmpty()) {
                    currentListState = salesTypeMngr.getSalesType(userToken, this.getSortObject());
                }

                if (currentListState != null) {
                    for (int i = 0; i < currentListState.size(); i++) {
                        Object element = currentListState.get(i);
                        if (element instanceof SalesType) {
                            SalesType st = (SalesType) element;
                            String paramValue = request.getParameter("slsTypeList[" + i + "].selected");
                            if ("on".equalsIgnoreCase(paramValue) || "true".equalsIgnoreCase(paramValue)) {
                                st.setSelected(true);
                            } else {
                                st.setSelected(false);
                            }
                        }
                    }
                }

                if (currentListState == null) {
                    currentListState = new Vector();
                }

                salesTypeMngr.deleteSalesType(userToken, currentListState);
                
                this.slsTypeToEdit = new SalesType();
                this.searchObject = new SalesType();
                this.slsTypeList = salesTypeMngr.getSalesType(userToken, this.getSortObject());
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
     * Migrated from EditSalesType
     */
    public String edit() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken userToken = this.getUserToken();
            this.processCmd();
            SalesTypeMngr salesTypeMngr = new SalesTypeMngr();

            try {
                this.slsTypeList = salesTypeMngr.getSalesType(userToken, this.getSortObject());
                this.slsTypeToEdit = salesTypeMngr.getSalesTypeBySlsType(userToken, this.getSearchObject());
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
     * Migrated from SaveSalesType
     */
    public String save() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        this.clearErrorsAndMessages();

        if (this.isSessionValid()) {
            UserToken userToken = this.getUserToken();
            this.processCmd();
            SalesTypeMngr salesTypeMngr = new SalesTypeMngr();

            try {
                salesTypeMngr.saveSalesType(userToken, this.getSlsTypeToEdit());
                this.slsTypeToEdit = new SalesType();
                this.searchObject = new SalesType();
                this.slsTypeList = salesTypeMngr.getSalesType(userToken, this.getSortObject());
                return TCGMConstants.FORWARD_SUCCESS;
            } catch (TCGMException tcgme) {
                this.logger.error(tcgme.toString(), tcgme);
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return TCGMConstants.G_FORWARD_EXCEPTION;
            }
        }
        return TCGMConstants.G_FORWARD_SELECT_MODEL;
    }

    private void processCmd() {
        if (TCGMConstants.URL_PARM_VAL_CANCEL.equals(this.getCmd())) {
            this.slsTypeToEdit = new SalesType();
            this.searchObject = new SalesType();
        }
    }

    public String getCmd() {
        return this.cmd;
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public SalesType getSearchObject() {
        if (this.searchObject == null) {
            this.searchObject = new SalesType();
        }
        return this.searchObject;
    }

    @StrutsParameter(depth = 1)
    public void setSearchObject(SalesType searchObject) {
        this.searchObject = searchObject;
    }

    public Sort getSortObject() {
        if (this.sortObject == null) {
            this.sortObject = new Sort("SLS_TYP", "ASC");
        }
        return this.sortObject;
    }

    @StrutsParameter(depth = 1)
    public void setSortObject(Sort sortObject) {
        this.sortObject = sortObject;
    }

    public Vector getSlsTypeList() {
        if (this.slsTypeList == null) {
            this.slsTypeList = new Vector();
        }
        return this.slsTypeList;
    }

    @StrutsParameter(depth = 2)
    public void setSlsTypeList(Vector slsTypeList) {
        this.slsTypeList = slsTypeList;
    }

    public SalesType getSlsTypelist(int index) {
        SalesType slsType = null;
        if (index >= 0 && index < this.getSlsTypeList().size()) {
            slsType = (SalesType) this.getSlsTypeList().elementAt(index);
        }
        return slsType;
    }

    public void setSlsTypelist(int index, SalesType slsType) {
        this.getSlsTypeList().setElementAt(slsType, index);
    }

    public int getSlsTypeListSize() {
        return this.getSlsTypeList().size();
    }

    public SalesType getSlsTypeToEdit() {
        if (this.slsTypeToEdit == null) {
            this.slsTypeToEdit = new SalesType();
        }
        return this.slsTypeToEdit;
    }

    @StrutsParameter(depth = 1)
    public void setSlsTypeToEdit(SalesType slsTypeToEdit) {
        this.slsTypeToEdit = slsTypeToEdit;
    }

    public Vector getDivCodes() {
        if (this.divCodes == null) {
            this.divCodes = new Vector();
        }
        return this.divCodes;
    }

    @StrutsParameter(depth = 1)
    public void setDivCodes(Vector divCodes) {
        this.divCodes = divCodes;
    }

    public String getDivCode(int index) {
        if (index >= 0 && index < this.getDivCodes().size()) {
            return (String) this.getDivCodes().elementAt(index);
        }
        return null;
    }

    public void setDivCode(int index, String divCode) {
        if (index >= 0 && index < this.getDivCodes().size()) {
            this.getDivCodes().set(index, divCode);
        }
    }
}
