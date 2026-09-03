/*
 * Created on Jun 5, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.data;

import java.util.ArrayList;

import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AffAreaDivDao {
	
	public ArrayList getAffcodes() throws TCGMException;
	
	public ArrayList getAffWanted() throws TCGMException;
	
	public int createAff(String affCode) throws TCGMException;
	
	public void deleteAff(ArrayList affUserList) throws TCGMException;

}
