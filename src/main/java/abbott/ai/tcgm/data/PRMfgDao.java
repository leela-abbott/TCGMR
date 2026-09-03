package abbott.ai.tcgm.data;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: PR Mfg Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface PRMfgDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject PRMfg object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(PRMfg searchObject) throws TCGMException;

	/**
	 * @param searchObject PRMfg object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(PRMfg searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject PRMfg object
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getVO(PRMfg searchObject) throws TCGMException;

	/**
	 * @param searchObject PRMfg object
	 * @param sortObject contains sort criteria
	 * @return Vector of PRMfg objects
	 * @throws TCGMException
	 */
	public Vector getVO(PRMfg searchObject,Sort sortObject) throws TCGMException;

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
	public PRMfg getPRMfgFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 *
	 * @param prMfgToInsert
	 * @throws TCGMException
	 */
	public void insert(PRMfg prMfgToInsert) throws TCGMException;

	/**
	 *
	 * @param prMfgToDelete
	 * @param conn
	 * @throws TCGMException
	 */
	public void delete(PRMfg prMfgToDelete,Connection conn) throws TCGMException;

	/**
	 *
	 * @param prMfgsToDelete
	 * @throws TCGMException
	 */
	public void delete(Vector prMfgsToDelete) throws TCGMException;
}