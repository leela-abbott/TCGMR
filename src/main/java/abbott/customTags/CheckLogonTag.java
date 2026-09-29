package abbott.customTags;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;

public final class CheckLogonTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    
    private String beanName = null;
    private String forwardPage = null;

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String pBeanName) {
        this.beanName = pBeanName;
    }

    public String getForwardPage() {
        return this.forwardPage;
    }

    public void setForwardPage(String pForwardPage) {
        this.forwardPage = pForwardPage;
    }

    @Override
    public int doStartTag() throws JspTagException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspTagException {
        boolean isValid = false;
        HttpSession session = pageContext.getSession();
        
        if ((session != null) && (session.getAttribute(beanName) != null)) {
            isValid = true;
        }
        
        if (isValid) {
            return EVAL_PAGE;
        } else {
            try {
                String ctxPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
                String targetPath = forwardPage;
                
                if (!targetPath.startsWith("/")) {
                    targetPath = "/" + targetPath;
                }
                
                if (targetPath.endsWith(".jsp") && !targetPath.contains("openLogin")) {
                    ((jakarta.servlet.http.HttpServletResponse) pageContext.getResponse()).sendRedirect(ctxPath + "/openLogin.action");
                } else {
                    pageContext.forward(targetPath);
                }
            } catch (Exception e) {
                throw new JspTagException(e.toString(), e);
            }
            return SKIP_PAGE;
        }
    }

    @Override
    public void release() {
        super.release();
        this.beanName = null;
        this.forwardPage = null;
    }
}
