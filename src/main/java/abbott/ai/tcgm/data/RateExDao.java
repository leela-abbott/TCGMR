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
public interface RateExDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject RateEx object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateEx searchObject) throws TCGMException;

	/**
	 * @param searchObject RateEx object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateEx searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject RateEx object
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateEx searchObject) throws TCGMException;

	/**
	 * @param searchObject RateEx object
	 * @param sortObject contains sort criteria
	 * @return Vector of RateEx objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateEx searchObject,Sort sortObject) throws TCGMException;

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
	public RateEx getRateExFromCurrentRow(RowSet rs) throws TCGMException;
}