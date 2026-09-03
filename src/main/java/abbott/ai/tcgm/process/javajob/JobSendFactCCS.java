/*
 * Created on Aug 21, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.process.javajob;

import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ExportMngr;
import abbott.ai.tcgm.process.JobInstance;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JobSendFactCCS implements JavaJob{
	public JobSendFactCCS()  {    }

	   public void perform(JobInstance jobInstance) throws TCGMException {
		   perform(jobInstance.getModel());
	   }

	   public void perform(String fileName) throws TCGMException {
		   new ExportMngr().exportFactCCS(fileName);
	   }

}
