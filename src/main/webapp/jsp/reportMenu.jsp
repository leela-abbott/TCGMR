<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 1. Pre-initialize pageTitle context BEFORE pulling layout headers --%>
<%! String pageTitle="Report Menu"; %>

<%-- 2. Pull down the document headers using your standard working static include --%>
<%@ include file="/include/header.jsf" %>

<head>
<style>
    /* Scoped styling to replace old table display formatting properties */
    .report-table {
        width: 500px;
        border: 0;
        border-collapse: collapse;
    }
    .report-table td {
        padding: 6px;
    }
    .text-center {
        text-align: center;
    }
    .text-left {
        text-align: left;
    }
    .valign-bottom {
        vertical-align: bottom;
    }
    .valign-top {
        vertical-align: top;
    }
</style>
</head>
<script>
function viewReport(sReportId)
{
	var sWindowParms;
	sWindowParms = "height=550,width=750,scrollbars=yes,resizable";
	newWindow = window.open("viewReport.action?reportId=" + sReportId, "TCGMReportViewer", sWindowParms);
	newWindow.focus();
}
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>


<s:form id="frmMain" name="frmMain" action="" method="post" theme="simple">
  <table class="report-table">
    <tr>
      <td style="width: 100px;">&nbsp;</td>
      <td style="width: 200px;" class="text-center tableHeading">Available Reports</td>
      <td colspan="2" style="width: 200px;" class="text-center tableHeading">
        Print &amp; Display Options
      </td>
    </tr>
    <tr>
      <td style="width: 100px;" rowspan="4">&nbsp;</td>
      <td style="width: 200px;" rowspan="4"> 
        <div class="text-center">
          <select name="reportList" size="10">
            <option value="asr_tree.rpt">ASR Tree, All</option>
            <option value="users.rpt">Users</option>
            <option value="group.rpt">Group</option>
            <option value="rate_exceptions.rpt">Rate Exceptions Set</option> 
            <option value="audit_reports.rpt">Audit Reports</option>
            <option value="split_factor.rpt">Split Factor</option>
            <option value="missing_factors.rpt">Missing Factors</option>
            <option value="factor_summary.rpt">Factor Summary</option>
          </select>
        </div>
      </td>
      <td colspan="2" class="text-center valign-bottom">
        <br>
        <%-- Updated execution script lookup syntax using explicit DOM element parsing safety --%>
        <img src="images/btnDisplay.png" 
             onclick="javascript:viewReport(document.getElementById('frmMain').reportList.value)" 
             style="width: 80px; height: 20px; cursor: pointer;" 
             alt="Display Report">
      </td>
    </tr>
    <tr>
      <td style="height: 40px; width: 200px;" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td style="width: 100px; height: 20px;" class="valign-top">
        <div class="text-center">
          <p class="text-left">
            <input type="checkbox" name="checkbox" value="checkbox" id="chkImmediate">
            <label for="chkImmediate" class="smNormal">Immediate</label>
          </p>
          <p class="text-left"> 
            <select name="select2">
              <option selected="selected">Local</option>
              <option>Rmt143</option>
              <option>Rmt14</option>
            </select>
          </p>
        </div>
      </td>
      <td style="width: 100px;" class="valign-top"> 
        <input type="checkbox" name="checkbox2" value="checkbox" id="chkRestrict"> 
        <label for="chkRestrict" class="smNormal">Restrict</label>
        <br><br> 
        <input name="textfield" type="text" value="1" size="3" maxlength="3"> 
        &nbsp;<span class="smNormal">Copies</span>
      </td>
    </tr>
    <tr>
      <td colspan="2" style="width: 200px;" class="text-center">
        <img src="images/btnPrint.png" style="width: 80px; height: 20px; cursor: pointer;" alt="Print Report">
      </td>
    </tr>
  </table>
</s:form>

<%-- 6. Clean application lifecycle exit --%>
<%@ include file="/include/footer.jsf" %>
</body>
</html>
