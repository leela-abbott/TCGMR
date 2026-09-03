<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="abbott.ai.tcgm.AppConst"%>
<%@ page import=" javax.servlet.RequestDispatcher"%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean"%>
<bean:define id="TCGMUser" name="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<html>
<head>
<title>TCGM Cognos Reports </title>
<SCRIPT type="text/javascript">
function loadReports(){
var password = encodeURIComponent('<%=  TCGMUser.getPassword()%>');
//alert('<%=AppConst.reportsUrl%>');
location.href = '<%=AppConst.reportsUrl%>?b_action=xts.run&m=portal/cc.xts&m_path=%2fcontent%2ffolder%5b%40name%3d%27AI%20TCGM%27%5d&CAMUsername=<%= TCGMUser.getUserid()%>&CAMPassword='+password;
}
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="javascript:loadReports()">

</body>
</html>
