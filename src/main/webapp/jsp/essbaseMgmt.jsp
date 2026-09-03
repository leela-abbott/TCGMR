<%! String pageTitle="Essbase Management";%>
<%@ include file="/include/header.jsf" %>

<%//Page is only accessible by Administrators%>
<abbott:securePage    userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>"
    requiredAccessLevel="<%=Role.Operator.getAccessLevel()%>"
    comparisonType=">"
	forwardPage="/insufficientPrivelage.do" />

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
    <html:form  name="essbaseMgmtForm" action="initiateEssbase" type="abbott.ai.tcgm.action.form.EssbaseMgmtForm">


  <table width="735" border="0" cellpadding="2" cellspacing="2">
    <tr>
          <td width="66">&nbsp;</td>
          <td colspan="8" bgcolor="#b4d8f4" ><p class="tableHeading">RGM Loads</p></td>
        </tr>

    <tr valign="middle"> 
      <td height="23" colspan=2 class=right ></td>
 	 <td width=75>
        Year<br>
              <input type="text" name="txtRGMYear" size=4 maxlength="4">
          </td>
      <td width=110>Period<br>
           <nested:define id="monthList" property="twoDigitMonthListNumberWithLabel" scope="request" />
           <html:select name="essbaseMgmtForm" property="selRGMMonth" >
           <html:options collection="monthList" property="value"  labelProperty="label" />
           <html:option value="PY">PY</html:option>
          </html:select></td>
    

      <td width=85>Version<br> <select name="selRGMVersion">
              <option>01</option>
              <option>02</option>
              <option>03</option>
            </select> </td>

     <!-- <td><a href="javascript:changeCmdAndSubmit(document.essbaseMgmtForm, 'RGM')"><img src="images/btnInitiate.png" border="0"></a></td> -->
     <td colspan=2><a href="javascript:changeCmdAndSubmit(document.essbaseMgmtForm, 'RGMALL')"><img src="images/btnExtractLoadAll.png" border="0"></a>
     <a href="javascript:changeCmdAndSubmit(document.essbaseMgmtForm, 'RGMPPVSRV')"><img src="images/btnExtractLoadPPV.png" border="0"></a>
     </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="8" bgcolor="#b4d8f4" ><p class="tableHeading">RTC Loads</p></td>
        </tr>

    <tr valign="middle">
      <td colspan="2"> </td>

      <td >Year<br>
        <input type="text" name="txtRTCYear" size=4 maxlength="4">
          </td>

      <td>Version<br> <select name="selRTCVersion">
		 <!-- <option>V1</option>
              <option>V2</option>
              <option>V3</option> -->
              <option>01</option>
              <option>02</option>
              <option>03</option>
            </select></td>
            
      <td >Load Type<br> <select name="selRTCLoadType"> <!--selRTCLoadType -->
              <option>CY</option>
              <option>PY</option>
            </select></td>

	  <td >Source File<br> <select name="selRTCSourceFile"> <!--selRTCSourceFile -->
              <option>Current</option>
              <option>Backup</option>
            </select></td>
	  <td >Generation<br> <select name="selRTCGeneration"> <!--selRTCGeneration -->
              <option>0</option>
              <option>-1</option>
              <option>-2</option>
              <option>-3</option>
              <option>-4</option>
              <option>-5</option>
              <option>-6</option>
              <option>-7</option>
              <option>-8</option>
              <option>-9</option>
            </select></td>            


      <td ><a href="javascript:changeCmdAndSubmit(document.essbaseMgmtForm, 'RTC')"><img src="images/btnInitiate.png" border="0"></a>
      </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="8" bgcolor="#b4d8f4" ><p class="tableHeading">Version Copy</p></td>
        </tr>
    <tr valign="middle">
      <td colspan="2"> </td>

      <td  >Year<br>
        <input type="text" name="txtVCopyYear" size=4 maxlength="4">
          </td>

      <td >From Version<br> <select name="selFVCopyVersion"> <!--selFVCopyVersion -->
              <option value=01>01</option>
              <option value=02>02</option>
              <option value=03>03</option>
            </select></td>

        <td >To Version<br> <select name="selTVCopyVersion"> <!--selTVCopyVersion -->
              <option value=01>01</option>
              <option value=02>02</option>
              <option value=03>03</option>
            </select></td>

      <td ><a href="javascript:confirmVersionCopy(document.essbaseMgmtForm, 'VCOPY')"><img src="images/btnInitiate.png" border="0"></a>
      </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="8" bgcolor="#b4d8f4" ><p class="tableHeading">Load PPV Exchange</p></td>
        </tr>
    <tr valign="middle">
      <td colspan="2"> </td>
       <td >Year<br>
        <input type="text" name="txtALOGYear" size=4 maxlength="4">
          </td>
      <!-- <td>Period<br>
           <nested:define id="monthList" property="twoDigitMonthListNumberWithLabel" scope="request" />
           <html:select name="essbaseMgmtForm" property="selALOGMonth" >
           <html:options collection="monthList" property="value"  labelProperty="label" />
           <html:option value="13">PY</html:option>
          </html:select></td> -->
     
      <td >Version<br> <select name="selALOGVersion">
              <option>01</option>
              <option>02</option>
              <option>03</option>
            </select></td>

 		<!-- <td >Load Type<br> <select name="selALOGLoadType"> 
              <option>CY</option>
              <option>PY</option>
            </select></td> -->

      <td ><a href="javascript:changeCmdAndSubmit(document.essbaseMgmtForm, 'ALOG')"><img src="images/btnInitiate.png" border="0"></a>
      </td>
        </tr>

      </table>
      <html:hidden property="cmd" />
</html:form>
<br>

<p>&nbsp;</p>

<script language=javascript>

function confirmVersionCopy(form, cmd){
	if ( confirm("Do you really want to copy from version "+form.selFVCopyVersion.options[form.selFVCopyVersion.selectedIndex].text+" to version "+form.selTVCopyVersion.options[form.selTVCopyVersion.selectedIndex].text+ " ?") ) 
        {
			changeCmdAndSubmit(form, cmd)
		}
}

</script>

<%@ include file="/include/footer.jsf" %>
