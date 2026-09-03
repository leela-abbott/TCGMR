<% String pageTitle="Report Menu"; %>

<%@ include file="/include/header.jsf" %>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<form name="frmMain" action="" method=post>
  <table width="500" border="0" cellpadding="6" cellspacing="0" >
    <tr>
      <td width=100>&nbsp;</td>
      <td width="200" align="center" class="tableHeading">Available Reports</td>
      <td colspan="2" width=200 align="center" class="tableHeading"><div align="center">Print
          &amp; Display Options </div></td>
    </tr>
    <tr>
      <td width="100" rowspan="4">&nbsp;</td>
      <td width="200" rowspan="4"> 
        <div align="center">
          <select name="reportList" size="10">
            <option value="asr_tree.rpt">ASR Tree, All</option>
            <option value="users.rpt">Users</option>
            <option value="group.rpt">Group</option>
            <option>Rate Exceptions Set</option> 
            <option>Audit Reports</option>
            <option>Split Factor</option>
            <option>Missing Factors</option>
            <option>Factor Summary</option>
          </select>
        </div></td>
      <td colspan="2" width=200 valign="bottom"><div align="center"><br>
          <img src="images/btnDisplay.png" onclick="javascript:viewReport(frmMain.reportList.value)" width="80" height="20"></div></td>
    </tr>
    <tr>
      <td height="40" width=200 colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td width="100" height="20" valign="top">
<div align="center">
          <p align="left">
            <input type="checkbox" name="checkbox" value="checkbox">
            <span class="smNormal">Immediate</span></p>
          <p align="left"> 
            <select name="select2">
              <option selected>Local</option>
              <option>Rmt143</option>
              <option>Rmt14</option>
            </select>
          </p>
        </div></td>
      <td width="100" valign="top"> <input type="checkbox" name="checkbox2" value="checkbox"> 
        <span class="smNormal">Restrict</span><br> <br> <input name="textfield" type="text" value="1" size="3" maxlength="3"> 
        &nbsp;<span class="smNormal">Copies</span></td>
    </tr>
    <tr>
      <td colspan="2" width=200><div align="center"><img src="images/btnPrint.png" width="80" height="20"></div></td>
    </tr>
  </table>
  <br>
  <p>&nbsp;</p>
</form>


<p>&nbsp;</p>
<%@ include file="/include/footer.jsf" %>

</body></html>
