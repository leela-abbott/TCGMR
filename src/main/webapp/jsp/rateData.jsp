<a name="FilterView"></a>
<%! String pageTitle = "Rate Data"; %>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:useBean id="DBConst" scope="session" class="abbott.ai.tcgm.data.DBConst" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadRate.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<s:form method="post" name="rateDataForm" action="rateDataMaint.action"  theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<s:hidden name="rowToCopy" />
		<!-- FILTER SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td>Cur<br>Cd</td>
				<td class="bgWhiteRight">&nbsp;</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<s:hidden name="searchObject.modelId" />
			<s:hidden name="searchObject.datasetTableId" />
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="searchObject.curCode" 
								 maxlength="5" 
								 cssClass="fltrWidth5"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 theme="simple" />
				</td>
				<td class="right">
					<a href="javascript:changeCmdAndSubmit(document.rateDataForm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.rateDataForm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>
		<hr />
		<a name="AddMassUpdateView"></a>
		<!-- ADD NEW SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rate</td>							
				<td rowspan="2">Currency<br>Code</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>				
			</tr>
			<tr></tr>
			<s:hidden name="addNew.rateData.modelId" />
			<s:hidden name="addNew.rateData.datasetTableId" />
			<tr class="oddRowCenter">
				<td>
					<s:if test="addNew.rateData.msg != null && addNew.rateData.msg != ''">
						<a class="error" href="#" id="anchorAddNew" name="anchorAddNew"
							onclick="return false;"
							onmouseover="showMsgPopup('anchorAddNew', '<s:property value="addNew.rateData.msg" escapeHtml="true"/>');"
							onmouseout='hideMsgPopup();'>
							<img src="images/exclamation.png" />
						</a>
					</s:if>
					<s:textfield name="addNew.actionCode" 
								 maxlength="1" 
								 cssClass="fltrWidth1"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this, 1, event);" 
								 theme="simple" />
				</td>
				<td colspan="1">
					<s:textfield name="addNew.rateData.rate" 
								 maxlength="15" 
								 cssClass="fltrWidth8"
								 onchange="makeAddNewDirty();"
								 onblur="alertLength(this,9);" 
								 onkeyup="return autoTab(this, 15, event);" 
								 theme="simple" />
				</td>					
				<td colspan="1">
					<s:textfield name="addNew.rateData.curCode" 
								 maxlength="5" 
								 cssClass="fltrWidth5"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this, 5, event);" 
								 theme="simple" />
				</td>
				<td colspan="1">
					<s:textfield name="addNew.rateData.begPeriod" 
								 maxlength="2" 
								 cssClass="fltrWidth2"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this, 2, event);"
								 onblur="checkPadLeft(this,'0',2);" 
								 theme="simple" />
				</td>
				<td colspan="1">
					<s:textfield name="addNew.rateData.endPeriod" 
								 maxlength="2" 
								 cssClass="fltrWidth2"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this, 2, event);"
								 onblur="checkPadLeft(this,'0',2);" 
								 theme="simple" />
				</td>												
				<td colspan="16" class="right">
					<a href="javascript:chgActCmdSubmit(document.rateDataForm,'save','rateDataSave.do');">
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.rateDataForm,'massupdate','rateDataSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.rateDataForm,'clearaddnew','rateDataMaint.do');">
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			</tr>
		</table>
		<hr />
		<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/rateDataPaging.jsf" %>
		</div>
		<!-- DATA GRID HEADERS -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;</td>				
				<td colspan="1" rowspan="2">Rate<br></td>
				<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>								
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataForm,'<%=DBConst.COL_CUR_CD%>');">
						Currency<br>Code<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;</td>				
				<td colspan="1" rowspan="2">Beg<br>Period<br></td>
				<td width="">&nbsp;</td>				
				<td colspan="1" rowspan="2">End<br>Period<br></td>
				<td colspan="11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>												
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('rateDataListItem','selected','<s:property value="rateDataListSize"/>');" />
				</td>
			</tr>
		</table>
		<!-- DATA ROWS -->
		<table width="780" cellspacing="0">
			<s:hidden name="rateDataListSize" />
			<s:if test="rateDataListSize != 0">
				<s:iterator value="rateDataList" var="rateDataBean" status="rateDataStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("rateDataStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
						String href = "javascript:copyRow(document.rateDataForm,'" + rowNumber + "','rateDataMaint.do');";
					%>
					<s:set var="onChangeCall" value="%{'makeEditDirty(\"rateDataListItem[' + #rateDataStatus.index + '].selected\")'}"/>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter" colspan="1">
							<s:property value="pagingFilter.startRecord + #rateDataStatus.index"/>
							<a href="<%=href%>">
								<img src="images/btnUpArrow.png" alt="Load Row" />
							</a>
							<s:if test="#rateDataBean.msg != null && #rateDataBean.msg != ''">
								<a class="error" href="#" 
								   id="anchor<%=rowNumber%>" 
								   name="anchor<%=rowNumber%>"					
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#rateDataBean.msg" escapeHtml="true"/>');"
								   onmouseout='hideMsgPopup();'>
									<img src="images/exclamation.png" />
								</a>
							</s:if>							
							&nbsp;&nbsp;
							<s:textfield name="rateDataListItem[%{#rateDataStatus.index}].rate" 
										 maxlength="15" 
										 cssClass="mntWidth10"
										 onchange="%{#onChangeCall}"
										 onblur="alertLength(this,9);" 
										 onkeyup="return autoTab(this, 15, event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter" colspan="1">
							<s:textfield name="rateDataListItem[%{#rateDataStatus.index}].curCode" 
										 maxlength="5" 
										 cssClass="mntWidth5"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 5, event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter" colspan="1">
							&nbsp;
							<s:textfield name="rateDataListItem[%{#rateDataStatus.index}].begPeriod" 
										 maxlength="2" 
										 cssClass="mntWidth2"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 2, event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter" colspan="1">
							<s:textfield name="rateDataListItem[%{#rateDataStatus.index}].endPeriod" 
										 maxlength="2" 
										 cssClass="mntWidth2"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 2, event);" 
										 theme="simple" />
						</td>
						<td colspan="11" width="">&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="mntCenter" colspan="1">
							<s:checkbox name="rateDataListItem[%{#rateDataStatus.index}].selected" 
										theme="simple" />
						</td>
					</abbott:row>
					<!-- Rate Period Values 1-12 -->
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						<td class="mntCenter">
							Rates 1-6<br>Rates 7-12
						</td>
						<td colspan="17" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#rateDataBean.rates" var="ratePeriod1" 
												begin="0" end="5" status="ratePeriodStatus1">
										<td class="mntRight" width="16%">
											<s:property value="#ratePeriod1.period"/>
										</td>
									</s:iterator>
								</tr>
								<tr>
									<s:iterator value="#rateDataBean.rates" var="ratePeriod2"
												begin="6" end="11" status="ratePeriodStatus2">
										<td class="mntRight">
											<s:property value="#ratePeriod2.period"/>
										</td>
									</s:iterator>				
								</tr>
							</table>
						</td>
					</abbott:row>
				</s:iterator>
			</s:if>
		</table>
		<s:if test="rateDataListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</s:if>
		<hr />
	</s:form>
	<!-- JAVASCRIPT SECTION -->
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<s:property value="focusField"/>');
		/**
		 * Prompt the user to select atleast one record and confirm the deletion of records
		 */
		
		/**
		 * Prompt the user to select atleast one record and confirm the deletion of records
		 */
		function doDelete(form, cmd, action) {
			var rowSelected = false;
			if (cmd == 'deleteall') {		
				rowSelected = true;
			} else {
				for (i = 0; i < document.rateDataForm.elements['rateDataListSize'].value; i++) {
					var element = "rateDataListItem[" + i + "].selected";				
					if (!rowSelected) {
						for (j = 0; j < document.rateDataForm.elements.length; j++) {
							if (document.rateDataForm.elements[j].name == element) {
								if (document.rateDataForm.elements[j].checked == true) {
									rowSelected = true;
								}
							}
						}
					}			
				}	
			}	
			if (rowSelected) {				
				chgActCmdSubmit(form, cmd, action);							
			} else {	
				alert('You must select at least one row to delete');
			}	
		}
		/**
		 * Prompt the user to select atleast one record to Save Selected
		 */
		function checkSave(form, cmd, action) {
			var rowSelected = false;
			for (i = 0; i < document.rateDataForm.elements['rateDataListSize'].value; i++) {
				var element = "rateDataListItem[" + i + "].selected";				
				if (!rowSelected) {
					for (j = 0; j < document.rateDataForm.elements.length; j++) {
						if (document.rateDataForm.elements[j].name == element) {
							if (document.rateDataForm.elements[j].checked == true) {
								rowSelected = true;
							}
						}
					}
				}			
			}			
			if (rowSelected) {	
				chgActCmdSubmit(form, cmd, action);
			} else {	
				alert('You must select at least one row to SaveSelected');
			}	
		}
	</script>
<%@ include file="/include/footer.jsf" %>
</body>	