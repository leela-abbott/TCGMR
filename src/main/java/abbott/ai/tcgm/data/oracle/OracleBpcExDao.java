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
public class OracleBpcExDao extends OracleDao implements BpcExDao
{
	private BpcEx searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_BPC_EX;
	private final String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID, " +
							   "END_AFF,END_INV_CD,END_LIST,END_LABEL,END_SIZE,END_PACK, " +
							   "RPT_AFF,RPT_INV_CD,RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK, " +
							   "SUP_AFF,SUP_INV_CD,SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK, " +
							   "USAGE_FAC,BP_CUR_CD,BP_1,BP_2,BP_3,BP_4,BP_5,BP_6,BP_7, "+
							   "BP_8,BP_9,BP_10,BP_11,BP_12,BP_13,COST_CUR_CD, " +
							   "COST_1,COST_2,COST_3,COST_4,COST_5,COST_6,COST_7,COST_8, " +
							   "COST_9,COST_10,COST_11,COST_12,COST_13,FREEZE_COST, "+
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcEx object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleBpcExDao(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcEx object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleBpcExDao(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject BpcEx object
	 */
	public OracleBpcExDao(UserToken userToken,BpcEx searchObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleBpcExDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST_EXCEPTIONS);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcEx object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcEx searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(BpcEx,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject BpcEx object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcEx searchObject) throws TCGMException
	{
		String methodName = "getRS(BpcEx)";
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
				  this.getEntity() +
				  this.genWhereClause() +
			      this.buildEBCDICSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + this.pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + this.pagingFilter.getEndRecord();
			}

			this.logger.debug("\nOracleBpcExDao - QUERY: " + query);

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
	 * @param searchObject BpcEx object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcEx searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(BpcEx,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject BpcEx object with search criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcEx searchObject) throws TCGMException
	{
		String methodName="getVO(BpcEx)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return
	 * @throws TCGMException
	 */
	/**
	 *
	 * @return Vector of BpcEx objects
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
				vec.add(this.getBpcExFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an BpcEx object
	 * @param rs RowSet
	 * @return BpcEx
	 * @throws TCGMException
	 */
	public BpcEx getBpcExFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getBpcExFromCurrentRow(RowSet)";

		BpcEx bpcEx = new BpcEx();

		try
		{
			//bpcEx.setDatasetTableId(rs.getString(DBConst.COL_DATASET_TABLE_ID));
			bpcEx.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			//bpcEx.setModelId(rs.getString(DBConst.COL_MODEL_ID));
			bpcEx.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			bpcEx.setEndAff(rs.getString(DBConst.COL_END_AFF));
			bpcEx.getEndProduct().setInvCode(rs.getString(DBConst.COL_END_INV_CD));
			bpcEx.getEndProduct().setList(rs.getString(DBConst.COL_END_LIST));
			bpcEx.getEndProduct().setLabel(rs.getString(DBConst.COL_END_LABEL));
			bpcEx.getEndProduct().setSize(rs.getString(DBConst.COL_END_SIZE));
			bpcEx.getEndProduct().setPack(rs.getString(DBConst.COL_END_PACK));

			bpcEx.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			bpcEx.getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			bpcEx.getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			bpcEx.getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			bpcEx.getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			bpcEx.getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			bpcEx.setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			bpcEx.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			bpcEx.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			bpcEx.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			bpcEx.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			bpcEx.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			bpcEx.setUsage(rs.getString(DBConst.COL_USAGE_FAC));
			bpcEx.setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcEx.setBpPeriodValues(i-1,new Period(rs.getString("BP_" + i)));
			}

			bpcEx.setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcEx.setCostPeriodValues(i-1,new Period(rs.getString("COST_" + i)));
			}

			// Set default value for Begin Period; Parm for BpcExTran only
			bpcEx.setBegPeriod("1");
			// Set default value for End Period; Parm for BpcExTran only
			bpcEx.setEndPeriod("12");

			bpcEx.setFreezeCost(rs.getString(DBConst.COL_FREEZE_COST));

			bpcEx.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			bpcEx.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			bpcEx.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			bpcEx.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return bpcEx;
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
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_END_AFF,searchObject.getEndAff().trim(),comparisonType(searchObject.getEndAff().trim())));
		this.searchList.add(new Search(DBConst.COL_END_INV_CD,searchObject.getEndProduct().getInvCode().trim(),comparisonType(searchObject.getEndProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_END_LIST,searchObject.getEndProduct().getList().trim(),comparisonType(searchObject.getEndProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_END_LABEL,searchObject.getEndProduct().getLabel(),comparisonType(searchObject.getEndProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_END_SIZE,searchObject.getEndProduct().getSize(),comparisonType(searchObject.getEndProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_END_PACK,searchObject.getEndProduct().getPack().trim(),comparisonType(searchObject.getEndProduct().getPack().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff().trim(),comparisonType(searchObject.getRptAff().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode().trim(),comparisonType(searchObject.getRptProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList().trim(),comparisonType(searchObject.getRptProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),comparisonType(searchObject.getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),comparisonType(searchObject.getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack().trim(),comparisonType(searchObject.getRptProduct().getPack().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff().trim(),comparisonType(searchObject.getSupAff().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode().trim(),comparisonType(searchObject.getSupProduct().getInvCode().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList().trim(),comparisonType(searchObject.getSupProduct().getList().trim())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),comparisonType(searchObject.getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),comparisonType(searchObject.getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack().trim(),comparisonType(searchObject.getSupProduct().getPack().trim())));
		//this.searchList.add(new Search(DBConst.COL_USAGE_FAC,TCGMUtil.getNumTrimLeadZero(searchObject.getUsage()),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpCurCode().trim(),comparisonType(searchObject.getBpCurCode().trim())));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode().trim(),comparisonType(searchObject.getCostCurCode().trim())));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getFreezeCost().trim(),comparisonType(searchObject.getFreezeCost().trim())));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
	}
	/*****************************************************************************************/
	/**
	 * Generates the vector of search parameters from the searchObject
	 */
	private void buildAdvancedSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_END_AFF,searchObject.getEndAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_INV_CD,searchObject.getEndProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LIST,searchObject.getEndProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LABEL,searchObject.getEndProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_SIZE,searchObject.getEndProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PACK,searchObject.getEndProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		//this.searchList.add(new Search(DBConst.COL_USAGE_FAC,searchObject.getUsage(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getFreezeCost().trim(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
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
	 * @param searchObject BpcEx
	 */
	private void setSearchObject(BpcEx searchObject)
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
	private BpcEx getSearchObject()
	{
		return this.searchObject;
	}
}