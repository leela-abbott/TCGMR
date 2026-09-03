package abbott.ai.tcgm.process.javajob;

import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ExportMngr;
import abbott.ai.tcgm.process.JobInstance;

/**
 * @author annampx
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JobSendPricingIPS implements JavaJob {
	public JobSendPricingIPS() {
	}

	public void perform(JobInstance jobInstance) throws TCGMException {
		perform(jobInstance.getModel());
	}

	public void perform(String fileName) throws TCGMException {
		new ExportMngr().exportPricingIPS(fileName);
	}

}
