package abbott.ai.tcgm.data;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: NotesTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface NotesTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject NotesTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(NotesTran searchObject) throws TCGMException;

	/**
	 * @param searchObject NotesTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(NotesTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject NotesTran object with search criteria
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(NotesTran searchObject) throws TCGMException;

	/**
	 * @param searchObject NotesTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(NotesTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param notesTranList Vector of NotesTran objects
	 * @throws TCGMException
	 */
// A.Winter - 7/27/05 changed return type to boolean	
	public boolean insert(Vector notesTranList) throws TCGMException, TCGMDuplicateItemException;

	/**
	 *
	 * @param notesTran NotesTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
//	A.Winter - 7/27/05 changed return type to boolean	
	public boolean insert(NotesTran notesTran,Connection conn) throws TCGMException, TCGMDuplicateItemException;

	/**
	 * @param notesTran NotesTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(NotesTran notesTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param notesTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector notesTranList) throws TCGMException;

	/**
	 * @param notesTran NotesTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(NotesTran notesTran,Connection conn) throws TCGMException;

	/**
	 * @param notesTranList Vector of NotesTran objects
	 * @throws TCGMException
	 */
	public void update(Vector notesTranList) throws TCGMException;

	/**
	 * @throws TCGMException
	 */
	public void publishAll(NotesTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param searchObject NotesTran
	 * @param newVals NotesTran
	 * @throws TCGMException
	 */
	public void massUpdate(NotesTran searchObject,NotesTran newVals) throws TCGMException;

	/**
	 * @param notesTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector notesTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException;

	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(NotesTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException;
}