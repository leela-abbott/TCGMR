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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Brian A. Dennis
 * @version 1.0
 */
public class OracleBpcRevDao extends OracleDao implements BpcRevDao
{
	private BpcRev searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_BPC_REV;
	private final String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID,RPT_AFF,SUP_AFF, " +
				 "SUP_INV_CD,SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,BP_CUR_CD, " +
				 "BP_1,BP_2,BP_3,BP_4,BP_5,BP_6,BP_7, " +
				 "BP_8,BP_9,BP_10,BP_11,BP_12,BP_13, " +
				 "COST_CUR_CD,COST_1,COST_2,COST_3,COST_4,COST_5,COST_6,COST_7, " +
				 "COST_8,COST_9,COST_10,COST_11,COST_12,COST_13, " +
				 "FREEZE_COST,CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME FROM (";
	/*****************************************************************************************/

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @param pagingFilter
	 * @param sortObject
	 */
	public OracleBpcRevDao(UserToken userToken,BpcRev searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject BpcRev object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleBpcRevDao(UserToken userToken,BpcRev searchObject, PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject BpcRev object
	 */
	public OracleBpcRevDao(UserToken userToken,BpcRev searchObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 *
	 * @param userToken
	 */
	public OracleBpcRevDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcRev object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRev searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(BpcRev,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 *
	 * @param searchObject
	 * @return
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRev searchObject) throws TCGMException
	{
		String methodName = "getRS(BpcRev)";
		this.setSearchObject(searchObject);
		return this.getRS();
	}
	/**
	 *
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
				  this.getEntity() +
				  this.genWhereClause() +
			      this.buildEBCDICSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + pagingFilter.getEndRecord();
			}

			this.logger.debug("\n\nOracleBpcRevDao - getRS QUERY: \n" + query);

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
	 *
	 * @param searchObject
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRev searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(BpcRev,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 *
	 * @param searchObject
	 * @return
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRev searchObject) throws TCGMException
	{
		String methodName="getVO(BpcRev)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}

	/**
	 *
	 * @return Vector of BpcRev objects
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
				vec.add(this.getBpcRevFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an ASR object
	 * @param rs RowSet
	 * @return BpcRev
	 * @throws TCGMException
	 */
	public BpcRev getBpcRevFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getBpcRevFromCurrentRow(RowSet)";

		BpcRev bpcRev = new BpcRev();

		try
		{

			bpcRev.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			bpcRev.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			bpcRev.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			bpcRev.setSupAff(rs.getString(DBConst.COL_SUP_AFF));

			bpcRev.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			bpcRev.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			bpcRev.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			bpcRev.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			bpcRev.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			//bpcRev.setBillPrice(rs.getString(DBConst.COL_BILL_PRICE)); Parm for BpcRevTran only
			bpcRev.setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));

			//bpcRev.setCostPrice(rs.getString(DBConst.COL_COST_PRICE)); Parm for BpcRevTran only

			// 4-25-03 Field does not belong in the BpcRev UI
			//bpcRev.setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcRev.setBpPeriodValues(i-1,new Period(rs.getString("BP_" + i)));
			}
			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcRev.setCostPeriodValues(i-1,new Period(rs.getString("COST_" + i)));
			}

			// Set default value for Begin Period; Parm for BpcRevTran only
			bpcRev.setBegPeriod("1");
			// Set default value for End Period; Parm for BpcRevTran only
			bpcRev.setEndPeriod("12");

			// 4-25-03 Field does not belong in the BpcRev UI
			//bpcRev.setFreezeCost(rs.getString(DBConst.COL_FREEZE_COST));

			bpcRev.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			bpcRev.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			bpcRev.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			bpcRev.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return bpcRev;
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


	/**
	 *
	 * @return
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),comparisonType(searchObject.getRptAff())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),comparisonType(searchObject.getSupAff())));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode(),comparisonType(searchObject.getSupProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList(),comparisonType(searchObject.getSupProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),comparisonType(searchObject.getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),comparisonType(searchObject.getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack(),comparisonType(searchObject.getSupProduct().getPack())));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpCurCode(),comparisonType(searchObject.getBpCurCode())));

		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getFreezeCost(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));

	}

	/**
	 *
	 * @return
	 */
	private void buildAdvancedSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpCurCode(),TCGMConstants.ORACLE_IN_COMPARISON));

		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode(),TCGMConstants.ORACLE_IN_COMPARISON));

		// 4-25-03 Field does not belong in the BpcRev UI
		//this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getFreezeCost(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));

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

	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject BpcRev
	 */
	private void setSearchObject(BpcRev searchObject)
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
	private BpcRev getSearchObject()
	{
		return this.searchObject;
	}
}