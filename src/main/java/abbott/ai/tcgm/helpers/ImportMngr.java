//Source file: C:\\DATA\\jbproject\\TCGM\\src\\abbott\\ai\\tcgm\\helpers\\ReportMgr.java

package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.exception.*;

/**
 * 
 * @author denniba
 *
 * The ImportMngr class is obsolete as of 11/16/2005.  Replaced with
 * the MiscJclMngr class.  Methods importProdHeaders() and 
 * importTestHeaders() have been replaced with the methods
 * sendProdHeaderLabelJcl() and sendTestHeaderLabelJcl() respectively
 * from the MiscJclMngr class.
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImportMngr implements TCGMMngr
{


    protected String name = this.getClass().getName();


    /**
   @roseuid 3D4FDE8D0314
   */
    public ImportMngr() {   }

    public void importProdHeaders() throws TCGMException {
        String methodName = "importProdHeaders()";
        String parmList = "";
        RSystem.RGM.sendJCL(JCLComposer.buildHeaderProdFetch());
    }

    public void importTestHeaders() throws TCGMException {
        String methodName = "importTestHeaders()";
        String parmList = "";
        RSystem.RGM.sendJCL(JCLComposer.buildHeaderTestFetch());
    }



}