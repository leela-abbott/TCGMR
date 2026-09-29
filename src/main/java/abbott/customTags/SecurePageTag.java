package abbott.customTags;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;

public final class SecurePageTag extends TagSupport
{
	private static final long serialVersionUID = 1L;

	private final String EQUALS = "=";
	private final String NOT_EQUAL = "!=";
	private final String LESS_THAN = "<";
	private final String LESS_THAN_OR_EQUAL_TO = "<=";
	private final String GREATER_THAN = ">";
	private final String GREATER_THAN_OR_EQUAL_TO = ">=";

	private int userAccessLevel;
	private int requiredAccessLevel;
	private String forwardPage = "";
	private String comparisonType = this.EQUALS;
	private boolean passedComparison = false;

	public SecurePageTag()
	{
	}

	@Override
	public int doStartTag() throws JspTagException
	{
		int returnVal = EVAL_BODY_INCLUDE;

		if (!performComparison())
		{
			returnVal = SKIP_BODY;
		}
		return (returnVal);
	}

	@Override
	public int doEndTag() throws JspTagException
	{
		int returnVal = EVAL_PAGE;

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
					throw new JspTagException(e.toString(), e);
				}
				returnVal = SKIP_PAGE;
			}
		}

		return (returnVal);
	}

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

	@Override
	public void release()
	{
		super.release();
		this.userAccessLevel = 0;
		this.requiredAccessLevel = 0;
		this.forwardPage = "";
		this.comparisonType = this.EQUALS;
		this.passedComparison = false;
	}

	public String getForwardPage()
	{
		if(this.forwardPage == null)
		{
			this.forwardPage = "";
		}
		return this.forwardPage;
	}

	public void setForwardPage(String pForwardPage)
	{
		if(pForwardPage == null)
		{
			pForwardPage = "";
		}
		this.forwardPage = pForwardPage;
	}

	public int getUserAccessLevel()
	{
		return userAccessLevel;
	}

	public void setUserAccessLevel(int userAccessLevel)
	{
		this.userAccessLevel = userAccessLevel;
	}

	public void setRequiredAccessLevel(int requiredAccessLevel)
	{
		this.requiredAccessLevel = requiredAccessLevel;
	}

	public int getRequiredAccessLevel()
	{
		return requiredAccessLevel;
	}

	public void setComparisonType(String comparisonType)
	{
		this.comparisonType = comparisonType;
	}

	public String getComparisonType()
	{
		return comparisonType;
	}
}
