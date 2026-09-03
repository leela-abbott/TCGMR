package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobSendFactors implements JavaJob {

	// This job expects 1 non standard parameter:
	// The R System to send to factors to.

	public JobSendFactors()  {
	}

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms();
		if (parms.size() != 1)
			throw new TCGMInvalidJobParmException( jobInstance.toShortString() );

		String rbbSystem = (String) parms.get( JobConstants.PN_R_SYSTEM_RBB );
// 9-3-03 Restrictions will not be implemented from the UI
//		String restrictions = parms.getProperty(JobConstants.PN_RESTRICTIONS);
		String modelId = jobInstance.getModelId();

// 9-3-03 Restrictions will not be implemented from the UI
//		new ExportMngr().exportFactors(RSystem.getObjFromName(rSystem), modelId, restrictions );
		new ExportMngr().exportFactors(RSystem.getObjFromName(rbbSystem), modelId);
	}
}