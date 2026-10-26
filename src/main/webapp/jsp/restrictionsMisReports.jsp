<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%! String pageTitle="Report Restrictions";%>
<%@ include file="/include/header.jsf" %>
<head>
<style>
    .btn-prod { 
        background-color: #FFC107; 
        border: 1px solid #757575; 
        border-radius: 3px; 
        color: #212121; 
        font-family: Arial, sans-serif; 
        font-size: 11px; 
        font-weight: bold; 
        padding: 2px 10px; 
        cursor: pointer; 
        text-align: center; 
    }
    .tableHeading {
        font-family: Arial, sans-serif;
        font-size: 14px;
        font-weight: bold;
        color: #002266;
    }
    .tableSubHeader {
        background-color: #b4d8f4;
        font-family: Arial, sans-serif;
        font-size: 11px;
        font-weight: bold;
        color: #002266;
        padding: 4px;
    }
    .commandOption {
        font-family: Arial, sans-serif;
        font-size: 11px;
    }
    .hidden { display: none; }
    
    .center-container {
        width: 100%;
        display: flex;
        justify-content: center;
        padding-top: 20px;
    }
</style>
</head>

<script type="text/javascript">
function submitRestrictions(formObj) {
    if (formObj) {
        formObj.elements['cmd'].value = 'ADD_JOB';
        formObj.elements['restrictionEntered'].checked = true;
        formObj.elements['restrictionEntered'].value = 'true'; 
        if (formObj.elements['chkRestrict']) {
            formObj.elements['chkRestrict'].value = 'false';
        }
        if (formObj.elements['formHandler']) {
            formObj.elements['formHandler'].value = "miscReports.action";
        }
        
        formObj.action = "miscReports.action";
        formObj.submit();
    }
}

function cancelAddJob(formObj) {
    if (formObj) {
        formObj.elements['cmd'].value = 'CANCEL';
        formObj.action = "miscReports.action";
        formObj.submit();
    }
}
</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<c:set var="pageTitle" value="Report Restrictions" scope="request" />
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<div class="center-container">
    <s:form id="restrictionForm" action="miscReports" method="post" theme="simple">
        <table width="690" border="0" cellpadding="2" cellspacing="1" align="center">
            <!-- Main Header Row -->
            <tr>
                <td colspan="2" class="tableHeading" height="30" valign="middle">Enter Job/Report Restrictions</td>
                <td align="right">
                    <button type="button" class="btn-prod" onclick="cancelAddJob(document.getElementById('restrictionForm'))">Cancel</button>
                    <button type="button" class="btn-prod" onclick="submitRestrictions(document.getElementById('restrictionForm'))">Add Job</button>
                </td>
            </tr>
            
            <!-- Restored Missing Table Sub-Headers Row -->
            <tr>
                <td width="145" class="tableSubHeader">Restriction</td>
                <td width="80" class="tableSubHeader">Operation</td>
                <td class="tableSubHeader">Value</td>
            </tr>

            <!-- Form Field Input Rows -->
            <tr>
                <td class="commandOption" height="24">Rpt Affiliate(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.rptAffLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
            <tr>
                <td class="commandOption" height="24">Sup Affiliate(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.supAffLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
            <tr>
                <td class="commandOption" height="24">End Affiliate(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.endAffLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
            <tr>
                <td class="commandOption" height="24">Rpt List Number(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.rptListLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
            <tr>
                <td class="commandOption" height="24">Sup List Number(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.supListLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
            <tr>
                <td class="commandOption" height="24">End List Number(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.endListLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
            <tr>
                <td class="commandOption" height="24">Group Id(s)</td>
                <td class="commandOption">IN</td>
                <td><s:textarea name="restrictions.groupIdLimiterValues" rows="4" cols="25" cssClass="commandOption" /></td>
            </tr>
        </table>
        
        <!-- Scope context tracking parameters -->
        <s:hidden name="formHandler" id="formHandler" value="%{formHandler}" />
        <s:hidden name="cmd" id="cmd" />
        <s:hidden name="jobName" id="jobName" value="%{jobName}" />
        <s:hidden name="chkRestrict" id="chkRestrict" value="%{chkRestrict}" />
        <s:hidden name="selReport" id="selReport" value="%{selReport}" />
        <s:hidden name="selReportDest" id="selReportDest" value="%{selReportDest}" />
        <s:hidden name="chkImmediate" id="chkImmediate" value="%{chkImmediate}" />
        <s:hidden name="numCopies" id="numCopies" value="%{numCopies}" />

        <div class="hidden">
            <s:checkbox name="restrictionEntered" id="restrictionEntered" />
        </div>
    </s:form>
</div>

<%@ include file="/include/footer.jsf" %>
</body>