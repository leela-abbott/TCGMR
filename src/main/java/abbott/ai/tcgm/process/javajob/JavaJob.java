package abbott.ai.tcgm.process.javajob;
//import java.util.Properties;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.process.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface JavaJob {
    public void perform(JobInstance jobInstance) throws TCGMException;
}