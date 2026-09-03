package abbott.customTags;

//import java.io.IOException;
//import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
//import javax.servlet.jsp.PageContext;

/**
 * <p>Title: SecurePageTag</p>
 * <p>Description:</p>
 * <p>
 * This tag will expect to take a minimum access level and the user's
 * current access level and will determine if the user has rights to the given page or section of a page.
 * </p>
 * <p>
 * If the user does not have rights and has specified a forwardPage value, the tag will forward to the page.<br />
 * If the user does not have rights and has not specified a forwardPage value, the tag will simply not display
 * the body content.
 * </p>
 * <p>
 * If the user does have rights the page and/or body content found within the tag will be displayed.
 * </p>
 * <p>
 * If you wish to use this tag to control access to an entire page then make sure you supply a forwardPage value.<br />
 * If you only wish to hide or display the body content of a tag do not supply a forwardPage value.
 * </p>
 * <p>
 * The tag will take some constants to determine how the comparison should be performed.
 * </p>
 * <p>
 * Comparisions will be performed for the following based on the comparisonType passed in.<br />
 * It is assumed that the accessLevels will be integers.<br />
 * Your application may need to store integer values with the string constants that describe your application roles.
 * </p>
 * <p>
 * The default comparison type is ==.<br />
 * The default forwardPage is "".<br />
 * All other attributes are required
 * </p>
 * <p>
 * Comparisions will be performed for the following based on the comparisionType passed in.<br />
 * <ul>
 * <li>userAccessLevel == requiredAccessLevel</li>
 * <li>userAccessLevel != requiredAccessLevel</li>
 * <li>userAccessLevel &lt; requiredAccessLevel</li>
 * <li>userAccessLevel &lt;= requiredAccessLevel</li>
 * <li>userAccessLevel &gt; requiredAccessLevel</li>
 * <li>userAccessLevel &gt;= requiredAccessLevel</li>
 * </ul>
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author Dave FIelds
 * @version 1.0
 */
public final class SecurePageTag extends TagSupport
{
	private final String EQUALS = "=";
	private final String NOT_EQUAL = "!=";
	private final String LESS_THAN = "<";
	private final String LESS_THAN_OR_EQUAL_TO = "<=";
	private final String GREATER_THAN = ">";
	private final String GREATER_THAN_OR_EQUAL_TO = ">=";

	/*****************************************************************************************/
	/**
	 * userAccessLevel is the access level of the current user
	 */
	private int userAccessLevel;

	/*****************************************************************************************/
	/**
	 * requiredAccessLevel is the value to compare against.  It may be the minimum, maximum, or exact
	 * access level required to gain access to this page or a section of the page included within the
	 * body of the tag.
	 */
	private int requiredAccessLevel;

	/*****************************************************************************************/
	/**
	 * forwardPage is the page to forward to in the event the accessLevel does not meet the specified criteria.
	 * If you want to use this tag to hide/display the body then do not provide a forwardPage value.
	 */
	private String forwardPage = "";

	/*****************************************************************************************/
	/**
	 * comparisonType is the type of comparison to be performed.
	 * Valid values are:
	 * <ul>
	 * <li>=</li>
	 * <li>!=</li>
	 * <li>&lt;</li>
	 * <li>&lt;=</li>
	 * <li>&gt;</li>
	 * <li>&gt;=</li>
	 * The default comparisionType is =
	 */
	private String comparisonType = this.EQUALS;


	/**
	 * This value is used internally by the bean to hold a boolean that indicates if the comparison passed or failed.
	 */
	private boolean passedComparison = false;

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public SecurePageTag()
	{
	}

	/*****************************************************************************************/
	/**
	 * If the comparison passes then eval the body, else skip it.
	 * @return A constant that tells the JSP how it should continue processing
	 * @throws JspTagException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspTagException
	{
		int returnVal = EVAL_BODY_INCLUDE;

		// Display the body based on the results of the comparison
		if (!performComparison())
		{
			returnVal = SKIP_BODY;
		}
		return (returnVal);
	}

	/*****************************************************************************************/
	/**
	 * If the comparision does not pass the check and a forwardPage has been specified
	 * then forward to the next page.
	 * @return A constant that tells the JSP how it should continue processing
	 * @throws JspTagException if a JSP exception has occurred
	 */
	public int doEndTag() throws JspTagException
	{
		int returnVal = EVAL_PAGE;

		// Forward control based on the results
		if (!this.passedComparison)
		{
			if(!this.getForwardPage().equals(""))
			{
				try
				{
					pageContext.forward(this.forwardPage);
				}
				catch (Exception e)
				{
					throw new JspTagException(e.toString());
				}
				returnVal = SKIP_PAGE;
			}
		}

		return (returnVal);
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return Returns true if the comparison is successful based on the comparison type passed in
	 */
	private boolean performComparison()
	{
		this.passedComparison = false;

		if(this.comparisonType.equals(this.EQUALS))
		{
			if(this.userAccessLevel == this.requiredAccessLevel)
			{
				this.passedComparison = true;
			}
		}
		else if(this.comparisonType.equals(this.NOT_EQUAL))
		{
			if(this.userAccessLevel != this.requiredAccessLevel)
			{
				this.passedComparison = true;
			}
		}
		else if(this.comparisonType.equals(this.LESS_THAN))
		{
			if(this.userAccessLevel < this.requiredAccessLevel)
			{
				this.passedComparison = true;
			}
		}
		else if(this.comparisonType.equals(this.LESS_THAN_OR_EQUAL_TO))
		{
			if(this.userAccessLevel <= this.requiredAccessLevel)
			{
				this.passedComparison = true;
			}
		}
		else if(this.comparisonType.equals(this.GREATER_THAN))
		{
			if(this.userAccessLevel > this.requiredAccessLevel)
			{
				this.passedComparison = true;
			}
		}
		else if(this.comparisonType.equals(this.GREATER_THAN_OR_EQUAL_TO))
		{
			if(this.userAccessLevel >= this.requiredAccessLevel)
			{
				this.passedComparison = true;
			}
		}

		return this.passedComparison;
	}

	/*********************************************************************************************************************/
	/**
	 * Release any acquired resources.
	 */
	public void release()
	{
		super.release();
	}

	/*****************************************************************************************/
	/**
	 * Return the forward page.
	 * @return the page to forward to
	 */
	public String getForwardPage()
	{
		if(this.forwardPage == null)
		{
			this.forwardPage = "";
		}
		return this.forwardPage;
	}
	/**
	 * Set the forward page.
	 * @param pForwardPage The new forward page
	 */
	public void setForwardPage(String pForwardPage)
	{
		if(pForwardPage == null)
		{
			pForwardPage = "";
		}
		this.forwardPage = pForwardPage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @return the current access level
	 */
	public int getUserAccessLevel()
	{
		return userAccessLevel;
	}
	/**
	 *
	 * @param userAccessLevel the current access level
	 */
	public void setUserAccessLevel(int userAccessLevel)
	{
		this.userAccessLevel = userAccessLevel;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param requiredAccessLevel the access level to compare against
	 */
	public void setRequiredAccessLevel(int requiredAccessLevel)
	{
		this.requiredAccessLevel = requiredAccessLevel;
	}
	/**
	 *
	 * @return the access level to compare against
	 */
	public int getRequiredAccessLevel()
	{
		return requiredAccessLevel;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param comparisonType the type of comparison to perform
	 */
	public void setComparisonType(String comparisonType)
	{
		this.comparisonType = comparisonType;
	}
	/**
	 *
	 * @return the type of comparison to perform
	 */
	public String getComparisonType()
	{
		return comparisonType;
	}
}