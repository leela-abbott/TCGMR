<%! String pageTitle = "Knoll Conv Aff Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Administrators%>
<abbott:securePage
	userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
	requiredAccessLevel="<%=Role.Administrator.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.do" />

<jsp:useBean id="knollConvForm" scope="session" class="abbott.ai.tcgm.action.form.KnollConvForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>

	<nested:form method="post" name="knollConvForm" type="abbott.ai.tcgm.action.form.KnollConvForm" action="/knollConvMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<table cellspacing="0" width="500">
			<tr>
				<td class="tableEntry">Sup Affiliate</td>
				<td class="tableEntry">Conv Affiliate</td>
			</tr>
			<tr> 
				<td class="mntLeft">
					<nested:nest property="knollConvToEdit">
						<nested:text property="supAff" maxlength="4" styleClass="mntWidth4" />
					</nested:nest>
				</td>
				<td>
					<nested:nest property="knollConvToEdit">
						<nested:text property="convAff" maxlength="4" styleClass="mntWidth4" />
					</nested:nest>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="right">
					<a href="javascript:chgActCmdSubmit(document.knollConvForm,'save','saveKnollConv.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.knollConvForm,'cancel','knollConvMaint.do');" >
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
		</table>

		<hr/>

		<table width="500" cellspacing="0">
			<tr>
				<td colspan="3" class="right">
					<a href="javascript:confirmDelete(document.knollConvForm,'deleteselected','deleteKnollConv.do');">
						<img src="images/btnDeleteSelected.png" alt="Delete Selected" /></a>
				</td>
			</tr>
			<tr class="fltrTblHdngLeft">
				<td>Sup Affiliate</td>
				<td>Conv Affiliate</td>
				<td class="center">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('knollConvList','selected','<%=knollConvForm.getKnollConvListSize()%>');" />
				</td>
			</tr>
			<nested:notEqual property="knollConvListSize" value="0">
				<nested:iterate property="knollConvList" type="abbott.ai.tcgm.entities.KnollConv">
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow">
						<td class="mntLeft">
							<nested:write property="supAff" />
						</td>
						<td class="mntLeft">
							<nested:write property="convAff" />
						</td>
						<td class="mntCenter">
							<nested:checkbox property="selected" />
						</td>
					</abbott:row>
				</nested:iterate>
			</nested:notEqual>
		</table>
		<nested:equal property="knollConvListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>
<%@ include file="/include/footer.jsf" %>