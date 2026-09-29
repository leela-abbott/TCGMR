package abbott.ai.tcgm.action;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletRequestAware;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.User;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Upgraded Struts 7.3.0 Session Termination Handler Action</p>
 * <p>Company: Abbott Laboratories</p>
 * @version 7.3.0
 */
public class Logout extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 3L;
    private HttpServletRequest request;

    /**
     * Default Constructor
     */
    public Logout() {
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String execute() throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute(TCGMConstants.SESSION_NAME_USER);
            if (user != null) {
                try {
                	SQLUtil.closeCachedConnection(user.getUserToken());
                } catch (Exception e) {
                    System.out.println("Exception closing cached database session connection: " + e);
                }
            }

            session.invalidate();
        }

        addActionMessage(getText("success.logout"));

        return SUCCESS; 
    }
}
