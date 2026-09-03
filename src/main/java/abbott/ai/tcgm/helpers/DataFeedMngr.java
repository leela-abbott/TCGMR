

package abbott.ai.tcgm.helpers;

import java.util.*;
//import java.io.*;
//import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.data.oracle.*;
//import abbott.ai.tcgm.process.*;
//import abbott.ai.tcgm.*;


public class DataFeedMngr implements TCGMMngr
{

    protected String name = this.getClass().getName();

    /**
   @roseuid 3D4FDE8D0314
   */
    public DataFeedMngr() {   }

  public ArrayList getAllDataFeeds() throws TCGMException {
        DataFeedDao dfd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDataFeedDao( SQLUtil.getOracleAdmin() );
        return dfd.getAllDataFeeds();
  }
  
  public ArrayList getAllDataFeedLogEntries() throws TCGMException {
        DataFeedLogDao logdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDataFeedLogDao( SQLUtil.getOracleAdmin() );
        return logdao.getAllEntities();
  }

  public void clearDataFeedLog() throws TCGMException {
        DataFeedLogDao logdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDataFeedLogDao( SQLUtil.getOracleAdmin() );
        logdao.clearLog();
  }

  public void writeLogEntry(DataFeedLogEntry entry) throws TCGMException {
        DataFeedLogDao logdao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDataFeedLogDao( SQLUtil.getOracleAdmin() );
        logdao.writeLogEntry(entry);
  }
  
}