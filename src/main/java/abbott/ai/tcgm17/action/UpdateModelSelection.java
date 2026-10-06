package abbott.ai.tcgm17.action;

import java.io.IOException;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.TCGMMngModelsForm;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;


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
	
    private String modelSelected;

	public String getModelSelected() {
		return modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		System.out.println("Inside UpdateModelSelection setter=="+modelSelected);
		this.modelSelected = modelSelected;
	}
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
	public String execute() throws Exception
    {
        HttpServletRequest request = getRequest();
		if ( this.isSessionValid(request) )
		{
			TCGMState state = (TCGMState) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_STATE);
			//TCGMMngModelsForm mf = (TCGMMngModelsForm) form;
			int modelId = Integer.parseInt( this.getModelSelected() );

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

		return this.getForward();
	}

	/**
	 * Default Constructor
	 */
	public UpdateModelSelection()
	{
		super();
	}
}