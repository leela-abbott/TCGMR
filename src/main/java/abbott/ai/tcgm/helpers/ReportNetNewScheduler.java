package abbott.ai.tcgm.helpers;
import java.math.BigInteger;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.cognos.developer.schemas.bibus._3.Account;
import com.cognos.developer.schemas.bibus._3.AddOptions;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseClassArrayProp;
import com.cognos.developer.schemas.bibus._3.BooleanProp;
import com.cognos.developer.schemas.bibus._3.CognosReportNetPortType;
import com.cognos.developer.schemas.bibus._3.Credential;
import com.cognos.developer.schemas.bibus._3.DateTimeProp;
import com.cognos.developer.schemas.bibus._3.NmtokenProp;
import com.cognos.developer.schemas.bibus._3.ParameterValueArrayProp;
import com.cognos.developer.schemas.bibus._3.PositiveIntegerProp;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.RunOption;
import com.cognos.developer.schemas.bibus._3.RunOptionArrayProp;
import com.cognos.developer.schemas.bibus._3.RunOptionBoolean;
import com.cognos.developer.schemas.bibus._3.RunOptionEnum;
import com.cognos.developer.schemas.bibus._3.Schedule;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.StringProp;
import com.cognos.developer.schemas.bibus._3.UpdateActionEnum;

public class ReportNetNewScheduler extends BaseClass 
{
	private Schedule newSchedule = new Schedule();
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.helpers.ReportNetNewScheduler" );	
	
	/**
	 * This method sets the job schedule. We are using this to schedule to
	 * the job immediately and specifying to run it only once.In the future
	 * we can take advantage of the existing code to schedule it in different ways.
	 * 
	 * @param oCrn       This Provides a Connection to Cognos.
	 * @param objectPath The ObjectPath contains the path of the job in the content store.
	 * @param runPeriod  It can be 'hourly', 'daily', 'weekly', etc.
	 * @param runFreq    It can be every 8 hours, once in a day, once in a week, etc.
	 * @param endOnDate  It can be every 8 hours, once in a day, once in a week, etc.
	 */

	public void setSchedule(CognosReportNetPortType oCrn, String objectPath,
							String runPeriod, String runFreq,
							Calendar endOnDate, String endOnTime) throws Exception  
	{
		BooleanProp isActive = new BooleanProp();
		DateTimeProp startDate = new DateTimeProp();
		DateTimeProp endDate = new DateTimeProp();
		RunOptionArrayProp roap = new RunOptionArrayProp();
		roap.setValue(this.setSchedulerRunOptions());
		isActive.setValue(true);
		setFrequencyByDay(runPeriod);
		setFrequency(runFreq);
		newSchedule.setStartDate(this.setScheduleStartDate());
		newSchedule.setEndType(this.setScheduleEndTime(endOnTime));
		newSchedule.setEndDate(this.setScheduleEndDate(endOnDate, endOnTime));
		newSchedule.setActive(isActive);
		newSchedule.setRunOptions(roap);
		//If the object is a report set parameter values
		//in this case we assume reports don't require parameters
		if (objectPath.indexOf("/report") > 0)
			newSchedule.setParameters(new ParameterValueArrayProp());
		//get the account that is currently logged in and add credentials to it
		Account logonInfo = this.getLogonAccount(oCrn);
		BaseClassArrayProp credentials = new BaseClassArrayProp();
		Credential crd = new Credential();
		//set search path for the credentials
		StringProp crdPath = new StringProp();
		crdPath.setValue( logonInfo.getSearchPath().getValue() + "/credential[@name='Credential']");
		crd.setSearchPath(crdPath);
		newSchedule.setCredential(credentials);
		newSchedule.getCredential().setValue(new BaseClass[] { crd });
		//add the schedule to the report
		AddOptions ao = new AddOptions();
		ao.setUpdateAction(UpdateActionEnum.replace);
		BaseClass newBc =
			oCrn.add(objectPath, new BaseClass[] { newSchedule }, ao)[0];
		if (newBc != null)
			myLogger.debug("Schedule created successfully");
		else
			myLogger.debug("Schedule NOT Created");
	}

	/**
	 * This method sets the Frequency for the job.
	 * @param runFreq    Set the Schedule Frequency for the job.
	 */
	public void setFrequency(String runFreq) 
	{
		PositiveIntegerProp freq = new PositiveIntegerProp();
		freq.setValue(new BigInteger(runFreq));
		newSchedule.setEveryNPeriods(freq);
	}

	/**
	 * This method sets the Frequency(runPeriod) for the job.
	 * @param runPeriod    Set the Schedule Frequency(runPeriod) for the job.
	 */
	public void setFrequencyByDay(String runPeriod) 
	{
		NmtokenProp period = new NmtokenProp();
		NmtokenProp howOften = new NmtokenProp();
		howOften.setValue("daily");
		period.setValue(runPeriod);
		newSchedule.setType(howOften);
		newSchedule.setDailyPeriod(period);
	}

	/**
	 * This method sets the runOptions for the Scheduler
	 */
	public RunOption[] setSchedulerRunOptions() 
	{
		 RunOptionBoolean prompt =
				new RunOptionBoolean();
		//Don't prompt for values
		prompt.setName(RunOptionEnum.prompt);
		prompt.setValue(false);
		return new RunOption[] { prompt };
	}

	/**
	 * This method sets the StartDate for the job
	 */
	public DateTimeProp setScheduleStartDate() 
	{
		DateTimeProp startDate = new DateTimeProp();
		Calendar myCal = Calendar.getInstance();
		myCal.add(Calendar.MINUTE, 5);
		startDate.setValue(myCal);
		return startDate;
	}

	/**
	 * This method sets the EndDate for the job
	 * @param  endOnDate  The Calendar Date on which the job will end running (last run)
	 * @param  endOnTime  The time the last run will happen on the last day.
	 * @return endDate    The DateTimeProp.
	 */

	public DateTimeProp setScheduleEndDate(Calendar endOnDate, String endOnTime) 
	{
		DateTimeProp endDate = new DateTimeProp();
		if (endOnTime.compareToIgnoreCase("onDate") == 0)
			if (endOnDate != null) 
			{
				endDate.setValue(endOnDate);
			} 
			else
		myLogger.info("Parameter endOnTime cannot be onDate if no endOnDate provided");
		return endDate;
	}
	
	/**
	 * This method sets the EndTime for the job
	 * @param  endOnTime  The time the last run will happen on the last day.
	 * @return endTime    The NmtokenProp.
	 */

	public NmtokenProp setScheduleEndTime(String endOnTime) 
	{
		NmtokenProp endTime = new NmtokenProp();
		if (endOnTime != null) 
		{
			endTime.setValue(endOnTime);
		} else
			myLogger.debug("Parameter endOnTime cannot be null! Options: indefinite or onDate");
		return endTime;
	}

	/**
	 * This method sets the LogonAccount
	 * @param  oCrn     The Connection to Cognos
	 * @return Account  The Login Account Information.
	 */

	public Account getLogonAccount(CognosReportNetPortType oCrn) 
	{
		PropEnum props[] =
			new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
		Account myAccount = new Account();
		if (oCrn != null) 
		{
			try 
			{
				BaseClass bc[] = oCrn.query("~", props, new Sort[] {}, new QueryOptions());
				if (bc != null) 
				{
					if (bc.length > 0) 
					{
						for (int i = 0; i < bc.length; i++) 
						{
							myAccount = (Account) bc[i];
						}
					}
				}
			} 
			catch (Exception e) 
			{
				myLogger.error(e.getMessage());
			}
		}
		return myAccount;
	}
}
