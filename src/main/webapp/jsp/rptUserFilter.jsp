<%! String pageTitle = "Report User Search"; %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:disableList();">
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="userForm" scope="session" class="abbott.ai.tcgm.action.form.RptUserForm" />
<bean:define id="TCGMUser" name="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<script language="JavaScript" src="include/sorttable.js" type=text/javascript></script>
<SCRIPT type="text/javascript" language="JAVASCRIPT">
function disableList()
	{	
	   	document.getElementById("areaCode").disabled=true;
	   	document.getElementById("affCode").disabled=true;
	   	document.getElementById("secCode").disabled=true;
	  	  
	}
function enableList(){
//alert(document.getElementById("role").value);
		if (document.getElementById("role").value=='Area')
	  	{
	  		document.getElementById("areaCode").disabled=false;
		   	document.getElementById("affCode").value=-1;
		   	document.getElementById("affCode").disabled=true;
		   	document.getElementById("secCode").value=-1;		   	
		   	document.getElementById("secCode").disabled=true;
		   	document.getElementById("division").value=-1;
		   	document.getElementById("division").disabled=false;		   	
	  	}
	  	else if (document.getElementById("role").value=='Affiliate')
	  	{
		  	document.getElementById("areaCode").value=-1;	  	
	  		document.getElementById("areaCode").disabled=true;
		   	document.getElementById("affCode").disabled=false;
		    document.getElementById("secCode").value=-1;
		   	document.getElementById("secCode").disabled=true;
		   	document.getElementById("division").value=-1;
		   	document.getElementById("division").disabled=false;		   			   	
	  	}
	  	else if (document.getElementById("role").value=='Sector')
	  	{
		  	document.getElementById("areaCode").value=-1;	  	
	  		document.getElementById("areaCode").disabled=true;
		   	document.getElementById("affCode").value=-1;	  		
		   	document.getElementById("affCode").disabled=true;
		   	document.getElementById("secCode").disabled=false;
		   	document.getElementById("division").value=-1;		   	
		   	document.getElementById("division").disabled=false;		   	
	  	}else if(document.getElementById("role").value=='D')
	  	{
		  	document.getElementById("areaCode").value=-1;
		   	document.getElementById("affCode").value=-1;
		   	document.getElementById("secCode").value=-1;
		   	document.getElementById("division").value=-1;

	  		document.getElementById("areaCode").disabled=true;
		   	document.getElementById("affCode").disabled=true;
		   	document.getElementById("secCode").disabled=true;
		   	document.getElementById("division").disabled=false;		   			   	
	  	}else {
		  	document.getElementById("areaCode").value=-1;
		   	document.getElementById("affCode").value=-1;
		   	document.getElementById("secCode").value=-1;
		   	document.getElementById("division").value=-1;

	  		document.getElementById("areaCode").disabled=true;
		   	document.getElementById("affCode").disabled=true;
		   	document.getElementById("secCode").disabled=true;
		   	document.getElementById("division").disabled=true;		   	
	  	}
}	

function getUsers(form, cmd, action){
		
		if(document.getElementById("role").value=='Area'||document.getElementById("role").value=='Affiliate'
			||document.getElementById("role").value=='Sector'||document.getElementById("role").value=='D'){
				if(document.getElementById("division").value=='-1'){
					alert('Please select Division');
					return false;
				}
		}
		
		
		if (document.getElementById("role").value=='Area')
	  	{
	  		if(document.getElementById("areaCode").value=='-1'){
			alert('Please select Area');
			return false;
			}
	  		form.selDesc.value = form.areaCode[form.areaCode.selectedIndex].text;

	  	}
	  	if (document.getElementById("role").value=='Affiliate')
	  	{
	  		if(document.getElementById("affCode").value=='-1'){
			alert('Please select Affiliate');
			return false;
			}
	  		form.selDesc.value = form.affCode[form.affCode.selectedIndex].text;
	  	}
	  	if (document.getElementById("role").value=='Sector')
	  	{
	  		if(document.getElementById("secCode").value=='-1'){
			alert('Please select Sector');
			return false;
			}	  	
	  		form.selDesc.value = form.secCode[form.secCode.selectedIndex].text;
	  	}
	  	//alert(form.selDesc.value);
	  	//alert(action);
		chgActCmdSubmit(form, cmd, action);
}	
function allCap(id){
var val = document.getElementById(id).value;
document.getElementById(id).value = val.toUpperCase();
}
function initCap(id){
var val = document.getElementById(id).value;
document.getElementById(id).value = val.substring(0,1).toUpperCase()+val.substring(1,val.length);
}

function confirmDelete(form, cmd, action)
		{
			if ( confirm("Are you sure that you would like to delete selected User Record(s)? ") ) 
			{
				chgActCmdSubmit(form, cmd, action);
			}
	}
	function confirmAddReprt(form, cmd, action)
	{
			var cntVar=document.getElementById("hidVal");
			var incr=0;
			if(null!=cntVar)
			{
				var cnt=cntVar.value;
				for(i=0;i<cnt;i++)
				{
					if(document.getElementById("userlist["+i+"].selected").checked)
					{
						incr++;
					}
				}
				if(incr>0)
				{
	 					if ( confirm("Are you sure that you would like to Add selected User Record(s) to Cognos? ") ) 
						{
							chgActCmdSubmit(form, cmd, action);
						}
				}else{
				 		alert('Please Select atleast a record to perform the action.');
				}
				
			}else{
		 		alert('Atleast a record should be there to perform the operation.');
		 	}
	
				
	}	

function confirmExport(form, cmd, action)
	{
			var cntVar=document.getElementById("hidVal");
			var incr=0;
			if(null!=cntVar)
			{
				var cnt=cntVar.value;
				for(i=0;i<cnt;i++)
				{
					if(document.getElementById("userlist["+i+"].selected").checked)
					{
						incr++;
					}
				}
				if(incr>0)
				{
	 					if ( confirm("Are you sure that you would like to Export selected User Record(s)? ") ) 
						{
							chgActCmdSubmit(form, cmd, action);
						}
				}else{
				 		alert('Please Select atleast a record to perform the action.');
				}
				
			}else{
		 		alert('Atleast a record should be there to perform the operation.');
		 	}
	
				
	}	
	function removeAllOptions(selectbox)
{
	var i;
	for(i=selectbox.options.length-1;i>=0;i--)
	{
		selectbox.remove(i);
	}
}
 
 function removeList(selList)
 {
 
	 var affList = document.getElementById(selList);
	 //alert(affList.options.length);
	 for(var i=affList.options.length-1;i>=0;i--){
	 		// alert(affList.options[i].text);
 			 //alert(affList.options[i].value);
		 if(affList.options[i].selected){
				// alert(affList.options[i].text);
		 		 //alert(affList.options[i].value);
		 affList.remove(i);
		 }
 	}
 	 	
 }
 
 function addOptions(selectbox,text,value)
{
	var optn = document.createElement("OPTION");
	optn.text = trim(text);
	optn.value = trim(value);
	selectbox.options.add(optn);
}
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function callValues()
{
	if(document.getElementById('division').value!='-1' && document.getElementById('role').value=='Area'){
		retrieveURL('./tcgmAjax.do?cascadingCmd=BurstArea&cascadingVal='+document.getElementById('division').value,'userForm','areaCode');
	}
	else if(document.getElementById('division').value!='-1' && document.getElementById('role').value=='Affiliate')
	{
		retrieveURL('./tcgmAjax.do?cascadingCmd=BurstAff&cascadingVal='+document.getElementById('division').value,'userForm','affCode');
	}
	else if(document.getElementById('division').value!='-1' && document.getElementById('role').value=='Sector')
	{
		retrieveURL('./tcgmAjax.do?cascadingCmd=BurstSector&cascadingVal='+document.getElementById('division').value,'userForm','secCode');
	}
	else
	{
	removeAllOptions(document.getElementById("areaCode"));
	removeAllOptions(document.getElementById("affCode"));
	removeAllOptions(document.getElementById("secCode"));
	}
}


</SCRIPT>
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
<bean:define id="RptUser" name="RptUser" scope="session" type="abbott.ai.tcgm.entities.RptUser" />	
	<nested:form method="post" name="userForm" type="abbott.ai.tcgm.action.form.RptUserForm" action="/rptUserMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="selDesc" />

  <table width="390" align="center">
    <tr>
      <td width="108" class="commandOptionLabel"><strong><span class="mntLeft" >User ID</span>
      </strong></td>
      <%String tmpProperty = "rptUser.userid";%>
      
      <td width="171"><nested:text property="<%=tmpProperty%>" onblur="javascript:allCap('userid');" 
      						onkeydown="if(event.keyCode == 13){javascript:allCap('userid');document.getElementById('GetButton').click();}" 
      							styleClass="mntLeft"  styleId="userid"/></td>
    </tr>
     <tr>
     <%tmpProperty = "rptUser.lastName";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >Last Name</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft"  onblur="javascript:initCap('lastName');"  
      		onkeydown="if(event.keyCode == 13){javascript:initCap('lastName'); document.getElementById('GetButton').click();}"	styleId="lastName"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
    <%tmpProperty = "rptUser.firstName";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >First Name</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft"  onblur="javascript:initCap('firstName');" 
      		onkeydown="if(event.keyCode == 13){javascript:initCap('firstName'); document.getElementById('GetButton').click();}" styleId="firstName"/></td>
      <td>&nbsp;</td>
    </tr>
   
     <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Role</span></strong></td>
		 <%tmpProperty = "rptUser.role";%>
		<td>
			<html:select property="<%=tmpProperty%>" onchange="javascript:enableList()" styleId="role">
              <option value="-1">All</option>
			  <option value="HQS">HQ Supervisor</option>
              <option value="HQC">HQ Consumer</option>
              <option value="DALL">All Divisions</option>                            
              <option value="D">Division</option>
              <option value="Area">Area</option>
              <option value="Sector">Sector</option>              
              <option value="Affiliate">Affiliate</option>
	        </html:select>
         </td> 
      <td>&nbsp;</td>
    </tr>
<bean:define id="divCollection" name="RptUser" property="div" type="java.util.HashMap"/>
     <tr>
         <td class="commandOptionLabel"><strong><span class="mntLeft">Division</span></strong></td>
		 <%tmpProperty = "rptUser.division";%>
		<td>
			<html:select property="<%=tmpProperty%>" styleId="division" onchange="javascript:callValues()">
            <!--  <option value="All">All</option> -->
              <option value="-1">Select One</option>
              <html:options property="value" labelProperty="key" collection="divCollection" />
                         </html:select>
         </td> 
         <td>&nbsp;</td>
    </tr>
     <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Area</span></strong></td>
		 
		 <td>
		<bean:define id="areaCollection" name="RptUser" property="areas" type="java.util.HashMap"/>
		 <%tmpProperty = "rptUser.areaCode";%>
		<nested:select  property="<%=tmpProperty%>" styleId="areaCode"  styleClass="commandOption">
        <option value="-1">ALL</option> 
		
		</nested:select>
         </td> 
		<!-- <td>
			<html:select property="<%=tmpProperty%>" styleId="areaCode">
              <option value="-1">Select One</option>
              <option value="07">A.I.O.</option>
              <option value="05">CANADA</option>
              <option value="08">DIVISION</option>
              <option value="02">EUROPE</option>
              <option value="06">JAPAN</option>              
              <option value="01">LATIN AMERICA</option>
              <option value="04">PACIFIC ASIA AFRICA</option>
            </html:select>
         </td> -->
      <td>&nbsp;</td>
    </tr>
    
   <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Sector</span></strong></td>
		<td>
		<bean:define id="secCollection" name="RptUser" property="sectors" type="java.util.HashMap"/>
		<%tmpProperty = "rptUser.secCode";%>
		<nested:select  property="<%=tmpProperty%>" styleId="secCode" styleClass="commandOption"> 
        <option value="-1">ALL</option>

		</nested:select>
         </td> 
      <td>&nbsp;</td>
    </tr>
     <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Affiliate</span></strong></td>
		<td>
		<bean:define id="affCollection" name="RptUser" property="affiliates" type="java.util.HashMap"/>
		 <%tmpProperty = "rptUser.affCode";%>
		<nested:select  property="<%=tmpProperty%>" styleId="affCode"  styleClass="commandOption"> 
        <option value="-1">ALL</option>
		
		</nested:select>
         </td> 
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3">   <table width="200" align="center">
       <tr>
      <td align=center><label >
         <input type="button" name="searchButton" id="GetButton" value="Get" onClick = "javascript:getUsers(document.userForm,'Get','rptUserMaint.do');">
      </label></td>
    </tr>
    
<tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>
      <fieldset>
      <legend class="style11">Search Results</legend>
      <%if (TCGMUser.getRole().getName().equalsIgnoreCase("TCGM_ADMINISTRATOR") || 
      			TCGMUser.getRole().getName().equalsIgnoreCase("TCGM_RPT ADMIN")) { %>
      <table width="700" cellpadding="1" cellspacing="1"> 
  		<tr>
			<td colspan="5" class="right">
			<input type="button" name="searchButton" id="searchButton" value="ReCreate Users" onClick = "javascript:confirmAddReprt(document.userForm,'recreate','rptUserMaint.do');">
			
			<input type="button" name="searchButton" id="searchButton" value="ReCertify Users" onClick = "javascript:confirmAddReprt(document.userForm,'recertify','rptUserMaint.do');">
				
			<td colspan="5" class="right">
				<a href="javascript:confirmDelete(document.userForm,'remove','rptUserMaint.do');">
					<img src="images/btnDeleteSelected.png" alt="Delete Selected" /></a>
&nbsp;
				  <A 
                  href="javascript:confirmExport(document.userForm,'export','rptUserMaint.do');"><IMG 
                  alt="Export Selected" 
                  src="images/btnExport.png"></A>
			</td>
		</tr>
	</table>
	<%} %>
      <table width="700" cellpadding="1" cellspacing="1" class="sortable">
   	 
        <tr class="mntTblHdng" bgcolor="#99CCFF">
        <% if (TCGMUser.getRole().getName().equalsIgnoreCase("TCGM_ADMINISTRATOR") || 
      			TCGMUser.getRole().getName().equalsIgnoreCase("TCGM_RPT ADMIN")) { %>
        	<th width="80" class="sorttable_nosort">
				<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('userList','selected','<%=userForm.getUserListSize()%>');" />
			</th>
		<%} %>
            <th width="80" class="style6" scope="col">User ID</th>
            <th width="80"  class="sorttable_nosort" scope="col">First Name</th>
            <th width="80" class="style6" scope="col">Last Name</th>
            <th width="92" class="style6" scope="col">Role</th>
            <th width="230" class="style6" scope="col">Role Desc</th>
			<th width="90" class="style6" scope="col">Create Date</th>	
			<th width="90" class="style6" scope="col">Recertify Date</th>		
          </tr>
		<nested:hidden property="userListSize" />
   	    <nested:notEqual property="userListSize" value="0">	      	
	    <% int rowNumber=0; %>
	    
		<c:forEach items="${sessionScope.userForm.userList}"    var="rptUser"     varStatus="userStatus">  
           	  

           <% String userListItemArray = "userListItem[" + rowNumber +"]."; %>
           <% tmpProperty = "" ; %>
            <% rowNumber++; %>
        <abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
   			<% //tmpProperty is initialized here as per the column selected %>
			<% tmpProperty = userListItemArray + "blnSelected"; %>
			
			<% if (TCGMUser.getRole().getName().equalsIgnoreCase("TCGM_ADMINISTRATOR") || 
      			TCGMUser.getRole().getName().equalsIgnoreCase("TCGM_RPT ADMIN")) {%>
		  	<td class="mntCenter">
							<input type="checkbox" name="userlist[<c:out value="${userStatus.index}"/>].selected" value="on">
			</td>
			<%} %>
            <td class="mntLeft" valign="middle"><c:out value="${rptUser.userid}"/></td>
            <td class="mntLeft" valign="middle"><c:out value="${rptUser.firstName}"/></td>
			<td class="mntLeft" valign="middle"><c:out value="${rptUser.lastName}"/></td>
			<td class="mntLeft" valign="middle"><c:out value="${rptUser.role}"/></td>
			<td class="mntLeft" valign="middle"><c:out value="${rptUser.roleDesc}"/></td>
			<td class="mntLeft" valign="middle"><c:out value="${rptUser.createDate}"/></td>
			<td class="mntLeft" valign="middle"><c:out value="${rptUser.recertifyDate}"/></td>
          </abbott:row>
          </c:forEach>
          <input type="hidden" id="hidVal" name="hidVal" value="<%=rowNumber %>" />
          </nested:notEqual>
      </table>
      </fieldset>
      </td>
    </tr>
    

      </table>
      </td>
    </tr>
  </table>
 <input type="hidden" name="cmd2"  value="creation">
</nested:form>

<%@ include file="/include/footer.jsf" %>