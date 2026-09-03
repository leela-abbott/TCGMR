package abbott.ai.tcgm.data.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Vector;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DatasetDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.exception.TCGMItemNotFoundException;
import abbott.ai.tcgm.exception.TCGMUniqueExpectedException;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Oracle Specific implementation of the Dataset Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleDatasetDao extends OracleDao implements DatasetDao
{
	private static Logger myLogger = Logger.getLogger( "ProcessScheduler" );
	private Dataset searchObject = null;
	private String ORDER_BY = " ORDER BY " + DBConst.COL_DATASET_NAME;
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject Dataset object
	 */
	public OracleDatasetDao(UserToken userToken,Dataset searchObject)
	{
		this.setEntityTable(DBConst.TABLE_DATASET);
		this.userToken = userToken;
		this.searchObject = searchObject;
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleDatasetDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_DATASET);
		this.userToken = userToken;
	}

	public OracleDatasetDao(UserToken userToken, Connection conn)
	{
		this.setEntityTable(DBConst.TABLE_DATASET);
		this.userToken = userToken;
		this._conn = conn;
	}

	/**
	 * @param searchObject Dataset
	 */

	private void setSearchObject(Dataset searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}

	/**
	 * @param searchObject Dataset
	 * @param wildcard boolean
	 */
	private void setSearchObject(Dataset searchObject, boolean wildcard)
	{
		this.searchObject = searchObject;
		this.buildSearchList(wildcard);
	}

	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	private RowSet getRS() throws TCGMException
	{
		String methodName = "getRS";

		try
		{
		// Alex Winter 6/17/05 - add condition 'Table_name' to retrive only one record			
//			String query = this.SELECT_ALL + this.getEntity() + this.genWhereClause() +
//			" AND TABLE_NAME = 'UNITS_DATA'" + this.ORDER_BY;   

			// 7/20/05 Removed Alex chg from 6/17/05 because it was causing the Rate Sets not
			// to be displayed on the RateSet page.
			String query = this.SELECT_ALL + this.getEntity() + this.genWhereClause() +
				this.ORDER_BY;   

			myLogger.info("OracleDatasetDao.getRs.query = " + query);
						
			this.initRS(query,TCGMConstants.JDBC_ROWSET);

			rs.execute();
			return rs;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}

	/**
	 *
	 */
	public void buildSearchList()
	{
		this.searchList.clear();
		this.searchList.add(new Search("TRIM("+DBConst.COL_DATASET_NAME+")", this.searchObject.getDatasetName() ) );
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID, this.searchObject.getDatasetTableId() ) );
		this.searchList.add(new Search(DBConst.COL_TABLE_NAME, this.searchObject.getTableName() ) );
		this.searchList.add(new Search(DBConst.COL_DATASET_DESCRIPTION,this.searchObject.getDatasetDesc()));
	}

	/**
	 * @param wildcard boolean
	 */
	public void buildSearchList(boolean wildcard)
	{
		if (wildcard)
		{
			this.searchList.clear();
			this.searchList.add(new Search("TRIM("+DBConst.COL_DATASET_NAME+")", this.searchObject.getDatasetName(), TCGMConstants.ORACLE_LIKE_COMPARISON ) );
			this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID, this.searchObject.getDatasetTableId(), TCGMConstants.ORACLE_LIKE_COMPARISON  ) );
			this.searchList.add(new Search(DBConst.COL_TABLE_NAME, this.searchObject.getTableName(), TCGMConstants.ORACLE_LIKE_COMPARISON ) );
			this.searchList.add(new Search(DBConst.COL_DATASET_DESCRIPTION,this.searchObject.getDatasetDesc(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		}
		else
		{
			buildSearchList();
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param searchObject Dataset object
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getVO(Dataset searchObject) throws TCGMException
	{
		this.setSearchObject(searchObject);
		return getVO();
	}

	/**
	 * @param searchObject Dataset
	 * @param wildcard boolean
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getVO(Dataset searchObject, boolean wildcard) throws TCGMException
	{
		this.setSearchObject(searchObject, wildcard);
		return getVO();
	}
	/**
	 * @return Vector
	 * @throws TCGMException
	 */
	private Vector getVO() throws TCGMException
	{
		String methodName = "getVO";
		Vector vec = new Vector();
		String dataNameLogStamp, createDate = "";
		String modifyDate="";
		try
		{
			this.getRS();
			TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
			long rawOffset = tz.getRawOffset();
			long time;
			long newTime=0;
			Date newDate;
			SimpleDateFormat sdf = new SimpleDateFormat(AppConst.getTcgmDateFormat());
			//if (tz.useDaylightTime())
			if(tz.inDaylightTime(new Date()))
			{
				rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
			}

			while (rs.next())
			{
				Dataset dataset = new Dataset();

				dataset.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
				dataset.setDatasetName(rs.getString(DBConst.COL_DATASET_NAME));
				dataset.setTableName(rs.getString(DBConst.COL_TABLE_NAME));
				dataset.setDatasetDesc(rs.getString(DBConst.COL_DATASET_DESCRIPTION));

				dataset.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
				dataset.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

				dataset.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
				dataset.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
				
				// 01-17-06 List date-time stamp with dataset name
				Timestamp timeStamp = rs.getTimestamp(DBConst.COL_CREATE_DATETIME);
				if (!(timeStamp == null))
				{
					time = timeStamp.getTime();
					dataset.setDatasetCreateDateTime(time);
					newTime = time;
					if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
					{
						newTime += rawOffset;
					}
					newDate = new Date (newTime);
				
					createDate = sdf.format(newDate);
				}
				
				Timestamp timeModifyStamp = rs.getTimestamp(DBConst.COL_MODIFY_DATETIME);
				if (!(timeModifyStamp == null))
				{
					time = timeModifyStamp.getTime();
					newTime = time;
					if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
					{
						newTime += rawOffset;
					}
					newDate = new Date (newTime);
				
					modifyDate = sdf.format(newDate);
				}
				
				dataNameLogStamp = dataset.getDatasetName() + "   " + modifyDate+ "   " + createDate;
				dataset.setDatasetNameLogStamp(dataNameLogStamp);

				vec.add(dataset);
			}
			return vec;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
		}
	}
	/**
	 * This method needs to delete the records in the model_dataset table that have the same dataset_table_id
	 * and then delete the records in the dataset table.
	 * @param datasetId int
	 * @throws TCGMException
	 */
	public void deleteDataset(int datasetId) throws TCGMException
	{
		String methodName = "deleteDataset()";
		String parameterList = "Dataset id: " + datasetId;

		CallableStatement cs = null;
		Connection conn = null;

		// This method should never be called against dataset id "-1" as these system level datasets should never be deleted
		if (datasetId == -1) throw new TCGMException(this.className, methodName, parameterList, "System datasets can not be deleted");

		try
		{
			conn = this.getConnection();
			cs = conn.prepareCall("{ CALL " + this.schema + ".DATASET_DELETE(?) }");
			cs.setInt(1, datasetId);
			cs.execute();
		}
		catch (SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, parameterList, sqle.getMessage() );
		}
		finally
		{
			SQLUtil.closeStatment(cs);
			SQLUtil.closeConnection(conn);
		}
	}
	/**
	 * This method needs to delete the records in the model_dataset table that have the same dataset_table_id
	 * and then delete the records in the dataset table.  It also needs to delete all the records in the
	 * rate data table that have the same dataset table id.  This extra condition is the only reason
	 * I have created this convenience method.  The code is almost identical to the deleteDataset method
	 * with the exception that it must also delete the ratedata records.
	 * @param datasetId int
	 * @throws TCGMException
	 */
	public void deleteRateSet(int datasetId) throws TCGMException
	{
		String methodName = "deleteRateSet()";
		String parameterList = "Dataset id: " + datasetId;
		Connection conn = null;
		Statement stmnt = null;

		String sqlDelRD = "DELETE FROM " + SQLUtil.getDatasourceSchemaName() + "." + DBConst.TABLE_RATE_DATA + " WHERE " + DBConst.COL_DATASET_TABLE_ID + " = " + datasetId;
		String sqlDelRDT = "DELETE FROM " + SQLUtil.getDatasourceSchemaName() + "." + DBConst.TABLE_RATE_DATA_T + " WHERE " + DBConst.COL_DATASET_TABLE_ID + " = " + datasetId;
		String sqlDelMDT = "DELETE FROM " + SQLUtil.getDatasourceSchemaName() + "." + DBConst.TABLE_MODEL_DATASET_TABLE + " WHERE " + DBConst.COL_DATASET_TABLE_ID + " = " + datasetId;
		String sqlDelDT = "DELETE FROM " + getEntity() + " WHERE " + DBConst.COL_DATASET_TABLE_ID + "=" + datasetId;

//		this.logger.debug(sqlDelRD);
//		this.logger.debug(sqlDelRDT);
//		this.logger.debug(sqlDelMDT);
//		this.logger.debug(sqlDelDT);

		try
		{
			conn = getConnection();

			conn.setAutoCommit(false);
			stmnt = conn.createStatement();
			stmnt.executeUpdate(sqlDelRD);
			stmnt.executeUpdate(sqlDelRDT);
			stmnt.executeUpdate(sqlDelMDT);
			stmnt.executeUpdate(sqlDelDT);

			conn.commit();
		}
		catch (SQLException sqle)
		{
			logException(className,methodName,sqle);
			SQLUtil.rollBack(conn);
			throw new TCGMException( this.className,methodName, parameterList, sqle.getMessage() );
		}
		finally
		{
			SQLUtil.enableAutoCommit(conn);
			SQLUtil.closeStatment(stmnt);
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param datasetId id of the dataset
	 * @return Dataset
	 * @throws TCGMException
	 */
	public Dataset getDatasetById(int datasetId) throws TCGMException
	{
		String methodName = "getDatasetById(int datasetId)";
		String parmList = "Dataset Id: " + datasetId;
		Dataset dataset = new Dataset();
		dataset.setDatasetTableIdInt(datasetId);

		Vector v = this.getVO(dataset);
		if (v.size() == 0)
		{
			//throw new TCGMItemNotFoundException(this.className, methodName, parmList);
			Dataset InvalidDataset = new Dataset();
			InvalidDataset.setDatasetTableIdInt(datasetId);
			InvalidDataset.setDatasetTableId(datasetId + "");
			InvalidDataset.setDatasetDesc("<b><font color=red>Invalid</font></b>");
			InvalidDataset.setDatasetName("<b><font color=red>Invalid</font></b>");
			return InvalidDataset;
		}
		else if (v.size() > 1) //7-14-04 bd; temporarily commented out because someone has created duplicate dataset IDs
		//else if (v.size() > 3)
		{
			throw new TCGMUniqueExpectedException(this.className, methodName, parmList);
		}
		else
		{
			return (Dataset) v.firstElement();
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param datasetName name of the dataset
	 * @return Dataset
	 * @throws TCGMException
	 */
	public Dataset getDatasetByName(String datasetName) throws TCGMException
	{
		String methodName = "getDatasetByName(String datasetName)";
		String parmList = "Dataset Name: " + datasetName;
		Dataset dataset = new Dataset();
		dataset.setDatasetName(datasetName);

		Vector vec = this.getVO(dataset);
		if (vec.size() == 0)
		{
			throw new TCGMItemNotFoundException(this.className, methodName, parmList);
		}
		else if (vec.size() > 1)
		{
			throw new TCGMUniqueExpectedException(this.className, methodName, parmList);
		}
		else
		{
			return (Dataset) vec.firstElement();
		}
	}
	// 8-3-05 Fix unit selection when system selects units by unit name; 
	/*****************************************************************************************/
	/**
	 *
	 * @param datasetName name of the dataset
	 * @return Dataset
	 * @throws TCGMException
	 */
	public Dataset getUnitDatasetByName(String datasetName) throws TCGMException
	{
		String methodName = "getDatasetByName(String datasetName)";
		String parmList = "Dataset Name: " + datasetName;
		Dataset dataset = new Dataset();
		dataset.setDatasetName(datasetName);
		
		// 8-3-05 Select Units only
		dataset.setTableName(DBConst.TABLE_UNIT_DATA);

		Vector vec = this.getVO(dataset);
		if (vec.size() == 0)
		{
			throw new TCGMItemNotFoundException(this.className, methodName, parmList);
		}
		else if (vec.size() > 1)
		{
			throw new TCGMUniqueExpectedException(this.className, methodName, parmList);
		}
		else
		{
			return (Dataset) vec.firstElement();
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param tableName String
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getDatasetByTableName(String tableName) throws TCGMException
	{
		String methodName = "getDatasetByTableName(String)";

		Dataset dataset = new Dataset();
		dataset.setTableName(tableName);

		Vector vec = this.getVO(dataset);


		// Probably don't want to throw an error since in this method an empty vector is valid.
	/*
  if(vec.size() == 0) //Error, none found
  {
   throw new TCGMItemNotFoundException("Dataset Table Name: " + tableName);
  }
	*/

		return vec;
	}
	/*****************************************************************************************/
	/**
	 * @return string
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.className);
		sb.append(", \n");
		sb.append("User Token,\n");
		sb.append(this.userToken.toString());
		sb.append("\nEntity: ");
		sb.append(this.getEntity());

		return sb.toString();
	}

	public Dataset createDataset(Dataset dataset, int modelId) throws TCGMException {
		String methodName = "create(Dataset, int modelId)";
		String parmList = "Dataset: " + dataset.toString();
		CallableStatement cs = null;
		Connection conn = null;
		String sql = "{ ? = CALL " + this.schema + ".DATASET_CREATE_FNC(?, ?, ?, ?) }";
		//  P_DATASET_NAME, P_DATASET_DESCRIPTION, P_TABLE_NAME, P_MODEL_ID

		try {
			conn = this.getConnection();
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER );
			cs.setString(2, dataset.getDatasetName() );
			//cs.setString(3, dataset.getDatasetDesc() );
			cs.setString( 3, this.updColDefault(dataset.getDatasetDesc()," "));
			cs.setString(4, dataset.getTableName() );
			cs.setInt(5, modelId);

			cs.execute();

			dataset.setDatasetTableIdInt(cs.getInt(1));
			return dataset;

		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, dataset.toString(), sqle.toString());
		}
		finally
		{
			SQLUtil.closeCS(cs);
			SQLUtil.closeConnection(conn);
		}

	}


	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the DATASET_TABLE_ID
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID				IN TCGM.DATASET_TABLE.DATASET_TABLE_ID%TYPE,
	 * p_DATASET_NAME					IN TCGM.DATASET_TABLE.DATASET_NAME%TYPE,
	 * p_DATASET_DESCRIPTION			IN TCGM.DATASET_TABLE.DATASET_DESCRIPTION%TYPE
	 * @param dataset Dataset object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(Dataset dataset,Connection conn) throws TCGMException
	{
		String methodName = "update(Dataset,Connection)";
		boolean connWasNull = false;

		CallableStatement cs = null;

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}

			String sql = "{ call " + this.schema + ".DATASET_UPDATE(?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt( 1, Integer.parseInt(dataset.getDatasetTableId()));
			cs.setString( 2, dataset.getDatasetName());
			//cs.setString( 3, dataset.getDatasetDesc());
			cs.setString( 3, this.updColDefault(dataset.getDatasetDesc()," "));

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, dataset.toString(), sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException ( className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeCS(cs);
			if(connWasNull)
			{
				//The connection was created within the method and not passed in
				//So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}
	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the DATASET_TABLE_ID
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID				IN TCGM.DATASET_TABLE.DATASET_TABLE_ID%TYPE,
	 * p_DATASET_NAME					IN TCGM.DATASET_TABLE.DATASET_NAME%TYPE,
	 * p_TABLE_NAME						IN TCGM.DATASET_TABLE.TABLE_NAME%TYPE
	 * @param dataset Dataset object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void updateTableName(Dataset dataset,Connection conn) throws TCGMException
	{
		String methodName = "updateTableName(Dataset,Connection)";
		boolean connWasNull = false;

		CallableStatement cs = null;

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}

			String sql = "{ call " + this.schema + ".DATASET_UPDATE_TBL_NAME(?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt( 1, Integer.parseInt(dataset.getDatasetTableId()));
			cs.setString( 2, dataset.getDatasetName());
			//cs.setString( 3, dataset.getDatasetDesc());
			cs.setString( 3, this.updColDefault(dataset.getTableName()," "));

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, dataset.toString(), sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException ( className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeCS(cs);
			if(connWasNull)
			{
				//The connection was created within the method and not passed in
				//So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}

	/**
	 * This method calls a stored procedure to physically insert a record into the DATASET_TABLE
	 * If the connection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method.  Model_Id does
	 * not belong to the dataset table but I need it in order to create the model_dataset record since
	 * every dataset belongs to at least one model.
	 * Order of params to the stored procedure
	 * p_DATASET_NAME
	 * p_DATASET_DESCRIPTION
	 * p_TABLE_NAME
	 * p_RATE_MODEL_ID
	 * @param dataset Dataset object
	 * @param modelId The model id to use when creating the model_dataset record
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
	public int insert(Dataset dataset, int modelId, Connection conn) throws TCGMException
	{
		String methodName = "insert(Dataset,Connection)";
		boolean connWasNull = false;

		CallableStatement cs = null;

		try
		{
			if (this.exists( dataset.getDatasetName() ))
				{
					   String errMsg = "A rate set already exist with the name: " + dataset.getDatasetName();
					   myLogger.error(errMsg);
					   throw new TCGMException( "OracleDatasetDao", methodName, errMsg );
				 }
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}

			String sql = "{ ? = call " + this.schema + ".DATASET_CREATE(?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString( 2, dataset.getDatasetName());
			cs.setString( 3, this.updColDefault(dataset.getDatasetDesc()," "));
			cs.setString( 4, dataset.getTableName());
			cs.setInt( 5, modelId);
			cs.setTimestamp(6, new java.sql.Timestamp(dataset.getDatasetCreateDateTime()));
			cs.execute();
			int dsid = cs.getInt(1);
			//Code to insert data into UNITS_DATA table while saving the units.
			  if(dataset.getSelDatasetid()!=""){
					CallableStatement cStm = null;
				/*	String inserSql = "insert into units_data"
					+" (DATASET_TABLE_ID,END_AFF,END_INV_CD,END_LIST,END_PACK,END_LABEL,END_SIZE,SLS_AFF,SLS_ID,SLS_TYP,"
					+"UNIT_1,UNIT_2,UNIT_3,UNIT_4,UNIT_5,UNIT_6,UNIT_7,UNIT_8,UNIT_9,UNIT_10,UNIT_11,UNIT_12,UNIT_13)"
					+" SELECT ?,END_AFF,END_INV_CD,END_LIST,END_PACK,END_LABEL,END_SIZE,SLS_AFF,SLS_ID,SLS_TYP,"
					+"UNIT_1,UNIT_2,UNIT_3,UNIT_4,UNIT_5,UNIT_6,UNIT_7,UNIT_8,UNIT_9,UNIT_10,UNIT_11,UNIT_12,UNIT_13"
					+" FROM UNITS_DATA WHERE DATASET_TABLE_ID = ?"; 
					
				    PreparedStatement ps = null;
					ps = conn.prepareStatement(inserSql);
					ps.setInt(1,dsid);
				    ps.setString(2,dataset.getSelDatasetid().trim());
					ps.executeQuery();*/
					
				String inserSql = "{ call " + this.schema + ".UNITS_DATA_CREATE(?,?) }";
				
				            cStm = conn.prepareCall(inserSql);
							cStm.setInt( 1, dsid);
							cStm.setString( 2, dataset.getSelDatasetid().trim());
							cStm.execute();
		
			  }
			return dsid;
		}
		catch(SQLException sqle)
		{
			// This indicates that a unique constraint was violated.
			// Throw special exception (TCGMDuplicateItemException) for this situation.
			// Otherwise throw generic exception
			// This shouldln't really happen as currently the only unique key is the datasetid
			// which is generated from a sequence. (JAW 4/17/03)
			if(sqle.toString().indexOf("ORA-00001") >= 0)
			{
				throw new TCGMDuplicateItemException( dataset.toString() );
			}
			else {
				logException(className,methodName,sqle);
				throw new TCGMException( this.className,methodName, dataset.toString(), sqle.toString());
			}
		}
		finally
		{
			SQLUtil.closeCS(cs);
		}
	}

	public boolean exists(int datasetId) throws TCGMException {
		// Poorly performing, inappropriate use of exceptions here...but it works. Fix it if you have time.
		// getDatasetById() probably should return null rather than throw an exception. Originally this method was only used
		// when the dataset id was assumed to exist, and not finding it truly was an exception situation. (JAW)
		try {
			this.getDatasetById(datasetId);
			return true;
		}
		catch (TCGMItemNotFoundException tex) {
			return false;
		}
	}

	/**
	 * Order of params to the stored procedure
	 * p_DATASET_NAME name of the new rateset
	 * p_DATASET_DESCRIPTION description for the new rateset
	 * p_TABLE_NAME table name for the new rateset
	 * p_RATE_MODEL_ID rate model id
	 * p_DUP_FROM_DATASET_TABLE_ID The dataset table id of the rateset to copy
	 * @param dataset Dataset
	 * @param copyFromDatasetTableId int
	 * @param modelId int
	 * @throws TCGMException
	 */
	public void copyRateSet(Dataset dataset,int copyFromDatasetTableId,int modelId) throws TCGMException
	{
		String methodName = "copyRateSet(Dataset,int,int)";

		CallableStatement cs = null;
		Connection conn = null;

		try
		{
			if (this.exists( dataset.getDatasetName() ))
				 {
				   String errMsg = "A rate set already exist with the name: " + dataset.getDatasetName();
				   myLogger.error(errMsg);
				   throw new TCGMException( "OracleDatasetDao", methodName, errMsg );
				 }
			conn = SQLUtil.openConnection();

			String sql = "{ call " + this.schema + ".DATASET_RATE_DUPLICATE(?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			
			if (modelId ==0) modelId =-1; // This is added as the insert to ModelDataset table would fail if modelId is 0;

			cs.setString( 1, dataset.getDatasetName());
			//cs.setString( 2, dataset.getDatasetDesc());
			cs.setString( 2, this.updColDefault(dataset.getDatasetDesc()," "));
			cs.setString( 3, DBConst.TABLE_RATE_DATA);
			cs.setInt( 4, modelId);
			cs.setInt(5, copyFromDatasetTableId);

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			//This indicates that a unique constraint was violated.
			//We do not need to throw this error.  Just ignore it. (DF)
			// Actually you may want to throw a specific duplicate exception (JAW)
			if(sqle.toString().indexOf("ORA-00001") < 0)
			{
				throw new TCGMException( this.className,methodName, dataset.toString(), sqle.toString());
			}
		}
		finally
		{
			SQLUtil.closeCS(cs);
		}
	}
  /**
   * @param modelName
   * @return
   * @throws TCGMException
   */
  public boolean exists(String rateSetName) throws TCGMException
  {
	String methodName = "exists(String)";
	String parameterList = "Rate Set Name is: " + rateSetName;

		Statement stmnt = null;
		Connection conn = null;
		ResultSet rs    = null;
		String sqlQry = "SELECT DATASET_NAME FROM DATASET_TABLE WHERE TABLE_NAME = 'RATE_DATA' AND DATASET_NAME = '"+rateSetName+"'";
		try
			{
				conn = getConnection();
				stmnt = conn.createStatement();
				rs = stmnt.executeQuery(sqlQry);
				
				if(rs.next()){
				return true;
				}else{
				   return false;
				}
									
			}
		catch (SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException( this.className,methodName, parameterList, sqle.getMessage() );
		}
		finally
		{
			SQLUtil.enableAutoCommit(conn);
			SQLUtil.closeStatment(stmnt);
			SQLUtil.closeConnection(conn);
		}
	}

  public HashMap getSalesType() throws TCGMException
	{
		String methodName = "getSalesType()";		

		Connection conn = SQLUtil.openConnection();
		HashMap userDetails=new HashMap();
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		String sql = "select distinct CATEGORY from t_sales_type";
		
		try
		{

			obj_Statement  = conn.createStatement();
			obj_ResultSet = obj_Statement.executeQuery(sql);
			while (obj_ResultSet.next())
			{
				userDetails.put(obj_ResultSet.getString("CATEGORY"),obj_ResultSet.getString("CATEGORY"));
				
			}
			return userDetails;			
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			
				// Error was some other error
			throw new TCGMException (className, methodName, sqle.toString());
			

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (obj_ResultSet != null)
				{
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null)
				{
					obj_Statement.close();
					obj_Statement = null;
				}

				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}
  
  public HashMap getSalesList(String datasetTableId) throws TCGMException
	{
		String methodName = "getSalesList(String datasetTableId)";		

		Connection conn = SQLUtil.openConnection();
		HashMap userDetails=new HashMap();
		Statement obj_Statement = null;
		ResultSet obj_ResultSet = null;
		String sql = "select distinct SLS_ID from UNITS_DATA WHERE DATASET_TABLE_ID="+datasetTableId;
		
		try
		{
			if(null!=datasetTableId && !datasetTableId.equals("")){
				obj_Statement  = conn.createStatement();
				obj_ResultSet = obj_Statement.executeQuery(sql);
				while (obj_ResultSet.next())
				{
					userDetails.put(obj_ResultSet.getString("SLS_ID"),obj_ResultSet.getString("SLS_ID"));
					
				}
			}
			
			return userDetails;			
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			
				// Error was some other error
			throw new TCGMException (className, methodName, sqle.toString());
			

		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			try
			{
				if (obj_ResultSet != null)
				{
					obj_ResultSet.close();
					obj_ResultSet = null;
				}
				if (obj_Statement != null)
				{
					obj_Statement.close();
					obj_Statement = null;
				}

				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (SQLException obj_SQLException)
			{
				obj_SQLException.printStackTrace();
			}
		}
	}
}