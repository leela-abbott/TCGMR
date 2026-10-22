<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%!String pageTitle = "Create Perpetual";%>

<%@ include file="/include/header.jsf"%>

<style type="text/css">
.prod-btn {
	display: inline-block;
	box-sizing: border-box;
	background: #FFD700;
	color: #000000;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	text-align: center;
	text-decoration: none;
	padding: 3px 12px;
	border-top: 2px solid #FFFDF0;
	border-left: 2px solid #FFFDF0;
	border-right: 2px solid #9A7B00;
	border-bottom: 2px solid #9A7B00;
	box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
	cursor: pointer;
}

.prod-btn:active {
	border-top: 2px solid #9A7B00;
	border-left: 2px solid #9A7B00;
	border-right: 2px solid #FFFDF0;
	border-bottom: 2px solid #FFFDF0;
}

.prod-btn:hover {
	background: #FFE240;
	text-decoration: none;
	color: #000000;
}

.commandOption {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}

.commandOptionLabel {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	color: #003366;
}
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
	onload="init()">
	<c:set var="pageTitle" value="Create Perpetual" scope="request" />
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form id="createPerpetualModelForm" name="createPerpetualModelForm"
		action="createPerpetualModel" method="POST">
		<s:hidden name="cmd" id="hiddenCmd" />

		<table class="tableCommand" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="6" class="tableHeading">Create New Perpetual Model</td>
			</tr>
			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Model
					Name</td>
				<td colspan="4"><s:textfield name="modelName" size="30"
						cssClass="commandOption" maxlength="30" theme="simple" /></td>
			</tr>
			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Model
					Desc.</td>
				<td colspan="4"><s:textfield name="modelDesc" size="65"
						cssClass="commandOption" maxlength="80" theme="simple" /></td>
			</tr>

			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Starting
					Inventory Model &amp; Units</td>
				<td width="135"><s:select name="selStartingModel"
						list="factorModels" listKey="modelId" listValue="name"
						cssClass="commandOption" theme="simple" /></td>
				<td width="135"><s:select name="selStartingModelUnits"
						list="units" listKey="datasetName" listValue="datasetName"
						cssClass="commandOption" theme="simple" /></td>
				<td width="150" colspan="2">&nbsp;</td>
			</tr>

			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Current
					Actual Model &amp; Actual Units</td>
				<td width="135"><s:select name="selCurrentYearActualModel"
						list="factorModels" listKey="modelId" listValue="name"
						cssClass="commandOption" theme="simple" /></td>
				<td width="135"><s:select name="selCurrentYearActualUnits"
						list="units" listKey="datasetName" listValue="datasetName"
						cssClass="commandOption" theme="simple" /></td>
				<td width="150" align="right" class="commandOptionLabel">Starting
					Period</td>
				<td width="141"><s:select name="selStartPeriod"
						list="monthListNumber" listKey="value" listValue="label"
						headerKey="13" headerValue="13" cssClass="commandOption"
						theme="simple" /> <s:select name="selStartYear" list="yearList"
						listKey="value" listValue="label" cssClass="commandOption"
						theme="simple" /></td>
			</tr>

			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Previous
					Actual Model &amp; Actual Units</td>
				<td width="135"><s:select name="selLastYearActualModel"
						list="factorModels" listKey="modelId" listValue="name"
						cssClass="commandOption" theme="simple" /></td>
				<td width="135"><s:select name="selLastYearActualUnits"
						list="units" listKey="datasetName" listValue="datasetName"
						cssClass="commandOption" theme="simple" /></td>
				<td width="150" align="right" class="commandOptionLabel">Ending
					Period</td>
				<td width="141"><s:select name="selEndPeriod"
						list="monthListNumber" listKey="value" listValue="label"
						headerKey="13" headerValue="13" cssClass="commandOption"
						theme="simple" /> <s:select name="selEndYear" list="yearList"
						listKey="value" listValue="label" cssClass="commandOption"
						theme="simple" /></td>
			</tr>

			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Costing
					Model</td>
				<td colspan="4"><s:select name="selCostingModel"
						list="factorModels" listKey="modelId" listValue="name"
						cssClass="commandOption" theme="simple" /></td>
			</tr>

			<tr>
				<td width="20"></td>
				<td width="158" align="right" class="commandOptionLabel">Ending
					Inventory &amp; Units</td>
				<td align="left"><s:select name="selEndingModel"
						list="factorModels" listKey="modelId" listValue="name"
						headerKey="NONE" headerValue="--none--" cssClass="commandOption"
						theme="simple" onchange="validateEndingCBO()" /></td>
				<td width="135"><s:select name="selEndingModelUnits"
						id="selEndingModelUnits" list="units" listKey="datasetName"
						listValue="datasetName" cssClass="commandOption" theme="simple" />
				</td>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr>
				<td width="20"></td>
				<td align="right" class="commandOptionLabel">Memo &nbsp;&nbsp;</td>
				<td colspan="4"><s:textarea name="memo" cols="68" rows="7"
						onkeyup="return checkLength(this, 425);" cssClass="commandOption"
						theme="simple" /></td>
			</tr>

			<tr>
				<td></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan="2" align="left" style="padding-top: 10px;">
					<button type="button" class="prod-btn"
						onclick="validateFormData('CREATE_MODEL')">Create Model</button>
				</td>
			</tr>
		</table>
	</s:form>

	<script type="text/javascript">
		function init() {
			validateEndingCBO();
		}

		function validateEndingCBO() {
			var selectElement = document.getElementsByName('selEndingModel')[0];
			var endingUnits = document.getElementById('selEndingModelUnits');
			if (selectElement && endingUnits) {
				endingUnits.disabled = false;
			}
		}

		function validateFormData(commandValue) {
			var form = document.getElementById('createPerpetualModelForm');
			var mName = document.getElementsByName('modelName')[0];
			var mDesc = document.getElementsByName('modelDesc')[0];

			if (mName && mName.value.trim() === "") {
				mName.value = "";
			}
			if (mDesc && mDesc.value.trim() === "") {
				mDesc.value = "";
			}

			document.getElementById('hiddenCmd').value = commandValue;
			form.submit();
		}

		function checkLength(control, maxLength) {
			if (control.value.length > maxLength) {
				control.value = control.value.substring(0, maxLength);
				alert("Memo field cannot exceed " + maxLength + " characters.");
				return false;
			}
			return true;
		}
	</script>

	<%@ include file="/include/footer.jsf"%>