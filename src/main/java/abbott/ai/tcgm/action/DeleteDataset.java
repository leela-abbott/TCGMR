package abbott.ai.tcgm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.form.TCGMMngSetsForm;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.entities.Dataset;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class DeleteDataset extends TCGMAction
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
				DatasetMngr dm = new DatasetMngr();
				TCGMMngSetsForm sdmf = (TCGMMngSetsForm) form;
				Dataset dataset= new Dataset();
				dataset = dm.getDatasetById(ut, Integer.parseInt(sdmf.getSelDataset()));
				if (dataset.getTableName().equalsIgnoreCase(DBConst.TABLE_UNIT_DATA_SAVE))
				{
					dataset.setTableName(DBConst.TABLE_UNIT_DATA);
					dm.updateDatasetTableName(ut, dataset);
				}
				dm.deleteDatasetById(ut, Integer.parseInt(sdmf.getSelDataset()) );
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.units.delete"));
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			}
		}
		catch (TCGMException ex)
		{
			request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		}
		this.logger.debug(this.className + " - Forward to " + this.getForward() );
		return mapping.findForward( this.getForward() );
	}

	public DeleteDataset() {
		super();
	}

}