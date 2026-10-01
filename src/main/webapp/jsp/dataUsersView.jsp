<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Struts 2 & JSTL Page Title Initialization Context --%>
<%! String pageTitle = "DatabaseUsers View"; %>
<c:set var="pageTitle" value="DatabaseUsers View" scope="request" />

<head>
<style type="text/css">
    body {
        font-family: Arial, sans-serif;
        background-color: #ffffff;
        color: #333333;
        margin: 0;
        padding: 0;
    }
    .process-container {
        width: 100%;
        max-width: 700px;
        margin: 60px auto;
        padding: 0 20px;
        text-align: center;
    }
    .info-msg-blue {
        font-size: 15px;
        color: #0044aa;
        margin-bottom: 12px;
        display: block;
    }
    .warning-msg-red {
        font-size: 15px;
        color: #cc0000;
        font-weight: bold;
        margin-bottom: 30px;
        display: block;
    }
    .btn-action-trigger {
        background: linear-gradient(to bottom, #ffcc44 0%, #ffbb22 100%);
        border: 1px solid #e5a515;
        border-radius: 6px;
        color: #222222;
        font-size: 14px;
        font-weight: bold;
        padding: 12px 36px;
        text-decoration: none;
        display: inline-block;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        transition: background 0.2s ease;
    }
    .btn-action-trigger:hover {
        background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
    }
    .back-to-top-link {
        display: block;
        text-align: center;
        margin-top: 80px;
        font-size: 13px;
        color: #0066cc;
        text-decoration: none;
    }
    .back-to-top-link:hover {
        text-decoration: underline;
    }
    #msgPopupDiv {
        position: absolute;
        visibility: hidden;
        background-color: #ffffcc;
        border: 1px solid #ddcc77;
        padding: 8px;
        border-radius: 4px;
    }
</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/header.jsf" %>
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<div class="process-container">
    <!-- Notice / Process Notification Elements -->
    <span class="info-msg-blue">This would trigger the process of getting Database Users.</span>
    <span class="warning-msg-red">It might take several hours to complete.</span>
    
    <!-- Corrected Struts 2 URL Action Mapping Layer using the "var" compilation parameter -->
    <s:url var="dbUsersUrl" action="rptUserMaint">
        <s:param name="cmd">datausers</s:param>
    </s:url>
    
    <!-- Evaluates action reference context explicitly utilizing the "#" operator symbol -->
    <s:a href="%{#dbUsersUrl}" cssClass="btn-action-trigger">
        Get DB Users
    </s:a>
    
    <!-- Retained dynamic message anchor targets -->
    <div id="msgPopupDiv"></div>

</div>

<%@ include file="/include/footer.jsf" %>
</body>
</html>
