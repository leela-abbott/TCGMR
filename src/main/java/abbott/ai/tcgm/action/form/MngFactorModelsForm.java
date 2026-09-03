package abbott.ai.tcgm.action.form;

import abbott.ai.tcgm.entities.*;

//import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public class MngFactorModelsForm extends TCGMMngModelsForm
{
    /**
     *
     */
    public MngFactorModelsForm()
    {
        super();
        this.setModelType(TCGMModel.Type.FACTOR);
        this.setFormHandler("mngFactorModels.do");
    }

    public String getModelDetailArrayString()
    {
        // *******************************************
        // not implemented currently for factor models
        // *******************************************
  /*
  StringBuffer sb = new StringBuffer("[");
  PerpetualModel currentModel;
  if (this.getModels() != null & this.getModels().size()>0) {
   Vector v = this.getModels();
   Iterator iterator = v.iterator();
   while (iterator.hasNext()) {
    currentModel = (PerpetualModel) iterator.next();
    sb.append( "['" );
    sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );
    sb.append( "<b>Starting Model:</b> " + currentModel.getStartingModelName() + "<br>" );
    sb.append( "<b>Costing Model:</b> " + currentModel.getCostingModelName() + "<br>"  );
    sb.append( "<b>Ending Inv Model:</b> " + currentModel.getEndingInventoryName() + "<br>"  );
    sb.append( "<b>Start Period:</b> " + currentModel.getStartPeriod() + ", " + currentModel.getStartYear()  + "<br>" );
    sb.append( "<b>End Period:</b> " + currentModel.getEndPeriod() + ", " + currentModel.getEndYear() + "<br>" );
    sb.append( "'],\n" );
   }
  }
  sb.append( "]");
  return sb.toString();
  */
        return null;
    }
}