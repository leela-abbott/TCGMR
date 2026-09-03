<a name="FilterView"></a>
<%! String pageTitle = "ASR Data Batch Entry"; %>
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
		<table width="780" cellspacing="0">
			<tr>
				<td colspan="16" class="right">
					<a href="javascript:checkSave(document.asrForm,'savebatch','asrBatchSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.asrForm,'clearbatch','asrBatchMaintenance.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			</tr>		
			<tr class="mntTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">
						Prod<br>Orig<br>
				</td>
				<td rowspan="2">
						Rpt<br>Aff<br>
				</td>
				<td rowspan="2">
						Inv<br>Cd<br>
				</td>
				<td colspan="4">
					Rpt Prod
				</td>
				<td rowspan="2">
						Supp<br>Aff<br>
				</td>
				<td rowspan="2">
						Inv<br>Cd<br>
				</td>
				<td colspan="4">
					Sup Prod
				</td>
				<td rowspan="2">
						Usage<br>Factor<br>
				</td>
				<td rowspan="2">
						Sup<br>Key<br>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('asrListItem','selected','<%=asrForm.getAsrListSize()%>');" />
				</td>
			</tr>
			<tr class="mntTblHdng">
				
				<td>
						List<br>
				</td> 	
				<td>
						Label<br>
				</td>
				<td>
						Size<br>
					
				</td>
				<td>
						Pack<br>
					
				</td>
				<td>
						List<br>
				</td>
				<td>
						Label<br>
				</td>
				<td>
						Size<br>
				</td>
				<td>
						Pack<br>
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
   			        <% String onFocusCall = "copyToNextRow('" + rowNumber + "');"; %>

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
							<% tmpProperty = asrListItemArray + "actionCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
									 onchange="<%=onChangeCall%>"
									 onfocus="<%=onFocusCall%>"
									 onkeyup="return autoTab(this, 1, event);" />
							<% //Sridevi.K End of code to print the line numbers %>
							
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
										   onkeyup="return autoTab(this, 1, event);" />
						</td>
						
						<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column rptAff %>
								<% tmpProperty = asrListItemArray + "rptAff" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
										   onchange="<%=onChangeCall%>"
										   onkeyup="return autoTab(this, 4, event);"
										   onblur="checkPadLeft(this,'0',4);" />		
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column invCode %>
							<% tmpProperty = asrListItemArray + "rptProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);"/>
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column list %>
							<% tmpProperty = asrListItemArray + "rptProduct.list" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column label %>
							<% tmpProperty = asrListItemArray + "rptProduct.label" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" />
						</td>
						<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column size %>
								<% tmpProperty = asrListItemArray + "rptProduct.size" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
										   onchange="<%=onChangeCall%>"
										   onkeyup="return autoTab(this, 3, event);"
										   onblur="checkPadLeft(this,'0',3);"/>
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column pack %>
							<% tmpProperty = asrListItemArray + "rptProduct.pack" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supAff %>
							<% tmpProperty = asrListItemArray + "supAff" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);" />
						</td>
						<td class="mntCenter">
							<%//tmpProperty is initialized here as per the column invCode of supProduct%>
							<% tmpProperty = asrListItemArray + "supProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column list of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.list" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column label of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.label" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column size of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.size" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" />		
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column pack of supProduct %>
							<% tmpProperty = asrListItemArray + "supProduct.pack" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
								       onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" />	
						</td>						
						<td class="mntLeft">
							<% //tmpProperty is initialized here as per the column usage %>
							<% tmpProperty = asrListItemArray + "usage" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="16" styleClass="mntWidth16"
									   onchange="<%=onChangeCall%>"
									   onblur="alertLength(this,10);" 
									   onkeyup="return autoTab(this, 16, event);" />										
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column supKey %>
							<% tmpProperty = asrListItemArray + "supKey" ; %>	
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
						 		       onchange="<%=onChangeCall%>" />	
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column selected %>
							<% tmpProperty = asrListItemArray + "selected"; %>
							<html:checkbox name="asrForm" property="<%=tmpProperty%>"/>
						</td>					
					</abbott:row>
				</c:forEach>
			</nested:notEqual>
			<tr>
				<td colspan="16" class="right">
					<a href="javascript:checkSave(document.asrForm,'savebatch','asrBatchSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.asrForm,'clearbatch','asrBatchMaintenance.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			</tr>		
			
		</table>
		
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">	
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
	function copyToNextRow(rowNumber)
	{
			if (rowNumber !=0 )
			{
			    var rowNumberFrom = rowNumber - 1;
			    var elementFrom   = 'asrListItem[' + rowNumberFrom +'].';
   			    var elementTo     = 'asrListItem[' + rowNumber +'].';
			    document.getElementById(elementTo + 'actionCode').value = document.getElementById(elementFrom + 'actionCode').value;
			    document.getElementById(elementTo + 'productOrigin').value = document.getElementById(elementFrom + 'productOrigin').value;
   			    document.getElementById(elementTo + 'rptAff').value = document.getElementById(elementFrom + 'rptAff').value;
   			    document.getElementById(elementTo + 'rptProduct.invCode').value = document.getElementById(elementFrom + 'rptProduct.invCode').value;
   			    document.getElementById(elementTo + 'rptProduct.list').value = document.getElementById(elementFrom + 'rptProduct.list').value;
   			    document.getElementById(elementTo + 'rptProduct.label').value = document.getElementById(elementFrom + 'rptProduct.label').value;
   			    document.getElementById(elementTo + 'rptProduct.size').value = document.getElementById(elementFrom + 'rptProduct.size').value;
   			    document.getElementById(elementTo + 'rptProduct.pack').value = document.getElementById(elementFrom + 'rptProduct.pack').value;
   			    document.getElementById(elementTo + 'supAff').value = document.getElementById(elementFrom + 'supAff').value;
   			    document.getElementById(elementTo + 'supProduct.invCode').value = document.getElementById(elementFrom + 'supProduct.invCode').value;
   			    document.getElementById(elementTo + 'supProduct.list').value = document.getElementById(elementFrom + 'supProduct.list').value;
   			    document.getElementById(elementTo + 'supProduct.label').value = document.getElementById(elementFrom + 'supProduct.label').value;
   			    document.getElementById(elementTo + 'supProduct.size').value = document.getElementById(elementFrom + 'supProduct.size').value;
   			    document.getElementById(elementTo + 'supProduct.pack').value = document.getElementById(elementFrom + 'supProduct.pack').value;
   			    document.getElementById(elementTo + 'usage').value = document.getElementById(elementFrom + 'usage').value;
   			    document.getElementById(elementTo + 'supKey').value = document.getElementById(elementFrom + 'supKey').value;
 		    
			}
	}

	</script>
	<%@ include file="/include/footer.jsf" %>
