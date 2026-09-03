package abbott.ai.tcgm.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.*;

import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MngReportJobs extends TCGMAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response) throws IOException, ServletException {
		try {
			if (this.isSessionValid(request)) {

				MngReportJobsForm myForm = null;
				myForm = (MngReportJobsForm) form;
				this.errors.clear();

				if ( isCmdValid(myForm) )  {
					// check for restriction still to be entered.
					if ( myForm.isChkRestrict() && !myForm.isRestrictionEntered() ) {
						// also place form object under common restriction attribute for retrieval and population by restriction jsp
						// these changed should be present under the specific form attribute.
						request.getSession().setAttribute("restrictionForm", myForm);
						this.setForward(TCGMConstants.G_FORWARD_RESTRICTIONS);
					}
					else {
						// process specific command
						processCmd(myForm, request);
						myForm.reset();
						this.setupForm(request);
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
				}
				else {
					this.setupForm(request);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
			}
		}
		catch (TCGMException ex) {
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}

		this.logger.debug(this.className + " - Forward to " + this.getForward());
		return mapping.findForward(this.getForward());
	}

	private void processCmd(MngReportJobsForm myForm, HttpServletRequest request ) throws TCGMException {

		// process specific command
		UserToken ut = this.getUserToken(request);
		String cmd = myForm.getCmd();
		if ( cmd.equalsIgnoreCase("DELETE") ) {
			// Break up selected report into two part key.
			String compKey = myForm.getSelReport();
			StringTokenizer st = new StringTokenizer(compKey, "-");
			String jobId = st.nextToken();
			String reportId = st.nextToken();

			new ReportMngr().deleteReportInstanceById(reportId, jobId, ut);
		}
		else if (cmd.equalsIgnoreCase("PRINT") ) {
			// Break up selected report into two part key.
			String compKey = myForm.getSelReport();
			StringTokenizer st = new StringTokenizer(compKey, "-");
			String jobId = st.nextToken();
			String reportId = st.nextToken();

			ReportInstance report = new ReportMngr().getReportInstanceById(reportId, jobId, ut);

			// Kind of a special job...hand roll it.
			JobInstance job = new JobInstance(JobDefinition.PRINT_REPORT);
			job.setPrintOutput(true);
			job.addJobParm( JobConstants.PN_REPORT_COPIES, myForm.getNumCopies() );
			job.addJobParm( JobConstants.PN_REPORT_DEST, myForm.getSelReportDest() );
			job.addJobParm( JobConstants.PN_REPORT_ID, reportId );
			job.addJobParm( JobConstants.PN_JOBQUE_ID, jobId );
// 9-3-03 Restrictions will not be implemented from UI
//			if (!myForm.getRestrictionStr().equals("") )
//				job.addJobParm( JobConstants.PN_RESTRICTIONS, myForm.getRestrictionStr() );

			job.setModelId( report.getJobInstance().getModelId() );
			job.setDatasetTableId( report.getDatasetId() );

			if (myForm.isChkImmediate())
				job.setJobStatus(JobInstance.JobStatus.Immediate);
			else
				job.setJobStatus(JobInstance.JobStatus.Batch);

			job.setDesc("PRINT " + report.getReportInstanceName() );
			new ProcessMngr().addJob(job, ut);
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName())); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}

	}

	private void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);

		ReportMngr rm = new ReportMngr();
		MngReportJobsForm myForm = new MngReportJobsForm();
		
		//8/20/2005 Sridevi.K code commented for putting a link to reportNet
        //myForm.setReportList( rm.getReportInstances( ut ) );
		// Attach beans to attributes
		request.getSession().setAttribute("mngReportJobsForm", myForm);
	}


	public MngReportJobs() {
		super();
	}

}