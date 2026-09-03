package abbott.ai.tcgm.data;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: Knoll Conv Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface KnollConvDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject KnollConv object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(KnollConv searchObject) throws TCGMException;

	/**
	 * @param searchObject KnollConv object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(KnollConv searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject KnollConv object
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getVO(KnollConv searchObject) throws TCGMException;

	/**
	 * @param searchObject KnollConv object
	 * @param sortObject contains sort criteria
	 * @return Vector of KnollConv objects
	 * @throws TCGMException
	 */
	public Vector getVO(KnollConv searchObject,Sort sortObject) throws TCGMException;

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
	public KnollConv getKnollConvFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 *
	 * @param knollConvToInsert
	 * @throws TCGMException
	 */
	public void insert(KnollConv knollConvToInsert) throws TCGMException;

	/**
	 *
	 * @param knollConvToDelete
	 * @param conn
	 * @throws TCGMException
	 */
	public void delete(KnollConv knollConvToDelete,Connection conn) throws TCGMException;

	/**
	 *
	 * @param knollConvsToDelete
	 * @throws TCGMException
	 */
	public void delete(Vector knollConvsToDelete) throws TCGMException;
}