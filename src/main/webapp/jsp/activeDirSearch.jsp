<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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
        max-width: 800px;
        margin: 40px auto;
        padding: 0 20px;
    }
    .form-group-row {
        display: flex;
        align-items: center;
        margin-bottom: 18px;
    }
    .form-group-row label {
        width: 160px;
        font-weight: bold;
        font-size: 14px;
        color: #333333;
        text-align: left;
    }
    .input-wrapper {
        flex: 1;
        display: flex;
        align-items: center;
    }
    .form-control-input {
        width: 280px;
        height: 36px;
        padding: 6px 12px;
        font-size: 14px;
        border: 1px solid #e0e0e0;
        border-radius: 4px;
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
        border-radius: 6px;
        color: #222222;
        font-size: 14px;
        font-weight: bold;
        padding: 10px 36px;
        cursor: pointer;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        display: block;
        margin: 30px auto;
        text-align: center;
        min-width: 160px;
    }
    .btn-submit-orange:hover {
        background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
    }
    .btn-action-gray {
        background: #f0f0f0;
        border: 1px solid #cccccc;
        border-radius: 4px;
        color: #333333;
        font-size: 13px;
        font-weight: bold;
        padding: 8px 20px;
        cursor: pointer;
        display: block;
        margin: 20px auto;
    }
    .btn-action-gray:hover {
        background: #e5e5e5;
    }
    .results-fieldset {
        border: 1px solid #dddddd;
        border-radius: 6px;
        padding: 20px;
        margin-top: 40px;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }
    .results-legend {
        font-size: 16px;
        font-weight: bold;
        color: #0044aa;
        padding: 0 10px;
    }
    .modern-grid-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
        font-size: 13px;
    }
    .modern-grid-table th {
        background-color: #99ccff;
        color: #333333;
        font-weight: bold;
        text-align: left;
        padding: 10px;
        border: 1px solid #d0e0f5;
    }
    .modern-grid-table td {
        padding: 8px 10px;
        border: 1px solid #e8f0fa;
    }
    .modern-grid-table .table-input-field {
        width: 100%;
        border: none;
        background: transparent;
        padding: 4px;
        font-size: 13px;
        color: #333333;
    }
    .modern-grid-table .table-input-field:focus {
        outline: none;
        background: #f0f5fa;
    }
    .evenRow { background-color: #ffffff; }
    .oddRow { background-color: #ffffdd; }
    .text-center { text-align: center; }
    .text-left { text-align: left; }
</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/header.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<%@ include file="/include/masthead.jsf" %>
	
<div class="search-container">
	
	<s:form id="activeDirSearchForm" name="activeDirSearchForm" namespace="/" action="ActiveDirSearch" method="post">
		<s:hidden name="cmd" id="cmd" />
		<s:hidden name="cmd2" id="cmd2" />
  
		<div class="form-group-row">
			<label for="usIdTextField">UserId</label>
			<div class="input-wrapper">
				<s:textfield name="usId" id="usIdTextField" cssClass="form-control-input" theme="simple"
							 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
			</div>
		</div>

		<div class="form-group-row">
			<label for="lastNameTextField">Last Name</label>
			<div class="input-wrapper">
				<s:textfield name="lastName" id="lastNameTextField" cssClass="form-control-input" theme="simple"
							 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
			</div>
		</div>    

		<div class="form-group-row">
			<label for="firstNameTextField">First Name</label>
			<div class="input-wrapper">
				<s:textfield name="firstName" id="firstNameTextField" cssClass="form-control-input" theme="simple"
							 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
			</div>
		</div>

		<input type="button" name="searchButton" id="searchButton" class="btn-submit-orange" value="Get Users" onClick="javascript:chgActCmdSubmit('Get');">

		<c:if test="${not empty activeDirUserList}">
			<fieldset class="results-fieldset">
				<legend class="results-legend">Search Results</legend>

				<c:if test="${activeDirUserList.size() > 10}">
					<input type="button" name="selectUserButtonTop" id="selectUserButtonTop" class="btn-action-gray" value="Select User" onClick="javascript:checkSelect();" style="margin-top: 0; margin-bottom: 15px;">
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
						<c:forEach items="${activeDirUserList}" var="activeBean" varStatus="activeStatus">  
							<tr class="${activeStatus.index % 2 == 0 ? 'evenRow' : 'oddRow'}">
								<td class="text-center">
									<input type="radio" name="blnSelected" value="<c:out value='${activeBean.userId}'/>" onclick="document.getElementById('selectUserButton').focus();"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].userId" value="%{#attr.activeBean.userId}" maxlength="30" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].firstName" value="%{#attr.activeBean.firstName}" maxlength="30" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].lastName" value="%{#attr.activeBean.lastName}" maxlength="30" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].abtNotesId" value="%{#attr.activeBean.abtNotesId}" maxlength="50" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].division" value="%{#attr.activeBean.division}" maxlength="50" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].employeeType" value="%{#attr.activeBean.employeeType}" maxlength="50" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
								<td>
									<s:textfield name="activeDirUserList[%{#activeStatus.index}].email" value="%{#attr.activeBean.email}" maxlength="50" cssClass="table-input-field" theme="simple" readonly="true"/>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>     
				 
				<input type="button" name="selectUserButton" id="selectUserButton" class="btn-action-gray" value="Select User" onClick="javascript:checkSelect();">
			</fieldset>
		</c:if> 
	</s:form>
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
