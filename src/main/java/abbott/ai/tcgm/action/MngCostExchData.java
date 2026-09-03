package abbott.ai.tcgm.action;

import java.io.IOException;
//import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MngCostExchData extends TCGMAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		try
		{
			if( this.isSessionValid(request))
			{
				UserToken ut = this.getUserToken(request);
				CostExchDataMngr sdm = new CostExchDataMngr();
				MngCostExchDataForm msdf = new MngCostExchDataForm();
				msdf.setDatasets( sdm.getCostExchSets(ut) );
				request.setAttribute("mngCostExchDataForm", msdf);
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}

		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}

	public MngCostExchData() {
		super();
	}

}