package abbott.ai.tcgm.action.bpcrev;

import org.apache.struts.action.*;
//import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Vector;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class BpcRevMaint extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcRevMaint()
	{
		super();
	}

	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		String methodName = "perform";

		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.errors.clear();
		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcrev.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			if(this.isModelSelected(request))
			{
				BpcRevForm bpcRevForm = (BpcRevForm)form;//cast the form that was passed in to the correct type for this action

				bpcRevForm.processCmd(mapping,request);
				if (bpcRevForm!=null && bpcRevForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_ADV_FILTER) )  {
						request.getSession().setAttribute("bpcRevTranAdvFilter", bpcRevForm);
						bpcRevForm.reset(mapping,request);
						this.setForward(TCGMConstants.FORWARD_ADVANCEDFILTER);
				}else{				
				BpcRevMngr bpcRevMngr = new BpcRevMngr(); //create the helper class that will handle the processing

				try
				{
					bpcRevForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					if(bpcRevForm.getErrs().equalsIgnoreCase("on")){
						bpcRevForm.setBpcRevList(bpcRevForm.getBpcRevErrorList());
						bpcRevForm.getPagingFilter().setTotalRecordsInSet(bpcRevForm.getBpcRevErrorList().size());
						bpcRevForm.setErrs("");
						this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.list"));
					}else{
					bpcRevForm.getPagingFilter().setTotalRecordsInSet(bpcRevMngr.getCount(this.getUserToken(request),bpcRevForm.getSearchObject()));
					
					if (bpcRevForm.getCmd().equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_EMPTY))
					{
						bpcRevForm.setBpcRevList(createEmptyBpcRevRecs(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID));
					}
					else
					{
						bpcRevForm.setBpcRevList(bpcRevMngr.getBpcRev(this.getUserToken(request),bpcRevForm.getSearchObject(),bpcRevForm.getPagingFilter(),bpcRevForm.getSortObject()));
					}
					bpcRevForm.setBpcRevErrorList(new Vector());
					}
					bpcRevForm.getAddNew().getBpcRev().setBegPeriod("1");
					bpcRevForm.getAddNew().getBpcRev().setEndPeriod("12");
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
		if(! this.errors.empty())
		{
			this.saveErrors(request,errors);
		}

		//forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}
}