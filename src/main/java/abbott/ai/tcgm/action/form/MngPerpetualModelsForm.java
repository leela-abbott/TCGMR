package abbott.ai.tcgm.action.form;

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

public class MngPerpetualModelsForm extends TCGMMngModelsForm {
	private String showPerpetualModel;
	public String getModelDetailArrayString() {
		StringBuffer sb = new StringBuffer("[");
		PerpetualModel currentModel;
		String none = TCGMConstants.NONE_SEL_HTML;
		if (this.getModels() != null & this.getModels().size()>0) {
			Vector v = this.getModels();
			Iterator iterator = v.iterator();
			while (iterator.hasNext()) {
				currentModel = (PerpetualModel) iterator.next();
				sb.append( "['" );
				sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );

				sb.append( "<b>Starting Model:</b> " + ( currentModel.getStartingModel() == null ? none : currentModel.getStartingModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getStartingInvUnits() == null ? none : currentModel.getStartingInvUnits().getDatasetName() ) + "<br>" );

				sb.append( "<b>Current Model:</b> " + ( currentModel.getCurrentYearActualModel() == null ? none : currentModel.getCurrentYearActualModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getCurrentYearActualUnits() == null ? none : currentModel.getCurrentYearActualUnits().getDatasetName() ) + "<br>" );

				sb.append( "<b>Previous Model:</b> " + ( currentModel.getLastYearActualModel() == null ? none : currentModel.getLastYearActualModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getLastYearActualUnits() == null ? none : currentModel.getLastYearActualUnits().getDatasetName() ) + "<br>" );

				sb.append( "<b>Costing Model:</b> " + ( currentModel.getCostingModel() == null ? none: currentModel.getCostingModel().getName() ) + "<br>"  );

				sb.append( "<b>Ending Inventory Model:</b> " + ( currentModel.getEndingModel() == null ? none: currentModel.getEndingModel().getName() ) );
				sb.append( "<b>Units:</b> " + ( currentModel.getEndingInvUnits() == null ? none : currentModel.getEndingInvUnits().getDatasetName() ) + "<br>"  );

				sb.append( "<b>Start Period:</b> " + currentModel.getStartPeriod() + ", " + currentModel.getStartYear()  + "<br>" );
				sb.append( "<b>End Period:</b> " + currentModel.getEndPeriod() + ", " + currentModel.getEndYear() + "<br>" );

//		A.Winter - add memo to param. display

   			    String temp = fixMemo(currentModel.getMemo());
				sb.append( "<b> Memo:</b> " + (  temp == "" ? none : temp)  + "<br>" );
				sb.append( "'],\n" );
			}
		}
		sb.append( "]");
		return sb.toString();
	}

	public MngPerpetualModelsForm() {
		super();
		this.setModelType(TCGMModel.Type.PERPETUAL);
	}
	/**
	 * @return Returns the showPerpetualModel.
	 */
	public String getShowPerpetualModel() {
		return showPerpetualModel;
	}
	/**
	 * @param showPerpetualModel The showPerpetualModel to set.
	 */
	public void setShowPerpetualModel(String showPerpetualModel) {
		this.showPerpetualModel = showPerpetualModel;
	}
}