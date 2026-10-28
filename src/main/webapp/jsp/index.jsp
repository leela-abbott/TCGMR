<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Automatically forwards root path visitors to your Struts 2 action pipeline
    response.sendRedirect(request.getContextPath() + "/login.action");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redirecting...</title>
</head>
<body>
    <p>Loading application, please wait... If you are not redirected automatically, <a href="<%= request.getContextPath() %>/login.action">click here</a>.</p>
</body>
</html>
