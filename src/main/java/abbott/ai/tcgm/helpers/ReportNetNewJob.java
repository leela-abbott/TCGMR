package abbott.ai.tcgm.helpers;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.AddOptions;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseClassArrayProp;
import com.cognos.developer.schemas.bibus._3.BooleanProp;
// REMOVED DEPRECATED: com.cognos.developer.schemas.bibus._3.CognosReportNetPortType
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.JobDefinition;
import com.cognos.developer.schemas.bibus._3.JobStepDefinition;
import com.cognos.developer.schemas.bibus._3.Nil;
import com.cognos.developer.schemas.bibus._3.NmtokenProp;
import com.cognos.developer.schemas.bibus._3.OptionArrayProp;
import com.cognos.developer.schemas.bibus._3.RunOptionArrayProp;
import com.cognos.developer.schemas.bibus._3.Schedule;
import com.cognos.developer.schemas.bibus._3.StringProp;
import com.cognos.developer.schemas.bibus._3.TokenProp;
import com.cognos.developer.schemas.bibus._3.UpdateActionEnum;

public class ReportNetNewJob {
	private BaseClass[] bca = null;
	private BaseClass bc = null;
	private static final Logger myLogger = LogManager.getLogger(ReportNetNewJob.class);

	/**
	 * This method sets the job schedule. We are using this to schedule to the job
	 * immediately and specifying to run it only once.
	 * 
	 * @param cmService   This Provides a Connection to Cognos Content Manager
	 *                    Service.
	 * @param name        The Job Name.
	 * @param packageName The package under which the job appears.
	 * @return BaseClass The BaseClass containing the job that is being created.
	 * @throws TCGMException
	 */
	public BaseClass addNewJob(ContentManagerService_PortType cmService, String name, String packageName)
			throws TCGMException {
		JobDefinition myJob = new JobDefinition();
		try {
			TokenProp jobDefName = new TokenProp();
			jobDefName.setValue(name);
			myJob.setDefaultName(jobDefName);
			bca = new BaseClass[] { myJob };

			BaseClass newBc = this.addObjectToCS(cmService, bca[0], "/content/package[@name='" + packageName + "']");
			if (newBc != null) {
				myLogger.debug("Job created successfully inside Content Store");
			}
			return newBc;
		} catch (Exception ex) {
			throw new TCGMException("ReportMngr", "printReportNetReport", "Job definition initialization failed",
					ex.getMessage());
		}
	}

	/**
	 * This method adds reports to the newly created job. When the job runs all the
	 * reports in the job will get executed.
	 * 
	 * @param cmService          This Provides a Connection to Cognos Content
	 *                           Manager Service.
	 * @param newJob             The newly created job.
	 * @param reports            The String array containing the reports that need
	 *                           to be added to the job.
	 * @param runOptionArrayProp The array containing the run options for each
	 *                           report.
	 * @throws TCGMException
	 */
	public void addReports(ContentManagerService_PortType cmService, JobDefinition newJob,
			   String[] reports, RunOptionArrayProp[] runOptionArrayProp) throws TCGMException 
{
try 
{
JobStepDefinition[] steps = new JobStepDefinition[reports.length];
for (int i = 0; i < reports.length; i++) 
{
	BaseClassArrayProp bcap = new BaseClassArrayProp();
	steps[i] = new JobStepDefinition();
	Nil temp = new Nil();
	StringProp searchPath = new StringProp();
	searchPath.setValue(reports[i]);
	temp.setSearchPath(searchPath);
	bcap.setValue(new BaseClass[] { temp });
	steps[i].setStepObject(bcap);
	
	// FIXED: Convert RunOptionArrayProp to OptionArrayProp to satisfy Cognos 12 layout
	OptionArrayProp stepOptions = new OptionArrayProp();
	if (runOptionArrayProp[i] != null && runOptionArrayProp[i].getValue() != null) {
		stepOptions.setValue(runOptionArrayProp[i].getValue());
	}
	steps[i].setOptions(stepOptions);
	
	// Adds each individual definition step to the target Job node path
	bc = this.addObjectToCS(cmService, steps[i], newJob.getSearchPath().getValue());
}
if (bc != null)
{
	myLogger.debug("Reports added successfully as steps into Job definition");
}
else
{
	myLogger.debug("Reports not added to Job");
}
} 
catch (Exception e) 
{
throw new TCGMException("ReportNetNewJob", "addReports", e.getMessage());
}
}


	/**
	 * This method sets the schedule for the job.
	 * 
	 * @param cmService This Provides a Connection to Cognos Content Manager
	 *                  Service.
	 * @param newJob    The newly created job.
	 * @throws TCGMException
	 */
	public void setScheduleByDay(ContentManagerService_PortType cmService, JobDefinition newJob) throws TCGMException {
		try {
			Schedule sch = new Schedule();
			StringProp sPath = new StringProp();
			sPath.setValue(newJob.getSearchPath().getValue() + "/schedule");
			sch.setSearchPath(sPath);
			BooleanProp disabled = new BooleanProp();
			disabled.setValue(false);

			ReportNetNewScheduler mySchedule = new ReportNetNewScheduler();
			mySchedule.setDisabled(disabled);
			mySchedule.setSearchPath(sch.getSearchPath());
			Calendar end = new GregorianCalendar();
			end.add(Calendar.DATE, 0);

			// FIXED: Removed the invalid (CognosReportNetPortType) cast.
			// Note: Your ReportNetNewScheduler method signature will also need to accept
			// ContentManagerService_PortType
			mySchedule.setSchedule(cmService, newJob.getSearchPath().getValue(), "hour", "8", end, "onDate");

			bca = new BaseClass[] { newJob };
			if (updateObjectInCS(cmService, bca) != null) {
				myLogger.debug("Schedule added successfully");
			} else {
				myLogger.debug("Schedule not added");
			}
		} catch (Exception ex) {
			throw new TCGMException("ReportNetNewJob", "setScheduleByDay", ex.getMessage());
		}
	}

	/**
	 * This method sets the sequence in which the reports in the job run.
	 * 
	 * @param cmService  This Provides a Connection to Cognos Content Manager
	 *                   Service.
	 * @param newJob     The newly created job.
	 * @param sequencing The Sequencing to run (Parallel or Sequential mode)
	 * @throws TCGMException
	 */
	public void setSequence(ContentManagerService_PortType cmService, JobDefinition newJob, String sequencing)
			throws TCGMException {
		try {
			NmtokenProp seq = new NmtokenProp();
			seq.setValue(sequencing);
			newJob.setSequencing(seq);
			bca = new BaseClass[] { newJob };
			if (updateObjectInCS(cmService, bca) != null) {
				myLogger.debug("Sequence set successfully");
			} else {
				myLogger.debug("Sequence not set");
			}
		} catch (Exception ex) {
			throw new TCGMException("ReportNetNewJob", "setSequence", ex.getMessage());
		}
	}

	/**
	 * This method enables the job. This will schedule the job to run immediately.
	 * 
	 * @param cmService This Provides a Connection to Cognos Content Manager
	 *                  Service.
	 * @param newJob    The newly created job.
	 * @throws TCGMException
	 */
	public void enableJob(ContentManagerService_PortType cmService, JobDefinition newJob) throws TCGMException {
		try {
			BooleanProp disabled = new BooleanProp();
			disabled.setValue(false);
			newJob.setDisabled(disabled);
			bca = new BaseClass[] { newJob };
			if (updateObjectInCS(cmService, bca) != null) {
				myLogger.debug("Job enabled successfully");
			}
		} catch (Exception ex) {
			throw new TCGMException("ReportNetNewJob", "enableJob", ex.getMessage());
		}
	}

	/**
	 * This method appends or replaces metadata definitions into Content Store.
	 * 
	 * @param cmService This Provides a Connection to Cognos Content Manager
	 *                  Service.
	 * @param bcp       The BaseClass payload
	 * @param path      The path where the job is located in the contentStore.
	 * @throws Exception
	 */
	public BaseClass addObjectToCS(ContentManagerService_PortType cmService, BaseClass bcp, String path)
			throws Exception {
		AddOptions ao = new AddOptions();
		ao.setUpdateAction(UpdateActionEnum.replace);

		// FIXED: Wrapped plain target String path parameter inside
		// SearchPathSingleObject type wrapper
		return cmService.add(new SearchPathSingleObject(path), new BaseClass[] { bcp }, ao)[0];
	}

	/**
	 * This method will update the job object in the Content Store.
	 * 
	 * @param cmService This Provides a Connection to Cognos Content Manager
	 *                  Service.
	 * @param bcp       The BaseClass properties payload array
	 * @throws Exception
	 */
	public BaseClass[] updateObjectInCS(ContentManagerService_PortType cmService, BaseClass[] bcp) throws Exception {
		return cmService.update(bcp, new com.cognos.developer.schemas.bibus._3.UpdateOptions());
	}
}
