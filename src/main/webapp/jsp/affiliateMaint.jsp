<%! String pageTitle = "Active Affiliate Maintenance"; %>
<%
String div = (String)request.getAttribute("div");
 String actEnable="";
 String inactEnable="";
String cat=(String)request.getAttribute("cat");
if(cat==null){
cat="";
}
if(div==null){
div="-1";
}
 
 if(cat.equals("Y"))
 {
	 actEnable="checked";
	inactEnable="";
 }
 if(cat.equals("N"))
 {
	 actEnable="";
	inactEnable="checked";
 }
 

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:enableList();">
<%@ include file="/include/header.jsf" %>
<SCRIPT type="text/javascript" language="JAVASCRIPT">
function saveForm(form, cmd, action,acinacflag,cat){
		
		if(document.getElementById("role").value=='-1'){
			alert('Please select Division');
			return false;
		}		
		document.body.style.cursor = 'wait';
		if(cat=='N'){
		document.forms[0].saveActiveButton.disabled=true;
		}else{
		document.forms[0].saveInActiveButton.disabled=true;
		}
	  	document.getElementById("categoryname").value=acinacflag;
		document.getElementById("category").value=cat;
		chgActCmdSubmit(form, cmd, action);
}
function enableList(){
document.body.style.cursor = "default";
var sel = document.getElementById("role");   
var txtVal = '<%=div%>';  
var i;
 for (i = 0; i < sel.options.length; i++) {
                if(sel.options[i].value==txtVal){
					break;
				}
            }

 document.getElementById("role").selectedIndex = i;
}	
function clearFields()
{
document.getElementById("role").value='-1';
//enableList();
}	
function activeInactive()
{
    	
				if(document.getElementById("role").value=='-1')
				{
					alert('Please select Division');
					return false;
				}	
				
		
				if(document.getElementById("blnActive").checked)
				{
					document.body.style.cursor = 'wait';
					document.getElementById("category").value='Y';
					chgActCmdSubmit(document.userForm, 'getactiveaff', 'rptUserMaint.do');
				}
				else if(document.getElementById("blnInactive").checked){
					document.body.style.cursor = 'wait';
					document.getElementById("category").value='N';
					chgActCmdSubmit(document.userForm, 'getactiveaff', 'rptUserMaint.do');
				}
				
		
}
</SCRIPT>




	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<jsp:useBean id="userForm" scope="session" class="abbott.ai.tcgm.action.form.RptUserForm" />

<bean:define id="RptUser" name="RptUser" scope="session" type="abbott.ai.tcgm.entities.RptUser" />	
	<nested:form method="post" name="userForm" type="abbott.ai.tcgm.action.form.RptUserForm" action="/rptUserMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="selDesc" />

<input type="hidden" name="status" id="hidVal"/>
<input type="hidden" name="category" id="category"/>
<input type="hidden" name="categoryname" id="categoryname"/>

  <table width="650" align="center" border="0" >
    <tr>

     <td class="commandOptionLabel" width="245" align=right><strong><span class="mntLeft">Division</span></strong></td>
		 <%String tmpProperty = "rptUser.division";%>
	 <td width="405">
		<html:select property="<%=tmpProperty%>"  onchange="javascript:activeInactive()" styleId="role">
          <option value="-1">Select One</option>
          <option value="AI">AI</option>
          <option value="ANI">ANI</option>
          <option value="AV">AV</option>		 
		 <option value="EPD">EPD</option>
		
        </html:select>
     </td> 
    </tr>
     <tr>

      <td class="commandOptionLabel" align=right><INPUT type="radio" name="blnDivision" value="Y" id="blnActive" onclick="javascript:activeInactive()" <%=actEnable %>><strong><span class="mntLeft" ><label id="code">Active</label></span></strong></td>
      <td valign="middle" class="commandOptionLabel" align=left><INPUT type="radio" name="blnDivision" value="N" id="blnInactive" onclick="javascript:activeInactive()" <%=inactEnable %>>
<strong><span class="mntLeft" ><label id="code">Inactive</label></span></strong>
	
      </td>
    </tr>    
    <tr>

      <td colspan="2" align="center"> 
<%if(cat.equals("N")){%>  
            <input type="button" name="saveActiveButton" id="saveActiveButton" value="Make Active"   onClick = "javascript:saveForm(document.userForm,'saveactiveaff','rptUserMaint.do','Y','N');">
			<%}if(cat.equals("Y"))
 {
%>
            <input type="button" name="saveInActiveButton" id="saveInActiveButton" value="Make In-Active"   onClick = "javascript:saveForm(document.userForm,'saveactiveaff','rptUserMaint.do','N','Y');">
<% }%>
      </td>
    </tr>
	<tr>
      <td colspan="2">   
       
    
      <table width="300" cellpadding="1" cellspacing="1" class="sortable" align="center">
   	 
        <tr class="mntTblHdng" bgcolor="#99CCFF">        
        	<th width="10" class="sorttable_nosort">
				<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('affMaintList','selected','<%=userForm.getAffMaintListSize()%>');" />
			</th>
		
            <th width="80" class="style6" scope="col">Affiliate</th>
            <th width="80"  class="sorttable_nosort" scope="col">Division</th>
          </tr>
		<nested:hidden property="affMaintListSize" />
   	    <nested:notEqual property="affMaintListSize" value="0">	      	
	    <% int rowNumber=0; %>
	    
		<c:forEach items="${sessionScope.userForm.affMaintList}"    var="rptUser"     varStatus="userStatus">  
           	  

           <% String userListItemArray = "affMaintListItem[" + rowNumber +"]."; %>
           <% tmpProperty = "" ; %>
            <% rowNumber++; %>
        <abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
   			<% //tmpProperty is initialized here as per the column selected %>
			<% tmpProperty = userListItemArray + "blnSelected"; %>
			
			
		  	<td class="mntCenter">
							<input type="checkbox" name="affMaintlist[<c:out value="${userStatus.index}"/>].selected" value="on">
			</td>			
            <td class="mntLeft" valign="middle"><c:out value="${rptUser.aff}"/></td>
            <td class="mntLeft" valign="middle"><c:out value="${rptUser.divisionCode}"/></td>
          </abbott:row>
          </c:forEach>
          <input type="hidden" id="hidVal" name="hidVal" value="<%=rowNumber %>" />
          </nested:notEqual>
<tr>

      <td colspan="2" align="center"> 
<%if(cat.equals("N")){%>  
            <input type="button" name="saveActiveButton" id="saveActiveButton" value="Make Active"   onClick = "javascript:saveForm(document.userForm,'saveactiveaff','rptUserMaint.do','Y');">
			<%}if(cat.equals("Y"))
 {
%>
            <input type="button" name="saveInActiveButton" id="saveInActiveButton" value="Make In-Active"   onClick = "javascript:saveForm(document.userForm,'saveactiveaff','rptUserMaint.do','N');">
<% }%>
      </td>
    </tr>
      </table>     
      </td>
    </tr>
    

      
  </table>
 <input type="hidden" name="cmd2"  value="creation">
</nested:form>
<br>
<%@ include file="/include/footer.jsf" %>