/*
 * Created on Jul 1, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.struts.upload.FormFile;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FileTransfer implements TCGMMngr {

	public FileTransfer() {
	}

	public String upload(FormFile myFile, String selectedDirectory)
			throws TCGMException {
		String uploadFlag = "";
		String methodName = "upload(FormFile myFile,String selectedDirectory)";

		try {

			String destFileName = myFile.getFileName();
			String fileName = myFile.getFileName();
			String filePath = AppConst.getSharelocation();//"S:\\MFGACCT\\TCGMTEST\\";
			if (!"root".equalsIgnoreCase(selectedDirectory)) {
				filePath = filePath + selectedDirectory + "\\";//AppConst.getFileUploadDirectory() ;
			}
			File fileToCreate = new File(filePath, fileName);

			if (!fileName.equals("")) {
				FileOutputStream fileOutStream = new FileOutputStream(
						fileToCreate);
				fileOutStream.write(myFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e
					.toString());
		} catch (IOException e) {

			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e
					.toString());
		}
		return uploadFlag;
	}

	public String createDirectory(String strDir) throws TCGMException {
		String uploadFlag = "";
		String methodName = "createDirectory(String strDir)";

		try {

			String filePath = AppConst.getSharelocation();//"S:\\MFGACCT\\TCGMTEST\\";//AppConst.getFileUploadDirectory() ;
			/*File fileObj = new File(filePath + strDir);
			if (!fileObj.isDirectory()) {
				new File(filePath + strDir.trim()).mkdir();
				uploadFlag = "success";
			}*/
		    boolean success = (new File(filePath + strDir.trim())).mkdir();
            if (success) {
                uploadFlag = "success";
            }

		}

		catch (Exception e) {

			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e
					.toString());
		}
		return uploadFlag;
	}

	public HashMap getDirectories(int accessLevel) throws TCGMException {
		String uploadFlag = "";
		String methodName = "getDirectories()";
		HashMap map = new HashMap();
		try {
			String filePath = AppConst.getSharelocation();//"S:\\MFGACCT\\TCGMTEST\\";//AppConst.getFileUploadDirectory() ;
			File dir = new File(filePath);
			File checkDir = null;
			String[] dirList = dir.list();
			if (dirList != null) {
				for (int i = 0; i < dirList.length; i++) {
					// Get filename of file or directory
					String filename = dirList[i];
					checkDir = new File(filePath, filename);
					if (checkDir.isDirectory()) {
						if(accessLevel==3 || accessLevel==5){
							if(filename.equals(TCGMConstants.OPEN_ITEMS)){
								map.put(filename, filename);
							}
						}else{
							if(!filename.equals(TCGMConstants.OPEN_ITEMS)){
								map.put(filename, filename);
							}
						}
					}
					checkDir = null;
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e
					.toString());
		}
		return map;
	}

	public HashMap getFiles(String strPath) throws TCGMException {
		String methodName = " getFiles(String strPath)";
		HashMap fileLstdisp = new HashMap();
		try {
			File dir = new File(strPath);
			File filesDir = null;
			String[] dirList = dir.list();

			if (dirList != null) {
				for (int i = 0; i < dirList.length; i++) {
					// Get filename of file or directory
					String filename = dirList[i];
					filesDir = new File(strPath, filename);
					if (filesDir.isFile()) {
						fileLstdisp.put(filename, filename + " ("
								+ filesDir.length() + " KB)");
					}
					filesDir = null;
				}

			}
			dir = null;
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e
					.toString());
		}
		return fileLstdisp;
	}
}