package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobSendFactorReport implements JavaJob {

	// This job expects 1 non standard parameter:
	// The R System to send to factors to.

	public JobSendFactorReport()  {
	}

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms();
//		if (parms.size() != 2)
//			throw new TCGMInvalidJobParmException( jobInstance.toShortString() );

		String restrictions = parms.getProperty(JobConstants.PN_RESTRICTIONS);
		String modelType = (String) parms.get( JobConstants.PN_MODEL_TYPE );
		String modelId = jobInstance.getModelId();

		// Get job id to run job under
		UserToken ut = AppConst.getInstance().getJobId();

		// Run the Procedure to produce my Factor Output Files
		ProcessMngr pm = new ProcessMngr();
		pm.executeJobStep("SEND_FACRPT", modelId, "-1", ut, parms);

// 9-3-03 Restrictions will not be implemented from UI
//		new ExportMngr().exportFactorReport(modelId, restrictions);
		new ExportMngr().exportFactorReport(modelId, modelType);
	}
}