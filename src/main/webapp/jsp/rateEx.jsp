<a name="FilterView"></a>
<%! String pageTitle = "Rate Exception Data"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="rateExForm" scope="session" class="abbott.ai.tcgm.action.form.RateExForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="rateExForm" type="abbott.ai.tcgm.action.form.RateExForm" action="/rateExMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">End<br>Aff</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">Rpt<br>Aff</td> 
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
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
							     onchange="makeFilterDirty('pagingDiv','red','bold');"
							     onkeyup="return autoTab(this, 4, event);" />
					</td>
					<nested:nest property="endProduct">
						<td>
							<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeyup="return autoTab(this, 6, event);" />
						</td>
						<td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeyup="return autoTab(this, 4, event);" />
						</td>
					</nested:nest>
					<td>
						<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
							     onchange="makeFilterDirty('pagingDiv','red','bold');"
							     onkeyup="return autoTab(this, 4, event);" />
					</td>
					<nested:nest property="rptProduct">
						<td>
							<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 6, event);" />
						</td>
						<td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 4, event);" />
						</td>
					</nested:nest>
				</tr>
			</nested:nest>
			<tr class="fltrTblHdng">
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td colspan="10" class="bgWhiteRight">&nbsp;</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
				<td colspan="10" class="bgWhiteRight">&nbsp;</td>
			</tr>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" value="ASC" />
			</nested:nest>			
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />						
				<tr class="oddRowCenter">
					<td>
						<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
							     onchange="makeFilterDirty('pagingDiv','red','bold');"
							     onkeyup="return autoTab(this, 4, event);" />
					</td>
					<nested:nest property="supProduct">
						<td>
							<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 6, event);" />
						</td>
						<td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								     onchange="makeFilterDirty('pagingDiv','red','bold');"
								     onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								     onchange="makeFilterDirty('pagingDiv','red','bold');" />
						</td>
					</nested:nest>
					<td colspan="10" class="bgWhiteRight">
						<a href="javascript:changeCmdAndSubmit(document.rateExForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.rateExForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest>				
		</table>

		<hr />

<!-- This whole middle section below was absent. -->

		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>			
				<td rowspan="2">End<br>Aff</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">Rpt<br>Aff</td>
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
			<nested:nest property="addNew">
				<nested:hidden property="rateEx.modelId" />
				<nested:hidden property="rateEx.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:notEqual property="rateEx.msg" value="">
							<a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="rateEx.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>
						</nested:notEqual>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							     onchange="makeAddNewDirty();"
							     onkeyup="return autoTab(this, 1, event);" />
					</td>	
					<nested:nest property="rateEx">
						<td>
							<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
								     onchange="makeAddNewDirty();"
								     onkeyup="<%=rateExForm.getAutoTab(4)%>"
								     onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="endProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(6)%>"
									     onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(3)%>"
									     onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(3)%>"
									     onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(4)%>"
									     onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								     onchange="makeAddNewDirty();"
								     onkeyup="<%=rateExForm.getAutoTab(4)%>"
								     onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(6)%>"
									     onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(3)%>"
									     onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(3)%>"
									     onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(4)%>"
									     onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest><%//End Nesting Sup Product%>
					</nested:nest><%//End rateEx%>
				</tr>
			</nested:nest><%//End addNew%>
			<tr class="fltrTblHdng">
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<nested:nest property="addNew">
				<nested:hidden property="rateEx.modelId" />
				<nested:hidden property="rateEx.datasetTableId" />
				<nested:nest property="rateEx">
					<tr class="oddRowCenter">
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								     onchange="makeAddNewDirty();"
								     onkeyup="<%=rateExForm.getAutoTab(4)%>"
								     onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(6)%>"
									     onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(3)%>"
									     onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(3)%>"
									     onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									     onchange="makeAddNewDirty();"
									     onkeyup="<%=rateExForm.getAutoTab(4)%>"
									     onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								     onchange="makeAddNewDirty();"
								     onkeyup="<%=rateExForm.getAutoTab(2)%>"
								     onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td>
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								     onchange="makeAddNewDirty();"
								     onkeyup="<%=rateExForm.getAutoTab(2)%>"
								     onblur="checkPadLeft(this,'0',2);" />
						</td>
					</tr>
				</nested:nest><%//End rateEx%>					
			</nested:nest><%//End addNew%>										

				<tr class="fltrTblHdng">
					<td colspan="2">BP<br>Factor</td>
					<td colspan="2">Cost<br>Factor</td>					
					<td colspan="2">BP<br>Plan</td>
					<td colspan="2">Cost<br>Plan</td>
					<td class="bgWhiteRight" colspan="5">&nbsp;</td>
				</tr>
				<nested:nest property="addNew">
					<nested:hidden property="rateEx.modelId" />
					<nested:hidden property="rateEx.datasetTableId" />
					<nested:nest property="rateEx">
						<tr class="oddRowCenter">				
							<td colspan="2">
								<nested:text property="bpfRate" maxlength="15" styleClass="fltrWidth15"
									     onchange="makeAddNewDirty();"
									     onblur="alertLength(this,9);"
									     onkeyup="<%=rateExForm.getAutoTab(15)%>" />
							</td>
							<td colspan="2">
								<nested:text property="costfRate" maxlength="15" styleClass="fltrWidth15"
									     onchange="makeAddNewDirty();"
									     onblur="alertLength(this,9);"
									     onkeyup="<%=rateExForm.getAutoTab(15)%>" />
							</td>
							<td colspan="2">
								<nested:text property="bppRate" maxlength="15" styleClass="fltrWidth15"
									     onchange="makeAddNewDirty();"
									     onblur="alertLength(this,9);"
									     onkeyup="<%=rateExForm.getAutoTab(15)%>" />
							</td>													
							<td colspan="2">
								<nested:text property="costpRate" maxlength="15" styleClass="fltrWidth15"
									     onchange="makeAddNewDirty();"
									     onblur="alertLength(this,9);"
									     onkeyup="<%=rateExForm.getAutoTab(15)%>" />
							</td>
							<td class="bgWhiteRight" colspan="12">
								<a href="javascript:chgActCmdSubmit(document.rateExForm,'save','rateExSave.do');" >
									<img src="images/btnSave.png" alt="Save" /></a>
								<a href="javascript:chgActCmdSubmit(document.rateExForm,'massupdate','rateExSave.do');">
									<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
								<a href="javascript:chgActCmdSubmit(document.rateExForm,'clearaddnew','rateExMaint.do');" >
									<img src="images/btnClear.png" alt="Clear"/></a>
							</td>
						</tr>
					</nested:nest><%//End rateEx%>
				</nested:nest><%//End addNew%>
		</table>

		<hr />


<!-- This whole middle section above was absent. -->

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/rateExPaging.jsf" %></div>
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
			
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_END_AFF%>');" >
						End<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_AFF%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="5">
					End Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="5">
					Rpt Prod
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('rateExListItem','selected','<%=rateExForm.getRateExListSize()%>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_END_INV_CD%>');" >
						Inv Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_INV_CD%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_END_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_LIST%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_END_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_LABEL%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_END_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_SIZE%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_END_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_PACK%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_RPT_INV_CD%>');" >
						Inv Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_INV_CD%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_RPT_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LIST%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_RPT_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LABEL%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_RPT_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_SIZE%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_RPT_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_PACK%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Sup<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_AFF%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="5">
					Sup Prod
				</td>
				<td colspan="1" rowspan="2">
					Beg<br>Period<br>
				</td>
				<td colspan="1" rowspan="2">
					End<br>Period<br>
				</td> 				
				<td colspan="9">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_INV_CD%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LIST%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LABEL%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_SIZE%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateExForm,'<%=DBConst.COL_SUP_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_PACK%>" >
							<img alt="<%=rateExForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateExForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
<!-- begin & end sort was here -->								
				<td colspan="8">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td colspan="2">
					BPF<br>Rate<br>
				</td>
				<td colspan="2">
					Cost F<br>Rate<br>
				</td>				
				<td colspan="2">
					BPP<br>Rate<br>
				</td>				
				<td colspan="2">
					Cost P<br>Rate<br>
				</td>
				<td colspan="6">&nbsp;</td>
			</tr>

		</table>
		<table width="780" cellspacing="0">			
			<nested:hidden property="rateExListSize" />
			<nested:notEqual property="rateExListSize" value="0">
			<% int rowNumber = 0; %>
			
			<%// used the JSTL c:forEach tag to loop through rateExList %>
			<c:forEach items="${sessionScope.rateExForm.rateExList}"
			                		 var="rateExBean"
				               		 varStatus="rateExStatus">
				
					<% // declare a String to notify when there is a change %>							
					<% String onChangeCall = "makeEditDirty('" + "rateExListItem[" + rowNumber + "].selected" + "');"; %>
					
					<% // define the common part of the property tag of html in another string %>
		        		<% String rateExListItemArray = "rateExListItem[" + rowNumber +"]."; %>

					<% // String href encapsulates the call to a JavaScript copyRow %>
					<% String href = "javascript:copyRow(document.rateExForm,'" + rowNumber++ + "','rateExMaint.do');"; %>
					
					<% // tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>

					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">
					<%//Sridevi.K end ..%>
					
					<td  class="mntCenter" colspan="1">
						<% //Sridevi.K code added to print the record numbers %>
						<c:out value="${sessionScope.rateExForm.pagingFilter.startRecord + rateExStatus.index}"/>
						<% //Sridevi.K End of code added to print the record numbers %>
						
						<a href="<%=href%>">
							<img src="images/btnUpArrow.png" alt="Load Row" />
						</a>

						<% // Using 'c:if' to check if the rateExBean msg is not equal to "  " %>
						<c:if test="${rateExBean.msg ne ''}" >
								<a class="error"
								   href="#"
								   id="anchor<c:out value="${rateExStatus.index}"/>"
								   name="anchor<c:out value="${rateExStatus.index}"/>"
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<c:out value="${rateExStatus.index}"/>', '<c:out value="${rateExBean.msg}" />');"
								   onmouseout='hideMsgPopup();' >
								   <img src="images/exclamation.png" />
								</a>
						</c:if>
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endAff %>
						<% tmpProperty = rateExListItemArray + "endAff" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(4)%>"
							   onblur="checkPadLeft(this,'0',4);" />
					</td>
					<% //<nested:nest property="endProduct"> %>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endProduct.invCode %>
						<% tmpProperty = rateExListItemArray + "endProduct.invCode" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(1)%>" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endProduct.list %>
						<% tmpProperty = rateExListItemArray + "endProduct.list" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(6)%>"
							   onblur="checkPadLeft(this,'0',6);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endProduct.label %>
						<% tmpProperty = rateExListItemArray + "endProduct.label" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(3)%>"
							   onblur="checkPadLeft(this,'0',3);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endProduct.size %>
						<% tmpProperty = rateExListItemArray + "endProduct.size" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(3)%>"
							   onblur="checkPadLeft(this,'0',3);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endProduct.pack %>
						<% tmpProperty = rateExListItemArray + "endProduct.pack" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(4)%>"
							   onblur="checkPadLeft(this,'0',4);" />
					</td>
					<%//End Nesting EndProduct%>
					
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column rptAff %>
						<% tmpProperty = rateExListItemArray + "rptAff" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(4)%>"
						           onblur="checkPadLeft(this,'0',4);" />
					</td>
					<% //<nested:nest property="rptProduct"> %>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column rptProduct.invCode %>
						<% tmpProperty = rateExListItemArray + "rptProduct.invCode" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(1)%>" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column rptProduct.list %>
						<% tmpProperty = rateExListItemArray + "rptProduct.list" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(6)%>"
							   onblur="checkPadLeft(this,'0',6);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column rptProduct.label %>
						<% tmpProperty = rateExListItemArray + "rptProduct.label" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(3)%>"
							   onblur="checkPadLeft(this,'0',3);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column rptProduct.size %>
						<% tmpProperty = rateExListItemArray + "rptProduct.size" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(3)%>"
							   onblur="checkPadLeft(this,'0',3);" />
				 	</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column rptProduct.pack %>
						<% tmpProperty = rateExListItemArray + "rptProduct.pack" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
							   onchange="<%=onChangeCall%>"
						  	   onkeyup="<%=rateExForm.getAutoTab(4)%>"
							   onblur="checkPadLeft(this,'0',4);" />
					</td>
					<%//End Nesting Rpt Product%>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column selected %>
						<% tmpProperty = rateExListItemArray + "selected" ; %>
						<html:checkbox property="<%=tmpProperty%>" />
					</td>
					</abbott:row>
					
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">
					<%//Sridevi.K end ..%>
					
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supAff %>
						<% tmpProperty = rateExListItemArray + "supAff" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(4)%>"
							   onblur="checkPadLeft(this,'0',4);" />
					</td>
					<% //<nested:nest property="supProduct"> %>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supProduct.invCode %>
						<% tmpProperty = rateExListItemArray + "supProduct.invCode" ; %>						
						<hrml:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(1)%>" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supProduct.list %>
						<% tmpProperty = rateExListItemArray + "supProduct.list" ; %>	
						<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(6)%>"
							   onblur="checkPadLeft(this,'0',6);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supProduct.label %>
						<% tmpProperty = rateExListItemArray + "supProduct.label" ; %>	
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(3)%>"
							   onblur="checkPadLeft(this,'0',3);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supProduct.size %>
						<% tmpProperty = rateExListItemArray + "supProduct.size" ; %>	
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						   	   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(3)%>"
							   onblur="checkPadLeft(this,'0',3);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supProduct.pack %>
						<% tmpProperty = rateExListItemArray + "supProduct.pack" ; %>	
						<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(4)%>"
							   onblur="checkPadLeft(this,'0',4);" />
					</td>
					<%//End Nesting Sup Product%>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column begPeriod %>
						<% tmpProperty = rateExListItemArray + "begPeriod" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(2)%>"
							   onblur="checkPadLeft(this,'0',2);" />
					</td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column endPeriod %>
						<% tmpProperty = rateExListItemArray + "endPeriod" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
							   onchange="<%=onChangeCall%>"
							   onblur="checkPadLeft(this,'0',2);" />
					</td>
					<td colspan="6">&nbsp;</td>
					</abbott:row>
					
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">
					<%//Sridevi.K end ..%>
					
					<td class="mntCenter" colspan="2">
						<% //tmpProperty is initialized here as per the column bpfRate %>
						<% tmpProperty = rateExListItemArray + "bpfRate" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
							   onblur="alertLength(this,9);" 
							   onchange="<%=onChangeCall%>"
							   onkeyup="<%=rateExForm.getAutoTab(15)%>" />
					</td>
					<td class="mntCenter" colspan="2">
						<% //tmpProperty is initialized here as per the column costfRate %>
						<% tmpProperty = rateExListItemArray + "costfRate" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
							   onchange="<%=onChangeCall%>"
							   onblur="alertLength(this,9);" 
							   onkeyup="<%=rateExForm.getAutoTab(15)%>" />
					</td>
					<td class="mntCenter" colspan="2">
						<% //tmpProperty is initialized here as per the column bppRate %>
						<% tmpProperty = rateExListItemArray + "bppRate" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
							   onchange="<%=onChangeCall%>"
							   onblur="alertLength(this,9);" 
							   onkeyup="<%=rateExForm.getAutoTab(15)%>" />
					</td>						
					<td class="mntCenter" colspan="2">
						<% //tmpProperty is initialized here as per the column costpRate %>
						<% tmpProperty = rateExListItemArray + "costpRate" ; %>
						<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
							   onchange="<%=onChangeCall%>"
							   onblur="alertLength(this,9);" 
							   onkeyup="<%=rateExForm.getAutoTab(15)%>" />
					</td>
					<td colspan="6">&nbsp;</td>
					</abbott:row>
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" >
					<%//Sridevi.K end ..%>
					
					<td class="mntCenter">BPF<br>Rates</td>
					<td colspan="16">
						<table width="100%" cellspacing="0">
							<tr>
								<% // Using c:forEach for looping the bpPeriodValues starting form 0 to 5 %>
								<c:forEach items="${rateExBean.bpfRates}" 
									   begin="0"
									   end="5"
									   step="1"
									   var="bpPeriod1"
			                				   varStatus="bpPeriodStatus1">
									   <td class="mntRight" width="16%">
									      <c:out value="${bpPeriod1.period}"/>
									   </td>
								</c:forEach>

							</tr>
							<tr>
								<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
								<c:forEach  items="${rateExBean.bpfRates}"
										begin="6"
										end="11"
										step="1"
										var="bpPeriod2"
			               						varStatus="bpPeriodStatus2"> 
										<td class="mntRight">
											<c:out value="${bpPeriod2.period}"/>
										</td>
								</c:forEach>										
							</tr>
						</table>
					</td>
					</abbott:row>
					
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" >
					<%//Sridevi.K end ..%>
					
					<td class="mntCenter">
						BPP<br>Rates
					</td>
					<td colspan="16">
						<table width="100%" cellspacing="0">
							<tr>
								<% // Using c:forEach for looping the costPeriodValues starting form 0 to 5 %>
								<c:forEach  items="${rateExBean.bppRates}"
									    begin="0"
									    end="5"
									    step="1"
								 	    var="costPeriodBean1"
			                				    varStatus="costPeriodStatus1"> 
									    <td class="mntRight" width="16%">
										<c:out value="${costPeriodBean1.period}"/>
									    </td>
								</c:forEach>				
								
							</tr>
							<tr>

								<% // Using c:forEach for looping the costPeriodValues starting form 6 to 11 %>
								<c:forEach items="${rateExBean.bppRates}" 									
									   begin="6"
									   end="11"
									   step="1"
									   var="costPeriod2"
									   varStatus="costPeriodStatus2"> 
									   <td class="mntRight">
										<c:out value="${costPeriod2.period}"/>
									   </td>
								</c:forEach>										
							</tr>
						</table>
					</td>
					</abbott:row>
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">
					<%//Sridevi.K end ..%>
					
					<td class="mntCenter">
						Cost F<br>Rates
					</td>
					<td colspan="16">
						<table width="100%" cellspacing="0">
							<tr>
								<% // Using c:forEach for looping the bpPeriodValues starting form 0 to 5 %>
								<c:forEach items="${rateExBean.costfRates}" 
									    begin="0"
									    end="5"
									    step="1"
									    var="bpPeriod1"
			                				    varStatus="bpPeriodStatus1">
									    <td class="mntRight" width="16%">
										<c:out value="${bpPeriod1.period}"/>
									    </td>
								</c:forEach>								
							</tr>
							<tr>
								<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
								<c:forEach  items="${rateExBean.costfRates}"
									    begin="6"
									    end="11"
									    step="1"
									    var="bpPeriod2"
			               					    varStatus="bpPeriodStatus2"> 
									    <td class="mntRight">
										<c:out value="${bpPeriod2.period}"/>
									    </td>
								</c:forEach>								
							</tr>
						</table>
					</td>
					</abbott:row>
					
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">
					<%//Sridevi.K end ..%>
					
					<td class="mntCenter">
						Cost P<br>Rates
					</td>
					<td colspan="16">
						<table width="100%" cellspacing="0">
							<tr>								
								<% // Using c:forEach for looping the bpPeriodValues starting form 0 to 5 %>
								<c:forEach 	items="${rateExBean.costpRates}" 
										begin="0"
										end="5"
										step="1"
										var="bpPeriod1"
			                					varStatus="bpPeriodStatus1">
										<td class="mntRight" width="16%">
											<c:out value="${bpPeriod1.period}"/>
										</td>
								</c:forEach>				
							</tr>
							<tr>
								<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
								<c:forEach  items="${rateExBean.costpRates}"
									    begin="6"
									    end="11"
									    step="1"
									    var="bpPeriod2"
			               					    varStatus="bpPeriodStatus2"> 
									    <td class="mntRight">
										<c:out value="${bpPeriod2.period}"/>
									    </td>
								</c:forEach>								
							</tr>
						</table>
					</td>
					</abbott:row>
				</c:forEach>
				<%//Sridevi.K End of changes to replace the nested iterate tag with JSTL For-Each tag%>
			</nested:notEqual>
		</table>
		<nested:equal property="rateExListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=rateExForm.getFocusField()%>');
	<%//Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record and confirm the deletion of records
 	*/
 	
	function doDelete(form,cmd,action)
	{
		var rowSelected=false;
		if ( cmd == 'deleteall'){		
			rowSelected=true;
		}
		else {
			for(i = 0; i < rateExForm.rateExListSize.value; i++) {
				var element = "rateExListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < rateExForm.elements.length; j++) {
						if(rateExForm.elements[j].name == element){
							if(rateExForm.elements[j].checked == true ) {
								rowSelected=true;
							}
						}
					}
				}			
			}	
		}	
		if( rowSelected ) {				
			chgActCmdSubmit(form,cmd,action);		
		}
		else {	
			alert( 'You must select at least one row to delete' );
		}	
	}
	<%//Sridevi.K 7-16-05 End of script..%>					
	
	<%//Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < rateExForm.rateExListSize.value; i++) {
			var element = "rateExListItem[" + i + "].selected";				
			if(!rowSelected){
				for(j = 0; j < rateExForm.elements.length; j++) {
					if(rateExForm.elements[j].name == element){
						if(rateExForm.elements[j].checked == true ) {
							rowSelected=true;
						}
					}
				}
			}
		}			
		if( rowSelected ) {		
			chgActCmdSubmit(form,cmd,action);
		}
		else {	
			alert( 'You must select at least one row to SaveSelected' );
		}	
	}
	<%//Sridevi.K 7-16-05 End of script..%>	
		
	</script>
<%@ include file="/include/footer.jsf" %>