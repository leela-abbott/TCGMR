package abbott.ai.tcgm.entities;

//import java.util.Vector;
import java.util.StringTokenizer;
import java.io.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReportRestriction implements Serializable {

//    public static String[] Operations = {"<", "<=", ">", ">=", "=", "<>"};
	public static String[] Operations = {"IN"}; //6-26-03 leave as array in case of future expansion
	private String column = new String();

	private String operation = new String();
	//private String value = new String(); 7-1-03 bd; replaced with ...LimiterValues
	private String rptAffLimiterValues = new String();
	private String supAffLimiterValues = new String();
	private String endAffLimiterValues = new String();
	private String rptListLimiterValues = new String();
	private String supListLimiterValues = new String();
	private String endListLimiterValues = new String();
	private String groupIdLimiterValues = new String();

	public ReportRestriction() {
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperation() {
		return operation;
	}

	public void setRptAffLimiterValues(String rptAffLimiterValues) {
		this.rptAffLimiterValues = rptAffLimiterValues;
	}
	public String getRptAffLimiterValues() {
		return rptAffLimiterValues;
	}
	public void setSupAffLimiterValues(String supAffLimiterValues) {
		this.supAffLimiterValues = supAffLimiterValues;
	}
	public String getSupAffLimiterValues() {
		return supAffLimiterValues;
	}
	public void setEndAffLimiterValues(String endAffLimiterValues) {
		this.endAffLimiterValues = endAffLimiterValues;
	}
	public String getEndAffLimiterValues() {
		return endAffLimiterValues;
	}
	public void setRptListLimiterValues(String rptListLimiterValues) {
		this.rptListLimiterValues = rptListLimiterValues;
	}
	public String getRptListLimiterValues() {
		return rptListLimiterValues;
	}
	public void setSupListLimiterValues(String supListLimiterValues) {
		this.supListLimiterValues = supListLimiterValues;
	}
	public String getSupListLimiterValues() {
		return supListLimiterValues;
	}
	public void setEndListLimiterValues(String endListLimiterValues) {
		this.endListLimiterValues = endListLimiterValues;
	}
	public String getEndListLimiterValues() {
		return endListLimiterValues;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
	}

	public String getRptAffRestriction() {
		if ( rptAffLimiterValues.trim().equals("") )
			return "";
		else
			//return (" RPT_AFF IN " + "(" + sb.toString() + ")");
			return (" RPT_AFF IN " + "(" + getFormattedRestriction(this.rptAffLimiterValues) + ")");
	}
	public String getSupAffRestriction() {
		if ( supAffLimiterValues.trim().equals("") )
			return "";
		else
			//return (this.column + this.operation + "'" + this.value + "'");
			return (" SUP_AFF IN " + "(" + getFormattedRestriction(this.supAffLimiterValues) + ")");
	}
	public String getEndAffRestriction() {
		if ( endAffLimiterValues.trim().equals("") )
			return "";
		else
			//return (this.column + this.operation + "'" + this.value + "'");
			return (" END_AFF IN " + "(" + getFormattedRestriction(this.endAffLimiterValues) + ")");
	}
	public String getRptListRestriction() {
		if ( rptListLimiterValues.trim().equals("") )
			return "";
		else
			return (" RPT_LIST IN " + "(" + getFormattedRestriction(this.rptListLimiterValues) + ")");
	}
	public String getSupListRestriction() {
		if ( supListLimiterValues.trim().equals("") )
			return "";
		else
			return (" SUP_LIST IN " + "(" + getFormattedRestriction(this.supListLimiterValues) + ")");
	}
	public String getEndListRestriction() {
		if ( endListLimiterValues.trim().equals("") )
			return "";
		else
			return (" END_LIST IN " + "(" + getFormattedRestriction(this.endListLimiterValues) + ")");
	}

	public String getFormattedRestriction(String limiterValues) {
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(limiterValues, ",");
		boolean firstTime = true;
		while(st.hasMoreTokens()){
			String tokenString = st.nextToken();
			if(firstTime) {
				sb.append( "'" + tokenString.trim() + "'"  );
			}
			else {
				sb.append( ", '" + tokenString.trim() + "'"  );
			}
		firstTime = false;
		}
		return sb.toString();
	}

	/**
	 * @return Returns the groupIdLimiterValues.
	 */
	public String getGroupIdLimiterValues() {
		if ( groupIdLimiterValues.trim().equals("") )
			return "";
		else
			return (" GROUP_ID IN " + "(" + getFormattedRestriction(this.groupIdLimiterValues) + ")");		
	}
	/**
	 * @param groupIdLimiterValues The groupIdLimiterValues to set.
	 */
	public void setGroupIdLimiterValues(String groupIdLimiterValues) {
		this.groupIdLimiterValues = groupIdLimiterValues;
	}
}