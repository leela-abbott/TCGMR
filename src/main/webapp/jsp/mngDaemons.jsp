<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "Manage Daemon Processes"; %>
<%@ include file="/include/header.jsf" %>
<c:set var="pageTitle" value="Manage Daemon Processes" scope="request" />
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />

<abbott:securePage
    userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
    requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
    comparisonType=">="
    forwardPage="/insufficientPrivelage.action" />

<style type="text/css">
    .form-center-container {
        width: 729px;
        margin: 25px auto 0 auto;
        font-family: Arial, Helvetica, sans-serif;
    }
    
    .form-center-container table {
        width: 729px;
        border-collapse: collapse;
        margin: 0 auto;
    }

    .form-center-container td {
        padding: 8px 10px;
        vertical-align: middle;
    }

    .tableHeadingSection {
        color: #003366 !important;
        font-weight: bold !important;
        font-size: 13px !important;
        padding: 6px 10px !important;
        background-color: #b2d1f0 !important;
    }

    .cmdOptLbl {
        font-weight: bold;
        font-size: 12px;
        color: #333333;
    }

    .form-center-container select {
        box-sizing: border-box;
        height: 22px;
        font-size: 12px;
        border: 1px solid #7f9db9;
        padding: 1px 3px;
        text-align: center;
    }

    .btn-tcgm-action {
        background: #ffcc00;
        background: linear-gradient(to bottom, #ffe066 0%, #ffcc00 40%, #ffa500 100%);
        border: 1px solid #b58000;
        border-radius: 4px;
        color: #000000;
        font-family: Arial, sans-serif;
        font-size: 11px;
        font-weight: bold;
        padding: 4px 14px;
        cursor: pointer;
        box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        display: inline-block;
        text-decoration: none;
        white-space: nowrap;
        width: 130px; /* Fixed width to make all buttons align perfectly */
        text-align: center;
    }

    .btn-tcgm-action:hover {
        background: linear-gradient(to bottom, #fff099 0%, #ffdb4d 40%, #ffb833 100%);
        border-color: #8c6300;
    }

    .btn-tcgm-action:active {
        background: linear-gradient(to bottom, #ffa500 0%, #ffcc00 100%);
        box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.3);
    }
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <div id="divToHide" class="form-center-container">
        <s:form id="mngDaemonsForm" name="mngDaemonsForm" method="post" action="mngDaemons">
            <s:hidden name="cmd" id="cmd" />
            
            <table border="0" cellpadding="2">
                <!-- Process Scheduler Section -->
                <tr>
                    <td class="tableHeadingSection" colspan="3">Process Scheduler</td>
                </tr>
                <tr> 
                    <td class="cmdOptLbl" style="width: 200px;">Status</td>
                    <td style="width: 350px;"><s:property value="processSchedulerStatus" /></td>
                    <td style="text-align: right; width: 179px;">
                        <button type="button" class="btn-tcgm-action" onclick="changeCmdAndSubmit(document.forms['mngDaemonsForm'], 'START_SCHEDULER')">Start Scheduler</button>
                    </td>
                </tr>
                <tr>
                    <td class="cmdOptLbl">Current Process</td>
                    <td><s:property value="currentProcess" /></td>
                    <td style="text-align: right;">
                        <button type="button" class="btn-tcgm-action" onclick="changeCmdAndSubmit(document.forms['mngDaemonsForm'], 'STOP_SCHEDULER')">Stop Scheduler</button>
                    </td>
                </tr>
                
                <!-- Spacer Row -->
                <tr><td colspan="3" style="height: 15px;"></td></tr>

                <!-- Datafeed Monitor Section -->
                <tr>
                    <td class="tableHeadingSection" colspan="3">Datafeed Monitor</td>
                </tr>
                <tr>
                    <td class="cmdOptLbl">Status</td>
                    <td><s:property value="datafeedMonitorStatus" /></td>
                    <td style="text-align: right;">
                        <button type="button" class="btn-tcgm-action" onclick="changeCmdAndSubmit(document.forms['mngDaemonsForm'], 'START_MONITOR')">Start Monitor</button>
                    </td>
                </tr>
                <tr>
                    <td class="cmdOptLbl">Current Feed</td>
                    <td><s:property value="currentFeed" /></td>
                    <td style="text-align: right;">
                        <button type="button" class="btn-tcgm-action" onclick="changeCmdAndSubmit(document.forms['mngDaemonsForm'], 'STOP_MONITOR')">Stop Monitor</button>
                    </td>
                </tr>

                <!-- Spacer Row -->
                <tr><td colspan="3" style="height: 15px;"></td></tr>

                <!-- System Compact Adjustment Section -->
                <tr>
                    <td class="tableHeadingSection" colspan="3">System Compact Adjustment</td>
                </tr>
                <tr> 
                    <td class="cmdOptLbl" colspan="3" style="padding-top: 12px; padding-bottom: 6px;">
                        <!-- Updated condition to target safe context action property value mapping -->
                        <s:if test="batchStart == 0">
                            The System Compact Process Will not Run Today.
                        </s:if>
                        <s:else>
                            The System Compact Process runs at <s:property value="batchStart" />pm
                        </s:else>
                    </td>
                </tr>
                <tr> 
                    <td class="cmdOptLbl" colspan="2" style="padding-top: 6px;">
                        Adjust today's System Compact time by &nbsp;
                        <s:select name="compactTime" id="compactTime"
                                  list="#{'0':'+0', '1':'+1', '2':'+2', '3':'+3', '-1':'Do not Run'}" 
                                  theme="simple" />
                        &nbsp; hrs
                    </td>
                    <td style="text-align: right; padding-top: 6px;">
                        <button type="button" class="btn-tcgm-action" onclick="changeCmdAndSubmit(document.forms['mngDaemonsForm'], 'APPLY_ADJUSTMENT')">Apply Adjustment</button>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

<script type="text/javascript">
function changeCmdAndSubmit(form, cmd) {
    document.getElementById('cmd').value = cmd;
    form.submit();
}
</script>

<%@ include file="/include/footer.jsf" %>
