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
 * <p>Title: TCGM</p>
 * <p>Description: Oracle Specific implementation of the BpcsTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
public class OracleBpcsTranDao extends OracleDao implements BpcsTranDao
{
	private static Logger myLogger = Logger.getLogger( "OracleBpcsTranDao" );
	private BpcsTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_BPCS_TRAN;
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
	 * @param searchObject BpcsTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleBpcsTranDao(UserToken userToken,BpcsTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_NOREV);
		this.setEntityView(DBConst.VW_BPCOST_T_NOREV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcsTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleBpcsTranDao(UserToken userToken,BpcsTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_NOREV);
		this.setEntityView(DBConst.VW_BPCOST_T_NOREV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcsTran object
	 */
	public OracleBpcsTranDao(UserToken userToken,BpcsTran searchObject)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_NOREV);
		this.setEntityView(DBConst.VW_BPCOST_T_NOREV);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleBpcsTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.VW_BPCS_TRAN_NOREV);
		this.setEntityView(DBConst.VW_BPCOST_T_NOREV);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcsTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcsTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(BpcsTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcsTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcsTran searchObject) throws TCGMException
	{
		String methodName = "getRS(BpcsTran)";
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
			this.sortObject.setSortColumn(DBConst.COL_BPC_DEF);
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

			this.logger.debug("\nOracleBpcsTranDao - QUERY: " + query);

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
	 * @param searchObject BpcsTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcsTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(BpcsTran, Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject BpcsTran object with search criteria
	 * @return vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcsTran searchObject) throws TCGMException
	{
		String methodName = "getVO(BpcsTran)";
		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return Vector of BpcsTran objects
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
				vec.add(this.getBpcsTranFromCurrentRow(rs));
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
	 * @return BpcsTran
	 * @throws TCGMException
	 */
	public BpcsTran getBpcsTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getBpcsTranFromCurrentRow(RowSet)";
		BpcsTran bpcsTran = new BpcsTran();

		try
		{
			bpcsTran.getBpcs().setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			bpcsTran.getBpcs().setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			bpcsTran.setActionCode(rs.getString(DBConst.COL_ACD));
			//bpcsTran.setRevType(rs.getString(DBConst.COL_REV_TYPE));

			bpcsTran.getBpcs().setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			bpcsTran.getBpcs().setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			bpcsTran.getBpcs().setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));
			bpcsTran.getBpcs().setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));
			bpcsTran.getBpcs().setFreezeCost(rs.getString(DBConst.COL_FREEZE_COST));
			bpcsTran.getBpcs().getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			bpcsTran.getBpcs().getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			bpcsTran.getBpcs().getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));
			bpcsTran.getBpcs().getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			bpcsTran.getBpcs().getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));

			//bpcsTran.setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			bpcsTran.getBpcs().setBegPeriod(rs.getString(DBConst.COL_BEG_PERIOD));
			//bpcsTran.setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));
			bpcsTran.getBpcs().setEndPeriod(rs.getString(DBConst.COL_END_PERIOD));

			bpcsTran.getBpcs().setBillPrice(rs.getString(DBConst.COL_BILL_PRICE));
			bpcsTran.getBpcs().setCostPrice(rs.getString(DBConst.COL_COST_PRICE));

			bpcsTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));
			bpcsTran.setBpcsTranId(rs.getString(DBConst.COL_BPCOST_T_ID));

			bpcsTran.getBpcs().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			bpcsTran.getBpcs().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			bpcsTran.getBpcs().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			bpcsTran.getBpcs().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcsTran.getBpcs().setBpPeriodValues(i-1,new Period(rs.getString("BP_" + i)));
			}

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcsTran.getBpcs().setCostPeriodValues(i-1,new Period(rs.getString("COST_" + i)));
			}
			return bpcsTran;
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
	 * @param bpcsTranList Vector of BpcsTran objects
	 * @throws TCGMException
	 */
//	A.Winter - change return type to boolean - 7/26/05
	public boolean insert(Vector bpcsTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(Vector)";
		boolean duplic = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcsTranList.size();i++)
			{
//	Alex Winter - 7/26//05	- start
				BpcsTran bet =(BpcsTran)bpcsTranList.elementAt(i);
				duplic = this.insert(bet,conn);
				if(!duplic){
						blnFlag = false;
					 bet.getBpcs().setMsg("Duplicate Row");
					}
				if(bet.getBpcsTranId().equals("DUP"))
				{
				   blnFlag = false;
//				   break;
				}
//	Alex Winter - 7/26//05	- end
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
	 * p_COST_CUR_CD
	 * p_BILL_PRICE
	 * p_COST_PRICE
	 * p_FREEZE_COST
	 * p_PUBLISH_FLAG
	 * @param bpcsTran BpcsTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
//	A.Winter 7/26/2005 - change method signature to boolean
	public boolean insert(BpcsTran bpcsTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(BpcsTran,Connection)";
		boolean connWasNull = false;
//	A.Winter 7/26/2005 - add boolean pointer
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
			int intResultCode = 0;
			//String sql = "{ call " + this.schema + ".BPCS_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_BPCS_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(bpcsTran.getBpcs().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(bpcsTran.getBpcs().getModelId()));
			cs.setString( 3, bpcsTran.getActionCode());
			cs.setString(4, this.updColDefault(bpcsTran.getRevType().trim()," "));
			cs.setString( 5, bpcsTran.getBpcs().getRptAff());
			cs.setString( 6, bpcsTran.getBpcs().getSupAff());
			cs.setString( 7, bpcsTran.getBpcs().getSupProduct().getInvCode());
			cs.setString( 8, bpcsTran.getBpcs().getSupProduct().getList());
			cs.setString( 9, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getLabel()," "));
			cs.setString( 10, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getSize()," "));
			cs.setString( 11, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getPack(), " ")); //Udaya B Aravapalli. 02/09/2006
			cs.setInt(12,Integer.parseInt(this.updColDefault(bpcsTran.getBpcs().getBegPeriod().trim(),"1")));
			cs.setInt(13,Integer.parseInt(this.updColDefault(bpcsTran.getBpcs().getEndPeriod().trim(),"12")));

			cs.setString(14,(this.updColDefault(bpcsTran.getBpcs().getBillPrice().trim(),"0.0")));
			cs.setString(16,(this.updColDefault(bpcsTran.getBpcs().getCostPrice().trim(),"0.0")));
			Period periodBP[]= (Period[])bpcsTran.getBpcs().getBpPeriodValues();
			if( periodBP[12].equals("")
			   && (!bpcsTran.getBpcs().getBpCurCode().equalsIgnoreCase(TCGMConstants.LBL_CCOST))) // empty string; no value entered
			{
				cs.setString( 15, " "); // blank out bpCurCode
			}
			else
			{			
				if((bpcsTran.getBpcs().getBillPrice().trim().equals("")) && (!periodBP[12].toString().equals(""))){
					cs.setString(14,periodBP[12].toString());
				}
				cs.setString( 15, this.updColDefault(bpcsTran.getBpcs().getBpCurCode().trim()," "));
			}
			Period periodCost[]= (Period[])bpcsTran.getBpcs().getCostPeriodValues();
			if( periodCost[12].equals("")
			   && (!bpcsTran.getBpcs().getCostCurCode().equalsIgnoreCase(TCGMConstants.LBL_CBP))) // empty string; no value entered
			{
				cs.setString( 17, " "); // blank out costCurCode
			}
			else
			{
				if((bpcsTran.getBpcs().getCostPrice().trim().equals("")) && (!periodCost[12].toString().equals(""))){
					cs.setString(16,periodCost[12].toString());
				}
				
				cs.setString( 17, this.updColDefault(bpcsTran.getBpcs().getCostCurCode().trim()," "));
			}

			cs.setString(18, this.updColDefault(bpcsTran.getBpcs().getFreezeCost().trim()," "));
			cs.setString( 19, bpcsTran.getPublishFlag() );
			cs.setString( 20, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.registerOutParameter(22, Types.NUMERIC);

			cs.execute();
			intResultCode = cs.getInt(22);
						if(intResultCode==1){
							duplic = false;
							bpcsTran.getBpcs().setMsg("Duplicate Row");
						}
			myLogger.info("value of the ResultCode = "+intResultCode);
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				bpcsTran.setBpcsTranId("DUP");
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
	
	public boolean insertAffBpc(BpcsTran bpcsTran,Connection conn, String transitAff) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insertAffBpc(BpcsTran bpcsTran,Connection conn, String transitAff)";
		boolean connWasNull = false;
//	A.Winter 7/26/2005 - add boolean pointer
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
			int intResultCode = 0;
			//String sql = "{ call " + this.schema + ".BPCS_TRAN_CREATE_XFER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_BPCS_TRAN_CREATE_XFER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(bpcsTran.getBpcs().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(bpcsTran.getBpcs().getModelId()));
			cs.setString( 3, bpcsTran.getActionCode());
			cs.setString(4, this.updColDefault(bpcsTran.getRevType().trim()," "));
			cs.setString( 5, bpcsTran.getBpcs().getRptAff());
			cs.setString( 6, bpcsTran.getBpcs().getSupAff());
			cs.setString( 7, bpcsTran.getBpcs().getSupProduct().getInvCode());
			cs.setString( 8, bpcsTran.getBpcs().getSupProduct().getList());
			cs.setString( 9, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getLabel()," "));
			cs.setString( 10, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getSize()," "));
			cs.setString( 11, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getPack(), " ")); //Udaya B Aravapalli. 02/09/2006
			cs.setInt(12,Integer.parseInt(this.updColDefault(bpcsTran.getBpcs().getBegPeriod().trim(),"1")));
			cs.setInt(13,Integer.parseInt(this.updColDefault(bpcsTran.getBpcs().getEndPeriod().trim(),"12")));

			cs.setString(14,(this.updColDefault(bpcsTran.getBpcs().getBillPrice().trim(),"0.0")));
			cs.setString(16,(this.updColDefault(bpcsTran.getBpcs().getCostPrice().trim(),"0.0")));
			Period periodBP[]= (Period[])bpcsTran.getBpcs().getBpPeriodValues();
			if( periodBP[12].equals("")
			   && (!bpcsTran.getBpcs().getBpCurCode().equalsIgnoreCase(TCGMConstants.LBL_CCOST))) // empty string; no value entered
			{
				cs.setString( 15, " "); // blank out bpCurCode
			}
			else
			{			
				if((bpcsTran.getBpcs().getBillPrice().trim().equals("")) && (!periodBP[12].toString().equals(""))){
					cs.setString(14,periodBP[12].toString());
				}
				cs.setString( 15, this.updColDefault(bpcsTran.getBpcs().getBpCurCode().trim()," "));
			}
			Period periodCost[]= (Period[])bpcsTran.getBpcs().getCostPeriodValues();
			if( periodCost[12].equals("")
			   && (!bpcsTran.getBpcs().getCostCurCode().equalsIgnoreCase(TCGMConstants.LBL_CBP))) // empty string; no value entered
			{
				cs.setString( 17, " "); // blank out costCurCode
			}
			else
			{
				if((bpcsTran.getBpcs().getCostPrice().trim().equals("")) && (!periodCost[12].toString().equals(""))){
					cs.setString(16,periodCost[12].toString());
				}
				
				cs.setString( 17, this.updColDefault(bpcsTran.getBpcs().getCostCurCode().trim()," "));
			}

			cs.setString(18, this.updColDefault(bpcsTran.getBpcs().getFreezeCost().trim()," "));
			cs.setString( 19, bpcsTran.getPublishFlag() );
			cs.setString( 20, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.registerOutParameter(22, Types.NUMERIC);
			cs.setString( 23, transitAff );

			cs.execute();
			intResultCode = cs.getInt(22);
						if(intResultCode==1){
							duplic = false;
							bpcsTran.getBpcs().setMsg("Duplicate Row");
						}
			myLogger.info("value of the ResultCode = "+intResultCode);
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				bpcsTran.setBpcsTranId("DUP");
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
	 * Order of params
	 * P_DATASET_TABLE_ID
	* P_MODEL_ID		
	* P_RPT_AFF		
	* P_SUP_AFF		
	* P_SUP_INV_CD		
	* P_SUP_LIST         
	* P_SUP_LABEL        
	* P_SUP_SIZE         
	* P_SUP_PACK         
	* P_RESULT_CODE      
	 * @param bpcsTran BpcsTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
//		A.Winter 7/26/2005 - change method signature to boolean
	public boolean isExistinASR(BpcsTran bpcsTran,Connection conn) throws TCGMException
	{
		String methodName = "isExistinASR(BpcsTran,Connection)";
		boolean connWasNull = false;
        boolean isExistASR = true;
		CallableStatement cs = null;

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				connWasNull = true;
			}
			int intResultCode = 0;
			//String sql = "{ call " + this.schema + ".AFFBPC_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_AFFBPC_TRAN_CREATE (?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(bpcsTran.getBpcs().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(bpcsTran.getBpcs().getModelId()));
		//	cs.setString( 3, bpcsTran.getActionCode());
		//	cs.setString(4, this.updColDefault(bpcsTran.getRevType().trim()," "));
			cs.setString( 3, bpcsTran.getBpcs().getRptAff());
			cs.setString( 4, bpcsTran.getBpcs().getSupAff());
			cs.setString( 5, bpcsTran.getBpcs().getSupProduct().getInvCode());
			cs.setString( 6, bpcsTran.getBpcs().getSupProduct().getList());
			cs.setString( 7, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getLabel()," "));
			cs.setString( 8, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getSize()," "));
			cs.setString( 9, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getPack(), " ")); //Udaya B Aravapalli. 02/09/2006

		//	cs.setString(12,(this.updColDefault(bpcsTran.getBpcs().getBillPrice().trim(),"0.0")));
		//	cs.setString(13, this.updColDefault(bpcsTran.getBpcs().getBpCurCode().trim()," "));
		//	cs.setString(14,(this.updColDefault(bpcsTran.getBpcs().getCostPrice().trim(),"0.0")));
		//	cs.setString(15, this.updColDefault(bpcsTran.getBpcs().getCostCurCode().trim()," "));
						
		//	cs.setString( 16, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.registerOutParameter(10, Types.NUMERIC);

			cs.execute();
			intResultCode = cs.getInt(10);
						if(intResultCode==1){
							isExistASR = false;
							bpcsTran.getBpcs().setMsg("Record not Exist in ASR");
						}
			myLogger.info("value of the ResultCode = "+intResultCode);
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
				// A.Winter 7/15/05 Error was a unique constraint error
				bpcsTran.setBpcsTranId("DUP");
				isExistASR = false;
			}
			else
			{
				// Error was some other error
				throw new TCGMException (className, methodName, sqle.toString());
			}
		}
//			catch(Exception e)
//			{
//				logException(className,methodName,e);
//				throw new TCGMException ( className,methodName,e.toString());
//			}

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
		return isExistASR;
	}

	/*****************************************************************************************/

	/*****************************************************************************************/
	/**
	 * This method calls a stored procedure to physically update a record based on the BpcsTranId
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
	 * p_COST_PRICE
	 * p_COST_CUR_CD
	 * p_FREEZE_COST
	 * p_PUBLISH_FLAG
	 * @param bpcsTran BpcsTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(BpcsTran bpcsTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(BpcsTran, Conn)";
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

			//String sql = "{ call " + this.schema + ".BPCS_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_BPCS_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);
			cs.setInt( 1, Integer.parseInt(bpcsTran.getBpcs().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(bpcsTran.getBpcs().getModelId()));
			cs.setString( 3, bpcsTran.getActionCode());
			// Set RevType to " " for Bpcs
			cs.setString(4, this.updColDefault(bpcsTran.getRevType().trim(), " "));
			cs.setString( 5, bpcsTran.getBpcs().getRptAff());
			cs.setString( 6, bpcsTran.getBpcs().getSupAff());
			cs.setString( 7, bpcsTran.getBpcs().getSupProduct().getInvCode());
			cs.setString( 8, bpcsTran.getBpcs().getSupProduct().getList());
			cs.setString( 9, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getLabel()," "));
			cs.setString( 10, this.updColDefault(bpcsTran.getBpcs().getSupProduct().getSize()," "));
			cs.setString( 11, bpcsTran.getBpcs().getSupProduct().getPack());

			cs.setInt(12,Integer.parseInt(this.updColDefault(bpcsTran.getBpcs().getBegPeriod().trim(),"1")));
			cs.setInt(13,Integer.parseInt(this.updColDefault(bpcsTran.getBpcs().getEndPeriod().trim(),"12")));

			cs.setDouble(14,Double.parseDouble(this.updColDefault(bpcsTran.getBpcs().getBillPrice().trim(),"0.0")));
			//cs.setString( 15, this.updColDefault(bpcsTran.getBpcs().getBpCurCode().trim()," "));
			cs.setDouble(16,Double.parseDouble(this.updColDefault(bpcsTran.getBpcs().getCostPrice().trim(),"0.0")));
			//cs.setString( 17, this.updColDefault(bpcsTran.getBpcs().getCostCurCode().trim()," "));

			if( bpcsTran.getBpcs().getBillPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 15, " "); // blank out bpCurCode
			}
			else
			{
				cs.setString( 15, this.updColDefault(bpcsTran.getBpcs().getBpCurCode().trim()," "));
			}

			if( bpcsTran.getBpcs().getCostPrice().trim().equals("")) // empty string; no value entered
			{
				cs.setString( 17, " "); // blank out costCurCode
			}
			else
			{
				cs.setString( 17, this.updColDefault(bpcsTran.getBpcs().getCostCurCode().trim()," "));
			}

			cs.setString( 18, this.updColDefault(bpcsTran.getBpcs().getFreezeCost().trim()," "));
			cs.setString( 19, bpcsTran.getPublishFlag() );
			cs.setString( 20, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 21, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 22, bpcsTran.getBpcsTranId());

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
	 * This method will loop through the given vector and update each BpcsTran object in the collection
	 * based on the BpcsTranId
	 * The connection is created internally
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector bpcsTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcsTranList.size();i++)
			{
				this.update((BpcsTran)bpcsTranList.elementAt(i),conn);
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
	public void publishAll(BpcsTran bpcsTran, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";

		String sql = "";
		if(blnFlag){
			sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();
		}else{
			sql = this.UPDATE + this.getEntity() + this.SET_UNPUBLISHED + this.genWhereClause();
		}

		this.logger.debug("SQL: " + sql);

		this.setSearchObject(bpcsTran);
		Search search;
		Iterator item = searchList.iterator();
		while (item.hasNext())
		{
			search = (Search)item.next();
			if((search.getColumnName().equals(DBConst.COL_CREATE_USERNAME)) &&
			   (search.getValue().equals("") || (search.getValue().equals(null))) )
			{
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
	 * 2.  Get the bpcs object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject BpcsTran
	 * @param newVals BpcsTran
	 * @throws TCGMException
	 */
	public void massUpdate(BpcsTran searchObject,BpcsTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(BpcsTran,BpcsTran)";
		RowSet rs = this.getRS(searchObject);

		try
		{
			int count = 1;
			while(rs.next())
			{
				BpcsTran bpcsTran = this.getBpcsTranFromCurrentRow(rs);

				bpcsTran.setActionCode(this.updCol(bpcsTran.getActionCode(),newVals.getActionCode()));
				bpcsTran.setPublishFlag(this.updCol(bpcsTran.getPublishFlag(),newVals.getPublishFlag()));
				//bpcsTran.setRevType(this.updCol(bpcsTran.getRevType(),newVals.getRevType()));
				bpcsTran.getBpcs().setRptAff(this.updCol(bpcsTran.getBpcs().getRptAff(),newVals.getBpcs().getRptAff()));
				bpcsTran.getBpcs().setSupAff(this.updCol(bpcsTran.getBpcs().getSupAff(),newVals.getBpcs().getSupAff()));
				bpcsTran.getBpcs().getSupProduct().setInvCode(this.updCol(bpcsTran.getBpcs().getSupProduct().getInvCode(),newVals.getBpcs().getSupProduct().getInvCode()));
				bpcsTran.getBpcs().getSupProduct().setLabel(this.updCol(bpcsTran.getBpcs().getSupProduct().getLabel(),newVals.getBpcs().getSupProduct().getLabel()));
				bpcsTran.getBpcs().getSupProduct().setList(this.updCol(bpcsTran.getBpcs().getSupProduct().getList(),newVals.getBpcs().getSupProduct().getList()));
				bpcsTran.getBpcs().getSupProduct().setPack(this.updCol(bpcsTran.getBpcs().getSupProduct().getPack(),newVals.getBpcs().getSupProduct().getPack()));
				bpcsTran.getBpcs().getSupProduct().setSize(this.updCol(bpcsTran.getBpcs().getSupProduct().getSize(),newVals.getBpcs().getSupProduct().getSize()));

				bpcsTran.getBpcs().setBegPeriod(this.updCol(bpcsTran.getBpcs().getBegPeriod(),newVals.getBpcs().getBegPeriod()));
				bpcsTran.getBpcs().setEndPeriod(this.updCol(bpcsTran.getBpcs().getEndPeriod(),newVals.getBpcs().getEndPeriod()));

				bpcsTran.getBpcs().setBpCurCode(this.updCol(bpcsTran.getBpcs().getBpCurCode(),newVals.getBpcs().getBpCurCode()));
				bpcsTran.getBpcs().setCostCurCode(this.updCol(bpcsTran.getBpcs().getCostCurCode(),newVals.getBpcs().getCostCurCode()));

				bpcsTran.getBpcs().setBillPrice(this.updCol(bpcsTran.getBpcs().getBillPrice(),newVals.getBpcs().getBillPrice()));
				bpcsTran.getBpcs().setCostPrice(this.updCol(bpcsTran.getBpcs().getCostPrice(),newVals.getBpcs().getCostPrice()));

				bpcsTran.getBpcs().setFreezeCost(this.updCol(bpcsTran.getBpcs().getFreezeCost(),newVals.getBpcs().getFreezeCost()));

				this.update(bpcsTran,this.getConnection());
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
	 * @param bpcsTran BpcsTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(BpcsTran bpcsTran,Connection conn) throws TCGMException,
																 TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(BpcsTran,Connection)";
		boolean connWasNull = false;


		this.setSearchObject(bpcsTran);
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
				search.setValue(null);
//				throw new TCGMUpdateWithBlankUsernameException("* Username Cannot Be Blank When Updating Records *");
			}
		}
		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains a bpcsTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(bpcsTran.getBpcsTranId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			// 4/21/03 Somehow, I screwed this line up and had to go back to history to replace
			//sql += " where BPCOST_T_ID = = " + bpcsTran.getBpcsTranId();
			sql += " where BPCOST_T_ID = ? ";
		}

		/*   The below code is added to refine the sql when a "ALL" is selected as the User ID in the
		 *   GUI--  Gain 03/09/2006			 */

		if (bpcsTran.getBpcs().getCreateLog().getUserName().equalsIgnoreCase("ALL")){

			int andIndex =sql.lastIndexOf("AND");
			sql=sql.substring(0,andIndex);

		}

		this.logger.debug("\nOracleBpcsTran.delete(BpcsTran, Conn) - SQL: " + sql + " <<<DateStamp: " + new java.util.Date() + ">>>");

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

			if(! bpcsTran.getBpcsTranId().equals(""))
			{
				ps.setLong(1,Long.parseLong(bpcsTran.getBpcsTranId()));
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
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector bpcsTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcsTranList.size();i++)
			{
				this.delete((BpcsTran)bpcsTranList.elementAt(i),conn);
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
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getBpcs().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getBpcs().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));

		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),comparisonType(searchObject.getActionCode().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getBpcs().getRptAff().trim(),comparisonType(searchObject.getBpcs().getRptAff().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getBpcs().getSupAff().trim(),comparisonType(searchObject.getBpcs().getSupAff().trim())));

		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getBpcs().getSupProduct().getInvCode().trim(),comparisonType(searchObject.getBpcs().getSupProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getBpcs().getSupProduct().getList().trim(),comparisonType(searchObject.getBpcs().getSupProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getBpcs().getSupProduct().getLabel(),comparisonType(searchObject.getBpcs().getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getBpcs().getSupProduct().getSize(),comparisonType(searchObject.getBpcs().getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getBpcs().getSupProduct().getPack().trim(),comparisonType(searchObject.getBpcs().getSupProduct().getPack().trim())));

		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBpcs().getBegPeriod().trim(),comparisonType(searchObject.getBpcs().getBegPeriod().trim())));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getBpcs().getEndPeriod().trim(),comparisonType(searchObject.getBpcs().getEndPeriod().trim())));

		//this.searchList.add(new Search(DBConst.COL_REV_TYPE,searchObject.getRevType().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,TCGMUtil.getNumTrimLeadZero(searchObject.getBpcs().getBillPrice()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getBpcs().getBillPrice()))));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpcs().getBpCurCode().trim(),comparisonType(searchObject.getBpcs().getBpCurCode().trim())));
		this.searchList.add(new Search(DBConst.COL_COST_PRICE,TCGMUtil.getNumTrimLeadZero(searchObject.getBpcs().getCostPrice()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getBpcs().getCostPrice()))));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getBpcs().getCostCurCode().trim(),comparisonType(searchObject.getBpcs().getCostCurCode().trim())));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getBpcs().getFreezeCost(),comparisonType(searchObject.getBpcs().getFreezeCost())));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),comparisonType(searchObject.getPublishFlag().trim())));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getBpcs().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BPCOST_T_ID,searchObject.getBpcsTranId().trim(),comparisonType(searchObject.getBpcsTranId().trim())));
	}

	/**
	 *
	 * @return
	 */
	private void buildAdvancedSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getBpcs().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getBpcs().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));

		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getBpcs().getRptAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getBpcs().getSupAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getBpcs().getSupProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getBpcs().getSupProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getBpcs().getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getBpcs().getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getBpcs().getSupProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_BEG_PERIOD,searchObject.getBpcs().getBegPeriod().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PERIOD,searchObject.getBpcs().getEndPeriod().trim(),TCGMConstants.ORACLE_IN_COMPARISON));

		//this.searchList.add(new Search(DBConst.COL_REV_TYPE,searchObject.getRevType().trim(),TCGMConstants.ORACLE_IN_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,searchObject.getBpcs().getBillPrice(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpcs().getBpCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_PRICE,searchObject.getBpcs().getCostPrice(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getBpcs().getCostCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getBpcs().getFreezeCost(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getBpcs().getCreateLog().getUserName(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BPCOST_T_ID,searchObject.getBpcsTranId().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
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
	 * @param bpcsTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector bpcsTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(Vector,String)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < bpcsTranList.size();i++)
			{
				BpcsTran bpcsTran = (BpcsTran)bpcsTranList.elementAt(i);
				bpcsTran.getBpcs().setModelId(copyToModel);
				this.insert(bpcsTran,conn);
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
	public void copy(BpcsTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(BpcsTran,String)";

		this.setSearchObject(searchObject);

		Vector vec = new Vector();
		Connection conn = null;

		try
		{
			this.getRS();

			conn = SQLUtil.openConnection( );

			while (rs.next())
			{
				BpcsTran bpcsTran = this.getBpcsTranFromCurrentRow(rs);
				bpcsTran.getBpcs().setModelId(copyToModel);
				this.insert(bpcsTran,conn);
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
	 * @param searchObject BpcsTran
	 */
	private void setSearchObject(BpcsTran searchObject)
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
	private BpcsTran getSearchObject()
	{
		return this.searchObject;
	}
}
