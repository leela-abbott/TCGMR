<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Create Margin Billed Exchange Flex";%>


<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:init()">
 <%@ include file="/include/masthead1.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>

<!-- Although we are using the Model framework and defining Cost Exchange as a Model, 
     we call it a Dataset to avoid confusing the user. -->  

<div id="divToHide">
<html:form name="createCostExchModelForm" action="createCostExchModel" type="abbott.ai.tcgm.action.form.CreateCostExchModelForm" >
<nested:define id="monthListNumber" property="monthListNumber" />
<nested:define id="yearList" property="yearList" />
    <table class="tableCommand">
      <tr>
        <td colspan="5"  class="tableHeading">Create New Margin Billed Exchange Flex Dataset</td>
      </tr>
      <tr> 
        <td width="20" height="0" class=right ></td>
        <td width="125" nowrap class="commandOptionLabel" >Dataset Name</td>
        <td colspan="3"><html:text property="modelName" name="createCostExchModelForm" size="30" styleClass="commandOption" maxlength="30"/></td>
      </tr>
      <tr> 
        <td width="20" height="0" class=right ></td>
        <td width="125" nowrap class="commandOptionLabel" >Dataset Desc.</td>
        <td colspan="3"><html:text property="modelDesc" name="createCostExchModelForm" size="65" styleClass="commandOption" maxlength="80"/></td>
      </tr>
      <tr> 
        <td width="20" height="0" class=right ></td>
        <td width="125" nowrap class="commandOptionLabel" >Rate Set</td>
        <bean:define id="rateSets"  name="createCostExchModelForm" property="rateSets" />
        <bean:define id="salesData"  name="createCostExchModelForm" property="salesData" />
        <bean:define id="unitSets"  name="createCostExchModelForm" property="unitSets" />
      	  
        <td width="100"> 
          <div align="left">
            <html:select property="selRateSet" name="createCostExchModelForm" styleClass="commandOption">
              <html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
            </html:select>
          </div></td>
        <td width="129" align="right" nowrap class="commandOptionLabel" ><div align="right">Starting D.5.6 Period<br>
             
      </div></td>
        <td width="158" nowrap >
	   	<div align="left">
            <nested:select property="selStartPeriod" styleClass="commandOption" >
              <html:options collection="monthListNumber" labelProperty="label" property="value" />
            </nested:select>
		    <nested:select property="selStartYear" styleClass="commandOption" >
              <html:options collection="yearList" labelProperty="label" property="value" />
        </nested:select>
		</div></td>
      </tr>
      <tr> 
        <td width="20" height="0" class=right ></td>
        <td width="125"  nowrap  class="commandOptionLabel" >Starting RGM Ver.</td>
        <td width="100">
			<div align="left">
            <html:select property="selStartingSalesData" name="createCostExchModelForm" styleClass="commandOptionLabel" >
              <html:options property="datasetTableId" labelProperty="datasetName" collection="salesData" />
            </html:select>
          </div></td>
        <td width="129" align="right" nowrap  class="commandOptionLabel" ><div align="right">Ending D.5.6 Period</div></td>
        <td width="158" nowrap>
	   	<div align="left">
            <html:select property="selEndPeriod" name="createCostExchModelForm" styleClass="commandOption" >
              <html:options collection="monthListNumber" labelProperty="label" property="value" />
            </html:select>
		    <html:select property="selEndYear" name="createCostExchModelForm" styleClass="commandOption" >
              <html:options collection="yearList" labelProperty="label" property="value" />
            </html:select>
		</div></td>
      </tr>
      <tr> 
        <td width="20"  ></td>
        <td width="125" nowrap class="commandOptionLabel" >Use Ending RGM Ver.</td>
        <td width="100" >
		<div align="left">
            <html:select property="selEndingSalesData"  name="createCostExchModelForm" styleClass="commandOption" >
              <html:option value="">--none--</html:option>
              <html:options property="datasetTableId" labelProperty="datasetName" collection="salesData" />
            </html:select>
          </div></td>
 		    <!-- Udaya B Aravapalli - 02/01/06 - added Factor Models -->
        <td width="129" align="right" nowrap  class="commandOptionLabel" ><div align="right">Factor Model</div></td>
        <td width="158" nowrap>
	   	<div align="left">
						<nested:define id="factorModels" property="models" />
            <html:select property="modelSelected" name="createCostExchModelForm" styleClass="commandOption" >
              <html:option value="">--none--</html:option>
     					<html:options property="modelId" labelProperty="name" collection="factorModels" />
            </html:select>
          </div></td>
          
        <td width="129" align="right" nowrap>&nbsp;</td>
        <td width="158" nowrap><div align="left"></div></td>
      </tr>
      <tr>
        <td width="20" height="0" class=right ></td>
        <td width="125"  nowrap class="commandOptionLabel" >
			 Units
		</td>
        <td width="100" align="left" > 
          <div align="left">
            <html:select property="selCostExchUnits" name="createCostExchModelForm" styleClass="commandOption"  >
              <html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
            </html:select>
          </div></td>
      </tr>
		    <!-- Udaya B Aravapalli - 01/10/06 - added textarea for memo -->
			<tr>
			  <td width="7" height="0" class=right ></td>
			  <td  class="commandOptionLabel"> Memo: </td>
			  <td colspan="3"  width="242" >
			      <html:textarea property="memo" name="createCostExchModelForm" cols="68" rows="7" onkeyup="return checkLength(this, 425);" styleClass="commandOption" />
			  </td>
			  </tr> 
		    <tr>
		      <td class=right ></td>
		      <td class="commandOptionLabel">&nbsp;</td>
		      <td>&nbsp;</td>
		      <td class="commandOptionLabel">&nbsp;</td>
		      <td width="158" nowrap><div align="left"><a href="javascript:validateFormData(document.forms[0], 'CREATE_MODEL');"><img border="0" height="20" src="images/btnCreateDataset.png" width="100"></a></div></td>
		    </tr>
		    <!-- Udaya B Aravapalli - 01/10/06 - added textarea for memo -->      
    </table>
   <html:hidden property="cmd" value="CREATE_MODEL" />
</html:form>
</div>

<script language="JavaScript" type="text/JavaScript">
 function init() {
     document.forms[0].selEndingInventory.disabled=true;
    }

 function validateEndingCBO() {
     document.forms[0].selEndingInventory.disabled=!document.forms[0].selEndingInventory.disabled;
    }
 
</script>
<%@ include file="/include/footer.jsf" %>