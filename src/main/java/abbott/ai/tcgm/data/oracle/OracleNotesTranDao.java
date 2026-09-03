package abbott.ai.tcgm.data.oracle;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: Oracle Specific implementation of the NotesTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public class OracleNotesTranDao extends OracleDao implements NotesTranDao
{
	private NotesTran searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_NOTES_TRAN;
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID, ACD, " +
									  "RPT_AFF, RPT_INV_CD, RPT_LIST, RPT_PACK, " +
			"RPT_LABEL, RPT_SIZE, NOTE, CREATE_USERNAME, CREATE_DATETIME, MODIFY_USERNAME, " +
			"MODIFY_DATETIME, PUBLISH_FLAG, NOTES_T_ID FROM (";
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken object
	 * @param searchObject NotesTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 */
	public OracleNotesTranDao(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_NOTES_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject NotesTran object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleNotesTranDao(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_NOTES_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 * @param userToken UserToken object
	 * @param searchObject NotesTran object
	 */
	public OracleNotesTranDao(UserToken userToken,NotesTran searchObject)
	{
		this.setEntityTable(DBConst.TABLE_NOTES_T);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 * @param userToken UserToken object
	 */
	public OracleNotesTranDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_NOTES_T);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject NotesTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(NotesTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(NotesTran,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject NotesTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(NotesTran searchObject) throws TCGMException
	{
		String methodName = "getRS(NotesTran)";
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
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + pagingFilter.getEndRecord();
			}

			this.logger.debug("OraceNotesTranDao - QUERY: " + query);

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
	 * @param searchObject NotesTran object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(NotesTran searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(NotesTran, Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 * @param searchObject NotesTran object with search criteria
	 * @return vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(NotesTran searchObject) throws TCGMException
	{
		String methodName = "getVO(NotesTran)";
		this.setSearchObject(searchObject);
		return this.getVO();
	}
	/**
	 * @return Vector of NotesTran objects
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
				vec.add(this.getNotesTranFromCurrentRow(rs));
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
	 * @param rs RowSet
	 * @return NotesTran
	 * @throws TCGMException
	 */
	private NotesTran getNotesTranFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getNotesTranFromCurrentRow(RowSet)";
		NotesTran notesTran = new NotesTran();

		try
		{
			notesTran.getNotes().setDatasetTableIdInt(rs.getInt(DBConst.COL_DATASET_TABLE_ID));
			notesTran.getNotes().setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			notesTran.setActionCode(rs.getString(DBConst.COL_ACD));
			notesTran.getNotes().setRptAff(rs.getString(DBConst.COL_RPT_AFF));

			notesTran.getNotes().getRptProduct().setInvCode(rs.getString(DBConst.COL_RPT_INV_CD));
			notesTran.getNotes().getRptProduct().setList(rs.getString(DBConst.COL_RPT_LIST));
			notesTran.getNotes().getRptProduct().setLabel(rs.getString(DBConst.COL_RPT_LABEL));
			notesTran.getNotes().getRptProduct().setSize(rs.getString(DBConst.COL_RPT_SIZE));
			notesTran.getNotes().getRptProduct().setPack(rs.getString(DBConst.COL_RPT_PACK));

			notesTran.setPublishFlag(rs.getString(DBConst.COL_PUBLISH_FLAG));
			notesTran.getNotes().setNote(rs.getString(DBConst.COL_NOTE));
			notesTran.setNotesTranId(rs.getString(DBConst.COL_NOTES_T_ID));


			notesTran.getNotes().getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			notesTran.getNotes().getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			notesTran.getNotes().getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			notesTran.getNotes().getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));

			return notesTran;
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
	 * in the vector into the Notes_T table.  The connection is created/closed internally.
	 * @param notesTranList Vector of NotesTran objects
	 * @throws TCGMException
	 */
//	Alex Winter - 7/27//05	
	public boolean insert(Vector notesTranList) throws TCGMException
	{
		String methodName = "insert(Vector)";
//		Alex Winter - 7/27//05
        boolean duplic = true;
        boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < notesTranList.size();i++)
			{
//	Alex Winter - 7/27//05
			//	this.insert((NotesTran)notesTranList.elementAt(i),conn);
				NotesTran nt =(NotesTran)notesTranList.elementAt(i);
				duplic = this.insert(nt,conn);
				if(!duplic){
					blnFlag = false;
					nt.getNotes().setMsg("Duplicate Row");
				}	
				if(nt.getNotesTranId().equals("DUP"))
				 {
					blnFlag = false;	
				 // break;
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
	 * This method calls a stored procedure to physically insert a record into the Notes_T table
	 * If the connection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method.
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_RPT_AFF
	 * p_RPT_INV_CD
	 * p_RPT_LIST
	 * p_RPT_LABEL
	 * p_RPT_SIZE
	 * p_RPT_PACK
	 * p_PUBLISH_FLAG
	 * p_NOTES
	 * @param notesTran NotesTran objects
	 * @param conn Connection to the database
	 * @throws TCGMException
	 */
//	A.Winter 7/26/05 - changed method to boolean		
	public boolean insert(NotesTran notesTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "insert(NotesTran,Connection)";
		boolean connWasNull = false;
//		A.Winter 7/27/2005 - add boolean pointer		
		boolean duplic = true;

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

			//String sql = "{ call " + this.schema + ".NOTES_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_NOTES_TRAN_CREATE(?,?,?,?,?,?,?,?,?,?,?,?,?) }";

			cs = conn.prepareCall(sql);

			cs.setInt( 1, Integer.parseInt(notesTran.getNotes().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(notesTran.getNotes().getModelId()));
			cs.setString( 3, notesTran.getActionCode());
			cs.setString( 4, notesTran.getNotes().getRptAff());
			cs.setString( 5, notesTran.getNotes().getRptProduct().getInvCode());
			cs.setString( 6, notesTran.getNotes().getRptProduct().getList());
			cs.setString( 7, this.updColDefault(notesTran.getNotes().getRptProduct().getLabel()," "));
			cs.setString( 8, this.updColDefault(notesTran.getNotes().getRptProduct().getSize()," "));
			cs.setString( 9, notesTran.getNotes().getRptProduct().getPack());
			cs.setString( 10, this.updColDefault(notesTran.getNotes().getNote()," ") );
			cs.setString( 11, notesTran.getPublishFlag() );
			cs.setString( 12, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );			
			cs.setString( 13, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );

			cs.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			if(sqle.getMessage().startsWith("ORA-00001"))
			{
		// A.Winter 7/26/05 Error was a unique constraint error
				notesTran.setNotesTranId("DUP");
				duplic = false;
				if(!duplic){ // means duplicate record
				  notesTran.getNotes().setMsg("Duplicate Row");
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
	 * This method calls a stored procedure to physically update a record based on the NotesTranId
	 * If the conection is not passed in, it will be created/closed within this method
	 * If the connection IS passed in, it will need to be closed by the calling method
	 * Order of params to the stored procedure
	 * p_DATASET_TABLE_ID
	 * p_MODEL_ID
	 * p_ACD
	 * p_RPT_AFF
	 * p_RPT_INV_CD
	 * p_RPT_LIST
	 * p_RPT_LABEL
	 * p_RPT_SIZE
	 * p_RPT_PACK
	 * p_NOTES_T_ID
	 * @param notesTran NotesTran objects
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(NotesTran notesTran,Connection conn) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(NotesTran)";
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

			//String sql = "{ call " + this.schema + ".NOTES_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			String sql = "{ call " + this.schema + ".APPLY_MAINTENANCE.BUILD_NOTES_TRAN_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			cs = conn.prepareCall(sql);
			cs.setInt( 1, Integer.parseInt(notesTran.getNotes().getDatasetTableId()));
			cs.setInt( 2, Integer.parseInt(notesTran.getNotes().getModelId()));
			cs.setString( 3, notesTran.getActionCode());
			cs.setString( 4, notesTran.getNotes().getRptAff());
			cs.setString( 5, notesTran.getNotes().getRptProduct().getInvCode());
			cs.setString( 6, notesTran.getNotes().getRptProduct().getList());
			cs.setString( 7, this.updColDefault(notesTran.getNotes().getRptProduct().getLabel()," "));
			cs.setString( 8, this.updColDefault(notesTran.getNotes().getRptProduct().getSize()," "));
			cs.setString( 9, notesTran.getNotes().getRptProduct().getPack());
			cs.setString( 10, this.updColDefault(notesTran.getNotes().getNote()," "));
			cs.setString( 11, notesTran.getPublishFlag());
			cs.setString( 12, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );			
			cs.setString( 13, this.updColDefault(this.userToken.getUserid().trim(),"Anonymous") );
			cs.setString( 14, notesTran.getNotesTranId());

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
	 * This method will loop through the given vector and update each NotesTran object in the collection
	 * based on the NotesTranId
	 * The connection is created internally
	 * @param notesTranList Vector
	 * @throws TCGMException
	 */
	public void update(Vector notesTranList) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "update(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < notesTranList.size();i++)
			{
				this.update((NotesTran)notesTranList.elementAt(i),conn);
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
	public void publishAll(NotesTran notesTran, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodName = "publishAll";

		String sql = "";
		if(blnFlag){
			sql = this.UPDATE + this.getEntity() + this.SET_PUBLISHED + this.genWhereClause();
		}else{
			sql = this.UPDATE + this.getEntity() + this.SET_UNPUBLISHED + this.genWhereClause();
		}

		this.logger.debug("SQL: " + sql);

		this.setSearchObject(notesTran);
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
	 * 2.  Get the notes object from the rowset
	 * 3.  Set the values
	 * 4.  Update record
	 * @param searchObject NotesTran
	 * @param newVals NotesTran
	 * @throws TCGMException
	 */
	public void massUpdate(NotesTran searchObject,NotesTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(NotesTran,NotesTran)";

		RowSet rs = this.getRS(searchObject);

		try
		{
			while(rs.next())
			{
				NotesTran notesTran = this.getNotesTranFromCurrentRow(rs);

				notesTran.setActionCode(this.updCol(notesTran.getActionCode(),newVals.getActionCode()));
				notesTran.setPublishFlag(this.updCol(notesTran.getPublishFlag(),newVals.getPublishFlag()));
				notesTran.getNotes().setRptAff(this.updCol(notesTran.getNotes().getRptAff(),newVals.getNotes().getRptAff()));
				notesTran.getNotes().getRptProduct().setInvCode(this.updCol(notesTran.getNotes().getRptProduct().getInvCode(),newVals.getNotes().getRptProduct().getInvCode()));
				notesTran.getNotes().getRptProduct().setList(this.updCol(notesTran.getNotes().getRptProduct().getList(),newVals.getNotes().getRptProduct().getList()));
				notesTran.getNotes().getRptProduct().setLabel(this.updCol(notesTran.getNotes().getRptProduct().getLabel(),newVals.getNotes().getRptProduct().getLabel()));
				notesTran.getNotes().getRptProduct().setSize(this.updCol(notesTran.getNotes().getRptProduct().getSize(),newVals.getNotes().getRptProduct().getSize()));
				notesTran.getNotes().getRptProduct().setPack(this.updCol(notesTran.getNotes().getRptProduct().getPack(),newVals.getNotes().getRptProduct().getPack()));
				notesTran.getNotes().setNote(this.updCol(notesTran.getNotes().getNote(),newVals.getNotes().getNote()));


				this.update(notesTran,this.getConnection());
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
	 * @param notesTran NotesTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(NotesTran notesTran,Connection conn) throws TCGMException,
																   TCGMUpdateWithBlankUsernameException
	{
		String methodName = "delete(NotesTran,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(notesTran);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains an notesTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(notesTran.getNotesTranId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			sql += " where NOTES_T_ID = " + notesTran.getNotesTranId();
		}
		
		/*   The below code is added to refine the sql when a "ALL" is selected as the User ID in the
				 *   GUI--  Gain 03/09/2006			 */
		
				if (notesTran.getNotes().getCreateLog().getUserName().equalsIgnoreCase("ALL")){
			
					int andIndex =sql.lastIndexOf("AND");
					sql=sql.substring(0,andIndex);
			
				}
		this.logger.debug("\nSQL: " + sql);

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
	 * @param notesTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector notesTranList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < notesTranList.size();i++)
			{
				this.delete((NotesTran)notesTranList.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	}

	/*****************************************************************************************/
	/**
	 * Builds the vector of Search objects to be used by the genWhereClause method.
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getNotes().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getNotes().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode(),comparisonType(searchObject.getActionCode())));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getNotes().getRptAff(),comparisonType(searchObject.getNotes().getRptAff())));
		this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getNotes().getRptProduct().getInvCode(),comparisonType(searchObject.getNotes().getRptProduct().getInvCode())));
		this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getNotes().getRptProduct().getList(),comparisonType(searchObject.getNotes().getRptProduct().getList())));
		this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getNotes().getRptProduct().getLabel(),comparisonType(searchObject.getNotes().getRptProduct().getLabel())));
		this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getNotes().getRptProduct().getSize(),comparisonType(searchObject.getNotes().getRptProduct().getSize())));
		this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getNotes().getRptProduct().getPack(),comparisonType(searchObject.getNotes().getRptProduct().getPack())));

		this.searchList.add(new Search(DBConst.COL_NOTE,searchObject.getNotes().getNote(),comparisonType(searchObject.getNotes().getNote())));
		this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag(),comparisonType(searchObject.getPublishFlag())));
		this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getNotes().getCreateLog().getUserName(),TCGMConstants.ORACLE_EQUALS_COMPARISON));
	}
	/*****************************************************************************************/
		/**
		 * Builds the vector of Search objects to be used by the genWhereClause method.
		 */
		private void buildAdvancedSearchList()
		{
			this.searchList = new Vector();

			//need to build a search object and then loop through it to get the clause.
			this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getNotes().getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
			this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getNotes().getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
			this.searchList.add(new Search(DBConst.COL_ACD,searchObject.getActionCode(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getNotes().getRptAff(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_INV_CD,searchObject.getNotes().getRptProduct().getInvCode(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_LIST,searchObject.getNotes().getRptProduct().getList(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_LABEL,searchObject.getNotes().getRptProduct().getLabel(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_SIZE,searchObject.getNotes().getRptProduct().getSize(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_RPT_PACK,searchObject.getNotes().getRptProduct().getPack(),TCGMConstants.ORACLE_IN_COMPARISON));

			this.searchList.add(new Search(DBConst.COL_NOTE,searchObject.getNotes().getNote(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_PUBLISH_FLAG,searchObject.getPublishFlag(),TCGMConstants.ORACLE_IN_COMPARISON));
			this.searchList.add(new Search(DBConst.COL_CREATE_USERNAME,searchObject.getNotes().getCreateLog().getUserName(),TCGMConstants.ORACLE_IN_COMPARISON));
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
		sb.append(this.searchObject.toString());

		return sb.toString();
	}
	/*****************************************************************************************/
	/**
	 * @param notesTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector notesTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(Vector,String)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < notesTranList.size();i++)
			{
				NotesTran notesTran = (NotesTran)notesTranList.elementAt(i);
				notesTran.getNotes().setModelId(copyToModel);
				this.insert(notesTran,conn);
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
	public void copy(NotesTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copy(NotesTran,String)";

		this.setSearchObject(searchObject);

		Vector vec = new Vector();
		Connection conn = null;

		try
		{
			this.getRS();

			conn = SQLUtil.openConnection( );

			while (rs.next())
			{
				NotesTran notesTran = this.getNotesTranFromCurrentRow(rs);
				notesTran.getNotes().setModelId(copyToModel);
				this.insert(notesTran,conn);
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
	 * @param searchObject NotesTran
	 */
	private void setSearchObject(NotesTran searchObject)
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
	private NotesTran getSearchObject()
	{
		return this.searchObject;
	}
}