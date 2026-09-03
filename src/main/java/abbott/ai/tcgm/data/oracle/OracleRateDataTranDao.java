package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;

import org.apache.log4j.Logger;

import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleRateDataTranDao extends OracleDao implements RateDataTranDao
{
	private static Logger myLogger = Logger.getLogger( "OracleRateDataTranDao" );
	private RateDataTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_RATE_DATA_TRAN;
	private final String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,ACD,CUR_CD, " +
							   "BEG_PERIOD,END_PERIOD,RATE,PUBLISH_FLAG, RATE_DATA_T_ID, " +
							   "RATE1,RATE2,RATE3,RATE4,RATE5,RATE6,RATE7, "+
							   "RATE8,RATE9,RATE10,RATE11,RATE12,RATE13, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateDataTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleRateDataTranDao(UserToken userToken,RateDataTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA_T);
		this.setEntityView(DBConst.VW_RATE_DATA_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateDataTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleRateDataTranDao(UserToken userToken,RateDataTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA_T);
		this.setEntityView(DBConst.VW_RATE_DATA_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateDataTran object
	 */
	public OracleRateDataTranDao(UserToken userToken,RateDataTran searchObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA_T);
		this.setEntityView(DBConst.VW_RATE_DATA_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleRateDataTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA_T);
		this.setEntityView(DBConst.VW_RATE_DATA_T);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateDataTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateDataTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(RateDataTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateDataTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateDataTran searchObject) throws TCGMException
	{
		String methodName = "getRS(RateDataTran)";
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

		try
		{
			String query = this.MIDDLE_SELECT_START +
				  this.INNER_SELECT +
				  this.getEntityView() +
				  this.genWhereClause() +
				  this.buildSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + this.pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + this.pagingFilter.getEndRecord();
			}

			this.logger.debug("\nOracleRateDataTranDao - QUERY: " + query);

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
	 * @param searchObject RateDataTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateDataTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(RateDataTran,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject RateDataTran object with search criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateDataTran searchObject) throws TCGMException
	{
		String methodName="getVO(RateDataTran)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return Vector
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
				vec.add(this.getRateDataTranFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an RateData object
	 * @param rs RowSet
	 * @return RateData
	 * @throws TCGMException
	 */
	public RateDataTran getRateDataTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getRateDataTranFromCurrentRow(RowSet)";

		RateDataTran rateDataTran = new RateDataTran();

		try
		{
			rateDataTran.getRateData().setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			//rateDataTran.getRateData().setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			rateDataTran.getRateData().setCurCode(rs.getString(DBConst.COL_CUR_CD));

			rateDataTran.getRateData().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			rateDataTran.getRateData().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			rateDataTran.getRateData().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			rateDataTran.getRateData().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateDataTran.getRateData().setRates(i-1,new Period(rs.getString("RATE" + i)));
			}

			rateDataTran.setActionCode(rs.getString(DBConst.COL_ACD));
			rateDataTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));

			rateDataTran.getRateData().setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			rateDataTran.getRateData().setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));
			rateDataTran.getRateData().setRate(rs.getString(DBConst.COL_RATE));

			rateDataTran.setRateDataTranId(rs.getString(DBConst.COL_RATE_DATA_T_ID));

			return rateDataTran;
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
	 * in the vector into the Exceptions_T table.  The connection is created/closed internally.
	 * @param rateDataTranList Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public boolean insert(Vector rateDataTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector)";
		boolean result = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateDataTranList.size();i++)
			{
				RateDataTran rdt =(RateDataTran)rateDataTranList.elementAt(i);
				result = this.insert((RateDataTran)rateDataTranList.elementAt(i),conn);
				if(!result){
					blnFlag = false;
					rdt.getRateData().setMsg("Duplicate Row");
				}
				if(rdt.getRateDataTranId().equals("DUP"))
				 {
					 blnFlag = false;
				  }				
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
		return blnFlag;		
	}
	/**
	 * This method calls a stored procedure to physically insert a record into the Exceptions_T table
	 * If the connection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method.
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_ACD
	 * p_PUBLISH_FLAG
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_RATE
	 * p_CUR_CD
	 * @param rateDataTran RateDataTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
	public boolean insert(RateDataTran rateDataTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(RateDataTran,Connection)";
		boolean connWasNull = false;
		boolean duplic = true;
		CallableStatement cs = null;

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				connWasNull = true;
			}
			int intResultCode = 0;
			String sql = "{ call " + this.schema + ".RATE_DATA_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			myLogger.debug("Dataset ID = " + rateDataTran.getRateData().getDatasetTableId().toString()	);
			//myLogger.debug("Dataset ID = " + rateDataTran.getDatasetTableId().toString()	);
			cs.setInt(1, Integer.parseInt(rateDataTran.getRateData().getDatasetTableId()));

			//cs.setInt(1, rateDataTran.getRateData().getDatasetTableInt());

			//cs.setInt(2, Integer.parseInt(rateDataTran.getRateData().getModelId()));
			cs.setString(2, rateDataTran.getActionCode());
			cs.setString(3, this.updColDefault(rateDataTran.getRateData().getCurCode().trim()," "));
			cs.setInt(4,Integer.parseInt(this.updColDefault(rateDataTran.getRateData().getBegPeriod().trim(),"1")));
			cs.setInt(5,Integer.parseInt(this.updColDefault(rateDataTran.getRateData().getEndPeriod().trim(),"12")));
			cs.setDouble(6,Double.parseDouble(this.updColDefault(rateDataTran.getRateData().getRate().trim(),"0.0")));
			cs.setString(7, rateDataTran.getPublishFlag());
			cs.setString(8, this.updCol(this.updColDefault(this.userToken.getUserid().trim(),"Anonymous"),rateDataTran.getUserName()) );			
			cs.setString(9, this.updCol(this.updColDefault(this.userToken.getUserid().trim(),"Anonymous"),rateDataTran.getUserName()) );	
			cs.registerOutParameter(10, Types.NUMERIC);		
			cs.execute();
			intResultCode = cs.getInt(10);
			if(intResultCode==1){
				duplic = false;
				rateDataTran.getRateData().setMsg("Duplicate Row");
			}			
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.toString().indexOf("ORA-00001") > 0)
			{
				rateDataTran.getRateData().setMsg("Duplicate Row");
				rateDataTran.setRateDataTranId("DUP");
				duplic = false;
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
	 return duplic;	
	}
	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the RateDataTranId
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_ACD
	 * p_PUBLISH_FLAG
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_RATE
	 * p_CUR_CD
	 * p_RATE_DATA_T_ID
	 * @param rateDataTran RateDataTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(RateDataTran rateDataTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(RateDataTran)";
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

			String sql = "{ call " + this.schema + ".RATE_DATA_TRAN_UPDATE(?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt(1, Integer.parseInt(rateDataTran.getRateData().getDatasetTableId()));
			//cs.setInt(2, Integer.parseInt(rateDataTran.getRateData().getModelId()));
			cs.setString(2, rateDataTran.getActionCode());
			cs.setString(3, this.updColDefault(rateDataTran.getRateData().getCurCode().trim()," "));

			cs.setInt(4,Integer.parseInt(this.updColDefault(rateDataTran.getRateData().getBegPeriod().trim(),"1")));
			cs.setInt(5,Integer.parseInt(this.updColDefault(rateDataTran.getRateData().getEndPeriod().trim(),"12")));
			cs.setDouble(6,Double.parseDouble(this.updColDefault(rateDataTran.getRateData().getRate().trim(),"0.0")));
			cs.setString(7, rateDataTran.getPublishFlag());
			cs.setString(8, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString(9,rateDataTran.getRateDataTranId());

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
	 * This method will loop through the given vector and update each RateDataTran object in the collection
	 * based on the RateDataTranId
	 * The connection is created internally
	 * @param rateDataTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector rateDataTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateDataTranList.size();i++)
			{
				this.update((RateDataTran)rateDataTranList.elementAt(i),conn);
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
	public void publishAll(RateDataTran rateDataTran) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";

		String sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();

		this.logger.debug("SQL: " + sql);

		this.setSearchObject(rateDataTran);
		Search search;
		Iterator item = searchList.iterator();
		while (item.hasNext())
		{
			search = (Search)item.next();
			if((search.getColumnName().equals(DBConst.COL_CREATE_USERNAME)) &&
			   (search.getValue().equals("") || (search.getValue().equals(null))) )
			{
				//04/27/2006 -- Udaya B Aravapalli
				//If the user selected is 'ALL' we will Publish the records
				//on the name of the current logged in user.
				search.setValue(this.userToken.getUserid());			//logException(className,methodName,ex);
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
	 * 2.  Get the rateData object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject RateDataTran
	 * @param newVals RateDataTran
	 * @throws TCGMException
	 */
	public void massUpdate(RateDataTran searchObject,RateDataTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(RateDataTran,RateDataTran)";

		RowSet rs = this.getRS(searchObject);

		try
		{
			int count = 1;
			while(rs.next())
			{
				RateDataTran rateDataTran = this.getRateDataTranFromCurrentRow(rs);

				rateDataTran.setActionCode(this.updCol(rateDataTran.getActionCode(),newVals.getActionCode()));
				rateDataTran.setPublishFlag(this.updCol(rateDataTran.getPublishFlag(),newVals.getPublishFlag()));

				rateDataTran.getRateData().setBegPeriod(this.updCol(rateDataTran.getRateData().getBegPeriod(),newVals.getRateData().getBegPeriod()));
				rateDataTran.getRateData().setEndPeriod(this.updCol(rateDataTran.getRateData().getEndPeriod(),newVals.getRateData().getEndPeriod()));
				rateDataTran.getRateData().setRate(this.updCol(rateDataTran.getRateData().getRate(),newVals.getRateData().getRate()));
				rateDataTran.getRateData().setCurCode(this.updCol(rateDataTran.getRateData().getCurCode(),newVals.getRateData().getCurCode()));
//				rateDataTran.setUserName(this.updCol(this.userToken.getUserid().trim(),rateDataTran.getRateData().getCreateLog().getUserName()));
				this.update(rateDataTran,this.getConnection());
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
	 * Deletes an individual record
	 * @param rateDataTran RateDataTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(RateDataTran rateDataTran,Connection conn) throws TCGMException,
																		 TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(RateDataTran,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(rateDataTran);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains a rateDataTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(rateDataTran.getRateDataTranId().equals(""))
		{
			sql += this.genWhereClause();
			if (rateDataTran.getRateData().getCreateLog().getUserName().equalsIgnoreCase("ALL"))
			{
				int andIndex =sql.lastIndexOf("AND");
				sql=sql.substring(0,andIndex);
			}
		}
		else
		{
			sql += " where RATE_DATA_T_ID = ? ";
		}
		this.logger.debug("\nSQL: " + sql);

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

			if(! rateDataTran.getRateDataTranId().equals(""))
			{
				ps.setLong(1,Long.parseLong(rateDataTran.getRateDataTranId()));
			}

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
	 * @param rateDataTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector rateDataTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateDataTranList.size();i++)
			{
				this.delete((RateDataTran)rateDataTranList.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}

	/*****************************************************************************************/
	/**
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		//this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getRateData().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getRateData().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));

		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBegPeriod().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getEndPeriod().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RATE,TCGMUtil.getNumTrimLeadZero(searchObject.getRate()),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CUR_CD,searchObject.getRateData().getCurCode().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_RATE_DATA_T_ID,searchObject.getRateDataTranId().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getRateData().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
	}

	/*****************************************************************************************/
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
		sb.append("\nEntity View: ");
		sb.append(this.getEntityView());
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
	 * @param rateDataTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
// 4-21-03 Copy function Not Applicable in Rate Data
//	public void copy(Vector rateDataTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
//	{
//		String methodName = "copy(Vector,String)";
//
//		Connection conn = null;
//		try
//		{
//			conn = SQLUtil.openConnection( this.userToken );
//			for(int i = 0; i < rateDataTranList.size();i++)
//			{
//				RateDataTran rateDataTran = (RateDataTran)rateDataTranList.elementAt(i);
//				rateDataTran.getRateData().setModelId(copyToModel);
//				this.insert(rateDataTran,conn);
//			}
//		}
//		finally
//		{
//			SQLUtil.closeConnection(conn);
//		}
//	}
	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
// 4-21-03 Copy function Not Applicable in Rate Data
//	public void copy(RateDataTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
//	{
//		String methodName = "copy(RateDataTran,String)";
//
//		this.setSearchObject(searchObject);
//
//		Vector vec = new Vector();
//		Connection conn = null;
//
//		try
//		{
//			this.getRS();
//
//			conn = SQLUtil.openConnection( this.userToken );
//
//			while (rs.next())
//			{
//				RateDataTran rateDataTran = this.getRateDataTranFromCurrentRow(rs);
//				rateDataTran.getRateData().setModelId(copyToModel);
//				this.insert(rateDataTran,conn);
//			}
//		}
//		catch(SQLException sqle)
//		{
//			logException(className,methodName,sqle);
//			if(sqle.toString().indexOf("ORA-00001") > 0)
//			{
//				// Error was a unique constraint error
//				throw new TCGMDuplicateItemException(sqle.toString());
//			}
//			else
//			{
//				// Error was some other error
//				throw new TCGMException (className, methodName, sqle.toString());
//			}
//		}
//		catch(Exception e)
//		{
//			logException(className,methodName,e);
//			throw new TCGMException(this.className,methodName,e.toString());
//		}
//		finally
//		{
//			SQLUtil.closeRowSet(rs);
//			SQLUtil.closeConnection(conn);
//		}
//	}
	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject RateDataTran
	 */
	private void setSearchObject(RateDataTran searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private RateDataTran getSearchObject()
	{
		return this.searchObject;
	}
}