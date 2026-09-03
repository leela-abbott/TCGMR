package abbott.ai.tcgm.exception;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class TCGMItemNotFoundException extends TCGMException
{

	/**
	 *
	 * @param duplicateItem
	 */
	public TCGMItemNotFoundException(String cls, String mthd, String parmList, String msg)
	{
		super(cls, mthd, parmList, msg);
	}

    public TCGMItemNotFoundException(String cls, String mthd, String parmList)
    {
        super(cls, mthd, parmList, "Item not found when expected to exist.");
	}

}