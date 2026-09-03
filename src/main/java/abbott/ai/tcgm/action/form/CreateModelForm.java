package abbott.ai.tcgm.action.form;

//import java.util.Vector;
import abbott.ai.tcgm.entities.TCGMModel;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public abstract class CreateModelForm extends TCGMForm {

    private String modelName;
    private String modelDesc;
    private java.util.Vector factorModels;

    public CreateModelForm() {
        super();
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public String getModelName() {
        return modelName;
    }
    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }
    public String getModelDesc() {
        return modelDesc;
    }
    public abstract TCGMModel getModelFromForm();
    public void setFactorModels(java.util.Vector factorModels) {
        this.factorModels = factorModels;
    }
    public java.util.Vector getFactorModels() {
        return factorModels;
    }
}