/*
 * Created on Jun 5, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.AffAreaDivForm;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AffAreaDivMngr;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AffAreaDivMaint extends TCGMAction {
	public AffAreaDivMaint(){
		
	}
	
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
		{
			String methodName = "perform";

			HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

			this.errors.clear();

			if(form == null)
			{
				//errors is an ActionErrors object defined in TCGMAction
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.affcode.form.missing"));
				this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
			}
			else if(this.isSessionValid(request))
			{
				AffAreaDivForm affCodeForm = (AffAreaDivForm)form;					

				AffAreaDivMngr affCodeMngr = new AffAreaDivMngr();

					try
					{
						if(affCodeForm.getCmd().equalsIgnoreCase("save")){							
							int cnt=affCodeMngr.createAffWanted(affCodeForm.getAffcode());
							if(cnt==0){
								errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.aaffArea.insert"));
								
							}else{
								errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.duplicate.affArea"));
								
							}
						}
						else if(affCodeForm.getCmd().equalsIgnoreCase("delete")){
							affCodeMngr.deleteAffWanted(affCodeForm.getAfflist());							
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.aaffArea.delete"));
							
						}
						affCodeForm.setCmd("");						
						affCodeForm.setAffCodeList(affCodeMngr.getAffcodes());
						affCodeForm.setAfflist(affCodeMngr.getAffWanted());						
						this.setForward(TCGMConstants.FORWARD_SUCCESS);
					}
					catch(TCGMException tcgme)
					{
						this.logger.error(tcgme.toString(),tcgme);
						//this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("exception.currency.maintenance"));
						//this.forward = TCGMConstants.FORWARD_ERROR;
						request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
						this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);					
					}
			}
			//if errors exist then save them into the request
			if(! this.errors.empty())
			{
				this.saveErrors(request,errors);
			}
			
			return mapping.findForward(this.getForward());
		}

}
