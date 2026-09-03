package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.TCGMException;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface EssbaseLoadDao extends TCGMDao {

    public void initiateRGMLoad(String month, String year, String version) throws TCGMException;
    public void initiateALOGLoad(String year, String version) throws TCGMException;
    public void initiateVersionCopy(String year, String version) throws TCGMException;
}