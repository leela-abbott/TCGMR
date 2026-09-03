package abbott.ai.tcgm.action.bpc;

import org.apache.struts.action.*;
import org.apache.log4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class BpcsDelete extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcsDelete()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcs.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if( this.isSessionValid(request))
		{
			if( this.isModelSelected(request))
			{
				BpcsForm bpcsForm = (BpcsForm)form;//cast the form that was passed in to the correct type for this action
//		A.Winter - 7/26/05			
		  	    boolean duplicate = true;
		  	    
				bpcsForm.processCmd(mapping,request);

				BpcsMngr bpcsMngr = new BpcsMngr(); //create the helper class that will handle the processing
				try
				{
					if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_ALL) )
					{
//		A.Winter - 7/26/05 - added boolean return							
						duplicate = bpcsMngr.addAllBpcsToTrans(this.getUserToken(request),bpcsForm.getSearchObject(),TCGMConstants.ACT_CD_DEL);
					}
					else if(bpcsForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_SELECTED) )
					{
//		A.Winter - 7/26/05 - added boolean return						
						duplicate = bpcsMngr.addSelectedBpcsToTrans(this.getUserToken(request),bpcsForm.getBpcsList(),TCGMConstants.ACT_CD_DEL);
					}
					else if(! bpcsForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
//	A.Winter 7/26/05 - start		
					if(duplicate)
					{
					     errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcsTran.insert"));
					}
					else
					{   
						 errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
//	A.Winter 7/26/05 - end			
					}
					bpcsForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					bpcsForm.getPagingFilter().setTotalRecordsInSet(bpcsMngr.getCount(this.getUserToken(request),bpcsForm.getSearchObject()));
					bpcsForm.setBpcsList(bpcsMngr.getBpcs(this.getUserToken(request),bpcsForm.getSearchObject(),bpcsForm.getPagingFilter(),bpcsForm.getSortObject()));

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