<%! String pageTitle = "Currency Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<%-- Core Tag Libraries --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="abbott" uri="/WEB-INF/taglib/abbott.tld" %>

<%-- Page is only accessible by Supervisor/Analyst --%>
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />

<abbott:securePage userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
    requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
    comparisonType="="
    forwardPage="/insufficientPrivelage.action" />

<style type="text/css">
    /* Form centering structure layout container wrapper */
    .form-center-container {
        margin: 20px auto;
        display: table;
        width: 500px;
    }
    
    /* Modern corporate styling matching old button image appearances */
    .btn-tcgm-action {
        background: linear-gradient(to bottom, #fff080 0%, #ffcc00 100%);
        border: 1px solid #cca300;
        border-radius: 4px;
        color: #333333;
        font-family: Arial, sans-serif;
        font-size: 12px;
        font-weight: bold;
        padding: 4px 14px;
        cursor: pointer;
        box-shadow: 0px 1px 2px rgba(0,0,0,0.15);
        text-decoration: none;
        display: inline-block;
    }

    .btn-tcgm-action:hover {
        background: linear-gradient(to bottom, #ffe64d 0%, #e6b800 100%);
        border-color: #b38f00;
    }

    .btn-tcgm-action:active {
        box-shadow: inset 0px 1px 3px rgba(0,0,0,0.2);
    }

.tcgm-check-btn {
	background: linear-gradient(to bottom, #fff085 0%, #ffcc24 100%);
	border: 1px solid #caa016;
	border-radius: 2px;
	width: 18px;
	height: 14px;
	cursor: pointer;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	padding: 0;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}

.tcgm-check-btn::after {
	content: '';
	display: block;
	width: 3px;
	height: 6px;
	border-width: 0 2px 2px 0;
	transform: rotate(45deg);
	margin-bottom: 2px;
</style>

<script>

function chgActCmdSubmit(form,cmd,action)
{
	form.cmd.value = cmd;
	form.action = action;
	alert("with in chgActCmdSubmit");
	form.submit();
}

</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>
 
    <div class="form-center-container">
        <%-- Struts 2 Form targeting unified layout mapping --%>
        <s:form method="post" id="currencyCodeForm" name="currencyCodeForm" action="currencyCodeMaint" theme="simple">
            
            <%-- Form State Properties --%>
            <s:hidden name="cmd" />
            <s:hidden name="searchObject.curCode" />
            
            <table cellspacing="0" width="500">
                <tr>
                    <td class="tableEntry">Currency Code</td>
                    <td class="tableEntry">Currency Name</td>
                </tr>
                <tr>
                    <td class="mntLeft">
                        <s:hidden name="currencyCodeToEdit.newCurrencyCode" />
                        <s:textfield name="currencyCodeToEdit.curCode" 
                                     maxlength="5" 
                                     cssClass="mntWidth5" 
                                     disabled="%{!currencyCodeToEdit.newCurrencyCode}" 
                                     theme="simple" />
                    </td>
                    <td>
                        <s:textfield name="currencyCodeToEdit.curName" 
                                     maxlength="50" 
                                     cssClass="mntWidth50" 
                                     theme="simple" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="right" style="padding-top: 8px;">
                        <button type="button" class="btn-tcgm-action" onclick="javascript:chgActCmdSubmit(document.currencyCodeForm,'save','saveCurrencyCode.action');">
                            Save
                        </button>
                        <button type="button" class="btn-tcgm-action" onclick="javascript:chgActCmdSubmit(document.currencyCodeForm,'cancel','currencyCodeMaint.action');">
                            Cancel
                        </button>
                    </td>
                </tr>
            </table>

            <hr style="margin: 15px 0; border: 0; border-top: 1px solid #ccc;"/>

            <table width="500" cellspacing="0" style="table-layout: fixed; width: 500px;">
                <tr>
                    <td colspan="3" class="right" style="padding-bottom: 8px;">
                        <button type="button" class="btn-tcgm-action" onclick="javascript:deleteCurrencyCode(document.currencyCodeForm,'deleteselected','deleteCurrencyCode.action');">
                            Delete Selected
                        </button>
                    </td>
                </tr>
                <%-- Synchronized Blue Banner Header Line Alignment --%>
                <tr class="fltrTblHdngLeft" style="background-color: #b0d5f5; height: 26px;">
                    <td width="30%" style="font-weight: bold; padding: 4px; vertical-align: middle; color: #003366;">Currency Code</td>
                    <td width="55%" style="font-weight: bold; padding: 4px; vertical-align: middle; color: #003366;">Currency Name</td>
                    <td width="15%" class="center" style="padding: 4px; text-align: center; vertical-align: middle;">
                        <%-- Graphical Character Tick-Mark Selector Trigger element --%>
                        <span class="tcgm-check-btn"
                              onClick="return toggleSelectAll('currencyList','selected','<s:property value="currencyListSize"/>');"
                              title="Toggle Select All">
                            &#x2714;
                        </span>
                    </td>
                </tr>
        
                <c:if test="${currencyListSize != 0}">
                    <c:forEach var="currencyCodeBean" items="${currencylist}" varStatus="status">                  
                        <abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${status.index}" id="mntRow"> 
                            <td width="30%" class="mntLeft" style="padding: 6px 4px; vertical-align: middle;">
                                <a href="javascript:chgCurCodeAndSubmit(document.currencyCodeForm,'<c:out value="${currencyCodeBean.curCode}" />','editCurrencyCode.action','edit','currencyCodeToEdit.curCode')">
                                    <c:out value="${currencyCodeBean.curCode}" />
                                </a>                            
                            </td>       
                            <td width="55%" class="mntLeft" style="padding: 6px 4px; vertical-align: middle;">
                                <c:out value="${currencyCodeBean.curName}" />
                            </td>
                            <td width="15%" class="mntCenter" style="padding: 6px 4px; text-align: center; vertical-align: middle;">
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
