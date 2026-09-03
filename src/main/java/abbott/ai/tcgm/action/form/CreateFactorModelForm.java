package abbott.ai.tcgm.action.form;

import abbott.ai.tcgm.entities.*;
import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class CreateFactorModelForm extends TCGMCreateModelForm {
    private Vector factorModels = new Vector();
    private String selSourceModelId;
    private String createModelName;
    private String createModelDesc;
    private int createModelKeepBpcsPeriod;
    private int createModelKeepBPExPeriod;
    private int createModelKeepExchExPeriod;
    private boolean createModelClearRevDates;
    private boolean createModelClearBPCS;
    private boolean createModelClearFreezeCosts;
    private String createModelCycle;
    private String createModelYear;
    public Vector getFactorModels() {
        return factorModels;
    }
    public void setFactorModels(Vector factorModels) {
        this.factorModels = factorModels;
    }
    public void setSelSourceModelId(String selSourceModelId) {
        this.selSourceModelId = selSourceModelId;
    }
    public String getSelSourceModelId() {
        return selSourceModelId;
    }
    public void setCreateModelName(String createModelName) {
        this.createModelName = createModelName;
    }
    public String getCreateModelName() {
        return createModelName;
    }
    public void setCreateModelDesc(String createModelDesc) {
        this.createModelDesc = createModelDesc;
    }
    public String getCreateModelDesc() {
        return createModelDesc;
    }

    public TCGMModel getModelFromForm() {
        return null;
    }

    public void setCreateModelClearBPCS(boolean createModelClearBPCS) {
        this.createModelClearBPCS = createModelClearBPCS;
    }
    public boolean isCreateModelClearBPCS() {
        return createModelClearBPCS;
    }
    public void setCreateModelClearRevDates(boolean createModelClearRevDates) {
        this.createModelClearRevDates = createModelClearRevDates;
    }
    public boolean isCreateModelClearRevDates() {
        return createModelClearRevDates;
    }
    public void setCreateModelKeepBpcsPeriod(int createModelKeepBpcsPeriod) {
        this.createModelKeepBpcsPeriod = createModelKeepBpcsPeriod;
    }
    public int getCreateModelKeepBpcsPeriod() {
        return createModelKeepBpcsPeriod;
    }
    public void setCreateModelKeepBPExPeriod(int createModelKeepBPExPeriod) {
        this.createModelKeepBPExPeriod = createModelKeepBPExPeriod;
    }
    public int getCreateModelKeepBPExPeriod() {
        return createModelKeepBPExPeriod;
    }
    public void setCreateModelKeepExchExPeriod(int createModelKeepExchExPeriod) {
        this.createModelKeepExchExPeriod = createModelKeepExchExPeriod;
    }
    public int getCreateModelKeepExchExPeriod() {
        return createModelKeepExchExPeriod;
    }

    public ModelCopyOptions getModelCopyOptions() {
        ModelCopyOptions options = new ModelCopyOptions();

        options.setClearBpcs( this.isCreateModelClearBPCS() );
        options.setClearRevisions( this.isCreateModelClearRevDates() );
        options.setClearFreezeCost( this.isCreateModelClearFreezeCosts() );
        options.setKeepBpExPeriod( this.getCreateModelKeepBPExPeriod() );
        options.setKeepBpPeriod( this.getCreateModelKeepBpcsPeriod() );
        options.setKeepExchRateExPeriod( this.getCreateModelKeepExchExPeriod() );

        return options;

    }
    public void setCreateModelClearFreezeCosts(boolean createModelClearFreezeCosts) {
        this.createModelClearFreezeCosts = createModelClearFreezeCosts;
    }
    public boolean isCreateModelClearFreezeCosts() {
        return createModelClearFreezeCosts;
    }
    public void setCreateModelCycle(String createModelCycle) {
        this.createModelCycle = createModelCycle;
    }
    public String getCreateModelCycle() {
        return createModelCycle;
    }
    public void setCreateModelYear(String createModelYear) {
        this.createModelYear = createModelYear;
    }
    public String getCreateModelYear() {
        return createModelYear;
    }

    public CreateFactorModelForm() {
        super();
        this.setModelType(TCGMModel.Type.FACTOR);
    }
}