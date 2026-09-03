package abbott.ai.tcgm.data;

import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public final class DatasetConst
{
	public static final String className = DatasetConst.class.getName();
	private static DatasetConst instance = null;

	/*****************************************************************************************/
	/**
	 * @return Returns an instance of the SQLUtil class
	 */
	public static DatasetConst getInstance()
	{
	   if(instance == null)
	   {
		   instance = new DatasetConst();
	   }
	   return instance;
	}
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	private DatasetConst()
	{
	}
	/*****************************************************************************************/
	/**
	 * @throws TCGMException
	 */
	public void init() throws TCGMException
	{
		//create dataset helper
		DatasetMngr dsMngr = new DatasetMngr();

		//affBpcDS = dsMngr.getDatasetByName(SQLUtil.getOracleAdmin(),affBpcDS.getDatasetName());

		//this.logger.debug(this.toString());
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		return sb.toString();
	}
}