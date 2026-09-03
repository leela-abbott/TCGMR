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
public class OracleRateDataDao extends OracleDao implements RateDataDao
{
	private RateData searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_RATE_DATA;
	private final String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID, " +
				 "CUR_CD,RATE1,RATE2,RATE3,RATE4,RATE5,RATE6,RATE7, " +
				 "RATE8,RATE9,RATE10,RATE11,RATE12,RATE13, " +
				 "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME FROM (";
	/*****************************************************************************************/

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @param pagingFilter
	 * @param sortObject
	 */
	public OracleRateDataDao(UserToken userToken,RateData searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject RateData object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleRateDataDao(UserToken userToken,RateData searchObject, PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject RateData object
	 */
	public OracleRateDataDao(UserToken userToken,RateData searchObject)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 *
	 * @param userToken
	 */
	public OracleRateDataDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_RATE_DATA);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject RateData object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateData searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(RateData,Sort)";
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
	public RowSet getRS(RateData searchObject) throws TCGMException
	{
		String methodName = "getRS(RateData)";
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

		try
		{
			String query = this.MIDDLE_SELECT_START +
				  this.INNER_SELECT +
				  this.getEntity() +
				  this.genWhereClause() +
				  this.buildSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + pagingFilter.getEndRecord();
			}

			this.logger.debug("\n\nOracleRateDataDao - getRS QUERY: \n" + query);

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
	public Vector getVO(RateData searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(RateData,Sort)";

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
	public Vector getVO(RateData searchObject) throws TCGMException
	{
		String methodName="getVO(RateData)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}

	/**
	 *
	 * @return Vector of RateData objects
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
				vec.add(this.getRateDataFromCurrentRow(rs));
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
	 * @return RateData
	 * @throws TCGMException
	 */
	public RateData getRateDataFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getRateDataFromCurrentRow(RowSet)";

		RateData rateData = new RateData();

		try
		{

			rateData.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			//rateData.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			rateData.setCurCode(rs.getString(DBConst.COL_CUR_CD));

			for(int i = 1; i <= TCGMConstants.MAX_PERIODS; i++)
			{
				rateData.setRates(i-1,new Period(rs.getString("RATE" + i)));
			}

			// Set default value for Begin Period; Parm for RateDataTran only
			rateData.setBegPeriod("1");
			// Set default value for End Period; Parm for RateDataTran only
			rateData.setEndPeriod("12");

			rateData.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			rateData.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			rateData.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			rateData.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return rateData;
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
		//this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_CUR_CD,searchObject.getCurCode().trim(),TCGMConstants.ORACLE_LIKE_COMPARISON));
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
	 * @param searchObject RateData
	 */
	private void setSearchObject(RateData searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private RateData getSearchObject()
	{
		return this.searchObject;
	}
}