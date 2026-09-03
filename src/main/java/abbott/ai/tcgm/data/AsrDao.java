package abbott.ai.tcgm.data;

import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: Asr Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface AsrDao extends TCGMDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject Asr object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Asr searchObject) throws TCGMException;

	/**
	 * @param searchObject Asr object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(Asr searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject Asr object
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO(Asr searchObject) throws TCGMException;


	/**
	 * @param searchObject Asr object
	 * @param sortObject contains sort criteria
	 * @return Vector of Asr objects
	 * @throws TCGMException
	 */
	public Vector getVO(Asr searchObject,Sort sortObject) throws TCGMException;

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
	public Asr getAsrFromCurrentRow(RowSet rs) throws TCGMException;
}