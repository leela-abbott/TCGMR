package abbott.ai.tcgm.helpers;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.logging.log4j.LogManager;

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
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.ReportService_PortType;
import com.cognos.developer.schemas.bibus._3.ReportService_ServiceLocator;
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
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.SimpleParmValueItem;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.StringProp;
import com.cognos.developer.schemas.bibus._3.UpdateActionEnum;
import com.cognos.developer.schemas.bibus._3.UpdateOptions;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;
import com.cognos.developer.schemas.bibus._3.Group;
import com.cognos.developer.schemas.bibus._3.Account;

public class ReportNetFacade {
    private static ContentManagerService_PortType cmService;
    private static ReportService_PortType reportService;
    
    private static final Logger myLogger = LogManager.getLogger(ReportNetFacade.class);   

    public ReportNetFacade() throws TCGMException, MalformedURLException, RemoteException {
        if (cmService == null || reportService == null || isTimedOut(cmService)) {
            try {
                final String SERVICELOCATOR = AppConst.getInstance().getReportNetServiceLocator();
                final String NAMESPACE = AppConst.getInstance().getReportNetNameSpace();
                final String USERNAME = AppConst.getInstance().getReportNetUsername();
                final String PASSWORD = AppConst.getInstance().getReportNetPassword();
                
                URL dispatcherUrl = new URL(SERVICELOCATOR);
                
                cmService = new ContentManagerService_ServiceLocator().getcontentManagerService(dispatcherUrl);
                reportService = new ReportService_ServiceLocator().getreportService(dispatcherUrl);
                
                String credentialStr = buildCredentialXML(NAMESPACE, USERNAME, PASSWORD);
                XmlEncodedXML xmlCredentials = new XmlEncodedXML();
                xmlCredentials.set_value(credentialStr);
                
                boolean isLoginSuccessful = false;

                try {
                    cmService.logon(xmlCredentials, new SearchPathSingleObject[0]);
                    SOAPHeaderElement responseHeader = ((Stub) cmService)
                        .getResponseHeader("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader");
                    ((Stub) cmService).setHeader(responseHeader);
                    isLoginSuccessful = true;
                    System.out.println("Logon successful.");

                } catch (RemoteException re) {
                    isLoginSuccessful = false;
                    System.err.println("Logon failed or client error: " + re.getMessage());
                    // Handle exception logic here (e.g., logging, throwing a custom business exception)
                } catch (Exception e) {
                    // Generic exception catching for header parsing issues
                    isLoginSuccessful = false;
                    e.printStackTrace();
                }
                
                
                
                configureTimeout(cmService);
                configureTimeout(reportService);

            } catch (Exception e) {
                myLogger.error("Failed to connect to Cognos 12.1.1 service endpoints", e);
                throw new TCGMException("ReportNetFacade", "Constructor", "Initialization Failure", e.toString());
            }
        }
    }

    private void configureTimeout(Object serviceStub) {
        if (serviceStub == null) return;
        
        try {
            if (serviceStub instanceof Stub axisStub) {
                axisStub.setTimeout(0);
                return;
            }
        } catch (NoClassDefFoundError ignored) {
        }

        String className = serviceStub.getClass().getName();
        if (className.contains("axis")) {
            try {
                Method getServiceClientMethod = serviceStub.getClass().getMethod("_getServiceClient");
                Object serviceClient = getServiceClientMethod.invoke(serviceStub);
                
                Method getOptionsMethod = serviceClient.getClass().getMethod("getOptions");
                Object options = getOptionsMethod.invoke(serviceClient);
                
                Method setTimeOutMethod = options.getClass().getMethod("setTimeOutInMilliSeconds", long.class);
                setTimeOutMethod.invoke(options, 0L);
            } catch (Exception e) {
                myLogger.warn("Could not apply Axis2 connection timeout properties via reflection: " + e.getMessage());
            }
        }
    }


    private boolean isTimedOut(ContentManagerService_PortType serviceInstance) {
        try {
            serviceInstance.query(new SearchPathMultipleObject("~"), new PropEnum[] {}, new Sort[] {}, new QueryOptions());
            return false;
        } catch (RemoteException e) {
            myLogger.warn("Cognos content management session timed out. Forcing reconnection.");
            return true;
        }
    }

    public RunOption[] executeReport(String aSearchPath, ReportDefinition report) throws RemoteException {
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

        String[] format = report.getOutputFormat().split(TCGMConstants.DT_COLON_DELIMITER);
        String useCase = report.getUseCase();
        
        if (useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF) 
                || useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S)
                || useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A)
                || useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_HQ_B)
                || useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_DIV_B)) {
            burstable.setValue(true);
        }

        if (!"NONE".equalsIgnoreCase(format[0])) {
            if (useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_HQ_I) 
                    || useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_I)) {
                saveOutput.setValue(false);
            }
            outputFormat.setValue(format);
        } else {
            saveOutput.setValue(false);
            outputFormat.setValue(null);
        }
        
        runOptions[0] = saveOutput;
        runOptions[1] = outputFormat;
        runOptions[2] = doPrompt;
        runOptions[3] = burstable;

        return runOptions;
    }
    
    public RunOption[] executeAndPrintReport(String aSearchPath, String aPrinterAddress, ReportDefinition report) throws RemoteException {
        RunOption[] runOptions = new RunOption[5];

        RunOptionBoolean saveOutput = new RunOptionBoolean();
        saveOutput.setName(RunOptionEnum.saveOutput);
        saveOutput.setValue(true);

        RunOptionStringArray outputFormat = new RunOptionStringArray();
        outputFormat.setName(RunOptionEnum.outputFormat);
        String[] format = report.getOutputFormat().split(TCGMConstants.DT_COLON_DELIMITER);

        if (!"NONE".equalsIgnoreCase(format[0])) {
            outputFormat.setValue(format);
        } else {
            outputFormat.setValue(null);
        }
          
        RunOptionBoolean doPrompt = new RunOptionBoolean();
        doPrompt.setName(RunOptionEnum.prompt);
        doPrompt.setValue(false);

        RunOptionBoolean doPrint = new RunOptionBoolean();
        doPrint.setName(RunOptionEnum.print);
        doPrint.setValue(true);

        RunOptionString printerAddress = new RunOptionString();
        printerAddress.setName(RunOptionEnum.printerAddress);
        printerAddress.setValue(aPrinterAddress);

        runOptions[0] = saveOutput;
        runOptions[1] = outputFormat;
        runOptions[2] = doPrompt;
        runOptions[3] = doPrint;
        runOptions[4] = printerAddress;

        return runOptions;
    }   
    
    public void burstReport(String aSearchPath, Map<String, String[]> aParameterMap) throws RemoteException {
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

        reportService.run(
            new SearchPathSingleObject(aSearchPath),
            createParameterValueArray(aParameterMap),
            runOptions
        );
    }
    public BaseClass addFolder(String aSearchPath, String aName) throws RemoteException {
        Folder folder = new Folder();

        MultilingualToken[] folderNames = new MultilingualToken[1];
        folderNames[0] = new MultilingualToken();
        folderNames[0].setLocale("en");
        folderNames[0].setValue(aName);

        folder.setName(new MultilingualTokenProp());
        folder.getName().setValue(folderNames);

        AddOptions addOpts = new AddOptions();
        addOpts.setUpdateAction(UpdateActionEnum.update);

        return cmService.add(new SearchPathSingleObject(aSearchPath), new BaseClass[] { folder }, addOpts)[0];
    }


    public BaseClass createReportView(String aSource, String aDestination, String aName, Map<String, String[]> aParameterMap, ReportDefinition report) throws RemoteException {
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

        int versionsCount = Integer.parseInt(report.getReportVersions());
        if (versionsCount > 1) {
            RetentionRuleArrayProp retentionRules = new RetentionRuleArrayProp();
            RetentionRule[] outputRule = new RetentionRule[1];
            outputRule[0] = new RetentionRule();
            outputRule[0].setObjectClass(ClassEnum.fromString("reportVersion"));
            outputRule[0].setProp(PropEnum.creationTime);
            outputRule[0].setMaxObjects(BigInteger.valueOf(versionsCount));
            retentionRules.setValue(outputRule);
            reportView.setRetentions(retentionRules);
        }
                
        String useCase = report.getUseCase();
        BooleanProp executionPromptProp = new BooleanProp();
        if (useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_HQ_I) || useCase.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_I)) {
            executionPromptProp.setValue(true);
        } else {
            executionPromptProp.setValue(false);
        }
        reportView.setExecutionPrompt(executionPromptProp);
        return cmService.add(new SearchPathSingleObject(aDestination), new BaseClass[] { reportView }, addOpts)[0];
    }

    public BaseClass[] queryForReports(String aSearchPath) throws RemoteException {
        PropEnum[] properties = {
            PropEnum.searchPath,
            PropEnum.defaultName,
            PropEnum.base
        };

        QueryOptions queryOptions = new QueryOptions();
        RefProp[] refProps = new RefProp[1];
        refProps[0] = new RefProp();
        refProps[0].setProperties(properties);
        refProps[0].setRefPropName(PropEnum.parameters);
        queryOptions.setRefProps(refProps);

        return cmService.query(new SearchPathMultipleObject(aSearchPath), properties, new Sort[0], queryOptions);
    }

    
    public BaseClass[] queryForObjects(String aSearchPath) throws RemoteException {
        PropEnum[] properties = {
            PropEnum.searchPath,
            PropEnum.defaultName
        };

        return cmService.query(new SearchPathMultipleObject(aSearchPath), properties, new Sort[0], new QueryOptions());
    }

    private ParameterValue[] createParameterValueArray(Map<String, String[]> aParameterMap) {
        List<ParameterValue> parameterList = new ArrayList<>();
        
        for (Map.Entry<String, String[]> entry : aParameterMap.entrySet()) {
            ParameterValue parameterValue = new ParameterValue();
            parameterValue.setName(entry.getKey());

            List<SimpleParmValueItem> parmValueItemList = new ArrayList<>();
            String[] parameterValues = entry.getValue();
            
            if (parameterValues != null) {
                for (String val : parameterValues) {
                    SimpleParmValueItem parmValueItem = new SimpleParmValueItem();
                    parmValueItem.setUse(val);
                    parmValueItem.setDisplay(val);
                    parmValueItemList.add(parmValueItem);
                }
            }
            parameterValue.setValue(parmValueItemList.toArray(new ParmValueItem[0]));
            parameterList.add(parameterValue);
        }

        return parameterList.toArray(new ParameterValue[0]);
    }

    private String buildCredentialXML(String aNamespaceId, String aUserName, String aPassword) {
        return "<credential>" +
               "<namespace>" + aNamespaceId + "</namespace>" +
               "<username>" + aUserName + "</username>" +
               "<password>" + aPassword + "</password>" +
               "</credential>";
    }

    public void addNewJob(String packageName, String jobName, String[] reportsArray, RunOptionArrayProp[] runOptionArrayProp) throws TCGMException {
        String parameterList = "packageName " + packageName + "  jobName  " + jobName + " reportsArray " + Arrays.toString(reportsArray) + " runOptionArrayProp " + Arrays.toString(runOptionArrayProp);
        try {
            ReportNetNewJob myJob = new ReportNetNewJob();
            
            // Note: These methods inside ReportNetNewJob will need to be refactored to accept ContentManagerService_PortType
            JobDefinition newJob = (JobDefinition) myJob.addNewJob(cmService, jobName, packageName);
            myJob.addReports(cmService, newJob, reportsArray, runOptionArrayProp);
            myJob.setSequence(cmService, newJob, AppConst.getReportNetExecutionSequence());
            
            reportService.run(
                new SearchPathSingleObject(newJob.getSearchPath().getValue()), 
                new ParameterValue[0], 
                new RunOption[0]
            );
        } catch (Exception e) {
            myLogger.error("An Exception Occured While Executing the batch job template. Message: ", e);
            throw new TCGMException("ReportNetFacade", "addNewJob", parameterList, e.toString());
        }
    }


    
    public void addToRole(String userSearchPath, String groupSearchPath) throws java.rmi.RemoteException {
        PropEnum[] properties = { PropEnum.defaultName, PropEnum.searchPath };
        SearchPathMultipleObject userPathObj = new SearchPathMultipleObject(userSearchPath);
        BaseClass[] member = cmService.query(userPathObj, properties, new Sort[0], new QueryOptions());
        PropEnum[] props = { PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
        SearchPathMultipleObject groupPathObj = new SearchPathMultipleObject(groupSearchPath);
        Group group = (Group) cmService.query(groupPathObj, props, new Sort[0], new QueryOptions())[0];

        if (group.getMembers().getValue() == null) {
            group.setMembers(new BaseClassArrayProp());
            group.getMembers().setValue(member);
            cmService.update(new BaseClass[] { group }, new UpdateOptions());
        } else {
            BaseClass[] existingMembers = group.getMembers().getValue();
            BaseClass[] newMembers = Arrays.copyOf(existingMembers, existingMembers.length + 1);
            newMembers[existingMembers.length] = member[0];

            group.setMembers(new BaseClassArrayProp());
            group.getMembers().setValue(newMembers);
            cmService.update(new BaseClass[] { group }, new UpdateOptions());
        }
    }

    public void addGroups(String userSearchPath, String groupSearchPath) throws java.rmi.RemoteException {
        String baseGroupSearchPath = "CAMID(\":nTCGM:\")";
        String baseUserSearchPath = "CAMID(\":nTCGM:11AI\")";

        PropEnum[] props = { PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
        
        // FIXED: Instantiated with the search path string directly inside the constructor
        SearchPathMultipleObject spMulti = new SearchPathMultipleObject(baseUserSearchPath);

        // FIXED: Removed .getValue() and passed the object directly into the query method
        NamespaceFolder group = (NamespaceFolder) cmService.query(spMulti, props, new Sort[0], new QueryOptions())[0];
        
        if (group.getName() == null) {
            MultilingualToken[] names = new MultilingualToken[1];
            names[0] = new MultilingualToken();
            names[0].setLocale("en");
            names[0].setValue(baseUserSearchPath);
            
            MultilingualTokenProp mulTP = new MultilingualTokenProp();
            mulTP.setValue(names);
            group.setName(mulTP);
            
            AddOptions addOpts = new AddOptions();
            addOpts.setUpdateAction(UpdateActionEnum.update);
            
            // FIXED: Wrapped baseGroupSearchPath string into a SearchPathSingleObject wrapper
            cmService.add(new SearchPathSingleObject(baseGroupSearchPath), new BaseClass[] { group }, addOpts);
        }
    }


    public boolean checkUserExists(String userPath, String groupPath) {
        return checkEntityExists(userPath, groupPath);
    }

    public boolean checkGroupExists(String groupPath, String groupPathExpanded) {
        return checkEntityExists(groupPath, groupPathExpanded);
    }

    private boolean checkEntityExists(String targetPath, String containerGroupPath) {
        BaseClass[] memberIDs = getMembers(containerGroupPath);
        if (memberIDs == null) return false;

        return Arrays.stream(memberIDs)
                .filter(member -> {
                    String name = member.getDefaultName().getValue();
                    return !"All Authenticated Users".equals(name) && !"Everyone".equals(name);
                })
                .anyMatch(member -> member.getSearchPath().getValue().equalsIgnoreCase(targetPath));
    }
    
    public Account[] getMembers(String targetSearchPath) {
        PropEnum[] props = { PropEnum.searchPath, PropEnum.defaultName, PropEnum.portalPage };
        
        try {
            // FIXED: Pass path directly into the constructor and omit .getValue()
            SearchPathMultipleObject spMulti = new SearchPathMultipleObject(targetSearchPath);
            BaseClass[] targets = cmService.query(spMulti, props, new Sort[0], new QueryOptions());
            
            return Arrays.stream(targets)
                         .map(Account.class::cast)
                         .toArray(Account[]::new);
        } catch (Exception e) {
            myLogger.error("Failed to query membership array for path: " + targetSearchPath, e);
            return new Account[0];
        }
    }   

    public void removeFromRole(String userSearchPath, String roleSearchPath) throws java.rmi.RemoteException {
        PropEnum[] properties = { PropEnum.defaultName, PropEnum.searchPath };
        
        // FIXED: Wrap user path directly in the constructor
        SearchPathMultipleObject userPathObj = new SearchPathMultipleObject(userSearchPath);
        BaseClass[] member = cmService.query(userPathObj, properties, new Sort[0], new QueryOptions());
                
        PropEnum[] props = { PropEnum.defaultName, PropEnum.searchPath, PropEnum.members };
        
        // FIXED: Wrap role path directly in the constructor
        SearchPathMultipleObject rolePathObj = new SearchPathMultipleObject(roleSearchPath);
        Group group = (Group) cmService.query(rolePathObj, props, new Sort[0], new QueryOptions())[0];

        if (group.getMembers().getValue() != null) {
            BaseClass[] currentMembers = group.getMembers().getValue();
            List<BaseClass> retainedMembers = new ArrayList<>();

            for (BaseClass obj : currentMembers) {
                // FIXED: Instantiated a fresh SearchPathMultipleObject instead of trying to call .setValue()
                SearchPathMultipleObject memberItemPath = new SearchPathMultipleObject(obj.getSearchPath().getValue());
                BaseClass[] memberProps = cmService.query(memberItemPath, props, new Sort[0], new QueryOptions());
                
                if (memberProps.length > 0) {
                    String csMemberPath = memberProps[0].getSearchPath().getValue();
                    if (!csMemberPath.equalsIgnoreCase(member[0].getSearchPath().getValue())) {
                        retainedMembers.add(obj);
                    }
                }
            }

            group.setMembers(new BaseClassArrayProp());
            group.getMembers().setValue(retainedMembers.toArray(new BaseClass[0]));
            cmService.update(new BaseClass[] { group }, new UpdateOptions());
         }
    }

    public void addGroup(String groupSearchPath, String groupPath) throws java.rmi.RemoteException {
        Group folder = new Group();
        MultilingualToken[] folderNames = new MultilingualToken[1];
        folderNames[0] = new MultilingualToken();
        folderNames[0].setLocale("en");
        folderNames[0].setValue(groupPath);

        folder.setName(new MultilingualTokenProp());
        folder.getName().setValue(folderNames);

        AddOptions addOpts = new AddOptions();
        addOpts.setUpdateAction(UpdateActionEnum.update);

        // FIXED: Wrapped groupSearchPath string inside SearchPathSingleObject container
        cmService.add(new SearchPathSingleObject(groupSearchPath), new BaseClass[] { folder }, addOpts);
    }

    public void addToReportGroup(String userSearchPath, String reportSearchPath) throws Exception {
        PropEnum[] properties = { PropEnum.defaultName, PropEnum.searchPath, PropEnum.policies, PropEnum.members };
        
        // FIXED: Removed .getValue()
        Folder reportObject = (Folder) cmService.query(new SearchPathMultipleObject(reportSearchPath), properties, new Sort[0], new QueryOptions())[0];
            
        Permission writePermission = new Permission();
        writePermission.setName("traverse");         
        writePermission.setAccess(AccessEnum.grant);
            
        Permission readPermission = new Permission();
        readPermission.setName("read");          
        readPermission.setAccess(AccessEnum.grant);
            
        boolean found = false;
        if (reportObject.getPolicies().getValue() != null) {
            for (Policy policy : reportObject.getPolicies().getValue()) {
                if (policy.getSecurityObject().getSearchPath().getValue().equalsIgnoreCase(userSearchPath)) {
                    found = true;
                    Permission[] newPerms = { writePermission, readPermission };
                    policy.setPermissions(newPerms);
                    break;
                }
            }
        }

        if (!found) {
            // FIXED: Removed .getValue()
            BaseClass entry = cmService.query(new SearchPathMultipleObject(userSearchPath), new PropEnum[0], new Sort[0], new QueryOptions())[0];

            Policy newPolicy = new Policy();
            newPolicy.setSecurityObject(entry);
            newPolicy.setPermissions(new Permission[] { writePermission, readPermission });

            Policy[] existingPols = reportObject.getPolicies().getValue() != null ? reportObject.getPolicies().getValue() : new Policy[0];
            Policy[] newPols = Arrays.copyOf(existingPols, existingPols.length + 1);
            newPols[existingPols.length] = newPolicy;
            
            reportObject.setPolicies(new PolicyArrayProp());
            reportObject.getPolicies().setValue(newPols);
        }
            
        cmService.update(new BaseClass[]{ reportObject }, new UpdateOptions());
    }
}
