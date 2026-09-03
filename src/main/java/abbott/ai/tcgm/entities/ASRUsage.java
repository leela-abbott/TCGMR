/*
 * Created on Jun 17, 2008
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
public class ASRUsage extends TCGMEntity implements Serializable{
	private boolean selected = false;

	

		private String rptAff = "";
		private String supAff = "";
		private Product rptProduct = new Product();
		private Product supProduct = new Product();
		private String productOrigin = "";
		private String supKey = "";
		private String usage = "";
		private String affiliate="";
		private String asrId="";
		private String cycleId="";
		/**
		 *
		 */
		public ASRUsage()
		{

		}

		/**
		 *
		 * @param rptAff
		 */
		public void setRptAff(String rptAff)
		{
			this.rptAff = rptAff;
		}

		/**
		 *
		 * @return
		 */
		public String getRptAff()
		{
			if(this.rptAff == null)
			{
				this.rptAff = "";
			}
			return this.rptAff.toUpperCase();
		}

		/**
		 *
		 * @param supAff
		 */
		public void setSupAff(String supAff)
		{
			this.supAff = supAff;
		}

		/**
		 *
		 * @return
		 */
		public String getSupAff()
		{
			if(this.supAff == null)
			{
				this.supAff = "";
			}
			return this.supAff.toUpperCase();
		}

		/**
		 *
		 * @param supProduct
		 */
		public void setSupProduct(Product supProduct)
		{
			this.supProduct = supProduct;
		}

		/**
		 *
		 * @return
		 */
		public Product getSupProduct()
		{
			if(this.supProduct == null)
			{
				this.supProduct = new Product();
			}
			return this.supProduct;
		}


		/**
		 *
		 * @param billPrice
		 */
		

		/**
		 * @return
		 */
		public String getProductOrigin() {
			return productOrigin;
		}

		/**
		 * @return
		 */
		public Product getRptProduct() {
			return rptProduct;
		}

	/**
	 * @return
	 */
	public boolean isSelected() {
		return selected;
	}

		/**
		 * @return
		 */
		public String getSupKey() {
			return supKey;
		}

		/**
		 * @return
		 */
		public String getUsage() {
			return usage;
		}

		/**
		 * @param string
		 */
		public void setProductOrigin(String string) {
			productOrigin = string;
		}

		/**
		 * @param product
		 */
		public void setRptProduct(Product product) {
			rptProduct = product;
		}

	/**
	 * @param b
	 */
	public void setSelected(boolean b) {
		selected = b;
	}

		/**
		 * @param string
		 */
		public void setSupKey(String string) {
			supKey = string;
		}

		/**
		 * @param string
		 */
		public void setUsage(String string) {
			usage = string;
		}

		/**
		 * @return
		 */
		public String getAffiliate() {
			return affiliate;
		}

		/**
		 * @param string
		 */
		public void setAffiliate(String string) {
			affiliate = string;
		}

		
		
		/**
		 * @return
		 */
		public String getAsrId() {
			return asrId;
		}

		/**
		 * @param string
		 */
		public void setAsrId(String string) {
			asrId = string;
		}

		/**
		 * @return Returns the cycleId.
		 */
		public String getCycleId() {
			return cycleId;
		}
		/**
		 * @param cycleId The cycleId to set.
		 */
		public void setCycleId(String cycleId) {
			this.cycleId = cycleId;
		}
}
