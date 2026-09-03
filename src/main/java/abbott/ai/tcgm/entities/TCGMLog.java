package abbott.ai.tcgm.entities;

import java.io.*;
//import abbott.ai.tcgm.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class TCGMLog implements Serializable
{
	private Stamp createLog = new Stamp();
	private Stamp modifyLog = new Stamp();
	private String msg = "";
	private String modelId="";
	private String datasetTableId="";
	private String actionCode="";
	private String publishFlag="";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMLog()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param actionCode Action Code
	 */
	public void setActionCode(String actionCode)
	{
		this.actionCode = actionCode;
	}
	/**
	 *
	 * @return actionCode
	 */
	public String getActionCode()
	{
		if(this.actionCode == null)
		{
			this.actionCode = "";
		}
		return this.actionCode.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Publish Flag
	 */
	public String getPublishFlag()
	{
		if(this.publishFlag == null)
		{
			this.publishFlag = "";
		}
		return this.publishFlag.trim().toUpperCase();
	}
	/**
	 *
	 * @param publishFlag Publish Flag
	 */
	public void setPublishFlag(String publishFlag)
	{
		this.publishFlag = publishFlag;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param modelId Model Id
	 */
	public void setModelIdInt(int modelIdInt)
	{
		this.modelId = Integer.toString(modelIdInt);
	}
	/**
	 *
	 * @param modelId
	 */
	public void setModelId(String modelId)
	{
		this.modelId = modelId;
	}
	/**
	 *
	 * @return Model Id
	 */
	public int getModelIdInt()
	{
		return Integer.parseInt(this.modelId);
	}
	/**
	 *
	 * @return
	 */
	public String getModelId()
	{
		return this.modelId;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param datasetTableId Dataset Table Id
	 */
	public void setDatasetTableId(String datasetTableId)
	{
		this.datasetTableId = datasetTableId;
	}
	/**
	 *
	 * @param datasetTableId Dataset Table Id
	 */
	public void setDatasetTableIdInt(int datasetTableIdInt)
	{
		this.datasetTableId = Integer.toString(datasetTableIdInt);
	}
	/**
	 *
	 * @return Dataset Table Id
	 */
	public String getDatasetTableId()
	{
		return this.datasetTableId;
	}
	/**
	 *
	 * @return int
	 */
	public int getDatasetTableIdInt()
	{
		return Integer.parseInt(this.datasetTableId);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param createLog Stamp object
	 */
	public void setCreateLog(Stamp createLog)
	{
		this.createLog = createLog;
	}
	/**
	 *
	 * @return Stamp object
	 */
	public Stamp getCreateLog()
	{
		if(this.createLog == null)
		{
			this.createLog = new Stamp();
		}
		return this.createLog;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param modifyLog Stamp object
	 */
	public void setModifyLog(Stamp modifyLog)
	{
		this.modifyLog = modifyLog;
	}
	/**
	 *
	 * @return Stamp object
	 */
	public Stamp getModifyLog()
	{
		if(this.modifyLog == null)
		{
			this.modifyLog = new Stamp();
		}
		return this.modifyLog;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param msg any message that will go along with this entity
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	/**
	 *
	 * @return msg
	 */
	public String getMsg()
	{
		if(this.msg == null)
		{
			this.msg = "";
		}
		return this.msg.trim();
	}
	/**
	 *
	 * @param msg String
	 */
	public void appendMsg(String msg)
	{
		if(this.getMsg().equals("")) // if no msg added so far just set it to the value passed in
		{
			this.setMsg(msg);
		}
		else //else append to the end of the current msg.
		{
			this.msg += msg;
		}
	}
	/**
	 *
	 */
	public void clearMsg()
	{
		this.msg = "";
	}
	/*****************************************************************************************/
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("\nDataset Table Id: ");
		sb.append(this.getDatasetTableId());
		sb.append("\nModel Id: ");
		sb.append(this.getModelId());

		sb.append("Action Code: ");
		sb.append(this.getActionCode());
		sb.append("\nPublish Flag: ");
		sb.append(this.getPublishFlag());
		sb.append("Create Log: ");
		sb.append(this.getCreateLog().toString());
		sb.append("\nModify Log: ");
		sb.append(this.getModifyLog().toString());
		sb.append("\nMsg: ");
		sb.append(this.getMsg());

		return sb.toString();
	}
}