/*
 * Created on Jul 10, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.form;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DeleteFileForm extends TCGMForm {
	
	
	private String fileName="";
	private HashMap fileListDisplay=new HashMap();
	private ArrayList dirList=new ArrayList();
	private String dirName = "";

	
	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		fileName = string;
	}
	
	public int getFileListSize()
	{
		return this.getFileListDisplay().size();
	}

	/**
	 * @return
	 */
	public HashMap getFileListDisplay() {
		return fileListDisplay;
	}

	/**
	 * @param list
	 */
	public void setFileListDisplay(HashMap list) {
		fileListDisplay = list;
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

}
