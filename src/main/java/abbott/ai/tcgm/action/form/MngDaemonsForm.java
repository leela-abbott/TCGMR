package abbott.ai.tcgm.action.form;


//import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class MngDaemonsForm extends TCGMForm {

    private String processSchedulerStatus;
    private String currentProcess;
    private String datafeedMonitorStatus;
    private String currentFeed;
    private String compactTime;
    public MngDaemonsForm() {
        super();
    }

    public void setProcessSchedulerStatus(String processSchedulerStatus) {
        this.processSchedulerStatus = processSchedulerStatus;
    }
    public String getProcessSchedulerStatus() {
        return processSchedulerStatus;
    }
    public void setCurrentProcess(String currentProcess) {
        this.currentProcess = currentProcess;
    }
    public String getCurrentProcess() {
        return currentProcess;
    }
    public void setDatafeedMonitorStatus(String datafeedMonitorStatus) {
        this.datafeedMonitorStatus = datafeedMonitorStatus;
    }
    public String getDatafeedMonitorStatus() {
        return datafeedMonitorStatus;
    }

    public void reset() {
        this.setCmd("");
    }
    public void setCurrentFeed(String currentFeed) {
        this.currentFeed = currentFeed;
    }
    public String getCurrentFeed() {
        return currentFeed;
    }





	/**
	 * @return Returns the compactTime.
	 */
	public String getCompactTime() {
		return compactTime;
	}
	/**
	 * @param compactTime The compactTime to set.
	 */
	public void setCompactTime(String compactTime) {
		this.compactTime = compactTime;
	}
}