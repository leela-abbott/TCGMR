package abbott.ai.tcgm.process;

//import java.lang.reflect.*;
//import abbott.ai.tcgm.exception.*;
import java.util.Properties;

import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.process.javajob.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public final class JobInstance extends TCGMEntity
{
	public JobInstance() {}

	public static final class JobStatus {
		private final String name;
		private final String code;

		public static final JobStatus Failed = new JobStatus("Failed", "F");
		public static final JobStatus Immediate = new JobStatus("Immediate", "I");
		public static final JobStatus Batch = new JobStatus("Batch", "B");
		public static final JobStatus Complete = new JobStatus("Complete", "C");
		public static final JobStatus Processing = new JobStatus("Processing", "P");
		public static final JobStatus Unknown = new JobStatus("Unknown", "X");
		public static final JobStatus Jobcomplete = new JobStatus("Jobcomplete", "J");
		public static final JobStatus Reportprocess = new JobStatus("Reportprocess", "R");
		public static final JobStatus Exceptionreporting = new JobStatus("Report Exception", "E");
		
		private JobStatus (String name, String code) {
			this.name = name;
			this.code = code;
		}
		public String getName() {return this.name;}
		public String toString() {return this.name;}
		public String getCode() {return this.code;}
		public static JobStatus getJobByCode(String code) {
			if ( code.equals("F") ) return JobStatus.Failed;
			else if (code.equals("I") ) return JobStatus.Immediate;
			else if (code.equals("B") ) return JobStatus.Batch;
			else if (code.equals("C") ) return JobStatus.Complete;
			else if (code.equals("P") ) return JobStatus.Processing;
			else if (code.equals("J") ) return JobStatus.Jobcomplete;
			else if (code.equals("R") ) return JobStatus.Reportprocess;
			else if (code.equals("E") ) return JobStatus.Exceptionreporting;
			else if (code.equals("X") ) return JobStatus.Unknown;
			else return null;
		}
	}

	private JobDefinition jobDef;
	private String model;
//	private static final UserToken userToken;
	private String desc;
	private String jobQueId;
	private JobStatus jobStatus;
	private Properties jobParms = new Properties();
	private String jobQueSeq;
	private boolean generateOutput;
	private boolean printOutput;
	private String endTime;
	private String submitTime;
	private String userSubmitted;
	private String startTime;

	public JobInstance(JobDefinition jdef) {
		this.jobDef = jdef;


	}

	public void setJobQueId(String id) {
		this.jobQueId = id;
	}

	public String getJobQueId() {
		return jobQueId;
	}

	public void setModel(String m) {
		this.model = m;
	}

	public String getModel() {
		return this.model;
	}

//	public UserToken getUserToken() {
//		return this.userToken;
//	}

	public String toShortString() {
		StringBuffer sb = new StringBuffer();
		if (this.jobDef == null) {
			sb.append("<None Specified>");
		}
		else {
			sb.append( this.getJobDef().getJobName() );
		}
		sb.append(" | " + this.getModel() );
		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.jobDef == null) {
			sb.append("Job: <None Specified>\n");
		}
		else {
			sb.append("Job Name: " + this.getJobDef().getJobName() + "\n");
			sb.append("Job Type: " + this.getJobDef().getJobType() + "\n");
		}

		sb.append( "Model: " + this.getModel() + "\n" );
		sb.append( "Model Id: " + this.getModelId() + "\n" );
		sb.append( "Job Status: " + this.getJobStatus() + "\n" );
		sb.append( "Job Q Id: " + this.getJobQueId() + "\n" );
		return sb.toString();
	}
	public void setDesc(String desc) {
		this.desc = desc.trim();
	}
	public String getDesc() {
		return desc.trim();
	}

	public JobDefinition getJobDef() {
		return jobDef;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	public void setJobStatusByCode(String code) {
		// 8-10-05 temporarily removed trim to test new 9i drivers
		//this.jobStatus = JobStatus.getJobByCode(code.trim());
		this.jobStatus = JobStatus.getJobByCode(code);
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

		public boolean isParamaterized() {
		  if (jobParms == null || jobParms.size() == 0)
			return false;
		  else
			return true;
		}

	public void addJobParm(String key, String value) {
		jobParms.put(key, value);
	}

	public void setJobParms(Properties jobParms) {
		this.jobParms = jobParms;
	}

	public Properties getJobParms() {
		return jobParms;
	}

	public void clearJobParms() {
		this.jobParms.clear();
	}

	public void setJobQueSeq(String jobQueSeq) {
		this.jobQueSeq = jobQueSeq;
	}
	public void setJobQueSeqInt(int jobQueSeq) {
		this.jobQueSeq = Integer.toString(jobQueSeq);

	}
	public String getJobQueSeq() {
		return jobQueSeq;
	}
	public void setPrintOutput(boolean printOutput) {
		this.printOutput = printOutput;
	}
	public boolean isPrintOutput() {
		return printOutput;
	}
	public void setGenerateOutput(boolean generateOutput) {
		this.generateOutput = generateOutput;
	}
	public boolean isGenerateOutput() {
		return generateOutput;
	}

	public void setEndTime(String endTime) {
			this.endTime = endTime;
	}
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @return
	 */
	public String getSubmitTime() {
		return submitTime;
	}

	/**
	 * @param string
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * @return
	 */
	public String getUserSubmitted() {
		return userSubmitted;
	}

	/**
	 * @param string
	 */
	public void setUserSubmitted(String userSubmitted) {
		this.userSubmitted = userSubmitted;
	}

	/**
	 * @return
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param string
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}