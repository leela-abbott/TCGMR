package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.TCGMException;
//import abbott.ai.tcgm.entities.*;

import java.util.ArrayList;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface DataFeedDao extends TCGMDao {

    public ArrayList getAllDataFeeds() throws TCGMException;
}