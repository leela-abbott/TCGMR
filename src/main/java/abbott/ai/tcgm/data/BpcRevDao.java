package abbott.ai.tcgm.data;

import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public interface BpcRevDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject BpcRev object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRev searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcRev object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRev searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject BpcRev object
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRev searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcRev object
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcRev objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRev searchObject,Sort sortObject) throws TCGMException;

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
	public BpcRev getBpcRevFromCurrentRow(RowSet rs) throws TCGMException;
}