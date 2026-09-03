<a name="FilterView"></a>
<%! String pageTitle = "ASR Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="asrTranForm" scope="session" class="abbott.ai.tcgm.action.form.AsrTranForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
		<nested:form method="post" name="asrTranForm" type="abbott.ai.tcgm.action.form.AsrTranForm" action="/asrTranMaintenance.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<%//Begin code for filter row%>
		<table width="780" cellspacing="0" >
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Prod<br>Orig</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Supp<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Sup Prod</td>
				<td rowspan="2">Usage<br>Factor</td>				
				<td rowspan="2">Sup<br>Key</td>
				<td rowspan="2">Pub<br>Flag</td>
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
			
			<%//Sridevi.K code modified to fix to toggle between the order of the data in a row%>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" />
			<%//Sridevi.K end..%>
			<% String submitFilter = "submitFilter(document.asrTranForm,'filter', event);"; %>
			<nested:nest property="searchObject">
				<nested:nest property="asr">
					<nested:hidden property="modelId" />
					<nested:hidden property="datasetTableId" />
				</nested:nest>
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							         onchange="makeFilterDirty('pagingDiv','red','bold');"
							         onkeydown = "<%=submitFilter%>"
							         onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="asr">
						<td>
							<nested:text property="productOrigin" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeFilterDirty('pagingDiv','red','bold'); "
										 onkeydown = "<%=submitFilter%>"
										 onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeydown = "<%=submitFilter%>"
										 onkeyup="return autoTab(this, 4, event);" 
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 6, event);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 3, event);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 3, event);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 4, event);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeydown = "<%=submitFilter%>"
										 onkeyup="return autoTab(this, 4, event);" 
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 6, event);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 3, event);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 3, event);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitFilter%>"
											 onkeyup="return autoTab(this, 4, event);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="usage" maxlength="16" styleClass="fltrWidth16"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onblur="alertLength(this,10);"
 										 onkeydown = "<%=submitFilter%>"
										 onkeyup="return autoTab(this, 16, event);"/>
										  
						</td>
						
						<td>
							<nested:text property="supKey" maxlength="1" styleClass="fltrWidth1"
										 onkeydown = "<%=submitFilter%>"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
 										 onkeyup="return autoTab(this, 1, event);" />
						</td>
					</nested:nest>
					<td>
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onkeydown = "<%=submitFilter%>" />
					</td>
				</tr>
				<tr class="fltrTblHdng">
					<td colspan="2">User<br>Id</td>
					<td colspan="15" class="bgWhiteRight">&nbsp;</td>
				</tr>
				<tr class="oddRowCenter">
					<td colspan="2">
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. Start
							-->
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="makeFilterDirty('pagingDiv','red','bold');" >
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. End
							-->
					</td>
					<td colspan="15" class="bgWhiteRight">
						<a href="javascript:changeCmdAndSubmit(document.asrTranForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.asrTranForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.asrTranForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter"/></a>
					</td>
				</tr>
			</nested:nest>
		</table>
		<hr />
		<%//Begin code for add new row%>
		<table width="780" cellspacing="0" >
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Prod<br>Orig</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Supp<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Sup Prod</td>
				<td rowspan="2">Usage<br>Factor</td>
				<td rowspan="2">Sup<br>Key</td>
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
			<nested:nest property="addNew">
				<nested:nest property="asr">
					<nested:hidden property="modelId" />
					<nested:hidden property="datasetTableId" />
				</nested:nest>
				<% String submitAdd = "submitAdd(document.asrTranForm,'add','asrTranSave.do', event);";
					if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
						submitAdd = "submitAdd(document.asrTranForm,'add','asrTranSave.do', event);";
					}else{
						submitAdd = "";
					}
				 %>
				<tr class="oddRowCenter">
					<td>
						<nested:notEqual property="asr.msg" value="">
							<a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="asr.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>
						</nested:notEqual>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
     					    onkeydown = "<%=submitAdd%>"							
							onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="asr">
						<td>
							<nested:text property="productOrigin" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 6, event);"
											 onblur="checkPadLeft(this,'0',6);"/>
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 4, event);"
											 onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 6, event);"
											 onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 4, event);"
											 onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="usage" maxlength="16" styleClass="fltrWidth16"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										  onblur="alertLength(this,10);"
										 onkeyup="return autoTab(this, 16, event);" />
						</td>
						<td>
							<nested:text property="supKey" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>" />
						</td>
					</tr>
				</nested:nest>
			</nested:nest>
			<tr>
			<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
				<td colspan="16" class="right">
					<a href="javascript:chgActCmdSubmit(document.asrTranForm,'add','asrTranSave.do');" >
						<img src="images/btnAdd.png" alt="Add" /></a>
					<a href="javascript:chgActCmdSubmit(document.asrTranForm,'massupdate','asrTranSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.asrTranForm,'clearaddnew','asrTranMaintenance.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
				<%}%>
			</tr>
		</table>
		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/asrTranPaging.jsf" %></div>

		<table width="780" cellspacing="0" >
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_ACD%>');" >
						Act<br>Code<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_ACD%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_PROD_ORIGIN%>');" >
						Prod<br>Orig<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_PROD_ORIGIN%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_INV_CD%>');" >
						Inv<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_INV_CD%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">
					Rpt Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Supp<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_AFF%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_INV_CD%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">
					Sup Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_USAGE_FAC%>');" >
						Usage<br>Factor<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_USAGE_FAC%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_KEY%>');" >
						Sup<br>Key<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_KEY%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');" >
						Pub<br>Flag<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_PUBLISH_FLAG%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				
				<%//Sridevi.K Code changed to toggled between select and deselect all the rows %>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('asrTranListItem','asr.selected','<%=asrTranForm.getAsrTranListSize()%>');" />
				<%//Sridevi.K Code changes ends%>
				
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LIST%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LABEL%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_SIZE%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_PACK%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LIST%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LABEL%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_SIZE%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_PACK%>" >
							<img alt="<%=asrTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
			</tr>
			<nested:hidden property="asrTranListSize" />
			<nested:notEqual property="asrTranListSize" value="0">
			
			<%//Sridevi.K Code changed to replace the nested:iterate tag of Struts with JSTL tag c:for-each %>
				<% int rowNumber = 0; %>

				<%// used the JSTL c:forEach tag to loop through asrTranList %>
				<c:forEach items="${sessionScope.asrTranForm.asrTranList}"
						   var="asrTranBean"
						   varStatus="asrTranStatus">
							
					<% // define the common part of the property tag of html in another string %>
					<% String asrTranListItemArray = "asrTranListItem[" + rowNumber +"]."; %>

					<% //declare a String to notify when there is a change %>
					<% String onChangeCall = "makeEditDirty('" + "asrTranListItem[" + rowNumber++ + "].asr.selected" + "');"; %>

					<% // tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>
					
					<%//Sridevi.K Abbott custom tag is changed to work properly without the nested iterate tag%>					
					<% // abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" id="mntRow" rowNum="<%=rowNumber%>" >
					<%//Sridevi.K End of code changes for Abbott tag%>
					
						<td class="mntCenter">
						
							<% //Sridevi.K Added code to print the line numbers %>
							<c:out value="${sessionScope.asrTranForm.pagingFilter.startRecord + asrTranStatus.index}"/>
							<%//Sridevi.K End of code to print the line numbers%>
							
							<% // Using 'c:if' to check if the asrTranBean.asr msg is not equal to "  " %>
							<c:if test="${asrTranBean.asr.msg ne ''}" >
								<a class="error"
								   href="#"
								   id="anchor<c:out value="${asrTranStatus.index}"/>"
								   name="anchor<c:out value="${asrTranStatus.index}"/>"
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<c:out value="${asrTranStatus.index}"/>', '<c:out value="${asrTranBean.asr.msg}"/>');"
								   onmouseout='hideMsgPopup();' >
								   <img src="images/exclamation.png" />
								</a>
							</c:if>
							
								<% //tmpProperty is initialized here as per the column actionCode %>
								<% tmpProperty = asrTranListItemArray + "actionCode" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
										   onchange="<%=onChangeCall%>"
										   onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column productOrigin %>
							<% tmpProperty = asrTranListItemArray + "asr.productOrigin" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);"
									   onblur="checkPadLeft(this,'0',1);" onkeydown="restrSpace(event);" />
						</td>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column rptAff %>
							<% tmpProperty = asrTranListItemArray + "asr.rptAff" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column invCode %>
							<% tmpProperty = asrTranListItemArray + "asr.rptProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);"
									   onblur="checkPadLeft(this,'0',1);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column list %>
							<% tmpProperty = asrTranListItemArray + "asr.rptProduct.list" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column label %>
							<% tmpProperty = asrTranListItemArray + "asr.rptProduct.label" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
								 	   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column size %>
							<% tmpProperty = asrTranListItemArray + "asr.rptProduct.size" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column pack %>
							<% tmpProperty = asrTranListItemArray + "asr.rptProduct.pack" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supAff %>
							<% tmpProperty = asrTranListItemArray + "asr.supAff" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column invCode %>
							<% tmpProperty = asrTranListItemArray + "asr.supProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column list %>
							<% tmpProperty = asrTranListItemArray + "asr.supProduct.list" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column label %>
							<% tmpProperty = asrTranListItemArray + "asr.supProduct.label" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column size %>
							<% tmpProperty = asrTranListItemArray + "asr.supProduct.size" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column pack %>
							<% tmpProperty = asrTranListItemArray + "asr.supProduct.pack" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column usage %>
							<% tmpProperty = asrTranListItemArray + "asr.usage" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="16" styleClass="mntWidth16"
									   onchange="<%=onChangeCall%>"
									   onblur="alertLength(this,10);"
									   onkeyup="return autoTab(this, 16, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supKey %>
							<% tmpProperty = asrTranListItemArray + "asr.supKey" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>" onkeydown="restrSpace(event);" />
						</td>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supKey %>
							<% tmpProperty = asrTranListItemArray + "publishFlag" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1" disabled="true" />
						</td>
						
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column selected %>
							<% tmpProperty = asrTranListItemArray + "asr.selected" ; %>
							<html:checkbox property="<%=tmpProperty%>" />
						</td>
					</abbott:row>
				</c:forEach>
				<%//Sridevi.K Code modified to replace the nested iterate tag with JSTL for-each tag%>
					<div name="navigation" id="navigation" class="hidden">
						<%@ include file="/include/asrTranBtmPaging.jsf" %>
					</div>				
			</nested:notEqual>
		</table>
		<nested:equal property="asrTranListSize" value="0">
			<p class="recordsNotFound">
				No Records Found Matching Filter Criteria
			</p>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=asrTranForm.getFocusField()%>');
	/**
	*
	*/
  <%//Sridevi.K Script added to alert the user if he clicks copySelected without selecting any row%>
  
  /**
  * Prompt the user to select atleast one records
  */
	function checkCopy(selectedModel,form,cmd,action) {

		if(selectedModel == 'none')	{
	
			alert('You must select a model to copy');
		} 
		else {
	
			var rowSelected=false;
			if ( cmd == 'copyall'){		
				rowSelected=true;
			}
			else {
				for(i = 0; i < asrTranForm.asrTranListSize.value; i++) {
					var element = "asrTranListItem[" + i + "].asr.selected";				
					if(!rowSelected){
						for(j = 0; j < asrTranForm.elements.length; j++) {
							if(asrTranForm.elements[j].name == element){

								if(asrTranForm.elements[j].checked == true ) {
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
				alert( 'You must select at least one row to copy' );
			}	
		}
	}
	
  <%//Sridevi.K added script to alert the user to select atleast one row to publish%>	
  /**
  * Prompt the user to select atleast one records
  */  	
	function checkPublish(form,cmd,action) {

			var rowSelected=false;
			if ( cmd == 'publishall' || cmd == 'unpublishall'){		
				rowSelected=true;
			}
			else {
				for(i = 0; i < asrTranForm.asrTranListSize.value; i++) {			
					var element = "asrTranListItem[" + i + "].asr.selected";				
					if(!rowSelected){				
						for(j = 0; j < asrTranForm.elements.length; j++) {
							if(asrTranForm.elements[j].name == element){
								if(asrTranForm.elements[j].checked == true ) {							
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
				alert( 'You must select at least one row to publish' );
			}	
	}		
	<%//Sridevi.K end of Script to alert the user if he clicks publish selected without selecting any rows%>
  
/**
 * Prompt the user to select atleast one record and confirm the deletion of records
 */
function confirmDelete(form,cmd,action)
{
	var rowSelected=false;
	if ( cmd == 'deleteall'){		
			rowSelected=true;
	}
	else {
		for(i = 0; i < asrTranForm.asrTranListSize.value; i++) {
			var element = "asrTranListItem[" + i + "].asr.selected";				
			if(!rowSelected){
				for(j = 0; j < asrTranForm.elements.length; j++) {
					if(asrTranForm.elements[j].name == element){
						if(asrTranForm.elements[j].checked == true ) {
							rowSelected=true;
						}
					}
				}
			}			
		}	
	}
	if( rowSelected ) {	
	
		if (confirm('Records will be permanently deleted?'))
		{
			chgActCmdSubmit(form,cmd,action)
		}		
	}
	else {	
		alert( 'You must select at least one row to delete' );
	}	
}			
function restrSpace(event){

     if(event.keyCode == 32){
		event.returnValue = false;
		return false;
		}
	else{
return true;
	}
        
}
  </script>
  <%//Sridevi.K end of script added to alert the user if he clicks copyselected without selecting any row%>
<%@ include file="/include/footer.jsf" %>