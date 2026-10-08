<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>System Error Diagnostic Matrix</title>
    <style>
        body { font-family: monospace; padding: 20px; background-color: #fcfcfc; color: #222; }
        .error-box { border: 2px solid #cc0000; background-color: #fff0f0; padding: 15px; margin-bottom: 20px; border-radius: 4px; }
        h1 { color: #cc0000; font-size: 20px; margin-top: 0; }
        table { border-collapse: collapse; margin-top: 10px; width: 100%; }
        td { padding: 6px; border: 1px solid #ddd; vertical-align: top; }
        .label { font-weight: bold; color: #0044aa; width: 150px; }
        pre { background: #222; color: #fff; padding: 15px; overflow: auto; border-radius: 4px; font-size: 12px; }
    </style>
</head>
<body>

    <div class="error-box">
        <h1>[TCGM Platform Intercept] Severe Core Failure Intercepted</h1>
        <p>The system isolated a container boundary error. Safe standard JSP rendering active.</p>
    </div>

    <table>
        <tr>
            <td class="label">Status Code:</td>
            <td><%= request.getAttribute("jakarta.servlet.error.status_code") %></td>
        </tr>
        <tr>
            <td class="label">Exception Type:</td>
            <td><%= (exception != null) ? exception.getClass().getName() : "Unknown/Filter Level Error" %></td>
        </tr>
        <tr>
            <td class="label">Error Message:</td>
            <td style="color:red; font-weight:bold;">
                <%= (exception != null) ? exception.getMessage() : request.getAttribute("jakarta.servlet.error.message") %>
            </td>
        </tr>
    </table>

    <h3>Root Stack Trace Detail:</h3>
    <pre><%
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
        } else {
            out.print("No explicit stack details captured at container layer.");
        }
    %></pre>

</body>
</html>