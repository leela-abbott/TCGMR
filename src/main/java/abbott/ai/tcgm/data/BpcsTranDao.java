package abbott.ai.tcgm.data;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: BpcsTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface BpcsTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject BpcsTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcsTran searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcsTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcsTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject BpcsTran object with search criteria
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcsTran searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcsTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcsTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param bpcsTranList Vector of BpcsTran objects
	 * @throws TCGMException
	 */
// A.Winter - 7/26/05 - changed method signature to boolean	
	public boolean insert(Vector bpcsTranList) throws TCGMException;

	/**
	 *
	 * @param bpcsTran BpcsTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
//	A.Winter - 7/26/05 - changed method signature to boolean	
	public boolean insert(BpcsTran bpcsTran,Connection conn) throws TCGMException;
	/**
	 *
	 * @param bpcsTran BpcsTran object
	 * @param conn Connection object
	 * @param transitAff String
	 * @throws TCGMException
	 */
//		A.Winter - 7/26/05 - changed method signature to boolean	
	public boolean insertAffBpc(BpcsTran bpcsTran,Connection conn, String transitAff) throws TCGMException;	
	/**
	 *
	 * @param bpcsTran BpcsTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */

	public boolean isExistinASR(BpcsTran bpcsTran,Connection conn) throws TCGMException;
		
	public void delete(BpcsTran bpcsTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector bpcsTranList) throws TCGMException;

	/**
	 * @param bpcsTran BpcsTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(BpcsTran bpcsTran,Connection conn) throws TCGMException;

	/**
	 * @param bpcsTranList Vector of BpcsTran objects
	 * @throws TCGMException
	 */
	public void update(Vector bpcsTranList) throws TCGMException;
	/**
	 * @throws TCGMException
	 */
	public void publishAll(BpcsTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param searchObject BpcsTran
	 * @param newVals BpcsTran
	 * @throws TCGMException
	 */
	public void massUpdate(BpcsTran searchObject,BpcsTran newVals) throws TCGMException;

	/**
	 * @param asrTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector asrTranList,String copyToModel) throws TCGMException;

	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(BpcsTran searchObject,String copyToModel) throws TCGMException;
}