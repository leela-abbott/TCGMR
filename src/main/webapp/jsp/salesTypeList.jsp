<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!String pageTitle = "Sales Type Maintenance";%>
<%@ include file="/include/header.jsf"%>

<%-- Core Tag Libraries --%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="abbott" uri="/WEB-INF/taglib/abbott.tld"%>

<%-- Page is only accessible by Administrators --%>
<c:set var="pageTitle" value="Sales Type Maintenance" scope="request" />
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />

<abbott:securePage
	userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="=" forwardPage="/insufficientPrivelage.action" />

<style type="text/css">
    /* Form centering structure layout container wrapper */
    .form-center-container {
        width: 520px;
        margin: 25px auto 0 auto;
        font-family: Arial, Helvetica, sans-serif;
    }
    
    /* Harmonized table configurations for precise row layouts */
    .form-center-container table {
        width: 520px;
        border-collapse: collapse;
        margin: 0 auto;
    }

    .form-center-container td {
        padding: 6px 8px;
        vertical-align: middle;
    }

    /* Synchronized Blue Banner Header Line Alignment matching prior screens */
    .tableEntry,
    .fltrTblHdngLeft td {
        color: #003366 !important;
        font-weight: bold !important;
        font-size: 12px !important;
        text-align: left !important; /* Left-aligned headers */
        padding: 6px 8px !important;
        background-color: #b2d1f0 !important;
        border: 1px solid #ffffff;
    }

    /* Clean Component Elements Inputs Styling Alignment */
    .form-center-container input[type="text"] {
        box-sizing: border-box;
        height: 22px;
        font-size: 12px;
        border: 1px solid #7f9db9;
        padding: 1px 3px;
        text-align: left; /* Left-aligned text fields */
    }

    /* Beautiful Custom Button Styling Mimicking Source UI Asset Elements */
    .btn-tcgm-action {
        background: #ffcc00;
        background: linear-gradient(to bottom, #ffe066 0%, #ffcc00 40%, #ffa500 100%);
        border: 1px solid #b58000;
        border-radius: 4px;
        color: #000000;
        font-family: Arial, sans-serif;
        font-size: 11px;
        font-weight: bold;
        padding: 4px 14px;
        cursor: pointer;
        box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        margin-left: 8px;
        display: inline-block;
        text-decoration: none;
    }

    .btn-tcgm-action:hover {
        background: linear-gradient(to bottom, #fff099 0%, #ffdb4d 40%, #ffb833 100%);
        border-color: #8c6300;
    }

    .btn-tcgm-action:active {
        background: linear-gradient(to bottom, #ffa500 0%, #ffcc00 100%);
        box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.3);
    }

    /* Modernized Header Button with matching layout and modified look */
    .tcgm-check-btn {
        background: #ffcc00;
        background: linear-gradient(to bottom, #ffe066 0%, #ffcc00 40%, #ffa500 100%);
        border: 1px solid #b58000;
        border-radius: 2px;
        color: #000000;
        font-family: Arial, sans-serif;
        font-size: 10px;
        font-weight: bold;
        padding: 2px 6px;
        cursor: pointer;
        box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        display: inline-block;
    }

    .tcgm-check-btn:hover {
        background: linear-gradient(to bottom, #fff099 0%, #ffdb4d 40%, #ffb833 100%);
        border-color: #8c6300;
    }
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<div class="form-center-container">
		<%-- Struts 2 Form targeting unified layout mapping --%>
		<s:form method="post" id="salesTypeForm" name="salesTypeForm"
			action="salesTypeMaint" theme="simple">

			<%-- Form State Tracking Properties --%>
			<s:hidden name="cmd" id="cmd" />
			<s:hidden name="searchObject.slsType" id="searchObject.slsType" />

			<table cellspacing="0" width="520">
				<tr>
					<td class="tableEntry" width="20%">Sales Type</td>
					<td class="tableEntry" width="26%">Category</td>
					<td class="tableEntry" width="28%">Division Name</td>
					<td class="tableEntry" width="26%">Division Code</td>
				</tr>
				<tr>
					<td style="text-align: left;">
						<s:hidden name="slsTypeToEdit.newSlsType" id="slsTypeToEdit.newSlsType" /> 
						<s:textfield name="slsTypeToEdit.slsType" id="slsTypeToEdit.slsType"
							maxlength="4" cssClass="mntWidth20" style="width: 90%; text-align: left;"
							disabled="%{!slsTypeToEdit.newSlsType}" theme="simple" />
					</td>
					<td style="text-align: left;">
						<s:textfield name="slsTypeToEdit.category" id="slsTypeToEdit.category" 
							maxlength="20" cssClass="mntWidth20" style="width: 90%; text-align: left;" theme="simple" />
					</td>
					<td style="text-align: left;">
						<s:textfield name="slsTypeToEdit.divisionName" id="slsTypeToEdit.divisionName" 
							maxlength="20" cssClass="mntWidth20" style="width: 90%; text-align: left;" theme="simple" />
					</td>
					<td style="text-align: left;">
						<s:textfield name="slsTypeToEdit.divisionCode" id="slsTypeToEdit.divisionCode" 
							maxlength="20" cssClass="mntWidth20" style="width: 90%; text-align: left;"
							onblur="javascript:changeCase('slsTypeToEdit.divisionCode');" theme="simple" />
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 15px; padding-bottom: 15px;">
						<button type="button" class="btn-tcgm-action"
							onclick="javascript:chgActCmdSubmit(document.salesTypeForm,'save','saveSalesType.action');">
							Save</button>
						<button type="button" class="btn-tcgm-action"
							onclick="javascript:chgActCmdSubmit(document.salesTypeForm,'cancel','salesTypeMaint.action');">
							Cancel</button>
					</td>
				</tr>
			</table>

			<hr style="margin: 20px auto; border: 0; border-top: 1px solid #cccccc; width: 520px;" />

			<table width="520" cellspacing="0" style="table-layout: fixed; width: 520px;">
				<tr>
					<td colspan="5" style="text-align: right; padding-top: 0; padding-bottom: 15px;">
						<button type="button" class="btn-tcgm-action"
							onclick="javascript:confirmDelete(document.salesTypeForm,'deleteselected','deleteSalesType.action');">
							Delete Selected</button>
					</td>
				</tr>
				<%-- Synchronized Corporate Blue Ribbon Banner Header Line --%>
				<tr class="fltrTblHdngLeft" style="height: 26px;">
					<td width="20%">Sales Type</td>
					<td width="25%">Category</td>
					<td width="25%">Division Name</td>
					<td width="18%">Division Code</td>
					<td width="12%" style="text-align: center !important; vertical-align: middle;">
						<span class="tcgm-check-btn"
						onClick="return toggleSelectAll('slsTypeList','selected','<s:property value="slsTypeListSize"/>');"
						title="Toggle Select All">&#10003;</span>
					</td>
				</tr>

				<c:if test="${slsTypeListSize != 0}">
					<c:forEach var="slsTypeListBean" items="${slsTypeList}" varStatus="status">
						<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${status.index}" id="mntRow">
							<td width="20%" class="mntLeft" style="padding: 5px; vertical-align: middle; text-align: left;">
								<a href="javascript:chgSlsTypeAndSubmit(document.salesTypeForm,'<c:out value="${slsTypeListBean.slsType}" />','editSalesType.action','edit','slsTypeToEdit.slsType')">
									<c:out value="${slsTypeListBean.slsType}" />
								</a>
							</td>
							<td width="25%" class="mntLeft" style="padding: 5px; vertical-align: middle; text-align: left;">
								<c:out value="${slsTypeListBean.category}" />
							</td>
							<td width="25%" class="mntLeft" style="padding: 5px; vertical-align: middle; text-align: left;">
								<c:out value="${slsTypeListBean.divisionName}" />
							</td>
							<td width="18%" class="mntLeft" style="padding: 5px; vertical-align: middle; text-align: left;">
								<c:out value="${slsTypeListBean.divisionCode}" />
							</td>
							<td width="12%" class="mntCenter" style="padding: 5px; vertical-align: middle; text-align: center;">
								<input type="checkbox" name="slsTypeList[<c:out value="${status.index}"/>].selected" value="on">
							</td>
						</abbott:row>
					</c:forEach>
				</c:if>
			</table>

			<c:if test="${slsTypeListSize == 0}">
				<%@ include file="/include/recordsNotFound.jsf"%>
			</c:if>
		</s:form>
	</div>

	<script>
		function chgSlsTypeAndSubmit(form, slstype, action, cmd, elementId) {
			document.getElementById('searchObject.slsType').value = slstype;
			document.getElementById('slsTypeToEdit.newSlsType').value = 'false';
			document.getElementById('slsTypeToEdit.slsType').value = '';
			document.getElementById('slsTypeToEdit.divisionName').value = '';
			document.getElementById('slsTypeToEdit.divisionCode').value = '';
			document.getElementById('slsTypeToEdit.category').value = '';

			chgActCmdSubmit(form, cmd, action);
		}

		function confirmDelete(form, cmd, action) {
			if (confirm("Are you sure that you would like to delete selected Sales Type Record(s)? ")) {
				chgActCmdSubmit(form, cmd, action);
			}
		}

		function changeCase(desc) {
			var el = document.getElementById(desc);
			if (el && el.value) {
				el.value = el.value.toUpperCase();
			}
		}

		function chgActCmdSubmit(form, cmd, action) {
			form.cmd.value = cmd;
			form.action = action;
			form.submit();
		}
	</script>
	<%@ include file="/include/footer.jsf"%>
