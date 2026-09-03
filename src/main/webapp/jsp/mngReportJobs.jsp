<%@ page import="abbott.ai.tcgm.AppConst" %>
<% String pageTitle="Job Reports"; %> 

<%@ include file="/include/header.jsf" %>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<table width="700" border="0" cellpadding="2" cellspacing="2" >
 <tr></tr>
 <tr></tr>
 <tr>
 
 <!-- <td align="center" class="tableHeading"><a href="http://abtapz0452dv/crn/cgi-bin/cognos.cgi?b_action=xts.run&m=portal/main.xts">
Click here to view Reports.
</a></td> -->
<% String reportsUrl = AppConst.reportsUrl; %>
	<td colspan="2" width=400 valign="center">
		<div align="center">
		  <br>
			<a href="javascript:viewReportNetReports('<%=reportsUrl%>')"><img src="images/btnOpenReportsWindow.png" border=0></a></div>
		  <br><br>
	</td>

 </tr>
</table>

<br><br>
<br><br>
<% //Sridevi.K 8/23/05 This code is all commented as this page just has to provide a link to the reportNet %>
	<%/*
	<html:form name="mngReportJobsForm" action="mngReportJobs.do" type="abbott.ai.tcgm.action.form.MngReportJobsForm" >
	<bean:define id="reportList" name="mngReportJobsForm" property="reportList" />
	<bean:define id="destinationList" name="mngReportJobsForm" property="destinationList" />
	<div id="divToHide">
	  <table width="700" border="0" cellpadding="2" cellspacing="2" >
		<tr>
		  <td align="center" class="tableHeading">Available Reports</td>
		  <td colspan="2" width=200 align="center" class="tableHeading">Print & Display Options</td>
		</tr>
		<tr valign="top">
		  <td rowspan="4" >
			  <div align="center">
				  <html:select property="selReport" size="20">
						  <html:options property="reportInstanceId" labelProperty="reportInstanceName" collection="reportList" />
			  </html:select>
		 </div>
			</td>
			<td>
			<table>
			 <tr>
			  <td colspan="2" width=300 valign="top">
				<div align="center">
				  <br>
				<a href="javascript:viewReport(document.forms[0].selReport.value)"><img src="images/btnDisplay.png" border=0 width="80" height="20"></a></div>
				  <br><br>
			  </td>
			</tr>
			<tr valign="top">
			  <td width="100" valign="top" class="smNormal">
				<html:checkbox property="chkImmediate" /> Immediate<br>
				<html:select property="selReportDest">
					<option>Rmt 143</option><option>Rmt 14</option>
				</html:select>
			  </td>
			  <td width="100" valign="top" class="smNormal">
<!--			  
					<html:checkbox property="chkRestrict" />Restrict<br>
-->					
					<html:text property="numCopies" value="1" size="3" maxlength="3"  styleClass="smNormal" /> Copies
			   </td>		   
			 </tr>
		<tr>
		  <td colspan="2" width=200><div align="center"><a href="javascript:changeCmdAndSubmit(document.forms[0], 'PRINT')"><img src="images/btnPrint.png" border=0 width="80" height="20"></a></div></td>
		</tr>
		</table>
		<div align="center"><br>
		  <br>
		  <br>
		  <a href="javascript:deleteReport(document.forms[0].selReport);"><img border="0" height="20" src="images/btnDelete.png" width="80"></a><br>
		</div></td>
		</tr>
	  </table>
	  </div>
	  <html:hidden property="cmd" />
	  <html:hidden property="returnLocation" />
	</html:form>
	<script language="javascript">


function deleteReport(selectList) {

	if (selectList.value == 0) {
		alert ("You must select a report to delete.");
		return;
	}

	var itemName = getSelectedOptionText(selectList);
	if ( confirm("Are you sure that you would like to delete " + itemName + "?\nThis can not be undone.") ) {
		changeCmdAndSubmit(document.forms[0], "delete");
		}
}

</script>
	*/%>
<%@ include file="/include/footer.jsf" %>