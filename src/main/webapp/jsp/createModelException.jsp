<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>System Error Diagnostic Matrix</title>
<style>
body {
	font-family: monospace;
	padding: 20px;
	background-color: #fcfcfc;
	color: #222;
}

.error-box {
	border: 2px solid #cc0000;
	background-color: #fff0f0;
	padding: 15px;
	margin-bottom: 20px;
	border-radius: 4px;
}

h1 {
	color: #cc0000;
	font-size: 20px;
	margin-top: 0;
}

table {
	border-collapse: collapse;
	margin-top: 10px;
	width: 100%;
}

td {
	padding: 6px;
	border: 1px solid #ddd;
	vertical-align: top;
}

.label {
	font-weight: bold;
	color: #0044aa;
	width: 150px;
}

pre {
	background: #f8f9fa;
	/* FIXED: Light grey background to replace the solid black banner */
	color: #333333;
	/* FIXED: Dark charcoal text color for easy readability */ border : 1px
	solid #e2e8f0;
	/* FIXED: Soft structural accent border line */
	padding: 15px;
	overflow: auto;
	border-radius: 4px;
	font-size: 12px;
	font-family: monospace;
	margin-top: 20px;
	border: 1px solid #e2e8f0;
}
</style>
</head>
<body>

	<table>
		<tr>
			<td class="label">Exception Type:</td>
			<td>
				<%
				if (exception != null) {
					out.print(exception.getClass().getName());
				} else if (request.getAttribute("jakarta.servlet.error.exception") != null) {
					out.print(request.getAttribute("jakarta.servlet.error.exception").getClass().getName());
				} else if (request.getAttribute("abbott.ai.tcgm.Exception") != null) {
					out.print(request.getAttribute("abbott.ai.tcgm.Exception").getClass().getName());
				} else {
					out.print("abbott.ai.tcgm.exception.TCGMException");
				}
				%>
			</td>
		</tr>
		<tr>
			<td class="label">Error Message:</td>
			<td style="color: red; font-weight: bold;">
				<%
				if (exception != null && exception.getMessage() != null) {
					out.print(exception.getMessage());
				} else if (request.getAttribute("jakarta.servlet.error.message") != null) {
					out.print(request.getAttribute("jakarta.servlet.error.message"));
				} else if (request.getAttribute("errorMessage") != null) {
					out.print(request.getAttribute("errorMessage"));
				} else {
					out.print("No explicit exception message string mapped to error tracking stream.");
				}
				%>
			</td>
		</tr>

		<%
		if (request.getAttribute("errorClass") != null) {
		%>
		<tr>
			<td class="label">Class:</td>
			<td><%=request.getAttribute("errorClass")%></td>
		</tr>
		<%
		}
		%>

		<%
		if (request.getAttribute("errorMethod") != null) {
		%>
		<tr>
			<td class="label">Method:</td>
			<td><%=request.getAttribute("errorMethod")%></td>
		</tr>
		<%
		}
		%>
	</table>

	<pre>
		<%
		if (exception != null) {
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw = new java.io.PrintWriter(sw);
			exception.printStackTrace(pw);
			out.print(sw.toString());
		} else if (request.getAttribute("jakarta.servlet.error.exception") != null) {
			Throwable t = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw = new java.io.PrintWriter(sw);
			t.printStackTrace(pw);
			out.print(sw.toString());
		} else if (request.getAttribute("abbott.ai.tcgm.Exception") != null) {
			Throwable t = (Throwable) request.getAttribute("abbott.ai.tcgm.Exception");
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw = new java.io.PrintWriter(sw);
			t.printStackTrace(pw);
			out.print(sw.toString());
		}
		%>
	</pre>

	<div
		style="margin-top: 25px; font-family: Arial, sans-serif; font-size: 13px;">
		<%
		if (request.getAttribute("errorMessage") != null && request.getRequestURI().contains("createAnalysisModel")) {
		%>
		<a href="createAnalysisModel.action"
			style="color: #0044aa; font-weight: bold; text-decoration: underline;">&lt;&lt;
			Return to Create Analysis Model</a>
		<%
		} else if (request.getAttribute("errorMessage") != null && request.getRequestURI().contains("createPerpetualModel")) {
		%>
		<a href="createPerpetualModel.action"
			style="color: #0044aa; font-weight: bold; text-decoration: underline;">&lt;&lt;
			Return to Create Perpetual Model</a>
		<%
		} else {
		%>
		<a href="javascript:window.history.back();"
			style="color: #0044aa; text-decoration: underline;">&lt;&lt;
			Return to Previous Page</a>
		<%
		}
		%>
	</div>
</body>
</html>
