package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.entities.*;

import java.util.ArrayList;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface DataFeedLogDao extends TCGMDao {

    public ArrayList getAllEntities() throws TCGMException;
    public void writeLogEntry(DataFeedLogEntry entry) throws TCGMException;
    public void clearLog() throws TCGMException;
}