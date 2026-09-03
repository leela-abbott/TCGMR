package abbott.ai.tcgm.action.form;


import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class ProcessForm extends TCGMForm {

    private Vector processList;
    private Vector completedJobList;
    private String processSchedulerStatus;
    private String currentProcess;
    private String selProcessId;
	private String selJodDesc;
		private String selJodStatus;	
    private int failedJobListSize;
	private Vector logList;
	private Vector errorList;

    public ProcessForm() {
        super();
    }

    public int getProcessListSize() {
        if (processList != null)
            return processList.size();
        else
            return 0;
    }
    public Vector getProcessList() {
        return processList;
    }
    public void setProcessList(Vector processList) {
        this.processList = processList;
    }
    public void setProcessSchedulerStatus(String processSchedulerStatus) {
        this.processSchedulerStatus = processSchedulerStatus;
    }
    public String getProcessSchedulerStatus() {
        return processSchedulerStatus;
    }
    public Vector getCompletedJobList() {
        return completedJobList;
    }
    public int getCompletedJobListSize() {
        if (completedJobList != null)
            return completedJobList.size();
        else
            return 0;
    }
    public void setCompletedJobList(Vector completedJobList) {
        this.completedJobList = completedJobList;
    }
    public void setCurrentProcess(String currentProcess) {
        this.currentProcess = currentProcess;
    }
    public String getCurrentProcess() {
        return currentProcess;
    }
    public String getSelProcessId() {
        return selProcessId;
    }
    public void setSelProcessId(String selProcessId) {
        this.selProcessId = selProcessId;
    }

	public Vector getLogList() {
		return this.logList;
	}
		
	public void setLogList(Vector logList) {
		this.logList = logList;
	} 
		
	public Vector getErrorList() {
		return this.errorList;
	}
		
	public void setErrorList(Vector errorList) {
		this.errorList = errorList;
	} 
	
	/**
		 * Job Desc for the Log  Report Page
		 * @return
		 */
		public String getSelJodDesc() {
			return selJodDesc;
		}

		/**
		 * Job Stataus for the Log  Report Page
		 * @return
		 */
		public String getSelJodStatus() {
			return selJodStatus;
		}

		/**
		 *  Setting Job Desc for the Log  Report Page
		 * @param string
		 */
		public void setSelJodDesc(String selJodDesc) {
			this.selJodDesc = selJodDesc;
		}

		/**
		 * Setting Job Stataus for the LOg  Report Page
		 * @param string
		 */
		public void setSelJodStatus(String selJodStatus) {
			this.selJodStatus = selJodStatus;
		}

}