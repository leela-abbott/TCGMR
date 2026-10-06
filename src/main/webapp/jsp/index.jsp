<%@ page errorPage="/jsp/exception.jsp" %>
<%@ page import="abbott.ai.tcgm.entities.Role" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="/WEB-INF/taglib/abbott.tld" prefix="abbott" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>TCGM System - Main Menu</title>
    <script type="text/javascript" src="include/common.js"></script>
    <script type="text/javascript">
        function checkUrl(src, dest){
            var xSrc = src;
            if(xSrc.indexOf('.action') != -1){
                if(dest.indexOf('.action') != -1){
                    xSrc = xSrc.substring(0, xSrc.lastIndexOf('/') + 1) + dest;                    
                } else {
                    xSrc = xSrc.substring(0, xSrc.lastIndexOf('/') + 1) + 'jsp/' + dest;
                }
            } else if(xSrc.indexOf('.jsp') != -1){    
                if(dest.indexOf('.action') != -1){
                    xSrc = xSrc.substring(0, xSrc.lastIndexOf('/') - 3) + dest;                    
                } else {            
                    xSrc = xSrc.substring(0, xSrc.lastIndexOf('/') + 1) + dest;
                }
            }
            return xSrc;
        }

        function callReport(action){
            var caller = checkUrl(window.location.href, action);
            window.open(caller, '', 'location=no,toolbar=yes,resizable,left=100,top=15,height=550,width=750,scrollbars=yes,status=yes');
        }

        function callFile(action, filename, dir){
            var caller = checkUrl(window.location.href, action);
            window.open(caller + '?dir=' + dir + '&fileName=' + filename, '', 'location=no,toolbar=yes,resizable,left=100,top=15,height=550,width=750,scrollbars=yes');
        }    
    </script>
</head>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">

    <!-- Secure Frontend Session Guard -->
    <s:if test="#session.TCGMUser == null">
        <script type="text/javascript">
            window.location.href = "${pageContext.request.contextPath}/openLogin.action";
        </script>
    </s:if>

    <s:form action="login.action" method="post" id="mainForm" theme="simple">

        <!-- 100% Full-Width Base Grid Table Layout Matrix -->
        <table style="text-align: center; width: 65%; font-size: large; font-weight: bold; color: Navy;" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <table style="width: 100%; text-align: center; font-size: large; font-weight: bold; color: Navy;">
                        <tr>
                            <td align="center">Trading Company Gross Margin</td>
                        </tr>
                        <tr>
                            <td align="center">Main Menu</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <marquee scrollamount="3" onmouseover="this.scrollAmount=0" onmouseout="this.scrollAmount=3" direction="left" width="800">
                                    <font face="Arial" size="3" color="Red">
                                        <b><s:property value="bulletinMessage" /></b>
                                    </font>
                                </marquee>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <%@ include file="/include/errorDisplay.jsf" %>
                </td>
            </tr>
            <tr>
                <td>
                    <table style="width: 100%;">
                        
                        <!-- System Broadcast Alerts Iterator Loop -->
                        <s:iterator value="messageList">
                            <tr>
                                <td align="left">
                                    <font face="Arial" size="2" color="Blue"><b><s:property /></b></font>
                                </td>
                            </tr>
                        </s:iterator>

                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="left" style="font-size: small; font-weight: bold; color: Navy;">
                                TCGM Links
                            </td>
                        </tr>
                        <tr>
                            <td align="center"><hr /></td>
                        </tr>

                        <tr>
                            <td align="left">
                                <table style="font-family: Arial, sans-serif; font-size: small; width: 100%;">
                                    <tr>
                                        <td align="left" style="font-size: small; font-weight: bold; color: Navy;">

                                            <!-- 1. ADMINISTRATOR ROLE CHECK --> 
                                            <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_ADMIN" || sessionScope.TCGMUser.role.accessLevel == 3}'>
                                                <a href="userMaint.action" style="color: #0000A0; text-decoration: underline;">Link to TCGM</a>
                                                <c:if test='${!sessionScope.TCGMUser.rptAccess}'>
                                                    </td>
                                                    <td align="right" style="font-size: small; font-weight: bold; color: Navy;">
                                                        <a href="#" onclick="callReport('reportView.jsp'); return false;" style="color: #0000A0; text-decoration: underline;">Link to Reports</a>
                                                </c:if> 
                                            </c:if> 

                                            <!-- 2. OPERATOR ROLE CHECK --> 
                                            <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_OPERATOR" || sessionScope.TCGMUser.role.accessLevel == 1}'>
                                                <a href="mngFactorModels.action" style="color: #0000A0; text-decoration: underline;">Link to TCGM</a>
                                            </c:if> 
                                            <!-- 3. ANALYST ROLE CHECK --> 
                                            <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_ANALYST" || sessionScope.TCGMUser.role.accessLevel == 2}'>
                                                <a href="mngFactorModels.action" style="color: #0000A0; text-decoration: underline;">Link to TCGM</a>
                                            </c:if> 

                                            <!-- 4. RPTADMIN (REPORT ADMINISTRATOR) ROLE CHECK --> 
                                            <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_RPTADMIN" || sessionScope.TCGMUser.role.accessLevel == 4}'>
                                                <a href="ActiveDirSearch.action?cmd=view" style="color: #0000A0; text-decoration: underline;">Link to TCGM</a>
                                                <c:if test='${!sessionScope.TCGMUser.rptAccess}'>
                                                    </td>
                                                    <td align="right" style="font-size: small; font-weight: bold; color: Navy;">
                                                        <a href="#" onclick="callReport('reportView.jsp'); return false;" style="color: #0000A0; text-decoration: underline;">Link to Reports</a>
                                                </c:if> 
                                            </c:if> 

                                            <!-- 5. QUERY ROLE CHECK --> 
                                            <c:if test='${sessionScope.TCGMUser.role.name == "TCGM_QUERY" || sessionScope.TCGMUser.role.accessLevel == 5}'>
                                                <a href="mngFactorModels.action" style="color: #0000A0; text-decoration: underline;">Link to TCGM</a>
                                            </c:if>

                                        </td>
                                        <td align="right" style="font-size: small; font-weight: bold; color: Navy;">
                                            <!-- GLOBAL REPORTS BACKUP VISIBILITY CHECK --> 
                                            <c:if test='${sessionScope.TCGMUser.rptAccess}'>
                                                <a href="#" onclick="callReport('reportView.jsp'); return false;" style="color: #0000A0; text-decoration: underline;">Link to Reports</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>

                        <tr>
                            <td align="center"><hr /></td>
                        </tr>

                        <tr style="height: 15px"></tr>
                        <tr>
                            <td align="left" style="font-size: small; font-weight: bold; color: Navy;">
                                File Folders
                            </td>
                        </tr>
                        <tr>
                            <td align="center"><hr /></td>
                        </tr>
                        <s:if test="cmd == null || cmd == '' || cmd.equalsIgnoreCase('dir')">
                            <s:iterator value="dirList">
                                <tr>
                                    <td align="left">
                                        <b>
                                            <a href="main.action?cmd=file&amp;dirName=<s:property />" style="color: Blue; text-decoration: none">
                                                <img src="images/folder.gif" border="0" alt="Folder" style="vertical-align: middle;" />
                                                <!-- Increased font size to a readable 13px -->
                                                <span style="font-size: 13px; font-weight: bold;"> <s:property /></span>
                                            </a>
                                        </b>
                                    </td>
                                </tr>
                            </s:iterator>
                            <tr>
                                <td align="center"><hr /></td>
                            </tr>
                        </s:if>
                        <s:else>
                            <s:iterator value="fileList">
                                <tr>
                                    <td align="left">
                                        <b>
                                            <a href="#" onclick="callFile('fileView.jsp', '<s:property />', '<s:property value="dirName" />'); return false;" style="color: #0000A0; text-decoration: underline;">
                                                <s:property />
                                            </a>
                                        </b>
                                    </td>
                                </tr>
                            </s:iterator>
                            <tr>
                                <td align="center"><hr /></td>
                            </tr>
                            <tr>
                                <td><br /></td>
                            </tr>
                            <tr>
                                <td align="left">
                                    <b>
                                        <!-- Increased font size to a readable 13px -->
                                        <a href="main.action?cmd=dir" style="color: #0000A0; text-decoration: underline; font-size: 13px;">Back to Dir</a>
                                    </b>
                                </td>
                            </tr>
                        </s:else>

                        <tr style="height: 15px"></tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td align="right" style="font-size: small; font-weight: bold; color: Navy;">
                                <a href="logout.action" style="color: Navy; text-decoration: underline;">Logout</a>
                            </td>
                        </tr>
                        <tr style="height: 15px"></tr>
                    </table>
                </td>
            </tr>
        </table>
    </s:form>
</body>
</html>
            