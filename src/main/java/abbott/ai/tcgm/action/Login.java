package abbott.ai.tcgm.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.struts2.ActionSupport;                 
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;

public class Login extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
    private String userid = "";
    private String password = "";
    private HttpServletRequest request;

    private static final Logger logger = LogManager.getLogger(Login.class);

    public Login() {
        
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void validate() {
        if (this.getUserid() == null || this.getUserid().trim().isEmpty()) {
            addFieldError("userid", getText("error.login.required.userid"));
        }

        if (this.getPassword() == null || this.getPassword().trim().isEmpty()) {
            addFieldError("password", getText("error.login.required.password"));
        }
    }

    @Override
    public String execute() throws Exception {
        HttpSession session = request.getSession();

        User user = new User(this.getUserid(), this.getPassword());
        LoginMngr loginMngr = new LoginMngr();

        try {
            if (loginMngr.authenticateUser(user)) {
                if (loginMngr.authorizeUser(user)) {
                    user.setUserlist(loginMngr.getAllUsers(user));
                    
                    session.setAttribute(TCGMConstants.SESSION_NAME_USER, user);
                    session.setAttribute(TCGMConstants.SESSION_NAME_STATE, new TCGMState());

                    if (user.getRole().getAccessLevel() == 4 && !user.isRptAccess()) {
                        addActionError(getText("error.login.role.failed"));
                        return INPUT; 
                    } else {
                        return "main";
                    }
                } else {
                    addActionError(getText("error.oraclelogin.failed"));
                    return INPUT;
                }
            } else {
                addActionError(getText("error.login.failed"));
                return INPUT;
            }
        } catch (TCGMException tcgme) {
            logger.error(tcgme.toString(), tcgme);

            if (tcgme.getErrorMessage() != null && tcgme.getErrorMessage().contains("ORA-01017")) {
                addActionError(getText("error.login.failed"));
                return INPUT;
            } else {
                request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
                return "exception"; 
            }
        }
    }

    public String getUserid() { return this.userid; }
    
    @StrutsParameter
    public void setUserid(String userid) { this.userid = userid; }

    public String getPassword() { return this.password; }
    
    @StrutsParameter
    public void setPassword(String password) { this.password = password; }
}
