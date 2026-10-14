<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%-- 1. Pre-initialize pageTitle context BEFORE pulling layout headers --%>
<%!String pageTitle = "Job Q Management";%>

<%-- 2. Pull down the document headers using your standard working static include --%>
<%@ include file="/include/header.jsf"%>
<c:set var="pageTitle" value="Job Q Management" scope="request" />
<head>
<style>
/* Global screen expansion container rules overriding static limits */
.screen-wrapper {
	width: 100%;
	box-sizing: border-box;
	padding: 10px 20px;
}

.mgmt-table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.mgmt-table td {
	padding: 4px 8px;
	vertical-align: top;
}

/* Production UI Header Styles matching original screenshot branding */
.tableHeading {
	background-color: #b4d8f4;
	font-family: Arial, sans-serif;
	font-size: 12px;
	font-weight: bold;
	color: #002266;
	padding: 6px 12px !important;
	text-align: left;
}

.subtitle-text {
	font-family: Arial, sans-serif;
	font-size: 11px;
	color: #555555;
	font-weight: normal;
	font-style: italic;
}

.value-display-box {
	font-family: Arial, sans-serif;
	font-size: 13px;
	padding: 12px;
	text-align: center;
	min-height: 20px;
}

.select-box-style {
	width: 100%;
	font-family: Courier New, monospace;
	font-size: 12px;
	padding: 4px;
	border: 1px solid #999999;
}

/* Production UI Control Stack layout layout */
.controls-column {
	width: 140px;
	text-align: center;
	padding-left: 15px !important;
}

/* Replica Medium Yellow-Gold Button Design metrics applied globally */
.btn-action {
	display: block;
	width: 125px;
	background: #FFD523;
	background: linear-gradient(to bottom, #FFE054 0%, #FFD523 100%);
	color: #000000;
	font-family: Arial, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	padding: 3px 6px;
	margin: 0 auto 4px auto;
	border: 1px solid #D4B218;
	border-radius: 3px;
	cursor: pointer;
	text-align: center;
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.3), 0 1px 1px
		rgba(0, 0, 0, 0.15);
}

.btn-action:hover {
	background: #E6BE1E;
}
</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<div class="screen-wrapper">
		<s:form id="processForm" name="processForm" action="" method="post"
			theme="simple">
			<div id="divToHide">
				<table class="mgmt-table">
					<!-- 1. Current Job Header Matrix Split (Takes 100% of the screen) -->
					<tr>
						<td class="tableHeading" style="width: 80%;">Current Job <span
							class="subtitle-text">(Job Name | Model Name | Start Time
								| User Name)</span>
						</td>
						<td class="tableHeading" style="width: 20%; text-align: center;">Scheduler
							Status</td>
					</tr>
					<tr>
						<td class="value-display-box"
							style="border-bottom: 1px solid #dddddd;"><s:if
								test="processSchedulerStatus == 'Running' && currentProcess != null && currentProcess != '' && currentProcess != '<none>'">
								<b><s:property value="currentProcess" /></b>
							</s:if></td>
						<td class="value-display-box"
							style="border-bottom: 1px solid #dddddd; font-weight: bold;">
							<s:if
								test="currentProcess == null || currentProcess == '' || currentProcess == '<none>'">
                    Stopped
                </s:if> <s:else>
								<s:property value="processSchedulerStatus" />
							</s:else>
						</td>
					</tr>

					<tr>
						<td colspan="2" style="height: 15px;">&nbsp;</td>
					</tr>

					<!-- 2. Pending Jobs Grid Section Layout Layout -->
					<tr>
						<td class="tableHeading" colspan="2">Pending Jobs <span
							class="subtitle-text">(Job Name | Model Name | Status |
								Submit Time | User Name)</span>
						</td>
					</tr>
					<tr>
						<td class="list-wrapper"><s:if test="processListSize > 0">
								<select name="selPendingJob" id="selPendingJob" size="10"
									class="select-box-style">
									<s:iterator value="processList" var="processes">
										<option value="<s:property value="#processes.jobQueId"/>">
											<s:property value="#processes.desc" /> |
											<s:property value="#processes.jobStatus.name" /> |
											<s:property value="#processes.submitTime" /> |
											<s:property value="#processes.userSubmitted" />
										</option>
									</s:iterator>
								</select>
							</s:if> <s:else>
								<div
									style="padding: 30px; text-align: center; font-family: Arial, sans-serif; font-size: 13px;">There
									are no pending jobs</div>
							</s:else></td>
						<td class="controls-column">
							<button type="button" class="btn-action"
								onclick="changeJobStatus(document.getElementById('selPendingJob'), 'I')">Immediate</button>
							<button type="button" class="btn-action"
								onclick="changeJobStatus(document.getElementById('selPendingJob'), 'B')">Batch</button>
							<button type="button" class="btn-action"
								onclick="reorderJobQue(document.getElementById('selPendingJob'), (1 - parseInt('<s:property value="processListSize"/>')))">Move
								to Top</button>
							<button type="button" class="btn-action"
								onclick="reorderJobQue(document.getElementById('selPendingJob'), -1)">Move
								Up</button>
							<button type="button" class="btn-action"
								onclick="reorderJobQue(document.getElementById('selPendingJob'), 1)">Move
								Down</button>
							<button type="button" class="btn-action"
								onclick="reorderJobQue(document.getElementById('selPendingJob'), (parseInt('<s:property value="processListSize"/>') - 1))">Move
								to Bottom</button>
							<button type="button" class="btn-action"
								onclick="deleteJob(document.getElementById('selPendingJob'))">Delete</button>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="height: 15px;">&nbsp;</td>
					</tr>

					<!-- 3. Completed Jobs Grid Section Layout Layout -->
					<tr>
						<td class="tableHeading" colspan="2">Completed Jobs <span
							class="subtitle-text">(Job Name | Model Name | Status |
								End Time | User Name)</span>
						</td>
					</tr>
					<tr>
						<td class="list-wrapper"><s:if
								test="completedJobListSize > 0">
								<select name="selFailedJob" id="selFailedJob" size="10"
									class="select-box-style">
									<s:iterator value="completedJobList" var="processes">
										<option value="<s:property value="#processes.jobQueId"/>">
											<s:property value="#processes.desc" /> |
											<s:property value="#processes.jobStatus" /> |
											<s:property value="#processes.endTime" /> |
											<s:property value="#processes.userSubmitted" />
										</option>
									</s:iterator>
								</select>
							</s:if> <s:else>
								<div
									style="padding: 30px; text-align: center; font-family: Arial, sans-serif; font-size: 13px;">There
									are no failed jobs</div>
							</s:else></td>
						<td class="controls-column">
							<button type="button" class="btn-action"
								onclick="logWindow(document.processForm, 'logReport.action', document.getElementById('selPendingJob'), document.getElementById('selFailedJob'))">View
								Log</button>
							<button type="button" class="btn-action"
								onclick="generateReports(document.getElementById('selFailedJob'), 'PRINT_REPORTS')">Generate
								Reports</button>
							<div style="height: 35px;">&nbsp;</div>
							<button type="button" class="btn-action"
								onclick="deleteJob(document.getElementById('selFailedJob'))">Delete</button>
						</td>
					</tr>
				</table>
			</div>

			<s:hidden name="selProcessId" id="selProcessId" />
			<s:hidden name="cmd" id="cmd" />
			<s:hidden name="selJodDesc" id="selJodDesc" />
			<s:hidden name="selJodStatus" id="selJodStatus" />
		</s:form>
	</div>

	<script type="text/javascript">
		function deleteJob(selectList) {
			if (!selectList || selectList.value == 0) {
				alert("You must select a job to delete.");
				return;
			}
			var jobName = selectList.options[selectList.selectedIndex].text;
			if (confirm("Are you sure that you would like to delete " + jobName
					+ "?\nThis can not be undone.")) {
				document.getElementById('selProcessId').value = selectList.value;
				changeActionAndSubmit(document.forms['processForm'],
						"deleteJob.action");
			}
		}

		function changeJobStatus(selectList, sCode) {
			if (!selectList || selectList.value == 0) {
				alert("You must first select a job, then change its status.");
				return;
			}
			document.getElementById('selProcessId').value = selectList.value;
			document.getElementById('cmd').value = sCode;
			changeActionAndSubmit(document.forms['processForm'],
					"changeJobStatus.action");
		}

		function generateReports(selectList, sCode) {
			if (!selectList || selectList.value == 0) {
				alert("You must first select a job, then change its status.");
				return;
			}
			document.getElementById('selProcessId').value = selectList.value;
			document.getElementById('cmd').value = sCode;
			window
					.open(
							'generateReport.action?selProcessId='
									+ document.getElementById('selProcessId').value
									+ '&cmd='
									+ document.getElementById('cmd').value,
							"_left",
							"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=yes, width=600, height=300,left=0,top=100");
		}

		function reorderJobQue(selectList, offset) {
			if (!selectList || selectList.value == 0) {
				alert("You must select a job to move within the queue.");
				return;
			}
			document.getElementById('selProcessId').value = selectList.value;
			document.getElementById('cmd').value = offset;
			changeActionAndSubmit(document.forms['processForm'],
					"reorderJobQue.action");
		}

		function logWindow(form, action, selectList1, selectList2) {
			var targetedSelect = null;
			if (selectList1 && selectList1.value != 0) {
				targetedSelect = selectList1;
			} else if (selectList2 && selectList2.value != 0) {
				targetedSelect = selectList2;
			}
			if (!targetedSelect) {
				alert("You must first select a job to view the log.");
				return;
			}

			document.getElementById('selProcessId').value = targetedSelect.value;
			var selectedItemTxt = targetedSelect.options[targetedSelect.selectedIndex].text;
			var comaIdx = selectedItemTxt.indexOf(",");
			var dashIdx = selectedItemTxt.indexOf(":");
			var jbDesc = (comaIdx !== -1) ? selectedItemTxt.substr(0, comaIdx)
					: selectedItemTxt;
			var jbSts = (comaIdx !== -1 && dashIdx !== -1) ? selectedItemTxt
					.substring(comaIdx + 1, dashIdx - 17) : "Completed";

			document.getElementById('selJodDesc').value = jbDesc.trim();
			document.getElementById('selJodStatus').value = jbSts.trim();
			window
					.open(
							'errorReport.action?selProcessId='
									+ document.getElementById('selProcessId').value
									+ '&selJodDesc='
									+ encodeURIComponent(document
											.getElementById('selJodDesc').value)
									+ '&selJodStatus='
									+ encodeURIComponent(document
											.getElementById('selJodStatus').value),
							"_left",
							"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=900, height=530,left=0,top=100");
		}
		
		function changeActionAndSubmit(form,action)
		{
			form.action = action;
			form.submit();
		}
	</script>

	<%@ include file="/include/footer.jsf"%>
</body>
</html>