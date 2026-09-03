/*
 * Created on Jun 2, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.helpers;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.rpc.ServiceException;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.Account;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.CognosReportNetPortType;
import com.cognos.developer.schemas.bibus._3.CognosReportNetServiceLocator;
import com.cognos.developer.schemas.bibus._3.ContentManagerServiceStub;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportOverview {
	
	public ReportOverview(){
		
	}
		private ContentManagerServiceStub cmStub = null;
		private static CognosReportNetPortType oCrn;

		public void rptOverview(String sendPoint)
		{
			 ContentManagerService_ServiceLocator cmServiceLocator = new ContentManagerService_ServiceLocator();
			  try {
				cmStub = new ContentManagerServiceStub(new java.net.URL(sendPoint),cmServiceLocator);
				
				cmStub.setTimeout(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public Account[] getMembers(String targetSearchPath)
		{
			PropEnum props[] = new PropEnum[] {PropEnum.searchPath, PropEnum.defaultName,PropEnum.creationTime,PropEnum.modificationTime,
											   PropEnum.portalPage};
			Sort sOpt[] = new Sort[]{};
			QueryOptions qOpt = new QueryOptions();
			SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
			spMulti.setValue(targetSearchPath);
			Account[] targetAccount = null;

			try
			{
				// get the target user's account object.
				BaseClass targets[] = cmStub.query(spMulti, props, sOpt, qOpt);
				// initialize targetAccount
				targetAccount = new Account[targets.length];
				for (int x=0;x<targetAccount.length;x++)
				{
					targetAccount[x] = (Account)targets[x];
				}

			}
			catch (Exception e)
			{
				//System.out.println(e);
				e.printStackTrace();
			}
			return targetAccount;

		}

		//This method loggs the user to ReportNet
		public String quickLogon(String namespace, String uid, String pwd)
		{
			StringBuffer credentialXML = new StringBuffer();

			credentialXML.append("<credential>");
			credentialXML.append("<namespace>").append(namespace).append("</namespace>");
			credentialXML.append("<username>").append(uid).append("</username>");
			credentialXML.append("<password>").append(pwd).append("</password>");
			credentialXML.append("</credential>");

			String encodedCredentials = credentialXML.toString();
			XmlEncodedXML xmlCredentials = new XmlEncodedXML();
			xmlCredentials.setValue(encodedCredentials);

		   try
			{
				cmStub.logon(xmlCredentials,null );
			}
			catch (Exception e)
			{
				//System.out.println(e);
			}
			return ("Logon successful as " + uid);
		}

		public BaseClass[] getRoles(String nameSpaceID)
		{
			// build the searchpath to retrieve the roles
			String roles = "CAMID(\""+nameSpaceID+"\")//role";
			PropEnum props[] = new PropEnum[] {PropEnum.searchPath,PropEnum.defaultName};
			BaseClass roleObjects[] = new BaseClass[]{};
			SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
			spMulti.setValue(roles);

			try
			{
				roleObjects = cmStub.query(spMulti, props, new Sort[]{}, new QueryOptions());
//				GroupsAndRolesGUI gui = new GroupsAndRolesGUI();
			}
			catch (Exception e)
			{
				//System.out.println(e);
				e.printStackTrace();
			}

			String[] roleSearchPaths = new String[roleObjects.length];

			if (!(roleObjects.length != 0))
			{
				//System.out.println("There were no Roles Found\n");
			}

			return roleObjects;

		}

		public BaseClass[] getGroups(String nameSpaceID)
		{
			// build the searchpath to retrieve the Groups
			String groups = "CAMID(\""+nameSpaceID+"\")//group";
			PropEnum props[] = new PropEnum[] {PropEnum.searchPath,PropEnum.defaultName};
			BaseClass groupObjects[] = new BaseClass[]{};
			SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
			spMulti.setValue(groups);

			try
			{
				groupObjects = cmStub.query(spMulti, props, new Sort[]{}, new QueryOptions());
			}
			catch (Exception e)
			{
				//System.out.println(e);
				e.printStackTrace();
			}

			String[] groupSearchPaths = new String[groupObjects.length];
			if (!(groupObjects.length != 0))
			{
				//System.out.println("There were no Groups Found\n");
			}

			return groupObjects;

		}
	
	public  void checkUsers(ArrayList rptList) throws TCGMException
	 {
	 				
				final String reportNetEndPoint = AppConst.getInstance().getReportNetServiceLocator();
				final String nameSpaceID = AppConst.getInstance().getReportNetNameSpace();
				final String userName = AppConst.getInstance().getReportNetUsername();
				final String password = AppConst.getInstance().getReportNetPassword();				
				HashMap userMap=new HashMap();
				ArrayList rptUserList=new ArrayList();				
				String fileName = System.getProperty("catalina.home")+"/webapps/tcgm/include/report.txt";

			
				this.rptOverview(reportNetEndPoint);
				// use this logon code to logon as a system administrator
				this.quickLogon(nameSpaceID,userName,password);
				try {
					oCrn = null;
					oCrn = (new CognosReportNetServiceLocator()).getCognosReportNetPort(new java.net.URL(reportNetEndPoint));
				} catch (MalformedURLException e1) {
						e1.printStackTrace();
				} catch (ServiceException e1) {
					 e1.printStackTrace();
				}
		
		
				FileOutputStream fs=null;
				try 
				{
					fs = new FileOutputStream(fileName);
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				PrintStream fout = new PrintStream(fs);
		
				fout.println("User Name\t\t\tUser ID\t");
				String nsToQuery = ":";
		
				BaseClass[] roleIDs = this.getRoles(nsToQuery);
				BaseClass[] groupIDs = this.getGroups(nsToQuery);
				Account[] targetAccount=null;
				String accountvalue="";
		
				try{
		      
				for (int x=0;x<roleIDs.length;x++)
				{
					targetAccount = this.getMembers("expandMembers("+roleIDs[x].getSearchPath().getValue()+")");

					if (targetAccount.length!=0)
					{
							
						for (int i=0;i<targetAccount.length;i++)
						{
							targetAccount[i]= (Account)targetAccount[i];
							accountvalue=targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou")).toLowerCase();							
							userMap.put(targetAccount[i].getDefaultName().getValue(),accountvalue);
						}
					}
					
				}
				accountvalue="";
				for (int x=0;x<groupIDs.length;x++)
				{
					// do not perform on groups that do not have specific members defined
					String allusers = "All Authenticated Users";
					String everyone = "Everyone";
					boolean test1 = groupIDs[x].getDefaultName().getValue().equals(allusers);
					boolean test2 = groupIDs[x].getDefaultName().getValue().equals(everyone);
					if ( !test1 && !test2  )
					{
						targetAccount = this.getMembers("expandMembers("+groupIDs[x].getSearchPath().getValue()+")");
						// Now print out the retrieved info
						if (targetAccount.length!=0)
						{
												
							for (int i=0;i<targetAccount.length;i++)
							{
								targetAccount[i]= (Account)targetAccount[i];
								accountvalue=targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou")).toLowerCase();							
								userMap.put(targetAccount[i].getDefaultName().getValue(),accountvalue);
							}
						}
						
					}
				}
				
				
				for(int i=0;i<rptList.size();i++){
					RptUser rptUser=(RptUser)rptList.get(i);
					String userid=rptUser.getUserid().toLowerCase();
					rptUserList.add(userid);
					if((!userMap.containsValue(userid))){
						fout.println(rptUser.getFullName()+ "\t\t\t"+userid);
					}
				}
				
				fout.println("\n\nUser Name\t\t\tUser ID\t");
				Iterator it=userMap.keySet().iterator();
				while(it.hasNext()){
					String value=(String)it.next();
					String userid=(String)userMap.get(value);
					if((!rptUserList.contains(userid))){
						
						fout.println(value+ "\t\t\t"+userid);
					}
				}
			fout.close();	
				
			}catch (NullPointerException e){
			e.printStackTrace();
			}
		
	}

}
