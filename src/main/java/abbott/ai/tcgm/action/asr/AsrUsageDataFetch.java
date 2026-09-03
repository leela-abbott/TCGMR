package abbott.ai.tcgm.action.asr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.action.TCGMAction;
import abbott.ai.tcgm.action.form.AsrUsageForm;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrMngr;
import abbott.ai.tcgm.helpers.AsrUsageMngr;
import abbott.ai.tcgm.helpers.ModelMngr;

public class AsrUsageDataFetch extends TCGMAction{
	
	public AsrUsageDataFetch()
	{
		super();
	}
	
	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,RPT_AFF,RPT_INV_CD, "
			+ "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, "
			+ "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,PROD_ORIGIN,SUP_KEY,USAGE_FACTOR,ID,CYCLE_ID, " + "CREATE_DATETIME "
			+ "FROM ";
	
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		String methodName = "perform";

		HttpSession session = request.getSession();

		AsrUsageForm asrUsageForm = (AsrUsageForm) form;
		asrUsageForm.processCmd(mapping, request);

		AsrUsageMngr asrMngr = new AsrUsageMngr();
		ModelMngr modelMngr = new ModelMngr();
		String aff = "";
		String cycleId = "";
		String dateFromSuff = "";

		this.errors.clear();

		long num = 0;

		if (form == null) {
			// errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.asrusage.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		} else if (this.isSessionValid(request)) {
			UserToken userToken = this.getUserToken(request);

			try {

				if (asrUsageForm.getCmd().equals("fetchedData")) {

					if (asrUsageForm.getSupAffSelected().equals("-1")) {
						asrUsageForm.getPagingFilter().setTotalRecordsInSet(
								asrMngr.getCount(this.getUserToken(request), asrUsageForm.getSearchObject()));
						long count = asrUsageForm.getPagingFilter().getTotalRecordsInSet();

						asrUsageForm.setAffASRList(
								asrMngr.getAsr(this.getUserToken(request), asrUsageForm.getSearchObject(),
										asrUsageForm.getPagingFilter(), asrUsageForm.getSortObject()));

					} else {

						Connection connection = null;
						ResultSet resultSet = null;
						Statement statement = null;

						cycleId = asrUsageForm.getSupAffSelected().substring(0,
								asrUsageForm.getSupAffSelected().indexOf("::"));

						aff = asrUsageForm.getSupAffSelected().substring(
								asrUsageForm.getSupAffSelected().indexOf("::") + 2,
								asrUsageForm.getSupAffSelected().lastIndexOf("::"));
						dateFromSuff = asrUsageForm.getSupAffSelected().substring(
								asrUsageForm.getSupAffSelected().lastIndexOf("::") + 2,
								asrUsageForm.getSupAffSelected().length());

						String sql = "select Count(*) as Number_Count from TCGM.AFF_ASR_USAGE_FACTOR_FILE b where ";
						sql = sql + "b.RPT_AFF = '" + aff + "'" + " AND b.CYCLE_ID= '" + cycleId + "'"
								+ " AND TRUNC(b.CREATE_DATETIME) = " + " to_date('" + dateFromSuff + "'"
								+ " ,'yyyy/mm/dd')";

						String updated_sql = this.MIDDLE_SELECT_START + "TCGM.AFF_ASR_USAGE_FACTOR_FILE b where "
								+ "b.RPT_AFF = '" + aff + "'" + " AND b.CYCLE_ID= '" + cycleId + "'"
								+ " AND TRUNC(b.CREATE_DATETIME) = " + " to_date('" + dateFromSuff + "'"
								+ " ,'yyyy/mm/dd')";

						try {
							connection = SQLUtil.openConnection();

							statement = connection.createStatement();
							resultSet = statement.executeQuery(sql);

							while (resultSet.next()) {

								num = resultSet.getInt("Number_Count");

							}

							asrUsageForm.getPagingFilter().setTotalRecordsInSet(num);

							asrUsageForm.setAffASRList(asrMngr.getAsrDataForSuffAffSelected(this.getUserToken(request),
									asrUsageForm.getSearchObject(), asrUsageForm.getPagingFilter(),
									asrUsageForm.getSortObject(), updated_sql, true));

						}

						catch (Exception ex) {
							this.logger.error(ex.toString(), ex);
							request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
							this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
						} finally {
							if (true) {
								SQLUtil.closeConnection(connection);
								SQLUtil.closeResultSet(resultSet);
							}
						}

					}

				}
				this.setForward(TCGMConstants.FORWARD_SUCCESS);

			} catch (TCGMException exc) {
				this.logger.error(exc.toString(), exc);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exc);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
		}

		if (!this.errors.empty()) {
			this.saveErrors(request, errors);
		}

		// forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}

}
