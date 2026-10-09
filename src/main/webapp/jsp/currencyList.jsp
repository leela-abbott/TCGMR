<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="abbott" uri="/WEB-INF/taglib/abbott.tld" %>

<%! String pageTitle = "Currency Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<c:set var="pageTitle" value="Currency Maintenance" scope="request" />
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />

<abbott:securePage userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
    requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
    comparisonType="="
    forwardPage="/insufficientPrivelage.action" />

<style type="text/css">
    .form-center-container {
        width: 500px;
        margin: 25px auto 0 auto;
        font-family: Arial, Helvetica, sans-serif;
    }
    
    .form-center-container table {
        width: 500px;
        border-collapse: collapse;
        margin: 0 auto;
    }

    .form-center-container td {
        padding: 6px 8px;
        vertical-align: middle;
    }

    .tableEntry,
    .fltrTblHdngLeft td {
        color: #003366 !important;
        font-weight: bold !important;
        font-size: 12px !important;
        text-align: left !important;
        padding: 6px 8px !important;
        background-color: #b2d1f0 !important;
        border: 1px solid #ffffff;
    }

    .form-center-container input[type="text"] {
        box-sizing: border-box;
        height: 22px;
        font-size: 12px;
        border: 1px solid #7f9db9;
        padding: 1px 3px;
        text-align: left;
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
        margin-left: 8px;
        display: inline-block;
        text-decoration: none;
    }

    .btn-tcgm-action:hover {
        background: linear-gradient(to bottom, #fff099 0%, #ffdb4d 40%, #ffb833 100%);
        border-color: #8c6300;
    }

    .btn-tcgm-action:active {
        background: linear-gradient(to bottom, #ffa500 0%, #ffcc00 100%);
        box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.3);
    }

    .tcgm-check-btn {
        background: #ffcc00;
        background: linear-gradient(to bottom, #ffe066 0%, #ffcc00 40%, #ffa500 100%);
        border: 1px solid #b58000;
        border-radius: 2px;
        color: #000000;
        font-family: Arial, sans-serif;
        font-size: 10px;
        font-weight: bold;
        padding: 2px 6px;
        cursor: pointer;
        box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        display: inline-block;
    }

    .tcgm-check-btn:hover {
        background: linear-gradient(to bottom, #fff099 0%, #ffdb4d 40%, #ffb833 100%);
        border-color: #8c6300;
    }
</style>

<script>
function chgActCmdSubmit(form,cmd,action)
{
	form.cmd.value = cmd;
	form.action = action;
	form.submit();
}
</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>
 
    <div class="form-center-container">
        <s:form method="post" id="currencyCodeForm" name="currencyCodeForm" action="currencyCodeMaint" theme="simple">
            
            <s:hidden name="cmd" />
            <s:hidden name="searchObject.curCode" />
            
            <table cellspacing="0" width="500">
                <tr>
                    <td class="tableEntry" width="30%">Currency Code</td>
                    <td class="tableEntry" width="70%">Currency Name</td>
                </tr>
                <tr>
                    <td style="text-align: left;">
                        <s:hidden name="currencyCodeToEdit.newCurrencyCode" />
                        <s:textfield name="currencyCodeToEdit.curCode" 
                                     maxlength="5" 
                                     cssClass="mntWidth5" 
                                     disabled="%{!currencyCodeToEdit.newCurrencyCode}" 
                                     theme="simple" style="width: 90%;" />
                    </td>
                    <td style="text-align: left;">
                        <s:textfield name="currencyCodeToEdit.curName" 
                                     maxlength="50" 
                                     cssClass="mntWidth50" 
                                     theme="simple" style="width: 100%;" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right; padding-top: 15px; padding-bottom: 15px;">
                        <button type="button" class="btn-tcgm-action" onclick="javascript:chgActCmdSubmit(document.currencyCodeForm,'save','saveCurrencyCode.action');">
                            Save
                        </button>
                        <button type="button" class="btn-tcgm-action" onclick="javascript:chgActCmdSubmit(document.currencyCodeForm,'cancel','currencyCodeMaint.action');">
                            Cancel
                        </button>
                    </td>
                </tr>
            </table>

            <hr style="margin: 20px auto; border: 0; border-top: 1px solid #cccccc; width: 500px;"/>

            <table width="500" cellspacing="0" style="table-layout: fixed; width: 500px;">
                <tr>
                    <td colspan="3" style="text-align: right; padding-top: 0; padding-bottom: 15px;">
                        <button type="button" class="btn-tcgm-action" onclick="javascript:deleteCurrencyCode(document.currencyCodeForm,'deleteselected','deleteCurrencyCode.action');">
                            Delete Selected
                        </button>
                    </td>
                </tr>
                <tr class="fltrTblHdngLeft" style="height: 26px;">
                    <td width="30%">Currency Code</td>
                    <td width="55%">Currency Name</td>
                    <td width="15%" style="text-align: center !important; vertical-align: middle;">
                        <span class="tcgm-check-btn"
                              onClick="return toggleSelectAll('currencyList','selected','<s:property value="currencyListSize"/>');"
                              title="Toggle Select All">
                            &#10003;
                        </span>
                    </td>
                </tr>
        
                <c:if test="${currencyListSize != 0}">
                    <c:forEach var="currencyCodeBean" items="${currencylist}" varStatus="status">                  
                        <abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${status.index}" id="mntRow"> 
                            <td width="30%" class="mntLeft" style="padding: 5px; vertical-align: middle; text-align: left;">
                                <a href="javascript:chgCurCodeAndSubmit(document.currencyCodeForm,'<c:out value="${currencyCodeBean.curCode}" />','editCurrencyCode.action','edit','currencyCodeToEdit.curCode')">
                                    <c:out value="${currencyCodeBean.curCode}" />
                                </a>                            
                            </td>       
                            <td width="55%" class="mntLeft" style="padding: 5px; vertical-align: middle; text-align: left;">
                                <c:out value="${currencyCodeBean.curName}" />
                            </td>
                            <td width="15%" class="mntCenter" style="padding: 5px; vertical-align: middle; text-align: center;">
                                <input type="checkbox" name="currencylist[<c:out value="${status.index}"/>].selected" value="on">
                            </td>
                        </abbott:row>
                    </c:forEach>                    
                </c:if>
            </table>
            
            <c:if test="${currencyListSize == 0}">
                <%@ include file="/include/recordsNotFound.jsf" %>
            </c:if>
        </s:form>
    </div>
    
<script type="text/javascript">
    function deleteCurrencyCode(form, cmd, action) {
        if (confirm("Are you sure that you would like to delete selected currency code(s). ")) {
            chgActCmdSubmit(form, cmd, action);
        }
    }   
</script>
    
<%@ include file="/include/footer.jsf" %>
