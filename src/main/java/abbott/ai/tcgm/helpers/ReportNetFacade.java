package abbott.ai.tcgm.helpers;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.entities.ReportDefinition;
import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.AccessEnum;
import com.cognos.developer.schemas.bibus._3.AddOptions;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseClassArrayProp;
import com.cognos.developer.schemas.bibus._3.BooleanProp;
import com.cognos.developer.schemas.bibus._3.ClassEnum;
import com.cognos.developer.schemas.bibus._3.CognosReportNetPortType;
import com.cognos.developer.schemas.bibus._3.CognosReportNetServiceLocator;
import com.cognos.developer.schemas.bibus._3.Folder;
import com.cognos.developer.schemas.bibus._3.JobDefinition;
import com.cognos.developer.schemas.bibus._3.MultilingualToken;
import com.cognos.developer.schemas.bibus._3.MultilingualTokenProp;
import com.cognos.developer.schemas.bibus._3.NamespaceFolder;
import com.cognos.developer.schemas.bibus._3.OrderEnum;
import com.cognos.developer.schemas.bibus._3.ParameterValue;
import com.cognos.developer.schemas.bibus._3.ParameterValueArrayProp;
import com.cognos.developer.schemas.bibus._3.ParmValueItem;
import com.cognos.developer.schemas.bibus._3.Permission;
import com.cognos.developer.schemas.bibus._3.Policy;
import com.cognos.developer.schemas.bibus._3.PolicyArrayProp;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.RefProp;
import com.cognos.developer.schemas.bibus._3.Report;
import com.cognos.developer.schemas.bibus._3.ReportView;
import com.cognos.developer.schemas.bibus._3.RetentionRule;
import com.cognos.developer.schemas.bibus._3.RetentionRuleArrayProp;
import com.cognos.developer.schemas.bibus._3.RunOption;
import com.cognos.developer.schemas.bibus._3.RunOptionArrayProp;
import com.cognos.developer.schemas.bibus._3.RunOptionBoolean;
import com.cognos.developer.schemas.bibus._3.RunOptionEnum;
import com.cognos.developer.schemas.bibus._3.RunOptionString;
import com.cognos.developer.schemas.bibus._3.RunOptionStringArray;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.SimpleParmValueItem;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.StringProp;
import com.cognos.developer.schemas.bibus._3.UpdateActionEnum;
import com.cognos.developer.schemas.bibus._3.UpdateOptions;
import com.cognos.developer.schemas.bibus._3.Group;
import com.cognos.developer.schemas.bibus._3.Account;
import org.apache.axis.client.Stub;

public class ReportNetFacade {
	private static CognosReportNetPortType service;
	//private final static String NAMESPACE = "Abbott Active Directory";
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.helpers.ReportNetFacade" );	
	public ReportNetFacade()
		throws TCGMException, ServiceException, MalformedURLException, RemoteException  
		{
			if (service == null || isTimedOut(service)) 
			{
				final String SERVICELOCATOR = AppConst.getInstance().getReportNetServiceLocator();
				final String NAMESPACE = AppConst.getInstance().getReportNetNameSpace();
				final String USERNAME = AppConst.getInstance().getReportNetUsername();
				final String PASSWORD = AppConst.getInstance().getReportNetPassword();
				
				service =
					(new CognosReportNetServiceLocator()).getCognosReportNetPort(						
						new URL(SERVICELOCATOR));
				service
					.logon(
						buildCredentialXML(NAMESPACE, USERNAME, PASSWORD),
						new String[] {
			});
				((Stub) service).setTimeout(0);
		}
	}

	/**
	 * check if the service hasn't yet timed out
	 *
	 * @param service
	 * @return <code>true</code> if the service has timed out
	 */
	private boolean isTimedOut(CognosReportNetPortType service) {
		try {
			// running a query for the user's "my folder".  this should be fairly quick.
			service.query("~", new PropEnum[] {
			}, new Sort[] {
			}, new QueryOptions());
		} catch (RemoteException e) {
			return true;
		}
		return false;
	}

	/**
	 * This method executes a report in HTML format and saves the output to the content store.
	 *
	 * @param aSearchPath   The search path for the report you want to execute.
	 * @param aParameterMap A map of parameters (key -> value[])
	 * @param report Contains the ReportDefinition (Report table)
	 * @throws RemoteException
	 */
	public RunOption[] executeReport(String aSearchPath, ReportDefinition report)
		throws RemoteException {
		RunOption[] runOptions = new RunOption[4];

		RunOptionBoolean saveOutput = new RunOptionBoolean();
		saveOutput.setName(RunOptionEnum.saveOutput);
		saveOutput.setValue(true);

		RunOptionBoolean doPrompt = new RunOptionBoolean();
		doPrompt.setName(RunOptionEnum.prompt);
		doPrompt.setValue(false);

		RunOptionBoolean burstable = new RunOptionBoolean();
		burstable.setName(RunOptionEnum.burst);
		burstable.setValue(false);

		RunOptionStringArray outputFormat = new RunOptionStringArray();
		outputFormat.setName(RunOptionEnum.outputFormat);
		/******************************************************************************
		 * Modifed by: Udaya B Aravapalli on 03/13/2006.
		 * Get the Output Format values from the REPORT.OUTPUT_FORMAT column value.
		 * The values will be delimited by '::'. i.e if the value is PDF::HTML
		 * we need to pregenerate the report in PDF and HTML formats.
		 ******************************************************************************/
		String[] format = report.getOutputFormat().split(TCGMConstants.DT_COLON_DELIMITER);
		
		if ((report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF)) 
		|| (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S))
		|| (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A))
		|| (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_HQ_B))
		|| (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_DIV_B)))
		
		{
			burstable.setValue(true);
		}

		if (!format[0].equalsIgnoreCase("NONE"))
		{
			if ((report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_HQ_I)) 
			|| (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_I)))
			{
				saveOutput.setValue(false);
			}
			outputFormat.setValue(format);
		}
		else
		{
			saveOutput.setValue(false);
			outputFormat.setValue(null);
		}
		runOptions[0] = saveOutput;
		runOptions[1] = outputFormat;
		runOptions[2] = doPrompt;
		runOptions[3] = burstable;

		return runOptions;
	}
	
	/**
	 * This method executes a report in HTML format, saves the output to the content store, and then sends the output
	 * to the specified printer.
	 *
	 * @param aSearchPath     The search path for the report you want to execute.
	 * @param aParameterMap   A map of parameters (key -> value[])
	 * @param aPrinterAddress The address of the printer to which the output will be sent
	 * @throws RemoteException
	 */
	public RunOption[] executeAndPrintReport(String aSearchPath, String aPrinterAddress, ReportDefinition report ) throws RemoteException
	{
	  RunOption[] runOptions = new RunOption[5];

	  RunOptionBoolean saveOutput = new RunOptionBoolean();
	  saveOutput.setName( RunOptionEnum.saveOutput );
	  saveOutput.setValue( true );

	  RunOptionStringArray outputFormat = new RunOptionStringArray();
	  outputFormat.setName( RunOptionEnum.outputFormat );
	  String[] format = report.getOutputFormat().split(TCGMConstants.DT_COLON_DELIMITER);
	  //outputFormat.setValue( new String[]{"PDF"} );

	  if (!format[0].equalsIgnoreCase("NONE"))
		  {
			  outputFormat.setValue(format);
		  }
		  else
		  {
			  outputFormat.setValue(null);
		  }
		  
	  RunOptionBoolean doPrompt = new RunOptionBoolean();
	  doPrompt.setName( RunOptionEnum.prompt );
	  doPrompt.setValue( false );

	  RunOptionBoolean doPrint = new RunOptionBoolean();
	  doPrint.setName( RunOptionEnum.print );
	  doPrint.setValue( true );

	  RunOptionString printerAddress = new RunOptionString();
	  printerAddress.setName( RunOptionEnum.printerAddress );
	  printerAddress.setValue( aPrinterAddress );

	  runOptions[0] = saveOutput;
	  runOptions[1] = outputFormat;
	  runOptions[2] = doPrompt;
	  runOptions[3] = doPrint;
	  runOptions[4] = printerAddress;

	 return runOptions;
	}	
	
	public void burstReport(String aSearchPath, Map aParameterMap)
		throws RemoteException {
		RunOption[] runOptions = new RunOption[4];

		RunOptionBoolean saveOutput = new RunOptionBoolean();
		saveOutput.setName(RunOptionEnum.saveOutput);
		saveOutput.setValue(true);
		
		RunOptionBoolean doBurst = new RunOptionBoolean();
		doBurst.setName(RunOptionEnum.burst);
		doBurst.setValue(true);

		RunOptionStringArray outputFormat = new RunOptionStringArray();
		outputFormat.setName(RunOptionEnum.outputFormat);
		outputFormat.setValue(new String[] { "HTML" });

		RunOptionBoolean doPrompt = new RunOptionBoolean();
		doPrompt.setName(RunOptionEnum.prompt);
		doPrompt.setValue(false);

		runOptions[0] = saveOutput;
		runOptions[1] = outputFormat;
		runOptions[2] = doPrompt;
		runOptions[3] = doBurst;

		service.execute(
			aSearchPath,
			createParameterValueArray(aParameterMap),
			runOptions);
	}

	/**
	 * This method adds a new folder to the content store.  It should inherit permissions from its parent.
	 *
	 * @param aSearchPath The parent folder in which to insert the new folder.
	 * @param aName       The name of the new folder.
	 * @return The folder that was just added.
	 * @throws RemoteException
	 */
	public BaseClass addFolder(String aSearchPath, String aName)
		throws RemoteException {
		Folder folder = new Folder();

		MultilingualToken[] folderNames = new MultilingualToken[1];
		folderNames[0] = new MultilingualToken();
		folderNames[0].setLocale("en");
		folderNames[0].setValue(aName);

		folder.setName(new MultilingualTokenProp());
		folder.getName().setValue(folderNames);

		AddOptions addOpts = new AddOptions();
		addOpts.setUpdateAction(UpdateActionEnum.update);

		return service.add(aSearchPath, new BaseClass[] { folder }, addOpts)[0];
	}

	/**
	 * This method creates a report view in a specified location.  All parameters are saved along with the
	 * report view.
	 *
	 * @param aSource       The search path for the base report.
	 * @param aDestination  The search path to which the report view will be saved.
	 * @param aName         The name for the report view.
	 * @param aParameterMap All user-specified parameters.
	 * @throws RemoteException
	 */
	public BaseClass createReportView(
		String aSource,
		String aDestination,
		String aName,
		Map aParameterMap,
	    ReportDefinition report)
		throws RemoteException {
		ReportView reportView = new ReportView();
		BaseClassArrayProp base = new BaseClassArrayProp();
		base.setValue(queryForReports(aSource));
		reportView.setBase(base);

		AddOptions addOpts = new AddOptions();
		addOpts.setUpdateAction(UpdateActionEnum.update);

		MultilingualToken[] reportNames = new MultilingualToken[1];
		reportNames[0] = new MultilingualToken();
		reportNames[0].setLocale("en");
		reportNames[0].setValue(aName);

		reportView.setName(new MultilingualTokenProp());
		reportView.getName().setValue(reportNames);

		ParameterValueArrayProp savedParameters = new ParameterValueArrayProp();
		savedParameters.setValue(createParameterValueArray(aParameterMap));
		reportView.setParameters(savedParameters);

		if (Integer.parseInt(report.getReportVersions()) > 1)
		{
			RetentionRuleArrayProp retentionRules = new  RetentionRuleArrayProp();
			RetentionRule[] outputRule = new RetentionRule[1];
			outputRule[0] = new RetentionRule();
			outputRule[0].setObjectClass(ClassEnum.fromString("reportVersion"));
			outputRule[0].setProp(PropEnum.creationTime);
			BigInteger max = BigInteger.valueOf(Integer.parseInt(report.getReportVersions()));
			outputRule[0].setMaxObjects(max);
			retentionRules.setValue(outputRule);
			reportView.setRetentions(retentionRules);
		}
				
		// If the report is an interactive report
		// the set the Prompts to true.
		if ((report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_HQ_I)) 
		|| (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_I)))
		{
			BooleanProp crnTrue = new BooleanProp();
			crnTrue.setValue(true);
			reportView.setExecutionPrompt(crnTrue);
		}
		else
		{
			BooleanProp crnFalse = new BooleanProp();
			crnFalse.setValue(false);
			reportView.setExecutionPrompt(crnFalse);
		}
	
		return service.add(
			aDestination,
			new BaseClass[] { reportView },
			addOpts)[0];
	}

	public BaseClass[] queryForReports(String aSearchPath)
		throws RemoteException {
		PropEnum properties[] =
			new PropEnum[] {
				PropEnum.searchPath,
				PropEnum.searchPathForURL,
				PropEnum.defaultName,
				PropEnum.base };

		QueryOptions queryOptions = new QueryOptions();

		RefProp[] refProps = new RefProp[1];
		refProps[0] = new RefProp();
		refProps[0].setProperties(properties);
		refProps[0].setRefPropName(PropEnum.parameters);
		queryOptions.setRefProps(refProps);

		return service.query(aSearchPath, properties, new Sort[] {
		}, queryOptions);
	}
	
	public BaseClass[] queryForObjects(String aSearchPath)
		throws RemoteException {
		PropEnum properties[] =
			new PropEnum[] {
				PropEnum.searchPath,
				PropEnum.searchPathForURL,
				PropEnum.defaultName };

		return service.query(aSearchPath, properties, new Sort[] {
		}, new QueryOptions());
	}

	private ParameterValue[] createParameterValueArray(Map aParameterMap) {
		List parameterList = new ArrayList();
		Iterator parameterIterator = aParameterMap.keySet().iterator();
		while (parameterIterator.hasNext()) {
			String parameter = (String) parameterIterator.next();
			ParameterValue parameterValue = new ParameterValue();
			parameterValue.setName(parameter);

			List parmValueItemList = new ArrayList();
			String[] parameterValues = (String[]) aParameterMap.get(parameter);
			if (parameterValues != null) {
				for (int i = 0; i < parameterValues.length; i++) {
					SimpleParmValueItem parmValueItem =
						new SimpleParmValueItem();
					parmValueItem.setUse(parameterValues[i]);
					parmValueItem.setDisplay(parameterValues[i]);

					parmValueItemList.add(parmValueItem);
				}
			}
			parameterValue
				.setValue(
					(ParmValueItem[]) parmValueItemList
					.toArray(new ParmValueItem[] {
			}));
			parameterList.add(parameterValue);
		}

		return (ParameterValue[]) parameterList.toArray(new ParameterValue[] {
		});
	}

	private String buildCredentialXML(
		String aNamespaceId,
		String aUserName,
		String aPassword) {
		StringBuffer credentialXML = new StringBuffer();

		credentialXML.append("<credential>");
		credentialXML.append("<namespace>").append(aNamespaceId).append(
			"</namespace>");
		credentialXML.append("<username>").append(aUserName).append(
			"</username>");
		credentialXML.append("<password>").append(aPassword).append(
			"</password>");
		credentialXML.append("</credential>");

		return credentialXML.toString();
	}

	public void addNewJob(String packageName, String jobName, String[] reportsArray, RunOptionArrayProp[] runOptionArrayProp)
	            throws TCGMException 
	{
		String parameterList = "packageName "+packageName+"  jobName  "+jobName+" reportsArray "+reportsArray.toString()+" runOptionArrayProp"+runOptionArrayProp.toString();
		try 
		{
			ReportNetNewJob myJob = new ReportNetNewJob();
			JobDefinition newJob = (JobDefinition) myJob.addNewJob(service, jobName, packageName);
			myJob.addReports(service, newJob, reportsArray, runOptionArrayProp);
			myJob.setSequence(service, newJob, AppConst.getReportNetExecutionSequence() );
			String jobEventID = service.submit(newJob.getSearchPath().getValue(), new ParameterValue[0], new RunOption[0] );
//			myJob.setScheduleByDay(service, newJob);
//			myJob.enableJob(service, newJob);
		} 
		catch (Exception e) 
		{
			myLogger.error("An Exception Occured While Executing the report. Message: " + e.toString());
			 throw new TCGMException("ReportNetFacade", "addNewJob",parameterList, e.toString());
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
	public void addToRole(String userSearchPath, String groupSearchPath)
		throws java.rmi.RemoteException
	{
		// Get the current role membership.
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(userSearchPath);
		PropEnum properties[] =
			new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath };
		BaseClass member[] = service.query(spMulti.getValue(), properties, new Sort[]{}, new QueryOptions());
			
		PropEnum[] props =
			{ PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
	
		BaseClass groupObjects[] = new BaseClass[]{};
		spMulti.setValue(groupSearchPath);

		Group group = (Group)service.query(spMulti.getValue(), props, new Sort[]{}, new QueryOptions())[0];

		if (group.getMembers().getValue() == null)
		{
			group.setMembers(new BaseClassArrayProp());
			group.getMembers().setValue(member);
			service.update(new BaseClass[] { group }, new UpdateOptions());
		}
		else
		{
			// Preserve all the existing members.
			BaseClass[] newMembers =
				new BaseClass[group.getMembers().getValue().length + 1];
			int index = 0;
			BaseClass obj = null;
			for (int i = 0; i < group.getMembers().getValue().length; i++)
			{
				obj = group.getMembers().getValue()[i];
				newMembers[index] = obj;
				index++;
			}

			newMembers[index] = member[0];

			group.setMembers(new BaseClassArrayProp());
			group.getMembers().setValue(newMembers);

			// Update the membership.
			service.update(new BaseClass[] { group }, new UpdateOptions());
		}
	}

	/**
		 * Add the specified group to the given Directory.
		 *
		 * @param   groupName        Group name to be added.
		 * @param   searchPath       Search path to the group.
		 *
		 */
		public void addGroups(String userSearchPath, String groupSearchPath)
			throws java.rmi.RemoteException
		{
			
			groupSearchPath = "CAMID(\":nTCGM:\")";
			userSearchPath = "CAMID(\":nTCGM:11AI\")";//CAMID("LDAP abbott.corp:u:cn=" + userId + ",ou=users");
			//CAMID("LDAP abbott.corp:f:ou=users")

			/*
		 MultilingualToken[] folderNames = new MultilingualToken[1];
		folderNames[0] = new MultilingualToken(); 		folderNames[0].setLocale("en");
		folderNames[0].setValue(aName); 		folder.setName(new MultilingualTokenProp());
		folder.getName().setValue(folderNames); 
		AddOptions addOpts = new AddOptions(); 
		addOpts.setUpdateAction(UpdateActionEnum.update);
		return service.add(aSearchPath, new BaseClass[] { folder }, addOpts)[0]; 		 */
							
			// Get the current role membership.
			SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
						
			PropEnum[] props =
				{ PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
	
			BaseClass groupObjects[] = new BaseClass[]{};
			spMulti.setValue(userSearchPath);

			NamespaceFolder group = (NamespaceFolder)service.query(spMulti.getValue(), props, new Sort[]{}, new QueryOptions())[0];
			//System.out.println(group.getName());
			
			//System.out.println((service.query(spMulti.getValue(), props, new Sort[]{}, new QueryOptions())[0]).getClass().getName());
			
			if (group.getName()== null)
			{
				MultilingualToken[] names = new MultilingualToken[1];
				names[0] = new MultilingualToken();
				names[0].setLocale("en");
				names[0].setValue(userSearchPath);
				
				MultilingualTokenProp mulTP = new MultilingualTokenProp();
				mulTP.setValue(names);
				group.setName(mulTP);
				AddOptions addOpts = new AddOptions();
				addOpts.setUpdateAction(UpdateActionEnum.update);
				//service.update(new BaseClass[] { group }, new UpdateOptions());
				service.add(groupSearchPath,new BaseClass[] { group }, addOpts);
			}
			/*else
			{
				// Preserve all the existing members.
				BaseClass[] newMembers =
					new BaseClass[group.getMembers().getValue().length + 1];
				int index = 0;
				BaseClass obj = null;
				for (int i = 0; i < group.getMembers().getValue().length; i++)
				{
					obj = group.getMembers().getValue()[i];
					newMembers[index] = obj;
					index++;
				}

				newMembers[index] = member[0];

				group.setMembers(new BaseClassArrayProp());
				group.getMembers().setValue(newMembers);

				// Update the membership.
				service.update(new BaseClass[] { group }, new UpdateOptions());
			}*/
		}

	
	public boolean checkUserExists(String userPath, String groupPath)
	{
		boolean userExists = false;
	
		Account[] targetAccount=null;
		BaseClass[] memberIDs = getMembers(groupPath);
	
		for (int x=0;x<memberIDs.length;x++)
		{
			String allusers = "All Authenticated Users";
			String everyone = "Everyone";
			boolean test1 = memberIDs[x].getDefaultName().getValue().equals(allusers);
			boolean test2 = memberIDs[x].getDefaultName().getValue().equals(everyone);
			if ( !test1 && !test2  )
			{
				if (memberIDs[x].getSearchPath().getValue().equalsIgnoreCase(userPath))
				{
					userExists =  true;
				}
			}
		}
	
	return userExists;	
	}

	public boolean checkGroupExists(String groupPath, String groupPathExpanded)
	{
		boolean userExists = false;
	
		Account[] targetAccount=null;
		BaseClass[] memberIDs = getMembers(groupPathExpanded);
	
		for (int x=0;x<memberIDs.length;x++)
		{
			String allusers = "All Authenticated Users";
			String everyone = "Everyone";
			boolean test1 = memberIDs[x].getDefaultName().getValue().equals(allusers);
			boolean test2 = memberIDs[x].getDefaultName().getValue().equals(everyone);
			if ( !test1 && !test2  )
			{
				if (memberIDs[x].getSearchPath().getValue().equalsIgnoreCase(groupPath))
				{
					userExists =  true;
				}
			}
		}
	
	return userExists;	
	}

	public Account[] getMembers(String targetSearchPath) 
	{
		PropEnum props[] = new PropEnum[] {PropEnum.searchPath, PropEnum.defaultName,
										   PropEnum.portalPage};
		Sort sOpt[] = new Sort[]{}; 
		QueryOptions qOpt = new QueryOptions(); 
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(targetSearchPath);
		Account[] targetAccount = null;
	
		try
		{
			// get the target user's account object. 
			BaseClass targets[] = service.query(spMulti.getValue(), props, sOpt, qOpt);
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

	/**
	 * Add the specified member to the specified role.
	 *
	 * @param   oCrn        CognosReportNetPortType object.
	 * @param   pathOfRole  Search path to the role.
	 * @param   member      User, group or role to be added.
	 *
	 */
	public void removeFromRole(String userSearchPath, String roleSearchPath)
		throws java.rmi.RemoteException
	{
	
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();
		spMulti.setValue(userSearchPath);
		PropEnum properties[] =
			new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath };
		BaseClass[] member = service.query(spMulti.getValue(), properties, new Sort[]{}, new QueryOptions());
				
		PropEnum[] props =
			{ PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
		
		BaseClass groupObjects[] = new BaseClass[]{};
		spMulti.setValue(roleSearchPath);

		Group group = (Group)service.query(spMulti.getValue(), props, new Sort[]{}, new QueryOptions())[0];

		if (group.getMembers().getValue() == null)
		{
			group.setMembers(new BaseClassArrayProp());
			group.getMembers().setValue(member);
			service.update(new BaseClass[] { group }, new UpdateOptions());
		}
		else
		{
			// Preserve all the existing members.			
			int index = 0;
			int count = 0;
			String csMember;
			String csMemberPath;
			BaseClass obj = null;
			for (int i = 0; i < group.getMembers().getValue().length; i++)
			{
				obj = group.getMembers().getValue()[i];
				spMulti.setValue(obj.getSearchPath().getValue());

				BaseClass[] memberProps =
					service.query(spMulti.getValue(), props, new Sort[]{}, new QueryOptions());
					//csHandler.queryObjectInCS(oCrn, obj.getSearchPath().getValue());
				if(memberProps.length > 0){						
						count=count+1;					
				}
			}
			BaseClass[] newMembers =
							new BaseClass[count - 1];
			for (int i = 0; i < group.getMembers().getValue().length; i++)
			{
				obj = group.getMembers().getValue()[i];
				spMulti.setValue(obj.getSearchPath().getValue());

				BaseClass[] memberProps =
					service.query(spMulti.getValue(), props, new Sort[]{}, new QueryOptions());
					//csHandler.queryObjectInCS(oCrn, obj.getSearchPath().getValue());
				if(memberProps.length > 0){
	
					csMember = memberProps[0].getDefaultName().getValue();
					csMemberPath = memberProps[0].getSearchPath().getValue();
					if ((csMemberPath.compareTo(member[0].getSearchPath().getValue())
							!= 0))
					{
						newMembers[index] = obj;
						index++;
					}
				}
			}			

			group.setMembers(new BaseClassArrayProp());
			group.getMembers().setValue(newMembers);

			// Update the membership.
			service.update(new BaseClass[] { group }, new UpdateOptions());
		 }
	}	
	public void addGroup(String groupSearchPath, String groupPath)
				throws java.rmi.RemoteException{
					
					//String aSearchPath, String aName)
					
					//groupSearchPath = "CAMID(\":nTCGM:\")";
					//groupPath = "01AI";
				Group folder = new Group();

			MultilingualToken[] folderNames = new MultilingualToken[1];
			folderNames[0] = new MultilingualToken();
			folderNames[0].setLocale("en");
			folderNames[0].setValue(groupPath);

			folder.setName(new MultilingualTokenProp());
			folder.getName().setValue(folderNames);

			AddOptions addOpts = new AddOptions();
			addOpts.setUpdateAction(UpdateActionEnum.update);

			service.add(groupSearchPath, new BaseClass[] { folder }, addOpts);
		}
	public void addToReportGroup(String userSearchPath, String reportSearchPath)
	throws Exception
{
			//String userSearchPath = "CAMID(\"Sample1:u:6\")"; //full searchpath for user account
//			 Search properties: we need the defaultName and the searchPath.
			PropEnum[] properties =
				{ PropEnum.defaultName, PropEnum.searchPath, PropEnum.policies,PropEnum.members }; //, PropEnum.parameters

			// Sort options: ascending sort on the defaultName property.
			Sort[] sortBy = { new Sort()};
			sortBy[0].setOrder(OrderEnum.ascending);
			sortBy[0].setPropName(PropEnum.defaultName);
			
			// Query options; use the defaults.
			QueryOptions options = new QueryOptions();
			
			boolean found = false;		
			//BaseClass[] results=service.query(new SearchPathMultipleObject(reportSearchPath).getValue(), properties, new Sort[]{}, new QueryOptions());
			Folder reportObject = (Folder)service.query(new SearchPathMultipleObject(reportSearchPath).getValue(), properties, new Sort[]{}, new QueryOptions())[0];
			//Report reportObject = (Report)results[0];
			
			

			Permission WritePermission = new Permission();
			WritePermission.setName("traverse");			
			WritePermission.setAccess(AccessEnum.grant);
			
			
			Permission ReadPermission = new Permission();
			ReadPermission.setName("read");			
			ReadPermission.setAccess(AccessEnum.grant);
			
			

			for (int i = 0; i < reportObject.getPolicies().getValue().length && !found; i ++)
			{
				Policy policy = reportObject.getPolicies().getValue()[i];
				//If the security object already exists, update its permissions
				if(policy.getSecurityObject().getSearchPath().getValue().equalsIgnoreCase(userSearchPath))
				{
					found = true;
					Permission newPerms[] = new Permission[2]; 
					//we know we are adding 3 permissions at the same time, and we are also
					//replacing all existing permissions for this user account.

					newPerms[0] = WritePermission;
					newPerms[1] = ReadPermission;			
					
					policy.setPermissions(newPerms);
				}
			}

			//If the security object does not exist, create a new one
			if(!found)
			{
				BaseClass entry = null;

				entry = (BaseClass)service.query(new SearchPathMultipleObject(userSearchPath).getValue(), new PropEnum[]{},new Sort[]{},new QueryOptions())[0];

				Policy newPolicy = new Policy();
				newPolicy.setSecurityObject(entry);
				Permission permissions[] = new Permission[2];
				permissions[0] = WritePermission;
				permissions[1] = ReadPermission;									
				
				newPolicy.setPermissions(permissions);

				PolicyArrayProp existingPols = reportObject.getPolicies();
				Policy newPols[]=null;
				int index = 0;
				
				 newPols = new Policy[existingPols.getValue().length + 1];
				 for(int j = 0; j < existingPols.getValue().length; j ++)
					{
						newPols[j] = existingPols.getValue()[j];
					}
				
				//group.setMembers(new BaseClassArrayProp());
				//group.getMembers().setValue(newMembers);
				newPols[newPols.length-1] = newPolicy;
				reportObject.setPolicies(new PolicyArrayProp());
				reportObject.getPolicies().setValue(newPols);
			}
			

			service.update(new BaseClass[]{reportObject}, new UpdateOptions());
		
}
	
}