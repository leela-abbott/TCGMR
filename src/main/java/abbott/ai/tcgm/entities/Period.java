package abbott.ai.tcgm.entities;

//import abbott.ai.tcgm.TCGMConstants;
//import java.lang.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class Period implements java.io.Serializable
{
	private String _period="";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Period()
	{

	}
	public Period(String period)
	{
		this.setPeriod(period);
	}
	/*****************************************************************************************/
	public String getPeriod()
	{
		if(this._period == null)
		{
			this._period = "";
		}
		return this._period.trim();
	}
	public void setPeriod(String _period)
	{
		this._period = _period;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(this.getPeriod());

		return sb.toString();
	}
}