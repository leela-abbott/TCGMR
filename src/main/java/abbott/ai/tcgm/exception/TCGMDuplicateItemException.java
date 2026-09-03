package abbott.ai.tcgm.exception;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class TCGMDuplicateItemException extends TCGMException
{

	/**
	 *
	 * @param duplicateItem
	 */
	public TCGMDuplicateItemException(String duplicateItem)
	{
		super();
	}
	
	public TCGMDuplicateItemException(String errorClass, String errorMethod, String errMsg)
	{
		super();
	}	
}