package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Contains properties relevant to all entities</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class TCGMTranEntity implements java.io.Serializable
{
	private String actionCode="";
	private String publishFlag="";
	private boolean tranAdvFilter = false;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMTranEntity()
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
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Action Code: ");
		sb.append(this.getActionCode());
		sb.append("\nPublish Flag: ");
		sb.append(this.getPublishFlag());

		return sb.toString();
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
}