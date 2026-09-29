<%! String pageTitle = "Report User Creation"; %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/include/header.jsf" %>

<head>
    <style>
        .tcgm-btn {
            display: inline-block;
            padding: 5px 18px;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 12px;
            font-weight: bold;
            color: #123456 !important;
            text-decoration: none;
            background: linear-gradient(to bottom, #ffe880 0%, #ffcd3c 100%);
            border: 1px solid #cca11f;
            border-radius: 4px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.15);
            cursor: pointer;
            text-shadow: 0 1px 0 rgba(255,255,255,0.4);
            transition: all 0.1s ease-in-out;
        }
        .tcgm-btn:hover {
            background: linear-gradient(to bottom, #ffed96 0%, #ffd455 100%);
            border-color: #b88f14;
        }
        .tcgm-btn:active {
            background: #ffcd3c;
            box-shadow: inset 0 1px 2px rgba(0,0,0,0.2);
        }
        .tcgm-input-readonly {
            border: 1px solid #b8d4f0;
            background-color: #e6e6e6;
            color: #555555;
            padding: 4px;
            width: 240px;
            font-family: Arial, sans-serif;
            border-radius: 3px;
        }
        .tcgm-select {
            border: 1px solid #b8d4f0;
            padding: 4px;
            width: 246px;
            font-family: Arial, sans-serif;
            border-radius: 3px;
        }
        .commandOptionLabel {
            font-family: Arial, sans-serif;
            font-size: 12px;
            font-weight: bold;
            color: #333333;
        }
    </style>
</head>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;" onload="javascript:disableList();">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <script type="text/javascript">
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
            chgActCmdSubmit(document.userForm, 'view', 'ActiveDirSearch.action');
        }

        function saveForm(form, cmd, action) {
            if (document.getElementById("role").value == '-1') {
                alert('Please select Role');
                return false;
            }
            if (document.getElementById("role").value == 'Area' || document.getElementById("role").value == 'Affiliate'
                || document.getElementById("role").value == 'Sector' || document.getElementById("role").value == 'D') {
                if (document.getElementById("division").value == '-1') {
                    alert('Please select Division');
                    return false;
                }
            }
            if (document.getElementById("role").value == 'Area') {
                if (document.getElementById("areaCodeLst").options.length < 1) {
                    alert('Please select Area');
                    return false;
                }
                form.selDesc.value = form.areaCode[form.areaCode.selectedIndex].text;
                var areaCodeList = "";
                var areaListSel = document.getElementById("areaCodeLst");
                for (var j = 0; j < areaListSel.options.length; j++) {
                    areaCodeList = areaCodeList + areaListSel.options[j].value + "|" + areaListSel.options[j].text + "*";
                }
                form.areaCodeList.value = areaCodeList.substring(0, areaCodeList.lastIndexOf("*"));
            }
                        if (document.getElementById("role").value == 'Affiliate') {
                if (document.getElementById("affCodeLst").options.length < 1) {
                    alert('Please select Affiliate');
                    return false;
                }
                form.selDesc.value = form.affCode[form.affCode.selectedIndex].text;
                var affCodeList = "";
                var affListSel = document.getElementById("affCodeLst");
                for (var j = 0; j < affListSel.options.length; j++) {
                    affCodeList = affCodeList + affListSel.options[j].value + "|" + affListSel.options[j].text + "*";
                }
                form.affCodeList.value = affCodeList.substring(0, affCodeList.lastIndexOf("*"));
            }
            if (document.getElementById("role").value == 'Sector') {
                if (document.getElementById("secCodeLst").options.length < 1) {
                    alert('Please select Sector');
                    return false;
                }      
                form.selDesc.value = form.secCode[form.secCode.selectedIndex].text;
                var secCodeList = "";
                var secListSel = document.getElementById("secCodeLst");
                for (var j = 0; j < secListSel.options.length; j++) {
                    secCodeList = secCodeList + secListSel.options[j].value + "|" + secListSel.options[j].text + "*";
                }
                form.secCodeList.value = secCodeList.substring(0, secCodeList.lastIndexOf("*"));
            }
            chgActCmdSubmit(form, cmd, action);
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

        function trim(stringToTrim) {
            return stringToTrim.replace(/^\s+|\s+$/g, "");
        }

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

    <s:form method="post" name="userForm" id="userForm" action="rptUserMaint.action" theme="simple">
        
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
                    <s:textfield name="rptUser.email" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>UPI</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.abtNotesId" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr> 
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Division</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.empDivision" readonly="true" cssClass="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr> 
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Type</strong></td>
                <td style="padding: 6px;">
                    <s:textfield name="rptUser.employeeType" readonly="true" cssClass="tcgm-input-readonly" />
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
                                <button type="button" id="saveButton" class="tcgm-btn" onclick="saveForm(document.userForm,'save','rptUserMaint.action');">Save</button>
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
            