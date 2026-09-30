<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<%! String pageTitle = "Report User Search & Selection Screen"; %>
<%@ include file="/include/header.jsf" %>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	
<s:form id="activeDirSearchForm" name="activeDirSearchForm" namespace="/" action="ActiveDirSearch" method="post">
  <s:hidden name="cmd" id="cmd" />
  <s:hidden name="cmd2" id="cmd2" />
  
  <table width="650" height="77" align="center">
          <tr>
            <td width="100"></td>
            <td height="33"><label align="left" class="commandOptionLabel">UserId</label></td>
            <td><label>
              <s:textfield name="usId" id="usIdTextField" cssClass="mntLeft" size="25" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
            </label></td>
          </tr>
          <tr>
            <td width="100"></td>
            <td height="33"><label align="left" class="commandOptionLabel">Last Name</label></td>
            <td><label>
              <s:textfield name="lastName" id="lastNameTextField" cssClass="mntLeft" size="25" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
            </label></td>
          </tr>    
          <tr>
            <td width="100"></td>
            <td width="102" height="36" align="left" class="commandOptionLabel"><label>First Name</label></td>
            <td><label>
              <s:textfield name="firstName" id="firstNameTextField" cssClass="mntLeft" size="25" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
            </label></td>
          </tr>
          <tr>
            <td align="center" colspan="3">
              <input type="button" name="searchButton" id="searchButton" value="Get Users" onClick="javascript:chgActCmdSubmit('Get');">
            </td>
          </tr>
  </table>
  <br>

  <c:if test="${not empty activeDirUserList}">
   		<c:if test="${activeDirUserList.size() > 10}">
   			  <table align="center">
		         <tr>
		            <td><input type="button" name="selectUserButtonTop" id="selectUserButtonTop" value="Select User" onClick="javascript:checkSelect();"> </td>
		         </tr>
		      </table>
   		</c:if>
   		
      <table width="426" align="center" cellpadding="1" cellspacing="1">
        <tr class="mntTblHdng" bgcolor="#99CCFF">
          <th width="57">
            <div align="center"><span class="style5"></span><span class="style5"></span></div>
            <span class="style5"><label></label></span> 
          </th>
          <th width="60" align="left">User ID</th>
          <th width="92" align="left">First Name</th>
          <th width="96" align="left">Last Name</th>
          <th width="60" align="left">UPI</th>
          <th width="50" align="left">Division</th>
          <th width="60" align="left">Type</th>
          <th width="60" align="left">Email</th>
        </tr>
			
        <c:forEach items="${activeDirUserList}" var="activeBean" varStatus="activeStatus">  
          <tr>
            <td>
              <input type="radio" name="blnSelected" value="<c:out value='${activeBean.userId}'/>" onclick="document.getElementById('selectUserButton').focus();"/>
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].userId" value="%{#attr.activeBean.userId}" maxlength="30" cssClass="mntLeft" />
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].firstName" value="%{#attr.activeBean.firstName}" maxlength="30" cssClass="mntLeft" />
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].lastName" value="%{#attr.activeBean.lastName}" maxlength="30" cssClass="mntLeft" />
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].abtNotesId" value="%{#attr.activeBean.abtNotesId}" maxlength="50" cssClass="mntLeft" />
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].division" value="%{#attr.activeBean.division}" maxlength="50" cssClass="mntLeft" />
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].employeeType" value="%{#attr.activeBean.employeeType}" maxlength="50" cssClass="mntLeft" />
            </td>
            <td>
              <s:textfield name="activeDirUserList[%{#activeStatus.index}].email" value="%{#attr.activeBean.email}" maxlength="50" cssClass="mntLeft" />
            </td>
          </tr>
        </c:forEach>
      </table>     
       
      <table align="center">
         <tr>
            <td><input type="button" name="selectUserButton" id="selectUserButton" value="Select User" onClick="javascript:checkSelect();"> </td>
         </tr>
      </table>
  </c:if> 
</s:form>

<script language="JavaScript1.2" type="text/javascript">
    document.getElementById('usIdTextField').focus();
</script>

<script type="text/javascript" language="JAVASCRIPT">
function chgActCmdSubmit(cmdValue) {
    document.getElementById('cmd').value = cmdValue;
    var form = document.getElementById('activeDirSearchForm');
    form.action = "ActiveDirSearch.action";
    form.submit();
}

function checkSelect(){
    var radioObj = document.getElementsByName('blnSelected');
    var flag = false;
    var objvalue;
    
    if (radioObj != null && radioObj.length > 0) {
        for (var i = 0; i < radioObj.length; i++) {
            if (radioObj[i].checked) {
                flag = true;
                objvalue = radioObj[i].value;
                break;
            }
        }
        if (flag) {
            chgActCmdSubmit('select');
        } else {
            alert('Please Select a User');
        }
    } else {
        alert('No users available to select');
    }
}

function callCancel(){
    document.getElementById('cmd').value = 'maint_create';
    var form = document.getElementById('activeDirSearchForm');
    form.action = "rptUserMaint.action";
    form.submit();
}
</script>

<%@ include file="/include/footer.jsf" %>
