package abbott.ai.tcgm.action.form;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MngUnitsForm extends TCGMMngSetsForm {

    private String selFetchSystem;
    private String txtFetchYear;
    private String txtFetchVal;
    public MngUnitsForm() {
        super();
    }
    public void setSelFetchSystem(String selFetchSystem) {
        this.selFetchSystem = selFetchSystem;
    }
    public String getSelFetchSystem() {
        return selFetchSystem;
    }
    public void setTxtFetchYear(String txtFetchYear) {
        this.txtFetchYear = txtFetchYear;
    }
    public String getTxtFetchYear() {
        return txtFetchYear;
    }

	/**
	 * @return Returns the txtFetchVal.
	 */
	public String getTxtFetchVal() {
		return txtFetchVal;
	}
	/**
	 * @param txtFetchVal The txtFetchVal to set.
	 */
	public void setTxtFetchVal(String txtFetchVal) {
		this.txtFetchVal = txtFetchVal;
	}
}