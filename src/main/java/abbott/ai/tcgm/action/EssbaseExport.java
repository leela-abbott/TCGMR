package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
//import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.process.*;
//import abbott.ai.tcgm.process.javajob.*;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;
import org.apache.log4j.*;
//import abbott.ai.tcgm.action.*;
//import abbott.ai.tcgm.action.form.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class EssbaseExport extends TCGMAction
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
	private static String name = "EssbaseExport";
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.action.EssbaseExport" );

	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		String method = "perform()";
		try
		{
			if ( this.isSessionValid( request) )
			{
				UserToken ut = this.getUserToken(request);
				// IEssbaseForm myForm = (IEssbaseForm) form;

				}
				else {
					EssbaseMngr em = new EssbaseMngr();
					// 8-13-03 bd; I need to get my type, ersion, and year from the form
					//myForm = (MngAnalysisModelsForm) form;
					//myForm.getCmd()
					// 8-14-03; After further investigation via searching struts-config & the entire path
					//          of this project, I don't think this EssbaseExport action is used. So
					//          don't worry about changing it for the JobSendAnlFlexEssbase job.
					em.exportEssbaseAnalysis("modelId", "year", "version", "A1");

					//String reportId = myForm.

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

	public EssbaseExport() {
		super();
	}

}