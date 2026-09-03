<!-- Although we are using the Model framework and defining Cost Exchange as a Model, 
     we call it a Dataset to avoid confusing the user. -->  

<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Margin Billed Exchange Flex";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<%@ include file="/include/masthead1.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<div id="divToHide">
<html:form  name="mngCostExchModelsForm" action="mngCostExchModels" type="abbott.ai.tcgm.action.form.MngCostExchModelsForm" scope="session">
<nested:define id="yearList" property="yearList" />
<%@ include file="/include/jobOptions.jsf" %>
<nested:text property="showCostModel" maxlength="8" styleClass="hidden" />
<TABLE style="margin-left:50px;text-indent: 3px;width: 675;border:0px solid;cell-padding:0;cell-spacing:0;">

		<TR>
<c:if test="${mngCostExchModelsForm.showCostModel =='open'}" >
			<TD valign="top">
<fieldset style="width:70px;"><legend class="commandOptionLabel"></legend>
  <table style="width: 675;border:0px solid;cell-padding:0;cell-spacing:0;" >
	<tr>


	  <td colspan="3" class="tableHeading" >Margin Billed Exchange Flex</td>
      <td class="tableHeading" >Dataset Details</td>
    </tr> 
	<tr>
	  <td width="20">&nbsp;</td>
      <td width="104"> 
		  <bean:define id="costExchModels" name="mngCostExchModelsForm" property="models" />
		  <html:select size="6" property="modelSelected" onclick="updateDetails()" >
			<html:options property="modelId" labelProperty="name" collection="costExchModels" />
		  </html:select>
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' >Show Closed Model</a>
			</abbott:securePage>

		
		</td>
      <td width="156">
	  	<p><a href=javascript:deleteModel(document.mngCostExchModelsForm.modelSelected)><img src="images/btnDeleteDataset.png"  border=0></a></p>
		<p><a href=javascript:closeModel(document.mngCostExchModelsForm.modelSelected)><img src="images/btnCloseModel.png"  border=0></a></p>
        <p><a href="createCostExchModel.do"><img src="images/btnCreateNewDataset.png"  border="0"></a></p>
        <p><a href="javascript:addJob(document.forms[0], 'CXCHG_FLEX')"><img src="images/btnFlexExchRates.png" border="0"></a>
		<p><a href="javascript:addJob(document.forms[0], 'CXC_ANL04E')"><img src="images/btnCalcCurrExposure.png" border="0"></a>
		</td>

      <td ><div id="divModelDetail" class="dataDisp"></div></td>
    </tr>
  </table>
  </fieldset>
</TD>
</c:if>
<c:if test="${mngCostExchModelsForm.showCostModel =='closed'}" >
<TD valign="top">
<fieldset style="width:70px;"><legend class="commandOptionLabel"></legend>
  <table style="width: 675;border:0px solid;cell-padding:0;cell-spacing:0;" >
	<tr>
<td colspan="3" class="tableHeading" >Closed Margin Billed Exchange Flex</td>
      <td class="tableHeading" >Dataset Details</td>
    </tr> 
	<tr>
	  <td width="20">&nbsp;</td>
      <td width="104"> 
		  <bean:define id="costExchModels" name="mngCostExchModelsForm" property="models" />
		  <html:select size="6" property="modelSelected" onclick="updateDetails()" >
			<html:options property="modelId" labelProperty="name" collection="costExchModels" />
		  </html:select>
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("open")' >Show Open Model</a>
			</abbott:securePage>

		
		</td>
      <td width="156" valign="top">
	  	<p><a href=javascript:openModel(document.mngCostExchModelsForm.modelSelected)><img src="images/btnOpenModel.png"  border=0></a></p>        
		</td>

      <td ><div id="divModelDetail" class="dataDisp"></div></td>
    </tr>
  </table>
  </fieldset>
</TD>
</c:if>
</TR>
</TABLE>

  <table class="tableCommand">
	
	<tr valign="top">	    
      <td >
      <fieldset style="width:70px;"><legend class="commandOptionLabel"></legend>
	  <table width="175">
	  <tr>
	  <td colspan="2" class="tableHeading">Reports</td>  	      
   	 </tr>
          <tr>
	  	    <td colspan="1">
	  			<a href="javascript:addJob(document.forms[0], 'CXCHG_CSTSL1')"><img src="images/btnDetailByRptAff.png" border="0"></a>
	  		 	<nested:select property="selDtlMetric" styleClass="commandOption" >
                	<option>Cost</option>
                	<option>Sales</option>
              </nested:select>
              <!-- <br><a href="javascript:addJob(document.forms[0], 'CXCHG_CSTSL2')"><img src="images/btnDetailBySupAff.png" border="0"></a> -->
			</td>
		  </tr>
			
          <tr>
	   		<td>
				<a href="javascript:addJob(document.forms[0], 'CXCHG_CSMP2')"><img src="images/btnCSMByALG.png" border="0"></a>
			</td>
          </tr>
       	  
     </table>
     </fieldset>
     </td>
	 
<!-- 
			<td>
			<fieldset style="width:100px;"><legend class="commandOptionLabel"></legend>			
		  		<table width="490">
		  		<tr>
	  
      <td colspan="3" class="tableHeading">Essbase Exports</td>    </tr>
		  			<tr>

						<td width="200">
							<a href="javascript:confirmAndAddJob(document.forms[0], 'SEND_ANL_FLEX_ESS')"><img src="images/btnSendToEssbase.png" border="0"></a>
						</td>
		  			</tr>
		  			<tr>
						<td class="commandOptionLabel">
							Version&nbsp;&nbsp;&nbsp;Year&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type&nbsp;&nbsp;&nbsp;&nbsp;Start Period&nbsp;&nbsp;&nbsp;&nbsp;End Period
						<br>
							<html:select property="essbaseVersion" styleClass="commandOption" >
								<option>01</option>
								<option>02</option>
								<option>03</option>
							</html:select> 
		           			<nested:select property="essbaseYear" styleClass="commandOption">
                    			<html:options collection="yearList" labelProperty="label" property="value" />
                  			</nested:select>
							<html:select property="essbaseType" styleClass="commandOption" >
				  				<option values="F1">F1</option>
				  				<option values="F2">F2</option>
							</html:select>&nbsp;&nbsp;
							<html:select property="essbaseStPeriod" styleClass="commandOption" onchange="changeSelect();" styleId="essbaseStPeriod">
				  				<option value="ALL" >ALL</option>
				  				<option value="1">1</option>
				  				<option value="2">2</option>
				  				<option value="3">3</option>
				  				<option value="4">4</option>
				  				<option value="5">5</option>
				  				<option value="6">6</option>
				  				<option value="7">7</option>
				  				<option value="8">8</option>
				  				<option value="9">9</option>
				  				<option value="10">10</option>
				  				<option value="11">11</option>
				  				<option value="12">12</option>
							</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:select property="essbaseEndPeriod" styleClass="commandOption" styleId="essbaseEndPeriod">
				  				<option value="ALL" >ALL</option>				  				
							</html:select>							
						</td>
		  			</tr>
				</table>
				</fieldset>
			</td>-->
	 
      <td width="20" > </td>
      <td width="116" >&nbsp;</td>
      <td width="177" >&nbsp;</td>
    </tr>
  </table>
</html:form></div>
<br>

<p>&nbsp;</p>

<script language=javascript>
	function deleteModel(se) {
		if (se.value == 0) {
			alert ("You must select a model to delete.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		if ( confirm("Are you sure that you would like to delete " + modelName + "?\nThis will delete all attributes and can not be undone.") ) {
			changeActionAndSubmit(se.form, "deleteCostExchModel.do");
		}
	}
function closeModel(se) {
		if (se.value == 0) {
			alert ("You must select a model to close.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		if ( confirm("Are you sure that you would like to close " + modelName + "?") ) {
			changeActionAndSubmit(se.form, "closeCostExchModel.do");
		}
	}
function openModel(se)
	 {
		if (se.value == 0) {
			alert ("You must select a model to open.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		 if ( confirm("Are you sure that you would like to open " + modelName + "?") ) {
			 //document.forms.mngCostExchModelsForm.modelSelected.value=modelid;
			 //document.forms.mngCostExchModelsForm.showModels.value = 'closed';
			 changeActionAndSubmit(document.forms.mngCostExchModelsForm, "openCostModel.do")
		 }
	}
	function updateDetails() {
		document.body.style.cursor = 'wait';
			processActiveURL('tcgmAjax.do?cascadingCmd=ModelInfo&cascadingVal='+document.mngCostExchModelsForm.modelSelected.value+'&cascadingSecVal=COSTEXCH','divModelDetail');
			document.body.style.cursor = "default";
			
			var x=''+document.getElementById('divModelDetail').innerHTML;
			var stPeriodData=x.substring(x.indexOf('<B>Start Period:</B>')+20);
			var endPeriodData=x.substring(x.indexOf('<B>End Period:</B>')+18);
			var stPeriod=parseInt(trim(stPeriodData.substring(0,stPeriodData.indexOf(','))));
			var endPeriod=parseInt(trim(endPeriodData.substring(0,endPeriodData.indexOf(','))));			
			var strt=document.forms[0].essbaseStPeriod;
			var endt=document.forms[0].essbaseEndPeriod;
			removeAllOptions(strt);
			removeAllOptions(endt);
			addOption(endt,'ALL','ALL');
			addOption(strt,'ALL','ALL');
			for(i=stPeriod;i<=endPeriod;i++){
	   			addOption(strt,''+i,''+i);
			}			

		}
function addOption(selectbox,text,value)
{
	var optn = document.createElement("OPTION");
	optn.text = trim(text);
	optn.value = trim(value);
	selectbox.options.add(optn);
}
function removeAllOptions(selectbox)
{
	var i;
	for(i=selectbox.options.length-1;i>=0;i--)
	{
		selectbox.remove(i);
	}
}
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function changeSelect(){

var strt=document.forms[0].essbaseStPeriod;
var endt=document.forms[0].essbaseEndPeriod;

			var x=''+document.getElementById('divModelDetail').innerHTML;			
			var endPeriodData=x.substring(x.indexOf('<B>End Period:</B>')+18);
			var endPeriod=parseInt(trim(endPeriodData.substring(0,endPeriodData.indexOf(','))));	


var i;
removeAllOptions(endt);
if(strt.value=='ALL'){	
	addOption(endt,'ALL','ALL');
}else{
	for(i=strt.value;i<=endPeriod;i++){
	
	   addOption(endt,''+i,''+i);
	  
	}
}

}		
function showClosedModelDetails(stat) {
		document.forms.mngCostExchModelsForm.showCostModel.value = stat;
		document.mngCostExchModelsForm.action = 'mngCostExchModels.do';
		document.mngCostExchModelsForm.submit();
		}	
</script>

<%@ include file="/include/footer.jsf" %>
