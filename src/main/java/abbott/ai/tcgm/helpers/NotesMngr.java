package abbott.ai.tcgm.helpers;

import java.util.*;
import java.sql.*;
import javax.sql.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class NotesMngr implements TCGMMngr
{
	public final String className = this.getClass().getName();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public NotesMngr()
	{}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Notes object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getNotes(UserToken userToken,Notes searchObject,PagingFilter pagingFilter, Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesDao notesDao = daoFactory.getNotesDao(userToken,searchObject,pagingFilter,sortObject);
		return notesDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param sortObject contains sort criteria
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getNotes(UserToken userToken,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesDao notesDao = daoFactory.getNotesDao(userToken,pagingFilter,sortObject);
		return notesDao.getVO();
	}

	/**
	 * 1.  Get a RowSet of Notes objects that match the search criteria.
	 * 2.  Loop through the rowset and convert each Notes to an NotesTran
	 * 3.  Call the notesTranDao.insert method to insert the record.
	 * 4.  Close the rowset
	 * @param userToken contains the id and password
	 * @param searchObject Notes object
	 * @param actionCode action code for notes tran
	 * @throws TCGMException
	 */
//	A.Winter 7/27/05 - chaged method to boolean		
	public boolean addAllNotesToTrans(UserToken userToken,Notes searchObject,String actionCode) throws TCGMException
	{
		String methodName = "addAllNotesToTrans";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesDao notesDao = daoFactory.getNotesDao(userToken,searchObject);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);
//		A.Winter 7/27/05		
		boolean res = true;	
		//The connection object is usually created inside of the dao.  The problem with this is that I am looping
		//here and I don't want to open/close the connection every time I create a record.  I will
		//open the connection 1 time here and then close it when I am done with it.
		Connection conn = SQLUtil.openConnection();
		RowSet rs = notesDao.getRS(searchObject);

		try
		{
			while (rs.next())
			{
				Notes notes = notesDao.getNotesFromCurrentRow(rs);
				NotesTran notesTran = this.convertNotesToTran(notes,actionCode);
			
			    res = notesTranDao.insert(notesTran,conn);
//		A.Winter - 7/27/05
				
		    	 if(res == false)
					  break;
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			throw new TCGMException(className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
			SQLUtil.closeConnection(conn);
		}
	 return res;	
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param notesList Vector of Notes objects
	 * @param actionCode action code for NotesTran
	 * @throws TCGMException
	 */
//	A.Winter 7/27/05 - changed method to boolean	
	public boolean addSelectedNotesToTrans(UserToken userToken,Vector notesList,String actionCode) throws TCGMException
	{
		String methodName = "addSelectedNotesToTrans";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		Vector notesTranList = null;
		Vector selectedNotesList = this.getSelectedNotes(notesList);

		notesTranList = convertNotesListToTran(selectedNotesList,actionCode);
//		A.Winter 7/27/05 - changed method to boolean
		boolean res = notesTranDao.insert(notesTranList);
	    return res; 
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param notesList Vector of Notes objects
	 * @param actionCode action code for NotesTran
	 * @return Vector
	 */
	public Vector convertNotesListToTran(Vector notesList,String actionCode)
	{
		String methodName = "convertNotesListToTran(Vector,String)";

		Vector notesTranList = new Vector();

		for(int i = 0; i < notesList.size(); i++)
		{
			notesTranList.add(convertNotesToTran((Notes)notesList.elementAt(i),actionCode) );
		}
		return notesTranList;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param notes Notes object
	 * @param actionCode action code for NotesTran
	 * @return NotesTran
	 */
	public NotesTran convertNotesToTran(Notes notes,String actionCode)
	{
		String methodName = "convertNotesToTran(Notes,String)";

		NotesTran notesTran = new NotesTran();
		notesTran.setNotes( notes );
		notesTran.setActionCode(actionCode);
		notesTran.getNotes().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
		notesTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);
		return notesTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getNotesTran(UserToken userToken,NotesTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken,searchObject);
		return notesTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getNotesTran(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken,searchObject,pagingFilter);
		return notesTranDao.getVO();
	}
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sorting criteria
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getNotesTran(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken,searchObject,pagingFilter,sortObject);
		return notesTranDao.getVO();
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param searchObject NotesTran
	 * @throws TCGMException
	 */
	public void deleteAllNotesTran(UserToken userToken,NotesTran searchObject) throws TCGMException,
																					  TCGMUpdateWithBlankUsernameException
	{
		String methodName = "deleteAllNotesTran(UserToken,NotesTran)";
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		notesTranDao.delete(searchObject,null);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param notesTranList Vector
	 * @throws TCGMException
	 */
	public void deleteSelectedNotesTran(UserToken userToken,Vector notesTranList) throws TCGMException
	{
		String methodName = "deleteSelectedNotesTran";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		Vector selectedNotesTranList = this.getSelectedNotesTran(notesTranList);

		notesTranDao.delete(selectedNotesTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param notesTranList Vector
	 * @throws TCGMException
	 */
	public void saveSelectedNotesTran(UserToken userToken,Vector notesTranList) throws TCGMException
	{
		String methodName = "saveNotesTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		Vector selectedNotesTranList = this.getSelectedNotesTran(notesTranList);

		notesTranDao.update(selectedNotesTranList);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param notesTranList Vector
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copySelectedNotesTran(UserToken userToken,Vector notesTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copySelectedNotesTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		Vector selectedNotesTranList = this.getSelectedNotesTran(notesTranList);

		notesTranDao.copy(selectedNotesTranList,copyToModel);
	}
	/**
	 * @param userToken UserToken
	 * @param searchObject NotesTran
	 * @param copyToModel String
	 * @throws TCGMException
	 */
	public void copyAllNotesTran(UserToken userToken,NotesTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "copyAllNotesTran(UserToken,Vector)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		notesTranDao.copy(searchObject,copyToModel);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param notesTranList Vector
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishSelectedNotesTran(UserToken userToken,Vector notesTranList, boolean blnFlag) throws TCGMException
	{
		String methodName = "publishNotesTran(UserToken,Vector, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		Vector selectedNotesTranList = this.getSelectedNotesTran(notesTranList);

		for(int i = 0; i<selectedNotesTranList.size(); i++)
		{
			if(blnFlag){
				((NotesTran)selectedNotesTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_PUBLISHED);
			}else{
				((NotesTran)selectedNotesTranList.elementAt(i)).setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);	
			}
		}

		notesTranDao.update(selectedNotesTranList);
	}
	/**
	 * @param userToken UserToken
	 * @param searchObject NotesTran
	 * @throws TCGMException
	 * If blnFlag is true then publish the records (P)
	 * If blnFlag is false then unpublish the records (U)
	 */
	public void publishAllNotesTran(UserToken userToken,NotesTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException
	{
		String methodNaem = "publishAllNotesTran(UserToken,NotesTran, boolean)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken,searchObject);

		notesTranDao.publishAll(searchObject, blnFlag);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param notesTran NotesTran
	 * @throws TCGMException
	 */
	public boolean addNewNotesTran(UserToken userToken,NotesTran notesTran) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "addNewNotesTran(UserToken,NotesTran";
		boolean dup = true; //Based on the existing code dup =true means there are no duplicates. :)
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);

		dup = notesTranDao.insert(notesTran,null);
		return dup;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject NotesTran
	 * @param newVals NotesTran
	 * @throws TCGMException
	 */
	public void massUpdate(UserToken userToken,NotesTran searchObject,NotesTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,NotesTran,NotesTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);
		notesTranDao.massUpdate(searchObject,newVals);
	}
	/**
	 * Used to do a mass update by creating trans records from existing notes records and replacing with values
	 * in newVals object.
	 *
	 * 1.  Get a rowset of Notes
	 * 2.  Create an notesTran and put the notes from the current row into it.
	 *
	 * @param userToken
	 * @param searchObject
	 * @param newVals
	 * @throws TCGMException
	 */
	public Vector massUpdate(UserToken userToken,Notes searchObject,NotesTran newVals) throws TCGMException, TCGMDuplicateItemException
	{
		String methodName = "massUpdate(UserToken,Notes,NotesTran)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesDao notesDao = daoFactory.getNotesDao(userToken,searchObject);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken);
		RowSet rs = notesDao.getRS();
		Vector notesDupRecList = new Vector();
		boolean result = true;
		boolean blnFlag = true;
		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection();

			while(rs.next())
			{
				NotesTran notesTran = new NotesTran();
				Notes notesObj = new Notes();
				notesObj = notesDao.getNotesFromCurrentRow(rs);
				notesTran.setNotes(notesObj);

				notesTran.setPublishFlag(TCGMConstants.FLAG_UNPUBLISHED);

				if(newVals.getActionCode().equals(""))
				{
					notesTran.setActionCode(TCGMUtil.getNewValue(notesTran.getActionCode(),TCGMConstants.ACT_CD_CHG));
				}
				else
				{
				notesTran.setActionCode(TCGMUtil.getNewValue(notesTran.getActionCode(),newVals.getActionCode()));
				}


				notesTran.setPublishFlag(TCGMUtil.getNewValue(notesTran.getPublishFlag(),newVals.getPublishFlag()));
				notesTran.getNotes().setRptAff(TCGMUtil.getNewValue(notesTran.getNotes().getRptAff(),newVals.getNotes().getRptAff()));
				notesTran.getNotes().getRptProduct().setInvCode(TCGMUtil.getNewValue(notesTran.getNotes().getRptProduct().getInvCode(),newVals.getNotes().getRptProduct().getInvCode()));
				notesTran.getNotes().getRptProduct().setList(TCGMUtil.getNewValue(notesTran.getNotes().getRptProduct().getList(),newVals.getNotes().getRptProduct().getList()));
				notesTran.getNotes().getRptProduct().setLabel(TCGMUtil.getNewValue(notesTran.getNotes().getRptProduct().getLabel(),newVals.getNotes().getRptProduct().getLabel()));
				notesTran.getNotes().getRptProduct().setSize(TCGMUtil.getNewValue(notesTran.getNotes().getRptProduct().getSize(),newVals.getNotes().getRptProduct().getSize()));
				notesTran.getNotes().getRptProduct().setPack(TCGMUtil.getNewValue(notesTran.getNotes().getRptProduct().getPack(),newVals.getNotes().getRptProduct().getPack()));
				notesTran.getNotes().setNote(TCGMUtil.getNewValue(notesTran.getNotes().getNote(),newVals.getNotes().getNote()));
				result = notesTranDao.insert(notesTran,conn);
				if(!result){
						blnFlag = false;
						notesTran.getNotes().setMsg("Duplicate Row");
						notesDupRecList.add(notesObj);
						}
			}
		}
		catch(SQLException sqle)
		{
			throw new TCGMException(className,methodName,sqle.toString());
		}
		finally
		{
			if(conn!=null)
			{
				SQLUtil.closeConnection(conn);
			}
		}
		return notesDupRecList;
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of NotesTran, returns the ones that have the selected flag set to true
	 * @param notesTranList Vector
	 * @return Vector
	 */
	public Vector getSelectedNotesTran(Vector notesTranList)
	{
		Vector selectedNotesTran = new Vector();

		for(int i = 0; i < notesTranList.size(); i++)
		{
			NotesTran notesTran = (NotesTran)notesTranList.elementAt(i);

			if(notesTran.getNotes().isSelected())
			{
				selectedNotesTran.add(notesTran);
			}
		}

		return selectedNotesTran;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject Notes object with search criteria
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,Notes searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesDao notesDao = daoFactory.getNotesDao(userToken,searchObject);
		return notesDao.getCount();
	}

	/**
	 *
	 * @param userToken contains user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public long getCount(UserToken userToken,NotesTran searchObject) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		NotesTranDao notesTranDao = daoFactory.getNotesTranDao(userToken,searchObject);
		return notesTranDao.getCount();
	}

	/*****************************************************************************************/
	/**
	 * Given a vector of Notes objects, returns the ones that have the selected flag set to true
	 * @param notesList Vector
	 * @return Vector
	 */
	private Vector getSelectedNotes(Vector notesList)
	{
		Vector selectedNotess = new Vector();

		for(int i = 0; i < notesList.size(); i++)
		{
			Notes notes = (Notes)notesList.elementAt(i);

			if(notes.isSelected())
			{
				selectedNotess.add(notes);
			}
		}

		return selectedNotess;
	}
}