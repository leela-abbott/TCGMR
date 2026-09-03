<%! String pageTitle="Data Feed Log"; %>
<%@ include file="../include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
  <%@ include file="../include/masthead.jsf" %>
  <%@ include file="/include/errorDisplay.jsf" %>

<nested:form method="post" name="mngDataFeedLogForm" type="abbott.ai.tcgm.action.form.MngDataFeedLogForm" action="/mngDataFeedLog.do" scope="request">

<table class="tableCommand">
	<tr><td colspan=4 align=right><a href="javascript:changeCmdAndSubmit(document.forms[0], 'DELETE_ALL')"><IMG src="images/btnDeleteAll.png" border=0></a> </td></tr>
	<tr class="tableHeading">
		<td>File</td>
		<td>Start Time</td>
		<td>End Time</td>
		<td>Completion Message</td>
	</tr>

	<% int rowNumber=0; %>
	<c:forEach var="mngDataFeedLogBean" items="${mngDataFeedLogForm.logentrylist}"  varStatus="mngDataFeedLogStatus">	               			
		<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	
			<td class="mntLeft"><c:out value="${mngDataFeedLogBean.fullFileName}" /></td>
			<td class="mntLeft"><c:out value="${mngDataFeedLogBean.startTimeInStringFormat}" /></td>
			<td class="mntLeft"><c:out value="${mngDataFeedLogBean.endTimeInStringFormat}" /></td>
			<td class="mntLeft"><c:out value="${mngDataFeedLogBean.completionMsg}" /></td>						
		</abbott:row>
		<% rowNumber++; %>  
	</c:forEach>

	<nested:hidden property="cmd" />
</table>
</nested:form>
<%@ include file="../include/footer.jsf" %>