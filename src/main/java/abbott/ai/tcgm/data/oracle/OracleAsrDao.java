package abbott.ai.tcgm.data.oracle;

//import org.apache.log4j.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM </p>
 * <p>Description: Oracle Specific implementation of the Asr Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleAsrDao extends OracleDao implements AsrDao
{
	private Asr searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_ASR;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID,RPT_AFF,RPT_INV_CD, " +
							   "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, " +
							   "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,PROD_ORIGIN,SUP_KEY,USAGE_FAC, " +
							   "CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME " +
							   "FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject Asr object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleAsrDao(UserToken userToken,Asr searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_ASR);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject Asr object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleAsrDao(UserToken userToken,Asr searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_ASR);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject Asr object
	 */
	public OracleAsrDao(UserToken userToken,Asr searchObject)
	{
		this.setEntityTable(DBConst.TABLE_ASR);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleAsrDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_ASR);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject Asr object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Asr searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(Asr,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject Asr object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Asr searchObject) throws TCGMException
	{
		String methodName = "getRS(Asr)";
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
			if (this.sortObject.getSortColumn().equalsIgnoreCase(DBConst.COL_DEF))
			{
				this.sortObject.setSortColumn(DBConst.COL_ASR_DEF);
			}
			String query = this.MIDDLE_SELECT_START +
				  this.INNER_SELECT +
				  this.getEntity() +
				  this.genWhereClause() +
				  //commneted for sort order - Gain 04-06-06
				  this.buildEBCDICSortClause(this.sortObject) +
//				  " ORDER BY RPT_AFF,RPT_LIST,RPT_PACK "+
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + this.pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + this.pagingFilter.getEndRecord();
			}

			this.logger.debug("OracleAsrDao - QUERY: " + query);

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
	 * @param searchObject Asr object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO(Asr searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(Asr,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject Asr object with search criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO(Asr searchObject) throws TCGMException
	{
		String methodName="getVO(Asr)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of Asr objects
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
				vec.add(this.getAsrFromCurrentRow(rs));
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
	 * @return Asr
	 * @throws TCGMException
	 */
	public Asr getAsrFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getAsrFromCurrentRow(RowSet)";

		Asr asr = new Asr();

		try
		{
			asr.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));
			asr.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			asr.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			asr.setSupAff(rs.getString(DBConst.COL_SUP_AFF));

			asr.getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			asr.getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			asr.getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			asr.getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			asr.getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			asr.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			asr.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			asr.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			asr.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			asr.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			asr.setProductOrigin(rs.getString(DBConst.COL_PROD_ORIGIN));
			asr.setSupKey(rs.getString(DBConst.COL_SUP_KEY));
			asr.setUsage(rs.getString(DBConst.COL_USAGE_FAC));

			asr.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			asr.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			asr.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			asr.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return asr;
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
		this.searchList.add(new Search(DBConst.COL_PROD_ORIGIN,searchObject.getProductOrigin(),comparisonType(searchObject.getProductOrigin())));	
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),comparisonType(searchObject.getRptAff())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode(),comparisonType(searchObject.getRptProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList(),comparisonType(searchObject.getRptProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),comparisonType(searchObject.getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),comparisonType(searchObject.getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack(),comparisonType(searchObject.getRptProduct().getPack())));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),comparisonType(searchObject.getSupAff())));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode(),comparisonType(searchObject.getSupProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList(),comparisonType(searchObject.getSupProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),comparisonType(searchObject.getSupProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),comparisonType(searchObject.getSupProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack(),comparisonType(searchObject.getSupProduct().getPack())));
		this.searchList.add(new Search(DBConst.COL_SUP_KEY,searchObject.getSupKey(),comparisonType(searchObject.getSupKey())));
		this.searchList.add(new Search(DBConst.COL_USAGE_FAC,TCGMUtil.getNumTrimLeadZero(searchObject.getUsage()),comparisonType(TCGMUtil.getNumTrimLeadZero(searchObject.getUsage()))));
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
		this.searchList.add(new Search(DBConst.COL_PROD_ORIGIN,searchObject.getProductOrigin(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_KEY,searchObject.getSupKey(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_USAGE_FAC,searchObject.getUsage(),TCGMConstants.ORACLE_IN_COMPARISON));
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
	 * @param searchObject Asr
	 */
	private void setSearchObject(Asr searchObject)
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
	private Asr getSearchObject()
	{
		return this.searchObject;
	}
}