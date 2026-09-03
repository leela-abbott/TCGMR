package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.TCGMException;
//import abbott.ai.tcgm.entities.*;

//import java.util.Vector;
//import java.sql.*;
import javax.sql.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface ReportQueDao extends TCGMDao {

	public void insertReportData(RowSet rsData) throws TCGMException;
	public void insertFactorReportTriggerRecord(int fileCount) throws TCGMException;
}