<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Create Perpetual";%>


<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:init()">
 <%@ include file="/include/masthead1.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<html:form name="createPerpetualModelForm" action="createPerpetualModel" type="abbott.ai.tcgm.action.form.CreatePerpetualModelForm" >
<nested:define id="monthListNumber" property="monthListNumber" />
<nested:define id="yearList" property="yearList" />
<nested:define id="factorModels"  name="createPerpetualModelForm" property="factorModels" />
<nested:define id="units"  name="createPerpetualModelForm" property="units" />
 
  <table class="tableCommand">
    <tr>
      <td colspan="5"  class="tableHeading">Create New Perpetual Model </td>
    </tr>
    <tr>
      <td width="20" height="23" class=right ></td>
      <td width="158" align="right" class="commandOptionLabel" >Model Name</td>
      <td colspan="3"><html:text property="modelName" name="createPerpetualModelForm" size="30" styleClass="commandOption" maxlength="30"/></td>
    </tr>
    <tr>
      <td width="20" height="23" class=right ></td>
      <td width="158" align="right" class="commandOptionLabel" >Model
        Desc.</td>
      <td colspan="3"><html:text property="modelDesc" name="createPerpetualModelForm" size="65" styleClass="commandOption" maxlength="80"/></td>
    </tr>

    <tr>
      <td width="20" height="23" class=right ></td>
      <td width="158" align="right" class="commandOptionLabel" >Starting  Inventory Model & Units</td>
      
      <td width="135">
      	  <html:select property="selStartingModel" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
        </html:select></td>
    
      <td width="135">
      	  <html:select property="selStartingModelUnits" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="datasetName" labelProperty="datasetName" collection="units" />
        </html:select></td>

        <td width="150" align="right"> </td>
        <td width="141" align="right"> </td>
    </tr>
    <tr>
      <td width="20" height="23" class=right ></td>
      <td width="158" align="right" class="commandOptionLabel" >Current Actual Model & Actual Units</td>
      
      <td width="135">
      	  <html:select property="selCurrentYearActualModel" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
        </html:select></td>
    
      <td width="135">
      	  <html:select property="selCurrentYearActualUnits" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="datasetName" labelProperty="datasetName" collection="units" />
        </html:select></td>
      <td width="150" align="right" class="commandOptionLabel" >Starting Period</td>
      <td width="141" >
	   	<nested:select property="selStartPeriod" name="createPerpetualModelForm" styleClass="commandOption" >
		  <option>13</option>
          <html:options collection="monthListNumber" labelProperty="label" property="value" />
        </nested:select>
		<nested:select property="selStartYear"  name="createPerpetualModelForm" styleClass="commandOption" >
          <html:options collection="yearList" labelProperty="label" property="value" />
        </nested:select></td>

    </tr>
    <tr>
      <td width="20" height="23" class=right ></td>
      <td width="158" align="right" class="commandOptionLabel" >Previous Actual Model & Actual Units</td>
      
      <td width="135">
      	  <html:select property="selLastYearActualModel" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
        </html:select></td>
    
      <td width="135">
      	  <html:select property="selLastYearActualUnits" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="datasetName" labelProperty="datasetName" collection="units" />
        </html:select></td>
       <td width="150" align="right"  class="commandOptionLabel" >Ending Period</td>

	  	<td><html:select property="selEndPeriod" name="createPerpetualModelForm" styleClass="commandOption" >
		  <option>13</option>
          <html:options collection="monthListNumber" labelProperty="label" property="value" />
        </html:select>
		<html:select property="selEndYear" name="createPerpetualModelForm" styleClass="commandOption" >
          <html:options collection="yearList" labelProperty="label" property="value" />
        </html:select>
	  </td>
       
    </tr>

    <tr>
      <td width="20" height="23" class=right ></td>
      <td width="158" align="right"  class="commandOptionLabel" >Costing Model</td>
      <td><html:select property="selCostingModel" name="createPerpetualModelForm" styleClass="commandOption" >
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
          </html:select>
      </td>
      <td width="141" >
    
    </tr>

    <tr>
      <td width="20" height="31" class=right ></td>
      <td width="158" height="31" align="right" class="commandOptionLabel" >Ending Inventory & Units</td>
      <td align="left" >
        <html:select property="selEndingModel" name="createPerpetualModelForm" styleClass="commandOption"  >
          <html:option value="NONE">--none--</html:option>
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
        </html:select></td>
      <td width="135">
      	  <html:select property="selEndingModelUnits" name="createPerpetualModelForm" styleClass="commandOption" >
		  
          <html:options property="datasetName" labelProperty="datasetName" collection="units" />
        </html:select></td>
      <td colspan="2" align="right"> &nbsp;</td>
    </tr>
    
    <!-- A.Winter - 7/1/05 - added textarea for memo -->
	<tr>
	   <td width="20" height="31" class=right ></td>
	   <td  align="right" class="commandOptionLabel"> Memo &nbsp;&nbsp;</td>
	   <td colspan="3"  width="242" >
	      <html:textarea property="memo" name="createPerpetualModelForm" cols="68" rows="7" onkeyup="return checkLength(this, 425);" styleClass="commandOption" />
	   </td>
    </tr>
	<!-- A.Winter - 7/1/05 - added textarea for memo -->
	</tr>
	  <td class=right ></td>
      <td class="commandOptionLabel">&nbsp;</td>
      <td>&nbsp;</td>
      <td class="commandOptionLabel">&nbsp;</td>
    <td colspan="2" align="right"><div align="left"><a href="javascript:validateFormData(document.forms[0], 'CREATE_MODEL');"><img border="0" height="20" src="images/btnCreateModel.png" width="100"></a></div></td>
    </tr>
  </table>
<nested:hidden property="cmd" />
</html:form>
<script language="JavaScript" type="text/JavaScript">
 function init() {
     document.forms[0].selEndingModel.disabled=false;
    }

 function validateEndingCBO() {
     document.forms[0].selEndingModel.disabled=!document.forms[0].selEndingModel.disabled;
    }
</script>
<%@ include file="/include/footer.jsf" %>