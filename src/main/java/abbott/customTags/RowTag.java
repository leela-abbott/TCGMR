package abbott.customTags;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.logic.IterateTag;
/**
 * <p>This tag generates table rows (i.e. &lt;tr&gt;....&lt;/tr&gt; elements) with the
 * background color set differently for alternating odd and even rows. This tag only operates
 * properly if embedded in an IterateTag.</p>
 *
 * <p>The following parameters can be specified for this Tag:</p>
 * <ul>
 * <li><code>oddColor </code> - The color for Odd numbered rows
 * <li><code>evenColor</code> - The color for Even numbered rows
 * <li><code>oddStyleClass</code> - The style class for Odd numbered rows
 * <li><code>evenStyleClass</code> - The style class for Even numbered rows
 * <li><code>align</code> - The alignment for the table row
 * <li><code>valign</code> - The vertical alignment for the table row
 * <li><code>id</code> - The id for the table row
 * </ul>
 *
 * <p>Additionally this tag inherits the Event Handler and Style attributes
 * from the BaseHandlerTag which can also be specified</p>
 *
 * @author Amarda Business Systems Ltd
 * @version 1.1
 *
 * <P>MOD HISTORY</P>
 * <table width="600">
 *     <tr>
 *         <td nowrap>
 *             <strong><u>Modified By</u></strong>
 *         </td>
 *         <td nowrap>
 *             <strong><u>Date</u></strong>
 *         </td>
 *         <td>
 *             <strong><u>Description</u></strong>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             David Fields - Abbott Laboratories
 *         </td>
 *         <td>
 *             11/15/2002
 *         </td>
 *         <td>
 *             Added property for id
 *         </td>
 *     </tr>
 * </table>
 *
 */
public final class RowTag extends org.apache.struts.taglib.html.BaseHandlerTag
{
	protected final static String QUOTE   = "\"";

	/*****************************************************************************************/
	/**
	 *  Color of Odd rows in a table
	 */
	protected String oddColor = null;
	/**
	 *
	 * @return Return the color of Odd rows
	 */
	public String getOddColor()
	{
		return (this.oddColor);
	}
	/**
	 * Set the color of Odd rows
	 * @param color HTML bgcolor value for Odd rows
	 */
	public void setOddColor(String color)
	{
		this.oddColor = color;
	}

	/*****************************************************************************************/
	//Sridevi.K 7/05/2005. Added a new variable rowNum and its get and set methods to this class.
	/**
	 *  Row number for the table row
	 */
	protected int rowNum = 1;
	/**
	 *
	 * @return Return the rowNumber of the row
	 */
	public int getRowNum()
	{
		return (this.rowNum);
	}
	/**
	 * Set the rowNumber of the row for the table row
	 * @param rowNumber for the table row
	 */
	public void setRowNum(int rowNumber)
	{
		this.rowNum = rowNumber;
	}
	//Sridevi.K 7/05/2005. End of new code for the attribute rowNum in the Abbott.tld.

	/*****************************************************************************************/
	/**
	 *  Color of Even rows in a table
	 */
	protected String evenColor = null;
	/**
	 * @return Return the color of Even rows
	 */
	public String getEvenColor()
	{
		return (this.evenColor);
	}
	/**
	 * Set the color of Even rows
	 * @param color HTML bgcolor value for Even rows
	 */
	public void setEvenColor(String color)
	{
		this.evenColor = color;
	}
	/*****************************************************************************************/
	/**
	 *  StyleClass of Odd rows in a table
	 */
	protected String oddStyleClass = null;
	/**
	 * @return Return the Style Class of Odd rows
	 */
	public String getOddStyleClass()
	{
		return (this.oddStyleClass);
	}
	/**
	 * Set the Style Class of Odd rows
	 *
	 * @param styleClass HTML Style Class value for Odd rows
	 */
	public void setOddStyleClass(String styleClass)
	{
		this.oddStyleClass = styleClass;
	}
	/*****************************************************************************************/
	/**
	 *  Style Class of Even rows in a table
	 */
	protected String evenStyleClass = null;
	/**
	 * @return Return the Style Class of Even rows
	 */
	public String getEvenStyleClass()
	{
		return (this.evenStyleClass);
	}
	/**
	 * Set the styleClass of Even rows
	 * @param styleClass HTML Style Class value for Even rows
	 */
	public void setEvenStyleClass(String styleClass)
	{
		this.evenStyleClass = styleClass;
	}
	/*****************************************************************************************/
	/**
	 *  Alignment of the table row
	 */
	protected String align = null;
	/**
	 * @return Return the Alignment
	 */
	public String getAlign()
	{
		return (this.align);
	}
	/**
	 * Set the Alignment
	 * @param align Value for Alignment
	 */
	public void setAlign(String align)
	{
		this.align = align;
	}
	/*****************************************************************************************/
	/**
	 *  Vertical Alignment of the table row
	 */
	protected String valign = null;
	/**
	 * @return Return the Vertical Alignment
	 */
	public String getValign()
	{
		return (this.valign);
	}
	/**
	 * Set the Vertical Alignment
	 * @param valign Value for Vertical Alignment
	 */
	public void setValign(String valign)
	{
		this.valign = valign;
	}
	/*****************************************************************************************/
	/**
	 * Id of the current row.  The index will be appended to this to make it unique
	 */
	protected String id = null;
	/**
	 *
	 * @return id
	 */
	public String getId()
	{
		return (this.id);
	}
	/**
	 * @param id id of the row tag
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	// ----------------------------------------------------- Public Methods

	/**
	 * Start of Tag processing
	 *
	 * @throws JspException if a JSP exception occurs
	 * @return int
	 */
	public int doStartTag() throws JspException
	{
		// Continue processing this page
		return (javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED);
	}

	/**
	 * End of Tag Processing
	 *
	 * @throws JspException if a JSP exception occurs
	 * @return int
	 */
	public int doEndTag() throws JspException
	{
		StringBuffer buffer = new StringBuffer(500);

		// Create a <tr> element based on the parameters
		buffer.append("<tr");

		// Prepare this HTML elements attributes
		prepareAttributes(buffer);

		buffer.append(">");

		// Add Body Content
		if (bodyContent != null)
		{
			buffer.append(bodyContent.getString().trim());
		}

		buffer.append("</tr>");

		// Render this element to our writer
		JspWriter writer = pageContext.getOut();
		try
		{
			writer.print(buffer.toString());
		}
		catch (IOException e)
		{
			throw new JspException("Exception in RowTag doEndTag():" + e.toString());
		}

		return EVAL_PAGE;
	}
	/**
	 * Prepare the attributes of the HTML element
	 * @param buffer StringBuffer
	 */
	protected void prepareAttributes(StringBuffer buffer) throws JspException
	{
		// Determine if it is an "Odd" or "Even" row
		boolean evenNumber = (getRowNumber() % 2) == 0 ? true : false;
		
		// Append bgcolor parameter
		buffer.append(prepareBgcolor(evenNumber));

		// Append CSS class parameter
		buffer.append(prepareClass(evenNumber));

		// Append "id" parameter
		buffer.append(prepareId());

		// Append "align" parameter
		buffer.append(prepareAttribute("align", align));

		// Append "valign" parameter
		buffer.append(prepareAttribute("valign", valign));

		// Append Event Handler details
		buffer.append(prepareEventHandlers());

		// Append Style details
		buffer.append(prepareStyles());
	}
	/*****************************************************************************************/
	/**
	 * Format attribute="value" from the specified attribute & value
	 * @param attribute attribute to prepare
	 * @param value value to use
	 * @return attribute
	 */
	protected String prepareAttribute(String attribute, String value)
	{
		return value == null ? "" : " " + attribute + "=" + QUOTE + value + QUOTE;
	}
	/*****************************************************************************************/
	/**
	 * @return id
	 */
	protected String prepareId()
	{
		String tempId = null;
		tempId = prepareAttribute("id",this.getId());

		if(this.getId() != null && ! this.getId().trim().equals(""))
		{
			tempId = " id=" + QUOTE + this.getId() + this.getRowNumber() + QUOTE;
		}

		return tempId;
	}
	/*****************************************************************************************/
	/**
	 * Format the bgcolor attribute depending on whether
	 * the row is odd or even.
	 *
	 * @param evenNumber Boolean set to true if an even numbered row
	 * @return bgcolor
	 */
	protected String prepareBgcolor(boolean evenNumber)
	{
		if (evenNumber)
		{
			return prepareAttribute("bgcolor", evenColor);
		}
		else
		{
			return prepareAttribute("bgcolor", oddColor);
		}
	}
	/*****************************************************************************************/
	/**
	 * Format the Style sheet class attribute depending on whether
	 * the row is odd or even.
	 *
	 * @param evenNumber Boolean set to true if an even numbered row
	 * @return class
	 */
	protected String prepareClass(boolean evenNumber)
	{
		if (evenNumber)
		{
			return prepareAttribute("class", evenStyleClass);
		}
		else
		{
			return prepareAttribute("class", oddStyleClass);
		}
	}
	/*****************************************************************************************/
	/**
	 * Determine the Row Number - from the IterateTag
	 * @return row number
	 */
	protected int getRowNumber()
	{
		// Determine if embedded in an IterateTag
		Tag tag = findAncestorWithClass(this, IterateTag.class);
		//Sridevi.K 7/05/2005. 
		//Code changed to display alternative shading effect when there is no iterate tag.
		if (tag == null)
		{
			//return 1;
			return (getRowNum());
		}
		//Sridevi.K 7/05/2005. 
		//End of code change to display alternative shadding effect when there is no iterate tag.

		// Determine the current row number.
		IterateTag iterator = (IterateTag)tag;
		return iterator.getIndex() + 1;
	}
	/*****************************************************************************************/
	/**
	 * Release resources after Tag processing has finished.
	 */
	public void release()
	{
		super.release();

		this.oddColor = null;
		this.evenColor = null;
		this.oddStyleClass = null;
		this.evenStyleClass = null;
		this.align = null;
		this.valign = null;
		this.id = null;
	}
}