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
public interface BpcsDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject Bpcs object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Bpcs searchObject) throws TCGMException;

	/**
	 * @param searchObject Bpcs object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Bpcs searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject Bpcs object
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public Vector getVO(Bpcs searchObject) throws TCGMException;

	/**
	 * @param searchObject Bpcs object
	 * @param sortObject contains sort criteria
	 * @return Vector of Bpcs objects
	 * @throws TCGMException
	 */
	public Vector getVO(Bpcs searchObject,Sort sortObject) throws TCGMException;

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
	public Bpcs getBpcsFromCurrentRow(RowSet rs) throws TCGMException;
}