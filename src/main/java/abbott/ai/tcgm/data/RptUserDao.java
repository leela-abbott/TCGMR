package abbott.ai.tcgm.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.exception.TCGMException;

public interface RptUserDao extends TCGMDao
{

	/**
	 * @param userBean UserBean
	 * @throws TCGMException
	 */
	
	public void create(RptUser userBean) throws TCGMException;

	public void addToBurstTable_MASS(RptUser rptUser, String strCatId, String strCatName) throws TCGMException;
	
	public void addToBurstTable(RptUser rptUser, String strCatId, String strCatName, String strCat,String division) throws TCGMException;
	
	public void addToBurstTable_DEFAULTGROUPS(RptUser rptUser, String strCatId, String strCatName) throws TCGMException;

	/**
	 * @param userBean UserBean
	 * @throws TCGMException
	 */
	
	public void create(RptUser userBean, HashMap map) throws TCGMException;	

	/**
	 * @param lightVersion boolean -- If true, will not load all the area and country drop down values
	 * @throws TCGMException
	 */

	public ArrayList read(boolean lightVersion) throws TCGMException;

	/**
	 * @param userList ArrayList of UserBean's that need to be updated.
	 * @throws TCGMException
	 */

	public void update(ArrayList userList) throws TCGMException;
	
	/**
	 * @param userList ArrayList of UserBean's that need to be deleted.
	 * @throws TCGMException
	 */
	public void delete(RptUser userBean) throws TCGMException;
	
		
	public HashMap getAllSectors(String division) throws TCGMException;
	
	public HashMap getAllAreas(String division) throws TCGMException;
		
	public HashMap getAllAreaAffs(String areaCode,String division) throws TCGMException;
	
	public HashMap getAllSectorAffs(String areaCode,String division) throws TCGMException;
	
	public HashMap getAllHQAffs() throws TCGMException;
	
	public HashMap getAllHQAffs(String division) throws TCGMException;
		
	public HashMap getAllAffiliates(String division) throws TCGMException;
	
	public HashMap getListValues(String query, Connection obj_Connection) throws TCGMException;
	
	public ArrayList getRptUsers() throws TCGMException;
	
	public void recertifyRptUsers(ArrayList userList, String userName)	throws TCGMException;
	
	public void loadBurstTable() throws TCGMException;
	
	public ArrayList getAffiliateStatus(String division,String affCode) throws TCGMException;
	
	public void saveAffiliateStatus(String division,ArrayList affCode,String status) throws TCGMException;
	
	public HashMap getDivision()throws TCGMException;
	
	public HashMap getTAreas(String division) throws TCGMException;
	
	public HashMap getTSectors(String division) throws TCGMException;
	
	public HashMap getTAffiliates(String division) throws TCGMException;
	
	public HashMap getBurstDivision()throws TCGMException;
	
	public HashMap getBurstAreas(String division) throws TCGMException;
	
	public HashMap getBurstSectors(String division) throws TCGMException;
	
	public HashMap getBurstAffiliates(String division) throws TCGMException;
	
	public String[] getDivisionException(String division)throws TCGMException;
}