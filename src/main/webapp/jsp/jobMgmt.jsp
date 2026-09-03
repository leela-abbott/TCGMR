<% String pageTitle="Job Q Management"; %>

<%@ include file="/include/header.jsf" %>
<jsp:useBean id="processForm" scope="request" class="abbott.ai.tcgm.action.form.ProcessForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<html:form name="processForm" action=""  scope="request" type="abbott.ai.tcgm.action.form.ProcessForm">
  <div id="divToHide">
  <table width="700" border="0" cellpadding="6" cellspacing="0">
	<tr>
	  <td width=20 rowspan="6">&nbsp;</td>
	  <td width="500" align="center" class="tableHeading">Current Job&nbsp;&nbsp;<font face="Arial" size=2 color=gray>(Job Name | Model Name | Start Time | User Name)</font></td>
	  <td width="180" align="center" class="tableHeading">Scheduler Status</td>
	</tr>
	<tr>
	  <td align="center">
	   <logic:equal name="processForm" property="processSchedulerStatus" value="Running">
		    <b><bean:write name="processForm" property="currentProcess" /></b>
	   </logic:equal>
	</td>
	  <td align="center"> 
		 <b><bean:write name="processForm" property="processSchedulerStatus" /></b>
	</td>
	</tr>
	<tr>
	  <td class="tableHeading" align="center" >Pending Jobs&nbsp;&nbsp;<font face="Arial" size=2 color=gray>(Job Name | Model Name | Status | Submit Time | User Name)</font></td><td class="tableHeading">&nbsp;</td>
	</tr>
	<tr>
	  <td width="261" >
		<div align="center">
		  <logic:greaterThan name="processForm" property="processListSize" value="0">
			<select name="selPendingJob" size="10">
			  <logic:iterate id="processes" name="processForm" property="processList" scope="request" type="abbott.ai.tcgm.process.JobInstance">
				<option value='<bean:write name="processes" property="jobQueId" ignore="true" />' >

				<bean:write name="processes" property="desc" ignore="true" />
				&nbsp;|&nbsp;
				<bean:write name="processes" property="jobStatus.name" ignore="true" />
				&nbsp;|&nbsp;
				<bean:write name="processes" property="submitTime" ignore="true" />				
				&nbsp;|&nbsp;
				<bean:write name="processes" property="userSubmitted" ignore="true" />
				</option>
			</logic:iterate>
			</select>
	 </logic:greaterThan>
		  <logic:equal name="processForm" property="processListSize" value="0">There are no pending jobs</logic:equal>
		</div></td>
	  <td height="20"><div align="left">
		  <a href='javascript:changeJobStatus(processForm.selPendingJob, "I")'><img border="0" height="20" src="images/btnImmediate.png" width="80"></a>
		  <a href='javascript:changeJobStatus(processForm.selPendingJob, "B")'><img border="0" height="20" src="images/btnBatch.png" width="80"></a>
		  <a href='javascript:reorderJobQue(processForm.selPendingJob, "<%=1-processForm.getProcessListSize()%>")'><img src="images/btnMoveToTop.png" height="20"></a><br>
		  <a href='javascript:reorderJobQue(processForm.selPendingJob, -1)'><img src="images/btnMoveUp.png" width="80" height="20"></a><br>
    	  <a href='javascript:reorderJobQue(processForm.selPendingJob, 1)'><img src="images/btnMoveDown.png" width="80" height="20"></a>
	      <a href='javascript:reorderJobQue(processForm.selPendingJob, "<%=processForm.getProcessListSize()-1%>")'><img src="images/btnMoveToBottom.png" height="20"></a>
		  <a href='javascript:deleteJob(processForm.selPendingJob)'> <img border="0" height="20" src="images/btnDelete.png" width="80"></a>
		</div></td>
	</tr>
	<tr>
	  <td class="tableHeading" align="center" >Completed Jobs&nbsp;&nbsp;<font face="Arial" size=2 color=gray>(Job Name | Model Name | Status | End Time | User Name)</font></td><td class="tableHeading">&nbsp;</td>

	</tr>
	<tr >
	  <td><div align="left">
		  <logic:greaterThan name="processForm" property="completedJobListSize" value="0">
			<select name="selFailedJob" size="10">
			  <logic:iterate id="processes" name="processForm" property="completedJobList" scope="request" type="abbott.ai.tcgm.process.JobInstance">
				<option value='<bean:write name="processes" property="jobQueId" ignore="true" />' >
				<bean:write name="processes" property="desc" ignore="true" />
				&nbsp;|&nbsp;
				<bean:write name="processes" property="jobStatus.name" ignore="true" />
				&nbsp;|&nbsp; 
				<bean:write name="processes" property="endTime" ignore="true" />
				&nbsp;|&nbsp;
				<bean:write name="processes" property="userSubmitted" ignore="true" />			
				</option>
			</logic:iterate>
			</select>
	 </logic:greaterThan>
		  <logic:equal name="processForm" property="completedJobListSize" value="0">There are no failed jobs</logic:equal>
		</div></td>
	  <td align="left" valign="top">

		<!-- <p><a href='javascript:changeJobStatus(processForm.selFailedJob, "B")'><img src="images/btnRetryJob.png" width="80" height="20" border=0></a></p> -->
		<p> <a href='javascript:logWindow(document.processForm, "logReport.do",processForm.selPendingJob,processForm.selFailedJob )'><img src="images/btnViewLog.png" width="80" height="20" border="0"></a>  
		<a href='javascript:generateReports(processForm.selFailedJob, "PRINT_REPORTS")'><img src="images/btnGenerateReports.png" height="20" border=0></a></p>
		<br><br>		<br><br>
		<p><a href='javascript:deleteJob(processForm.selFailedJob)'><img src="images/btnDelete.png" width="80" height="20" border="0"></a></p>

		</td>
	</tr>
  </table>
  </div>
  <html:hidden property="selProcessId" /><html:hidden property="cmd" />
   <html:hidden property="selJodDesc" /><html:hidden property="selJodStatus" />
  </html:form>

<script language="javascript">
function deleteJob(selectList) {

	if (selectList.value == 0) {
		alert ("You must select a job to delete.");
		return;
	}
	var jobName = getSelectedOptionText(selectList);
	if ( confirm("Are you sure that you would like to delete " + jobName + "?\nThis can not be undone.") ) {
		document.processForm.selProcessId.value = selectList.value;
		changeActionAndSubmit(document.processForm, "deleteJob.do");
		}
}

function changeJobStatus(selectList, sCode) {

	if (selectList.value == 0) {
		alert ("You must first select a job, then change its status.");
		return;
	}
	
	document.processForm.selProcessId.value = selectList.value;
	document.processForm.cmd.value=sCode;
	changeActionAndSubmit(document.processForm, "changeJobStatus.do");
}

function printReports(selectList, sCode) {

	if (selectList.value == 0) {
		alert ("You must first select a job, then change its status.");
		return;
	}
	
	document.processForm.selProcessId.value = selectList.value;
	document.processForm.cmd.value=sCode;
	changeActionAndSubmit(document.processForm, "changeJobStatus.do");
}

function generateReports(selectList, sCode) {

	if (selectList.value == 0) {
		alert ("You must first select a job, then change its status.");
		return;
	}
	
	document.processForm.selProcessId.value = selectList.value;
	document.processForm.cmd.value=sCode;
	//changeActionAndSubmit(document.processForm, "changeJobStatus.do");
	window.open('generateReport.do?selProcessId='+document.processForm.selProcessId.value+'&cmd='+document.processForm.cmd.value,"_left","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=yes, width=600, height=300,left = 0,top =100"); 
	
}

function reorderJobQue(selectList, offset) {
	document.processForm.selProcessId.value = selectList.value;
	document.processForm.cmd.value=offset;
	changeActionAndSubmit(document.processForm, "reorderJobQue.do");
}

function logWindow(form,action,selectList1,selectList2) {

   if (selectList1!=null && selectList2!=null){
	  if (  selectList1.value == 0 && selectList2.value==0) {
    		alert ("You must first select a job to view the log.");
			return;
		}
		if (selectList1.value != 0){
		document.processForm.selProcessId.value = selectList1.value;
		}
	}
	
	else if (selectList2!=null){
		if (selectList2.value==0) {
    		alert ("You must first select a job to view the log.");
			return;
		}
	}
	
	if(selectList2.value != 0){
		document.processForm.selProcessId.value = selectList2.value;
	} 
	
	var selectedItemNum=selectList2.selectedIndex;
	var selectedItemTxt=selectList2.options[selectList2.selectedIndex].text;
	var comaIdx=selectedItemTxt.indexOf(",");
	var dashIdx=selectedItemTxt.indexOf(":");
	var jbDesc=selectedItemTxt.substr(0,comaIdx);
	var jbSts=selectedItemTxt.substring(comaIdx+1,dashIdx-17);
	
	document.processForm.selJodDesc.value = jbDesc;
	document.processForm.selJodStatus.value = jbSts;
	
	
	window.open('errorReport.do?selProcessId='+document.processForm.selProcessId.value+'&selJodDesc='+document.processForm.selJodDesc.value+'&selJodStatus='+document.processForm.selJodStatus.value,"_left","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=900, height=530,left = 0,top =100"); 
	 	   	
}   

</script>
<%@ include file="/include/footer.jsf" %>  