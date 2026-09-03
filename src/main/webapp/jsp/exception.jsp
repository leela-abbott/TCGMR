<%@ page isErrorPage="true" %>
<%! String pageTitle="Exception"; %>
<%@ include file="../include/headerLogin.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="../include/mastheadLogin.jsf" %>

	<logic:present name="TCGMException" scope="request">
		<table width="750" border="0" cellpadding="0" cellspacing="0">
	 		<tr>
				<td>
					<table border="0" align="center">
						<tr>
							<td class="labelLeft">
								Error Message:
							</td>
							<td>
								<bean:write ignore="true" name="TCGMException" property="errorMessage" scope="request" />
		 					</td>
						</tr>
						<tr>
							<td class="labelLeft">
								Class:
							</td>
							<td>
								<bean:write ignore="true" name="TCGMException" property="throwingClass" scope="request" />
							</td>
						</tr>
						<tr>
							<td class="labelLeft">
								Method:
							</td>
							<td>
								<bean:write ignore="true" name="TCGMException" property="throwingMethod" scope="request" />
							</td>
						</tr>
						<tr>
							<td class="labelLeft">
								Parameter List:
							</td>
							<td>
								<bean:write ignore="true" name="TCGMException" property="parameterList" scope="request" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</logic:present>
	<logic:notPresent name="TCGMException" scope="request">
		<table width="750" border="0" cellpadding="0" cellspacing="0">
	 		<tr>
				<td>
					<table border="0" align="center">
						<tr>
							<td class="labelLeft">
								Error Message:
							</td>
							<td>
								<%=exception.toString()%>					
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</logic:notPresent>
	<%@ include file="../include/footer.jsf" %>