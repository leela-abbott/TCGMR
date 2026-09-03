/*
 * Created on Nov 16, 2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author denniba
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MiscJclMngr 
{
	
	public MiscJclMngr() 
	{
	}

	public void sendTestHeaderLabelJcl() throws TCGMException 
	{
		String methodName = "sendTestHeaderLabelJcl()";
		RSystem.RGM.sendJCL(JCLComposer.buildHeaderTestFetch());
 
	}
	public void sendPackCodesJcl() throws TCGMException 
	{
		String methodName = "sendPackCodesJcl()";
		RSystem.RGM.sendJCL(JCLComposer.buildPackCodesFetch());
 	}

	public void sendProdHeaderLabelJcl() throws TCGMException 
	{
		String methodName = "sendProdHeaderLabelJcl()";
		RSystem.RGM.sendJCL(JCLComposer.buildHeaderProdFetch());
 
	}

}
