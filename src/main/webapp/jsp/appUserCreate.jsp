<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "App User Creation"; %>
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
            background-color: #f4f7fa;
            color: #555555;
            padding: 4px;
            width: 200px;
            font-family: Arial, sans-serif;
            border-radius: 3px;
        }
        .tcgm-select {
            border: 1px solid #b8d4f0;
            padding: 4px;
            width: 208px;
            font-family: Arial, sans-serif;
            border-radius: 3px;
        }
        .commandOptionLabel {
            font-family: Arial, sans-serif;
            font-size: 12px;
            color: #333333;
        }
    </style>
</head>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <script type="text/javascript">
        function chgActCmdSubmit(cmdValue, targetAction) {
            document.getElementById('cmd').value = cmdValue;
            var form = document.getElementById('userForm');
            form.action = targetAction;
            form.submit();
        }

        function lookupUser() {
            chgActCmdSubmit('appview', 'ActiveDirSearch.action');
        }

        function saveForm() {
            var roleSelect = document.getElementById('role');
            if (roleSelect.value === "-1") {
                alert("Please select a valid Role mapping before saving.");
                roleSelect.focus();
                return false;
            }
            chgActCmdSubmit('saveAppUser', 'rptUserMaint.action');
        }   
    </script>

    <s:form method="post" name="userForm" id="userForm" action="rptUserMaint" theme="simple">
        <s:hidden name="cmd" id="cmd" />
        <s:hidden name="cmd2" id="cmd2" value="creation" />
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
                    <s:textfield name="rptUser.division" id="division" readonly="true" cssClass="tcgm-input-readonly" />
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
                    <s:select name="rptUser.role" id="role" cssClass="tcgm-select"
                              list="#{'ADMINISTRATOR':'ADMINISTRATOR', 'ANALYST':'ANALYST', 'OPERATOR':'OPERATOR', 'RPT ADMIN':'RPT ADMIN', 'QUERY':'QUERY'}" 
                              headerKey="-1" headerValue="Select One" />
                </td> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4" style="height: 15px;">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4">
                    <table align="center" style="border-spacing: 10px 0;">
                        <tr>
                            <td>
                                <button type="button" id="addButton" class="tcgm-btn" onClick="javascript:lookupUser();">LookUp</button>
                            </td>
                            <td>
                                <button type="button" id="saveButton" class="tcgm-btn" onClick="javascript:saveForm();">Save</button>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </s:form>

    <%@ include file="/include/footer.jsf" %>
</body>
</html>
