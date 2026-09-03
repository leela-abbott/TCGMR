
<%! String pageTitle = "BPC Exception Data"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpcExForm" scope="session" class="abbott.ai.tcgm.action.form.BpcExForm" />
<a name="FilterView"></a><body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %> 
	<nested:form method="post" name="bpcExForm" type="abbott.ai.tcgm.action.form.BpcExForm" action="/bpcExMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />		
		<nested:hidden property="rowToCopy" />
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
			<nested:hidden property="bpcExErrorListSize" />
			<nested:notEqual property="bpcExErrorListSize" value="0">
			 	<td rowspan="2">Errors</td>
			</nested:notEqual>
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
			
			<%//Sridevi.K code modified to fix the toggle between the order of the data in a row. %>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
    		<% String submitFilter = "submitFilter(document.bpcExForm,'filter', event);"; %>
			<tr class="oddRowCenter">
				<nested:hidden property="bpcExErrorListSize" />
				<nested:notEqual property="bpcExErrorListSize" value="0">
					<td>
						<input type=checkbox name=errs value="on" onClick="javascript:changeCmdAndSubmit(document.bpcExForm,'filter');">
					</td>
				</nested:notEqual>
			<% //Sridevi.K changed the existing code to fix to toggled between the ascending and descending order of the data %>
			
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
					<td>
						<nested:text property="endAff" maxlength="4" size="4" styleClass="fltrWidth4"
							         onchange="makeFilterDirty('pagingDiv','red','bold');"
     								 onkeydown = "<%=submitFilter%>"
							         onkeyup="return autoTab(this, 4, event);" 
							         onblur="checkPadLeft(this,'0',4);" />
					</td>
					<nested:nest property="endProduct">
						<td>
							<nested:text property="invCode" maxlength="1" size="1" styleClass="fltrWidth1"
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
								         onkeydown = "<%=submitFilter%>" />
					   </td>
					</nested:nest>
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
				</tr>
			</nested:nest>
			<tr class="fltrTblHdng">
				<td rowspan="2">Supp<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Frz<br>Cost</td>
				<td rowspan="2">BP<br>Cur<br>Cd</td>
				<td rowspan="2">Cost<br>Cur<br>Cd</td>
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
			<nested:nest property="searchObject">
				<tr class="oddRowCenter">
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
						<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
							         onchange="makeFilterDirty('pagingDiv','red','bold');"
							         onkeydown = "<%=submitFilter%>"
							         onkeyup="return autoTab(this, 1, event);" />
					</td>
					<td>
						<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
							         onchange="makeFilterDirty('pagingDiv','red','bold');"
							         onkeydown = "<%=submitFilter%>"
							         onkeyup="return autoTab(this, 5, event);" />
					</td>
					<td>
						<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
							         onchange="makeFilterDirty('pagingDiv','red','bold');"
							         onkeydown = "<%=submitFilter%>"
							         onkeyup="return autoTab(this, 5, event);" />
					</td>
					<td colspan="3" class="bgWhiteRight">
						<a href="javascript:changeCmdAndSubmit(document.bpcExForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.bpcExForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.bpcExForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest>

		</table>
		<hr />

		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Cd</td>
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
			<% String submitSave = "submitSave(document.bpcExForm,'save','bpcExSave.do', event);"; 
			if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.bpcExForm,'save','bpcExSave.do', event);";
				}else{
					submitSave = "";
				}
			%>
			<nested:nest property="addNew">
				<nested:hidden property="bpcEx.modelId" />
				<nested:hidden property="bpcEx.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:notEqual property="bpcEx.msg" value="">
							<a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="bpcEx.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>
						</nested:notEqual>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
           				    onkeydown = "<%=submitSave%>"
							onkeyup="<%=bpcExForm.getAutoTab(1)%>" />
					</td>
					<nested:nest property="bpcEx">
						<td>
							<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="<%=bpcExForm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
					 	</td>
					  	<nested:nest property="endProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(6)%>"
									         onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(3)%>"
									         onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(3)%>"
									         onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(4)%>"
									         onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								         onchange="makeAddNewDirty();"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="<%=bpcExForm.getAutoTab(4)%>"
								         onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(6)%>"
									         onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(3)%>"
									         onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(3)%>"
									         onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(4)%>"
									         onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
					</nested:nest>
				</tr>
			</nested:nest>
			<tr class="fltrTblHdng">
				<td rowspan="2">Supp<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Frz<br>Cost</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
				<td rowspan="2" colspan="1">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Cd</td>
				<td rowspan="2" colspan="1"><br>Cost</td>
				<td rowspan="2">Cost<br>Cur<br>Cd</td>				
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Cd</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<nested:nest property="addNew">
				<nested:hidden property="bpcEx.modelId" />
				<nested:hidden property="bpcEx.datasetTableId" />			
				<nested:nest property="bpcEx">
					<tr class="oddRowCenter">					
					   <td>
						<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								     onchange="makeAddNewDirty();"
    								 onkeydown = "<%=submitSave%>"
								     onkeyup="<%=bpcExForm.getAutoTab(4)%>"
								     onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(6)%>"
									         onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(3)%>"
									         onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(3)%>"
									         onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									         onchange="makeAddNewDirty();"
									         onkeydown = "<%=submitSave%>"
									         onkeyup="<%=bpcExForm.getAutoTab(4)%>"
									         onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
								         onchange="makeAddNewDirty();"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="<%=bpcExForm.getAutoTab(1)%>" />
						</td>
						<td>
						<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
							         onchange="makeAddNewDirty();"
							         onkeydown = "<%=submitSave%>"
							         onkeyup="<%=bpcExForm.getAutoTab(2)%>"
							         onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td>
						<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
							         onchange="makeAddNewDirty();"
							         onkeydown = "<%=submitSave%>"
							         onkeyup="<%=bpcExForm.getAutoTab(2)%>"
							         onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td colspan="1">
						<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
							         onchange="makeAddNewDirty();"
							         onkeydown = "<%=submitSave%>"
							         onblur="alertLength(this,10);" 
							         onkeyup="<%=bpcExForm.getAutoTab(15)%>" />
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								         onchange="makeAddNewDirty();"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="<%=bpcExForm.getAutoTab(5)%>" />
						</td>
						<td colspan="1">
							<nested:text property="costPrice" maxlength="15" styleClass="fltrWidth10"
								         onchange="makeAddNewDirty();"
								         onkeydown = "<%=submitSave%>"
								         onblur="alertLength(this,10);" 
								         onkeyup="<%=bpcExForm.getAutoTab(15)%>" />
						</td>
						 <td>
							<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
								         onchange="makeAddNewDirty();"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="<%=bpcExForm.getAutoTab(5)%>" />
						</td>						
					</tr>
				</nested:nest>
			</nested:nest>
			<tr class="oddRowCenter">			
			<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>		
				<td class="bgWhiteRight" colspan="15">
					<a href="javascript:chgActCmdSubmit(document.bpcExForm,'save','bpcExSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.bpcExForm,'massupdate','bpcExSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.bpcExForm,'clearaddnew','bpcExMaint.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
 				</td>
 				<%}%>
			</tr>			
		</table>

		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/bpcExPaging.jsf" %></div>

		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2"></td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_END_AFF)%>" >
						End<br>Aff<br>
						<%=bpcExForm.dspSort(DBConst.COL_END_AFF)%>
					</a>
				</td>
				<td colspan="5">
					End Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_RPT_AFF)%>" >
						Rpt<br>Aff<br>
						<%=bpcExForm.dspSort(DBConst.COL_RPT_AFF)%>
					</a>
				</td>
				<td colspan="5">
					Rpt Prod
				</td>
				<td rowspan="2" valign="middle">
				
				<% //Sridevi.Kalidindi: Changed to select and deselect all the rows %>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('bpcExListItem','selected','<%=bpcExForm.getBpcExListSize()%>');" />
				<% //Sridevi.K end of code change for select and deselect all the rows %>
					
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_END_INV_CD)%>" >
						Inv Cd<br>
						<%=bpcExForm.dspSort(DBConst.COL_END_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_END_LIST)%>" >
						List<br>
						<%=bpcExForm.dspSort(DBConst.COL_END_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_END_LABEL)%>" >
						Label<br>
						<%=bpcExForm.dspSort(DBConst.COL_END_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_END_SIZE)%>" >
						Size<br>
						<%=bpcExForm.dspSort(DBConst.COL_END_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_END_PACK)%>" >
						Pack<br>
						<%=bpcExForm.dspSort(DBConst.COL_END_PACK)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_RPT_INV_CD)%>" >
						Inv Cd<br>
						<%=bpcExForm.dspSort(DBConst.COL_RPT_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_RPT_LIST)%>" >
						List<br>
						<%=bpcExForm.dspSort(DBConst.COL_RPT_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_RPT_LABEL)%>" >
						Label<br>
						<%=bpcExForm.dspSort(DBConst.COL_RPT_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_RPT_SIZE)%>" >
						Size<br>
						<%=bpcExForm.dspSort(DBConst.COL_RPT_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_RPT_PACK)%>" >
						Pack<br>
						<%=bpcExForm.dspSort(DBConst.COL_RPT_PACK)%>
					</a>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_SUP_AFF)%>" >
						Supp<br>Aff<br>
						<%=bpcExForm.dspSort(DBConst.COL_SUP_AFF)%>
					</a>
				</td>
				<td colspan="5">
					Sup Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_FREEZE_COST)%>" >
						Frz<br>Cost<br>
						<%=bpcExForm.dspSort(DBConst.COL_FREEZE_COST)%>
					</a>
				</td>
				<td rowspan="2">
					Beg<br>Period<br>
				</td>
				<td rowspan="2">
					End<br>Period<br>
				</td>
				<td rowspan="2" colspan="1">
					Bill<br>Price<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_BP_CUR_CD)%>" >
						BP Cur<br>Code<br>
						<%=bpcExForm.dspSort(DBConst.COL_BP_CUR_CD)%>
					</a>
				</td>
				<td rowspan="2" colspan="1">
					<br>Cost<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_COST_CUR_CD)%>" >
						Cost<br>Cur Cd<br>
						<%=bpcExForm.dspSort(DBConst.COL_COST_CUR_CD)%>
					</a>
				</td>
				<td rowspan="2" colspan="1">&nbsp;
					
				</td>				
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_SUP_INV_CD)%>" >
						Inv Cd<br>
						<%=bpcExForm.dspSort(DBConst.COL_SUP_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_SUP_LIST)%>" >
						List<br>
						<%=bpcExForm.dspSort(DBConst.COL_SUP_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_SUP_LABEL)%>" >
						Label<br>
						<%=bpcExForm.dspSort(DBConst.COL_SUP_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_SUP_SIZE)%>" >
						Size<br>
						<%=bpcExForm.dspSort(DBConst.COL_SUP_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcExForm.getSrtHref(DBConst.COL_SUP_PACK)%>" >
						Pack<br>
						<%=bpcExForm.dspSort(DBConst.COL_SUP_PACK)%>
					</a>
				</td>		
			</tr>
			<!-- End Headings for Row Data Subf -->
		</table>
		
		<table width="780" cellspacing="0">	
		
			<nested:hidden property="bpcExListSize" />
			<nested:notEqual property="bpcExListSize" value="0">
			<%//Sridevi.K Code modified to replace the nested iterate tag %>
			<% int rowNumber = 0; %>				
			<%// used the JSTL c:forEach tag to loop through bpcExList %>
				<c:forEach items="${sessionScope.bpcExForm.bpcExList}"
			               var="bpcExBean"
			               varStatus="bpcExStatus">

			<% //declare a String to notify when there is a change %>					                			                
			<% String onChangeCall = "makeEditDirty('" + "bpcExListItem[" + rowNumber + "].selected" + "');"; %>

			<% //define the common part of the property tag of html in another string %>
		    <% String bpcExListItemArray = "bpcExListItem[" + rowNumber +"]."; %>

			<%//String href encapsulates the call to a JavaScript copyRow %>
			<% String href = "javascript:copyRow(document.bpcExForm,'" + rowNumber++ + "','bpcExMaint.do');"; %>

			<% //tmpProperty is given a null to set its values compatible to the property%>
			<% String tmpProperty = "" ; %>
			
			  <% String tmpEndAffl = "endAffl"+rowNumber ; %>
			  <% String tmpEndAfflInvCode = "endAfflInvCode"+rowNumber ; %> 
			  <% String tmpEndAfflList = "endAfflList"+rowNumber ; %> 
			  <% String tmpEndAfflLabel = "endAfflLabel"+rowNumber ; %> 
			   <% String tmpEndAfflSize = "endAfflSize"+rowNumber ; %> 
			    <% String tmpEndAfflPack = "endAfflPack"+rowNumber ; %> 
				
		<!--Report Aff-->		
				<% String tmpRptAff = "rptAff"+rowNumber ; %>
			  <% String tmpRptInvCode = "rptInvCode"+rowNumber ; %> 
			  <% String tmpRptList = "rptList"+rowNumber ; %> 
			  <% String tmpRptLabel = "rptLabel"+rowNumber ; %> 
			   <% String tmpRptSize = "rptSize"+rowNumber ; %> 
			    <% String tmpRptPack = "rptPack"+rowNumber ; %> 
				
				<!--Report Aff End-->	
				
				<!--Supp  Aff-->	
			  <% String tmpSptAff = "sptAff"+rowNumber ; %>
			  <% String tmpSptInvCode = "sptInvCode"+rowNumber ; %> 
			  <% String tmpSptList = "sptList"+rowNumber ; %> 
			  <% String tmpSptLabel = "sptLabel"+rowNumber ; %> 
			   <% String tmpSptSize = "sptSize"+rowNumber ; %> 
			    <% String tmpSptPack = "sptPack"+rowNumber ; %> 
			  
			  
			
			
			
				<% String tmpRevTp = "RevTp"+rowNumber ; %>
					
					<% String tmpRpt = "Rpt"+rowNumber ; %>
					
					<% String tmpSupAff = "SupAff"+rowNumber ; %>
					
					<% String tmpInvCode = "invCode"+rowNumber ; %> 
					
					<% String tmpList = "list"+rowNumber ; %>
					
					<% String tmpLabel = "label"+rowNumber ; %>
					
					<% String tmpSize = "size"+rowNumber ; %>
					
					<% String tmpPack = "pack"+rowNumber ; %>
					
					<% String tmpBillPrice = "billPrce"+rowNumber ; %>
					
					<% String tmpCurCode = "curCode"+rowNumber ; %>
					
					<% String tmpFrzCost = "frzCost"+rowNumber ; %>
					
					<% String tmpSlChk = "slChk"+rowNumber ; %>
					
					<% String tmpCstPrce = "CstPr"+rowNumber ; %>
					
					<% String tmpCstCur = "cstCur"+rowNumber ; %>
					
					<% String tmpBegPd = "begPd"+rowNumber ; %>
					
					<% String tmpEndPd = "endPd"+rowNumber ; %>

			<% //Sridevi.K code added to the abbott custom tag to work without the custom tag %>
			<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
			<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">
			<%//Sridevi.K end ..%>
			
			<td width="80" colspan="1" class="mntCenter">
			<% //Sridevi.K, Code added to print the line numbers %>
			<c:out value="${sessionScope.bpcExForm.pagingFilter.startRecord + bpcExStatus.index}"/>
			<% //Sridevi.K end of code added to print the line numbers%>
			
			<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
				<a href="<%=href%>">
					<img src="images/btnUpArrow.png" alt="Load Row" />				</a>
				<% //Using 'c:if' to check if the bpcExBean msg is not equal to "  " %>
				
			<c:if test="${bpcExBean.msg ne ''}" >
			<%	out.println("<a class=\"error\"");
			    out.println(" href=\"#\"");
				out.print(" id=\"anchor");%><c:out value="${bpcExStatus.index}"/><% out.println("\" ");%>  
			<%out.print(" name=\"anchor");%><c:out value="${bpcExStatus.index}"/><% out.println("\" ");%>  
			<%out.println(" onclick=\"return false;\"");%>
	        <%out.print(" onmouseover=\"showMsgPopup('anchor");%><c:out value="${bpcExStatus.index}"/><%out.print("' ");%>
		    <%out.print(" , '");%><c:out value="${bpcExBean.msg}"/> <% out.println("');\"");%>
			<%out.println(" onmouseout=\"hideMsgPopup();\" >");
			out.println(" <img src=\"images/exclamation.png\" />");
			out.println("  </a>");	%>																	
			  </c:if>			</td>
			<td width="40" class="mntCenter">
				
					
				<% //tmpProperty is initialized here as per the column endAff %>
				<% tmpProperty = bpcExListItemArray + "endAff" ; %>	
				
				 											
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />	 	</td>							
			<td width="77" class="mntCenter">
				<% //tmpProperty is initialized here as per the column endProduct.invCode %>
				<% tmpProperty = bpcExListItemArray + "endProduct.invCode" ; %>
				
				
								
				<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" /> 	</td>
			<td width="64" class="mntCenter">
				<% //tmpProperty is initialized here as per the column endProduct.list %>
				<% tmpProperty = bpcExListItemArray + "endProduct.list" ; %>
				
				
								
				<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
			   			   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(6)%>"
						   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />	 </td>
			<td width="56" class="mntCenter">
				<% //tmpProperty is initialized here as per the column endProduct.label %>
				<% tmpProperty = bpcExListItemArray + "endProduct.label" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />  </td>
			<td width="48" class="mntCenter">
				<% //tmpProperty is initialized here as per the column endProduct.size %>
				<% tmpProperty = bpcExListItemArray + "endProduct.size" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />	</td>
			<td width="67" class="mntCenter">
				<% //tmpProperty is initialized here as per the column endProduct.pack %>
				<% tmpProperty = bpcExListItemArray + "endProduct.pack" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />  </td>
			<%//End Nesting End Product%>
							
			<td width="63" class="mntCenter">
				<% //tmpProperty is initialized here as per the column rptAff %>
				<% tmpProperty = bpcExListItemArray + "rptAff" ; %>
				
				
								
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />		</td>
			<td width="43" class="mntCenter">
				<% //tmpProperty is initialized here as per the column rptProduct.invCode %>
				<% tmpProperty = bpcExListItemArray + "rptProduct.invCode" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />		</td>
			<td width="64" class="mntCenter">
				<div align="right">
				  <% //tmpProperty is initialized here as per the column rptProduct.list %>
				    <% tmpProperty = bpcExListItemArray + "rptProduct.list" ; %>
					
					
		      <html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(6)%>"
						   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />			  </div></td>
			<td width="32" class="mntCenter">
				<div align="right">
				  <% //tmpProperty is initialized here as per the column rptProduct.label %>
				    <% tmpProperty = bpcExListItemArray + "rptProduct.label" ; %>
					
		      <html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						   onchange="<%=onChangeCall%>"
			        	   onkeyup="<%=bpcExForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />			  </div></td>
			<td width="51" class="mntCenter">
				<div align="right">
				  <% //tmpProperty is initialized here as per the column rptProduct.size %>
				    <% tmpProperty = bpcExListItemArray + "rptProduct.size" ; %>
					
		      <html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" /> 	  </div></td>
		   <td width="29" class="mntCenter">
				<% //tmpProperty is initialized here as per the column rptProduct.pack %>
				<% tmpProperty = bpcExListItemArray + "rptProduct.pack" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						   onchange="<%=onChangeCall%>"
		 				    onkeyup="<%=bpcExForm.getAutoTab(4)%>"
							onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />		  </td>
		  <%//End Nesting RptProduct%>

		  <td width="36" class="mntCenter">
				<% //tmpProperty is initialized here as per the column selected %>
				<% tmpProperty = bpcExListItemArray + "selected" ; %>
				
			  <html:checkbox property="<%=tmpProperty%>" />	</td>
		</abbott:row>
		
		<% //Sridevi.K code modified to fit the abbott custom tag %>
		<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" >
		<%//Sridevi.K end..%>
		
		<td class="mntCenter"><div align="left">
		  <% //tmpProperty is initialized here as per the column supAff %>
		      <% tmpProperty = bpcExListItemArray + "supAff" ; %>
			
		  <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
							onchange="<%=onChangeCall%>"
							onkeyup="<%=bpcExForm.getAutoTab(4)%>"
							onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" /> 	
		  </div></td>					
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column supProduct.invCode %>
				<% tmpProperty = bpcExListItemArray + "supProduct.invCode" ; %>
				
				 
				<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" /> 	</td>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column supProduct.list%>
				<% tmpProperty = bpcExListItemArray + "supProduct.list" ; %>
				
								
				<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(6)%>"
							onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />		</td>
			<td class="mntCenter"> 
				<% //tmpProperty is initialized here as per the column supProduct.label%>
					<% tmpProperty = bpcExListItemArray + "supProduct.label" ; %>
				
								
			  <html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />	
			</td>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column supProduct.size%>
				<% tmpProperty = bpcExListItemArray + "supProduct.size" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />			</td>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column supProduct.pack%>
				<% tmpProperty = bpcExListItemArray + "supProduct.pack" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />		</td>
			<%//End Nesting Sup Product%>

			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column freezeCost%>
				<% tmpProperty = bpcExListItemArray + "freezeCost" ; %>
				
				
				<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpcExForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />		</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column begPeriod%>
							<% tmpProperty = bpcExListItemArray + "begPeriod" ; %>
							
						
								
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcExForm.getAutoTab(2)%>"
									   onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />				</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column endPeriod%>
							<% tmpProperty = bpcExListItemArray + "endPeriod" ; %>
						
								
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcExForm.getAutoTab(2)%>"
									   onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />					</td>
						<td class="mntCenter" colspan="1">
							<% //tmpProperty is initialized here as per the column billPrice%>
							
							<% tmpProperty = bpcExListItemArray + "billPrice" ; %>
							
								
							
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
									   onchange="<%=onChangeCall%>"
									   onblur="alertLength(this,10);" 
									   onkeyup="<%=bpcExForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />				</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpCurCode%>
							<% tmpProperty = bpcExListItemArray + "bpCurCode" ; %>
							
							
								
							<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcExForm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />					</td>
						<td class="mntCenter" colspan="1">
							<% //tmpProperty is initialized here as per the column costPrice%>
							<% tmpProperty = bpcExListItemArray + "costPrice" ; %>
							
						
								
							 <html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
								        onchange="<%=onChangeCall%>"
								        onblur="alertLength(this,10);" 
								        onkeyup="<%=bpcExForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />							</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column costCurCode%>
							<% tmpProperty = bpcExListItemArray + "costCurCode" ; %>
							
						
							<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcExForm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />		</td>
							<td rowspan="2" colspan="1">&nbsp;							</td>														
					</abbott:row>
					
					<% //Sridevi.K code modified for the abbott custom tag without nested iterate tag %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" >
					<% //Sridevi.K End of code modification %>
					
						<td class="mntCenter">
							Bill 1-6<br>Price 7-12						</td>
						<td colspan="16" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<% // Using c:forEach for looping the bpPeriodValues starting form 0 to 5 %>
									<c:forEach 	items="${bpcExBean.bpPeriodValues}" 
											begin="0"
											end="5"
											step="1"
											var="bpPeriod1"
			                				varStatus="bpPeriodStatus1">
											<td class="mntRight" width="16%">
											<c:out value="${bpPeriod1.period}"/>										</td>
									</c:forEach>
								</tr>
								<tr>
									<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
									<c:forEach  items="${bpcExBean.bpPeriodValues}"
											begin="6"
											end="11"
											step="1"
											var="bpPeriod2"
			                				varStatus="bpPeriodStatus2"> 
											<td class="mntRight">
												<c:out value="${bpPeriod2.period}"/>											</td>
									</c:forEach>									
								</tr>
							</table>						</td>
					</abbott:row>
					
					<%// Sridevi.K the abbott custom tag modified to work with out the nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" >
					<%// Sridevi.K end of abbott custom tag modification %>
					
						<td class="mntCenter">
							1-6<br>Cost 7-12						</td>
						<td colspan="16" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<% // Using c:forEach for looping the costPeriodValues starting form 0 to 5 %>
									<c:forEach  items="${bpcExBean.costPeriodValues}"
										begin="0"
										end="5"
										step="1"
									 	var="costPeriodBean1"
			                			varStatus="costPeriodStatus1"> 
										<td class="mntRight" width="16%">

											<c:out value="${costPeriodBean1.period}"/>										</td>
									</c:forEach>									
								</tr>
								<tr>
									<% // Using c:forEach for looping the costPeriodValues starting form 6 to 11 %>
									<c:forEach  items="${bpcExBean.costPeriodValues}" 									
										begin="6"
										end="11"
										step="1"
										var="costPeriod2"
										varStatus="costPeriodStatus2"> 
										<td class="mntRight">
											<c:out value="${costPeriod2.period}"/>										</td>
									</c:forEach>																		
								</tr>
							</table>						</td>
					<% //End the abbott custom tag %>
					</abbott:row>

				<%// End of the iterations of the bpcExList%>
				</c:forEach>
				<%// Sridevi.K, end the modification of the code to replace the nested iterate tag with JSTL tags%>
							
			<%// End - Subf Data Portion for Original flds	%>
						<div name="navigation" id="navigation" class="hidden">
							<%@ include file="/include/bpcExPaging.jsf" %>
						</div>
			</nested:notEqual>
		</table>
		<nested:equal property="bpcExListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=bpcExForm.getFocusField()%>');			
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
			for(i = 0; i < bpcExForm.bpcExListSize.value; i++) {
				var element = "bpcExListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcExForm.elements.length; j++) {
						if(bpcExForm.elements[j].name == element){
							if(bpcExForm.elements[j].checked == true ) {
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
	
	<% //Sridevi.K 7-17-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < bpcExForm.bpcExListSize.value; i++) {
				var element = "bpcExListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcExForm.elements.length; j++) {
						if(bpcExForm.elements[j].name == element){
							if(bpcExForm.elements[j].checked == true ) {
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
	<%//Sridevi.K 7-17-05 End of script..%>					
	
 </script>
<%@ include file="/include/footer.jsf" %>