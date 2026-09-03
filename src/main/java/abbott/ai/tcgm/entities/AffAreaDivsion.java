/*
 * Created on Jun 5, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.entities;

import java.io.Serializable;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AffAreaDivsion extends TCGMLog implements Serializable{
	
	private boolean selected = false;

		private String affCode = "";
		private String affDesc = "";
		private String areaCode = "";
		private String areaDesc = "";
		private String regCode = "";
		private String regDesc = "";
		private String secCode = "";
		private String secDesc = "";
		

		/**
		 * @return
		 */
		public String getAffCode() {
			return affCode;
		}

		/**
		 * @return
		 */
		public String getAffDesc() {
			return affDesc;
		}

		/**
		 * @return
		 */
		public String getAreaCode() {
			return areaCode;
		}

		/**
		 * @return
		 */
		public String getAreaDesc() {
			return areaDesc;
		}

		/**
		 * @return
		 */
		public String getRegCode() {
			return regCode;
		}

		/**
		 * @return
		 */
		public String getRegDesc() {
			return regDesc;
		}

		/**
		 * @return
		 */
		public String getSecCode() {
			return secCode;
		}

		/**
		 * @return
		 */
		public String getSecDesc() {
			return secDesc;
		}

	/**
	 * @return
	 */
	public boolean isSelected() {
		return selected;
	}

		/**
		 * @param string
		 */
		public void setAffCode(String string) {
			affCode = string;
		}

		/**
		 * @param string
		 */
		public void setAffDesc(String string) {
			affDesc = string;
		}

		/**
		 * @param string
		 */
		public void setAreaCode(String string) {
			areaCode = string;
		}

		/**
		 * @param string
		 */
		public void setAreaDesc(String string) {
			areaDesc = string;
		}

		/**
		 * @param string
		 */
		public void setRegCode(String string) {
			regCode = string;
		}

		/**
		 * @param string
		 */
		public void setRegDesc(String string) {
			regDesc = string;
		}

		/**
		 * @param string
		 */
		public void setSecCode(String string) {
			secCode = string;
		}

		/**
		 * @param string
		 */
		public void setSecDesc(String string) {
			secDesc = string;
		}

	/**
	 * @param b
	 */
	public void setSelected(boolean b) {
		selected = b;
	}

}
