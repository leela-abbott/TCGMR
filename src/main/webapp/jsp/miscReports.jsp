<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Miscellaneous Reports" scope="request" />
<head>
<meta charset="UTF-8">
<title>Miscellaneous Reports</title>
<%@ include file="/include/header.jsf"%>
<style type="text/css">
.btn-job {
	background: #ffea4a;
	background: linear-gradient(to bottom, #fff67a 0%, #ffea4a 30%, #e0c716 100%);
	border: 1px solid #bfa506;
	color: #0b3168;
	padding: 0px 4px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	cursor: pointer;
	border-radius: 4px;
	width: 175px;
	height: 22px;
	line-height: 20px;
	text-align: center;
	box-sizing: border-box;
	box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	display: inline-block;
	vertical-align: middle;
}

.btn-job:hover {
	background: linear-gradient(to bottom, #fff899 0%, #ffef66 30%, #ebd121 100%);
	border-color: #a38d04;
}

.dropdown-auto-width {
	width: auto;
	min-width: 175px;
	max-width: 550px;
	box-sizing: border-box;
}
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form action="miscReports" method="post" theme="simple"
		style="width: 100%;">
		<s:hidden name="cmd" id="cmdField" />
		<s:hidden name="jobName" id="jobNameField" />

		<div id="divToHide" style="width: 100%;">
			<%@ include file="/include/jobOptions.jsf"%>
			<table class="tableCommand" style="width: 100%;">
				<tr>
					<td colspan="2" class="tableHeading">Misc Jobs and Reports</td>
				</tr>
				<tr>
					<td width="20">&nbsp;</td>
					<td align="left">
						<table cellspacing="4"
							style="border-collapse: separate; border-spacing: 12px 6px; width: 100%;">
							<tr>
								<td width="20%" valign="top">
									<button type="button" onclick="deleteAsrPartial()"
										class="btn-job">Delete ASR of Partial Trees</button>
								</td>
								<td width="40%" valign="top">
									<button type="button" onclick="addJob('BPC_PRT0CST')"
										class="btn-job">BPC with All Zero Costs</button>
								</td>
								<td width="40%" valign="top"></td>
							</tr>
							<tr>
								<td width="20%" valign="top">
									<button type="button" onclick="deleteAsr()" class="btn-job">Delete
										ASR w/o BP/C Match</button>
								</td>
								<td width="40%" valign="top">
									<button type="button" onclick="addJob('BPC_PRT0BP')"
										class="btn-job">BP/C with all Zero Prices</button>
								</td>
								<td width="40%" valign="top"></td>
							</tr>
							<tr>
								<td width="20%" valign="top">
									<button type="button" onclick="deleteBpc()" class="btn-job">Delete
										BP/C w/o ASR Match</button>
								</td>
								<td width="40%" valign="top">
									<button type="button" onclick="addJob('DELETE_AUDIT')"
										class="btn-job">Delete Audit Sets</button>
								</td>
								<td width="40%" valign="top"></td>
							</tr>

							<tr valign="top">
								<td width="20%" height="40"></td>
								<td width="40%">&nbsp;</td>
								<td width="40%">&nbsp;</td>
							</tr>

							<tr valign="top">
								<td width="20%" valign="top">
									<div class="commandOption"
										style="border: solid blue 0px; padding: 0px; width: 100%;">
										<button type="button" onclick="addJob('BPC_0PLNVSIM')"
											class="btn-job">Zero BP/C Compared w Model</button>
										<br>Comparison Model:<br>
										<s:select name="selComparisonModel" list="factorModels"
											listKey="modelId" listValue="name"
											cssClass="commandOption dropdown-auto-width" />
									</div>
								</td>
								<td width="40%" valign="top">
									<button type="button" onclick="addJob('ROUTE_EXCEPT')"
										class="btn-job">Route Exception</button> <br>
									<div class="commandOption" align="left"
										style="margin-right: 10px; width: 100%;">
										Starting D.5.6 Period:
										<s:select name="selRoutingStartPeriod" list="monthListNumber"
											listKey="value" listValue="label" headerKey="13"
											headerValue="13" cssClass="commandOption" />
										<s:select name="selRoutingStartYear" list="yearList"
											listKey="value" listValue="label" cssClass="commandOption" />
										<br> Ending D.5.6 Period:
										<s:select name="selRoutingEndPeriod" list="monthListNumber"
											listKey="value" listValue="label" headerKey="13"
											headerValue="13" cssClass="commandOption" />
										<s:select name="selRoutingEndYear" list="yearList"
											listKey="value" listValue="label" cssClass="commandOption" />
										<br> Beginning Routing Year Units: <br>
										<s:select name="begRouteUnitsSelected" list="unitSets"
											listKey="datasetTableId" listValue="datasetNameLogStamp"
											headerKey="" headerValue="<none>"
											cssClass="commandOption dropdown-auto-width" />
										<br> Ending Routing Year Units: <br>
										<s:select name="endRouteUnitsSelected" list="unitSets"
											listKey="datasetTableId" listValue="datasetNameLogStamp"
											headerKey="" headerValue="<none>"
											cssClass="commandOption dropdown-auto-width" />
									</div>
								</td>
								<td width="40%" valign="top" style="padding-left: 40px;">
									<button type="button" onclick="addJob('BPC_ANALYS')"
										class="btn-job">Inter Co Transfer Analysis</button>
									<div class="commandOption" style="width: 100%;">
										Period:
										<s:select name="interCoTransferPeriod" list="monthListNumber"
											listKey="value" listValue="label" headerKey="13"
											headerValue="13" cssClass="commandOption" />
										<br> Actual Units:
										<s:select name="actualUnitsSelected" list="unitSets"
											listKey="datasetTableId" listValue="datasetNameLogStamp"
											headerKey="0" headerValue="<none>"
											cssClass="commandOption dropdown-auto-width" />

										<s:textfield name="treeFilename" value="TCGMICTA"
											id="treeFilename" cssClass="commandOption"
											style="width: 175px; margin-top: 2px; font-weight: normal; color: #000000; -webkit-text-fill-color: #000000;"
											readonly="true" />
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</s:form>

	<script type="text/javascript">
		function addJob(commandName) {
			var cmdField = document.getElementById('cmdField');
			var jobNameField = document.getElementById('jobNameField');

			if (cmdField) {
				cmdField.value = "ADD_JOB";

				if (jobNameField) {
					jobNameField.value = commandName;
				}

				var form = cmdField.form;
				var jobElements = form.elements['jobName'];
				if (jobElements) {
					if (jobElements.length) {
						for (var j = 0; j < jobElements.length; j++) {
							jobElements[j].value = commandName;
						}
					} else {
						jobElements.value = commandName;
					}
				}

				form.submit();
			}
		}
		function deleteAsrPartial() {
			if (confirm("Do You Really Want to Delete ASR of Partial Trees Record?")) {
				addJob('ASR_DLT_PART');
			}
		}

		function deleteAsr() {
			if (confirm("Do You Really Want to Delete ASR w/o BP/C Records?")) {
				addJob('ASR_DELETE');
			}
		}

		function deleteBpc() {
			if (confirm("Do You Really Want to Delete BP/C w/o ASR Records?")) {
				addJob('BPC_DELETE');
			}
		}
	</script>
	<%@ include file="/include/footer.jsf"%>
</body>
</html>
