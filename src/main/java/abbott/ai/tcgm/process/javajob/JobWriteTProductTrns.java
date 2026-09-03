package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
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

public class JobWriteTProductTrns implements JavaJob {
	
	public JobWriteTProductTrns()  { }

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms(); 

		String tProductFileName = (String) parms.get( JobConstants.PN_T_PRODUCT_FILENAME);
		//String AffFileName = (String) parms.get( JobConstants.PN_AFF_FILENAME);
		String modelId = jobInstance.getModelId();
		UserToken ut = AppConst.getInstance().getJobId();

		JobConstants.ESSBASE_EXP_DIR = AppConst.getInstance().essbaseExportDir;
		ProcessMngr pm = new ProcessMngr();

		
			pm.executeJobStep("WRITE_T_PRODUCT_TRNS", modelId, "-1", ut, parms);
			JobConstants.ESSBASE_FILE_NAME = tProductFileName;
		
		new EssbaseMngr().exportEssbaseTProduct(modelId);
		
		
		/*	pm.executeJobStep("WRITE_AFF_TRNS", modelId, "-1", ut, parms);
			JobConstants.ESSBASE_FILE_NAME = AffFileName;
			
		new EssbaseMngr().exportEssbaseAffiliate(modelId);
		*/
		
		

	}
}