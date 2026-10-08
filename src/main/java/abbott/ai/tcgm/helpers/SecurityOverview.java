package abbott.ai.tcgm.helpers;

/**
 * SecurityOverview.java
 * Description: KB #1011543 - How to use the Cognos Software Development Kit to define all Groups/Roles and their respective Users
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.Account;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseClassArrayProp;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.Group;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.Role;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.UpdateOptions;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;
import com.cognos.developer.schemas.bibus._3.AddOptions;
import com.cognos.developer.schemas.bibus._3.UpdateActionEnum;

public class SecurityOverview
{
	private ContentManagerService_PortType cmService = null;
	private static final Logger myLogger = LogManager.getLogger(SecurityOverview.class);

	public SecurityOverview(String sendPoint)
	{
		ContentManagerService_ServiceLocator cmServiceLocator = new ContentManagerService_ServiceLocator();
		try {
			cmService = cmServiceLocator.getcontentManagerService(new URL(sendPoint));
			configureTimeout(cmService);
		} catch (Exception e) {
			myLogger.error("Failed to connect to ContentManagerService gateway at endpoint: " + sendPoint, e);
		}
	}

	private void configureTimeout(Object serviceStub) {
		if (serviceStub == null) return;
		try {
			if (serviceStub instanceof org.apache.axis.client.Stub axisStub) {
				axisStub.setTimeout(0);
				return;
			}
		} catch (NoClassDefFoundError ignored) {
		}

		String className = serviceStub.getClass().getName();
		if (className.contains("axis2")) {
			try {
				java.lang.reflect.Method getServiceClientMethod = serviceStub.getClass().getMethod("_getServiceClient");
				Object serviceClient = getServiceClientMethod.invoke(serviceStub);
				java.lang.reflect.Method getOptionsMethod = serviceClient.getClass().getMethod("getOptions");
				Object options = getOptionsMethod.invoke(serviceClient);
				java.lang.reflect.Method setTimeOutMethod = options.getClass().getMethod("setTimeOutInMilliSeconds", long.class);
				setTimeOutMethod.invoke(options, 0L);
			} catch (Exception e) {
				myLogger.warn("Could not dynamically apply Axis2 connection timeout properties: " + e.getMessage());
			}
		}
	}
	
	public Account[] getMembers(String targetSearchPath)
	{
		PropEnum[] props = new PropEnum[] {
			PropEnum.searchPath, 
			PropEnum.defaultName,
			PropEnum.creationTime,
			PropEnum.modificationTime,
			PropEnum.portalPage
		};
		Sort[] sOpt = new Sort[]{};
		QueryOptions qOpt = new QueryOptions();
		
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject(targetSearchPath);
		Account[] targetAccount = null;

		try
		{
			BaseClass[] targets = cmService.query(spMulti, props, sOpt, qOpt);
			if (targets != null) {
				targetAccount = Arrays.stream(targets)
									  .map(Account.class::cast)
									  .toArray(Account[]::new);
			}
		}
		catch (Exception e)
		{
			myLogger.error("Error executing dynamic member directory resolution query.", e);
		}
		
		return targetAccount != null ? targetAccount : new Account[0];
	}


	public String quickLogon(String namespace, String uid, String pwd)
	{
		String credentialXML = "<credential>" +
							   "<namespace>" + namespace + "</namespace>" +
							   "<username>" + uid + "</username>" +
							   "<password>" + pwd + "</password>" +
							   "</credential>";

		XmlEncodedXML xmlCredentials = new XmlEncodedXML();
		xmlCredentials.set_value(credentialXML);

		try
		{
			cmService.logon(xmlCredentials, new SearchPathSingleObject[] { new SearchPathSingleObject() });
		}
		catch (Exception e)
		{
			myLogger.error("Logon invocation failed inside ContentManager authentication pipeline", e);
		}
		return ("Logon successful as " + uid);
	}


	public BaseClass[] getRoles(String nameSpaceID)
	{
		String roles = "CAMID(\"" + nameSpaceID + "\")//role";
		PropEnum[] props = new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
		
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject(roles);
		BaseClass[] roleObjects = new BaseClass[]{};

		try
		{
			roleObjects = cmService.query(spMulti, props, new Sort[]{}, new QueryOptions());
		}
		catch (Exception e)
		{
			myLogger.error("Error retrieving namespace Roles definition context logs", e);
		}
		return roleObjects;
	}

	public BaseClass[] getGroups(String nameSpaceID)
	{
		String groups = "CAMID(\"" + nameSpaceID + "\")//group";
		PropEnum[] props = new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
		
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject(groups);
		BaseClass[] groupObjects = new BaseClass[]{};

		try
		{
			groupObjects = cmService.query(spMulti, props, new Sort[]{}, new QueryOptions());
		}
		catch (Exception e)
		{
			myLogger.error("Error retrieving namespace Groups metadata models", e);
		}
		return groupObjects;
	}

	public BaseClass[] getAvailableMembers(String nameSpaceID)
	{
		String groups = "CAMID(\"LDAP abbott.corp:u:cn=kasikms,ou=users\")";
		PropEnum[] props = new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
		
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject(groups);
		BaseClass[] groupObjects = new BaseClass[]{};

		try
		{
			groupObjects = cmService.query(spMulti, props, new Sort[]{}, new QueryOptions());
		}
		catch (Exception e)
		{
			myLogger.error("Error recovering explicitly defined consumer account definitions", e);
		}
		return groupObjects;
	}

	public BaseClass[] getMemberInfo(ContentManagerService_PortType targetCmService, String pathOfUser)
	{
		BaseClass[] groups = new BaseClass[] {};
		PropEnum[] props = new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
		try
		{
			groups = targetCmService.query(new SearchPathMultipleObject(pathOfUser), props, new Sort[] {}, new QueryOptions());
		}
		catch (Exception e)
		{
			myLogger.error("Error extracting explicit account info for target: " + pathOfUser, e);
		}
		return groups;
	}
	public void addToRole() throws java.rmi.RemoteException
	{
		String members = "CAMID(\"LDAP abbott.corp:u:cn=kasikms,ou=users\")";
		
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject(members);
		PropEnum[] properties = new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath };
		BaseClass[] member = cmService.query(spMulti, properties, new Sort[]{}, new QueryOptions());

		PropEnum[] props = { PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
		String groups = "CAMID(\":AI TCGM:level 2:TCGM_AFFILIATE_SECTOR_Consumer\")";

		SearchPathMultipleObject groupPathObj = new SearchPathMultipleObject(groups);
		BaseClass[] queryResults = cmService.query(groupPathObj, props, new Sort[]{}, new QueryOptions());
		Group role = (queryResults != null && queryResults.length > 0) ? (Group) queryResults[0] : null;

		if (role != null) {
			if (role.getMembers().getValue() == null)
			{
				role.setMembers(new BaseClassArrayProp());
				role.getMembers().setValue(member);
				cmService.update(new BaseClass[] { role }, new UpdateOptions());
			}
			else
			{
				BaseClass[] existingMembers = role.getMembers().getValue();
				BaseClass[] newMembers = Arrays.copyOf(existingMembers, existingMembers.length + 1);
				newMembers[existingMembers.length] = member[0];

				role.setMembers(new BaseClassArrayProp());
				role.getMembers().setValue(newMembers);

				cmService.update(new BaseClass[] { role }, new UpdateOptions());
			}
		}
	}

	public void removeFromRole() throws java.rmi.RemoteException
	{
		String members = "CAMID(\"LDAP abbott.corp:u:cn=kasikms,ou=users\")";
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject(members);
		PropEnum[] properties = new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath };
		BaseClass[] member = cmService.query(spMulti, properties, new Sort[]{}, new QueryOptions());

		PropEnum[] props = { PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
		String groups = "CAMID(\":Authors\")";

		SearchPathMultipleObject groupPathObj = new SearchPathMultipleObject(groups);
		BaseClass[] queryResults = cmService.query(groupPathObj, props, new Sort[]{}, new QueryOptions());
		
		// FIXED: Indexing the queryResults array directly to obtain the target Role
		Role role = (queryResults != null && queryResults.length > 0) ? (Role) queryResults[0] : null;

		if (role != null && member != null && member.length > 0) {
			if (role.getMembers().getValue() == null)
			{
				role.setMembers(new BaseClassArrayProp());
				role.getMembers().setValue(member);
				cmService.update(new BaseClass[] { role }, new UpdateOptions());
			}
			else
			{
				BaseClass[] currentMembers = role.getMembers().getValue();
				java.util.List<BaseClass> retainedMembers = new java.util.ArrayList<>();

				for (BaseClass obj : currentMembers)
				{
					SearchPathMultipleObject itemPath = new SearchPathMultipleObject(obj.getSearchPath().getValue());
					BaseClass[] memberProps = cmService.query(itemPath, props, new Sort[]{}, new QueryOptions());

					if (memberProps != null && memberProps.length > 0) {
						String csMember = memberProps[0].getDefaultName().getValue();
						String csMemberPath = memberProps[0].getSearchPath().getValue();
						if ((csMember.compareTo(member[0].getDefaultName().getValue()) != 0)
							&& (csMemberPath.compareTo(member[0].getSearchPath().getValue()) != 0))
						{
							retainedMembers.add(obj);
						}
					}
				}

				role.setMembers(new BaseClassArrayProp());
				// FIXED: Swapped 'new BaseClass' statement to a valid array layout 'new BaseClass[0]'
				role.getMembers().setValue(retainedMembers.toArray(new BaseClass[0]));

				cmService.update(new BaseClass[] { role }, new UpdateOptions());
			}
		}
	}

	public static void generateUsers() throws TCGMException
	{
		final String reportNetEndPoint = AppConst.getInstance().getReportNetServiceLocator();
		final String nameSpaceID = AppConst.getInstance().getReportNetNameSpace();
		final String userName = AppConst.getInstance().getReportNetUsername();
		final String password = AppConst.getInstance().getReportNetPassword();
		
		String fileName = System.getProperty("catalina.home") + "/webapps/tcgm/include/users.txt";			
		
		SecurityOverview so = new SecurityOverview(reportNetEndPoint);
		so.quickLogon(nameSpaceID, userName, password);
		
		FileOutputStream fs = null;
		try 
		{
			fs = new FileOutputStream(fileName);
		}
		catch (FileNotFoundException e) 
		{
			myLogger.error("Failed to generate file generation stream handle output context", e);
		}
		PrintStream fout = new PrintStream(fs);
	
		fout.println("Role\tUser Name\tUser ID\tDate Created\tDate Last Logged");
		String nsToQuery = ":";
	
		BaseClass[] roleIDs = so.getRoles(nsToQuery);
		BaseClass[] groupIDs = so.getGroups(nsToQuery);
		Account[] targetAccount = null;
	
		try {
			for (int x = 0; x < roleIDs.length; x++)
			{
				targetAccount = so.getMembers("expandMembers(" + roleIDs[x].getSearchPath().getValue() + ")");

				if (targetAccount != null && targetAccount.length != 0)
				{
					Calendar calendar = Calendar.getInstance();
					String dateTime = "";
					String dateTimeModified = "";
				
					for (int i = 0; i < targetAccount.length; i++)
					{
						calendar = targetAccount[i].getCreationTime().getValue();
						if (calendar != null) {
							dateTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
									   calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
									   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
						} else {
							dateTime = "None";
						}
					
						calendar = targetAccount[i].getModificationTime().getValue();
						if (calendar != null) {
							dateTimeModified = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
											   calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
											   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
						} else {
							dateTimeModified = "None";
						}
							
						String rawPath = targetAccount[i].getSearchPath().getValue();
						String userIdPart = (rawPath.contains("cn=") && rawPath.contains(",ou")) ? 
								rawPath.substring(rawPath.indexOf("cn=") + 3, rawPath.indexOf(",ou")) : rawPath;

						fout.println("Role:" + roleIDs[x].getDefaultName().getValue() + "\t" + targetAccount[i].getDefaultName().getValue() + "\t" + userIdPart + "\t" + dateTime + "\t" + dateTimeModified);
					}
				}
			}
		
			for (int x = 0; x < groupIDs.length; x++)
			{
				String allusers = "All Authenticated Users";
				String everyone = "Everyone";
				boolean test1 = groupIDs[x].getDefaultName().getValue().equals(allusers);
				boolean test2 = groupIDs[x].getDefaultName().getValue().equals(everyone);
				if (!test1 && !test2)
				{
					targetAccount = so.getMembers("expandMembers(" + groupIDs[x].getSearchPath().getValue() + ")");
					if (targetAccount != null && targetAccount.length != 0)
					{
						Calendar calendar = Calendar.getInstance();
						String dateTime = "";
						String dateTimeModified = "";
					
						for (int i = 0; i < targetAccount.length; i++)
						{
							calendar = targetAccount[i].getCreationTime().getValue();
							if (calendar != null) {
								dateTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
										   calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
										   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
							} else {
								dateTime = "None";
							}
					
							calendar = targetAccount[i].getModificationTime().getValue();
							if (calendar != null) {
								dateTimeModified = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
												   calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
												   calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
							} else {
								dateTimeModified = "None";
							}
							
							String rawPath = targetAccount[i].getSearchPath().getValue();
							String userIdPart = (rawPath.contains("cn=") && rawPath.contains(",ou")) ? 
									rawPath.substring(rawPath.indexOf("cn=") + 3, rawPath.indexOf(",ou")) : rawPath;

							fout.println("Group:" + groupIDs[x].getDefaultName().getValue() + "\t" + targetAccount[i].getDefaultName().getValue() + "\t" + userIdPart + "\t" + dateTime + "\t" + dateTimeModified);
						}
					}
				}
			}
			fout.close();
		} catch (NullPointerException e) {
			myLogger.error("Null structural assignment discovered traversing account listing iterations", e);
		}
	}	
}
