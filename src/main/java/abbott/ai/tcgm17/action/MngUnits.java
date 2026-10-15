package abbott.ai.tcgm17.action;

import java.util.Map;
import java.util.Vector;

import org.apache.struts2.action.SessionAware;

import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.UnitMngr;

public class MngUnits extends TCGMAction implements SessionAware {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8033393194077467409L;
	/* =========================
     * Form Bean Properties
     * ========================= */
    private String selDataset;
    private Vector datasets;

    private String selFetchSystem;
    private String txtFetchYear;
    private String txtFetchVal;
    
    private Map<String, Object> session;
    
    public MngUnits() {
		super();
	}

    public String execute() {

        try {

            if (isSessionValid()) {

                UserToken ut = getUserToken();

                UnitMngr um = new UnitMngr();

                datasets = um.getUnitSets(ut);

                return SUCCESS;
            }

            return LOGIN;

        } catch (TCGMException ex) {

            addActionError(ex.getMessage());
            //setSessionException(ex);

            return "exception";
        }
    }

    public void reset() {

        selDataset = null;
        datasets = null;
        selFetchSystem = null;
        txtFetchYear = null;
        txtFetchVal = null;
    }

    public void initModelAndDataset() {

        try {
            UserToken ut = getUserToken();
            UnitMngr um = new UnitMngr();
            datasets = um.getUnitSets(ut);
        } catch (Exception e) {
            logger.error("Error initializing datasets", e);
        }
    }

    /* =========================
     * Getters and Setters
     * ========================= */

    public String getSelDataset() {
        return selDataset;
    }

    public void setSelDataset(String selDataset) {
        this.selDataset = selDataset;
    }

    public Vector getDatasets() {
        return datasets;
    }

    public void setDatasets(Vector datasets) {
        this.datasets = datasets;
    }

    public String getSelFetchSystem() {
        return selFetchSystem;
    }

    public void setSelFetchSystem(String selFetchSystem) {
        this.selFetchSystem = selFetchSystem;
    }

    public String getTxtFetchYear() {
        return txtFetchYear;
    }

    public void setTxtFetchYear(String txtFetchYear) {
        this.txtFetchYear = txtFetchYear;
    }

    public String getTxtFetchVal() {
        return txtFetchVal;
    }

    public void setTxtFetchVal(String txtFetchVal) {
        this.txtFetchVal = txtFetchVal;
    }

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	
}