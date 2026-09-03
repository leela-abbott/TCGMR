package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Brian Dennis
 * @version 1.0
 */
public class OracleRateExTranDao extends OracleDao implements RateExTranDao
{
	private RateExTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_RATEEX_TRAN;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID,ACD," +
									  "END_AFF, END_INV_CD,END_LIST,END_LABEL,END_SIZE,END_PACK," +
									  "RPT_AFF, RPT_INV_CD,RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK," +
									  "SUP_AFF,SUP_INV_CD,SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK," +
									  "BEG_PERIOD,END_PERIOD,BPF_RATE,COSTF_RATE,BPP_RATE,COSTP_RATE," +
									  "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME," +
									  "BPF_RAT_1,BPF_RAT_2,BPF_RAT_3,BPF_RAT_4,BPF_RAT_5,BPF_RAT_6,BPF_RAT_7,BPF_RAT_8,BPF_RAT_9,BPF_RAT_10,BPF_RAT_11,BPF_RAT_12,BPF_RAT_13," +
									  "BPP_RAT_1,BPP_RAT_2,BPP_RAT_3,BPP_RAT_4,BPP_RAT_5,BPP_RAT_6,BPP_RAT_7,BPP_RAT_8,BPP_RAT_9,BPP_RAT_10,BPP_RAT_11,BPP_RAT_12,BPP_RAT_13," +
									  "COSTF_RAT_1,COSTF_RAT_2,COSTF_RAT_3,COSTF_RAT_4,COSTF_RAT_5,COSTF_RAT_6,COSTF_RAT_7,COSTF_RAT_8,COSTF_RAT_9,COSTF_RAT_10,COSTF_RAT_11,COSTF_RAT_12,COSTF_RAT_13," +
									  "COSTP_RAT_1,COSTP_RAT_2,COSTP_RAT_3,COSTP_RAT_4,COSTP_RAT_5,COSTP_RAT_6,COSTP_RAT_7,COSTP_RAT_8,COSTP_RAT_9,COSTP_RAT_10,COSTP_RAT_11,COSTP_RAT_12,COSTP_RAT_13," +
									  "PUBLISH_FLAG, EXRATE_T_ID " +
									  "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateExTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleRateExTranDao(UserToken userToken,RateExTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_RATE_EXCEPTIONS_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateExTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleRateExTranDao(UserToken userToken,RateExTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_RATE_EXCEPTIONS_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateExTran object
	 */
	public OracleRateExTranDao(UserToken userToken,RateExTran searchObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_RATE_EXCEPTIONS_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleRateExTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_RATE_EXCEPTIONS_T);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateExTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public javax.sql.RowSet getRS(RateExTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(RateExTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateExTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateExTran searchObject) throws TCGMException
	{
		String methodName = "getRS(RateExTran)";
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

			this.logger.debug("OraceRateExTranDao - QUERY: " + query);

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
	 * @param searchObject RateExTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateExTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(RateExTran, Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject RateExTran object with search criteria
	 * @return vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateExTran searchObject) throws TCGMException
	{
		String methodName = "getVO(RateExTran)";
		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return Vector of RateExTran objects
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
				vec.add(this.getRateExTranFromCurrentRow(rs));
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
	 * @return RateExTran
	 * @throws TCGMException
	 */
	private RateExTran getRateExTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getRateExTranFromCurrentRow(RowSet)";
		RateExTran rateExTran = new RateExTran();

		try
		{
			rateExTran.getRateEx().setDatasetTableId(rs.getString(DBConst.COL_DATASET_TABLE_ID));
			rateExTran.getRateEx().setModelId(rs.getString(DBConst.COL_MODEL_ID));
			rateExTran.setActionCode(rs.getString(DBConst.COL_ACD));

			rateExTran.getRateEx().setEndAff(rs.getString(DBConst.COL_END_AFF));
			rateExTran.getRateEx().setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			rateExTran.getRateEx().setSupAff(rs.getString(DBConst.COL_SUP_AFF));

			rateExTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));
			rateExTran.setRateExTranId(rs.getString(DBConst.COL_EXRATE_T_ID));

			rateExTran.getRateEx().getEndProduct().setInvCode(rs.getString(DBConst.COL_END_INV_CD));
			rateExTran.getRateEx().getEndProduct().setList(rs.getString(DBConst.COL_END_LIST));
			rateExTran.getRateEx().getEndProduct().setLabel(rs.getString(DBConst.COL_END_LABEL));
			rateExTran.getRateEx().getEndProduct().setSize(rs.getString(DBConst.COL_END_SIZE));
			rateExTran.getRateEx().getEndProduct().setPack(rs.getString(DBConst.COL_END_PACK));

			rateExTran.getRateEx().getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			rateExTran.getRateEx().getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			rateExTran.getRateEx().getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			rateExTran.getRateEx().getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			rateExTran.getRateEx().getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			rateExTran.getRateEx().getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			rateExTran.getRateEx().getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			rateExTran.getRateEx().getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			rateExTran.getRateEx().getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			rateExTran.getRateEx().getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			//rateExTran.setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			rateExTran.getRateEx().setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			//rateExTran.setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));
			rateExTran.getRateEx().setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));


			rateExTran.getRateEx().setBpfRate(rs.getString(DBConst.COL_BPF_RATE));
			rateExTran.getRateEx().setBppRate(rs.getString(DBConst.COL_BPP_RATE));
			rateExTran.getRateEx().setCostfRate(rs.getString(DBConst.COL_COSTF_RATE));
			rateExTran.getRateEx().setCostpRate(rs.getString(DBConst.COL_COSTP_RATE));

			rateExTran.getRateEx().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			rateExTran.getRateEx().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			rateExTran.getRateEx().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			rateExTran.getRateEx().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateExTran.getRateEx().setBppRates(i-1,new Period(rs.getString("BPP_RAT_" + i)));
			}

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateExTran.getRateEx().setBpfRates(i-1,new Period(rs.getString("BPF_RAT_" + i)));
			}

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateExTran.getRateEx().setCostpRates(i-1,new Period(rs.getString("COSTP_RAT_" + i)));
			}

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateExTran.getRateEx().setCostfRates(i-1,new Period(rs.getString("COSTF_RAT_" + i)));
			}

			return rateExTran;
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
	 * in the vector into the RateEx_T table.  The connection is created/closed internally.
	 * @param rateExTranList Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public boolean insert(Vector rateExTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector)";
 //A.Winter - 7/18/05       
        boolean result = true;
        boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateExTranList.size();i++)
			{
			//	this.insert((RateExTran)rateExTranList.elementAt(i),conn);
				RateExTran at =(RateExTran)rateExTranList.elementAt(i);
				result = this.insert(at,conn);
//				if(result == false)
//				  break;
				if(!result){
					blnFlag = false;
					at.getRateEx().setMsg("Duplicate Row");
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
	 * This method calls a stored procedure to physically insert a record into the RateEx_T table
	 * If the connection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method.
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_PUBLISH_FLAG
	 * p_END_AFF
	 * p_END_INV_CD
	 * p_END_LIST
	 * p_END_LABEL
	 * p_END_SIZE
	 * p_END_PACK
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
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_BPF_RATE
	 * p_BPP_RATE
	 * p_COSTF_RATE
	 * p_COSTP_RATE
	 * @param rateExTran RateExTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
// A.Winter - 7/18/05	
	public boolean insert(RateExTran rateExTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(RateExTran,Connection)";
		boolean connWasNull = false;
        boolean dupl = true;
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

			//String sql = "{ call " + this.schema + ".EXRATE_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_EXRATE_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, rateExTran.getRateEx().getDatasetTableIdInt());
			cs.setInt( 2, rateExTran.getRateEx().getModelIdInt());
			cs.setString( 3, rateExTran.getActionCode());
			cs.setString( 4, rateExTran.getPublishFlag() );

			cs.setString( 5, rateExTran.getRateEx().getEndAff());
			cs.setString( 6, rateExTran.getRateEx().getEndProduct().getInvCode());
			cs.setString( 7, rateExTran.getRateEx().getEndProduct().getList());
			cs.setString( 8, this.updColDefault(rateExTran.getRateEx().getEndProduct().getLabel()," "));
			cs.setString( 9, this.updColDefault(rateExTran.getRateEx().getEndProduct().getSize()," "));
			cs.setString( 10, rateExTran.getRateEx().getEndProduct().getPack());

			cs.setString( 11, rateExTran.getRateEx().getRptAff());
			cs.setString( 12, rateExTran.getRateEx().getRptProduct().getInvCode());
			cs.setString( 13, rateExTran.getRateEx().getRptProduct().getList());
			cs.setString( 14, this.updColDefault(rateExTran.getRateEx().getRptProduct().getLabel()," "));
			cs.setString( 15, this.updColDefault(rateExTran.getRateEx().getRptProduct().getSize()," "));
			cs.setString( 16, rateExTran.getRateEx().getRptProduct().getPack());

			cs.setString( 17, rateExTran.getRateEx().getSupAff());
			cs.setString( 18, rateExTran.getRateEx().getSupProduct().getInvCode());
			cs.setString( 19, rateExTran.getRateEx().getSupProduct().getList());
			cs.setString( 20, this.updColDefault(rateExTran.getRateEx().getSupProduct().getLabel()," "));
			cs.setString( 21, this.updColDefault(rateExTran.getRateEx().getSupProduct().getSize()," "));
			cs.setString( 22, rateExTran.getRateEx().getSupProduct().getPack() );

			cs.setInt(23,Integer.parseInt(this.updColDefault(rateExTran.getRateEx().getBegPeriod().trim(),"1")));
			cs.setInt(24,Integer.parseInt(this.updColDefault(rateExTran.getRateEx().getEndPeriod().trim(),"12")));

			cs.setDouble(25,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getBpfRate().trim(),"0.0")));
			cs.setDouble(26,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getBppRate().trim(),"0.0")));
			cs.setDouble(27,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getCostfRate().trim(),"0.0")));
			cs.setDouble(28,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getCostpRate().trim(),"0.0")));
			cs.setString( 29, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );			
			cs.setString( 30, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				rateExTran.setRateExTranId("DUP");
				dupl = false;
				if(!dupl){ // means duplicate record
					rateExTran.getRateEx().setMsg("Duplicate Row");
				}
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
	 return dupl;	
	}
	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the RateExTranId
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_PUBLISH_FLAG
	 * p_END_AFF
	 * p_END_INV_CD
	 * p_END_LIST
	 * p_END_LABEL
	 * p_END_SIZE
	 * p_END_PACK
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
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_BPF_RATE
	 * p_BPP_RATE
	 * p_COSTF_RATE
	 * p_COSTP_RATE
	 * p_EXRATE_T_ID
	 * @param rateExTran RateExTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(RateExTran rateExTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(RateExTran)";
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

			//String sql = "{ call " + this.schema + ".EXRATE_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_EXRATE_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(rateExTran.getRateEx().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(rateExTran.getRateEx().getModelId()));
			cs.setString( 3, rateExTran.getActionCode());
			cs.setString( 4, rateExTran.getPublishFlag() );
			cs.setString( 5, rateExTran.getRateEx().getEndAff());
			cs.setString( 6, rateExTran.getRateEx().getEndProduct().getInvCode());
			cs.setString( 7, rateExTran.getRateEx().getEndProduct().getList());
			cs.setString( 8, this.updColDefault(rateExTran.getRateEx().getEndProduct().getLabel()," "));
			cs.setString( 9, this.updColDefault(rateExTran.getRateEx().getEndProduct().getSize()," "));
			cs.setString( 10, rateExTran.getRateEx().getEndProduct().getPack());

			cs.setString( 11, rateExTran.getRateEx().getRptAff());
			cs.setString( 12, rateExTran.getRateEx().getRptProduct().getInvCode());
			cs.setString( 13, rateExTran.getRateEx().getRptProduct().getList());
			cs.setString( 14, this.updColDefault(rateExTran.getRateEx().getRptProduct().getLabel()," "));
			cs.setString( 15, this.updColDefault(rateExTran.getRateEx().getRptProduct().getSize()," "));
			cs.setString( 16, rateExTran.getRateEx().getRptProduct().getPack());

			cs.setString( 17, rateExTran.getRateEx().getSupAff());
			cs.setString( 18, rateExTran.getRateEx().getSupProduct().getInvCode());
			cs.setString( 19, rateExTran.getRateEx().getSupProduct().getList());
			cs.setString( 20, this.updColDefault(rateExTran.getRateEx().getSupProduct().getLabel()," "));
			cs.setString( 21, this.updColDefault(rateExTran.getRateEx().getSupProduct().getSize()," "));
			cs.setString( 22, rateExTran.getRateEx().getSupProduct().getPack());

			cs.setInt(23,Integer.parseInt(this.updColDefault(rateExTran.getRateEx().getBegPeriod().trim(),"1")));
			cs.setInt(24,Integer.parseInt(this.updColDefault(rateExTran.getRateEx().getEndPeriod().trim(),"12")));

			cs.setDouble(25,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getBpfRate().trim(),"0.0")));
			cs.setDouble(26,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getBppRate().trim(),"0.0")));
			cs.setDouble(27,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getCostfRate().trim(),"0.0")));
			cs.setDouble(28,Double.parseDouble(this.updColDefault(rateExTran.getRateEx().getCostpRate().trim(),"0.0")));
			cs.setString( 29, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );			
			cs.setString( 30, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setInt(31,Integer.parseInt(rateExTran.getRateExTranId()));

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
	 * This method will loop through the given vector and update each RateExTran object in the collection
	 * based on the RateExTranId
	 * The connection is created internally
	 * @param rateExTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector rateExTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateExTranList.size();i++)
			{
				this.update((RateExTran)rateExTranList.elementAt(i),conn);
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
	public void publishAll(RateExTran rateExTran) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";

		String sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();

		this.logger.debug("SQL: " + sql);

		this.setSearchObject(rateExTran);
		Search search;
		Iterator item = searchList.iterator();
		while (item.hasNext())
		{
			search = (Search)item.next();
			if((search.getColumnName().equals(DBConst.COL_CREATE_USERNAME)) &&
			   (search.getValue().equals("") || (search.getValue().equals(null))) )
			{
				//03/22/06--Udaya B Aravapalli
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
	 * 2.  Get the rateEx object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject RateExTran
	 * @param newVals RateExTran
	 * @throws TCGMException
	 */
	public void massUpdate(RateExTran searchObject,RateExTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(RateExTran,RateExTran)";

		RowSet rs = this.getRS(searchObject);

		try
		{
			int count = 1;
			while(rs.next())
			{
				RateExTran rateExTran = this.getRateExTranFromCurrentRow(rs);

				rateExTran.setActionCode(this.updCol(rateExTran.getActionCode(),newVals.getActionCode()));
				rateExTran.setPublishFlag(this.updCol(rateExTran.getPublishFlag(),newVals.getPublishFlag()));

				rateExTran.getRateEx().setEndAff(this.updCol(rateExTran.getRateEx().getEndAff(),newVals.getRateEx().getEndAff()));
				rateExTran.getRateEx().getEndProduct().setInvCode(this.updCol(rateExTran.getRateEx().getEndProduct().getInvCode(),newVals.getRateEx().getEndProduct().getInvCode()));
				rateExTran.getRateEx().getEndProduct().setList(this.updCol(rateExTran.getRateEx().getEndProduct().getList(),newVals.getRateEx().getEndProduct().getList()));
				rateExTran.getRateEx().getEndProduct().setLabel(this.updCol(rateExTran.getRateEx().getEndProduct().getLabel(),newVals.getRateEx().getEndProduct().getLabel()));
				rateExTran.getRateEx().getEndProduct().setSize(this.updCol(rateExTran.getRateEx().getEndProduct().getSize(),newVals.getRateEx().getEndProduct().getSize()));
				rateExTran.getRateEx().getEndProduct().setPack(this.updCol(rateExTran.getRateEx().getEndProduct().getPack(),newVals.getRateEx().getEndProduct().getPack()));

				rateExTran.getRateEx().setRptAff(this.updCol(rateExTran.getRateEx().getRptAff(),newVals.getRateEx().getRptAff()));
				rateExTran.getRateEx().getRptProduct().setInvCode(this.updCol(rateExTran.getRateEx().getRptProduct().getInvCode(),newVals.getRateEx().getRptProduct().getInvCode()));
				rateExTran.getRateEx().getRptProduct().setList(this.updCol(rateExTran.getRateEx().getRptProduct().getList(),newVals.getRateEx().getRptProduct().getList()));
				rateExTran.getRateEx().getRptProduct().setLabel(this.updCol(rateExTran.getRateEx().getRptProduct().getLabel(),newVals.getRateEx().getRptProduct().getLabel()));
				rateExTran.getRateEx().getRptProduct().setSize(this.updCol(rateExTran.getRateEx().getRptProduct().getSize(),newVals.getRateEx().getRptProduct().getSize()));
				rateExTran.getRateEx().getRptProduct().setPack(this.updCol(rateExTran.getRateEx().getRptProduct().getPack(),newVals.getRateEx().getRptProduct().getPack()));

				rateExTran.getRateEx().setSupAff(this.updCol(rateExTran.getRateEx().getSupAff(),newVals.getRateEx().getSupAff()));
				rateExTran.getRateEx().getSupProduct().setInvCode(this.updCol(rateExTran.getRateEx().getSupProduct().getInvCode(),newVals.getRateEx().getSupProduct().getInvCode()));
				rateExTran.getRateEx().getSupProduct().setList(this.updCol(rateExTran.getRateEx().getSupProduct().getList(),newVals.getRateEx().getSupProduct().getList()));
				rateExTran.getRateEx().getSupProduct().setLabel(this.updCol(rateExTran.getRateEx().getSupProduct().getLabel(),newVals.getRateEx().getSupProduct().getLabel()));
				rateExTran.getRateEx().getSupProduct().setSize(this.updCol(rateExTran.getRateEx().getSupProduct().getSize(),newVals.getRateEx().getSupProduct().getSize()));
				rateExTran.getRateEx().getSupProduct().setPack(this.updCol(rateExTran.getRateEx().getSupProduct().getPack(),newVals.getRateEx().getSupProduct().getPack()));

				rateExTran.getRateEx().setBegPeriod(this.updCol(rateExTran.getRateEx().getBegPeriod(),newVals.getRateEx().getBegPeriod()));
				rateExTran.getRateEx().setEndPeriod(this.updCol(rateExTran.getRateEx().getEndPeriod(),newVals.getRateEx().getEndPeriod()));

				rateExTran.getRateEx().setBpfRate(this.updCol(rateExTran.getRateEx().getBpfRate(),newVals.getRateEx().getBpfRate()));
				rateExTran.getRateEx().setBppRate(this.updCol(rateExTran.getRateEx().getBppRate(),newVals.getRateEx().getBppRate()));
				rateExTran.getRateEx().setCostfRate(this.updCol(rateExTran.getRateEx().getCostfRate(),newVals.getRateEx().getCostfRate()));
				rateExTran.getRateEx().setCostpRate(this.updCol(rateExTran.getRateEx().getCostpRate(),newVals.getRateEx().getCostpRate()));

				this.update(rateExTran,this.getConnection());
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
	 * @param rateExTran RateExTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(RateExTran rateExTran,Connection conn) throws TCGMException,
																	 TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(RateExTran,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(rateExTran);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains an rateExTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(rateExTran.getRateExTranId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			sql += " where EXRATE_T_ID = ? ";
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

			if(! rateExTran.getRateExTranId().equals(""))
			{
				ps.setLong(1,Long.parseLong(rateExTran.getRateExTranId()));
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
	 * @param rateExTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector rateExTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateExTranList.size();i++)
			{
				this.delete((RateExTran)rateExTranList.elementAt(i),conn);
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
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getRateEx().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getRateEx().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_END_AFF,searchObject.getRateEx().getEndAff().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_INV_CD,searchObject.getRateEx().getEndProduct().getInvCode().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LIST,searchObject.getRateEx().getEndProduct().getList().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LABEL,searchObject.getRateEx().getEndProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_SIZE,searchObject.getRateEx().getEndProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PACK,searchObject.getRateEx().getEndProduct().getPack().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRateEx().getRptAff().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRateEx().getRptProduct().getInvCode().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRateEx().getRptProduct().getList().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRateEx().getRptProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRateEx().getRptProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRateEx().getRptProduct().getPack().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getRateEx().getSupAff().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getRateEx().getSupProduct().getInvCode().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getRateEx().getSupProduct().getList().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getRateEx().getSupProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getRateEx().getSupProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getRateEx().getSupProduct().getPack().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getRateEx().getBegPeriod().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getRateEx().getEndPeriod().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_BPF_RATE,TCGMUtil.getNumTrimLeadZero(searchObject.getRateEx().getBpfRate()),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BPP_RATE,TCGMUtil.getNumTrimLeadZero(searchObject.getRateEx().getBppRate()),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COSTF_RATE,TCGMUtil.getNumTrimLeadZero(searchObject.getRateEx().getCostfRate()),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COSTP_RATE,TCGMUtil.getNumTrimLeadZero(searchObject.getRateEx().getCostpRate()),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getRateEx().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_EXRATE_T_ID,searchObject.getRateExTranId().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
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
	 * @param rateExTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector rateExTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(Vector,String)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < rateExTranList.size();i++)
			{
				RateExTran rateExTran = (RateExTran)rateExTranList.elementAt(i);
				rateExTran.getRateEx().setModelId(copyToModel);
				this.insert(rateExTran,conn);
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
	public void copy(RateExTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(RateExTran,String)";

		this.setSearchObject(searchObject);

		Vector vec = new Vector();
		Connection conn = null;

		try
		{
			this.getRS();

			conn = SQLUtil.openConnection( );

			while (rs.next())
			{
				RateExTran rateExTran = this.getRateExTranFromCurrentRow(rs);
				rateExTran.getRateEx().setModelId(copyToModel);
				this.insert(rateExTran,conn);
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
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject RateExTran
	 */
	private void setSearchObject(RateExTran searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}

	/**
	 *
	 * @return SearchObject
	 */
	private RateExTran getSearchObject()
	{
		return this.searchObject;
	}
}