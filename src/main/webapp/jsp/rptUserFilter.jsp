<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<%
String pageTitle = "Report User Search";
%>
<c:set var="pageTitle" value="Report User Search" scope="request" />
<script type="text/javascript" src="/include/common.js"></script>
<head>
<style type="text/css">
.no-results-container {
	width: 100%;
	min-width: 760px;
	margin: 20px auto;
	padding: 15px;
	background-color: #fff8f8;
	border: 1px solid #ffcccc;
	border-radius: 4px;
	color: #cc0000;
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: bold;
	text-align: center;
	box-sizing: border-box;
}

body {
	font-family: Arial, sans-serif;
	background-color: #ffffff;
	color: #333333;
	margin: 0;
	padding: 0;
}

.search-container {
	width: 100%;
	max-width: 95%;
	margin: 30px auto;
	padding: 0 15px;
	display: flex;
	flex-direction: column;
	align-items: center;
}

#userForm {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin: 0 auto;
	width: 100%;
}

.form-group-row {
	display: flex;
	align-items: center;
	margin-bottom: 8px;
	width: 390px;
}

.form-group-row label {
	width: 160px;
	font-weight: bold;
	font-size: 12px;
	color: #333333;
	text-align: left;
}

.input-wrapper {
	width: 230px;
	display: flex;
	align-items: center;
}

.form-control-input, .form-control-select {
	width: 230px;
	height: 24px;
	padding: 2px 6px;
	font-size: 12px;
	border: 1px solid #e0e0e0;
	border-radius: 3px;
	box-sizing: border-box;
	background-color: #ffffff;
	transition: border-color 0.2s ease;
}

.form-control-input:focus, .form-control-select:focus {
	border-color: #a0a0a0;
	outline: none;
}

.form-control-input:disabled, .form-control-select:disabled {
	background-color: #f5f5f5;
	color: #b0b0b0;
	cursor: not-allowed;
}

.btn-submit-orange {
	background: linear-gradient(to bottom, #ffcc44 0%, #ffbb22 100%);
	border: 1px solid #e5a515;
	border-radius: 4px;
	color: #222222;
	font-size: 12px;
	font-weight: bold;
	padding: 4px 16px;
	cursor: pointer;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
	display: block;
	margin: 15px auto 5px auto;
	text-align: center;
	min-width: 110px;
}

.btn-submit-orange:hover {
	background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
}

.btn-action-gray {
	background: #f0f0f0;
	border: 1px solid #cccccc;
	border-radius: 4px;
	color: #333333;
	font-size: 12px;
	font-weight: bold;
	padding: 5px 10px;
	cursor: pointer;
	margin-right: 5px;
}

.btn-action-gray:hover {
	background: #e5e5e5;
}

.btn-action-delete {
	background: #fff0f0;
	border: 1px solid #ffcccc;
	color: #cc0000;
	border-radius: 4px;
	font-size: 12px;
	font-weight: bold;
	padding: 5px 10px;
	text-decoration: none;
	display: inline-block;
	margin-right: 5px;
}

.btn-action-delete:hover {
	background: #ffe0e0;
}

.btn-action-export {
	background: #f0f8ff;
	border: 1px solid #bce0ff;
	color: #0066cc;
	border-radius: 4px;
	font-size: 12px;
	font-weight: bold;
	padding: 5px 10px;
	text-decoration: none;
	display: inline-block;
}

.btn-action-export:hover {
	background: #e0f0ff;
}

.btn-toggle-select {
	background: #ffffff;
	border: 1px solid #bbbbbb;
	border-radius: 3px;
	font-size: 11px;
	padding: 2px 6px;
	cursor: pointer;
}

.results-fieldset {
	border: 1px solid #dddddd;
	border-radius: 4px;
	padding: 15px;
	margin: 20px auto;
	display: table;
	box-sizing: border-box;
	min-width: 760px;
	max-width: 100%;
	text-align: left;
}

.results-legend {
	font-size: 13px;
	font-weight: bold;
	color: #0044aa;
	padding: 0 6px;
	margin-left: 0px;
	margin-right: auto;
	text-align: left;
	display: block;
	width: 100%;
	margin-top: 15px;
}

.action-bar-table {
	width: 100%;
	margin-top: 10px;
	margin-bottom: 20px;
	border-collapse: collapse;
}

.modern-grid-table {
	width: auto;
	min-width: 100%;
	border-collapse: collapse;
	margin-top: 5px;
	font-family: Consolas, "Courier New", Courier, monospace;
	font-size: 13px;
	table-layout: auto;
}

.modern-grid-table th {
	background-color: #99ccff;
	color: #333333;
	font-weight: bold;
	text-align: left;
	padding: 8px 14px;
	border: 1px solid #d0e0f5;
	white-space: nowrap;
}

.modern-grid-table td {
	padding: 8px 14px;
	border: 1px solid #e0e0e0;
	color: #111111;
	text-align: left;
	vertical-align: middle;
	white-space: nowrap;
}

.evenRow {
	background-color: #ffffff;
}

.oddRow {
	background-color: #ffffff;
}

.text-center {
	text-align: center;
}

.text-left {
	text-align: left;
}

.text-right {
	text-align: right;
}

.custom-checkbox-container {
	display: inline-block;
	position: relative;
	cursor: pointer;
	width: 18px;
	height: 18px;
}

.custom-checkbox-container input {
	opacity: 0;
	position: absolute;
	cursor: pointer;
	width: 100%;
	height: 100%;
	margin: 0;
	z-index: 2;
}

.checkmark-indicator {
	position: absolute;
	top: 0;
	left: 0;
	height: 18px;
	width: 18px;
	background-color: #ffcc44;
	border: 1px solid #d4a017;
	border-radius: 3px;
	z-index: 1;
}

.checkmark-indicator:after {
	content: "";
	position: absolute;
	display: block;
	left: 5px;
	top: 2px;
	width: 5px;
	height: 10px;
	border: solid #222222;
	border-width: 0 2.5px 2.5px 0;
	transform: rotate(45deg);
}
</style>

<script type="text/javascript" src="include/sorttable.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function enableList() {
		var roleVal = document.getElementById("role").value;
		if (roleVal == 'Area') {
			document.getElementById("areaCode").disabled = false;
			document.getElementById("affCode").value = -1;
			document.getElementById("affCode").disabled = true;
			document.getElementById("secCode").value = -1;
			document.getElementById("secCode").disabled = true;
			document.getElementById("division").value = -1;
			document.getElementById("division").disabled = false;
		} else if (roleVal == 'Affiliate') {
			document.getElementById("areaCode").value = -1;
			document.getElementById("areaCode").disabled = true;
			document.getElementById("affCode").disabled = false;
			document.getElementById("secCode").value = -1;
			document.getElementById("secCode").disabled = true;
			document.getElementById("division").value = -1;
			document.getElementById("division").disabled = false;
		} else if (roleVal == 'Sector') {
			document.getElementById("areaCode").value = -1;
			document.getElementById("areaCode").disabled = true;
			document.getElementById("affCode").value = -1;
			document.getElementById("affCode").disabled = true;
			document.getElementById("secCode").disabled = false;
			document.getElementById("division").value = -1;
			document.getElementById("division").disabled = false;
		} else if (roleVal == 'D') {
			document.getElementById("areaCode").value = -1;
			document.getElementById("affCode").value = -1;
			document.getElementById("secCode").value = -1;
			document.getElementById("division").value = -1;

			document.getElementById("areaCode").disabled = true;
			document.getElementById("affCode").disabled = true;
			document.getElementById("secCode").disabled = true;
			document.getElementById("division").disabled = false;
		} else {
			document.getElementById("areaCode").value = -1;
			document.getElementById("affCode").value = -1;
			document.getElementById("secCode").value = -1;
			document.getElementById("division").value = -1;

			document.getElementById("areaCode").disabled = true;
			document.getElementById("affCode").disabled = true;
			document.getElementById("secCode").disabled = true;
			document.getElementById("division").disabled = true;
		}
	}
</script>
<script type="text/javascript">
	function chgActCmdSubmitRptFilter(form, cmd, action) {
		form.cmd.value = cmd;
		form.action = action;
		//alert("with in chgActCmdSubmitRptFilter");
		form.submit();
	}

	function getUsers(form, cmd, action) {
		//alert("clicked get button");
		var roleVal = document.getElementById("role").value;
		if (roleVal == 'Area' || roleVal == 'Affiliate' || roleVal == 'Sector'
				|| roleVal == 'D') {
			if (document.getElementById("division").value == '-1') {
				alert('Please select Division');
				return false;
			}
		}

		if (roleVal == 'Area') {
			if (document.getElementById("areaCode").value == '-1') {
				alert('Please select Area');
				return false;
			}
			form.elements['selDesc'].value = form.elements['areaCode'].options[form.elements['areaCode'].selectedIndex].text;
		}
		if (roleVal == 'Affiliate') {
			if (document.getElementById("affCode").value == '-1') {
				alert('Please select Affiliate');
				return false;
			}
			form.elements['selDesc'].value = form.elements['affCode'].options[form.elements['affCode'].selectedIndex].text;
		}
		if (roleVal == 'Sector') {
			if (document.getElementById("secCode").value == '-1') {
				alert('Please select Sector');
				return false;
			}
			form.elements['selDesc'].value = form.elements['secCode'].options[form.elements['secCode'].selectedIndex].text;
		}
		chgActCmdSubmitRptFilter(form, cmd, action);
	}

	function allCap(id) {
		var val = document.getElementById(id).value;
		document.getElementById(id).value = val.toUpperCase();
	}
	function initCap(id) {
		var val = document.getElementById(id).value;
		document.getElementById(id).value = val.substring(0, 1).toUpperCase()
				+ val.substring(1, val.length);
	}

	function toggleSelectAll(listName, propName, totalSize) {
		return false;
	}

	function confirmDelete(form, cmd, action) {
		if (confirm("Are you sure that you would like to delete selected User Record(s)? ")) {
			chgActCmdSubmitRptFilter(form, cmd, action);
		}
	}
	function confirmAddReprt(form, cmd, action) {
		var cntVar = document.getElementById("hidVal");
		var incr = 0;
		if (null != cntVar) {
			var cnt = cntVar.value;
			for (i = 0; i < cnt; i++) {
				if (document.getElementById("userlist[" + i + "].selected").checked) {
					incr++;
				}
			}
			if (incr > 0) {
				if (confirm("Are you sure that you would like to Add selected User Record(s) to Cognos? ")) {
					chgActCmdSubmitRptFilter(form, cmd, action);
				}
			} else {
				alert('Please Select atleast a record to perform the action.');
			}
		} else {
			alert('Atleast a record should be there to perform the operation.');
		}
	}

	function confirmExport(form, cmd, action) {
		var cntVar = document.getElementById("hidVal");
		var incr = 0;
		if (null != cntVar) {
			var cnt = cntVar.value;
			for (i = 0; i < cnt; i++) {
				if (document.getElementById("userlist[" + i + "].selected").checked) {
					incr++;
				}
			}
			if (incr > 0) {
				if (confirm("Are you sure that you would like to Export selected User Record(s)? ")) {
					chgActCmdSubmitRptFilter(form, cmd, action);
				}
			} else {
				alert('Please Select atleast a record to perform the action.');
			}
		} else {
			alert('Atleast a record should be there to perform the operation.');
		}
	}

	function removeAllOptions(selectbox) {
		for (var i = selectbox.options.length - 1; i >= 0; i--) {
			selectbox.remove(i);
		}
	}

	function removeList(selList) {
		var affList = document.getElementById(selList);
		for (var i = affList.options.length - 1; i >= 0; i--) {
			if (affList.options[i].selected) {
				affList.remove(i);
			}
		}
	}

	function addOptions(selectbox, text, value) {
		var optn = document.createElement("OPTION");
		optn.text = trim(text);
		optn.value = trim(value);
		selectbox.options.add(optn);
	}

	function trim(stringToTrim) {
		return stringToTrim.replace(/^\s+|\s+$/g, "");
	}

	function callValues() {
		var divVal = document.getElementById('division').value;
		var roleVal = document.getElementById('role').value;
		if (divVal != '-1' && roleVal == 'Area') {
			retrieveURL(
					'./tcgmAjax.action?cascadingCmd=BurstArea&cascadingVal='
							+ divVal, 'userForm', 'areaCode');
		} else if (divVal != '-1' && roleVal == 'Affiliate') {
			retrieveURL('./tcgmAjax.action?cascadingCmd=BurstAff&cascadingVal='
					+ divVal, 'userForm', 'affCode');
		} else if (divVal != '-1' && roleVal == 'Sector') {
			retrieveURL(
					'./tcgmAjax.action?cascadingCmd=BurstSector&cascadingVal='
							+ divVal, 'userForm', 'secCode');
		} else {
			removeAllOptions(document.getElementById("areaCode"));
			removeAllOptions(document.getElementById("affCode"));
			removeAllOptions(document.getElementById("secCode"));
		}
	}
</script>
</head>
<body onload="disableList();">
	<%@ include file="/include/header.jsf"%>
	<%@ include file="/include/masthead.jsf"%>

	<c:set var="userForm" value="${sessionScope.userForm}" scope="session" />
	<c:set var="TCGMUser" value="${sessionScope.TCGMUser}" scope="session" />
	<c:set var="RptUser" value="${sessionScope.RptUser}" scope="session" />

	<%@ include file="/include/errorDisplay.jsf"%>

	<div class="search-container">

		<s:form id="userForm" name="userForm" method="post"
			action="rptUserMaint">
			<s:hidden name="cmd" property="cmd" />
			<s:hidden name="selDesc" property="selDesc" />

			<div class="form-group-row">
				<label for="userid">User ID</label>
				<div class="input-wrapper">
					<s:textfield name="rptUser.userid" id="userid"
						cssClass="form-control-input" theme="simple"
						onblur="allCap('userid');"
						onkeydown="if(event.keyCode == 13){allCap('userid'); document.getElementById('GetButton').click();}" />
				</div>
			</div>
			<div class="form-group-row">
				<label for="lastName">Last Name</label>
				<div class="input-wrapper">
					<s:textfield name="rptUser.lastName" id="lastName"
						cssClass="form-control-input" theme="simple"
						onblur="initCap('lastName');"
						onkeydown="if(event.keyCode == 13){initCap('lastName'); document.getElementById('GetButton').click();}" />
				</div>
			</div>
			<div class="form-group-row">
				<label for="firstName">First Name</label>
				<div class="input-wrapper">
					<s:textfield name="rptUser.firstName" id="firstName"
						cssClass="form-control-input" theme="simple"
						onblur="initCap('firstName');"
						onkeydown="if(event.keyCode == 13){initCap('firstName'); document.getElementById('GetButton').click();}" />
				</div>
			</div>

			<div class="form-group-row">
				<label for="role">Role</label>
				<div class="input-wrapper">
					<s:select name="rptUser.role" id="role" onchange="enableList()"
						cssClass="form-control-select" theme="simple"
						list="#{'-1':'All', 'HQS':'HQ Supervisor', 'HQC':'HQ Consumer', 'DALL':'All Divisions', 'D':'Division', 'Area':'Area', 'Sector':'Sector', 'Affiliate':'Affiliate'}" />
				</div>
			</div>

			<div class="form-group-row">
				<label for="division">Division</label>
				<div class="input-wrapper">
					<select name="rptUser.division" id="division"
						onchange="callValues()" class="form-control-select">
						<option value="-1">Select One</option>
						<c:forEach
							items="${rptUser['div'] != null ? rptUser['div'] : sessionScope.RptUser['div']}"
							var="divItem">
							<option value="${divItem.value}">${divItem.key}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group-row">
				<label for="areaCode">Area</label>
				<div class="input-wrapper">
					<select name="rptUser.areaCode" id="areaCode"
						class="form-control-select">
						<option value="-1">ALL</option>
						<c:forEach
							items="${rptUser.areas != null ? rptUser.areas : sessionScope.RptUser.areas}"
							var="areaItem">
							<option value="${areaItem.value}">${areaItem.key}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group-row">
				<label for="secCode">Sector</label>
				<div class="input-wrapper">
					<select name="rptUser.secCode" id="secCode"
						class="form-control-select">
						<option value="-1">ALL</option>
						<c:forEach
							items="${rptUser.sectors != null ? rptUser.sectors : sessionScope.RptUser.sectors}"
							var="secItem">
							<option value="${secItem.value}">${secItem.key}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group-row">
				<label for="affCode">Affiliate</label>
				<div class="input-wrapper">
					<select name="rptUser.affCode" id="affCode"
						class="form-control-select">
						<option value="-1">ALL</option>
						<c:forEach
							items="${rptUser.affiliates != null ? rptUser.affiliates : sessionScope.RptUser.affiliates}"
							var="affItem">
							<option value="${affItem.value}">${affItem.key}</option>
						</c:forEach>
					</select>
				</div>
			</div>


			<input type="button" name="searchButton" id="GetButton"
				class="btn-submit-orange" value="Get"
				onClick="getUsers(document.userForm,'Get','rptUserMaint.action');">

			<c:if test="${sessionScope.userForm.userListSize ne 0}">
				<hr>
				<span class="results-legend">Search Results</span>

				<c:if
					test="${sessionScope.TCGMUser.role.name eq 'TCGM_ADMINISTRATOR' || sessionScope.TCGMUser.role.name eq 'TCGM_RPT ADMIN'}">
					<table class="action-bar-table">
						<tr>
							<td class="text-left"><input type="button"
								name="searchButton" class="btn-action-gray"
								value="ReCreate Users"
								onClick="confirmAddReprt(document.userForm,'recreate','rptUserMaint.action');">
								<input type="button" name="searchButton" class="btn-action-gray"
								value="ReCertify Users"
								onClick="confirmAddReprt(document.userForm,'recertify','rptUserMaint.action');">
							</td>
							<td class="text-right"><a
								href="javascript:confirmDelete(document.userForm,'remove','rptUserMaint.action');"
								class="btn-action-delete"> Delete Selected </a> <a
								href="javascript:confirmExport(document.userForm,'export','rptUserMaint.action');"
								class="btn-action-export"> Export Selected </a></td>
						</tr>
					</table>
				</c:if>

				<table class="modern-grid-table sortable">
					<thead>
						<tr>
							<c:if
								test="${sessionScope.TCGMUser.role.name eq 'TCGM_ADMINISTRATOR' || sessionScope.TCGMUser.role.name eq 'TCGM_RPT ADMIN'}">
								<th width="50" class="sorttable_nosort text-center"><span
									class="custom-checkbox-container"> <input
										type="checkbox" id="selectAllToggle"
										onClick="return toggleSelectAll('userList','selected','${sessionScope.userForm.userListSize}');">
										<span class="checkmark-indicator"></span>
								</span></th>
							</c:if>
							<th scope="col">User ID</th>
							<th class="sorttable_nosort" scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Role</th>
							<th scope="col">Role Desc</th>
							<th scope="col">Create Date</th>
							<th scope="col">Recertify Date</th>
						</tr>
					</thead>
					<tbody>
						<s:hidden name="userListSize"
							value="%{#session.userForm.userListSize}" id="userListSize" />
						<c:forEach items="${sessionScope.userForm.userList}"
							var="rptUserItem" varStatus="userStatus">
							<tr class="${userStatus.index % 2 == 0 ? 'evenRow' : 'oddRow'}">

								<c:if
									test="${sessionScope.TCGMUser.role.name eq 'TCGM_ADMINISTRATOR' || sessionScope.TCGMUser.role.name eq 'TCGM_RPT ADMIN'}">
									<td class="text-center"><input type="checkbox"
										name="userList[${userStatus.index}].selected" value="true">

										<input type="hidden"
										name="userList[${userStatus.index}].userid"
										value="<c:out value='${rptUserItem.userid}'/>"> <input
										type="hidden" name="userList[${userStatus.index}].firstName"
										value="<c:out value='${rptUserItem.firstName}'/>"> <input
										type="hidden" name="userList[${userStatus.index}].lastName"
										value="<c:out value='${rptUserItem.lastName}'/>"> <input
										type="hidden" name="userList[${userStatus.index}].role"
										value="<c:out value='${rptUserItem.role}'/>"> <input
										type="hidden" name="userList[${userStatus.index}].roleDesc"
										value="<c:out value='${rptUserItem.roleDesc}'/>"> <input
										type="hidden" name="userList[${userStatus.index}].createDate"
										value="<c:out value='${rptUserItem.createDate}'/>"> <input
										type="hidden"
										name="userList[${userStatus.index}].recertifyDate"
										value="<c:out value='${rptUserItem.recertifyDate}'/>">
									</td>
								</c:if>

								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.userid}" /></td>
								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.firstName}" /></td>
								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.lastName}" /></td>
								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.role}" /></td>
								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.roleDesc}" /></td>
								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.createDate}" /></td>
								<td class="text-left" valign="middle"><c:out
										value="${rptUserItem.recertifyDate}" /></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="8" style="display: none;"><input type="hidden"
								id="hidVal" name="hidVal"
								value="${fn:length(sessionScope.userForm.userList)}" /></td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<c:if
				test="${param.cmd eq 'Get' and sessionScope.userForm.userListSize eq 0}">
				<div class="no-results-container">No results found.</div>
			</c:if>
			<input type="hidden" name="cmd2" value="creation">
		</s:form>
	</div>

	<%@ include file="/include/footer.jsf"%>
</body>
</html>