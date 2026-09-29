<%! String pageTitle = "User Maintenance"; %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/include/header.jsf" %>

<head>
    <style>
        /* Pure CSS Modern Button Component Matching Your Design Spec */
        .tcgm-btn {
            display: inline-block;
            padding: 5px 14px;
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

        /* Pure CSS Custom Checkmark Icon Component */
        .tcgm-check-btn {
            background: linear-gradient(to bottom, #fff085 0%, #ffcc24 100%);
            border: 1px solid #caa016;
            border-radius: 3px;
            width: 24px;
            height: 18px;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0;
            box-shadow: 0 1px 1px rgba(0,0,0,0.1);
        }
        .tcgm-check-btn::after {
            content: '';
            display: block;
            width: 4px;
            height: 9px;
            border: solid #113355;
            border-width: 0 2.5px 2.5px 0;
            transform: rotate(45deg);
            margin-bottom: 2px;
        }
    </style>
</head>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <s:form method="post" name="userForm" id="userForm" action="userMaint.action" theme="simple">
        
        <!-- Safe OGNL Nested Dot Notation handling properties bindings -->
        <s:hidden name="searchObject.userid" id="searchObject_userid" />
        <s:hidden name="cmd" id="cmd" />
        <s:hidden name="userListSize" id="userListSize" />

        <!-- Modernized Layout Matrix Table Grid System matching HTML5 metrics -->
        <table style="width: 760px; border-collapse: collapse; border-spacing: 0; margin-top: 10px;">
            <tr>
                <td colspan="8" style="text-align: right; padding-bottom: 10px;">
                    <!-- Secure Role Evaluation Layer using standard Jakarta Session checks -->
                    <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_ADMIN" || sessionScope.TCGMUser.role.accessLevel == 3}'>
                        <button type="button" class="tcgm-btn" onclick="chgActCmdSubmit(document.userForm, 'deleteselected', 'deleteUser.action');">
                            Delete Selected
                        </button>
                    </c:if>
                </td>
            </tr>
            
            <tr class="fltrTblHdng" style="background-color: #b4d8f4; font-weight: bold; color: Navy; text-align: left;">
                <td style="padding: 6px;">User Id</td>
                <td style="padding: 6px;">First Name</td>
                <td style="padding: 6px;">Last Name</td>
                <td style="padding: 6px;">Phone</td>
                <td style="padding: 6px;">UPI</td>
                <td style="padding: 6px;">&nbsp;Role</td>
                <td style="padding: 6px;">&nbsp;Email</td>
                <td style="padding: 6px; text-align: center; vertical-align: middle;">
                    <!-- CSS Native Checkmark Action Trigger replacing the legacy image element -->
                    <button type="button" class="tcgm-check-btn" title="Toggle Select All"
                            onClick="return toggleSelectAll('userlist', 'selected', '${action.userListSize}');"></button>
                </td>
            </tr>

            <!-- Evaluates list collections layout state checks using safe JSTL empty validation -->
            <c:if test="${not empty action.userlist}">
                
                <!-- Native Thread-Safe Iterator handling sequential index binding loops -->
                <c:forEach items="${action.userlist}" var="userItem" varStatus="status">
                    <tr id="mntRow" class="${status.index % 2 == 0 ? 'evenRow' : 'oddRow'}">
                        
                        <td class="mntLeft" style="padding: 6px;">
                            <input type="hidden" name="userlist[${status.index}].userinfoid" value="<c:out value='${userItem.userinfoid}' />" />
                            <c:out value="${userItem.userid}" />
                        </td>        
                        <td class="mntLeft" style="padding: 6px;"><c:out value="${userItem.firstName}" /></td>
                        <td class="mntLeft" style="padding: 6px;"><c:out value="${userItem.lastName}" /></td>
                        <td class="mntLeft" style="padding: 6px;"><c:out value="${userItem.phone}" /></td>
                        <td class="mntLeft" style="padding: 6px;"><c:out value="${userItem.abtNotesId}" /></td>
                        <td class="mntCenter" style="padding: 6px;">&nbsp;&nbsp;<c:out value="${userItem.userRole}" /></td>
                        <td class="mntLeft" style="padding: 6px;">&nbsp;&nbsp;<c:out value="${userItem.email}" /></td>
                        
                        <td class="mntCenter" style="padding: 6px; text-align: center; vertical-align: middle;">
                            <input type="checkbox" name="userlist[${status.index}].selected" value="true" />
                        </td>
                    </tr>
                </c:forEach>
                
            </c:if>
        </table>

        <!-- Render Fallback Elements cleanly if structural record data yields zero elements -->
        <c:if test="${empty action.userlist}">
            <%@ include file="/include/recordsNotFound.jsf" %>
        </c:if>

    </s:form>
    <%@ include file="/include/footer.jsf" %>
</body>
</html>
