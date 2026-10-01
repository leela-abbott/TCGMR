<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "Report User Creation"; %>
<%@ include file="/include/header.jsf" %>
<c:set var="pageTitle" value="Report User Creation" scope="request" />

<head>
<style>
    /* Modernized Pure CSS Gradient Button matching design metrics */
    .tcgm-btn {
        display: inline-block;
        padding: 6px 24px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 13px;
        font-weight: bold;
        color: #112233 !important;
        text-decoration: none;
        background: linear-gradient(to bottom, #fff093 0%, #ffca36 100%);
        border: 1px solid #c59b1a;
        border-radius: 4px;
        box-shadow: 0 1px 2px rgba(0,0,0,0.15);
        cursor: pointer;
        text-shadow: 0 1px 0 rgba(255,255,255,0.4);
        transition: all 0.1s ease-in-out;
    }
    .tcgm-btn:hover {
        background: linear-gradient(to bottom, #fff4aa 0%, #ffd44f 100%);
        border-color: #b58d12;
    }
    .tcgm-btn:active {
        background: #ffca36;
        box-shadow: inset 0 1px 3px rgba(0,0,0,0.2);
    }
    
    /* Clean-cut Input Field System with clear visual hierarchy mapping */
    .tcgm-input-readonly {
        border: 1px solid #cccccc;
        background-color: #ffffff;
        color: #333333;
        padding: 5px 8px;
        width: 200px;
        height: 24px;
        font-family: Arial, sans-serif;
        font-size: 13px;
        border-radius: 3px;
        box-sizing: border-box;
    }
    
    /* Dropdown Selection Field Structural Alignment */
    .tcgm-select {
        border: 1px solid #cccccc;
        padding: 5px 8px;
        width: 200px;
        height: 24px;
        font-family: Arial, sans-serif;
        font-size: 13px;
        border-radius: 3px;
        box-sizing: border-box;
    }
    
    /* Label Component formatting layer matching your reference layout metrics */
    .commandOptionLabel {
        font-family: Arial, sans-serif;
        font-size: 13px;
        font-weight: normal;
        color: #222222;
        text-align: left;
        width: 130px;
    }

    /* Structural Table layout layer matching reference look */
    table[align="center"] {
        width: 400px !important;
        margin-top: 40px !important;
        margin-bottom: 30px !important;
    }

    table[align="center"] td {
        padding: 8px 0 !important;
    }

    /* Target the button table spacing to align beautifully in center */
    table table {
        margin-top: 15px !important;
    }
</style>
</head>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;" onload="javascript:disableList();">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <script type="text/javascript">
        function chgActCmdSubmit(cmdValue, targetAction) {
            document.getElementById('cmd').value = cmdValue;
            var form = document.getElementById('userForm');
            form.action = targetAction;
            form.submit();
        }

        function disableList() {   
            document.getElementById("areaCode").disabled = true;
            document.getElementById("affCode").disabled = true;
            document.getElementById("secCode").disabled = true;
            document.getElementById("affCodeLst").disabled = true;
            document.getElementById("secCodeLst").disabled = true;
            document.getElementById("areaCodeLst").disabled = true;
            document.getElementById("areaDiv").style.display = 'none';
            document.getElementById("affDiv").style.display = 'none';
            document.getElementById("secDiv").style.display = 'none';
        }

        function enableList() {
            var roleVal = document.getElementById("role").value;
            if (roleVal == 'Area') {
                document.getElementById("areaCode").disabled = false;
                document.getElementById("affCode").value = -1;
                document.getElementById("affCode").disabled = true;
                document.getElementById("secCode").value = -1;               
                document.getElementById("secCode").disabled = true;
                document.getElementById("division").value = -1;
                document.getElementById("division").disabled = false;
                document.getElementById("affCodeLst").disabled = true;
                document.getElementById("secCodeLst").disabled = true;
                document.getElementById("areaCodeLst").disabled = false;
                removeAllOptions(document.getElementById("areaCodeLst"));
                document.getElementById("areaDiv").style.display = '';
                document.getElementById("affDiv").style.display = 'none';
                document.getElementById("secDiv").style.display = 'none';
                removeAllOptions(document.getElementById("areaCode"));
            }
            else if (roleVal == 'Affiliate') {
                document.getElementById("areaCode").value = -1;      
                document.getElementById("areaCode").disabled = true;
                document.getElementById("affCode").disabled = false;
                document.getElementById("secCode").value = -1;
                document.getElementById("secCode").disabled = true;
                document.getElementById("division").value = -1;
                document.getElementById("division").disabled = false;
                document.getElementById("secCodeLst").disabled = true;
                document.getElementById("affCodeLst").disabled = false;
                removeAllOptions(document.getElementById("affCodeLst"));
                document.getElementById("areaCodeLst").disabled = true;
                document.getElementById("areaDiv").style.display = 'none';
                document.getElementById("affDiv").style.display = '';
                document.getElementById("secDiv").style.display = 'none';
                removeAllOptions(document.getElementById("affCode"));
            }
            else if (roleVal == 'Sector') {
                document.getElementById("areaCode").value = -1;      
                document.getElementById("areaCode").disabled = true;
                document.getElementById("affCode").value = -1;          
                document.getElementById("affCode").disabled = true;
                document.getElementById("secCode").disabled = false;
                document.getElementById("division").value = -1;         
                document.getElementById("division").disabled = false;
                document.getElementById("affCodeLst").disabled = true;
                document.getElementById("secCodeLst").disabled = false;
                removeAllOptions(document.getElementById("secCodeLst"));
                document.getElementById("areaCodeLst").disabled = true;
                document.getElementById("areaDiv").style.display = 'none';
                document.getElementById("affDiv").style.display = 'none';
                document.getElementById("secDiv").style.display = '';           
                removeAllOptions(document.getElementById("secCode"));
            } else if (roleVal == 'D') {
                document.getElementById("areaCode").value = -1;
                document.getElementById("affCode").value = -1;
                document.getElementById("secCode").value = -1;
                document.getElementById("division").value = -1;
                document.getElementById("areaCode").disabled = true;
                document.getElementById("affCode").disabled = true;
                document.getElementById("secCode").disabled = true;
                document.getElementById("division").disabled = false;
                document.getElementById("affCodeLst").disabled = true;
                document.getElementById("secCodeLst").disabled = true;
                document.getElementById("areaCodeLst").disabled = true;
                document.getElementById("areaDiv").style.display = 'none';
                document.getElementById("affDiv").style.display = 'none';
                document.getElementById("secDiv").style.display = 'none';           
            } else {
                document.getElementById("areaCode").value = -1;
                document.getElementById("affCode").value = -1;
                document.getElementById("secCode").value = -1;
                document.getElementById("division").value = -1;
                document.getElementById("areaCode").disabled = true;
                document.getElementById("affCode").disabled = true;
                document.getElementById("secCode").disabled = true;
                document.getElementById("division").disabled = true;
                document.getElementById("affCodeLst").disabled = true;
                document.getElementById("secCodeLst").disabled = true;
                document.getElementById("areaCodeLst").disabled = true;
                document.getElementById("areaDiv").style.display = 'none';
                document.getElementById("affDiv").style.display = 'none';
                document.getElementById("secDiv").style.display = 'none';           
            }
        }   

        function add() {
            chgActCmdSubmit('view', 'ActiveDirSearch.action');
        }

        function saveForm() {
            var roleElement = document.getElementById("role");
            var divisionElement = document.getElementById("division");
            
            if (roleElement.value == '-1') {
                alert('Please select Role');
                return false;
            }
            if (roleElement.value == 'Area' || roleElement.value == 'Affiliate' || roleElement.value == 'Sector' || roleElement.value == 'D') {
                if (divisionElement.value == '-1') {
                    alert('Please select Division');
                    return false;
                }
            }
            if (roleElement.value == 'Area') {
                var areaListSel = document.getElementById("areaCodeLst");
                var areaCodeBox = document.getElementById("areaCode");
                if (areaListSel.options.length < 1) {
                    alert('Please select Area');
                    return false;
                }
                document.getElementById("selDesc").value = areaCodeBox.options[areaCodeBox.selectedIndex].text;
                var areaCodeList = "";
                for (var j = 0; j < areaListSel.options.length; j++) {
                    areaCodeList = areaCodeList + areaListSel.options[j].value + "|" + areaListSel.options[j].text + "*";
                }
                document.getElementById("areaCodeList").value = areaCodeList.substring(0, areaCodeList.lastIndexOf("*"));
            }
            if (roleElement.value == 'Affiliate') {
                var affListSel = document.getElementById("affCodeLst");
                var affCodeBox = document.getElementById("affCode");
                if (affListSel.options.length < 1) {
                    alert('Please select Affiliate');
                    return false;
                }
                document.getElementById("selDesc").value = affCodeBox.options[affCodeBox.selectedIndex].text;
                var affCodeList = "";
                for (var j = 0; j < affListSel.options.length; j++) {
                    affCodeList = affCodeList + affListSel.options[j].value + "|" + affListSel.options[j].text + "*";
                }
                document.getElementById("affCodeList").value = affCodeList.substring(0, affCodeList.lastIndexOf("*"));
            }
            if (roleElement.value == 'Sector') {
                var secListSel = document.getElementById("secCodeLst");
                var secCodeBox = document.getElementById("secCode");
                if (secListSel.options.length < 1) {
                    alert('Please select Sector');
                    return false;
                }      
                document.getElementById("selDesc").value = secCodeBox.options[secCodeBox.selectedIndex].text;
                var secCodeList = "";
                for (var j = 0; j < secListSel.options.length; j++) {
                    secCodeList = secCodeList + secListSel.options[j].value + "|" + secListSel.options[j].text + "*";
                }
                document.getElementById("secCodeList").value = secCodeList.substring(0, secCodeList.lastIndexOf("*"));
            }
            chgActCmdSubmit('save', 'rptUserMaint.action');
        }   

        function addList(list, selList) {
            var affList = document.getElementById(list);
            var affListSel = document.getElementById(selList);
            var statusFlag = false;
            for (var i = 0; i < affList.options.length; i++) {
                if (affList.options[i].selected) {
                    for (var j = 0; j < affListSel.options.length; j++) {
                        if (affList.options[i].value == affListSel.options[j].value) {
                            statusFlag = true;
                        }
                    }
                    if (!statusFlag) {
                        addOptions(document.getElementById(selList), affList.options[i].text, affList.options[i].value)
                    }
                    statusFlag = false; 
                }
            }
        }

        function removeAllOptions(selectbox) {
            for (var i = selectbox.options.length - 1; i >= 0; i--) {
                selectbox.remove(i);
            }
        }
         
        function removeList(selList) {
            var affList = document.getElementById(selList);
            for (var i = affList.options.length - 1; i >= 0; i--) {
                if (affList.options[i].selected) {
                    affList.remove(i);
                }
            }
        }
         
        function addOptions(selectbox, text, value) {
            var optn = document.createElement("OPTION");
            optn.text = trim(text);
            optn.value = trim(value);
            selectbox.options.add(optn);
        }

        document.addEventListener("DOMContentLoaded", function() {
            window.trim = function(stringToTrim) {
                return stringToTrim.replace(/^\s+|\s+$/g, "");
            };
        });

        function callValues() {
            var divVal = document.getElementById('division').value;
            var roleVal = document.getElementById('role').value;
            if (divVal != '-1' && roleVal == 'Area') {
                retrieveURL('./tcgmAjax.action?cascadingCmd=BurstArea&cascadingVal=' + divVal, 'userForm', 'areaCode');
            }
            else if (divVal != '-1' && roleVal == 'Affiliate') {
                retrieveURL('./tcgmAjax.action?cascadingCmd=BurstAff&cascadingVal=' + divVal, 'userForm', 'affCode');
            }
            else if (divVal != '-1' && roleVal == 'Sector') {
                retrieveURL('./tcgmAjax.action?cascadingCmd=BurstSector&cascadingVal=' + divVal, 'userForm', 'secCode');
            }
            else {
                removeAllOptions(document.getElementById("areaCode"));
                removeAllOptions(document.getElementById("affCode"));
                removeAllOptions(document.getElementById("secCode"));
            }
        }
    </script>

    <s:form method="post" name="userForm" id="userForm" action="rptUserMaint" theme="simple">
        <s:hidden name="cmd" id="cmd" />
        <s:hidden name="selDesc" id="selDesc" />
        <s:hidden name="affCodeList" id="affCodeList" />
        <s:hidden name="secCodeList" id="secCodeList" />
        <s:hidden name="areaCodeList" id="areaCodeList" />

        <table style="width: 700px; margin: 20px auto; border-collapse: collapse;" border="0" align="center">
            <tr>
                <td style="width: 120px; padding: 6px;" class="commandOptionLabel"><strong>User Id</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.userid" id="userid" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Last Name</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.lastName" id="lastName" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>First Name</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.firstName" id="firstName" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
                        <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Email</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.email" id="email" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>UPI</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.abtNotesId" id="abtNotesId" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr> 
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Division</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.empDivision" id="empDivision" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr> 
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Type</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.employeeType" id="employeeType" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Role</strong></td>
                <td style="padding: 6px;">
                    <s:select name="rptUser.role" id="role" cssClass="tcgm-select" onchange="enableList()"
                              list="#{'HQS':'HQ Supervisor', 'HQC':'HQ Consumer', 'DALL':'All Divisions', 'D':'Division', 'Area':'Area', 'Sector':'Sector', 'Affiliate':'Affiliate'}" 
                              headerKey="-1" headerValue="Select One" />
                </td> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Division</strong></td>
                <td style="padding: 6px;">
                    <s:select name="rptUser.division" id="division" cssClass="tcgm-select" onchange="callValues()"
                              list="divCollection" headerKey="-1" headerValue="Select One" />
                </td> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            
            <tbody id="areaDiv" style="display:none">
                <tr>
                    <td style="padding: 6px;" class="commandOptionLabel"><strong>Area</strong></td>
                    <td style="padding: 6px;">
                        <s:select name="rptUser.areaCode" id="areaCode" multiple="true" size="12" 
                                  style="width:240px; border:1px solid #b8d4f0; border-radius:3px;" list="#templateList" />
                    </td> 
                    <td style="text-align: center; vertical-align: middle; padding: 0 10px;">
                        <button type="button" class="tcgm-btn" style="margin-bottom: 10px; width: 110px;" onclick="addList('areaCode','areaCodeLst');">Insert &gt;&gt;</button>
                        <br>
                        <button type="button" class="tcgm-btn" style="width: 110px;" onclick="removeList('areaCodeLst');">&lt;&lt; Remove</button>
                    </td>
                    <td style="padding: 6px;">
                        <s:select name="rptUser.areaCodeList" id="areaCodeLst" multiple="true" size="12" 
                                  style="width:220px; border:1px solid #b8d4f0; border-radius:3px;" list="#templateList" />
                    </td> 
                </tr>
            </tbody>
            
            <tbody id="affDiv" style="display:none">
                <tr>
                    <td style="padding: 6px;" class="commandOptionLabel"><strong>Affiliate</strong></td>
                    <td style="padding: 6px;">
                        <s:select name="rptUser.affCode" id="affCode" multiple="true" size="12" 
                                  style="width:240px; border:1px solid #b8d4f0; border-radius:3px;" list="#templateList" />
                    </td> 
                    <td style="text-align: center; vertical-align: middle; padding: 0 10px;">
                        <button type="button" class="tcgm-btn" style="margin-bottom: 10px; width: 110px;" onclick="addList('affCode','affCodeLst');">Insert &gt;&gt;</button>
                        <br>
                        <button type="button" class="tcgm-btn" style="width: 110px;" onclick="removeList('affCodeLst');">&lt;&lt; Remove</button>
                    </td>
                    <td style="padding: 6px;">
                        <s:select name="rptUser.affCodeList" id="affCodeLst" multiple="true" size="12" 
                                  style="width:220px; border:1px solid #b8d4f0; border-radius:3px;" list="#templateList" />
                    </td> 
                </tr>
            </tbody>

            <tbody id="secDiv" style="display:none">   
                <tr>
                    <td style="padding: 6px;" class="commandOptionLabel"><strong>Sector</strong></td>
                    <td style="padding: 6px;">
                        <s:select name="rptUser.secCode" id="secCode" multiple="true" size="12" 
                                  style="width:240px; border:1px solid #b8d4f0; border-radius:3px;" list="#templateList" />
                    </td> 
                    <td style="text-align: center; vertical-align: middle; padding: 0 10px;">
                        <button type="button" class="tcgm-btn" style="margin-bottom: 10px; width: 110px;" onclick="addList('secCode','secCodeLst');">Insert &gt;&gt;</button>
                        <br>
                        <button type="button" class="tcgm-btn" style="width: 110px;" onclick="removeList('secCodeLst');">&lt;&lt; Remove</button>
                    </td>
                    <td style="padding: 6px;">
                        <s:select name="rptUser.secCodeList" id="secCodeLst" multiple="true" size="12" 
                                  style="width:220px; border:1px solid #b8d4f0; border-radius:3px;" list="#templateList" />
                    </td> 
                </tr>
            </tbody>
            
            <tr>
                <td colspan="4" style="height: 15px;">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4">
                    <table align="center" style="border-spacing: 10px 0;">
                        <tr>
                            <td>
                                <button type="button" id="addButton" class="tcgm-btn" onclick="add();">LookUp</button>
                            </td>
                            <td>
                                <button type="button" id="saveButton" class="tcgm-btn" onclick="saveForm();">Save</button>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <input type="hidden" name="cmd2" id="cmd2" value="creation">
    </s:form>

    <%@ include file="/include/footer.jsf" %>
</body>
</html>
            