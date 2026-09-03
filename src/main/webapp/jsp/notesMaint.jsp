<%! String pageTitle = "Notes Data"; %>
<a name="FilterView"></a>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="notesForm" scope="session" class="abbott.ai.tcgm.action.form.NotesForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %> 
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="notesForm" type="abbott.ai.tcgm.action.form.NotesForm" action="/notesMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
			<nested:hidden property="notesErrorListSize" />
			<nested:notEqual property="notesErrorListSize" value="0">
			 	<td rowspan="2">Errors</td>
			</nested:notEqual>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Notes<br></td>
			</tr>
			<tr class="fltrTblHdng">
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<%//Sridevi.K code modified to toggle between the order of the data%>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
			<% String submitFilter = "submitFilter(document.notesForm,'filter', event);"; %>			
			<tr class="oddRowCenter">
				<nested:hidden property="notesErrorListSize" />
				<nested:notEqual property="notesErrorListSize" value="0">
					<td>
						<input type=checkbox name=errs value="on" onClick="javascript:changeCmdAndSubmit(document.notesForm,'filter');">
					</td>
				</nested:notEqual>
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
			
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
						<nested:textarea property="note" rows="2" cols="50"
							             onchange="makeFilterDirty('pagingDiv','red','bold');"
							             onkeydown = "<%=submitFilter%>"
							             onkeyup="return checkLength(this, 255);" />
					</td>
				</tr>
				<tr class="evenRowCenter">
					<td colspan="15" class="right">
					 <a href="javascript:changeCmdAndSubmit(document.notesForm,'filter');" >
					 	<img src="images/btnFilter.png" alt="Filter" /></a>
					 <a href="javascript:changeCmdAndSubmit(document.notesForm,'advancedfilter');" >
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
					 <a href="javascript:changeCmdAndSubmit(document.notesForm,'clearfilter');" >
					 	<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest>
		</table>
		<hr />

		<%//Begin code for add new row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
			   <td rowspan="2">Rpt<br>Aff</td>
			   <td rowspan="2">Inv<br>Cd</td>
			   <td colspan="4">Rpt Prod</td>
			   <td rowspan="2">Notes<br></td>
		   </tr>
		   <tr class="fltrTblHdng">
			   <td>List</td>
			   <td>Label</td>
			   <td>Size</td>
			   <td>Pack</td>
			</tr>
			<nested:nest property="addNew">
				<nested:hidden property="notes.modelId" />
				<nested:hidden property="notes.datasetTableId" />
				<% String submitSave = "submitSave(document.notesForm,'save','notesSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.notesForm,'save','notesSave.do', event);";
				}else{
					submitSave = "";
				}
				%>
				 <tr class="oddRowCenter">
					 <td>
					   <nested:notEqual property="notes.msg" value="">
						 <a class="error"
							 href="#"
							 id="anchorAddNew"
							 name="anchorAddNew"
							 onclick="return false;"
							 onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="notes.msg" />');"
							 onmouseout='hideMsgPopup();' >
						   <img src="images/exclamation.png" />
						  </a>
						 </nested:notEqual>
						 <nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
									  onchange="makeAddNewDirty();"
  									  onkeydown = "<%=submitSave%>"
									  onkeyup="return autoTab(this, 1, event);" />
						</td>
						<nested:nest property="notes">
						<td>
						<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
							         onchange="makeFilterDirty('pagingDiv','red','bold');"
							         onkeydown = "<%=submitSave%>"
							         onkeyup="return autoTab(this, 4, event);" 
							         onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="rptProduct">
						 <td>
							<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
								         onchange="makeFilterDirty('pagingDiv','red','bold');"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="return autoTab(this, 1, event);" />
						  </td>
						  <td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								         onchange="makeFilterDirty('pagingDiv','red','bold');"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="return autoTab(this, 6, event);" 
								         onblur="checkPadLeft(this,'0',6);" />
						   </td>
						   <td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								         onchange="makeFilterDirty('pagingDiv','red','bold');"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="return autoTab(this, 3, event);" 
								         onblur="checkPadLeft(this,'0',3);" />
						   </td>
						   <td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								         onchange="makeFilterDirty('pagingDiv','red','bold');"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="return autoTab(this, 3, event);" 
								         onblur="checkPadLeft(this,'0',3);" />
						   </td>
						   <td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								         onchange="makeFilterDirty('pagingDiv','red','bold');"
								         onkeydown = "<%=submitSave%>"
								         onkeyup="return autoTab(this, 4, event);" 
								         onblur="checkPadLeft(this,'0',4);" />
						   </td>
						   </nested:nest>
						   <td>
							 <nested:textarea property="note" rows="2" cols="50" 
								              onchange="makeFilterDirty('pagingDiv','red','bold');"
								              onkeydown = "<%=submitSave%>"
								              onkeyup="return checkLength(this, 255);" />
						   </td>
						</tr>
				   </nested:nest>
				</nested:nest>
				<tr>
				<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
				<td colspan="16" class="right">
					<a href="javascript:chgActCmdSubmit(document.notesForm,'save','notesSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.notesForm,'massupdate','notesSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.notesForm,'clearaddnew','notesMaint.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			<%}%>	
			</tr>
		</table>
		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/notesPaging.jsf" %></div>

		<table width="780" cellspacing="0">

			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_INV_CD%>');" >
						Inv<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_INV_CD%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">
					Rpt Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_NOTE%>');" >
						Note<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_NOTE%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				<%//Sridevi.K code modified to toggle between selectall and deselectall the rows%>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('NotesListItem','selected','<%=notesForm.getNotesListSize()%>');" />
				<% // Sridevi.K end of code modification to toggle between select and deselect all the rows%>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LIST%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
				   <a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LABEL%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_SIZE%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesForm,'<%=DBConst.COL_RPT_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_PACK%>" >
							<img alt="<%=notesForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
			</tr>

			<nested:hidden property="notesListSize" />
			<nested:notEqual property="notesListSize" value="0">
			
			<%//Sridevi.K Code modified to replace the nested iterate tag with the JSTL tags%>
				<% int rowNumber = 0; %>
				
				 <%// used the JSTL c:forEach tag to loop through asrList %>
				 <c:forEach items="${sessionScope.notesForm.notesList}" 
				       	    var="notesBean" 
			           	    varStatus="notesStatus" >	         
			        				           
					<% //declare a String to notify when there is a change %>			
			 		<% String onChangeCall = "makeEditDirty('" + "notesListItem[" + rowNumber + "].selected" + "');"; %>
			 						 				
			 		<% //define the common part of the property tag of html in another string %>
			 		<% String notesListItemArray="notesListItem[" + rowNumber + "].";%>
			 									
			 		<% //String href encapsulates the call to a JavaScript copyRow %>
			 		<% String href = "javascript:copyRow(document.notesForm,'" + rowNumber++ + "','notesMaint.do');"; %>
			 				 				 	
			 		<% //tmpProperty is given a null to set its values compatible to each property%>
			 		<% String tmpProperty=""; %>	
					
					<%// Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>	
			  		<% //abbott is a custom tag to give some coloring effect to the alternate rows %>	
			  		<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
			  		<%//Sridevi.K End of modifications to the Abbott custom tag to work with out the nested iterate tag %>
			  	
					<td class="mntCenter">
						<% //Sridevi.K code added to print the line numbers %>
						<c:out value="${sessionScope.notesForm.pagingFilter.startRecord + notesStatus.index}"/>
						<%//Sridevi.K end..%>
				
						<a href="<%=href%>">							
							<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
							<img src="images/btnUpArrow.png" alt="Load Row" />
						</a>
					</td>
				
					<td class="mntCenter">					 
						<c:if test="${notesBean.msg ne ''}" >
					  	  <a class="error"
					      	 href="#"
					      	 id="anchor<c:out value="${notesStatus.index}" />"
					      	 name="anchor<c:out value="${notesStatus.index}" />"
					     	 onclick="return false;"
					      	 onmouseover="showMsgPopup('anchor<c:out value="${notesStatus.index}" />', '<c:out value="${notesBean.msg}" />');"
					      	 onmouseout='hideMsgPopup();' >
					      	 <img src="images/exclamation.png" />
					  	 </a>
				  	   </c:if>			  	
				  	
				  	   <% //tmpProperty is initialized here as per the column rptAff %>				  			  	      					
					   <%tmpProperty = notesListItemArray + "rptAff" ; %>
					   <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						          onchange="<%=onChangeCall%>"
						          onkeyup="return autoTab(this, 4, event);"
						          onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				  </td>
				
				 <td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rptProduct.invCode %>				  			  	      					
					<%tmpProperty = notesListItemArray + "rptProduct.invCode" ; %>
					<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
					           onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rptProduct.list" %>				  			  	      					
					<%tmpProperty = notesListItemArray + "rptProduct.list" ; %> 
					<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
						       onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 6, event);"
						       onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rptProduct.label" %>
					<%tmpProperty = notesListItemArray + "rptProduct.label" ; %> 
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						       onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 3, event);"
						       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rptProduct.size" %>
					<%tmpProperty = notesListItemArray + "rptProduct.size" ; %> 
					<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
						       onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 3, event);"
						       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rptProduct.pack" %>
					<%tmpProperty = notesListItemArray + "rptProduct.pack" ; %> 
					<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
						       onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 4, event);"
						       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column rptProduct.note" %>
                   	<%tmpProperty = notesListItemArray + "note" ; %> 
					<html:textarea property="<%=tmpProperty%>" rows="2" cols="50"								   
						           onchange="<%=onChangeCall%>"							
						           onkeyup="return autoTab(this, 50, event);" />
				</td>
				<td class="mntCenter">
					<% //tmpProperty is initialized here as per the column selected" %>
					<%tmpProperty = notesListItemArray + "selected" ; %> 
					<html:checkbox property="<%=tmpProperty%>" />
				</td>
			  </abbott:row>
			</c:forEach>
			<%//Sridevi.K code modification to replace the nested iteratag ends..%>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/notesPaging.jsf" %>
				</div>
		</nested:notEqual>
	</table>
	<nested:equal property="notesListSize" value="0">
		<%@ include file="/include/recordsNotFound.jsf" %>
	</nested:equal>
	<hr />
 </nested:form>
<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=notesForm.getFocusField()%>');	
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
			for(i = 0; i < notesForm.notesListSize.value; i++) {
				var element = "notesListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < notesForm.elements.length; j++) {
						if(notesForm.elements[j].name == element){
							if(notesForm.elements[j].checked == true ) {
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
	<% //Srideivi.K 7-16-05 End of script added to alert the user if he clicks rowSelected without selecting any row%>		
	
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < notesForm.notesListSize.value; i++) {
				var element = "notesListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < notesForm.elements.length; j++) {
						if(notesForm.elements[j].name == element){
							if(notesForm.elements[j].checked == true ) {
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