package abbott.ai.tcgm.action.form;

import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.*;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MngReportJobsForm extends TCGMProductionForm {

    public String getReportDetailArrayString() {
        StringBuffer sb = new StringBuffer("[");
        ReportInstance currentInstance;
        if (this.getReportList() != null & this.getReportList().size()>0) {
            Vector v = this.getReportList();
            Iterator iterator = v.iterator();

            while (iterator.hasNext()) {

                currentInstance = (ReportInstance) iterator.next();
                sb.append( "['" );
                sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentInstance.getReportDefinition().getDesc() ) + "<br>" );
                sb.append( "<b>Job Desc:</b> " + TCGMUtil.escapeString( currentInstance.getJobInstance().getDesc() ) + "<br>" );
                sb.append( "<b>Model:</b> " + TCGMUtil.escapeString( currentInstance.getJobInstance().getModel() ) + "<br>" );

                sb.append("'],");
            }
        }
        sb.append( "]");
        return sb.toString();
    }

}