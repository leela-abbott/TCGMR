package abbott.ai.tcgm.action.form;


import java.util.Vector;
//import java.util.Iterator;
import abbott.ai.tcgm.entities.*;
import org.apache.log4j.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.struts.action.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TCGMProductionForm extends TCGMForm {

	private java.util.Vector reportList;
	private String selReport;
	private boolean chkRestrict;
	private static Vector destinationList;
	private String selReportDest;
	private boolean chkImmediate;
//	private String[] columns = new String[] { "GROUP", "PRODUCT", "RPT_AFF", "SUP_AFF" } ; //MURALRS 04/03
//	private String[] columns = new String[] { "RPT_AFF", "SUP_AFF", "END_AFF", "LIST" } ;
	private String[] operations;
	//private ReportRestriction[] restrictions; 7-1-03 bd; replaced by non-array below
	private ReportRestriction restrictions;
	private String numCopies;
	private String formHandler;
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.action.form.TCGMProductionForm" );
	private String jobName;
	private String modelLongDesc ="";  //A.Winter 
	private String memo ="";  //A.Winter
	private boolean restrictionEntered = false;

	public TCGMProductionForm() {
		if (destinationList == null) {
			destinationList = new Vector(3);
			destinationList.add(new Option("Run Only", "0" ) );
			destinationList.add(new Option("Generate Report", "G" ) );
			destinationList.add(new Option("Generate & Print Report", "GP" ) );
			//destinationList.add(new Option("Rmt 143", "Rmt 143" ) );
			//destinationList.add(new Option("Rmt 14", "Rmt 14" ) );
			//destinationList.add(new Option("Rmt 2017", "Rmt 2017" ) ); // Added 9-4-03
			//destinationList.add(new Option("Area", "Area" ) ); // Added 9-9-03
		}

		this.resetRestrictions();
		operations = ReportRestriction.Operations;
	 }

	private void resetRestrictions() {
//		restrictions = new ReportRestriction[10];
// 6-20-03 changed by bd
//		restrictions = new ReportRestriction[4];
//		for (int i = 0; i<restrictions.length; i++) {
//			restrictions[i] = new ReportRestriction();
//		}

		restrictions = new ReportRestriction();
	}


	public Vector getReportList() {
		return reportList;
	}
	public void setReportList(Vector reportList) {
		this.reportList = reportList;
	}
	public void setSelReport(String selReport) {
		this.selReport = selReport;
	}
	public String getSelReport() {
		return selReport;
	}
	public void setChkImmediate(boolean chkImmediate) {
		this.chkImmediate = chkImmediate;
	}
	public boolean isChkImmediate() {
		return chkImmediate;
	}
	public void setChkRestrict(boolean chkRestrict) {
		this.chkRestrict = chkRestrict;
	}
	public boolean isChkRestrict() {
		return chkRestrict;
	}
	public void setSelReportDest(String selReportDest) {
		this.selReportDest = selReportDest;
	}
	public String getSelReportDest() {
		return selReportDest;
	}
	public void setDestinationList(Vector destinationList) {
		this.destinationList = destinationList;
	}
	public Vector getDestinationList() {
		return destinationList;
	}

// 7-1-03 bd; not using any longer; used limiter
//	public String[] getColumns() {
//		return columns;
//	}
//	public void setColumns(String[] columns) {
//		this.columns = columns;
//	}

	public void setOperations(String[] operations) {
		this.operations = operations;
	}
	public String[] getOperations() {
		return operations;
	}
//	public ReportRestriction[] getRestrictions() {
//		return restrictions;
//	}
//	public void setRestrictions(ReportRestriction[] restrictions) {
//		this.restrictions = restrictions;
//	}

	public ReportRestriction getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(ReportRestriction restrictions) {
		this.restrictions = restrictions;
	}

	public void setNumCopies(String numCopies) {
		this.numCopies = numCopies;
	}
	public String getNumCopies() {
		return numCopies;
	}

	public String getRestrictionStr() {
		StringBuffer sb = new StringBuffer();
//		for (int i=0; i < restrictions.length; i++) {
//			if ( !restrictions[i].getRestriction().equals("") ) {
//				sb.append( " AND " + restrictions[i].getRestriction()  ); //MURALRS 04/03
//			}
//		}

		// 7-1-03 Added by bd in leiu of code above
		//        The pipe ("|") char will be used in the Oracle procedure to separate WHERE
		//        conditions and throw out column names that don't apply to the job.
		//        All conditions (once validated in Oracle procedure) will be "ANDed" together in Oracle
		if ( !restrictions.getRptAffRestriction().equals("") ) {
			sb.append( " | " + restrictions.getRptAffRestriction()  );
		}
		if ( !restrictions.getSupAffRestriction().equals("") ) {
			sb.append( " | " + restrictions.getSupAffRestriction()  );
		}
		if ( !restrictions.getEndAffRestriction().equals("") ) {
			sb.append( " | " + restrictions.getEndAffRestriction()  );
		}
		if ( !restrictions.getRptListRestriction().equals("") ) {
			sb.append( " | " + restrictions.getRptListRestriction()  );
		}
		if ( !restrictions.getSupListRestriction().equals("") ) {
			sb.append( " | " + restrictions.getSupListRestriction()  );
		}
		if ( !restrictions.getEndListRestriction().equals("") ) {
			sb.append( " | " + restrictions.getEndListRestriction()  );
		}
		if ( !restrictions.getGroupIdLimiterValues().equals("") ) {
			sb.append( " | " + restrictions.getGroupIdLimiterValues()  );
		}

		// trim off leading AND and return
		if (sb.toString().length() > 2)
		   // returns the string buffer starting at position 4 to the end of the buffer
		   return sb.toString().substring(2);
		else
		   // no restrictions entered so return an empty string buffer
		   return sb.toString();
	}

	public ReportPrintRequest getReportPrintRequest() {
		ReportPrintRequest rpr = new ReportPrintRequest();
		rpr.setReportId( this.getSelReport() );
		rpr.setReportDest( this.getSelReportDest() );
		rpr.setNumCopies( this.getNumCopies() );
		return rpr;
	}
	public void setFormHandler(String formHandler) {
		this.formHandler = formHandler;
	}
	public String getFormHandler() {
		return formHandler;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobName() {
		return jobName;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// this.reset();
	}

	public void reset(ActionMapping mapping, ServletRequest request) {
		// this.reset();
	}

	public void reset() {
		myLogger.debug("Reset being called");
		this.setCmd("");
		this.setJobName("");
		this.setRestrictionEntered(false);
		this.resetRestrictions();
		this.setModelSelected(this.getModelSelected());
	}

	public void setRestrictionEntered(boolean restrictionEntered) {
		this.restrictionEntered = restrictionEntered;
	}
	public boolean isRestrictionEntered() {
		return restrictionEntered;
	}

	/**
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @return
	 */
	public String getModelLongDesc() {
		return modelLongDesc;
	}

	/**
	 * @param string
	 */
	public void setMemo(String string) {
		memo = string;
	}

	/**
	 * @param string
	 */
	public void setModelLongDesc(String string) {
		modelLongDesc = string;
	}

}