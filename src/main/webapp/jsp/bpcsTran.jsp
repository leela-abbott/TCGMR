<%@ page language="java" 
contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%!
    String pageTitle = "BPSC Maint  Data";
%>

<a id="FilterView" name="FilterView"></a>
<%@ include file="/include/header.jsf" %>

<%-- <jsp:useBean id="bpcsTranForm" scope="session" class="abbott.ai.tcgm.action.form.BpcsTranForm" /> --%>

<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser"  scope="session" type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst"  scope="session" class="abbott.ai.tcgm.data.DBConst" />

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<s:form method="post" name="bpcsTranForm" type="abbott.ai.tcgm.action.form.BpcsTranForm" action="bpcsTranMaint.do" scope="session">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<%//Begin code for filter row%>
		
		<!-- Table1 -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">  
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Code</td>
				<td rowspan="2">Freeze<br>Cost</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			
			<% //Sridevi.K Code changed for fixing the toggle between the ascending and descending order of the data %>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />			
			<% String submitFilter = "submitFilter(document.bpcsTranForm,'filter', event);"; %>
			
			<s:iterator value="searchObject">
				<s:hidden name="bpcs.modelId" />
				<s:hidden name="bpcs.datasetTableId" />
				
				<tr class="oddRowCenter">
					<td>
					  <s:textfield theme="simple" name="actionCode"
						maxlength="1" value=""
						onkeydown="submitFilter(document.asrTranForm,'filter', event);"
						onkeyup="return autoTab(this, 1, event);"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						class="fltrWidth1" />

					</td>
					
					<s:iterator value ="bpcs">
						<td>
							<s:textfield theme="simple" name="searchObject.bpcs.rptAff"
							maxlength="4" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 4, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth4" />
							
						</td>
						<td>
							
							<s:textfield theme="simple" name="searchObject.bpcs.supAff"
							maxlength="4" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 4, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth4" />
						</td>
						
						<s:iterator value ="supProduct">
						<td>
									
						    <s:textfield theme="simple" name="searchObject.bpcs.supProduct.invCode"
							maxlength="1" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 1, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth1" />
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.bpcs.supProduct.list"
								maxlength="6" value=""
								onkeydown="submitFilter(document.asrTranForm,'filter', event);"
								onkeyup="return autoTab(this, 6, event);"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								class="fltrWidth6" />
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.bpcs.supProduct.label"
								maxlength="3" value=""
								onkeydown="submitFilter(document.asrTranForm,'filter', event);"
								onkeyup="return autoTab(this, 3, event);"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								class="fltrWidth3" />
									
							</td>
							<td>
								<s:textfield theme="simple" name="searchObject.bpcs.supProduct.size"
								maxlength="3" value=""
								onkeydown="submitFilter(document.asrTranForm,'filter', event);"
								onkeyup="return autoTab(this, 3, event);"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								class="fltrWidth3" />
							</td>
							<td>
							<s:textfield theme="simple" name="searchObject.bpcs.supProduct.pack"
							maxlength="4" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 4, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth4" />
							</td>
						</s:iterator>
						
							
						
						<td colspan="1">
							<%-- <nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onblur="alertLength(this,10);" 
								onkeyup="<%=bpcsTranForm.getAutoTab(15)%>"/> --%>
								
							<s:textfield theme="simple" name="searchObject.bpcs.supProduct.billPrice"
							maxlength="15" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 10, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth10" />
						</td>
						<td>
							<%-- <nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(5)%>"/> --%>
								
							<s:textfield theme="simple" name="searchObject.bpcs.supProduct.bpCurCode"
							maxlength="5" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 5, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth5" />
						</td>
						<td>
							<%-- <nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(1)%>"/> --%>
								
							<s:textfield theme="simple" name="searchObject.bpcs.supProduct.freezeCost"
							maxlength="1" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 5, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth1" />
						</td>

					</s:iterator>
					
					
				</tr>
				
				<tr class="fltrTblHdng">
					<td><br>cost</td>
					<td>Cost<br>Cur<br>Code</td>
					<td>Beg<br>Period</td>
					<td>End<br>Period</td>
					<td>Pub<br>Flag</td>
					<td>User<br>Id</td>
					<td class="bgWhiteRight" colspan="7">&nbsp;</td>
				</tr>
		       <s:iterator value ="bpcs">
				
					<tr class="oddRowCenter">
						<td>
							<s:textfield theme="simple" name="searchObject.bpcs.costPrice"
							maxlength="15" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 15, event);"
							onblur="alertLength(this,10);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth10" />
						</td>
						<td>
							
								
							<s:textfield theme="simple" name="searchObject.bpcs.costCurCode"
							maxlength="5" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 5, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth5" />
						</td>
						<td>
							
								
							<s:textfield theme="simple" name="searchObject.bpcs.begPeriod"
							maxlength="2" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 2, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth2" />
						</td>
						<td>
							
								
						  <s:textfield theme="simple" name="searchObject.bpcs.endPeriod"
							maxlength="2" value=""
							onkeydown="submitFilter(document.asrTranForm,'filter', event);"
							onkeyup="return autoTab(this, 2, event);"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							class="fltrWidth2" />
						</td>
				   
		         </s:iterator>
				
					<td>
						<s:textfield theme="simple" name="searchObject.bpcs.publishFlag"
						maxlength="1" value=""
						onkeydown="submitFilter(document.asrTranForm,'filter', event);"
						onchange="makeFilterDirty('pagingDiv','red','bold');"
						class="fltrWidth2" />
					</td>
					
					
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
						<td colspan="7" class="bgWhiteRight">
							<a href="javascript:changeCmdAndSubmit(document.bpcsTranForm,'filter');" >
								<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.bpcsTranForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>								
							<a href="javascript:changeCmdAndSubmit(document.bpcsTranForm,'clearfilter');" >
								<img src="images/btnClear.png" alt="Clear Filter"/></a>
						</td>
					</tr>
					
		</s:iterator>
				
		</table>
		
		<!-- Table1 end -->
		<hr />

<%//Begin row for adding new record%>

<!--  Table2 starts -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">Bp<br>Cur<br>Code</td>
				<td rowspan="2">Freeze<br>Cost</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden nmae="sortObject.sortOrder" value="ASC" />
			<% String submitAdd = "submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
						submitAdd = "submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);";
					}else{
						submitAdd = "";
					}
			%>
			<s:iterator value ="addNew">
			
				<s:hidden name="bpcs.modelId" />
				<s:hidden name="bpcs.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<%-- <%=bpcsTranForm.dspAddNewMsg()%> --%>
						
						<s:if test="addNew.bpcs.msg != null && addNew.bpcs.msg.trim().length() > 0">
						    <a class="error"
						       href="#"
						       id="anchorAddNew"
						       name="anchorAddNew"
						       onclick="return false;"
						       onmouseover="showMsgPopup('anchorAddNew', '<s:property value="addNew.bpcs.msg"/>');"
						       onmouseout="hideMsgPopup();">
						        <img src="images/exclamation.png" />
						    </a>
						</s:if>
						
						
						<%-- <nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeAddNewDirty();"
									 onkeydown = "<%=submitAdd%>"
									 onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" /> --%>
									 
							<s:textfield theme="simple" name="actionCode" maxlength="1" cssClass="fltrWidth1"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 1, event);" />
						</td>
					<s:iterator value = "bpcs">
						<td>
							<%-- <nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
										 onblur="checkPadLeft(this,'0',4);" /> --%>
										 
							<s:textfield theme="simple" name="rptAff" maxlength="4" cssClass="fltrWidth4"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 4, event);" />
						</td>
						<td>
							<%-- <nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
										 onblur="checkPadLeft(this,'0',4);" /> --%>
										 
							<s:textfield theme="simple" name="supAff" maxlength="4" cssClass="fltrWidth4"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 4, event);" />			 
						</td>
						
						<s:iterator value = "supProduct">
						
							<td>
								<%-- <nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" /> --%>
							<s:textfield theme="simple" name="invCode" maxlength="1" cssClass="fltrWidth1"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 1, event);" />			 
							</td>
							<td>
								<%-- <nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(6)%>"
											 onblur="checkPadLeft(this,'0',6);" /> --%>
							<s:textfield theme="simple" name="list" maxlength="6" cssClass="fltrWidth6"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 6, event);" 
		                    onblur="checkPadLeft(this,'0',6);"/>				 
											 
							</td>
							<td>
								<%-- <nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
											 onblur="checkPadLeft(this,'0',3);" /> --%>
											 
								<s:textfield theme="simple" name="label" maxlength="3" cssClass="fltrWidth3"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 3, event);" 
		                     onblur="checkPadLeft(this,'0',3);"/>			 
							</td>
							<td>
								<%-- <nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
											 onblur="checkPadLeft(this,'0',3);" /> --%>
											 
								<s:textfield theme="simple" name="size" maxlength="1" cssClass="fltrWidth3"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 3, event);" 
		                    onblur="checkPadLeft(this,'0',3);"/>			 
							</td>
							<td>
								<%-- <nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
											 onblur="checkPadLeft(this,'0',4);" /> --%>
											 
								<s:textfield theme="simple" name="pack" maxlength="4" cssClass="fltrWidth4"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 4, event);" 
		                    onblur="checkPadLeft(this,'0',4);"/>			 
							</td>
						</s:iterator>
						
						
						<td>
							<%-- <nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
										 onchange="makeAddNewDirty();"
										 onblur="alertLength(this,10);" 
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" /> --%>
										 
						 <s:textfield theme="simple" name="billPrice" maxlength="15" cssClass="fltrWidth10"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 15, event);" 
		                    onblur="alertLength(this,10);"/>				 
						</td>
						<td>
							<%-- <nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(5)%>" /> --%>
										 
							<s:textfield theme="simple" name="bpCurCode" maxlength="5" cssClass="fltrWidth5"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 5, event);" />
						</td>
						<td>
							<%-- <nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" /> --%>
										 
						 <s:textfield theme="simple" name="bpCurCode" maxlength="1" cssClass="fltrWidth1"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 1, event);" />
						</td>
					</s:iterator>
				</tr>
				
				
				<tr class="fltrTblHdng">
					<td><br>Cost</td>
					<td>Cost<br>Cur<br>Code</td>
					<td>Beg<br>Period</td>
					<td>End<br>Period</td>
					<td class="bgWhiteRight" colspan="9">&nbsp;</td>
				</tr>
				
				
				<tr class="oddRowCenter">
				
				<s:iterator value ="bpcs">
				
						<td>
							<%-- <nested:text property="costPrice" maxlength="15" styleClass="fltrWidth10"
										 onchange="makeAddNewDirty();"
										 onblur="alertLength(this,10);" 
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" /> --%>
										 
							<s:textfield theme="simple" name="costPrice" maxlength="15" cssClass="fltrWidth10"
		                    onchange="makeAddNewDirty();" 
		                    onblur="alertLength(this,10);" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 15, event);" />
						</td>
						<td>
							<%-- <nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(5)%>" /> --%>
										 
						<s:textfield theme="simple" name="costCurCode" maxlength="5" cssClass="fltrWidth5"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 5, event);" />				 
						</td>
						<td>
							<%-- <nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
										 onblur="checkPadLeft(this,'0',2);" /> --%>
						<s:textfield theme="simple" name="begPeriod" maxlength="2" cssClass="fltrWidth2"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 5, event);" 
		                    onblur="checkPadLeft(this,'0',2);"/>
						</td>
						<td>
							<%-- <nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
										 onblur="checkPadLeft(this,'0',2);" /> --%>
						<s:textfield theme="simple" name="endPeriod" maxlength="2" cssClass="fltrWidth2"
		                    onchange="makeAddNewDirty();" 
		                    onkeydown="submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"
		                    onkeyup="return autoTab(this, 2, event);" 
		                    onblur="checkPadLeft(this,'0',2);"/>
						</td>
					</s:iterator>
					
					
					<%-- <% 
					if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
						<td class="bgWhiteRight" colspan="9">
							<a href="<%=bpcsTranForm.getAddBtnHref()%>" >
								<img src="images/btnAdd.png" alt="Add" /></a>
							<a href="<%=bpcsTranForm.getMassBtnHref()%>" >
								<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
							<a href="<%=bpcsTranForm.getClrBtnHref()%>" >
								<img src="images/btnClear.png" alt="Clear"/></a>
						</td>
					<%}%> --%>	
					
					<abbott:securePage
					userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
					requiredAccessLevel="<%=Role.Query.getAccessLevel()%>"
					comparisonType="!=">
					<td colspan="9" class="bgWhiteRight">
					<a
						href="javascript:chgActCmdSubmit(document.bpcsTranForm,'add','bpcsTranSave.do');">
							<img src="images/btnAdd.png" alt="Add" />
					</a> 
					<a
						href="javascript:chgActCmdSubmit(document.bpcsTranForm,'massupdate','bpcsTranSave.do');">
							<img src="images/btnMassUpdate.png"
							alt="Apply Changes to all records based on Filter criteria" />
					</a> 
					<a
						href="javascript:chgActCmdSubmit(document.bpcsTranForm,'clearaddnew','bpcsTranMaint.do');">
							<img src="images/btnClear.png" alt="Clear" />
					</a></td>
				</abbott:securePage>
					
				</tr>
				
			</s:iterator>
			
		</table>
		
		<!-- Table2 end -->

		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden">
		<%@ include file="/include/bpcsTranPaging.jsf" %></div>

<%// Start labels for bottom part of page. (subf) %>

<!-- Table3 starts -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_ACD%>');">
						Act<br>Code<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_ACD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					
					
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_RPT_AFF%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_SUP_AFF%>');">
						Supp<br>Aff<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_AFF%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">
					Bill<br>Price<br>
				</td>
				<td rowspan="2">
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_BP_CUR_CD)%>" >
						BP Cur<br>Code<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_BP_CUR_CD)%>
					</a> --%>
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_BP_CUR_CD%>');">
						BP Cur<br>Code<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_BP_CUR_CD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
					
				</td>
				<td rowspan="2">
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_FREEZE_COST)%>" >
						Freeze<br>Cost<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_FREEZE_COST)%>
					</a> --%>
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_FREEZE_COST%>');">
						Freeze<br>Cost<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_FREEZE_COST%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_PUBLISH_FLAG)%>" >
						Pub<br>Flag<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_PUBLISH_FLAG)%>
					</a> --%>
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');">
						Pub<br>Flag<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_PUBLISH_FLAG%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				
					<% //Sridevi.K code changed to toggle between select and deselect all the rows of data %>
					<input type="image" src="images/btnCheck.png" 
					alt="Toggle Select All" 
					onClick="return toggleSelectAll('bpcsTranListItem','bpcs.selected','<s:property value='bpcsTranListSize'/>');" />
					<% //Sridevi.K %>
					
				</td>
				
				<td colspan="1">&nbsp;</td>
			</tr>

			<tr class="mntTblHdng">
				<td>
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_INV_CD)%>" >
						Inv Cd<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_INV_CD)%>
					</a> --%>
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv Code<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_INV_CD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
					
				</td>
				<td>
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_LIST)%>" >
						List<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_LIST)%>
					</a> --%>
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_LIST%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_LABEL)%>" >
						Label<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_LABEL)%>
					</a> --%>
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_LABEL%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
					
				</td>
				<td>
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_SIZE)%>" >
						Size<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_SIZE)%>
					</a> --%>
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_SIZE%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_PACK)%>" >
						Pack<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_PACK)%>
					</a> --%>
					
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_SUP_PACK%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
				</td>
				
				
				
				
				
				<td rowspan="2" colspan="4">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td colspan="1">&nbsp;</td>
				<!-- Adding blank width such that it assigns on right side of the page -->
				 <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				<td>
			    </td>
				<td>
					<br>Cost<br>
				</td>
				<td>
					<%-- <a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_COST_CUR_CD)%>" >
						Cost Cur<br>Code<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_COST_CUR_CD)%>
					</a> --%>
					<a class="mntSort" 
					   href="javascript:chgSrtSubEbcdic(document.bpcsTranForm,'<%=DBConst.COL_COST_CUR_CD%>');">
						Cost Cur<br>Code<br>
						<s:if test="sortObject.sortColumn == '<%=DBConst.COL_COST_CUR_CD%>'">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
							     src="<s:property value='sortObject.sortImg'/>" 
							     align="center" />
						</s:if>
					</a>
					
				</td>
				<td>
					Beg<br>Period<br>
				</td>
				<td>
					End<br>Period<br>
				</td>
				<td colspan="9">&nbsp;</td>
			</tr>
			<%// End - Subf Lables %>

			<s:hidden name="bpcsTranListSize"/>
			
		<s:if test="bpcsTranListSize != 0">
    
             
			<% int rowNumber = 0; %>

			<%-- <c:forEach items="${sessionScope.bpcsTranForm.bpcsTranList}"
			           var="bpcsTranBean"
			           varStatus="bpcsTranStatus">
			           
			        <% // define the common part of the property tag of html in another string %>
		    		<% String bpcsTranItemArray = "bpcsTranListItem[" + rowNumber +"]."; %>

					<% // declare a String to notify when there is a change %>
					<% String onChangeCall = "makeEditDirty('" + "bpcsTranListItem[" + rowNumber++ + "].bpcs.selected" + "');"; %>		
					
					<% // tmpProperty is given a null to set its values compatible to the property %>
					<% String tmpProperty = "" ; %>
		
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<%//Sridevi.K abbott tag modified to work fine without nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
					<%//Sridevi.K End..%> --%>
					
					<s:set var="tmpProperty"  value="" />
					
		<s:iterator value="bpcsTranForm.bpcsTranList" var="bpcsTranBean" status="bpcsTranStatus">
				    <s:set var="bpcsTranItemArray" value="'bpcsTranListItem[' + #bpcsTranStatus.index + '].'" />
				    <s:set var="onChangeCall" value="'makeEditDirty(\'' + #bpcsTranItemArray + 'bpcs.selected\');'" />
				    <abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${bpcsTranStatus.index}" id="mntRow">
				        

						<td class="mntCenter">
						
							<% //Sridevi.K Added code to print the line numbers %>
							<% //Sridevi.K End of code to print the line numbers %>
							
							<s:property value="pagingFilter.startRecord + #bpcsTranStatus.index" />
							<s:if test="#bpcsTranBean.bpcs.msg != ''">
							
								<a class="error"
									href="#"
									id="anchor<s:property value='#bpcsTranStatus.index'/>"
									name="anchor<s:property value='#bpcsTranStatus.index'/>"
									onclick="return false;"
									onmouseover="showMsgPopup('anchor<s:property value='#bpcsTranStatus.index'/>', '<s:property value='#bpcsTranBean.bpcs.msg'/>');"
									onmouseout='hideMsgPopup();' >
									<img src="images/exclamation.png" />
								</a>
							</s:if>

							<%-- <% tmpProperty = bpcsTranItemArray + "actionCode" ; %>	
							<html:text property="<%= tmpProperty %>" 
							           maxlength="1" styleClass="mntWidth1"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 1, event);" 
								       onkeydown="restrSpace(event);" /> --%>
								       
								       
								       <s:textfield
										    name="bpcsTranList[%{#bpcsTranStatus.index}].actionCode"
										    maxlength="1"
										    cssClass="mntWidth1"
										    onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
										    onkeyup="return autoTab(this,1,event);"
										    onkeydown="restrSpace(event);" />
						</td>
						<%-- <td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.rptAff %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.rptAff" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="4" styleClass="fltrWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.supAff %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supAff" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="4" styleClass="fltrWidth4"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.invCode %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.invCode" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="1" styleClass="fltrWidth1"
								       onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.list %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.list" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="6" styleClass="fltrWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(6)%>"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.label %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.label" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="3" styleClass="fltrWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.size %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.size" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="3" styleClass="fltrWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.pack %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.pack" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="4" styleClass="fltrWidth4"
						               onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<// end of property supProduct>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.billPrice %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.billPrice" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="15" styleClass="fltrWidth15"
								  	   onchange="<%=onChangeCall%>" 
								  	   onblur="alertLength(this,10);" 
									   onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.bpCurCode %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.bpCurCode" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="5" styleClass="fltrWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.freezeCost %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.freezeCost" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="1" styleClass="fltrWidth1"
								       onchange="<%=onChangeCall%>" onkeydown="restrSpace(event);" />
						</td>
					<// end of property bpcs>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column publishFlag %>
							<% tmpProperty = bpcsTranItemArray + "publishFlag" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1" disabled="true"/>
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.selected %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.selected" ; %>
							<html:checkbox property="<%=tmpProperty%>" />
						</td> --%>
						
						<!-- Copilot -->
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.rptAff"
						        maxlength="4"
						        cssClass="fltrWidth4"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,4,event);"
						        onblur="checkPadLeft(this,'0',4);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.supAff"
						        maxlength="4"
						        cssClass="fltrWidth4"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,4,event);"
						        onblur="checkPadLeft(this,'0',4);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.supProduct.invCode"
						        maxlength="1"
						        cssClass="fltrWidth1"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,1,event);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.supProduct.list"
						        maxlength="6"
						        cssClass="fltrWidth6"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,6,event);"
						        onblur="checkPadLeft(this,'0',6);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.supProduct.label"
						        maxlength="3"
						        cssClass="fltrWidth3"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,3,event);"
						        onblur="checkPadLeft(this,'0',3);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.supProduct.size"
						        maxlength="3"
						        cssClass="fltrWidth3"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,3,event);"
						        onblur="checkPadLeft(this,'0',3);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.supProduct.pack"
						        maxlength="4"
						        cssClass="fltrWidth4"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,4,event);"
						        onblur="checkPadLeft(this,'0',4);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.billPrice"
						        maxlength="15"
						        cssClass="fltrWidth15"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onblur="alertLength(this,10);"
						        onkeyup="return autoTab(this,15,event);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.bpCurCode"
						        maxlength="5"
						        cssClass="fltrWidth5"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,5,event);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.freezeCost"
						        maxlength="1"
						        cssClass="fltrWidth1"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].publishFlag"
						        maxlength="1"
						        cssClass="fltrWidth1"
						        disabled="true" />
						</td>
						
						<td class="mntCenter">
						    <s:checkbox
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected" />
						</td>
												
						<td colspan="1">&nbsp;</td>
					</abbott:row>
					
					
					
					
					<%// Start next subf row for additional fields %>
					<%//Sridevi.K Abbott custom tag is modified to work fine without nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
					<%//Sridevi.K End..%>
					
						<td colspan="1">&nbsp;</td>
						<!-- Adding blank width such that it assigns on right side of the page -->
						 <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						<td>              
						 </td>
						<%-- <td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.costPrice %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.costPrice" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth10"
								       onchange="<%=onChangeCall%>"
								       onblur="alertLength(this,10);" 
								       onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.costCurCode %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.costCurCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth10"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.begPeriod %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.begPeriod" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
								       onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.endPeriod %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.endPeriod" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
								       onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
						</td> --%>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.costPrice"
						        maxlength="15"
						        cssClass="fltrWidth10"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onblur="alertLength(this,10);"
						        onkeyup="return autoTab(this,15,event);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.costCurCode"
						        maxlength="15"
						        cssClass="fltrWidth10"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,15,event);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.begPeriod"
						        maxlength="2"
						        cssClass="fltrWidth2"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,2,event);"
						        onblur="checkPadLeft(this,'0',2);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						<td class="mntCenter">
						    <s:textfield
						        name="bpcsTranList[%{#bpcsTranStatus.index}].bpcs.endPeriod"
						        maxlength="2"
						        cssClass="fltrWidth2"
						        onchange="makeEditDirty('bpcsTranList[%{#bpcsTranStatus.index}].bpcs.selected');"
						        onkeyup="return autoTab(this,2,event);"
						        onblur="checkPadLeft(this,'0',2);"
						        onkeydown="restrSpace(event);" />
						</td>
						
						
						<% // End of bpcs nesting %>
						<td colspan="10">&nbsp;</td>
					</abbott:row>

		<%// Start the 1 - 13 billing and cost price period fields %>
		<% // Billing Price Periods %>
				<%//Sridevi.K Abbott custom tag is modified to work fine without nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
				<%//Sridevi.K End..%>
							<td colspan="1">&nbsp;</td>
							<td class="mntCenter">BP<br>Period</td>
							<td colspan="13" class="mntCenter">
								<table width="100%" cellspacing="0">
									<tr>
										<% // Using c:forEach for looping the bpcs.bpPeriodValues starting form 0 to 5 %>
										<%-- <c:forEach 	items="${bpcsTranBean.bpcs.bpPeriodValues}" 
											        begin="0"
											        end="5"
											        step="1"
											        var="bpPeriod1"
			                				        varStatus="bpPeriodStatus1">
											        <td class="mntRight" width="16%">
												       <c:out value="${bpPeriod1.period}"/>
											        </td>
										</c:forEach> --%>
										
												<s:iterator value="bpcsTranBean.bpcs.bpPeriodValues" 
												begin="0" end="5" status="bpPeriodStatus1">
												    <td class="mntRight" width="16%">
												        <s:property value="bpPeriod1.period" />
												    </td>
												</s:iterator>
										
										
									</tr>
									<tr>
										<% // Using c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
										<%-- <c:forEach  items="${bpcsTranBean.bpcs.bpPeriodValues}"
											        begin="6"
											        end="11"
											        step="1"
											        var="bpPeriod2"
			                				        varStatus="bpPeriodStatus2"> 
											        <td class="mntRight">
												       <c:out value="${bpPeriod2.period}"/>
											        </td>
										</c:forEach> --%>
										
										<s:iterator value="bpcsTranBean.bpcs.bpPeriodValues" 
											begin="6" end="11" 
											status="bpPeriodStatus2">
											    <td class="mntRight">
											        <s:property value="bpPeriod2.period" />
											    </td>
										</s:iterator>
																					
									</tr>
								</table>
							</td>				
				</abbott:row>
				
				<% // Cost Price Periods %>
				
				<%//Sridevi.K Abbott custom tag modified to work fine without nested iterate tag %>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${bpcsTranStatus.index}" >
							<td colspan="1">&nbsp;</td>
							<td class="mntCenter">
								Cost<br>Period
							</td>
							<td colspan="13" class="mntCenter">
								<table width="100%" cellspacing="0">
									<tr>
										<% // Using c:forEach for looping the bpcExBean.bpcs.costPeriodValues starting form 0 to 5 %>
										<%-- <c:forEach items="${bpcsTranBean.bpcs.costPeriodValues}"
												   begin="0"
												   end="5"
												   step="1"
									 			   var="costPeriodBean1"
			                					   varStatus="costPeriodStatus1"> 
												   <td class="mntRight" width="16%">
													  <c:out value="${costPeriodBean1.period}"/>
												   </td>
										</c:forEach> --%>

										<s:iterator value="bpcsTranBean.bpcs.costPeriodValues" 
										   begin="0" end="5" 
										    status="costPeriodStatus1">
										    <td class="mntRight" width="16%">
										        <s:property value="costPeriodBean1.period" />
										    </td>
										</s:iterator>									
									</tr>
									<tr>
										<%-- <c:forEach  items="${bpcsTranBean.bpcs.costPeriodValues}" 									
											        begin="6"
											        end="11"
											        step="1"
											        var="costPeriod2"
											        varStatus="costPeriodStatus2"> 
											        <td class="mntRight">
												        <c:out value="${costPeriod2.period}"/>
											        </td>
										</c:forEach> --%>
										
										<s:iterator value="bpcsTranBean.bpcs.costPeriodValues"
								            begin="6"
								            end="11"
								            var="costPeriod2"
								            status="costPeriodStatus2">
								
										    <td class="mntRight">
										        <s:property value="costPeriod2.period"/>
										    </td>
										
										</s:iterator>											
									</tr>
								</table>
							</td>
					</abbott:row>
				
				</s:iterator>
				
				
				
				
				
				

			<div name="navigation" id="navigation" class="hidden">
				<%@ include file="/include/bpcsTranBtmPaging.jsf" %>
			</div>
			
	      </s:if>		
		</table>
		
		<!-- Table3 end -->
		<%-- <nested:equal property="bpcsTranListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal> --%>
		
			<s:if test="bpcsTranList.size() == 0">
	           <%@ include file="/include/recordsNotFound.jsf" %>
	        </s:if>
		<hr />
		
		
	</s:form>
	
	
	<!--  Java script section -->
	<script language="JavaScript1.2" type="text/javascript">
	showObj('navigation');
	<%-- setFocusReposition('<%=bpcsTranForm.getFocusField()%>'); --%>
	setFocusReposition('<s:property value="focusField"/>');
	/**
	*
	*/
	<%//Sridevi.K Script added to alert the user if he clicks copySelected without selecting any row%>
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
				for(i = 0; i < bpcsTranForm.bpcsTranListSize.value; i++) {
					var element = "bpcsTranListItem[" + i + "].bpcs.selected";
				
					if(!rowSelected){
						for(j = 0; j < bpcsTranForm.elements.length; j++) {
							if(bpcsTranForm.elements[j].name == element){

								if(bpcsTranForm.elements[j].checked == true ) {
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
			if ( cmd == 'publishall' || cmd == 'unpublishall'){				
				rowSelected=true;
			}
			else {
				for(i = 0; i < bpcsTranForm.bpcsTranListSize.value; i++) {			
					var element = "bpcsTranListItem[" + i + "].bpcs.selected";				
					if(!rowSelected){				
						for(j = 0; j < bpcsTranForm.elements.length; j++) {
							if(bpcsTranForm.elements[j].name == element){
								if(bpcsTranForm.elements[j].checked == true ) {							
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
			for(i = 0; i < bpcsTranForm.bpcsTranListSize.value; i++) {
				var element = "bpcsTranListItem[" + i + "].bpcs.selected";				
				if(!rowSelected){
					for(j = 0; j < bpcsTranForm.elements.length; j++) {
						if(bpcsTranForm.elements[j].name == element){
							if(bpcsTranForm.elements[j].checked == true ) {
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
	<%//Sridevi.K end of Script %>
<%@ include file="/include/footer.jsf" %>