package abbott.ai.tcgm.entities;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class ModelCopyOptions {

    private boolean clearBpcs;
    private boolean clearRevisions;
    private int keepBpPeriod;
    private int keepBpExPeriod;
    private int keepExchRateExPeriod;
    private boolean clearFreezeCost;
    public ModelCopyOptions() {
    }
    public boolean isClearBpcs() {
        return clearBpcs;
    }
    public void setClearBpcs(boolean clearBpcs) {
        this.clearBpcs = clearBpcs;
    }
    public void setClearRevisions(boolean clearRevisions) {
        this.clearRevisions = clearRevisions;
    }
    public boolean isClearRevisions() {
        return clearRevisions;
    }
    public void setKeepBpPeriod(int keepBpPeriod) {
        this.keepBpPeriod = keepBpPeriod;
    }
    public int getKeepBpPeriod() {
        return keepBpPeriod;
    }
    public void setKeepBpExPeriod(int keepBpExPeriod) {
        this.keepBpExPeriod = keepBpExPeriod;
    }
    public int getKeepBpExPeriod() {
        return keepBpExPeriod;
    }
    public void setKeepExchRateExPeriod(int keepExchRateExPeriod) {
        this.keepExchRateExPeriod = keepExchRateExPeriod;
    }
    public int getKeepExchRateExPeriod() {
        return keepExchRateExPeriod;
    }
    public void setClearFreezeCost(boolean clearFreezeCost) {
        this.clearFreezeCost = clearFreezeCost;
    }
    public boolean isClearFreezeCost() {
        return clearFreezeCost;
    }
}