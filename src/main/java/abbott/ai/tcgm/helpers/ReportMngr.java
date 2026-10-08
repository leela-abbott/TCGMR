package abbott.ai.tcgm.helpers;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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

import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.RunOption;
import com.cognos.developer.schemas.bibus._3.RunOptionArrayProp;

public class ReportMngr implements TCGMMngr {
	protected String name = this.getClass().getName();
	private static final Logger myLogger = LogManager.getLogger(ReportMngr.class);

	public ReportMngr() {
	}

	public void printReportBundle(JobInstance job) throws TCGMException {
		myLogger.debug("Step1: Entered printReportBundle. Processing job --> {}", job.getDesc());
		myLogger.debug("Job Que Id --> {}", job.getJobQueId());
		String dest = job.getJobParms().getProperty(JobConstants.PN_REPORT_DEST);
		myLogger.debug("Step2: dest --> {}", dest);
		String copies = job.getJobParms().getProperty(JobConstants.PN_REPORT_COPIES);
		myLogger.debug("Step2: copies --> {}", copies);
		printReportBundle(job.getJobQueId(), dest, copies);
	}

	@SuppressWarnings("unchecked")
	public void printReportBundle(String jobQId, String dest, String copies) throws TCGMException {
		String parameterList = "jobQId: " + jobQId + ", dest: " + dest + ", copies: " + copies;
		myLogger.debug("Step3: Entered printReportBundle 2");
		UserToken ut = SQLUtil.getOracleAdmin();

		List<ReportInstance> reports = (List<ReportInstance>) this.getReportInstancesByJobQId(jobQId, ut);
		ReportPrintRequest rpr = new ReportPrintRequest();
		rpr.setReportDest(dest);
		rpr.setNumCopies(copies);
		rpr.setJobQueId(jobQId);
		ReportNetFacade facade = null;

		List<String> finalReportsVector = new ArrayList<>();
		List<RunOption[]> runOptionsVector = new ArrayList<>();
		RunOptionArrayProp[] runOptionArrayProp = null;
		RunOption[] runOptions = null;
		String jobName = "Job With No Job Description";
		final String PRINTER1 = AppConst.getInstance().getReportNetPrinter1();
		final String PACKAGENAME = AppConst.getInstance().getReportNetPackageName();
		int finalReportSize = 0;

		try {
			facade = new ReportNetFacade();
			for (ReportInstance instance : reports) {
				if (instance.getRowCount() > 0 || instance.getReportDefinition().isGenerateEmptyReport()) {
					String rptID = instance.getReportDefinition().getId();
					int rptid = Integer.parseInt(rptID);
					rpr.setReportId(rptID);
					rpr.setDatasetId(instance.getDatasetId());

					if (rptid == 100 || rptid == 101 || rptid == 102 || rptid == 103 || rptid == 228 || rptid == 256
							|| rptid == 257 || rptid == 258 || rptid == 259 || rptid == 260) {
						rpr.setReportContentId(instance.getReportContentId().trim() + " - "
								+ getJobQueParam(jobQId, "DFRD_RPT_NAME_SUF", ut));
					} else if (rptid == 155 || rptid == 156 || rptid == 192 || rptid == 193 || rptid == 272
							|| rptid == 273 || rptid == 274 || rptid == 275) {
						rpr.setReportContentId(instance.getReportContentId().trim() + " - "
								+ getJobQueParam(jobQId, "HDGE_RPT_NAME_SUF", ut));
					} else {
						rpr.setReportContentId(instance.getReportContentId());
					}

					BaseClass[] reportViews = this.printReportNetReport(rpr, facade);
					if (reportViews != null) {
						for (BaseClass view : reportViews) {
							if (view != null) {
								finalReportsVector.add(view.getSearchPath().getValue());
								boolean printReportFlag = "GP".equals(rpr.getReportDest());
								ReportDefinition report = this.getReportById(rpr.getReportId(), ut);

								if (printReportFlag) {
									runOptions = facade.executeAndPrintReport(view.getSearchPath().getValue(), PRINTER1,
											report);
								} else {
									runOptions = facade.executeReport(view.getSearchPath().getValue(), report);
								}
								runOptionsVector.add(runOptions);
								finalReportSize++;
							}
						}
					}
				}
			}
			if (finalReportSize > 0) {
				runOptionArrayProp = new RunOptionArrayProp[finalReportSize];
				for (int i = 0; i < finalReportSize; i++) {
					runOptionArrayProp[i] = new RunOptionArrayProp();
					runOptionArrayProp[i].setValue(runOptionsVector.get(i));
				}
				String[] finalReportsArray = finalReportsVector.toArray(new String[0]);
				ProcessDao processDao = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(ut);
				jobName = processDao.getJobDesc(jobQId);
				facade.addNewJob(PACKAGENAME, jobName, finalReportsArray, runOptionArrayProp);
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("An explicit Exception Occured While Executing printReportBundle: ", e);
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		} catch (Exception e) {
			myLogger.error("A Generic Runtime Exception Occured While Executing printReportBundle: ", e);
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		}
	}

	public String[] getRestrictCols(String reportId) throws TCGMException {
		return new String[] { "RPT_AFF", "SUP_AFF" };
	}

	@SuppressWarnings("rawtypes")
	public List getReportInstancesByJobQId(String jobQId, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getReportInstancesByJobQId(jobQId);
	}

	public String getJobQueParam(String jobQId, String jobParmName, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getJobQueParmName(jobQId, jobParmName);
	}

	@SuppressWarnings("rawtypes")
	public List getReportInstances(UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getReportInstances();
	}

	public ReportDefinition getReportById(String reportId, UserToken ut) throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getReportById(reportId);
	}

	@SuppressWarnings("rawtypes")
	public List getAffiliatesBySectorId(String sectorId, UserToken ut) throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getAffiliatesBySectorId(sectorId);
	}

	@SuppressWarnings("rawtypes")
	public List getAllRGMAffReports(UserToken ut) throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getAllRGMAffReports();
	}

	@SuppressWarnings("rawtypes")
	public List getAllAffIdsForThisRun(UserToken ut, String columnName, String viewName, String datasetId)
			throws TCGMException {
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.getAllAffIdsForThisRun(columnName, viewName, datasetId);
	}

	public ReportInstance getReportInstanceById(String reportId, String jobQId, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		return rid.getReportInstanceById(reportId, jobQId);
	}

	public void runPrintReport(String modelId, ReportPrintRequest rpr) throws TCGMException {
		this.runReport(rpr.getReportId(), modelId);
	}

	public void deleteReportInstanceById(String reportId, String jobQId, UserToken ut) throws TCGMException {
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		ReportInstance report = rid.getReportInstanceById(reportId, jobQId);
		new DatasetMngr().deleteDatasetById(ut, Integer.parseInt(report.getDatasetId()));
		rid.deleteReportInstanceById(reportId, jobQId);
	}

	@SuppressWarnings("unchecked")
	public void deleteReportInstancesByJobQueId(String jobQId, UserToken ut) throws TCGMException {
		DatasetMngr dm = new DatasetMngr();
		ReportInstanceDao rid = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportInstanceDao(ut);
		List<ReportInstance> reports = (List<ReportInstance>) this.getReportInstancesByJobQId(jobQId, ut);
		for (ReportInstance report : reports) {
			rid.deleteReportInstanceById(report.getReportDefinition().getId(), jobQId);
			dm.deleteDatasetById(ut, Integer.parseInt(report.getDatasetId()));
		}
	}

	public int runReport(String reportId, String modelId) throws TCGMException {
		UserToken ut = SQLUtil.getOracleAdmin();
		ReportDao rd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getReportDao(ut);
		return rd.runReportDirect(reportId, modelId);
	}

	public void printReport(ReportPrintRequest rpr) throws TCGMException {
		myLogger.debug("Printing report: {}, Dest: {}, Copies: {}", rpr.getReportId(), rpr.getReportDest(),
				rpr.getNumCopies());
		UserToken ut = SQLUtil.getOracleAdmin();
		UserToken as400ut = new UserToken("TCGMFTP", "BRIDGE9QZ");
		String lib = "AITCGDVFIL";

		ReportInstance reportInstance = this.getReportInstanceById(rpr.getReportId(), rpr.getJobQueId(), ut);
		AS400 as400 = new AS400(as400ut);
	}

	public BaseClass[] printReportNetReport(ReportPrintRequest rpr, ReportNetFacade facade) throws TCGMException {
		BaseClass[] reportViews = null;
		boolean printReportFlag = "GP".equals(rpr.getReportDest());

		UserToken ut = SQLUtil.getOracleAdmin();
		ReportDefinition report = this.getReportById(rpr.getReportId(), ut);
		ReportInstance reportInstance = this.getReportInstanceById(rpr.getReportId(), rpr.getJobQueId(), ut);

		String reportId = rpr.getReportId();
		String modelId = reportInstance.getJobInstance().getModelId();
		String datasetId = rpr.getDatasetId();
		String jobQueId = reportInstance.getJobInstance().getJobQueId();
		String reportContentId = rpr.getReportContentId();

		if (report.getUseCase() != null && !report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_I)) {
			if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF)
					|| report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S)
					|| report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A)) {
				reportViews = this.burstReport(report.getSearchPath(), datasetId, modelId, jobQueId, report, facade,
						reportContentId);
			} else if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_DIV_B)) {
				reportViews = this.createDivReportViews(report.getSearchPath(), datasetId, modelId, jobQueId,
						printReportFlag, report, reportContentId);
			} else {
				reportViews = this.createReportViews(report.getSearchPath(), datasetId, modelId, jobQueId,
						printReportFlag, report, reportContentId);
			}
		}

		myLogger.debug("Report executed in printReportNetReport with: id={}, model={}, dataset={}", reportId, modelId,
				datasetId);
		return reportViews;
	}

	private BaseClass[] createReportViews(String reportPath, String datasetId, String modelId, String jobQueId,
			boolean printReport, ReportDefinition report, String reportContentId) throws TCGMException {
		String parameterList = "modelId: " + modelId + ", reportPath: " + reportPath + ", datasetId: " + datasetId
				+ ", jobQueId: " + jobQueId;
		BaseClass[] reportViews = null;
		if (reportPath != null && reportPath.startsWith("/")) {
			try {
				ReportNetFacade facade = new ReportNetFacade();
				Map<String, String[]> parameterMap = new HashMap<>();
				parameterMap.put(AppConst.getReportNetParmsDatasetTableID(), new String[] { datasetId });
				parameterMap.put(AppConst.getReportNetParmsModelID(), new String[] { modelId });
				parameterMap.put(AppConst.getReportNetParmsJobQueueID(), new String[] { jobQueId });

				UserToken ut = SQLUtil.getOracleAdmin();
				ModelMngr mm = new ModelMngr();
				String modelName = mm.getModelName(ut, Integer.parseInt(modelId));
				if ("Default".equalsIgnoreCase(reportContentId)) {
					reportContentId = " - Model- " + modelName;
				}

				if (report.getName().equalsIgnoreCase("EXCPT_TCGM02_RPT1")
						|| report.getName().equalsIgnoreCase("AFF EXCPT_TCGM02_RPT1")) {
					reportContentId = reportContentId + " - Unit-" + mm.getModelParmValue(ut, Integer.parseInt(modelId),
							TCGMModel.Type.FACTOR, "CURRNT_UNITS_NAME");
				}

				int rptid = Integer.parseInt(report.getId());
				if (rptid == 100 || rptid == 101 || rptid == 102 || rptid == 103 || rptid == 228) {
					String dispName = report.getDisplayName();
					String value = mm.getModelParmValue(ut, Integer.parseInt(modelId), TCGMModel.Type.ANALYSIS,
							"DFRD_UNITS_NAME");
					String frthChar = value.substring(3, 4);
					if (frthChar.equals("A") || frthChar.equals("P") || frthChar.equals("U")) {
						report.setDisplayName("Earned " + dispName);
					} else {
						report.setDisplayName("Deferred " + dispName);
					}
				}

				BaseClass baseReport = facade.queryForObjects(reportPath)[0];
				BaseClass newFolder = facade.addFolder(AppConst.getReportNetHQPath(),
						report.getDisplayName() + " (" + report.getName() + ") ");
				reportViews = new BaseClass[1];

				if (rptid == 137 || rptid == 138) {
					String dispName = report.getDisplayName();
					String value = mm.getModelParmValue(ut, Integer.parseInt(modelId), TCGMModel.Type.ANALYSIS,
							"SLSID_TITLE");
					report.setDisplayName(dispName + " " + value);
				}

				reportViews[0] = facade.createReportView(baseReport.getSearchPath().getValue(),
						newFolder.getSearchPath().getValue(),
						report.getDisplayName() + " (" + report.getName() + ") " + reportContentId, parameterMap,
						report);
				myLogger.debug("Step 5: HQ Report View Created Successfully");
			} catch (Exception ex) {
				myLogger.error("Exception Occured inside createReportViews: ", ex);
				throw new TCGMException("ReportMngr", "createReportViews", parameterList, ex.getMessage());
			}
		}
		return reportViews;
	}

	private BaseClass[] createDivReportViews(String reportPath, String datasetId, String modelId, String jobQueId,
			boolean printReport, ReportDefinition report, String reportContentId) throws TCGMException {
		String parameterList = "modelId: " + modelId + ", reportPath: " + reportPath + ", datasetId: " + datasetId
				+ ", jobQueId: " + jobQueId;
		BaseClass[] reportViews = null;
		if (reportPath != null && reportPath.startsWith("/")) {
			try {
				ReportNetFacade facade = new ReportNetFacade();
				Map<String, String[]> parameterMap = new HashMap<>();
				parameterMap.put(AppConst.getReportNetParmsDatasetTableID(), new String[] { datasetId });
				parameterMap.put(AppConst.getReportNetParmsModelID(), new String[] { modelId });
				parameterMap.put(AppConst.getReportNetParmsJobQueueID(), new String[] { jobQueId });

				UserToken ut = SQLUtil.getOracleAdmin();
				ModelMngr mm = new ModelMngr();
				String modelName = mm.getModelName(ut, Integer.parseInt(modelId));
				if ("Default".equalsIgnoreCase(reportContentId)) {
					reportContentId = " - Model- " + modelName;
				}

				int rptid = Integer.parseInt(report.getId());
				if (rptid == 256 || rptid == 257 || rptid == 258 || rptid == 259 || rptid == 260) {
					String dispName = report.getDisplayName();
					String value = mm.getModelParmValue(ut, Integer.parseInt(modelId), TCGMModel.Type.ANALYSIS,
							"DFRD_UNITS_NAME");
					String frthChar = value.substring(3, 4);
					if (frthChar.equals("A") || frthChar.equals("P") || frthChar.equals("U")) {
						report.setDisplayName("Earned " + dispName);
					} else {
						report.setDisplayName("Deferred " + dispName);
					}
				}

				BaseClass baseReport = facade.queryForObjects(reportPath)[0];
				BaseClass newFolder = facade.addFolder(AppConst.getReportNetDIVPath(),
						report.getDisplayName() + " (" + report.getName() + ") ");
				reportViews = new BaseClass[1];

				reportViews[0] = facade.createReportView(baseReport.getSearchPath().getValue(),
						newFolder.getSearchPath().getValue(),
						report.getDisplayName() + " (" + report.getName() + ") " + reportContentId, parameterMap,
						report);
			} catch (Exception ex) {
				myLogger.error("Exception Occured inside createDivReportViews: ", ex);
				throw new TCGMException("ReportMngr", "createDivReportViews", parameterList, ex.getMessage());
			}
		}
		return reportViews;
	}

	private BaseClass[] burstReport(String reportPath, String datasetId, String modelId, String jobQueId,
			ReportDefinition report, ReportNetFacade facade, String reportContentId) throws TCGMException {
		BaseClass[] reportViews = null;
		String path = null;
		String parameterList = "modelId: " + modelId + ", reportPath: " + reportPath + ", datasetId: " + datasetId
				+ ", jobQueId: " + jobQueId;
		UserToken ut = SQLUtil.getOracleAdmin();
		ModelMngr mm = new ModelMngr();
		String modelName = mm.getModelName(ut, Integer.parseInt(modelId));
		if ("Default".equalsIgnoreCase(reportContentId)) {
			reportContentId = " - Model- " + modelName;
		}
		if (report.getName().equalsIgnoreCase("EXCPT_TCGM02_RPT1")
				|| report.getName().equalsIgnoreCase("AFF EXCPT_TCGM02_RPT1")) {
			reportContentId = reportContentId + " - Unit-"
					+ mm.getModelParmValue(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR, "CURRNT_UNITS_NAME");
		}

		if (reportPath != null && reportPath.startsWith("/")) {
			try {
				BaseClass baseReport = facade.queryForObjects(reportPath)[0];
				reportViews = new BaseClass[1];

				Map<String, String[]> parameterMap = new HashMap<>();
				parameterMap.put(AppConst.getReportNetParmsDatasetTableID(), new String[] { datasetId });
				parameterMap.put(AppConst.getReportNetParmsModelID(), new String[] { modelId });

				if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF)
						|| report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S)
						|| report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A)) {
					parameterMap.put(AppConst.getReportNetParmsJobQueueID(), new String[] { jobQueId });
					if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF)) {
						path = AppConst.getReportNetAffiliateReportsPath();
					} else if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_S)) {
						path = AppConst.getReportNetSectorPath();
					} else if (report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF_A)) {
						path = AppConst.getReportNetSectorPath();
						path = path.replaceAll("Sector", "Area");
					}

					BaseClass[] reportFolders = facade.queryForObjects(path);
					if (reportFolders.length == 0
							&& report.getUseCase().equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_AFF)) {
						facade.addFolder(AppConst.getReportNetAffiliatePath(), AppConst.getReportNetFolderReports());
					}
				}

				reportViews[0] = facade.createReportView(baseReport.getSearchPath().getValue(), path,
						report.getDisplayName() + " (" + report.getName() + ") " + reportContentId, parameterMap,
						report);
			} catch (Exception ex) {
				myLogger.error("Exception Occured While Executing burstReport: ", ex);
				throw new TCGMException("ReportMngr", "burstReport", parameterList, ex.getMessage());
			}
		}
		return reportViews;
	}

	public void userMaintenance(ArrayList<RptUser> userList, String action) throws TCGMException {
		ReportNetFacade facade = null;
		final String NAMESPACE = AppConst.getReportNetNameSpace();
		String groupSearchPathExpanded = "";
		String groupSearchPath = "";

		try {
			facade = new ReportNetFacade();
			for (RptUser userBean : userList) {
				String[] strDivisions = null;
				RptUserMngr rptMngr = new RptUserMngr();
				strDivisions = rptMngr.getDivisionException(userBean.getDivision());

				for (String div : strDivisions) {
					String userSearchPath = "CAMID(\"" + NAMESPACE + ":u:cn=" + userBean.getUserid() + ",ou=users\")";

					groupSearchPath = getGroupSearchPath(userBean, div.trim());
					groupSearchPathExpanded = "expandMembers(" + groupSearchPath + ")";

					boolean check = facade.checkUserExists(userSearchPath, groupSearchPathExpanded);

					if (check && action.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_REMOVE_USERS)) {
						facade.removeFromRole(userSearchPath, groupSearchPath);
					} else if ((!check) && action.equalsIgnoreCase(TCGMConstants.REPORT_CONSTANT_ADD_USERS)) {
						facade.addToRole(userSearchPath, groupSearchPath);
					}
				}
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("Exception Occured inside userMaintenance connection pipeline: ", e);
			throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
		} catch (Exception e) {
			myLogger.error("Generic Error caught during userMaintenance assignment: ", e);
			throw new TCGMException("ReportMngr", "userMaintenance", null, e.getMessage());
		}
	}

	public void addGroup(RptUser userBean, String strCatId, String strCatName) throws TCGMException {
		ReportNetFacade facade = null;
		String groupSearchPath = "";

		try {
			facade = new ReportNetFacade();
			groupSearchPath = "CAMID(\":nTCGM:\")";
			String[] strDivisions = new String[] { userBean.getDivision() };

			for (String div : strDivisions) {
				facade.addGroup(groupSearchPath, strCatId + div);
				String userSearchPath = "";
				String groupSearchPathExpanded = "";
				String grpPath = "";

				if (userBean.getRole().equalsIgnoreCase(TCGMConstants.AREA)) {
					userSearchPath = "CAMID(\":nTCGM:" + strCatId + div + "\")";
					groupSearchPathExpanded = "expandMembers(" + userSearchPath + ")";
					grpPath = "CAMID(\":nTCGM:Areas:\")";
				} else if (userBean.getRole().equalsIgnoreCase(TCGMConstants.SECTOR)) {
					userSearchPath = "CAMID(\":nTCGM:" + strCatId + div + "\")";
					groupSearchPathExpanded = "expandMembers(" + userSearchPath + ")";
					grpPath = "CAMID(\":nTCGM:Sectors:\")";
				} else if (userBean.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE)) {
					userSearchPath = "CAMID(\":nTCGM:" + strCatId + div + "\")";
					groupSearchPathExpanded = "expandMembers(" + userSearchPath + ")";
					grpPath = "CAMID(\":nTCGM:Affiliates:\")";
				}

				if (!userBean.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)) {
					boolean check = facade.checkUserExists(userSearchPath, groupSearchPathExpanded);
					if (!check) {
						facade.addToRole(userSearchPath, grpPath);
					}
				}
				if (userBean.getRole().equalsIgnoreCase(TCGMConstants.DIVISION)) {
					facade.addToReportGroup("CAMID(\":nTCGM:" + strCatId + div + "\")",
							"/content/folder[@name='AI TCGM']/folder[@name='Division']");
				}
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("Exception processing addGroup deployment metadata nodes: ", e);
			throw new TCGMException("ReportMngr", "addGroup", null, e.getMessage());
		} catch (Exception e) {
			myLogger.error("Uncaught processing Exception during addGroup iteration: ", e);
			throw new TCGMException("ReportMngr", "addGroup", null, e.getMessage());
		}
	}

	private String getGroupSearchPath(RptUser rptUser, String strDiv) throws TCGMException {
		String groupSearchPath = "";
		String role = rptUser.getRole();

		if (role.equalsIgnoreCase(TCGMConstants.AFFILIATE)) {
			groupSearchPath = "CAMID(\":nTCGM:" + rptUser.getAffCode() + strDiv + "\")";
		} else if (role.equalsIgnoreCase(TCGMConstants.AREA)) {
			groupSearchPath = "CAMID(\":nTCGM:" + rptUser.getAreaCode() + strDiv + "\")";
		} else if (role.equalsIgnoreCase(TCGMConstants.SECTOR)) {
			groupSearchPath = "CAMID(\":nTCGM:" + rptUser.getSecCode() + strDiv + "\")";
		} else if (role.equalsIgnoreCase(TCGMConstants.HQ_SUP)) {
			groupSearchPath = "CAMID(\":nTCGM:" + TCGMConstants.HQ_SUP + "\")";
		} else if (role.equalsIgnoreCase(TCGMConstants.HQ_CON)) {
			groupSearchPath = "CAMID(\":nTCGM:" + TCGMConstants.HQ_CON + "\")";
		} else if (role.equalsIgnoreCase(TCGMConstants.ALL_DIVISIONS)) {
			groupSearchPath = "CAMID(\":nTCGM:" + TCGMConstants.ALL_DIVISIONS + "\")";
		} else if (role.equalsIgnoreCase(TCGMConstants.DIVISION)) {
			groupSearchPath = "CAMID(\":nTCGM:" + TCGMConstants.DIVISION + strDiv + "\")";
		}

		return groupSearchPath;
	}

	public void addUsersToGroup(ArrayList<String> userList, String groupSearchPath) throws TCGMException {
		ReportNetFacade facade = null;
		String groupSearchPathExpanded = "";
		try {
			facade = new ReportNetFacade();
			for (String userID : userList) {
				String userSearchPath = "CAMID(\":nTCGM:" + userID + "\")";
				groupSearchPathExpanded = "expandMembers(" + groupSearchPath + ")";

				boolean check = facade.checkUserExists(userSearchPath, groupSearchPathExpanded);
				if (!check) {
					facade.addToRole(userSearchPath, groupSearchPath);
				}
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("Exception handled executing addUsersToGroup configuration bounds: ", e);
			throw new TCGMException("ReportMngr", "addUsersToGroup", null, e.getMessage());
		} catch (Exception e) {
			myLogger.error("Generic exception caught executing addUsersToGroup transaction logs: ", e);
			throw new TCGMException("ReportMngr", "addUsersToGroup", null, e.getMessage());
		}
	}

	public void removeUsersFromGroup(ArrayList<String> userList, String groupSearchPath) throws TCGMException {
		ReportNetFacade facade = null;
		String groupSearchPathExpanded = "";
		try {
			facade = new ReportNetFacade();
			for (String userID : userList) {
				String userSearchPath = "CAMID(\":nTCGM:" + userID + "\")";
				groupSearchPathExpanded = "expandMembers(" + groupSearchPath + ")";

				boolean check = facade.checkUserExists(userSearchPath, groupSearchPathExpanded);
				if (check) {
					facade.removeFromRole(userSearchPath, groupSearchPath);
				}
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("Exception encountered executing removeUsersFromGroup processing pipeline: ", e);
			throw new TCGMException("ReportMngr", "removeUsersFromGroup", null, e.getMessage());
		} catch (Exception e) {
			myLogger.error("Uncaught runtime Exception executing removeUsersFromGroup iteration blocks: ", e);
			throw new TCGMException("ReportMngr", "removeUsersFromGroup", null, e.getMessage());
		}
	}

	public void recreateUserMaintenance(ArrayList<RptUser> userList) throws TCGMException {
		ReportNetFacade facade = null;
		final String NAMESPACE = AppConst.getReportNetNameSpace();
		String groupSearchPathExpanded = "";
		String groupSearchPath = "";

		try {
			facade = new ReportNetFacade();
			for (RptUser userBean : userList) {
				String userSearchPath = "CAMID(\"" + NAMESPACE + ":u:cn=" + userBean.getUserid() + ",ou=users\")";
				groupSearchPath = "CAMID(\":nTCGM:" + userBean.getRole() + "\")";
				groupSearchPathExpanded = "expandMembers(" + groupSearchPath + ")";

				boolean check = facade.checkUserExists(userSearchPath, groupSearchPathExpanded);
				if (!check) {
					facade.addToRole(userSearchPath, groupSearchPath);
				}
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("Exception processing recreateUserMaintenance schema arrays: ", e);
			throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
		} catch (Exception e) {
			myLogger.error("Uncaught dynamic reflection Exception caught on recreateUserMaintenance processing loops: ",
					e);
			throw new TCGMException("ReportMngr", "recreateUserMaintenance", null, e.getMessage());
		}
	}

	public void generateUnitReport(String datasetId, String dataSetName) throws TCGMException {
		String parameterList = "datasetId: " + datasetId;
		UserToken ut = SQLUtil.getOracleAdmin();

		ReportNetFacade facade = null;
		List<String> finalReportsVector = new ArrayList<>();
		List<RunOption[]> runOptionsVector = new ArrayList<>();
		RunOptionArrayProp[] runOptionArrayProp = null;
		RunOption[] runOptions = null;
		String jobName = "Job With No Job Description";
		final String PACKAGENAME = AppConst.getInstance().getReportNetPackageName();
		int finalReportSize = 0;

		try {
			facade = new ReportNetFacade();
			ReportDefinition report = this.getReportById("244", ut);

			Map<String, String[]> parameterMap = new HashMap<>();
			parameterMap.put("Dataset_Table_Id", new String[] { datasetId });

			BaseClass baseReport = facade.queryForObjects(report.getSearchPath())[0];
			BaseClass newFolder = facade.addFolder(AppConst.getReportNetHQPath(),
					report.getDisplayName() + " (" + report.getName() + ") ");
			BaseClass[] reportViews = new BaseClass[1];

			reportViews[0] = facade.createReportView(baseReport.getSearchPath().getValue(),
					newFolder.getSearchPath().getValue(),
					report.getDisplayName() + " (" + report.getName() + ")-" + dataSetName, parameterMap, report);

			if (reportViews != null) {
				for (BaseClass view : reportViews) {
					if (view != null) {
						finalReportsVector.add(view.getSearchPath().getValue());
						runOptions = facade.executeReport(view.getSearchPath().getValue(), report);
						runOptionsVector.add(runOptions);
						finalReportSize++;
					}
				}
			}

			if (finalReportSize > 0) {
				runOptionArrayProp = new RunOptionArrayProp[finalReportSize];
				for (int i = 0; i < finalReportSize; i++) {
					runOptionArrayProp[i] = new RunOptionArrayProp();
					runOptionArrayProp[i].setValue(runOptionsVector.get(i));
				}
				String[] finalReportsArray = finalReportsVector.toArray(new String[0]);
				jobName = "Units Data";
				facade.addNewJob(PACKAGENAME, jobName, finalReportsArray, runOptionArrayProp);
			}
		} catch (MalformedURLException | RemoteException | TCGMException e) {
			myLogger.error("Exception handled deploying custom asynchronous segment nodes: ", e);
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		} catch (Exception e) {
			myLogger.error("Uncaught system Exception during customized node generation process dispatch: ", e);
			throw new TCGMException("ReportMngr", "printReportBundle", parameterList, e.getMessage());
		}
	}
}
