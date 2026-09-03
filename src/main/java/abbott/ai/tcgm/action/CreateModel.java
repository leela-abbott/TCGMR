package abbott.ai.tcgm.action;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class CreateModel extends TCGMAction
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
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		try
		{
			if( this.isSessionValid(request))
			{
				//Create the new model
				// Populate model object with parameters from form
				errors.clear();
				ModelMngr mm = new ModelMngr();
				TCGMCreateModelForm cmf = (TCGMCreateModelForm) form;
				mm.createModel(this.getUserToken(request), cmf.getModelFromForm() );

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMDuplicateItemException dupex)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.model.create.duplicate") );
			this.saveErrors(request, errors);
			this.setForward(TCGMConstants.FORWARD_FAILURE);
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		return mapping.findForward(this.getForward());
	}

	/**
	 * Default Constructor
	 */
	public CreateModel()
	{
		super();
	}
}