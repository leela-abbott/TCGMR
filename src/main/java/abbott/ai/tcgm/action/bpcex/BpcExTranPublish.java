package abbott.ai.tcgm.action.bpcex;

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
public class BpcExTranPublish extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcExTranPublish()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcextran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isModelSelected(request))
			{
				BpcExTranForm bpXTrnFrm = (BpcExTranForm)form;//cast the form that was passed in to the correct type for this action

				bpXTrnFrm.processCmd(mapping,request);

				BpcExMngr bpcExMngr = new BpcExMngr(); //create the helper class that will handle the processing
				ModelMngr modelMngr = new ModelMngr();
				try
				{
					bpXTrnFrm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					// Username must be set first before using helper methods
					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Publish the records of any user. Start
					**************************************************************/
					if (bpXTrnFrm.getUserSelected() == null)
					{
						if(bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().getUserName().equals(""))
						{
							bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(userToken.getUserid());
							bpXTrnFrm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(bpXTrnFrm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(bpXTrnFrm.getUserSelected());
					}
					if (bpXTrnFrm.getUserSelected().equalsIgnoreCase("ALL"))
					{	
						bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(null);
						bpXTrnFrm.setUserSelected("ALL");
					}
					
					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Publish the records of any user. End
					**************************************************************/

					if(bpXTrnFrm.getCmd().equals(TCGMConstants.URL_PARM_VAL_PUBLISH_SELECTED) )
					{
						bpcExMngr.publishSelectedBpcExTran(userToken,bpXTrnFrm.getBpcExTranList(), true);
					}
					else if(bpXTrnFrm.getCmd().equals(TCGMConstants.URL_PARM_VAL_UNPUBLISH_SELECTED) )
					{
						bpcExMngr.publishSelectedBpcExTran(userToken,bpXTrnFrm.getBpcExTranList(), false);
					}					
					else if(bpXTrnFrm.getCmd().equals(TCGMConstants.URL_PARM_VAL_PUBLISH_ALL) )
					{
						bpcExMngr.publishAllBpcExTran(userToken,bpXTrnFrm.getSearchObject(), true);
					}
					else if(bpXTrnFrm.getCmd().equals(TCGMConstants.URL_PARM_VAL_UNPUBLISH_ALL) )
					{
						bpcExMngr.publishAllBpcExTran(userToken,bpXTrnFrm.getSearchObject(), false);
					}
					else if(! bpXTrnFrm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
					if(bpXTrnFrm.getCmd().equals(TCGMConstants.URL_PARM_VAL_PUBLISH_ALL)||
					   bpXTrnFrm.getCmd().equals(TCGMConstants.URL_PARM_VAL_PUBLISH_SELECTED)){
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.published"));
					 }else{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcextran.unpublished"));
					 }

					bpXTrnFrm.getAddNew().getBpcEx().setModelIdInt(this.getState(request).getCurrentModelId());
					bpXTrnFrm.getAddNew().getBpcEx().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					bpXTrnFrm.getPagingFilter().setTotalRecordsInSet(bpcExMngr.getCount(userToken,bpXTrnFrm.getSearchObject()));

					bpXTrnFrm.setBpcExTranList(bpcExMngr.getBpcExTran(userToken,bpXTrnFrm.getSearchObject(),bpXTrnFrm.getPagingFilter(),bpXTrnFrm.getSortObject()));
					bpXTrnFrm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
					bpXTrnFrm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMUpdateWithBlankUsernameException ex)
				{
					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.update.with.blank.username"));
					try
					{
						bpXTrnFrm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						bpXTrnFrm.getPagingFilter().setTotalRecordsInSet(bpcExMngr.getCount(userToken,bpXTrnFrm.getSearchObject()));
						bpXTrnFrm.setBpcExTranList(bpcExMngr.getBpcExTran(userToken,bpXTrnFrm.getSearchObject(),bpXTrnFrm.getPagingFilter(),bpXTrnFrm.getSortObject()));
						bpXTrnFrm.getSearchObject().getBpcEx().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
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
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}
}