<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>

<%! String pageTitle="Create Margin Billed Exchange Flex";%>
<%@ include file="/include/header.jsf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Margin Billed Exchange Flex</title>
<style>
/* Global Page Reset & Fonts matching Production */
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #000000;
	margin: 0;
	padding: 0;
	background-color: #ffffff;
}

#divToHide {
	padding: 0px;
	margin: 0px;
}

/* Table Structure & Spacing */
.tableCommand {
	width: 100%;
	border-collapse: collapse;
	margin-top: 15px;
}

.tableCommand td {
	padding: 4px 4px 4px 6px;
	vertical-align: middle;
}

/* Banner Style Header */
.tableHeading {
	background-color: #9bc2e6; /* Production soft steel blue */
	color: #002060; /* Production dark corporate navy */
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	padding: 4px 8px;
	text-align: left;
	border-top: 1px solid #7f7f7f;
	border-bottom: 1px solid #7f7f7f;
}

/* Label Formatting */
.commandOptionLabel {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #002060; /* Exact production deep corporate blue text color */
	text-align: left;
	white-space: nowrap;
}

.right-align-label {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #002060; /* Exact production deep corporate blue text color */
	text-align: right;
	white-space: nowrap;
	padding-right: 10px !important;
}

/* Form Inputs & Select Elements */
.commandOption {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	border: 1px solid #7f7f7f;
	padding: 1px;
	background-color: #ffffff;
	box-sizing: border-box;
}

input[type="text"].commandOption {
	height: 19px;
}

select.commandOption {
	height: 20px;
	padding: 0px;
}

textarea.commandOption {
	border: 1px solid #7f7f7f;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	resize: none;
}

/* Pixel-Perfect Production Button Design Token */
.btn-create-dataset {
	background: linear-gradient(to bottom, #ffeb82 0%, #fcd116 100%);
	color: #000000;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	/* Production button uses a clear slanted/italic font style */
	border-top: 1px solid #eed04b;
	border-left: 1px solid #cca100;
	border-right: 1px solid #cca100;
	border-bottom: 2px solid #997a00;
	border-radius: 3px;
	padding: 3px 12px;
	cursor: pointer;
	box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.25);
	display: inline-block;
}

.btn-create-dataset:hover {
	background: #fcd116;
}

.btn-container {
	text-align: right;
	padding-right: 145px !important;
	/* Forces the button inline-alignment precisely under the dates */
	padding-top: 12px !important;
}

/* Bottom "Return to Top" Anchor Link */
.footer-link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #0000ff;
	text-decoration: underline;
	margin-left: 6px;
	margin-top: 30px;
	display: inline-block;
}
</style>
<script type="text/javascript">
	function init() {
		var endingInventory = document.getElementById("selEndingInventory");
		if (endingInventory) {
			endingInventory.disabled = true;
		}
	}

	function validateFormData(formId, commandValue) {
		var cmdElement = document.getElementById("form_cmd");
		var formElement = document.getElementById(formId);
		if (cmdElement && formElement) {
			cmdElement.value = commandValue;
			formElement.submit();
		}
	}

	function checkLength(textarea, maxLength) {
		if (textarea.value.length > maxLength) {
			textarea.value = textarea.value.substring(0, maxLength);
			return false;
		}
		return true;
	}
</script>
</head>
<body onload="init();" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

	<!-- JSTL Request Title Context Scope Token -->
	<c:set var="pageTitle" value="Create Margin Billed Exchange Flex"
		scope="request" />

	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<div id="divToHide">
		<s:form id="createCostExchModelForm" action="createCostExchModel"
			method="POST">
			<s:hidden id="form_cmd" name="cmd" value="CREATE_MODEL" />

			<table class="tableCommand">
				<!-- Header Banner Section -->
				<tr>
					<td colspan="5" class="tableHeading">Create New Margin Billed
						Exchange Flex Dataset</td>
				</tr>

				<!-- Dataset Name Column Mapping -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel" style="width: 130px;">Dataset
						Name</td>
					<td colspan="3"><s:textfield name="modelName" size="30"
							maxlength="30" cssClass="commandOption" style="width: 215px;" />
					</td>
				</tr>

				<!-- Dataset Description Mapping -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel">Dataset Desc.</td>
					<td colspan="3"><s:textfield name="modelDesc" size="65"
							maxlength="80" cssClass="commandOption" style="width: 440px;" />
					</td>
				</tr>

				<!-- Rate Set & Starting Period Elements -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel">Rate Set</td>
					<td style="width: 250px;"><s:select name="selRateSet"
							list="rateSets" listKey="datasetTableId" listValue="datasetName"
							cssClass="commandOption" style="width: 155px;" /></td>
					<td class="right-align-label" style="width: 160px;">Starting
						D.5.6 Period</td>
					<td><s:select name="selStartPeriod" list="monthListNumber"
							listKey="value" listValue="label" cssClass="commandOption"
							style="width: 38px;" /> <s:select name="selStartYear"
							list="yearList" listKey="value" listValue="label"
							cssClass="commandOption" style="width: 58px;" /></td>
				</tr>

				<!-- Starting RGM & Ending Period Layout Elements -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel">Starting RGM Ver.</td>
					<td><s:select name="selStartingSalesData" list="salesData"
							listKey="datasetTableId" listValue="datasetName"
							cssClass="commandOption" style="width: 130px;" /></td>
					<td class="right-align-label">Ending D.5.6 Period</td>
					<td><s:select name="selEndPeriod" list="monthListNumber"
							listKey="value" listValue="label" cssClass="commandOption"
							style="width: 38px;" /> <s:select name="selEndYear"
							list="yearList" listKey="value" listValue="label"
							cssClass="commandOption" style="width: 58px;" /></td>
				</tr>

				<!-- Optional Fields Mapping -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel">Use Ending RGM Ver.</td>
					<td><s:select name="selEndingSalesData" list="salesData"
							listKey="datasetTableId" listValue="datasetName" headerKey=""
							headerValue="--none--" cssClass="commandOption"
							style="width: 130px;" /></td>
					<td class="right-align-label">Factor Model</td>
					<td><s:select name="modelSelected" list="factorModels"
							listKey="modelId" listValue="name" headerKey=""
							headerValue="--none--" cssClass="commandOption"
							style="width: 175px;" /></td>
				</tr>

				<!-- Big Multi-Column Units Mapping Dropdown -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel">Units</td>
					<td colspan="3"><s:select name="selCostExchUnits"
							list="unitSets" listKey="datasetTableId"
							listValue="datasetNameLogStamp" cssClass="commandOption"
							style="width: 635px;" /></td>
				</tr>

				<!-- Large Dataset Context Memo Box -->
				<tr>
					<td style="width: 20px;"></td>
					<td class="commandOptionLabel"
						style="vertical-align: top; padding-top: 6px;">Memo:</td>
					<td colspan="3"><s:textarea name="memo" cols="68" rows="7"
							onkeyup="return checkLength(this, 425);" cssClass="commandOption"
							style="width: 440px; height: 135px;" /></td>
				</tr>

				<!-- Action Submit Button Row -->
				<tr>
					<td colspan="4"></td>
					<td class="btn-container">
						<button type="button" class="btn-create-dataset"
							onclick="validateFormData('createCostExchModelForm', 'CREATE_MODEL');">
							Create Dataset</button>
					</td>
				</tr>
			</table>
		</s:form>

		<a href="#top" class="footer-link">Return to Top</a>
	</div>

	<s:include value="/include/footer.jsp" />
</body>
</html>
