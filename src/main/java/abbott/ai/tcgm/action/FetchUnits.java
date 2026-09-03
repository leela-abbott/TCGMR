package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;
import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class FetchUnits extends TCGMAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		try
		{
			if( this.isSessionValid(request))
			{
				UserToken ut = this.getUserToken(request);
				UnitMngr um = new UnitMngr();
				MngUnitsForm umf = (MngUnitsForm) form;
				String sendingCycleVal="";
				String frthVal="";
				String genDataGroup = "+0"; // GDG = +0 for all other system/cycles except Actuals
				if(umf.getSelFetchSystem().equals("ACT"))
				{
					genDataGroup = "+1"; // GDG = +1 for Actuals
				}		
				if(!umf.getTxtFetchVal().equals(""))
				{
					if(umf.getSelFetchSystem().length()==3)
			        {
			        	frthVal=umf.getSelFetchSystem().substring(0,1);
			        }else{
			        	frthVal=umf.getSelFetchSystem().substring(3,4);
			        }
					sendingCycleVal=umf.getTxtFetchVal()+frthVal;
				}else{
					if(umf.getSelFetchSystem().length()==3)
			        {
			        	frthVal=umf.getSelFetchSystem().substring(0,1);
			        }
					sendingCycleVal=umf.getSelFetchSystem()+frthVal;
				}
				um.fetchUnits(umf.getTxtFetchYear(), sendingCycleVal, genDataGroup, ut );
                this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.units.fetch", umf.getSelFetchSystem() ) ); // not really an error, just feedback
                this.saveErrors(request, this.errors);

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}

	public FetchUnits() {
		super();
	}

}