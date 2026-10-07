<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<% String pageTitle = "File Upload"; %>
<c:set var="pageTitle" value="File Upload" scope="request" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= pageTitle %></title>
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
        width: 250px;
        height: 30px;
        padding: 4px 10px;
        font-size: 13px;
        border: 1px solid #e0e0e0;
        border-radius: 4px;
        box-sizing: border-box;
        background-color: #ffffff;
        transition: border-color 0.2s ease;
    }
    .form-control-file {
        font-size: 13px;
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
        border-radius: 4px;
        color: #222222;
        font-size: 13px;
        font-weight: bold;
        padding: 6px 20px;
        cursor: pointer;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        min-width: 120px;
        text-align: center;
    }
    .btn-submit-orange:hover {
        background: linear-gradient(to bottom, #ffd666 0%, #ffcc33 100%);
    }
    .btn-action-gray {
        background: #f0f0f0;
        border: 1px solid #cccccc;
        border-radius: 4px;
        color: #333333;
        font-size: 13px;
        font-weight: bold;
        padding: 6px 20px;
        cursor: pointer;
        min-width: 90px;
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

function upload(){
    var fileInput = document.getElementById("theFile");
    
    // Safety check: Handles validation if validateFile exists, otherwise allows submission
    if(fileInput) {
        if (typeof validateFile === "function" && !validateFile(fileInput.value)) {
            return false;
        }
        
        document.body.style.cursor = 'wait';
        var cmdInput = document.getElementById("cmd");
        if(cmdInput) {
            cmdInput.value = 'Upload';
        }
        
        // RESOLVED: Uses document.getElementById to safely target and submit the form element
        var form = document.getElementById("fileUploadForm");
        if(form) {
            form.submit();
            return true;
        }
    }
    return false;
}
</script>
</head>
<%-- RESOLVED: Renamed ID here from fileUploadForm to fileUploadFormBean to avoid duplicate ID conflicts with the form --%>
<jsp:useBean id="fileUploadFormBean" scope="session" class="abbott.ai.tcgm17.action.FileUploadAction" />
<body bgcolor="white" onload="setCursor();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/header.jsf" %>
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<div class="search-container">

    <s:form id="fileUploadForm" action="fileUploadv2" namespace="/" method="post" enctype="multipart/form-data" theme="simple">
        <s:hidden name="cmd" id="cmd" />
        <fieldset class="form-section-fieldset">
            <legend class="form-section-legend">Upload File Action</legend>
            <div class="form-group-row">
                <label for="theFile">File Name</label>
                <div class="input-wrapper">
                    <s:file name="theFile" id="theFile" cssClass="form-control-file" theme="simple" />
                    <s:select name="strDirectory" id="strDirectory" cssClass="form-control-select" theme="simple"
                              list="dirs" headerKey="root" headerValue="Root" />
                </div>
            </div>
            <div class="button-group-row">
                <%-- RESOLVED: Changed class to btn-submit-orange to map correctly to your style definitions --%>
                <button type="button" class="btn-submit-orange" onclick="upload();">Upload</button>
                <input type="button" id="btnCancel" class="btn-action-gray" value="Cancel" onclick="checkFilterDirtyFlag('mainMenu.action');" />
            </div>
        </fieldset>
    </s:form>
    
    <s:form id="directoryCreateForm" action="createDirectory" method="post">
        <input type="hidden" name="cmd" value="Create" />
        <fieldset class="form-section-fieldset">
            <legend class="form-section-legend">Create Directory Utility</legend>
            <div class="form-group-row">
                <label for="dirName">Directory Name</label>
                <div class="input-wrapper">
                    <s:textfield name="dirName" id="dirName" cssClass="form-control-input" maxlength="30" theme="simple" />
                </div>
            </div>
            <div class="button-group-row">
                <s:submit class="btn-submit-orange" value="Create Directory" theme="simple" />
            </div>
        </fieldset>
    </s:form>

</div>

<%@ include file="/include/footer.jsf" %>
</body>
</html>
