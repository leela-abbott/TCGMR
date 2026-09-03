package abbott.ai.tcgm.helpers;
/**
 * SecurityOverview.java
 * Description: KB #1011543 - How to use the Cognos Software Development Kit to define all Groups/Roles and their respective Users
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.Account;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseClassArrayProp;
import com.cognos.developer.schemas.bibus._3.CognosReportNetPortType;
import com.cognos.developer.schemas.bibus._3.CognosReportNetServiceLocator;
import com.cognos.developer.schemas.bibus._3.ContentManagerServiceStub;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.Group;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.Role;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.UpdateOptions;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;

public class SecurityOverview
{

	private ContentManagerServiceStub cmStub = null;
	private static CognosReportNetPortType oCrn;

	public SecurityOverview(String sendPoint)
	{
		 ContentManagerService_ServiceLocator cmServiceLocator = new ContentManagerService_ServiceLocator();
		  try {
			cmStub = new ContentManagerServiceStub(new java.net.URL(sendPoint),cmServiceLocator);

			// Set the Axis request timeout
			// in milliseconds, 0 turns the timeout off
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

	   //Invoke the ContentManager service logon() method passing the credential string
	   //You will pass an empty string in the second argument. Optionally,
	   //you could pass the Role as an argument but for the purpose of this
	   //workshop don’t be concerned with Roles.

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
//			GroupsAndRolesGUI gui = new GroupsAndRolesGUI();
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

	public BaseClass[] getAvailableMembers(String nameSpaceID)
	{
		// build the searchpath to retrieve the Groups
		//String groups = "CAMID(\":AI TCGM:level 2:level 3:TCGM_SEC020222_Consumer\")";
		String groups = "CAMID(\"LDAP abbott.corp:u:cn=kasikms,ou=users\")";
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
	/**
	 * Get information specific to a particular user.
	 *
	 * @param   oCrn         CognosReportNetPortType object.
	 * @param   pathOfUser   Search path to user to query.
	 * @return               User Information.
	 *
	 */
	public BaseClass[] getMemberInfo(
		CognosReportNetPortType oCrn,
		String pathOfUser)
	{
		BaseClass groups[] = new BaseClass[] {};
		PropEnum props[] =
			new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
		try
		{
			groups =
				oCrn.query(
					pathOfUser,
					props,
					new Sort[] {},
					new QueryOptions());
		}
		catch (Exception e)
		{
			//System.out.println(e);
		}
		return groups;
	}
	/**
	 * Add the specified member to the specified role.
	 *
	 * @param   oCrn        CognosReportNetPortType object.
	 * @param   pathOfRole  Search path to the role.
	 * @param   member      User, group or role to be added.
	 *
	 */
	public void addToRole()
//		CognosReportNetPortType oCrn,
//		String pathOfRole,
//		BaseClass[] member)
		throws java.rmi.RemoteException
	{
		// Get the current role membership.
		String members = "CAMID(\"LDAP abbott.corp:u:cn=kasikms,ou=users\")";
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(members);
		PropEnum properties[] =
			new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath };
		BaseClass member[] = cmStub.query(spMulti, properties, new Sort[]{}, new QueryOptions());

		PropEnum[] props =
			{ PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
		//String groups = "CAMID(\":Authors\")";
		//CAMID(":AI:AI-GPS-Consumers")
		String groups = "CAMID(\":AI TCGM:level 2:TCGM_AFFILIATE_SECTOR_Consumer\")";

		BaseClass groupObjects[] = new BaseClass[]{};
//		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(groups);

		//Role role = (Role)cmStub.query(spMulti, props, new Sort[]{}, new QueryOptions())[0];
		Group role = (Group)cmStub.query(spMulti, props, new Sort[]{}, new QueryOptions())[0];

		if (role.getMembers().getValue() == null)
		{
			role.setMembers(new BaseClassArrayProp());
			role.getMembers().setValue(member);
			cmStub.update(new BaseClass[] { role }, new UpdateOptions());
		}
		else
		{
			// Preserve all the existing members.
			BaseClass[] newMembers =
				new BaseClass[role.getMembers().getValue().length + 1];
			int index = 0;
			BaseClass obj = null;
			for (int i = 0; i < role.getMembers().getValue().length; i++)
			{
				obj = role.getMembers().getValue()[i];

				//BaseClass[] memberProps =
					//csHandler.queryObjectInCS(
					//	oCrn,
					//	obj.getSearchPath().getValue());

				newMembers[index] = obj;
				index++;
			}

			newMembers[index] = member[0];

			role.setMembers(new BaseClassArrayProp());
			role.getMembers().setValue(newMembers);

			// Update the membership.
			cmStub.update(new BaseClass[] { role }, new UpdateOptions());
		}
	}
	/**
	 * Add the specified member to the specified role.
	 *
	 * @param   oCrn        CognosReportNetPortType object.
	 * @param   pathOfRole  Search path to the role.
	 * @param   member      User, group or role to be added.
	 *
	 */
	public void removeFromRole()
//		CognosReportNetPortType oCrn,
//		String pathOfRole,
//		BaseClass[] member)
		throws java.rmi.RemoteException
	{
		// Get the current role membership.
		String members = "CAMID(\"LDAP abbott.corp:u:cn=kasikms,ou=users\")";
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(members);
		PropEnum properties[] =
			new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath };
		BaseClass[] member = cmStub.query(spMulti, properties, new Sort[]{}, new QueryOptions());
		//System.out.println("member info:" + member[0].getDefaultName().getValue());

		PropEnum[] props =
			{ PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
		String groups = "CAMID(\":Authors\")";

		BaseClass groupObjects[] = new BaseClass[]{};
//		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(groups);

		Role role = (Role)cmStub.query(spMulti, props, new Sort[]{}, new QueryOptions())[0];

		if (role.getMembers().getValue() == null)
		{
			role.setMembers(new BaseClassArrayProp());
			role.getMembers().setValue(member);
			cmStub.update(new BaseClass[] { role }, new UpdateOptions());
		}
		else
		{
			// Preserve all the existing members.
			BaseClass[] newMembers =
				new BaseClass[role.getMembers().getValue().length - 1];
			int index = 0;
			String csMember;
			String csMemberPath;
			BaseClass obj = null;
			for (int i = 0; i < role.getMembers().getValue().length; i++)
			{
				obj = role.getMembers().getValue()[i];
				spMulti.setValue(obj.getSearchPath().getValue());

				BaseClass[] memberProps =
				   cmStub.query(spMulti, props, new Sort[]{}, new QueryOptions());
					//csHandler.queryObjectInCS(oCrn, obj.getSearchPath().getValue());

				csMember = memberProps[0].getDefaultName().getValue();
				csMemberPath = memberProps[0].getSearchPath().getValue();
				if ((csMember.compareTo(member[0].getDefaultName().getValue()) != 0)
					&& (csMemberPath.compareTo(member[0].getSearchPath().getValue())
						!= 0))
				{
					newMembers[index] = obj;
					index++;
				}
			}

	//		newMembers[index] = member[0];

			role.setMembers(new BaseClassArrayProp());
			role.getMembers().setValue(newMembers);

			// Update the membership.
			cmStub.update(new BaseClass[] { role }, new UpdateOptions());
		}
	}

/*	public static void main(String[] args)
	{
		//String nameSpaceID = "LDAP abbott.corp:f:ou=groups";
		// Variable that contains the default URL for CRN Content Manager.

		String reportNetEndPoint = "http://AINAPA0100:9301/p2pd/servlet/dispatch";
//		// must be a user with System administrator privledges.
		String userName = "svc-tcgm"; //"TST-TCGM_NA1";
		String password = "Abbott123";
		String nameSpaceID = "LDAP abbott.corp";

//		String reportNetEndPoint = "http://gpodlcd857255:9302/p2pd/servlet/dispatch";
		// must be a user with System administrator privledges.
//		String userName = "tst-ppgbi10";
//		String password = "!Tomcat1";
//		String nameSpaceID = "ADAM";
		String fileName = "C:/downloads/cognos/users.txt";

//		String fileName = "C:/downloads/cognos/users.txt";

		SecurityOverview so = new SecurityOverview(reportNetEndPoint);
		// use this logon code to logon as a system administrator
		so.quickLogon(nameSpaceID,userName,password);
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

		// define the namespace to query - : means the Cognos namespace
		String nsToQuery = ":";


//		try {
//			so.addToRole();
//			//so.removeFromRole();
//		} catch (RemoteException e2) {
//			e2.printStackTrace();
//		}

		BaseClass[] roleIDs = so.getRoles(nsToQuery);
		BaseClass[] groupIDs = so.getGroups(nsToQuery);
//		BaseClass[] memberIDs = so.getAvailableMembers(nameSpaceID);
//		BaseClass[] memberIDs = so.getMemberInfo(oCrn, "CAMID(\":AI TCGM:level 2:level 3:TCGM_SEC020222_Consumer\")");
		Account[] targetAccount=null;

		for (int x=0;x<roleIDs.length;x++)
		{
			//System.out.println("\nRole: "+roleIDs[x].getDefaultName().getValue());
			//System.out.println("\nRole: "+roleIDs[x].getDefaultName().getValue());
			//System.out.println("\nRole Search Path: "+roleIDs[x].getSearchPath().getValue());
			fout.println("\nRole: "+roleIDs[x].getDefaultName().getValue());
//			fout.println("\nRole Search Path: "+roleIDs[x].getSearchPath().getValue());
			targetAccount = so.getMembers("expandMembers("+roleIDs[x].getSearchPath().getValue()+")");
			// Now print out the retrieved info
			if (targetAccount.length!=0)
			{
				for (int i=0;i<targetAccount.length;i++)
				{
					targetAccount[i]= (Account)targetAccount[i];

					fout.println("      "+targetAccount[i].getDefaultName().getValue() + " -- "+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou")));
//					fout.println("      "+targetAccount[i].getDefaultName().getValue());
//					fout.println("          User Info :     "+targetAccount[i].getSearchPath().getValue());

//					fout.println(" Role Search Path2 :     "+targetAccount[i].getSearchPath().getValue());
//					fout.println(" Email :     "+targetAccount[i].getEmail());
//					fout.println(" Format :     "+targetAccount[i].getFormat());
//					fout.println(" Given Name :     "+targetAccount[i].getGivenName());
//					fout.println(" Home Phone :     "+targetAccount[i].getHomePhone());
//					fout.println(" Name :     "+targetAccount[i].getName());
//					fout.println(" Options :     "+targetAccount[i].getOptions());
//					fout.println(" Parameters :     "+targetAccount[i].getParameters());
//					fout.println(" Owner :     "+targetAccount[i].getOwner());
//					fout.println(" Stored Id :     "+targetAccount[i].getStoreID());
//					fout.println(" Type Desc :     "+targetAccount[i].getObjectClass());
					//fout.println(" Type Desc :     "+targetAccount[i].getSearchPath().get );
					//System.out.println("      "+targetAccount[i].getDefaultName().getValue());
				}
			}
			else
			{
				//System.out.println("No Members Found");
				fout.println("No Members Found");
			}
		}

		for (int x=0;x<groupIDs.length;x++)
		{
			// do not perform on groups that do not have specific members defined
			String allusers = "All Authenticated Users";
			String everyone = "Everyone";
			boolean test1 = groupIDs[x].getDefaultName().getValue().equals(allusers);
			boolean test2 = groupIDs[x].getDefaultName().getValue().equals(everyone);
			if ( !test1 && !test2  )
			{
				//System.out.println("\nGroup: "+groupIDs[x].getDefaultName().getValue());
				//System.out.println("\nGroup Search Path: "+groupIDs[x].getSearchPath().getValue());
				fout.println("\nGroup: "+groupIDs[x].getDefaultName().getValue());
				fout.println("\nGroup Search Path: "+groupIDs[x].getSearchPath().getValue());
				targetAccount = so.getMembers("expandMembers("+groupIDs[x].getSearchPath().getValue()+")");
				// Now print out the retrieved info
				if (targetAccount.length!=0)
				{
					for (int i=0;i<targetAccount.length;i++)
					{
						targetAccount[i]= (Account)targetAccount[i];
						//fout.println("      "+targetAccount[i].getDefaultName().getValue() );
						if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_AFF"))
						{
							fout.println(groupIDs[x].getDefaultName().getValue().substring(8,11)+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+ "\t"+targetAccount[i].getSearchPath().getValue());
						}
						else if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_SEC"))
						{
							fout.println(groupIDs[x].getDefaultName().getValue().substring(8,14)+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+ "\t"+targetAccount[i].getSearchPath().getValue());
						}
						else
						{
							fout.println(groupIDs[x].getDefaultName().getValue()+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+ "\t"+targetAccount[i].getSearchPath().getValue());
						}
						if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_AFF"))
						{
							//System.out.println(groupIDs[x].getDefaultName().getValue().substring(8,11)+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+ "\t"+targetAccount[i].getSearchPath().getValue());
						}
						else if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_SEC"))
						{
							//System.out.println(groupIDs[x].getDefaultName().getValue().substring(8,14)+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+ "\t"+targetAccount[i].getSearchPath().getValue());
						}
						else
						{
							//System.out.println(groupIDs[x].getDefaultName().getValue()+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+ "\t"+targetAccount[i].getSearchPath().getValue());
						}
					}
				}
				else
				{
					//System.out.println("No Members Found");
					fout.println("No Members Found");
				}
			}
		}

//		for (int x=0;x<memberIDs.length;x++)
//		{
//			// do not perform on groups that do not have specific members defined
//			String allusers = "All Authenticated Users";
//			String everyone = "Everyone";
//			boolean test1 = memberIDs[x].getDefaultName().getValue().equals(allusers);
//			boolean test2 = memberIDs[x].getDefaultName().getValue().equals(everyone);
//			if ( !test1 && !test2  )
//			{
//				//System.out.println("\nGroup: "+memberIDs[x].getDefaultName().getValue());
//				fout.println("\nGroup: "+memberIDs[x].getDefaultName().getValue());
//				fout.println("\nGroup Search Path: "+memberIDs[x].getSearchPath().getValue());
//				targetAccount = so.getMembers("expandMembers("+memberIDs[x].getSearchPath().getValue()+")");
//				// Now print out the retrieved info
//				if (targetAccount.length!=0)
//				{
//					for (int i=0;i<targetAccount.length;i++)
//					{
//						targetAccount[i]= (Account)targetAccount[i];
//						fout.println("      "+targetAccount[i].getDefaultName().getValue() + "(" + targetAccount[i].getOwner()+ ")");
//						//System.out.println("      "+targetAccount[i].getDefaultName().getValue()+ "(" + targetAccount[i].getOwner()+ ")");
//						fout.println(" Search Path2 :     "+targetAccount[i].getSearchPath().getValue());
//					}
//				}
//				else
//				{
//					//System.out.println("No Members Found");
//					fout.println("No Members Found");
//				}
//			}
//		}
	}*/

	
public static void generateUsers() throws TCGMException
		{

//			String reportNetEndPoint = "http://AINAPA0100:9301/p2pd/servlet/dispatch";  //Prod
//			String reportNetEndPoint = "http://AINAPA0100D:9300/p2pd/servlet/dispatch";  //QA
//			String userName = "svc-tcgm"; //"TST-TCGM_NA1"; 
//			String password = "Abbott123";
//			String nameSpaceID = "LDAP abbott.corp";
			
			final String reportNetEndPoint = AppConst.getInstance().getReportNetServiceLocator();
			final String nameSpaceID = AppConst.getInstance().getReportNetNameSpace();
			final String userName = AppConst.getInstance().getReportNetUsername();
			final String password = AppConst.getInstance().getReportNetPassword();
			
			System.out.println(System.getProperty("catalina.home"));
			//System.out.println(System.getProperties().toString());
			String fileName = System.getProperty("catalina.home")+"/webapps/tcgm/include/users.txt";//"C:/downloads/cognos/users.txt";			
			System.out.println(fileName);
			
			SecurityOverview so = new SecurityOverview(reportNetEndPoint);
			// use this logon code to logon as a system administrator
			so.quickLogon(nameSpaceID,userName,password);
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
		
			fout.println("Role\tUser Name\tUser ID\tDate Created\tDate Last Logged");
			String nsToQuery = ":";
		
			BaseClass[] roleIDs = so.getRoles(nsToQuery);
			BaseClass[] groupIDs = so.getGroups(nsToQuery);
			Account[] targetAccount=null;
		
			try{
		
			for (int x=0;x<roleIDs.length;x++)
			{
				targetAccount = so.getMembers("expandMembers("+roleIDs[x].getSearchPath().getValue()+")");

				if (targetAccount.length!=0)
				{
					Calendar calendar = Calendar.getInstance();
					String dateTime ="";
					String dateTimeModified ="";
				
					for (int i=0;i<targetAccount.length;i++)
					{
						targetAccount[i]= (Account)targetAccount[i];
						calendar = targetAccount[i].getCreationTime().getValue();
						if(calendar!=null){
						dateTime = calendar.get(calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
								   calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
								   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
						}else{
							dateTime = "None";
							}
					
						calendar = targetAccount[i].getModificationTime().getValue();
						if(calendar!=null){
						dateTimeModified = calendar.get(calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
										   calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
										   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
						}else{
								dateTimeModified = "None";
							}
							
						fout.println("Role:"+roleIDs[x].getDefaultName().getValue()+"\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+"\t"+dateTime+"\t"+dateTimeModified);
					}
				}
				else
				{
					//fout.println("No Members Found");
				}
			}
		
			for (int x=0;x<groupIDs.length;x++)
			{
				// do not perform on groups that do not have specific members defined
				String allusers = "All Authenticated Users";
				String everyone = "Everyone";
				boolean test1 = groupIDs[x].getDefaultName().getValue().equals(allusers);
				boolean test2 = groupIDs[x].getDefaultName().getValue().equals(everyone);
				if ( !test1 && !test2  )
				{
					targetAccount = so.getMembers("expandMembers("+groupIDs[x].getSearchPath().getValue()+")");
					// Now print out the retrieved info
					if (targetAccount.length!=0)
					{
						Calendar calendar = Calendar.getInstance();
						String dateTime ="";
						String dateTimeModified ="";
					
						for (int i=0;i<targetAccount.length;i++)
						{
							targetAccount[i]= (Account)targetAccount[i];
						
							calendar = targetAccount[i].getCreationTime().getValue();
							if(calendar!=null){
							dateTime = calendar.get(calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
									   calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
									   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
							}else{
								dateTime = "None";
							}
					
							calendar = targetAccount[i].getModificationTime().getValue();
							if(calendar!=null){
							dateTimeModified = calendar.get(calendar.YEAR)+"-"+  (calendar.get(Calendar.MONTH) + 1) + "-" +
											   calendar.get(Calendar.DATE)+" "+  calendar.get(Calendar.HOUR_OF_DAY) + ":" +
											   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
							}else{
								dateTimeModified = "None";
							}
							
							/*if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_AFF"))
							{
								fout.println("Group:"+groupIDs[x].getDefaultName().getValue()+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+"\t"+dateTime+"\t"+dateTimeModified);
							}
							else if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_SEC"))
							{
								fout.println("Group:"+groupIDs[x].getDefaultName().getValue()+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+"\t"+dateTime+"\t"+dateTimeModified);
							}
							else
							{
								fout.println("Group:"+groupIDs[x].getDefaultName().getValue()+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+"\t"+dateTime+"\t"+dateTimeModified);
							}
							if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_AFF"))
							{

							}
							else if (groupIDs[x].getDefaultName().getValue().substring(0,8).equalsIgnoreCase("TCGM_SEC"))
							{

							}
							else
							{

							}*/
							fout.println("Group:"+groupIDs[x].getDefaultName().getValue()+ "\t"+targetAccount[i].getDefaultName().getValue() + "\t"+targetAccount[i].getSearchPath().getValue().substring(targetAccount[i].getSearchPath().getValue().indexOf("cn=")+3,targetAccount[i].getSearchPath().getValue().indexOf(",ou"))+"\t"+dateTime+"\t"+dateTimeModified);
						}
					}
					else
					{
						//fout.println("No Members Found");
					}
				}
			}
		}catch (NullPointerException e){
		e.printStackTrace();
		}
		
		}	
}
