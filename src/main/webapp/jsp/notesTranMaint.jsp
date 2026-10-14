<%! String pageTitle = "Notes Maintenance"; %>
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
	
	<s:form method="post" name="notesTranForm" action="notesTranMaint.action" theme="simple">
		<s:hidden name="cmd" />
		<!-- FILTER SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Notes<br></td>
				<td rowspan="2">Pub<br>Flag</td>
				<td rowspan="2">User<br>Id</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<s:hidden name="searchObject.notes.modelId" />
			<s:hidden name="searchObject.notes.datasetTableId" />
			<% String submitFilter = "submitFilter(document.notesTranForm,'filter', event);"; %>			
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="searchObject.actionCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.notes.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.notes.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.notes.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.notes.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.notes.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.notes.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textarea name="searchObject.notes.note" rows="2" cols="50"
						onchange="makeFilterDirty('pagingDiv','red','bold');" 
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 50, event);" 
						theme="simple" />							
				</td>
				<td rowspan="2">
					<s:textfield name="searchObject.publishFlag" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');" 
						onkeydown="%{submitFilter}" 
						theme="simple" />
				</td>
				<td colspan="2" rowspan="2">
					<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" 
									   requiredAccessLevel="<%=abbott.ai.tcgm.entities.Role.Analyst.getAccessLevel()%>" 
									   comparisonType="<">
						<%=TCGMUser.getUserid()%>
					</abbott:securePage>
					<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" 
									   requiredAccessLevel="<%=abbott.ai.tcgm.entities.Role.Analyst.getAccessLevel()%>" 
									   comparisonType=">=">
						<s:select name="searchObject.userSelected" 
								  cssClass="commandOption" 
								  onchange="makeFilterDirty('pagingDiv','red','bold');"
								  list="#session.TCGMUser.userlist" 
								  headerKey="ALL" 
								  headerValue="ALL" 
								  theme="simple" />
					</abbott:securePage>
				</td>
			</tr>
			<tr class="evenRowCenter">
				<td colspan="15" class="right">
					<a href="javascript:changeCmdAndSubmit(document.notesTranForm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.notesTranForm,'advancedfilter');">
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>					 
					<a href="javascript:changeCmdAndSubmit(document.notesTranForm,'clearfilter');">
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
				String submitAdd = "submitAdd(document.notesTranForm,'add','notesTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) {
					submitAdd = "submitAdd(document.notesTranForm,'add','notesTranSave.do', event);";
				} else {
					submitAdd = "";
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
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />								
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 6, event);" 
						onblur="checkPadLeft(this,'0',6);" 
						theme="simple" />									
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);" 
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />									
				</td>
				<td>
					<s:textfield name="addNew.notes.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);" 
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />									
				</td>	
				<td>
					<s:textfield name="addNew.notes.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />									
				</td>
				<td>
					<s:textarea name="addNew.notes.note" rows="2" cols="50"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 50, event);" 
						theme="simple" />								 
				</td>
			</tr>
			<tr>
				<% if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) { %>
					<td colspan="16" class="right">
						<a href="javascript:chgActCmdSubmit(document.notesTranForm,'add','notesTranSave.do');">
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="javascript:chgActCmdSubmit(document.notesTranForm,'massupdate','notesTranSave.do');">
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="javascript:chgActCmdSubmit(document.notesTranForm,'clearaddnew','notesTranMaint.do');">
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				<% } %>	
			</tr>
		</table>
		<hr />
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/notesTranPaging.jsf" %>
		</div>
		<!-- DATA GRID SECTION -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_ACD%>');">
						Act<br>Code<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_ACD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_INV_CD%>');">
						Inv<br>Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_NOTE%>');">
						Note<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_NOTE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');">
						Pub<br>Flag<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_PUBLISH_FLAG">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('notesTranListItem','notes.selected','<s:property value="notesTranListSize"/>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<!-- DATA ROWS -->
			<s:hidden name="notesTranListSize" />
			<s:if test="notesTranListSize != 0">
				<s:iterator value="notesTranList" var="notesTranBean" status="notesTranStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("notesTranStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
					%>
					<s:set var="onChangeCall" value="%{'makeEditDirty(\"notesTranListItem[' + #notesTranStatus.index + '].notes.selected\")'}"/>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
						<td>&nbsp;</td>
						<td class="mntCenter">
							<s:property value="pagingFilter.startRecord + #notesTranStatus.index"/>
							<s:if test="#notesTranBean.notes.msg != null && #notesTranBean.notes.msg != ''">			   		
								<a class="error" href="#" 
								   id="anchor<%=rowNumber%>" 
								   name="anchor<%=rowNumber%>"				
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#notesTranBean.notes.msg" escapeHtml="true"/>');"
								   onmouseout='hideMsgPopup();'>
									<img src="images/exclamation.png" />	
								</a>
							</s:if>&nbsp;&nbsp;
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].actionCode" 
										 maxlength="1" cssClass="mntWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />				
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].notes.rptAff" 
										 maxlength="4" cssClass="mntWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].notes.rptProduct.invCode" 
										 maxlength="1" cssClass="mntWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].notes.rptProduct.list" 
										 maxlength="6" cssClass="mntWidth6"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 6, event);"
										 onblur="checkPadLeft(this,'0',6);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].notes.rptProduct.label" 
										 maxlength="3" cssClass="mntWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].notes.rptProduct.size" 
										 maxlength="3" cssClass="mntWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].notes.rptProduct.pack" 
										 maxlength="4" cssClass="mntWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>			
						<td class="mntCenter">
							<s:textarea name="notesTranListItem[%{#notesTranStatus.index}].notes.note" 
										rows="2" cols="50"
										onchange="%{#onChangeCall}"
										onkeyup="return autoTab(this, 50, event);" 
										theme="simple" />														   
						</td>
						<td class="mntCenter">
							<s:textfield name="notesTranListItem[%{#notesTranStatus.index}].publishFlag" 
										 maxlength="1" cssClass="mntWidth1" disabled="true" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:checkbox name="notesTranListItem[%{#notesTranStatus.index}].notes.selected" 
										theme="simple" />
						</td>
					</abbott:row>
				</s:iterator>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/notesTranBtmPaging.jsf" %>
				</div>
			</s:if>
		</table>
		<s:if test="notesTranListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</s:if>
		<hr />		
	</s:form>
	<!-- JAVASCRIPT SECTION -->
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		/**
		 * Check if model is selected and at least one row is selected for copy
		 */
		function checkCopy(selectedModel, form, cmd, action) {
			if (selectedModel == 'none') {	
				alert('You must select a model to copy');
			} else {	
				var rowSelected = false;
				if (cmd == 'copyall') {		
					rowSelected = true;
				} else {
					for (i = 0; i < document.notesTranForm.elements['notesTranListSize'].value; i++) {			
						var element = "notesTranListItem[" + i + "].notes.selected";				
						if (!rowSelected) {				
							for (j = 0; j < document.notesTranForm.elements.length; j++) {
								if (document.notesTranForm.elements[j].name == element) {
									if (document.notesTranForm.elements[j].checked == true) {							
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
					alert('You must select at least one row to copy');
				}	
			}
		}
		/**
		 * Prompt the user to select atleast one record to publish
		 */
		function checkPublish(form, cmd, action) {
			var rowSelected = false;
			if (cmd == 'publishall' || cmd == 'unpublishall') {		
				rowSelected = true;
			} else {
				for (i = 0; i < document.notesTranForm.elements['notesTranListSize'].value; i++) {			
					var element = "notesTranListItem[" + i + "].notes.selected";				
					if (!rowSelected) {				
						for (j = 0; j < document.notesTranForm.elements.length; j++) {
							if (document.notesTranForm.elements[j].name == element) {
								if (document.notesTranForm.elements[j].checked == true) {							
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
				alert('You must select at least one row to publish');
			}	
		}		
		/**
		 * Prompt the user to select atleast one record and confirm the deletion of records
		 */
		function confirmDelete(form, cmd, action) {
			var rowSelected = false;
			if (cmd == 'deleteall') {		
				rowSelected = true;
			} else {
				for (i = 0; i < document.notesTranForm.elements['notesTranListSize'].value; i++) {
					var element = "notesTranListItem[" + i + "].notes.selected";				
					if (!rowSelected) {
						for (j = 0; j < document.notesTranForm.elements.length; j++) {
							if (document.notesTranForm.elements[j].name == element) {
								if (document.notesTranForm.elements[j].checked == true) {
									rowSelected = true;
								}
							}
						}
					}			
				}	
			}
			if (rowSelected) {	
				if (confirm('Records will be permanently deleted?')) {
					chgActCmdSubmit(form, cmd, action);
				}		
			} else {	
				alert('You must select at least one row to delete');
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