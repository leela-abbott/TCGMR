<%! String pageTitle="Unit Management";%>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<s:form name="mngUnitsForm" action="" namespace="/" theme="simple">
		<table height="183" class="tableCommand">
			<tr>
				<td height="21" colspan="3"></td>
				<td height="21" colspan="2"></td>
			</tr>
			<tr>
				<td height="21" colspan="3"></td>
				<td height="21" colspan="2"></td>
			</tr>
			<tr>
				<td height="21" colspan="3" class="tableHeading">Current Unit Sets</td>
				<td height="21" colspan="2" class="tableHeading">Fetch New Unit Sets</td>
			</tr>
			<tr valign="top">
				<td width="20">&nbsp;</td>
				<td width="110" rowspan="4">
					<div id="divToHide">
						<s:select name="selDataset" 
								  size="10"
								  list="datasets" 
								  listKey="datasetTableId" 
								  listValue="datasetNameLogStamp"
								  theme="simple" />
					</div>
				</td>
				<td width="104" rowspan="4">
					<a href="javascript:deleteDataset(document.forms[0].selDataset, 'deleteUnitSet.action')">
						<img border="0" height="20" src="images/btnDelete.png" width="80"></a>
					<br><br>&nbsp;
					<a href="javascript:saveDataset(document.forms[0].selDataset, 'saveDataSet.action')">
						<img border="0" height="20" src="images/btnSave.png" width="80"></a>
					<br>&nbsp;
					<a href="javascript:printDataset(document.forms[0].selDataset, 'printDataSet.action')">
						<img border="0" height="20" src="images/btnPrint.png" width="80"></a>
				</td>
				<td width="20">&nbsp;</td>
				<td width="20">
					<table>
						<tr>
							<td width="65" height="0" class="commandOptionLabel" valign="bottom">System:</td>
							<td width="140" height="60" valign="bottom">
								<s:select name="selFetchSystem" 
										  cssClass="commandOption"
										  list="#{'PLN':'Plan','UPD':'Plan Upd','INV1':'April Inv','INV2':'Sept Inv','INV3':'Dec Inv'}"
										  theme="simple" />
							</td>
						</tr>
						<tr>
							<td class="commandOptionLabel">&nbsp;</td>
							<td>
								<s:textfield name="txtFetchVal" 
											 size="3" 
											 maxlength="3" 
											 cssClass="commandOption"
											 onblur="javascript:checkLength()"
											 theme="simple" />
							</td>
						</tr>
						<tr>
							<td height="21" class="commandOptionLabel">Year:</td>
							<td height="21">
								<s:textfield name="txtFetchYear" 
											 size="2" 
											 maxlength="2" 
											 cssClass="commandOption"
											 theme="simple" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<a href='javascript:fetchDupCheck()'>
									<img border="0" height="20" src="images/btnFetch.png" width="80"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
	<script language="javascript">
		function fetchDupCheck() {
			var frm = document.forms[0];
			var unitName;
			var frthVal;
			if (frm.selFetchSystem.value.length == 3) {
				frthVal = frm.selFetchSystem.value.substring(0, 1);
			} else {
				frthVal = '';
			}
			unitName = frm.selFetchSystem.value + frthVal + frm.txtFetchYear.value;
			if (frm.txtFetchVal.value != '') {
				if (frm.selFetchSystem.value.length == 3) {
					frthVal = frm.selFetchSystem.value.substring(0, 1);
				} else {
					frthVal = frm.selFetchSystem.value.substring(3, 4);
				}
				unitName = frm.txtFetchVal.value + frthVal + frm.txtFetchYear.value;
			}
			var ok = true;
			if (optionExists(frm.selDataset, unitName)) {
				alert("There is already a unit set named " + unitName + ". You will need to delete the existing set.");
			} else {
				changeActionAndSubmit(frm, 'fetchUnits.do');
			}
		}
		function checkLength() {
			var frm = document.forms[0];
			if (frm.txtFetchVal.value != '') {
				if (frm.txtFetchVal.value.length != 3) {
					alert('Unit Name cannot be less than 3 Character');
					frm.txtFetchVal.focus();
					return false;
				} else {
					frm.txtFetchVal.value = frm.txtFetchVal.value.toUpperCase();
				}
			}
		}
		function printDataset(se, action) {
			if (se.value == 0) {
				alert("You must select a dataset to Print.");
				return;
			}
			var datasetName = getSelectedOptionText(se);
			if (confirm("Are you sure that you would like to Print " + datasetName + "?\n")) {
				changeActionAndSubmit(se.form, action);
			}
		}
	</script>
<%@ include file="/include/footer.jsf" %>
</body>