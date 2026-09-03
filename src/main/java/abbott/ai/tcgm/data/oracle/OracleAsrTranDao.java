package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Oracle Specific implementation of the AsrTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleAsrTranDao extends OracleDao implements AsrTranDao
{
	private static Logger myLogger = Logger.getLogger( "OracleAsrTranDao" );
	private AsrTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_ASR_TRAN;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID, " +
									  "ACD, PROD_ORIGIN, RPT_AFF, RPT_INV_CD, " +
									  "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, " +
									  "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,SUP_KEY, USAGE_FAC, " +
									  "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME, " +
									  "PUBLISH_FLAG, ASR_T_ID " +
									  "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject AsrTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleAsrTranDao(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_ASR_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject AsrTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleAsrTranDao(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_ASR_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject AsrTran object
	 */
	public OracleAsrTranDao(UserToken userToken,AsrTran searchObject)
	{
		this.setEntityTable(DBConst.TABLE_ASR_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleAsrTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_ASR_T);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject AsrTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AsrTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(AsrTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject AsrTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AsrTran searchObject) throws TCGMException
	{
		String methodName = "getRS(AsrTran)";
		this.setSearchObject(searchObject);
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException
	{
		String methodName = "getRS()";
		if (this.sortObject.getSortColumn().equalsIgnoreCase(DBConst.COL_DEF))
		{
			this.sortObject.setSortColumn(DBConst.COL_ASR_DEF);
		}

		try
		{
			String query = this.MIDDLE_SELECT_START +
				  this.INNER_SELECT +
				  this.getEntity() +
				  this.genWhereClause() +
				  this.buildEBCDICSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + pagingFilter.getEndRecord();
			}

			this.logger.debug("OraceAsrTranDao - QUERY: " + query);

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
	/*****************************************************************************************/
	/**
	 * @param searchObject AsrTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(AsrTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(AsrTran, Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject AsrTran object with search criteria
	 * @return vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(AsrTran searchObject) throws TCGMException
	{
		String methodName = "getVO(AsrTran)";
		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException
	{
		String methodName = "getVO";
		Vector vec = new Vector();

		try
		{
			this.getRS();

			while (rs.next())
			{
				vec.add(this.getAsrTranFromCurrentRow(rs));
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
	/*****************************************************************************************/
	/**
	 * @param rs RowSet
	 * @return AsrTran
	 * @throws TCGMException
	 */
	private AsrTran getAsrTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getAsrTranFromCurrentRow(RowSet)";
		AsrTran asrTran = new AsrTran();

		try
		{
			asrTran.getAsr().setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			asrTran.getAsr().setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			asrTran.setActionCode(rs.getString(DBConst.COL_ACD));
			asrTran.getAsr().setProductOrigin(rs.getString(DBConst.COL_PROD_ORIGIN));
			asrTran.getAsr().setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			asrTran.getAsr().setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			asrTran.getAsr().setSupKey(rs.getString(DBConst.COL_SUP_KEY));
			asrTran.getAsr().setUsage(rs.getString(DBConst.COL_USAGE_FAC));
			asrTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));
			asrTran.setAsrTranId(rs.getString(DBConst.COL_ASR_T_ID));

			asrTran.getAsr().getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			asrTran.getAsr().getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			asrTran.getAsr().getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			asrTran.getAsr().getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			asrTran.getAsr().getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			asrTran.getAsr().getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			asrTran.getAsr().getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			asrTran.getAsr().getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			asrTran.getAsr().getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			asrTran.getAsr().getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			asrTran.getAsr().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			asrTran.getAsr().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			asrTran.getAsr().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			asrTran.getAsr().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return asrTran;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className,methodName,e.toString());
		}
	}
	/*****************************************************************************************/
	/**
	 * This method will loop through the vector that is passed in and insert each object
	 * in the vector into the Asr_T table.  The connection is created/closed internally.
	 * @param asrTranList Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public boolean insert(Vector asrTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector)";
        boolean result = true;
        boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < asrTranList.size();i++)
			{
				AsrTran at =(AsrTran)asrTranList.elementAt(i);
				result = this.insert(at,conn);
			// A.Winter - check for duplicate transaction

				if(!result){
					blnFlag = false;
					at.getAsr().setMsg("Duplicate Row");
				}
				if(at.getAsrTranId().equals("DUP"))
				 {
				     blnFlag = false;
//				 	 break;
				  }
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
		// Returning blnFlag (as false) if at least one duplicate Record Exists
	return blnFlag;
	}
	/**
	 * This method calls a stored procedure to physically insert a record into the Asr_T table
	 * If the connection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method.
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_PROD_ORIGIN
	 * p_RPT_AFF
	 * p_RPT_INV_CD
	 * p_RPT_LIST
	 * p_RPT_LABEL
	 * p_RPT_SIZE
	 * p_RPT_PACK
	 * p_SUP_AFF
	 * p_SUP_INV_CD
	 * p_SUP_LIST
	 * p_SUP_LABEL
	 * p_SUP_SIZE
	 * p_SUP_PACK
	 * p_SUP_KEY
	 * p_USAGE_FAC
	 * p_PUBLISH_FLAG
	 * @param asrTran AsrTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
	public boolean insert(AsrTran asrTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(AsrTran,Connection)";
		boolean connWasNull = false;
		boolean duplic = true;

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
			int intResultCode = 0;
			//String sql = "{ call " + schema + ".ASR_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + schema + ".APPLY_MAINTENANCE.BUILD_ASR_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(asrTran.getAsr().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(asrTran.getAsr().getModelId()));
			cs.setString( 3, asrTran.getActionCode());
			cs.setString( 4, this.updColDefault(asrTran.getAsr().getProductOrigin()," "));
			cs.setString( 5, asrTran.getAsr().getRptAff());
			cs.setString( 6, asrTran.getAsr().getRptProduct().getInvCode());
			cs.setString( 7, asrTran.getAsr().getRptProduct().getList());
			cs.setString( 8, this.updColDefault(asrTran.getAsr().getRptProduct().getLabel()," "));
			cs.setString( 9, this.updColDefault(asrTran.getAsr().getRptProduct().getSize()," "));
			cs.setString( 10, asrTran.getAsr().getRptProduct().getPack());
			cs.setString( 11, asrTran.getAsr().getSupAff());
			cs.setString( 12, asrTran.getAsr().getSupProduct().getInvCode());
			cs.setString( 13, asrTran.getAsr().getSupProduct().getList());
			cs.setString( 14, this.updColDefault(asrTran.getAsr().getSupProduct().getLabel()," "));
			cs.setString( 15, this.updColDefault(asrTran.getAsr().getSupProduct().getSize()," "));
			cs.setString( 16, asrTran.getAsr().getSupProduct().getPack() );
			cs.setString( 17, this.updColDefault(asrTran.getAsr().getSupKey()," ") );
			cs.setDouble( 18, Double.parseDouble(asrTran.getAsr().getUsage()) );
			cs.setString( 19, asrTran.getPublishFlag() );
			cs.setString( 20, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.registerOutParameter(22, Types.NUMERIC);
			cs.execute();
			intResultCode = cs.getInt(22);
			if(intResultCode==1){
				duplic = false;
				asrTran.getAsr().setMsg("Duplicate Row");
			}
			myLogger.debug("value of the ResultCode = "+intResultCode);
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);

			myLogger.error(sqle.toString() );
			myLogger.debug( "-- Parameters -- ");
			myLogger.error( "Table ID: " +Integer.parseInt(asrTran.getAsr().getDatasetTableId()));
			myLogger.error( "Model ID: " +Integer.parseInt(asrTran.getAsr().getModelId()));
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				asrTran.getAsr().setMsg("Duplicate Row");
				asrTran.setAsrTranId("DUP");
				duplic = false;
			}
			else if(sqle.getMessage().startsWith("ORA-20000"))
			{
				// 04/07/2006 Looping Error happened.
				asrTran.getAsr().setMsg("Looping Error");
				duplic = false;
			}
			else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}
		}
//		catch(Exception e)
//		{
//			logException(className,methodName,e);
//			throw new TCGMException ( className,methodName,e.toString());
//		}
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
		return duplic;
	}
	/**
	 * This method calls a stored procedure to physically insert a records into the Asr_T table
	 * If the connection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method.
	 * Order of params to the stored procedure
	 * P_ASR_TRAN
	 * P_USERNAME
	 * @param vector of asrTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
	public Vector insert(Vector asrTransList,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector,Connection)";
		boolean connWasNull = false;
		boolean duplic = true;
		Vector asrTranErrorList = null;

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
			int intResultCode = 0;
			String sql = "{ call " + schema + ".ASR_TRAN_CREATE_PV(?,?) }";

			cs = conn.prepareCall(sql);
			ArrayDescriptor des = ArrayDescriptor.createDescriptor("T_ASR_TRAN", conn);
			ARRAY a = new ARRAY(des, conn, asrTransList);
			cs.setObject( 1, (Object)a);
			cs.setString( 2, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			//cs.registerOutParameter(1, Types.JAVA_OBJECT,"Asr_Tran_Pkg.T_ASR_TRAN");
			cs.execute();
			//asrTransList =(Vector)cs.getObject(1);

			for(int j=0;j<asrTransList.size();j++){
				String strMessage = ((AsrTran)asrTransList.elementAt(j)).getAsr().getMsg();
				if(!strMessage.equalsIgnoreCase("")) {
					asrTranErrorList.add((AsrTran)asrTransList.elementAt(j));
				}
			}

			myLogger.debug("Size of the return Vector = "+asrTranErrorList.size());
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);

			myLogger.error(sqle.toString() );
			myLogger.debug( "-- Parameters -- ");
			//myLogger.error( "Table ID: " +Integer.parseInt(asrTran.getAsr().getDatasetTableId()));
			//myLogger.error( "Model ID: " +Integer.parseInt(asrTran.getAsr().getModelId()));
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				throw new TCGMException (className, methodName, sqle.toString());
			}
			else if(sqle.getMessage().startsWith("ORA-20000"))
			{
				throw new TCGMException (className, methodName, sqle.toString());
			}
			else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}
		}
//		catch(Exception e)
//		{
//			logException(className,methodName,e);
//			throw new TCGMException ( className,methodName,e.toString());
//		}
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
		return asrTranErrorList;
	}
	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the AsrTranId
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_PROD_ORIGIN
	 * p_RPT_AFF
	 * p_RPT_INV_CD
	 * p_RPT_LIST
	 * p_RPT_LABEL
	 * p_RPT_SIZE
	 * p_RPT_PACK
	 * p_SUP_AFF
	 * p_SUP_INV_CD
	 * p_SUP_LIST
	 * p_SUP_LABEL
	 * p_SUP_SIZE
	 * p_SUP_PACK
	 * p_SUP_KEY
	 * p_USAGE_FAC
	 * p_PUBLISH_FLAG
	 * p_ASR_T_ID
	 * @param asrTran AsrTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(AsrTran asrTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(AsrTran)";
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

			//String sql = "{ call " + this.schema + ".ASR_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_ASR_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt( 1, Integer.parseInt(asrTran.getAsr().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(asrTran.getAsr().getModelId()));
			cs.setString( 3, asrTran.getActionCode());
			cs.setString( 4, this.updColDefault(asrTran.getAsr().getProductOrigin()," "));
			cs.setString( 5, asrTran.getAsr().getRptAff());
			cs.setString( 6, asrTran.getAsr().getRptProduct().getInvCode());
			cs.setString( 7, asrTran.getAsr().getRptProduct().getList());
			cs.setString( 8, this.updColDefault(asrTran.getAsr().getRptProduct().getLabel()," "));
			cs.setString( 9, this.updColDefault(asrTran.getAsr().getRptProduct().getSize()," "));
			cs.setString( 10, asrTran.getAsr().getRptProduct().getPack());
			cs.setString( 11, asrTran.getAsr().getSupAff());
			cs.setString( 12, asrTran.getAsr().getSupProduct().getInvCode());
			cs.setString( 13, asrTran.getAsr().getSupProduct().getList());
			cs.setString( 14, this.updColDefault(asrTran.getAsr().getSupProduct().getLabel()," "));
			cs.setString( 15, this.updColDefault(asrTran.getAsr().getSupProduct().getSize()," "));
			cs.setString( 16, asrTran.getAsr().getSupProduct().getPack());
			cs.setString( 17, this.updColDefault(asrTran.getAsr().getSupKey()," "));
			cs.setDouble( 18, Double.parseDouble(asrTran.getAsr().getUsage()));
			cs.setString( 19, asrTran.getPublishFlag());
			cs.setString( 20, this.updColDefault(this.userToken.getUserid(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid(),"Anonymous") );
			cs.setString( 22, asrTran.getAsrTranId());

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.toString().indexOf("ORA-00001") > 0)
			{
				// Error was a unique constraint error
				throw new TCGMDuplicateItemException(sqle.toString());
			}
			else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}
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
	 * This method will loop through the given vector and update each AsrTran object in the collection
	 * based on the AsrTranId
	 * The connection is created internally
	 * @param asrTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector asrTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < asrTranList.size();i++)
			{
				this.update((AsrTran)asrTranList.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @throws TCGMException
	 */
	public void publishAll(AsrTran asrTran, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";
		
		String sql = "";
		if(blnFlag){
			sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();
		}else{
			sql = this.UPDATE + this.getEntity() + this.SET_UNPUBLISHED + this.genWhereClause();
		}
		
		this.logger.debug("SQL: " + sql);

		this.setSearchObject(asrTran);
		Search search;
		Iterator item = searchList.iterator();
		while (item.hasNext())
		{
			search = (Search)item.next();
			if((search.getColumnName().equals(DBConst.COL_CREATE_USERNAME)) &&
			   (search.getValue().equals("") || (search.getValue().equals(null))) )
			{
				//03/22/2006 -- Udaya B Aravapalli
				//If the user selected is 'ALL' we will Publish the records
				//on the name of the current logged in user.
				search.setValue(this.userToken.getUserid());
			}
		}

		PreparedStatement ps = null;
		Connection conn = SQLUtil.openConnection();

		try
		{
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closePS(ps);
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * 1.  Get the rowset
	 * 2.  Get the asr object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject AsrTran
	 * @param newVals AsrTran
	 * @throws TCGMException
	 */
	public void massUpdate(AsrTran searchObject,AsrTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(AsrTran,AsrTran)";

		RowSet rs = this.getRS(searchObject);

		try
		{
			while(rs.next())
			{
				AsrTran asrTran = this.getAsrTranFromCurrentRow(rs);

				asrTran.setActionCode(this.updCol(asrTran.getActionCode(),newVals.getActionCode()));
				asrTran.setPublishFlag(this.updCol(asrTran.getPublishFlag(),newVals.getPublishFlag()));
				asrTran.getAsr().setProductOrigin(this.updCol(asrTran.getAsr().getProductOrigin(),newVals.getAsr().getProductOrigin()));
				asrTran.getAsr().setRptAff(this.updCol(asrTran.getAsr().getRptAff(),newVals.getAsr().getRptAff()));
				asrTran.getAsr().getRptProduct().setInvCode(this.updCol(asrTran.getAsr().getRptProduct().getInvCode(),newVals.getAsr().getRptProduct().getInvCode()));
				asrTran.getAsr().getRptProduct().setList(this.updCol(asrTran.getAsr().getRptProduct().getList(),newVals.getAsr().getRptProduct().getList()));
				asrTran.getAsr().getRptProduct().setLabel(this.updCol(asrTran.getAsr().getRptProduct().getLabel(),newVals.getAsr().getRptProduct().getLabel()));
				asrTran.getAsr().getRptProduct().setSize(this.updCol(asrTran.getAsr().getRptProduct().getSize(),newVals.getAsr().getRptProduct().getSize()));
				asrTran.getAsr().getRptProduct().setPack(this.updCol(asrTran.getAsr().getRptProduct().getPack(),newVals.getAsr().getRptProduct().getPack()));
				asrTran.getAsr().setSupAff(this.updCol(asrTran.getAsr().getSupAff(),newVals.getAsr().getSupAff()));
				asrTran.getAsr().setSupKey(this.updCol(asrTran.getAsr().getSupKey(),newVals.getAsr().getSupKey()));
				asrTran.getAsr().getSupProduct().setInvCode(this.updCol(asrTran.getAsr().getSupProduct().getInvCode(),newVals.getAsr().getSupProduct().getInvCode()));
				asrTran.getAsr().getSupProduct().setList(this.updCol(asrTran.getAsr().getSupProduct().getList(),newVals.getAsr().getSupProduct().getList()));
				asrTran.getAsr().getSupProduct().setLabel(this.updCol(asrTran.getAsr().getSupProduct().getLabel(),newVals.getAsr().getSupProduct().getLabel()));
				asrTran.getAsr().getSupProduct().setSize(this.updCol(asrTran.getAsr().getSupProduct().getSize(),newVals.getAsr().getSupProduct().getSize()));
				asrTran.getAsr().getSupProduct().setPack(this.updCol(asrTran.getAsr().getSupProduct().getPack(),newVals.getAsr().getSupProduct().getPack()));
				asrTran.getAsr().setUsage(this.updCol(asrTran.getAsr().getUsage(),newVals.getAsr().getUsage()));

				this.update(asrTran,this.getConnection());
			}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		finally
		{
			SQLUtil.closeConnection(this._conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * @param asrTran AsrTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(AsrTran asrTran,Connection conn) throws TCGMException,
															   TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(AsrTran,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(asrTran);
// 8-21-03 I just set the search list so I can check it here for username and throw an exception.
// traverse he searchList (its a vector) looking for a "columnName" of CREATE_USERNAME; If I find it
// and the "value" = ""; throw an exception.
		Search search;
		Iterator item = searchList.iterator();
		while (item.hasNext())
		{
			search = (Search)item.next();
			if((search.getColumnName().equals(DBConst.COL_CREATE_USERNAME)) &&
			   (search.getValue().equals("") || (search.getValue().equals(null))) )
			{
				//03/22/2006 -- Udaya B Aravapalli
				//If the user selected is 'ALL' we will delete all the records
				//on the name of the current logged in user.
				search.setValue(null);
//				throw new TCGMUpdateWithBlankUsernameException("* Username Cannot Be Blank When Updating Records *");
			}
		}
		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains an asrTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(asrTran.getAsrTranId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			sql += " where ASR_T_ID = " + asrTran.getAsrTranId();
		}

		if (asrTran.getAsr().getCreateLog().getUserName().equalsIgnoreCase("ALL")){

					int andIndex =sql.lastIndexOf("AND");
					sql=sql.substring(0,andIndex);

				}
		this.logger.debug("\nOracleAsrTran.delete(AsrTran, Conn) - SQL: " + sql + " <<<DateStamp: " + new java.util.Date() + ">>>");

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}

			ps = conn.prepareStatement(sql);
			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className, methodName, e.toString());
		}
		finally
		{
			SQLUtil.closePS(ps);
			if(connWasNull)
			{
				//The connection was created within the method and not passed in
				//So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}
	/**
	 * @param asrTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector asrTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < asrTranList.size();i++)
			{
				this.delete((AsrTran)asrTranList.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}

	/*****************************************************************************************/
	/**
	 * Builds the vector of Search objects to be used by the genWhereClause method.
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getAsr().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getAsr().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode(),comparisonType(searchObject.getActionCode())));
		this.searchList.add(new Search(DBConst.COL_PROD_ORIGIN,searchObject.getAsr().getProductOrigin(),comparisonType(searchObject.getAsr().getProductOrigin())));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getAsr().getRptAff(),comparisonType(searchObject.getAsr().getRptAff())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getAsr().getRptProduct().getInvCode(),comparisonType(searchObject.getAsr().getRptProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getAsr().getRptProduct().getList(),comparisonType(searchObject.getAsr().getRptProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getAsr().getRptProduct().getLabel(),comparisonType(searchObject.getAsr().getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getAsr().getRptProduct().getSize(),comparisonType(searchObject.getAsr().getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getAsr().getRptProduct().getPack(),comparisonType(searchObject.getAsr().getRptProduct().getPack())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getAsr().getSupAff(),comparisonType(searchObject.getAsr().getSupAff())));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getAsr().getSupProduct().getInvCode(),comparisonType(searchObject.getAsr().getSupProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getAsr().getSupProduct().getList(),comparisonType(searchObject.getAsr().getSupProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getAsr().getSupProduct().getLabel(),comparisonType(searchObject.getAsr().getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getAsr().getSupProduct().getSize(),comparisonType(searchObject.getAsr().getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getAsr().getSupProduct().getPack(),comparisonType(searchObject.getAsr().getSupProduct().getPack())));
		this.searchList.add(new Search(DBConst.COL_SUP_KEY,searchObject.getAsr().getSupKey(),comparisonType(searchObject.getAsr().getSupKey())));
		this.searchList.add(new Search(DBConst.COL_USAGE_FAC,TCGMUtil.getNumTrimLeadZero(searchObject.getAsr().getUsage()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getAsr().getUsage()))));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag(),comparisonType(searchObject.getPublishFlag())));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getAsr().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
	}
	/*****************************************************************************************/
		/**
		 * Builds the vector of Search objects to be used by the genWhereClause method.
		 */
		private void buildAdvancedSearchList()
		{
			this.searchList = new Vector();

			//need to build a search object and then loop through it to get the clause.
			this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getAsr().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
			this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getAsr().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
			this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_PROD_ORIGIN,searchObject.getAsr().getProductOrigin(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getAsr().getRptAff(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getAsr().getRptProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getAsr().getRptProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getAsr().getRptProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getAsr().getRptProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getAsr().getRptProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));

			this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getAsr().getSupAff(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getAsr().getSupProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getAsr().getSupProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getAsr().getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getAsr().getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getAsr().getSupProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_SUP_KEY,searchObject.getAsr().getSupKey(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_USAGE_FAC,searchObject.getAsr().getUsage(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getAsr().getCreateLog().getUserName(),TCGMConstants.ORACLE_IN_COMPARISON));
		}
	/**
	 *
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

		sb.append("\n");
		sb.append(this.pagingFilter.toString());

		sb.append("\n");
		sb.append(this.sortObject.toString());

		sb.append("\nSearch Object: ");
		sb.append(this.searchObject.toString());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 * @param asrTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector asrTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(Vector,String)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < asrTranList.size();i++)
			{
				AsrTran asrTran = (AsrTran)asrTranList.elementAt(i);
				asrTran.getAsr().setModelId(copyToModel);
				this.insert(asrTran,conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}
	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(AsrTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(AsrTran,String)";

		this.setSearchObject(searchObject);

		Vector vec = new Vector();
		Connection conn = null;

		try
		{
			this.getRS();

			conn = SQLUtil.openConnection( );

			while (rs.next())
			{
				AsrTran asrTran = this.getAsrTranFromCurrentRow(rs);
				asrTran.getAsr().setModelId(copyToModel);
				this.insert(asrTran,conn);
			}
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.toString().indexOf("ORA-00001") > 0)
			{
				// Error was a unique constraint error
				throw new TCGMDuplicateItemException(sqle.toString());
			}
			else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}
		}
//		catch(Exception e)
//		{
//			logException(className,methodName,e);
//			throw new TCGMException(this.className,methodName,e.toString());
//		}
		finally
		{
			SQLUtil.closeRowSet(rs);
			SQLUtil.closeConnection(conn);
		}
	}
	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject AsrTran
	 */
	private void setSearchObject(AsrTran searchObject)
	{
		this.searchObject = searchObject;
		if(searchObject.isTranAdvFilter()){
			this.buildAdvancedSearchList();
		}else{
			this.buildSearchList();
		}
// 8-21-03 Upon return from buildSearchList(); I should check for UserName here or check where
// 		   setSearchObject got called.
	}
	/**
	 *
	 * @return SearchObject
	 */
	private AsrTran getSearchObject()
	{
		return this.searchObject;
	}
}