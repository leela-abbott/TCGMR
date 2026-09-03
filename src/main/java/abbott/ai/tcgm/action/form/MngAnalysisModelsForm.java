package abbott.ai.tcgm.action.form;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import java.util.Vector;
import java.util.Iterator;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class MngAnalysisModelsForm extends TCGMMngModelsForm {

	private String extValueVersion;
	private String essbaseVersion;
	private String essbaseYear;
	private String essbaseType;
	//A.Winter - add description 6/29/06
	private String modelLongDesc; 
	private String showAnalModel;
	public String getModelDetailArrayString() {
		String none = TCGMConstants.NONE_SEL_HTML;
		StringBuffer sb = new StringBuffer("[");
		AnalysisModel currentModel;
		if (this.getModels() != null & this.getModels().size()>0) {
			Vector v = this.getModels();
			Iterator iterator = v.iterator();
			while (iterator.hasNext()) {
				currentModel = (AnalysisModel) iterator.next();
				sb.append( "['" );
				sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );
				
				//Sridevi.K text modified according to the ticket.
				sb.append( "<b>Base Factor Model:</b> " + ( currentModel.getBaseModel() == null ? none : currentModel.getBaseModel().getName() ) + ", Period: " + currentModel.getBaseModelPeriod() + "<br>" );
				sb.append( "<b> Analysis Model:</b> " + ( currentModel.getAnalysisModel() == null ? none : currentModel.getAnalysisModel().getName() ) + ", Period: " + currentModel.getAnalysisModelPeriod() + "<br>" );
				sb.append( "<b>Base Units:</b> " + ( currentModel.getAnalysisUnits() == null ? none : currentModel.getAnalysisUnits().getDatasetName() ) + ", Period: " + currentModel.getAnalysisUnitsPeriod() + "<br>" );
				//Sridevi.K End of text modification according to the ticket.
				
				sb.append( "<b>Analysis Units:</b> " + ( currentModel.getVolumeUnits() == null ? none : currentModel.getVolumeUnits().getDatasetName() ) + ", Period: " + currentModel.getVolumeUnitsPeriod() + "<br>" );
/* 8-3-05 Remove Current & last year model & unit from parm display
				sb.append( "<b>Current Year Model:</b> " + ( currentModel.getCurrentYearActualModel() == null ? none : currentModel.getCurrentYearActualModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getCurrentYearActualUnits() == null ? none : currentModel.getCurrentYearActualUnits().getDatasetName() ) + "<br>" );

				sb.append( "<b>Last Year Model:</b> " + ( currentModel.getLastYearActualModel() == null ? none : currentModel.getLastYearActualModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getLastYearActualUnits() == null ? none : currentModel.getLastYearActualUnits().getDatasetName() ) + "<br>" );
*/
				// A.Winter - add memo to param. display
                String temp = fixMemo(currentModel.getMemo());
				//sb.append( "<b> Memo:</b> " + (  currentModel.getMemo() == "" ? none : currentModel.getMemo())  + "<br>" );
				sb.append( "<b> Memo:</b> " + (  temp == "" ? none : temp)  + "<br>" );

				sb.append( "'],\n" );
			}
		}
		sb.append( "]");
		//"['Base Model: Factor02<br>Object Model: Actual02']";
		return sb.toString();
	}

	public MngAnalysisModelsForm() {
		super();
		this.setModelType(TCGMModel.Type.ANALYSIS);
		this.setFormHandler("mngAnalysisModels.do");
	}

	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ( TCGMUtil.isEmpty( this.getModelSelected() ) ) {
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.model.none_selected"));
		}

		if ( errors.empty() )
			return null;
		else
			return errors;
	}

	public void setExtValueVersion(String extValueVersion) {
		this.extValueVersion = extValueVersion;
	}
	public String getExtValueVersion() {
		return extValueVersion;
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
	/**
	 * @return
	 */
	public String getModelLongDesc() {
		return modelLongDesc;
	}

	/**
	 * @param string
	 */
	public void setModelLongDesc(String string) {
		modelLongDesc = string;
	}

	/**
	 * @return Returns the showAnalModel.
	 */
	public String getShowAnalModel() {
		return showAnalModel;
	}
	/**
	 * @param showAnalModel The showAnalModel to set.
	 */
	public void setShowAnalModel(String showAnalModel) {
		this.showAnalModel = showAnalModel;
	}
}