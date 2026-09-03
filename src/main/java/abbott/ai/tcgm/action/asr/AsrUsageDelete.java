/*
 * Created on Jun 18, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.action.asr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.exception.TCGMUpdateWithBlankUsernameException;
import abbott.ai.tcgm.helpers.AsrUsageMngr;
import abbott.ai.tcgm.helpers.ModelMngr;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AsrUsageDelete extends TCGMAction {

	public AsrUsageDelete() {
		super();
	}

	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,RPT_AFF,RPT_INV_CD, "
			+ "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, "
			+ "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,PROD_ORIGIN,SUP_KEY,USAGE_FACTOR,ID,CYCLE_ID, " + "CREATE_DATETIME "
			+ "FROM ";

	/**
	 *
	 * @param mapping  ActionMapping
	 * @param form     ActionForm
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String methodName = "perform";

		AsrUsageDelete asrDel = new AsrUsageDelete();

		HttpSession session = request.getSession();// get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if (form == null) {
			// errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.asrusage.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		} else if (this.isSessionValid(request)) {
			UserToken userToken = this.getUserToken(request);

			AsrUsageForm asrTranForm = (AsrUsageForm) form;// cast the form that was passed in to the correct type for
															// this action

			asrTranForm.processCmd(mapping, request);

			AsrUsageMngr asrMngr = new AsrUsageMngr(); // create the helper class that will handle the processing
			ModelMngr modelMngr = new ModelMngr();

			try {
				// asrTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);

				if (asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_ALL)) {
					String aff = "";
					String cycleId = "";
					if (asrTranForm.getSupAffSelected() == null || asrTranForm.getSupAffSelected().equals("-1")) {
						aff = "-1";

					} else {
						cycleId = asrTranForm.getSupAffSelected().substring(0,
								asrTranForm.getSupAffSelected().indexOf("::"));
						aff = asrTranForm.getSupAffSelected().substring(
								asrTranForm.getSupAffSelected().indexOf("::") + 2,
								asrTranForm.getSupAffSelected().lastIndexOf("::"));

					}
					asrMngr.deleteAllAsrTran(userToken, asrTranForm.getSearchObject(), asrTranForm.getAffASRList(), aff,
							cycleId);
				} else if (asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_SELECTED))

				{
					asrMngr.deleteSelectedAsrTran(userToken, asrTranForm.getSearchObject(),
							asrTranForm.getAffASRList());

				} else if (!asrTranForm.getCmd().trim().equals("")) {
					throw new TCGMException(className, methodName, "Invalid command in action");
				}
								
				if (asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_SELECTED)) {
					if (!asrTranForm.getSupAffSelected().equals("-1")) {
						boolean completionFlag = asrDel.asrDataFetchAfterDelete(asrTranForm, request);

					} else {

						asrTranForm.getPagingFilter().setTotalRecordsInSet(
								asrMngr.getCount(this.getUserToken(request), asrTranForm.getSearchObject()));

						long count = asrTranForm.getPagingFilter().getTotalRecordsInSet();

						asrTranForm
								.setAffASRList(asrMngr.getAsr(this.getUserToken(request), asrTranForm.getSearchObject(),
										asrTranForm.getPagingFilter(), asrTranForm.getSortObject()));
					}

				} else if (asrTranForm.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE_ALL)) {

					asrTranForm.getPagingFilter().setTotalRecordsInSet(
							asrMngr.getCount(this.getUserToken(request), asrTranForm.getSearchObject()));
					asrTranForm.setAffASRList(asrMngr.getAsr(this.getUserToken(request), asrTranForm.getSearchObject(),
							asrTranForm.getPagingFilter(), asrTranForm.getSortObject()));
				}

				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("success.asrusage.delete"));

				session.setAttribute("deleteMessage", "Transaction Record(s) Deleted");
				if (asrTranForm.getSupAffSelected() == null || asrTranForm.getSupAffSelected().equals("-1")) {
					asrTranForm.setSupAffSelected("-1");
					asrTranForm.setSupAffList(asrMngr.getSupAff(this.getUserToken(request)));
					asrTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
				} else {
					asrTranForm.setSupAffList(asrMngr.getSupAff(this.getUserToken(request)));
					asrTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));
				}
				asrTranForm.setModelSelected(TCGMConstants.NONE);
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			} catch (TCGMUpdateWithBlankUsernameException ex) {
				this.logger.error(ex.toString(), ex);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.update.with.blank.username"));
				try {
					// asrTranForm.initModelAndDataset(this.getState(request).getCurrentModelIdString(),DBConst.DEF_DATASET_TABLE_ID);
					asrTranForm.getPagingFilter().setTotalRecordsInSet(
							asrMngr.getCount(this.getUserToken(request), asrTranForm.getSearchObject()));
					asrTranForm.setAffASRList(asrMngr.getAsr(this.getUserToken(request), asrTranForm.getSearchObject(),
							asrTranForm.getPagingFilter(), asrTranForm.getSortObject()));

					asrTranForm.setSupAffList(asrMngr.getSupAff(this.getUserToken(request)));
					asrTranForm.setModels(modelMngr.getModels(userToken, new FactorModel()));

					asrTranForm.setModelSelected(TCGMConstants.NONE);
					this.setForward(TCGMConstants.FORWARD_SUCCESS);
				} catch (TCGMException exc) {
					this.logger.error(exc.toString(), exc);
					request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, exc);
					this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
				}
			}

			catch (TCGMException ex) {
				this.logger.error(ex.toString(), ex);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}

		}
		// if errors exist then save them into the request
		if (!this.errors.empty()) {
			this.saveErrors(request, errors);
		}

		// forward to the next page or servlet found in the struts-config mapping
		this.logger.debug(className + " Forward: " + this.getForward());
		return mapping.findForward(this.getForward());
	}

	public boolean asrDataFetchAfterDelete(AsrUsageForm asrForm, HttpServletRequest req) {

		AsrUsageMngr asrDataFetchMngr = new AsrUsageMngr();
		String aff = "";
		String cycleId = "";
		String dateFromSuff = "";
		long num = 0;
		boolean resultFlag = false;

		UserToken userToken = this.getUserToken(req);

		AsrUsageMngr asrMngr = new AsrUsageMngr();

		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;

		cycleId = asrForm.getSupAffSelected().substring(0, asrForm.getSupAffSelected().indexOf("::"));

		aff = asrForm.getSupAffSelected().substring(asrForm.getSupAffSelected().indexOf("::") + 2,
				asrForm.getSupAffSelected().lastIndexOf("::"));
		dateFromSuff = asrForm.getSupAffSelected().substring(asrForm.getSupAffSelected().lastIndexOf("::") + 2,
				asrForm.getSupAffSelected().length());

		String sql = "select Count(*) as Number_Count from TCGM.AFF_ASR_USAGE_FACTOR_FILE b where ";
		sql = sql + "b.RPT_AFF = '" + aff + "'" + " AND b.CYCLE_ID= '" + cycleId + "'"
				+ " AND TRUNC(b.CREATE_DATETIME) = " + " to_date('" + dateFromSuff + "'" + " ,'yyyy/mm/dd')";

		String updated_sql = MIDDLE_SELECT_START + "TCGM.AFF_ASR_USAGE_FACTOR_FILE b where " + "b.RPT_AFF = '" + aff
				+ "'" + " AND b.CYCLE_ID= '" + cycleId + "'" + " AND TRUNC(b.CREATE_DATETIME) = " + " to_date('"
				+ dateFromSuff + "'" + " ,'yyyy/mm/dd')";

		try {
			connection = SQLUtil.openConnection();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {

				num = resultSet.getInt("Number_Count");

			}

			resultFlag = num == 0 ? false : true;

			asrForm.getPagingFilter().setTotalRecordsInSet(num);

			asrForm.setAffASRList(asrMngr.getAsrDataForSuffAffSelected(this.getUserToken(req),
					asrForm.getSearchObject(), asrForm.getPagingFilter(), asrForm.getSortObject(), updated_sql, true));
		}

		catch (Exception ex) {
			this.logger.error(ex.toString(), ex);
			req.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
			this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
		} finally {
			if (true) {
				SQLUtil.closeConnection(connection);
				SQLUtil.closeResultSet(resultSet);
			}
		}
		return resultFlag;

	}
}
