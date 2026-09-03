<a name="FilterView"></a>
<%! String pageTitle = "BPC Exception Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpXTrnFrm" scope="session" class="abbott.ai.tcgm.action.form.BpcExTranForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="bpXTrnFrm" type="abbott.ai.tcgm.action.form.BpcExTranForm" action="/bpcExTranMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Cd</td>
				<td rowspan="2">End<br>Aff</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td colspan="5">Rpt Prod</td>
				<td colspan="2"></td>
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
				<td colspan="2"></td>
			</tr>
			
			<%//Sridevi.K code modified to toggle between the ascending and descending order of the data%>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" />
			<% String submitFilter = "submitFilter(document.bpXTrnFrm,'filter', event);"; %>			
			<nested:nest property="searchObject">
			<%//Sridevi.K end..%>
			
				<nested:hidden property="bpcEx.modelId" />
				<nested:hidden property="bpcEx.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpXTrnFrm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>"/>
					</td>
					<nested:nest property="bpcEx">
						<td>
							<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
								onchange="<%=bpXTrnFrm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>" 
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="endProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="<%=bpXTrnFrm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>" 
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>" />
							</td>
							<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</nested:nest>
					</nested:nest><%//End Nesting BpcEx%>
				</tr>
			</nested:nest><%//End Nesting SearchObject%>
			<tr class="fltrTblHdng">
				<td rowspan="2">Supp<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Frz<br>Cost</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Cd</td>
				<td rowspan="2"><br>Cost</td>
				<td rowspan="2">Cost<br>Cur<br>Cd</td>
				<td rowspan="2">Pub<br>Flag</td>
				<td rowspan="2">User<br>Id</td>				
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
					<nested:nest property="bpcEx">
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="<%=bpXTrnFrm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>" 
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=bpXTrnFrm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>" />
							</td>
						</nested:nest><%//End Nesting Sup Product%>
						<td>
							<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
								onchange="<%=bpXTrnFrm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
						</td>
						<td>
						   <nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
							  onchange="<%=bpXTrnFrm.getFltrChng()%>"
							  onkeydown = "<%=submitFilter%>"
							  onkeyup="<%=bpXTrnFrm.getAutoTab(2)%>" />
						</td>
						<td>
						   <nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
							   onchange="<%=bpXTrnFrm.getFltrChng()%>"
							   onkeydown = "<%=submitFilter%>"
							   onkeyup="<%=bpXTrnFrm.getAutoTab(2)%>" />
						</td>
						<td colspan="1">
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
							   onchange="<%=bpXTrnFrm.getFltrChng()%>" 
							   onkeydown = "<%=submitFilter%>"
							   onblur="alertLength(this,10);"  
							   onkeyup="<%=bpXTrnFrm.getAutoTab(15)%>" />
						</td>
						<td>
						   <nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="<%=bpXTrnFrm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(5)%>" />
						</td>	
						 <td colspan="1">
						   <nested:text property="costPrice" maxlength="15" styleClass="fltrWidth10"
							   onchange="<%=bpXTrnFrm.getFltrChng()%>"
							   onkeydown = "<%=submitFilter%>"
							   onblur="alertLength(this,10);"  
							   onkeyup="<%=bpXTrnFrm.getAutoTab(15)%>" />
						</td>
						<td>
						   <nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
							   onchange="<%=bpXTrnFrm.getFltrChng()%>"
							   onkeydown = "<%=submitFilter%>"
						 	  onkeyup="<%=bpXTrnFrm.getAutoTab(5)%>" />
						</td>
					</nested:nest>
					<td>
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpXTrnFrm.getFltrChng()%>" 
							onkeydown = "<%=submitFilter%>" />
					</td>
					<td colspan="1">
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. Start
							-->
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="<%=bpXTrnFrm.getFltrChng()%>">
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. End
							-->
					</td>							
				</nested:nest>
				</tr>
				<tr class="oddRowCenter">
					<td class="bgWhiteRight" colspan="16">
						<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>							
						<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
		</table>

		<hr />

		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Cd</td>
				<td rowspan="2">End<br>Aff</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td colspan="5">Rpt Prod</td>
				<td colspan="2"></td>
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
				<td colspan="2"></td>
			</tr>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" value="ASC" />
			<% String submitAdd = "submitAdd(document.bpXTrnFrm,'add','bpcExTranSave.do', event);"; 
			if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
						submitAdd = "submitAdd(document.bpXTrnFrm,'add','bpcExTranSave.do', event);";
					}else{
						submitAdd = "";
					}
			%>
			<nested:nest property="addNew">
				<nested:hidden property="bpcEx.modelId" />
				<nested:hidden property="bpcEx.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<%=bpXTrnFrm.dspAddNewMsg()%>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
							onkeydown = "<%=submitAdd%>"
							onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
					</td>
					<nested:nest property="bpcEx">
						<td>
							<nested:text property="endAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="endProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
							<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Cd</td>
				<td rowspan="2"><br>Cost</td>
				<td rowspan="2">Cost<br>Cur<br>Cd</td>
				<td rowspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>								
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
					<nested:nest property="bpcEx">
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest><%//End Nesting Sup Product%>
						<td>
							<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" />
						</td>
						<td>
						   <nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(2)%>"
								onblur="checkPadLeft(this,'0',2);" />
					   </td>
					   <td>
						   <nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(2)%>"
								onblur="checkPadLeft(this,'0',2);" />
					   </td>
					   <td>
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
							    onblur="alertLength(this,10);"  	
								onkeyup="<%=bpXTrnFrm.getAutoTab(15)%>" />
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(5)%>" />
						</td>
						<td>
							<nested:text property="costPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
							    onblur="alertLength(this,10);" 	
								onkeyup="<%=bpXTrnFrm.getAutoTab(15)%>" />
						</td>
						<td>
							<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpXTrnFrm.getAutoTab(5)%>" />
						</td>
						<td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>												
					</nested:nest>
				</tr>
				<tr class="oddRowCenter">
				<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
					<td class="bgWhiteRight" colspan="16">
						<a href="<%=bpXTrnFrm.getAddBtnHref()%>" >
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="<%=bpXTrnFrm.getMassBtnHref()%>" >
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="<%=bpXTrnFrm.getClrBtnHref()%>" >
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
					<%}%>
				</tr>
			</nested:nest>
		</table>

		<hr />


<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/bpcExTranPaging.jsf" %></div>

		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_ACD)%>" >
						Act<br>Code<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_ACD)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_END_AFF)%>" >
						End<br>Aff<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_END_AFF)%>
					</a>
				</td>
				<td colspan="5">
					End Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_RPT_AFF)%>" >
						Rpt<br>Aff<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_RPT_AFF)%>
					</a>
				</td>
				<td colspan="5">
					Rpt Prod
				</td>
				<td colspan="2" rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_PUBLISH_FLAG)%>" >
						Pub<br>Flag<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_PUBLISH_FLAG)%>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<%//Sridevi.K code modified to toggle between the select and deselect the data%>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('bpcExTranListItem','bpcEx.selected','<%=bpXTrnFrm.getBpcExTranListSize()%>');" />
					<%//Sridevi.K end...%>
				</td>								
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_END_INV_CD)%>" >
						Inv Cd<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_END_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_END_LIST)%>" >
						List<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_END_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_END_LABEL)%>" >
						Label<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_END_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_END_SIZE)%>" >
						Size<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_END_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_END_PACK)%>" >
						Pack<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_END_PACK)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_RPT_INV_CD)%>" >
						Inv Cd<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_RPT_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_RPT_LIST)%>" >
						List<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_RPT_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_RPT_LABEL)%>" >
						Label<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_RPT_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_RPT_SIZE)%>" >
						Size<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_RPT_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_RPT_PACK)%>" >
						Pack<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_RPT_PACK)%>
					</a>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_SUP_AFF)%>" >
						Supp<br>Aff<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_SUP_AFF)%>
					</a>
				</td>
				<td colspan="5" rowspan="1">
					Sup Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_FREEZE_COST)%>" >
						Frz<br>Cost<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_FREEZE_COST)%>
					</a>
				</td>
				<td rowspan="2">
					Beg<br>Period<br>
				</td>
				<td rowspan="2">
					End<br>Period<br>
				</td>				
				<td colspan="2" rowspan="2">
					Bill<br>Price<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_BP_CUR_CD)%>" >
						BP<br>Cur Cd<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_BP_CUR_CD)%>
					</a>
				</td>
				<td colspan="2" rowspan="2">
					<br>Cost<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_COST_CUR_CD)%>" >
						Cost<br>Cur Cd<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_COST_CUR_CD)%>
					</a>
				</td>
				<td colspan="1" rowspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>												
			</tr>
			<tr class="mntTblHdng">
				<td colspan="1" rowspan="1">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_SUP_INV_CD)%>" >
						Inv Cd<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_SUP_INV_CD)%>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_SUP_LIST)%>" >
						List<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_SUP_LIST)%>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_SUP_LABEL)%>" >
						Label<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_SUP_LABEL)%>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_SUP_SIZE)%>" >
						Size<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_SUP_SIZE)%>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort"
						href="<%=bpXTrnFrm.getSrtHref(DBConst.COL_SUP_PACK)%>" >
						Pack<br>
						<%=bpXTrnFrm.dspSort(DBConst.COL_SUP_PACK)%>
					</a>
				</td>
			</tr>

			<nested:hidden property="bpcExTranListSize" />
			<nested:notEqual property="bpcExTranListSize" value="0">
			
			<%// sridevi.K code modified to replace the nested iterate tag%>
			<% int rowNumber = 0; %>
			
			<%// used the JSTL c:forEach tag to loop through bpcExTranList %>
			<c:forEach items="${sessionScope.bpXTrnFrm.bpcExTranList}"
			                				 var="bpcExTranBean"
				               				 varStatus="bpcExTranStatus">
				               	     
           	    <% // define the common part of the property tag of html in another string %>
		   		<% String bpcExTranListItemArray = "bpcExTranListItem[" + rowNumber +"]."; %>              				               	     
				
				<% // declare a String to notify when there is a change %>				
				<% String onChangeCall = "makeEditDirty('" + "bpcExTranListItem[" + rowNumber++ + "].bpcEx.selected" + "');"; %>				
				
				<% // tmpProperty is given a null to set its values compatible to the property%>
				<% String tmpProperty = "" ; %>
				<%//Sridevi.K Abbott tag is changed to modify the custom tag to work fine without nested iterate tag %>
				<% // abbott is a custom tag to give some coloring effect to the alternate rows %>				
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">
				<%//Sridevi.K End of modification%>
			
				<td class="mntCenter">				
					<% //Sridevi.K code added to print the line numbers %>
					<c:out value="${sessionScope.bpXTrnFrm.pagingFilter.startRecord + bpcExTranStatus.index}"/>
					<%//Sridevi.K end--%>
							
					<% // Using 'c:if' to check if the bpcExTranBean.bpcEx.msg is not equal to "  " %>
					<c:if test="${bpcExTranBean.bpcEx.msg ne ''}" >
					   <a class="error"
						  href="#"
						  id="anchor<c:out value="${bpcExTranStatus.index}"/>"
						  name="anchor<c:out value="${bpcExTranStatus.index}"/>"
						  onclick="return false;"
						  onmouseover="showMsgPopup('anchor<c:out value="${bpcExTranStatus.index}"/>', '<c:out value="${bpcExTranBean.bpcEx.msg}" />');"
						  onmouseout='hideMsgPopup();' >
						  <img src="images/exclamation.png" />
					  </a>
				    </c:if>	
					<% //tmpProperty is initialized here as per the column actionCode %>
					<% tmpProperty = bpcExTranListItemArray + "actionCode" ; %>						
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						       onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />							
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endAff %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endAff" ; %>				
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>				
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endProduct.invCode %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endProduct.invCode" ; %>				
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endProduct.list %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endProduct.list" ; %>				
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>"
						       onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endProduct.label %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endProduct.label" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						   onchange="<%=onChangeCall%>"
						   onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
						   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endProduct.size %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endProduct.size" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
						       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endProduct.pack %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endProduct.pack" ; %>	
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>
				
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.rptAff %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.rptAff" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>
			
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.rptProduct.invCode %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.rptProduct.invCode" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
			 			       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.rptProduct.list %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.rptProduct.list" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>"
						       onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.rptProduct.label %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.rptProduct.label" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
						       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.rptProduct.size %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.rptProduct.size" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
					           onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
					           onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.rptProduct.pack %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.rptProduct.pack" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>
				
				<td colspan="2"  class="mntCenter">
					<% //tmpProperty is initialized here as per the column publishFlag %>
					<% tmpProperty = bpcExTranListItemArray + "publishFlag" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" disabled="true" styleClass="fltrWidth1"/>
				</td> <%//<nested:nest property="bpcEx"> %>
			
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.selected %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.selected" ; %>
					<html:checkbox property="<%=tmpProperty%>" />
				</td><%//End Nesting BpcEx%>								
				</abbott:row>
				
				<% //Sridevi.K abbott custom tag is modified to work fine without the nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">			
				<% //Sridevi.K End..%>
				
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.supAff %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.supAff" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>

				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.supProduct.invCode %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.supProduct.invCode" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.supProduct.list %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.supProduct.list" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(6)%>"
						       onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.supProduct.label %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.supProduct.label" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
						       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.supProduct.size %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.supProduct.size" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(3)%>"
						       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.supProduct.pack %>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.supProduct.pack" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(4)%>"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>
				<%//End Nesting Sup Product%>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.freezeCost%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.freezeCost" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.begPeriod%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.begPeriod" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
						       onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(2)%>"
						       onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
				</td>				
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.endPeriod%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.endPeriod" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
			   		      	   onchange="<%=onChangeCall%>"
						       onkeyup="<%=bpXTrnFrm.getAutoTab(2)%>"
						       onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
				</td>		
				<td colspan="2">					
					<% //tmpProperty is initialized here as per the column bpcEx.billPrice%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.billPrice" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth10"
						       onchange="<%=onChangeCall%>"
						       onblur="alertLength(this,10);"  
						       onkeyup="<%=bpXTrnFrm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.bpCurCode%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.bpCurCode" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="fltrWidth5"
					       	   onchange="<%=onChangeCall%>"
					           onkeyup="<%=bpXTrnFrm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />
				</td>
				<td colspan="2"  class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.costPrice%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.costPrice" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth10"							
						       onchange="<%=onChangeCall%>"
						       onblur="alertLength(this,10);"  
						       onkeyup="<%=bpXTrnFrm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column bpcEx.costCurCode%>
					<% tmpProperty = bpcExTranListItemArray + "bpcEx.costCurCode" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="fltrWidth5"
					       	   onchange="<%=onChangeCall%>"
					           onkeyup="<%=bpXTrnFrm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />
				</td>
			
				<td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>						 
				</abbott:row>
				
				<%//Sridevi.K Abbott custom tag is modified to work without nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">
				<% //Sridevi.K End of abbott custom tag modification %>
				
				<td class="mntCenter">
					BP<br>Prds
				</td>
				<td colspan="16">
					<table width="100%" cellspacing="0">
						<tr>
							<% // Using c:forEach for looping the bpPeriodValues starting form 0 to 5 %>								     
							<c:forEach items="${bpcExTranBean.bpcEx.bpPeriodValues}" 
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
							<% // Using c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
							<c:forEach items="${bpcExTranBean.bpcEx.bpPeriodValues}"
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
				<%//Sridevi.K Abbott custom tag is modified to work without nested iterate tag %>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">
				<%//Sridevi.K End of Abbott custom tag modification %>
				<td class="mntCenter">
					Cost<br>Prds
				</td>
				<td colspan="16">
					<table width="100%" cellspacing="0">
						<tr>
							<% // Using c:forEach for looping the costPeriodValues starting form 0 to 5 %>
							<c:forEach items="${bpcExTranBean.bpcEx.costPeriodValues}"
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
							<c:forEach items="${bpcExTranBean.bpcEx.costPeriodValues}" 									
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
			</c:forEach>
			<%//Sridevi.K end of modification to replace the nested iterate tag%>
			<div name="navigation" id="navigation" class="hidden">
				<%@ include file="/include/bpcExTranBtmPaging.jsf" %>
			</div>
		</nested:notEqual>		
	</table>
	<nested:equal property="bpcExTranListSize" value="0">
		<%@ include file="/include/recordsNotFound.jsf" %>
	</nested:equal>
	<hr />
</nested:form>
<%//Sridevi.K script added to alert the user if he clicks copy selected button without selecting any row%>
<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=bpXTrnFrm.getFocusField()%>');
		/**
		*
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
			for(i = 0; i < bpXTrnFrm.bpcExTranListSize.value; i++) {			
				var element = "bpcExTranListItem[" + i + "].bpcEx.selected";				
				if(!rowSelected){				
					for(j = 0; j < bpXTrnFrm.elements.length; j++) {
						if(bpXTrnFrm.elements[j].name == element){
							if(bpXTrnFrm.elements[j].checked == true ) {							
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
<%//Sridevi.K end of code to alert the user if he does not selects any row to copy%>

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
				for(i = 0; i < bpXTrnFrm.bpcExTranListSize.value; i++) {			
					var element = "bpcExTranListItem[" + i + "].bpcEx.selected";				
					if(!rowSelected){				
						for(j = 0; j < bpXTrnFrm.elements.length; j++) {
							if(bpXTrnFrm.elements[j].name == element){
								if(bpXTrnFrm.elements[j].checked == true ) {							
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

	
<%//Sridevi.K added script to alert the user if he does not select any row to delete and also to alert the conformation of delete%>
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
		for(i = 0; i < bpXTrnFrm.bpcExTranListSize.value; i++) {
			var element = "bpcExTranListItem[" + i + "].bpcEx.selected";				
			if(!rowSelected){
				for(j = 0; j < bpXTrnFrm.elements.length; j++) {
					if(bpXTrnFrm.elements[j].name == element){
						if(bpXTrnFrm.elements[j].checked == true ) {
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
	
<%//Sridevi.K end.. %>
</script>
<%@ include file="/include/footer.jsf" %>