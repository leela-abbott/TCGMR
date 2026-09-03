<%! String pageTitle = "Aff Currency Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Administrators%>
<abbott:securePage
	userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.do" />

<jsp:useBean id="affCstCurForm" scope="session" class="abbott.ai.tcgm.action.form.AffCstCurForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %> 

	<nested:form method="post" name="affCstCurForm" type="abbott.ai.tcgm.action.form.AffCstCurForm" action="/affCstCurMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="searchObject.aff" />
		<table cellspacing="0" width="500">
			<tr>
				<td class="tableEntry">Affiliate</td>
				<td class="tableEntry">Desc</td>
				<td class="tableEntry">Currency Code</td>
			</tr>
			<tr>
				<td class="mntLeft">
					<nested:nest property="affCstCurToEdit">
						<nested:hidden property="newAffCstCur" />
						<nested:text property="aff" maxlength="4" styleClass="mntWidth4" disabled="<%=!affCstCurForm.getAffCstCurToEdit().getNewAffCstCur()%>"/>
					</nested:nest>
				</td>
				<td>
					<nested:nest property="affCstCurToEdit">
						<nested:text property="affDesc" maxlength="20" styleClass="mntWidth20" />
					</nested:nest>
				</td>
				<td>


				<nested:define id="currencyCodes" property="curCodes" />

				<html:select name="affCstCurForm" property="affCstCurToEdit.curCode" size="1">
					<html:options property="curCode" labelProperty="curCode" collection="currencyCodes" />
				</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="3" class="right">
					<a href="javascript:chgActCmdSubmit(document.affCstCurForm,'save','saveAffCstCur.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.affCstCurForm,'cancel','affCstCurMaint.do');" >
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
		</table>

		<hr/>

		<table width="500" cellspacing="0">
			<tr>
				<td colspan="4" class="right">
					<a href="javascript:confirmDelete(document.affCstCurForm,'deleteselected','deleteAffCstCur.do');">
						<img src="images/btnDeleteSelected.png" alt="Delete Selected" /></a>
				</td>
			</tr>
			<tr class="fltrTblHdngLeft">
				<td>Affiliate</td>
				<td>Description</td>
				<td>Currency Code</td>
				<td class="center">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('affCstCurList','selected','<%=affCstCurForm.getAffCstCurListSize()%>');" />
				</td>
			</tr>
			<nested:notEqual property="affCstCurListSize" value="0">
						
				<% int rowNumber=0; %>
				<c:forEach var="affCstCurListBean" items="${affCstCurForm.affCstCurList}"  varStatus="affCstCurListStatus">	               			
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
						<td class="mntLeft">
							<a href="javascript:chgAffAndSubmit(document.affCstCurForm,'<c:out value="${affCstCurListBean.aff}" />','editAffCstCur.do','edit','affCstCurToEdit.aff')">
								<c:out value="${affCstCurListBean.aff}" />
							</a>							
						</td>		
						<td class="mntLeft">
							<c:out value="${affCstCurListBean.affDesc}" />
						</td>
						<td class="mntLeft">
							<c:out value="${affCstCurListBean.curCode}" />
						</td>
						<td class="mntCenter">
							<input type="checkbox" name="affCstCurlist[<c:out value="${affCstCurListStatus.index}"/>].selected" value="on">
						</td>
					</abbott:row>
			  		<% rowNumber++; %>			
				</c:forEach>
			</nested:notEqual> 
		</table>
		<nested:equal property="affCstCurListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>
	<script language=javascript>
	function confirmDelete(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like to delete selected Aff Curr Record(s)? ") ) 
			{
				chgActCmdSubmit(form, cmd, action);
			}
	}	
</script>
<%@ include file="/include/footer.jsf" %>