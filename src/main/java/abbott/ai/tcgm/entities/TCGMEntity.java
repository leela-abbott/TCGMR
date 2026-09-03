package abbott.ai.tcgm.entities;

import java.io.*;
//import abbott.ai.tcgm.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Contains properties relevant to all entities</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class TCGMEntity extends TCGMLog implements Serializable
{
	private String modelId="";
	private String datasetTableId="";
	private String actionCode="";
	private String publishFlag="";
	private boolean tranAdvFilter = false;

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMEntity()
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
	/**
	 * @return
	 */
	public boolean isTranAdvFilter() {
		return tranAdvFilter;
	}
	/**
	 * @param tranAdvFilter
	 */
	public void setTranAdvFilter(boolean tranAdvFilter) {
		this.tranAdvFilter = tranAdvFilter;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
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

		return sb.toString();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param oos
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
	}
	/**
	 *
	 * @param ois
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
	}
}