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

public class JobSendAnlFlexEss implements JavaJob {
	public static final String pn_ANALYSIS_UNITS_ID = "ANAL_UNITS";

	public JobSendAnlFlexEss()  { }

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms(); // 7-17-03 bd; Jim was cheating here; he added the parms to the jobInstance (ta's OK);
// 7-17-03 now he is retrieving the parms back from that instance; i.e., he doesn't get them from the global pool
// 7-17-03 the difference simply is that on a java job, they never get pushed to the global pool
// 7-17-03 this is only a problem if a java job calls a procedure who needs the parm from the global pool
		// 7-17-03 bd; the procedure used here needs a parm from this job; Is it a problem?	See below; Jim adds the parms on the global pool
		//if (parms.size() != 4)
		//	throw new TCGMInvalidJobParmException( jobInstance.toShortString() );

		// Set up parms to push to the global parm table here
		String year = (String) parms.get(JobConstants.PN_ESSBASE_YEAR);
		String version = (String) parms.get( JobConstants.PN_ESSBASE_VERSION );
		String atype = (String) parms.get( JobConstants.PN_ESSBASE_TYPE );
		String modelId = jobInstance.getModelId();

		// Get job id to run job under
		UserToken ut = AppConst.getInstance().getJobId();

// 7-17-03 bd; executeJobStep() is used in java jobs only when you are calling a procedure
// 7-17-03 bd; it pushes parms to the global pool using pushParameters() as well as executes the procedure
		// Manually run job step to calculate essbase analysis
		JobConstants.ESSBASE_EXP_DIR = AppConst.getInstance().essbaseExportDir;
		ProcessMngr pm = new ProcessMngr();
		if( (atype.equals(JobConstants.PN_ESSBASE_FLEX_1)) || (atype.equals(JobConstants.PN_ESSBASE_FLEX_2)) )
		{
			//pm.executeJobStep("ESS_FLXMRGN", modelId, "-1", ut, parms);
			pm.executeNewJobStep("ESS_FLXMRGN", modelId, "-1", ut, parms);
			JobConstants.ESSBASE_FILE_NAME = AppConst.getInstance().file_ESS_FLEX;
		}
		else if( (atype.equals(JobConstants.PN_ESSBASE_ANALYSIS_2)) || (atype.equals(JobConstants.PN_ESSBASE_ANALYSIS_3)) )
		{
			pm.executeJobStep("ESS_ANLSUM", modelId, "-1", ut, parms);
			JobConstants.ESSBASE_FILE_NAME = AppConst.getInstance().file_ESS_ANLA2A3;
		}
		else if((atype.startsWith(JobConstants.PN_ESSBASE_ANALYSIS_1)) || (atype.equals(JobConstants.PN_ESSBASE_ANALYSIS_4)))
		{
			pm.executeJobStep("ESS_ANLSUMON", modelId, "-1", ut, parms);
			JobConstants.ESSBASE_FILE_NAME = AppConst.getInstance().file_ESS_ANLA1A4;
		}		

		new EssbaseMngr().exportEssbaseAnalysis(modelId, year, version, atype);

	}
}