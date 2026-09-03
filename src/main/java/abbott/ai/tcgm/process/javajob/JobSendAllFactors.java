package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.AppConst;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobSendAllFactors implements JavaJob {

	// This job expects 1 non standard parameter:
	// The R System to send to factors to.

	public JobSendAllFactors()  {  }

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms();
		if (parms.size() != 1)
			throw new TCGMInvalidJobParmException( jobInstance.toShortString() );

		String rbbSystem = (String) parms.get( JobConstants.PN_R_SYSTEM_RBB );
		String restrictions = parms.getProperty(JobConstants.PN_RESTRICTIONS);
		String modelId = jobInstance.getModelId();

		// Get job id to run job under
		UserToken ut = AppConst.getInstance().getJobId();
		ProcessMngr pm = new ProcessMngr();

		//new ProcessMngr().executeJobStep("R_SLCT_FAC", modelId, jobInstance.getDatasetTableId(), AppConst.getInstance().getJobId(), parms);
		// Job Step 1 for jobId 88
		pm.executeJobStep("SLCT_R_TRNS", modelId, jobInstance.getDatasetTableId(), ut, parms);
		// Job Step 2 for jobId 88
		pm.executeJobStep("CREATE_R_TRN", modelId, jobInstance.getDatasetTableId(), ut, parms);

// Restrictions will not be implemented from the UI
//		new ExportMngr().exportFactors(RSystem.getObjFromName(rSystem), modelId, restrictions );
		new ExportMngr().exportFactors(RSystem.getObjFromName(rbbSystem), modelId);

	}
}