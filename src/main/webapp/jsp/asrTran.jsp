<a name="FilterView"></a>
<%! String pageTitle = "ASR Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<%-- <jsp:useBean id="asrTranForm" scope="session" class="abbott.ai.tcgm.action.form.AsrTranForm" /> --%>
                   <!-- Added for -->
<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser"  scope="session" type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst"  scope="session" class="abbott.ai.tcgm.data.DBConst" />
<jsp:useBean id="asrForm" scope="session" class="abbott.ai.tcgm.action.form.AsrForm" />
<!-- end -->
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
		<s:form method="post" name="asrTranForm" id ="asrTranForm" class="abbott.ai.tcgm.action.form.AsrTranForm" action="asrTranMaintenance.action" scope="session">
		<s:hidden property="cmd" />
		<s:hidden property="focusField" />
		<%//Begin code for filter row%>
		<table width="780" cellspacing="0" border=""1" >
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
			<s:hidden property="sortObject.sortColumn" />
			<s:hidden property="sortObject.sortOrder" />
			<%//Sridevi.K end..%>
			<% String submitFilter = "submitFilter(document.asrTranForm,'filter', event);"; %>
			
			<%-- <nested:nest property="searchObject">
				<nested:nest property="asr">
					<s:hidden property="modelId" />
					<s:hidden property="datasetTableId" />
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
							<select property="userSelected" styleClass="commandOption" onchange="makeFilterDirty('pagingDiv','red','bold');" >
		          <option value="ALL">ALL</option>
       				<options name="TCGMUser" property="userlist" /></select> 
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
			</nested:nest> --%>
			
			<!-- Struts2 starts -->
		
    <s:iterator value="searchObject">
        <s:hidden name="asr.modelId" />
        <s:hidden name="asr.datasetTableId" />
        
        
        <!-- Starts -->
        
        <tr class="oddRowCenter">
					<td>
						<s:textfield theme="simple" name="searchObject.actionCode" maxlength="1" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 1, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth1"/>
					</td>
					
					<td>
						<s:textfield theme="simple" name="searchObject.asr.productOrigin" maxlength="1" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 1, event);" onchange="makeFilterDirty('pagingDiv','red','bold'); " class="fltrWidth1"/>
					</td>
						<td>
							<s:textfield theme="simple"  name="searchObject.asr.rptAff" maxlength="4" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 4, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" onblur="checkPadLeft(this,'0',4);" class="fltrWidth4"/>
						</td>
						
							<td>
								<s:textfield theme="simple"  name="searchObject.asr.rptProduct.invCode" maxlength="1" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 1, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth1"/>
							</td>
							<td>
								<s:textfield theme="simple"  name="searchObject.asr.rptProduct.list" maxlength="6" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 6, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth6"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.rptProduct.label" maxlength="3" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 3, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth3"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.rptProduct.size" maxlength="3" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 3, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth3"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.rptProduct.pack" maxlength="4" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 4, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth4"/>
							</td>
						
						<td>
							<s:textfield theme="simple" name="searchObject.asr.supAff" maxlength="4" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 4, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" onblur="checkPadLeft(this,'0',4);" class="fltrWidth4"/>
						</td>
						
							<td>
								<s:textfield theme="simple" name="searchObject.asr.supProduct.invCode" maxlength="1" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 1, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth1"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.supProduct.list" maxlength="6" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 6, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth6"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.supProduct.label" maxlength="3" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 3, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth3"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.supProduct.size" maxlength="3" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 3, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth3"/>
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.asr.supProduct.pack" maxlength="4" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 4, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth4"/>
							</td>
						
						<td>
							<s:textfield theme="simple" name="searchObject.asr.usage" maxlength="16" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 16, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" onblur="alertLength(this,10);" class="fltrWidth16"/>
										  
						</td>
						
						<td>
							<s:textfield theme="simple" name="searchObject.asr.supKey" maxlength="1" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onkeyup="return autoTab(this, 1, event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth1"/>
						</td>
					
					<td>
						<s:textfield theme="simple" name="searchObject.publishFlag" maxlength="1" value="" onkeydown="submitFilter(document.asrTranForm,'filter', event);" onchange="makeFilterDirty('pagingDiv','red','bold');" class="fltrWidth1"/>
					</td>
				</tr>
        
        <!-- End -->
        
        <!-- Comment starts -->
        
        
        <%-- <tr class="oddRowCenter">
            <td>
                <s:textfield name="actionCode" maxlength="1" cssClass="fltrWidth1"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onkeydown="return autoTab(this, 1, event);" />
            </td>
            <td>
                <s:textfield name="asr.productOrigin" maxlength="1" cssClass="fltrWidth1"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onkeydown="return autoTab(this, 1, event);" />
            </td>
            <td>
                <s:textfield name="asr.rptAff" maxlength="4" cssClass="fltrWidth4"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onkeydown="return autoTab(this, 4, event);" 
                    onblur="checkPadLeft(this,'0',4);" />
            </td>
            <s:iterator value="asr.rptProduct">
                <td>
                    <s:textfield name="asr.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 1, event);" />
                </td>
                <td>
                    <s:textfield name="asr.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 6, event);" />
                </td>
                <td>
                    <s:textfield name="asr.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 3, event);" />
                </td>
                <td>
                    <s:textfield name="asr.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 3, event);" />
                </td>
                <td>
                    <s:textfield name="asr.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 4, event);" />
                </td>
            </s:iterator>
            <td>
                <s:textfield name="asr.supAff" maxlength="4" cssClass="fltrWidth4"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onkeydown="return autoTab(this, 4, event);" 
                    onblur="checkPadLeft(this,'0',4);" />
            </td>
            <s:iterator value="asr.supProduct">
                <td>
                    <s:textfield name="asr.supProduct.invCode" maxlength="1" cssClass="fltrWidth1"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 1, event);" />
                </td>
                <td>
                    <s:textfield name="asr.supProduct.list" maxlength="6" cssClass="fltrWidth6"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 6, event);" />
                </td>
                <td>
                    <s:textfield name="asr.supProduct.label" maxlength="3" cssClass="fltrWidth3"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 3, event);" />
                </td>
                <td>
                    <s:textfield name="asr.supProduct.size" maxlength="3" cssClass="fltrWidth3"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 3, event);" />
                </td>
                <td>
                    <s:textfield name="asr.supProduct.pack" maxlength="4" cssClass="fltrWidth4"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" 
                        onkeydown="return autoTab(this, 4, event);" />
                </td>
            </s:iterator>
            <td>
                <s:textfield name="asr.usage" maxlength="16" cssClass="fltrWidth16"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onblur="alertLength(this,10);" 
                    onkeydown="return autoTab(this, 16, event);" />
            </td>
            <td>
                <s:textfield name="asr.supKey" maxlength="1" cssClass="fltrWidth1"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onkeydown="return autoTab(this, 1, event);" />
            </td>
            <td>
                <s:textfield name="publishFlag" maxlength="1" cssClass="fltrWidth1"
                    onchange="makeFilterDirty('pagingDiv','red','bold');" 
                    onkeydown="return autoTab(this, 1, event);" />
            </td>
        </tr> --%>
        
        <!-- Comment end -->
        
        <tr class="fltrTblHdng">
            <td colspan="2">User<br>Id</td>
            <td colspan="15" class="bgWhiteRight">&nbsp;</td>
        </tr>
        <tr class="oddRowCenter">
            <td colspan="2">
            <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
                 <%-- <s:if test="TCGMUser.role.accessLevel >= #{Role.Analyst.accessLevel}"> --%>
                    <s:select name="userSelected" cssClass="commandOption"
                        list="userlist"
                        onchange="makeFilterDirty('pagingDiv','red','bold');" />
                </abbott:securePage>
                <%-- </s:if> --%>
                <s:else>
                    <s:property value="TCGMUser.userid" />
                </s:else>
                
                
                <%-- <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<select property="userSelected" styleClass="commandOption" onchange="makeFilterDirty('pagingDiv','red','bold');" >
		          <option value="ALL">ALL</option>
       				<options name="TCGMUser" property="userlist" /></select> 
						</abbott:securePage> --%>
                
                
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
    </s:iterator>
			
			
			
			
			<!-- Strust2 end -->
			
			
		</table>
		<hr />
		<%//Begin code for add new row%>
		
<%-- 		
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
		</table> --%>
		
		
		<!-- Strusts 2 starts -->
		
<table width="780" cellspacing="0" border="1">
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
    <s:iterator value="addNew">
        <s:hidden name="asr.modelId" />
        <s:hidden name="asr.datasetTableId" />
        <tr class="oddRowCenter">
            <td>
                <s:if test="asr.msg != ''">
                    <a class="error" href="#" id="anchorAddNew" name="anchorAddNew"
                        onclick="return false;"
                        onmouseover="showMsgPopup('anchorAddNew', '<s:property value="asr.msg" />');"
                        onmouseout='hideMsgPopup();'>
                        <img src="images/exclamation.png" />
                    </a>
                </s:if>
                <s:textfield theme="simple" name="actionCode" maxlength="1" cssClass="fltrWidth1"
                    onchange="makeAddNewDirty();" 
                    onkeydown="return autoTab(this, 1, event);" />
            </td>
            <s:iterator value="asr">
                <td>
                    <s:textfield theme="simple" name="productOrigin" maxlength="1" cssClass="fltrWidth1"
                        onchange="makeAddNewDirty();" 
                        onkeydown="return autoTab(this, 1, event);" />
                </td>
                <td>
                    <s:textfield theme="simple" name="rptAff" maxlength="4" cssClass="fltrWidth4"
                        onchange="makeAddNewDirty();" 
                        onkeydown="return autoTab(this, 4, event);" 
                        onblur="checkPadLeft(this,'0',4);" />
                </td>
                <s:iterator value="rptProduct">
                    <td>
                        <s:textfield theme="simple" name="invCode" maxlength="1" cssClass="fltrWidth1"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 1, event);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="list" maxlength="6" cssClass="fltrWidth6"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 6, event);" 
                            onblur="checkPadLeft(this,'0',6);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="label" maxlength="3" cssClass="fltrWidth3"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 3, event);" 
                            onblur="checkPadLeft(this,'0',3);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="size" maxlength="3" cssClass="fltrWidth3"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 3, event);" 
                            onblur="checkPadLeft(this,'0',3);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="pack" maxlength="4" cssClass="fltrWidth4"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 4, event);" 
                            onblur="checkPadLeft(this,'0',4);" />
                    </td>
                </s:iterator>
                <td>
                    <s:textfield theme="simple" name="supAff" maxlength="4" cssClass="fltrWidth4"
                        onchange="makeAddNewDirty();" 
                        onkeydown="return autoTab(this, 4, event);" 
                        onblur="checkPadLeft(this,'0',4);" />
                </td>
                <s:iterator value="supProduct">
                    <td>
                        <s:textfield theme="simple" name="invCode" maxlength="1" cssClass="fltrWidth1"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 1, event);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="list" maxlength="6" cssClass="fltrWidth6"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 6, event);" 
                            onblur="checkPadLeft(this,'0',6);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="label" maxlength="3" cssClass="fltrWidth3"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 3, event);" 
                            onblur="checkPadLeft(this,'0',3);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="size" maxlength="3" cssClass="fltrWidth3"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 3, event);" 
                            onblur="checkPadLeft(this,'0',3);" />
                    </td>
                    <td>
                        <s:textfield theme="simple" name="pack" maxlength="4" cssClass="fltrWidth4"
                            onchange="makeAddNewDirty();" 
                            onkeydown="return autoTab(this, 4, event);" 
                            onblur="checkPadLeft(this,'0',4);" />
                    </td>
                </s:iterator>
                <td>
                    <s:textfield theme="simple" name="usage" maxlength="16" cssClass="fltrWidth16"
                        onchange="makeAddNewDirty();" 
                        onkeydown="return autoTab(this, 16, event);" 
                        onblur="alertLength(this,10);" />
                </td>
                <td>
                    <s:textfield theme="simple" name="supKey" maxlength="1" cssClass="fltrWidth1"
                        onchange="makeAddNewDirty();" 
                        onkeydown="return autoTab(this, 1, event);" />
                </td>
            </s:iterator>
        </tr>
    </s:iterator>
    <tr>

				<abbott:securePage
					userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
					requiredAccessLevel="<%=Role.Query.getAccessLevel()%>"
					comparisonType="!=">
					<td colspan="16" class="right"><a
						href="javascript:chgActCmdSubmit(document.asrTranForm,'add','asrTranSave.do');">
							<img src="images/btnAdd.png" alt="Add" />
					</a> <a
						href="javascript:chgActCmdSubmit(document.asrTranForm,'massupdate','asrTranSave.do');">
							<img src="images/btnMassUpdate.png"
							alt="Apply Changes to all records based on Filter criteria" />
					</a> <a
						href="javascript:chgActCmdSubmit(document.asrTranForm,'clearaddnew','asrTranMaintenance.do');">
							<img src="images/btnClear.png" alt="Clear" />
					</a></td>
				</abbott:securePage>
			</tr>
</table>

   <!-- Strusts2 end -->		
		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/asrTranPaging.jsf" %></div>

		 <table width="780" cellspacing="0" >
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_ACD%>');">
						Act<br>Code<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_ACD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_PROD_ORIGIN%>');">
						Prod<br>Orig<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_PROD_ORIGIN%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_AFF%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_INV_CD%>');">
						Inv<br>Cd<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_INV_CD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_AFF%>');">
						Supp<br>Aff<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_AFF%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv<br>Cd<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_INV_CD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="4">Sup Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_USAGE_FAC%>');">
						Usage<br>Factor<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_USAGE_FAC%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_KEY%>');">
						Sup<br>Key<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_KEY%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');">
						Pub<br>Flag<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_PUBLISH_FLAG%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onclick="return toggleSelectAll('asrTranListItem','asr.selected','<s:property value='asrTranListSize'/>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_LIST%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_LABEL%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_SIZE%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_RPT_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_PACK%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_LIST%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_LABEL%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_SIZE%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.asrTranForm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_PACK%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<s:hidden property="asrTranListSize" />
			
			
			<s:if test="asrTranListItem != null && !asrTranListItem.isEmpty()">
				<s:iterator value="asrTranListItem" var="asrTranBean" status="asrTranStatus">
					<tr class="<s:if test='#asrTranStatus.even'>evenRow</s:if><s:else>oddRow</s:else>">
						<td class="mntCenter">
							<s:property value="pagingFilter.startRecord + #asrTranStatus.index" />
							<s:if test="#asrTranBean.asr.msg != ''">
								<a class="error" href="#"
								   id="anchor<s:property value='#asrTranStatus.index'/>"
								   name="anchor<s:property value='#asrTranStatus.index'/>"
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<s:property value='#asrTranStatus.index'/>', '<s:property value='#asrTranBean.asr.msg'/>');"
								   onmouseout="hideMsgPopup();">
									<img src="images/exclamation.png" />
								</a>
							</s:if>
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].actionCode" maxlength="1" cssClass="mntWidth1"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.productOrigin" maxlength="1" cssClass="mntWidth1"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 1, event);" onblur="checkPadLeft(this,'0',1);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.rptAff" maxlength="4" cssClass="mntWidth4"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 4, event);" onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.rptProduct.invCode" maxlength="1" cssClass="mntWidth1"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 1, event);" onblur="checkPadLeft(this,'0',1);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="asrTranListItem[%{#asrTranStatus.index}].asr.rptProduct.list" maxlength="6" cssClass="mntWidth6"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 6, event);" onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.rptProduct.label" maxlength="3" cssClass="mntWidth3"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 3, event);" onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.rptProduct.size" maxlength="3" cssClass="mntWidth3"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 3, event);" onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.rptProduct.pack" maxlength="4" cssClass="mntWidth4"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 4, event);" onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>

						<td class="mntCenter">
							<s:textfield theme ="simple"  name="asrTranListItem[%{#asrTranStatus.index}].asr.supAff" maxlength="4" cssClass="mntWidth4"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 4, event);" onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.supProduct.invCode" maxlength="1" cssClass="mntWidth1"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.supProduct.list" maxlength="6" cssClass="mntWidth6"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 6, event);" onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.supProduct.label" maxlength="3" cssClass="mntWidth3"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 3, event);" onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.supProduct.size" maxlength="3" cssClass="mntWidth3"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 3, event);" onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.supProduct.pack" maxlength="4" cssClass="mntWidth4"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onkeyup="return autoTab(this, 4, event);" onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.usage" maxlength="16" cssClass="mntWidth16"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');"
								 onblur="alertLength(this,10);" onkeyup="return autoTab(this, 16, event);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield theme ="simple" name="asrTranListItem[%{#asrTranStatus.index}].asr.supKey" maxlength="1" cssClass="mntWidth1"
								 onchange="makeEditDirty('asrTranListItem[%{#asrTranStatus.index}].asr.selected');" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<s:textfield name="asrTranListItem[%{#asrTranStatus.index}].publishFlag" maxlength="1" cssClass="mntWidth1" disabled="true" />
						</td>
						<td class="mntCenter">
							<s:checkbox name="asrTranListItem[%{#asrTranStatus.index}].asr.selected" />
						</td>
					</tr>
				</s:iterator>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/asrTranBtmPaging.jsf" %>
				</div>
			</s:if>
		</table>
		
		
		
		<s:if test="asrTranListItem == null || asrTranListItem.isEmpty()">
			<p class="recordsNotFound">No Records Found Matching Filter Criteria</p>
		</s:if>
		<hr />
	</s:form>
	
	
	<!-- Java Script Section -->
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<s:property value="focusField"/>');
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