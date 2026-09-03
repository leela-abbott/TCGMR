/*
 * Created on Jul 1, 2008
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
import abbott.ai.tcgm.action.form.FileUploadForm;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.FileTransfer;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileUpload extends TCGMAction {

	public FileUpload() {
		super();
	}

	public ActionForward perform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String methodName = "perform";
		HttpSession session = request.getSession();//get existing session or create a new one if it doesn't exist
		this.errors.clear();

		if (form == null) {
			//errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.fileupload.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		} else if (this.isSessionValid(request)) {
			UserToken userToken = this.getUserToken(request);
			FileUploadForm myForm = (FileUploadForm) form;//cast the form that was passed in to the correct type for this action
			FileTransfer fileTransfer = new FileTransfer(); //create the helper class that will handle the processing
			User user=(User)request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);
			try {
				if (myForm.getCmd().equalsIgnoreCase("Upload")) {
					if (null != myForm.getTheFile()) {
						if (myForm.getTheFile().getFileName().length() > 0) {
							myForm.setCmd("");
							fileTransfer.upload(myForm.getTheFile(), myForm
									.getStrDirectory());
							errors
									.add(
											ActionErrors.GLOBAL_ERROR,
											new ActionError(
													"success.fileupload.copied"));
						}
					}
				} else if (myForm.getCmd().equalsIgnoreCase("Create")) {
					String strReturn = "";
					myForm.setCmd("");
					strReturn = fileTransfer.createDirectory(myForm
							.getDirName());
					myForm.setDirs(fileTransfer.getDirectories(user.getRole().getAccessLevel())); // resetting the directories
					if (strReturn.equalsIgnoreCase("success"))
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								"success.createdir"));
					else
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								"error.createdir"));
					myForm.setDirName("");
				} else {
					myForm.setDirs(fileTransfer.getDirectories(user.getRole().getAccessLevel()));
				}

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			} catch (TCGMException tcgme) {
				this.logger.error(tcgme.toString(), tcgme);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION,
						tcgme);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
		}

		if (!this.errors.empty()) {
			this.saveErrors(request, errors);
		}

		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}

}