package abbott.ai.tcgm17.action;
import java.io.IOException;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.TCGMMngModelsForm;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.ProcessMngr;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public class DeleteModel extends TCGMAction
{
	
	
	
private String modelSelected;

	

	public String getModelSelected() {
		return modelSelected;
	}

	@StrutsParameter
	public void setModelSelected(String modelSelected) {
		System.out.println("Inside setter=="+modelSelected);
		this.modelSelected = modelSelected;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String execute() throws Exception
    {
        HttpServletRequest request = getRequest();
		try
		{
			if ( this.isSessionValid(request) )
			{
				clearActionErrors();
				int modelIdInt = Integer.parseInt( modelSelected );

//				TCGMMngModelsForm modelForm = null;
//				modelForm = (TCGMMngModelsForm) form;
//				int modelIdInt = Integer.parseInt( modelForm.getModelSelected() );

				ModelMngr mm = new ModelMngr();
				ProcessMngr pm = new ProcessMngr();
				UserToken ut = this.getUserToken(request);

				if ( pm.isJobPending(ut, modelIdInt) )
				{
					addActionError(getText("error.model.delete.job_pending"));
					// errors are managed by ActionSupport.addActionError()
					this.setForward(TCGMConstants.FORWARD_FAILURE);
				}
				else
				{
					String modelId = Integer.toString(modelIdInt);
					TCGMModel model = mm.getModelFromId(ut,modelIdInt,TCGMModel.Type.FACTOR);
					model.setStatus(TCGMModel.Status.DELETED);
					int intCurModel = this.getState(request).getCurrentModelId();
					if(modelIdInt==intCurModel){
						this.getState(request).setCurrentModelName(TCGMConstants.NONE_SELECTED);
					}
					mm.updateModelStatus(ut,model);
					// errors are managed by ActionSupport.addActionError()
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		logger.debug("DeleteModel - Forward to " + this.getForward() );
		return this.getForward();
	}

	/**
	 * Default Constructor
	 */
	public DeleteModel()
	{
		super();
	}
}