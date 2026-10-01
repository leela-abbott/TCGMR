package abbott.ai.tcgm17.action;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.ProcessMngr;
import jakarta.servlet.http.HttpServletRequest;

public class OpenModel extends TCGMAction
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

	public String execute() throws Exception
	{
		HttpServletRequest request = getRequest();
		try
		{
			if ( this.isSessionValid(request) )
			{
				clearActionErrors();
				int modelIdInt = Integer.parseInt( modelSelected );

				ModelMngr mm = new ModelMngr();
				ProcessMngr pm = new ProcessMngr();
				UserToken ut = this.getUserToken(request);

				if ( pm.isJobPending(ut, modelIdInt) )
				{
					addActionError(getText("error.model.close.job_pending"));
					this.setForward(TCGMConstants.FORWARD_FAILURE);
				}
				else
				{
					TCGMModel model = mm.getClosedModelFromId(ut, modelIdInt, TCGMModel.Type.FACTOR);
					model.setStatus(TCGMModel.Status.OPEN);
					mm.updateModelStatus(ut, model);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		logger.debug(this.className + " - Forward to " + this.getForward());
		return this.getForward();
	}

	public OpenModel()
	{
		super();
	}
}