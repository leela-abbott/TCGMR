package abbott.ai.tcgm17.action;

import java.util.Map;
import java.io.File;
import java.util.List;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.FileTransfer;

public class FileUploadAction extends TCGMAction implements SessionAware, UploadedFilesAware {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(FileUploadAction.class);

	private String dirName;
	private Map<String, Object> dirs;
	private String strDirectory;
	private String cmd = "";

	private List<UploadedFile> uploadedFiles;
	private Map<String, Object> session;

	@Override
	public void withSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	@StrutsParameter(depth = 1)
	public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public String execute() {
		if (session == null) {
			addActionError(getText("error.fileupload.form.missing"));
			return "selectModel";
		}

		User user = (User) session.get(TCGMConstants.SESSION_NAME_USER);
		if (user == null) {
			return "login";
		}

		FileTransfer fileTransfer = new FileTransfer();

		try {
			this.dirs = fileTransfer.getDirectories(user.getRole().getAccessLevel());

			if ("Upload".equalsIgnoreCase(cmd)) {
				this.cmd = "";
				if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
					UploadedFile uploadedFile = uploadedFiles.get(0);
					File fileContent = null;

					if (uploadedFile.getContent() instanceof File) {
						fileContent = (File) uploadedFile.getContent();
					} else {
						String absolutePath = uploadedFile.getAbsolutePath();
						if (absolutePath != null) {
							fileContent = new File(absolutePath);
						}
					}

					// FIX: Swapped uploadedFile.getName() out for uploadedFile.getOriginalName()
					String fileName = uploadedFile.getOriginalName();

					if (fileContent != null && fileName != null && !fileName.isEmpty()) {
						fileTransfer.upload(fileContent, fileName, strDirectory);
						addActionMessage(getText("success.fileupload.copied"));
					} else {
						logger.error("Failed to materialize upload file instance from MultiPart wrapper.");
						addActionError("File processing failed. Internal file instantiation error.");
						return INPUT;
					}
				} else {
					logger.warn("UploadedFiles list received by Action is empty.");
					addActionError("No file data received. Ensure file doesn't exceed allowed size metrics.");
					return INPUT;
				}

			} else if ("Create".equalsIgnoreCase(cmd)) {
				this.cmd = "";
				String strReturn = fileTransfer.createDirectory(dirName);
				this.dirs = fileTransfer.getDirectories(user.getRole().getAccessLevel());

				if ("success".equalsIgnoreCase(strReturn)) {
					addActionMessage(getText("success.createdir"));
				} else {
					addActionError(getText("error.createdir"));
				}
				this.dirName = "";
			}

			logger.debug("FileUpload Action Forward: success");
			return SUCCESS;

		} catch (TCGMException tcgme) {
			logger.error(tcgme.getMessage(), tcgme);
			session.put(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
			return "exception";
		} catch (Exception e) {
			logger.error("Unexpected runtime error during upload execution: " + e.getLocalizedMessage(), e);

			String params = "cmd=" + cmd + ", strDirectory=" + strDirectory;
			TCGMException wrapper = new TCGMException("FileUploadAction", "execute", params,
					"Upload failed due to internal error: " + e.getMessage(), e);

			session.put(TCGMConstants.SESSION_NAME_EXCEPTION, wrapper);
			return "exception";
		}
	}

	public String getDirName() {
		return dirName;
	}

	@StrutsParameter
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public Map<String, Object> getDirs() {
		return dirs;
	}

	public String getStrDirectory() {
		return strDirectory;
	}

	@StrutsParameter
	public void setStrDirectory(String strDirectory) {
		this.strDirectory = strDirectory;
	}

	public String getCmd() {
		return cmd;
	}

	@StrutsParameter
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
