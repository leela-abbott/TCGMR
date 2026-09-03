package abbott.ai.tcgm.action.form;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TCGMMngSetsForm extends TCGMForm {

    private String selDataset;
    private java.util.Vector datasets;
    public TCGMMngSetsForm() {
        super();
    }
    public String getSelDataset() {
        return selDataset;
    }
    public void setSelDataset(String selDataset) {
        this.selDataset = selDataset;
    }
    public void setDatasets(java.util.Vector datasets) {
        this.datasets = datasets;
    }
    public java.util.Vector getDatasets() {
        return datasets;
    }
}