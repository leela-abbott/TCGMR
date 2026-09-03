package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

import org.apache.log4j.Logger;

/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class OracleRateExDao extends OracleDao implements RateExDao
{
	private static Logger myLogger = Logger.getLogger( "OracleRateExDao" );
	private RateEx searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_RATEEX;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID," +
									  "END_AFF,END_INV_CD,END_LIST,END_LABEL,END_SIZE,END_PACK," +
									  "RPT_AFF,RPT_INV_CD,RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK," +
									  "SUP_AFF,SUP_INV_CD,SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK," +
									  "BPF_RAT_1,BPF_RAT_2,BPF_RAT_3,BPF_RAT_4,BPF_RAT_5,BPF_RAT_6,BPF_RAT_7,BPF_RAT_8,BPF_RAT_9,BPF_RAT_10,BPF_RAT_11,BPF_RAT_12,BPF_RAT_13,"+
									  "BPP_RAT_1,BPP_RAT_2,BPP_RAT_3,BPP_RAT_4,BPP_RAT_5,BPP_RAT_6,BPP_RAT_7,BPP_RAT_8,BPP_RAT_9,BPP_RAT_10,BPP_RAT_11,BPP_RAT_12,BPP_RAT_13,"+
									  "COSTF_RAT_1,COSTF_RAT_2,COSTF_RAT_3,COSTF_RAT_4,COSTF_RAT_5,COSTF_RAT_6,COSTF_RAT_7,COSTF_RAT_8,COSTF_RAT_9,COSTF_RAT_10,COSTF_RAT_11,COSTF_RAT_12,COSTF_RAT_13,"+
									  "COSTP_RAT_1,COSTP_RAT_2,COSTP_RAT_3,COSTP_RAT_4,COSTP_RAT_5,COSTP_RAT_6,COSTP_RAT_7,COSTP_RAT_8,COSTP_RAT_9,COSTP_RAT_10,COSTP_RAT_11,COSTP_RAT_12,COSTP_RAT_13,"+
									  "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
									  "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateEx object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleRateExDao(UserToken userToken,RateEx searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateEx object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleRateExDao(UserToken userToken,RateEx searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject RateEx object
	 */
	public OracleRateExDao(UserToken userToken,RateEx searchObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleRateExDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_RATE_EXCEPTIONS);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateEx object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateEx searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(RateEx,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateEx object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateEx searchObject) throws TCGMException
	{
		String methodName = "getRS(RateEx)";
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
				  this.getEntity() +
				  this.genWhereClause() +
				  this.buildSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			myLogger.info("OracleRateExDao/getRS()- QUERY: " + query);

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + pagingFilter.getEndRecord();
			}

			this.logger.debug("\n\nOracleRateExDao - QUERY: " + query);

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
	 * @param searchObject RateEx object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateEx searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(RateEx,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject RateEx object with search criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateEx searchObject) throws TCGMException
	{
		String methodName="getVO(RateEx)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of RateEx objects
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
				vec.add(this.getRateExFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an RateEx object
	 * @param rs RowSet
	 * @return RateEx
	 * @throws TCGMException
	 */
	public RateEx getRateExFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getRateExFromCurrentRow(RowSet)";

		RateEx rateEx = new RateEx();

		try
		{
			rateEx.setDatasetTableId(rs.getString(DBConst.COL_DATASET_TABLE_ID));
			rateEx.setModelId(rs.getString(DBConst.COL_MODEL_ID));

			rateEx.setEndAff(rs.getString(DBConst.COL_END_AFF));
			rateEx.getEndProduct().setInvCode(rs.getString(DBConst.COL_END_INV_CD));
			rateEx.getEndProduct().setList(rs.getString(DBConst.COL_END_LIST));
			rateEx.getEndProduct().setLabel(rs.getString(DBConst.COL_END_LABEL));
			rateEx.getEndProduct().setSize(rs.getString(DBConst.COL_END_SIZE));
			rateEx.getEndProduct().setPack(rs.getString(DBConst.COL_END_PACK));

			rateEx.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			rateEx.getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			rateEx.getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			rateEx.getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			rateEx.getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			rateEx.getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			rateEx.setSupAff(rs.getString(DBConst.COL_SUP_AFF));
			rateEx.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			rateEx.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			rateEx.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			rateEx.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			rateEx.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateEx.setBpfRates(i-1,new Period(rs.getString("BPF_RAT_" + i)));
			}
			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateEx.setBppRates(i-1,new Period(rs.getString("BPP_RAT_" + i)));
			}
			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateEx.setCostfRates(i-1,new Period(rs.getString("COSTF_RAT_" + i)));
			}
			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateEx.setCostpRates(i-1,new Period(rs.getString("COSTP_RAT_" + i)));
			}

			// Set default value for Begin Period; Parm for RateExTran only
			rateEx.setBegPeriod("1");
			// Set default value for End Period; Parm for RateExTran only
			rateEx.setEndPeriod("12");

			rateEx.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			rateEx.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			rateEx.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			rateEx.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return rateEx;
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

		this.searchList.add(new Search(DBConst.COL_END_AFF,searchObject.getEndAff(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_INV_CD,searchObject.getEndProduct().getInvCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LIST,searchObject.getEndProduct().getList(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_LABEL,searchObject.getEndProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_SIZE,searchObject.getEndProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_END_PACK,searchObject.getEndProduct().getPack(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack(),TCGMConstants.ORACLE_LIKE_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
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
		sb.append(this.searchObject);

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject RateEx
	 */
	private void setSearchObject(RateEx searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private RateEx getSearchObject()
	{
		return this.searchObject;
	}
}
