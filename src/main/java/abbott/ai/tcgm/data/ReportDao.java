package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.entities.*;
import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface ReportDao extends TCGMDao {

	public String[] getRestrictCols(String reportId) throws TCGMException;

	public ReportDefinition getReportById(String reportId) throws TCGMException;
	public Vector getAffiliatesBySectorId(String sectorId) throws TCGMException;
	public void runProcedure(String procName) throws TCGMException;
	public int runReportDirect(String reportId, String modelId) throws TCGMException;
	public java.io.File createReportExtractFile( ReportDefinition report ) throws TCGMException;
	//public boolean hasData(String sql) throws TCGMException;
	public boolean hasData(String fileName, String whereClause) throws TCGMException;
	public Vector getAllRGMAffReports() throws TCGMException;
	public Vector getAllAffIdsForThisRun(String columnName, String viewName, String datasetId) throws TCGMException ;
}