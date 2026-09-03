
<%@ page import="abbott.ai.tcgm.action.form.ProcessForm" %>
<%! String pageTitle = "Generate Report"; %>
<%@ include file="/include/headerJbQue.jsf" %>
<jsp:useBean id="processForm" scope="session" class="abbott.ai.tcgm.action.form.ProcessForm" />
<body leftmargin="100" topmargin="0" marginwidth="4" marginheight="0">
	<a name="top"></a>
<table width="600" height="62" border="0" cellpadding="0" cellspacing="0" background="images/masthead.png">
<tr>
<td width="100" >&nbsp;&nbsp;
		</td>
<td class="pageHeading" nowrap>&nbsp;
		</td>
	</tr>
</table> 
		
	<html:form name="processingForm" action="" type="abbott.ai.tcgm.action.form.ProcessForm">
	<%@ include file="/include/errorDisplay.jsf" %>
<table  align="left" cellpadding="2">
<tr>
<td class="tableEntry">Job Queue Id</td>
<td class="tableEntry">&nbsp;&nbsp;</td>
<td class="tableEntry"><%=processForm.getSelProcessId() %></td>
</tr>
 <tr>
 <td >&nbsp;&nbsp;</td>
 <td >&nbsp;&nbsp;</td>
<td   align="right" class="pageHeading"><div align="right"><input name="button" type=button onClick="close_win()" value="Close Window"> </div></td>  </tr>

</table> 
 

      
</html:form>
	


<script>
function close_win() 
    {
     window.close();
    }
 </script>    