package abbott.ai.tcgm.action.form;

import java.util.Vector;
//import java.util.Iterator;
import abbott.ai.tcgm.entities.*;
import org.apache.log4j.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReportMenuForm extends TCGMForm {

	private java.util.Vector reportList;
	private String selReport;
	private boolean chkRestrict;
	private static Vector destinationList;
	private String selReportDest;
	private boolean chkImmediate;
	private String[] columns;
	private String[] operations;
	private java.util.Vector restrictions;
	private String numCopies;
	private String reportHandler;
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.action.form.ReportMenuForm" );

	public ReportMenuForm() {
		if (destinationList == null) {
			destinationList = new Vector(2);
			destinationList.add(new Option("Rmt 143", "1" ) );
			destinationList.add( new Option("Rmt 14", "2" ) );
			destinationList.add(new Option("Rmt 2017", "3" ) );
			destinationList.add(new Option("Area", "4" ) );
		}

		restrictions = new Vector(10);
		for (int i = 0; i<10; i++)
			restrictions.add( new ReportRestriction() );
		operations = ReportRestriction.Operations;
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
	public void reset() {
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public void setOperations(String[] operations) {
		this.operations = operations;
	}
	public String[] getOperations() {
		return operations;
	}
	public java.util.Vector getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(java.util.Vector restrictions) {
		this.restrictions = restrictions;
	}
	public void setNumCopies(String numCopies) {
		this.numCopies = numCopies;
	}
	public String getNumCopies() {
		return numCopies;
	}

// 7-1-03 bd; I might have to restore this; I cldn't compile so I removed it; I cldn't c wt its used for
//    public String getRestrictionStr() {
//        StringBuffer sb = new StringBuffer();
//        Iterator i = restrictions.iterator();
//        ReportRestriction r = null;
//        while ( i.hasNext() ) {
//            r = (ReportRestriction) i.next();
//            if ( !r.getRestriction().equals("") )
//                sb.append(i.next().toString()+";");
//        }
//        return sb.toString();
//    }

	public ReportPrintRequest getReportPrintRequest() {
		ReportPrintRequest rpr = new ReportPrintRequest();
		rpr.setReportId( this.getSelReport() );
		rpr.setReportDest( this.getSelReportDest() );
		rpr.setNumCopies( this.getNumCopies() );
		return rpr;
	}
	public void setReportHandler(String reportHandler) {
		this.reportHandler = reportHandler;
	}
	public String getReportHandler() {
		myLogger.debug("Retrieving Report Handler: " + reportHandler );
		return reportHandler;
	}
}