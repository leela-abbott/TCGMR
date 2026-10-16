package abbott.ai.tcgm17.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionContext;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.DataFeedLogDao;
import abbott.ai.tcgm.data.SQLUtil;

public class MngDataFeedLogAction extends TCGMAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MngDataFeedLogAction.class);

	private String cmd = "";
	private List<DataFeedLogEntry> logentrylist = new ArrayList<>(20);
	protected String forward = TCGMConstants.FORWARD_SUCCESS;

	public MngDataFeedLogAction() {
		super();
	}

	@Override
	public String execute() {
		try {
			if (this.isSessionValid()) {
				processCmd();
				populateLogEntries();
			}
		} catch (TCGMException ex) {
			ActionContext.getContext().put(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.forward = TCGMConstants.G_FORWARD_EXCEPTION;
		}
		logger.debug("Forward to: {}", this.forward);
		return this.forward;
	}

	private void processCmd() throws TCGMException {
		if ("DELETE_ALL".equalsIgnoreCase(this.cmd)) {
			new DataFeedMngr().clearDataFeedLog();
			this.cmd = "";
		}
	}

	private void populateLogEntries() {
		try {
			DataFeedLogDao logdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE)
					.getDataFeedLogDao(SQLUtil.getOracleAdmin());
			@SuppressWarnings("unchecked")
			List<DataFeedLogEntry> entries = (List<DataFeedLogEntry>) logdao.getAllEntities();
			this.logentrylist = entries != null ? entries : new ArrayList<>();
		} catch (TCGMException tex) {
			logger.error("Error while populating log entries", tex);
		}
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@StrutsParameter(depth = 1)
	public List<DataFeedLogEntry> getLogentrylist() {
		return logentrylist;
	}

	public void setLogentrylist(List<DataFeedLogEntry> logentrylist) {
		this.logentrylist = logentrylist;
	}

	public DataFeedLogEntry getLogEntryList(int index) {
		if (index >= 0 && index < this.logentrylist.size()) {
			return this.logentrylist.get(index);
		}
		return null;
	}

	public void setLogEntryList(int index, DataFeedLogEntry entry) {
		while (this.logentrylist.size() <= index) {
			this.logentrylist.add(null);
		}
		this.logentrylist.set(index, entry);
	}
}
