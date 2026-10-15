<a name="FilterView"></a>
<%! String pageTitle = "ASR Usage Factors"; %>
<%@ include file="/include/header.jsf"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:useBean id="TCGMUser" scope="session"
	type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst" scope="session"
	class="abbott.ai.tcgm.data.DBConst" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/maintNav.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>
	<s:form method="post" name="affasrForm" action="affAsrMaint"
		enctype="multipart/form-data" theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<s:hidden name="rowToCopy" />
		<!-- Upload/Download Section -->
		<table width="780" cellspacing="0">
			<tr>
				<td colspan="11"><font
					style="font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: normal; color: #0A4876; text-indent: 4pt; vertical-align: bottom;">
						ASR Usage Upload </font> <br /> <font
					style="font-family: Lucida Console, fixedsys, Courier New, Courier, monospace; font-size: 9pt; font-weight: bold; color: #0A4876; text-indent: 0pt; text-align: left;">
						Actual(S)<input type="radio" name="cycle" value="S" /> Prelim(P)<input
						type="radio" name="cycle" value="P" /> Final(F)<input type="radio"
						name="cycle" value="F" /> Other(X)<input type="radio" name="cycle"
						value="X" />
				</font> <br /></td>
			</tr>
			<tr>
				<td colspan="11" align="right"><s:file name="theFile"
						theme="simple" /> <a href="javascript:setDownload();"> <img
						src="images/btnDownload.png" alt="Download" /></a> <a
					href="javascript:setUpload();"> <img src="images/btnUpload.png"
						alt="Upload" /></a></td>
			</tr>
		</table>
		<!-- Filter Section -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<s:hidden name="asrErrorListSize" />
				<s:if test="asrErrorListSize != 0">
					<td rowspan="2">Errors</td>
				</s:if>
				<td rowspan="2">Prod<br>Orig
				</td>
				<td rowspan="2">Rpt<br>Aff
				</td>
				<td rowspan="2">Inv<br>Cd
				</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Supp<br>Aff
				</td>
				<td rowspan="2">Inv<br>Cd
				</td>
				<td colspan="4">Sup Prod</td>
				<td rowspan="2">Usage<br>Factor
				</td>
				<td rowspan="2">Sup<br>Key
				</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<s:hidden name="searchObject.modelId" />
			<s:hidden name="searchObject.datasetTableId" />
			<s:hidden name="searchObject.affiliate" />
			<s:hidden name="searchObject.cycleId" />
			<% String submitFilter = "submitFilter(document.affasrForm,'filter', event);"; %>
			<tr class="oddRowCenter">
				<s:if test="asrErrorListSize != 0">
					<td><input type="checkbox" name="errs" value="on"
						onClick="javascript:changeCmdAndSubmit(document.affasrForm,'filter');">
					</td>
				</s:if>
				<td><s:textfield name="searchObject.productOrigin"
						maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.rptAff" maxlength="4"
						cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" theme="simple" /></td>
				<td><s:textfield name="searchObject.rptProduct.invCode"
						maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.rptProduct.list"
						maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.rptProduct.label"
						maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.rptProduct.size"
						maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.rptProduct.pack"
						maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supAff" maxlength="4"
						cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supProduct.invCode"
						maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supProduct.list"
						maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supProduct.label"
						maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supProduct.size"
						maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supProduct.pack"
						maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.usage" maxlength="16"
						cssClass="fltrWidth15"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onblur="alertLength(this,10);" onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 16, event);" theme="simple" /></td>
				<td><s:textfield name="searchObject.supKey" maxlength="1"
						cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}" theme="simple" /></td>
			</tr>
			<tr class="evenRowCenter">
				<td colspan="15" class="right"><a
					href="javascript:changeCmdAndSubmit(document.affasrForm,'filter');">
						A<img src="images/btnFilter.png" alt="Filter" />
				</a> <a
					href="javascript:changeCmdAndSubmit(document.affasrForm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter" />
				</a></td>
			</tr>
		</table>
		<hr />
		<!-- Paging Section -->
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/asrUsagePaging.jsf"%>
		</div>
		<!-- Data Grid Headers -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_PROD_ORIGIN%>');">
						Prod<br>Orig<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_PROD_ORIGIN">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_RPT_INV_CD%>');">
						Inv<br>Cd<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_AFF%>');">
						Supp<br>Aff<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>


				<!-- 1st part end -->


				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv<br>Cd<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td colspan="4">Sup Prod</td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_USAGE_FACTOR%>');">
						Usage<br>Factor<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_USAGE_FACTOR">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_KEY%>');">
						Sup<br>Key<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_KEY">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2" valign="middle"><input type="image"
					src="images/btnCheck.png" alt="Toggle Select All"
					onClick="return toggleSelectAll('affBpcListItem','selected','<s:property value="asrListSize"/>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_RPT_LIST%>');">
						List<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_RPT_LABEL%>');">
						Label<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_RPT_SIZE%>');">
						Size<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_RPT_PACK%>');">
						Pack<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.affasrForm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
			</tr>
			<!-- Data Rows -->
			<s:hidden name="asrListSize" />
			<s:if test="asrListSize != 0">
				<s:iterator value="asrList" var="asrBean" status="asrStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("asrStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
					%>
					<s:set var="onChangeCall"
						value="%{'makeEditDirty(\"affBpcListItem[' + #asrStatus.index + '].selected\")'}" />
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow"
						rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter">
							<!-- Empty for row number/actions -->
						</td>
						<td class="mntCenter"><s:if
								test="#asrBean.msg != null && #asrBean.msg != ''">
								<a class="error" href="#" id="anchor<%=rowNumber%>"
									name="anchor<%=rowNumber%>" onclick="return false;"
									onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#asrBean.msg" escapeHtml="true"/>');"
									onmouseout='hideMsgPopup();'> <img
									src="images/exclamation.png" />
								</a>
							</s:if> <s:textfield name="asrList[%{#asrStatus.index}].productOrigin"
								value="%{#asrBean.productOrigin}" maxlength="1"
								cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);"
								onblur="checkPadLeft(this,'0',1);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].rptAff"
								value="%{#asrBean.rptAff}" maxlength="4" cssClass="fltrWidth4"
								onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].rptProduct.invCode"
								value="%{#asrBean.rptProduct.invCode}" maxlength="1"
								cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);"
								onblur="checkPadLeft(this,'0',1);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].rptProduct.list"
								value="%{#asrBean.rptProduct.list}" maxlength="6"
								cssClass="mntWidth6" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 6, event);"
								onblur="checkPadLeft(this,'0',6);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].rptProduct.label"
								value="%{#asrBean.rptProduct.label}" maxlength="3"
								cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].rptProduct.size"
								value="%{#asrBean.rptProduct.size}" maxlength="3"
								cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].rptProduct.pack"
								value="%{#asrBean.rptProduct.pack}" maxlength="4"
								cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supAff"
								value="%{#asrBean.supAff}" maxlength="4" cssClass="mntWidth4"
								onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supProduct.invCode"
								value="%{#asrBean.supProduct.invCode}" maxlength="1"
								cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supProduct.list"
								value="%{#asrBean.supProduct.list}" maxlength="6"
								cssClass="mntWidth6" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 6, event);"
								onblur="checkPadLeft(this,'0',6);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supProduct.label"
								value="%{#asrBean.supProduct.label}" maxlength="3"
								cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supProduct.size"
								value="%{#asrBean.supProduct.size}" maxlength="3"
								cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);" theme="simple" /></td>

						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supProduct.pack"
								value="%{#asrBean.supProduct.pack}" maxlength="4"
								cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" theme="simple" /></td>
						<td class="mntLeft"><s:textfield
								name="asrList[%{#asrStatus.index}].usage"
								value="%{#asrBean.usage}" maxlength="16" cssClass="mntWidth16"
								onchange="%{#onChangeCall}" onblur="alertLength(this,10);"
								onkeyup="return autoTab(this, 16, event);" theme="simple" /></td>
						<td class="mntCenter"><s:textfield
								name="asrList[%{#asrStatus.index}].supKey"
								value="%{#asrBean.supKey}" maxlength="1" cssClass="mntWidth1"
								onchange="%{#onChangeCall}" theme="simple" />
						</td>
						<td class="mntCenter"><s:checkbox
								name="asrList[%{#asrStatus.index}].selected"
								value="%{#asrBean.selected}" theme="simple" /></td>
					</abbott:row>
				</s:iterator>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/asrUsageBtmPaging.jsf"%>
				</div>
			</s:if>
		</table>

		<s:if test="asrListSize == 0">
		MMM
			<%@ include file="/include/recordsNotFound.jsf"%>
		</s:if>
		<hr />
	</s:form>
	<!-- JavaScript Section -->

	

	<!-- Java script start-->


	<!-- JavaScript Section -->
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
				// Get the form elements
				var asrListSizeElement = document.affasrForm.elements['asrListSize'];
				if (asrListSizeElement) {
					var listSize = asrListSizeElement.value;
					for (i = 0; i < listSize; i++) {
						var element = "asrList[" + i + "].selected";				
						if (!rowSelected) {
							for (j = 0; j < document.affasrForm.elements.length; j++) {
								if (document.affasrForm.elements[j].name == element) {
									if (document.affasrForm.elements[j].checked == true) {
										rowSelected = true;
									}
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
			// Get the form elements
			var asrListSizeElement = document.affasrForm.elements['asrListSize'];
			if (asrListSizeElement) {
				var listSize = asrListSizeElement.value;
				for (i = 0; i < listSize; i++) {
					var element = "asrList[" + i + "].selected";				
					if (!rowSelected) {
						for (j = 0; j < document.affasrForm.elements.length; j++) {
							if (document.affasrForm.elements[j].name == element) {
								if (document.affasrForm.elements[j].checked == true) {
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
				alert('You must select at least one row to SaveSelected');
			}	
		}
		/**
		 * Download ASR Usage data
		 */
		function setDownload() {
			var flag = true;
			if ((document.forms[0].cycle[0].checked || 
				 document.forms[0].cycle[1].checked || 
				 document.forms[0].cycle[2].checked || 
				 document.forms[0].cycle[3].checked)) {
				alert("Please De-Select Plan");
				flag = false;
			} else {
				flag = true;
			}
			if (flag) {
				chgActCmdSubmit(document.affasrForm, 'download', 'affasrFormDownLoad.do');
			}
		}
		/**
		 * Upload ASR Usage data
		 */
		function setUpload() {
			var flag = false;
			if ((document.forms[0].cycle[0].checked || 
				 document.forms[0].cycle[1].checked || 
				 document.forms[0].cycle[2].checked || 
				 document.forms[0].cycle[3].checked)) {
				if (validateFile(document.forms[0].theFile.value)) {
					flag = true;
				} else {
					alert("Invalid File"); 
					flag = false;
				}
			} else {
				alert("Please Select a Plan");
				flag = false;
			}
			if (flag) {
				chgActCmdSubmit(document.affasrForm, 'upload', 'affasrFormUpload.do');
			}
		}
		/**
		 * Validate file extension
		 */
		function validateFile(fileName) {
			var dotPosition = fileName.indexOf(".");
			var alertMessage = "";
			if (dotPosition > 0) {
				var extension = fileName.substring(dotPosition + 1).toLowerCase();
				if (!(extension == 'xls' || extension == 'xlsx')) {
					alertMessage = alertMessage + "Invalid File Name\n";
				}
			} else {
				alertMessage = alertMessage + "Invalid File\n";
			}
			if (alertMessage.length > 0) {
				return false;
			} else {
				return true;
			}
		}
	</script>

	<!-- Java script end -->


	<%@ include file="/include/footer.jsf"%>
</body>
