/*
 * Created on Jul 1, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.form;

import java.util.HashMap;

import org.apache.struts.upload.FormFile;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileUploadForm extends TCGMForm {
	private FormFile theFile;
	
	private String dirName;
	
	private HashMap dirs;
	
	private String strDirectory;
	/**
	 * @return
	 */
	public FormFile getTheFile() {
		return theFile;
	}

	/**
	 * @param file
	 */
	public void setTheFile(FormFile file) {
		theFile = file;
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
	public HashMap getDirs() {
		return dirs;
	}

	/**
	 * @param map
	 */
	public void setDirs(HashMap map) {
		dirs = map;
	}

	/**
	 * @return
	 */
	public String getStrDirectory() {
		return strDirectory;
	}

	/**
	 * @param string
	 */
	public void setStrDirectory(String string) {
		strDirectory = string;
	}

}
