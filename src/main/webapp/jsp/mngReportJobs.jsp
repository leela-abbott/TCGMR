<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 1. Initialize pageTitle context BEFORE pulling layout headers --%>
<%! String pageTitle="Job Reports"; %>

<%-- 2. Include document headers --%>
<%@ include file="/include/header.jsf" %>

<head>
<style>
    /* Safe visual centering container protecting top global menus */
    .center-container {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 50vh;
        box-sizing: border-box;
        padding: 20px;
    }
</style>

<script type="text/javascript">
function openReportNet() {
    // 1. Grab the base reports URL exposed from your backend Action class getter
    var baseUrl = '<s:property value="reportsUrl" escapeHtml="false" />';
    
    // 2. Fallback protection: if the OGNL property evaluates to blank, explicitly apply your target configuration address
    if (!baseUrl || baseUrl === "") {
        baseUrl = "http://oneabbott.com";
    }
    
    // 3. Route to your standard corporate framework script or default to native window launch
    if (typeof viewReportNetReports === 'function') {
        viewReportNetReports(baseUrl);
    } else {
        window.open(baseUrl, 'ReportNetWindow', 'width=1280,height=800,resizable=yes,scrollbars=yes,status=yes');
    }
}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<%-- 3. Standard global application template masthead and errors interface imports --%>
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<%-- 4. Isolated flexbox display content wrapper --%>
<div class="center-container">
    
    <%-- Native CSS-styled Action Button targeting our simplified wrapper script function --%>
    <button type="button" 
            onclick="openReportNet()"
            style="background-color: #FFD523; 
                   color: #003B73; 
                   font-family: sans-serif;
                   font-size: 16px; 
                   font-weight: bold; 
                   font-style: italic;
                   padding: 10px 24px; 
                   border: 1px solid #E6BE1E; 
                   border-radius: 4px; 
                   cursor: pointer; 
                   box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                   transition: background-color 0.2s ease;">
        Open ReportNet Window
    </button>
    
</div>

<%@ include file="/include/footer.jsf" %>
</body>
