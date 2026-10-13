<%! String pageTitle = "Notes Data"; %>
<a name="FilterView"></a>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst" scope="session" class="abbott.ai.tcgm.data.DBConst" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %> 
	<%@ include file="/include/errorDisplay.jsf" %>
	<s:form method="post" name="notesForm" action="notesMaint" namespace="/" theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<s:hidden name="rowToCopy" />
		<!-- FILTER SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<s:hidden name="notesErrorListSize" />
				<s:if test="notesErrorListSize != 0">
					<td rowspan="2">Errors</td>
				</s:if>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Notes<br></td>
			</tr>
			<tr class="fltrTblHdng">
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<s:hidden name="searchObject.modelId" />
			<s:hidden name="searchObject.datasetTableId" />
			<% String submitFilter = "submitFilter(document.notesForm,'filter', event);"; %>			
			<tr class="oddRowCenter">
				<s:if test="notesErrorListSize != 0">
					<td>
						<input type="checkbox" name="errs" value="on" 
							   onClick="javascript:changeCmdAndSubmit(document.notesForm,'filter');">
					</td>
				</s:if>
				<td>
					<s:textfield name="searchObject.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textarea name="searchObject.note" rows="2" cols="50"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return checkLength(this, 255);" 
						theme="simple" />
				</td>
			</tr>
			<tr class="evenRowCenter">
				<td colspan="15" class="right">
					<a href="javascript:changeCmdAndSubmit(document.notesForm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.notesForm,'advancedfilter');">
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.notesForm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>
		<hr />
		<!-- ADD NEW SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Notes<br></td>
			</tr>
			<tr class="fltrTblHdng">
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="addNew.notes.modelId" />
			<s:hidden name="addNew.notes.datasetTableId" />
			<% 
				String submitSave = "submitSave(document.notesForm,'save','notesSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.notesForm,'save','notesSave.do', event);";
				} else {
					submitSave = "";
				}
			%>
			<tr class="oddRowCenter">
				<td>
					<s:if test="addNew.notes.msg != null && addNew.notes.msg != ''">
						<a class="error" href="#" id="anchorAddNew" name="anchorAddNew"
							onclick="return false;"
							onmouseover="showMsgPopup('anchorAddNew', '<s:property value="addNew.notes.msg" escapeHtml="true"/>');"
							onmouseout='hideMsgPopup();'>
							<img src="images/exclamation.png" />
						</a>
					</s:if>
					<s:textfield name="addNew.actionCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 6, event);" 
						onblur="checkPadLeft(this,'0',6);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);" 
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);" 
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textarea name="addNew.notes.note" rows="2" cols="50" 
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitSave}"
						onkeyup="return checkLength(this, 255);" 
						theme="simple" />
				</td>
			</tr>
			<tr>
				<% if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) { %>
					<td colspan="16" class="right">
						<a href="javascript:chgActCmdSubmit(document.notesForm,'save','notesSave.do');">
							<img src="images/btnSave.png" alt="Save" /></a>
						<a href="javascript:chgActCmdSubmit(document.notesForm,'massupdate','notesSave.do');">
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="javascript:chgActCmdSubmit(document.notesForm,'clearaddnew','notesMaint.do');">
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				<% } %>	
			</tr>
		</table>
		<hr />
		<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/notesPaging.jsf" %>
		</div>
		<!-- DATA GRID SECTION -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_INV_CD%>');">
						Inv<br>Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_NOTE%>');">
						Note<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_NOTE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('NotesListItem','selected','<s:property value="notesListSize"/>');" />
				</td>
			</tr>
			
			
			<!-- 1st Part end -->
			
			
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<!-- DATA ROWS -->
			<s:hidden name="notesListSize" />
			<s:if test="notesListSize != 0">
				<s:iterator value="notesList" var="notesBean" status="notesStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("notesStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
						String href = "javascript:copyRow(document.notesForm,'" + rowNumber + "','notesMaint.do');";
					%>
					<s:set var="onChangeCall" value="%{'makeEditDirty(\"NotesListItem[' + #notesStatus.index + '].selected\")'}"/>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter">
							<s:property value="pagingFilter.startRecord + #notesStatus.index"/>
							<a href="<%=href%>">							
								<img src="images/btnUpArrow.png" alt="Load Row" />
							</a>
						</td>
						<td class="mntCenter">					 
							<s:if test="#notesBean.msg != null && #notesBean.msg != ''">
								<a class="error" href="#" 
								   id="anchor<%=rowNumber%>" 
								   name="anchor<%=rowNumber%>"
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#notesBean.msg" escapeHtml="true"/>');"
								   onmouseout='hideMsgPopup();'>
									<img src="images/exclamation.png" />
								</a>
							</s:if>			  	
							<s:textfield name="NotesListItem[%{#notesStatus.index}].rptAff" 
										 maxlength="4" cssClass="mntWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="NotesListItem[%{#notesStatus.index}].rptProduct.invCode" 
										 maxlength="1" cssClass="mntWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="NotesListItem[%{#notesStatus.index}].rptProduct.list" 
										 maxlength="6" cssClass="mntWidth6"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 6, event);"
										 onblur="checkPadLeft(this,'0',6);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="NotesListItem[%{#notesStatus.index}].rptProduct.label" 
										 maxlength="3" cssClass="mntWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="NotesListItem[%{#notesStatus.index}].rptProduct.size" 
										 maxlength="3" cssClass="mntWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="NotesListItem[%{#notesStatus.index}].rptProduct.pack" 
										 maxlength="4" cssClass="mntWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textarea name="NotesListItem[%{#notesStatus.index}].note" 
										rows="2" cols="50"								   
										onchange="%{#onChangeCall}"							
										onkeyup="return autoTab(this, 50, event);" 
										theme="simple" />
						</td>
						<td class="mntCenter">
							<s:checkbox name="NotesListItem[%{#notesStatus.index}].selected" 
										theme="simple" />
						</td>
					</abbott:row>
				</s:iterator>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/notesPaging.jsf" %>
				</div>
			</s:if>
		</table>
		<s:if test="notesListSize == 0">
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
		function doDelete(form, cmd, action) {
			var rowSelected = false;
			if (cmd == 'deleteall') {		
				rowSelected = true;
			} else {
				for (i = 0; i < document.notesForm.elements['notesListSize'].value; i++) {
					var element = "NotesListItem[" + i + "].selected";				
					if (!rowSelected) {
						for (j = 0; j < document.notesForm.elements.length; j++) {
							if (document.notesForm.elements[j].name == element) {
								if (document.notesForm.elements[j].checked == true) {
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
			for (i = 0; i < document.notesForm.elements['notesListSize'].value; i++) {
				var element = "NotesListItem[" + i + "].selected";				
				if (!rowSelected) {
					for (j = 0; j < document.notesForm.elements.length; j++) {
						if (document.notesForm.elements[j].name == element) {
							if (document.notesForm.elements[j].checked == true) {
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
		function restrSpace(event) {
			if (event.keyCode == 32) {
				event.returnValue = false;
				return false;
			} else {
				return true;
			}
		}
	</script>
<%@ include file="/include/footer.jsf" %>
</body>
			
			