<%@ page import="abbott.ai.tcgm.AppConst"%>
<%String pageTitle = "Cognos Users View";
			%>

<%@ include file="/include/header.jsf"%>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf"%>
<%@ include file="/include/errorDisplay.jsf"%>

<table width="700" border="0" cellpadding="2" cellspacing="2">
	<tr></tr>
	<tr></tr>
	<tr>

		<td align="center" colspan=3><font size=2 color="Blue"> This would trigger the process of getting Cognos Users.
		</td>
	</tr>
	<tr>

		<td align="center" colspan=3><font size=2 color="Red"> <b> It might take several hours to complete. </b>
		</td>
	</tr>
	
	<tr>
		<td align="center" colspan=3>
		<a href="rptUserMaint.do?cmd=users">Get Cognos Users </a>
		</td>

	</tr>

</table>

<br>
<br>
<br>
<br>


<div ID="msgPopupDiv"
	STYLE="position:absolute;visibility:hidden;background-color:yellow"></div>
<br>
<br>

<a href="#top">Return to Top</a>
</body>
</html>
