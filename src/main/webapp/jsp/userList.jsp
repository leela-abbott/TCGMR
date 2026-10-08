<%! String pageTitle = "User Maintenance"; %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/include/header.jsf"%>
<c:set var="pageTitle" value="User Maintenance" scope="request" />

<head>
<style type="text/css">
.tcgm-btn {
	display: inline-block;
	padding: 5px 8px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	color: #123456 !important;
	text-decoration: none;
	background: linear-gradient(to bottom, #ffe880 0%, #ffcd3c 100%);
	border: 1px solid #cca11f;
	border-radius: 3px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
	cursor: pointer;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.4);
	transition: all 0.1s ease-in-out;
	line-height: 1.2;
}

.tcgm-btn:hover {
	background: linear-gradient(to bottom, #ffed96 0%, #ffd455 100%);
	border-color: #b88f14;
}

.tcgm-btn:active {
	background: #ffcd3c;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.15);
}

.tcgm-check-btn {
	background: linear-gradient(to bottom, #fff085 0%, #ffcc24 100%);
	border: 1px solid #caa016;
	border-radius: 2px;
	width: 18px;
	height: 14px;
	cursor: pointer;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	padding: 0;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}

.tcgm-check-btn::after {
	content: '';
	display: block;
	width: 3px;
	height: 6px;
	border: solid #113355;
	border-width: 0 2px 2px 0;
	transform: rotate(45deg);
	margin-bottom: 2px;
}

.compact-table td {
	padding: 6px 8px !important;
	font-size: 12px !important;
}

.compact-table input[type="checkbox"] {
	margin: 0;
	padding: 0;
	transform: scale(0.95);
	vertical-align: middle;
}
</style>
</head>
<script>
function chgActCmdSubmit(form,cmd,action)
{
	form.cmd.value = cmd;
	form.action = action;
	//alert("with in chgActCmdSubmit");
	form.submit();
}
</script>
<body
	style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; font-size: 12px;">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form method="post" name="userForm" id="userForm"
		action="userMaint.action" theme="simple">

		<s:hidden name="searchObject.userid" id="searchObject_userid" />
		<s:hidden name="cmd" id="cmd" />
		<s:hidden name="userListSize" id="userListSize" />

		<table class="compact-table"
			style="width: 920px; border-collapse: collapse; border-spacing: 0; margin-top: 5px; margin-left: auto; margin-right: auto;">
			<tr>
				<td colspan="8" style="text-align: right; padding-bottom: 5px;">
					<c:if
						test='${sessionScope.TCGMUser.role.name == "TCGM_ADMIN" || sessionScope.TCGMUser.role.accessLevel == 3}'>
						<button type="button" class="tcgm-btn"
							onclick="chgActCmdSubmit(document.userForm, 'deleteselected', 'deleteUser.action');">
							Delete Selected</button>
					</c:if>
				</td>
			</tr>
			<tr class="fltrTblHdng"
				style="background-color: #b4d8f4; font-weight: bold; color: Navy; text-align: left; font-size: 12px;">
				<td style="padding: 8px 8px;">User Id</td>
				<td style="padding: 8px 8px;">First Name</td>
				<td style="padding: 8px 8px;">Last Name</td>
				<td style="padding: 8px 8px;">Phone</td>
				<td style="padding: 8px 8px;">UPI</td>
				<td style="padding: 8px 8px;">&nbsp;Role</td>
				<td style="padding: 8px 8px;">&nbsp;Email</td>
				<td
					style="padding: 8px 8px; text-align: center; vertical-align: middle;">
					<button type="button" class="tcgm-check-btn"
						title="Toggle Select All"
						onClick="return toggleSelectAll('userlist', 'selected', '${action.userListSize}');"></button>
				</td>
			</tr>
			<c:if test="${not empty action.userlist}">

				<c:forEach items="${action.userlist}" var="userItem"
					varStatus="status">
					<tr id="mntRow"
						class="${status.index % 2 == 0 ? 'evenRow' : 'oddRow'}"
						style="font-size: 12px;">

						<td class="mntLeft" style="padding: 6px 8px;"><input
							type="hidden" name="userlist[${status.index}].userid"
							value="<c:out value='${userItem.userid}' />" /> <input
							type="hidden" name="userlist[${status.index}].userinfoid"
							value="<c:out value='${userItem.userinfoid}' />" /> <c:out
								value="${userItem.userid}" /></td>
						<td class="mntLeft" style="padding: 6px 8px;"><c:out
								value="${userItem.firstName}" /></td>
						<td class="mntLeft" style="padding: 6px 8px;"><c:out
								value="${userItem.lastName}" /></td>
						<td class="mntLeft" style="padding: 6px 8px;"><c:out
								value="${userItem.phone}" /></td>
						<td class="mntLeft" style="padding: 6px 8px;"><c:out
								value="${userItem.abtNotesId}" /></td>
						<td class="mntCenter" style="padding: 6px 8px;">&nbsp;&nbsp;<c:out
								value="${userItem.userRole}" /></td>
						<td class="mntLeft" style="padding: 6px 8px;">&nbsp;&nbsp;<c:out
								value="${userItem.email}" /></td>

						<td class="mntCenter"
							style="padding: 6px 8px; text-align: center; vertical-align: middle;">
							<input type="checkbox" name="userlist[${status.index}].selected"
							value="true" />
						</td>
					</tr>
				</c:forEach>

			</c:if>
		</table>

		<c:if test="${empty action.userlist}">
			<div
				style="text-align: center; margin: 15px auto; width: 920px; font-size: 12px;">
				<%@ include file="/include/recordsNotFound.jsf"%>
			</div>
		</c:if>

	</s:form>
	<%@ include file="/include/footer.jsf"%>
</body>
</html>
