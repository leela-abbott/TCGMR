<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="../include/header.jsf"%>

<c:set var="pageTitle" value="Data Feed Log" scope="request" />

<style>
body {
	font-family: "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
	color: #333333;
	background-color: #fcfcfc;
	margin: 0;
}

.tableContainer {
	width: 100%;
	margin: 0 auto;
	padding: 40px;
	padding-top: 0px;
	box-sizing: border-box;
	overflow-x: hidden;
}

.tableContainer p, .tableContainer form {
	margin: 0;
	padding: 0;
}

.tableCommand {
	width: 100%;
	table-layout: fixed;
	border-collapse: collapse;
	margin: 0;
}

.tableHeading {
	background-color: #b9d3ee;
	color: #1e3f66;
	font-weight: 600;
}

.tableHeading td {
	padding: 6px 8px;
	font-size: 12px;
	border-bottom: 2px solid #a2bee0;
}

.mntLeft {
	padding: 6px 8px;
	font-size: 11px;
	border-bottom: 1px solid #e2e8f0;
	line-height: 1.4;
}

.col-file {
	width: 44%;
	word-break: break-all;
	font-family: Consolas, "Courier New", monospace;
}

.col-time {
	width: 16%;
	white-space: nowrap;
}

.col-msg {
	width: 24%;
}

.btn-action-yellow {
	background: linear-gradient(to bottom, #ffe875 0%, #f7cd1d 100%);
	border: 1px solid #d4ae00;
	border-radius: 4px;
	color: #2b2b2b;
	font-family: inherit;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	padding: 4px 10px;
	cursor: pointer;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.btn-action-yellow:hover {
	background: linear-gradient(to bottom, #fff094 0%, #ecd043 100%);
}
</style>
<script>
function changeCmdAndSubmit(form,cmd)
{
	form.cmd.value = cmd;
	form.submit();
}

</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="../include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<div class="tableContainer">
		<s:form method="post" id="mngDataFeedLogForm"
			name="mngDataFeedLogForm" action="mngDataFeedLog" theme="simple">

			<table class="tableCommand">
				<colgroup>
					<col class="col-file" />
					<col class="col-time" />
					<col class="col-time" />
					<col class="col-msg" />
				</colgroup>
				<tr>
					<td colspan="4" align="right" style="padding: 4px 0 8px 0;">
						<button type="button" class="btn-action-yellow"
							onclick="changeCmdAndSubmit(this.form, 'DELETE_ALL')">
							Delete All</button>
					</td>
				</tr>
				<tr class="tableHeading">
					<td>File</td>
					<td>Start Time</td>
					<td>End Time</td>
					<td>Completion Message</td>
				</tr>

				<c:forEach var="mngDataFeedLogBean" items="${logentrylist}"
					varStatus="mngDataFeedLogStatus">
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow"
						rowNum="${mngDataFeedLogStatus.index}" id="mntRow">
						<td class="mntLeft col-file"><c:out
								value="${mngDataFeedLogBean.fullFileName}" /></td>
						<td class="mntLeft col-time"><c:out
								value="${mngDataFeedLogBean.startTimeInStringFormat}" /></td>
						<td class="mntLeft col-time"><c:out
								value="${mngDataFeedLogBean.endTimeInStringFormat}" /></td>
						<td class="mntLeft col-msg"><c:out
								value="${mngDataFeedLogBean.completionMsg}" /></td>
					</abbott:row>
				</c:forEach>

				<s:hidden name="cmd" id="cmd" />
			</table>
		</s:form>
	</div>

	<%@ include file="../include/footer.jsf"%>