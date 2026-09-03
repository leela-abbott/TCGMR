package abbott.ai.tcgm.exception;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class TCGMUniqueExpectedException extends TCGMException
{

	/**
	 *
	 * @param duplicateItem
	 */
	public TCGMUniqueExpectedException(String itemDuplicateFound)
	{
		super();
	}

	public TCGMUniqueExpectedException(String pThrowingClass,String pThrowingMethod, String pParameterList,String pErrorMessage) {
        super(pThrowingClass, pThrowingMethod, pParameterList, pErrorMessage);
	}

    public TCGMUniqueExpectedException( String pThrowingClass,String pThrowingMethod, String pParameterList ) {
        super(pThrowingClass, pThrowingMethod, pParameterList, "Result returned was nonunique when unique was expected");
	}

    public String toString() {
        return (this.getClass().getName() + this.getMessage() );
    }




}