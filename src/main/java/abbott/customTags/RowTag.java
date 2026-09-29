package abbott.customTags;

import java.io.IOException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts2.views.jsp.IteratorStatus;

public final class RowTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    protected final static String QUOTE = "\"";

    protected String oddColor = null;
    protected String evenColor = null;
    protected String oddStyleClass = null;
    protected String evenStyleClass = null;
    protected String align = null;
    protected String valign = null;
    protected String id = null;
    protected int rowNum = 1;

    public String getOddColor() {
        return this.oddColor;
    }

    public void setOddColor(String color) {
        this.oddColor = color;
    }

    public int getRowNum() {
        return (this.rowNum);
    }

    public void setRowNum(int rowNumber) {
        this.rowNum = rowNumber;
    }

    public String getEvenColor() {
        return (this.evenColor);
    }

    public void setEvenColor(String color) {
        this.evenColor = color;
    }

    public String getOddStyleClass() {
        return (this.oddStyleClass);
    }

    public void setOddStyleClass(String styleClass) {
        this.oddStyleClass = styleClass;
    }

    public String getEvenStyleClass() {
        return (this.evenStyleClass);
    }

    public void setEvenStyleClass(String styleClass) {
        this.evenStyleClass = styleClass;
    }

    public String getAlign() {
        return (this.align);
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getValign() {
        return (this.valign);
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    @Override
    public String getId() {
        return (this.id);
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        StringBuilder buffer = new StringBuilder(500);
        buffer.append("<tr");
        prepareAttributes(buffer);
        buffer.append(">");

        if (bodyContent != null) {
            buffer.append(bodyContent.getString().trim());
        }

        buffer.append("</tr>");

        JspWriter writer = pageContext.getOut();
        try {
            writer.print(buffer.toString());
        } catch (IOException e) {
            throw new JspException("Exception in RowTag doEndTag(): " + e.toString(), e);
        }

        return EVAL_PAGE;
    }

    protected void prepareAttributes(StringBuilder buffer) throws JspException {
        boolean evenNumber = (getRowNumber() % 2) == 0;
        buffer.append(prepareBgcolor(evenNumber));
        buffer.append(prepareClass(evenNumber));
        buffer.append(prepareId());
        buffer.append(prepareAttribute("align", align));
        buffer.append(prepareAttribute("valign", valign));
    }

    protected String prepareAttribute(String attribute, String value) {
        return value == null ? "" : " " + attribute + "=" + QUOTE + value + QUOTE;
    }

    protected String prepareId() {
        if (this.getId() != null && !this.getId().trim().equals("")) {
            return " id=" + QUOTE + this.getId() + this.getRowNumber() + QUOTE;
        }
        return "";
    }

    protected String prepareBgcolor(boolean evenNumber) {
        if (evenNumber) {
            return prepareAttribute("bgcolor", evenColor);
        } else {
            return prepareAttribute("bgcolor", oddColor);
        }
    }

    protected String prepareClass(boolean evenNumber) {
        if (evenNumber) {
            return prepareAttribute("class", evenStyleClass);
        } else {
            return prepareAttribute("class", oddStyleClass);
        }
    }

    protected int getRowNumber() {
        Object statusObj = pageContext.findAttribute("status");
        if (statusObj instanceof IteratorStatus) {
            return ((IteratorStatus) statusObj).getIndex() + 1;
        }
        return getRowNum();
    }

    @Override
    public void release() {
        super.release();
        this.oddColor = null;
        this.evenColor = null;
        this.oddStyleClass = null;
        this.evenStyleClass = null;
        this.align = null;
        this.valign = null;
        this.id = null;
        this.rowNum = 1;
    }
}