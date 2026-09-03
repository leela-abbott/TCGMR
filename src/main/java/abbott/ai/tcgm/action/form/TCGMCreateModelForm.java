package abbott.ai.tcgm.action.form;
import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.struts.action.*;
//import java.util.Vector;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import org.apache.log4j.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public abstract class TCGMCreateModelForm extends TCGMForm {

    private String modelName;
    private String modelDesc;
    private java.util.Vector factorModels;
    private static Logger myLogger = Logger.getLogger("abbott.ai.tcgm.action.form.TCGMCreateModelForm");

    public TCGMCreateModelForm() {
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
    public abstract TCGMModel getModelFromForm() throws TCGMException;
    public void setFactorModels(java.util.Vector factorModels) {
        this.factorModels = factorModels;
    }
    public java.util.Vector getFactorModels() {
        return factorModels;
    }


    public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {

        // if cmd is empty this form is clean and should not be validated
        if ( TCGMUtil.isEmpty( this.getCmd() ) ) return null;

        ActionErrors errors = new ActionErrors();

        /*try {

            ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao( SQLUtil.getOracleAdmin(), this.getModelType() );

            if ( md.exists( this.getModelName() ) ) {
                errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.create.duplicate"));
            }

        }
        catch (TCGMException tex) {
            // severe exceptions only
            myLogger.error("Exception validating form input for model type: " + this.getModelType().toString(), tex);
        }*/
        if ( errors.empty() )
            return null;
        else
            return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.reset();
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        this.reset();
    }

    public void reset() {
        this.setCmd("");
    }
}