package abbott.ai.tcgm17.action.asr;

import java.io.IOException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.AsrForm;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Asr;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;
import abbott.ai.tcgm17.action.TCGMAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class AsrMaint extends TCGMAction
{
	private static Logger myLogger = LogManager.getLogger( "AsrMaint" );
	/**
	 * Default Constructor
	 */
	public AsrMaint()
	{
		super();
	}

	/**
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public String execute() throws Exception
    {
        HttpServletRequest request = getRequest();
		myLogger.debug("Executing perform() method in AsrMaint.");
		String methodName = "perform";
		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.clearActionErrors();
		Object form = session.getAttribute("asrForm");

		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			addActionError(getText("error.asr.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				AsrForm asrForm = (AsrForm)form;//cast the form that was passed in to the correct type for this action
				
				asrForm.processCmd();
				if (asrForm!=null && asrForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_ADV_FILTER) )  {
						request.getSession().setAttribute("asrTranAdvFilter", asrForm);
						asrForm.reset();
						this.setForward(TCGMConstants.FORWARD_ADVANCEDFILTER);
				}
				else{				

				AsrMngr asrMngr = new AsrMngr(); //create the helper class that will handle the processing

				try
				{
					asrForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					if(asrForm.getErrs().equalsIgnoreCase("on")){
						asrForm.setAsrList(asrForm.getAsrErrorList());
						asrForm.getPagingFilter().setTotalRecordsInSet(asrForm.getAsrErrorList().size());
						asrForm.setErrs("");
						this.addActionError(getText("error.list"));
					}else{
					asrForm.getPagingFilter().setTotalRecordsInSet(asrMngr.getCount(this.getUserToken(request),asrForm.getSearchObject()));
					/* Checking for the value of the CMD, if it is empty means its a first 
					 * time loading the page. Showing empty records during the initial load.
					 */
					if(asrForm.getCmd().equalsIgnoreCase("")){
						asrForm.setAsrList((Vector<Asr>) createEmptyAsrRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
					}else{
						asrForm.setAsrList(asrMngr.getAsr(this.getUserToken(request),asrForm.getSearchObject(),asrForm.getPagingFilter(),asrForm.getSortObject()));
					}
					asrForm.setAsrErrorList(new Vector());
					}
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMException ex)
				{
					this.logger.error(ex.toString(),ex);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			   }
			}
		}
		//if errors exist then save them into the request
		if(! this.getActionErrors().isEmpty())
		{
			// errors are managed by ActionSupport.addActionError()
			addActionError("Please select a model");
			addActionMessage("Please select====");
		}

		//forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return this.getForward();
	}
	

}