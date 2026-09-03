<%! String pageTitle="Select or Create Model";%>
<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<html:form name="mngFactorModelsForm" action="" type="abbott.ai.tcgm.action.form.MngFactorModelsForm">
  <nested:define id="monthListNumber" property="monthListNumber" />

  <table width="684" cellpadding="2">
  
	<tr>
	  <td width="24">&nbsp;</td>
	  <td colspan="5" class="tableHeading">Select Existing Model</td>
	</tr>

	<tr> 
	  <td width="24">&nbsp;</td>
	  <td width="87" class="tableEntry">Model Actions</td>
	  <td width="49" class="tableEntry">Name</td>
	  <td width="46" class="tableEntry" >Cycle</td>
	  <td width="55" class="tableEntry" >Year</td>
	  <td width="383" class="tableEntry">Description</td>
	</tr>
	<nested:equal property="showModels" value="open">
	<logic:iterate id="models" name="mngFactorModelsForm" scope="request" property="models" type="abbott.ai.tcgm.entities.FactorModel" >
	  <tr>
		<td height="23" colspan=2 class=right >&nbsp;
			<!-- <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
				<a class="CmdSm" href='javascript:compactModel(<bean:write name="models" property="modelId"  />, "<bean:write name="models" property="name"  />")' >Compact</a>
			</abbott:securePage> -->
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:closeModel(<bean:write name="models" property="modelId"  />, "<bean:write name="models" property="name"  />")' >Close</a>
			</abbott:securePage>
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				&nbsp;&nbsp;&nbsp;&nbsp;<a class="CmdSm" href='javascript:deleteModel(<bean:write name="models" property="modelId"  />, "<bean:write name="models" property="name"  />")' >Del</a>
			</abbott:securePage>
		</td>
		<td >
			<a href='javascript:selectModel(<bean:write name="models" property="modelId" />)' >
		  <bean:write name="models" property="name"  />
		  </a>
		</td>
		<td class="commandOptionLabel"><bean:write name="models" property="modelCycleLongName" /></td>
		<td class="commandOptionLabel"><bean:write name="models" property="modelYear" /></td>
		<td class="commandOptionLabel"><bean:write name="models" property="desc" /></td>
	  </tr>
	 
		</logic:iterate>
		<td height="23" colspan=3 class=right >&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' >Show Closed Models</a>
			</abbott:securePage>
		</td>
		
		</nested:equal>	
	<nested:notEqual property="showModels" value="open">
	<logic:iterate id="closedModels" name="mngFactorModelsForm" scope="request" property="closedModels" type="abbott.ai.tcgm.entities.FactorModel" >
	  <tr>
		<td height="23" colspan=2 class=right >&nbsp;
			<!-- <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
				<a class="CmdSm" href='javascript:compactModel(<bean:write name="closedModels" property="modelId"  />, "<bean:write name="closedModels" property="name"  />")' >Compact</a>
			</abbott:securePage> -->
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:openModel(<bean:write name="closedModels" property="modelId"  />, "<bean:write name="closedModels" property="name"  />")' >Open</a>
			</abbott:securePage>
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				&nbsp;&nbsp;&nbsp;&nbsp;<a class="CmdSm" href='javascript:deleteModel(<bean:write name="closedModels" property="modelId"  />, "<bean:write name="closedModels" property="name"  />")' >Del</a>
			</abbott:securePage>
		</td>
		<td class="commandOptionLabel"> <bean:write name="closedModels" property="name"/></td>
		<td class="commandOptionLabel"><bean:write name="closedModels" property="modelCycleLongName" /></td>
		<td class="commandOptionLabel"><bean:write name="closedModels" property="modelYear" /></td>
		<td class="commandOptionLabel"><bean:write name="closedModels" property="desc" /></td>
	  </tr>
	  
		</logic:iterate>

		<td height="23" colspan=3 class=right >&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("open")' >Show Open Models</a>
			</abbott:securePage>
		</td>

		</nested:notEqual>
		
  </table>
  <html:hidden property="modelSelected" />
  	<nested:text property="showModels" maxlength="8" styleClass="hidden" />	
</html:form>

<br>

<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
 	
  <html:form name="createFactorModelForm" action="/createFactorModel" type="abbott.ai.tcgm.action.form.CreateFactorModelForm" >
  <nested:define property="factorModels" id="factorModels" />
<nested:equal property="showModels" value="open">
	<table width="598" class="tableCommand">
	  <tr>
		<td colspan="6" nowrap class="tableHeading">Create New Model</td>
	  </tr>

	  <tr>
		<td width="89" nowrap class="commandOptionLabel"><div align="right">Name:</div></td>
		<td width="1" nowrap class="commandOptionLabel">
		<html:text property="createModelName" size="30" maxlength="30" styleClass="commandOption" />
		 </td>
		<td width="38" nowrap  align="right" class="commandOptionLabel">Cycle:</td>
		<td width="50" nowrap  alingn="left" class="commandOptionLabel">
		
			<html:select property="createModelCycle" styleClass="commandOption">			
			<option value="ACT">Actual</option>
			<option value="PLN">Plan</option>
			<option value="UPD">Updated Plan</option>
			<option value="SIM">Simulation</option>
			<option value="INV1">April Inv</option>
			<option value="INV2">Sept Inv</option>
			<option value="INV3">Dec Inv</option>
			</html:select></td>
			
		<td width="33" nowrap align="right" class="commandOptionLabel">Year:</td>
		<td nowrap align="left" class="commandOptionLabel"><html:text property="createModelYear" size="4" maxlength="4" styleClass="commandOption" /></td>
	  </tr>

	  <tr>
		<td width="89" height="22" align="right" nowrap class="commandOptionLabel" >Description:</td>
		<td colspan="4" nowrap>
			<html:text property="createModelDesc" size="80" maxlength="80" styleClass="commandOption" /></td>
		<td width="166" nowrap class="commandOptionLabel" ></td>
	  </tr>
	  
	  <tr>
		<td width="89" height="94" nowrap align="right" class="tableEntry" style="padding:2px;" >Copy Options</td>
		<td colspan="4" valign=top nowrap>
			<table width="323">

			<tr>
			  <td colspan="3" align="left" nowrap class="commandOptionLabel">Copy From:
				  <html:select property="selSourceModelId" styleClass="commandOption">
					<option value="none">&lt;none&gt;</option>
					<html:options property="modelId" labelProperty="name" collection="factorModels" />
				  </html:select>
				</td>
			</tr>
			<tr>
			  <td width="150" height="22" valign="top" nowrap class="commandOptionLabel">
				<html:checkbox property='createModelClearFreezeCosts' styleClass="commandOption" />
				 Clear Freeze Costs</td>
			  <td width="150" nowrap class="commandOptionLabel" > BP/C Period</td>
			  <td width="50" nowrap>
				<html:select property='createModelKeepBpcsPeriod' styleClass="commandOption">
				  <option value=0>&lt;don't&gt;</option>

				  <html:options collection="monthListNumber" labelProperty="label" property="value" />
				</html:select>
				</td>
			</tr>

		<!--	<tr>
			  <td width="150" nowrap class="commandOptionLabel">
				<html:checkbox property='createModelClearRevDates' styleClass="commandOption" />
				 Clear BPC Revisions</td>
			  <td width="150" nowrap class="commandOptionLabel">Keep BP/C Ex From</td>
			  <td width="50" nowrap>
				<html:select property='createModelKeepBPExPeriod' styleClass="commandOption">

				  <option value=0>&lt;don't&gt;</option>
				  
				  <html:options collection="monthListNumber" labelProperty="label" property="value" />
				</html:select>
				</td>
			</tr>-->

			<tr>
			  <td width="250" nowrap class="commandOptionLabel">
				<html:checkbox property="createModelClearBPCS" styleClass="commandOption" />
				Clear BP/C and BP/C Exceptions</td>
			  <!--<td width="150" nowrap class="commandOptionLabel" >Keep Rate Ex From</td>
			  <td width="50" nowrap>
				<html:select property='createModelKeepExchExPeriod' styleClass="commandOption">
				  <option value=0>&lt;don't&gt;</option>

				  <html:options collection="monthListNumber" labelProperty="label" property="value" />
					</html:select>
				</td>-->
			</tr>
			</table></td>  
		
<!--Sridevi.K Code for document.createFactorModelForm.submit() is replaced 06/05/2005 
		<td><a href="javascript:document.createFactorModelForm.submit()" ><img height="20" src="images/btnCreateNewModel.png" border=0></a></td>
-->		
<!-- Sridevi.K New code that is replaced for the above submit is added on 06/05/05 starts here -->
		<td><a href="javascript:createModel()" ><img height="20" src="images/btnCreateNewModel.png" border=0></a></td>
<!-- Sridevi.K New code that is replaced for the above submit is added on 06/05/05 ends here -->
	  </tr>
	</table>
	 </nested:equal>
  </html:form>
	 
  </abbott:securePage>

<script language=javascript>
	function deleteModel(modelid, modelname)
	 {
		if ( confirm("Are you sure that you would like to delete " + modelname + "?\nThis will delete all attributes and can not be undone.") ) {
			if(confirm("ARE YOU SURE to delete " + modelname + "?") ){
				document.forms.mngFactorModelsForm.modelSelected.value=modelid;
				changeActionAndSubmit(document.forms.mngFactorModelsForm, "deleteFactorModel.do")
			}
		}
	}

	function closeModel(modelid, modelname)
	 {
		 if ( confirm("Are you sure that you would like to close " + modelname + "?") ) {
			 document.forms.mngFactorModelsForm.modelSelected.value=modelid;
			 document.forms.mngFactorModelsForm.showModels.value = 'open';
			 changeActionAndSubmit(document.forms.mngFactorModelsForm, "closeModel.do")
		 }
	}

	function openModel(modelid, modelname)
	 {
		 if ( confirm("Are you sure that you would like to open " + modelname + "?") ) {
			 document.forms.mngFactorModelsForm.modelSelected.value=modelid;
			 document.forms.mngFactorModelsForm.showModels.value = 'closed';
			 changeActionAndSubmit(document.forms.mngFactorModelsForm, "openModel.do")
		 }
	}

	function compactModel(modelid, modelname)
	 {
		 if ( confirm("Are you sure that you would like to compact " + modelname + "?\nAll temporary data and reports will be deleted.") ) {
			 document.forms.mngFactorModelsForm.modelSelected.value=modelid;
			 changeActionAndSubmit(document.forms.mngFactorModelsForm, "compactModel.do")
		 }
	}

	function selectModel(modelid, modelname)
	{
		document.forms.mngFactorModelsForm.modelSelected.value=modelid;
		changeActionAndSubmit(document.forms.mngFactorModelsForm, "updateModelSelection.do")
	}
	
	<%//Sridevi.K 06/05/2005 New script for fixing the empty description field while creating a model starts here.%>
	function createModel()
	 {
		var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
		var check = true;
	    for (var i = 0; i < document.forms.createFactorModelForm.createModelName.value.length; i++) 
	    {
	  	if (iChars.indexOf(document.forms.createFactorModelForm.createModelName.value.charAt(i)) != -1) 
	  	{
		  	alert ("The Model name you eneterd has special characters. \n Please remove them and try again.");
		  	check = false;
	  	}
	  	}
		if ( emptyAlert('Description', document.createFactorModelForm.createModelDesc) && check) {
		 
			 document.createFactorModelForm.submit();
		 }
	}
	<%//Sridevi.K New script ends here 06/05/2005%>
	function showClosedModelDetails(stat) {
		document.forms.mngFactorModelsForm.showModels.value = stat;
		document.mngFactorModelsForm.action = 'mngFactorModels.do';
		document.mngFactorModelsForm.submit();
		}	

</script>
<%@ include file="/include/footer.jsf" %>
