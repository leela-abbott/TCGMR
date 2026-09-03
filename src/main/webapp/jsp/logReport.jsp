<%@ page import="abbott.ai.tcgm.action.form.ProcessForm" %>
<%! String pageTitle = " Application and Error Logs- TCGM"; %>
<%@ include file="/include/headerJbQue.jsf" %>




<jsp:useBean id="processForm" scope="session" class="abbott.ai.tcgm.action.form.ProcessForm" />

<body leftmargin="100" topmargin="0" marginwidth="4" marginheight="0">
	<a name="top"></a>

<table width="640" height="62" border="0" cellpadding="0" cellspacing="0" background="images/masthead.png">
	<tr>
		
<td width="100" >&nbsp;&nbsp;
		</td>
<td class="pageHeading" nowrap>&nbsp;
			
		</td>
		<td class="pageHeading" nowrap>
            Application and Error Logs- TCGM
		</td>
	</tr>
</table> 
		
	<html:form name="processingForm" action="" type="abbott.ai.tcgm.action.form.ProcessForm">
	
<table  align="left" cellpadding="2">
 <tr>
<td   align="right" class="pageHeading"><div align="right"><input name="button" type=button onClick="close_win()" value="Close Window"> </div></td>  </tr>

  <tr>
	  <td colspan="2" class="tableEntry"> <div align="left">Job Queue Id - <%=processForm.getSelProcessId() %>,&nbsp; <%=processForm.getSelJodDesc() %> <%=processForm.getSelJodStatus() %>
      </div></td></tr>
<% String strModelName = processForm.getSelJodStatus().substring(processForm.getSelJodStatus().indexOf("|")+1,processForm.getSelJodStatus().lastIndexOf("|")-1); %>
  
<% if (processForm.getLogList()!=null && processForm.getLogList().size()!=0){ %>
  
    <tr>
		<td> 
 <table width="749" align="left" cellpadding="3">
 
 
  <tr>
	  <td width="276"><div align="left"></div></td>
	  <td width="214" align="center" class="pageHeading">Application Log</td>
	  <td align="center" class="pageHeading">&nbsp;</td>
	  <td align="center" class="pageHeading">&nbsp;</td>
	  <td colspan="6" align="center" class="pageHeading">&nbsp;</td>
	</tr>
 </table> </td> 
 </tr>
  
    <tr>
		<td> 
 <table width="750" align="left" cellpadding="3">
 
   <tr>
		
		<td class="tableHeading">Model Name</td>
		<td class="tableHeading">Proc Name</td>
		<td class="tableHeading">Status</td>
		<td class="tableHeading">Message</td>  
		<td class="tableHeading" width="200">Time</td> 
	  </tr> 

	<logic:iterate id="prfm" name="processForm" scope="session" property="logList" type="abbott.ai.tcgm.entities.LogReportBean" >
	  <tr>
		
		<!-- <td class="tableEntry"><bean:write name="prfm" property="modelid" /></td> -->
		<td class="tableEntry"><%=strModelName %></td>
		<td class="tableEntry"><bean:write name="prfm" property="procname" /></td>
		<td class="tableEntry"><bean:write name="prfm" property="status" /></td>
		<td class="tableEntry"><bean:write name="prfm" property="msg" /></td> 
		<td class="tableEntry" width="200"><bean:write name="prfm" property="crtdate" /></td> 
	  </tr> 
	</logic:iterate>  
</table></td> 
</tr> 

   

<%}else{%>
  <tr>
   <td>
 

 <table width="500" align="center" cellpadding="0">

  <tr>
	  <td width="100">&nbsp;</td>
	  <td colspan="5" align="center" class="pageHeading">Application Log</td>
	  </tr>
	  <tr>
	  <td width="200">&nbsp;</td>
	  <td colspan="5" align="center" class="tableEntry">There is no data in the Application Log Table</td>
	</tr>
 </table> </td> 
 </tr>

    

<%}%>
        
	<br>
	<br>  
	<br>
	<br>  
	
	<% if (processForm.getErrorList()!=null && processForm.getErrorList().size()!=0){ %>
	
	
<tr>
   <td>
   
  <table width="300" align="center" cellpadding="3">
  
  <tr> 
	  <td width="100">&nbsp;</td>
	  <td colspan="6" class="pageHeading">Error Log</td>
	</tr>
	</table> </td> 
 </tr>
 

 
 <tr>
 <td>
	
	<table width="750" align="left" cellpadding="3">
	
	<tr>
		
		<td class="tableHeading">Modelid</td>
		<td class="tableHeading">ProcName</td>
		<td class="tableHeading">Status</td>
		<td class="tableHeading">Message</td>  
		<td class="tableHeading" width="200">Time</td>  
	  </tr>
	
	<logic:iterate id="errorLog" name="processForm" scope="session" property="errorList" type="abbott.ai.tcgm.entities.ErrorLogBean" >
	  <tr>
		
   
		<td class="tableEntry"><bean:write name="errorLog" property="modelid" /></td>
		<td class="tableEntry"><bean:write name="errorLog" property="procname" /></td>
		<td class="tableEntry"><bean:write name="errorLog" property="status" /></td>
		<td class="tableEntry" ><bean:write name="errorLog" property="msg" /></td> 
		<td class="tableEntry" width="200"><bean:write name="prfm" property="crtdate" /></td> 
	  </tr> 
	</logic:iterate>  
 </table> </td> 
 </tr> 


 
 <%}else{%> 
 
 <tr>
		<td>
 
 <table width="500" align="left" cellpadding="0">

  <tr>
	  <td width="100">&nbsp;</td>
	  <td colspan="5" align="center" class="pageHeading">Error Log</td>
	  </tr> 
	  <tr>
	  <td width="200">&nbsp;</td>
	  <td colspan="5" align="center" class="tableEntry">There is no data in the Error Log Table</td>
	</tr> 
 </table> </td> 
	  </tr> 

    

<%}%>
  

 <tr>
 <td>
 <%@ include file="/include/footer.jsf" %> </td>
 </tr>
</table> 
 

      
</html:form>
	


<script>
function close_win() 
    {
     window.close();
    }
 </script>    