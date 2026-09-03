package abbott.ai.tcgm.process.javajob;

import java.util.Properties;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobPrintReport implements JavaJob {

	public JobPrintReport()  {
	}

	public void perform(JobInstance jobInstance) throws TCGMException {
		Properties parms = jobInstance.getJobParms();

		ReportMngr rm = new ReportMngr();

		String jobQId = (String) parms.get( JobConstants.PN_JOBQUE_ID );
		String reportId = (String) parms.get( JobConstants.PN_REPORT_ID );
		String reportDest = (String) parms.get(JobConstants.PN_REPORT_DEST);
		String reportCopies = (String) parms.get(JobConstants.PN_REPORT_COPIES);
// 9-3-03 Restrictions will not be implemented from the UI
//        String restrictions = (String) parms.get(JobConstants.PN_RESTRICTIONS);

		ReportPrintRequest rpr = new ReportPrintRequest();
		rpr.setReportId(reportId);
		rpr.setJobQueId(jobQId);
		rpr.setReportDest(reportDest);
		rpr.setNumCopies(reportCopies);
// 9-3-03 Restrictions will not be implemented from the UI
//        rpr.setRestriction(restrictions);

		rm.printReport(rpr);
	}
}