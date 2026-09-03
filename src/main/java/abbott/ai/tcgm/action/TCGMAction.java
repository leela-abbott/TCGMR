package abbott.ai.tcgm.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.action.form.TCGMProductionForm;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.entities.BpcEx;
import abbott.ai.tcgm.entities.BpcRev;
import abbott.ai.tcgm.entities.Bpcs;
import abbott.ai.tcgm.entities.Notes;
import abbott.ai.tcgm.entities.TCGMState;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ModelMngr;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Base class for all actions in the TCGM application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class TCGMAction extends Action
{
	protected String forward;
	protected static Logger logger = null;
	protected ActionErrors errors = new ActionErrors();
	protected final String className = this.getClass().getName();

	/**
	 * Default Constructor
	 */
	public TCGMAction()
	{
		super();
		this.logger = Logger.getLogger(this.getClass());
	}


	/**
	 * Checks to see if the user session is valid, otherwise returns the action mapping to Login page
	 * Returns a ActionMapping to indicate invalid User Session<br>
	 * @param request HttpServletRequest
	 * @return true if TCGM_USER is found in the session
	 */
	protected boolean isSessionValid(HttpServletRequest request)
	{
		boolean isSessionValid = true;
		if ( request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER) == null)
		{
			isSessionValid = false;
			errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.user.session.invalid"));
			this.forward = TCGMConstants.G_FORWARD_LOGIN;
			this.saveErrors(request,errors);
		}
		return isSessionValid;
	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @return true if a model has been selected
	 */
	protected boolean isModelSelected(HttpServletRequest request)
	{
		boolean isModelSelected = true;
		if(this.getState(request).getCurrentModelName().equals(TCGMConstants.NONE_SELECTED))
		{
			isModelSelected = false;
// Alex Winter 06/17/05 fix duplicate messages - start
			if(this.errors.empty())
			{
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.none_selected"));
			}
// Alex Winter 06/17/05 fix duplicate messages - end
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
			this.saveErrors(request, errors);
		}
		return isModelSelected;
	}

	/**
	 *
	 * @param request
	 * @return
	 */
// 10-17-03 Returns true if the model the user selected is Open
	protected boolean isModelOpen(HttpServletRequest request)
	{
		boolean isModelOpen = true;
		if(this.getState(request).getCurrentModelName().equals(TCGMConstants.NONE_SELECTED))
		{
			isModelOpen = false;
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.none_selected"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
			this.saveErrors(request, errors);
		}
		else
		{
			ModelMngr mm = new ModelMngr();
			UserToken ut = this.getUserToken(request);
			String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
			try
			{
				String modelStatus = mm.getModelStatus(ut, modelId).trim();
				if(!modelStatus.equals("OPEN"))
				{
					isModelOpen = false;
					logger.info("modelStatus = " + modelStatus);
				}
			}
			catch(TCGMException ex)
			{
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
		}
		return isModelOpen;
	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @return true if a model has been selected
	 */
	protected boolean isRateSetSelected(HttpServletRequest request)
	{
		boolean isRateSetSelected = true;
		if(this.getState(request).getCurRateSetName().equals(TCGMConstants.NONE_SELECTED))
		{
			isRateSetSelected = false;
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.rateset.none_selected"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_RATE_SET);
			this.saveErrors(request, errors);
		}
		return isRateSetSelected;
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	protected User getSessionUser(HttpServletRequest request)
	{
		return (User)request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);
	}
	/**
	 *
	 * @param request HttpServletRequest
	 * @return UserToken
	 */
	protected UserToken getUserToken(HttpServletRequest request)
	{
		return getSessionUser(request).getUserToken();

	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @return TCGMState
	 */
	protected TCGMState getState(HttpServletRequest request)
	{
		TCGMState state = (TCGMState)request.getSession().getAttribute(TCGMConstants.SESSION_NAME_STATE);
		if(state == null)
		{
			state = new TCGMState();
		}
		return state;
	}

		protected boolean isCmdValid(TCGMProductionForm myForm) {
			if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) && !myForm.getCmd().equals("CANCEL") )
				return true;
			else
				return false;
		}


	/**
	 * If a url parameter named "cmd" exists this method will return the value assigned to it.
	 * @param request HttpServletRequest
	 * @return cmd value
	 */
	protected String getCmd(HttpServletRequest request)
	{
		String cmd = (String)request.getParameter(TCGMConstants.URL_PARM_CMD);

		if(cmd == null)
		{
			cmd = "";
		}
		return cmd;
	}

	/**
	 *
	 * @return page or action to forward to
	 */
	protected String getForward()
	{
		return this.forward;
	}

	/**
	 *
	 * @param forward page or action to forward to
	 */
	protected void setForward(String forward)
	{
		this.forward = forward;
	}

	/**
	 *
	 * @param logger Log4j logger object
	 */
	protected void setLogger(Logger logger)
	{
		this.logger = logger;
	}

	/**
	 *
	 * @return Log4j logger
	 */
	protected Logger getLogger()
	{
		return this.logger;
	}

	/**
	 *
	 * @param errors ActionErrors
	 */
	protected void setErrors(ActionErrors errors)
	{
		this.errors = errors;
	}

	/**
	 *
	 * @return errors
	 */
	protected ActionErrors getErrors()
	{
		return this.errors;
	}
	
	/**
	 *
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector createEmptyAsrRecs(String modelId, String datasetTableId) throws TCGMException
	{
		String methodName = "createEmptyAsrRecs";

		Vector vec = new Vector();

		try
		{
			for (int i=0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++)
			{
				Asr asr = new Asr();
				asr.setModelId(modelId);
				asr.setDatasetTableId(datasetTableId);
				vec.add(asr);
			}
			return vec;
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}
	/**
	 *
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector createEmptyBpcRecs(String modelId, String datasetTableId) throws TCGMException
	{
		String methodName = "createEmptyBpcRecs";

		Vector vec = new Vector();

		try
		{
			for (int i=0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++)
			{
				Bpcs bpcs = new Bpcs();
				bpcs.setModelId(modelId);
				bpcs.setDatasetTableId(datasetTableId);
				bpcs.setBegPeriod("1");
				bpcs.setEndPeriod("12");
				vec.add(bpcs);
			}
			return vec;
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}		

	/**
	 *
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector createEmptyBpcRevRecs(String modelId, String datasetTableId) throws TCGMException
	{
		String methodName = "createEmptyBpcRecs";

		Vector vec = new Vector();

		try
		{
			for (int i=0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++)
			{
				BpcRev bpcRev = new BpcRev();
				bpcRev.setModelId(modelId);
				bpcRev.setDatasetTableId(datasetTableId);
				bpcRev.setBegPeriod("1");
				bpcRev.setEndPeriod("12");
				vec.add(bpcRev);
			}
			return vec;
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}			

	/**
	 *
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector createEmptyBpcExRecs(String modelId, String datasetTableId) throws TCGMException
	{
		String methodName = "createEmptyExRecs";

		Vector vec = new Vector();

		try
		{
			for (int i=0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++)
			{
				BpcEx bpcEx = new BpcEx();
				bpcEx.setModelId(modelId);
				bpcEx.setDatasetTableId(datasetTableId);
				bpcEx.setBegPeriod("1");
				bpcEx.setEndPeriod("12");
				vec.add(bpcEx);
			}
			return vec;
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}			

	/**
	 *
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector createEmptyNotesRecs(String modelId, String datasetTableId) throws TCGMException
	{
		String methodName = "createEmptyNotesRecs";

		Vector vec = new Vector();

		try
		{
			for (int i=0; i < TCGMConstants.MAX_RECS_TO_RETRIEVE; i++)
			{
				Notes notes = new Notes();
				notes.setModelId(modelId);
				notes.setDatasetTableId(datasetTableId);
				vec.add(notes);
			}
			return vec;
		}
		catch(Exception e)
		{
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}			
			
}