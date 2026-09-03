<%! String pageTitle = "Report User Creation"; %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:disableList();">
<%@ include file="/include/header.jsf" %>
<SCRIPT type="text/javascript" language="JAVASCRIPT">
function disableList()
	{	
	   	document.getElementById("areaCode").disabled=true;
	   	document.getElementById("affCode").disabled=true;
	   	document.getElementById("secCode").disabled=true;
	   	document.getElementById("affCodeLst").disabled=true;
	   	document.getElementById("secCodeLst").disabled=true;
	   	document.getElementById("areaCodeLst").disabled=true;
	   	document.getElementById("userid").style.backgroundColor="#E6E6E6";
	   	document.getElementById("firstName").style.backgroundColor="#E6E6E6";
	   	document.getElementById("lastName").style.backgroundColor="#E6E6E6";
	  	document.getElementById("areaDiv").style.display ='none';
	  	document.getElementById("affDiv").style.display ='none';
	  	document.getElementById("secDiv").style.display ='none';
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
		   	document.getElementById("affCodeLst").disabled=true;
		   	document.getElementById("secCodeLst").disabled=true;
		   	document.getElementById("areaCodeLst").disabled=false;
		   	removeAllOptions(document.getElementById("areaCodeLst"));
  		  	document.getElementById("areaDiv").style.display ='';
		  	document.getElementById("affDiv").style.display ='none';
		  	document.getElementById("secDiv").style.display ='none';
		  	removeAllOptions(document.getElementById("areaCode"));
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
		   	document.getElementById("secCodeLst").disabled=true;
		   	document.getElementById("affCodeLst").disabled=false;
		   	removeAllOptions(document.getElementById("affCodeLst"));
		   	document.getElementById("areaCodeLst").disabled=true;
  		  	document.getElementById("areaDiv").style.display ='none';
		  	document.getElementById("affDiv").style.display ='';
		  	document.getElementById("secDiv").style.display ='none';
		  	removeAllOptions(document.getElementById("affCode"));
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
		   	document.getElementById("affCodeLst").disabled=true;
		   	document.getElementById("secCodeLst").disabled=false;
		   	removeAllOptions(document.getElementById("secCodeLst"));
		   	document.getElementById("areaCodeLst").disabled=true;
  		  	document.getElementById("areaDiv").style.display ='none';
		  	document.getElementById("affDiv").style.display ='none';
		  	document.getElementById("secDiv").style.display ='';		   	
		  	removeAllOptions(document.getElementById("secCode"));
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
		   	document.getElementById("affCodeLst").disabled=true;
		   	document.getElementById("secCodeLst").disabled=true;
		   	document.getElementById("areaCodeLst").disabled=true;
  		  	document.getElementById("areaDiv").style.display ='none';
		  	document.getElementById("affDiv").style.display ='none';
		  	document.getElementById("secDiv").style.display ='none';		   	
	  	}else {
		  	document.getElementById("areaCode").value=-1;
		   	document.getElementById("affCode").value=-1;
		   	document.getElementById("secCode").value=-1;
		   	document.getElementById("division").value=-1;

	  		document.getElementById("areaCode").disabled=true;
		   	document.getElementById("affCode").disabled=true;
		   	document.getElementById("secCode").disabled=true;
		   	document.getElementById("division").disabled=true;
		   	document.getElementById("affCodeLst").disabled=true;
		   	document.getElementById("secCodeLst").disabled=true;
		   	document.getElementById("areaCodeLst").disabled=true;
		   	document.getElementById("division").disabled=true;
  		  	document.getElementById("areaDiv").style.display ='none';
		  	document.getElementById("affDiv").style.display ='none';
		  	document.getElementById("secDiv").style.display ='none';		   	
	  	}
}	
function add(){
		javascript:chgActCmdSubmit(document.userForm,'view','ActiveDirSearch.do');
		//window.showModalDialog('/GPS/ActiveDirSearch.do?cmd=view',"name","dialogWidth:500px;dialogHeight:500px");
		//window.open('/GPS/ActiveDirSearch.do?cmd=view','Notification');
	}
function saveForm(form, cmd, action){
		
		
		if(document.getElementById("role").value=='-1'){
			alert('Please select Role');
			return false;
		}
		if(document.getElementById("role").value=='Area'||document.getElementById("role").value=='Affiliate'
    		||document.getElementById("role").value=='Sector'||document.getElementById("role").value=='D'){
				if(document.getElementById("division").value=='-1'){
					alert('Please select Division');
					return false;
				}
		}
		if (document.getElementById("role").value=='Area')
	  	{
	  		if(document.getElementById("areaCodeLst").options.length < 1){
			alert('Please select Area');
			return false;
			}
	  		form.selDesc.value = form.areaCode[form.areaCode.selectedIndex].text;

			var areaCodeList = "";
            var areaListSel = document.getElementById("areaCodeLst");
            
	  		 for(var j=0;j<areaListSel.options.length;j++){
									 		 
			 		 areaCodeList = areaCodeList+areaListSel.options[j].value+"|"+areaListSel.options[j].text+"*";

	 		 }//end of for j
	  		form.areaCodeList.value = areaCodeList.substring(0,areaCodeList.lastIndexOf("*"));
			
	  	}
	  	if (document.getElementById("role").value=='Affiliate')
	  	{
		  	
	  		if(document.getElementById("affCodeLst").options.length < 1){
			alert('Please select Affiliate');
			return false;
			}
	  		form.selDesc.value = form.affCode[form.affCode.selectedIndex].text;
	  		
	  		var affCodeList = "";
            var affListSel = document.getElementById("affCodeLst");
            
	  		 for(var j=0;j<affListSel.options.length;j++){
			 		 
			 		 affCodeList = affCodeList+affListSel.options[j].value+"|"+affListSel.options[j].text+"*";
	 		 }//end of for j

	  		form.affCodeList.value = affCodeList.substring(0,affCodeList.lastIndexOf("*"));
	  		//alert(form.affCodeList.value);
	  	}
	  	if (document.getElementById("role").value=='Sector')
	  	{
	  		if(document.getElementById("secCodeLst").options.length < 1){
			alert('Please select Sector');
			return false;
			}	  	
	  		form.selDesc.value = form.secCode[form.secCode.selectedIndex].text;
	  		var secCodeList = "";
            var secListSel = document.getElementById("secCodeLst");
            
	  		 for(var j=0;j<secListSel.options.length;j++){
			 		 
			 		 secCodeList = secCodeList+secListSel.options[j].value+"|"+secListSel.options[j].text+"*";
	 		 }//end of for j

	  		form.secCodeList.value = secCodeList.substring(0,secCodeList.lastIndexOf("*"));
	  		//alert(form.secCodeList.value);
	  	}
	  	//alert(form.selDesc.value);
	  	//alert(action);
	  	//return false;
		chgActCmdSubmit(form, cmd, action);
}	
 function addList(list, selList)
 {
 
	 var affList = document.getElementById(list);
 	 var affListSel = document.getElementById(selList);
 	 var statusFlag = false;
	 for(var i=0;i<affList.options.length;i++){
		 if(affList.options[i].selected){
	 		 for(var j=0;j<affListSel.options.length;j++){
			 		 if(affList.options[i].value==affListSel.options[j].value) {
				 		 statusFlag=true;
			 		 }
	 		 }//end of for j
	 		 if(!statusFlag){
			 	addOptions(document.getElementById(selList),affList.options[i].text,affList.options[i].value)
			 }
			 statusFlag=false; 
		}
 	 }//end of for i
 	 	
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
		<nested:hidden property="affCodeList" />
		<nested:hidden property="secCodeList" />
		<nested:hidden property="areaCodeList" />

  <table width="700" align="center" border=0>
    <tr>
      <td width="89" class="commandOptionLabel"><strong>
      <span class="mntLeft" >User ID</span>
      </strong></td>
      <%String tmpProperty = "rptUser.userid";%>
<!-- onblur="style.backgroundColor='gray'; style.color='black'" onfocus="style.backgroundColor='gray'; style.color='black'" -->      
      <td ><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft"  styleId="userid" readonly="true" /></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
     <%tmpProperty = "rptUser.lastName";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >Last Name</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft"  styleId="lastName" readonly="true" /></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
    <%tmpProperty = "rptUser.firstName";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >First Name</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft"  styleId="firstName" readonly="true" /></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
     <%tmpProperty = "rptUser.email";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >Email</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft" readonly="true" /></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
     <%tmpProperty = "rptUser.abtNotesId";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >UPI</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft" readonly="true" /></td>
      <td>&nbsp;</td>
    </tr> 
    <tr>
     <%tmpProperty = "rptUser.empDivision";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >Division</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft" readonly="true" /></td>
      <td>&nbsp;</td>
    </tr> 
    <tr>
     <%tmpProperty = "rptUser.employeeType";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft" >Type</span></strong></td>
      <td><nested:text  property="<%=tmpProperty%>" styleClass="mntLeft" readonly="true" /></td>
      <td>&nbsp;</td>
    </tr> 
    <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Role</span></strong></td>
		 <%tmpProperty = "rptUser.role";%>
		<td>
			<html:select property="<%=tmpProperty%>" onchange="javascript:enableList()" styleId="role">
              <option value="-1">Select One</option>
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
         <td>&nbsp;</td>
    </tr>
    <tbody id="areaDiv" style="display:none">
     <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Area</span></strong></td>
		 
		<td>
		<bean:define id="areaCollection" name="RptUser" property="areas" type="java.util.HashMap"/>
		 <%tmpProperty = "rptUser.areaCode";%>
		<nested:select  property="<%=tmpProperty%>" multiple="yes" size="12" style="width:240px;border: 1px;" styleId="areaCode"  styleClass="commandOption">
      	<!-- <option value="-1">Select One</option> --> 

		</nested:select>
       	</td> 
	  	<TD align="center">
			<input type=button value="    Insert >>  " onclick="javacript:addList('areaCode','areaCodeLst');" tabindex="4">
			<BR>
			<BR>
			<input type=button value="<< Remove"  onclick="javacript:removeList('areaCodeLst');" tabindex="5">
		</td>
  		<td>
		<%tmpProperty = "rptUser.areaCodeList";%>
		<nested:select  property="<%=tmpProperty%>" multiple="yes" size="12" style="width:220px;border: 1px;" styleId="areaCodeLst"  styleClass="commandOption"> 
        		
		</nested:select>
         </td> 
    </tr>
    </tbody>
    <tbody id="affDiv" style="display:none">
     <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Affiliate</span></strong></td>
		<td>
		<bean:define id="affCollection" name="RptUser" property="affiliates" type="java.util.HashMap"/>
		 <%tmpProperty = "rptUser.affCode";%>
		<nested:select  property="<%=tmpProperty%>" multiple="yes" size="12" style="width:240px;border: 1px;" styleId="affCode"  styleClass="commandOption"> 
        <!-- <option value="-1">Select One</option> -->

		</nested:select>
         </td> 
		<TD align="center">
			<input type=button value="    Insert >>  " onclick="javacript:addList('affCode','affCodeLst');" tabindex="4">
			<BR>
			<BR>
			<input type=button value="<< Remove"  onclick="javacript:removeList('affCodeLst');" tabindex="5">
		</td>
  		<td>
		<%tmpProperty = "rptUser.affCodeList";%>
		<nested:select  property="<%=tmpProperty%>" multiple="yes" size="12" style="width:220px;border: 1px;" styleId="affCodeLst"  styleClass="commandOption"> 
        		
		</nested:select>
         </td> 
    </tr>
     </tbody>
    <tbody id="secDiv" style="display:none">   
    
   <tr>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Sector</span></strong></td>
		<td>
		<bean:define id="secCollection" name="RptUser" property="sectors" type="java.util.HashMap"/>
		<%tmpProperty = "rptUser.secCode";%>
		<nested:select  property="<%=tmpProperty%>" multiple="yes" size="12" style="width:240px;border: 1px;" styleId="secCode" styleClass="commandOption"> 
        <!-- <option value="-1">Select One</option> -->

		</nested:select>
         </td> 
		<TD align="center">
			<input type=button value="    Insert >>  " onclick="javacript:addList('secCode','secCodeLst');" tabindex="4">
			<BR>
			<BR>
			<input type=button value="<< Remove"  onclick="javacript:removeList('secCodeLst');" tabindex="5">
		</td>
  		<td>
		<%tmpProperty = "rptUser.secCodeList";%>
		<nested:select  property="<%=tmpProperty%>" multiple="yes" size="12" style="width:220px;border: 1px;" styleId="secCodeLst"  styleClass="commandOption"> 
        		
		</nested:select>
         </td> 
    </tr>
  </tbody>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4">   <table align="center">
        <tr>
          <td>
            <input type="button" name="addButton" id="addButton" value="LookUp"  onClick = "javascript:add();">
          </td>
          <td>        
        <td>
            <input type="button" name="saveButton" id="saveButton" value="Save"  onClick = "javascript:saveForm(document.userForm,'save','rptUserMaint.do');">
          </td>
	   <!--  <td>
            <input type="button" name="button2" id="button2" value="Reset" onClick = "javascript:chgActCmdSubmit(document.userForm,'Reset','rptUserMaint.do');">
          </td>
    	 <td>
            <input type="button" name="removeButton" id="removeButton" value="Remove"  onClick = "javascript:saveForm(document.userForm,'remove','rptUserMaint.do');">
          </td>          -->
		<td>&nbsp;</td>
        </tr>
      </table>
      </td>
    </tr>
  </table>
 <input type="hidden" name="cmd2"  value="creation">
</nested:form>

<%@ include file="/include/footer.jsf" %>