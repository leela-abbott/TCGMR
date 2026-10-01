<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="abbott.ai.tcgm.entities.RptUser" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "App User Creation"; %>
<%@ include file="/include/header.jsf" %>

<%
    RptUser sessionUser = (RptUser) session.getAttribute("RptUser");
    String displayUserId = "";
    String displayLastName = "";
    String displayFirstName = "";
    String displayEmail = "";
    String displayUpi = "";
    String displayDivision = "";
    String displayType = "";

    if (sessionUser != null) {
        displayUserId = (sessionUser.getUserid() != null) ? sessionUser.getUserid() : "";
        displayLastName = (sessionUser.getLastName() != null) ? sessionUser.getLastName() : "";
        displayFirstName = (sessionUser.getFirstName() != null) ? sessionUser.getFirstName() : "";
        displayEmail = (sessionUser.getEmail() != null) ? sessionUser.getEmail() : "";
        displayUpi = (sessionUser.getAbtNotesId() != null) ? sessionUser.getAbtNotesId() : "";
        displayDivision = (sessionUser.getDivision() != null) ? sessionUser.getDivision() : ((sessionUser.getEmpDivision() != null) ? sessionUser.getEmpDivision() : "");
        displayType = (sessionUser.getEmployeeType() != null) ? sessionUser.getEmployeeType() : "";
    }
%>

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

        // Validates input fields and triggers standard save endpoint
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
        <input type="hidden" name="cmd" id="cmd" value="" />
        <input type="hidden" name="cmd2" id="cmd2" value="creation" />
        <input type="hidden" name="selDesc" id="selDesc" value="" />
        <input type="hidden" name="affCodeList" id="affCodeList" value="" />
        <input type="hidden" name="secCodeList" id="secCodeList" value="" />
        <input type="hidden" name="areaCodeList" id="areaCodeList" value="" />

        <table style="width: 700px; margin: 20px auto; border-collapse: collapse;" border="0">
            <tr>
                <td style="width: 120px; padding: 6px;" class="commandOptionLabel"><strong>User Id</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.userid" id="userid" value="<%=displayUserId%>" readonly="readonly" class="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Last Name</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.lastName" id="lastName" value="<%=displayLastName%>" readonly="readonly" class="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>First Name</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.firstName" id="firstName" value="<%=displayFirstName%>" readonly="readonly" class="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Email</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.email" id="email" value="<%=displayEmail%>" readonly="readonly" class="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>UPI</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.abtNotesId" id="abtNotesId" value="<%=displayUpi%>" readonly="readonly" class="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr> 
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Division</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.division" id="division" value="<%=displayDivision%>" readonly="readonly" class="tcgm-input-readonly" />
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr> 
            <tr>
                <td style="padding: 6px;" class="commandOptionLabel"><strong>Type</strong></td>
                <td style="padding: 6px;">
                    <input type="text" name="rptUser.employeeType" id="employeeType" value="<%=displayType%>" readonly="readonly" class="tcgm-input-readonly" />
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
                    <table style="border-spacing: 10px 0; margin-left: auto; margin-right: auto;">
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