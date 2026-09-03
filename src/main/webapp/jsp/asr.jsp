<a name="FilterView"></a>
<%! String pageTitle = "ASR Data"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="asrForm" scope="session" class="abbott.ai.tcgm.action.form.AsrForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>	
	<nested:form method="post" name="asrForm" type="abbott.ai.tcgm.action.form.AsrForm" action="/asrMaintenance.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />

		<% // Begin the view for the Filter rows from here! %>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
			<nested:hidden property="asrErrorListSize" />
			<nested:notEqual property="asrErrorListSize" value="0">
			 	<td rowspan="2">Errors</td>
			</nested:notEqual>
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
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
			<% String submitFilter = "submitFilter(document.asrForm,'filter', event);"; %>
			<tr class="oddRowCenter">
				<nested:hidden property="asrErrorListSize" />
				<nested:notEqual property="asrErrorListSize" value="0">
					<td>
						<input type=checkbox name=errs value="on" onClick="javascript:changeCmdAndSubmit(document.asrForm,'filter');">
					</td>
				</nested:notEqual>
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
				
								
					<td>
						<nested:text property="productOrigin" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
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
							<nested:text property="usage" maxlength="16" styleClass="fltrWidth15"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onblur="alertLength(this,10);"
										 onkeydown = "<%=submitFilter%>"
										 onkeyup="return autoTab(this, 16, event);" />
						</td>
						<td>
							<nested:text property="supKey" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeydown = "<%=submitFilter%>" />
						</td>
				</tr>
				<tr class="evenRowCenter">
					<td colspan="15" class="right">
						<a href="javascript:changeCmdAndSubmit(document.asrForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.asrForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.asrForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest>
		</table>
		<hr />
		<% // The view for Filter rows end here! %>
		
		
	<%// veiw for Update starts from here %>
	<a name="AddMassUpdateView"></a>
		<%//Begin code for add new row%>
		<table width="780" cellspacing="0">
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
				<nested:hidden property="asr.modelId" />
				<nested:hidden property="asr.datasetTableId" />
				<tr class="oddRowCenter">
				<% 
				String submitSave = "submitSave(document.asrForm,'save','asrSave.do', event);";
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.asrForm,'save','asrSave.do', event);";
				}else{
					submitSave = "";
				}
				
				 %>
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
									 onkeydown = "<%=submitSave%>"
									 onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="asr">
						<td>
							<nested:text property="productOrigin" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeAddNewDirty();"
	 									 onkeydown = "<%=submitSave%>"
										 onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitSave%>"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>	
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 6, event);"
											 onblur="checkPadLeft(this,'0',6);"/>
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 4, event);"
											 onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitSave%>"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 6, event);"
											 onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 3, event);"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitSave%>"
											 onkeyup="return autoTab(this, 4, event);"
											 onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="usage" maxlength="16" styleClass="fltrWidth16"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitSave%>"
										 onblur="alertLength(this,10);"
										 onkeyup="return autoTab(this, 16, event);" />
						</td>
						<td>
							<nested:text property="supKey" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeAddNewDirty();" 
										 onkeydown = "<%=submitSave%>"/>
						</td>
					</tr>
				</nested:nest>
			</nested:nest>
			<tr>
			<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
				<td colspan="16" class="right">
					<a href="javascript:chgActCmdSubmit(document.asrForm,'save','asrSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.asrForm,'massupdate','asrSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.asrForm,'clearaddnew','asrMaintenance.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
				<%
					}
				%>
			</tr>
		</table>
		<hr />
		<%// veiw for Mass Update ends here %>

		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/asrPaging.jsf" %>
		</div>
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_PROD_ORIGIN%>');" >
						Prod<br>Orig<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_PROD_ORIGIN%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_INV_CD%>');" >
						Inv<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_INV_CD%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">
					Rpt Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Supp<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_AFF%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_INV_CD%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">
					Sup Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_USAGE_FAC%>');" >
						Usage<br>Factor<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_USAGE_FAC%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_KEY%>');" >
						Sup<br>Key<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_KEY%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('asrListItem','selected','<%=asrForm.getAsrListSize()%>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LIST%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td> 	
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LABEL%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_SIZE%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_PACK%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LIST%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LABEL%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_SIZE%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_PACK%>" >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
			</tr>	
				
			<nested:hidden property="asrListSize" />			
			<nested:notEqual property="asrListSize" value="0">				
				<% int rowNumber=0; %>	
				<%// used the JSTL c:forEach tag to loop through asrList %>
				<c:forEach items="${sessionScope.asrForm.asrList}"
			               var="asrBean"
			               varStatus="asrStatus">

					<% // declare a String to notify when there is a change %>					                			                
			        <% String onChangeCall = "makeEditDirty('" + "asrListItem[" + rowNumber + "].selected" + "');"; %>

					<% // define the common part of the property tag of html in another string %>
		        	<% String asrListItemArray = "asrListItem[" + rowNumber +"]."; %>
					
					<%// String href encapsulates the call to a JavaScript copyRow %>
					<% String href = "javascript:copyRow(document.asrForm,'" + rowNumber++ + "','asrMaintenance.do');"; %>										
					
					<% // tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>
					
					<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
					<% // abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
					<%//Sridevi.K end ..%>
															
						<td class="mntCenter">
						
							<% //Sridevi.K code added to print the line numbers %>
							<c:out value="${sessionScope.asrForm.pagingFilter.startRecord + asrStatus.index}"/>
							<% //Sridevi.K End of code to print the line numbers %>
							
							<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
							<a href="<%=href%>" >
								<img src="images/btnUpArrow.png" alt="Load Row" />
							</a>
							
						</td>
						
						<td class="mntCenter">
							<% // Using 'c:if' to check if the asrBean msg is not equal to "  " %>
						  	<c:if test="${asrBean.msg ne ''}" >
								<a class="error"
									href="#"
									id="anchor<c:out value="${asrStatus.index}"/>"
									name="anchor<c:out value="${asrStatus.index}"/>"
									onclick="return false;"
									onmouseover="showMsgPopup('anchor<c:out value="${asrStatus.index}"/>', '<c:out value="${asrBean.msg}"/>');"
									onmouseout='hideMsgPopup();' >
									<img src="images/exclamation.png" />
								</a>
							</c:if>
								<% //tmpProperty is initialized here as per the column productOrigin %>
								<% tmpProperty = asrListItemArray + "productOrigin" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
										   onchange="<%=onChangeCall%>"
										   onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column rptAff %>
								<% tmpProperty = asrListItemArray + "rptAff" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
										   onchange="<%=onChangeCall%>"
										   onkeyup="return autoTab(this, 4, event);"
										   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />		
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column invCode %>
							<% tmpProperty = asrListItemArray + "rptProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column list %>
							<% tmpProperty = asrListItemArray + "rptProduct.list" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column label %>
							<% tmpProperty = asrListItemArray + "rptProduct.label" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column size %>
								<% tmpProperty = asrListItemArray + "rptProduct.size" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
										   onchange="<%=onChangeCall%>"
										   onkeyup="return autoTab(this, 3, event);"
										   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column pack %>
							<% tmpProperty = asrListItemArray + "rptProduct.pack" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supAff %>
							<% tmpProperty = asrListItemArray + "supAff" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<%//tmpProperty is initialized here as per the column invCode of supProduct%>
							<% tmpProperty = asrListItemArray + "supProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column list of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.list" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column label of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.label" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column size of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.size" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />		
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column pack of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.pack" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
								       onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />	
						</td>						
						<td class="mntLeft">
							<% //tmpProperty is initialized here as per the column usage %>
							<% tmpProperty = asrListItemArray + "usage" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="16" styleClass="mntWidth16"
									   onchange="<%=onChangeCall%>"
									   onblur="alertLength(this,10);" 
									   onkeyup="return autoTab(this, 16, event);" onkeydown="restrSpace(event);" />										
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supKey %>
							<% tmpProperty = asrListItemArray + "supKey" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						 		       onchange="<%=onChangeCall%>" onkeydown="restrSpace(event);" />	
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column selected %>
							<% tmpProperty = asrListItemArray + "selected"; %>
							<html:checkbox name="asrForm" property="<%=tmpProperty%>"/>
						</td>					
					</abbott:row>
				</c:forEach>
						<div name="navigation" id="navigation" class="hidden">
							<%@ include file="/include/asrPaging.jsf" %>
						</div>
			</nested:notEqual>
		</table>
		<nested:equal property="asrListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=asrForm.getFocusField()%>');
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
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
			for(i = 0; i < asrForm.asrListSize.value; i++) {
				var element = "asrListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < asrForm.elements.length; j++) {
						if(asrForm.elements[j].name == element){
							if(asrForm.elements[j].checked == true ) {
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
	
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < asrForm.asrListSize.value; i++) {
			var element = "asrListItem[" + i + "].selected";				
			if(!rowSelected){
				for(j = 0; j < asrForm.elements.length; j++) {
					if(asrForm.elements[j].name == element){
						if(asrForm.elements[j].checked == true ) {
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
function restrSpace(event){

     if(event.keyCode == 32){
		event.returnValue = false;
		return false;
		}
	else{
return true;
	}
        
}
	<%//Sridevi.K 7-16-05 End of script..%>	
		
	</script>
	<%@ include file="/include/footer.jsf" %>
