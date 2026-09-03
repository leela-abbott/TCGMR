package abbott.ai.tcgm.helpers;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.ProcessDao;
import abbott.ai.tcgm.data.ReportDao;
import abbott.ai.tcgm.data.ReportInstanceDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.ReportDefinition;
import abbott.ai.tcgm.entities.ReportInstance;
import abbott.ai.tcgm.entities.ReportPrintRequest;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.process.JobConstants;
import abbott.ai.tcgm.process.JobInstance;
import abbott.ai.tcgm.entities.TCGMModel;

import com.cognos.developer.schemas.bibus._3.AccessEnum;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.OrderEnum;
import com.cognos.developer.schemas.bibus._3.Permission;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.Report;
import com.cognos.developer.schemas.bibus._3.RunOption;
import com.cognos.developer.schemas.bibus._3.RunOptionArrayProp;


public class ReportMngr implements TCGMMngr
{

	protected String name = this.getClass().getName();
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.helpers.ReportMngr" );
	
	public ReportMngr()	{	}

	public void printReportBundle(JobInstance job) throws TCGMException {
		
		myLogger.debug("Step1: Entered printReportBundle. Processing job -->" + job.getDesc());
		myLogger.debug("Job Que Id -->" + job.getJobQueId());
		String dest = job.getJobParms().getProperty(JobConstants.PN_REPORT_DEST);
		myLogger.debug("Step2: dest -->" + dest);
		// 11-21-05 Copies not used anymore w/ ReportNet integration
		String copies = job.getJobParms().getProperty(JobConstants.PN_REPORT_COPIES);
		myLogger.debug("Step2: copies -->" + copies);
		printReportBundle(job.getJobQueId(), dest, copies);
	}

	public void printReportBundle(String jobQId, String dest, String copies) throws TCGMException {
		String parameterList = "jobQId: " + jobQId + ", dest: " + dest+ ", copies: " + copies;
		myLogger.debug("Step3: Entered printReportBundle 2");		
		UserToken ut = SQLUtil.getOracleAdmin();
		Vector reports = this.getReportInstancesByJobQId(jobQId, ut);
		Iterator iterator = reports.iterator();
		ReportInstance instance = null;
		ReportPrintRequest rpr = new ReportPrintRequest();
		rpr.setReportDest(dest);
		rpr.setNumCopies(copies);
		rpr.setJobQueId(jobQId);
		ReportNetFacade facade = null; 

		String[] reportsArray = new String[reports.size()];
		Vector finalReportsVector = new Vector();
		Vector runOptionsVector = new Vector();
		RunOptionArrayProp[] runOptionArrayProp = null;
		RunOption[] runOptions = null;
		String jobName = "Job With No Job Description";
		final String PRINTER1 = AppConst.getInstance().getReportNetPrinter1();
		final String PACKAGENAME = AppConst.getInstance().getReportNetPackageName();
		int reportSize = 0;
		int finalReportSize = 0;

		try 
		{
			facade = new ReportNetFacade();
			while (iterator.hasNext() ) 
			{
				instance = (ReportInstance) iterator.next();
				
				if (instance.getRowCount() > 0 || instance.getReportDefinition().isGenerateEmptyReport())
				{
					String rptID = instance.getReportDefinition().getId();
					int rptid = Integer.parseInt(rptID);
					rpr.setReportId( rptID );
					rpr.setDatasetId(instance.getDatasetId());
					//System.out.println(instance.getReportDefinition().getId());
					//String strReportSuffix = getJobQueParam(jobQId, "RPT_NAME_SUF", ut);
					if(rptid==100 ||rptid == 101 ||rptid == 102 ||rptid==103||rptid==228 || rptid==256 || rptid==257 || rptid==258 || rptid==259 || rptid==260){
						rpr.setReportContentId(instance.getReportContentId().trim()+" - "+getJobQueParam(jobQId, "DFRD_RPT_NAME_SUF", ut));
					}
					else if(rptid==155 || rptid == 156 || rptid == 192 || rptid==193 || rptid==272 || rptid==273 || rptid==274 || rptid==275){
						rpr.setReportContentId(instance.getReportContentId().trim()+" - "+getJobQueParam(jobQId, "HDGE_RPT_NAME_SUF", ut));
					}
					else{
						rpr.setReportContentId(instance.getReportContentId());
					}
					
						/*****************************************************************
						 * This block of code will create the reportView -- Start
						 ******************************************************************/
						BaseClass[] reportViews = this.printReportNetReport(rpr, facade);
						if (!(reportViews == null))
						{
							for (int i=0; i< reportViews.length; i++)
							{
								if (!(reportViews[i]==null))
								{
									finalReportsVector.add(reportViews[i].getSearchPath().getValue());
					
									/*****************************************************************
									 * This block of code will create the reportView -- End
									 ******************************************************************/
			
									boolean printReportFlag = false;
									if(rpr.getReportDest().equals("GP"))
									{
										printReportFlag = true;
									}
									ReportDefinition report = this.getReportById(rpr.getReportId(), ut);

									/*****************************************************************
									 * This block of code will create the runOptions for each Report -- Start
									 ******************************************************************/
									if(printReportFlag)
									{
										runOptions = facade.executeAndPrintReport( reportViews[i].getSearchPath().getValue(), PRINTER1, report );				
									}
									else
									{
										runOptions = facade.executeReport( reportViews[i].getSearchPath().getValue(), report );	
									}
									runOptionsVector.add(runOptions);
									finalReportSize++;
									/*****************************************************************
									 * This block of code will create the runOptions for each Report -- End
									 ******************************************************************/
								}

							}
						}
						reportSize++;					
					}
				} 
			if (finalReportSize > 0)
			{	
				runOptionArrayProp = new RunOptionArrayProp[finalReportSize];	
				for(int i=0 ; i <finalReportSize; i++)	
				{
					runOptionArrayProp[i] = new RunOptionArrayProp();
					runOptionArrayProp[i].setValue((RunOption[])runOptionsVector.get(i));
				}
				String[] finalReportsArray = new String[finalReportsVector.size()];
				finalReportsVector.toArray(finalReportsArray);	
				ProcessDao processDao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
				jobName = processDao.getJobDesc(jobQId);
				facade.addNewJob(PACKAGENAME, jobName, finalReportsArray, runOptionArrayProp);
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
			
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
			myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		} 
		catch (TCGMException e) 
		{
			e.printStackTrace();
			myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		} 
		catch (ServiceException e) 
		{
			e.printStackTrace();
			myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		}		

	}


	public String[] getRestrictCols(String reportId) throws TCGMException {
		String[] cols = { "RPT_AFF", "SUP_AFF" };
		return cols;
	}

	public Vector getReportInstancesByJobQId(String jobQId, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getReportInstancesByJobQId(jobQId);
	}

	public String getJobQueParam(String jobQId, String jobParmName, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getJobQueParmName(jobQId, jobParmName);
	}

	public Vector getReportInstances(UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getReportInstances();
	}
	public ReportDefinition getReportById(String reportId, UserToken ut) throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getReportById(reportId);
	}

	public Vector getAffiliatesBySectorId(String sectorId, UserToken ut) throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getAffiliatesBySectorId(sectorId);
	}

	public Vector getAllRGMAffReports(UserToken ut) throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getAllRGMAffReports();
	}
	public Vector getAllAffIdsForThisRun(UserToken ut,String columnName, String viewName, String datasetId) throws TCGMException
	{
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getAllAffIdsForThisRun(columnName, viewName, datasetId );
	} 
	public ReportInstance getReportInstanceById(String reportId, String jobQId, UserToken ut) throws TCGMException {

		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao( ut );
		return rid.getReportInstanceById( reportId, jobQId );
	}

	public void runPrintReport(String modelId, ReportPrintRequest rpr) throws TCGMException {
		String methodName = "runPrintReport(ReportPrintRequest rpr)";
		ReportNetFacade facade = null; 
		String parameterList = "rpr: " + rpr.toString();
		UserToken ut = SQLUtil.getOracleAdmin();

		int datasetTableId = this.runReport(rpr.getReportId(), modelId );
		// 11-1-05 Replace with ReportNet specific call
		//this.printReport(rpr);

//		this.printReportNetReport(rpr, facade, folders);
	}

	public void deleteReportInstanceById(String reportId, String jobQId, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		ReportInstance report = rid.getReportInstanceById(reportId, jobQId);
		new DatasetMngr().deleteDatasetById(ut, Integer.parseInt( report.getDatasetId() ));
		rid.deleteReportInstanceById(reportId, jobQId);
	}

	public void deleteReportInstancesByJobQueId(String jobQId, UserToken ut) throws TCGMException {
		// 1st delete any children datasets for the reports.
		DatasetMngr dm = new DatasetMngr();
		ReportInstance report = null;
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		Vector reports = this.getReportInstancesByJobQId(jobQId, ut);
		Iterator i = reports.iterator();
		while (i.hasNext() ) {
			report = (ReportInstance) i.next();
			rid.deleteReportInstanceById(report.getReportDefinition().getId(), jobQId);
			dm.deleteDatasetById(ut, Integer.parseInt( report.getDatasetId() ) );
		}
	}

	// return datasetTableId of the report that was run.
	public int runReport(String reportId, String modelId) throws TCGMException {

		UserToken ut = SQLUtil.getOracleAdmin();
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);

		return rd.runReportDirect(reportId, modelId);
	}
	
	/** 
	 * 
	 * @param rpr
	 * @throws TCGMException
	 * 11-21-05 Sending of report data to the iSeries (AS400) is now obsolete.
	 */
	public void printReport(ReportPrintRequest rpr) throws TCGMException {
		String methodName = "printReport(ReportPrintRequest rpr)";
		String parameterList = "rpr: " + rpr.toString();
		myLogger.debug("Printing report: " + rpr.getReportId() + ", Dest: " + rpr.getReportDest() + ", Copies: " + rpr.getNumCopies() );

		// For printing purposes use oracle admin account
		UserToken ut = SQLUtil.getOracleAdmin();

		// Move these constants into descriptor parameters
		UserToken as400ut = new UserToken("TCGMFTP", "BRIDGE9QZ");
		String lib = "AITCGDVFIL";

		ReportDefinition report = this.getReportById(rpr.getReportId(), ut);
		ReportInstance reportInstance = this.getReportInstanceById( rpr.getReportId(), rpr.getJobQueId(), ut);

//		File extract = ExtractComposer.getReportExtractFile( report, reportInstance );

//		String member = "M" + TCGMUtil.getRandomDigitStr(6);
//		String destFilename = extract.getName().substring(0,10);

		AS400 as400 = new AS400( as400ut );
//		as400.sendTextFile(extract, lib + "/" + destFilename +  "." + member);
//		myLogger.debug("Sending report <after sendTextFile>: lib = " + lib + ", destFilename = " + destFilename + ", Mbr = " + member);

		// 11-9-05 insertReportTriggerRecord() was used in the old (pre-ReportNet)  
		//         infrastructure to insert the report parms into the AS400 parameter file. 
//		as400.insertReportTriggerRecord(rpr, reportInstance, destFilename, member );
//		myLogger.debug("After send of report trigger record: destFilename = " + destFilename + ", Mbr = " + member);

	}
	
	// 11-1-05 New call to ReportNet Integration
	public BaseClass[] printReportNetReport(ReportPrintRequest rpr, ReportNetFacade facade) throws TCGMException 
	{
		String methodName = "printReportNetReport(ReportPrintRequest rpr)";
		String parameterList = "rpr: " + rpr.toString();
		BaseClass[] reportViews = null;
		boolean printReportFlag = false;

		UserToken ut = SQLUtil.getOracleAdmin();
		ReportDefinition report = this.getReportById(rpr.getReportId(), ut);
		ReportInstance reportInstance = this.getReportInstanceById( rpr.getReportId(), rpr.getJobQueId(), ut);

		String reportId = rpr.getReportId();
		String modelId = reportInstance.getJobInstance().getModelId();
		String datasetId = rpr.getDatasetId();
		String jobQueId = reportInstance.getJobInstance().getJobQueId();
		String reportContentId = rpr.getReportContentId();
		

		if(rpr.getReportDest().equals("GP"))
		{
			printReportFlag = true;
		}
		
		if( (report.getUseCase() != null)  && (!report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_I)))
		{
			if( (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF)) 
			 || (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S))||
			(report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A))) // publish
			{
				reportViews = this.burstReport( report.getSearchPath(), datasetId, modelId, jobQueId, report, facade, reportContentId );
			}
			else if(report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_DIV_B))
			{
				reportViews = this.createDivReportViews( report.getSearchPath(), datasetId, modelId, jobQueId, printReportFlag, report, reportContentId );
				
			}
			else
			{
				reportViews = this.createReportViews( report.getSearchPath(), datasetId, modelId, jobQueId, printReportFlag, report, reportContentId );
			}
		}
		
		myLogger.debug("Report executed in printReportNetReport(rpr) with parm values:");
		myLogger.debug("report id = " + reportId + " model id = " + modelId + " dataset id = " + datasetId);
		return reportViews;
	}
	
	private BaseClass[] createReportViews(String reportPath, String datasetId, String modelId, String jobQueId, boolean printReport,
								  ReportDefinition report, String reportContentId) throws TCGMException
	{
		String methodName = "createReportViews(reportPath, datasetId, modelId, jobQueId,printReport, report)";
		String parameterList = "modelId: " + modelId + ", reportPath: " + reportPath+ ", datasetId: " + datasetId+ ", jobQueId: " + jobQueId+ ", report: " + report.toString();
		BaseClass reportView = null;
		BaseClass[] reportViews = null;
		if(reportPath != null && reportPath.startsWith("/"))
		{
			try
			{
				ReportNetFacade facade = new ReportNetFacade();
				Map parameterMap = new HashMap();
				parameterMap.put( AppConst.getReportNetParmsDatasetTableID(), new String[] { datasetId } ); 
				parameterMap.put( AppConst.getReportNetParmsModelID(), new String[] { modelId } );
				parameterMap.put( AppConst.getReportNetParmsJobQueueID(), new String[] { jobQueId } );
				
				UserToken ut = SQLUtil.getOracleAdmin();
				ModelMngr mm = new ModelMngr();
				String modelName = mm.getModelName(ut, Integer.parseInt(modelId) );
				if (reportContentId.equalsIgnoreCase("Default"))
				{
					reportContentId = " - Model- " + modelName;
				}
				
				if(report.getName().equalsIgnoreCase("EXCPT_TCGM02_RPT1")||report.getName().equalsIgnoreCase("AFF EXCPT_TCGM02_RPT1")){
					
					reportContentId = reportContentId +" - Unit-"+mm.getModelParmValue(ut, Integer.parseInt(modelId),TCGMModel.Type.FACTOR,"CURRNT_UNITS_NAME");
				}
				//for deferred or earned				
				int rptid = Integer.parseInt(report.getId());
				
				if(rptid==100 ||rptid == 101 ||rptid == 102 ||rptid==103||rptid==228)
				{
					String dispName=report.getDisplayName();
					String value=mm.getModelParmValue(ut, Integer.parseInt(modelId),TCGMModel.Type.ANALYSIS,"DFRD_UNITS_NAME");
					String frthChar=value.substring(3,4);
					if(frthChar.equals("A") || frthChar.equals("P") || frthChar.equals("U")){
					report.setDisplayName("Earned "+dispName);
					}else{
					report.setDisplayName("Deferred "+dispName);
					}
				}				
				
				
				// start by retrieving the base report
				BaseClass baseReport = facade.queryForObjects( reportPath )[0]; // [0] return 1st value only
	
				BaseClass newFolder = facade.addFolder( AppConst.getReportNetHQPath(), report.getDisplayName() + " (" + report.getName() + ") " );
				reportViews = new BaseClass[1];
				
				if(rptid==137 ||rptid == 138)
				{
					String dispName=report.getDisplayName();
					String value=mm.getModelParmValue(ut, Integer.parseInt(modelId),TCGMModel.Type.ANALYSIS,"SLSID_TITLE");
					report.setDisplayName(dispName+" "+value);
				}
			
				reportView = facade.createReportView( baseReport.getSearchPath().getValue(),
						newFolder.getSearchPath().getValue(),
						report.getDisplayName() + " (" + report.getName() + ") " + reportContentId,
						parameterMap, report );

				reportViews[0] = reportView;		
				myLogger.debug("Step 5: Report View Created");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				myLogger.error("An Exception Occured While Executing the createReportViews. Message:" + ex.toString());
				throw new TCGMException("ReportMngr", "createReportViews", parameterList, ex.getMessage());
			}
		}
		return reportViews;
	}
	private BaseClass[] createDivReportViews(String reportPath, String datasetId, String modelId, String jobQueId, boolean printReport,
			  ReportDefinition report, String reportContentId) throws TCGMException
	{
		String methodName = "createDivReportViews(reportPath, datasetId, modelId, jobQueId,printReport, report)";
		String parameterList = "modelId: " + modelId + ", reportPath: " + reportPath+ ", datasetId: " + datasetId+ ", jobQueId: " + jobQueId+ ", report: " + report.toString();
		BaseClass reportView = null;
		BaseClass[] reportViews = null;
		if(reportPath != null && reportPath.startsWith("/"))
		{
			try
			{
				ReportNetFacade facade = new ReportNetFacade();
				Map parameterMap = new HashMap();
				parameterMap.put( AppConst.getReportNetParmsDatasetTableID(), new String[] { datasetId } ); 
				parameterMap.put( AppConst.getReportNetParmsModelID(), new String[] { modelId } );
				parameterMap.put( AppConst.getReportNetParmsJobQueueID(), new String[] { jobQueId } );
				
				UserToken ut = SQLUtil.getOracleAdmin();
				ModelMngr mm = new ModelMngr();
				String modelName = mm.getModelName(ut, Integer.parseInt(modelId) );
				if (reportContentId.equalsIgnoreCase("Default"))
				{
					reportContentId = " - Model- " + modelName;
				}
				
				int rptid = Integer.parseInt(report.getId());		
				if(rptid==256 ||rptid == 257 ||rptid == 258 ||rptid==259||rptid==260)
				{
					String dispName=report.getDisplayName();
					String value=mm.getModelParmValue(ut, Integer.parseInt(modelId),TCGMModel.Type.ANALYSIS,"DFRD_UNITS_NAME");
					String frthChar=value.substring(3,4);
					if(frthChar.equals("A") || frthChar.equals("P") || frthChar.equals("U")){
					report.setDisplayName("Earned "+dispName);
					}else{
					report.setDisplayName("Deferred "+dispName);
					}
				}
				
				// start by retrieving the base report
				BaseClass baseReport = facade.queryForObjects( reportPath )[0]; // [0] return 1st value only
				
				BaseClass newFolder = facade.addFolder( AppConst.getReportNetDIVPath(), report.getDisplayName() + " (" + report.getName() + ") " );
				reportViews = new BaseClass[1];
				
				
				
				reportView = facade.createReportView( baseReport.getSearchPath().getValue(),
					newFolder.getSearchPath().getValue(),
					report.getDisplayName() + " (" + report.getName() + ") " + reportContentId,
					parameterMap, report );
				
				reportViews[0] = reportView;		
				
			}
			catch(Exception ex)
			{
			ex.printStackTrace();
			myLogger.error("An Exception Occured While Executing the createReportViews. Message:" + ex.toString());
			throw new TCGMException("ReportMngr", "createReportViews", parameterList, ex.getMessage());
			}
		}
		return reportViews;
	}	
	

	private BaseClass[] burstReport(String reportPath, String datasetId, String modelId, String jobQueId, ReportDefinition report,
									ReportNetFacade facade, String reportContentId) throws TCGMException {

		/*****************************************************************
		 * This block of code will initialize the Parameters -- Start
		 ******************************************************************/

		BaseClass reportView = null;
		BaseClass[] reportViews = null;
		String path = null;
		String methodName = "burstReport(reportPath, datasetId, modelId, jobQueId, report, facade, folders)";
		String parameterList = "modelId: " + modelId + ", reportPath: " + reportPath+ ", datasetId: " + datasetId+ ", jobQueId: " + jobQueId+ ", report: " + report.toString();
		boolean publishReport = false; 
		UserToken ut = SQLUtil.getOracleAdmin();
		ModelMngr mm = new ModelMngr();
		String modelName = mm.getModelName(ut, Integer.parseInt(modelId) );
		if (reportContentId.equalsIgnoreCase("Default"))
		{
			reportContentId = " - Model- " + modelName;
		}
		if(report.getName().equalsIgnoreCase("EXCPT_TCGM02_RPT1")||report.getName().equalsIgnoreCase("AFF EXCPT_TCGM02_RPT1")){
							
			reportContentId = reportContentId +" - Unit-"+mm.getModelParmValue(ut, Integer.parseInt(modelId),TCGMModel.Type.FACTOR,"CURRNT_UNITS_NAME");
		}
		/*****************************************************************
		 * This block of code will initialize the Parameters -- Start
		 ******************************************************************/
		
		// 11-2-05 Hack approach! We know ReportNet searchpaths will start with "/"
		if(reportPath != null && reportPath.startsWith("/"))
		{
			try
			{
				BaseClass baseReport = facade.queryForObjects( reportPath )[0];
				reportViews = new BaseClass[1];

				Map parameterMap = new HashMap();
		  
				parameterMap.put( AppConst.getReportNetParmsDatasetTableID(), new String[] { datasetId } );
				parameterMap.put( AppConst.getReportNetParmsModelID(), new String[] { modelId } );
			
				if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF) || 
					report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S)|| 
				report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A))
				{
				  parameterMap.put( AppConst.getReportNetParmsJobQueueID(), new String[] { jobQueId } );
				  if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF))
				  {
					path = AppConst.getReportNetAffiliateReportsPath();
				  }
				  else if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S))
				  {
					path = AppConst.getReportNetSectorPath();
				  }else if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A))
				  {
					path = AppConst.getReportNetSectorPath();
					path = path.replaceAll("Sector","Area");
				  }
				  
				  BaseClass[] reportFolders = facade.queryForObjects(path);
				  
				  if (reportFolders.length==0 && report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF))
				  {
					  BaseClass newFolder = facade.addFolder(AppConst.getReportNetAffiliatePath(), AppConst.getReportNetFolderReports() );
				  }
				}

				reportView = facade.createReportView( baseReport.getSearchPath().getValue() ,
													  path ,
													  report.getDisplayName() + " (" + report.getName() + ") " + reportContentId, 					  
													  parameterMap,report );	
			   reportViews[0]= reportView;									    
			   	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				myLogger.error("An Exception Occured While Executing the burstReport. Message:" + ex.toString());
				throw new TCGMException("ReportMngr", "burstReport", parameterList, ex.getMessage());
			}
		}
		myLogger.debug("burstReport(reportPath, modelId) Parm values:");
		myLogger.debug("report path = " + reportPath + " model id = " + modelId + " dataset id = " + datasetId);
		return reportViews;	
	}
	
	// Adding new user to group
	public void userMaintenance(ArrayList userList, String action) throws TCGMException
		{

			ReportNetFacade facade = null; 

			final String NAMESPACE               = AppConst.getReportNetNameSpace();
			String GROUPSEARCHPATHEXPANDED = "";//"expandMembers(CAMID(\":AI TCGM:level 2:level 3:Level 4:TCGM_AFF021_Consumer\"))";//AppConst.getReportNetGroupSearchPathExpanded();
			String GROUPSEARCHPATH		    = "";//"CAMID(\":AI TCGM:level 2:level 3:Level 4:TCGM_AFF021_Consumer\")";//AppConst.getReportNetGroupSearchPath();	
		
			try 
			{
				facade = new ReportNetFacade();
			
				for (int i=0; i<userList.size(); i++)
				{
					RptUser userBean = (RptUser)userList.get(i);
					String strDivisions[] = null;
					/*if(userBean.getDivision().equalsIgnoreCase("All")){
						strDivisions = new String[]{ "AI", "ANI", "AV", "PNI", "OTH", "EV", "CHX","EPD"};
					}else{
						if(userBean.getDivision().equalsIgnoreCase("AV")){
							strDivisions = new String[]{ "AV", "EV", "CHX"};
						}else{
							strDivisions = new String[]{ userBean.getDivision()};
						}
					}*/
					RptUserMngr rptMngr=new RptUserMngr();
					strDivisions=rptMngr.getDivisionException(userBean.getDivision());
						for(int j=0;j<strDivisions.length;j++){
							
							//userBean.setDivision(strDivisions[i]);	
													
							String userSearchPath = "CAMID(\"" + NAMESPACE + ":u:cn=" + userBean.getUserid() + ",ou=users\")";
																
							GROUPSEARCHPATH = getGroupSearchPath(userBean, (String)strDivisions[j].trim());
							GROUPSEARCHPATHEXPANDED = "expandMembers("+GROUPSEARCHPATH+")"; //getGroupSearchPathExpanded(userBean);
							
							
							boolean check = facade.checkUserExists(userSearchPath, GROUPSEARCHPATHEXPANDED);
							
							if (check && action.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_REMOVE_USERS))
							{
								facade.removeFromRole(userSearchPath, GROUPSEARCHPATH);
							}
							else if ((!check) && action.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_ADD_USERS))
							{
								facade.addToRole(userSearchPath, GROUPSEARCHPATH);
							}
					
						}
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
			
			} 
			catch (RemoteException e) 
			{
				e.printStackTrace();
				myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
			} 
			catch (TCGMException e) 
			{
				e.printStackTrace();
				myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
			} 
			catch (ServiceException e) 
			{
				e.printStackTrace();
				myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
			}		

		}

//	Adding new user to group
	 public void addGroup(RptUser userBean, String strCatId, String strCatName) throws TCGMException
		 {

			 ReportNetFacade facade = null; 

			 final String NAMESPACE               = "LDAP abbott.corp";//AppConst.getReportNetNameSpace();
			 String GROUPSEARCHPATH = "";
			 String GROUPHPATH		    = "";	
		
			 try 
			 {
				 facade = new ReportNetFacade();
			
				 GROUPSEARCHPATH = "CAMID(\":nTCGM:\")";
 				 String strDivisions[] = null;
						/*if(userBean.getDivision().equalsIgnoreCase("All")){
							strDivisions = new String[]{ "AI", "ANI", "AV", "PNI", "OTH", "EV", "CHX","EPD"};
	    				  }
	    				else{
							strDivisions = new String[]{ userBean.getDivision()};
							}*/
							strDivisions = new String[]{ userBean.getDivision()};
							for(int i=0;i<strDivisions.length;i++){
								
								facade.addGroup(GROUPSEARCHPATH, strCatId+""+strDivisions[i]);
								String userSearchPath = "";
								String GROUPSEARCHPATHEXPANDED = "";
								String GrpPath="";
																
								if(userBean.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
									userSearchPath = "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")";
									GROUPSEARCHPATHEXPANDED = "expandMembers("+userSearchPath+")";
									GrpPath="CAMID(\":nTCGM:Areas:\")";
								}
								else if(userBean.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
									userSearchPath = "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")";
									GROUPSEARCHPATHEXPANDED = "expandMembers("+userSearchPath+")";
									GrpPath="CAMID(\":nTCGM:Sectors:\")";
								}
								else if(userBean.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
									userSearchPath = "CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")";
									GROUPSEARCHPATHEXPANDED = "expandMembers("+userSearchPath+")";
									GrpPath="CAMID(\":nTCGM:Affiliates:\")";
								}
								
								 if(!(userBean.getRole().equalsIgnoreCase(TCGMConstants.DIVISION))){
								 	boolean check = facade.checkUserExists(userSearchPath, GROUPSEARCHPATHEXPANDED);

									if (!check) 
									{
										facade.addToRole(userSearchPath, GrpPath);
									}

								}
								 if((userBean.getRole().equalsIgnoreCase(TCGMConstants.DIVISION))){
								 	facade.addToReportGroup("CAMID(\":nTCGM:"+strCatId+""+strDivisions[i]+"\")","/content/folder[@name='AI TCGM']/folder[@name='Division']");
								 }
															}
			 } 
			 catch (MalformedURLException e) 
			 {
				 e.printStackTrace();
				 myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				 throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			
			 } 
			 catch (RemoteException e) 
			 {
				 e.printStackTrace();
				 myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				 throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			 } 
			 catch (TCGMException e) 
			 {
				 e.printStackTrace();
				 myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				 throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			 } 
			 catch (ServiceException e) 
			 {
				 e.printStackTrace();
				 myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				 throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			 }
			 catch (Exception e) 
			 {
				 e.printStackTrace();
				 myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				 throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			 }		

		 }


	private String getGroupSearchPath(RptUser rptUser, String strDiv) throws TCGMException
	{
		String GROUPSEARCHPATH="";
		
		if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+rptUser.getAffCode()+""+strDiv+"\")";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+rptUser.getAreaCode()+""+strDiv+"\")";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+rptUser.getSecCode()+""+strDiv+"\")";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+TCGMConstants.HQ_SUP+"\")";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_CON)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+TCGMConstants.HQ_CON+"\")";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+TCGMConstants.ALL_DIVISIONS+"\")";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)){
			GROUPSEARCHPATH = "CAMID(\":nTCGM:"+TCGMConstants.DIVISION+""+strDiv+"\")";
		}
		
		return GROUPSEARCHPATH;
	}

/*	private String getGroupSearchPathExpanded(RptUser rptUser) throws TCGMException
	{
		String GROUPSEARCHPATHEXPANDED="";
	
		if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)){
			GROUPSEARCHPATHEXPANDED = "expandMembers(CAMID(\":AI TCGM:level 2:level 3:Level 4:TCGM_AFF"+rptUser.getAffCode()+"_Consumer\"))";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.AREA)){
		GROUPSEARCHPATHEXPANDED = "expandMembers(CAMID(\":AI TCGM:level 2:level 3:Level 4:TCGM_AREA"+rptUser.getAreaCode()+"_Consumer\"))";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)){
		GROUPSEARCHPATHEXPANDED = "expandMembers(CAMID(\":AI TCGM:level 2:level 3:TCGM_SEC"+rptUser.getSecCode()+"_Consumer\"))";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_SUP)){
		GROUPSEARCHPATHEXPANDED = "expandMembers(CAMID(\":AI TCGM:level 2:level 3:Level 4:"+TCGMConstants.HQ_SUP+"\"))";
		}else if(rptUser.getRole().equalsIgnoreCase(TCGMConstants.HQ_CON)){
			GROUPSEARCHPATHEXPANDED = "expandMembers(CAMID(\":AI TCGM:level 2:level 3:Level 4:"+TCGMConstants.HQ_CON+"\"))";
		}
	
		return GROUPSEARCHPATHEXPANDED;
	}	*/
	
	public void addUsersToGroup(ArrayList userList, String GROUPSEARCHPATH) throws TCGMException
		{

			ReportNetFacade facade = null; 

			final String NAMESPACE               = "Cognos";
			String GROUPSEARCHPATHEXPANDED = "";	
			try 
			{
				facade = new ReportNetFacade();
				for (int i=0; i<userList.size(); i++)
				{
					String userID = (String)userList.get(i);
					String userSearchPath = "CAMID(\":nTCGM:"+userID+"\")";//"CAMID(\"" + NAMESPACE + ":u:cn=" + userID+ ",ou=users\")";
					GROUPSEARCHPATHEXPANDED = "expandMembers("+GROUPSEARCHPATH+")"; //getGroupSearchPathExpanded(userBean);

					boolean check = facade.checkUserExists(userSearchPath, GROUPSEARCHPATHEXPANDED);

					if (!check) 
					{
						facade.addToRole(userSearchPath, GROUPSEARCHPATH);
					}
					
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			
			} 
			catch (RemoteException e) 
			{
				e.printStackTrace();
				myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			} 
			catch (TCGMException e) 
			{
				e.printStackTrace();
				myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			} 
			catch (ServiceException e) 
			{
				e.printStackTrace();
				myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			}		

		}	

	public void removeUsersFromGroup(ArrayList userList, String GROUPSEARCHPATH) throws TCGMException
		{

			ReportNetFacade facade = null; 

			final String NAMESPACE               = AppConst.getReportNetNameSpace();
			String GROUPSEARCHPATHEXPANDED = "";	
			try 
			{
				facade = new ReportNetFacade();
				for (int i=0; i<userList.size(); i++)
				{
					String userID = (String)userList.get(i);
					//String userSearchPath = "CAMID(\"" + NAMESPACE + ":u:cn=" + userID + ",ou=users\")";
					String userSearchPath = "CAMID(\":nTCGM:"+userID+"\")";
					GROUPSEARCHPATHEXPANDED = "expandMembers("+GROUPSEARCHPATH+")"; //getGroupSearchPathExpanded(userBean);
					//System.out.println(userSearchPath);
					//System.out.println(GROUPSEARCHPATHEXPANDED);
					boolean check = facade.checkUserExists(userSearchPath, GROUPSEARCHPATHEXPANDED);
//System.out.println(check);
					if (check){ 
						facade.removeFromRole(userSearchPath, GROUPSEARCHPATH);
					}
						
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
	
			} 
			catch (RemoteException e) 
			{
				e.printStackTrace();
				myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			} 
			catch (TCGMException e) 
			{
				e.printStackTrace();
				myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			} 
			catch (ServiceException e) 
			{
				e.printStackTrace();
				myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", null, e.getMessage());
			}		

		}	

		public void recreateUserMaintenance(ArrayList userList) throws TCGMException
		{

					ReportNetFacade facade = null; 

					final String NAMESPACE               = AppConst.getReportNetNameSpace();
					String GROUPSEARCHPATHEXPANDED = "";
					String GROUPSEARCHPATH		    = "";	
		
					try 
					{
						facade = new ReportNetFacade();
			
						for (int i=0; i<userList.size(); i++)
						{
							RptUser userBean = (RptUser)userList.get(i);							
										
							String userSearchPath = "CAMID(\"" + NAMESPACE + ":u:cn=" + userBean.getUserid() + ",ou=users\")";
							GROUPSEARCHPATH = "CAMID(\":nTCGM:"+userBean.getRole()+"\")";
							GROUPSEARCHPATHEXPANDED = "expandMembers("+GROUPSEARCHPATH+")"; 
					
					
							boolean check = facade.checkUserExists(userSearchPath, GROUPSEARCHPATHEXPANDED);
					
							if (!check)
							{
								facade.addToRole(userSearchPath, GROUPSEARCHPATH);
							}
					
								
						}
					} 
					catch (MalformedURLException e) 
					{
						e.printStackTrace();
						myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
						throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
			
					} 
					catch (RemoteException e) 
					{
						e.printStackTrace();
						myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
						throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
					} 
					catch (TCGMException e) 
					{
						e.printStackTrace();
						myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
						throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
					} 
					catch (ServiceException e) 
					{
						e.printStackTrace();
						myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
						throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
						throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
					}		

		}
		public void generateUnitReport(String datasetId,String dataSetName) throws TCGMException {
			String parameterList = "datasetId: " + datasetId ;
			UserToken ut = SQLUtil.getOracleAdmin();
			
			
			
			ReportNetFacade facade = null; 

			String[] reportsArray = new String[1];
			Vector finalReportsVector = new Vector();
			Vector runOptionsVector = new Vector();
			RunOptionArrayProp[] runOptionArrayProp = null;
			RunOption[] runOptions = null;
			String jobName = "Job With No Job Description";			
			final String PACKAGENAME = AppConst.getInstance().getReportNetPackageName();
			int reportSize = 0;
			int finalReportSize = 0;

			try 
			{
				facade = new ReportNetFacade();
				
				ReportDefinition report = this.getReportById("244", ut);	
						
						
							/*****************************************************************
							 * This block of code will create the reportView -- Start
							 ******************************************************************/
							BaseClass[] reportViews = null;
							BaseClass reportView = null;
							Map parameterMap = new HashMap();
							parameterMap.put( "Dataset_Table_Id", new String[] { datasetId } );
							
							
							// start by retrieving the base report
							BaseClass baseReport = facade.queryForObjects( report.getSearchPath() )[0]; // [0] return 1st value only
				
							BaseClass newFolder = facade.addFolder( AppConst.getReportNetHQPath(), report.getDisplayName() + " (" + report.getName() + ") " );
							reportViews = new BaseClass[1];
						
							reportView = facade.createReportView( baseReport.getSearchPath().getValue(),
									newFolder.getSearchPath().getValue(),
									report.getDisplayName() + " (" + report.getName() + ")-"+dataSetName,
									parameterMap, report );

							reportViews[0] = reportView;
							if (!(reportViews == null))
							{
								for (int i=0; i< reportViews.length; i++)
								{
									if (!(reportViews[i]==null))
									{
										finalReportsVector.add(reportViews[i].getSearchPath().getValue());
						
										
										/*****************************************************************
										 * This block of code will create the runOptions for each Report -- Start
										 ******************************************************************/
										
										
										runOptions = facade.executeReport( reportViews[i].getSearchPath().getValue(), report );	
										
										runOptionsVector.add(runOptions);
										finalReportSize++;
										/*****************************************************************
										 * This block of code will create the runOptions for each Report -- End
										 ******************************************************************/
									}

								}
							}
							
					
				if (finalReportSize > 0)
				{	
					runOptionArrayProp = new RunOptionArrayProp[finalReportSize];	
					for(int i=0 ; i <finalReportSize; i++)	
					{
						runOptionArrayProp[i] = new RunOptionArrayProp();
						runOptionArrayProp[i].setValue((RunOption[])runOptionsVector.get(i));
					}
					String[] finalReportsArray = new String[finalReportsVector.size()];
					finalReportsVector.toArray(finalReportsArray);	
					ProcessDao processDao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
					jobName = "Units Data";
					facade.addNewJob(PACKAGENAME, jobName, finalReportsArray, runOptionArrayProp);
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				myLogger.error("A Malformed URL Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
				
			} 
			catch (RemoteException e) 
			{
				e.printStackTrace();
				myLogger.error("A Remote Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
			} 
			catch (TCGMException e) 
			{
				e.printStackTrace();
				myLogger.error("An Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
			} 
			catch (ServiceException e) 
			{
				e.printStackTrace();
				myLogger.error("A Service Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				myLogger.error("A Generic Exception Occured While Executing the printReportBundle. Message: " + e.toString());
				throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
			}		

		}		
			
}