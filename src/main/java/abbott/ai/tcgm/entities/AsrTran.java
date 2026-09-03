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
public class AsrTran extends TCGMTranEntity implements Serializable
{
	private Asr asr = new Asr();
	private String asrTranId = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public AsrTran()
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

		sb.append("Asr Tran Id:");
		sb.append(this.getAsrTranId());
		sb.append("\n");
		sb.append(this.getAsr().toString());
		sb.append("\n");
		sb.append(super.toString());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param asr Asr object
	 */
	public void setAsr(Asr asr)
	{
		this.asr = asr;
	}
	/**
	 *
	 * @return asr
	 */
	public Asr getAsr()
	{
		if(this.asr == null)
		{
			this.asr = new Asr();
		}
		return this.asr;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Asr Tran Id
	 */
	public String getAsrTranId()
	{
		if(this.asrTranId == null)
		{
			this.asrTranId = "";
		}
		return this.asrTranId.trim();
	}
	/**
	 *
	 * @param asrTranId Asr Tran Id
	 */
	public void setAsrTranId(String asrTranId)
	{
		this.asrTranId = asrTranId;
	}
}