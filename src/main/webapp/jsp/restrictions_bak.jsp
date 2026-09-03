<%! String pageTitle="Report Restrictions";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
 <%@ include file="/include/masthead.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<html:form name="restrictionForm" action="" type="abbott.ai.tcgm.action.form.TCGMProductionForm" scope="session" >
<nested:define id="columns"  property="columns" />
<nested:define id="operations"  property="operations" />
	   <table width="690" border="0" cellpadding="2">
		 <tr>
		   <td width="86">&nbsp;</td> 
		   <td colspan="5"  class="tableHeading">Enter Job/Report Restrictions</td>
		 </tr>
		 <tr>
		   <td height="23" class=right ></td>
		   <td width="31" height="23" class=right ></td>
		   <td width="145" class="tableEntry" >Restriction</td><td class="tableEntry" >Operation</td><td class="tableEntry" >Value</td>

	<td><a href="javascript:cancelAddJob(document.forms[0])" ><img border="0"  src="images/btnCancel.png" ></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:submitRestrictions(document.forms[0])"><img border="0" src="images/btnAddJob.png" ></a></td>
		 </tr>
			 <nested:iterate name="mngFactorsForm" property="restrictions" id="reportRestriction" type="abbott.ai.tcgm.entities.ReportRestriction">
		<tr>
		  <td height="24"></td>
		  <td height="24" ></td>
		  <td width="145"><nested:select name="reportRestriction" property="column" styleClass="cmdOpt"><html:options name="columns" /></nested:select></td>
		  <td width="64"><nested:select name="reportRestriction" property="operation" styleClass="cmdOpt"><html:options name="operations" /></nested:select></td>
		  <td width="184"><nested:text name="reportRestriction" property="value" styleClass="cmdOpt" /></td>
		  <td width="142">&nbsp;</td>
		</tr>
			 </nested:iterate>
		 <tr>

	<td colspan=6 align=right>&nbsp;</td>
		 </tr>
	   </table>
<html:hidden property="formHandler" /><html:hidden property="cmd" />
<div class="hidden"><html:checkbox property="restrictionEntered" /></div>
</html:form>

<p>&nbsp; </p>
<p>&nbsp;</p>

<%@ include file="/include/footer.jsf" %>