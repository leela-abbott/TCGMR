package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.util.logging.Logger;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Oracle Specific implementation of the BpcRevTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
public class OracleBpcRevTranDao extends OracleDao implements BpcRevTranDao
{
	private static Logger myLogger = Logger.getLogger( "OracleBpcRevTranDao" );
	private BpcRevTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_BPC_REV_TRAN;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID, " +
							   "ACD, REV_TYPE, RPT_AFF, SUP_AFF, " +
							   "SUP_INV_CD, SUP_LIST, SUP_LABEL, SUP_SIZE, SUP_PACK, BP_CUR_CD, COST_CUR_CD, " +
							   "BEG_PERIOD, END_PERIOD, BILL_PRICE, COST_PRICE, " +
							   "FREEZE_COST, CREATE_USERNAME, CREATE_DATETIME, " +
							   "MODIFY_USERNAME, MODIFY_DATETIME, PUBLISH_FLAG, BPCOST_T_ID, " +
								"BP_1,BP_2,BP_3,BP_4,BP_5,BP_6,BP_7,BP_8,BP_9,BP_10,BP_11,BP_12,BP_13, " +
								"COST_1,COST_2,COST_3,COST_4,COST_5,COST_6,COST_7,COST_8,COST_9,COST_10,COST_11,COST_12,COST_13 " +
								"FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcRevTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleBpcRevTranDao(UserToken userToken,BpcRevTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_REV);
		this.setEntityView(DBConst.VW_BPCOST_T_REV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcRevTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleBpcRevTranDao(UserToken userToken,BpcRevTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_REV);
		this.setEntityView(DBConst.VW_BPCOST_T_REV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcRevTran object
	 */
	public OracleBpcRevTranDao(UserToken userToken,BpcRevTran searchObject)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_REV);
		this.setEntityView(DBConst.VW_BPCOST_T_REV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleBpcRevTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_REV);
		this.setEntityView(DBConst.VW_BPCOST_T_REV);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcRevTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRevTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(BpcRevTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcRevTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRevTran searchObject) throws TCGMException
	{
		String methodName = "getRS(BpcRevTran)";
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
		if (this.sortObject.getSortColumn().equalsIgnoreCase(DBConst.COL_DEF))
		{
			this.sortObject.setSortColumn(DBConst.COL_BPC_DEF);
		}		
		String methodName = "getRS()";

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

			this.logger.debug("\nOracleBpcRevTranDao - QUERY: " + query);

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
	 * @param searchObject BpcRevTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRevTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(BpcRevTran, Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject BpcRevTran object with search criteria
	 * @return vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRevTran searchObject) throws TCGMException
	{
		String methodName = "getVO(BpcRevTran)";
		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return Vector of BpcRevTran objects
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
				vec.add(this.getBpcRevTranFromCurrentRow(rs));
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
	 *
	 * @param rs RowSet
	 * @return BpcRevTran
	 * @throws TCGMException
	 */
	public BpcRevTran getBpcRevTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getBpcRevTranFromCurrentRow(RowSet)";
		BpcRevTran bpcRevTran = new BpcRevTran();

		try
		{
			bpcRevTran.getBpcRev().setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			bpcRevTran.getBpcRev().setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			bpcRevTran.setActionCode(rs.getString(DBConst.COL_ACD));
			bpcRevTran.getBpcRev().setRevType(rs.getString(DBConst.COL_REV_TYPE));

			bpcRevTran.getBpcRev().setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			bpcRevTran.getBpcRev().setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			bpcRevTran.getBpcRev().setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));

			// 4-25-03 Field does not belong in the BpcRev UI
			//bpcRevTran.getBpcRev().setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));

			// 4-25-03 Field does not belong in the BpcRev UI
			//bpcRevTran.getBpcRev().setFreezeCost(rs.getString(DBConst.COL_FREEZE_COST));
			bpcRevTran.getBpcRev().getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			bpcRevTran.getBpcRev().getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			bpcRevTran.getBpcRev().getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));
			bpcRevTran.getBpcRev().getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			bpcRevTran.getBpcRev().getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));

			//bpcRevTran.setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			bpcRevTran.getBpcRev().setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			//bpcRevTran.setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));
			bpcRevTran.getBpcRev().setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));

			bpcRevTran.getBpcRev().setBillPrice(rs.getString(DBConst.COL_BILL_PRICE));

			// 4-25-03 Field does not belong in the BpcRev UI
			//bpcRevTran.getBpcRev().setCostPrice(rs.getString(DBConst.COL_COST_PRICE));

			bpcRevTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));
			bpcRevTran.setBpcRevTranId(rs.getString(DBConst.COL_BPCOST_T_ID));

			bpcRevTran.getBpcRev().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			bpcRevTran.getBpcRev().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			bpcRevTran.getBpcRev().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			bpcRevTran.getBpcRev().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcRevTran.getBpcRev().setBpPeriodValues(i-1,new Period(rs.getString("BP_" + i)));
			}

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcRevTran.getBpcRev().setCostPeriodValues(i-1,new Period(rs.getString("COST_" + i)));
			}
			return bpcRevTran;
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
	 * @param bpcRevTranList Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
//	A.Winter 7/26/05 - changed method to boolean
	public boolean insert(Vector bpcRevTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector)";
		boolean duplic = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcRevTranList.size();i++)
			{
//				Alex Winter - 7/26//05
			//	this.insert((BpcRevTran)bpcRevTranList.elementAt(i),conn);

				BpcRevTran bet =(BpcRevTran)bpcRevTranList.elementAt(i);
				duplic = this.insert(bet,conn);
				if(!duplic){
					blnFlag = false;
					bet.getBpcRev().setMsg("Duplicate Row");
				}
				if(bet.getBpcRevTranId().equals("DUP"))
				 {
					blnFlag = false;
//				  break;
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
	 * Order of params
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_REV_TYPE
	 * p_RPT_AFF
	 * p_SUP_AFF
	 * p_SUP_INV_CD
	 * p_SUP_LIST
	 * p_SUP_LABEL
	 * p_SUP_SIZE
	 * p_SUP_PACK
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_BP_CUR_CD
	 //* p_COST_CUR_CD
	 * p_BILL_PRICE
	 //* p_COST_PRICE
	 //* p_FREEZE_COST
	 * p_PUBLISH_FLAG
	 * @param bpcRevTran BpcRevTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
	public boolean insert(BpcRevTran bpcRevTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(BpcRevTran,Connection)";
		boolean connWasNull = false;
//		A.Winter 7/18/2005 - add boolean pointer
		 boolean duplic = true;

		CallableStatement cs = null;

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs to be closed here.
				connWasNull = true;
			}

			// BpcRev calls the same procedure as Bpcs because they both create records
			//    in the same underlined DB (BPCOST_T)
			int intResultCode = 0;
			//String sql = "{ call " + this.schema + ".BPCS_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_BPCS_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(bpcRevTran.getBpcRev().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(bpcRevTran.getBpcRev().getModelId()));
			cs.setString( 3, bpcRevTran.getActionCode());

			cs.setString(4, this.updColDefault(bpcRevTran.getBpcRev().getRevType().trim(),"1"));
			cs.setString( 5, bpcRevTran.getBpcRev().getRptAff());
			cs.setString( 6, bpcRevTran.getBpcRev().getSupAff());
			cs.setString( 7, bpcRevTran.getBpcRev().getSupProduct().getInvCode());
			cs.setString( 8, bpcRevTran.getBpcRev().getSupProduct().getList());
			cs.setString( 9, this.updColDefault(bpcRevTran.getBpcRev().getSupProduct().getLabel()," "));
			cs.setString( 10, this.updColDefault(bpcRevTran.getBpcRev().getSupProduct().getSize()," "));
			cs.setString( 11, bpcRevTran.getBpcRev().getSupProduct().getPack());

			cs.setInt(12,Integer.parseInt(this.updColDefault(bpcRevTran.getBpcRev().getBegPeriod().trim(),"1")));
			cs.setInt(13,Integer.parseInt(this.updColDefault(bpcRevTran.getBpcRev().getEndPeriod().trim(),"12")));

			cs.setDouble(14,Double.parseDouble(this.updColDefault(bpcRevTran.getBpcRev().getBillPrice().trim(),"0.0")));

			// 4-25-03 Field does not belong in the BpcRev UI
			//cs.setDouble(16,Double.parseDouble(this.updColDefault(bpcRevTran.getBpcRev().getCostPrice().trim(),"0.0")));
			cs.setDouble(16,0.0);

			if( bpcRevTran.getBpcRev().getBillPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 15, " "); // blank out bpCurCode
			}
			else
			{
				cs.setString( 15, this.updColDefault(bpcRevTran.getBpcRev().getBpCurCode().trim()," "));
			}

			// 4-25-03 Field does not belong in the BpcRev UI
//			if( bpcRevTran.getBpcRev().getCostPrice().trim().equals("")) // empty string; no value entered
//			{
//				cs.setString( 17, " "); // blank out costCurCode
//			}
//			else
//			{
//				cs.setString( 17, this.updColDefault(bpcRevTran.getBpcRev().getCostCurCode().trim()," "));
//			}
			cs.setString( 17, " ");

			// 4-25-03 Field does not belong in the BpcRev UI
			//cs.setString(18, this.updColDefault(bpcRevTran.getBpcRev().getFreezeCost().trim()," "));
			cs.setString( 18, " ");

			cs.setString( 19, bpcRevTran.getPublishFlag() );
			cs.setString( 20, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.registerOutParameter(22, Types.NUMERIC);
			cs.execute();
			intResultCode = cs.getInt(22);
			if(intResultCode==1){
					duplic = false;
				bpcRevTran.getBpcRev().setMsg("Duplicate Row");
				}
			myLogger.info("value of the ResultCode = "+intResultCode);
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
		// A.Winter 7/26/05 Error was a unique constraint error
				bpcRevTran.setBpcRevTranId("DUP");
				duplic = false;
//				throw new TCGMDuplicateItemException(className, methodName, sqle.toString());
			}
			else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}
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
	 * This method calls a stored procedure to physically update a record based on the BpcRevTranId
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_REV_TYPE
	 * p_RPT_AFF
	 * p_SUP_AFF
	 * p_SUP_INV_CD
	 * p_SUP_LIST
	 * p_SUP_LABEL
	 * p_SUP_SIZE
	 * p_SUP_PACK
	 * p_BEG_PERIOD
	 * p_END_PERIOD
	 * p_BILL_PRICE
	 * p_BP_CUR_CD
	 //* p_COST_PRICE
	 //* p_COST_CUR_CD
	 //* p_FREEZE_COST
	 * p_PUBLISH_FLAG
	 * @param bpcRevTran BpcRevTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(BpcRevTran bpcRevTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(BpcRevTran)";
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

			// BpcRev calls the same procedure as Bpcs because they both create records
			//    in the same underlined DB (BPCOST_T)
			//String sql = "{ call " + this.schema + ".BPCS_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_BPCS_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt( 1, Integer.parseInt(bpcRevTran.getBpcRev().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(bpcRevTran.getBpcRev().getModelId()));
			cs.setString( 3, bpcRevTran.getActionCode());
			//cs.setString(4, this.updColDefault(bpcRevTran.getRevType().trim(), "1"));
			cs.setString(4, bpcRevTran.getBpcRev().getRevType().trim());
			cs.setString( 5, bpcRevTran.getBpcRev().getRptAff());
			cs.setString( 6, bpcRevTran.getBpcRev().getSupAff());
			cs.setString( 7, bpcRevTran.getBpcRev().getSupProduct().getInvCode());
			cs.setString( 8, bpcRevTran.getBpcRev().getSupProduct().getList());
			cs.setString( 9, this.updColDefault(bpcRevTran.getBpcRev().getSupProduct().getLabel()," "));
			cs.setString( 10, this.updColDefault(bpcRevTran.getBpcRev().getSupProduct().getSize()," "));
			cs.setString( 11, bpcRevTran.getBpcRev().getSupProduct().getPack());

			cs.setInt(12,Integer.parseInt(this.updColDefault(bpcRevTran.getBpcRev().getBegPeriod().trim(),"1")));
			cs.setInt(13,Integer.parseInt(this.updColDefault(bpcRevTran.getBpcRev().getEndPeriod().trim(),"12")));

			cs.setDouble(14,Double.parseDouble(this.updColDefault(bpcRevTran.getBpcRev().getBillPrice().trim(),"0.0")));
			//cs.setString( 15, this.updColDefault(bpcRevTran.getBpcRev().getBpCurCode().trim()," "));

			// 4-25-03 Field does not belong in the BpcRev UI
			//cs.setDouble(16,Double.parseDouble(this.updColDefault(bpcRevTran.getBpcRev().getCostPrice().trim(),"0.0")));
			cs.setDouble(16,0.0);
			//cs.setString( 17, this.updColDefault(bpcRevTran.getBpcRev().getCostCurCode().trim()," "));

			if( bpcRevTran.getBpcRev().getBillPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 15, " "); // blank out bpCurCode
			}
			else
			{
				cs.setString( 15, this.updColDefault(bpcRevTran.getBpcRev().getBpCurCode().trim()," "));
			}

			// 4-25-03 Field does not belong in the BpcRev UI
//			if( bpcRevTran.getBpcRev().getCostPrice().trim().equals("")) // empty string; no value entered
//			{
//				cs.setString( 17, " "); // blank out costCurCode
//			}
//			else
//			{
//				cs.setString( 17, this.updColDefault(bpcRevTran.getBpcRev().getCostCurCode().trim()," "));
//			}
			cs.setString( 17, " ");

			// 4-25-03 Field does not belong in the BpcRev UI
			//cs.setString( 18, this.updColDefault(bpcRevTran.getBpcRev().getFreezeCost().trim()," "));
			cs.setString( 18, " ");

			cs.setString( 19, bpcRevTran.getPublishFlag() );
			cs.setString( 20, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 22, bpcRevTran.getBpcRevTranId());

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
	 * This method will loop through the given vector and update each BpcRevTran object in the collection
	 * based on the BpcRevTranId
	 * The connection is created internally
	 * @param bpcRevTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector bpcRevTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcRevTranList.size();i++)
			{
				this.update((BpcRevTran)bpcRevTranList.elementAt(i),conn);
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
	public void publishAll(BpcRevTran bpcRevTran, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";

		String sql = "";
		if(blnFlag){
			sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();
		}else{
			sql = this.UPDATE + this.getEntity() + this.SET_UNPUBLISHED + this.genWhereClause();
		}

		this.logger.debug("SQL: " + sql);

		this.setSearchObject(bpcRevTran);
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
	 * 2.  Get the bpcRev object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject BpcRevTran
	 * @param newVals BpcRevTran
	 * @throws TCGMException
	 */
	public void massUpdate(BpcRevTran searchObject,BpcRevTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(BpcRevTran,BpcRevTran)";

		RowSet rs = this.getRS(searchObject);

		try
		{
			int count = 1;
			while(rs.next())
			{
				BpcRevTran bpcRevTran = this.getBpcRevTranFromCurrentRow(rs);

				bpcRevTran.setActionCode(this.updCol(bpcRevTran.getActionCode(),newVals.getActionCode()));
				bpcRevTran.setPublishFlag(this.updCol(bpcRevTran.getPublishFlag(),newVals.getPublishFlag()));
				bpcRevTran.getBpcRev().setRevType(this.updCol(bpcRevTran.getBpcRev().getRevType(),newVals.getBpcRev().getRevType()));
				bpcRevTran.getBpcRev().setRptAff(this.updCol(bpcRevTran.getBpcRev().getRptAff(),newVals.getBpcRev().getRptAff()));
				bpcRevTran.getBpcRev().setSupAff(this.updCol(bpcRevTran.getBpcRev().getSupAff(),newVals.getBpcRev().getSupAff()));
				bpcRevTran.getBpcRev().getSupProduct().setInvCode(this.updCol(bpcRevTran.getBpcRev().getSupProduct().getInvCode(),newVals.getBpcRev().getSupProduct().getInvCode()));
				bpcRevTran.getBpcRev().getSupProduct().setLabel(this.updCol(bpcRevTran.getBpcRev().getSupProduct().getLabel(),newVals.getBpcRev().getSupProduct().getLabel()));
				bpcRevTran.getBpcRev().getSupProduct().setList(this.updCol(bpcRevTran.getBpcRev().getSupProduct().getList(),newVals.getBpcRev().getSupProduct().getList()));
				bpcRevTran.getBpcRev().getSupProduct().setPack(this.updCol(bpcRevTran.getBpcRev().getSupProduct().getPack(),newVals.getBpcRev().getSupProduct().getPack()));
				bpcRevTran.getBpcRev().getSupProduct().setSize(this.updCol(bpcRevTran.getBpcRev().getSupProduct().getSize(),newVals.getBpcRev().getSupProduct().getSize()));
				bpcRevTran.getBpcRev().setBegPeriod(this.updCol(bpcRevTran.getBpcRev().getBegPeriod(),newVals.getBpcRev().getBegPeriod()));
				bpcRevTran.getBpcRev().setEndPeriod(this.updCol(bpcRevTran.getBpcRev().getEndPeriod(),newVals.getBpcRev().getEndPeriod()));
				bpcRevTran.getBpcRev().setBpCurCode(this.updCol(bpcRevTran.getBpcRev().getBpCurCode(),newVals.getBpcRev().getBpCurCode()));

				// 4-25-03 Field does not belong in the BpcRev UI
				//bpcRevTran.getBpcRev().setCostCurCode(this.updCol(bpcRevTran.getBpcRev().getCostCurCode(),newVals.getBpcRev().getCostCurCode()));

				bpcRevTran.getBpcRev().setBillPrice(this.updCol(bpcRevTran.getBpcRev().getBillPrice(),newVals.getBpcRev().getBillPrice()));

				// 4-25-03 Field does not belong in the BpcRev UI
				//bpcRevTran.getBpcRev().setCostPrice(this.updCol(bpcRevTran.getBpcRev().getCostPrice(),newVals.getBpcRev().getCostPrice()));
				// 4-25-03 Field does not belong in the BpcRev UI
				//bpcRevTran.getBpcRev().setFreezeCost(this.updCol(bpcRevTran.getBpcRev().getFreezeCost(),newVals.getBpcRev().getFreezeCost()));

				this.update(bpcRevTran,this.getConnection());
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
	 * @param bpcRevTran BpcRevTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(BpcRevTran bpcRevTran,Connection conn) throws TCGMException,
																	 TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(BpcRevTran,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(bpcRevTran);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains a bpcRevTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(bpcRevTran.getBpcRevTranId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			sql += " where BPCOST_T_ID = ? ";
		}
		this.logger.debug("\nSQL: " + sql);


		if (bpcRevTran.getBpcRev().getCreateLog().getUserName().equalsIgnoreCase("ALL")){

					int andIndex =sql.lastIndexOf("AND");
					sql=sql.substring(0,andIndex);

				}

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

			if(! bpcRevTran.getBpcRevTranId().equals(""))
			{
				ps.setLong(1,Long.parseLong(bpcRevTran.getBpcRevTranId()));
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
	 * @param bpcRevTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector bpcRevTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcRevTranList.size();i++)
			{
				this.delete((BpcRevTran)bpcRevTranList.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}


	/**
	 *
	 * @return
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getBpcRev().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getBpcRev().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));

		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),comparisonType(searchObject.getActionCode().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getBpcRev().getRptAff().trim(),comparisonType(searchObject.getBpcRev().getRptAff().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getBpcRev().getSupAff().trim(),comparisonType(searchObject.getBpcRev().getSupAff().trim())));

		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getBpcRev().getSupProduct().getInvCode().trim(),comparisonType(searchObject.getBpcRev().getSupProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getBpcRev().getSupProduct().getList().trim(),comparisonType(searchObject.getBpcRev().getSupProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getBpcRev().getSupProduct().getLabel(),comparisonType(searchObject.getBpcRev().getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getBpcRev().getSupProduct().getSize(),comparisonType(searchObject.getBpcRev().getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getBpcRev().getSupProduct().getPack().trim(),comparisonType(searchObject.getBpcRev().getSupProduct().getPack().trim())));

		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBpcRev().getBegPeriod().trim(),comparisonType(searchObject.getBpcRev().getBegPeriod().trim())));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getBpcRev().getEndPeriod().trim(),comparisonType(searchObject.getBpcRev().getEndPeriod().trim())));

		this.searchList.add(new Search(DBConst.COL_REV_TYPE,searchObject.getRevType().trim(),comparisonType(searchObject.getRevType().trim())));

		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,TCGMUtil.getNumTrimLeadZero(searchObject.getBpcRev().getBillPrice()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getBpcRev().getBillPrice()))));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpcRev().getBpCurCode().trim(),comparisonType(searchObject.getBpcRev().getBpCurCode().trim())));

		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_COST_PRICE,TCGMUtil.getNumTrimLeadZero(searchObject.getBpcRev().getCostPrice()),comparisonType()));
		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getBpcRev().getCostCurCode().trim(),comparisonType()));
		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getBpcRev().getFreezeCost(),comparisonType()));

		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),comparisonType(searchObject.getPublishFlag().trim())));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getBpcRev().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BPCOST_T_ID,searchObject.getBpcRevTranId().trim(),comparisonType(searchObject.getBpcRevTranId().trim())));
	}

	/**
	 *
	 * @return
	 */
	private void buildAdvancedSearchList()
	{
		this.searchList = new Vector();
		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getBpcRev().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getBpcRev().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getBpcRev().getRptAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getBpcRev().getSupAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getBpcRev().getSupProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getBpcRev().getSupProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getBpcRev().getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getBpcRev().getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getBpcRev().getSupProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBpcRev().getBegPeriod().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getBpcRev().getEndPeriod().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_REV_TYPE,searchObject.getRevType().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,searchObject.getBpcRev().getBillPrice(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpcRev().getBpCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_COST_PRICE,searchObject.getBpcRev().getCostPrice(),TCGMConstants.ORACLE_IN_COMPARISON));
		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getBpcRev().getCostCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getBpcRev().getFreezeCost(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getBpcRev().getCreateLog().getUserName(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BPCOST_T_ID,searchObject.getBpcRevTranId().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
	}
	/**
	 *
	 * @return
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
		sb.append(this.searchObject);
		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 * @param bpcRevTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector bpcRevTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(Vector,String)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcRevTranList.size();i++)
			{
				BpcRevTran bpcRevTran = (BpcRevTran)bpcRevTranList.elementAt(i);
				bpcRevTran.getBpcRev().setModelId(copyToModel);
				this.insert(bpcRevTran,conn);
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
	public void copy(BpcRevTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(BpcRevTran,String)";

		this.setSearchObject(searchObject);

		Vector vec = new Vector();
		Connection conn = null;

		try
		{
			this.getRS();

			conn = SQLUtil.openConnection( );

			while (rs.next())
			{
				BpcRevTran bpcRevTran = this.getBpcRevTranFromCurrentRow(rs);
				bpcRevTran.getBpcRev().setModelId(copyToModel);
				this.insert(bpcRevTran,conn);
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
	 * @param searchObject BpcRevTran
	 */
	private void setSearchObject(BpcRevTran searchObject)
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
	private BpcRevTran getSearchObject()
	{
		return this.searchObject;
	}
}
