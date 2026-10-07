<%@ page isErrorPage="true" %>
<%! String pageTitle="Exception"; %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../include/headerLogin.jsf" %>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">
    <%@ include file="../include/mastheadLogin.jsf" %>

    <table style="width: 100%; border: 0; cellpadding: 0; cellspacing: 0;">
        <tr>
            <td>
                <table style="border: 0; margin: 0 auto; text-align: left; font-family: Arial, sans-serif; font-size: small;">

                    <s:if test="#request.TCGMException != null">
                        <tr>
                            <td class="labelLeft" style="font-weight: bold; color: Navy; padding: 5px; text-align: right; width: 120px;">
                                Error Message:
                            </td>
                            <td style="padding: 5px; color: Red; font-weight: bold;">
                                <s:property value="#request.TCGMException.errorMessage" default="N/A" />
                            </td>
                        </tr>
                        <tr>
                            <td class="labelLeft" style="font-weight: bold; color: Navy; padding: 5px; text-align: right;">
                                Class:
                            </td>
                            <td style="padding: 5px;">
                                <s:property value="#request.TCGMException.throwingClass" default="N/A" />
                            </td>
                        </tr>
                        <tr>
                            <td class="labelLeft" style="font-weight: bold; color: Navy; padding: 5px; text-align: right;">
                                Method:
                            </td>
                            <td style="padding: 5px;">
                                <s:property value="#request.TCGMException.throwingMethod" default="N/A" />
                            </td>
                        </tr>
                        <tr>
                            <td class="labelLeft" style="font-weight: bold; color: Navy; padding: 5px; text-align: right;">
                                Parameter List:
                            </td>
                            <td style="padding: 5px;">
                                <s:property value="#request.TCGMException.parameterList" default="N/A" />
                            </td>
                        </tr>
                    </s:if>
                    <s:else>
                        <tr>
                            <td class="labelLeft" style="font-weight: bold; color: Navy; padding: 5px; text-align: right; width: 120px;">
                                Error Message:
                            </td>
                            <td style="padding: 5px; color: Red; font-weight: bold;">
                                <s:if test="exception != null">
                                    <s:property value="exception.toString()" />
                                </s:if>
                                <s:else>
                                    <%= (exception != null) ? exception.toString() : "An unexpected application error occurred." %>
                                </s:else>
                            </td>
                        </tr>
                    </s:else>

                </table>
            </td>
        </tr>
    </table>

    <%@ include file="../include/footer.jsf" %>
</body>
</html>


<!-- <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>

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
</html> -->