<%@ page import="abbott.ai.tcgm.process.ProcessScheduler" %>
<%! String pageTitle="Manage Daemon Processes";%>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Administrators%>
<abbott:securePage
    userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
    requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
    comparisonType=">="
	forwardPage="/insufficientPrivelage.do" />

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 <%@ include file="/include/masthead.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<div id="divToHide">
<html:form name="mngDaemonsForm" action="/mngDaemons" type="abbott.ai.tcgm.action.form.MngDaemonsForm" scope="request">
    <table width="729" border="0" cellpadding="2">
      <tr>
        <td width="50" height="0">&nbsp;</td>
        <td height="0" colspan="7" bgcolor="#b4d8f4" > <p class="tableHeading">Process Schduler</p></td>
      </tr>
      <tr> 
        <td width="50" height="0" class=right ></td>
        <td width="7" height="0" class=right ></td>
        <td width="130" height="0" class="cmdOptLbl">Status</td>
        <td width="73" height="0"><nested:write property="processSchedulerStatus" /></td>
        <td width="102" height="0">&nbsp;</td>
        <td colspan="2"><a href="javascript:changeCmdAndSubmit(document.forms[0], 'START_SCHEDULER')"><img src="images/btnStartScheduler.png"  border="0"></a></td>
        <td width="143">&nbsp;</td>
      </tr>
      <tr>
        <td width="50" height="0" class=right ></td>
        <td width="7" height="0" class=right ></td>
        <td height="0" class="cmdOptLbl">Current Process</td>
        <td height="0"><nested:write property="currentProcess" /> </td>
        <td height="0">&nbsp;</td>
        <td colspan="2"><a href="javascript:changeCmdAndSubmit(document.forms[0], 'STOP_SCHEDULER')"><img src="images/btnStopScheduler.png" border="0"></a></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="50">&nbsp;</td>
        <td colspan="8" bgcolor="#b4d8f4" ><p class="tableHeading">Datafeed Monitor</p></td>
      </tr>
      <tr>
        <td height="0" class=right ></td>
        <td height="0" class=right ></td>
        <td height="0" class="cmdOptLbl">Status</td>
        <td height="0"> <nested:write property="datafeedMonitorStatus" /></td>
        <td height="0">&nbsp;</td>
        <td colspan="2"><a href="javascript:changeCmdAndSubmit(document.forms[0], 'START_MONITOR')"><img src="images/btnStartMonitor.png"  border="0"></a></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="0" class=right ></td>
        <td height="0" class=right ></td>
        <td height="0" class="cmdOptLbl">Current Feed</td>
        <td height="0"> <nested:write property="currentFeed" /></td>
        <td height="0">&nbsp;</td>
        <td colspan="2"><a href="javascript:changeCmdAndSubmit(document.forms[0], 'STOP_MONITOR')"><img src="images/btnStopMonitor.png" border="0"></a></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="50" height="0">&nbsp;</td>
        <td height="0" colspan="7" bgcolor="#b4d8f4" > <p class="tableHeading">System Compact Adjustment</p></td>
      </tr>
      <tr> 
        <td width="50" height="0" class=right ></td>
        <td width="7" height="0" class=right ></td>
        <td width="450" colspan="3" height="0" class="cmdOptLbl">
        <% if(ProcessScheduler.BATCH_START==0){ %>
        The System Compact Process Will not Run Today.
        <%}else{ %>
        The System Compact Process runs at <%=ProcessScheduler.BATCH_START%>pm
        <%} %>
        </td>
        <!--  <td width="73" height="0"><nested:write property="processSchedulerStatus" /></td>
        <td width="102" height="0">&nbsp;</td>
        <td width="143">&nbsp;</td> -->
      </tr>
      <tr> <td> &nbsp; </td></tr>
      <tr> 
        <td width="50" height="0" class=right ></td>
        <td width="7" height="0" class=right ></td>
        <td colspan="3" height="0" class="cmdOptLbl">Adjust today's System Compact time by 
	        &nbsp;
	        <html:select property="compactTime" styleClass="commandOption">
				<html:option value="0">+0</html:option>
				<html:option value="1">+1</html:option>
				<html:option value="2">+2</html:option>
				<html:option value="3">+3</html:option>
				<html:option value="-1">Do not Run</html:option>
				</html:select>
			hrs
		</td>
		<td colspan="2"><a href="javascript:changeCmdAndSubmit(document.forms[0], 'APPLY_ADJUSTMENT')"><img src="images/applyAdjustment.png"  border="0"></a></td>
      </tr>
    </table>
<nested:hidden property="cmd" />
</html:form>
</div>
<script language=javascript>


</script>
<%@ include file="/include/footer.jsf" %>
