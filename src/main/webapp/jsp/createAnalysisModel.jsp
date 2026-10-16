<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/include/header.jsf"%>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<s:set var="pageTitle" value="'Create Factor Analysis'" scope="page" />
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<style type="text/css">
/* Precise reproduction style layout rule matching production image button aesthetics */
.btn-create-model {
	display: inline-block;
	background: linear-gradient(to bottom, #FFF7A1 0%, #FFD700 100%);
	border: 1px solid #C2A800;
	border-radius: 4px;
	color: #333333;
	font-family: Arial, sans-serif;
	font-size: 12px;
	font-weight: bold;
	padding: 2px 14px;
	height: 24px;
	cursor: pointer;
	box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
	text-shadow: 0px 1px 0px rgba(255, 255, 255, 0.5);
	transition: background 0.1s ease;
}

.btn-create-model:hover {
	background: linear-gradient(to bottom, #FFE86B 0%, #E6C000 100%);
}

.btn-create-model:active {
	transform: translateY(1px);
	box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.2);
}
</style>

	<s:if test="hasActionErrors()">
		<div class="tcgm-error-msg-container">
			<s:iterator value="actionErrors">
				<span class="tcgm-error-msg-text"> <s:property
						escapeHtml="false" />
				</span>
			</s:iterator>
		</div>
	</s:if>

	<s:form id="createAnalysisModelForm" action="createAnalysisModel"
		method="post" theme="simple">

		<table class="tableCommand" width="820" cellpadding="3"
			cellspacing="1" border="0">
			<tr>
				<td colspan="5" bgcolor="#b4d8f4">
					<p class="tableHeading"
						style="font-family: Arial, Helvetica, sans-serif; font-size: 13px; font-weight: bold; margin: 4px; color: #1A365D;">Create
						New Factor Analysis</p>
				</td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<!-- EXACT FIX: Width set to 140 to force the label to wrap over two lines exactly like production -->
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 4px 0;">Factor
					Analysis Model Name</td>
				<td colspan="3" align="left"><s:textfield name="modelName"
						id="modelName" size="30" cssClass="commandOption" maxlength="30"
						style="font-family: Arial; font-size: 12px; width: 220px;" /></td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 4px 0;">Model
					Desc</td>
				<td colspan="3" align="left"><s:textfield name="modelDesc"
						id="modelDesc" size="65" cssClass="commandOption" maxlength="80"
						style="font-family: Arial; font-size: 12px; width: 450px;" /></td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 4px 0;">Base
					Factor Model</td>
				<td width="320" align="left"><s:select name="baseModelSelected"
						id="baseModelSelected" list="factorModels" listKey="modelId"
						listValue="name" cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 310px;" /></td>
				<td width="55" class="commandOptionLabel" align="right"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding-right: 8px;">Period</td>
				<td width="290" align="left"><s:select name="baseModelPeriod"
						id="baseModelPeriod" list="periodMonthList" listKey="value"
						listValue="label" headerKey="13" headerValue="13"
						cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 55px;" /></td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 4px 0;">Analysis
					Factor Model</td>
				<td align="left"><s:select name="analysisModelSelected"
						id="analysisModelSelected" list="factorModels" listKey="modelId"
						listValue="name" cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 310px;" /></td>
				<td class="commandOptionLabel" align="right"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding-right: 8px;">Period</td>
				<td align="left"><s:select name="analysisModelPeriod"
						id="analysisModelPeriod" list="periodMonthList" listKey="value"
						listValue="label" headerKey="13" headerValue="13"
						cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 55px;" /></td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 4px 0;">Base
					Units</td>
				<td align="left"><s:select name="analysisUnitsSelected"
						id="analysisUnitsSelected" list="unitSets"
						listKey="datasetTableId" listValue="datasetNameLogStamp"
						cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 620px;" /></td>
				<td class="commandOptionLabel" align="right"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding-right: 8px;">Period</td>
				<td align="left"><s:select name="analysisUnitsPeriod"
						id="analysisUnitsPeriod" list="periodMonthList" listKey="value"
						listValue="label" headerKey="13" headerValue="13"
						cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 55px;" /></td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 4px 0;">Analysis
					Units</td>
				<td align="left"><s:select name="volumeUnitsSelected"
						id="volumeUnitsSelected" list="unitSets" listKey="datasetTableId"
						listValue="datasetNameLogStamp" cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 620px;" /></td>
				<td class="commandOptionLabel" align="right"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding-right: 8px;">Period</td>
				<td align="left"><s:select name="volumeUnitsPeriod"
						id="volumeUnitsPeriod" list="periodMonthList" listKey="value"
						listValue="label" headerKey="13" headerValue="13"
						cssClass="commandOption"
						style="font-family: Arial; font-size: 12px; width: 55px;" /></td>
			</tr>

			<tr>
				<td width="15" class="right"></td>
				<td width="140" class="commandOptionLabel"
					style="font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #000000; padding: 8px 0 valign: top;">Memo:</td>
				<td colspan="3" align="left">
					<!-- EXACT FIX: Adjusted dimensions and font styles to perfectly hold layout text without shifting surrounding elements -->
					<s:textarea name="memo" id="memo" cols="60" rows="6"
						onkeyup="return checkLength(this, 425);" cssClass="commandOption"
						style="font-family: Arial, sans-serif; font-size: 12px; width: 450px; height: 110px; resize: none;" />
				</td>
			</tr>

			<tr>
				<td class="right">&nbsp;</td>
				<td class="commandOptionLabel">&nbsp;</td>
				<td colspan="3" align="right"
					style="padding-top: 15px; padding-right: 172px;">
					<button type="button" class="btn-create-model"
						onclick="validateFormData(document.getElementById('createAnalysisModelForm'), 'CREATE_MODEL');">
						Create Model</button>
				</td>
			</tr>
		</table>

		<s:hidden name="cmd" id="formCmd" />
	</s:form>


	<p>&nbsp;</p>
	<p>&nbsp;</p>

	<script language="javascript">
		function validateFormData(formObj, actionCmd) {
			if (!formObj)
				return;

			var hiddenCmd = document.getElementById('formCmd');
			if (hiddenCmd) {
				hiddenCmd.value = actionCmd;
				formObj.submit();
			}
		}

		function checkLength(field, maxLimit) {
			if (field.value.length > maxLimit) {
				field.value = field.value.substring(0, maxLimit);
				return false;
			}
			return true;
		}
	</script>

	<%@ include file="/include/footer.jsf"%>