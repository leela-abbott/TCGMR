<%! String pageTitle="Select or Create Rate Set";%>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:useBean id="TCGMConstants" scope="application" class="abbott.ai.tcgm.TCGMConstants" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadRate.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<s:form name="mngRateSets" action="openMngRateSets" namespace="/" method="post" theme="simple">
		<s:hidden name="cmd" />
		<table cellspacing="0" width="460" align="center">
			<tr>
				<td class="tableHeading" colspan="2">
					Rate Sets
				</td> 
			</tr>
			<tr>
				<td width="180">
					<div id="divToHide">
						<s:select name="selectedDatasetTableId" 
								  size="6"
								  list="rateSetList" 
								  listKey="datasetTableId" 
								  listValue="datasetName"
								  theme="simple" />
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
						<img src="images/btnRename.png" alt="Rename Rate Set" /></a>
				</td>
			</tr>
		</table>
		<br />
		<s:hidden name="editRateSet.datasetTableId" />
		<table cellspacing="0" width="460" align="center">
			<tr>
				<td class="tableEntry">
					Rate Set Name
				</td>
				<td>
					<s:textfield name="editRateSet.datasetName" 
								 maxlength="20" 
								 cssClass="rateWidth20"
								 theme="simple" />
				</td>
			</tr>
			<tr>
				<td class="tableEntry">
					Rate Set Description
				</td>
				<td>
					<s:textfield name="editRateSet.datasetDesc" 
								 maxlength="128" 
								 cssClass="rateWidth40"
								 theme="simple" />
				</td>
			</tr>
			<tr>
				<td class="tableEntry">
					Copy From
				</td>
				<td>
					<s:select name="copyFromDatasetTableId" 
							  size="1"
							  list="rateSetList" 
							  listKey="datasetTableId" 
							  listValue="datasetName"
							  
                              headerKey="@abbott.ai.tcgm.TCGMConstants@NONE_SELECTED" 
                              headerValue="< none >"
							  theme="simple" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="center">
					<a href="javascript:chgActCmdSubmit(document.mngRateSets,'save','saveRateSet.do');">
						<img src="images/btnSave.png" alt="Save Rate Set" /></a>
					<a href="javascript:chgActCmdSubmit(document.mngRateSets,'cancel','openMngRateSets.do');">
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
		</table>
	</s:form>
	<script language="JavaScript1.2" type="text/javascript">
		setFocus('selectedDatasetTableId');
		function confirmDelete(form, cmd, action) {
			if (confirm("Are you sure that you would like to delete selected Rate Set.")) {
				chgActCmdSubmit(form, cmd, action);
			}
		}	
	</script>
<%@ include file="/include/footer.jsf" %>
</body>