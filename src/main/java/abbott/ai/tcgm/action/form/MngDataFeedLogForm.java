package abbott.ai.tcgm.action.form;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.DataFeedLogDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.DataFeedLogEntry;
import abbott.ai.tcgm.exception.TCGMException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

public class MngDataFeedLogForm extends TCGMForm  {
    ArrayList logentrylist = new ArrayList(20);
    private static final Logger logger = LogManager.getLogger("abbott.ai.tcgm.action.form.MngDataFeedLogForm");

    public MngDataFeedLogForm() {
        this.reset();
    }

    public ArrayList getLogentrylist() {
        return logentrylist;
    }

    public DataFeedLogEntry getLogEntryList(int index) {
        DataFeedLogEntry entry = null;
        if(index >= 0 && index < this.getLogentrylist().size())
        {
            entry = (DataFeedLogEntry)this.getLogentrylist().get(index);
        }
        return entry;
    }

    public void setLogentrylist(ArrayList newLogEntryList) {
        logentrylist = newLogEntryList;
    }

    public void setLogEntryList(int index, DataFeedLogEntry entry) {
        this.getLogentrylist().set(index, entry);
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        reset();
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        super.reset(mapping, request);
        reset();
    }

    public void reset() {
        try {
            super.reset();
            DataFeedLogDao logdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDataFeedLogDao( SQLUtil.getOracleAdmin() );
            this.logentrylist = logdao.getAllEntities();
        }
        catch (TCGMException tex) {
            logger.error("Error while resetting form", tex);
        }
    }
}