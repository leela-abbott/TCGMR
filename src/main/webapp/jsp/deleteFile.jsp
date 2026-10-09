<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%-- Struts 2 & JSTL Page Title Initialization Context --%>
<%!String pageTitle = "Delete File";%>
<c:set var="pageTitle" value="Delete File" scope="request" />

<head>
<style type="text/css">
body {
	font-family: Arial, sans-serif;
	background-color: #ffffff;
	color: #333333;
	margin: 0;
	padding: 0;
}

.search-container {
	width: 100%;
	max-width: 600px;
	margin: 40px auto;
	padding: 0 20px;
}

.results-fieldset {
	border: 1px solid #dddddd;
	border-radius: 6px;
	padding: 20px;
	margin-top: 20px;
	display: flex;
	flex-direction: column;
	box-sizing: border-box;
}

.results-legend {
	font-size: 16px;
	font-weight: bold;
	color: #0044aa;
	padding: 0 10px;
}

.modern-grid-table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 10px;
	font-size: 13px;
	margin-bottom: 20px;
}

.modern-grid-table th {
	background-color: #99ccff;
	color: #333333;
	font-weight: bold;
	text-align: left;
	padding: 10px;
	border: 1px solid #d0e0f5;
}

.modern-grid-table td {
	padding: 10px;
	border: 1px solid #e8f0fa;
}

.directory-item {
	display: flex;
	align-items: center;
	gap: 10px;
	padding: 10px;
	border-bottom: 1px solid #e8f0fa;
}

.directory-item a {
	color: #0044aa;
	text-decoration: none;
	font-weight: bold;
	font-size: 14px;
	display: flex;
	align-items: center;
	gap: 8px;
}

.directory-item a:hover {
	text-decoration: underline;
}

.directory-item img {
	width: 16px;
	height: 16px;
	border: 0;
}

.evenRow {
	background-color: #ffffff;
}

.oddRow {
	background-color: #ffffdd;
}

.btn-submit-orange {
	background: linear-gradient(to bottom, #ffcc44 0%, #ffbb22 100%);
	border: 1px solid #e5a515;
	border-radius: 6px;
	color: #222222;
	font-size: 14px;
	font-weight: bold;
	padding: 5px 25px;
	cursor: pointer;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	display: block;
	margin: 20px auto 10px auto;
	text-align: center;
	min-width: 160px;
}

.btn-submit-orange:hover {
	background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
}

.navigation-link-row {
	margin-top: 15px;
	font-size: 13px;
}

.navigation-link-row a {
	color: #0066cc;
	text-decoration: none;
	font-weight: bold;
}

.navigation-link-row a:hover {
	text-decoration: underline;
}

.text-center {
	text-align: center;
}

.text-left {
	text-align: left;
}
</style>

<script type="text/javascript">
	function setCursor() {
		document.body.style.cursor = "default";
	}
	function doCall() {
		var cnt = document.getElementById("hidVal");
		var incr = 0;
		var fname = "";

		if (cnt && cnt.value > 0) {
			for (var i = 0; i < cnt.value; i++) {
				var rdo = document.getElementById("files" + i);
				if (rdo && rdo.checked) {
					incr++;
					fname = rdo.value;
				}
			}
		}

		if (incr > 0) {
			document.getElementById("fileName").value = fname;
			document.getElementById("cmd").value = 'deletefile';
			document.getElementById("deleteFileForm").submit();
			return true;
		} else {
			alert("Please Select a File to be Deleted");
			return false;
		}
	}
</script>
</head>

<body bgcolor="white" onload="setCursor();" leftmargin="0" topmargin="0"
	marginwidth="0" marginheight="0">
	<%@ include file="/include/header.jsf"%>
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<div class="search-container">

		<s:form id="deleteFileForm" name="deleteFileForm" action="deleteFile"
			method="post" onsubmit="return doCall();">
			<s:hidden name="cmd" id="cmd" />
			<s:hidden name="fileName" id="fileName" />
			<s:hidden name="dirName" id="dirName" />

			<s:if test='cmd != "dir"'>
				<fieldset class="results-fieldset">
					<legend class="results-legend">Available Records</legend>
					<s:if test="fileListSize != 0">
						<table class="modern-grid-table">
							<thead>
								<tr>
									<th width="50" class="text-center">Select</th>
									<th class="text-left">File Name</th>
								</tr>
							</thead>
							<tbody>
								<%-- Iterates using standard Struts 2 value-stack lookup --%>
								<s:iterator value="fileListDisplay" status="rowStatus">
									<tr
										class="<s:if test='#rowStatus.even'>evenRow</s:if><s:else>oddRow</s:else>">
										<td class="text-center"><input type="radio" name="file"
											value="<s:property value='key'/>"
											id="files<s:property value='#rowStatus.index'/>" /></td>
										<td class="text-left"><s:property value="value" /></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>

						<input type="hidden" id="hidVal" name="hidVal"
							value="<s:property value='fileListDisplay.size()'/>" />

						<button name="Delete" value="Delete File"
							class="btn-submit-orange">Delete File</button>
					</s:if>

					<div class="navigation-link-row">
						<s:url var="backToDirUrl" action="deleteFile">
							<s:param name="cmd">dir</s:param>
						</s:url>
						<b><s:a href="%{#backToDirUrl}">Back to Dir</s:a></b>
					</div>
				</fieldset>
			</s:if>

			<%-- Conditionally render Directory Selection List Panel if cmd is not "file" --%>
			<s:if test='cmd != "file"'>
				<s:if test='cmd == "" || cmd == "dir"'>
					<fieldset class="results-fieldset" style="margin-top: 25px;">
						<legend class="results-legend">Directory Selection</legend>

						<s:iterator value="dirList" var="dirItem">
							<div class="directory-item">
								<s:url var="exploreDirUrl" action="deleteFile">
									<s:param name="cmd">file</s:param>
									<s:param name="dirName" value="#dirItem" />
								</s:url>
								<s:a href="%{#exploreDirUrl}">
									<img src="images/folder.gif" alt="Folder Icon">
									<s:property value="#dirItem" />
								</s:a>
							</div>
						</s:iterator>
					</fieldset>
				</s:if>
			</s:if>
		</s:form>
	</div>
	<%@ include file="/include/footer.jsf"%>
</body>
</html>