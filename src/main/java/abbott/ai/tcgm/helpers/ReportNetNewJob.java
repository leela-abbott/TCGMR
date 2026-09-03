package abbott.ai.tcgm.helpers;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.AddOptions;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseClassArrayProp;
import com.cognos.developer.schemas.bibus._3.BooleanProp;
import com.cognos.developer.schemas.bibus._3.CognosReportNetPortType;
import com.cognos.developer.schemas.bibus._3.JobDefinition;
import com.cognos.developer.schemas.bibus._3.JobStepDefinition;
import com.cognos.developer.schemas.bibus._3.Nil;
import com.cognos.developer.schemas.bibus._3.NmtokenProp;
import com.cognos.developer.schemas.bibus._3.RunOptionArrayProp;
import com.cognos.developer.schemas.bibus._3.Schedule;
import com.cognos.developer.schemas.bibus._3.StringProp;
import com.cognos.developer.schemas.bibus._3.TokenProp;
import com.cognos.developer.schemas.bibus._3.UpdateActionEnum;

public class ReportNetNewJob {
	private BaseClass[] bca = null;
	private BaseClass bc = null;
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.helpers.ReportNetNewJob" );

	/**
	 * This method sets the job schedule. We are using this to schedule to
	 * the job immediately and specifying to run it only once.In the future
	 * we can take advantage of the existing code to schedule it in different ways.
	 * 
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param name       The Job Name (We are trying to establish a visual relation ship
	 *                   to the front end by naming the job , with  the Job Description
	 *                   specified in the Job table.    
	 * @param packageName The package under which the job appears. This value should be
	 *                    taken from the web.xml as it may vary depending on where we
	 *                    are deploying the app (i.e dev, qa, prod,etc).
	 * @return BaseClass  The BaseClass containg the job that is being created.
	 * @throws TCGMException 
	 */
	
	public BaseClass addNewJob(CognosReportNetPortType oCrn, String name, String packageName) throws TCGMException 
	{
		JobDefinition myJob = new JobDefinition();
		try 
		{
			TokenProp jobDefName = new TokenProp();
			jobDefName.setValue(name);
			myJob.setDefaultName(jobDefName);
			bca = new BaseClass[] { myJob };
			BaseClass newBc = this.addObjectToCS( oCrn, bca[0],	"/content/package[@name=\'" + packageName + "\']");
			if (newBc != null)	
			{
				myLogger.debug("Job created successfully");
			}
			return newBc;
		} 
		catch (Exception ex)
		{
			throw new TCGMException("ReportMngr", "printReportNetReport", "who cares", ex.getMessage());
		}
	}
	
	/**
	 * This method adds reports to the newly created job. When the job runs
	 * all the reports in the job will get executed.
	 * 
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param newJob     The newly created job.
	 * @param reports    The String array containg the reports that need
	 *                   to be added to the job.
	 * @param runOptionArrayProp The array containg the run options for each report.
	 * @throws TCGMException 
	 */
	
	public void addReports( CognosReportNetPortType oCrn, JobDefinition newJob,
							String[] reports, RunOptionArrayProp[] runOptionArrayProp) throws TCGMException 
	{
		try 
		{
			JobStepDefinition steps[] = new JobStepDefinition[reports.length];
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
				steps[i].setRunOptions(runOptionArrayProp[i]);
				//adds each definition to the job
				bc = this.addObjectToCS( oCrn, steps[i], newJob.getSearchPath().getValue());
			}
			if (bc != null)
			{
				myLogger.debug("Reports added successfully");
			}
			else
			{
				myLogger.debug("Reports not added");
			}
		} 
		catch (Exception e) 
		{
			throw new TCGMException("ReportNetNewJob", "addReports", e.getMessage());
		}
	}
	
	/**
	 * This method sets the schedule for the job.
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param newJob     The newly created job.
	 * @throws TCGMException
	 */

	public void setScheduleByDay( CognosReportNetPortType oCrn, JobDefinition newJob) throws TCGMException
	{
		try 
		{
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
			end.add(Calendar.DATE, + 0);
			mySchedule.setSchedule( oCrn, newJob.getSearchPath().getValue(), "hour",
									"8", end, "onDate");
			bca = new BaseClass[] { newJob };
			if (updateObjectInCS(oCrn, bca) != null)
			{
				myLogger.debug("Schedule added successfully");
			}
			else
			{
				myLogger.debug("Schedule not added");
			}
		} 
		catch (Exception ex) 
		{
			throw new TCGMException("ReportNetNewJob", "setScheduleByDay", ex.getMessage());
		}
	}

	/**
	 * This method sets the sequence in which the reports in the job run. This can either be
	 * sequential or parallel. We are running in parallel mode.
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param newJob     The newly created job.
	 * @param sequencing The Sequencing to run (Parallel or Sequential mode)
	 * @throws TCGMException
	 */

	public void setSequence( CognosReportNetPortType oCrn, JobDefinition newJob, String sequencing) throws TCGMException
	{
		try 
		{
			NmtokenProp seq = new NmtokenProp();
			seq.setValue(sequencing);
			newJob.setSequencing(seq);
			bca = new BaseClass[] { newJob };
			if (updateObjectInCS(oCrn, bca) != null)
			{
				myLogger.debug("Sequence set successfully");
			}
			else
			{
				myLogger.debug("Sequence not set");
			}
		} 
		catch (Exception ex) 
		{
			throw new TCGMException("ReportNetNewJob", "setSequence", ex.getMessage());
		}
	}

	/**
	 * This method enables the job.This will schedule the job to run immediately.
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param newJob     The newly created job.
	 * @throws TCGMException
	 */

	public void enableJob(CognosReportNetPortType oCrn, JobDefinition newJob) throws TCGMException 
	{
		try 
		{
			BooleanProp disabled = new BooleanProp();
			disabled.setValue(false);
			newJob.setDisabled(disabled);
			bca = new BaseClass[] { newJob };
			if (updateObjectInCS(oCrn, bca) != null)
			{
				myLogger.debug("Job enabled successfully");
			}
		} 
		catch (Exception ex)  
		{
			throw new TCGMException("ReportNetNewJob", "setSequence", ex.getMessage());
		}
	}

	/**
	 * This method enables the job.This will schedule the job to run immediately.
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param bcp        The BaseClass
	 * @param path       The path where the job is located in the contentStore.
	 * @throws Exception
	 */

	public BaseClass addObjectToCS( CognosReportNetPortType oCrn, BaseClass bcp, String path)
		throws Exception 
	{
		AddOptions ao = new AddOptions();
		ao.setUpdateAction(UpdateActionEnum.replace);
		return oCrn.add(path, new BaseClass[] { bcp }, ao)[0];
	}

	/**
	 * This method will update the job object in the Content Store.
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param bcp        The BaseClass
	 * @param path       The path where the job is located in the contentStore.
	 * @throws Exception
	 */

	public BaseClass[] updateObjectInCS( CognosReportNetPortType oCrn, BaseClass[] bcp)
		throws Exception 
	{
		return oCrn.update(bcp);
	}
}