package abbott.ai.tcgm.data;

//import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: Notes Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface NotesDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject Notes object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Notes searchObject) throws TCGMException;

	/**
	 * @param searchObject Notes object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Notes searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject Notes object
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getVO(Notes searchObject) throws TCGMException;

	/**
	 * @param searchObject Notes object
	 * @param sortObject contains sort criteria
	 * @return Vector of Notes objects
	 * @throws TCGMException
	 */
	public Vector getVO(Notes searchObject,Sort sortObject) throws TCGMException;

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
	public Notes getNotesFromCurrentRow(RowSet rs) throws TCGMException;


}