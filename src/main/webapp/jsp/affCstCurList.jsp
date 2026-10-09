<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "Aff Currency Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<c:set var="pageTitle" value="Affiliate Currency Maintenance" scope="request" />
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />

<abbott:securePage
	userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.action" />

<style type="text/css">
	/* Base Structure Layout Rules */
	.tcgm-wrapper-table {
		width: 500px;
		margin: 25px auto 0 auto;
		border-collapse: collapse;
		font-family: Arial, Helvetica, sans-serif;
	}
	
	/* Form Input Table Spacing Styles */
	.tcgm-form-table {
		width: 500px;
		margin: 0 auto;
		border-collapse: collapse;
		font-family: Arial, Helvetica, sans-serif;
	}
	.tcgm-form-table td {
		padding: 6px 8px;
		vertical-align: middle;
	}
	
	/* Functional Content Headers Mapping Reference Screen */
	.tcgm-header-row {
		background: #b2d1f0;
	}
	.tcgm-header-row td, 
	.tcgm-header-row th {
		color: #003366;
		font-weight: bold;
		font-size: 12px;
		text-align: left; /* Aligned headers left */
		padding: 6px 8px;
		border: 1px solid #ffffff;
	}
	
	/* Clean Component Elements Inputs Styling */
	.tcgm-form-table input[type="text"], 
	.tcgm-form-table select {
		box-sizing: border-box;
		height: 22px;
		font-size: 12px;
		border: 1px solid #7f9db9;
		padding: 1px 3px;
		text-align: left; /* Aligned input text left */
	}
	.tcgm-form-table input[type="text"]:disabled {
		background-color: #e0e0e0;
		color: #7f7f7f;
	}

	/* Beautiful Custom Button Styling Mimicking Source UI Asset Elements */
	.tcgm-btn {
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
	}
	.tcgm-btn:hover {
		background: linear-gradient(to bottom, #fff099 0%, #ffdb4d 40%, #ffb833 100%);
		border-color: #8c6300;
	}
	.tcgm-btn:active {
		background: linear-gradient(to bottom, #ffa500 0%, #ffcc00 100%);
		box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.3);
	}

	/* All Button Containers Layout Control Blocks */
	.tcgm-btn-container {
		text-align: right; /* Aligned button containers to the right side */
		padding: 15px 0;
		width: 100%;
	}
	
	/* Horizontal Structural Divider Styles */
	.tcgm-divider {
		border: 0;
		border-top: 1px solid #cccccc;
		margin: 20px auto;
		width: 500px;
	}
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %> 

	<s:form id="affCstCurForm" name="affCstCurForm" method="post" action="affCstCurMaint">
		<s:hidden name="cmd" id="cmd" />
		<s:hidden name="searchObject.aff" id="searchObject_aff" />
		
		<!-- Upper Action Maintenance Input Table Definition -->
		<table class="tcgm-form-table" cellspacing="0">
			<tr class="tcgm-header-row">
				<td style="width: 25%;">Affiliate</td>
				<td style="width: 45%;">Desc</td>
				<td style="width: 30%;">Currency Code</td>
			</tr>
			<tr>
				<td style="text-align: left;">
					<s:hidden name="affCstCurToEdit.newAffCstCur" />
					<s:textfield name="affCstCurToEdit.aff" maxlength="4" cssClass="mntWidth4" style="width: 90%; text-align: left;" disabled="%{!affCstCurToEdit.newAffCstCur}" />
				</td>
				<td style="text-align: left;">
					<s:textfield name="affCstCurToEdit.affDesc" maxlength="20" cssClass="mntWidth20" style="width: 95%; text-align: left;" />
				</td>
				<td style="text-align: left;">
					<s:select name="affCstCurToEdit.curCode" list="curCodes" listKey="curCode" listValue="curCode" size="1" style="width: 100%; text-align: left;" />
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="tcgm-btn-container">
						<button type="button" class="tcgm-btn" onclick="chgActCmdSubmit(document.forms['affCstCurForm'],'save','saveAffCstCur.action');">Save</button>
						<button type="button" class="tcgm-btn" onclick="chgActCmdSubmit(document.forms['affCstCurForm'],'cancel','affCstCurMaint.action');">Cancel</button>
					</div>
				</td>
			</tr>
		</table>

		<hr class="tcgm-divider" />

		<!-- Lower Data Grid Display Table Definition -->
		<table class="tcgm-wrapper-table" cellspacing="0">
			<tr>
				<td colspan="4">
					<div class="tcgm-btn-container" style="padding-top: 0; padding-bottom: 15px;">
						<button type="button" class="tcgm-btn" onclick="confirmDelete(document.forms['affCstCurForm'],'deleteselected','deleteAffCstCur.action');">Delete Selected</button>
					</div>
				</td>
			</tr>
			<tr class="tcgm-header-row">
				<td style="width: 25%;">Affiliate</td>
				<td style="width: 45%;">Description</td>
				<td style="width: 20%;">Currency Code</td>
				<td style="width: 10%; text-align: center !important;">
					<button type="button" class="tcgm-btn" style="padding: 2px 6px; font-size: 10px; margin: 0; border-radius: 2px;" onclick="return toggleSelectAll('affCstCurList','selected','${affCstCurListSize}');">&#10003;</button>
				</td>
			</tr>
			
			<s:if test="affCstCurListSize > 0">
				<c:forEach var="affCstCurListBean" items="${affCstCurList}" varStatus="status">
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${status.index}" id="mntRow">	
						<td class="mntLeft" style="text-align: left; padding: 6px 8px;">
							<a href="javascript:chgAffAndSubmit(document.forms['affCstCurForm'],'<c:out value="${affCstCurListBean.aff}" />','editAffCstCur.action','edit','affCstCurToEdit.aff')">
								<c:out value="${affCstCurListBean.aff}" />
							</a>							
						</td>		
						<td class="mntLeft" style="text-align: left; padding: 6px 8px;">
							<c:out value="${affCstCurListBean.affDesc}" />
						</td>
						<td class="mntLeft" style="text-align: left; padding: 6px 8px;">
							<c:out value="${affCstCurListBean.curCode}" />
						</td>
						<td class="mntCenter" style="text-align: center; padding: 6px 8px;">
							<input type="checkbox" name="affCstCurList[${status.index}].selected" value="true" <c:if test="${affCstCurListBean.selected}">checked="checked"</c:if> />
						</td>
					</abbott:row>
				</c:forEach>
			</s:if>
		</table>
		
		<s:if test="affCstCurListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</s:if>
	</s:form>
	
<script type="text/javascript">
	function confirmDelete(form, cmd, action) {
		if (confirm("Are you sure that you would like to delete selected Aff Curr Record(s)? ")) {
			chgActCmdSubmit(form, cmd, action);
		}
	}	
	
	function chgActCmdSubmit(form,cmd,action)
	{
		form.cmd.value = cmd;
		form.action = action;
		form.submit();
	}
</script>
<%@ include file="/include/footer.jsf" %>
