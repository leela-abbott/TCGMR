<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Data Transfer and Report Publication";%> 
<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:init()" >
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<html:form name="dataTransfersForm" action="dataTransfers" type="abbott.ai.tcgm.action.form.DataTransfersForm" scope="session" enctype="multipart/form-data">
<bean:define id="unitSets" name="dataTransfersForm" property="unitSets" />
<bean:define id="savedSets" name="dataTransfersForm" property="savedSets" />
<nested:define id="monthListNumber" property="monthListNumber" />
  <div id="divToHide">
	<%@ include file="/include/jobOptions.jsf" %>
	<table class="tableCommand">
	  <tr>
		<td colspan="4"  class="tableHeading">Transfer Jobs</td>
	  </tr>
	  <tr>
		<td width="30" height="23" class=right ></td>
		<td width="623" colspan="3" align="left"  >
		  <table cellpadding=4 cellspacing=2> 
			<tr>
			<td><div class="commandOption" style="margin-right:80px;">Period:
				  <nested:select property="sendTreeToMVSPeriod"  styleClass="commandOption" >
				    <html:option value="13">13</html:option>
                    <html:options collection="monthListNumber" labelProperty="label" property="value" />
				  </nested:select> </td>
				  
				<td> &nbsp;</td>				  
			</tr>
			
			<tr>
			   <td width="325"  >
				<a href="javascript:confirmAndAddJob(document.forms[0], 'SEND_TREE_TO_MVS')"><img src="images/btnSendTreeToMVS.png" border=0></a>
			  </td>
 			  <td height="40"><a href="javascript:confirmAndAddJob(document.forms[0], 'SEND_PRICING')"><img src="images/btnSendPricingData.png" border=0></a></td>
			  <!-- 11-20-05 Filename parm added -->				
			  <td width="325" height="45" class="hidden">
				&nbsp;&nbsp;Tree File Name:&nbsp;&nbsp;<nested:text property="treeFilename" value="INBOUND1" maxlength="8" styleClass="hidden" />
			  </td>
			</tr>
<!-- 
			<tr>
    			<td width="325" height="40">
					<a href="javascript:confirmAndAddJob(document.forms[0], 'WRITE_T_PRODUCT_TRNS')"><img src="images/btnSendProductDatatoEssbase.png" border=0></a>
					
				</td>	
				<td width="325" >
					<a href="javascript:confirmAndAddJob(document.forms[0], 'WRITE_AFF_TRNS')"><img src="images/btnSendAffHierarchytoEssbase.png" border=0></a>
				</td>
			</tr>-->			
		  </table></td>
	  </tr>
	</table>
	<br>
	<table class="tableCommand">
	  <tr>
		<td colspan="4"  class="tableHeading">Report Publication</td>
	  </tr>
	  <tr>
		<td width="30" height="23" class=right ></td>
		<td width="660colspan="3" align="left"  >
		  <table cellpadding=4 cellspacing=2>
			<tr>
			  <td width="250">
			  <table>
			  	<tr >
			  		<td ><div class="ReportLabels">Factor Inquiry</div></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'GEN_FCT_RPT')"><img src="images/btnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'UNGEN_FCT_RPT')"><img src="images/btnUnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'PUB_FCT_RPT')"><img src="images/btnPublishNew.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'UNPUB_FCT_RPT')"><img src="images/btnUnPublish.png" border=0></a></td>
			  	</tr>
			  	<tr >
			  		<td ><div class="ReportLabels">Factor Report</div></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'GEN_FCT_REPORT')"><img src="images/btnGenerateReport.png" border=0></a></td>			  		
			  	</tr>
			  	<tr>
			  		<td ><div class="ReportLabels">Net Cost Inquiry</div></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'GEN_NET_COST')"><img src="images/btnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'UNGEN_NET_COST')"><img src="images/btnUnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'PUB_NET_COST')"><img src="images/btnPublishNew.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'UNPUB_NET_COST')"><img src="images/btnUnPublish.png" border=0></a></td>
			  	</tr>
			  	<tr>
			  		<td ><div class="ReportLabels">Net Cost Report</div></td>			  		
    		  		<td><a href="javascript:addJob(document.forms[0], 'GEN_NET_COST_RPT')"><img src="images/btnGenerateReport.png" border=0></a></td>
    		  		<td><a href="javascript:addJob(document.forms[0], 'NETCOST_RPT_PUB_PRE')"><img src="images/btnPublishReport.png" border=0></a></td>
			  	</tr>
			  	<tr>
			  		<td ><div class="ReportLabels">Factor Changes</div></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'GEN_FCT_CHGS_RPT')"><img src="images/btnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'PUB_FCT_CHGS')"><img src="images/btnPublishNew.png" border=0></a></td>
			  	</tr>
			  	<tr>
			  		<td ><div class="ReportLabels">Factor Summary</div></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'GEN_FACTR_SUMMRY')"><img src="images/btnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'PUB_FCT_SMRY')"><img src="images/btnPublishNew.png" border=0></a></td>
			  	</tr>
			  	<tr>
			  		<td ><div class="ReportLabels">TCGM Changes</div></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'GEN_TCGM_CHGS_RPT')"><img src="images/btnGenerate.png" border=0></a></td>
			  		<td><a href="javascript:addJob(document.forms[0], 'PUB_TCGM_CHGS')"><img src="images/btnPublishNew.png" border=0></a></td>
			  	</tr>
			  	<tr/>

			  </table>

			  </td>
			</tr>
		   </table>
		  </td>
		</tr>
      </table>	
     	<table class="tableCommand">
	  <tr>
		<td width="30" height="23" class=right ></td>
		<td width="623" colspan="3" align="left"  >
		  <table cellpadding=4 cellspacing=2> 
			  	<tr>			  		
			  		<td class="commandOptionLabel">Current Units:</td><td>
			  		    	<html:select property="currUnitsSelected" styleClass="commandOption" >
            				<html:options property="datasetTableId" labelProperty="datasetNameLogStamp"                                                  collection="unitSets" />
                            </html:select> 
                    </td>
			  	</tr>
			  	<tr>			  		
			  		<td class="commandOptionLabel">Saved Units:</td><td>
							<html:select property="savedUnitsSelected" styleClass="commandOption" >
            				<html:options property="datasetTableId" labelProperty="datasetNameLogStamp"                                                  collection="savedSets" />
                            </html:select> 
			  		</td>
				</tr>
		  </table></td>
	  </tr>
	<tr>
		<td colspan="4"  class="tableHeading">TPSS Upload</td>
	  </tr>
<tr>
		<td width="30" height="23" class=right ></td>
		<td width="660colspan="3" align="left"  >
		  <table cellpadding=4 cellspacing=2><tr><td><html:file property="theFile"  value=""/>
<a href="javascript:setUpload();" ><img src="images/btnUpload.png" alt="Filter" /></a></td></tr></table></td>
</tr>
	</table> 					
	</div>
</html:form>
<script language="JavaScript1.2" type="text/javascript">
			
function setUpload()
{
var flag=false;


		if(validateFile(document.forms[0].theFile.value)){
		     flag= true;
		}
		else{alert("Invalid File"); flag= false; }
    
if(flag){
javascript:chgActCmdSubmit(document.dataTransfersForm,'upload','dataTransfers.do');
}
 }

function validateFile(fileName){
	var dotPosition = fileName.indexOf(".");
	var alertMessage="";
	if(dotPosition > 0 ){
		if(!(fileName.substring(dotPosition+1)=='txt'||fileName.substring(dotPosition+1)=='TXT')){
			alertMessage=alertMessage+"Invalid File Name\n";
		}
	}else{
		alertMessage=alertMessage+"Invalid File\n";
	}
	if(alertMessage.length > 0){
		return false;
	}else {
		return true;
	}
}
	</script>
<%@ include file="/include/footer.jsf" %>
