package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ProcessMngr;
import abbott.ai.tcgm.process.JobInstance;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobPublishNetCostRpt implements JavaJob {

	public JobPublishNetCostRpt()  {
	}

	public void perform(JobInstance jobInstance) throws TCGMException 
	{
		Properties parms = jobInstance.getJobParms();

		/*******************************************************************************
		  * Modified By : Udaya B Aravapalli
		  * Modified On : 01/20/2006
		  * Description : This code will do the following functionality
		  *               a.Execute the Stored Proc SEND_FACRPT   
		  *               b.Execute the Stored Proc CPY2_NETCST_PUB.
		  ********************************************************************************/
		 String modelId = jobInstance.getModelId();
		 UserToken ut = AppConst.getInstance().getJobId();

		 ProcessMngr pm = new ProcessMngr();		
		 pm.executeJobStep("SEND_PRICING", modelId, "-1", ut, parms); 		// Execute Stored Proc: SEND_PRICING
		 pm.executeJobStep("CPY2_NETCST_PUB", modelId, "-1", ut, parms);    // Execute Stored Proc: CPY2_NETCST_PUB
	 }
 }