/*
 * Created on Jun 12, 2007
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.*;

/**
 * @author pesalvk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JobWriteAffTrns implements JavaJob {
	
	public JobWriteAffTrns()  { }

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms(); 

		String AffFileName = (String) parms.get( JobConstants.PN_AFF_FILENAME);
		String modelId = jobInstance.getModelId();
		UserToken ut = AppConst.getInstance().getJobId();

		JobConstants.ESSBASE_EXP_DIR = AppConst.getInstance().essbaseExportDir;
		ProcessMngr pm = new ProcessMngr();

			pm.executeJobStep("WRITE_AFF_TRNS", modelId, "-1", ut, parms);
			JobConstants.ESSBASE_FILE_NAME = AffFileName;
			
		new EssbaseMngr().exportEssbaseAffiliate(modelId);

	}
}