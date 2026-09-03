package abbott.ai.tcgm.action.bpc;

import org.apache.struts.action.*;
//import org.apache.log4j.*;
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
public class BpcsTranDelete extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcsTranDelete()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcsTran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isModelSelected(request))
			{
				BpcsTranForm bpcsTranForm = (BpcsTranForm)form;//cast the form that was passed in to the correct type for this action

				bpcsTranForm.processCmd(mapping,request);

				BpcsMngr bpcsMngr = new BpcsMngr(); //create the helper class that will handle the processing
				ModelMngr modelMngr = new ModelMngr();

				try
				{
					bpcsTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					// Username must be set first before using helper methods
					/*************************************************************
					*	Added by Uday on 03/20/2006 to provide the user(Analyst)
					*   the option to publish the records of any user. Start
					**************************************************************/
					if (bpcsTranForm.getUserSelected() == null)
					{
						if(bpcsTranForm.getSearchObject().getBpcs().getCreateLog().getUserName().equals(""))
						{
							bpcsTranForm.getSearchObject().getBpcs().getCreateLog().setUserName(userToken.getUserid());
							bpcsTranForm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(bpcsTranForm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						bpcsTranForm.getSearchObject().getBpcs().getCreateLog().setUserName(bpcsTranForm.getUserSelected());
					}
					if (bpcsTranForm.getUserSelected().equalsIgnoreCase("ALL"))
					{	
						bpcsTranForm.getSearchObject().getBpcs().getCreateLog().setUserName(null);
						bpcsTranForm.setUserSelected("ALL");
					}

					/*************************************************************
					*	Added by Uday on 03/20/2006 to provide the user(Analyst)
					*   the oprion to publish the records of any user. End
					**************************************************************/

					if(bpcsTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_ALL) )
					{
						bpcsMngr.deleteAllBpcsTran(	userToken,	bpcsTranForm.getSearchObject());

					}
					else if(bpcsTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_SELECTED) )
					{
						bpcsMngr.deleteSelectedBpcsTran(userToken,bpcsTranForm.getBpcsTranList());
					}
					else if(! bpcsTranForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcsTran.delete"));
					session.setAttribute("deleteMessage","Transaction Record(s) Deleted");

					bpcsTranForm.getAddNew().getBpcs().setModelIdInt(this.getState(request).getCurrentModelId());
					bpcsTranForm.getAddNew().getBpcs().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					
					bpcsTranForm.getPagingFilter().setTotalRecordsInSet(bpcsMngr.getCount(userToken,bpcsTranForm.getSearchObject()));

					bpcsTranForm.setBpcsTranList(bpcsMngr.getBpcsTran(userToken,bpcsTranForm.getSearchObject(),bpcsTranForm.getPagingFilter(),bpcsTranForm.getSortObject()));
					bpcsTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					bpcsTranForm.getSearchObject().getBpcs().getCreateLog().setUserName(userToken.getUserid());//03/23/2006 Udaya B Aravapalli	
					bpcsTranForm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
//				catch(TCGMException ex)
//				{
//					this.logger.error(ex.toString(),ex);
//					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
//					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
//				}
				catch(TCGMUpdateWithBlankUsernameException ex)
				{
					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.update.with.blank.username"));
					try
					{
						bpcsTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						bpcsTranForm.getPagingFilter().setTotalRecordsInSet(bpcsMngr.getCount(userToken,bpcsTranForm.getSearchObject()));
						bpcsTranForm.setBpcsTranList(bpcsMngr.getBpcsTran(userToken,bpcsTranForm.getSearchObject(),bpcsTranForm.getPagingFilter(),bpcsTranForm.getSortObject()));
						bpcsTranForm.getSearchObject().getBpcs().getCreateLog().setUserName(userToken.getUserid());//03/23/2006 Udaya B Aravapalli
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
					catch(TCGMException exc)
					{
						this.logger.error(exc.toString(),exc);
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exc);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
					}
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
		this.logger.debug("BpcsTranDelete Forward" + this.getForward());
		return mapping.findForward(this.getForward());
	}
}