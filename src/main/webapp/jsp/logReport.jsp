<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 1. Pre-initialize pageTitle context BEFORE pulling template headers --%>
<%! String pageTitle = " Application and Error Logs- TCGM"; %>

<%-- 2. Pull down document headers matching your custom job queue layout specs --%>
<%@ include file="/include/headerJbQue.jsf" %>

<head>
<style>
    /* Full fluid width layout matching production look and feel metrics */
    .logs-wrapper-table { width: 100%; border-collapse: collapse; border: 0; }
    .logs-grid-table { width: 100%; border-collapse: collapse; text-align: left; }
    .logs-grid-table td { padding: 4px 6px; }
    .text-right { text-align: right; }
    .text-center { text-align: center; }
</style>
</head>

<body leftmargin="100" topmargin="0" marginwidth="4" marginheight="0">
    <a name="top"></a>

<table width="640" height="62" border="0" cellpadding="0" cellspacing="0" background="images/masthead.png">
    <tr>
        <td width="100">&nbsp;&nbsp;</td>
        <td class="pageHeading" nowrap>&nbsp;</td>
        <td class="pageHeading" nowrap>Application and Error Logs- TCGM</td>
    </tr>
</table> 
        
<s:form id="processingForm" name="processingForm" action="" method="post" theme="simple">
<table class="logs-wrapper-table" cellpadding="2">
    <tr>
        <td class="pageHeading">
            <div class="text-right">
                <input name="button" type="button" onClick="close_win()" value="Close Window"> 
            </div>
        </td>  
    </tr>
    <tr>
        <td class="tableEntry"> 
            <div align="left">
                Job Queue Id - <s:property value="selProcessId" />,&nbsp; <s:property value="selJodDesc" /> <s:property value="selJodStatus" />
            </div>
        </td>
    </tr>

    <%-- 
      3. LIVE REPLICATION BUG FIX:
      Swapped dangerous index position slicing operations for a safe, modern split-array look-up.
      This handles double pipes seamlessly without ever risking a page rendering compilation crash.
    --%>
    <s:if test="selJodStatus != null && selJodStatus.contains('|')">
        <s:set var="statusTokens" value="selJodStatus.split('\\\|')" />
        <s:set var="strModelName" value="#statusTokens.length > 1 ? #statusTokens[1].trim() : ''" />
    </s:if>
    <s:else>
        <s:set var="strModelName" value="''" />
    </s:else>
  
    <tr>
        <td>
            <%-- Application Log Evaluation Branch --%>
            <s:if test="logList != null && logList.size() != 0">
                <table width="100%" class="logs-grid-table" cellpadding="3" style="margin-bottom: 20px;">
                    <tr>
                        <td width="30%">&nbsp;</td>
                        <td width="40%" class="text-center pageHeading">Application Log</td>
                        <td width="30%">&nbsp;</td>
                    </tr>
                </table>
                
                <table width="100%" class="logs-grid-table" cellpadding="3" style="border: 1px solid #cccccc;">
                    <tr>
                        <td class="tableHeading">Model Name</td>
                        <td class="tableHeading">Proc Name</td>
                        <td class="tableHeading">Status</td>
                        <td class="tableHeading">Message</td>  
                        <td class="tableHeading" style="width: 200px;">Time</td> 
                    </tr> 
                    <s:iterator value="logList" var="prfm">
                        <tr>
                            <td class="tableEntry"><s:property value="#attr.strModelName" /></td>
                            <td class="tableEntry"><s:property value="#prfm.procname" /></td>
                            <td class="tableEntry"><s:property value="#prfm.status" /></td>
                            <td class="tableEntry"><s:property value="#prfm.msg" /></td> 
                            <td class="tableEntry" style="width: 200px;"><s:property value="#prfm.crtdate" /></td> 
                        </tr> 
                    </s:iterator>  
                </table>
            </s:if>
            <s:else>
                <table width="500" class="logs-grid-table" align="center" cellpadding="0">
                    <tr>
                        <td style="width: 100px;">&nbsp;</td>
                        <td class="text-center pageHeading">Application Log</td>
                    </tr>
                    <tr>
                        <td style="width: 200px;">&nbsp;</td>
                        <td class="text-center tableEntry">There is no data in the Application Log Table</td>
                    </tr>
                </table> 
            </s:else>
        </td> 
    </tr>
    <tr><td><br><br></td></tr>
    
    <tr>
        <td>
            <%-- Error Log Evaluation Branch --%>
            <s:if test="errorList != null && errorList.size() != 0">
                <table width="100%" class="logs-grid-table" cellpadding="3" style="margin-bottom: 10px;">
                    <tr> 
                        <td style="width: 100px;">&nbsp;</td>
                        <td class="pageHeading">Error Log</td>
                    </tr>
                </table> 
                
                <table width="100%" class="logs-grid-table" cellpadding="3" style="border: 1px solid #cccccc;">
                    <tr>
                        <td class="tableHeading">Modelid</td>
                        <td class="tableHeading">ProcName</td>
                        <td class="tableHeading">Status</td>
                        <td class="tableHeading">Message</td>  
                        <td class="tableHeading" style="width: 200px;">Time</td>  
                    </tr>
                    <s:iterator value="errorList" var="errorLog">
                        <tr>
                            <td class="tableEntry"><s:property value="#errorLog.modelid" /></td>
                            <td class="tableEntry"><s:property value="#errorLog.procname" /></td>
                            <td class="tableEntry"><s:property value="#errorLog.status" /></td>
                            <td class="tableEntry"><s:property value="#errorLog.msg" /></td> 
                            <td class="tableEntry" style="width: 200px;"><s:property value="#errorLog.crtdate" /></td> 
                        </tr> 
                    </s:iterator>  
                </table> 
            </s:if>
            <s:else> 
                <table width="500" class="logs-grid-table" cellpadding="0">
                    <tr>
                        <td style="width: 100px;">&nbsp;</td>
                        <td class="text-center pageHeading">Error Log</td>
                    </tr> 
                    <tr>
                        <td style="width: 200px;">&nbsp;</td>
                        <td class="text-center tableEntry">There is no data in the Error Log Table</td>
                    </tr> 
                </table> 
            </s:else>
        </td>
    </tr>

    <tr>
        <td>
            <%@ include file="/include/footer.jsf" %> 
        </td>
    </tr>
</table> 
</s:form>
    
<script type="text/javascript">
function close_win() {
    window.close();
}
</script>
</body>
</html>