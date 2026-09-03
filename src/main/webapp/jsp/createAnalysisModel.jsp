<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Create Factor Analysis";%>


<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 
<%@ include file="/include/masthead1.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<html:form name="createAnalysisModelForm" action="createAnalysisModel" type="abbott.ai.tcgm.action.form.CreateAnalysisModelForm">
<bean:define id="unitSets"  name="createAnalysisModelForm" property="unitSets" />
<bean:define id="factorModels"  name="createAnalysisModelForm" property="factorModels" />
<nested:define id="periodMonthList" property="periodMonthList" />

  <table class="tableCommand" >
    <tr>
      <td colspan="5" bgcolor="#b4d8f4" ><p class="tableHeading">Create New Factor
        Analysis</p></td>
    </tr> 
    <tr>
      <td width="22" class=right ></td>
      <td width="361" class="commandOptionLabel" >Factor Analysis Model Name</td>
      <td colspan="3"><html:text property="modelName" name="createAnalysisModelForm" size="30" styleClass="commandOption" maxlength="30"/></td>
    </tr>
    <tr>
      <td width="22" class=right ></td>
      <td class="commandOptionLabel">Model Desc</td>
      <td colspan="3"><html:text property="modelDesc" name="createAnalysisModelForm" size="65" styleClass="commandOption" maxlength="80"/></td>
    </tr>

    <tr>
      <td width="22" class=right ></td>
      <td width="121" class="commandOptionLabel" >Base Factor Model</td>
      <td width="150">
	  <html:select property="baseModelSelected" name="createAnalysisModelForm" styleClass="commandOption" >
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
      </html:select></td>
      <td width="127" class="commandOptionLabel">Period</td>
      <td width="172">
	  <html:select property="baseModelPeriod" name="createAnalysisModelForm" styleClass="commandOption" >
	      <html:option value="13">13</html:option>
          <html:options collection="periodMonthList" labelProperty="label" property="value" />
      </html:select>
	  </td>
    </tr>
    <tr>
      <td width="22" class=right ></td>
      <td width="201" class="commandOptionLabel">Analysis Factor Model</td>
      <td><html:select property="analysisModelSelected" name="createAnalysisModelForm" styleClass="commandOption" >
          <html:options property="modelId" labelProperty="name" collection="factorModels" />
      </html:select></td>
      <td class="commandOptionLabel">Period</td>
      <td><html:select property="analysisModelPeriod" name="createAnalysisModelForm" styleClass="commandOption" > 
	  		<html:option value="13">13</html:option>               
            <html:options collection="periodMonthList" labelProperty="label" property="value" />
          </html:select></td>
    </tr>
    <tr>
      <td width="22" class=right ></td>
      <td width="127" class="commandOptionLabel">Base Units</td>
      <td width="172"><html:select property="analysisUnitsSelected" name="createAnalysisModelForm" styleClass="commandOption" >
          <html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
      </html:select></td>
      <td class="commandOptionLabel">Period</td>
      <td>
	  <html:select property="analysisUnitsPeriod" name="createAnalysisModelForm" styleClass="commandOption" >   
	  		<html:option value="13">13</html:option>             
            <html:options collection="periodMonthList" labelProperty="label" property="value" />
      </html:select>
	  </td>
    </tr>
    <tr>
      <td class=right ></td>
      <td class="commandOptionLabel">Analysis Units</td>
      <td><html:select property="volumeUnitsSelected" name="createAnalysisModelForm" styleClass="commandOption" >
          <html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
      </html:select></td>
      <td class="commandOptionLabel">Period</td>
      <td>
		<html:select property="volumeUnitsPeriod" name="createAnalysisModelForm" styleClass="commandOption" > 
		    <html:option value="13">13</html:option>               
            <html:options collection="periodMonthList" labelProperty="label" property="value" />
        </html:select> 
	  </td>
    </tr>
    
    <!-- A.Winter - 7/1/05 - added textarea for memo -->
	<tr>
	  <td width="7" height="0" class=right ></td>
	  <td  class="commandOptionLabel"> Memo: </td>
	  <td colspan="3"  width="242" >
	      <html:textarea property="memo" name="createAnalysisModelForm" cols="68" rows="7" onkeyup="return checkLength(this, 425);" styleClass="commandOption" />
	  </td>
	  </tr> 
	   <!-- A.Winter - 7/1/05 - added textarea for memo -->
    
    <tr>
      <td class=right ></td>
      <td class="commandOptionLabel">&nbsp;</td>
      <td>&nbsp;</td>
      <td class="commandOptionLabel">&nbsp;</td>
      <td><a href="javascript:validateFormData(document.forms[0], 'CREATE_MODEL');"><img border="0" height="20" src="images/btnCreateModel.png" width="100"></a></td>
    </tr>
  </table>
<nested:hidden property="cmd" />
</html:form>

<p>&nbsp; </p>
<p>&nbsp;</p>

<%@ include file="/include/footer.jsf" %>