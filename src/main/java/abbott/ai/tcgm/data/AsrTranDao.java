package abbott.ai.tcgm.data;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: AsrTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface AsrTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject AsrTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AsrTran searchObject) throws TCGMException;

	/**
	 * @param searchObject AsrTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AsrTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject AsrTran object with search criteria
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(AsrTran searchObject) throws TCGMException;

	/**
	 * @param searchObject AsrTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(AsrTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param asrTranList Vector of AsrTran objects
	 * @throws TCGMException
	 */
// A.Winter - 7/8/05 - changed return type	
	public boolean insert(Vector asrTranList) throws TCGMException, TCGMDuplicateItemException;

	/**
	 *
	 * @param asrTran AsrTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
//	public void insert(AsrTran asrTran,Connection conn) throws TCGMException, TCGMDuplicateItemException;

	/**
	 * @param asrTran AsrTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
//	A.Winter - 7/15/05 - changed return type		
	public boolean insert(AsrTran asrTran,Connection conn) throws TCGMException, TCGMDuplicateItemException;
	
	/**
	 * @param asrTranList Vector object
	 * @param conn Connection
	 * @return Vector of asrtranErrorList
	 * @throws TCGMException
	 */
//		A.Winter - 7/15/05 - changed return type		
	public Vector insert(Vector asrTranList,Connection conn) throws TCGMException, TCGMDuplicateItemException;	

		/**
		 * @param asrTran AsrTran object
		 * @param conn Connection
		 * @throws TCGMException
		 */
	public void delete(AsrTran asrTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param asrTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector asrTranList) throws TCGMException;

	/**
	 * @param asrTran AsrTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(AsrTran asrTran,Connection conn) throws TCGMException;

	/**
	 * @param asrTranList Vector of AsrTran objects
	 * @throws TCGMException
	 */
	public void update(Vector asrTranList) throws TCGMException;

	/**
	 *
	 * @param searchObject
	 * @throws TCGMException
	 */
	public void publishAll(AsrTran searchObject, boolean blnFalg) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param searchObject AsrTran
	 * @param newVals AsrTran
	 * @throws TCGMException
	 */
	public void massUpdate(AsrTran searchObject,AsrTran newVals) throws TCGMException;

	/**
	 * @param asrTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector asrTranList,String copyToModel) throws TCGMException, TCGMDuplicateItemException;

	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(AsrTran searchObject,String copyToModel) throws TCGMException, TCGMDuplicateItemException;
}