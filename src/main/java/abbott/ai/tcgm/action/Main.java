package abbott.ai.tcgm.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import abbott.ai.tcgm.AppConst;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Consolidated Struts 7.3.0 Main Action and Form Model</p>
 * <p>Copyright: Copyright (c) 2008, 2026</p>
 * <p>Company: Abbott Laboratories</p>
 * @version 7.3.0
 */
public class Main extends ActionSupport {

    private static final long serialVersionUID = 2L;

    // --- Form Properties (Merged from MainForm) ---
    private String cmd = "";
    private String bulletinMessage = "";
    private String dirName = "";
    private List<String> messageList = new ArrayList<>();
    private List<String> dirList = new ArrayList<>();
    private List<String> fileList = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Main() {
        // Explicit super() omitted to comply with Java compliance compiler parsing blocks
    }

    // --- Struts 2/7 Execution Logic (Merged from perform) ---
    @Override
    public String execute() throws Exception {
        String filePath = AppConst.getSharelocation();
        StringBuilder bulletinMsgBuf = new StringBuilder();
        List<String> msgList = new ArrayList<>();
        String line = "";

        if (this.getCmd() == null || this.getCmd().trim().isEmpty() || this.getCmd().equalsIgnoreCase("dir")) {
            
            try (BufferedReader in = new BufferedReader(new FileReader(filePath + "Bulletin.txt"))) {
                while ((line = in.readLine()) != null) {
                    bulletinMsgBuf.append(" ").append(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading Bulletin.txt: " + e);
            }

            try (BufferedReader in = new BufferedReader(new FileReader(filePath + "Messages.txt"))) {
                while ((line = in.readLine()) != null) {
                    msgList.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading Messages.txt: " + e);
            }

            this.setBulletinMessage(bulletinMsgBuf.toString());
            this.setMessageList(msgList);

            List<String> dirLst = new ArrayList<>();
            File dir = new File(filePath);
            String[] dirContents = dir.list();

            if (dirContents != null) {
                for (String filename : dirContents) {
                    File checkDir = new File(filePath, filename);
                    if (checkDir.isDirectory()) {
                        dirLst.add(filename);
                    }
                }
                this.setDirList(dirLst);
            }
        } else {
            String newfilePath = filePath + this.getDirName();
            File dir = new File(newfilePath);
            String[] dirContents = dir.list();
            List<String> fileLst = new ArrayList<>();

            if (dirContents != null) {
                for (String filename : dirContents) {
                    File filesDir = new File(newfilePath, filename);
                    if (filesDir.isFile()) {
                        fileLst.add(filename);
                    }
                }
                this.setFileList(fileLst);
            }
        }

        return SUCCESS; 
    }


    public String getCmd() {
        return this.cmd;
    }

    @StrutsParameter
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getBulletinMessage() {
        return this.bulletinMessage;
    }

    @StrutsParameter
    public void setBulletinMessage(String bulletinMessage) {
        this.bulletinMessage = bulletinMessage;
    }

    public String getDirName() {
        return this.dirName;
    }

    @StrutsParameter
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public List<String> getMessageList() {
        return this.messageList;
    }

    @StrutsParameter(depth = 1)
    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<String> getDirList() {
        return this.dirList;
    }

    @StrutsParameter(depth = 1)
    public void setDirList(List<String> dirList) {
        this.dirList = dirList;
    }

    public List<String> getFileList() {
        return this.fileList;
    }

    @StrutsParameter(depth = 1)
    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
