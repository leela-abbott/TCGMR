<a name="FilterView"></a>
<%! String pageTitle = "Rate Data"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="rateDataForm" scope="session" class="abbott.ai.tcgm.action.form.RateDataForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadRate.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="rateDataForm" type="abbott.ai.tcgm.action.form.RateDataForm" action="/rateDataMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td>Cur<br>Cd</td>
				<td class="bgWhiteRight">&nbsp;</td>
			</tr>
			
			<%//Sridevi.K Code modified to fix to toggle between the data in a row%>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:text property="curCode" maxlength="5" styleClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');"/>
					</td>
					<td class="right">
						<a href="javascript:changeCmdAndSubmit(document.rateDataForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.rateDataForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest>
		</table>
		
		<hr />

<a name="AddMassUpdateView"></a>

		<%//Begin code for add new row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rate</td>							
				<td rowspan="2">Currency<br>Code</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>				
			</tr>
			<tr>
			<nested:nest property="addNew">
				<nested:hidden property="rateData.modelId" />
				<nested:hidden property="rateData.datasetTableId" />				
				<tr class="oddRowCenter">
					<td>
						<nested:notEqual property="rateData.msg" value="">
							<a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="rateData.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>
						</nested:notEqual>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
							onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="rateData">
						<td colspan="1">
							<nested:text property="rate" maxlength="15" styleClass="fltrWidth8"
								onchange="makeAddNewDirty();"
								onblur="alertLength(this,9);" 
								onkeyup="return autoTab(this, 15, event);" />
						</td>					
						<td colspan="1">
							<nested:text property="curCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeyup="return autoTab(this, 5, event);" />
						</td>
						<td colspan="1">
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td colspan="1">
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);" />
						</td>												
					</nested:nest><!-- End rateData -->
					<td colspan="16" class="right">
						<a href="javascript:chgActCmdSubmit(document.rateDataForm,'save','rateDataSave.do');" >
							<img src="images/btnSave.png" alt="Save" /></a>
						<a href="javascript:chgActCmdSubmit(document.rateDataForm,'massupdate','rateDataSave.do');">
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="javascript:chgActCmdSubmit(document.rateDataForm,'clearaddnew','rateDataMaint.do');" >
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				</tr>
			</nested:nest><!-- End addNew -->				
		</table>
		
		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/rateDataPaging.jsf" %></div>

		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;</td>				
				<td colspan="1" rowspan="2">
					Rate<br>
				</td>
				<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>								
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataForm,'<%=DBConst.COL_CUR_CD%>');" >
						Currency<br>Code<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_CUR_CD%>" >
							<img alt="<%=rateDataForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;</td>				
				<td colspan="1" rowspan="2">
					Beg<br>Period<br>
				</td>
				<td width="">&nbsp;</td>				
				<td colspan="1" rowspan="2">
					End<br>Period<br>
				</td>

				<td colspan="11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>												
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('rateDataListItem','selected','<%=rateDataForm.getRateDataListSize()%>');" />
				</td>

			</tr>
		</table>
		<table width="780" cellspacing="0">
			<nested:hidden property="rateDataListSize" />
			<nested:notEqual property="rateDataListSize" value="0">
			
			<%//Sridevi.K code added to replace the nested iterate tag with the JSTL tags%>
				<% int rowNumber = 0; %>

				<%// used the JSTL c:forEach tag to loop through rateDataList %>
				<c:forEach items="${sessionScope.rateDataForm.rateDataList}"
			          	   var="rateDataBean"	
			           	   varStatus="rateDataStatus">

					<% //declare a String to notify when there is a change %>					                			                
					<% String onChangeCall = "makeEditDirty('" + "rateDataListItem[" + rowNumber + "].selected" + "');"; %>

					<% //define the common part of the property tag of html in another string %>
		    		<% String rateDataListItemArray = "rateDataListItem[" + rowNumber +"]."; %>
			
					<%//String href encapsulates the call to a JavaScript copyRow %>
					<% String href = "javascript:copyRow(document.rateDataForm,'" + rowNumber++ + "','rateDataMaint.do');"; %>

					<% //tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>

					<%//Sridevi.K Abbott row tag is fixed to work without the nested iterate tag%>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
					<%//Sridevi.K end of Abbott tag fix%>

						<td  class="mntCenter" colspan="1">
						
						<% //Sridevi.K code added to print the line numbers %>
						<c:out value="${sessionScope.rateDataForm.pagingFilter.startRecord + rateDataStatus.index}"/>
						<%//Sridevi.K End ..%>
							
						<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
							<a href="<%=href%>">
								<img src="images/btnUpArrow.png" alt="Load Row" /></a>
							</a>
							<c:if test="${rateDataBean.msg ne ''}" >
								<% //Using 'c:if' to check if the rateDataBean msg is not equal to "  " %>
								<a class="error"
								   href="#"
								   id="anchor<c:out value="${rateDataStatus.index}"/>"
								   name="anchor<c:out value="${rateDataStatus.index}"/>"					
								   onclick="return false;"
								   onmouseover="showMsgPopup('anchor<c:out value="${rateDataStatus.index}"/>', '<c:out value="${rateDataBean.msg}"/>');"
								   onmouseout='hideMsgPopup();' >
								   <img src="images/exclamation.png" />
								</a>
							</c:if>							
							&nbsp;&nbsp;
							
							<% //tmpProperty is initialized here as per the column rate %>
							<% tmpProperty = rateDataListItemArray + "rate" ; %>				
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
								       onchange="<%=onChangeCall%>"
								       onblur="alertLength(this,9);" 
								       onkeyup="return autoTab(this, 15, event);" />								       
								
						</td>
						<td class="mntCenter" colspan="1">
							<% //tmpProperty is initialized here as per the column curCode%>
							<% tmpProperty = rateDataListItemArray + "curCode" ; %>														
							<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 5, event);" />
						</td>
						<td class="mntCenter" colspan="1">
							&nbsp;
							<% //tmpProperty is initialized here as per the column begPeriod%>
							<% tmpProperty = rateDataListItemArray + "begPeriod" ; %>				
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 2, event);" />
						</td>
						<td class="mntCenter" colspan="1">
							<% //tmpProperty is initialized here as per the column endPeriod%>
							<% tmpProperty = rateDataListItemArray + "endPeriod" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 2, event);" />
						</td>
						<td colspan="11" width="">&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="mntCenter" colspan="1">
							<% //tmpProperty is initialized here as per the column selected%>
							<% tmpProperty = rateDataListItemArray + "selected" ; %>
							<html:checkbox property="<%=tmpProperty%>" />
						</td>
					</abbott:row>
					
					<%// Start the 1 - 12 rate period fields %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
						<td class="mntCenter">
							Rates 1-6<br>Rates 7-12
						</td>
						<td colspan="17" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<% // Using c:forEach for looping the rates starting form 0 to 5 %>
									<c:forEach items="${rateDataBean.rates}" 
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
									<% // Using c:forEach for looping the rates starting form 6 to 11 %>
									<c:forEach items="${rateDataBean.rates}"
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
				<%// End the 1 - 12 rate period fields %>
				</c:forEach>	
				<%//Sridevi.K end of code modification to replace the nested iterate tag with the JSTL tags%>
				
			<%// End - Subf Data Portion for Original flds	%>
			</nested:notEqual>
		</table>
		<nested:equal property="rateDataListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=rateDataForm.getFocusField()%>');
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
			for(i = 0; i < rateDataForm.rateDataListSize.value; i++) {
				var element = "rateDataListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < rateDataForm.elements.length; j++) {
						if(rateDataForm.elements[j].name == element){
							if(rateDataForm.elements[j].checked == true ) {
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
	<%//Sridevi.K 7-16-05 end of script..%>
				
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < rateDataForm.rateDataListSize.value; i++) {
			var element = "rateDataListItem[" + i + "].selected";				
			if(!rowSelected){
				for(j = 0; j < rateDataForm.elements.length; j++) {
					if(rateDataForm.elements[j].name == element){
						if(rateDataForm.elements[j].checked == true ) {
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