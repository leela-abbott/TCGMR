package abbott.ai.tcgm.action.affbpc;

import org.apache.struts.action.*;
//import org.apache.log4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class AffBpcUpload extends TCGMAction
{
	/**
	 * Default Constructor
	 */
	public AffBpcUpload()
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
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		String methodName = "perform";

		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if(form == null)
		{
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.affBpc.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}
		else if(this.isSessionValid(request))
		{
			UserToken userToken = this.getUserToken(request);
			boolean nonDuplicate = true;
			User user = (User) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);
			AffBpcForm affBpcForm = (AffBpcForm)form;//cast the form that was passed in to the correct type for this action

			affBpcForm.processCmd(mapping,request);

			AffBpcMngr affBpcMngr = new AffBpcMngr(); //create the helper class that will handle the processing
			try
			{
				Date uploadDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat(
                "MMddyyyy hhmmss a");
        String fileName = affBpcForm.getTheFile().getFileName();
        String destFileName = user.getUserid() + "--"
                + formatter.format(uploadDate) + "--" + fileName;
        
        String filePath = AppConst.getFileUploadDirectory();
        File fileToCreate = new File(filePath, fileName);
        
	        if (!fileName.equals("") && (!fileToCreate.exists())) {
	            FileOutputStream fileOutStream = new FileOutputStream(
	                    fileToCreate);
	            fileOutStream.write(affBpcForm.getTheFile().getFileData());
	            fileOutStream.flush();
	            fileOutStream.close();
	        }
            fileToCreate.renameTo(new File(filePath + destFileName));
//            fileToCreate=null;
				
					 affBpcMngr.upload(userToken,filePath + destFileName,affBpcForm.getCycle());
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.affBpc.upload"));

				affBpcForm.getPagingFilter().setTotalRecordsInSet(affBpcMngr.getCount(this.getUserToken(request),affBpcForm.getSearchObject()));
				affBpcForm.setAffBpcList(affBpcMngr.getAffBpc(this.getUserToken(request),affBpcForm.getSearchObject(),affBpcForm.getPagingFilter(),affBpcForm.getSortObject()));

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
			catch(TCGMException ex)
			{
				this.logger.error(ex.toString(),ex);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
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