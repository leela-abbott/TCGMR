package abbott.ai.tcgm.action;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import org.apache.struts.action.*;
import org.apache.log4j.Logger;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class CreateFactorModel extends TCGMAction
{
	private static Logger myLogger = Logger.getLogger( "CreateFactorModel" );
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

		this.errors.clear();
		
		try
		{
			if( this.isSessionValid(request))
			{
				//Create the new model
				// Populate model object with parameters from form
				errors.clear();
				ModelMngr mm = new ModelMngr();
				CreateFactorModelForm mscf = (CreateFactorModelForm) form;
				FactorModel model = new FactorModel();
				model.setName( mscf.getCreateModelName() ); // 9-6-05 debug; up to this point, I have the right cycle name
				model.setDesc( mscf.getCreateModelDesc() );
				model.setType( TCGMModel.Type.FACTOR );
				model.setModelCycle( Cycle.getObjectFromName( mscf.getCreateModelCycle() ) );
				model.setModelYear( mscf.getCreateModelYear() ); /* 9-6-05 debug; up to this point, I have the right cycle name
																	and it gets set in the setModelCycle() method */
				if ( mscf.getSelSourceModelId().equals("none") )
				{
					// Create the model from scratch if Copy From value = <none>
					mm.createModel(this.getUserToken(request), model );
				}
				else
				{
					UserToken ut = this.getUserToken(request);  // Goes to this else when the user wants to create by copying a model
					FactorModel baseModel = (FactorModel) mm.getModelFromId(ut, Integer.parseInt( mscf.getSelSourceModelId() ), TCGMModel.Type.FACTOR);
					ModelCopyOptions options = mscf.getModelCopyOptions();
					// 2-7-06 Temporarily set Max Session Time to unimited
					int maxInactIntTime = request.getSession().getMaxInactiveInterval();
					myLogger.info("Max Timeout is = " + maxInactIntTime);
					request.getSession().setMaxInactiveInterval(3600);
					mm.createModel(ut, model, baseModel, options);
					// 2-7-06 Reset MaxInactiveInterval
					request.getSession().setMaxInactiveInterval(maxInactIntTime);
				}
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMDuplicateItemException dupex)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.model.create.duplicate") );
			
			//Sridevi.K 7-19-05 adding code to display error if trying to create a closed model
			//request.setAttribute("duplicateError", new ActionError("error.model.create.duplicate") );			
			//Sridevi.K 7-19-05 End of added code to display error. 
			
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
	public CreateFactorModel()
	{
		super();
	}
}