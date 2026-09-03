/*
 * Created on Jun 17, 2008
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
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.AsrUsageMngr;
import abbott.ai.tcgm.helpers.ModelMngr;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AsrUsageMaint extends TCGMAction {
	/**
	 * Default Constructor
	 */
	public AsrUsageMaint() {
		super();
	}

	private final static String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,RPT_AFF,RPT_INV_CD, "
			+ "RPT_LIST,RPT_LABEL,RPT_SIZE,RPT_PACK,SUP_AFF,SUP_INV_CD, "
			+ "SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,PROD_ORIGIN,SUP_KEY,USAGE_FACTOR,ID,CYCLE_ID, " + "CREATE_DATETIME "
			+ "FROM ";

	/**
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

		HttpSession session = request.getSession();// get existing session or create a new one if it doesn't exist

		this.errors.clear();

		if (form == null) {
			// errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.asrusage.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		} else if (this.isSessionValid(request)) {
			UserToken userToken = this.getUserToken(request);
			AsrUsageForm affBpcForm = (AsrUsageForm) form;// cast the form that was passed in to the correct type for
															// this action

			affBpcForm.processCmd(mapping, request);

			AsrUsageMngr affBpcMngr = new AsrUsageMngr(); // create the helper class that will handle the processing
			ModelMngr modelMngr = new ModelMngr();

			AsrUsageMaint asrMaint = new AsrUsageMaint();

			boolean flag = false;

			try {

				affBpcForm.setSupAffList(affBpcMngr.getSupAff(this.getUserToken(request)));

				if (affBpcForm.getSupAffSelected() == null) {
					affBpcForm.getSearchObject().setModelId(DBConst.DEF_MODEL_ID);
					affBpcForm.getSearchObject().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);
					affBpcForm.getPagingFilter().setTotalRecordsInSet(
							affBpcMngr.getCount(this.getUserToken(request), affBpcForm.getSearchObject()));
					affBpcForm.setAffASRList(affBpcMngr.getAsr(this.getUserToken(request), affBpcForm.getSearchObject(),
							affBpcForm.getPagingFilter(), affBpcForm.getSortObject()));
					// affBpcForm.setSupAffSelected("-1");

				} else {

					affBpcForm.getSearchObject().setModelId(DBConst.DEF_MODEL_ID);
					affBpcForm.getSearchObject().setDatasetTableId(DBConst.DEF_DATASET_TABLE_ID);

					if (affBpcForm.getSupAffSelected().equals("-1")) {

						affBpcForm.getPagingFilter().setTotalRecordsInSet(
								affBpcMngr.getCount(this.getUserToken(request), affBpcForm.getSearchObject()));
						affBpcForm.setAffASRList(
								affBpcMngr.getAsr(this.getUserToken(request), affBpcForm.getSearchObject(),
										affBpcForm.getPagingFilter(), affBpcForm.getSortObject()));
					}

					else if (affBpcForm.getPagingFilter().getDispNextPage()
							|| affBpcForm.getPagingFilter().getDispPrevPage()) {

						asrMaint.asrDataFetchAfterDelete(affBpcForm, request);
					}

				}

				FactorModel fm = new FactorModel();
				fm.setStatus(TCGMModel.Status.OPEN);
				affBpcForm.setModels(modelMngr.getModels(userToken, fm));

				affBpcForm.setModelSelected(TCGMConstants.NONE);
				this.setForward(TCGMConstants.FORWARD_SUCCESS);
			} catch (TCGMException tcgme) {
				this.logger.error(tcgme.toString(), tcgme);
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, tcgme);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			}
		}

		if (!this.errors.empty()) {
			this.saveErrors(request, errors);
		}

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
