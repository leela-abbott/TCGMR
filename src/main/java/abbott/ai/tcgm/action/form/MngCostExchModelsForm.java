package abbott.ai.tcgm.action.form;

import java.util.Vector;
import java.util.Iterator;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.TCGMConstants;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class MngCostExchModelsForm extends TCGMMngModelsForm {

	private String selDtlMetric;
	private String selCSMAff;
	private String essbaseVersion;
	private String essbaseYear;
	private String essbaseType;
	private String essbaseStPeriod;
	private String essbaseEndPeriod;
	private String showCostModel;
	public String getModelDetailArrayString() {
		StringBuffer sb = new StringBuffer("[");
		CostExchModel currentModel;
		if (this.getModels() != null & this.getModels().size()>0) {
			Vector v = this.getModels();
			Iterator iterator = v.iterator();
			while (iterator.hasNext()) {
				currentModel = (CostExchModel) iterator.next();
				sb.append( "['" );
				sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );

				sb.append( "<b>Rate Set:</b> " );
				if (!currentModel.getRateSet().getDatasetName().equals(""))
					sb.append( currentModel.getRateSet().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");
				// Label Modified from Starting RGM Data to Starting RGM Ver by Udaya B Aravapalli on 01/10/2006 to 
				// be consistent and clear.
				sb.append( "<b>Starting RGM Ver:</b> " );
				if (!currentModel.getStartingSalesData().getDatasetName().equals(""))
					sb.append( currentModel.getStartingSalesData().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");
				// Label Modified from Ending RGM Data to Ending RGM Ver by Udaya B Aravapalli on 01/10/2006 to 
				// be consistent and clear.
				sb.append( "<b>Ending RGM Ver:</b> ");
				if (!currentModel.getEndingSalesData().getDatasetName().equals(""))
					sb.append( currentModel.getEndingSalesData().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");

				sb.append( "<b>Start Period:</b> " + currentModel.getStartPeriod() + ", " + currentModel.getStartYear()  + "<br>" );
				sb.append( "<b>End Period:</b> " + currentModel.getEndPeriod() + ", " + currentModel.getEndYear() + "<br>" );

				sb.append( "<b>Cost Exch Units:</b> ");
				if (!currentModel.getCostExchUnits().getDatasetName().equals(""))
					sb.append( currentModel.getCostExchUnits().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");
				// Added by Udaya B Aravapalli on 01/10/2006 to display Memo.
				sb.append( "<b>Memo:</b> " + currentModel.getMemo() + "<br>" );
				// Added by Udaya B Aravapalli on 01/31/2006 to display Factor Model.
				
				sb.append( "<b>Factor Model:</b> " + currentModel.getFactorModelName() + "<br>" );
				    
				sb.append("'],");
			}
		}
		sb.append( "]");
		return sb.toString();
	}
	public void setSelDtlMetric(String selDtlMetric) {
		this.selDtlMetric = selDtlMetric;
	}
	public String getSelDtlMetric() {
		return selDtlMetric;
	}
	public void setSelCSMAff(String selCSMAff) {
		this.selCSMAff = selCSMAff;
	}
	public String getSelCSMAff() {
		return selCSMAff;
	}
	public void setEssbaseVersion(String essbaseVersion) {
		this.essbaseVersion = essbaseVersion;
	}
	public String getEssbaseVersion() {
		return essbaseVersion;
	}
	public void setEssbaseYear(String essbaseYear) {
		this.essbaseYear = essbaseYear;
	}
	public String getEssbaseYear() {
		return essbaseYear;
	}
	public void setEssbaseType(String essbaseType) {
		this.essbaseType = essbaseType;
	}
	public String getEssbaseType() {
		return essbaseType;
	}
	public MngCostExchModelsForm() {
		super();
		this.setModelType(TCGMModel.Type.COSTEXCH);
		this.setFormHandler("mngCostExchModels.do");
	}
	/**
	 * @return
	 */
	public String getEssbaseEndPeriod() {
		return essbaseEndPeriod;
	}

	/**
	 * @return
	 */
	public String getEssbaseStPeriod() {
		return essbaseStPeriod;
	}

	/**
	 * @param string
	 */
	public void setEssbaseEndPeriod(String string) {
		essbaseEndPeriod = string;
	}

	/**
	 * @param string
	 */
	public void setEssbaseStPeriod(String string) {
		essbaseStPeriod = string;
	}

	/**
	 * @return Returns the showCostModel.
	 */
	public String getShowCostModel() {
		return showCostModel;
	}
	/**
	 * @param showCostModel The showCostModel to set.
	 */
	public void setShowCostModel(String showCostModel) {
		this.showCostModel = showCostModel;
	}
}