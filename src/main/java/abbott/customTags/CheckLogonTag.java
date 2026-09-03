package abbott.customTags;

//import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
//import javax.servlet.jsp.PageContext;

/**
* Check for a valid User logged on in the current session.  If there is no
* such user, forward control to the logon page.
*
* @author David Fields
* @version 1.0
*/
public final class CheckLogonTag extends TagSupport
{
	/*********************************************************************************************************************/
	/**
	 * beanName is the name of the session object to check.  Pass in a bean name that
	 * should exist if the user has successfully logged in.
	 */
	private String beanName = null;
	/**
	 * forwardPage is the page to forward to in the event the session object is not available
	 */
	private String forwardPage = null;

	/*********************************************************************************************************************/
	/**
	* Return the bean name.
	*/
	public String getBeanName()
	{
		return this.beanName;
	}
	/**
	* Set the bean name.
	*
	* @param name The new bean name
	*/
	public void setBeanName(String pBeanName)
	{
		this.beanName = pBeanName;
	}

	/*********************************************************************************************************************/
	/**
	* Return the forward page.
	*/
	public String getForwardPage()
	{
		return this.forwardPage;
	}
	/**
	* Set the forward page.
	*
	* @param pForwardPage The new forward page
	*/
	public void setForwardPage(String pForwardPage)
	{
		this.forwardPage = pForwardPage;
	}

	/*********************************************************************************************************************/
	/**
	* Defer our checking until the end of this tag is encountered.
	*
	* @exception JspTagException if a JSP exception has occurred
	*/
	public int doStartTag() throws JspTagException
	{
		return (SKIP_BODY);
	}

	/*********************************************************************************************************************/
	/**
	* Perform our logged-in user check by looking for the existence of
	* a session scope bean under the specified name.  If this bean is not
	* present, control is forwarded to the specified logon page.
	*
	* @exception JspTagException if a JSP exception has occurred
	*/
	public int doEndTag() throws JspTagException
	{
		// Is there a valid user logged on?
		boolean isValid = false;
		HttpSession session = pageContext.getSession();
		if ((session != null) && (session.getAttribute(beanName) != null))
		{
			isValid = true;
		}
		// Forward control based on the results
		if (isValid)
		{
			return (EVAL_PAGE);
		}
		else
		{
			try
			{
				pageContext.forward(this.forwardPage);
			}
			catch (Exception e)
			{
				throw new JspTagException(e.toString());
			}
			return (SKIP_PAGE);
		}
	}

	/*********************************************************************************************************************/
	/**
	* Release any acquired resources.
	*/
	public void release()
	{
		super.release();
	}
}