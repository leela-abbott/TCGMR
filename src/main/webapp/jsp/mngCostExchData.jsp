<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Manage Margin Billed Exch Flex Data";%>
<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<nested:form method="post" name="mngCostExchDataForm" type="abbott.ai.tcgm.action.form.MngCostExchDataForm" action="/mngCostExchData.do" scope="session">  
<TABLE class="tableCommand">

		<TR>
			<TD valign="top">
<fieldset style="width:70px;"><legend class="commandOptionLabel"></legend>
<table style="width: 675;border:0px solid;cell-padding:0;cell-spacing:0;">
  <tr> 
    <td colspan="3" class="tableHeading">Current RGM Exchange Datasets</td>
    <td colspan="2" class="tableHeading" >Fetch New RGM Set</td>
  </tr>
  <tr valign="top"> 
    <td width="20">&nbsp;</td>
    <td width="70" > 
	<bean:define id="datasets" name="mngCostExchDataForm" property="datasets" /> 
      <html:select property="selDataset" size="10" > <html:options property="datasetTableId" labelProperty="datasetName" collection="datasets" /> 
      </html:select>
	  </td> 
    <td width="187" > <a href="javascript:deleteDataset(document.mngCostExchDataForm.selDataset, 'deleteCostExchData.do')"> 
      <img border="0" height="20" src="images/btnDelete.png" width="80"></a> </td>
    <td width="20" >&nbsp;</td>
    <td width="219" >
	  <html:text property="createName" onkeydown="if(event.keyCode == 32){return false;}"/><br>
      <a href="javascript:fetchDupCheck()"><img border="0" height="20" src="images/btnFetch.png" width="80"></a></td>
  </tr>
</table>
</fieldset>
</TD>
</TR>
</TABLE>
</nested:form>
<script language=javascript>
    function fetchDupCheck() {
        var frm = document.forms[0];
        var name = frm.createName.value;
        if (optionExists(frm.selDataset, name))
            alert("There is already a set named " + name + ". You will need to delete the existing set.");
        else
            changeActionAndSubmit(frm, 'fetchCostExchData.do');

    }
</script>
<%@ include file="/include/footer.jsf" %>