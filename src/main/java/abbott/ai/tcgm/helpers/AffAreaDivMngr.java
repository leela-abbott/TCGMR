/*
 * Created on Jun 5, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.helpers;

import java.util.ArrayList;

import abbott.ai.tcgm.data.AffAreaDivDao;
import abbott.ai.tcgm.data.DaoFactory;
import abbott.ai.tcgm.entities.AffAreaDivsion;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AffAreaDivMngr implements TCGMMngr {
	public final String className = this.getClass().getName();

	public ArrayList getAffcodes() throws TCGMException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffAreaDivDao affDao = daoFactory.getAffAreaDivDao();
		return affDao.getAffcodes();
	}
	public ArrayList getAffWanted() throws TCGMException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffAreaDivDao affDao = daoFactory.getAffAreaDivDao();
		return affDao.getAffWanted();
	}

	public int createAffWanted(String affCode) throws TCGMException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffAreaDivDao affDao = daoFactory.getAffAreaDivDao();
		return affDao.createAff(affCode);
	}
	public void deleteAffWanted(ArrayList userList) throws TCGMException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.ORACLE);
		AffAreaDivDao affDao = daoFactory.getAffAreaDivDao();		
		affDao.deleteAff(getSelected(userList));
	}

	private ArrayList getSelected(ArrayList userList) {
		ArrayList selectedRptUsers = new ArrayList();

		for (int i = 0; i < userList.size(); i++) {

			AffAreaDivsion affUser = (AffAreaDivsion) userList.get(i);

			if (affUser.isSelected()) {
				selectedRptUsers.add(affUser);
			}
		}

		return selectedRptUsers;
	}

}
