package abbott.ai.tcgm.data;

/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public interface TCGMDao
{
    public void pushParameters( java.util.Properties props, String modelId, String datasetId) throws java.sql.SQLException, abbott.ai.tcgm.exception.TCGMException;
}