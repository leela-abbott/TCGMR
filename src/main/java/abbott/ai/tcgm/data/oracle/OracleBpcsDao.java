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
public class OracleBpcsDao extends OracleDao implements BpcsDao
{
	private Bpcs searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_BPCS;
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
	public OracleBpcsDao(UserToken userToken,Bpcs searchObject,PagingFilter pagingFilter,Sort sortObject)
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
	 * @param searchObject Bpcs object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleBpcsDao(UserToken userToken,Bpcs searchObject, PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject Bpcs object
	 */
	public OracleBpcsDao(UserToken userToken,Bpcs searchObject)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 *
	 * @param userToken
	 */
	public OracleBpcsDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_BPCOST);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject Bpcs object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Bpcs searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(Bpcs,Sort)";
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
	public RowSet getRS(Bpcs searchObject) throws TCGMException
	{
		String methodName = "getRS(Bpcs)";
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

			this.logger.debug("\n\nOracleBpcsDao - getRS QUERY: \n" + query);

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
	public Vector getVO(Bpcs searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(Bpcs,Sort)";

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
	public Vector getVO(Bpcs searchObject) throws TCGMException
	{
		String methodName="getVO(Bpcs)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}

	/**
	 *
	 * @return Vector of Bpcs objects
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
				vec.add(this.getBpcsFromCurrentRow(rs));
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
	 * @return Bpcs
	 * @throws TCGMException
	 */
	public Bpcs getBpcsFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getBpcsFromCurrentRow(RowSet)";

		Bpcs bpcs = new Bpcs();

		try
		{

			bpcs.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			bpcs.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			bpcs.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			bpcs.setSupAff(rs.getString(DBConst.COL_SUP_AFF));

			bpcs.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			bpcs.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			bpcs.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			bpcs.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			bpcs.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			//bpcs.setBillPrice(rs.getString(DBConst.COL_BILL_PRICE)); Parm for BpcsTran only
			bpcs.setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));

			//bpcs.setCostPrice(rs.getString(DBConst.COL_COST_PRICE)); Parm for BpcsTran only
			bpcs.setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcs.setBpPeriodValues(i-1,new Period(rs.getString("BP_" + i)));
			}
			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				bpcs.setCostPeriodValues(i-1,new Period(rs.getString("COST_" + i)));
			}

			// Set default value for Begin Period; Parm for BpcsTran only
			bpcs.setBegPeriod("1");
			// Set default value for End Period; Parm for BpcsTran only
			bpcs.setEndPeriod("12");

			bpcs.setFreezeCost(rs.getString(DBConst.COL_FREEZE_COST));

			bpcs.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			bpcs.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			bpcs.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			bpcs.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return bpcs;
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
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode(),comparisonType(searchObject.getCostCurCode())));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getFreezeCost(),comparisonType(searchObject.getFreezeCost())));
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
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_FREEZE_COST,searchObject.getFreezeCost(),TCGMConstants.ORACLE_IN_COMPARISON));
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
	 * @param searchObject Bpcs
	 */
	private void setSearchObject(Bpcs searchObject)
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
	private Bpcs getSearchObject()
	{
		return this.searchObject;
	}
}