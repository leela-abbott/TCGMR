package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.action.form.*;
import org.apache.struts.action.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class UpdateModelSelection extends TCGMAction
{
	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		if ( this.isSessionValid(request) )
		{
			TCGMState state = (TCGMState) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_STATE);
			TCGMMngModelsForm mf = (TCGMMngModelsForm) form;
			int modelId = Integer.parseInt( mf.getModelSelected() );

			ModelMngr mm = new ModelMngr();
			String model = TCGMConstants.NONE_SELECTED;

			try
			{
				model = mm.getModelName( this.getUserToken(request), modelId );
				state.setCurrentModel(model, modelId);
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
			catch (TCGMException te)
			{
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
		}

		return mapping.findForward(this.getForward());
	}

	/**
	 * Default Constructor
	 */
	public UpdateModelSelection()
	{
		super();
	}
}