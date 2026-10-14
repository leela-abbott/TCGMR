jsp
<%! String pageTitle = "Rate Data Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:useBean id="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst" scope="session" class="abbott.ai.tcgm.data.DBConst" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadRate.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<s:form method="post" name="rateDataTranForm" action="rateDataTranMaint" namespace="/" theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="sortObject.sortColumn" />
		<s:hidden name="sortObject.sortOrder" />
		<!-- FILTER SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td>Act<br>Code</td>
				<td>Rate</td>				
				<td>Cur<br>Cd</td>
				<td>Beg<br>Period</td>
				<td>End<br>Period</td>
				<td>Pub<br>Flag</td>
				<td>User Id<br>Flag</td>
				<td width="40%" class="bgWhiteRight">&nbsp;</td>
			</tr>
			<s:hidden name="searchObject.rateData.modelId" />
			<s:hidden name="searchObject.rateData.datasetTableId" />
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="searchObject.actionCode" 
								 maxlength="1" 
								 cssClass="fltrWidth1"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 onkeyup="return autoTab(this,1,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rate" 
								 maxlength="15" 
								 cssClass="fltrWidth15"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 onblur="alertLength(this,9);" 
								 onkeyup="return autoTab(this,15,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.rateData.curCode" 
								 maxlength="5" 
								 cssClass="fltrWidth5"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 onkeyup="return autoTab(this,5,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.begPeriod" 
								 maxlength="2" 
								 cssClass="fltrWidth2"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 onkeyup="return autoTab(this,2,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.endPeriod" 
								 maxlength="2" 
								 cssClass="fltrWidth2"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 onkeyup="return autoTab(this,2,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.publishFlag" 
								 maxlength="1" 
								 cssClass="fltrWidth1"
								 onchange="makeFilterDirty('pagingDiv','red','bold');"
								 theme="simple" />
				</td>
	<%-- 			<td>
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
	 --%>			
				
				<td>
			            <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" 
			                              requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" 
			                              comparisonType=">=">
			                    <s:select name="userSelected" 
			                              cssClass="commandOption"
			                              list="userlist"
			                              onchange="makeFilterDirty('pagingDiv','red','bold');" />
			            </abbott:securePage>
			                <s:else>
			                    <s:property value="TCGMUser.userid" />
			                </s:else>
                     </td>
				
									
				<td class="bgWhiteRight">
					<a href="javascript:changeCmdAndSubmit(document.rateDataTranForm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.rateDataTranForm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>
		<hr />
		<!-- ADD NEW SECTION -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td>Act<br>Code</td>
				<td>Rate</td>				
				<td>Cur<br>Cd</td>
				<td>Beg<br>Period</td>
				<td>End<br>Period</td>
				<td width="40%" class="bgWhiteRight">&nbsp;</td>
			</tr>
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
								 onkeyup="return autoTab(this,1,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.rateData.rate" 
								 maxlength="15" 
								 cssClass="fltrWidth15"
								 onchange="makeAddNewDirty();"
								 onblur="alertLength(this,9);" 
								 onkeyup="return autoTab(this,15,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.rateData.curCode" 
								 maxlength="5" 
								 cssClass="fltrWidth5"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this,5,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.rateData.begPeriod" 
								 maxlength="2" 
								 cssClass="fltrWidth2"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this,2,event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.rateData.endPeriod" 
								 maxlength="2" 
								 cssClass="fltrWidth2"
								 onchange="makeAddNewDirty();"
								 onkeyup="return autoTab(this,2,event);"
								 theme="simple" />
				</td>					
				<td class="bgWhiteRight">
					<a href="javascript:chgActCmdSubmit(document.rateDataTranForm,'add','rateDataTranSave.do');">
						<img src="images/btnAdd.png" alt="Add" /></a>
					<a href="javascript:chgActCmdSubmit(document.rateDataTranForm,'massupdate','rateDataTranSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.rateDataTranForm,'clearaddnew','rateDataTranMaint.do');">
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			</tr>
		</table>
		<hr/>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/rateDataTranPaging.jsf" %>
		</div>
		<!-- DATA GRID HEADERS -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_ACD%>');">
						Act<br>Code<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_ACD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_RATE%>');">
						Rate<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RATE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>				
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_CUR_CD%>');">
						Cur<br>Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_BEG_PERIOD%>');">
						Beg<br>Period<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_BEG_PERIOD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_END_PERIOD%>');">
						End<br>Period<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_PERIOD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');">
						Pub<br>Flag<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_PUBLISH_FLAG">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('rateDataTranListItem','rateData.selected','<s:property value="rateDataTranListSize"/>');" />
				</td>
			</tr>
			<!-- DATA ROWS -->
			<s:hidden name="rateDataTranListSize" />
			<s:if test="rateDataTranListSize != 0">
				<s:iterator value="rateDataTranList" var="rateDataTranBean" status="rateDataTranStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("rateDataTranStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
					%>
					<s:set var="onChangeCall" value="%{'makeEditDirty(\"rateDataTranListItem[' + #rateDataTranStatus.index + '].rateData.selected\")'}"/>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter">
							<s:property value="pagingFilter.startRecord + #rateDataTranStatus.index"/>
							
							
								   
								   
							<!-- 1st Part end -->

             
							<s:if test="#rateDataTranBean.rateData.msg != null && #rateDataTranBean.rateData.msg != ''">				
								<a class="error" href="#" 
								   id="anchor<%=rowNumber%>" 
								   name="anchor<%=rowNumber%>"				
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#rateDataTranBean.rateData.msg" escapeHtml="true"/>');"
								   onmouseout='hideMsgPopup();'>
									<img src="images/exclamation.png" />
								</a>
							</s:if>		
							<s:textfield name="rateDataTranListItem[%{#rateDataTranStatus.index}].actionCode" 
										 maxlength="1" 
										 cssClass="mntWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="rateDataTranListItem[%{#rateDataTranStatus.index}].rateData.rate" 
										 maxlength="15" 
										 cssClass="mntWidth15"
										 onblur="alertLength(this,9);" 
										 onchange="%{#onChangeCall}"
										 theme="simple" />
						</td>						
						<td class="mntCenter">
							<s:textfield name="rateDataTranListItem[%{#rateDataTranStatus.index}].rateData.curCode" 
										 maxlength="5" 
										 cssClass="mntWidth5"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this,5,event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="rateDataTranListItem[%{#rateDataTranStatus.index}].rateData.begPeriod" 
										 maxlength="2" 
										 cssClass="mntWidth2"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this,2,event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="rateDataTranListItem[%{#rateDataTranStatus.index}].rateData.endPeriod" 
										 maxlength="2" 
										 cssClass="mntWidth2"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this,2,event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="rateDataTranListItem[%{#rateDataTranStatus.index}].publishFlag" 
										 maxlength="1" 
										 cssClass="mntWidth1" 
										 disabled="true"
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:checkbox name="rateDataTranListItem[%{#rateDataTranStatus.index}].rateData.selected" 
										theme="simple" />
						</td>
					</abbott:row>
					<!-- Rate Period Values 1-12 -->
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						<td class="mntCenter">
							Rates 1-6<br>Rates 7-12
						</td>
						<td colspan="16">
							<table width="100%" cellspacing="0">
								<tr>		
									<s:iterator value="#rateDataTranBean.rateData.rates" var="ratePeriod1" 
												begin="0" end="5" status="ratePeriodStatus1">
										<td class="mntRight" width="16%">
											<s:property value="#ratePeriod1.period"/>
										</td>
									</s:iterator>			
								</tr>
								<tr>
									<s:iterator value="#rateDataTranBean.rateData.rates" var="ratePeriod2"
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
		<s:if test="rateDataTranListSize == 0">
			<p class="recordsNotFound">
				No Records Found Matching Filter Criteria
			</p>
		</s:if>
		<hr />
	</s:form>
	<!-- JAVASCRIPT SECTION -->
	<script language="JavaScript1.2" type="text/javascript">
		setFocus('addNew.actionCode');
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
					for (i = 0; i < document.rateDataTranForm.elements['rateDataTranListSize'].value; i++) {
						var element = "rateDataTranListItem[" + i + "].rateData.selected";				
						if (!rowSelected) {
							for (j = 0; j < document.rateDataTranForm.elements.length; j++) {
								if (document.rateDataTranForm.elements[j].name == element) {
									if (document.rateDataTranForm.elements[j].checked == true) {
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
			if (cmd == 'publishall') {		
				rowSelected = true;
			} else {
				for (i = 0; i < document.rateDataTranForm.elements['rateDataTranListSize'].value; i++) {	
					var element = "rateDataTranListItem[" + i + "].rateData.selected";				
					if (!rowSelected) {		
						for (j = 0; j < document.rateDataTranForm.elements.length; j++) {
							if (document.rateDataTranForm.elements[j].name == element) {
								if (document.rateDataTranForm.elements[j].checked == true) {							
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
				for (i = 0; i < document.rateDataTranForm.elements['rateDataTranListSize'].value; i++) {
					var element = "rateDataTranListItem[" + i + "].rateData.selected";				
					if (!rowSelected) {
						for (j = 0; j < document.rateDataTranForm.elements.length; j++) {
							if (document.rateDataTranForm.elements[j].name == element) {
								if (document.rateDataTranForm.elements[j].checked == true) {
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
	</script>
<%@ include file="/include/footer.jsf" %>
</body>  							