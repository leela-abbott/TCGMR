<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="abbott.ai.tcgm.AppConst"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set var="TCGMUser" value="#session.TCGMUser" scope="page" />

<html>
<head>
<title>TCGM Cognos Reports</title>
<script type="text/javascript">
function loadReports(){
    // Use Struts 2 s:property tags to safely output the user properties
    var password = encodeURIComponent('<s:property value="#TCGMUser.password" escapeJavaScript="true" />');
    
    location.href = '<%= AppConst.reportsUrl %>?&CAMUsername=<s:property value="#TCGMUser.userid" escapeJavaScript="true" />&CAMPassword=' + password;
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="loadReports()">

</body>
</html>
