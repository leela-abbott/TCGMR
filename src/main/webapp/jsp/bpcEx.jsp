<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%!
    String pageTitle = "BPC Exception Data";
%>

<a id="FilterView" name="FilterView"></a>
<%@ include file="/include/header.jsf"%>


<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser" scope="session"
	type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst" scope="session"
	class="abbott.ai.tcgm.data.DBConst" />

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/maintNav.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form method="post" name="bpcExForm" action="bpcExMaint.action"
		theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<s:hidden name="rowToCopy" />
		 <table width="780" cellspacing="0" border="0">
			<tr class="fltrTblHdng">
				<s:hidden name="bpcExErrorListSize" />
				<s:if test="bpcExErrorListSize != 0">
					<td rowspan="2">Errors</td>
				</s:if>
				<td rowspan="2">End<br>Aff
				</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">Rpt<br>Aff
				</td>
				<td colspan="5">Rpt Prod</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>

			
			<% String submitFilter = "submitFilter(document.bpcExForm,'filter', event);"; %>

			<tr class="oddRowCenter">
				<s:hidden name="bpcExErrorListSize" />
				<s:if test="bpcExErrorListSize != 0">
					<td><input type="checkbox" name="errs" value="on"
						onClick="javascript:changeCmdAndSubmit(document.bpcExForm,'filter');">
					</td>
				</s:if>

				<s:iterator value="searchObject">
					<s:hidden name="modelId" />
					<s:hidden name="datasetTableId" />
					<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
					<td><s:textfield theme="simple" name="searchObject.endAff"
							maxlength="4" size="4" cssClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown="%{submitFilter}"
							onkeyup="return autoTab(this, 4, event);"
							onblur="checkPadLeft(this,'0',4);" /></td>

					<s:iterator value="endProduct">


						<td><s:textfield theme="simple"
								name="searchObject.endProduct.invCode" maxlength="1" size="1"
								cssClass="fltrWidth1"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 1, event);" /></td>
						<td><s:textfield theme="simple"
								name="searchObject.endProduct.list" maxlength="6"
								cssClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 6, event);" /></td>
						<td><s:textfield name="searchObject.endProduct.label"
								maxlength="3" cssClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 3, event);" /></td>
						<td><s:textfield name="searchObject.endProduct.size"
								maxlength="3" cssClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 3, event);" /></td>
						<td><s:textfield name="searchObject.endProduct.pack"
								maxlength="4" cssClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}" /></td>


					</s:iterator>



					<td><s:textfield name="searchObject.rptAff" maxlength="4"
							cssClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown="%{submitFilter}"
							onkeyup="return autoTab(this, 4, event);"
							onblur="checkPadLeft(this,'0',4);" /></td>
					<s:iterator value="rptProduct">

						<td><s:textfield name="searchObject.rptProduct.invCode"
								maxlength="1" cssClass="fltrWidth1"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 1, event);" /></td>
						<td><s:textfield name="searchObject.rptProduct.list"
								maxlength="6" cssClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 6, event);" /></td>
						<td><s:textfield name="searchObject.rptProduct.label"
								maxlength="3" cssClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 3, event);" /></td>
						<td><s:textfield name="searchObject.rptProduct.size"
								maxlength="3" cssClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 3, event);" /></td>
						<td><s:textfield name="searchObject.rptProduct.pack"
								maxlength="4" cssClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 4, event);" /></td>
					</s:iterator>

				</s:iterator>
			</tr>


			<tr class="fltrTblHdng">
				<td rowspan="2">Supp<br>Aff
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Frz<br>Cost
				</td>
				<td rowspan="2">BP<br>Cur<br>Cd
				</td>
				<td rowspan="2">Cost<br>Cur<br>Cd
				</td>
				<td colspan="2" class="bgWhiteRight">&nbsp;</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
				<td colspan="7" class="bgWhiteRight">&nbsp;</td>
			</tr>

			<s:iterator value="searchObject">
				<tr class="oddRowCenter">
					<td><s:textfield name="searchObject.supAff" maxlength="4"
							cssClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown="%{submitFilter}"
							onkeyup="return autoTab(this, 4, event);"
							onblur="checkPadLeft(this,'0',4);" /></td>
					<s:iterator value="supProduct">

						<td><s:textfield name="searchObject.supProduct.invCode"
								maxlength="1" cssClass="fltrWidth1"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 1, event);" /></td>
						<td><s:textfield name="searchObject.supProduct.list"
								maxlength="6" cssClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 6, event);" /></td>
						<td><s:textfield name="searchObject.supProduct.label"
								maxlength="3" cssClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 3, event);" /></td>
						<td><s:textfield name="searchObject.supProduct.size"
								maxlength="3" cssClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 3, event);" /></td>
						<td><s:textfield name="searchObject.supProduct.pack"
								maxlength="4" cssClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown="%{submitFilter}"
								onkeyup="return autoTab(this, 4, event);" /></td>

					</s:iterator>

					<td><s:textfield name="searchObject.freezeCost" maxlength="1"
							cssClass="fltrWidth1"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown="%{submitFilter}"
							onkeyup="return autoTab(this, 1, event);" /></td>
					<td><s:textfield name="searchObject.bpCurCode" maxlength="5"
							cssClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown="%{submitFilter}"
							onkeyup="return autoTab(this, 5, event);" /></td>
					<td><s:textfield name="searchObject.costCurCode" maxlength="5"
							cssClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown="%{submitFilter}"
							onkeyup="return autoTab(this, 5, event);" /></td>

					<td colspan="7" class="right"><a
						href="javascript:changeCmdAndSubmit(document.bpcExForm,'filter');">
							<img src="images/btnFilter.png" alt="Filter" />
					</a> <a
						href="javascript:changeCmdAndSubmit(document.bpcExForm,'advancedfilter');">
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" />
					</a> <a
						href="javascript:changeCmdAndSubmit(document.bpcExForm,'clearfilter');">
							<img src="images/btnClear.png" alt="Clear Filter" />
					</a></td>
				</tr>
			</s:iterator>

		</table>

		<hr />

		<!--  Table 2 starts -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Cd
				</td>
				<td rowspan="2">End<br>Aff
				</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">Rpt<br>Aff
				</td>
				<td colspan="5">Rpt Prod</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<% 
				String submitSave = "submitSave(document.bpcExForm,'save','bpcExSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.bpcExForm,'save','bpcExSave.do', event);";
				} else {
					submitSave = "";
				}
			%>
			<s:hidden name="addNew.bpcEx.modelId" />
			<s:hidden name="addNew.bpcEx.datasetTableId" />
			<tr class="oddRowCenter">
				<td><s:if
						test="addNew.bpcEx.msg != null && addNew.bpcEx.msg != ''">
						<a class="error" href="#" id="anchorAddNew" name="anchorAddNew"
							onclick="return false;"
							onmouseover="showMsgPopup('anchorAddNew', '<s:property value="addNew.bpcEx.msg" escapeHtml="true"/>');"
							onmouseout='hideMsgPopup();'> <img
							src="images/exclamation.png" />
						</a>
					</s:if> <s:textfield name="addNew.actionCode" maxlength="1"
						cssClass="fltrWidth1" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" /></td>





				<td><s:textfield name="addNew.bpcEx.endAff" maxlength="4"
						cssClass="fltrWidth4" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" /></td>
				<td><s:textfield name="addNew.bpcEx.endProduct.invCode"
						maxlength="1" cssClass="fltrWidth1" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" /></td>
				<td><s:textfield name="addNew.bpcEx.endProduct.list"
						maxlength="6" cssClass="fltrWidth6" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" /></td>
				<td><s:textfield name="addNew.bpcEx.endProduct.label"
						maxlength="3" cssClass="fltrWidth3" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" /></td>
				<td><s:textfield name="addNew.bpcEx.endProduct.size"
						maxlength="3" cssClass="fltrWidth3" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" /></td>
				<td><s:textfield name="addNew.bpcEx.endProduct.pack"
						maxlength="4" cssClass="fltrWidth4" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" /></td>
				<td><s:textfield name="addNew.bpcEx.rptAff" maxlength="4"
						cssClass="fltrWidth4" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" /></td>
				<td><s:textfield name="addNew.bpcEx.rptProduct.invCode"
						maxlength="1" cssClass="fltrWidth1" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" /></td>
				<td><s:textfield name="addNew.bpcEx.rptProduct.list"
						maxlength="6" cssClass="fltrWidth6" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" /></td>
				<td><s:textfield name="addNew.bpcEx.rptProduct.label"
						maxlength="3" cssClass="fltrWidth3" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" /></td>
				<td><s:textfield name="addNew.bpcEx.rptProduct.size"
						maxlength="3" cssClass="fltrWidth3" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" /></td>
				<td><s:textfield name="addNew.bpcEx.rptProduct.pack"
						maxlength="4" cssClass="fltrWidth4" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" /></td>
			</tr>
			<tr class="fltrTblHdng">
				<td rowspan="2">Supp<br>Aff
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Frz<br>Cost
				</td>
				<td rowspan="2">Beg<br>Period
				</td>
				<td rowspan="2">End<br>Period
				</td>
				<td rowspan="2" colspan="1">Bill<br>Price
				</td>
				<td rowspan="2">BP<br>Cur<br>Cd
				</td>
				<td rowspan="2" colspan="1"><br>Cost</td>
				<td rowspan="2">Cost<br>Cur<br>Cd
				</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<tr class="oddRowCenter">
				<td><s:textfield name="addNew.bpcEx.supAff" maxlength="4"
						cssClass="fltrWidth4" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" /></td>
				<td><s:textfield name="addNew.bpcEx.supProduct.invCode"
						maxlength="1" cssClass="fltrWidth1" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" /></td>
				<td><s:textfield name="addNew.bpcEx.supProduct.list"
						maxlength="6" cssClass="fltrWidth6" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" /></td>
				<td><s:textfield name="addNew.bpcEx.supProduct.label"
						maxlength="3" cssClass="fltrWidth3" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" /></td>
				<td><s:textfield name="addNew.bpcEx.supProduct.size"
						maxlength="3" cssClass="fltrWidth3" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" /></td>
				<td><s:textfield name="addNew.bpcEx.supProduct.pack"
						maxlength="4" cssClass="fltrWidth4" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" /></td>
				<td><s:textfield name="addNew.bpcEx.freezeCost" maxlength="1"
						cssClass="fltrWidth1" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 1, event);" /></td>
				<td><s:textfield name="addNew.bpcEx.begPeriod" maxlength="2"
						cssClass="fltrWidth2" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 2, event);"
						onblur="checkPadLeft(this,'0',2);" /></td>
				<td><s:textfield name="addNew.bpcEx.endPeriod" maxlength="2"
						cssClass="fltrWidth2" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 2, event);"
						onblur="checkPadLeft(this,'0',2);" /></td>
				<td colspan="1"><s:textfield name="addNew.bpcEx.billPrice"
						maxlength="15" cssClass="fltrWidth10"
						onchange="makeAddNewDirty();" onkeydown="%{submitSave}"
						onblur="alertLength(this,10);"
						onkeyup="return autoTab(this, 15, event);" /></td>
				<td><s:textfield name="addNew.bpcEx.bpCurCode" maxlength="5"
						cssClass="fltrWidth5" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 5, event);" /></td>
				<td colspan="1"><s:textfield name="addNew.bpcEx.costPrice"
						maxlength="15" cssClass="fltrWidth10"
						onchange="makeAddNewDirty();" onkeydown="%{submitSave}"
						onblur="alertLength(this,10);"
						onkeyup="return autoTab(this, 15, event);" /></td>
				<td><s:textfield name="addNew.bpcEx.costCurCode" maxlength="5"
						cssClass="fltrWidth5" onchange="makeAddNewDirty();"
						onkeydown="%{submitSave}"
						onkeyup="return autoTab(this, 5, event);" /></td>
			</tr>
			<tr class="oddRowCenter">
				<% if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) { %>
				<td class="bgWhiteRight" colspan="15"><a
					href="javascript:chgActCmdSubmit(document.bpcExForm,'save','bpcExSave.do');">
						<img src="images/btnSave.png" alt="Save" />
				</a> <a
					href="javascript:chgActCmdSubmit(document.bpcExForm,'massupdate','bpcExSave.do');">
						<img src="images/btnMassUpdate.png"
						alt="Apply Changes to all records based on Filter criteria" />
				</a> <a
					href="javascript:chgActCmdSubmit(document.bpcExForm,'clearaddnew','bpcExMaint.do');">
						<img src="images/btnClear.png" alt="Clear" />
				</a></td>
				<% } %>
			</tr>
		</table>

		<hr />
		<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/bpcExPaging.jsf"%>
		</div>
		<%-- CRITICAL: Keep data rows in SAME table as headers for alignment --%>

		<!--  Table 3 -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2"></td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_END_AFF%>');">
						End<br>Aff<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td colspan="5">End Prod</td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td colspan="5">Rpt Prod</td>
				<td rowspan="2" valign="middle"><input type="image"
					src="images/btnCheck.png" alt="Toggle Select All"
					onClick="return toggleSelectAll('bpcExListItem','selected','<s:property value="bpcExListSize"/>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_END_INV_CD%>');">
						Inv Cd<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>

				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_END_LIST%>');">
						List<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_END_LABEL%>');">
						Label<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_END_SIZE%>');">
						Size<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_END_PACK%>');">
						Pack<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_RPT_INV_CD%>');">
						Inv Cd<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_RPT_LIST%>');">
						List<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_RPT_LABEL%>');">
						Label<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_RPT_SIZE%>');">
						Size<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_RPT_PACK%>');">
						Pack<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
			</tr>
			<tr class="mntTblHdng">
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_SUP_AFF%>');">
						Supp<br>Aff<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_FREEZE_COST%>');">
						Frz<br>Cost<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_FREEZE_COST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2">Beg<br>Period<br></td>
				<td rowspan="2">End<br>Period<br></td>
				<td rowspan="2" colspan="1">Bill<br>Price<br></td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_BP_CUR_CD%>');">
						BP Cur<br>Code<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_BP_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2" colspan="1"><br>Cost<br></td>
				<td rowspan="2"><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_COST_CUR_CD%>');">
						Cost<br>Cur Cd<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_COST_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td rowspan="2" colspan="1">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv Cd<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
				<td><a class="mntSort"
					href="javascript:chgSrtSubEbcdic(document.bpcExForm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br> <s:if
							test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>"
								src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
				</a></td>
			</tr>
			<s:hidden name="bpcExListSize" />
			<s:if test="bpcExListSize != 0">
				<s:iterator value="bpcExList" var="bpcExBean" status="bpcExStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("bpcExStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
						String href = "javascript:copyRow(document.bpcExForm,'" + rowNumber + "','bpcExMaint.do');";
					%>
					<s:set var="onChangeCall"
						value="%{'makeEditDirty(\"bpcExListItem[' + #bpcExStatus.index + '].selected\")'}" />
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow"
						rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter"><s:property
								value="pagingFilter.startRecord + #bpcExStatus.index" /> <a
							href="<%=href%>"> <img src="images/btnUpArrow.png"
								alt="Load Row" />
						</a> <s:if test="#bpcExBean.msg != null && #bpcExBean.msg != ''">
								<a class="error" href="#" id="anchor<%=rowNumber%>"
									name="anchor<%=rowNumber%>" onclick="return false;"
									onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#bpcExBean.msg" escapeHtml="true"/>');"
									onmouseout="hideMsgPopup();"> <img
									src="images/exclamation.png" />
								</a>
							</s:if></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endAff" maxlength="4"
								cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endProduct.invCode"
								maxlength="1" cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endProduct.list"
								maxlength="6" cssClass="mntWidth6" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 6, event);"
								onblur="checkPadLeft(this,'0',6);"
								onkeydown="restrSpace(event);" /></td>



						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endProduct.label"
								maxlength="3" cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endProduct.size"
								maxlength="3" cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endProduct.pack"
								maxlength="4" cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].rptAff" maxlength="4"
								cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].rptProduct.invCode"
								maxlength="1" cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].rptProduct.list"
								maxlength="6" cssClass="mntWidth6" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 6, event);"
								onblur="checkPadLeft(this,'0',6);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].rptProduct.label"
								maxlength="3" cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].rptProduct.size"
								maxlength="3" cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].rptProduct.pack"
								maxlength="4" cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:checkbox
								name="bpcExListItem[%{#bpcExStatus.index}].selected" /></td>
					</abbott:row>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow"
						rowNum="<%=rowNumber%>">
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].supAff" maxlength="4"
								cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].supProduct.invCode"
								maxlength="1" cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].supProduct.list"
								maxlength="6" cssClass="mntWidth6" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 6, event);"
								onblur="checkPadLeft(this,'0',6);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].supProduct.label"
								maxlength="3" cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].supProduct.size"
								maxlength="3" cssClass="mntWidth3" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 3, event);"
								onblur="checkPadLeft(this,'0',3);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].supProduct.pack"
								maxlength="4" cssClass="mntWidth4" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].freezeCost"
								maxlength="1" cssClass="mntWidth1" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 1, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].begPeriod"
								maxlength="2" cssClass="mntWidth2" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].endPeriod"
								maxlength="2" cssClass="mntWidth2" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter" colspan="1"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].billPrice"
								maxlength="15" cssClass="mntWidth10" onchange="%{#onChangeCall}"
								onblur="alertLength(this,10);"
								onkeyup="return autoTab(this, 15, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].bpCurCode"
								maxlength="5" cssClass="mntWidth5" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 5, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter" colspan="1"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].costPrice"
								maxlength="15" cssClass="mntWidth10" onchange="%{#onChangeCall}"
								onblur="alertLength(this,10);"
								onkeyup="return autoTab(this, 15, event);"
								onkeydown="restrSpace(event);" /></td>
						<td class="mntCenter"><s:textfield
								name="bpcExListItem[%{#bpcExStatus.index}].costCurCode"
								maxlength="5" cssClass="mntWidth5" onchange="%{#onChangeCall}"
								onkeyup="return autoTab(this, 5, event);"
								onkeydown="restrSpace(event);" /></td>
						<td rowspan="2" colspan="1">&nbsp;</td>
					</abbott:row>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow"
						rowNum="<%=rowNumber%>">
						<td class="mntCenter">Bill 1-6<br>Price 7-12
						</td>
						<td colspan="16" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#bpcExBean.bpPeriodValues" var="bpPeriod1"
										begin="0" end="5" status="bpPeriodStatus1">
										<td class="mntRight" width="16%"><s:property
												value="#bpPeriod1.period" /></td>
									</s:iterator>
								</tr>
								<tr>
									<s:iterator value="#bpcExBean.bpPeriodValues" var="bpPeriod2"
										begin="6" end="11" status="bpPeriodStatus2">
										<td class="mntRight"><s:property
												value="#bpPeriod2.period" /></td>
									</s:iterator>
								</tr>
							</table>
						</td>
					</abbott:row>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow"
						rowNum="<%=rowNumber%>">
						<td class="mntCenter">1-6<br>Cost 7-12
						</td>
						<td colspan="16" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#bpcExBean.costPeriodValues"
										var="costPeriod1" begin="0" end="5" status="costPeriodStatus1">
										<td class="mntRight" width="16%"><s:property
												value="#costPeriod1.period" /></td>
									</s:iterator>
								</tr>
								<tr>
									<s:iterator value="#bpcExBean.costPeriodValues"
										var="costPeriod2" begin="6" end="11"
										status="costPeriodStatus2">
										<td class="mntRight"><s:property
												value="#costPeriod2.period" /></td>
									</s:iterator>
								</tr>
							</table>
						</td>
					</abbott:row>
				</s:iterator>
			</s:if>
		</table>


		<s:if test="bpcExListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf"%>
		</s:if>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/bpcExPaging.jsf"%>
		</div>
		<hr />
	</s:form>
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
				for (i = 0; i < document.bpcExForm.elements['bpcExListSize'].value; i++) {
					var element = "bpcExListItem[" + i + "].selected";				
					if (!rowSelected) {
						for (j = 0; j < document.bpcExForm.elements.length; j++) {
							if (document.bpcExForm.elements[j].name == element) {
								if (document.bpcExForm.elements[j].checked == true) {
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
			for (i = 0; i < document.bpcExForm.elements['bpcExListSize'].value; i++) {
				var element = "bpcExListItem[" + i + "].selected";				
				if (!rowSelected) {
					for (j = 0; j < document.bpcExForm.elements.length; j++) {
						if (document.bpcExForm.elements[j].name == element) {
							if (document.bpcExForm.elements[j].checked == true) {
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
	<%@ include file="/include/footer.jsf"%>
</body>
