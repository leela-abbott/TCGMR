<%! String pageTitle = "Report User Search & Selection Screen"; %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/include/header.jsf" %>

<head>
    <style>
        /* Modern CSS Buttons matching the corporate TCGM application spec */
        .tcgm-btn {
            display: inline-block;
            padding: 5px 16px;
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

        /* Form element styling reflecting modern layout guidelines */
        .tcgm-input-text {
            border: 1px solid #b8d4f0;
            padding: 4px;
            font-family: Arial, sans-serif;
            border-radius: 3px;
            box-sizing: border-box;
        }
        .tcgm-grid-input {
            border: 1px solid #dcdcdc;
            padding: 3px;
            font-family: Arial, sans-serif;
            font-size: 11px;
            border-radius: 2px;
            width: 100%;
            box-sizing: border-box;
        }
        .commandOptionLabel {
            font-family: Arial, sans-serif;
            font-size: 12px;
            font-weight: bold;
            color: #333333;
        }
    </style>
</head>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <!-- Struts 2 Migrated Form Architecture mapping directly to the new action intercept points -->
    <s:form method="post" name="activeDirSearchForm" id="activeDirSearchForm" action="ActiveDirSearch.action" theme="simple">
        
        <!-- Standardized OGNL Field Bindings -->
        <s:hidden name="cmd" id="cmd" />
        <s:hidden name="cmd2" id="cmd2" />

        <table style="width: 650px; margin: 20px auto; border-collapse: collapse;">
            <tr>
                <td style="width: 100px;">&nbsp;</td>
                <td style="height: 33px; width: 120px;" class="commandOptionLabel"><label for="usIdTextField">UserId</label></td>
                <td>
                    <s:textfield name="usId" id="usIdTextField" size="25" cssClass="tcgm-input-text" 
                                 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td style="height: 33px;" class="commandOptionLabel"><label for="lastNameTextField">Last Name</label></td>
                <td>
                    <s:textfield name="lastName" id="lastNameTextField" size="25" cssClass="tcgm-input-text" 
                                 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
                </td>
            </tr>    
            <tr>
                <td>&nbsp;</td>
                <td style="height: 36px;" class="commandOptionLabel"><label for="firstNameTextField">First Name</label></td>
                <td>
                    <s:textfield name="firstName" id="firstNameTextField" size="25" cssClass="tcgm-input-text" 
                                 onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
                </td>
            </tr>
            <tr>
                <td colspan="3" style="text-align: center; padding-top: 15px;">
                    <button type="button" name="searchButton" id="searchButton" class="tcgm-btn" 
                            onClick="javascript:chgActCmdSubmit(document.activeDirSearchForm,'Get','ActiveDirSearch.action');">Get Users</button>
                </td>
            </tr>
        </table>
        
        <br>

        <!-- Condition evaluation layer using clean JSTL expressions pulled from modern Action variables -->
        <c:if test="${action.activeDirUserListSize != 0 && not empty action.activeDirUserList}">
            
            <%-- Contextual Button Render Condition checking list capacity variables safely via EL --%>
            <c:if test="${action.activeDirUserListSize > 10}">
                <table align="center" style="margin-bottom: 10px;">
                    <tr>
                        <td>
                            <button type="button" class="tcgm-btn" onClick="javascript:checkSelect();">Select User</button>
                        </td>
                    </tr>
                </table>
            </c:if>

            <!-- Modernized Results Grid satisfying HTML5 criteria -->
            <table style="width: 760px; margin: 10px auto; border-collapse: collapse; border-spacing: 0;">
                <tr class="fltrTblHdng" style="background-color: #b4d8f4; font-weight: bold; color: Navy; text-align: left;">
                    <th style="width: 40px; padding: 6px; text-align: center;">&nbsp;</th>
                    <th style="width: 80px; padding: 6px;">User ID</th>
                    <th style="width: 100px; padding: 6px;">First Name</th>
                    <th style="width: 100px; padding: 6px;">Last Name</th>
                    <th style="width: 80px; padding: 6px;">UPI</th>
                    <th style="width: 80px; padding: 6px;">Division</th>
                    <th style="width: 80px; padding: 6px;">Type</th>
                    <th style="width: 140px; padding: 6px;">Email</th>
                </tr>
                
                <!-- Native Thread-Safe Iterator handling structural data index populations safely -->
                <c:forEach items="${action.activeDirUserList}" var="activeBean" varStatus="status">  
                    <tr id="mntRow" class="${status.index % 2 == 0 ? 'evenRow' : 'oddRow'}">
                        <td style="padding: 6px; text-align: center; vertical-align: middle;">
                            <input type="radio" name="blnSelected" value="<c:out value='${activeBean.userId}'/>" 
                                   onclick="document.getElementById('selectUserButtonBottom').focus();"/>
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].userId" value="%{#attr.activeBean.userId}" maxlength="30" cssClass="tcgm-grid-input" />
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].firstName" value="%{#attr.activeBean.firstName}" maxlength="30" cssClass="tcgm-grid-input" />
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].lastName" value="%{#attr.activeBean.lastName}" maxlength="30" cssClass="tcgm-grid-input" />
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].abtNotesId" value="%{#attr.activeBean.abtNotesId}" maxlength="50" cssClass="tcgm-grid-input" />
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].division" value="%{#attr.activeBean.division}" maxlength="50" cssClass="tcgm-grid-input" />
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].employeeType" value="%{#attr.activeBean.employeeType}" maxlength="50" cssClass="tcgm-grid-input" />
                        </td>
                        <td style="padding: 4px;">
                            <s:textfield name="activeDirUserListItem[%{#status.index}].email" value="%{#attr.activeBean.email}" maxlength="50" cssClass="tcgm-grid-input" />
                        </td>
                    </tr>
                </c:forEach>
            </table>     
           
            <table align="center" style="margin-top: 10px;">
                <tr>
                    <td>
                        <button type="button" id="selectUserButtonBottom" class="tcgm-btn" onClick="javascript:checkSelect();">Select User</button>
                    </td>
                </tr>
            </table>
        </c:if> 
    </s:form>
    <script type="text/javascript">
        // Core framework initialization hook
        setFocus('usIdTextField');

        /**
         * Validates radio collection selections and dispatches payload values
         */
        function checkSelect(){
            var radioObj = document.activeDirSearchForm.blnSelected;
            var radioLength;
            var flag = false;
            var objvalue;
            
            if (radioObj != null) {
                radioLength = radioObj.length;   
                
                // Handles edge-case where the grid returns exactly one row item
                if (radioLength == undefined) {
                    if (radioObj.checked) {
                        chgActCmdSubmit(document.activeDirSearchForm, 'select', 'ActiveDirSearch.action');         
                    } else {
                        alert('Please Select a User');
                    }
                } else {        
                    // Iterates across multiple collection entities to capture selected value
                    for (var i = 0; i < radioLength; i++) {
                        if (radioObj[i].checked) {
                            flag = true;
                            objvalue = radioObj[i].value;
                            break;
                        }
                    }
                    if (flag) {
                        chgActCmdSubmit(document.activeDirSearchForm, 'select', 'ActiveDirSearch.action');
                    } else {
                        alert('Please Select a User');
                    }
                }   
            }
        }

        /**
         * Cancels transaction operations and redirects back to base user view split
         */
        function callCancel(){
            chgActCmdSubmit(document.activeDirSearchForm, 'maint_create', 'rptUserMaint.action');
        }
    </script>

    <%@ include file="/include/footer.jsf" %>
</body>
</html>
    