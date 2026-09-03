package abbott.ai.tcgm.action.form;
import java.util.ArrayList;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
import org.apache.struts.action.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

public class MngDataFeedLogForm extends TCGMForm  {
    ArrayList logentrylist = new ArrayList(20);
    private static Logger logger = Logger.getLogger("abbott.ai.tcgm.action.form.MngDataFeedLogForm");

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