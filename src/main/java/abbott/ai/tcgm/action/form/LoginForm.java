package abbott.ai.tcgm.action.form;

import org.apache.struts.action.*;
import javax.servlet.http.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Action form for the login page</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */

public class LoginForm extends TCGMForm
{
	private String userid="";
	private String password="";

	/**
	 * Default Constructor
	 */
	public LoginForm()
	{
	}

	/**
	 *
	 * @return userid
	 */
	public String getUserid()
	{
		return this.userid;
	}

	/**
	 *
	 * @param userid
	 */
	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	/**
	 *
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 *
	 * @return password
	 */
	public String getPassword()
	{
		return this.password;
	}

	/**
	 *
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.userid = "";
		this.password = "";
	}

	/**
	 *
	 * @param mapping
	 * @param request
	 * @return
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if(this.getUserid() == null || this.getUserid().trim().equals(""))
		{
			errors.add("userid",new ActionError("error.login.required.userid"));
		}

		if(this.getPassword() == null || this.getPassword().trim().equals(""))
		{
			errors.add("password",new ActionError("error.login.required.password"));
		}

		if(errors.empty())
		{
			return null;
		}
		else
		{
			return errors;
		}
	}
}