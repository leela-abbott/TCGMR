package abbott.ai.tcgm.data;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: RateDataTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface RateDataTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject RateDataTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateDataTran searchObject) throws TCGMException;

	/**
	 * @param searchObject RateDataTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(RateDataTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject RateDataTran object with search criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateDataTran searchObject) throws TCGMException;

	/**
	 * @param searchObject RateDataTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(RateDataTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param rateDataTranList Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public boolean insert(Vector rateDataTranList) throws TCGMException;

	/**
	 *
	 * @param rateDataTran RateDataTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
	public boolean insert(RateDataTran rateDataTran,Connection conn) throws TCGMException;
	/**
	 *
	 * @param rateDataTran RateDataTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(RateDataTran rateDataTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param rateDataTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector rateDataTranList) throws TCGMException;

	/**
	 * @param rateDataTran RateDataTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(RateDataTran rateDataTran,Connection conn) throws TCGMException;

	/**
	 * @param rateDataTranList Vector of RateDataTran objects
	 * @throws TCGMException
	 */
	public void update(Vector rateDataTranList) throws TCGMException;
	/**
	 * @throws TCGMException
	 */
	public void publishAll(RateDataTran searchObject) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param searchObject RateDataTran
	 * @param newVals RateDataTran
	 * @throws TCGMException
	 */
	public void massUpdate(RateDataTran searchObject,RateDataTran newVals) throws TCGMException;

	/**
	 * @param asrTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
// 4-21-03 Copy function Not Applicable in Rate Data
//	public void copy(Vector asrTranList,String copyToModel) throws TCGMException;

	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
// 4-21-03 Copy function Not Applicable in Rate Data
//	public void copy(RateDataTran searchObject,String copyToModel) throws TCGMException;
}