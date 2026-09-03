/*
 * Created on Apr 23, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.form;

import java.util.ArrayList;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MainForm extends TCGMForm{
	
	private String bulletinMessage="";
	private ArrayList messageList=new ArrayList();
	private ArrayList dirList=new ArrayList();
	private String dirName="";
	private ArrayList fileList=new ArrayList();

	/**
	 * @return
	 */
	public ArrayList getMessageList() {
		return messageList;
	}

	

	/**
	 * @param list
	 */
	public void setMessageList(ArrayList list) {
		messageList = list;
	}

	/**
	 * @return
	 */
	public String getBulletinMessage() {
		return bulletinMessage;
	}

	/**
	 * @param string
	 */
	public void setBulletinMessage(String string) {
		bulletinMessage = string;
	}

	/**
	 * @return
	 */
	public ArrayList getDirList() {
		return dirList;
	}

	/**
	 * @param list
	 */
	public void setDirList(ArrayList list) {
		dirList = list;
	}

	/**
	 * @return
	 */
	public String getDirName() {
		return dirName;
	}

	/**
	 * @param string
	 */
	public void setDirName(String string) {
		dirName = string;
	}

	/**
	 * @return
	 */
	public ArrayList getFileList() {
		return fileList;
	}

	/**
	 * @param list
	 */
	public void setFileList(ArrayList list) {
		fileList = list;
	}

}
