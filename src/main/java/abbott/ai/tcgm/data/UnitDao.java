//Source file: C:\\DATA\\jbproject\\TCGMWeb\\src\\abbott\\ai\\tcgm\\data\\MessageDao.java

package abbott.ai.tcgm.data;

//import abbott.ai.tcgm.entities.UserToken;
import java.util.Vector;
//import java.io.File;
import abbott.ai.tcgm.exception.TCGMException;


/**
<p>Title: TCGM Application</p>
<p>Description: </p>
<p>Copyright: Copyright (c) 2002</p>
<p>Company: Abbott International</p>
@author Jim Watkins
@version 1.0
 */
public interface UnitDao extends TCGMDao
{

    public Vector getAllUnitSets() throws TCGMException;
    public void deleteUnitSet(String unitId) throws TCGMException;

}
