package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.process.javajob.JavaJob;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobLoadEssbaseRGM implements JavaJob {
    public static final String pn_YEAR = "RGM_YEAR";
    public static final String pn_MONTH = "RGM_MONTH";
    public static final String pn_VERSION = "RGM_VERSION";

    public JobLoadEssbaseRGM()  {
    }

    public void perform(JobInstance jobInstance) throws TCGMException {
        Properties parms = jobInstance.getJobParms();
        if (parms.size() != 3)
            throw new TCGMInvalidJobParmException( this.toString() );

        EssbaseMngr em = new EssbaseMngr();

        String year = (String) parms.get( this.pn_YEAR );
        String month = (String) parms.get(this.pn_MONTH);
        String version = (String) parms.get(this.pn_VERSION);
        String loadTyp = "";

        em.intiateRGMLoad(year, month, version,loadTyp);
    }
}