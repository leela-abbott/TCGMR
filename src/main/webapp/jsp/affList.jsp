<%@ page import="abbott.ai.tcgm.entities.AffAreaDivsion" %>
<%! String pageTitle = "Affiliate Area UnWanted Division"; %>
<%@ include file="/include/header.jsf" %>
<abbott:securePage	userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
	requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
	comparisonType="="
	forwardPage="/insufficientPrivelage.do" />

<jsp:useBean id="affCodeForm" scope="session" class="abbott.ai.tcgm.action.form.AffAreaDivForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
 
	<nested:form method="post" name="affCodeForm" type="abbott.ai.tcgm.action.form.AffAreaDivForm" action="/affAreaMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="affcode" />
		<table cellspacing="0" width="800">
			<tr>
				<td class="tableEntry" width="400">Affiliate</td>
				<td class="tableEntry" width="400">Area</td>
				<td class="tableEntry" width="400">Region</td>
				<td class="tableEntry" width="400">Sector</td>
			</tr>
			<tr>
				<td class="mntLeft" width="400">
				
				<html:select name="affCodeForm" property="affListCode" size="1" onchange='javascript:setFilter();' styleClass="mntLeft">
						  <option value="-1">Select Affiliate</option>
					  	<%  
					  	   AffAreaDivsion affUser=null;
					  	   for (int i=0; i<affCodeForm.getAffCodeList().size(); i++) {
					  	   affUser=(AffAreaDivsion)affCodeForm.getAffCodeList().get(i);
					  	   
					  	%>
						  <option value="<%=affUser.getAffCode()%>"><%=affUser.getAffDesc()%></option>
						  <%  } %>
			   </html:select>
				</td>
				<td width="400" class="mntLeft">
					<label id="areaLab"> </label> 
				</td >
				<td width="400" class="mntLeft">
					<label id="regLab"> </label> 
				</td>
				<td width="400" class="mntLeft">
					<label id="secLab"> </label> 
				</td>
			</tr>
			<tr>
				<td colspan="5" class="right">
					<font color="red" size="1">Note:The Save Button Makes the selected record as UnWanted</font>
				</td>
			</tr>
			<tr>
				<td colspan="5" class="right">
					<a href="javascript:doSave();" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:doCancel();" >
						<img src="images/btnCancel.png" alt="Cancel" /></a>
				</td>
			</tr>
			
		</table>

		<hr/>

		<table width="800" cellspacing="0">
			<tr>
				<td colspan="5" class="right">
					<font color="red" size="1">Note:The Delete Selected Button Makes the selected record as Wanted</font>
				</td>
			</tr>
			<tr>
				<td colspan="5" class="right">
					<a href="javascript:deleteCurrencyCode(document.affCodeForm,'delete','affAreaMaint.do');">
						<img src="images/btnDeleteSelected.png" alt="Delete Selected" />
					</a>
				</td>
			</tr>
			<tr class="fltrTblHdngLeft">
				<td>Affiliate</td>
				<td>Area</td>
				<td>Region</td>
				<td>Sector</td>
				<td class="center">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('affCodeList','selected','<%=affCodeForm.getAffListSize()%>');" />
				</td>
			</tr>
	
			<nested:notEqual property="affListSize" value="0">
				<% int rowNumber=0; %>
				<c:forEach var="affCodeBean" items="${affCodeForm.afflist}"  varStatus="affCodeStatus">	               			
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
						<td class="mntLeft" width="400">
							<c:out value="${affCodeBean.affDesc}" />(<c:out value="${affCodeBean.affCode}" />)
						</td>		
						<td class="mntLeft" width="400">
							<c:out value="${affCodeBean.areaDesc}" />
						</td>
						<td class="mntLeft" width="400">
							<c:out value="${affCodeBean.regDesc}" />
						</td>		
						<td class="mntLeft" width="400">
							<c:out value="${affCodeBean.secDesc}" />
						</td>
						<td class="mntCenter">
							<input type="checkbox" name="affCodeList[<c:out value="${affCodeStatus.index}"/>].selected" value="on">
						</td>
					</abbott:row>
			  		<% rowNumber++; %>			
				</c:forEach>					
			</nested:notEqual>
		</table>
		<nested:equal property="affListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>
	
<script language=javascript>
	function deleteCurrencyCode(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like make selected Affiliate code(s) to Wanted?. ") ) 
			{
				javascript:chgActCmdSubmit(document.affCodeForm,'delete','affAreaMaint.do');
			}
	}	
	function setFilter(){
	  var selVal=document.affCodeForm.affListCode.value;
	 if(selVal=="-1"){ 
		 document.getElementById('areaLab').innerHTML='';
		 document.getElementById('regLab').innerHTML='';
		 document.getElementById('secLab').innerHTML='';
	 }else{
	 	var x=selVal.split(',');
		 document.getElementById('areaLab').innerHTML=x[2];
		 document.getElementById('regLab').innerHTML=x[4];
		 document.getElementById('secLab').innerHTML=x[6];
	 }	 
	}
	function doSave(){
		 var selVal=document.affCodeForm.affListCode.value;
	 if(selVal=="-1"){ 
	 	alert("Select a Affliate to be Saved");
	 }else{
	 	 var x=selVal.split(',');
		 document.affCodeForm.affcode.value=x[0];		 
	 	javascript:chgActCmdSubmit(document.affCodeForm,'save','affAreaMaint.do');
	 }
	}
	function doCancel(){
		document.affCodeForm.affListCode.value="-1";
		document.getElementById('areaLab').innerHTML='';
		 document.getElementById('regLab').innerHTML='';
		 document.getElementById('secLab').innerHTML='';
	}
</script>
	
<%@ include file="/include/footer.jsf" %>