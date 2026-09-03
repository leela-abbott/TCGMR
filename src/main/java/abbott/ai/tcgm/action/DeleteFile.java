/*
 * Created on Jul 10, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.DeleteFileForm;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.FileTransfer;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DeleteFile extends TCGMAction {

	public ActionForward perform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DeleteFileForm mainForm = (DeleteFileForm) form;//cast the form that was passed in to the correct type for this action
		String filePath = AppConst.getSharelocation();//"S:\\MFGACCT\\TCGMTEST\\";
		String bulletinMsg = "";
		ArrayList messageList = new ArrayList();
		String line = "";
		User user=(User)request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);
		int accessLevel = user.getRole().getAccessLevel();
		
		try {

			if (mainForm.getCmd().equalsIgnoreCase("deletefile")
					&& !(mainForm.getFileName().equals(""))) {
				if (mainForm.getDirName() != null) {
					filePath = filePath + mainForm.getDirName() + "\\";
				}
				File fileDelete = new File(filePath + mainForm.getFileName());
				if (fileDelete.exists()) {
					fileDelete.delete();
					mainForm.setCmd("file");
					FileTransfer fileTrns = new FileTransfer();
					mainForm.setFileListDisplay(fileTrns.getFiles(filePath));
				}
			} else if (mainForm.getCmd().equalsIgnoreCase("dir")) {
				File dir = new File(filePath);
				File checkDir = null;
				String[] dirList = dir.list();
				ArrayList dirLst = new ArrayList();
				if (dirList != null) {
					for (int i = 0; i < dirList.length; i++) {
						// Get filename of file or directory
						String filename = dirList[i];
						checkDir = new File(filePath, filename);
						if (checkDir.isDirectory()) {
							//dirLst.add(filename);
							if(accessLevel==3 || accessLevel==5){
								if(filename.equals(TCGMConstants.OPEN_ITEMS)){
									dirLst.add(filename);
								}
							}else{
								if(!filename.equals(TCGMConstants.OPEN_ITEMS)){
									dirLst.add(filename);
								}
							}
						}
						checkDir = null;
					}
					mainForm.setDirList(dirLst);
				}
				dir = null;
			} else if (mainForm.getCmd().equalsIgnoreCase("file")) {
				String newfilePath = filePath + mainForm.getDirName() + "\\";
				FileTransfer fileTrns = new FileTransfer();
				mainForm.setFileListDisplay(fileTrns.getFiles(newfilePath));
			}

		} catch (TCGMException tcgme) {
			this.logger.error(tcgme.toString(), tcgme);
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}

		if (!this.errors.empty()) {
			this.saveErrors(request, errors);
		}

		this.setForward(TCGMConstants.FORWARD_SUCCESS);
		return mapping.findForward(this.getForward());
	}

}