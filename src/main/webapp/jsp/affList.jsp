<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "Affiliate Area UnWanted Division"; %>
<%@ include file="/include/header.jsf" %>

<c:set var="pageTitle" value="Affiliate Area UnWanted Division" scope="request" />

<abbott:securePage userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
    requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
    comparisonType="="
    forwardPage="/insufficientPrivelage.action" />

<style type="text/css">
    .form-center-container {
        width: 800px;
        margin: 25px auto 0 auto;
        font-family: Arial, Helvetica, sans-serif;
    }
    
    .form-center-container table {
        width: 800px;
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

    .form-center-container select,
    .form-center-container select option {
        box-sizing: border-box;
        height: 22px;
        font-size: 12px;
        border: 1px solid #7f9db9;
        padding: 1px 3px;
        text-align: left !important;
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
        margin: 0 4px;
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
    
    .note-text {
        color: red;
        font-size: 11px;
        text-align: right !important;
        padding-top: 4px;
    }
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>
 
    <div class="form-center-container">
        <s:form id="affCodeForm" name="affCodeForm" method="post" action="affAreaMaint">
            <s:hidden name="cmd" id="cmd" />
            <s:hidden name="affcode" id="affcode" />
            
            <table cellspacing="0" width="800">
                <tr>
                    <td class="tableEntry" width="22%">Affiliate</td>
                    <td class="tableEntry" width="26%">Area</td>
                    <td class="tableEntry" width="26%">Region</td>
                    <td class="tableEntry" width="26%">Sector</td>
                </tr>
                <tr>
                    <td class="mntLeft" width="22%" style="text-align: left;">
                        <s:select name="affListCode" id="affListCode" list="affCodeList" 
                                  listKey="affCode" listValue="affDesc" 
                                  headerKey="-1" headerValue="Select Affiliate"
                                  onchange="setFilter();" cssClass="mntLeft" style="width: 100%; text-align: left;" />
                    </td>
                    <td width="26%" style="text-align: left; font-size: 12px; font-weight: bold; color: #333333;">
                        <label id="areaLab"> </label> 
                    </td>
                    <td width="26%" style="text-align: left; font-size: 12px; font-weight: bold; color: #333333;">
                        <label id="regLab"> </label> 
                    </td>
                    <td width="26%" style="text-align: left; font-size: 12px; font-weight: bold; color: #333333;">
                        <label id="secLab"> </label> 
                    </td>
                </tr>
                <tr>
                    <td colspan="4" class="note-text">
                        Note: The Save Button Makes the selected record as UnWanted
                    </td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: right; padding-top: 10px; padding-bottom: 10px;">
                        <button type="button" class="btn-tcgm-action" onclick="doSave();">Save</button>
                        <button type="button" class="btn-tcgm-action" onclick="doCancel();">Cancel</button>
                    </td>
                </tr>
            </table>

            <hr style="margin: 20px auto; border: 0; border-top: 1px solid #cccccc; width: 800px;"/>
            <table width="800" cellspacing="0">
                <tr>
                    <td colspan="5" class="note-text">
                        Note: The Delete Selected Button Makes the selected record as Wanted
                    </td>
                </tr>
                <tr>
                    <td colspan="5" style="text-align: right; padding-top: 10px; padding-bottom: 15px;">
                        <button type="button" class="btn-tcgm-action" onclick="deleteCurrencyCode();">Delete Selected</button>
                    </td>
                </tr>
                <tr class="fltrTblHdngLeft">
                    <td width="22%">Affiliate</td>
                    <td width="26%">Area</td>
                    <td width="26%">Region</td>
                    <td width="20%">Sector</td>
                    <td width="6%" style="text-align: center !important;">
                        <span class="tcgm-check-btn" 
                              onclick="return toggleSelectAll('affCodeList','selected','${affListSize}');" 
                              title="Toggle Select All">&#10003;</span>
                    </td>
                </tr>
        
                <s:if test="affListSize > 0">
                    <c:forEach var="affCodeBean" items="${afflist}" varStatus="status">
                        <abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="${status.index}" id="mntRow"> 
                            <td class="mntLeft" style="text-align: left; width: 22%;">
                                <c:out value="${affCodeBean.affDesc}" />(<c:out value="${affCodeBean.affCode}" />)
                            </td>       
                            <td class="mntLeft" style="text-align: left; width: 26%;">
                                <c:out value="${affCodeBean.areaDesc}" />
                            </td>
                            <td class="mntLeft" style="text-align: left; width: 26%;">
                                <c:out value="${affCodeBean.regDesc}" />
                            </td>       
                            <td class="mntLeft" style="text-align: left; width: 20%;">
                                <c:out value="${affCodeBean.secDesc}" />
                            </td>
                            <td class="mntCenter" style="text-align: center; width: 6%;">
                                <input type="checkbox" name="afflist[${status.index}].selected" value="true" <c:if test="${affCodeBean.selected}">checked="checked"</c:if> />
                            </td>
                        </abbott:row>
                    </c:forEach>                   
                </s:if>
            </table>
            
            <s:if test="affListSize == 0">
                <%@ include file="/include/recordsNotFound.jsf" %>
            </s:if>
        </s:form>
    </div>
    
<script type="text/javascript">
    function deleteCurrencyCode() {
        if (confirm("Are you sure that you would like make selected Affiliate code(s) to Wanted?. ")) {
            chgActCmdSubmit(document.forms['affCodeForm'], 'delete', 'affAreaMaint.action');
        }
    }   
    function setFilter(){
        var selVal = document.getElementById('affListCode').value;
        if(selVal == "-1"){ 
            document.getElementById('areaLab').innerHTML = '';
            document.getElementById('regLab').innerHTML = '';
            document.getElementById('secLab').innerHTML = '';
        } else {
            var x = selVal.split(',');
            document.getElementById('areaLab').innerHTML = x || '';
            document.getElementById('regLab').innerHTML = x || '';
            document.getElementById('secLab').innerHTML = x || '';
        }   
    }
    function doSave(){
        var selVal = document.getElementById('affListCode').value;
        if(selVal == "-1"){ 
            alert("Select a Affiliate to be Saved");
        } else {
            var x = selVal.split(',');
            document.getElementById('affcode').value = x;        
            chgActCmdSubmit(document.forms['affCodeForm'], 'save', 'affAreaMaint.action');
        }
    }
    function doCancel(){
        document.getElementById('affListCode').value = "-1";
        document.getElementById('areaLab').innerHTML = '';
        document.getElementById('regLab').innerHTML = '';
        document.getElementById('secLab').innerHTML = '';
    }
    
    function chgActCmdSubmit(form,cmd,action)
    {
    	form.cmd.value = cmd;
    	form.action = action;
    	form.submit();
    }
</script>
    
<%@ include file="/include/footer.jsf" %>
            