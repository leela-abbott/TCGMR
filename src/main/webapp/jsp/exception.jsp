<%@ page isErrorPage="true" %>
<%! String pageTitle="Exception"; %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../include/headerLogin.jsf" %>

<body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;">
    <%@ include file="../include/mastheadLogin.jsf" %>

    <!-- Modernized Full-Width Layout Matrix Container -->
    <table style="width: 100%; border: 0; cellpadding: 0; cellspacing: 0;">
        <tr>
            <td>
                <table style="border: 0; margin: 0 auto; text-align: left; font-family: Arial, sans-serif; font-size: small;">
                    
                    <!-- Case 1: Handle Known Application-Specific TCGMException Contexts -->
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

                    <!-- Case 2: Fallback to Raw Uncaught Systemic Web Container Exceptions -->
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