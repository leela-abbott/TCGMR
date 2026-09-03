package abbott.ai.tcgm.data;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: BpcRevTran Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface BpcRevTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject BpcRevTran object with search criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRevTran searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcRevTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcRevTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject BpcRevTran object with search criteria
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRevTran searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcRevTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcRevTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param bpcRevTranList Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
//	A.Winter - 7/26/05 change void to boolean	
	public boolean insert(Vector bpcRevTranList) throws TCGMException;

	/**
	 *
	 * @param bpcRevTran BpcRevTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
//	A.Winter - 7/26/05 change void to boolean	
	public boolean insert(BpcRevTran bpcRevTran,Connection conn) throws TCGMException;
	/**
	 *
	 * @param bpcRevTran BpcRevTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(BpcRevTran bpcRevTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param bpcRevTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector bpcRevTranList) throws TCGMException;

	/**
	 * @param bpcRevTran BpcRevTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(BpcRevTran bpcRevTran,Connection conn) throws TCGMException;

	/**
	 * @param bpcRevTranList Vector of BpcRevTran objects
	 * @throws TCGMException
	 */
	public void update(Vector bpcRevTranList) throws TCGMException;
	/**
	 * @throws TCGMException
	 */
	public void publishAll(BpcRevTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param searchObject BpcRevTran
	 * @param newVals BpcRevTran
	 * @throws TCGMException
	 */
	public void massUpdate(BpcRevTran searchObject,BpcRevTran newVals) throws TCGMException;

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
	public void copy(BpcRevTran searchObject,String copyToModel) throws TCGMException;
}