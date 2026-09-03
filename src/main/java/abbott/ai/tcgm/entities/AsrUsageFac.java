/*
 * Created on Jun 24, 2008
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
public class AsrUsageFac implements Serializable{
	
	private String acd = "";                  
	private int model_id  ;            
	private int dataset_table_id  ;    
	private String prod_origin = "";                             
	private String rpt_aff   = "";            
	private String rpt_inv_cd = "";           
	private String rpt_list  = "";            
	private String rpt_pack   = "";           
	private String rpt_label  = "";           
	private String rpt_size  = "";            
	private String sup_aff = "";              
	private String sup_inv_cd = "";           
	private String sup_list = "";             
	private String sup_pack  = "";            
	private String sup_label= "";             
	private String sup_size  = "";            
	private String sup_key  = "";             
	private double usage_fac ;            
	private String publish_flag = "";         
	private String msg2 = "";
	private String msg = "";                

	/**
	 * @return
	 */
	public String getAcd() {
		return acd;
	}

	/**
	 * @return
	 */
		/**
	 * @return
	 */
	public String getMsg2() {
		return msg2;
	}

	/**
	 * @return
	 */
	public String getProd_origin() {
		return prod_origin;
	}

	/**
	 * @return
	 */
	public String getPublish_flag() {
		return publish_flag;
	}

	/**
	 * @return
	 */
	public String getRpt_aff() {
		return rpt_aff;
	}

	/**
	 * @return
	 */
	public String getRpt_inv_cd() {
		return rpt_inv_cd;
	}

	/**
	 * @return
	 */
	public String getRpt_label() {
		return rpt_label;
	}

	/**
	 * @return
	 */
	public String getRpt_list() {
		return rpt_list;
	}

	/**
	 * @return
	 */
	public String getRpt_pack() {
		return rpt_pack;
	}

	/**
	 * @return
	 */
	public String getRpt_size() {
		return rpt_size;
	}

	/**
	 * @return
	 */
	public String getSup_aff() {
		return sup_aff;
	}

	/**
	 * @return
	 */
	public String getSup_inv_cd() {
		return sup_inv_cd;
	}

	/**
	 * @return
	 */
	public String getSup_key() {
		return sup_key;
	}

	/**
	 * @return
	 */
	public String getSup_label() {
		return sup_label;
	}

	/**
	 * @return
	 */
	public String getSup_list() {
		return sup_list;
	}

	/**
	 * @return
	 */
	public String getSup_pack() {
		return sup_pack;
	}

	/**
	 * @return
	 */
	public String getSup_size() {
		return sup_size;
	}

	/**
	 * @return
	
	/**
	 * @param string
	 */
	public void setAcd(String string) {
		acd = string;
	}

	/**
	 * @param string
	 */
	

	/**
	 * @param string
	 */
	public void setMsg2(String string) {
		msg2 = string;
	}

	/**
	 * @param string
	 */
	public void setProd_origin(String string) {
		prod_origin = string;
	}

	/**
	 * @param string
	 */
	public void setPublish_flag(String string) {
		publish_flag = string;
	}

	/**
	 * @param string
	 */
	public void setRpt_aff(String string) {
		rpt_aff = string;
	}

	/**
	 * @param string
	 */
	public void setRpt_inv_cd(String string) {
		rpt_inv_cd = string;
	}

	/**
	 * @param string
	 */
	public void setRpt_label(String string) {
		rpt_label = string;
	}

	/**
	 * @param string
	 */
	public void setRpt_list(String string) {
		rpt_list = string;
	}

	/**
	 * @param string
	 */
	public void setRpt_pack(String string) {
		rpt_pack = string;
	}

	/**
	 * @param string
	 */
	public void setRpt_size(String string) {
		rpt_size = string;
	}

	/**
	 * @param string
	 */
	public void setSup_aff(String string) {
		sup_aff = string;
	}

	/**
	 * @param string
	 */
	public void setSup_inv_cd(String string) {
		sup_inv_cd = string;
	}

	/**
	 * @param string
	 */
	public void setSup_key(String string) {
		sup_key = string;
	}

	/**
	 * @param string
	 */
	public void setSup_label(String string) {
		sup_label = string;
	}

	/**
	 * @param string
	 */
	public void setSup_list(String string) {
		sup_list = string;
	}

	/**
	 * @param string
	 */
	public void setSup_pack(String string) {
		sup_pack = string;
	}

	/**
	 * @param string
	 */
	public void setSup_size(String string) {
		sup_size = string;
	}

	/**
	 * @param string
	 */
	
	/**
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param string
	 */
	public void setMsg(String string) {
		msg = string;
	}

	/**
	 * @return
	 */
	public int getDataset_table_id() {
		return dataset_table_id;
	}

	/**
	 * @return
	 */
	public int getModel_id() {
		return model_id;
	}

	/**
	 * @return
	 */
	public double getUsage_fac() {
		return usage_fac;
	}

	/**
	 * @param i
	 */
	public void setDataset_table_id(int i) {
		dataset_table_id = i;
	}

	/**
	 * @param i
	 */
	public void setModel_id(int i) {
		model_id = i;
	}

	/**
	 * @param d
	 */
	public void setUsage_fac(double d) {
		usage_fac = d;
	}

}
