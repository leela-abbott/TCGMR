package abbott.ai.tcgm.action.form;
import abbott.ai.tcgm.entities.*;
import org.apache.log4j.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MngFactorsForm extends TCGMProductionForm {

	private String selBegFactorPeriod;
	private String selFactorRate;
	private String planUnitsSelected;
	private String actualUnitsSelected;
	private java.util.Vector unitSets;
	private java.util.Vector rateSets;
	private static Logger myLogger = Logger.getLogger( "abbott.ai.tcgm.action.form.MngFactorsForm" );
//	private String selExportRSystem;
	private java.util.Vector exportSystemSets;
	private String selCostRate;
	private String selRevisionRate;
	
	// 10-10-05
	private boolean rgmSystemSelected;
	private boolean rtcSystemSelected;
	private boolean rbbSystemSelected;
	private boolean rblSystemSelected;
	private boolean ccsSystemSelected;
	private String ccsType; 
	//12-27-2005
	private String lastExSavePeriod13FactorsDt;

	public MngFactorsForm() {
		super();
		this.setFormHandler("mngFactors.do");
	}
	public void setSelBegFactorPeriod(String selBegFactorPeriod) {
		this.selBegFactorPeriod = selBegFactorPeriod;
	}
	public String getSelBegFactorPeriod() {
		return selBegFactorPeriod;
	}
	public String getSelFactorRate() {
		return selFactorRate;
	}
	public void setSelFactorRate(String selFactorRate) {
		this.selFactorRate = selFactorRate;
	}
	public void setPlanUnitsSelected(String planUnitsSelected) {
		this.planUnitsSelected = planUnitsSelected;
	}
	public String getPlanUnitsSelected() {
		return planUnitsSelected;
	}
	public void setActualUnitsSelected(String actualUnitsSelected) {
		this.actualUnitsSelected = actualUnitsSelected;
	}
	public String getActualUnitsSelected() {
		return actualUnitsSelected;
	}
	public void setUnitSets(java.util.Vector unitSets) {
		this.unitSets = unitSets;
	}
	public java.util.Vector getUnitSets() {
		return unitSets;
	}
	public void setRateSets(java.util.Vector rateSets) {
		this.rateSets = rateSets;
	}
	public java.util.Vector getRateSets() {
		return rateSets;
	}
//	public void setSelExportRSystem(String selExportRSystem) {
//		this.selExportRSystem = selExportRSystem;
//	}
//	public String getSelExportRSystem() {
//		return selExportRSystem;
//	}

	public void setExportSystemSets(java.util.Vector exportSystemSets) {
		this.rateSets = exportSystemSets;
	}
	public java.util.Vector getExportSystemSets() {
		return exportSystemSets;
	}

	public void setSelCostRate(String selCostRate) {
		this.selCostRate = selCostRate;
	}
	public String getSelCostRate() {
		return selCostRate;
	}
	public void setSelRevisionRate(String selRevisionRate) {
		this.selRevisionRate = selRevisionRate;
	}
	public String getSelRevisionRate() {
		return selRevisionRate;
	}

// 10-10-05 Make Rsystem Selection a Checkbox so multiple system can be selected	
	public boolean isRgmSystemSelected()
	{
		return this.rgmSystemSelected;
	}	
	public void setRgmSystemSelected(boolean rgmSystemSelected) 
	{
		this.rgmSystemSelected = rgmSystemSelected;
	}
	public boolean getRgmSystemSelected() 
	{
		return rgmSystemSelected;
	}
	
	public boolean isRtcSystemSelected()
	{
		return this.rtcSystemSelected;
	}	
	public void setRtcSystemSelected(boolean rtcSystemSelected) 
	{
		this.rtcSystemSelected = rtcSystemSelected;
	}
	public boolean getRtcSystemSelected() 
	{
		return rtcSystemSelected;
	}

	public boolean isRbbSystemSelected()
	{
		return this.rbbSystemSelected;
	}	
	public void setRbbSystemSelected(boolean rbbSystemSelected) 
	{
		this.rbbSystemSelected = rbbSystemSelected;
	}
	public boolean getRbbSystemSelected() 
	{
		return rbbSystemSelected;
	}
	
	public boolean isRblSystemSelected()
	{
		return this.rblSystemSelected;
	}
	public void setRblSystemSelected(boolean rblSystemSelected) 
	{
		this.rblSystemSelected = rblSystemSelected;
	}
	public boolean getRblSystemSelected() 
	{
		return this.rblSystemSelected;
	}	
	
	public void reset(FactorModel myModel) {
		super.reset();
		this.setSelBegFactorPeriod( myModel.getBegFactorPeriod() );
		this.setActualUnitsSelected( myModel.getActualUnitsId() );
		this.setPlanUnitsSelected( myModel.getPlanUnitsId() );
		this.setSelFactorRate( myModel.getRateSetFactorActualId() );
		this.setSelCostRate( myModel.getRateSetCostId() );
		this.setSelRevisionRate( myModel.getRateSetRevisionId() );

		// 10-11-2005 Allow Multiple R-systems Selection
		// this.setSelExportRSystem( myModel.getExportRSystem() );			
//		if(myModel.getExportRgmSystem().equals("Y"))
//			this.setRgmSystemSelected(true);
//		else
//			this.setRgmSystemSelected(false);
			
//		if(myModel.getExportRtcSystem().equals("Y"))
//			this.setRtcSystemSelected(true);
//		else
//			this.setRtcSystemSelected(false);
		
//		if(myModel.getExportRbbSystem().equals("Y"))
//			this.setRbbSystemSelected(true);
//		else
//			this.setRbbSystemSelected(false);
		
//		if(myModel.getExportRblSystem().equals("Y"))
//			this.setRblSystemSelected(true);
//		else
//			this.setRblSystemSelected(false);	

		// 12-22-05 Try reseting the check boxes back to false (un-checked)

	}


	/**
	 * @return
	 */
	public String getLastExSavePeriod13FactorsDt() {
		return lastExSavePeriod13FactorsDt;
	}

	/**
	 * @param string
	 */
	public void setLastExSavePeriod13FactorsDt(String string) {
		lastExSavePeriod13FactorsDt = string;
	}

	/**
	 * @return
	 */
	public boolean isCcsSystemSelected() {
		return ccsSystemSelected;
	}

	/**
	 * @param b
	 */
	public void setCcsSystemSelected(boolean b) {
		ccsSystemSelected = b;
	}
	
	public boolean getCcsSystemSelected() 
	{
			return this.ccsSystemSelected;
	}

	/**
	 * @return
	 */
	public String getCcsType() {
		return ccsType;
	}

	/**
	 * @param string
	 */
	public void setCcsType(String string) {
		ccsType = string;
	}

}