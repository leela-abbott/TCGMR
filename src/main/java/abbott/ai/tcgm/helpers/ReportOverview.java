package abbott.ai.tcgm.helpers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.exception.TCGMException;

import com.cognos.developer.schemas.bibus._3.Account;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;

public class ReportOverview {
    
    private ContentManagerService_PortType cmService = null;
    private static final Logger myLogger = LogManager.getLogger(ReportOverview.class);

    public ReportOverview() {
    }

    public void rptOverview(String sendPoint) {
        ContentManagerService_ServiceLocator cmServiceLocator = new ContentManagerService_ServiceLocator();
        try {
            cmService = cmServiceLocator.getcontentManagerService(new URL(sendPoint));
            configureTimeout(cmService);
        } catch (Exception e) {
            myLogger.error("Failed to initialize ContentManagerService gateway for Endpoint: " + sendPoint, e);
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
    public Account[] getMembers(String targetSearchPath) {
        PropEnum[] props = new PropEnum[] {
            PropEnum.searchPath, 
            PropEnum.defaultName,
            PropEnum.creationTime,
            PropEnum.modificationTime,
            PropEnum.portalPage
        };
        Sort[] sOpt = new Sort[]{};
        QueryOptions qOpt = new QueryOptions();
        
        // FIXED: Passed search path string parameter directly into constructor instantiation
        SearchPathMultipleObject spMulti = new SearchPathMultipleObject(targetSearchPath);
        Account[] targetAccount = null;

        try {
            // FIXED: Removed target reference text methods, passing SearchPathMultipleObject directly 
            BaseClass[] targets = cmService.query(spMulti, props, sOpt, qOpt);
            if (targets != null) {
                targetAccount = Arrays.stream(targets)
                                      .map(Account.class::cast)
                                      .toArray(Account[]::new);
            }
        } catch (Exception e) {
            myLogger.error("Error executing dynamic directory group query for path: " + targetSearchPath, e);
        }
        return targetAccount != null ? targetAccount : new Account[0];
    }

    public String quickLogon(String namespace, String uid, String pwd) {
        String credentialXML = "<credential>" +
                               "<namespace>" + namespace + "</namespace>" +
                               "<username>" + uid + "</username>" +
                               "<password>" + pwd + "</password>" +
                               "</credential>";

        XmlEncodedXML xmlCredentials = new XmlEncodedXML();
        xmlCredentials.set_value(credentialXML);

        try {
            // FIXED: satisfaction of explicit SearchPathSingleObject[] constraint profile requirements 
            cmService.logon(xmlCredentials, new SearchPathSingleObject[0]);
        } catch (Exception e) {
            myLogger.error("Logon validation failed inside modern ContentManager pipeline", e);
        }
        return "Logon successful as " + uid;
    }

    public BaseClass[] getRoles(String nameSpaceID) {
        String roles = "CAMID(\"" + nameSpaceID + "\")//role";
        PropEnum[] props = new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
        
        // FIXED: Enforce immediate constructor population parameters 
        SearchPathMultipleObject spMulti = new SearchPathMultipleObject(roles);
        BaseClass[] roleObjects = new BaseClass[]{};

        try {
            roleObjects = cmService.query(spMulti, props, new Sort[]{}, new QueryOptions());
        } catch (Exception e) {
            myLogger.error("Failed looking up security Role objects for Context ID: " + nameSpaceID, e);
        }
        return roleObjects;
    }

    public BaseClass[] getGroups(String nameSpaceID) {
        String groups = "CAMID(\"" + nameSpaceID + "\")//group";
        PropEnum[] props = new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName };
        
        // FIXED: Explicit constructor parameter payload binding allocation
        SearchPathMultipleObject spMulti = new SearchPathMultipleObject(groups);
        BaseClass[] groupObjects = new BaseClass[]{};

        try {
            groupObjects = cmService.query(spMulti, props, new Sort[]{}, new QueryOptions());
        } catch (Exception e) {
            myLogger.error("Failed looking up namespace Group definitions for Context ID: " + nameSpaceID, e);
        }
        return groupObjects;
    }
    public void checkUsers(ArrayList<RptUser> rptList) throws TCGMException {
        final String reportNetEndPoint = AppConst.getInstance().getReportNetServiceLocator();
        final String nameSpaceID = AppConst.getInstance().getReportNetNameSpace();
        final String userName = AppConst.getInstance().getReportNetUsername();
        final String password = AppConst.getInstance().getReportNetPassword();                
        
        // FIXED: Parametrized Java 17 collection types safely
        HashMap<String, String> userMap = new HashMap<>();
        ArrayList<String> rptUserList = new ArrayList<>();                
        String fileName = System.getProperty("catalina.home") + "/webapps/tcgm/include/report.txt";

        this.rptOverview(reportNetEndPoint);
        this.quickLogon(nameSpaceID, userName, password);
        
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            myLogger.error("Target reporting diagnostic output file generation context missing structural permission", e);
        }
        
        PrintStream fout = new PrintStream(fs);
        fout.println("User Name\t\t\tUser ID\t");
        String nsToQuery = ":";
        
        BaseClass[] roleIDs = this.getRoles(nsToQuery);
        BaseClass[] groupIDs = this.getGroups(nsToQuery);
        Account[] targetAccount = null;
        String accountvalue = "";
        
        try {
            for (int x = 0; x < roleIDs.length; x++) {
                targetAccount = this.getMembers("expandMembers(" + roleIDs[x].getSearchPath().getValue() + ")");
                if (targetAccount != null && targetAccount.length != 0) {
                    for (int i = 0; i < targetAccount.length; i++) {
                        String rawSearchPath = targetAccount[i].getSearchPath().getValue();
                        if (rawSearchPath.contains("cn=") && rawSearchPath.contains(",ou")) {
                            accountvalue = rawSearchPath.substring(rawSearchPath.indexOf("cn=") + 3, rawSearchPath.indexOf(",ou")).toLowerCase();                            
                            userMap.put(targetAccount[i].getDefaultName().getValue(), accountvalue);
                        }
                    }
                }
            }
            
            accountvalue = "";
            for (int x = 0; x < groupIDs.length; x++) {
                String allusers = "All Authenticated Users";
                String everyone = "Everyone";
                boolean test1 = groupIDs[x].getDefaultName().getValue().equals(allusers);
                boolean test2 = groupIDs[x].getDefaultName().getValue().equals(everyone);
                if (!test1 && !test2) {
                    targetAccount = this.getMembers("expandMembers(" + groupIDs[x].getSearchPath().getValue() + ")");
                    if (targetAccount != null && targetAccount.length != 0) {
                        for (int i = 0; i < targetAccount.length; i++) {
                            String rawSearchPath = targetAccount[i].getSearchPath().getValue();
                            if (rawSearchPath.contains("cn=") && rawSearchPath.contains(",ou")) {
                                accountvalue = rawSearchPath.substring(rawSearchPath.indexOf("cn=") + 3, rawSearchPath.indexOf(",ou")).toLowerCase();                            
                                userMap.put(targetAccount[i].getDefaultName().getValue(), accountvalue);
                            }
                        }
                    }
                }
            }
            
            for (int i = 0; i < rptList.size(); i++) {
                RptUser rptUser = rptList.get(i);
                String userid = rptUser.getUserid().toLowerCase();
                rptUserList.add(userid);
                if ((!userMap.containsValue(userid))) {
                    fout.println(rptUser.getFullName() + "\t\t\t" + userid);
                }
            }
            
            fout.println("\n\nUser Name\t\t\tUser ID\t");
            Iterator<String> it = userMap.keySet().iterator();
            while (it.hasNext()) {
                String value = it.next();
                String userid = userMap.get(value);
                if ((!rptUserList.contains(userid))) {
                    fout.println(value + "\t\t\t" + userid);
                }
            }
            fout.close();    
            
        } catch (NullPointerException e) {
            myLogger.error("Null structural payload encountered traversing Content Manager output processing iteration hierarchy", e);
        }
    }
}
