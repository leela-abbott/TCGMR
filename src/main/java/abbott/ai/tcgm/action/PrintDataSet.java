package abbott.ai.tcgm.action;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.*;
//import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.*;

import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Save Unit Set Data</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author Gain Joseph
 * @version 1.0
 */
public class PrintDataSet extends TCGMAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		try
		{
			if( this.isSessionValid(request))
			{
				UserToken ut = this.getUserToken(request);
				UnitMngr um = new UnitMngr();
				Dataset dataset = new Dataset(); 
				String dataSetName=null;
				TCGMMngSetsForm sdmf = (TCGMMngSetsForm) form;
				int selDatasetid=Integer.parseInt(sdmf.getSelDataset());
				Vector v= um.getUnitSets(ut) ;
				for(int i=0;i<=v.size();i++){
					dataset= (Dataset)v.elementAt(i);
					if(dataset.getDatasetTableIdInt()==selDatasetid){
						dataSetName=dataset.getDatasetName();						
						break;
					}
				}
				
				ReportMngr mngr=new ReportMngr();
				mngr.generateUnitReport(sdmf.getSelDataset(),dataSetName);
				
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
				this.errors.clear();
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.units.print"));
			}
		} 
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		saveErrors(request,errors);
		return mapping.findForward( this.getForward() );
	}

	public PrintDataSet() {
		super();
	}

}