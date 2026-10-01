package abbott.ai.tcgm17.action;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.FileTransfer;

public class DeleteFileAction extends TCGMAction implements SessionAware {

    private static final Logger logger = LogManager.getLogger(DeleteFileAction.class);

    private String fileName = "";
    private HashMap<String, String> fileListDisplay = new HashMap<>();
    private ArrayList<String> dirList = new ArrayList<>();
    private String dirName = "";
    private String cmd = "";

    private Map<String, Object> session;

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    public String execute() {
        if (session == null) {
            return "login";
        }

        User user = (User) session.get(TCGMConstants.SESSION_NAME_USER);
        if (user == null) {
            return "login";
        }

        int accessLevel = user.getRole().getAccessLevel();
        String filePath = AppConst.getSharelocation();

        try {
            if ("deletefile".equalsIgnoreCase(cmd) && !fileName.isEmpty()) {
                if (dirName != null && !dirName.isEmpty()) {
                    filePath = filePath + dirName + File.separator;
                }
                File fileDelete = new File(filePath + fileName);
                if (fileDelete.exists()) {
                    fileDelete.delete();
                    this.cmd = "file";
                    FileTransfer fileTrns = new FileTransfer();
                    this.fileListDisplay = fileTrns.getFiles(filePath);
                }
            } else if ("dir".equalsIgnoreCase(cmd)) {
                File dir = new File(filePath);
                String[] dirListArray = dir.list();
                ArrayList<String> dirLst = new ArrayList<>();
                if (dirListArray != null) {
                    for (String filename : dirListArray) {
                        File checkDir = new File(filePath, filename);
                        if (checkDir.isDirectory()) {
                            if (accessLevel == 3 || accessLevel == 5) {
                                if (filename.equals(TCGMConstants.OPEN_ITEMS)) {
                                    dirLst.add(filename);
                                }
                            } else {
                                if (!filename.equals(TCGMConstants.OPEN_ITEMS)) {
                                    dirLst.add(filename);
                                }
                            }
                        }
                    }
                    this.dirList = dirLst;
                }
            } else if ("file".equalsIgnoreCase(cmd)) {
                String newfilePath = filePath + dirName + File.separator;
                FileTransfer fileTrns = new FileTransfer();
                this.fileListDisplay = fileTrns.getFiles(newfilePath);
            }

            return SUCCESS;

        } catch (TCGMException tcgme) {
            logger.error(tcgme.getMessage(), tcgme);
            session.put(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
            return "exception";
        }
    }

    public int getFileListSize() {
        return this.fileListDisplay != null ? this.fileListDisplay.size() : 0;
    }

    public String getFileName() { 
        return fileName; 
    }

    @StrutsParameter
    public void setFileName(String fileName) { 
        this.fileName = fileName; 
    }

    public HashMap<String, String> getFileListDisplay() { 
        return fileListDisplay; 
    }

    @StrutsParameter(depth = 2)
    public void setFileListDisplay(HashMap<String, String> fileListDisplay) { 
        this.fileListDisplay = fileListDisplay; 
    }

    public ArrayList<String> getDirList() { 
        return dirList; 
    }

    @StrutsParameter(depth = 2)
    public void setDirList(ArrayList<String> dirList) { 
        this.dirList = dirList; 
    }

    public String getDirName() { 
        return dirName; 
    }

    @StrutsParameter
    public void setDirName(String dirName) { 
        this.dirName = dirName; 
    }

    public String getCmd() { 
        return cmd; 
    }

    @StrutsParameter
    public void setCmd(String cmd) { 
        this.cmd = cmd; 
    }
}
