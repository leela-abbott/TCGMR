<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%!String pageTitle = "Perpetual Model Management";%>

<%@ include file="/include/header.jsf"%>
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />

<style type="text/css">
.errorMessage, .errorMessageBlock, .errorDisplay {
	background-color: transparent !important;
	background: none !important;
	border: none !important;
	box-shadow: none !important;
	color: #CC0000 !important;
	padding: 4px 0px !important;
}

.dataDisp {
	display: block;
	width: 100%;
	min-height: 180px;
	padding: 10px 15px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
	line-height: 1.6;
	color: #003366;
	box-sizing: border-box;
	text-align: left;
}

.dataDisp b {
	color: #003366;
	font-weight: bold;
	display: inline-block;
}

.prod-btn {
	display: inline-block;
	width: 145px;
	box-sizing: border-box;
	background: #FFD700;
	color: #000000;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	font-style: italic;
	text-align: center;
	text-decoration: none;
	padding: 4px 2px;
	margin-bottom: 5px;
	border-top: 2px solid #FFFDF0;
	border-left: 2px solid #FFFDF0;
	border-right: 2px solid #9A7B00;
	border-bottom: 2px solid #9A7B00;
	box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.4);
	text-shadow: 0.5px 0.5px 0px rgba(0, 0, 0, 0.2);
	cursor: pointer;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	font-style: italic;
}

.prod-btn:active {
	border-top: 2px solid #9A7B00;
	border-left: 2px solid #9A7B00;
	border-right: 2px solid #FFFDF0;
	border-bottom: 2px solid #FFFDF0;
	box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.3);
}

.prod-btn:hover {
	background: #FFE240;
	text-decoration: none;
	color: #000000;
}
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<c:set var="pageTitle" value="Perpetual Model Management"
		scope="request" />
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<div id="divToHide">

		<s:form id="mngPerpetualModelsForm" name="mngPerpetualModelsForm"
			action="mngPerpetualModels" method="POST">

			<s:hidden name="cmd" id="hiddenCmd" />
			<s:hidden name="showPerpetualModel" id="showPerpetualModel" />
			<s:hidden name="jobName" id="hiddenJobName" />

			<%@ include file="/include/jobOptions.jsf"%>

			<table class="tableCommand">
				<c:if test="${showPerpetualModel == 'open'}">
					<tr>
						<td colspan="3" class="tableHeading">Perpetual Models</td>
						<td class="tableHeading">Model Details</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td width="170"><s:select size="6" name="modelSelected"
								id="modelSelected" list="models" listKey="modelId"
								listValue="name" onchange="updateDetails()" /> <br>&nbsp;
							<abbott:securePage
								userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
								requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
								comparisonType="=">
								<a class="CmdSm"
									href="javascript:showClosedModelDetails('closed')">Show
									Closed Model</a>
							</abbott:securePage></td>
						<td width="150" valign="top">
							<p>
								<a
									href="javascript:deleteModel(document.getElementById('modelSelected'))"
									class="prod-btn"> Delete Model </a>
							</p>
							<p>
								<a
									href="javascript:closeModel(document.getElementById('modelSelected'))"
									class="prod-btn"> Close Model </a>
							</p>
							<p>
								<a href="createPerpetualModel.action" class="prod-btn">
									Create New Model </a>
							</p>
							<p>
								<a href="javascript:addJobLocal('PERP_TOTCALC')"
									class="prod-btn"> Calculate Perpetual </a>
							</p>
							<p>
								<a href="javascript:addJobLocal('PPT_INVCALC1')"
									class="prod-btn"> Calc &amp; Equiv Units </a>
							</p>
						</td>

						<td width="285"><div id="divModelDetail" class="dataDisp"></div></td>
					</tr>
				</c:if>

				<c:if test="${showPerpetualModel == 'closed'}">
					<tr>
						<td colspan="3" class="tableHeading">Closed Perpetual Models</td>
						<td class="tableHeading">Model Details</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td width="170"><s:select size="6" name="modelSelected"
								id="modelSelected" list="models" listKey="modelId"
								listValue="name" onchange="updateDetails()" /> <br>&nbsp;
							<abbott:securePage
								userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
								requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
								comparisonType="=">
								<a class="CmdSm"
									href="javascript:showClosedModelDetails('open')">Show Open
									Model</a>
							</abbott:securePage></td>
						<td width="150" valign="top">
							<p>
								<a
									href="javascript:openModel(document.getElementById('modelSelected'))"
									class="prod-btn"> Open Model </a>
							</p>
						</td>
						<td width="285"><div id="divModelDetail" class="dataDisp"></div></td>
					</tr>
				</c:if>
			</table>

			<table class="tableCommand">
				<tr>
					<td colspan="2" class="tableHeading">Reports</td>
				</tr>
				<tr valign="top">
					<td width="20">&nbsp;</td>
					<td width="420">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td style="padding-right: 15px;"><a
									href="javascript:addJobLocal('PERP_SUMMARY')" class="prod-btn"
									style="width: 165px;"> Costed Summary </a></td>
								<td><a href="javascript:addJobLocal('PERP_DETAIL')"
									class="prod-btn" style="width: 165px;"> Costed Detail </a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
	<p>&nbsp;</p>

	<script type="text/javascript">
		function getSelectedOptionText(se) {
			if (se.selectedIndex == -1)
				return "";
			return se.options[se.selectedIndex].text;
		}

		function changeActionAndSubmit(formElement, actionUrl) {
			formElement.action = actionUrl;
			formElement.submit();
		}

		function addJobLocal(jobDefCode) {
			var form = document.mngPerpetualModelsForm;
			document.getElementById('hiddenCmd').value = "ADD_JOB";
			document.getElementById('hiddenJobName').value = jobDefCode;
			changeActionAndSubmit(form, "mngPerpetualModels.action");
		}

		function deleteModel(se) {
			if (!se || se.value == "" || se.value == "0") {
				alert("You must select a model to delete.");
				return;
			}
			var modelName = getSelectedOptionText(se);
			if (confirm("Are you sure that you would like to delete "
					+ modelName
					+ "?\nThis will delete all attributes and can not be undone.")) {
				changeActionAndSubmit(document.mngPerpetualModelsForm,
						"deletePerpetualModel.action");
			}
		}

		function closeModel(se) {
			if (!se || se.value == "" || se.value == "0") {
				alert("You must select a model to close.");
				return;
			}
			var modelName = getSelectedOptionText(se);
			if (confirm("Are you sure that you would like to close "
					+ modelName + "?")) {
				changeActionAndSubmit(document.mngPerpetualModelsForm,
						"closePerpetualModel.action");
			}
		}

		function openModel(se) {
			if (!se || se.value == "" || se.value == "0") {
				alert("You must select a model to open.");
				return;
			}
			var modelName = getSelectedOptionText(se);
			if (confirm("Are you sure that you would like to open " + modelName
					+ "?")) {
				changeActionAndSubmit(document.mngPerpetualModelsForm,
						"openPerpetualModel.action");
			}
		}

		function updateDetails() {
			var selectElement = document.getElementById('modelSelected');
			if (!selectElement || selectElement.value == ""
					|| selectElement.value == "0") {
				document.getElementById('divModelDetail').innerHTML = "";
				return;
			}

			document.body.style.cursor = 'wait';
			processActiveURL(
					'tcgmAjax.action?cascadingCmd=ModelInfo&cascadingVal='
							+ selectElement.value
							+ '&cascadingSecVal=PERPETUAL', 'divModelDetail');
			document.body.style.cursor = "default";
		}

		function showClosedModelDetails(stat) {
			document.getElementById('showPerpetualModel').value = stat;
			var form = document.mngPerpetualModelsForm;
			changeActionAndSubmit(form, "mngPerpetualModels.action");
		}

		window.onload = function() {
			var selectElement = document.getElementById('modelSelected');
			if (selectElement && selectElement.value != ""
					&& selectElement.value != "0") {
				updateDetails();
			}
		};
	</script>

	<%@ include file="/include/footer.jsf"%>