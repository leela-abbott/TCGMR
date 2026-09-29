<!DOCTYPE html>
<%!String pageTitle = "TCGM Login";%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Swapped out Struts 1 for modern Struts 2 --%>
<%@ include file="/include/headerLogin.jsf"%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>TCGM Login</title>
<style type="text/css">
.viewport-center-table {
	width: 100%;
	height: 100vh;
	border: 0;
	border-collapse: collapse;
}

.unified-login-card {
	border: 1px solid #D5E1F2;
	padding: 30px 25px;
	background-color: #FFFFFF;
	border-radius: 4px;
	box-shadow: 0px 4px 16px rgba(0, 0, 0, 0.05);
	width: 295px;
	box-sizing: border-box;
	text-align: left;
	display: inline-block;
}

.rurd-label {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
	font-weight: bold;
	color: #000000;
	text-align: left;
	display: block;
	width: 100%;
	margin-top: 8px;
}

.rurd-input {
	width: 100%;
	box-sizing: border-box;
	padding: 5px 6px;
	font-size: 13px;
	font-family: Arial, Helvetica, sans-serif;
	background: linear-gradient(to bottom, #E8F0FE 0%, #DCE6F5 100%);
	border: 1px solid #A4B5D0;
	border-radius: 2px;
	color: #000000;
	margin-top: 4px;
	margin-bottom: 12px;
	display: block;
}

.legacy-btn-container {
	display: flex;
	justify-content: space-between;
	gap: 12px;
	margin-top: 12px;
	width: 100%;
}

/* Styled to match Application A's button styling exactly */
.legacy-button {
	display: inline-block;
	flex: 1;
	padding: 5px 0;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
	font-weight: bold;
	font-style: italic;
	color: #0000A0;
	text-align: center;
	background: linear-gradient(to bottom, #FFE042 0%, #F5B01A 100%);
	border: 1px solid #C49000;
	border-radius: 4px;
	box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.2);
	cursor: pointer;
}

.legacy-button:hover {
	background: linear-gradient(to bottom, #FFF073 0%, #F7C03E 100%);
}
</style>
</head>

<body
	style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #FFFFFF;">
	<table class="viewport-center-table">
		<tr>
			<td align="center" valign="middle">
				<div class="unified-login-card">
					<div style="width: 100%; text-align: center; margin-bottom: 15px;">
						<img
							src="${pageContext.request.contextPath}/images/Abbott_Laboratories_logo.png"
							alt="Abbott Logo"
							style="width: 210px; height: auto; display: inline-block;" />
					</div>
					<div class="gpsPageHdng"
						style="font-size: 20pt; font-weight: bold; color: #0000A0; font-family: Arial, Helvetica, sans-serif; margin-bottom: 25px; width: 100%; text-align: center;">
						TCGM</div>

					<%@ include file="/include/errorDisplay.jsf"%>

					<%-- Converted to Struts 2 s:form. Maps to modern action suffix routing --%>
					<s:form action="login.action" method="post" id="loginForm"
						style="width: 100%; margin: 0; padding: 0;">

						<label for="userid" class="rurd-label">User Id:</label>
						<s:textfield name="userid" id="userid" cssClass="rurd-input"
							theme="simple" />

						<label for="password" class="rurd-label">Password:</label>
						<s:password name="password" id="password" cssClass="rurd-input"
							theme="simple" />

						<!-- Action Button Layout matched to Application A, now processing cleanly -->
						<div class="legacy-btn-container">
							<button type="submit" class="legacy-button">Login</button>
							<button type="button" class="legacy-button"
								onclick="document.getElementById('loginForm').reset();">Reset</button>
						</div>

					</s:form>

				</div>

			</td>
		</tr>
	</table>

	<!-- Version Release Sticky Footer Component matched to Application A -->
	<div style="position: fixed; bottom: 35px; left: 25px;">
		<span
			style="font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #0000A0; font-weight: bold;">The
			current release # TCGMv2.2</span>
	</div>

	<script type="text/javascript">
		// Standard legacy execution callback mapping
		setFocus('userid');
	</script>

	<%@ include file="/include/footer.jsf"%>
</body>
</html>
