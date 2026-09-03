package abbott.ai.tcgm.helpers;

/*******************************************************************************

 * $Abbott: ActiveDirSearchMgr,v 1.0 2007/08/11 11:22:00 $
 * Copyright (C) 2007  Abbott International,. All Rights Reserved.
 * $name:         ActiveDirSearchMgr.java
 * $description:  The ActiveDirSearchMgr.java retrieves all the users based on
 *				  the user specified search criterion. The results are sorted
 *                based on the user preference.This code references ClientSideSort.java
 *                Provided by Novell as a part of Sample code for LDAP classes for Java.
 ******************************************************************************/

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.entities.ActiveDirSearchDtlBean;
import abbott.ai.tcgm.exception.TCGMException;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPCompareAttrNames;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

public class ActiveDirSearchMngr implements TCGMMngr

{
	public final String className = this.getClass().getName();
	private static Logger myLogger = Logger.getLogger( "LDAPUserSearch" );
	/**
	 * Default Constructor
	 */
	public ActiveDirSearchMngr()
	{}
	
	public ArrayList getUserListAll(String firstName, String lastName,ArrayList usrList, String[] namesToSortBy, boolean[] sortOrder)
			  throws TCGMException

		{
			String methodName = "getUserList(firstName,lastName,namesToSortBy,sortOrder)";
			/*****************************************************************************
			 * The Search scope provides functionality to check at one level below
			 * (LDAPConnection.SCOPE_ONE) or at a sub tree level (LDAPConnection.SCOPE_SUB).
			 ******************************************************************************/
			int searchScope = LDAPConnection.SCOPE_SUB;
			int ldapVersion = LDAPConnection.LDAP_V3;
			/*****************************************************************************
			 * Get the LDAP Authentication host, port,credentials ,etc information from
			 * the configiration file (web.xml) file.
			 ******************************************************************************/
			/*int ldapPort      = Integer.parseInt(AppConst.getAuthenticationPort());
			String ldapHost   = AppConst.getAuthenticationHost()  ;
			String loginDN    = AppConst.getAuthenticationUserId();
			String password   = AppConst.getAuthenticationPassword();
			String searchBase = AppConst.getAuthenticationBaseDn();*/
		
			int ldapPort      = 389;
			String ldapHost   = "directory.abbott.corp"  ;
			String loginDN    = "LDAPBIND";
			String password   = "Password1";
			String searchBase = "OU=Users,DC=abbott,DC=corp";
			
			
			LDAPConnection conn = new LDAPConnection();

			/*****************************************************************************
			 * The user can search on the first name or the last name of the user.
			 * Based on the search the searchFilter would change. There seems to be a
			 * limitation that only a single filter(either first name or last name) can be
			 * specified at a time and not both. Need to dig this more to find ways to
			 * provide multiple search filters.
			 ******************************************************************************/
			String filter="";
			ArrayList userDtls = new ArrayList();
			try
			{
			for(int i=0;i<usrList.size();i++){
			
				filter = "(userPrincipalName=" + usrList.get(i) + ")";
			
			String searchFilter = "(&" + filter + ")";
			myLogger.debug("Search Filter is :" + searchFilter);

			

			/*****************************************************************************
			 * Get the connection to LDAP , connect and bind.
			 ******************************************************************************/
			

		
				conn.connect(ldapHost, ldapPort);
				conn.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

				/*****************************************************************************
				 * Get the search results (i.e users).The attributes to be retrieved are specified
				 * as a String Array. This should be taken out of here and should be derived from
				 * a config file.
				 ******************************************************************************/
				LDAPSearchResults searchResults = conn.search(searchBase, searchScope, searchFilter,
						new String[] { "cn", "givenName", "mail", "sn", "userPrincipalName", "employeeNumber","employeeType","division" },false);


				/*****************************************************************************
				 * Add the results to a TreeSet.
				 ******************************************************************************/
				TreeSet sortedResults = new TreeSet();
				while (searchResults.hasMore())
				{
					try
					{
						sortedResults.add(searchResults.next());
					}
					catch (LDAPException ldape)
					{
						myLogger.error(className,ldape);
						continue;
					}
				}

				/*****************************************************************************
				 * Reshuffle the search results based on the sort criteria specified by the user.
				 ******************************************************************************/
				LDAPCompareAttrNames myComparator = new LDAPCompareAttrNames(namesToSortBy, sortOrder);
				Object sortedSpecial[] = sortedResults.toArray();
				Arrays.sort(sortedSpecial, myComparator);

				/*****************************************************************************
				 * Add each result bean to an ArrayList
				 ******************************************************************************/
				for (int j = 0; j < sortedSpecial.length; j++)
				{
					userDtls.add(buildUserDetail((LDAPEntry) sortedSpecial[j]));
				}
			}
				conn.disconnect();
			}
			catch (LDAPException ldape)
			{
				myLogger.error(className,ldape);
				throw new TCGMException(this.className,methodName,ldape.toString());
			}
			catch (UnsupportedEncodingException usee)
			{
				myLogger.error(className,usee);
				throw new TCGMException(this.className,methodName,usee.toString());
			}
			return userDtls;
		}


	/**
	 *
	 * @param String     firstName (wildcard(*) can be specified)
	 * @param String     lastName (wildcard(*) can be specified)
	 * @param String[]   namesToSortBy
	 * @param boolean[]  sortOrder
	 * @return ArrayList userDtls
	 * @throws GPSException
	 */
	public ArrayList getUserList(String firstName, String lastName,String userId, String[] namesToSortBy, boolean[] sortOrder)
	      throws TCGMException

	{
		String methodName = "getUserList(firstName,lastName,namesToSortBy,sortOrder)";
		/*****************************************************************************
		 * The Search scope provides functionality to check at one level below
		 * (LDAPConnection.SCOPE_ONE) or at a sub tree level (LDAPConnection.SCOPE_SUB).
		 ******************************************************************************/
		int searchScope = LDAPConnection.SCOPE_SUB;
		int ldapVersion = LDAPConnection.LDAP_V3;
		/*****************************************************************************
		 * Get the LDAP Authentication host, port,credentials ,etc information from
		 * the configiration file (web.xml) file.
		 ******************************************************************************/
		int ldapPort      = Integer.parseInt(AppConst.getAuthenticationPort());
		String ldapHost   = AppConst.getAuthenticationHost()  ;
		String loginDN    = AppConst.getAuthenticationUserId();
		String password   = AppConst.getAuthenticationPassword();
		String searchBase = AppConst.getAuthenticationBaseDn();
		
		/*int ldapPort      = 389;
		String ldapHost   = "directory.abbott.corp"  ;
		String loginDN    = "LDAPBIND";
		String password   = "Password1";
		String searchBase = "OU=Users,DC=abbott,DC=corp";*/

		/*****************************************************************************
		 * The user can search on the first name or the last name of the user.
		 * Based on the search the searchFilter would change. There seems to be a
		 * limitation that only a single filter(either first name or last name) can be
		 * specified at a time and not both. Need to dig this more to find ways to
		 * provide multiple search filters.
		 ******************************************************************************/
		String filter="";
		if(!userId.equalsIgnoreCase("")){
			filter = "(userPrincipalName=" + userId + "*)";
			
		}else{
		 
			if (!lastName.equalsIgnoreCase(""))
			{
				filter = "(sn=" + lastName + "*)";
			}
			if (!firstName.equalsIgnoreCase(""))
			{
				filter += "(givenName=" + firstName + "*)";
			}
		}	
		String searchFilter = "(&" + filter + ")";
		myLogger.debug("Search Filter is :" + searchFilter);

		ArrayList userDtls = new ArrayList();

		/*****************************************************************************
		 * Get the connection to LDAP , connect and bind.
		 ******************************************************************************/
		LDAPConnection conn = new LDAPConnection();

		try
		{
			conn.connect(ldapHost, ldapPort);
			conn.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

			/*****************************************************************************
			 * Get the search results (i.e users).The attributes to be retrieved are specified
			 * as a String Array. This should be taken out of here and should be derived from
			 * a config file.
			 ******************************************************************************/
			LDAPSearchResults searchResults = conn.search(searchBase, searchScope, searchFilter,
					new String[] { "cn", "givenName", "mail", "sn", "userPrincipalName","employeeNumber","division","employeeType" },false);


			/*****************************************************************************
			 * Add the results to a TreeSet.
			 ******************************************************************************/
			TreeSet sortedResults = new TreeSet();
			while (searchResults.hasMore())
			{
				try
				{
					sortedResults.add(searchResults.next());
				}
				catch (LDAPException ldape)
				{
					myLogger.error(className,ldape);
					continue;
				}
			}

			/*****************************************************************************
			 * Reshuffle the search results based on the sort criteria specified by the user.
			 ******************************************************************************/
			LDAPCompareAttrNames myComparator = new LDAPCompareAttrNames(namesToSortBy, sortOrder);
			Object sortedSpecial[] = sortedResults.toArray();
			Arrays.sort(sortedSpecial, myComparator);

			/*****************************************************************************
			 * Add each result bean to an ArrayList
			 ******************************************************************************/
			for (int j = 0; j < sortedSpecial.length; j++)
			{
				userDtls.add(buildUserDetail((LDAPEntry) sortedSpecial[j]));
			}
			conn.disconnect();
		}
		catch (LDAPException ldape)
		{
			myLogger.error(className,ldape);
			throw new TCGMException(this.className,methodName,ldape.toString());
		}
		catch (UnsupportedEncodingException usee)
		{
			myLogger.error(className,usee);
			throw new TCGMException(this.className,methodName,usee.toString());
		}
		return userDtls;
	}

	/**
	 *
	 * @param LDAPEntry    entry
	 * @return LDAPUserDtl userDtl
	 */
	public ActiveDirSearchDtlBean buildUserDetail(LDAPEntry entry)
	{

		//System.out.println(entry.getDN());
		//System.out.println("\tAttributes: ");
		LDAPAttributeSet attributeSet = entry.getAttributeSet();
		Set sortedAttributes = new TreeSet(attributeSet);
		Iterator allAttributes = sortedAttributes.iterator();
		int count = 0;
		ActiveDirSearchDtlBean userDtl = new ActiveDirSearchDtlBean();

		while (allAttributes.hasNext())
		{

			LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
			String attributeName = attribute.getName();
			//System.out.println("\t\t" + attributeName);
			Enumeration allValues = attribute.getStringValues();
			if (allValues != null)
			{
				while (allValues.hasMoreElements())
				{
					String Value = (String) allValues.nextElement();
					//System.out.println("\t\t\t" + Value);
					if (Value == null)	{ Value = " "; }

					/*****************************************************************************
					 * For each attribute, set the value in the entity bean.
					 ******************************************************************************/

					if (attribute.getName().equalsIgnoreCase("cn"))
					{
						//userDtl.setUserId(Value);
					}
					/*else if (attribute.getName().equalsIgnoreCase("employeeType"))
					{
						userDtl.setEmployeeType(Value);
					}*/
					else if (attribute.getName().equalsIgnoreCase("givenName"))
					{
						userDtl.setFirstName(Value);
					}
					else if (attribute.getName().equalsIgnoreCase("mail"))
					{
						userDtl.setEmail(Value);
					}
					else if (attribute.getName().equalsIgnoreCase("sn"))
					{
						userDtl.setLastName(Value);
					}
					else if (attribute.getName().equalsIgnoreCase("userPrincipalName"))
					{
						userDtl.setUserId(Value);
					}
					else if (attribute.getName().equalsIgnoreCase("employeeNumber"))
					{
						userDtl.setAbtNotesId(Value);
					}else if(attribute.getName().equalsIgnoreCase("employeeType")){
						userDtl.setEmployeeType(Value);
					}else if(attribute.getName().equalsIgnoreCase("division")){
						if(Value != null && Value.indexOf("-") >0) {
							userDtl.setDivision(Value.substring(0, Value.indexOf("-")));
						}
					}
				}

			}
		}
		return userDtl;
	}


	 /*
	 * Given a ArrayList of ActiveDirSearchDtlBean, returns the ones that have the selected flag set to true
	 * @param userList ArrayList
	 * @return ArrayList
	 */
	public ActiveDirSearchDtlBean getSelectedUser(ArrayList userList,String userId)
	{
		ActiveDirSearchDtlBean userDtl = new ActiveDirSearchDtlBean();
		for(int i = 0; i < userList.size(); i++)
		{
			userDtl = (ActiveDirSearchDtlBean)userList.get(i);
			if (userDtl.getUserId().equalsIgnoreCase(userId))
			{
				break;
			}
		}
		return userDtl;
	}
	
	
}
