package abbott.ai.tcgm.action.asr;

import org.apache.struts.action.*;
//import org.apache.log4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;

public class AsrUsageUpload extends TCGMAction {

	public AsrUsageUpload() {

		super();

	}

	public ActionForward perform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

	
		HttpSession session = request.getSession();

		this.errors.clear();

		if (form == null) {
			
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.asrUsageForm.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		}

		else if (this.isSessionValid(request)) {

			UserToken userToken = this.getUserToken(request);
			boolean nonDuplicate = true;
			User user = (User) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);

			AsrUsageForm asrUsageForm = (AsrUsageForm) form;

			asrUsageForm.processCmd(mapping, request);

			AsrMngr asrMngr = new AsrMngr();
			
			try {
				Date uploadDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy hhmmss a");
				String fileName = asrUsageForm.getTheFile().getFileName();
				String destFileName = user.getUserid() + "--" + formatter.format(uploadDate) + "--" + fileName;

				String filePath = AppConst.getFileUploadDirectory();
				File fileToCreate = new File(filePath, fileName);

				if (!fileName.equals("") && (!fileToCreate.exists())) {
					FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(asrUsageForm.getTheFile().getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
				fileToCreate.renameTo(new File(filePath + destFileName));           

				asrMngr.upload(userToken, filePath + destFileName, asrUsageForm.getCycle());
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("success.asrUsage.upload"));
								
				String procName = "AFFASR_LOAD";
				String methodName = "processAffASRDataFeed(File file, String procName)";				
				String processCall = "{call " + "TCGM" + "." + procName + "(?, ?, ?, ?) }";
				String UploadfileName = "ASRTXALL-"+asrUsageForm.getCycle()+".asrusage";				
				
				CallableStatement cs = null;
				Connection conn = null;
				String supAffID=null;							
				try {
					conn = SQLUtil.openConnection();
					cs = conn.prepareCall(processCall);										
					cs.setString(1,"IMPORT_DIRECTORY_ASR"); 				
					cs.setString(2, UploadfileName);											
					cs.setString(3, "tcgm");
					cs.registerOutParameter(4, Types.CHAR);
					boolean retValue = cs.execute();					
					supAffID=cs.getString(4).trim();
									
					if(!supAffID.isEmpty()) {						
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("success.asrUsage.proc"));					
					}
					
					  else { 
						  errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("success.asrUsage.proc.error"));
					  }
					 					
				}
				catch (SQLException sqe)
				{
					throw new TCGMException( this.className,methodName,sqe.getMessage() );
				}
				finally
				{
					SQLUtil.closeCS(cs);
					SQLUtil.closeConnection(conn);
				}
				
				asrUsageForm.getPagingFilter().setTotalRecordsInSet(
						asrMngr.getCount(this.getUserToken(request), asrUsageForm.getSearchObject()));
				long count =asrUsageForm.getPagingFilter().getTotalRecordsInSet();
				
				asrUsageForm.setAffASRList(asrMngr.getAsrUsage(this.getUserToken(request),
						asrUsageForm.getSearchObject(), asrUsageForm.getPagingFilter(), asrUsageForm.getSortObject()));

				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}

			catch (TCGMException ex) {
				
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
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
