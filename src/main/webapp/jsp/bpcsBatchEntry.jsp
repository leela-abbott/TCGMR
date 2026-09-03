
<%! String pageTitle = "BPC Data Batch Entry"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpcsForm" scope="session" class="abbott.ai.tcgm.action.form.BpcsForm" />
<a name="FilterView"></a><body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>	
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="bpcsForm" type="abbott.ai.tcgm.action.form.BpcsForm" action="/bpcsMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />

<%// Start 1st Row of Headers & Input fields for Bottom 3rd of Screen %>
		<table width="787" cellspacing="0">
			<tr>
				<td colspan="16" class="right">
					<a href="javascript:checkSave(document.bpcsForm,'savebatch','bpcsBatchSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.bpcsForm,'clearbatch','bpcsBatchMaint.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			</tr>		
		
			<tr class="mntTblHdng">
				<td  rowspan="2" colspan="1">&nbsp</td>				
				<td  rowspan="2" colspan="1">&nbsp</td>				
				<td  rowspan="2" colspan="1">Act<br>Code</td>			
				 <td rowspan="2" colspan="1"> Rpt<br>Aff</td>
				<td  rowspan="2" colspan="1">Sup<br>Aff</td>
				<td  rowspan="2" colspan="1">Inv<br>Cd</td>
				<td  rowspan="1" colspan="2">Sup Prod </td>
			    <td  rowspan="2" colspan="1">Bill<br>Price </td>
				<td  rowspan="2" colspan="1"> BP<br>Cur<br>Cd</td>
 			    <td rowspan="2"  colspan="1">Frz<br> Cost</td>
   		 	    <td colspan="1" rowspan="4"><br>Cost<br></td>
				<td colspan="1" rowspan="1">Cost<br>CurCd<br></td>
				<td colspan="1" rowspan="1">Beg<br>Prd<br></td>
				<td colspan="1" rowspan="1">End<br>Prd<br></td>
			 
			</tr>
			<tr class="mntTblHdng">
				<td  rowspan="1" colspan="2">   List    Label    Size    Pack</td>
				<td  rowspan="1" colspan="2"></td>
				<td colspan="2" ><input name="image" type="image" onClick="return toggleSelectAll('bpcsListItem','selected','<%=bpcsForm.getBpcsListSize()%>');" src="images/btnCheck.png" alt="Toggle Select All" />
                <%//Sridevi.K End of code modification to fix the toggle between selecting and deselecting all the row %></td>
			</tr>

			<!--<tr class="mntTblHdng">
			  <td>              
				 </td>
						 	 <td colspan="1" rowspan="1">
				<br>Cost<br>				</td>
				<td colspan="1" rowspan="1">
						Cost<br>CurCode<br>
				</td>
				<td colspan="1" rowspan="1">
					Beg<br>Period<br>				</td>
				<td colspan="1" rowspan="1">
					End<br>Period<br>				</td>
				<td colspan="7"><span class="mntCenter">
				  
				</span></td>
			</tr>-->
	  </table>
		<table width="789" cellspacing="0">

			
			<nested:hidden property="bpcsListSize" />
			<nested:notEqual property="bpcsListSize" value="0">
			
			<% //Sridevi.K JSTL tags are used to replace nested iterate tag of struts %>
			<% int rowNumber = 0; %>

			<%// used the JSTL c:forEach tag to loop through bpcsList %>
			<c:forEach items="${sessionScope.bpcsForm.bpcsList}"
			                		  var="bpcsBean"
			                	varStatus="bpcsStatus">

					<% //declare a String to notify when there is a change %>	
					<% String onChangeCall = "makeEditDirty('" + "bpcsListItem[" + rowNumber + "].selected" + "');"; %>
   			        <% String onFocusCall = "copyToNextRow('" + rowNumber + "');"; %>

					<% // define the common part of the property tag of html in another string %>
		        	<% String bpcsListItemArray = "bpcsListItem[" + rowNumber +"]."; %>

					<%//String href encapsulates the call to a JavaScript copyRow %>
					<% String href = "javascript:copyRow(document.bpcsForm,'" + rowNumber++ + "','bpcsMaint.do');"; %>

					<% // tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>
					
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
					
										
    
					<% // abbott is a custom tag to give some coloring effect to the alternate rows %>
					<%//Sridevi.K Abbott custom tag is changed to work properly without the nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" id="mntRow" rowNum="<%=rowNumber%>">
					<% //Sridevi.K End of code added to Abbott custom tag %>

						<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
						<td width="69" colspan="1"  class="mntCenter">
							<% // Sridevi.K Coded added to print the line numbers %>
							<c:out value="${sessionScope.bpcsForm.pagingFilter.startRecord + bpcsStatus.index}"/>
						
							<% // Using 'c:if' to check if the bpcsBean msg is not equal to "  " %>
							<c:if test="${bpcsBean.msg ne ''}" >
						<%	out.println("<a class=\"error\"");
									out.println(" href=\"#\"");
									out.print(" id=\"anchor");%><c:out value="${bpcsStatus.index}"/><% out.println("\" ");%>  
									<%out.print(" name=\"anchor");%><c:out value="${bpcsStatus.index}"/><% out.println("\" ");%>  
									<%out.println(" onclick=\"return false;\"");%>
	<%out.print(" onmouseover=\"showMsgPopup('anchor");%><c:out value="${bpcsStatus.index}"/><%out.print("' ");%>
		 <%out.print(" , '");%><c:out value="${bpcsBean.msg}"/> <% out.println("');\"");%>
									
				
					
									<%out.println(" onmouseout=\"hideMsgPopup();\" >");
									out.println(" <img src=\"images/exclamation.png\" />");
								out.println("  </a>");	%>																	
						</c:if>						</td>
						
						<td class="mntCenter">
						
							<% tmpProperty = bpcsListItemArray + "actionCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
									 onchange="<%=onChangeCall%>"
									 onfocus="<%=onFocusCall%>"
									 onkeyup="return autoTab(this, 1, event);" />
							<% //Sridevi.K End of code to print the line numbers %>
							
						</td>

				<td width="62" colspan="1" class="mntCenter"><% //tmpProperty is initialized here as per the column rptAff %>
				  <% tmpProperty = bpcsListItemArray + "rptAff" ; %>
				 
								

                <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 4, event);"
								       onblur="checkPadLeft(this,'0',4);" />  </td>

						<td width="60" colspan="1" class="mntCenter">
							<% //tmpProperty is initialized here as per the column supAff %>
							<% tmpProperty = bpcsListItemArray + "supAff" ; %>
							
							
							
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
							 		   onblur="checkPadLeft(this,'0',4);" /> 						</td>

						<td width="57" colspan="1" class="mntCenter">
						
						
							<%//tmpProperty is initialized here as per the column invCode of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);" /> 						</td>
						<td width="72" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column list of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.list" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" /> 						</td>
						<td width="70" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column label of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.label" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" /> 						</td>

						<td width="64" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column size of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.size" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" /> 					</td>

						<td width="62" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column pack of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.pack" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" /> 						</td>

						<td width="65" colspan="1" class="mntCenter">
						
						
							<%//tmpProperty is initialized here as per the column billPrice%>
							<% tmpProperty = bpcsListItemArray + "billPrice" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
									   onchange="<%=onChangeCall%>"
									   onblur="alertLength(this,10);" 
									   onkeyup="return autoTab(this, 15, event);" />					</td>

						<td width="78" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column BpCurCode%>
							<% tmpProperty = bpcsListItemArray + "bpCurCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 5, event);" /> 						</td>

						<td width="47" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column freezeCost%>
							<% tmpProperty = bpcsListItemArray + "freezeCost" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"/> 						</td>
						 
						  <td width="78" class="mntCenter" colspan="1">
						  <%//tmpProperty is initialized here as per the column costPrice %>
                            <% tmpProperty = bpcsListItemArray + "costPrice" ; %>
                            <html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
									   onchange="<%=onChangeCall%>" 
									   onblur="alertLength(this,10);" 
									   onkeyup="return autoTab(this, 15, event);" />  </td>

						  <td class="mntCenter" colspan="1">						 
						  <%//tmpProperty is initialized here as per the column costCurCode %>
                            <% tmpProperty = bpcsListItemArray + "costCurCode" ; %>
                            <html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 5, event);" />  </td>

						  <td class="mntCenter" colspan="1">
						  <%//tmpProperty is initialized here as per the column begPeriod %>
                            <% tmpProperty = bpcsListItemArray + "begPeriod" ; %>
                           
							
							
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
									onchange="<%=onChangeCall%>"
									onkeyup="return autoTab(this, 2, event);" /> </td>
						  <td class="mntCenter">
						  
						  <%//tmpProperty is initialized here as per the column EndPeriod %>
						   <% tmpProperty = bpcsListItemArray + "endPeriod" ; %>
						  <html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
									onchange="<%=onChangeCall%>"
									onkeyup="return autoTab(this, 2, event);" /> </td>
						<td width="57" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column selected%>
							<% tmpProperty = bpcsListItemArray + "selected" ; %>
							<html:checkbox property="<%=tmpProperty%>" styleClass="mntWidth1" /> 				</td>

					</abbott:row>

				<%// End of the iterations of the bpcsList%>
			  </c:forEach>
				<% //Sridevi.K end of replacement of code for nested iterate tag %>
			<%// End - Subf Data Portion for Original flds	%>
			</nested:notEqual>
			<tr>
				<td colspan="16" class="right">
					<a href="javascript:checkSave(document.bpcsForm,'savebatch','bpcsBatchSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.bpcsForm,'clearbatch','bpcsBatchMaint.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			</tr>		
			
	</table>
	<nested:equal property="bpcsListSize" value="0">
		<%@ include file="/include/recordsNotFound.jsf" %>
	</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
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
			for(i = 0; i < bpcsForm.bpcsListSize.value; i++) {
				var element = "bpcsListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcsForm.elements.length; j++) {
						if(bpcsForm.elements[j].name == element){
							if(bpcsForm.elements[j].checked == true ) {
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
	<%//Sridevi.K 7-16-05 End script..%>
	
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < bpcsForm.bpcsListSize.value; i++) {
				var element = "bpcsListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcsForm.elements.length; j++) {
						if(bpcsForm.elements[j].name == element){
							if(bpcsForm.elements[j].checked == true ) {
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
			alert( 'You must select at least one row to Save' );
		}	
	}
	<%//Sridevi.K 7-16-05 End of script..%>					
	function copyToNextRow(rowNumber)
	{
			if (rowNumber !=0 )
			{
			    var rowNumberFrom = rowNumber - 1;
			    var elementFrom   = 'bpcsListItem[' + rowNumberFrom +'].';
   			    var elementTo     = 'bpcsListItem[' + rowNumber +'].';
			    document.getElementById(elementTo + 'actionCode').value = document.getElementById(elementFrom + 'actionCode').value;
   			    document.getElementById(elementTo + 'rptAff').value = document.getElementById(elementFrom + 'rptAff').value;
   			    document.getElementById(elementTo + 'supAff').value = document.getElementById(elementFrom + 'supAff').value;
   			    document.getElementById(elementTo + 'supProduct.invCode').value = document.getElementById(elementFrom + 'supProduct.invCode').value;
   			    document.getElementById(elementTo + 'supProduct.list').value = document.getElementById(elementFrom + 'supProduct.list').value;
   			    document.getElementById(elementTo + 'supProduct.label').value = document.getElementById(elementFrom + 'supProduct.label').value;
   			    document.getElementById(elementTo + 'supProduct.size').value = document.getElementById(elementFrom + 'supProduct.size').value;
   			    document.getElementById(elementTo + 'supProduct.pack').value = document.getElementById(elementFrom + 'supProduct.pack').value;
   			    document.getElementById(elementTo + 'billPrice').value = document.getElementById(elementFrom + 'billPrice').value;
   			    document.getElementById(elementTo + 'bpCurCode').value = document.getElementById(elementFrom + 'bpCurCode').value;
   			    document.getElementById(elementTo + 'freezeCost').value = document.getElementById(elementFrom + 'freezeCost').value;
   			    document.getElementById(elementTo + 'costPrice').value = document.getElementById(elementFrom + 'costPrice').value;
   			    document.getElementById(elementTo + 'costCurCode').value = document.getElementById(elementFrom + 'costCurCode').value;
   			    document.getElementById(elementTo + 'begPeriod').value = document.getElementById(elementFrom + 'begPeriod').value;
   			    document.getElementById(elementTo + 'endPeriod').value = document.getElementById(elementFrom + 'endPeriod').value;
 
 		    
			}
	}
	</script>
<%@ include file="/include/footer.jsf" %>