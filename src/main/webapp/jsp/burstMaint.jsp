<%! String pageTitle = "Add Division / Area / Sector / Affiliate"; %>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:enableList();">
<%@ include file="/include/header.jsf" %>
<SCRIPT type="text/javascript" language="JAVASCRIPT">
function disableList()
	{	
	   //	document.getElementById("category").disabled=true;
	   	  	  
	}
function saveForm(form, cmd, action){
		
		

		
		
		if(document.getElementById("role").value=='-1'){
			alert('Please select Role');
			return false;
		}

		if(document.getElementById("role").value=='D'){
			
			if(document.getElementById("division").value=='-1'){
				alert('Please select Division');
				return false;
			}
				document.forms[0].categoryid.value='D';
				document.forms[0].categoryname.value=document.getElementById("division").value;
			

		}
		else if (document.getElementById("role").value=='Area')
		{
			if(document.getElementById("division").value=='-1'){
				alert('Please select Division');
				return false;
			}else if(document.getElementById("areaCode").value=='-1'){
				alert('Please select Area');
				return false;
			}
			
				document.forms[0].categoryid.value=document.getElementById("areaCode").value;
				document.forms[0].categoryname.value=getSlectedText(document.getElementById("areaCode")).substring(5);
			
			
		}else if (document.getElementById("role").value=='Affiliate')
		{
			if(document.getElementById("division").value=='-1'){
				alert('Please select Division');
				return false;
			}else if(document.getElementById("affCode").value=='-1'){
				alert('Please select Affiliate');
				return false;
			}
				document.forms[0].categoryid.value=document.getElementById("affCode").value;
				document.forms[0].categoryname.value=getSlectedText(document.getElementById("affCode")).substring(7);
			
			
		}else if (document.getElementById("role").value=='Sector')
		{
			if(document.getElementById("division").value=='-1'){
				alert('Please select Division');
				return false;
			}else if(document.getElementById("secCode").value=='-1'){
				alert('Please select Sector');
				return false;
			}
				document.forms[0].categoryid.value=document.getElementById("secCode").value;
				document.forms[0].categoryname.value=getSlectedText(document.getElementById("secCode")).substring(9);
			
			
		}
		document.forms[0].divisionCode.value=document.getElementById("division").value;
		chgActCmdSubmit(form, cmd, action);
}
function enableList(){

		
		if (document.getElementById("role").value=='-1' || document.getElementById("division").value=='-1')
	  	{
	  	document.getElementById("code").innerHTML="Affiliate / Sector / Area #";
	  	//document.getElementById("name").innerHTML="Affiliate / Sector / Area Name";
	  	document.getElementById("affCode").style.display='none';
	  	document.getElementById("secCode").style.display='none';
	  	document.getElementById("areaCode").style.display='none';
	  	//document.getElementById("catID").value='';
	  	document.getElementById("catName").value='';
	  			   	
	  	}else if (document.getElementById("role").value=='Area')
	  	{
	  	document.getElementById("code").innerHTML="Area Code";
	  	//document.getElementById("name").innerHTML="Area Name";
	  	document.getElementById("affCode").style.display='none';
	  	document.getElementById("secCode").style.display='none';
	  	document.getElementById("areaCode").style.display='';
		//document.getElementById("catName").value='';	  	
	  	retrieveURL('./tcgmAjax.do?cascadingCmd=Area&cascadingVal='+document.getElementById("division").value,'userForm','areaCode');
	  	
	  	}else if (document.getElementById("role").value=='Affiliate')
	  	{
	  	document.getElementById("code").innerHTML="Affiliate Code";
	  	//document.getElementById("name").innerHTML="Affiliate Name";
	  	document.getElementById("affCode").style.display='';
	  	document.getElementById("secCode").style.display='none';
	  	document.getElementById("areaCode").style.display='none';
		//document.getElementById("catName").value='';
	  	retrieveURL('./tcgmAjax.do?cascadingCmd=Country&cascadingVal='+document.getElementById("division").value,'userForm','affCode');
	  	}else if (document.getElementById("role").value=='Sector')
	  	{
	  	document.getElementById("code").innerHTML="Sector Code";
	  	//document.getElementById("name").innerHTML="Sector Name";
	  	document.getElementById("affCode").style.display='none';
	  	document.getElementById("secCode").style.display='';
	  	document.getElementById("areaCode").style.display='none';
		//document.getElementById("catName").value='';
	  	retrieveURL('./tcgmAjax.do?cascadingCmd=Sector&cascadingVal='+document.getElementById("division").value,'userForm','secCode');
	  	}
		else if (document.getElementById("role").value=='D')
	  	{
		document.getElementById("code").innerHTML="Affiliate / Sector / Area #";
	  	//document.getElementById("name").innerHTML="Affiliate / Sector / Area Name";
		try{
	  	document.getElementById("affCode").style.display='none';
		}catch(err) {
    		
		}
		try{
	  	document.getElementById("secCode").style.display='none';
		}catch(err1){
		}
		try{
	  	document.getElementById("areaCode").style.display='none';
		}catch(err2){
		}
	  	//document.getElementById("catID").value='';
	  	//document.getElementById("catName").value='';	  		  	
	  	
	  	}

}	
function loadAreaDesc(){
	if (document.getElementById("role").value=='-1' || document.getElementById("division").value=='-1' || document.getElementById("areaCode").value=='-1')
	  	{
			document.getElementById("catName").value='';
		}else{

			document.getElementById("catName").value=getSlectedText(document.getElementById("areaCode")).substring(5);
		}
}
function loadSectorDesc(){
		if (document.getElementById("role").value=='-1' || document.getElementById("division").value=='-1' || document.getElementById("secCode").value=='-1')
	  	{
			document.getElementById("catName").value='';
		}else{
			document.getElementById("catName").value=getSlectedText(document.getElementById("secCode")).substring(9);
		}
}
function loadAffiliateDesc(){
	if (document.getElementById("role").value=='-1' || document.getElementById("division").value=='-1' || document.getElementById("affCode").value=='-1')
	  	{
			document.getElementById("catName").value='';
		}else{
			document.getElementById("catName").value=getSlectedText(document.getElementById("affCode")).substring(7);
		}
}
function getSlectedText(slctBox){
return slctBox[slctBox.selectedIndex].text;
}
function clearFields()
{
document.getElementById("role").value='-1';
enableList();
}	

function loadBurst(form, cmd, action){
		if ( confirm("Are you sure that you would like to Load the Burst Tables? ") ) 
		{
			document.body.style.cursor = 'wait';
			document.forms[0].saveButton.disabled=true;
			document.forms[0].loadButton.disabled=true;
			chgActCmdSubmit(form, cmd, action);
		}
}
</SCRIPT>
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<jsp:useBean id="paramForm" scope="session" class="abbott.ai.tcgm.action.form.RptUserForm" />

<bean:define id="RptUser" name="RptUser" scope="session" type="abbott.ai.tcgm.entities.RptUser" />	
	<nested:form method="post" name="userForm" type="abbott.ai.tcgm.action.form.RptUserForm" action="/rptUserMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="selDesc" />



  <table width="650" align="center" border="0" >
  
   <tr>

     <td class="commandOptionLabel" width="245" align=right><strong><span class="mntLeft">Role</span></strong></td>
		 <%String tmpProperty = "rptUser.role";%>
	 <td width="405">
		<html:select property="<%=tmpProperty%>"  onchange="javascript:enableList()" styleId="role">
          <option value="-1">Select One</option>
		  <option value="D">Division</option>
          <option value="Area">Area</option>          
          <option value="Sector">Sector</option>
		  <option value="Affiliate">Affiliate</option>
        </html:select>
     </td> 
    </tr>
    <tr>

     <td class="commandOptionLabel" width="245" align=right><strong><span class="mntLeft">Division</span></strong></td>		 
	 <td width="405">
	 <%tmpProperty = "rptUser.division";%>
		<bean:define id="divCollection" name="RptUser" property="div" type="java.util.HashMap"/>

		<nested:select  property="<%=tmpProperty%>" styleId="division" style="display:" onchange="javascript:enableList()">
		<option value="-1">Select One</option>
		<html:options property="value" labelProperty="key" collection="divCollection" />
		</nested:select>
      <nested:hidden property="divisionCode"/>
	  <nested:hidden property="categoryid" />
	  <nested:hidden property="categoryname"/>
      </td>
      </tr>
    <tr>

      <td class="commandOptionLabel" align=right><strong><span class="mntLeft" ><label id="code">Affiliate / Sector / Area #</label></span></strong></td>
      <td valign="middle">
      
		<bean:define id="areaCollection" name="RptUser" property="areas" type="java.util.HashMap"/>
		 <%tmpProperty = "rptUser.areaCode";%>
		<nested:select  property="<%=tmpProperty%>" styleId="areaCode" style="display:none" >
		
		</nested:select>
      
		<bean:define id="secCollection" name="RptUser" property="sectors" type="java.util.HashMap" />
		<%tmpProperty = "rptUser.secCode";%>
		<nested:select  property="<%=tmpProperty%>" styleId="secCode" style="display:none" > 
		
		</nested:select>

		<bean:define id="affCollection" name="RptUser" property="affiliates" type="java.util.HashMap"/>
		 <%tmpProperty = "rptUser.affCode";%>
		<nested:select  property="<%=tmpProperty%>"  styleId="affCode"  style="display:none" > 
		
		</nested:select>
      </td>
    </tr>
<!-- 
    <tr>

      <td class="commandOptionLabel" align=right><strong><span class="mntLeft" ><label id="name">Affiliate / Sector / Area Name</label></span></strong></td>
      <td><nested:text  property="categoryname" styleClass="mntLeft" styleId="catName" readonly="true"/></td>
    </tr>-->
    <tr>

      <td colspan="2" align="center">   
            <input type="button" name="saveButton" id="saveButton" value="Save"  onClick = "javascript:saveForm(document.userForm,'burstmaint','rptUserMaint.do');">
      </td>
    </tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
<tr>
		<td colspan="2"><font color="red" size="2">By choosing the &#34;Load Burst Tables&#34; button below, the system will remove and re-build the burst tables in TCGM based on the latest hierarchy. It will not create the Cognos groups.</font></td>
	</tr>
<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>

      <td colspan="2" align="center">   
          <input type="button" name="loadButton" id="loadButton" value="Load Burst Tables"  onClick = "javascript:loadBurst(document.userForm,'loadBurst','rptUserMaint.do');"> 
      </td>
    </tr>	
  </table>
 <input type="hidden" name="cmd2"  value="creation">
</nested:form>
<br>
<%@ include file="/include/footer.jsf" %>