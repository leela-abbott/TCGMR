package abbott.ai.tcgm.entities;
//import abbott.ai.tcgm.data.*;
import java.io.Serializable;
/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class ErrorLogBean extends TCGMTranEntity implements Serializable
{
	private Asr asr = new Asr();
	private String jobqueueid = "";
	private String modelid;
	private String tableid;
	private String msg;
	private String status;						
	private String procname;
	private String crtdate;
			
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public ErrorLogBean()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return string representation of the class properties and their values.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Job Queue Id:");
		sb.append(this.getJobqueueid());
		sb.append("\n");
		sb.append(super.toString());

		return sb.toString();
	}
	
	/*****************************************************************************************/
	/**
	 *
	 * @return Job Queue Id
	 */
	public String getJobqueueid()
	{
		if(this.jobqueueid == null)
		{
			this.jobqueueid = "";
		}
		
		return this.jobqueueid.trim();
	}
	/**
	 *
	 * @param asrTranId Asr Tran Id
	 */
	public void setJobqueueid(String jobqueueid)
	{
		this.jobqueueid = jobqueueid; 
		
	}
	
	public void setTableid(String tableId){
	this.tableid = tableId;
    }
    
	public String getTableid(){
		return this.tableid;
	}
	
	public void setModelid(String modelid){
		this.modelid = modelid;
		}
    
	public String getModelid(){
		return this.modelid;
	}

	
	/**
	 * @return
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * @return
	 */
	public String getProcname() {
		return this.procname;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param string
	 */
	public void setMsg(String string) {
		this.msg = string;
	}

	/**
	 * @param string
	 */
	public void setProcname(String string) {
		this.procname = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		this.status = string;
	}
	
	/**
		 * @return
		 */
		public String getCrtdate() {
			return crtdate;
		}

		/**
		 * @param string
		 */
		public void setCrtdate(String string) {
			this.crtdate = string;
		}

}