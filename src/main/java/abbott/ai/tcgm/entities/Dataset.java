package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.data.DBConst;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class Dataset extends TCGMLog implements java.io.Serializable
{
	private String datasetTableId = "";
	private String datasetName = "";
	private String tableName = "";
	private String datasetDesc = "";
    private DatasetType datasetType = Dataset.DatasetType.UNSPECIFIED;
    private String datasetNameLogStamp = "";
    private long datasetCreateDateTime ;
    
	private String selDatasetid = "";

    // Enumerated static types for facilitating the creation of common datasets.
    // The key characteristic of these types is the tablename they point to,
    // so the updating of that field checks and modifies the type, and the converse.
    public static class DatasetType {
      private final String name;
      private final String tableName;

      public static final DatasetType UNIT_SET = new DatasetType ("UNIT_SET", DBConst.TABLE_UNIT_DATA);
      public static final DatasetType RATE_SET = new DatasetType ("RATE_SET", DBConst.TABLE_RATE_DATA);
      public static final DatasetType UNSPECIFIED = new DatasetType ("UNSPECIFIED", null);

      private DatasetType(String name, String tableName) { this.name = name; this.tableName = tableName; }
      public String toString() { return this.name; }
      public String getTableName() { return this.tableName; }
  }
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Dataset()
	{
	}
	/**
	 * @param datasetName
	 */
	public Dataset(String datasetName)
	{
		this.datasetName = datasetName;
	}

    public Dataset(DatasetType dsType) {
        this.setDatasetType(dsType);
    }

    public DatasetType getDatasetTYpe() {
        return this.datasetType;
    }

    // Keep this private to control access to it. Keeps datasets immutable once created with a given type.
    private void setDatasetType(DatasetType dsType) {
        this.datasetType = dsType;

        // Most types have preset characteristics, so use them, unless unspecified.
        if (dsType != dsType.UNSPECIFIED) {
            this.tableName = dsType.getTableName();
        }
    }

	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String getDatasetTableId()
	{
		if(this.datasetTableId == null)
		{
			this.datasetTableId="";
		}
		return this.datasetTableId.trim();
	}
	/**
	 * @return
	 */
	public int getDatasetTableIdInt()
	{
		return Integer.parseInt(this.getDatasetTableId());
	}
	/**
	 * @param datasetTableId
	 */
	public void setDatasetTableId(String datasetTableId)
	{
		this.datasetTableId = datasetTableId;
	}
	/**
	 *
	 * @param datasetTableId
	 */
	public void setDatasetTableIdInt(int datasetTableId)
	{
		this.datasetTableId = Integer.toString(datasetTableId);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param datasetName
	 */
	public void setDatasetName(String datasetName)
	{
		this.datasetName = datasetName;
	}
	/**
	 *
	 * @return
	 */
	public String getDatasetName()
	{
		if(this.datasetName == null)
		{
			this.datasetName="";
		}
		return this.datasetName.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 * @param tableName
	 */
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
	/**
	 * @return
	 */
	public String getTableName()
	{
		if(this.tableName == null)
		{
			this.tableName = "";
		}
		return this.tableName.trim().toUpperCase();
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String getDatasetDesc()
	{
		if(this.datasetDesc == null)
		{
			this.datasetDesc = "";
		}
		return this.datasetDesc.trim();
	}
	/**
	 * @param datasetDesc
	 */
	public void setDatasetDesc(String datasetDesc)
	{
		this.datasetDesc = datasetDesc;
	}

	/**
	 * 
	 * @return
	 */
	public String getDatasetNameLogStamp()
	{
		if(this.datasetNameLogStamp == null)
		{
			this.datasetNameLogStamp = "";
		}
		return this.datasetNameLogStamp.trim();
	}
	/**
	 * @param createDateTimeStamp
	 */
	public void setDatasetNameLogStamp(String dataNameLogStamp)
	{
		this.datasetNameLogStamp = dataNameLogStamp; 
	}	
	
	/*****************************************************************************************/
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(super.toString());
		sb.append("Dataset Name: ");
		sb.append(this.getDatasetName());
		sb.append("Dataset Table Id: ");
		sb.append(this.getDatasetTableId());
		sb.append("\nTableName: ");
		sb.append(this.getTableName());
		sb.append("\nDataset Desc: ");
		sb.append(this.getDatasetDesc());
		sb.append("\nDatasetName & Date-Time Stamp: ");
		sb.append(this.getDatasetNameLogStamp());		

		return sb.toString();
	}
	/**
	 * @return
	 */
	public String getSelDatasetid() {
		if(this.selDatasetid == null)
				{
					this.selDatasetid="";
				}
		return this.selDatasetid.trim();
		
	}

	/**
	 * @param string
	 */
	public void setSelDatasetid(String string) {
		selDatasetid = string;
	}

	
	/**
	 * @return Returns the datasetCreateDateTime.
	 */
	public long getDatasetCreateDateTime() {
		return datasetCreateDateTime;
	}
	/**
	 * @param datasetCreateDateTime The datasetCreateDateTime to set.
	 */
	public void setDatasetCreateDateTime(long datasetCreateDateTime) {
		this.datasetCreateDateTime = datasetCreateDateTime;
	}
}