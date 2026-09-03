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
public interface SalesTypeDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject SalesType object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(SalesType searchObject) throws TCGMException;

	/**
	 * @param searchObject SalesType object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(SalesType searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of SalesType objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject SalesType object
	 * @return Vector of SalesType objects
	 * @throws TCGMException
	 */
	public Vector getVO(SalesType searchObject) throws TCGMException;

	/**
	 * @param searchObject SalesType object
	 * @param sortObject contains sort criteria
	 * @return Vector of SalesType objects
	 * @throws TCGMException
	 */
	public Vector getVO(SalesType searchObject,Sort sortObject) throws TCGMException;

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
	public SalesType getSalesTypeFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 *
	 * @return SalesType
	 * @throws TCGMException
	 */
	public SalesType getSalesTypeBySlsType() throws TCGMException;

	/**
	 *
	 * @param slsTypeToInsert
	 * @throws TCGMException
	 */
	public void insert(UserToken userToken,SalesType slsTypeToInsert) throws TCGMException;

	/**
	 *
	 * @param slsTypeToUpdate
	 * @throws TCGMException
	 */
	public void update(UserToken userToken,SalesType slsTypeToUpdate) throws TCGMException;

	/**
	 *
	 * @param slsTypeToDelete
	 * @param conn
	 * @throws TCGMException
	 */
	public void delete(SalesType slsTypeToDelete,Connection conn) throws TCGMException;

	/**
	 *
	 * @param slsTypesToDelete
	 * @throws TCGMException
	 */
	public void delete(Vector slsTypesToDelete) throws TCGMException;
}