package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
//import org.apache.log4j.*;
/**
 * <p>Title: TCGM </p>
 * <p>Description: Oracle Specific implementation of the Notes Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleNotesDao extends OracleDao implements NotesDao
{
	private Notes searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_NOTES;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID, " +
									  "RPT_AFF, RPT_INV_CD, RPT_LIST, RPT_PACK, " +
			"RPT_LABEL, RPT_SIZE, NOTE, CREATE_USERNAME, CREATE_DATETIME, MODIFY_USERNAME, " +
			"MODIFY_DATETIME FROM (";

	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param sortObject Sort object
	 */
	public OracleNotesDao(UserToken userToken, PagingFilter pagingFilter, Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_NOTES);
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject Notes object
	 * @param sortObject Sort object
	 */
	public OracleNotesDao(UserToken userToken,Notes searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_NOTES);
		this.userToken = userToken;
		this.sortObject = sortObject;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject Notes object
	 */
	public OracleNotesDao(UserToken userToken,Notes searchObject, PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_NOTES);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject Notes object
	 */
	public OracleNotesDao(UserToken userToken,Notes searchObject)
	{
		this.setEntityTable(DBConst.TABLE_NOTES);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleNotesDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_NOTES);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject Notes object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Notes searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(Notes,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject Notes object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Notes searchObject) throws TCGMException
	{
		String methodName = "getRS(Notes)";
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
			this.sortObject.setSortColumn(DBConst.COL_NOTES_DEF);
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

			this.logger.debug("OracleNotesDao - QUERY: " + query);

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
	 * @param searchObject Notes object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getVO(Notes searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(Notes,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject Notes object with search criteria
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getVO(Notes searchObject) throws TCGMException
	{
		String methodName="getVO(Notes)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 *
	 * @return Vector of Notes objects
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
				vec.add(this.getNotesFromCurrentRow(rs));
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
	 * This method will be used to convert the "next()" RowSet ojbect to an NOTES object
	 * @param rs RowSet
	 * @return Notes
	 * @throws TCGMException
	 */
	public Notes getNotesFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getNotesFromCurrentRow(RowSet)";

		Notes notes = new Notes();

		try
		{
			notes.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));
			notes.setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			notes.setRptAff(rs.getString(DBConst.COL_RPT_AFF));

			notes.getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			notes.getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			notes.getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			notes.getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			notes.getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			notes.setNote(rs.getString(DBConst.COL_NOTE));

			notes.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			notes.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));
			notes.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			notes.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			notes.setNewNotes(false);

			return notes;
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

		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),comparisonType(searchObject.getRptAff())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode(),comparisonType(searchObject.getRptProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList(),comparisonType(searchObject.getRptProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),comparisonType(searchObject.getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),comparisonType(searchObject.getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack(),comparisonType(searchObject.getRptProduct().getPack())));

		this.searchList.add(new Search(DBConst.COL_NOTE,searchObject.getNote(),comparisonType(searchObject.getNote())));
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

		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getRptProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getRptProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getRptProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getRptProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getRptProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));

		this.searchList.add(new Search(DBConst.COL_NOTE,searchObject.getNote(),TCGMConstants.ORACLE_IN_COMPARISON));
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
		sb.append(this.sortObject.toString());
		sb.append("\n");
		sb.append(this.pagingFilter.toString());
		sb.append("\nSearch Object: ");
		sb.append(this.searchObject);

		return sb.toString();
	}

	/*****************************************************************************************/
	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject Notes
	 */
	private void setSearchObject(Notes searchObject)
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
	private Notes getSearchObject()
	{
		return this.searchObject;
	}
}