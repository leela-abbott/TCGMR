
<%! String pageTitle = "BPC Revision Maintenance"; %>
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
	<s:form method="post" name="bpcRevTranForm" action="bpcRevTranMaint.action"  theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<%//Begin code for filter row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rev<br>Type</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Code</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
				<td rowspan="2">Pub<br>Flag</td>
				<td rowspan="2">User<br>Id</td>								
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<% String submitFilter = "submitFilter(document.bpcRevTranForm,'filter', event);"; %>
			<s:hidden name="searchObject.bpcRev.modelId" />
			<s:hidden name="searchObject.bpcRev.datasetTableId" />
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="searchObject.actionCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.revType" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.supAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.supProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.supProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.supProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.supProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.supProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" />
				</td>
				<td colspan="1">
					<s:textfield name="searchObject.bpcRev.billPrice" maxlength="15" cssClass="fltrWidth10"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onblur="alertLength(this,10);" 
						onkeyup="return autoTab(this, 15, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.bpCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 5, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.costCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 5, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcRev.begPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 2, event);" />
				</td>
				<td>
					<s:textfield name="searchObject.publishFlag" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');" 
						onkeydown="%{submitFilter}" />
				</td>
				<%-- <td>
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
								  list="#{'ALL':'ALL'}" 
								  listKey="key" 
								  listValue="value" />
						<s:select name="searchObject.userSelected" 
								  list="#session.TCGMUser.userlist" 
								  cssClass="commandOption"
								  headerKey="ALL" 
								  headerValue="ALL" />
					</abbott:securePage>
				</td> --%>
				
				<td colspan="2">
            <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" 
                              requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" 
                              comparisonType=">=">
                    <s:select name="userSelected" 
                              cssClass="commandOption"
                              list="userlist"
                              onchange="makeFilterDirty('pagingDiv','red','bold');" />
            </abbott:securePage>
                <%-- </s:if> --%>
                <s:else>
                    <s:property value="TCGMUser.userid" />
                </s:else>
            </td>					
			</tr>
			<tr>
				<td colspan="18" class="bgWhiteRight">
					<a href="javascript:changeCmdAndSubmit(document.bpcRevTranForm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.bpcRevTranForm,'advancedfilter');">
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>						
					<a href="javascript:changeCmdAndSubmit(document.bpcRevTranForm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter"/></a>
				</td>
			</tr>
		</table>
		<hr />
		<%//Begin row for adding new record%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rev<br>Type</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">Bp<br>Cur<br>Code</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" value="ASC" />
			<s:hidden name="addNew.bpcRev.modelId" />
			<s:hidden name="addNew.bpcRev.datasetTableId" />
			<% 
				String submitAdd = "submitAdd(document.bpcRevTranForm,'add','bpcRevTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) {
					submitAdd = "submitAdd(document.bpcRevTranForm,'add','bpcRevTranSave.do', event);";
				} else {
					submitAdd = "";
				}
			%>
			<tr class="oddRowCenter">
				<td>
					<s:if test="addNew.bpcRev.msg != null && addNew.bpcRev.msg != ''">
						<a class="error" href="#" id="anchorAddNew" name="anchorAddNew"
							onclick="return false;"
							onmouseover="showMsgPopup('anchorAddNew', '<s:property value="addNew.bpcRev.msg" escapeHtml="true"/>');"
							onmouseout='hideMsgPopup();'>
							<img src="images/exclamation.png" />
						</a>
					</s:if>
					<s:textfield name="addNew.actionCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.revType" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.supAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.supProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.supProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" />
				</td>
				
						
						<!-- Second Part -->
						
						
				<td>
					<s:textfield name="addNew.bpcRev.supProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.supProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.supProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.billPrice" maxlength="15" cssClass="fltrWidth10"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onblur="alertLength(this,10);" 
						onkeyup="return autoTab(this, 15, event);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.bpCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 5, event);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.begPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 2, event);"
						onblur="checkPadLeft(this,'0',2);" />
				</td>
				<td>
					<s:textfield name="addNew.bpcRev.endPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 2, event);"
						onblur="checkPadLeft(this,'0',2);" />
				</td>
			</tr>
			<tr>
				<% if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) { %>
					<td class="bgWhiteRight" colspan="18">
						<a href="javascript:chgActCmdSubmit(document.bpcRevTranForm,'add','bpcRevTranSave.do');">
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="javascript:chgActCmdSubmit(document.bpcRevTranForm,'massupdate','bpcRevTranSave.do');">
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="javascript:chgActCmdSubmit(document.bpcRevTranForm,'clearaddnew','bpcRevTranMaint.do');">
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				<% } %>
			</tr>
		</table>
		<hr />
		<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/bpcRevTranPaging.jsf" %>
		</div>
		<%// Start labels for bottom part of page. (subf) %>
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_ACD%>');">
						Act<br>Code<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_ACD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_REV_TYPE%>');">
						Rev<br>Type<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_REV_TYPE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_SUP_AFF%>');">
						Supp<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price<br></td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_BP_CUR_CD%>');">
						BP Cur<br>Code<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_BP_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">Beg<br>Period<br></td>
				<td rowspan="2">End<br>Period<br></td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');">
						Pub<br>Flag<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_PUBLISH_FLAG">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('bpcRevTranList','bpcRev.selected','<s:property value="bpcRevTranListSize"/>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpcRevTranForm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<%// End - Subf Labels %>
			<s:hidden name="bpcRevTranListSize" />
			<s:if test="bpcRevTranListSize != 0">
				<s:iterator value="bpcRevTranList" var="bpcRevTranBean" status="bpcRevTranStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("bpcRevTranStatus");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
						String onChangeCall = "makeEditDirty('bpcRevTranList[" + rowNumber + "].bpcRev.selected');";
					%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter">
							<s:property value="pagingFilter.startRecord + #bpcRevTranStatus.index"/>
							<s:if test="#bpcRevTranBean.bpcRev.msg != null && #bpcRevTranBean.bpcRev.msg != ''">
								<a class="error" href="#" 
								   id="anchor<%=rowNumber%>" 
								   name="anchor<%=rowNumber%>"
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#bpcRevTranBean.bpcRev.msg" escapeHtml="true"/>');"
								   onmouseout='hideMsgPopup();'>
									<img src="images/exclamation.png" />
								</a>
							</s:if>
							<s:textfield 
							            name="bpcRevTranList[%{#bpcRevTranStatus.index}].actionCode" 
										 readonly="true" maxlength="1" cssClass="mntWidth1"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.revType" 
										 maxlength="1" cssClass="fltrWidth1"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.rptAff" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" />
						</td>
		
							
										
				<!-- Last Part -->
				
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.supAff" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.supProduct.invCode" 
										 maxlength="1" cssClass="fltrWidth1"
										onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.supProduct.list" 
										 maxlength="6" cssClass="fltrWidth6"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 6, event);"
										 onblur="checkPadLeft(this,'0',6);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.supProduct.label" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.supProduct.size" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.supProduct.pack" 
										 maxlength="4" cssClass="fltrWidth4"
										onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.billPrice" 
										 maxlength="15" cssClass="fltrWidth15"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onblur="alertLength(this,10);" 
										 onkeyup="return autoTab(this, 15, event);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.bpCurCode" 
										 maxlength="5" cssClass="fltrWidth5"
										onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 5, event);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.begPeriod" 
										 maxlength="2" cssClass="fltrWidth2"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 2, event);"
										 onblur="checkPadLeft(this,'0',2);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.endPeriod" 
										 maxlength="2" cssClass="fltrWidth2"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeyup="return autoTab(this, 2, event);"
										 onblur="checkPadLeft(this,'0',2);" 
										 onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcRevTranList[%{#bpcRevTranStatus.index}].publishFlag" 
										 maxlength="1" cssClass="fltrWidth1" 
										 disabled="true"/>
						</td>
						<td class="mntCenter">
							<s:checkbox name="bpcRevTranList[%{#bpcRevTranStatus.index}].bpcRev.selected" />
						</td>
					</abbott:row>
					<%// Start next subf row for additional fields %>
					<%// Start the 1 - 13 billing and cost price period fields %>
					<%// Billing Price Periods %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						<td colspan="1">&nbsp;</td>
						<td class="mntCenter">BP<br>Period</td>
						<td colspan="16" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#bpcRevTranBean.bpcRev.bpPeriodValues" var="bpPeriod1" 
												begin="0" end="5" status="bpPeriodStatus1">
										<td class="mntRight" width="16%">
											<s:property value="#bpPeriod1.period"/>
										</td>
									</s:iterator>
								</tr>
								<tr>
									<s:iterator value="#bpcRevTranBean.bpcRev.bpPeriodValues" var="bpPeriod2" 
												begin="6" end="11" status="bpPeriodStatus2">
										<td class="mntRight">
											<s:property value="#bpPeriod2.period"/>
										</td>
									</s:iterator>
								</tr>
							</table>
						</td>
					</abbott:row>
					<%// Cost Price Periods %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						<td colspan="1">&nbsp;</td>
						<td class="mntCenter">Cost<br>Period</td>
						<td colspan="16" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#bpcRevTranBean.bpcRev.costPeriodValues" var="costPeriod1" 
												begin="0" end="5" status="costPeriodStatus1">
										<td class="mntRight" width="16%">
											<s:property value="#costPeriod1.period"/>
										</td>
									</s:iterator>
								</tr>
								<tr>
									<s:iterator value="#bpcRevTranBean.bpcRev.costPeriodValues" var="costPeriod2" 
												begin="6" end="11" status="costPeriodStatus2">
										<td class="mntRight">
											<s:property value="#costPeriod2.period"/>
										</td>
									</s:iterator>
								</tr>
							</table>
						</td>
					</abbott:row>
					<%// End the 1 - 13 bill and cost period fields %>
				</s:iterator>
				<%// End - Subf Data Portion for Original flds %>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/bpcRevTranBtmPaging.jsf" %>
				</div>
			</s:if>
		</table>
		<s:if test="bpcRevTranListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</s:if>
		<hr />
	</s:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<s:property value="focusField"/>');
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
					for (i = 0; i < document.bpcRevTranForm.elements['bpcRevTranListSize'].value; i++) {
						var element = "bpcRevTranList[" + i + "].bpcRev.selected";
						if (!rowSelected) {
							for (j = 0; j < document.bpcRevTranForm.elements.length; j++) {
								if (document.bpcRevTranForm.elements[j].name == element) {
									if (document.bpcRevTranForm.elements[j].checked == true) {
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
		 * Prompt the user to select atleast one records to publish
		 */  	
		function checkPublish(form, cmd, action) {
			var rowSelected = false;
			if (cmd == 'publishall' || cmd == 'unpublishall') {				
				rowSelected = true;
			} else {
				for (i = 0; i < document.bpcRevTranForm.elements['bpcRevTranListSize'].value; i++) {			
					var element = "bpcRevTranList[" + i + "].bpcRev.selected";				
					if (!rowSelected) {				
						for (j = 0; j < document.bpcRevTranForm.elements.length; j++) {
							if (document.bpcRevTranForm.elements[j].name == element) {
								if (document.bpcRevTranForm.elements[j].checked == true) {							
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
				for (i = 0; i < document.bpcRevTranForm.elements['bpcRevTranListSize'].value; i++) {
					var element = "bpcRevTranList[" + i + "].bpcRev.selected";				
					if (!rowSelected) {
						for (j = 0; j < document.bpcRevTranForm.elements.length; j++) {
							if (document.bpcRevTranForm.elements[j].name == element) {
								if (document.bpcRevTranForm.elements[j].checked == true) {
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
						