package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.entities.UserToken;
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
public class InitiateEssbase extends TCGMAction
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
		try {

			if ( this.isSessionValid( request) )
			{
				EssbaseMgmtForm emf = (EssbaseMgmtForm) form;
				EssbaseMngr em = new EssbaseMngr();
				String cmd = emf.getCmd();
				
				String strLoadTyp= "";
					if(cmd.equals("RGMALL"))
						strLoadTyp = "ALL";
				if(cmd.equals("RGMPPVSRV"))
						strLoadTyp = "PPV";

				// bypass job mechanism.
				// Initial set to success
				if ( cmd.equals("RGMALL")||cmd.equals("RGMPPVSRV") )
					em.intiateRGMLoad( emf.getSelRGMMonth(), emf.getTxtRGMYear(), emf.getSelRGMVersion(),strLoadTyp);
				else if ( cmd.equals("RTC") )
					em.initiateRTCLoad( emf.getTxtRTCYear(), emf.getSelRTCVersion(), emf.getSelRTCLoadType(), emf.getSelRTCSourceFile(), emf.getSelRTCGeneration());
				else if ( cmd.equals("VCOPY") )
					em.initiateVersionCopy(emf.getTxtVCopyYear(), emf.getSelFVCopyVersion(), emf.getSelTVCopyVersion());
				else if ( cmd.equals("ALOG") )
					em.initiateALOGLoad( emf.getTxtALOGYear(), emf.getSelALOGVersion());
				else
				{
					throw new TCGMException(this.className, "perform", "Cmd: " + cmd, "Invalid Command Submitted to Action");
				}
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

	public InitiateEssbase() {
		super();
	}
}