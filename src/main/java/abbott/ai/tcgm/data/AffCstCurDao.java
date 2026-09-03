package abbott.ai.tcgm.data;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: Aff Cst Cur Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface AffCstCurDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject AffCstCur object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffCstCur searchObject) throws TCGMException;

	/**
	 * @param searchObject AffCstCur object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffCstCur searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject AffCstCur object
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getVO(AffCstCur searchObject) throws TCGMException;

	/**
	 * @param searchObject AffCstCur object
	 * @param sortObject contains sort criteria
	 * @return Vector of AffCstCur objects
	 * @throws TCGMException
	 */
	public Vector getVO(AffCstCur searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param rs
	 * @return
	 * @throws TCGMException
	 */
	public AffCstCur getAffCstCurFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 *
	 * @return AffCstCur
	 * @throws TCGMException
	 */
	public AffCstCur getAffCstCurByAff() throws TCGMException;

	/**
	 *
	 * @param affCstCurToInsert
	 * @throws TCGMException
	 */
	public void insert(UserToken userToken,AffCstCur affCstCurToInsert) throws TCGMException;

	/**
	 *
	 * @param affCstCurToUpdate
	 * @throws TCGMException
	 */
	public void update(UserToken userToken,AffCstCur affCstCurToUpdate) throws TCGMException;

	/**
	 *
	 * @param affCstCurToDelete
	 * @param conn
	 * @throws TCGMException
	 */
	public void delete(AffCstCur affCstCurToDelete,Connection conn) throws TCGMException;

	/**
	 *
	 * @param affCstCursToDelete
	 * @throws TCGMException
	 */
	public void delete(Vector affCstCursToDelete) throws TCGMException;
}