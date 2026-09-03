/*
 * Created on Jun 5, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.form;

import java.util.ArrayList;

import abbott.ai.tcgm.entities.AffAreaDivsion;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AffAreaDivForm extends TCGMForm{
	private AffAreaDivsion searchObject = new AffAreaDivsion();
	private ArrayList afflist = new ArrayList();
	private ArrayList affCodeList = new ArrayList();
	private String affcode="";
	private String affListCode="";

	/**
	 * @return
	 */
	public ArrayList getAfflist() {
		return afflist;
	}

	/**
	 * @return
	 */
	public AffAreaDivsion getSearchObject() {
		return searchObject;
	}

	/**
	 * @param list
	 */
	public void setAfflist(ArrayList list) {
		afflist = list;
	}

	/**
	 * @param divsion
	 */
	public void setSearchObject(AffAreaDivsion divsion) {
		searchObject = divsion;
	}
	
	public AffAreaDivsion getAffCodeList(int index)
		{
			AffAreaDivsion affCode = null;
			if(index >= 0 && index < this.getAfflist().size())
			{
				affCode = (AffAreaDivsion)this.getAfflist().get(index);
			}
			return affCode;
		}
	public void setAffCodeList(int index,AffAreaDivsion affCode)
		{
			this.getAfflist().add(index,affCode);
		}	
	public int getAffListSize()
		{
			return this.getAfflist().size();
		}		

	/**
	 * @return
	 */
	public String getAffcode() {
		return affcode;
	}

	/**
	 * @param string
	 */
	public void setAffcode(String string) {
		affcode = string;
	}

	/**
	 * @return
	 */
	public String getAffListCode() {
		return affListCode;
	}

	/**
	 * @param string
	 */
	public void setAffListCode(String string) {
		affListCode = string;
	}

	/**
	 * @return
	 */
	public ArrayList getAffCodeList() {
		return affCodeList;
	}

	/**
	 * @param list
	 */
	public void setAffCodeList(ArrayList list) {
		affCodeList = list;
	}

}
