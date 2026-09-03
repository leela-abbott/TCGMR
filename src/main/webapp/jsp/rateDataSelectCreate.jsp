<%! String pageTitle="Select or Create Rate Set";%>
<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadRate.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form name="mngRateSets" action="/openMngRateSets.do" type="abbott.ai.tcgm.action.form.MngRateSetsForm" scope="session" method="post">
		<nested:hidden property="cmd" />
		<table cellspacing="0" width="460" align="center">
			<tr>
				<td class="tableHeading" colspan="2">
					Rate Sets
				</td> 
			</tr>
			<tr>
				<td width="180">
                <div id="divToHide">
					<nested:define id="rateSets" property="rateSetList"/>
					<html:select name="mngRateSets" property="selectedDatasetTableId" size="6">
						<html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
					</html:select>
                </div>
				</td>
				<td valign="top" align="center">
					<a href="javascript:confirmDelete(document.mngRateSets,'delete','deleteRateSet.do');">
						<img src="images/btnDeleteWide.png" alt="Delete Rate Set" /></a>
					<br />
					<a href="javascript:chgActCmdSubmit(document.mngRateSets,'maintain','maintRateSet.do');">
						<img src="images/btnMaintain.png" alt="Maintain Rate Set" /></a>
					<br />
					<a href="javascript:chgActCmdSubmit(document.mngRateSets,'rename','renameRateSet.do');">
						<img src="images/btnRename.png" alt="Raname Rate Set" /></a>
				</td>
			</tr>
		</table>
		<br />

		<nested:hidden property="editRateSet.datasetTableId" />

		<table cellspacing="0" width="460" align="center">
			<tr>
				<td class="tableEntry">
					Rate Set Name
				</td>
				<td>
					<nested:text property="editRateSet.datasetName" maxlength="20" styleClass="rateWidth20"/>
				</td>
			</tr>
			<tr>
				<td class="tableEntry">
					Rate Set Description
				</td>
				<td>
					<nested:text property="editRateSet.datasetDesc" maxlength="128" styleClass="rateWidth40" />
				</td>
			</tr>
			<tr>
				<td class="tableEntry">
					Copy From
				</td>
				<td>
					<html:select name="mngRateSets" property="copyFromDatasetTableId" size="1" >
						<html:option value="<%=TCGMConstants.NONE_SELECTED%>"><%=TCGMConstants.NONE_SEL_HTML%></html:option>
						<html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="center">
					<a href="javascript:chgActCmdSubmit(document.mngRateSets,'save','saveRateSet.do');">
						<img src="images/btnSave.png" alt="Save Rate Set" /></a>
					<a href="javascript:chgActCmdSubmit(document.mngRateSets,'cancel','openMngRateSets.do');">
						<img src="images/btnCancel.png" alt="Raname Rate Set" /></a>
				</td>
			</tr>
		</table>
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		setFocus('selectedDatasetTableId');
		function confirmDelete(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like to delete selected Rate Set. ") ) 
			{
				chgActCmdSubmit(form, cmd, action);
			}
		}	
	</script>
<%@ include file="/include/footer.jsf" %>