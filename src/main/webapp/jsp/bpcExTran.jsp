
<%! String pageTitle = "BPC Exception Maintenance"; %>
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
	<s:form method="post" name="bpXTrnFrm" action="bpcExTranMaint" namespace="/" theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
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
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<s:hidden name="searchObject.bpcEx.modelId" />
			<s:hidden name="searchObject.bpcEx.datasetTableId" />
			<% String submitFilter = "submitFilter(document.bpXTrnFrm,'filter', event);"; %>			
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="searchObject.actionCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						theme="simple" />
				</td>
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
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
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="searchObject.bpcEx.supAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.supProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.supProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 6, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.supProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.supProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 3, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.supProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 4, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.freezeCost" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.begPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 2, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.endPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 2, event);" 
						theme="simple" />
				</td>
				<td colspan="1">
					<s:textfield name="searchObject.bpcEx.billPrice" maxlength="15" cssClass="fltrWidth10"
						onchange="makeFilterDirty('pagingDiv','red','bold');" 
						onkeydown="%{submitFilter}"
						onblur="alertLength(this,10);"  
						onkeyup="return autoTab(this, 15, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.bpCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 5, event);" 
						theme="simple" />
				</td>	
				<td colspan="1">
					<s:textfield name="searchObject.bpcEx.costPrice" maxlength="15" cssClass="fltrWidth10"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onblur="alertLength(this,10);"  
						onkeyup="return autoTab(this, 15, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.bpcEx.costCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						onkeydown="%{submitFilter}"
						onkeyup="return autoTab(this, 5, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.publishFlag" maxlength="1" cssClass="fltrWidth1"
						onchange="makeFilterDirty('pagingDiv','red','bold');" 
						onkeydown="%{submitFilter}" 
						theme="simple" />
				</td>
				
									   
				<!-- 1st Part end -->					  



				<%-- <td colspan="1">
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
								  listValue="value" 
								  theme="simple" />
						<s:select name="searchObject.userSelected" 
								  list="#session.TCGMUser.userlist" 
								  cssClass="commandOption"
								  headerKey="ALL" 
								  headerValue="ALL" 
								  theme="simple" />
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
			                <s:else>
			                    <s:property value="TCGMUser.userid" />
			                </s:else>
                     </td>							
			</tr>
			<tr class="oddRowCenter">
				<td class="bgWhiteRight" colspan="16">
					<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'advancedfilter');">
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>							
					<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>
		<hr />
		<!-- ADD NEW SECTION -->
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
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" value="ASC" />
			<s:hidden name="addNew.bpcEx.modelId" />
			<s:hidden name="addNew.bpcEx.datasetTableId" />
			<% 
				String submitAdd = "submitAdd(document.bpXTrnFrm,'add','bpcExTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) {
					submitAdd = "submitAdd(document.bpXTrnFrm,'add','bpcExTranSave.do', event);";
				} else {
					submitAdd = "";
				}
			%>
			<tr class="oddRowCenter">
				<td>
					<s:if test="addNew.bpcEx.msg != null && addNew.bpcEx.msg != ''">
						<a class="error" href="#" id="anchorAddNew" name="anchorAddNew"
							onclick="return false;"
							onmouseover="showMsgPopup('anchorAddNew', '<s:property value="addNew.bpcEx.msg" escapeHtml="true"/>');"
							onmouseout='hideMsgPopup();'>
							<img src="images/exclamation.png" />
						</a>
					</s:if>
					<s:textfield name="addNew.actionCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.rptAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.rptProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.rptProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.rptProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.rptProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.rptProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
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
			<tr class="oddRowCenter">
				<td>
					<s:textfield name="addNew.bpcEx.supAff" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.supProduct.invCode" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.supProduct.list" maxlength="6" cssClass="fltrWidth6"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 6, event);"
						onblur="checkPadLeft(this,'0',6);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.supProduct.label" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.supProduct.size" maxlength="3" cssClass="fltrWidth3"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 3, event);"
						onblur="checkPadLeft(this,'0',3);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.supProduct.pack" maxlength="4" cssClass="fltrWidth4"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 4, event);"
						onblur="checkPadLeft(this,'0',4);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.freezeCost" maxlength="1" cssClass="fltrWidth1"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 1, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.begPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 2, event);"
						onblur="checkPadLeft(this,'0',2);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.endPeriod" maxlength="2" cssClass="fltrWidth2"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 2, event);"
						onblur="checkPadLeft(this,'0',2);" 
						theme="simple" />
				</td>
				


<!-- 2nd Part end -->					

           
				<td>
					<s:textfield name="addNew.bpcEx.billPrice" maxlength="15" cssClass="fltrWidth10"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onblur="alertLength(this,10);"  	
						onkeyup="return autoTab(this, 15, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.bpCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 5, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.costPrice" maxlength="15" cssClass="fltrWidth10"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onblur="alertLength(this,10);" 	
						onkeyup="return autoTab(this, 15, event);" 
						theme="simple" />
				</td>
				<td>
					<s:textfield name="addNew.bpcEx.costCurCode" maxlength="5" cssClass="fltrWidth5"
						onchange="makeAddNewDirty();"
						onkeydown="%{submitAdd}"
						onkeyup="return autoTab(this, 5, event);" 
						theme="simple" />
				</td>
				<td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>												
			</tr>
			<tr class="oddRowCenter">
				<% if ((TCGMUser.getRole().getAccessLevel()) != (abbott.ai.tcgm.entities.Role.Query.getAccessLevel())) { %>
					<td class="bgWhiteRight" colspan="16">
						<a href="javascript:chgActCmdSubmit(document.bpXTrnFrm,'add','bpcExTranSave.do');">
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="javascript:chgActCmdSubmit(document.bpXTrnFrm,'massupdate','bpcExTranSave.do');">
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="javascript:chgActCmdSubmit(document.bpXTrnFrm,'clearaddnew','bpcExTranMaint.do');">
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				<% } %>
			</tr>
		</table>
		<hr />
		<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/bpcExTranPaging.jsf" %>
		</div>
		<!-- DATA GRID SECTION -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_ACD%>');">
						Act<br>Code<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_ACD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_END_AFF%>');">
						End<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="5">End Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="5">Rpt Prod</td>
				<td colspan="2" rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_PUBLISH_FLAG%>');">
						Pub<br>Flag<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_PUBLISH_FLAG">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('bpcExTranListItem','bpcEx.selected','<s:property value="bpcExTranListSize"/>');" />
				</td>								
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_END_INV_CD%>');">
						Inv Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_END_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_END_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_END_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_END_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_END_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_RPT_INV_CD%>');">
						Inv Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_RPT_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_RPT_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_RPT_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_RPT_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_SUP_AFF%>');">
						Supp<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="5" rowspan="1">Sup Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_FREEZE_COST%>');">
						Frz<br>Cost<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_FREEZE_COST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">Beg<br>Period<br></td>
				<td rowspan="2">End<br>Period<br></td>				
				<td colspan="2" rowspan="2">Bill<br>Price<br></td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_BP_CUR_CD%>');">
						BP<br>Cur Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_BP_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				
				
<!-- 3rd part end -->
				
				
				<td colspan="2" rowspan="2"><br>Cost<br></td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_COST_CUR_CD%>');">
						Cost<br>Cur Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_COST_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="1" rowspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>												
			</tr>
			<tr class="mntTblHdng">
				<td colspan="1" rowspan="1">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort" href="javascript:chgSrtSubEbcdic(document.bpXTrnFrm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<!-- DATA ROWS -->
			<s:hidden name="bpcExTranListSize" />
			<s:if test="bpcExTranListSize != 0">
				<s:iterator value="bpcExTranList" var="bpcExTranBean" status="bpcExTranStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("bpcExTranStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
					%>
					<s:set var="onChangeCall" value="%{'makeEditDirty(\"bpcExTranListItem[' + #bpcExTranStatus.index + '].bpcEx.selected\")'}"/>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
						<td class="mntCenter">				
							<s:property value="pagingFilter.startRecord + #bpcExTranStatus.index"/>
							<s:if test="#bpcExTranBean.bpcEx.msg != null && #bpcExTranBean.bpcEx.msg != ''">
								<a class="error" href="#" 
								   id="anchor<%=rowNumber%>" 
								   name="anchor<%=rowNumber%>"
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<%=rowNumber%>', '<s:property value="#bpcExTranBean.bpcEx.msg" escapeHtml="true"/>');"
								   onmouseout='hideMsgPopup();'>
									<img src="images/exclamation.png" />
								</a>
							</s:if>
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].actionCode" 
										 maxlength="1" cssClass="mntWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />							
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endAff" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>				
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endProduct.invCode" 
										 maxlength="1" cssClass="fltrWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endProduct.list" 
										 maxlength="6" cssClass="fltrWidth6"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 6, event);"
										 onblur="checkPadLeft(this,'0',6);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endProduct.label" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endProduct.size" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endProduct.pack" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.rptAff" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.rptProduct.invCode" 
										 maxlength="1" cssClass="fltrWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.rptProduct.list" 
										 maxlength="6" cssClass="fltrWidth6"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 6, event);"
										 onblur="checkPadLeft(this,'0',6);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.rptProduct.label" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.rptProduct.size" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.rptProduct.pack" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td colspan="2" class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].publishFlag" 
										 maxlength="1" disabled="true" cssClass="fltrWidth1" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:checkbox name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.selected" 
										theme="simple" />
						</td>								
					</abbott:row>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">			
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.supAff" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.supProduct.invCode" 
										 maxlength="1" cssClass="fltrWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
																 
										 
						<!-- 4rth part end -->	


						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.supProduct.list" 
										 maxlength="6" cssClass="fltrWidth6"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 6, event);"
										 onblur="checkPadLeft(this,'0',6);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.supProduct.label" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.supProduct.size" 
										 maxlength="3" cssClass="fltrWidth3"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 3, event);"
										 onblur="checkPadLeft(this,'0',3);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.supProduct.pack" 
										 maxlength="4" cssClass="fltrWidth4"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 4, event);"
										 onblur="checkPadLeft(this,'0',4);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.freezeCost" 
										 maxlength="1" cssClass="fltrWidth1"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 1, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.begPeriod" 
										 maxlength="2" cssClass="fltrWidth2"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 2, event);"
										 onblur="checkPadLeft(this,'0',2);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>				
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.endPeriod" 
										 maxlength="2" cssClass="fltrWidth2"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 2, event);"
										 onblur="checkPadLeft(this,'0',2);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>		
						<td colspan="2">					
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.billPrice" 
										 maxlength="15" cssClass="fltrWidth10"
										 onchange="%{#onChangeCall}"
										 onblur="alertLength(this,10);"  
										 onkeyup="return autoTab(this, 15, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.bpCurCode" 
										 maxlength="5" cssClass="fltrWidth5"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 5, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td colspan="2" class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.costPrice" 
										 maxlength="15" cssClass="fltrWidth10"							
										 onchange="%{#onChangeCall}"
										 onblur="alertLength(this,10);"  
										 onkeyup="return autoTab(this, 15, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td class="mntCenter">
							<s:textfield name="bpcExTranListItem[%{#bpcExTranStatus.index}].bpcEx.costCurCode" 
										 maxlength="5" cssClass="fltrWidth5"
										 onchange="%{#onChangeCall}"
										 onkeyup="return autoTab(this, 5, event);" 
										 onkeydown="restrSpace(event);" 
										 theme="simple" />
						</td>
						<td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>						 
					</abbott:row>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						<td class="mntCenter">BP<br>Prds</td>
						<td colspan="16">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#bpcExTranBean.bpcEx.bpPeriodValues" var="bpPeriod1" 
												begin="0" end="5" status="bpPeriodStatus1">
										<td class="mntRight" width="16%">
											<s:property value="#bpPeriod1.period"/>
										</td>
									</s:iterator>	
								</tr>
								<tr>
									<s:iterator value="#bpcExTranBean.bpcEx.bpPeriodValues" var="bpPeriod2"
												begin="6" end="11" status="bpPeriodStatus2">
										<td class="mntRight">
											<s:property value="#bpPeriod2.period"/>
										</td>
									</s:iterator>											
								</tr>
							</table>
						</td>
					</abbott:row>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						<td class="mntCenter">Cost<br>Prds</td>
						<td colspan="16">
							<table width="100%" cellspacing="0">
								<tr>
									<s:iterator value="#bpcExTranBean.bpcEx.costPeriodValues" var="costPeriod1"
												begin="0" end="5" status="costPeriodStatus1">
										<td class="mntRight" width="16%">
											<s:property value="#costPeriod1.period"/>
										</td>
									</s:iterator>									
								</tr>
								<tr>
									<s:iterator value="#bpcExTranBean.bpcEx.costPeriodValues" var="costPeriod2" 									
												begin="6" end="11" status="costPeriodStatus2">
										<td class="mntRight">
											<s:property value="#costPeriod2.period"/>
										</td>
									</s:iterator>	
								</tr>
							</table>
						</td>
					</abbott:row>
				</s:iterator>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/bpcExTranBtmPaging.jsf" %>
				</div>
			</s:if>
		</table>
		<s:if test="bpcExTranListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</s:if>
		<hr />
	</s:form>
	<!-- JAVASCRIPT SECTION -->
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
					for (i = 0; i < document.bpXTrnFrm.elements['bpcExTranListSize'].value; i++) {			
						var element = "bpcExTranListItem[" + i + "].bpcEx.selected";				
						if (!rowSelected) {				
							for (j = 0; j < document.bpXTrnFrm.elements.length; j++) {
								if (document.bpXTrnFrm.elements[j].name == element) {
									if (document.bpXTrnFrm.elements[j].checked == true) {							
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
			if (cmd == 'publishall' || cmd == 'unpublishall') {		
				rowSelected = true;
			} else {
				for (i = 0; i < document.bpXTrnFrm.elements['bpcExTranListSize'].value; i++) {			
					var element = "bpcExTranListItem[" + i + "].bpcEx.selected";				
					if (!rowSelected) {				
						for (j = 0; j < document.bpXTrnFrm.elements.length; j++) {
							if (document.bpXTrnFrm.elements[j].name == element) {
								if (document.bpXTrnFrm.elements[j].checked == true) {							
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
				for (i = 0; i < document.bpXTrnFrm.elements['bpcExTranListSize'].value; i++) {
					var element = "bpcExTranListItem[" + i + "].bpcEx.selected";				
					if (!rowSelected) {
						for (j = 0; j < document.bpXTrnFrm.elements.length; j++) {
							if (document.bpXTrnFrm.elements[j].name == element) {
								if (document.bpXTrnFrm.elements[j].checked == true) {
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