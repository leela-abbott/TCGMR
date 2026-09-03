package abbott.ai.tcgm.action.asr;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.*;
//import org.apache.log4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.action.*;

import abbott.ai.tcgm.action.TCGMAction;

public class AsrUsageDownload extends TCGMAction {

	protected static Logger logger = null;
	private static final long serialVersionUID = 1L;
	private final static String SELECT_ASR_USAGE_QUERY = "SELECT a.PROD_ORIGIN \"Org\",a.RPT_AFF \"Rpt Aff\",a.RPT_INV_CD \"R I C\", a.RPT_LIST \"Rpt List\", a.RPT_LABEL \"Rpt Lbl Cde\", a.RPT_SIZE \"Rpt Siz Cde\", a.RPT_PACK \"Rpt Pack\","
			+"a.SUP_AFF \"Sup Aff\",a.SUP_INV_CD \"S I C\",a.SUP_LIST \"Sup List\", "
			+"a.SUP_LABEL \"Sup Lbl Cde\", a.SUP_SIZE \"Sup Siz Cde\",  a.SUP_pack \"Sup Pack\", TO_CHAR(TO_CHAR(a.USAGE_FAC,'FM9999D000000'),'9999.999999') \"Usage Factor\",a.SUP_KEY \"K e y\"";

	public AsrUsageDownload() {

		super();
		this.logger = Logger.getLogger(this.getClass());

	}

	public ActionForward perform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

	    this.errors.clear();
		AsrUsageForm asrUsageForm = (AsrUsageForm) form;
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("ASR_Report");
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		boolean flag = false;
		String cycle = asrUsageForm.getCycle();
		
		if (form == null) {
			// errors is an ActionErrors object defined in TCGMAction
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.asrUsageForm.form.missing"));
			this.setForward(TCGMConstants.G_FORWARD_SELECT_MODEL);
		} else if (this.isSessionValid(request) && asrUsageForm.getCmd().equalsIgnoreCase("download")) {
			UserToken userToken = this.getUserToken(request);
			boolean nonDuplicate = true;
			User user = (User) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_USER);
			asrUsageForm.processCmd(mapping, request);

			Connection connection = null;
			ResultSet resultSet = null;
			Statement statement = null;
			String model_name = this.getState(request).getCurrentModelName();

						String sql = SELECT_ASR_USAGE_QUERY
					+ "from TCGM.ASR a , TCGM.Model b where a.MODEL_ID = b.MODEL_ID AND b.MODEL_DESC='" + model_name
					+ "'" + "AND a.PROD_ORIGIN='" + cycle + "'";

			try {
				connection = SQLUtil.openConnection();			
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);

				OutputStream outputStream =null;
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				

				// Write column names to the first row
				Row headerRow = sheet.createRow(0);
				for (int i = 1; i <= columnCount; i++) {
					Cell cell = headerRow.createCell(i - 1);
					cell.setCellValue(metaData.getColumnName(i));
					cell.setCellStyle(headerStyle);

				}

				int rownum = 1;
				while (resultSet.next()) {
					Row row = sheet.createRow(rownum++);
					for (int i = 1; i <= columnCount; i++) {
						Cell cell = row.createCell(i - 1);
						cell.setCellValue(resultSet.getString(i));
					}
				}

				workbook.write(outputStream);
				

				flag = true;

			} catch (Exception ex) {
				
				request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
				this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
			} finally {
				if (flag) {
					SQLUtil.closeConnection(connection);
					SQLUtil.closeResultSet(resultSet);
				}
			}

		}

		return mapping.findForward(this.getForward());

	}

	protected TCGMState getState(HttpServletRequest request) {
		TCGMState state = (TCGMState) request.getSession().getAttribute(TCGMConstants.SESSION_NAME_STATE);
		if (state == null) {
			state = new TCGMState();
		}
		return state;
	}

}
