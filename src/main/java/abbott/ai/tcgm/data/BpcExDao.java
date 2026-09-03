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
public interface BpcExDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject BpcEx object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcEx searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcEx object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcEx searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject BpcEx object
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcEx searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcEx object
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcEx searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 * @param rs RowSet
	 * @return BpcEx
	 * @throws TCGMException
	 */
	public BpcEx getBpcExFromCurrentRow(RowSet rs) throws TCGMException;
}