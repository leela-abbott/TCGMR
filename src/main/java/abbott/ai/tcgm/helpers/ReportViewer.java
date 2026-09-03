package abbott.ai.tcgm.helpers;

//import org.apache.struts.action.*;
import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

//import abbott.ai.tcgm.action.*;
//import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;

//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.exception.*;

import com.crystaldecisions.sdk.occa.report.application.*;
import com.crystaldecisions.report.web.viewer.*;
import com.crystaldecisions.sdk.occa.report.data.*;
import com.crystaldecisions.sdk.occa.report.lib.*;
import com.crystaldecisions.sdk.occa.report.reportsource.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class ReportViewer
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */

	String reportName = new String();
	UserToken userToken = null;
	String modelId = null;
	String cacheKey = null;
	String className = this.getClass().getName();
	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.helpers.ReportViewer");
	private CrystalReportViewer viewer = new CrystalReportInteractiveViewer();
	private ReportInstance reportInstance = null;



	public ReportViewer(ReportInstance instance, UserToken ut) throws TCGMException {
		logger.debug("Constructing report viewer.");

//		this.reportName = instance.getReportDefinition().getCrystalFile();
		this.userToken = ut;
		this.modelId = instance.getJobInstance().getModelId();
		this.reportInstance = instance;

		// Random String to ensure that the source can be reused per initiatialization of the ReportViewer object.
		// Stored in session along with initial retrieval of report.
		this.cacheKey = TCGMUtil.getRandomDigitStr(6);

		// initialize the viewer
		this.init();
		this.setDataSource();
		this.setParameters();
	}

	public void renderReport(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws TCGMException, IOException {
		String methodName = "renderReport(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)";

		try {
			//Have the viewer render the page.
			//response.setContentType("text/html; charset=UTF-8");
			//viewer.
			viewer.processHttpRequest(request, response, servletContext, null );
		}
		catch (ReportSDKExceptionBase rex) {
			throw new TCGMException( this.className, methodName, "Error Rendering Report - Report SDK Exception", rex.getMessage() );
		}
	}

	private void setDataSource() throws TCGMException {
		String methodName = "setDataSource()";

		try {
			ReportClientDocument clientDoc = new ReportClientDocument();
			clientDoc.setReportAppServer( AppConst.getInstance().getCrystalServer() );
			clientDoc.open(reportName + ".rpt", OpenReportOptions._openAsReadOnly);
			IReportSource source = clientDoc.getReportSource();
			logger.debug("Generating new report source");
			viewer.setReportSource(source);
		}
		catch (ReportSDKExceptionBase rex) {
			throw new TCGMException( this.className, methodName, "Error Setting Report Datasource", rex.getMessage() );
		}
	}

	private void setParameters() {

		Fields fields = new Fields();

		fields.add(this.createStringParameter("model_id", this.reportInstance.getJobInstance().getModelId() ));
		fields.add(this.createStringParameter("dataset_id", this.reportInstance.getDatasetId() ) );

		String[] parmKeys = this.reportInstance.getReportDefinition().getParmKeys();
		String parmValue = null;
		Properties parms = this.reportInstance.getJobInstance().getJobParms();
		if (parmKeys != null) {
			for (int i = 0; i< parmKeys.length; i++) {
				parmValue = parms.getProperty(parmKeys[i]); // this could be inlined but it gets pretty unreadable
				if ( (parmValue != null) && (! parmValue.equals("")) ) {
					fields.add(this.createStringParameter(parmKeys[i], parmValue ));
				}
			}
		}

		viewer.setParameterFields(fields);
	}

	private IParameterField createStringParameter(String parmName, String parmValue) {

		IParameterField pf = new ParameterField();
		ParameterFieldDiscreteValue pfdv = new ParameterFieldDiscreteValue();
		pfdv.setValue( parmValue );

		pf.setName(parmName);
		Values v = new Values();
		v.add(pfdv);
		pf.setCurrentValues( v );
		return pf;
	}

	private void init() throws TCGMException {

		logger.debug("Initializing report viewer");
		viewer.setName("Crystal_Reports_Interactive_Viewer");

		//Set the display characteristics.
		//In this case the page is displayed with group trees and the toolbar enabled, and it is set that the viewer owns the page.
		viewer.setDisplayPage(true);
		viewer.setDisplayGroupTree(false);
		viewer.setHasToggleGroupTreeButton(true);
		viewer.setDisplayToolbar(true);
		viewer.setOwnPage(true);
		viewer.setOwnForm(true);

		//Set the viewer to prompt for any database logon information or report parameters.
		//For information on setting these values through code see the Java Report Application Server SDK Java docs.
		viewer.setEnableLogonPrompt(false);
		viewer.setEnableParameterPrompt(false);

		ConnectionInfo ci = new ConnectionInfo();
		ConnectionInfos cis = new ConnectionInfos();
		PropertyBag pb = new PropertyBag();

		//place all of the database connection information into the property bag
		pb.put(PropertyBagHelper.CONNINFO_DATABASE_DLL, "crdb_odbc.dll");
		pb.put(PropertyBagHelper.CONNINFO_SERVER_NAME, "TCGM" ); // migrate to constant
		pb.put(PropertyBagHelper.CONNINFO_SERVER_TYPE, "ODBC (RDO)");
		pb.put(PropertyBagHelper.CONNINFO_DATABASE_NAME, "TCGM" );
		logger.debug("Crystal Connection Property Bag: " + pb.toString() );

		//attach the property bag to the connection info object
		ci.setAttributes(pb);
		ci.setUserName( "TCGMRPT" );
		ci.setPassword( "TCGMRPT" ); // migrate this to constants...
		ci.setKind(ConnectionInfoKind.SQL);

		cis.add(ci);
		viewer.setDatabaseLogonInfos(cis);
	}

	public void destroy() {
		logger.debug("Destroying report viewer");
		viewer.dispose();
	}
}