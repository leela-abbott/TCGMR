package abbott.ai.tcgm.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.data.RptUserDao;
import abbott.ai.tcgm.entities.ActiveAffMaint;
import abbott.ai.tcgm.entities.RptUser;
import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;

/*******************************************************************************

* $Abbott: ActiveDirSearchMgr,v 1.0 2007/08/11 11:22:00 $
* Copyright (C) 2007  Abbott International,. All Rights Reserved.
* $name:         UserMaintMgr.java
* $description:  The UserMaintMgr.java acts as a business layer class. 
*				 This Manager class is used to perform the Database (CRUD -- Create,
*				 Read, Update and Delete operations) activities. 
******************************************************************************/
	public class RptUserMngr implements TCGMMngr
	{
		public final String className = this.getClass().getName();
		
		/**
		 * Default Constructor
		 */
		public RptUserMngr()
		{
		}
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllAffiliates(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllAffiliates(division);
		}
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllSectors(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllSectors(division);
		}	
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllAreas(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllAreas(division);
		}
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllAreaAffs(String areaCode,String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllAreaAffs(areaCode,division);
		}				
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllSectorAffs(String secCode,String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllSectorAffs(secCode,division);
		}						
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllHQAffs() throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllHQAffs();
		}	
		public HashMap getDivision() throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getDivision();
		}
		/**
		 * @throws TCGMException
		 */
		public HashMap getTAreas(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getTAreas(division);
		}
		/**
		 * @throws TCGMException
		 */
		public HashMap getTAffiliates(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getTAffiliates(division);
		}				
		/**
		 * @throws TCGMException
		 */
		public HashMap getTSectors(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getTSectors(division);
		}
		public HashMap getBurstDivision()throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getBurstDivision();
		}
		public HashMap getBurstAreas(String division) throws TCGMException
		{
			String methodName = "getBurstAreas(String division)";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getBurstAreas(division);
		}
		public HashMap getBurstSectors(String division) throws TCGMException
		{
			String methodName = "getBurstSectors(String division)";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getBurstSectors(division);
		}
		public HashMap getBurstAffiliates(String division) throws TCGMException{
			String methodName = "getBurstAffiliates(String division)";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getBurstAffiliates(division);
		}
		/**
		 * @throws TCGMException
		 */
		public HashMap getAllHQAffs(String division) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			return userDao.getAllHQAffs(division);
		}	
		/**
		 *
		 * @param userToken contains user id and password
		 * @param searchObject PriceHdr object with search criteria
		 * @return Vector of PriceHdr objects
		 * @throws TCGMException
		 */
		public ArrayList getUserList(RptUser searchObject, boolean lightVersion) throws TCGMException
		{
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao(searchObject);
			return userDao.read(lightVersion);
		}

		/*****************************************************************************************/
		/**
		 * @param userToken UserToken
		 * @param bpcsTranList Vector
		 * @throws TCGMException
		 */
		public void saveUser(RptUser userBean) throws TCGMException
		{
			String methodName = "saveUser(RptUser userBean)";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			userDao.create(userBean);
			
			ArrayList selectedUserList = new ArrayList();
			selectedUserList.add(userBean);
			
			ReportMngr reportMngr = new ReportMngr();
			reportMngr.userMaintenance(selectedUserList, TCGMConstants.REPORT_CONSTANT_ADD_USERS);
		}
		/**
		 * @throws TCGMException
		 */
		public void addToBurstTables(RptUser userBean, String strCatId, String strCatName, String strCat,String division) throws TCGMException
		{
			String methodName = "addToBurstTables(RptUser userBean, String strCatId, String strCatName, String strCat,String division)";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			//By default adding groups to all divisions
			userBean.setDivision("All");
	    	userDao.addToBurstTable(userBean, strCatId, strCatName, strCat,division);
			//userDao.addToBurstTable_DEFAULTGROUPS(userBean, strCatId, strCatName);
			//userDao.addToBurstTable_MASS(userBean, strCatId, strCatName);
			
			//ReportMngr reportMngr = new ReportMngr();
			//reportMngr.addGroup(userBean, strCatId, strCatName);
		}
		public void loadBurstTables() throws TCGMException
		{
			String methodName = "loadBurstTables()";
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			userDao.loadBurstTable();
			
		}
		/**
		 * @throws TCGMException
		 */
		public void generateUsers(User user) throws TCGMException
		{
			String methodName = "generateUsers()";
			
			Thread thread = new Thread(new CognosUsersThread(user));
	        thread.start();
			//SecurityOverview.generateUsers();
		}			
		/**
		 * @param userToken UserToken
		 * @param bpcsTranList Vector
		 * @throws TCGMException
		 */
		public void saveUser(RptUser userBean, HashMap map) throws TCGMException
		{
			String methodName = "saveUser(RptUser userBean)";
			
			//RptUser cogosUser = userBean;

			//ArrayList selectedUserList = new ArrayList();
			//selectedUserList.add(cogosUser);


			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();
			userDao.create(userBean, map);
			
			//ReportMngr reportMngr = new ReportMngr();
			//reportMngr.userMaintenance(selectedUserList, TCGMConstants.REPORT_CONSTANT_ADD_USERS);
			
			
		
		}
		/*****************************************************************************************/
		/**
		 * @param userToken UserToken
		 * @param bpcsTranList Vector
		 * @throws TCGMException
		 */
		public void removeSelectedUser(RptUser userBean) throws TCGMException
		{
			String methodName = "removeSelectedUser(RptUser userBean)";
			userBean = populateDeleteUser(userBean);
			
			/*RptUser cogosUser = userBean;
			
			ArrayList selectedUserList = new ArrayList();
			selectedUserList.add(cogosUser);
			ReportMngr reportMngr = new ReportMngr();
			reportMngr.userMaintenance(selectedUserList, TCGMConstants.REPORT_CONSTANT_REMOVE_USERS);*/

			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao userDao = daoFactory.getRptUserDao();

			userDao.delete(userBean);
			
			
		}	
		private RptUser populateDeleteUser(RptUser userToDelete)
		{
			String strRoleName = userToDelete.getRoleName();
			String strRole = userToDelete.getRole();
			userToDelete.setRole(strRoleName);
			userToDelete.setRecipient("CAMID(\"LDAP abbott.corp:u:cn="+userToDelete.getUserid()+",ou=users\")");

			if(strRoleName.equalsIgnoreCase(TCGMConstants.AREA)){
				userToDelete.setAreaCode(strRole.substring(0,2));
				userToDelete.setDivision(strRole.substring(2,strRole.length()));
			}else if(strRoleName.equalsIgnoreCase(TCGMConstants.AFFILIATE)){
				userToDelete.setAffCode(strRole.substring(0,3));
				userToDelete.setDivision(strRole.substring(3,strRole.length()));
			}else if(strRoleName.equalsIgnoreCase(TCGMConstants.SECTOR)){
				userToDelete.setSecCode(strRole.substring(0,6));
				userToDelete.setDivision(strRole.substring(6,strRole.length()));
			}else if(strRoleName.equalsIgnoreCase(TCGMConstants.DIVISION)){
				userToDelete.setDivision(strRole.substring(1,strRole.length()));
			}else if(strRoleName.equalsIgnoreCase(TCGMConstants.HQ_CON)||strRoleName.equalsIgnoreCase(TCGMConstants.HQ_SUP)){
				userToDelete.setDivision("All");
			}
			
		
			return userToDelete;
		}
		public void removeSelectedUser(RptUser userBean, String strList) throws TCGMException
		{
			String methodName = "removeSelectedUser(RptUser userBean, String strList)";
			
			
			HashMap mapList = new HashMap();
			   		
			mapList = getList(strList);
			Iterator iterate = mapList.keySet().iterator();
				while (iterate.hasNext()){
						String key = (String)iterate.next();
						if(userBean.getRole().equalsIgnoreCase(TCGMConstants.AFFILIATE))
							{
								userBean.setCode(key);
								userBean.setAffCode(key);
							}
						if(userBean.getRole().equalsIgnoreCase(TCGMConstants.SECTOR))
							{
								userBean.setCode(key);
								userBean.setSecCode(key);
							}
					removeSelectedUser(userBean);							
				}
		}
	/*****************************************************************************************/
	/**
	 * @param userToken UserToken
	 * @param bpcsTranList Vector
	 * @throws TCGMException
	 */
	public void regenerateSelectedUsers(ArrayList userList) throws TCGMException
	{
		String methodName = "regenerateSelectedUsers(ArrayList userList)";

		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RptUserDao userDao = daoFactory.getRptUserDao();

		ArrayList selectedUserList = this.getSelectedUserList(userList);

		userDao.update(selectedUserList);
		
	}			
	/*****************************************************************************************/
	/**
	 * Given a vector of BpcsTran, returns the ones that have the selected flag set to true
	 * @param bpcsTranList Vector
	 * @return Vector
	 */
	public ArrayList getSelectedUserList(ArrayList userList)
	{
		ArrayList selectedUserBeans = new ArrayList();

		for(int i = 0; i < userList.size(); i++)
		{
			RptUser userBean = (RptUser)userList.get(i);

			if(userBean.isSelected())
			{
				selectedUserBeans.add(userBean);
			}
		}

		return selectedUserBeans;
	}
	
	public HashMap getList(String strList)
	{
		HashMap mapList = new HashMap();			
		StringTokenizer strTokens = new StringTokenizer(strList, "*");
		
		StringTokenizer tempTokens = null;
		
		while(strTokens.hasMoreElements()){
			tempTokens = new StringTokenizer(strTokens.nextToken(), "|");
			if(tempTokens.hasMoreElements())
				mapList.put(tempTokens.nextToken(),tempTokens.nextToken());
			
		}
		

		return mapList;
	}	
		
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param rptUserList Vector of RptUser objects
	 * @throws TCGMException
	 */
	public void deleteRptUser(ArrayList rptUserList) throws TCGMException
	{
		ArrayList selectedRptUsers = getSelectedRptUser(rptUserList);

		if(selectedRptUsers.size() > 0)
		{
			delete(selectedRptUsers);
		}
	}
	public void delete(ArrayList rptUsersToDelete) throws TCGMException
	{
		String methodName = "delete(ArrayList)";
		
			for(int i = 0; i < rptUsersToDelete.size();i++)
			{
				removeSelectedUser((RptUser)rptUsersToDelete.get(i));
			}
	}
	/*****************************************************************************************/
	/**
	 * Given a vector of RptUser objects, returns the ones that have the selected flag set to true
	 * @param userList Vector
	 * @return Vector
	 */
	private ArrayList getSelectedRptUser(ArrayList userList)
	{
		ArrayList selectedRptUsers = new ArrayList();

		for(int i = 0; i < userList.size(); i++)
		{
			RptUser rptUser = (RptUser)userList.get(i);

			if(rptUser.isSelected())
			{
				selectedRptUsers.add(rptUser);
			}
		}

		return selectedRptUsers;
	}	
	
	public void getRptUser(User user) throws TCGMException
	{
				String methodName = "getRptUser()";			
				ArrayList al=null;


				DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
				RptUserDao userDao = daoFactory.getRptUserDao();
				al=userDao.getRptUsers();
				
				Thread thread = new Thread(new DatabaseUsersThread(user, al));
		        thread.start();
		
		}
	public void recreateUser(ArrayList userList)throws TCGMException
	{
		String methodName = "recreateUser()";			
		ArrayList al=null;
		ArrayList selectedRptUsers = getSelectedRptUser(userList);
		ReportMngr reportMngr = new ReportMngr();
		if(selectedRptUsers.size() > 0)
		{
			reportMngr.recreateUserMaintenance(selectedRptUsers);
		}
								
	}
	
	public void recertifyUser(ArrayList userList, String userName)throws TCGMException
	{
		String methodName = "recertifyUser()";			
		ArrayList al=null;
		ArrayList selectedRptUsers = getSelectedRptUser(userList);
		ReportMngr reportMngr = new ReportMngr();
		if(selectedRptUsers.size() > 0)
		{
			reportMngr.recreateUserMaintenance(selectedRptUsers);
			
			// Change / set the RECERTIFY FLAG in DB
			DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
			RptUserDao rptUserDao = daoFactory.getRptUserDao();
			rptUserDao.recertifyRptUsers(selectedRptUsers, userName);
			
		}
								
	}
	
	public ArrayList getAffiliateStatus(String division,String affCode) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RptUserDao userDao = daoFactory.getRptUserDao();
		return userDao.getAffiliateStatus(division,affCode);
	}
	
	public void saveAffiliateStatus(String division,ArrayList affCode,String status) throws TCGMException
	{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RptUserDao userDao = daoFactory.getRptUserDao();
		userDao.saveAffiliateStatus(division,getSelectedAffMaint(affCode),status);
	}
	
	private ArrayList getSelectedAffMaint(ArrayList userList)
	{
		ArrayList selectedRptUsers = new ArrayList();

		for(int i = 0; i < userList.size(); i++)
		{
			ActiveAffMaint rptUser = (ActiveAffMaint)userList.get(i);

			if(rptUser.isSelected())
			{
				selectedRptUsers.add(rptUser);
			}
		}

		return selectedRptUsers;
	}
	public String[] getDivisionException(String division)throws TCGMException{
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		RptUserDao userDao = daoFactory.getRptUserDao();
		
		return userDao.getDivisionException(division);
	}
}


