package abbott.ai.tcgm.data;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public interface BpcExTranDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject BpcExTran object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcExTran searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcExTran object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(BpcExTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject BpcExTran object
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcExTran searchObject) throws TCGMException;

	/**
	 * @param searchObject BpcExTran object
	 * @param sortObject contains sort criteria
	 * @return Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public Vector getVO(BpcExTran searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return number of records returned from query
	 * @throws TCGMException
	 */
	public long getCount() throws TCGMException;

	/**
	 *
	 * @param bpcExTranList Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public boolean insert(Vector bpcExTranList) throws TCGMException;

	/**
	 *
	 * @param bpcExTran BpcExTran object
	 * @param conn Connection object
	 * @throws TCGMException
	 */
	public boolean insert(BpcExTran bpcExTran,Connection conn) throws TCGMException;

	/**
	 *
	 * @param bpcExTran bpcExTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(BpcExTran bpcExTran,Connection conn) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 * @param bpcExTranList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector bpcExTranList) throws TCGMException;

	/**
	 * @param bpcExTran BpcExTran object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void update(BpcExTran bpcExTran,Connection conn) throws TCGMException;

	/**
	 * @param bpcExTranList Vector of BpcExTran objects
	 * @throws TCGMException
	 */
	public void update(Vector bpcExTranList) throws TCGMException;

	/**
	 * @throws TCGMException
	 */
	public void publishAll(BpcExTran searchObject, boolean blnFlag) throws TCGMException, TCGMUpdateWithBlankUsernameException;

	/**
	 *
	 * @param searchObject BpcExTran
	 * @param newVals BpcExTran
	 * @throws TCGMException
	 */
	public void massUpdate(BpcExTran searchObject,BpcExTran newVals) throws TCGMException;

	/**
	 *
	 * @param rs RowSet
	 * @return BpcExTran
	 * @throws TCGMException
	 */
	public BpcExTran getBpcExTranFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 * @param bpcExTranList
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(Vector bpcExTranList,String copyToModel) throws TCGMException;

	/**
	 * @param searchObject
	 * @param copyToModel
	 * @throws TCGMException
	 */
	public void copy(BpcExTran searchObject,String copyToModel) throws TCGMException;
}