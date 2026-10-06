package abbott.ai.tcgm17.action;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.CreateFactorModelForm;
import abbott.ai.tcgm.entities.Cycle;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.ModelCopyOptions;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;
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
public class CreateFactorModel extends TCGMAction
{
	private static Logger myLogger = LogManager.getLogger( "CreateFactorModel" );
	
	
	private String createModelName;
    private String createModelDesc;
    private String createModelCycle;
    private String createModelYear;
    private String selSourceModelId;
    
    private int createModelKeepBpcsPeriod;
    private int createModelKeepBPExPeriod;
    private int createModelKeepExchExPeriod;
    private boolean createModelClearRevDates;
    private boolean createModelClearBPCS;
    private boolean createModelClearFreezeCosts;
    private CreateFactorModelForm createFactorModelForm;
    
	public String getCreateModelName() {
		return createModelName;
	}

	@StrutsParameter
	public void setCreateModelName(String createModelName) {
		this.createModelName = createModelName;
	}

	public String getCreateModelDesc() {
		return createModelDesc;
	}

	@StrutsParameter
	public void setCreateModelDesc(String createModelDesc) {
		this.createModelDesc = createModelDesc;
	}

	
	public String getCreateModelCycle() {
		return createModelCycle;
	}

	@StrutsParameter
	public void setCreateModelCycle(String createModelCycle) {
		this.createModelCycle = createModelCycle;
	}

	public String getCreateModelYear() {
		return createModelYear;
	}

	@StrutsParameter
	public void setCreateModelYear(String createModelYear) {
		this.createModelYear = createModelYear;
	}

	public String getSelSourceModelId() {
		return selSourceModelId;
	}
	
	@StrutsParameter
	public void setSelSourceModelId(String selSourceModelId) {
		this.selSourceModelId = selSourceModelId;
	}

	public CreateFactorModelForm getCreateFactorModelForm() {
		return createFactorModelForm;
	}
	
	@StrutsParameter
	public void setCreateFactorModelForm(CreateFactorModelForm createFactorModelForm) {
		this.createFactorModelForm = createFactorModelForm;
	}

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

		this.clearActionErrors();
		
		try
		{
			if( this.isSessionValid(request))
			{
				//Create the new model
				// Populate model object with parameters from form
				clearActionErrors();
				ModelMngr mm = new ModelMngr();
				//CreateFactorModelForm mscf = (CreateFactorModelForm) form;
				FactorModel model = new FactorModel();
				model.setName( this.getCreateModelName() ); // 9-6-05 debug; up to this point, I have the right cycle name
				model.setDesc( this.getCreateModelDesc() );
				model.setType( TCGMModel.Type.FACTOR );
				model.setModelCycle( Cycle.getObjectFromName( this.getCreateModelCycle() ) );
				model.setModelYear( this.getCreateModelYear() ); /* 9-6-05 debug; up to this point, I have the right cycle name
																	and it gets set in the setModelCycle() method */
				if ( this.getSelSourceModelId().equals("none") )
				{
					// Create the model from scratch if Copy From value = <none>
					mm.createModel(this.getUserToken(request), model );
				}
				else
				{
					UserToken ut = this.getUserToken(request);  // Goes to this else when the user wants to create by copying a model
					FactorModel baseModel = (FactorModel) mm.getModelFromId(ut, Integer.parseInt( this.getSelSourceModelId() ), TCGMModel.Type.FACTOR);
					ModelCopyOptions options = this.getModelCopyOptions();
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
			addActionError(getText("error.model.create.duplicate"));
			
			//Sridevi.K 7-19-05 adding code to display error if trying to create a closed model
			//request.setAttribute("duplicateError", new ActionError("error.model.create.duplicate") );			
			//Sridevi.K 7-19-05 End of added code to display error. 
			
			// errors are managed by ActionSupport.addActionError()			
			this.setForward(TCGMConstants.FORWARD_FAILURE);
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		return this.getForward();
	}

	/**
	 * Default Constructor
	 */
	public CreateFactorModel()
	{
		super();
	}
	
	 public ModelCopyOptions getModelCopyOptions() {
	        ModelCopyOptions options = new ModelCopyOptions();

	        options.setClearBpcs( this.isCreateModelClearBPCS() );
	        options.setClearRevisions( this.isCreateModelClearRevDates() );
	        options.setClearFreezeCost( this.isCreateModelClearFreezeCosts() );
	        options.setKeepBpExPeriod( this.getCreateModelKeepBPExPeriod() );
	        options.setKeepBpPeriod( this.getCreateModelKeepBpcsPeriod() );
	        options.setKeepExchRateExPeriod( this.getCreateModelKeepExchExPeriod() );

	        return options;

	    }
	 
	 @StrutsParameter
	 public void setCreateModelClearBPCS(boolean createModelClearBPCS) {
	        this.createModelClearBPCS = createModelClearBPCS;
	    }
	    public boolean isCreateModelClearBPCS() {
	        return createModelClearBPCS;
	    }
	    @StrutsParameter
	    public void setCreateModelClearRevDates(boolean createModelClearRevDates) {
	        this.createModelClearRevDates = createModelClearRevDates;
	    }
	    public boolean isCreateModelClearRevDates() {
	        return createModelClearRevDates;
	    }
	    @StrutsParameter
	    public void setCreateModelKeepBpcsPeriod(int createModelKeepBpcsPeriod) {
	        this.createModelKeepBpcsPeriod = createModelKeepBpcsPeriod;
	    }
	    public int getCreateModelKeepBpcsPeriod() {
	        return createModelKeepBpcsPeriod;
	    }
	    @StrutsParameter
	    public void setCreateModelKeepBPExPeriod(int createModelKeepBPExPeriod) {
	        this.createModelKeepBPExPeriod = createModelKeepBPExPeriod;
	    }
	    public int getCreateModelKeepBPExPeriod() {
	        return createModelKeepBPExPeriod;
	    }
	    @StrutsParameter
	    public void setCreateModelKeepExchExPeriod(int createModelKeepExchExPeriod) {
	        this.createModelKeepExchExPeriod = createModelKeepExchExPeriod;
	    }
	    public int getCreateModelKeepExchExPeriod() {
	        return createModelKeepExchExPeriod;
	    }
	    @StrutsParameter
	    public void setCreateModelClearFreezeCosts(boolean createModelClearFreezeCosts) {
	        this.createModelClearFreezeCosts = createModelClearFreezeCosts;
	    }
	    public boolean isCreateModelClearFreezeCosts() {
	        return createModelClearFreezeCosts;
	    }
}