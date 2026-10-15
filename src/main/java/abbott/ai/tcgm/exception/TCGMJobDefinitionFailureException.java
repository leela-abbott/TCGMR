package abbott.ai.tcgm.exception;

//import java.lang.Exception;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class TCGMJobDefinitionFailureException extends TCGMException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*****************************************************************************************/
	private String throwingClass;
	private String throwingMethod;
	private String parameterList;
	private String errorMessage;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMJobDefinitionFailureException() {
	    // Calls the single-string parent constructor with a default message
	    super("Job definition failure exception occurred."); 
	}

	public TCGMJobDefinitionFailureException(String pThrowingClass, String pThrowingMethod, String pParameterList, String pErrorMessage) {
	    // Explicitly invokes the matching 4-argument parent constructor
	    super(pThrowingClass, pThrowingMethod, pParameterList, pErrorMessage);
	}

	public TCGMJobDefinitionFailureException(String pThrowingClass, String pThrowingMethod, String pErrorMessage) {
	    // Explicitly invokes the matching 3-argument parent constructor
	    super(pThrowingClass, pThrowingMethod, pErrorMessage);
	}

	public String getThrowingClass()
	{
		return this.throwingClass;
	}
	/**
	 *
	 * @param throwingClass name of the throwing class
	 */
	public void setThrowingClass(String throwingClass)
	{
		this.throwingClass = throwingClass;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param throwingMethod name of the throwing method
	 */
	public void setThrowingMethod(String throwingMethod)
	{
		this.throwingMethod = throwingMethod;
	}
	/**
	 *
	 * @return throwingMethod
	 */
	public String getThrowingMethod()
	{
		return this.throwingMethod;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param parameterList list of parameters from the throwing class
	 */
	public void setParameterList(String parameterList)
	{
		this.parameterList = parameterList;
	}
	/**
	 *
	 * @return parameter list
	 */
	public String getParameterList()
	{
		return this.parameterList;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param errorMessage error message
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	/**
	 *
	 * @return error message
	 */
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
	/*****************************************************************************************/
	/**
	 * This method returns a string with all the properties in this class.
	 *
	 * @return buf.toString()
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer ();
		buf.append("Throwing Class: " + this.getThrowingClass() + "\n");
		buf.append("Throwing Method: " + this.getThrowingMethod() + "\n");
		buf.append("Parameter List: " + this.getParameterList() + "\n");
		buf.append("Error Message: " + this.getErrorMessage());
		return buf.toString ();
	}
}