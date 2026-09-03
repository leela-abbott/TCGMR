package abbott.ai.tcgm.data;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: TCGM Application</p>
 * <p>Description: CurrencyCode Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface CurrencyCodeDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject CurrencyCode object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(CurrencyCode searchObject) throws TCGMException;

	/**
	 * @param searchObject CurrencyCode object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(CurrencyCode searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject CurrencyCode object
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getVO(CurrencyCode searchObject) throws TCGMException;

	/**
	 * @param searchObject CurrencyCode object
	 * @param sortObject contains sort criteria
	 * @return Vector of CurrencyCode objects
	 * @throws TCGMException
	 */
	public Vector getVO(CurrencyCode searchObject,Sort sortObject) throws TCGMException;

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
	public CurrencyCode getCurrencyCodeFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 *
	 * @return CurrencyCode
	 * @throws TCGMException
	 */
	public CurrencyCode getCurrencyByCode() throws TCGMException;

	/**
	 *
	 * @param currencyCodeToInsert
	 * @throws TCGMException
	 */
	public void insert(CurrencyCode currencyCodeToInsert) throws TCGMException;

	/**
	 *
	 * @param currencyCodeToUpdate
	 * @throws TCGMException
	 */
	public void update(CurrencyCode currencyCodeToUpdate) throws TCGMException;

	/**
	 *
	 * @param currencyCodeToDelete
	 * @param conn
	 * @throws TCGMException
	 */
	public void delete(CurrencyCode currencyCodeToDelete,Connection conn) throws TCGMException;

	/**
	 *
	 * @param currencysToDelete
	 * @throws TCGMException
	 */
	public void delete(Vector currencysToDelete) throws TCGMException;
}