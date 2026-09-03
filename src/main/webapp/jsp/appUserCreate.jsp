<%! String pageTitle = "App User Creation"; %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/header.jsf" %>
<SCRIPT type="text/javascript" language="JAVASCRIPT">

function add(){
		javascript:chgActCmdSubmit(document.userForm,'appview','ActiveDirSearch.do');
	}
function saveForm(form, cmd, action){

		chgActCmdSubmit(form, cmd, action);
}	
 
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
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
     <%tmpProperty = "rptUser.division";%>
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
       <%tmpProperty = "rptUser.role";%>
      <td class="commandOptionLabel"><strong><span class="mntLeft">Role</span></strong></td>
		<td>
			<nested:select property="<%=tmpProperty%>" styleId="role" > 
              <option value="-1">Select One</option>
              <option value="ADMINISTRATOR">ADMINISTRATOR</option>
              <option value="ANALYST">ANALYST</option>
              <option value="OPERATOR">OPERATOR</option>
              <option value="RPT ADMIN">RPT ADMIN</option>
              <option value="QUERY">QUERY</option>
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
      <td colspan="4">   <table align="center">
        <tr>
          <td>
            <input type="button" name="addButton" id="addButton" value="LookUp"  onClick = "javascript:add();">
          </td>
          <td>        
        <td>
            <input type="button" name="saveButton" id="saveButton" value="Save"  onClick = "javascript:saveForm(document.userForm,'saveAppUser','rptUserMaint.do');">
          </td>
		<td>&nbsp;</td>
        </tr>
      </table>
      </td>
    </tr>
  </table>
 <input type="hidden" name="cmd2"  value="creation">
</nested:form>

<%@ include file="/include/footer.jsf" %>