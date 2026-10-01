<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Struts 2 & JSTL Page Title Initialization Context --%>
<%! String pageTitle = "File Upload"; %>
<c:set var="pageTitle" value="File Upload" scope="request" />

<head>
<style type="text/css">
    body {
        font-family: Arial, sans-serif;
        background-color: #ffffff;
        color: #333333;
        margin: 0;
        padding: 0;
    }
    .search-container {
        width: 100%;
        max-width: 800px;
        margin: 40px auto;
        padding: 0 20px;
    }
    .form-section-fieldset {
        border: 1px solid #dddddd;
        border-radius: 6px;
        padding: 25px;
        margin-bottom: 25px;
        box-sizing: border-box;
    }
    .form-section-legend {
        font-size: 15px;
        font-weight: bold;
        color: #0044aa;
        padding: 0 10px;
    }
    .form-group-row {
        display: flex;
        align-items: center;
        margin-bottom: 18px;
    }
    .form-group-row label {
        width: 160px;
        font-weight: bold;
        font-size: 14px;
        color: #333333;
        text-align: left;
    }
    .input-wrapper {
        flex: 1;
        display: flex;
        align-items: center;
        gap: 15px;
    }
    .form-control-input, .form-control-select {
        width: 280px;
        height: 36px;
        padding: 6px 12px;
        font-size: 14px;
        border: 1px solid #e0e0e0;
        border-radius: 4px;
        box-sizing: border-box;
        background-color: #ffffff;
        transition: border-color 0.2s ease;
    }
    .form-control-file {
        font-size: 14px;
    }
    .form-control-input:focus, .form-control-select:focus {
        border-color: #a0a0a0;
        outline: none;
    }
    .button-group-row {
        margin-left: 160px;
        display: flex;
        gap: 10px;
        margin-top: 10px;
    }
    .btn-submit-orange {
        background: linear-gradient(to bottom, #ffcc44 0%, #ffbb22 100%);
        border: 1px solid #e5a515;
        border-radius: 6px;
        color: #222222;
        font-size: 14px;
        font-weight: bold;
        padding: 10px 24px;
        cursor: pointer;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        min-width: 140px;
        text-align: center;
    }
    .btn-submit-orange:hover {
        background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
    }
    .btn-action-gray {
        background: #f0f0f0;
        border: 1px solid #cccccc;
        border-radius: 6px;
        color: #333333;
        font-size: 14px;
        font-weight: bold;
        padding: 10px 24px;
        cursor: pointer;
        min-width: 100px;
        text-align: center;
    }
    .btn-action-gray:hover {
        background: #e5e5e5;
    }
</style>

<script type="text/javascript">
function setCursor() {
    document.body.style.cursor = "default";
}

// Cleaned up duplicate JS declaration into a single structural logic controller
function handleAction(actionType) {
    var form = document.getElementById('fileUploadForm');
    
    if (!actionType) {
        document.body.style.cursor = 'wait';
        if (document.getElementById('btnUpload')) document.getElementById('btnUpload').disabled = true;
        if (document.getElementById('btnCancel')) document.getElementById('btnCancel').disabled = true;
        document.getElementById('cmd').value = 'Upload';
        return true;
    }
    
    if (actionType === 'Upload') {
        document.getElementById('cmd').value = 'Upload';
        form.submit(); 
    } else if (actionType === 'Create') {
        document.getElementById('cmd').value = 'Create';
        form.submit(); 
    }
}
</script>
</head>

<body bgcolor="white" onload="setCursor();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/header.jsf" %>
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<div class="search-container">

    <s:form id="fileUploadForm" name="fileUploadForm" action="fileUpload" method="post" enctype="multipart/form-data" onsubmit="return handleAction();">
        <s:hidden name="cmd" id="cmd" />

        <!-- Segmented Section 1: File Storage Upload Interface -->
        <fieldset class="form-section-fieldset">
            <legend class="form-section-legend">Upload File Action</legend>
            
            <div class="form-group-row">
                <label for="theFile">File Name</label>
                <div class="input-wrapper">
                    <s:file name="theFile" id="theFile" cssClass="form-control-file" theme="simple" />
                    
                    <!-- Dynamic map/collection selection binding configured for Struts 2 -->
                    <s:select name="strDirectory" id="strDirectory" cssClass="form-control-select" theme="simple"
                              list="dirs" headerKey="root" headerValue="Root" />
                </div>
            </div>

            <div class="button-group-row">
                <input type="button" id="btnUpload" class="btn-submit-orange" value="Upload File" onclick="handleAction('Upload');" />
                <input type="button" id="btnCancel" class="btn-action-gray" value="Cancel" onclick="checkFilterDirtyFlag('mainMenu.action');" />
            </div>
        </fieldset>

        <!-- Segmented Section 2: Directory Utility Panel -->
        <fieldset class="form-section-fieldset">
            <legend class="form-section-legend">Create Directory Utility</legend>
            
            <div class="form-group-row">
                <label for="dirName">Directory Name</label>
                <div class="input-wrapper">
                    <s:textfield name="dirName" id="dirName" cssClass="form-control-input" maxlength="30" theme="simple" />
                </div>
            </div>

            <div class="button-group-row">
                <input type="button" id="btnCreate" class="btn-submit-orange" value="Create Directory" onclick="handleAction('Create');" />
            </div>
        </fieldset>

    </s:form>
</div>

<%@ include file="/include/footer.jsf" %>
</body>
</html>