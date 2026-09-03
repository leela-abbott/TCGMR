<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Perpetual Model Management";%>


<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<%@ include file="/include/masthead1.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<div id="divToHide">
<html:form  name="mngPerpetualModelsForm" action="mngPerpetualModels" type="abbott.ai.tcgm.action.form.MngPerpetualModelsForm">
<%@ include file="/include/jobOptions.jsf" %>
<nested:text property="showPerpetualModel" maxlength="8" styleClass="hidden" />

  <table class="tableCommand" >
<c:if test="${mngPerpetualModelsForm.showPerpetualModel =='open'}" >
	<tr>
	  <td colspan="3"  class="tableHeading">Perpetual Models</td>
	  <td  class="tableHeading">Model Details</td>
	</tr>
	<tr>
	  <td width="20" >&nbsp;</td>
	  <td width="170"> 
		  <bean:define id="perpetualModels"  name="mngPerpetualModelsForm" property="models" />
		  <html:select size="6" property="modelSelected" onchange="updateDetails()" >
			<html:options property="modelId" labelProperty="name" collection="perpetualModels" />
		  </html:select>
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' >Show Closed Model</a>
			</abbott:securePage>
		   </td>
	  <td width="110">
		<p><a href=javascript:deleteModel(document.mngPerpetualModelsForm.modelSelected)>
			<img src="images/btnDeleteModel.png"  border=0></a></p>
		<p><a href=javascript:closeModel(document.mngPerpetualModelsForm.modelSelected)>
			<img src="images/btnCloseModel.png"  border=0></a></p>
		<!-- <p><a href=javascript:compactModel(document.mngPerpetualModelsForm.modelSelected)>
			<img src="images/btnCompactModel.png"  border=0></a></p> -->						
		<p><a href="createPerpetualModel.do"><img src="images/btnCreateNewModel.png"  border="0"></a></p>
		<p><a href="javascript:addJob(document.forms[0], 'PERP_TOTCALC')"><img src="images/btnCalculatePerpetual.png" border="0" ></a></p>
		<p><a href="javascript:addJob(document.forms[0], 'PPT_INVCALC1')"><img src="images/btnCalcEqUnits.png" border="0" ></a></p></td>
	  <td width="285" ><div id="divModelDetail" class="dataDisp"></div></td>
	</tr>
</c:if>
<c:if test="${mngPerpetualModelsForm.showPerpetualModel =='closed'}" >
	<tr>
	  <td colspan="3"  class="tableHeading">Closed Perpetual Models</td>
	  <td  class="tableHeading">Model Details</td>
	</tr>
	<tr>
	  <td width="20" >&nbsp;</td>
	  <td width="170"> 
		  <bean:define id="perpetualModels"  name="mngPerpetualModelsForm" property="models" />
		  <html:select size="6" property="modelSelected" onchange="updateDetails()" >
			<html:options property="modelId" labelProperty="name" collection="perpetualModels" />
		  </html:select>
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("open")' >Show Open Model</a>
			</abbott:securePage>
		   </td>
	  <td width="110" valign="top">
		<!--  <p><a href=javascript:deleteModel(document.mngPerpetualModelsForm.modelSelected)>
			<img src="images/btnDeleteModel.png"  border=0></a></p>-->
		<p><a href=javascript:openModel(document.mngPerpetualModelsForm.modelSelected)>
			<img src="images/btnOpenModel.png"  border=0></a></p>
		</td>
	  <td width="285" ><div id="divModelDetail" class="dataDisp"></div></td>
	</tr>
</c:if>
  </table>
  <table class="tableCommand" >
	<tr>
	  <td colspan="2" class="tableHeading">Reports</td>
	</tr>
	<tr valign="top">
	  <td width="20">&nbsp;</td>
	  <td width="420" >
	  <table>
		  <tr>
			<td><a href="javascript:addJob(document.forms[0], 'PERP_SUMMARY')"><img src="images/btnCostedSummary.png" border="0" ></a></td>
			<td><a href="javascript:addJob(document.forms[0], 'PERP_DETAIL')"><img src="images/btnCostedDetail.png" border="0" ></a></td>
		  </tr>
	  </table>
	  </td>
	</tr>
  </table>
</html:form>
</div>
<p>&nbsp;</p>

<script language=javascript>
	function deleteModel(se) 
	{
		if (se.value == 0) 
		{
			alert ("You must select a model to delete.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		if ( confirm("Are you sure that you would like to delete " + modelName + "?\nThis will delete all attributes and can not be undone.") ) 
		{
			changeActionAndSubmit(se.form, "deletePerpetualModel.do");
		}
	}
	function closeModel(se) 
	{
		if (se.value == 0) 
		{
			alert ("You must select a model to close.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		if ( confirm("Are you sure that you would like to close " + modelName + "?") ) 
		{
			changeActionAndSubmit(se.form, "closePerpetualModel.do");
		}
	}
function openModel(se) 
	{
		if (se.value == 0) 
		{
			alert ("You must select a model to open.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		if ( confirm("Are you sure that you would like to open " + modelName + "?") ) 
		{
			changeActionAndSubmit(se.form, "openPerpetualModel.do");
		}
	}
	function compactModel(se) 
	{
		if (se.value == 0) 
		{
			alert ("You must select a model to compact.");
			return;
		}
		var modelName = getSelectedOptionText(se);
		if ( confirm("Are you sure that you would like to compact " + modelName + "?\nAll temporary data and reports will be deleted.") ) 
		{
			changeActionAndSubmit(se.form, "compactPerpetualModel.do");
		}
	}		

	function updateDetails() {			
			document.body.style.cursor = 'wait';
			processActiveURL('tcgmAjax.do?cascadingCmd=ModelInfo&cascadingVal='+document.mngPerpetualModelsForm.modelSelected.value+'&cascadingSecVal=PERPETUAL','divModelDetail');
			document.body.style.cursor = "default";

		}
function showClosedModelDetails(stat) {
		document.mngPerpetualModelsForm.showPerpetualModel.value = stat;
		document.mngPerpetualModelsForm.action = 'mngPerpetualModels.do';
		document.mngPerpetualModelsForm.submit();
		}	
</script>

<%@ include file="/include/footer.jsf" %>
