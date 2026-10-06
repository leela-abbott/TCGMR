package abbott.ai.tcgm.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.exception.TCGMException;

public class FileTransfer implements TCGMMngr {

	public FileTransfer() {
	}

	public String upload(File myFile, String destFileName, String selectedDirectory)
			throws TCGMException {
		String uploadFlag = "";
		String methodName = "upload(File myFile, String destFileName, String selectedDirectory)";

		try {
			if (myFile != null && destFileName != null && !destFileName.isEmpty()) {
				String filePath = AppConst.getSharelocation();
				if (!"root".equalsIgnoreCase(selectedDirectory)) {
					filePath = filePath + selectedDirectory + File.separator;
				}
				Path destination = Paths.get(filePath, destFileName);
				Files.copy(myFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e.toString());
		}
		return uploadFlag;
	}

	public String createDirectory(String strDir) throws TCGMException {
		String uploadFlag = "";
		String methodName = "createDirectory(String strDir)";

		try {
			String filePath = AppConst.getSharelocation();
			Path newDirPath = Paths.get(filePath + strDir.trim());
			
			if (Files.notExists(newDirPath)) {
				Files.createDirectory(newDirPath);
				uploadFlag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e.toString());
		}
		return uploadFlag;
	}

	public HashMap<String, Object> getDirectories(int accessLevel) throws TCGMException {
	    String methodName = "getDirectories()";
	    HashMap<String, Object> map = new HashMap<>();
	    try {
	        String filePath = AppConst.getSharelocation();
	        File dir = new File(filePath);
	        String[] dirList = dir.list();
	        if (dirList != null) {
	            for (String filename : dirList) {
	                File checkDir = new File(filePath, filename);
	                if (checkDir.isDirectory()) {
	                    if (accessLevel == 3 || accessLevel == 5) {
	                        if (filename.equals(TCGMConstants.OPEN_ITEMS)) {
	                            map.put(filename, filename);
	                        }
	                    } else {
	                        if (!filename.equals(TCGMConstants.OPEN_ITEMS)) {
	                            map.put(filename, filename);
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new TCGMException(this.getClass().getName(), methodName, e.toString());
	    }
	    return map;
	}

	public HashMap<String, String> getFiles(String strPath) throws TCGMException {
		String methodName = " getFiles(String strPath)";
		HashMap<String, String> fileLstdisp = new HashMap<>();
		try {
			File dir = new File(strPath);
			String[] dirList = dir.list();

			if (dirList != null) {
				for (String filename : dirList) {
					File filesDir = new File(strPath, filename);
					if (filesDir.isFile()) {
						fileLstdisp.put(filename, filename + " (" + (filesDir.length() / 1024) + " KB)");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TCGMException(this.getClass().getName(), methodName, e.toString());
		}
		return fileLstdisp;
	}
}
