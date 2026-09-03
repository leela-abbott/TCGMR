	<a name="FilterView"></a>
<%! String pageTitle = "Rate Exception Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="rateExTranForm" scope="session" class="abbott.ai.tcgm.action.form.RateExTranForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="rateExTranForm" type="abbott.ai.tcgm.action.form.RateExTranForm" action="/rateExTranMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		
		<%//Sridevi.K code modified to fix to toggle between the order of the data%>
		<nested:hidden property="sortObject.sortColumn" />
		<nested:hidden property="sortObject.sortOrder" />
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
			<nested:nest property="searchObject">
				<nested:hidden property="rateEx.modelId" />
				<nested:hidden property="rateEx.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=rateExTranForm.getFltrChng()%>"
							onkeyup="<%=rateExTranForm.getAutoTab(1)%>"/>
					</td>
					<nested:nest property="rateEx">
						<td>
							<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onkeyup="<%=rateExTranForm.getAutoTab(4)%>" />
						</td>
						<nested:nest property="endProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(6)%>" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(4)%>" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onkeyup="<%=rateExTranForm.getAutoTab(4)%>" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(6)%>" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(4)%>" />
							</td>
						</nested:nest><%//End Nesting Rpt Product%>
					</nested:nest><%//End Nesting RateEx%>
				</tr>
			</nested:nest><%//End Nesting SearchObject%>
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
			<nested:nest property="searchObject">
				<tr class="oddRowCenter">
					<nested:nest property="rateEx">
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onkeyup="<%=rateExTranForm.getAutoTab(4)%>" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(6)%>" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=rateExTranForm.getFltrChng()%>"
									onkeyup="<%=rateExTranForm.getAutoTab(4)%>" />
							</td>
						</nested:nest>
					<td>
						<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
							onchange="<%=rateExTranForm.getFltrChng()%>"
							onkeyup="<%=rateExTranForm.getAutoTab(2)%>" />
					</td>
					<td>
						<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
							onchange="<%=rateExTranForm.getFltrChng()%>"
							onkeyup="<%=rateExTranForm.getAutoTab(2)%>" />
					</td>
					</nested:nest><!-- End rateEx -->
				</tr>
			</nested:nest><!-- End searchObject -->				
			<tr class="fltrTblHdng">
				<td colspan="2">BP<br>Factor</td>
				<td colspan="2">Cost<br>Factor</td>
				<td colspan="2">BP<br>Plan</td>					
				<td colspan="2">Cost<br>Plan</td>
				<td>Pub<br>Flag</td>
				<td>User<br>Id</td>
				<td class="bgWhiteRight" colspan="3">&nbsp;</td>
			</tr>
			<nested:nest property="searchObject">
				<tr class="oddRowCenter">
					<nested:nest property="rateEx">
						<td colspan="2">
							<nested:text property="bpfRate" maxlength="15" styleClass="fltrWidth15"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>
						<td colspan="2">
							<nested:text property="costfRate" maxlength="15" styleClass="fltrWidth15"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>					
						<td colspan="2">
							<nested:text property="bppRate" maxlength="15" styleClass="fltrWidth15"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>
						<td colspan="2">										
							<nested:text property="costpRate" maxlength="15" styleClass="fltrWidth15"
								onchange="<%=rateExTranForm.getFltrChng()%>"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>
					</nested:nest><!-- End rateEx -->										
					<td>
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=rateExTranForm.getFltrChng()%>" />
					</td>
					<td >
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. Start
							-->
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="<%=rateExTranForm.getFltrChng()%>" >
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. End
							-->
					</td>					
					<td class="bgWhiteRight" colspan="3">
						<a href="javascript:changeCmdAndSubmit(document.rateExTranForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.rateExTranForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest><!-- End searchObject -->				
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
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" value="ASC" />
			<nested:nest property="addNew">
				<nested:hidden property="rateEx.modelId" />
				<nested:hidden property="rateEx.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<%=rateExTranForm.dspAddNewMsg()%>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
							onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
					</td>
					<nested:nest property="rateEx">
						<td>
							<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="endProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest><!-- End End Product -->
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest><%//End Nesting Rpt Product%>
					</nested:nest><!-- End rateEx -->
				</tr>
			</nested:nest><!-- End addNew -->
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
				<tr class="oddRowCenter">
					<nested:nest property="rateEx">
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest><!-- End Sup Product -->
					<td>
						<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
							onchange="makeAddNewDirty();"
							onkeyup="<%=rateExTranForm.getAutoTab(2)%>"
							onblur="checkPadLeft(this,'0',2);" />
					</td>
					<td>
						<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
							onchange="makeAddNewDirty();"
							onkeyup="<%=rateExTranForm.getAutoTab(2)%>"
							onblur="checkPadLeft(this,'0',2);" />
					</td>
					</nested:nest><!-- End rateEx -->					
				</tr>
			</nested:nest><!-- End addNew -->				
			<tr class="fltrTblHdng">
				<td colspan="2">BP<br>Factor</td>
				<td colspan="2">Cost<br>Factor</td>					
				<td colspan="2">BP<br>Plan</td>					
				<td colspan="2">Cost<br>Plan</td>
				<td class="bgWhiteRight" colspan="5">&nbsp;</td>
			</tr>
			<tr class="oddRowCenter">
				<nested:nest property="addNew">
					<tr class="oddRowCenter">
					<nested:nest property="rateEx">				
						<td colspan="2">
							<nested:text property="bpfRate" maxlength="15" styleClass="fltrWidth15"
								onchange="makeAddNewDirty();"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>
						<td colspan="2">
							<nested:text property="costfRate" maxlength="15" styleClass="fltrWidth15"
								onchange="makeAddNewDirty();"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>
						<td colspan="2">
							<nested:text property="bppRate" maxlength="15" styleClass="fltrWidth15"
								onchange="makeAddNewDirty();"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>					
						<td colspan="2">
							<nested:text property="costpRate" maxlength="15" styleClass="fltrWidth15"
								onchange="makeAddNewDirty();"
								onblur="alertLength(this,9);"
								onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
						</td>
						<td class="bgWhiteRight" colspan="5">
							<a href="<%=rateExTranForm.getAddBtnHref()%>" >
								<img src="images/btnAdd.png" alt="Add" /></a>
							<a href="<%=rateExTranForm.getMassBtnHref()%>" >
								<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
							<a href="<%=rateExTranForm.getClrBtnHref()%>" >
								<img src="images/btnClear.png" alt="Clear"/></a>
						</td>
					</tr>
			</nested:nest><!-- End rateEx -->								
			</nested:nest><!-- End addNew -->			
		</table>

		<hr />
		
<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/rateExTranPaging.jsf" %></div>

		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_ACD)%>" >
						Act<br>Code<br>
						<%=rateExTranForm.dspSort(DBConst.COL_ACD)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_END_AFF)%>" >
						End<br>Aff<br>
						<%=rateExTranForm.dspSort(DBConst.COL_END_AFF)%>
					</a>
				</td>
				<td colspan="5">
					End Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_RPT_AFF)%>" >
						Rpt<br>Aff<br>
						<%=rateExTranForm.dspSort(DBConst.COL_RPT_AFF)%>
					</a>
				</td>
				<td colspan="5">
					Rpt Prod
				</td>
				<td rowspan="2" valign="middle">
				<%//Sridevi.K fixed to toggle between the selectall and deselect the rows%>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('rateExTranListItem','rateEx.selected','<%=rateExTranForm.getRateExTranListSize()%>');" />
					<%//Sridevi.K end..%>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_END_INV_CD)%>" >
						Inv<br>Cd<br>
						<%=rateExTranForm.dspSort(DBConst.COL_END_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_END_LIST)%>" >
						List<br>
						<%=rateExTranForm.dspSort(DBConst.COL_END_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_END_LABEL)%>" >
						Label<br>
						<%=rateExTranForm.dspSort(DBConst.COL_END_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_END_SIZE)%>" >
						Size<br>
						<%=rateExTranForm.dspSort(DBConst.COL_END_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_END_PACK)%>" >
						Pack<br>
						<%=rateExTranForm.dspSort(DBConst.COL_END_PACK)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_RPT_INV_CD)%>" >
						Inv<br>Cd<br>
						<%=rateExTranForm.dspSort(DBConst.COL_RPT_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_RPT_LIST)%>" >
						List<br>
						<%=rateExTranForm.dspSort(DBConst.COL_RPT_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_RPT_LABEL)%>" >
						Label<br>
						<%=rateExTranForm.dspSort(DBConst.COL_RPT_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_RPT_SIZE)%>" >
						Size<br>
						<%=rateExTranForm.dspSort(DBConst.COL_RPT_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_RPT_PACK)%>" >
						Pack<br>
						<%=rateExTranForm.dspSort(DBConst.COL_RPT_PACK)%>
					</a>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_SUP_AFF)%>" >
						Sup<br>Aff<br>
						<%=rateExTranForm.dspSort(DBConst.COL_SUP_AFF)%>
					</a>
				</td>
				<td colspan="5">
					Sup Prod
				</td>
				<td rowspan="2">
					Beg<br>Period<br>
				</td>
				<td rowspan="2">
					End<br>Period<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_PUBLISH_FLAG)%>" >
						Pub<br>Flag<br>
						<%=rateExTranForm.dspSort(DBConst.COL_PUBLISH_FLAG)%>
					</a>
				</td>
				<td rowspan="2" colspan="5">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_SUP_INV_CD)%>" >
						Inv<br>Cd<br>
						<%=rateExTranForm.dspSort(DBConst.COL_SUP_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_SUP_LIST)%>" >
						List<br>
						<%=rateExTranForm.dspSort(DBConst.COL_SUP_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_SUP_LABEL)%>" >
						Label<br>
						<%=rateExTranForm.dspSort(DBConst.COL_SUP_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_SUP_SIZE)%>" >
						Size<br>
						<%=rateExTranForm.dspSort(DBConst.COL_SUP_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=rateExTranForm.getSrtHref(DBConst.COL_SUP_PACK)%>" >
						Pack<br>
						<%=rateExTranForm.dspSort(DBConst.COL_SUP_PACK)%>
					</a>
				</td>
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
			<nested:hidden property="rateExTranListSize" />
			<nested:notEqual property="rateExTranListSize" value="0">
			
			<%//Sridevi.K code added to replace the nested iterate tag with the JSTL tags %>
			<% int rowNumber = 0; %>
				<% //<nested:iterate property="rateExTranList" type="abbott.ai.tcgm.entities.RateExTran" indexId="idx"> %>

				<%// used the JSTL c:forEach tag to loop through rateExTranList %>
				<c:forEach items="${sessionScope.rateExTranForm.rateExTranList}"
			                   var="rateExTranBean"
				           varStatus="rateExTranStatus">

				<% // define the common part of the property tag of html in another string %>	
				<% String rateExTranListItemArray = "rateExTranListItem[" + rowNumber +"]."; %>  							
				               	     
           	    <% // declare a String to notify when there is a change %>			
				<% String onChangeCall = "makeEditDirty('" + "rateExTranListItem[" + rowNumber++ + "].rateEx.selected" + "');"; %>				
				
				<% // tmpProperty is given a null to set its values compatible to the property%>
				<% String tmpProperty = "" ; %>  				               	     				
				
				<%//Sridevi.K Abbott tag is modified to work without the nested iterate tag %>
				<% // abbott is a custom tag to give some coloring effect to the alternate rows %>				
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
				<%//Sridevi.K End..%>

				<td colspan="1" class="mntCenter">
				
					<% //Sridevi.K code added to print the record numbers %>
					<c:out value="${sessionScope.rateExTranForm.pagingFilter.startRecord + rateExTranStatus.index}"/>
					<% //Sridevi.K end of code..%>
			
					<% // Using 'c:if' to check if the rateExTranBean.rateEx.msg is not equal to "  " %>
					<c:if test="${rateExTranBean.rateEx.msg ne ''}" >
					   <a class="error"
						  href="#"
						  id="anchor<c:out value="${rateExTranStatus.rateEx.index}"/>"
						  name="anchor<c:out value="${rateExTranStatus.rateEx.index}"/>"
						  onclick="return false;"
						  onmouseover="showMsgPopup('anchor<c:out value="${rateExTranStatus.rateEx.index}"/>', '<c:out value="${rateExTranBean.rateEx.msg}" />');"
						  onmouseout='hideMsgPopup();' >
						  <img src="images/exclamation.png" />
					  </a>
				   </c:if>	
				   
					<% //tmpProperty is initialized here as per the column actionCode %>
					<% tmpProperty = rateExTranListItemArray + "actionCode" ; %>			
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="return autoTab(this, 1, event);" />   							
				</td>
				<% //<nested:nest property="rateEx"> %>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endAff %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endAff" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" />
				</td>
				<% //<nested:nest property="endProduct"> %>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endProduct.invCode %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endProduct.invCode" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endProduct.list %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endProduct.list" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(6)%>"
						   onblur="checkPadLeft(this,'0',6);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endProduct.label %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endProduct.label" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endProduct.size %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endProduct.size" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endProduct.pack %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endProduct.pack" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" />
				</td>
				<%//End Nesting EndProduct%>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.rptAff %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.rptAff" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" />
				</td>
				<% //<nested:nest property="rptProduct"> %>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.rptProduct.invCode %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.rptProduct.invCode" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.rptProduct.list %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.rptProduct.list" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(6)%>"
						   onblur="checkPadLeft(this,'0',6);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.rptProduct.label %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.rptProduct.label" ; %>
				       <html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						  onchange="<%=onChangeCall%>"
						  onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
						  onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.rptProduct.size %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.rptProduct.size" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.rptProduct.pack %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.rptProduct.pack" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" />
				</td>
					<%//End Nesting Rpt Product%>
					<%//End Nesting RateEx%> 
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.selected %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.selected" ; %>
						<html:checkbox property="<%=tmpProperty%>" />
				</td>
				</abbott:row>
				<%//Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
				<%//Sridevi.K end..%>
				<% //<nested:nest property="rateEx"> %>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.supAff %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.supAff" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
				                   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" />
				</td>
				<% //<nested:nest property="supProduct"> %>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.supProduct.invCode %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.supProduct.invCode" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(1)%>" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.supProduct.list %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.supProduct.list" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
					           onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(6)%>"
						   onblur="checkPadLeft(this,'0',6);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.supProduct.label %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.supProduct.label" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
					           onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.supProduct.size %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.supProduct.size" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.supProduct.pack %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.supProduct.pack" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
				   		   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(4)%>"
						   onblur="checkPadLeft(this,'0',4);" />
				</td>
				<%//End Nesting Sup Product%>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.begPeriod %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.begPeriod" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
					 	   onchange="<%=onChangeCall%>"
						   onkeyup="<%=rateExTranForm.getAutoTab(2)%>"
						   onblur="checkPadLeft(this,'0',2);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rateEx.endPeriod %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.endPeriod" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
				 		   onchange="<%=onChangeCall%>"
						   onblur="checkPadLeft(this,'0',2);" />
				</td>
				<%//End Nesting rateEx%>						
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column publishFlag %>
					<% tmpProperty = rateExTranListItemArray + "publishFlag" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1" disabled="true"/>
				</td>
				<td colspan="5">&nbsp;</td>
				</abbott:row>

				<%//Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
				<%//Sridevi.K End..%>
				
				<%// <nested:nest property="rateEx"> %>
				<td colspan="2">
					<% //tmpProperty is initialized here as per the column rateEx.bpfRate %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.bpfRate" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
						   onchange="<%=onChangeCall%>"
						   onblur="alertLength(this,9);"
						   onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
				</td>
				<td colspan="2">
					<% //tmpProperty is initialized here as per the column rateEx.costfRate %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.costfRate" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
				 		   onchange="<%=onChangeCall%>"
				 		   onblur="alertLength(this,9);"
						   onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
				</td>
				<td colspan="2">
					<% //tmpProperty is initialized here as per the column rateEx.bppRate %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.bppRate" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
				  		   onchange="<%=onChangeCall%>"
				  		   onblur="alertLength(this,9);"
						   onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
				</td>						
				<td colspan="2">
					<% //tmpProperty is initialized here as per the column rateEx.costpRate %>
					<% tmpProperty = rateExTranListItemArray + "rateEx.costpRate" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
						   onchange="<%=onChangeCall%>"
						   onblur="alertLength(this,9);"
						   onkeyup="<%=rateExTranForm.getAutoTab(15)%>" />
				</td>
				<td colspan="6">&nbsp;</td>
				<%//End Nesting rateEx%>						
				</abbott:row>
				<%//Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
				<%//Sridevi.K end.%>
				<td class="mntCenter">
					BPF<br>Rates
				</td>
				<td colspan="16">
					<table width="100%" cellspacing="0">
					<tr>																	
						<% //Using c:forEach for looping the bpfRates starting form 0 to 5 %>								     
						<c:forEach items="${rateExTranBean.rateEx.bpfRates}" 
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
						<% // Using c:forEach for looping the bpfRates starting form 6 to 11 %>
						<c:forEach items="${rateExTranBean.rateEx.bpfRates}"
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
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
				<td class="mntCenter">Cost F<br>Rates</td>
				<td colspan="16">
					<table width="100%" cellspacing="0">
						<tr>
							<% // Using c:forEach for looping the costfRates starting form 0 to 5 %>
							<c:forEach items="${rateExTranBean.rateEx.costfRates}"
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
							<% // Using c:forEach for looping the costfRates starting form 6 to 11 %>
							<c:forEach items="${rateExTranBean.rateEx.costfRates}" 									
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
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
				<td class="mntCenter">BPP<br>Rates
				</td>
				<td colspan="16">
					<table width="100%" cellspacing="0">
						<tr>
							<% // Using c:forEach for looping the bppRates starting form 0 to 5 %>
							<c:forEach items="${rateExTranBean.rateEx.bppRates}" 
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
							<% // Using c:forEach for looping the bppRates starting form 6 to 11 %>
							<c:forEach items="${rateExTranBean.rateEx.bppRates}"
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
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
					<td class="mntCenter">Cost P<br>Rates</td>
					<td colspan="16">
						<table width="100%" cellspacing="0">
							<tr>
								<% // Using c:forEach for looping the rateEx.costpRates starting form 0 to 5 %>
								<c:forEach items="${rateExTranBean.rateEx.costpRates}" 
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
								<% // Using c:forEach for looping the rateEx.costpRates starting form 6 to 11 %>
								<c:forEach  items="${rateExTranBean.rateEx.costpRates}"
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
		</nested:notEqual>
	</table>
	<nested:equal property="rateExTranListSize" value="0">
		<%@ include file="/include/recordsNotFound.jsf" %>
	</nested:equal>
	<hr />
	</nested:form>
	
	<%//Sridevi.K Script added to alert the user if he clicks rowselected without selecting the any rows%>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=rateExTranForm.getFocusField()%>');
			/**
		*
		*/
		function checkCopy(selectedModel,form,cmd,action) 
		{
			if(selectedModel == 'none')	{	
				alert('You must select a model to copy');
			} 
			else {	
				var rowSelected=false;
				if ( cmd == 'copyall'){		
					rowSelected=true;
				}
				else {
					for(i = 0; i < rateExTranForm.rateExTranListSize.value; i++) {			
						var element = "rateExTranListItem[" + i + "].rateEx.selected";				
						if(!rowSelected){				
							for(j = 0; j < rateExTranForm.elements.length; j++) {
								if(rateExTranForm.elements[j].name == element){
									if(rateExTranForm.elements[j].checked == true ) {							
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

		<%//Sridevi.K end of Script to alert the user if copy selected is clicked without selecting a row%>
		
		<%//Sridevi.K added script to alert the user to select atleast one row to publish%>	
    /**
    	* Prompt the user to select atleast one records
    	*/  	
		function checkPublish(form,cmd,action) {

			var rowSelected=false;
			if ( cmd == 'publishall'){		
				rowSelected=true;
			}
			else {
				for(i = 0; i < rateExTranForm.rateExTranListSize.value; i++) {			
					var element = "rateExTranListItem[" + i + "].rateEx.selected";				
					if(!rowSelected){				
						for(j = 0; j < rateExTranForm.elements.length; j++) {
							if(rateExTranForm.elements[j].name == element){
								if(rateExTranForm.elements[j].checked == true ) {							
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
	
		<%//Sridevi.K added script to alert the user if delete selected is clicked without selecting any row%>	

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
				for(i = 0; i < rateExTranForm.rateExTranListSize.value; i++) {
					var element = "rateExTranListItem[" + i + "].rateEx.selected";				
					if(!rowSelected){
						for(j = 0; j < rateExTranForm.elements.length; j++) {
							if(rateExTranForm.elements[j].name == element){
								if(rateExTranForm.elements[j].checked == true ) {
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
	<%//Sridevi.K end ..%>
</script>
<%@ include file="/include/footer.jsf" %>