<%! String pageTitle = "Notes Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="notesTranForm" scope="session" class="abbott.ai.tcgm.action.form.NotesTranForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>

	<nested:form method="post" name="notesTranForm" type="abbott.ai.tcgm.action.form.NotesTranForm" action="/notesTranMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Inv<br>Cd</td>
				<td colspan="4">Rpt Prod</td>
				<td rowspan="2">Notes<br></td>
				<td rowspan="2">Pub<br>Flag</td>
				<td rowspan="2">User<br>Id</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<% //Sridevi.K Code modified to fix the toggle between the order of the data in a row %>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" />						
			<nested:nest property="searchObject">
			<% String submitFilter = "submitFilter(document.notesTranForm,'filter', event);"; %>			
			   <nested:nest property="notes">
					<nested:hidden property="modelId" />
					<nested:hidden property="datasetTableId" />
				</nested:nest>					
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onkeydown = "<%=submitFilter%>"
									 onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="notes">					
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
										 onkeyup="return autoTab(this, 50, event);" />							
					</td>
					<td rowspan="2">
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeFilterDirty('pagingDiv','red','bold');" 
									 onkeydown = "<%=submitFilter%>" />
					</td>
					<td colspan="2" rowspan="2">
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. Start
							-->
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="makeFilterDirty('pagingDiv','red','bold');" >
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. End
							-->
					</td>
				</tr>
				<tr class="evenRowCenter">
					<td colspan="15" class="right">
					 <a href="javascript:changeCmdAndSubmit(document.notesTranForm,'filter');" >
					 <img src="images/btnFilter.png" alt="Filter" /></a>
					 <a href="javascript:changeCmdAndSubmit(document.notesTranForm,'advancedfilter');" >
					 <img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>					 
					 <a href="javascript:changeCmdAndSubmit(document.notesTranForm,'clearfilter');" >
					 <img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
				</nested:nest>
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
			   <nested:nest property="notes">
					<nested:hidden property="modelId" />
					<nested:hidden property="datasetTableId" />
				</nested:nest>
				<% String submitAdd = "submitAdd(document.notesTranForm,'add','notesTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
						submitAdd = "submitAdd(document.notesTranForm,'add','notesTranSave.do', event);";
					}else{
						submitAdd = "";
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
							onkeydown = "<%=submitAdd%>"
							onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="notes">
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="return autoTab(this, 4, event);" 
										 onblur="checkPadLeft(this,'0',4);" />								
						</td>
						<nested:nest property="rptProduct">
						 	<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 6, event);" 
											 onblur="checkPadLeft(this,'0',6);"/>									
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 3, event);" 
										 	 onblur="checkPadLeft(this,'0',3);" />									
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 3, event);" 
											 onblur="checkPadLeft(this,'0',3);" />									
							</td>	
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeFilterDirty('pagingDiv','red','bold');"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 4, event);" 
											 onblur="checkPadLeft(this,'0',4);" />									
							</td>
						</nested:nest>
						<td>
							<nested:textarea property="note" rows="2" cols="50"
										 	 onchange="makeFilterDirty('pagingDiv','red','bold');"
										 	 onkeydown = "<%=submitAdd%>"
											 onkeyup="return autoTab(this, 50, event);" />								 
						</td>
					</tr>
				</nested:nest>
			</nested:nest>
			<tr>
			<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
				<td colspan="16" class="right">
					<a href="javascript:chgActCmdSubmit(document.notesTranForm,'add','notesTranSave.do');" >
						<img src="images/btnAdd.png" alt="Add" /></a>
					<a href="javascript:chgActCmdSubmit(document.notesTranForm,'massupdate','notesTranSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.notesTranForm,'clearaddnew','notesTranMaint.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>
				</td>
			<%}%>	
			</tr>
		</table>
		<hr />
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/notesTranPaging.jsf" %></div>
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_ACD%>');" >
						Act<br>Code<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_ACD%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_INV_CD%>');" >
						Inv<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_INV_CD%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td colspan="4">
					Rpt Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_NOTE%>');" >
						Note<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_NOTE%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');" >
						Pub<br>Flag<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_PUBLISH_FLAG%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				<%// Sridevi.K to toggle between the select and deselect all the rows %>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('notesTranListItem','notes.selected','<%=notesTranForm.getNotesTranListSize()%>');" />
				<%// Sridevi.K end of code modification. %>
				</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="">&nbsp;</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LIST%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
				   <a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_LABEL%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_SIZE%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.notesTranForm,'<%=DBConst.COL_RPT_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_PACK%>" >
							<img alt="<%=notesTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=notesTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
			</tr>
			<nested:hidden property="notesTranListSize" />
			<nested:notEqual property="notesTranListSize" value="0">
			
			<%//Sridevi.K code added to replace the nested iterate tag with the JSTL tags%>
			<% int rowNumber = 0; %>
	
			<%//used the JSTL c:forEach tag to loop through notesTranList %>
			<c:forEach items="${sessionScope.notesTranForm.notesTranList}"
			           var="notesTranBean"                 
			           varStatus="notesTranStatus">

			<% //define the common part of the property tag of html in another string %>
			<% String notesTranListItemArray = "notesTranListItem[" + rowNumber +"]."; %>

			<% //declare a String to notify when there is a change %>              
			<% String onChangeCall = "makeEditDirty('" + "notesTranListItem[" + rowNumber++ + "].notes.selected" + "');"; %>

			<% //tmpProperty is given a null to set its values compatible to the property%>
			<% String tmpProperty = "" ; %>
			
			<%//Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>
			<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
			<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
			<% // Sridevi.K End..%>
			<td>&nbsp;</td>
			
			<td class="mntCenter">
				<% //Sridevi.K code added to print the line numbers %>
				<c:out value="${sessionScope.notesTranForm.pagingFilter.startRecord + notesTranStatus.index}"/>
				<%//sridevi.K end of code to print the line numbers%>
										
				<% //Using 'c:if' to check if the notesTranBean.notes.msg is not equal to "  " %>
				<c:if test="${notesTranBean.notes.msg ne ''}" >			   		
			   		<a class="error"
			   		   href="#"
			   		   id="anchor<c:out value="${notesTranStatus.index}"/>"
			   		   name="anchor<c:out value="${notesTranStatus.index}"/>"				
			   		   onclick="return false;"
			           onmouseover="showMsgPopup('anchor<c:out value="${notesTranStatus.index}"/>', '<c:out value="${notesTranBean.notes.msg}"/>');"
			           onmouseout='hideMsgPopup();' >
			           <img src="images/exclamation.png" />	
					</a>
			    </c:if>&nbsp;&nbsp;

				<% //tmpProperty is initialized here as per the column actionCode %>
			 	<% tmpProperty = notesTranListItemArray + "actionCode" ; %>	
			 	<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
					       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />				
			</td>
			<% //nesting the property notes %>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.rptAff %>
			 	<% tmpProperty = notesTranListItemArray + "notes.rptAff" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
					       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 4, event);"
					       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
			</td>
			
			<%//nesting the property rptProduct with in notes %>			
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.rptProduct.invCode %>
			 	<% tmpProperty = notesTranListItemArray + "notes.rptProduct.invCode" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
			   		       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />
			</td>
		 	<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.rptProduct.list %>
			 	<% tmpProperty = notesTranListItemArray + "notes.rptProduct.list" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
					       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 6, event);"
					       onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
			</td>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.rptProduct.label %>
			 	<% tmpProperty = notesTranListItemArray + "notes.rptProduct.label" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
					       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 3, event);"
					       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
			</td>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.rptProduct.size %>
			 	<% tmpProperty = notesTranListItemArray + "notes.rptProduct.size" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
			  		       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 3, event);"
					       onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
			</td>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.rptProduct.pack %>
			 	<% tmpProperty = notesTranListItemArray + "notes.rptProduct.pack" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
					       onchange="<%=onChangeCall%>"
					       onkeyup="return autoTab(this, 4, event);"
					       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
			</td>			
			<% //End nesting of rptProduct %>
			
			<%//nesting the property notes %>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column notes.note %>
			 	<% tmpProperty = notesTranListItemArray + "notes.note" ; %>	
				<html:textarea property="<%=tmpProperty%>" rows="2" cols="50"
					           onchange="<%=onChangeCall%>"
						       onkeyup="return autoTab(this, 50, event);" />														   
			</td>
			<% // End nesting of notes %>

			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column publishFlag %>
			 	<% tmpProperty = notesTranListItemArray + "publishFlag" ; %>	
				<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1" disabled="true" />
			</td>

			<% //nesting the property notes %>
			<td class="mntCenter">
				<% //tmpProperty is initialized here as per the column publishFlag %>
			 	<% tmpProperty = notesTranListItemArray + "notes.selected" ; %>	
				<html:checkbox property="<%=tmpProperty%>" />
			</td>
			<% // End nesting of notes %>
		</abbott:row>
		</c:forEach>	
		<%//Sridevi.K end of modification to replace the nested iterate tag with the JSTL tags %>
			<div name="navigation" id="navigation" class="hidden">
				<%@ include file="/include/notesTranBtmPaging.jsf" %>
			</div>
		
		</nested:notEqual>
		</table>
		<nested:equal property="notesTranListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />		
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
	/**
	*
	*/
	<%//Sridevi.K Script added to alert the user if he clicks rowselected without selecting any row%>
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
				for(i = 0; i < notesTranForm.notesTranListSize.value; i++) {			
					var element = "notesTranListItem[" + i + "].notes.selected";				
					if(!rowSelected){				
						for(j = 0; j < notesTranForm.elements.length; j++) {
							if(notesTranForm.elements[j].name == element){
								if(notesTranForm.elements[j].checked == true ) {							
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
	function checkPublish(form,cmd,action) {

			var rowSelected=false;
			if ( cmd == 'publishall' || cmd == 'unpublishall'){		
				rowSelected=true;
			}
			else {
				for(i = 0; i < notesTranForm.notesTranListSize.value; i++) {			
					var element = "notesTranListItem[" + i + "].notes.selected";				
					if(!rowSelected){				
						for(j = 0; j < notesTranForm.elements.length; j++) {
							if(notesTranForm.elements[j].name == element){
								if(notesTranForm.elements[j].checked == true ) {							
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
			for(i = 0; i < notesTranForm.notesTranListSize.value; i++) {
				var element = "notesTranListItem[" + i + "].notes.selected";				
				if(!rowSelected){
					for(j = 0; j < notesTranForm.elements.length; j++) {
						if(notesTranForm.elements[j].name == element){
							if(notesTranForm.elements[j].checked == true ) {
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
<%//Sridevi.K End of script to alter the user %>
<%@ include file="/include/footer.jsf" %>