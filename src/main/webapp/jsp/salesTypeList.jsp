<%! String pageTitle = "Sales Type Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Administrators%>
<abbott:securePage
	userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.do" />

<jsp:useBean id="salesTypeForm" scope="session" class="abbott.ai.tcgm.action.form.SalesTypeForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %> 

	<nested:form method="post" name="salesTypeForm" type="abbott.ai.tcgm.action.form.SalesTypeForm" action="/salesTypeMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="searchObject.slsType" />
		<table cellspacing="0" width="500">
			<tr>
				<td class="tableEntry">Sales Type</td>
				<td class="tableEntry">Category</td>
				<td class="tableEntry">Division Name</td>
				<td class="tableEntry">Division Code</td>
			</tr>
			<tr>
				<td class="mntLeft">
					<nested:nest property="slsTypeToEdit">
						<nested:hidden property="newSlsType" />
						<nested:text property="slsType" maxlength="4" styleClass="mntWidth20" disabled="<%=!salesTypeForm.getSlsTypeToEdit().getNewSlsType()%>"/>
					</nested:nest>
				</td>
				<td>
					<nested:nest property="slsTypeToEdit">
						<nested:text property="category" maxlength="20" styleClass="mntWidth20" />
					</nested:nest>
				</td>
				<td>
					<nested:nest property="slsTypeToEdit">
						<nested:text property="divisionName" maxlength="20" styleClass="mntWidth20" />
					</nested:nest>
				</td>
				<td>
					<nested:nest property="slsTypeToEdit">
						<nested:text property="divisionCode" maxlength="20" styleClass="mntWidth20" styleId="divisionCode" onblur="javascript:changeCase('divisionCode');"/>
					</nested:nest>
				</td>

			</tr>
			<tr>
				<td colspan="3" class="right">
					<a href="javascript:chgActCmdSubmit(document.salesTypeForm,'save','saveSalesType.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.salesTypeForm,'cancel','salesTypeMaint.do');" >
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
		</table>

		<hr/>

		<table width="500" cellspacing="0">
			<tr>
				<td colspan="4" class="right">
					<a href="javascript:confirmDelete(document.salesTypeForm,'deleteselected','deleteSalesType.do');">
						<img src="images/btnDeleteSelected.png" alt="Delete Selected" /></a>
				</td>
			</tr>
			<tr class="fltrTblHdngLeft">
				<td>Sales Type</td>
				<td>Category</td>
				<td>Division Name</td>
				<td>Division Code</td>
				<td class="center">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('slsTypeList','selected','<%=salesTypeForm.getSlsTypeListSize()%>');" />
				</td>
			</tr>

			<nested:notEqual property="slsTypeListSize" value="0">
				<% int rowNumber=0; %>
				<c:forEach var="slsTypeListBean" items="${salesTypeForm.slsTypeList}" varStatus="slsTypeListStatus">	               			

					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
						<td class="mntLeft">
							<a href="javascript:chgSlsTypeAndSubmit(document.salesTypeForm,'<c:out value="${slsTypeListBean.slsType}" />','editSalesType.do','edit','slsTypeToEdit.slsType')">
								<c:out value="${slsTypeListBean.slsType}" />
							</a>							
						</td>		
						<td class="mntLeft">
							<c:out value="${slsTypeListBean.category}" />
						</td>
						<td class="mntLeft">
							<c:out value="${slsTypeListBean.divisionName}" />
						</td>
						<td class="mntLeft">
							<c:out value="${slsTypeListBean.divisionCode}" />
						</td>
						<td class="mntCenter">
							<input type="checkbox" name="slsTypelist[<c:out value="${slsTypeListStatus.index}"/>].selected" value="on">
						</td>
					</abbott:row>
			  		<% rowNumber++; %>			
				</c:forEach>
			</nested:notEqual> 
		</table>
		<nested:equal property="slsTypeListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>
	<script language=javascript>
	function chgSlsTypeAndSubmit(form,slstype,action,cmd,elementId)
		{
			document.getElementById('searchObject.slsType').value = slstype;
			document.getElementById('slsTypeToEdit.newSlsType').value = 'false';
			document.getElementById('slsTypeToEdit.slsType').value = '';
			document.getElementById('slsTypeToEdit.divisionName').value = '';
			document.getElementById('slsTypeToEdit.divisionCode').value = '';
			document.getElementById('slsTypeToEdit.category').value = '';
		
			chgActCmdSubmit(form,cmd,action);
		}
	function confirmDelete(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like to delete selected Sales Type Record(s)? ") ) 
			{
				chgActCmdSubmit(form, cmd, action);
			}
	}
function changeCase(desc)
{
document.getElementById(desc).value=document.getElementById(desc).value.toUpperCase();
}	
</script>
<%@ include file="/include/footer.jsf" %>