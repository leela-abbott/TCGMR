/*
 * Created on Apr 23, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.MainForm;
/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Main extends TCGMAction {
	public Main()
		{
			super();
		}

	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
		{
			
		   MainForm mainForm = (MainForm)form;//cast the form that was passed in to the correct type for this action
		  
		   String filePath = AppConst.getSharelocation();//"S:\\MFGACCT\\TCGMTEST\\";
		   String bulletinMsg="";
		   ArrayList messageList=new ArrayList();
		   String line="";
		   if(mainForm.getCmd().equalsIgnoreCase("") || mainForm.getCmd().equalsIgnoreCase("dir")){
		   
		   BufferedReader in=null;
		   try{
			 
			    in = new BufferedReader(new FileReader(AppConst.getSharelocation()+"Bulletin.txt"));
			   if (!in.ready()){
				throw new IOException();
			   }
			   while ((line = in.readLine()) != null){
				bulletinMsg=bulletinMsg+" "+line; 
			   }
			
			   in.close();
			}catch (IOException e)
				   {
					   System.out.println(e);
					   //return null;
				   }
				   
			try{
			 
				in = new BufferedReader(new FileReader(AppConst.getSharelocation()+"Messages.txt"));
				  if (!in.ready()){
						throw new IOException();
				   }
				   while ((line = in.readLine()) != null){
					messageList.add(line); 
				   }
				   in.close();
				}catch (IOException e)
				  {
					System.out.println(e);
				  }
		   
				   
			mainForm.setBulletinMessage(bulletinMsg);
			mainForm.setMessageList(messageList);
			ArrayList dirLst=new ArrayList();
		   File dir = new File(filePath);
		   File checkDir=null;
			String[] dirList = dir.list();
			if (dirList != null) {				
				for (int i=0; i<dirList.length; i++) {
					// Get filename of file or directory
					String filename = dirList[i];					
					checkDir=new File(filePath,filename);							            
					if(checkDir.isDirectory()){						
						dirLst.add(filename);	
					}
					checkDir=null;
				}
				mainForm.setDirList(dirLst);
					
			}			
		  
		   dir=null;
		   }else{ 
					String newfilePath=filePath+mainForm.getDirName();	
					  File dir = new File(newfilePath);
					  File filesDir=null;
					  String[] dirList = dir.list();
					  ArrayList fileLst=new ArrayList();
					   if (dirList != null) {				
						   for (int i=0; i<dirList.length; i++) {
							   // Get filename of file or directory
							   String filename = dirList[i];					
							filesDir=new File(newfilePath,filename);							            
							   if(filesDir.isFile()){						
								//fileLst.add(filename+" ("+filesDir.length()+" KB)");	
								fileLst.add(filename);
							   }
							filesDir=null;
						   }
						   mainForm.setFileList(fileLst);
					
					   }			
		  
					  dir=null;	
		   }
		  this.setForward(TCGMConstants.FORWARD_SUCCESS);
			return mapping.findForward(this.getForward());
		}

}
