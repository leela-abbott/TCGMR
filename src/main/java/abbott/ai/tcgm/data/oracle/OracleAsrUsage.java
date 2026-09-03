/*
 * Created on Jun 17, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.data.oracle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.poi.ss.usermodel.Cell;
import java.io.InputStream;

import javax.sql.RowSet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.AsrUsageDao;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OracleAsrUsage extends OracleDao implements AsrUsageDao {
	/**
	 * 
	 */
	private static Logger myLogger = Logger.getLogger("OracleAsrUsage");
	private ASRUsage searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_ASR;
	private final String SUP_AFF_SELECT = "SELECT RPT_AFF, max(CREATE_DATETIME) as CREATE_DATETIME,cycle_id FROM ";
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,RPT_AFF,RPT_INV_CD, "
			+ "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, "
			+ "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,PROD_ORIGIN,SUP_KEY,USAGE_FACTOR,ID,CYCLE_ID, " + "CREATE_DATETIME "
			+ "FROM (";

	// Added by Debajyoti For ASR
	private String query = null;
	private boolean flag = false;

	/*****************************************************************************************/
	/**
	 * @param userToken    UserToken object
	 * @param searchObject Asr object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject   Sort object
	 * @param flag
	 * @param query
	 */

	public OracleAsrUsage(UserToken userToken, ASRUsage searchObject, PagingFilter pagingFilter, Sort sortObject) {
		this.setEntityTable("AFF_ASR_USAGE_FACTOR_FILE");
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;

	}

	/**
	 * @param userToken    UserToken object
	 * @param searchObject Asr object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleAsrUsage(UserToken userToken, ASRUsage searchObject, PagingFilter pagingFilter) {
		this.setEntityTable("AFF_ASR_USAGE_FACTOR_FILE");
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}

	/**
	 * @param userToken    UserToken object
	 * @param searchObject Asr object
	 */
	public OracleAsrUsage(UserToken userToken, ASRUsage searchObject) {
		this.setEntityTable("AFF_ASR_USAGE_FACTOR_FILE");
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}

	/**
	 * @param userToken UserToken object
	 */
	public OracleAsrUsage(UserToken userToken) {
		this.setEntityTable("AFF_ASR_USAGE_FACTOR_FILE");
		this.userToken = userToken;
	}

	// Added the constructor By Debajyoti for ASR(Query and flag)
	public OracleAsrUsage(UserToken userToken, ASRUsage searchObject, PagingFilter pagingFilter, Sort sortObject,
			String query, boolean flag) {
		this.setEntityTable("AFF_ASR_USAGE_FACTOR_FILE");
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.query = query;
		this.flag = flag;

	}

	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet. It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it
	 * is finished with it.
	 * 
	 * @param searchObject Asr object with search criteria
	 * @param sortObject   Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(ASRUsage searchObject, Sort sortObject) throws TCGMException {
		String methodName = "getRS(Asr,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}

	/**
	 * This method queries the database for a RowSet. It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it
	 * is finished with it.
	 * 
	 * @param searchObject Asr object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(ASRUsage searchObject) throws TCGMException {
		String methodName = "getRS(ASRUsage)";
		this.setSearchObject(searchObject);
		return this.getRS();
	}

	/**
	 * This method queries the database for a RowSet. It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it
	 * is finished with it.
	 * 
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException {
		String methodName = "getRS()";

		try {
			if (this.sortObject.getSortColumn().equalsIgnoreCase(DBConst.COL_DEF)) {
				this.sortObject.setSortColumn(DBConst.COL_ASR_DEF);
			}
			String query = this.MIDDLE_SELECT_START + this.INNER_SELECT + this.getEntity() + this.genWhereClause() +
			// commneted for sort order - Gain 04-06-06
					this.buildEBCDICSortClause(this.sortObject) +
//					  " ORDER BY RPT_AFF,RPT_LIST,RPT_PACK "+
					this.MIDDLE_SELECT_END;

			// if a paging filter exists then we need to change the sql to add the outer sql
			// clause
			if (this.pagingFilter != null)

			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + this.pagingFilter.getStartRecord()
						+ this.OUTER_WHERE_MAX_BOUND + this.pagingFilter.getEndRecord();

			}

			this.initRS(query, TCGMConstants.JDBC_ROWSET);

			rs.execute();
			return rs;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(this.className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		}
	}

	/*****************************************************************************************/
	/**
	 * @param searchObject Asr object with search criteria
	 * @param sortObject   Sort object with sort criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO(ASRUsage searchObject, Sort sortObject) throws TCGMException {
		String methodName = "getVO(ASRUsage,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}

	/**
	 * @param searchObject Asr object with search criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO(ASRUsage searchObject) throws TCGMException {
		String methodName = "getVO(ASRUsage)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}

	/**
	 *
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException {
		String methodName = "getVO";

		Vector vec = new Vector();

		try {
			this.getRS();

			while (rs.next()) {
				vec.add(this.getAsrFromCurrentRow(rs));
			}
			return vec;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(this.className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			SQLUtil.closeRowSet(rs);
		}
	}

	// This below method is added for ASR DATA fetch for Suff Aff Selection:

	public Vector getVoForSuffAffSelected(String updatedquery, boolean flag) throws TCGMException {

		String methodName = "getVO";

		Vector vec = new Vector();
		String newmethodName = "getVoForSuffAffSelected";

		try {

			if (this.sortObject.getSortColumn().equalsIgnoreCase(DBConst.COL_DEF)) {
				this.sortObject.setSortColumn(DBConst.COL_ASR_DEF);
			}

			if (this.pagingFilter != null) {
				updatedquery = "Select * from (" + updatedquery + ")m  Where m.RN BETWEEN "
						+ this.pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND
						+ this.pagingFilter.getEndRecord();
			}

			this.getRSForSuffAff(updatedquery);

			while (rs.next()) {
				vec.add(this.getAsrFromCurrentRow(rs));
			}
			return vec;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(this.className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			SQLUtil.closeRowSet(rs);
		}

	}

	public RowSet getRSForSuffAff(String sql) throws TCGMException {
		String methodName = "getRSForSuffAff";

		try {
			this.initRS(sql, TCGMConstants.JDBC_ROWSET);

			rs.execute();
			return rs;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(this.className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		}
	}

	// This above method is added for ASR DATA fetch for Suff Aff Selection end
	// here:

	/**
	 * This method will be used to convert the "next()" RowSet ojbect to an ASR
	 * object
	 * 
	 * @param rs RowSet
	 * @return Asr
	 * @throws TCGMException
	 */
	public ASRUsage getAsrFromCurrentRow(RowSet rs) throws TCGMException {
		String methodName = "getAsrFromCurrentRow(RowSet)";

		ASRUsage asr = new ASRUsage();

		try {
			// asr.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));
			// asr.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			asr.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			asr.setSupAff(rs.getString(DBConst.COL_SUP_AFF));

			asr.getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			asr.getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			asr.getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			asr.getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			asr.getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			asr.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			asr.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			asr.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			asr.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			asr.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			asr.setProductOrigin(rs.getString(DBConst.COL_PROD_ORIGIN));
			asr.setSupKey(rs.getString(DBConst.COL_SUP_KEY));
			asr.setUsage(rs.getString("USAGE_FACTOR"));
			asr.setAsrId(rs.getString("ID"));
			asr.setCycleId(rs.getString("CYCLE_ID"));
			// asr.setActionCode(rs.getString("ACD"));

			// asr.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			asr.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			// asr.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			// asr.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return asr;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		}
	}

	/*****************************************************************************************/
	/**
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildSearchList() {
		this.searchList = new Vector();

		// need to build a search object and then loop through it to get the clause.
		// this.searchList.add(new
		// Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		// this.searchList.add(new
		// Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_PROD_ORIGIN, searchObject.getProductOrigin(),
				comparisonType(searchObject.getProductOrigin())));
		this.searchList.add(
				new Search(DBConst.COL_RPT_AFF, searchObject.getRptAff(), comparisonType(searchObject.getRptAff())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD, searchObject.getRptProduct().getInvCode(),
				comparisonType(searchObject.getRptProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST, searchObject.getRptProduct().getList(),
				comparisonType(searchObject.getRptProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL, searchObject.getRptProduct().getLabel(),
				comparisonType(searchObject.getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE, searchObject.getRptProduct().getSize(),
				comparisonType(searchObject.getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK, searchObject.getRptProduct().getPack(),
				comparisonType(searchObject.getRptProduct().getPack())));
		this.searchList.add(
				new Search(DBConst.COL_SUP_AFF, searchObject.getSupAff(), comparisonType(searchObject.getSupAff())));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD, searchObject.getSupProduct().getInvCode(),
				comparisonType(searchObject.getSupProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST, searchObject.getSupProduct().getList(),
				comparisonType(searchObject.getSupProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL, searchObject.getSupProduct().getLabel(),
				comparisonType(searchObject.getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE, searchObject.getSupProduct().getSize(),
				comparisonType(searchObject.getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK, searchObject.getSupProduct().getPack(),
				comparisonType(searchObject.getSupProduct().getPack())));
		this.searchList.add(
				new Search(DBConst.COL_SUP_KEY, searchObject.getSupKey(), comparisonType(searchObject.getSupKey())));
		this.searchList.add(new Search("USAGE_FACTOR", TCGMUtil.getNumTrimLeadZero(searchObject.getUsage()),
				comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getUsage()))));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME, searchObject.getCreateLog().getUserName(),
				TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search("RPT_AFF", searchObject.getAffiliate(), TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search("ID", searchObject.getAsrId(), TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search("CYCLE_ID", searchObject.getCycleId(), TCGMConstants.ORACLE_EQUALS_COMPARISON));
	}

	/**
	 *
	 * @return string
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.className);
		sb.append(", \n");
		sb.append("User Token,\n");
		sb.append(this.userToken.toString());
		sb.append("\nEntity: ");
		sb.append(this.getEntity());
		sb.append("\n");
		sb.append(this.pagingFilter.toString());
		sb.append("\n");
		sb.append(this.sortObject.toString());
		sb.append("\nSearch Object: ");
		sb.append(this.searchObject);

		return sb.toString();
	}

	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * 
	 * @param searchObject Asr
	 */
	private void setSearchObject(ASRUsage searchObject) {
		this.searchObject = searchObject;
		this.buildSearchList();

	}

	/**
	 *
	 * @return SearchObject
	 */
	private ASRUsage getSearchObject() {
		return this.searchObject;
	}

	public ArrayList getSupAff(Connection conn) throws TCGMException {
		String methodName = "getSupAff(Connection conn)";
		boolean connWasNull = false;

		Statement stmt = null;
		String sql = this.SUP_AFF_SELECT + this.getEntity() + "group by RPT_AFF,cycle_id order by cycle_id,RPT_AFF";
		this.logger.debug("\nSQL: " + sql);

		ArrayList supAff = new ArrayList();
		try {
			if (conn == null) {
				conn = SQLUtil.openConnection();
				// Set this so that we know the connection was not created externally and needs
				// to be closed here.
				connWasNull = true;
			}
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				supAff.add(rs.getString("cycle_id").trim() + "::" + rs.getString("RPT_AFF").trim() + "::"
						+ rs.getDate("CREATE_DATETIME"));
			}
			return supAff;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		} finally {
			SQLUtil.closeResultSet(rs);
			SQLUtil.closeStatment(stmt);
			if (connWasNull) {
				// The connection was created within the method and not passed in
				// So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}

	public void delete(ASRUsage affBpc, Connection conn, String aff, String cycleId) throws TCGMException {
		String methodName = "delete(AffBpc,Connection)";
		boolean connWasNull = false;
		this.setSearchObject(affBpc);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		if (aff == null || aff.equals("-1")) {// -1 is added by Debajyoti			
			sql += this.genWhereClause();
			
		} else if ((aff == null || aff == "") && (cycleId == null || cycleId == "")) {
			sql += " WHERE ID='" + affBpc.getAsrId() + "'";
			
		}
		else {
			sql += " WHERE RPT_AFF='" + aff + "' AND CYCLE_ID='" + cycleId + "'";			
		}

		this.logger.debug("\nSQL: " + sql);
		logger.error("Calling SQL is :----"+sql);

		try {
			if (conn == null) {
				conn = SQLUtil.openConnection();
				// Set this so that we know the connection was not created externally and needs
				// to be closed here.
				connWasNull = true;
			}

			ps = conn.prepareStatement(sql);

			ps.execute();
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(className, methodName, sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		} finally {
			SQLUtil.closePS(ps);
			if (connWasNull) {
				// The connection was created within the method and not passed in
				// So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}

	public void delete(Vector affBpcList) throws TCGMException {
		String methodName = "delete(Vector)";

		Connection conn = null;
		try {
			conn = SQLUtil.openConnection();

			for (int i = 0; i < affBpcList.size(); i++) {
				this.delete((ASRUsage) affBpcList.elementAt(i), conn, "", "");
			}
		} finally {
			SQLUtil.closeConnection(conn);
		}
	}

	public boolean insert(Vector asrTransList, int modelId, String acd, Connection conn, String userId)
			throws TCGMException, TCGMDuplicateItemException {
		String methodName = "insert(Vector,Connection)";
		boolean connWasNull = false;
		boolean duplic = true;
		Vector asrTranErrorList = null;
		Vector list = null;

		CallableStatement cs = null;
		PreparedStatement stmt = null;
		Statement obj_Del_Statement = null;
		int result = 0;
		boolean flag = true;
		String insertSql = "INSERT INTO TCGM.ASR_T_INTERMEDIATE(MODEL_ID," + " DATASET_TABLE_ID," + " RPT_AFF	,"
				+ " RPT_INV_CD," + " RPT_LIST," + " RPT_PACK," + " RPT_LABEL," + " RPT_SIZE," + " SUP_AFF	,"
				+ " SUP_INV_CD," + " SUP_LIST," + " SUP_PACK," + " SUP_LABEL," + " SUP_SIZE," + " ACD," + " USERNAME,"
				+ " PROD_ORIGIN," + " SUP_KEY," + " USAGE_FAC) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			if (conn == null) {
				conn = SQLUtil.openConnection();
				connWasNull = true;
			}

			stmt = conn.prepareStatement(insertSql);
			obj_Del_Statement = conn.createStatement();
			for (int i = 0; i < asrTransList.size(); i++) {
				ASRUsage fac = (ASRUsage) asrTransList.get(i);

				stmt.setInt(1, Integer.parseInt(fac.getModelId()));
				stmt.setInt(2, fac.getDatasetTableIdInt());
				stmt.setString(3, fac.getRptAff());
				stmt.setString(4, fac.getRptProduct().getInvCode());
				stmt.setString(5, fac.getRptProduct().getList());
				stmt.setString(6, fac.getRptProduct().getPack());
				stmt.setString(7, fac.getRptProduct().getLabel());
				stmt.setString(8, fac.getRptProduct().getSize());
				stmt.setString(9, fac.getSupAff());
				stmt.setString(10, fac.getSupProduct().getInvCode());
				stmt.setString(11, fac.getSupProduct().getList());
				stmt.setString(12, fac.getSupProduct().getPack());
				stmt.setString(13, fac.getSupProduct().getLabel());
				stmt.setString(14, fac.getSupProduct().getSize());
				stmt.setString(15, fac.getActionCode());
				stmt.setString(16, userId);
				stmt.setString(17, fac.getProductOrigin());
				stmt.setString(18, fac.getSupKey());
				stmt.setDouble(19, Double.parseDouble(fac.getUsage()));
				stmt.addBatch();

			}
			stmt.executeBatch();
			int intResultCode = 0;
			String errorCode = "";
			// String sql = "{ call " + schema + ".ASR_CREATE.copy_selected(?,?,?,?,?) }";
			String sql = "{ call " + schema + ".APPLY_MAINTENANCE.BUILD_ASR_CREATE_COPY_SELECTED(?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt(1, modelId);
			cs.setString(2, acd);
			cs.setString(3, userId);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			obj_Del_Statement.execute("DELETE FROM TCGM.ASR_T_INTERMEDIATE");
			result = cs.getInt(5);
			if (result > 0) {
				errorCode = cs.getString(4);
				flag = false;
			}

		} catch (SQLException sqle) {
			flag = false;
			logException(className, methodName, sqle);

			myLogger.debug("-- Parameters -- ");
			if (sqle.getMessage().startsWith("ORA-00001")) {

				throw new TCGMException(className, methodName, sqle.toString());
			} else if (sqle.getMessage().startsWith("ORA-20000")) {
				throw new TCGMException(className, methodName, sqle.toString());
			} else {

				throw new TCGMException(className, methodName, sqle.toString());
			}
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (stmt != null) {
					obj_Del_Statement.close();
					obj_Del_Statement = null;
				}
			} catch (SQLException obj_SQLException) {
				obj_SQLException.printStackTrace();
			}

			SQLUtil.closeCS(cs);
			if (connWasNull) {
				SQLUtil.closeConnection(conn);
			}
		}
		return flag;
	}

	public boolean insert(int modelId, String acd, String afiliate, Connection conn, String userId, String cycleId)
			throws TCGMException, TCGMDuplicateItemException {
		String methodName = "insert(String modelId,String actionCode,Connection conn)";
		boolean connWasNull = false;
		boolean duplic = true;
		Vector asrTranErrorList = null;
		Vector list = null;
		boolean flag = true;
		CallableStatement cs = null;
		int result = 0;

		try {
			if (conn == null) {
				conn = SQLUtil.openConnection();
				connWasNull = true;
			}

			int intResultCode = 0;
			String errorCode = "";
			// String sql = "{ call " + schema + ".ASR_CREATE.COPY_ALL(?,?,?,?,?,?,?) }";
			String sql = "{ call " + schema + ".APPLY_MAINTENANCE.BUILD_ASR_CREATE_COPY_ALL(?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt(1, modelId);
			cs.setString(2, acd);
			cs.setString(3, afiliate);
			cs.setString(4, userId);
			cs.setString(5, cycleId);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.registerOutParameter(7, Types.INTEGER);
			cs.execute();
			result = cs.getInt(7);
			if (result > 0) {
				errorCode = cs.getString(6);
				flag = false;
			}

		} catch (SQLException sqle) {
			flag = false;
			logException(className, methodName, sqle);

			myLogger.debug("-- Parameters -- ");
			if (sqle.getMessage().startsWith("ORA-00001")) {

				throw new TCGMException(className, methodName, sqle.toString());
			} else if (sqle.getMessage().startsWith("ORA-20000")) {
				throw new TCGMException(className, methodName, sqle.toString());
			} else {

				throw new TCGMException(className, methodName, sqle.toString());
			}
		} finally {
			SQLUtil.closeCS(cs);
			if (connWasNull) {
				SQLUtil.closeConnection(conn);
			}
		}
		return flag;
	}

	public void update(ASRUsage asrTran, Connection conn) throws TCGMException, TCGMDuplicateItemException {
		String methodName = "update(AsrTran)";
		boolean connWasNull = false;

		CallableStatement cs = null;

		try {
			if (conn == null) {
				conn = SQLUtil.openConnection();
				// Set this so that we know the connection was not created externally and needs
				// to be closed here.
				connWasNull = true;
			}

			String sql = "{ call " + this.schema + ".AFF_ASR_USAGE_FACTOR_FILE_UPD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt(1, Integer.parseInt(asrTran.getAsrId()));
			cs.setString(2, this.updColDefault(asrTran.getProductOrigin(), " "));
			cs.setString(3, asrTran.getRptAff());
			cs.setString(4, asrTran.getRptProduct().getInvCode());
			cs.setString(5, asrTran.getRptProduct().getList());
			cs.setString(6, this.updColDefault(asrTran.getRptProduct().getLabel(), " "));
			cs.setString(7, this.updColDefault(asrTran.getRptProduct().getSize(), " "));
			cs.setString(8, asrTran.getRptProduct().getPack());
			cs.setString(9, asrTran.getSupAff());
			cs.setString(10, asrTran.getSupProduct().getInvCode());
			cs.setString(11, asrTran.getSupProduct().getList());
			cs.setString(12, this.updColDefault(asrTran.getSupProduct().getLabel(), " "));
			cs.setString(13, this.updColDefault(asrTran.getSupProduct().getSize(), " "));
			cs.setString(14, asrTran.getSupProduct().getPack());
			cs.setString(15, this.updColDefault(asrTran.getSupKey(), " "));
			cs.setDouble(16, Double.parseDouble(asrTran.getUsage()));
			cs.execute();
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			if (sqle.toString().indexOf("ORA-00001") > 0) {
				// Error was a unique constraint error
				throw new TCGMDuplicateItemException(sqle.toString());
			} else {
				// Error was some other error
				throw new TCGMException(className, methodName, sqle.toString());
			}
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		} finally {
			SQLUtil.closeCS(cs);
			if (connWasNull) {
				// The connection was created within the method and not passed in
				// So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}

	/**
	 * This method will loop through the given vector and update each AsrTran object
	 * in the collection based on the AsrTranId The connection is created internally
	 * 
	 * @param asrTranList Vector
	 * @throws TCGMException
	 */
	public boolean update(Vector asrTranList) throws TCGMException, TCGMDuplicateItemException {
		String methodName = "update(Vector)";
		boolean flag = false;

		Connection conn = null;
		try {
			conn = SQLUtil.openConnection();

			for (int i = 0; i < asrTranList.size(); i++) {
				this.update((ASRUsage) asrTranList.elementAt(i), conn);
				flag = true;
			}
		} catch (TCGMDuplicateItemException ex) {
			flag = false;
			throw new TCGMDuplicateItemException(ex.toString());
		}

		catch (TCGMException ex) {
			flag = false;
			throw new TCGMException(ex);
		}

		finally {
			SQLUtil.closeConnection(conn);
		}
		return flag;
	}

	@Override

	// The below method is added for ASRUsage Upload on 2024.
	public void upload(String fileName, String cycle) throws TCGMException, IOException {

		String methodName = "upload(String fileName)";
		String sql = "SELECT DATAFEED_AS_LOC FROM TCGM.DATA_FEED WHERE DATAFEED_PROC='AFFASR_LOAD'";
		Connection conn = null;
		ResultSet rstSet = null;
		Statement stmt = null;
		String fileNameCreate = "";
		FileWriter out = null;
		

		try {
			conn = SQLUtil.openConnection();
			stmt = conn.createStatement();
			rstSet = stmt.executeQuery(sql);
			if (rstSet.next()) {
				fileNameCreate = rstSet.getString(1);

			}
			fileNameCreate = TCGMUtil.escapeString(fileNameCreate + "\\ASRTXALL-" + cycle + ".asrusage");
			HSSFWorkbook wb = null;
			POIFSFileSystem fs;
			Sheet sheetXls = null;
			Row sheet;
			InputStream inp = null;
			Workbook wb_xssf = null;
			Workbook wb_hssf = null;

			String fileExtn = GetFileExtension(fileName);
			inp = new FileInputStream(fileName);

			if (fileExtn.equalsIgnoreCase("xlsx")) {

				wb_xssf = new XSSFWorkbook(inp);
				sheetXls = wb_xssf.getSheetAt(0);

			}
			if (fileExtn.equalsIgnoreCase("xls")) {

				wb_hssf = new HSSFWorkbook(inp);
				sheetXls = wb_hssf.getSheetAt(0);
			}

			out = new FileWriter(new File(fileNameCreate));

			String arrayStr[] = null;
			String appendString = "";

			for (int j = 1; j < sheetXls.getPhysicalNumberOfRows(); j++) {
				sheet = (Row) sheetXls.getRow(j);
				appendString = "";

				if (isNotBlank(sheet.getCell(2))) {
					break;
				}

				if (getCellStringValue(sheet.getCell(0)).trim().length() > 1) {
					appendString += getCellStringValue(sheet.getCell(0)).trim().substring(0, 1) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(0)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(1)).trim().length() > 4) {
					appendString += getCellStringValue(sheet.getCell(1)).trim().substring(0, 4) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(1)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(2)).trim().length() > 1) {
					appendString += getCellStringValue(sheet.getCell(2)).trim().substring(0, 1) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(2)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(3)).trim().length() > 6) {
					appendString += getCellStringValue(sheet.getCell(3)).trim().substring(0, 6) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(3)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(4)).trim().length() > 3) {
					appendString += getCellStringValue(sheet.getCell(4)).trim().substring(0, 3) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(4)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(5)).trim().length() > 3) {
					appendString += getCellStringValue(sheet.getCell(5)).trim().substring(0, 3) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(5)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(6)).trim().length() > 4) {
					appendString += getCellStringValue(sheet.getCell(6)).trim().substring(0, 4) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(6)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(7)).trim().length() > 4) {
					appendString += getCellStringValue(sheet.getCell(7)).trim().substring(0, 4) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(7)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(8)).trim().length() > 1) {
					appendString += getCellStringValue(sheet.getCell(8)).trim().substring(0, 1) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(8)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(9)).trim().length() > 6) {
					appendString += getCellStringValue(sheet.getCell(9)).trim().substring(0, 6) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(9)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(10)).trim().length() > 3) {
					appendString += getCellStringValue(sheet.getCell(10)).trim().substring(0, 3) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(10)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(11)).trim().length() > 3) {
					appendString += getCellStringValue(sheet.getCell(11)).trim().substring(0, 3) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(11)).trim() + ",";
				}
				if (getCellStringValue(sheet.getCell(12)).trim().length() > 4) {
					appendString += getCellStringValue(sheet.getCell(12)).trim().substring(0, 4) + ",";
				} else {
					appendString += getCellStringValue(sheet.getCell(12)).trim() + ",";
				}
				if (getCellDoubleValue(sheet.getCell(13)).length() >= 1) {
					appendString += getCellDoubleValue(sheet.getCell(13)) + ",";
				}else {
					appendString += "" + ",";
				}
				if (getCellStringValue(sheet.getCell(14)).trim().length() >= 1) {
					appendString += getCellStringValue(sheet.getCell(14)).trim() + "\n";
				} else {
					appendString += "" + "\n";
				}
				out.write(appendString);

			}
			if (out != null) {
				out.close();
			}

		}

		catch (IOException ex) {
			if (out != null) {
				out.close();
			}

			logException(className, methodName, ex);
			throw new TCGMException(className, methodName, ex.toString());
		} catch (Exception e) {
			if (out != null) {
				out.close();
			}

			logException(className, methodName, e);
			throw new TCGMException(className, methodName, e.toString());
		} finally {
			SQLUtil.closeResultSet(rstSet);
			SQLUtil.closeStatment(stmt);
			SQLUtil.closeConnection(conn);
		}
	}

	public String getCellStringValue(Cell cell) {
		String retVal = "";

		try {
			if (null == cell) {
				retVal = "";
			}
			if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				retVal = "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				retVal = "" + cell.getNumericCellValue();
			} else {
				retVal = cell.getRichStringCellValue().getString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal.trim();
	}

	private static String GetFileExtension(String fname2) {
		String fileName = fname2;
		String fname = "";
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		fname = fileName.substring(0, mid);
		ext = fileName.substring(mid + 1, fileName.length());
		return ext;
	}

	private boolean isNotBlank(Cell cell) {
		if (null == cell) {
			return true;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return true;
		}
		return false;
	}

	public String getCellDoubleValue(Cell cell) {
		String retVal = "";
		int x = 0;
		try {
			if (null == cell) {
				retVal = "";
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				retVal = "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				retVal = "" + cell.getNumericCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				retVal = cell.getRichStringCellValue().getString().replaceAll(",", "");

			} else {
				retVal = cell.getRichStringCellValue().getString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal.trim();
	}

	public String round(String value) {
		String val = "";

		val = "" + returnDouble(value);
		while (val.length() < 16) {
			val = "0" + val;
		}

		return val;
	}

	public String returnDouble(String dob) {
		String absVal = "";
		String retval = "";
		String prexretval;
		String suffretval;
		if (dob.indexOf(".") > 0) {
			if (dob.substring(dob.indexOf(".") + 1, dob.length()).length() > 0)

			{
				prexretval = dob.substring(0, dob.indexOf("."));
				suffretval = dob.substring(dob.indexOf(".") + 1, dob.length());
	
				while (suffretval.length() < 7) {
					
					if (suffretval.length() == 6) {
						break;
					}
				}
				retval = prexretval + "," + suffretval;

			} else if (dob.substring(dob.indexOf(".") + 1, dob.length()).length() == 4) {
				retval = dob.substring(0, dob.indexOf(".")).concat(dob.substring(dob.indexOf(".") + 1, dob.length()));
			}

		} else {

			// retval = dob.substring(dob.indexOf(".") + 1, dob.length());

			prexretval = dob.substring(0, dob.length());
			suffretval = "000000";

			while (prexretval.length() < 11) {

				if (prexretval.length() == 10) {
					break;
				}
				prexretval = "0" + prexretval;
			}

			retval = prexretval + "," + suffretval;
		}
		return retval;
	}
}
