package abbott.ai.tcgm17.action;

import java.io.IOException;
import java.util.Vector;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.CreateFactorModelForm;
import abbott.ai.tcgm.action.form.MngFactorModelsForm;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
///import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class MngFactorModels extends TCGMAction
{
	/**
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */
	public String execute() throws Exception
    {
        HttpServletRequest request = getRequest();
		try
		{
			this.clearActionErrors();
						
			if( isSessionValid(request) )
			{
				UserToken ut = this.getUserToken(request);
				this.clearActionErrors();
				ModelMngr mm = new ModelMngr();

				//Setup empty factor model as search object to return all open factor models
				FactorModel f = new FactorModel();
				f.setStatus(TCGMModel.Status.OPEN);
				Vector v = mm.getModels(ut, f);

				//Setup model selection form
				MngFactorModelsForm msf = new MngFactorModelsForm();
				msf.setModels ( v );
				if (!(request.getParameter("showModels")==null))
				msf.setShowModels(request.getParameter("showModels"));
				else msf.setShowModels("open");
				f.setStatus(TCGMModel.Status.CLOSED);
				Vector vecClosedModels = mm.getModels(ut, f);
				msf.setClosedModels(vecClosedModels);
				request.setAttribute("mngFactorModelsForm", msf );
				//Setup model creation form
				CreateFactorModelForm mcf = new CreateFactorModelForm();
				mcf.setFactorModels( v );
				mcf.setShowModels(msf.getShowModels());
				request.setAttribute("createFactorModelForm", mcf);
				
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return this.getForward();
	}

	/**
	 * Default constructor
	 */
	public MngFactorModels()
	{
		super();
	}

}