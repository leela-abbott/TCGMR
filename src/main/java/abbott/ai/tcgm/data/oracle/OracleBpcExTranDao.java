package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
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
public class OracleBpcExTranDao extends OracleDao implements BpcExTranDao
{
	private BpcExTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_BPC_EX;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID, " +
									  "ACD,PUBLISH_FLAG,END_AFF,END_INV_CD,END_LIST,END_LABEL,END_SIZE,END_PACK, " +
									  "RPT_AFF,RPT_INV_CD,RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK, " +
									  "SUP_AFF,SUP_INV_CD,SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK, " +
									  "USAGE_FAC,BEG_PERIOD,END_PERIOD,BILL_PRICE,BP_CUR_CD, " +
									  "COST_PRICE,COST_CUR_CD, FREEZE_COST, EXCEPTIONS_T_ID, " +
									  "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME, " +
									  "BP_1,BP_2,BP_3,BP_4,BP_5,BP_6,BP_7,BP_8,BP_9,BP_10,BP_11,BP_12,BP_13, " +
									  "COST_1,COST_2,COST_3,COST_4,COST_5,COST_6,COST_7,COST_8,COST_9,COST_10,COST_11,COST_12,COST_13 " +
									  "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcExTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleBpcExTranDao(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_BPCOST_EXCEPTIONS_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcExTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleBpcExTranDao(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_BPCOST_EXCEPTIONS_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcExTran object
	 */
	public OracleBpcExTranDao(UserToken userToken,BpcExTran searchObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_BPCOST_EXCEPTIONS_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleBpcExTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS_T);
		this.setEntityView(DBConst.VW_BPCOST_EXCEPTIONS_T);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcExTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcExTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(BpcExTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcExTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcExTran searchObject) throws TCGMException
	{
		String methodName = "getRS(BpcExTran)";
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
			this.sortObject.setSortColumn(DBConst.COL_BPC_EXC_DEF);
		}
		try
		{
			String query = this.MIDDLE_SELECT_START +
				  this.INNER_SELECT +
				  this.getEntityView() +
				  this.genWhereClause() +
			      this.buildEBCDICSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + this.pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + this.pagingFilter.getEndRecord();
			}

			this.logger.debug("\nOracleBpcExTranDao - QUERY: " + query);

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
	 * @param searchObject BpcExTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcExTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(BpcExTran,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject BpcExTran object with search criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcExTran searchObject) throws TCGMException
	{
		String methodName="getVO(BpcExTran)";

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
				vec.add(this.getBpcExTranFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an BpcEx object
	 * @param rs RowSet
	 * @return BpcEx
	 * @throws TCGMException
	 */
	public BpcExTran getBpcExTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getBpcExTranFromCurrentRow(RowSet)";

		BpcExTran bpcExTran = new BpcExTran();

		try
		{
			//bpcExTran.getBpcEx().setDatasetTableId(rs.getString(DBConst.COL_DATASET_TABLE_ID));
			//bpcExTran.getBpcEx().setModelId(rs.getString(DBConst.COL_MODEL_ID));
			bpcExTran.getBpcEx().setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			bpcExTran.getBpcEx().setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			bpcExTran.getBpcEx().setEndAff(rs.getString(DBConst.COL_END_AFF));
			bpcExTran.getBpcEx().getEndProduct().setInvCode(rs.getString(DBConst.COL_END_INV_CD));
			bpcExTran.getBpcEx().getEndProduct().setList(rs.getString(DBConst.COL_END_LIST));
			bpcExTran.getBpcEx().getEndProduct().setLabel(rs.getString(DBConst.COL_END_LABEL));
			bpcExTran.getBpcEx().getEndProduct().setSize(rs.getString(DBConst.COL_END_SIZE));
			bpcExTran.getBpcEx().getEndProduct().setPack(rs.getString(DBConst.COL_END_PACK));

			bpcExTran.getBpcEx().setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			bpcExTran.getBpcEx().getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			bpcExTran.getBpcEx().getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			bpcExTran.getBpcEx().getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			bpcExTran.getBpcEx().getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			bpcExTran.getBpcEx().getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			bpcExTran.getBpcEx().setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			bpcExTran.getBpcEx().getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			bpcExTran.getBpcEx().getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			bpcExTran.getBpcEx().getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			bpcExTran.getBpcEx().getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			bpcExTran.getBpcEx().getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			bpcExTran.getBpcEx().setUsage(rs.getString(DBConst.COL_USAGE_FAC));
			bpcExTran.getBpcEx().setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));
			bpcExTran.getBpcEx().setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));
			bpcExTran.getBpcEx().setFreezeCost(rs.getString(DBConst.COL_FREEZE_COST));

			bpcExTran.getBpcEx().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			bpcExTran.getBpcEx().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			bpcExTran.getBpcEx().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			bpcExTran.getBpcEx().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcExTran.getBpcEx().setBpPeriodValues(i-1,new Period(rs.getString("BP_" + i)));
			}

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcExTran.getBpcEx().setCostPeriodValues(i-1,new Period(rs.getString("COST_" + i)));
			}

			bpcExTran.setActionCode(rs.getString(DBConst.COL_ACD));
			bpcExTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));

			bpcExTran.getBpcEx().setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			bpcExTran.getBpcEx().setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));
			bpcExTran.getBpcEx().setBillPrice(rs.getString(DBConst.COL_BILL_PRICE));
			bpcExTran.getBpcEx().setCostPrice(rs.getString(DBConst.COL_COST_PRICE));

			bpcExTran.setBpcExTranId(rs.getString(DBConst.COL_EXCEPTIONS_T_ID));

			return bpcExTran;
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
	 * @param bpcExTranList Vector of BpcExTran objects
	 * @throws TCGMException
	 */
//	A.Winter - change return type - 7/18/05
	public boolean insert(Vector bpcExTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector)";
		boolean duplic = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcExTranList.size();i++)
			{
				BpcExTran bet =(BpcExTran)bpcExTranList.elementAt(i);
				duplic = this.insert(bet,conn);
				if(!duplic){
					blnFlag = false;
					bet.getBpcEx().setMsg("Duplicate Row");
				}
		// Alex Winter - 7/18//05
				if(bet.getBpcExTranId().equals("DUP"))
					 {
						blnFlag = false;
//						 break;
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
	 * p_USAGE_FACTOR
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_BILL_PRICE
	 * p_BP_CUR_CD
	 * p_COST_PRICE
	 * p_COST_CUR_CD
	 * p_FREEZE_COST
	 * @param bpcExTran BpcExTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
	public boolean insert(BpcExTran bpcExTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(BpcExTran,Connection)";
		boolean connWasNull = false;
// A.Winter 7/18/2005 - add boolean pointer
		boolean duplic = true;
		CallableStatement cs = null;

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				connWasNull = true;
			}

			//String sql = "{ call " + this.schema + ".EXCEPTIONS_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_EXCEPTIONS_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt(1, Integer.parseInt(bpcExTran.getBpcEx().getDatasetTableId()));
			cs.setInt(2, Integer.parseInt(bpcExTran.getBpcEx().getModelId()));
			cs.setString(3, bpcExTran.getActionCode());
			cs.setString(4, bpcExTran.getPublishFlag());
			cs.setString(5, bpcExTran.getBpcEx().getEndAff());
			cs.setString(6, bpcExTran.getBpcEx().getEndProduct().getInvCode());
			cs.setString(7, bpcExTran.getBpcEx().getEndProduct().getList());
			cs.setString(8, this.updColDefault(bpcExTran.getBpcEx().getEndProduct().getLabel()," "));
			cs.setString(9, this.updColDefault(bpcExTran.getBpcEx().getEndProduct().getSize()," "));
			cs.setString(10, bpcExTran.getBpcEx().getEndProduct().getPack());
			cs.setString(11, bpcExTran.getBpcEx().getRptAff());
			cs.setString(12, bpcExTran.getBpcEx().getRptProduct().getInvCode());
			cs.setString(13, bpcExTran.getBpcEx().getRptProduct().getList());
			cs.setString(14, this.updColDefault(bpcExTran.getBpcEx().getRptProduct().getLabel()," "));
			cs.setString(15, this.updColDefault(bpcExTran.getBpcEx().getRptProduct().getSize()," "));
			cs.setString(16, bpcExTran.getBpcEx().getRptProduct().getPack());
			cs.setString(17, bpcExTran.getBpcEx().getSupAff());
			cs.setString(18, bpcExTran.getBpcEx().getSupProduct().getInvCode());
			cs.setString(19, bpcExTran.getBpcEx().getSupProduct().getList());
			cs.setString(20, this.updColDefault(bpcExTran.getBpcEx().getSupProduct().getLabel()," "));
			cs.setString(21, this.updColDefault(bpcExTran.getBpcEx().getSupProduct().getSize()," "));
			cs.setString(22, bpcExTran.getBpcEx().getSupProduct().getPack());
			cs.setDouble(23, Double.parseDouble(this.updColDefault(bpcExTran.getBpcEx().getUsage().trim(),"0.0")));

			cs.setInt(24,Integer.parseInt(this.updColDefault(bpcExTran.getBpcEx().getBegPeriod().trim(),"1")));
			cs.setInt(25,Integer.parseInt(this.updColDefault(bpcExTran.getBpcEx().getEndPeriod().trim(),"12")));

			cs.setDouble(26,Double.parseDouble(this.updColDefault(bpcExTran.getBpcEx().getBillPrice().trim(),"0.0")));
			//cs.setString(27, this.updColDefault(bpcExTran.getBpcEx().getBpCurCode().trim()," "));
			cs.setDouble(28,Double.parseDouble(this.updColDefault(bpcExTran.getBpcEx().getCostPrice().trim(),"0.0")));
			//cs.setString(29, this.updColDefault(bpcExTran.getBpcEx().getCostCurCode().trim()," "));

			if( bpcExTran.getBpcEx().getBillPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 27, " "); // blank out bpCurCode
			}
			else
			{
				cs.setString( 27, this.updColDefault(bpcExTran.getBpcEx().getBpCurCode().trim()," "));
			}

			if( bpcExTran.getBpcEx().getCostPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 29, " "); // blank out costCurCode
			}
			else
			{
				cs.setString( 29, this.updColDefault(bpcExTran.getBpcEx().getCostCurCode().trim()," "));
			}

			cs.setString(30, this.updColDefault(bpcExTran.getBpcEx().getFreezeCost().trim()," "));
			cs.setString( 31, this.updColDefault(this.userToken.getUserid(),"Anonymous") );
			cs.setString( 32, this.updColDefault(this.userToken.getUserid(),"Anonymous") );

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				bpcExTran.setBpcExTranId("DUP");
				duplic = false;
				if(!duplic){ // means duplicate record
					bpcExTran.getBpcEx().setMsg("Duplicate Row");
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
		return duplic;
	}
	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the BpcExTranId
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
	 * p_USAGE_FACTOR
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_BILL_PRICE
	 * p_BP_CUR_CD
	 * p_COST_PRICE
	 * p_COST_CUR_CD
	 * p_FREEZE_COST
	 * p_EXCEPTIONS_T_ID
	 * @param bpcExTran BpcExTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(BpcExTran bpcExTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(BpcExTran)";
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

			//String sql = "{ call " + this.schema + ".EXCEPTIONS_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_EXCEPTIONS_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt(1, Integer.parseInt(bpcExTran.getBpcEx().getDatasetTableId()));
			cs.setInt(2, Integer.parseInt(bpcExTran.getBpcEx().getModelId()));
			cs.setString(3, bpcExTran.getActionCode());
			cs.setString(4, bpcExTran.getPublishFlag());
			cs.setString(5, bpcExTran.getBpcEx().getEndAff());
			cs.setString(6, bpcExTran.getBpcEx().getEndProduct().getInvCode());
			cs.setString(7, bpcExTran.getBpcEx().getEndProduct().getList());
			cs.setString(8, this.updColDefault(bpcExTran.getBpcEx().getEndProduct().getLabel()," "));
			cs.setString(9, this.updColDefault(bpcExTran.getBpcEx().getEndProduct().getSize()," "));
			cs.setString(10, bpcExTran.getBpcEx().getEndProduct().getPack());
			cs.setString(11, bpcExTran.getBpcEx().getRptAff());
			cs.setString(12, bpcExTran.getBpcEx().getRptProduct().getInvCode());
			cs.setString(13, bpcExTran.getBpcEx().getRptProduct().getList());
			cs.setString(14, this.updColDefault(bpcExTran.getBpcEx().getRptProduct().getLabel()," "));
			cs.setString(15, this.updColDefault(bpcExTran.getBpcEx().getRptProduct().getSize()," "));
			cs.setString(16, bpcExTran.getBpcEx().getRptProduct().getPack());
			cs.setString(17, bpcExTran.getBpcEx().getSupAff());
			cs.setString(18, bpcExTran.getBpcEx().getSupProduct().getInvCode());
			cs.setString(19, bpcExTran.getBpcEx().getSupProduct().getList());
			cs.setString(20, this.updColDefault(bpcExTran.getBpcEx().getSupProduct().getLabel()," "));
			cs.setString(21, this.updColDefault(bpcExTran.getBpcEx().getSupProduct().getSize()," "));
			cs.setString(22, bpcExTran.getBpcEx().getSupProduct().getPack());
			//cs.setDouble(23, Double.parseDouble(bpcExTran.getBpcEx().getUsage().trim()));
			cs.setDouble(23, Double.parseDouble(this.updColDefault(bpcExTran.getBpcEx().getUsage().trim(),"0.0")));

			cs.setInt(24,Integer.parseInt(this.updColDefault(bpcExTran.getBpcEx().getBegPeriod().trim(),"1")));
			cs.setInt(25,Integer.parseInt(this.updColDefault(bpcExTran.getBpcEx().getEndPeriod().trim(),"12")));
			cs.setDouble(26,Double.parseDouble(this.updColDefault(bpcExTran.getBpcEx().getBillPrice().trim(),"0.0")));
			cs.setDouble(28,Double.parseDouble(this.updColDefault(bpcExTran.getBpcEx().getCostPrice().trim(),"0.0")));
			if( bpcExTran.getBpcEx().getBillPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 27, " "); // blank out bpCurCode
			}
			else
			{
				cs.setString( 27, this.updColDefault(bpcExTran.getBpcEx().getBpCurCode().trim()," "));
			}

			if( bpcExTran.getBpcEx().getCostPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 29, " "); // blank out costCurCode
			}
			else
			{
				cs.setString( 29, this.updColDefault(bpcExTran.getBpcEx().getCostCurCode().trim()," "));
			}

			cs.setString(30, this.updColDefault(bpcExTran.getBpcEx().getFreezeCost().trim()," "));
			cs.setString( 31, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 32, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString(33,bpcExTran.getBpcExTranId());

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
	 * This method will loop through the given vector and update each BpcExTran object in the collection
	 * based on the BpcExTranId
	 * The connection is created internally
	 * @param bpcExTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector bpcExTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcExTranList.size();i++)
			{
				this.update((BpcExTran)bpcExTranList.elementAt(i),conn);
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
	public void publishAll(BpcExTran bpcExTran, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";

		String sql = "";
		if(blnFlag){
			sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();
		}else{
			sql = this.UPDATE + this.getEntity() + this.SET_UNPUBLISHED + this.genWhereClause();
		}		

		this.logger.debug("SQL: " + sql);

		this.setSearchObject(bpcExTran);
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
	 * 2.  Get the bpcEx object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject BpcExTran
	 * @param newVals BpcExTran
	 * @throws TCGMException
	 */
	public void massUpdate(BpcExTran searchObject,BpcExTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(BpcExTran,BpcExTran)";

		RowSet rs = this.getRS(searchObject);

		try
		{
			int count = 1;
			while(rs.next())
			{
				BpcExTran bpcExTran = this.getBpcExTranFromCurrentRow(rs);

				bpcExTran.setActionCode(this.updCol(bpcExTran.getActionCode(),newVals.getActionCode()));
				bpcExTran.setPublishFlag(this.updCol(bpcExTran.getPublishFlag(),newVals.getPublishFlag()));

				bpcExTran.getBpcEx().setEndAff(this.updCol(bpcExTran.getBpcEx().getEndAff(),newVals.getBpcEx().getEndAff()));
				bpcExTran.getBpcEx().getEndProduct().setInvCode(this.updCol(bpcExTran.getBpcEx().getEndProduct().getInvCode(),newVals.getBpcEx().getEndProduct().getInvCode()));
				bpcExTran.getBpcEx().getEndProduct().setList(this.updCol(bpcExTran.getBpcEx().getEndProduct().getList(),newVals.getBpcEx().getEndProduct().getList()));
				bpcExTran.getBpcEx().getEndProduct().setLabel(this.updCol(bpcExTran.getBpcEx().getEndProduct().getLabel(),newVals.getBpcEx().getEndProduct().getLabel()));
				bpcExTran.getBpcEx().getEndProduct().setSize(this.updCol(bpcExTran.getBpcEx().getEndProduct().getSize(),newVals.getBpcEx().getEndProduct().getSize()));
				bpcExTran.getBpcEx().getEndProduct().setPack(this.updCol(bpcExTran.getBpcEx().getEndProduct().getPack(),newVals.getBpcEx().getEndProduct().getPack()));

				bpcExTran.getBpcEx().setRptAff(this.updCol(bpcExTran.getBpcEx().getRptAff(),newVals.getBpcEx().getRptAff()));
				bpcExTran.getBpcEx().getRptProduct().setInvCode(this.updCol(bpcExTran.getBpcEx().getRptProduct().getInvCode(),newVals.getBpcEx().getRptProduct().getInvCode()));
				bpcExTran.getBpcEx().getRptProduct().setList(this.updCol(bpcExTran.getBpcEx().getRptProduct().getList(),newVals.getBpcEx().getRptProduct().getList()));
				bpcExTran.getBpcEx().getRptProduct().setLabel(this.updCol(bpcExTran.getBpcEx().getRptProduct().getLabel(),newVals.getBpcEx().getRptProduct().getLabel()));
				bpcExTran.getBpcEx().getRptProduct().setSize(this.updCol(bpcExTran.getBpcEx().getRptProduct().getSize(),newVals.getBpcEx().getRptProduct().getSize()));
				bpcExTran.getBpcEx().getRptProduct().setPack(this.updCol(bpcExTran.getBpcEx().getRptProduct().getPack(),newVals.getBpcEx().getRptProduct().getPack()));

				bpcExTran.getBpcEx().setSupAff(this.updCol(bpcExTran.getBpcEx().getSupAff(),newVals.getBpcEx().getSupAff()));
				bpcExTran.getBpcEx().getSupProduct().setInvCode(this.updCol(bpcExTran.getBpcEx().getSupProduct().getInvCode(),newVals.getBpcEx().getSupProduct().getInvCode()));
				bpcExTran.getBpcEx().getSupProduct().setList(this.updCol(bpcExTran.getBpcEx().getSupProduct().getList(),newVals.getBpcEx().getSupProduct().getList()));
				bpcExTran.getBpcEx().getSupProduct().setLabel(this.updCol(bpcExTran.getBpcEx().getSupProduct().getLabel(),newVals.getBpcEx().getSupProduct().getLabel()));
				bpcExTran.getBpcEx().getSupProduct().setSize(this.updCol(bpcExTran.getBpcEx().getSupProduct().getSize(),newVals.getBpcEx().getSupProduct().getSize()));
				bpcExTran.getBpcEx().getSupProduct().setPack(this.updCol(bpcExTran.getBpcEx().getSupProduct().getPack(),newVals.getBpcEx().getSupProduct().getPack()));

				bpcExTran.getBpcEx().setUsage(this.updCol(bpcExTran.getBpcEx().getUsage(),newVals.getBpcEx().getUsage()));

				bpcExTran.getBpcEx().setBegPeriod(this.updCol(bpcExTran.getBpcEx().getBegPeriod(),newVals.getBpcEx().getBegPeriod()));
				bpcExTran.getBpcEx().setEndPeriod(this.updCol(bpcExTran.getBpcEx().getEndPeriod(),newVals.getBpcEx().getEndPeriod()));
				bpcExTran.getBpcEx().setBillPrice(this.updCol(bpcExTran.getBpcEx().getBillPrice(),newVals.getBpcEx().getBillPrice()));
				bpcExTran.getBpcEx().setCostPrice(this.updCol(bpcExTran.getBpcEx().getCostPrice(),newVals.getBpcEx().getCostPrice()));
				bpcExTran.getBpcEx().setBpCurCode(this.updCol(bpcExTran.getBpcEx().getBpCurCode(),newVals.getBpcEx().getBpCurCode()));
				bpcExTran.getBpcEx().setCostCurCode(this.updCol(bpcExTran.getBpcEx().getCostCurCode(),newVals.getBpcEx().getCostCurCode()));
				bpcExTran.getBpcEx().setFreezeCost(this.updCol(bpcExTran.getBpcEx().getFreezeCost(),newVals.getBpcEx().getFreezeCost()));

				this.update(bpcExTran,this.getConnection());
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
	 * @param bpcExTran BpcExTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(BpcExTran bpcExTran,Connection conn) throws TCGMException,
																   TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(BpcExTran,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(bpcExTran);
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
				//03/22/06--Udaya B Aravapalli
				search.setValue(null);
			}
		}
		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains a bpcExTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(bpcExTran.getBpcExTranId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			sql += " where EXCEPTIONS_T_ID = ? ";
		}


		if (bpcExTran.getBpcEx().getCreateLog().getUserName().equalsIgnoreCase("ALL")){

							int andIndex =sql.lastIndexOf("AND");
							sql=sql.substring(0,andIndex);

						}

		this.logger.debug("\nOracleBpcExTran.delete(BpcExTran, Conn) - SQL: " + sql + " <<<DateStamp: " + new java.util.Date() + ">>>");

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

			if(! bpcExTran.getBpcExTranId().equals(""))
			{
				ps.setLong(1,Long.parseLong(bpcExTran.getBpcExTranId()));
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
	 * @param bpcExTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector bpcExTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcExTranList.size();i++)
			{
				this.delete((BpcExTran)bpcExTranList.elementAt(i),conn);
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
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getBpcEx().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getBpcEx().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_END_AFF,searchObject.getBpcEx().getEndAff().trim(),comparisonType(searchObject.getBpcEx().getEndAff().trim())));
		this.searchList.add(new Search(DBConst.COL_END_INV_CD,searchObject.getBpcEx().getEndProduct().getInvCode().trim(),comparisonType(searchObject.getBpcEx().getEndProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_END_LIST,searchObject.getBpcEx().getEndProduct().getList().trim(),comparisonType(searchObject.getBpcEx().getEndProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_END_LABEL,searchObject.getBpcEx().getEndProduct().getLabel(),comparisonType(searchObject.getBpcEx().getEndProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_END_SIZE,searchObject.getBpcEx().getEndProduct().getSize(),comparisonType(searchObject.getBpcEx().getEndProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_END_PACK,searchObject.getBpcEx().getEndProduct().getPack().trim(),comparisonType(searchObject.getBpcEx().getEndProduct().getPack().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getBpcEx().getRptAff().trim(),comparisonType(searchObject.getBpcEx().getRptAff().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getBpcEx().getRptProduct().getInvCode().trim(),comparisonType(searchObject.getBpcEx().getRptProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getBpcEx().getRptProduct().getList().trim(),comparisonType(searchObject.getBpcEx().getRptProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getBpcEx().getRptProduct().getLabel(),comparisonType(searchObject.getBpcEx().getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getBpcEx().getRptProduct().getSize(),comparisonType(searchObject.getBpcEx().getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getBpcEx().getRptProduct().getPack().trim(),comparisonType(searchObject.getBpcEx().getRptProduct().getPack().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getBpcEx().getSupAff().trim(),comparisonType(searchObject.getBpcEx().getSupAff().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getBpcEx().getSupProduct().getInvCode().trim(),comparisonType(searchObject.getBpcEx().getSupProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getBpcEx().getSupProduct().getList().trim(),comparisonType(searchObject.getBpcEx().getSupProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getBpcEx().getSupProduct().getLabel(),comparisonType(searchObject.getBpcEx().getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getBpcEx().getSupProduct().getSize(),comparisonType(searchObject.getBpcEx().getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getBpcEx().getSupProduct().getPack().trim(),comparisonType(searchObject.getBpcEx().getSupProduct().getPack().trim())));
		//this.searchList.add(new Search(DBConst.COL_USAGE_FAC,TCGMUtil.getNumTrimLeadZero(searchObject.getBpcEx().getUsage()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getBpcEx().getUsage()))));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpcEx().getBpCurCode().trim(),comparisonType(searchObject.getBpcEx().getBpCurCode().trim())));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getBpcEx().getCostCurCode().trim(),comparisonType(searchObject.getBpcEx().getCostCurCode().trim())));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getBpcEx().getFreezeCost().trim(),comparisonType(searchObject.getBpcEx().getFreezeCost().trim())));
		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),comparisonType(searchObject.getActionCode().trim())));
		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBegPeriod().trim(),comparisonType(searchObject.getBegPeriod().trim())));
		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,TCGMUtil.getNumTrimLeadZero(searchObject.getBillPrice()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getBillPrice()))));
		this.searchList.add(new Search(DBConst.COL_EXCEPTIONS_T_ID,searchObject.getBpcExTranId().trim(),comparisonType(searchObject.getBpcExTranId().trim())));
		this.searchList.add(new Search(DBConst.COL_COST_PRICE,TCGMUtil.getNumTrimLeadZero(searchObject.getCostPrice()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getCostPrice()))));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getEndPeriod().trim(),comparisonType(searchObject.getEndPeriod().trim())));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),comparisonType(searchObject.getPublishFlag().trim())));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getBpcEx().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
	}

	/*****************************************************************************************/
	/**
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildAdvancedSearchList()
	{
		this.searchList = new Vector();
		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getBpcEx().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getBpcEx().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_END_AFF,searchObject.getBpcEx().getEndAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_INV_CD,searchObject.getBpcEx().getEndProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LIST,searchObject.getBpcEx().getEndProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LABEL,searchObject.getBpcEx().getEndProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_SIZE,searchObject.getBpcEx().getEndProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PACK,searchObject.getBpcEx().getEndProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getBpcEx().getRptAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getBpcEx().getRptProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getBpcEx().getRptProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getBpcEx().getRptProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getBpcEx().getRptProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getBpcEx().getRptProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getBpcEx().getSupAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getBpcEx().getSupProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getBpcEx().getSupProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getBpcEx().getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getBpcEx().getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getBpcEx().getSupProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		//this.searchList.add(new Search(DBConst.COL_USAGE_FAC,searchObject.getBpcEx().getUsage(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpcEx().getBpCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getBpcEx().getCostCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getBpcEx().getFreezeCost().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBegPeriod().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,searchObject.getBillPrice(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_EXCEPTIONS_T_ID,searchObject.getBpcExTranId().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_PRICE,searchObject.getCostPrice(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getEndPeriod().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getBpcEx().getCreateLog().getUserName(),TCGMConstants.ORACLE_IN_COMPARISON));
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
	 * @param bpcExTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector bpcExTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(Vector,String)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcExTranList.size();i++)
			{
				BpcExTran bpcExTran = (BpcExTran)bpcExTranList.elementAt(i);
				bpcExTran.getBpcEx().setModelId(copyToModel);
				this.insert(bpcExTran,conn);
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
	public void copy(BpcExTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(BpcExTran,String)";

		this.setSearchObject(searchObject);

		Vector vec = new Vector();
		Connection conn = null;

		try
		{
			this.getRS();

			conn = SQLUtil.openConnection( );

			while (rs.next())
			{
				BpcExTran bpcExTran = this.getBpcExTranFromCurrentRow(rs);
				bpcExTran.getBpcEx().setModelId(copyToModel);
				this.insert(bpcExTran,conn);
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
	 * @param searchObject BpcExTran
	 */
	private void setSearchObject(BpcExTran searchObject)
	{
		this.searchObject = searchObject;
		if(searchObject.isTranAdvFilter()){
			this.buildAdvancedSearchList();
		}else{
			this.buildSearchList();
		}
	}
	/**
	 *
	 * @return SearchObject
	 */
	private BpcExTran getSearchObject()
	{
		return this.searchObject;
	}
}