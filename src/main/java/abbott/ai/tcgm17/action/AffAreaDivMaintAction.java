package abbott.ai.tcgm17.action;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.AffAreaDivsion;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AffAreaDivMngr;

public class AffAreaDivMaintAction extends TCGMAction implements ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AffAreaDivMaintAction.class);

    private HttpServletRequest request;

    private String cmd = "";
    private String affcode = "";
    private String affListCode = "";
    private AffAreaDivsion searchObject = new AffAreaDivsion();
    private List<AffAreaDivsion> afflist = new ArrayList<>();
    private List<AffAreaDivsion> affCodeList = new ArrayList<>();

    public AffAreaDivMaintAction() {
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String execute() {
        HttpSession session = request.getSession();

        if (!isSessionValid()) {
          //  return "selectModel";
        }

        AffAreaDivMngr affCodeMngr = new AffAreaDivMngr();

        try {
            if ("save".equalsIgnoreCase(cmd)) {
                int cnt = affCodeMngr.createAffWanted(affcode);
                if (cnt == 0) {
                    addActionMessage(getText("success.aaffArea.insert"));
                } else {
                    addActionError(getText("error.duplicate.affArea"));
                }
            } else if ("delete".equalsIgnoreCase(cmd)) {
                affCodeMngr.deleteAffWanted((ArrayList) afflist);
                addActionMessage(getText("success.aaffArea.delete"));
            }

            this.setCmd("");
            this.setAffCodeList(affCodeMngr.getAffcodes());
            this.setAfflist(affCodeMngr.getAffWanted());

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

    @StrutsParameter
    public void setAffcode(String affcode) {
        this.affcode = affcode;
    }

    public String getAffcode() {
        return affcode;
    }

    @StrutsParameter
    public void setAffListCode(String affListCode) {
        this.affListCode = affListCode;
    }

    public String getAffListCode() {
        return affListCode;
    }

    @StrutsParameter(depth = 2)
    public void setSearchObject(AffAreaDivsion divsion) {
        this.searchObject = divsion;
    }

    public AffAreaDivsion getSearchObject() {
        return searchObject;
    }

    @StrutsParameter(depth = 2)
    public void setAfflist(List<AffAreaDivsion> list) {
        this.afflist = list;
    }

    public List<AffAreaDivsion> getAfflist() {
        return afflist;
    }

    @StrutsParameter(depth = 2)
    public void setAffCodeList(List<AffAreaDivsion> list) {
        this.affCodeList = list;
    }

    public List<AffAreaDivsion> getAffCodeList() {
        return affCodeList;
    }
    
    public AffAreaDivsion getAffCodeList(int index) {
        if (index >= 0 && index < this.afflist.size()) {
            return this.afflist.get(index);
        }
        return null;
    }

    public void setAffCodeList(int index, AffAreaDivsion affCode) {
        if (index >= 0 && index <= this.afflist.size()) {
            this.afflist.add(index, affCode);
        }
    }

    public int getAffListSize() {
        return this.afflist.size();
    }
}
