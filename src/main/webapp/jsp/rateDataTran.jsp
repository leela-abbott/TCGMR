<%! String pageTitle = "Rate Data Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="rateDataTranForm" scope="session" class="abbott.ai.tcgm.action.form.RateDataTranForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadRate.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
<nested:form method="post" name="rateDataTranForm" type="abbott.ai.tcgm.action.form.RateDataTranForm" action="/rateDataTranMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<%//Sridevi.K code modified to fix to toggle between the order of the data in a row%>
		<nested:nest property="sortObject">
			<nested:hidden property="sortColumn" />
			<nested:hidden property="sortOrder" />
		</nested:nest>
		<%//Begin code for filter row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td>Act<br>Code</td>
				<td>Rate</td>				
				<td>Cur<br>Cd</td>
				<td>Beg<br>Period</td>
				<td>End<br>Period</td>
				<td>Pub<br>Flag</td>
				<td>User Id<br>Flag</td>
				<td width="40%%" class="bgWhiteRight">&nbsp;</td>
			</tr>
			<nested:nest property="searchObject">
				<nested:nest property="rateData">
					<nested:hidden property="modelId" />
					<nested:hidden property="datasetTableId" />
				</nested:nest>
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onkeyup="return autoTab(this,1,event);"/>
					</td>
					<td>
						<nested:text property="rate" maxlength="15" styleClass="fltrWidth15"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onblur="alertLength(this,9);" 
									 onkeyup="return autoTab(this,15,event);"/>
					</td>
					<td>
						<nested:text property="rateData.curCode" maxlength="5" styleClass="fltrWidth5"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onkeyup="return autoTab(this,5,event);"/>
					</td>
					<td>
						<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onkeyup="return autoTab(this,2,event);"/>
					</td>
					<td>
						<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"
									 onkeyup="return autoTab(this,2,event);"/>
					</td>
					<td>
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeFilterDirty('pagingDiv','red','bold');"/>
					</td>
					<td >
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="makeFilterDirty('pagingDiv','red','bold');" >
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
					</td>					
					<td class="bgWhiteRight">
					<a href="javascript:changeCmdAndSubmit(document.rateDataTranForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.rateDataTranForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>
			</nested:nest>
		</table>
		<hr />
		<%//Begin code for add new row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td>Act<br>Code</td>
				<td>Rate</td>				
				<td>Cur<br>Cd</td>
				<td>Beg<br>Period</td>
				<td>End<br>Period</td>
				<td width="40%" class="bgWhiteRight">&nbsp;</td>
			</tr>
			<nested:nest property="addNew">
				<nested:nest property="rateData">
					<nested:hidden property="modelId" />
					<nested:hidden property="datasetTableId" />
				</nested:nest>
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
									 onkeyup="return autoTab(this,1,event);"/>
					</td>
					<nested:nest property="rateData">					
					<td>
						<nested:text property="rate" maxlength="15" styleClass="fltrWidth15"
									 onchange="makeAddNewDirty();"
									 onblur="alertLength(this,9);" 
									 onkeyup="return autoTab(this,15,event);"/>
					</td>
					<td>
						<nested:text property="curCode" maxlength="5" styleClass="fltrWidth5"
									 onchange="makeAddNewDirty();"
									 onkeyup="return autoTab(this,5,event);"/>
					</td>
					<td>
						<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
									 onchange="makeAddNewDirty();"
									 onkeyup="return autoTab(this,2,event);"/>
					</td>
					<td>
						<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
									 onchange="makeAddNewDirty();"
									 onkeyup="return autoTab(this,2,event);"/>
					</td>
					</nested:nest>					
					<td class="bgWhiteRight">
						<a href="javascript:chgActCmdSubmit(document.rateDataTranForm,'add','rateDataTranSave.do');" >
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="javascript:chgActCmdSubmit(document.rateDataTranForm,'massupdate','rateDataTranSave.do');">
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="javascript:chgActCmdSubmit(document.rateDataTranForm,'clearaddnew','rateDataTranMaint.do');" >
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				</tr>
			</nested:nest>
		</table>

		<hr/>

		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/rateDataTranPaging.jsf" %></div>

		<table width="780" cellspacing="0" >
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_ACD%>');" >
						Act<br>Code<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_ACD%>" >
							<img alt="<%=rateDataTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_RATE%>');" >
						Rate<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RATE%>" >
							<img alt="<%=rateDataTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>				
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_CUR_CD%>');" >
						Cur<br>Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_CUR_CD%>" >
							<img alt="<%=rateDataTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_BEG_PERIOD%>');" >
						Beg<br>Period<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_BEG_PERIOD%>" >
							<img alt="<%=rateDataTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_END_PERIOD%>');" >
						End<br>Period<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_END_PERIOD%>" >
							<img alt="<%=rateDataTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.rateDataTranForm,'<%=DBConst.COL_PUBLISH_FLAG%>');" >
						Pub<br>Flag<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_PUBLISH_FLAG%>" >
							<img alt="<%=rateDataTranForm.getSortObject().getSortImgAltTxt()%>" src="<%=rateDataTranForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('rateDataTranListItem','rateData.selected','<%=rateDataTranForm.getRateDataTranListSize()%>');" />
				</td>
			</tr>
	<nested:hidden property="rateDataTranListSize" />
	<nested:notEqual property="rateDataTranListSize" value="0">
	<%//Sridevi.K code added to replace the nested iterate tag with the JSTL tags%>
		<% int rowNumber = 0; %>
		
		<%//used the JSTL c:forEach tag to loop through rateDataTranList %>
		<c:forEach items="${sessionScope.rateDataTranForm.rateDataTranList}"
			   	   var="rateDataTranBean"
			  	   varStatus="rateDataTranStatus">				

		<% //define the common part of the property tag of html in another string %>
		<% String rateDataTranListItemArray = "rateDataTranListItem[" + rowNumber +"]."; %>
			
		<% //declare a String to notify when there is a change %>
		<%String onChangeCall = "makeEditDirty('" + "rateDataTranListItem[" + rowNumber++ + "].rateData.selected" + "');";%>

		<% //tmpProperty is given a null to set its values compatible to the property%>
		<% String tmpProperty = "" ; %>

		<%//Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>
		<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
		<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
		<%//Sridevi.K end..%>

		<td class="mntCenter">
			<% //Sridevi.K Code modified to print the record numbers %>
			<c:out value="${sessionScope.rateDataTranForm.pagingFilter.startRecord + rateDataTranStatus.index}"/>
			<%//Sridevi.K End..%>
		
			<% //Using 'c:if' to check if the rateDataTranBean msg is not equal to "  " %>
			<c:if test="${rateDataTranBean.rateData.msg ne ''}" >				
				<a class="error"
			  	   href="#"
				   id="anchor<c:out value="${rateDataTranStatus.rateData.index}"/>"
				   name="anchor<c:out value="${rateDataTranStatus.rateData.index}"/>"				
				   onclick="return false;"
 				   onmouseover="showMsgPopup('anchor<c:out value="${rateDataStatus.rateData.index}"/>', '<c:out value="${rateDataBean.rateData.msg}"/>');"
				   onmouseout='hideMsgPopup();' >
				   <img src="images/exclamation.png" />	</a>
			</c:if>		
			<% //tmpProperty is initialized here as per the column actionCode %>
			<% tmpProperty = rateDataTranListItemArray + "actionCode" ; %>	
			<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
		     	       onchange="<%=onChangeCall%>"
 		               onkeyup="return autoTab(this, 1, event);" />
		</td>
		<td class="mntCenter">
			<% //tmpProperty is initialized here as per the column rateData.rate %>
			<% tmpProperty = rateDataTranListItemArray + "rateData.rate" ; %>	
			<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth15"
					   onblur="alertLength(this,9);" 
			     	   onchange="<%=onChangeCall%>"/>
		</td>						
		<td class="mntCenter">
			<% //tmpProperty is initialized here as per the column rateData.curCode %>
			<% tmpProperty = rateDataTranListItemArray + "rateData.curCode" ; %>	
			<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
			     	   onchange="<%=onChangeCall%>"
			      	   onkeyup="return autoTab(this,5,event);" />
		</td>
		<td class="mntCenter">
			<% //tmpProperty is initialized here as per the column rateData.begPeriod %>
			<% tmpProperty = rateDataTranListItemArray + "rateData.begPeriod" ; %>	
			<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
			     	   onchange="<%=onChangeCall%>"								                                     onkeyup="return autoTab(this,2,event);" />
		</td>
		<td class="mntCenter">
			<% //tmpProperty is initialized here as per the column rateData.endPeriod %>
			<% tmpProperty = rateDataTranListItemArray + "rateData.endPeriod" ; %>	
			<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
	               	   onchange="<%=onChangeCall%>"
		               onkeyup="return autoTab(this,2,event);" />
		</td>
		<td class="mntCenter">
			<% //tmpProperty is initialized here as per the column publishFlag %>
			<% tmpProperty = rateDataTranListItemArray + "publishFlag" ; %>	
			<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1" disabled="true"/>
		</td>
		<% //nesting of property rateData> %>
		<td class="mntCenter">
			<% //tmpProperty is initialized here as per the column rateData.selected %>
			<% tmpProperty = rateDataTranListItemArray + "rateData.selected" ; %>	
			<html:checkbox property="<%=tmpProperty%>" />
		</td>
		<% //End of nesting of rateData %>
	</abbott:row>
	<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
		<td class="mntCenter">
			Rates 1-6<br>Rates 7-12
		</td>
		<td colspan="16">
		<table width="100%" cellspacing="0">
		<tr>		
			<% //Using c:forEach for looping the rates starting form 0 to 5 %>
			<c:forEach items="${rateDataTranBean.rateData.rates}" 
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
			<%//Using c:forEach for looping the rates starting form 6 to 11 %>				
			<c:forEach items="${rateDataTranBean.rateData.rates}"
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
 <%//Sridevi.K end of code modification for replacing the nested iterate tag with JSTL tag%>
 
 </nested:notEqual>
 </table>
  <nested:equal property="rateDataTranListSize" value="0">
  	<p class="recordsNotFound">
		No Records Found Matching Filter Criteria
 	</p>
 </nested:equal>
 <hr />
 </nested:form>
 <script language="JavaScript1.2" type="text/javascript">
	 setFocus('addNew.actionCode');
	 showObj('navigation');

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
				for(i = 0; i < rateDataTranForm.rateDataTranListSize.value; i++) {
				
					var element = "rateDataTranListItem[" + i + "].rateData.selected";				
					if(!rowSelected){
					
						for(j = 0; j < rateDataTranForm.elements.length; j++) {
						
							  if(rateDataTranForm.elements[j].name == element){

								  if(rateDataTranForm.elements[j].checked == true ) {
								
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
				for(i = 0; i < rateDataTranForm.rateDataTranListSize.value; i++) {	
						
					var element = "rateDataTranListItem[" + i + "].rateData.selected";				
					if(!rowSelected){		
							
						for(j = 0; j < rateDataTranForm.elements.length; j++){
						
							if(rateDataTranForm.elements[j].name == element){
							
								if(rateDataTranForm.elements[j].checked == true ) {							
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
				for(i = 0; i < rateDataTranForm.rateDataTranListSize.value; i++) {
					var element = "rateDataTranListItem[" + i + "].rateData.selected";				
					if(!rowSelected){
						for(j = 0; j < rateDataTranForm.elements.length; j++) {
							if(rateDataTranForm.elements[j].name == element){
								if(rateDataTranForm.elements[j].checked == true ) {
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