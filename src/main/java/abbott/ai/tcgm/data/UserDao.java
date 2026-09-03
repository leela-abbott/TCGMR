package abbott.ai.tcgm.data;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;

/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
*/
public interface UserDao extends TCGMDao
{
	/**
	 *
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException;

	/**
	 *
	 * @param searchObject User
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(User searchObject) throws TCGMException;

	/**
	 *
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException;

	/**
	 *
	 * @param searchObject User
	 * @return Vector
	 * @throws TCGMException
	 */
	public Vector getVO(User searchObject) throws TCGMException;

	/**
	 *
	 * @param user User
	 * @return boolean
	 * @throws TCGMException
	 */
	public boolean loadUser(User user) throws TCGMException;

	/**
	 *
	 * @param rs RowSet
	 * @param getRole boolean
	 * @return User
	 * @throws TCGMException
	 */
	public User getUserFromCurrentRow(RowSet rs,boolean getRole) throws TCGMException;

	/**
	 *
	 * @return User
	 * @throws TCGMException
	 */
	public User getUserById() throws TCGMException;

	/**
	 *
	 * @param userToInsert
	 * @throws TCGMException
	 */
	public void insert(User userToInsert) throws TCGMException;

	/**
	 *
	 * @param userToUpdate
	 * @throws TCGMException
	 */
	public void update(User userToUpdate) throws TCGMException;

	/**
	 *
	 * @param userToDelete
	 * @param conn
	 * @throws TCGMException
	 */
	public void delete(User userToDelete,Connection conn) throws TCGMException;

	/**
	 *
	 * @param usersToDelete
	 * @throws TCGMException
	 */
	public void delete(Vector usersToDelete) throws TCGMException;
	
	
	public boolean exists(String userID) throws TCGMException;
	
	public void checkRptUser(User user) throws TCGMException;
}