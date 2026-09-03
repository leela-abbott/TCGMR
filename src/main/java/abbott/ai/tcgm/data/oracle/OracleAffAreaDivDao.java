/*
 * Created on Jun 5, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.data.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.data.AffAreaDivDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.AffAreaDivsion;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OracleAffAreaDivDao extends OracleDao implements AffAreaDivDao {
	private static Logger myLogger = Logger.getLogger("OracleAffAreaDivDao");
	private final String className = this.getClass().getName();
	public OracleAffAreaDivDao() {

	}

	public ArrayList getAffcodes() throws TCGMException {

		String methodName = "getAffcodes()";

		Connection obj_Connection = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		ArrayList userDetails = new ArrayList();

		try {
			String query = "SELECT AFF,AFF_DESC,AREA,AREA_DESC,REGION,REGION_DESC,SECTOR,SECTOR_DESC FROM VW_T_AFFILIATES WHERE AREA='08' ORDER BY AFF_DESC";

			OracleAffAreaDivDao.logger.debug(
				"\n\nOracleUserDao - getRS QUERY: \n" + query);

			obj_Connection = SQLUtil.openConnection();
			obj_Statement = obj_Connection.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(query);
			AffAreaDivsion affUser = null;
			while (obj_ResultSet.next()) {
				affUser = new AffAreaDivsion();
				affUser.setAffCode(
					obj_ResultSet.getString("AFF")
						+ ","
						+ obj_ResultSet.getString("AREA")
						+ ","
						+ obj_ResultSet.getString("AREA_DESC")
						+ ","
						+ obj_ResultSet.getString("REGION")
						+ ","
						+ obj_ResultSet.getString("REGION_DESC")
						+ ","
						+ obj_ResultSet.getString("SECTOR")
						+ ","
						+ obj_ResultSet.getString("SECTOR_DESC"));
				affUser.setAffDesc(obj_ResultSet.getString("AFF_DESC")+"("+obj_ResultSet.getString("AFF")+")");
				userDetails.add(affUser);
				affUser = null;
			}
			return userDetails;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(
				this.className,
				methodName,
				sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			try {
				if (obj_ResultSet != null) {
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null) {
					obj_Statement.close();
					obj_Statement = null;
				}
				if (obj_Connection != null) {
					obj_Connection.close();
					obj_Connection = null;
				}
			} catch (SQLException obj_SQLException) {
				logException(className, methodName, obj_SQLException);
				throw new TCGMException(
					this.className,
					methodName,
					obj_SQLException.toString());
			}
		}

	}

	public ArrayList getAffWanted() throws TCGMException {

		String methodName = "getAffWanted()";

		Connection obj_Connection = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		ArrayList userDetails = new ArrayList();

		try {
			String query =
				"SELECT * FROM VW_T_AFF_AREA_UNWANTED_DIV ORDER BY AFF_DESC";

			OracleAffAreaDivDao.logger.debug(
				"\n\nOracleUserDao - getRS QUERY: \n" + query);

			obj_Connection = SQLUtil.openConnection();
			obj_Statement = obj_Connection.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(query);
			AffAreaDivsion affUser = null;
			while (obj_ResultSet.next()) {
				affUser = new AffAreaDivsion();
				affUser.setAffCode(obj_ResultSet.getString(1));
				affUser.setAffDesc(obj_ResultSet.getString(2));
				affUser.setAreaCode(obj_ResultSet.getString(3));
				affUser.setAreaDesc(obj_ResultSet.getString(4));
				affUser.setRegCode(obj_ResultSet.getString(5));
				affUser.setRegDesc(obj_ResultSet.getString(6));
				affUser.setSecCode(obj_ResultSet.getString(7));
				affUser.setSecDesc(obj_ResultSet.getString(8));
				userDetails.add(affUser);
				affUser = null;
			}
			return userDetails;
		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(
				this.className,
				methodName,
				sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			try {
				if (obj_ResultSet != null) {
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null) {
					obj_Statement.close();
					obj_Statement = null;
				}
				if (obj_Connection != null) {
					obj_Connection.close();
					obj_Connection = null;
				}
			} catch (SQLException obj_SQLException) {
				logException(className, methodName, obj_SQLException);
				throw new TCGMException(
					this.className,
					methodName,
					obj_SQLException.toString());
			}
		}

	}

	public int createAff(String affCode) throws TCGMException {

		String methodName = "createAff()";

		Connection obj_Connection = null;
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		int count = 0;

		try {

			String query =
				"Insert into T_AFFILIATE_AREA_UNWANTED_DIV(AFF) Values('0"
					+ affCode
					+ "')";

			String selquery =
				"select count('x') from T_AFFILIATE_AREA_UNWANTED_DIV where aff='0"
					+ affCode
					+ "'";

			OracleAffAreaDivDao.logger.debug(
				"\n\nOracleUserDao - getRS QUERY: \n" + query);

			obj_Connection = SQLUtil.openConnection();
			obj_Connection.setAutoCommit(false);
			obj_Statement = obj_Connection.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(selquery);
			AffAreaDivsion affUser = null;
			if (obj_ResultSet.next()) {
				count = obj_ResultSet.getInt(1);

			}

			if (count == 0) {
				obj_Statement.execute(query);
			}
			obj_Connection.commit();
			return count;

		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(
				this.className,
				methodName,
				sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			try {
				if (obj_ResultSet != null) {
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null) {
					obj_Statement.close();
					obj_Statement = null;
				}
				if (obj_Connection != null) {
					obj_Connection.close();
					obj_Connection = null;
				}
			} catch (SQLException obj_SQLException) {
				logException(className, methodName, obj_SQLException);
				throw new TCGMException(
					this.className,
					methodName,
					obj_SQLException.toString());
			}
		}

	}

	public void deleteAff(ArrayList affUserList) throws TCGMException {

		String methodName = "deleteAff()";

		Connection obj_Connection = null;
		Statement obj_Statement = null;

		try {

			String selquery = "";
			obj_Connection = SQLUtil.openConnection();
			obj_Connection.setAutoCommit(false);
			obj_Statement = obj_Connection.createStatement();

			AffAreaDivsion affUser = null;

			for (int i = 0; i < affUserList.size(); i++) {
				affUser = (AffAreaDivsion) affUserList.get(i);
				selquery =
					"Delete from T_AFFILIATE_AREA_UNWANTED_DIV where aff='"
						+ affUser.getAffCode()
						+ "'";
				obj_Statement.addBatch(selquery);
			}
			obj_Statement.executeBatch();
			obj_Connection.commit();

		} catch (SQLException sqle) {
			logException(className, methodName, sqle);
			throw new TCGMException(
				this.className,
				methodName,
				sqle.toString());
		} catch (Exception e) {
			logException(className, methodName, e);
			throw new TCGMException(this.className, methodName, e.toString());
		} finally {
			try {

				if (obj_Statement != null) {
					obj_Statement.close();
					obj_Statement = null;
				}
				if (obj_Connection != null) {
					obj_Connection.close();
					obj_Connection = null;
				}
			} catch (SQLException obj_SQLException) {
				logException(className, methodName, obj_SQLException);
				throw new TCGMException(
					this.className,
					methodName,
					obj_SQLException.toString());
			}
		}

	}

}
