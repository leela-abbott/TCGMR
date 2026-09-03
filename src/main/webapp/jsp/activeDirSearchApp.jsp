<%! String pageTitle = "App User Search & Selection Screen"; %>
<%@ include file="/include/header.jsf" %>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:setFocus('usId');">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	
<jsp:useBean id="activeDirSearchForm" scope="session" class="abbott.ai.tcgm.action.form.ActiveDirSearchForm" />
<nested:form name="activeDirSearchForm" type="abbott.ai.tcgm.action.form.ActiveDirSearchForm" method="post" action="/ActiveDirSearch.do">
  <nested:hidden property="cmd" />
   <nested:hidden property="cmd2" />
<table width="650" height="77" align="center" >

          <tr>
            <td width=100></td>
            <td height="33"><label align="left" class="commandOptionLabel">UserId</label></td>
            <td><label>
              <input name="usId" type="text" value="" id="usIdTextField" size="25" class="mntLeft" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}">
            </label></td>
          </tr>
          <tr>
            <td width=100></td>
            <td height="33"><label align="left" class="commandOptionLabel">Last Name</label></td>
            <td><label>
              <input name="lastName" type="text" id="lastNameTextField" size="25" class="mntLeft" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}">
            </label></td>
          </tr>    
          <tr>
          <td width=100></td>
            <td width="102" height="36" align="left" class="commandOptionLabel"><label>First Name</label></td>
            <td ><label>
              <input name="firstName" type="text" id="firstNameTextField" size="25" class="mntLeft" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}">
            </label></td>
          </tr>
                
          

    <tr>

      <td align="center" colspan=3>
        <input type="button" name="searchButton" id="searchButton" value="Get Users" onClick="javascript:chgActCmdSubmit(document.activeDirSearchForm,'AppGet','ActiveDirSearch.do');">
	 </td>
    </tr>
</table>
<br>
   <nested:notEqual property="activeDirUserListSize" value="0">
   		<%if(activeDirSearchForm.getActiveDirUserListSize()>10){%>
   			  <table align="center">
		         <tr>
		            <td><input type="button" name="selectUserButton" id="selectUserButton" value="Select User" onClick = "javascript:checkSelect();"> </td>
		           <!-- <td><input type="button" name="cancelButton" id="cancelButton" value="Cancel" onclick="javascript:callCancel();">  </td> -->
		         </tr>
		      </table>
   		<%}%>
      <table width="426" align="center" cellpadding="1" cellspacing="1">
        <tr class="mntTblHdng" bgcolor="#99CCFF">
          <th width="57" >
            <div align="center"><span class="style5">
            </span><span class="style5"></span></div>            <span class="style5"><label></label>
            </span> </th>
            <th width="60" align="left">User ID</th>
            <th width="92" align="left">First Name</th>
            <th width="96" align="left">Last Name</th>
            <th width="60" align="left">UPI</th>
            <th width="50" align="left">Division</th>
            <th width="60" align="left">Type</th>
            <th width="60" align="left">Email</th>
          </tr>
			
			 <% int rowNumber=0; %>
			 <c:forEach items="${sessionScope.activeDirSearchForm.activeDirUserList}"
			       var="activeBean"
	               varStatus="activeStatus">  
	               <% String activeDirUserListItemArray = "activeDirUserListItem[" + rowNumber +"]."; %>
	               <% String tmpProperty = "" ; %>
	        
	        <tr>
          <td>
   			<% tmpProperty = activeDirUserListItemArray + "blnSelected"; %>			
			<INPUT type="radio" name="blnSelected" value='<c:out value="${activeBean.userId}"/>' onclick="document.getElementById('selectUserButton').focus();"/>
          </td>
            <td><% tmpProperty = activeDirUserListItemArray + "userId" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="30" styleClass="mntLeft"/></td>
            <td><% tmpProperty = activeDirUserListItemArray + "firstName" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="30" styleClass="mntLeft"/></td>
            <td><% tmpProperty = activeDirUserListItemArray + "lastName" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="30" styleClass="mntLeft"/></td>
            <td><% tmpProperty = activeDirUserListItemArray + "abtNotesId" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="50" styleClass="mntLeft"/></td>
            <td><% tmpProperty = activeDirUserListItemArray + "division" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="50" styleClass="mntLeft"/></td>
            <td><% tmpProperty = activeDirUserListItemArray + "employeeType" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="50" styleClass="mntLeft"/></td>
            <td><% tmpProperty = activeDirUserListItemArray + "email" ; %>
            <html:text property="<%=tmpProperty%>" maxlength="50" styleClass="mntLeft"/></td>
          </tr>
          	 <% rowNumber++; %>
           </c:forEach>
           </table>     
       
      <table align="center">
         <tr>
            <td><input type="button" name="selectUserButton" id="selectUserButton" value="Select User" onClick = "javascript:checkSelect();"> </td>
           <!-- <td><input type="button" name="cancelButton" id="cancelButton" value="Cancel" onclick="javascript:callCancel();">  </td> -->
         </tr>
      </table>
	</nested:notEqual> 
	
	
	<br><br>
	<hr>
	<font face="Arial" size=2 color=blue>	Existing TCGM Application Users : </font>
	<br>
	
	<table width="760" cellspacing="0" cellpadding="5">
			
			<tr class="fltrTblHdng">
				
				<td>User Id</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Phone</td>
				<td>UPI</td>
				<td>&nbsp;Role</td>
				<td>&nbsp;Email</td>
			</tr>
	
	<nested:hidden property="userListSize" />	
			<nested:notEqual property="userListSize" value="0">
			  <% int rowNumber=0; %>

				<c:forEach var="userBean" items="${activeDirSearchForm.userlist}"  varStatus="userStatus">
		               			
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	

						<td class="mntLeft">
							<c:out value="${userBean.userid}" />
						</td>		
						<td class="mntLeft">
							<c:out value="${userBean.firstName}" />
						</td>
						<td class="mntLeft">
							<c:out value="${userBean.lastName}" />
						</td>

						<td class="mntLeft">
							<c:out value="${userBean.phone}" />
						</td>
						
						<td class="mntLeft">
							<c:out value="${userBean.abtNotesId}" />
						</td>

						<td class="mntCenter">
							&nbsp;&nbsp;<c:out value="${userBean.userRole}" />
						</td>

						<td class="mntLeft">
							&nbsp;&nbsp;<c:out value="${userBean.email}" />
						</td>

					</abbott:row>
		  	<% rowNumber++; %>			
				</c:forEach>	
				
			</nested:notEqual>
		</table>
		<nested:equal property="userListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		
</nested:form>

<script language="JavaScript1.2" type="text/javascript">
		setFocus('usIdTextField');
</script>
<SCRIPT type="text/javascript" language="JAVASCRIPT">

function checkSelect(){
var radioObj=document.activeDirSearchForm.blnSelected;
var radioLength;
var flag;
var objvalue;
   if(radioObj!=null){
 	radioLength = radioObj.length;   
	if(radioLength == undefined)
	{
	
		if(radioObj.checked){
			javascript:chgActCmdSubmit(document.activeDirSearchForm,'appselect','ActiveDirSearch.do');			
			//window.close();
			}
		else{
			alert('Please Select a User');
			flag=false;
			}
	}
	else{		
		for(var i = 0; i < radioLength; i++) {
			if(radioObj[i].checked) {
				flag=true;
				objvalue=radioObj[i].value;
			}
		}
		if(flag){
			//window.opener.document.getElementById( "userid" ).value=objvalue;
			javascript:chgActCmdSubmit(document.activeDirSearchForm,'appselect','ActiveDirSearch.do');
			//window.close();
			
		}else{
			alert('Please Select a User');
		}
	}	
}
}
function callCancel(){

		javascript:chgActCmdSubmit(document.activeDirSearchForm,'maint_create','rptUserMaint.do');
	}

</SCRIPT>

<%@ include file="/include/footer.jsf" %>