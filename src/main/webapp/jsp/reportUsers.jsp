<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Struts 2 & JSTL Page Title Initialization Context --%>
<%! String pageTitle = "DataBase Report Users"; %>
<c:set var="pageTitle" value="DataBase Report Users" scope="request" />

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
        font-weight: bold;
        margin-bottom: 30px;
        display: block;
        line-height: 1.5;
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
    <span class="info-msg-blue">The file is getting processed. We will email the file once it is complete.</span>
    
    <!-- Retained dynamic message anchor targets -->
    <div id="msgPopupDiv"></div>

    <a href="#top" class="back-to-top-link">Return to Top</a>
</div>

<%@ include file="/include/footer.jsf" %>
</body>
</html>