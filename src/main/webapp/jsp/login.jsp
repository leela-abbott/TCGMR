<%! String pageTitle= "Login";%>
<%@ include file="/include/headerLogin.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/mastheadLogin.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<table width="734" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<html:form method="post" name="loginForm" type="abbott.ai.tcgm.action.form.LoginForm" action="/login.do" scope="request">
					<table border="0" align="center">
						<tr>
							<td class="labelLeft">
								User Id:
							</td>
							<td>
								<html:text property="userid"/>
							</td>
						</tr>
						<tr>
							<td class="labelLeft">
								Password:
							</td> 
							<td>
								<html:password property="password"/>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="center">
								<html:image alt="Login" border="0" src="images/btnLogin.png"/>
								<a href="javascript:document.loginForm.reset();">
									<img alt="Reset" border="0" src="images/btnReset.png"/></a>
							</td>
						</tr>
					</table>
				</html:form>
			</td>
		</tr>
	</table>
	<script language="JavaScript1.2" type="text/javascript">
		setFocus('userid');
	</script>
	<b><font face="Arial" size=2 color=blue>The current release # nTCGMv2.2</font></b>
<%@ include file="/include/footer.jsf" %>