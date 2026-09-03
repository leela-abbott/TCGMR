package abbott.ai.tcgm.data;

import javax.sql.*;
import java.util.*;
import java.sql.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface RateExTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject RateExTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateExTran searchObject) throws TCGMException;

	/**
	 * @param searchObject RateExTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateExTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject RateExTran object with search criteria
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateExTran searchObject) throws TCGMException;

	/**
	 * @param searchObject RateExTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateExTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param rateExTranList Vector of RateExTran objects
	 * @throws TCGMException
	 */
// A.Winter - change to boolean
	public boolean insert(Vector rateExTranList) throws TCGMException;

	/**
	 *
	 * @param rateExTran RateExTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
// A.Winter - change to boolean
	public boolean insert(RateExTran rateExTran,Connection conn) throws TCGMException;

	/**
	 *
	 * @param rateExTran RateExTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(RateExTran rateExTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param rateExTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector rateExTranList) throws TCGMException;

	/**
	 * @param rateExTran RateExTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(RateExTran rateExTran,Connection conn) throws TCGMException;

	/**
	 * @param rateExTranList Vector of RateExTran objects
	 * @throws TCGMException
	 */
	public void update(Vector rateExTranList) throws TCGMException;

	/**
	 * @throws TCGMException
	 */
	public void publishAll(RateExTran searchObject) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 *
	 * @param searchObject RateExTran
	 * @param newVals RateExTran
	 * @throws TCGMException
	 */
	public void massUpdate(RateExTran searchObject,RateExTran newVals) throws TCGMException;

	/**
	 * @param rateExTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector rateExTranList,String copyToModel) throws TCGMException;

	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(RateExTran searchObject,String copyToModel) throws TCGMException;
}