package abbott.ai.tcgm.data;

import javax.sql.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: AffBpc Data Access Object</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author David Fields
 * @version 1.0
 */
public interface AffBpcDao
{
	/**
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 * @param searchObject AffBpc object
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffBpc searchObject) throws TCGMException;

	/**
	 * @param searchObject AffBpc object
	 * @param sortObject contains sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffBpc searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 * @param searchObject AffBpc object
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getVO(AffBpc searchObject) throws TCGMException;

	/**
	 * @param searchObject AffBpc object
	 * @param sortObject contains sort criteria
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getVO(AffBpc searchObject,Sort sortObject) throws TCGMException;

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
	public AffBpc getAffBpcFromCurrentRow(RowSet rs) throws TCGMException;

	/**
	 * @param affBpc AffBpc object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(AffBpc affBpc,Connection conn) throws TCGMException;

	/**
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public ArrayList getSupAff(Connection conn) throws TCGMException;


	/**
	 * @param affBpcList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector affBpcList) throws TCGMException;
	
	public void upload(String fileName,String cycle) throws TCGMException,IOException;
}