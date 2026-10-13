<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 1. Initialize pageTitle context BEFORE pulling template headers --%>
<%! String pageTitle = "Generate Report"; %>

<%-- 2. Pull down document headers matching your custom job queue layout specs --%>
<%@ include file="/include/headerJbQue.jsf" %>

<head>
<style>
    /* Clean layout metrics replacing old structural attributes */
    .popup-wrapper-table { 
        width: 100%; 
        border-collapse: collapse; 
        text-align: left; 
    }
    .popup-wrapper-table td { 
        padding: 4px 8px; 
    }
    .text-right { 
        text-align: right; 
    }
</style>
</head>

<body leftmargin="100" topmargin="0" marginwidth="4" marginheight="0">
    <a name="top"></a>

<table width="600" height="62" border="0" cellpadding="0" cellspacing="0" background="images/masthead.png">
    <tr>
        <td width="100">&nbsp;&nbsp;</td>
        <td class="pageHeading" nowrap>&nbsp;</td>
    </tr>
</table> 
		
<s:form id="processingForm" name="processingForm" action="" method="post" theme="simple">
    <%-- 3. Include standard validation and action message display alerts --%>
    <%@ include file="/include/errorDisplay.jsf" %>
    
    <table class="popup-wrapper-table" cellpadding="2">
        <tr>
            <td class="tableEntry" style="width: 120px;">Job Queue Id</td>
            <td class="tableEntry" style="width: 20px;">&nbsp;&nbsp;</td>
            <%-- 4. Safely evaluates property directly from the ValueStack --%>
            <td class="tableEntry">
                <b><s:property value="selProcessId" /></b>
            </td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;</td>
            <td>&nbsp;&nbsp;</td>
            <td class="pageHeading">
                <div class="text-right">
                    <input name="button" type="button" onClick="close_win()" value="Close Window"> 
                </div>
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
