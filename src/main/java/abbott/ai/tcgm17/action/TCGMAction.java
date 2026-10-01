package abbott.ai.tcgm17.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionContext;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.action.ServletRequestAware;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.BpcEx;
import abbott.ai.tcgm.entities.BpcRev;
import abbott.ai.tcgm.entities.Bpcs;
import abbott.ai.tcgm.entities.Notes;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class TCGMAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;
    
    protected String forward;
    protected static Logger logger = LogManager.getLogger(TCGMAction.class);
    protected final String className = this.getClass().getName();
    protected HttpServletRequest request;

    public TCGMAction() {
        super();
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    protected boolean isSessionValid() {
        if (this.request == null) {
            return false;
        }
        HttpSession session = this.request.getSession(false);
        if (session == null || session.getAttribute(TCGMConstants.SESSION_NAME_USER) == null) {
            addActionError(getText("error.user.session.invalid"));
            this.forward = TCGMConstants.G_FORWARD_LOGIN;
            return false;
        }
        return true;
    }

    protected boolean isModelSelected() {
        if (!isSessionValid()) return false;
        
        if (this.getState().getCurrentModelName().equals(TCGMConstants.NONE_SELECTED)) {
            if (!hasActionErrors()) {
                addActionError(getText("error.model.none_selected"));
            }
            this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
            return false;
        }
        return true;
    }

    protected boolean isModelOpen() {
        if (!isSessionValid()) return false;

        if (this.getState().getCurrentModelName().equals(TCGMConstants.NONE_SELECTED)) {
            addActionError(getText("error.model.none_selected"));
            this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
            return false;
        } else {
            ModelMngr mm = new ModelMngr();
            UserToken ut = this.getUserToken();
            String modelId = this.getState().getCurrentModelIdString();
            try {
                String modelStatus = mm.getModelStatus(ut, modelId).trim();
                if (!"OPEN".equalsIgnoreCase(modelStatus)) {
                    logger.info("modelStatus = {}", modelStatus);
                    return false;
                }
            } catch (TCGMException ex) {
                if (this.request != null) {
                    this.request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
                }
                this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
                return false;
            }
        }
        return true;
    }

    protected boolean isRateSetSelected() {
        if (!isSessionValid()) return false;

        if (this.getState().getCurRateSetName().equals(TCGMConstants.NONE_SELECTED)) {
            addActionError(getText("error.rateset.none_selected"));
            this.setForward(TCGMConstants.G_FORWARD_SELECT_RATE_SET);
            return false;
        }
        return true;
    }

    protected User getSessionUser() {
        if (this.request == null) return null;
        HttpSession session = this.request.getSession(false);
        return (session != null) ? (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER) : null;
    }

    protected UserToken getUserToken() {
        User user = getSessionUser();
        return (user != null) ? user.getUserToken() : null;
    }
    
    protected UserToken getUserToken(HttpServletRequest request)
	{
		return getSessionUser(request).getUserToken();
	}

    protected TCGMState getState() {
        if (this.request == null) return new TCGMState();
        HttpSession session = this.request.getSession();
        TCGMState state = (TCGMState) session.getAttribute(TCGMConstants.SESSION_NAME_STATE);
        if (state == null) {
            state = new TCGMState();
            session.setAttribute(TCGMConstants.SESSION_NAME_STATE, state);
        }
        return state;
    }

    protected boolean isCmdValid(TCGMProductionForm myForm) {
        return myForm != null && !TCGMUtil.isEmpty(myForm.getCmd()) && !"CANCEL".equals(myForm.getCmd());
    }

    protected String getCmd() {
        if (this.request == null) return "";
        String cmd = this.request.getParameter(TCGMConstants.URL_PARM_CMD);
        return (cmd != null) ? cmd : "";
    }

    protected String getForward() {
        return this.forward;
    }

    protected void setForward(String forward) {
        this.forward = forward;
    }

    public List<Asr> createEmptyAsrRecs(String modelId, String datasetTableId) throws TCGMException {
        String methodName = "createEmptyAsrRecs";
        List<Asr> list = new ArrayList<>();
        try {
            for (int i = 0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++) {
                Asr asr = new Asr();
                asr.setModelId(modelId);
                asr.setDatasetTableId(datasetTableId);
                list.add(asr);
            }
            return list;
        } catch (Exception e) {
            throw new TCGMException(this.className, methodName, e.toString());
        }
    }

    public List<Bpcs> createEmptyBpcRecs(String modelId, String datasetTableId) throws TCGMException {
        String methodName = "createEmptyBpcRecs";
        List<Bpcs> list = new ArrayList<>();
        try {
            for (int i = 0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++) {
                Bpcs bpcs = new Bpcs();
                bpcs.setModelId(modelId);
                bpcs.setDatasetTableId(datasetTableId);
                bpcs.setBegPeriod("1");
                bpcs.setEndPeriod("12");
                list.add(bpcs);
            }
            return list;
        } catch (Exception e) {
            throw new TCGMException(this.className, methodName, e.toString());
        }
    }        

    public List<BpcRev> createEmptyBpcRevRecs(String modelId, String datasetTableId) throws TCGMException {
        String methodName = "createEmptyBpcRevRecs";
        List<BpcRev> list = new ArrayList<>();
        try {
            for (int i = 0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++) {
                BpcRev bpcRev = new BpcRev();
                bpcRev.setModelId(modelId);
                bpcRev.setDatasetTableId(datasetTableId);
                bpcRev.setBegPeriod("1");
                bpcRev.setEndPeriod("12");
                list.add(bpcRev);
            }
            return list;
        } catch (Exception e) {
            throw new TCGMException(this.className, methodName, e.toString());
        }
    }            

    public List<BpcEx> createEmptyBpcExRecs(String modelId, String datasetTableId) throws TCGMException {
        String methodName = "createEmptyBpcExRecs";
        List<BpcEx> list = new ArrayList<>();
        try {
            for (int i = 0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++) {
                BpcEx bpcEx = new BpcEx();
                bpcEx.setModelId(modelId);
                bpcEx.setDatasetTableId(datasetTableId);
                bpcEx.setBegPeriod("1");
                bpcEx.setEndPeriod("12");
                list.add(bpcEx);
            }
            return list;
        } catch (Exception e) {
            throw new TCGMException(this.className, methodName, e.toString());
        }
    }            

    public List<Notes> createEmptyNotesRecs(String modelId, String datasetTableId) throws TCGMException {
        String methodName = "createEmptyNotesRecs";
        List<Notes> list = new ArrayList<>();
        try {
            for (int i = 0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++) {
                Notes notes = new Notes();
                notes.setModelId(modelId);
                notes.setDatasetTableId(datasetTableId);
                list.add(notes);
            }
            return list;
        } catch (Exception e) {
            throw new TCGMException(this.className, methodName, e.toString());
        }
    } 
    
    protected HttpServletRequest getRequest()
	{
		return (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
	}
    
    protected boolean isSessionValid(HttpServletRequest request)
	{
		boolean isSessionValid = true;
		if (request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER) == null)
		{
			isSessionValid = false;
			this.addActionError(getText("error.user.session.invalid"));
			this.forward = TCGMConstants.G_FORWARD_LOGIN;
		}
		return isSessionValid;
	}
    
    protected User getSessionUser(HttpServletRequest request)
	{
		return (User) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);
	}
}
