package abbott.ai.tcgm.process.javajob;

import java.util.Properties;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobSendSalesExtSmry implements JavaJob {

    public JobSendSalesExtSmry()  {    }

    public void perform(JobInstance jobInstance) throws TCGMException {

        /* As of 10-5-05, this java job had not been fully implemented.
         * From what we can deduce, job may first need to execute oracle procedure (ANL_XTSM_400 ??? or some other name. 
         * Even the procedure name is in question. Then transfer the data. To where,
         * no one knows.
         * 
         * Refer to JobSendAnlFexEss as a guide for implementation
        */
        
        // Get a parms reference so we can push any parameters to the global parm pool later
		Properties parms = jobInstance.getJobParms(); 

		if (parms.size() != 4)
			throw new TCGMInvalidJobParmException( jobInstance.toShortString() );

		/* Determine what the parms should be for Sales Ext Smry & add them here
		String year = (String) parms.get(JobConstants.PN_ESSBASE_YEAR);
		String version = (String) parms.get( JobConstants.PN_ESSBASE_VERSION );
		String atype = (String) parms.get( JobConstants.PN_ESSBASE_TYPE );
		*/
		String modelId = jobInstance.getModelId();

		// Get job id to run job under
		UserToken ut = AppConst.getInstance().getJobId();

		// executeJobStep() is used in java jobs only when you are calling a procedure
		// it pushes parms to the global pool using pushParameters() as well as executes the procedure
		// Manually run job step to calculate essbase analysis
		ProcessMngr pm = new ProcessMngr();
		pm.executeJobStep("ANL_XTSM_400", modelId, "-1", ut, parms);

		// 10-5-05 At this point, not sure if I am exporting to MVS or exporting to Essbase
		//new EssbaseMngr().exportEssbaseAnalysis(modelId, year, version, atype);
        
    }
}