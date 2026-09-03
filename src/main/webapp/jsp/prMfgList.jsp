<%! String pageTitle = "PR Mfg Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Supervisor/Analyst%>
<% // Modified by Udaya B Aravapalli on 01/12/2006 to restrict access based on roles.-- Start %>
<abbott:securePage
	userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.do" />
<% // Modified by Udaya B Aravapalli on 01/12/2006 to restrict access based on roles.-- End %>	

<jsp:useBean id="prMfgForm" scope="session" class="abbott.ai.tcgm.action.form.PRMfgForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>

	<nested:form method="post" name="prMfgForm" type="abbott.ai.tcgm.action.form.PRMfgForm" action="/prMfgMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<table cellspacing="0" width="500">
			<tr>
				<td class="tableEntry">Sup Affiliate</td>
			</tr>
			<tr>
				<td class="mntLeft">
					<nested:nest property="prMfgToEdit">
						<nested:text property="supAff" maxlength="4" styleClass="mntWidth4" />
					</nested:nest>
				</td>
			</tr>
			<tr>
				<td class="right">
					<a href="javascript:chgActCmdSubmit(document.prMfgForm,'save','savePRMfg.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.prMfgForm,'cancel','prMfgMaint.do');" >
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
		</table>

		<hr/>

		<table width="500" cellspacing="0">
			<tr>
				<td colspan="2" class="right">
					<a href="javascript:confirmDelete(document.prMfgForm,'deleteselected','deletePRMfg.do');">
						<img src="images/btnDeleteSelected.png" alt="Delete Selected" /></a>
				</td>
			</tr>
			<tr class="fltrTblHdngLeft">
				<td>Sup Affiliate</td>
				<td class="center">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('prMfgList','selected','<%=prMfgForm.getPrMfgListSize()%>');" />
				</td>
			</tr>


			<nested:notEqual property="prMfgListSize" value="0">



				
     <% int rowNumber=0; %>

	<c:forEach var="PRMfgBean" items="${prMfgForm.prmfglist}"  varStatus="prMfgStatus">	               			
		<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
			<td class="mntLeft">
				<c:out value="${PRMfgBean.supAff}" />
				
			 </td>


                       <td class="mntCenter">
							<input type="checkbox" name="prMfgList[<c:out value="${prMfgStatus.index}"/>].selected" value="on">
						</td>		
						
			
		</abbott:row>
    <% rowNumber++; %>

			
	</c:forEach>




			</nested:notEqual>
		</table>
		<nested:equal property="prMfgListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>


<script language=javascript>
	function confirmDelete(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like to delete selected PR Mfg Record(s). ") ) 
			{
				chgActCmdSubmit(form, cmd, action);
			}
	}	
</script>
<%@ include file="/include/footer.jsf" %>