package abbott.ai.tcgm.action;

import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave FIelds
 * @version 1.0
 */

public class TCGMActionMapping extends ActionMapping
{
	/**
	 * In almost all cases a login will be required.  If it is not required
	 * for a specific page or action to load then set it to false in the struts-config.xml file.
	 */
	protected boolean loginRequired = true;

	/**
	 * Default Constructor
	 */
	public TCGMActionMapping()
	{
		super();
	}

	/**
	 *
	 * @return login required
	 */
	public boolean getLoginRequired()
	{
		return this.loginRequired;
	}
	/**
	 *
	 * @param loginRequired tells if a valid user login is reqired
	 */
	public void setLoginRequired(boolean loginRequired)
	{
		this.loginRequired = loginRequired;
	}
}