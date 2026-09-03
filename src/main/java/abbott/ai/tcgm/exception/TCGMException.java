package abbott.ai.tcgm.exception;

import java.lang.Exception;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class TCGMException extends Exception
{
	/*****************************************************************************************/
	private String throwingClass;
	private String throwingMethod;
	private String parameterList;
	private String errorMessage;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMException()
	{
	}
	/**
	 * Override default constructor
	 * @param pThrowingClass name of the throwing class
	 * @param pThrowingMethod name of the throwing method
	 * @param pParameterList list of parameters
	 * @param pErrorMessage error message
	 */
	public TCGMException (TCGMException te) {

		this.setErrorMessage( te.getErrorMessage() );
		this.setThrowingClass( te.getThrowingClass() );
		this.setThrowingMethod( te.getThrowingMethod() );
		this.setParameterList( te.getParameterList() );
	}

	public TCGMException(String pThrowingClass,String pThrowingMethod, String pParameterList,String pErrorMessage)
	{
		this.setErrorMessage(pErrorMessage);
		this.setThrowingClass(pThrowingClass);
		this.setThrowingMethod(pThrowingMethod);
		this.setParameterList(pParameterList);
	}
	/**
	 * Override default constructor
	 * @param pThrowingClass name of the throwing class
	 * @param pThrowingMethod name of the throwing method
	 * @param pErrorMessage error message
	 */
	public TCGMException(String pThrowingClass,String pThrowingMethod,String pErrorMessage)
	{
		this.setErrorMessage(pErrorMessage);
		this.setThrowingClass(pThrowingClass);
		this.setThrowingMethod(pThrowingMethod);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return throwingClass
	 */
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
		buf.append("Throwing Class: " + this.getThrowingClass());
		buf.append("\nThrowing Method: " + this.getThrowingMethod());
		buf.append("\nParameter List: " + this.getParameterList());
		buf.append("\nError Message: " + this.getErrorMessage());
		return buf.toString ();
	}
}