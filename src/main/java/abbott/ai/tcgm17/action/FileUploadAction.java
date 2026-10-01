package abbott.ai.tcgm17.action;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.FileTransfer;

public class FileUploadAction extends TCGMAction implements SessionAware {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(FileUploadAction.class);

    private File theFile;
    private String theFileContentType;
    private String theFileFileName;
    private String dirName;
    private HashMap<String, Object> dirs;
    private String strDirectory;
    private String cmd = "";

    private Map<String, Object> session;

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
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
            if ("Upload".equalsIgnoreCase(cmd)) {
                if (theFile != null && theFileFileName != null && !theFileFileName.isEmpty()) {
                    this.cmd = "";
                    fileTransfer.upload(theFile, theFileFileName, strDirectory);
                    addActionMessage(getText("success.fileupload.copied"));
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
            } else {
                this.dirs = fileTransfer.getDirectories(user.getRole().getAccessLevel());
            }

            logger.debug("FileUpload Action Forward: success");
            return SUCCESS;

        } catch (TCGMException tcgme) {
            logger.error(tcgme.getMessage(), tcgme);
            session.put(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return "exception";
        }
    }

    public File getTheFile() { 
        return theFile; 
    }

    @StrutsParameter
    public void setTheFile(File theFile) { 
        this.theFile = theFile; 
    }

    public String getTheFileContentType() { 
        return theFileContentType; 
    }

    @StrutsParameter
    public void setTheFileContentType(String theFileContentType) { 
        this.theFileContentType = theFileContentType; 
    }

    public String getTheFileFileName() { 
        return theFileFileName; 
    }

    @StrutsParameter
    public void setTheFileFileName(String theFileFileName) { 
        this.theFileFileName = theFileFileName; 
    }

    public String getDirName() { 
        return dirName; 
    }

    @StrutsParameter
    public void setDirName(String dirName) { 
        this.dirName = dirName; 
    }

    public HashMap<String, Object> getDirs() { 
        return dirs; 
    }

    @StrutsParameter(depth = 2)
    public void setDirs(HashMap<String, Object> dirs) { 
        this.dirs = dirs; 
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
