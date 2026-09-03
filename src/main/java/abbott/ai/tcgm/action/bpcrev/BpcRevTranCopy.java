package abbott.ai.tcgm.action.bpcrev;

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
public class BpcRevTranCopy extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public BpcRevTranCopy()
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
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.bpcrevtran.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);

			if(this.isModelSelected(request))
			{
				BpcRevTranForm bpcRevTranForm = (BpcRevTranForm)form;//cast the form that was passed in to the correct type for this action

				bpcRevTranForm.processCmd(mapping,request);

				BpcRevMngr bpcRevMngr = new BpcRevMngr(); //create the helper class that will handle the processing
				ModelMngr modelMngr = new ModelMngr();

				try
				{
					bpcRevTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);

					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Copy the records(from one model to other)
					*   of any user. Start
					**************************************************************/
					if (bpcRevTranForm.getUserSelected() == null)
					{
						if(bpcRevTranForm.getSearchObject().getBpcRev().getCreateLog().getUserName().equals(""))
						{
							bpcRevTranForm.getSearchObject().getBpcRev().getCreateLog().setUserName(userToken.getUserid());
							bpcRevTranForm.setUserSelected(userToken.getUserid());
						}
					}
					else if (!(bpcRevTranForm.getUserSelected().equalsIgnoreCase("ALL")))
					{
						bpcRevTranForm.getSearchObject().getBpcRev().getCreateLog().setUserName(bpcRevTranForm.getUserSelected());
					}

					if (bpcRevTranForm.getUserSelected().equalsIgnoreCase("ALL"))
					{	
						bpcRevTranForm.getSearchObject().getBpcRev().getCreateLog().setUserName(null);
						bpcRevTranForm.setUserSelected("ALL");
					}

					/*************************************************************
					*	Added by Uday on 03/21/2006 to provide the user(Analyst)
					*   the option to Copy the records(from one model to other)
					*   of any user. End
					**************************************************************/
					if(bpcRevTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_ALL) )
					{
						bpcRevMngr.copyAllBpcRevTran(userToken,bpcRevTranForm.getSearchObject(),bpcRevTranForm.getModelSelected());
					}
					else if(bpcRevTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY_SELECTED) )
					{
						bpcRevMngr.copySelectedBpcRevTran(userToken,bpcRevTranForm.getBpcRevTranList(),bpcRevTranForm.getModelSelected());
					}
					else if(! bpcRevTranForm.getCmd().trim().equals(""))
					{
						throw new TCGMException(className,methodName,"Invalid command in action");
					}
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.bpcrevtran.copied"));
					bpcRevTranForm.getAddNew().getBpcRev().setModelIdInt(this.getState(request).getCurrentModelId());
					bpcRevTranForm.getAddNew().getBpcRev().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					bpcRevTranForm.getPagingFilter().setTotalRecordsInSet(bpcRevMngr.getCount(userToken,bpcRevTranForm.getSearchObject()));
					bpcRevTranForm.setBpcRevTranList(bpcRevMngr.getBpcRevTran(userToken,bpcRevTranForm.getSearchObject(),bpcRevTranForm.getPagingFilter(),bpcRevTranForm.getSortObject()));
					bpcRevTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
					bpcRevTranForm.getSearchObject().getBpcRev().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
					bpcRevTranForm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				}
				catch(TCGMDuplicateItemException ex)
				{

					this.logger.error(ex.toString(),ex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate"));
					try
					{
						bpcRevTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
						bpcRevTranForm.getPagingFilter().setTotalRecordsInSet(bpcRevMngr.getCount(userToken,bpcRevTranForm.getSearchObject()));
						bpcRevTranForm.setBpcRevTranList(bpcRevMngr.getBpcRevTran(userToken,bpcRevTranForm.getSearchObject(),bpcRevTranForm.getPagingFilter(),bpcRevTranForm.getSortObject()));
						bpcRevTranForm.getSearchObject().getBpcRev().getCreateLog().setUserName(userToken.getUserid()); //03/23/2006 Udaya B Aravapalli
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