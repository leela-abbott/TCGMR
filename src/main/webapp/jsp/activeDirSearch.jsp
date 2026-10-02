<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%! String pageTitle = "Report User Search & Selection Screen"; %>
<c:set var="pageTitle" value="Report User Search & Selection Screen" scope="request" />

<head>
<style type="text/css">
    body {
        font-family: Arial, sans-serif;
        background-color: #ffffff;
        color: #333333;
        margin: 0;
        padding: 0;
    }
    .search-container {
        width: 100%;
        max-width: 95%; /* Increased from 800px to accommodate wider dynamic tables */
        margin: 30px auto;
        padding: 0 15px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    #activeDirSearchForm {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 0 auto;
        width: 100%;
    }
    .form-group-row {
        display: flex;
        align-items: center;
        margin-bottom: 8px;
        width: 330px;
    }
    .form-group-row label {
        width: 100px;
        font-weight: bold;
        font-size: 12px;
        color: #333333;
        text-align: left;
    }
    .input-wrapper {
        width: 230px;
        display: flex;
        align-items: center;
    }
    .form-control-input {
        width: 100%;
        height: 24px;
        padding: 2px 6px;
        font-size: 12px;
        border: 1px solid #e0e0e0;
        border-radius: 3px;
        box-sizing: border-box;
        background-color: #ffffff;
        transition: border-color 0.2s ease;
    }
    .form-control-input:focus {
        border-color: #a0a0a0;
        outline: none;
    }
    .btn-submit-orange {
        background: linear-gradient(to bottom, #ffcc44 0%, #ffbb22 100%);
        border: 1px solid #e5a515;
        border-radius: 4px;
        color: #222222;
        font-size: 12px;
        font-weight: bold;
        padding: 4px 16px;
        cursor: pointer;
        box-shadow: 0 1px 2px rgba(0,0,0,0.1);
        display: block;
        margin: 15px auto 5px auto;
        text-align: center;
        min-width: 110px;
    }
    .btn-submit-orange:hover {
        background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
    }
    .btn-action-gray {
        background: linear-gradient(to bottom, #ffcc44 0%, #ffbb22 100%);
        border: 1px solid #e5a515;
        border-radius: 4px;
        color: #222222;
        font-size: 12px;
        font-weight: bold;
        padding: 4px 16px;
        cursor: pointer;
        box-shadow: 0 1px 2px rgba(0,0,0,0.1);
        display: block;
        margin: 15px auto;
        text-align: center;
        min-width: 110px;
    }
    .btn-action-gray:hover {
        background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
    }
    .results-fieldset {
        border: 1px solid #dddddd;
        border-radius: 4px;
        padding: 15px;
        margin-top: 20px;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
        width: fit-content; /* Dynamic scaling based on the table's fully unwrapped size */
        max-width: 100%;
        margin-left: auto;
        margin-right: auto;
        overflow-x: auto; /* Adds a clean horizontal scroll bar if screen resolution gets too narrow */
    }
    .results-legend {
        font-size: 13px;
        font-weight: bold;
        color: #0044aa;
        padding: 0 6px;
    }
    .modern-grid-table {
        width: auto; /* Changed to auto so columns scale strictly up to text lengths */
        min-width: 100%;
        border-collapse: collapse;
        margin-top: 5px;
        font-family: Consolas, "Courier New", Courier, monospace;
        font-size: 13px;
        table-layout: auto; /* Forces browser to look at content sizing instead of breaking down spaces */
    }
    .modern-grid-table th {
        background-color: #99ccff;
        color: #333333;
        font-weight: bold;
        text-align: left;
        padding: 8px 14px;
        border: 1px solid #d0e0f5;
        white-space: nowrap; /* Keeps column text headers on one single row line */
    }
    .modern-grid-table td {
        padding: 8px 14px;
        border: 1px solid #e0e0e0;
        color: #111111;
        text-align: left;
        vertical-align: middle;
        white-space: nowrap; /* Crucial: Prevents user data from breaking or wrapping downwards */
    }
    .modern-grid-table th.text-center, 
    .modern-grid-table td.text-center { 
        text-align: center; 
    }
    .evenRow { background-color: #ffffff; }
    .oddRow { background-color: #ffffff; }
</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/header.jsf" %>
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	
<div class="search-container">
	
	<form id="activeDirSearchForm" name="activeDirSearchForm" action="ActiveDirSearch" method="post">
		<input type="hidden" name="cmd" id="cmd" value="<c:out value='${action.cmd}' />" />
		<input type="hidden" name="cmd2" id="cmd2" value="<c:out value='${action.cmd2}' />" />
  
		<div class="form-group-row">
			<label for="usIdTextField">UserId</label>
			<div class="input-wrapper">
				<input type="text" name="usId" id="usIdTextField" class="form-control-input" value="<c:out value='${action.usId}' />"
							 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
			</div>
		</div>

		<div class="form-group-row">
			<label for="lastNameTextField">Last Name</label>
			<div class="input-wrapper">
				<input type="text" name="lastName" id="lastNameTextField" class="form-control-input" value="<c:out value='${action.lastName}' />"
							 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
			</div>
		</div>    

		<div class="form-group-row">
			<label for="firstNameTextField">First Name</label>
			<div class="input-wrapper">
				<input type="text" name="firstName" id="firstNameTextField" class="form-control-input" value="<c:out value='${action.firstName}' />"
							 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
			</div>
		</div>

		<input type="button" name="searchButton" id="searchButton" class="btn-submit-orange" value="Get Users" onClick="javascript:chgActCmdSubmit('Get');">
		
		<c:if test="${not empty action.activeDirUserList && fn:length(action.activeDirUserList) > 0}">
			<fieldset class="results-fieldset">
				<legend class="results-legend">Search Results</legend>

				<c:if test="${fn:length(action.activeDirUserList) > 10}">
					<input type="button" name="selectUserButtonTop" id="selectUserButtonTop" class="btn-action-gray" value="Select User" onClick="javascript:checkSelect();" style="margin-top: 0; margin-bottom: 8px;">
				</c:if>
				
				<table class="modern-grid-table">
					<thead>
						<tr>
							<th width="40" class="text-center">Select</th>
							<th width="80" align="left">User ID</th>
							<th width="100" align="left">First Name</th>
							<th width="100" align="left">Last Name</th>
							<th width="80" align="left">UPI</th>
							<th width="80" align="left">Division</th>
							<th width="70" align="left">Type</th>
							<th width="120" align="left">Email</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${action.activeDirUserList}" var="userItem" varStatus="activeStatus">  
							<tr class="${activeStatus.index % 2 == 0 ? 'evenRow' : 'oddRow'}">
								<td class="text-center">
									<input type="radio" name="blnSelected" value="<c:out value='${userItem.userId}'/>" onclick="document.getElementById('selectUserButton').focus();"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].userId" value="<c:out value='${userItem.userId}'/>"/>
									<c:out value="${userItem.userId}"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].firstName" value="<c:out value='${userItem.firstName}'/>"/>
									<c:out value="${userItem.firstName}"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].lastName" value="<c:out value='${userItem.lastName}'/>"/>
									<c:out value="${userItem.lastName}"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].abtNotesId" value="<c:out value='${userItem.abtNotesId}'/>"/>
									<c:out value="${userItem.abtNotesId}"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].division" value="<c:out value='${userItem.division}'/>"/>
									<c:out value="${userItem.division}"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].employeeType" value="<c:out value='${userItem.employeeType}'/>"/>
									<c:out value="${userItem.employeeType}"/>
								</td>
								<td>
									<input type="hidden" name="activeDirUserList[${activeStatus.index}].email" value="<c:out value='${userItem.email}'/>"/>
									<c:out value="${userItem.email}"/>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>     
				 
				<input type="button" name="selectUserButton" id="selectUserButton" class="btn-action-gray" value="Select User" onClick="javascript:checkSelect();">
			</fieldset>
		</c:if> 
	</form>
</div>

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
</body>
</html>
