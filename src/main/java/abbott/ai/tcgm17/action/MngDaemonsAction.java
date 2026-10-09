package abbott.ai.tcgm17.action;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.process.DaemonMngr;
import abbott.ai.tcgm.process.ProcessScheduler;

public class MngDaemonsAction extends TCGMAction implements ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(MngDaemonsAction.class);

    private HttpServletRequest request;

    private String cmd = "";
    private String processSchedulerStatus;
    private String currentProcess;
    private String datafeedMonitorStatus;
    private String currentFeed;
    private String compactTime;

    public MngDaemonsAction() {
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String execute() {
        try {
            if (isSessionValid(request)) {
                if (cmd != null && !cmd.trim().isEmpty()) {
                    System.out.println(compactTime);
                    processCmd(cmd, compactTime);
                }
                setupFormState();
                return SUCCESS;
            }
            return "selectModel";
        } catch (TCGMException ex) {
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            return "exception";
        }
    }

    private void processCmd(String command, String timeValue) throws TCGMException {
        DaemonMngr dm = DaemonMngr.getInstance();

        if ("START_SCHEDULER".equals(command)) {
            dm.startProcessScheduler();
            dm.startProcessSchedulerMonitor();
        } else if ("STOP_SCHEDULER".equals(command)) {
            dm.stopProcessScheduler();
            dm.stopProcessSchedulerMonitor();
        } else if ("APPLY_ADJUSTMENT".equalsIgnoreCase(command)) {
            logger.debug("User Selected System Compact Time: " + timeValue);
            ProcessScheduler.setBATCH_START(Integer.parseInt(timeValue));
        } else if ("START_MONITOR".equals(command)) {
            dm.startDataFeedMonitor();
        } else if ("STOP_MONITOR".equals(command)) {
            dm.stopDataFeedMonitor();
        } else {
            throw new TCGMException("MngDaemonsAction", "processCmd(cmd)", "Cmd: " + command, "Invalid Command");
        }
    }

    private void setupFormState() throws TCGMException {
        ModelMngr mm = new ModelMngr();
        UserToken ut = this.getUserToken(request);

        DaemonMngr dm = DaemonMngr.getInstance();

        this.setDatafeedMonitorStatus(dm.isDatafeedMonitorRunning() ? "Running" : "Stopped");
        this.setProcessSchedulerStatus(dm.isProcessSchedulerRunning() ? "Running" : "Stopped");
        this.setCurrentProcess(dm.getCurrentProcess());
        this.setCurrentFeed(dm.getCurrentFeed());
    }


    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    @StrutsParameter
    public void setCompactTime(String compactTime) {
        this.compactTime = compactTime;
    }

    public String getCompactTime() {
        return compactTime;
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

    public void setCurrentFeed(String currentFeed) {
        this.currentFeed = currentFeed;
    }

    public String getCurrentFeed() {
        return currentFeed;
    }
    
    public int getBatchStart() {
        return ProcessScheduler.BATCH_START;
    }

}
