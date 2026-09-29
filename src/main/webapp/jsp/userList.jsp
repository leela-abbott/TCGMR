<%! String pageTitle = "User Maintenance"; %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/include/header.jsf" %>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>

    <!-- Converted Form Mapping to target modern .action extensions -->
    <s:form method="post" name="userForm" id="userForm" action="userMaint.action" theme="simple">
        
        <!-- Safe OGNL Nested Dot Notation handling properties bindings -->
        <s:hidden name="searchObject.userid" id="searchObject_userid" />
        <s:hidden name="cmd" id="cmd" />
        <s:hidden name="userListSize" id="userListSize" />

        <!-- Modernized Layout Matrix Table Grid System matching standard metrics -->
        <table style="width: 760px; border-collapse: collapse; margin-top: 10px;" cellspacing="0">
            <tr>
                <td colspan="8" class="right" style="text-align: right; padding-bottom: 10px;">
                    <!-- Secure Role Evaluation Layer using standard Jakarta Session checks -->
                    <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_ADMIN" || sessionScope.TCGMUser.role.accessLevel == 3}'>
                        <a href="javascript:chgActCmdSubmit(document.userForm, 'deleteselected', 'deleteUser.action');">
                            <img src="images/btnDeleteSelected.png" alt="Delete Selected" style="border: 0;" />
                        </a>
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
                <td style="padding: 6px; text-align: center;">
                    <!-- Safe collection calculation pointer trigger -->
                    <input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
                           onClick="return toggleSelectAll('userlist', 'selected', '<s:property value="userListSize" />');" />
                </td>
            </tr>

            <!-- Evaluates list collections layout state checks -->
            <s:if test="userListSize != 0 && userlist != null && !userlist.isEmpty()">
                
                <!-- Native Thread-Safe Iterator handling sequential index binding loops -->
                <s:iterator value="userlist" status="status">
                    <tr id="mntRow" class="<s:property value="#status.even ? 'evenRow' : 'oddRow'" />">
                        
                        <!-- Map list index structures natively using Struts 7 tracking tokens -->
                        <s:hidden name="userlist[%{#status.index}].userinfoid" value="%{userinfoid}" />
                        
                        <td class="mntLeft" style="padding: 6px;"><s:property value="userid" /></td>        
                        <td class="mntLeft" style="padding: 6px;"><s:property value="firstName" /></td>
                        <td class="mntLeft" style="padding: 6px;"><s:property value="lastName" /></td>
                        <td class="mntLeft" style="padding: 6px;"><s:property value="phone" /></td>
                        <td class="mntLeft" style="padding: 6px;"><s:property value="abtNotesId" /></td>
                        <td class="mntCenter" style="padding: 6px;">&nbsp;&nbsp;<s:property value="userRole" /></td>
                        <td class="mntLeft" style="padding: 6px;">&nbsp;&nbsp;<s:property value="email" /></td>
                        
                        <td class="mntCenter" style="padding: 6px; text-align: center;">
                            <!-- Bind native selected collection parameters directly into lists index slots -->
                            <s:checkbox name="userlist[%{#status.index}].selected" fieldValue="true" theme="simple" />
                        </td>
                    </tr>
                </s:iterator>
                
            </s:if>
        </table>

        <!-- Render Fallback Elements cleanly if structural record data yields zero elements -->
        <s:if test="userListSize == 0 || userlist == null || userlist.isEmpty()">
            <%@ include file="/include/recordsNotFound.jsf" %>
        </s:if>

    </s:form>
    <%@ include file="/include/footer.jsf" %>
</body>
</html>
