<%! String pageTitle = "Currency Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Supervisor/Analyst%>
<% // Modified by Udaya B Aravapalli on 01/12/2006 to restrict access based on roles.-- Start %>
<abbott:securePage	userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.do" />
<% // Modified by Udaya B Aravapalli on 01/12/2006 to restrict access based on roles.-- Start %>

<jsp:useBean id="currencyCodeForm" scope="session" class="abbott.ai.tcgm.action.form.CurrencyCodeForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
 
	<nested:form method="post" name="currencyCodeForm" type="abbott.ai.tcgm.action.form.CurrencyCodeForm" action="/currencyCodeMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="searchObject.curCode" />
		<table cellspacing="0" width="500">
			<tr>
				<td class="tableEntry">Currency Code</td>
				<td class="tableEntry">Currency Name</td>
			</tr>
			<tr>
				<td class="mntLeft">
					<nested:nest property="currencyCodeToEdit">
						<nested:hidden property="newCurrencyCode" />
						<nested:text property="curCode" maxlength="5" styleClass="mntWidth5" disabled="<%=!currencyCodeForm.getCurrencyCodeToEdit().getNewCurrencyCode()%>"/>
					</nested:nest>
				</td>
				<td>
					<nested:nest property="currencyCodeToEdit">
						<nested:text property="curName" maxlength="50" styleClass="mntWidth50" />
					</nested:nest>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="right">
					<a href="javascript:chgActCmdSubmit(document.currencyCodeForm,'save','saveCurrencyCode.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.currencyCodeForm,'cancel','currencyCodeMaint.do');" >
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
		</table>

		<hr/>

		<table width="500" cellspacing="0">
			<tr>
				<td colspan="3" class="right">
					<a href="javascript:deleteCurrencyCode(document.currencyCodeForm,'deleteselected','deleteCurrencyCode.do');">
						<img src="images/btnDeleteSelected.png" alt="Delete Selected" />
					</a>
				</td>
			</tr>
			<tr class="fltrTblHdngLeft">
				<td>Currency Code</td>
				<td>Currency Name</td>
				<td class="center">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('currencyList','selected','<%=currencyCodeForm.getCurrencyListSize()%>');" />
				</td>
			</tr>
	
			<nested:notEqual property="currencyListSize" value="0">
				<% int rowNumber=0; %>
				<c:forEach var="currencyCodeBean" items="${currencyCodeForm.currencylist}"  varStatus="currencyCodeStatus">	               			
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
						<td class="mntLeft">
							<a href="javascript:chgCurCodeAndSubmit(document.currencyCodeForm,'<c:out value="${currencyCodeBean.curCode}" />','editCurrencyCode.do','edit','currencyCodeToEdit.curCode')">
								<c:out value="${currencyCodeBean.curCode}" />
							</a>							
						</td>		
						<td class="mntLeft">
							<c:out value="${currencyCodeBean.curName}" />
						</td>
						<td class="mntCenter">
							<input type="checkbox" name="currencyList[<c:out value="${currencyCodeStatus.index}"/>].selected" value="on">
						</td>
					</abbott:row>
			  		<% rowNumber++; %>			
				</c:forEach>					
			</nested:notEqual>
		</table>
		<nested:equal property="currencyListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>
	
<script language=javascript>
	function deleteCurrencyCode(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like to delete selected currency code(s). ") ) 
			{
				chgActCmdSubmit(form, cmd, action);
			}
	}	
</script>
	
<%@ include file="/include/footer.jsf" %>