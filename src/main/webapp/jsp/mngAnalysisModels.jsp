<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Factor Analysis Model Management";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead1.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %> 
<html:form  name="mngAnalysisModelsForm" action="mngAnalysisModels" type="abbott.ai.tcgm.action.form.MngAnalysisModelsForm" scope="session">
<div id="divToHide">
<%@ include file="/include/jobOptions.jsf" %>
<nested:text property="showAnalModel" maxlength="8" styleClass="hidden" />
<TABLE class="tableCommand">
<c:if test="${mngAnalysisModelsForm.showAnalModel =='open'}" >
		<TR>
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
				<TABLE>
				
					<tr>
					  <td colspan="3" class="tableHeading" width=50%>Factor Analysis Models</td>
					  <td class="tableHeading" width=50%>Model Details</td>
					</tr>
					
					<tr>
					  <td width="20">&nbsp; </td>
					  <td width="150">
						 <nested:define id="analysisModels" property="models" />
						  <html:select size="6" property="modelSelected" onchange="updateDetails()" >
							<html:options property="modelId" labelProperty="name" collection="analysisModels" />
						  </html:select>
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' >Show Closed Model</a>
			</abbott:securePage>
						 </td>
					  <td width="80" align="left">
						 <a href=javascript:closeModel(document.mngAnalysisModelsForm.modelSelected)><img src="images/btnCloseModel.png"  border=0></a><br>
						<br> 
						<!--<a href=javascript:compactModel(document.mngAnalysisModelsForm.modelSelected)><img src="images/btnCompactModel.png"  border=0></a><br>
						<br> -->		
						<a href=javascript:deleteModel(document.mngAnalysisModelsForm.modelSelected)><img src="images/btnDeleteModel.png"  border=0></a><br>
						<br>		
						<a href="createAnalysisModel.do"><img src="images/btnCreateNewModel.png"  border="0"></a><br>
						<br>
						<a href="javascript:addJob(document.forms[0], 'ANL_CALC')"><img src="images/btnCalculateAnalysis.png" border="0"></a>
						</td>	  			  		
					  <td width="330" ><div id="divModelDetail" class="dataDisp"></div></td>
					</tr>
	
				</TABLE>
				
				</fieldset>
			
			</TD>
		</TR>
</c:if>

<c:if test="${mngAnalysisModelsForm.showAnalModel =='closed'}" >
		<TR>
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
				<TABLE>
				
					<tr>
					  <td colspan="3" class="tableHeading" width=50%>Closed Factor Analysis Models</td>
					  <td class="tableHeading" width=50%>Model Details</td>
					</tr>
					
					<tr>
					  <td width="20">&nbsp; </td>
					  <td width="150">
						 <nested:define id="analysisModels" property="models" />
						  <html:select size="6" property="modelSelected" onchange="updateDetails()" >
							<html:options property="modelId" labelProperty="name" collection="analysisModels" />
						  </html:select>
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("open")' >Show Open Model</a>
			</abbott:securePage>
						 </td>
					  <td width="80" align="left" valign="top"> 
						 <a href=javascript:openModel(document.mngAnalysisModelsForm.modelSelected)><img src="images/btnOpenModel.png"  border=0></a><br>						
						</td>	  			  		
					  <td width="330" ><div id="divModelDetail" class="dataDisp"></div></td>
					</tr>
	
				</TABLE>
				
				</fieldset>
			
			</TD>
		</TR>
</c:if>
</TABLE>

	<br><br>
	
	<TABLE class="tableCommand">

		<TR>
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
				<TABLE>
					<tr>
					  <td colspan="3" class="tableHeading" width=50%>HQ Reports</td>
					</tr>
					
					<tr valign="top">
						  <td width="20">&nbsp;</td>
						  <td width="50" >
							<table width="100" cellpadding=4 cellspacing=2>
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_EXSUMMRY')"><img src="images/btnSalesExtendedSummary.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_WSUMMRY')"><img src="images/btnSalesWorldSummary.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_XSEC_SUM')"><img src="images/btnSalesSectorSummary.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_ARSUMMRY')"><img src="images/btnSalesAreaRegionSmry.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_SAMPEX')"><img src="images/btnSampleExtendedSummary.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_SAMPW')"><img src="images/btnSampleWorldSmry.png" border="0"></a></td>
								  </tr>
								  
								   <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_SAMSEC_SUM')"><img src="images/btnSampleSectorSummary.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_SAMPAR')"><img src="images/btnSampleAreaRegionSmry.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_PUSRCSUM')"><img src="images/btnExtendedSummarybySrc.png" border="0"></a></td>
								  </tr>
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_PUSUMMRY')"><img src="images/btnPerUnitSummary.png" border="0"></a></td>
								  </tr>
						
								  <tr>
									<td width=225><a href="javascript:addJob(document.forms[0], 'ANL_REJECTS')"><img src="images/btnUnableToAnalyze.png" border="0"></a></td>
								  </tr>
								  
								  
								  <tr>
									<td><a href="javascript:addJob(document.forms[0], 'ANL_DETAIL')"><img src="images/btnDetailAnalysis.png" border="0"></a></td>
								  </tr>
						
							 </table>
						 </td>
						 
				</TABLE>
								
				</fieldset>
							
			</TD>
							
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
					<TABLE>
							<tr>
								  <td colspan="3" class="tableHeading" width=50%>Publish Reports</td>
							</tr>
							
							<tr>
								<td width="20">&nbsp;</td>
								<td width="238">
		 	 
									<table border="0">
										  <tr>
											<td><a href="javascript:confirmAndAddJob(document.forms[0], 'ANL_PSUM_PUB')"><img src="images/btnPubPerUnitSmry.png" border="0"></a></td>
										  </tr>
										  
										  <!-- <tr>
											<td><a href="javascript:changeCmdAndSubmit(document.forms[0], 'FETCH_PACK_CODES')"><img src="images/btnFetchPackCodes.png" border="0"></a></td>
										  </tr> -->
								
								  		 <%//Sridevi.K <tr> changed to fix ticket on Analysis Model. %>
										  <tr>
											<td><a href="javascript:confirmAndAddJob(document.forms[0], 'ANL_XTSM_400')"><img src="images/btnPubSalesExtSummary.png" border="0"></a></td>
										  </tr>
										 <%//Sridevi.K <tr> changed to fix ticket on Analysis Model.%>
										  
										  <tr>
											<td><a href="javascript:confirmAndAddJob(document.forms[0], 'ANL_PSUM_400')"><img src="images/btnPubSalesSectorSmry.png" border="0"></a></td>
										  </tr>
										  
										  <tr>
											<td><a href="javascript:confirmAndAddJob(document.forms[0], 'ANL_PSSES_AREA')"><img src="images/btnPubSalesSamplesExtSmryArea.png" border="0"></a></td>
										  </tr>
										  <tr>
											<td><!-- <div class="commandOption">Define AS400 Ext Va Version
												<html:select property="extValueVersion" styleClass="commandOption" >
												  <option>1</option>
												  <option>2</option>
												  <option>3</option>
												</html:select>
											  </div> --> <br></td>
										  </tr>		  
									</table> 
								</td> 
											
							</tr>
								
					</TABLE>
				
				</fieldset>
				
				<!--   Misc Reports -->
				
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
					<TABLE>
							<tr>
								  <td colspan="3" class="tableHeading" width=50%>Misc Reports</td>
							</tr>
							
							<tr>
							
								<td><a href="javascript:changeCmdAndSubmit(document.forms[0], 'FETCH_TEST_HEADERS')"><img src="images/btnFetchTestHeaderLabels.png" border="0"></a></td>
							  </tr>
							  <tr>
								<td><a href="javascript:changeCmdAndSubmit(document.forms[0], 'FETCH_PROD_HEADERS')"><img src="images/btnFetchProductionLabels.png" border="0"></a></td>
							  </tr>
							  
							  <tr> <td> &nbsp; </td></tr>
					<!-- 
							  <tr>
								<td><a href="javascript:confirmAndAddJob(document.forms[0], 'SEND_ANL_FLEX_ESS')"><img src="images/btnSendToEssbase.png" border="0"></a></td>
							  </tr>-->
							  
							  <tr> <td> &nbsp; </td></tr>
							  
							  </table>
							  
							  <!-- <table>
							  <tr>
								<td>
									<div align="left" class="commandOptionLabel">Version</div>
								</td>
								<td>
								    <div align="left" class="commandOptionLabel">Year</div>
								</td>
								<td>
									<div align="left" class="commandOptionLabel">Type</div>
								</td>
							  </tr>
							  <tr>
								<td> 
									<html:select property="essbaseVersion" styleClass="commandOption" >
									<option>01</option>
									<option>02</option>
									<option>03</option>
									</html:select> 
								 </td>
								 <td>
									<input type="text" name="essbaseYear" size=3 maxlength="4">		  
								 </td>				
								 <td>			
									<html:select property="essbaseType" styleClass="commandOption" >
									  <option values="A1-10">A1-10</option>
									  <option values="A1-11">A1-11</option>
					  				  <option values="A1-12">A1-12</option>
									  <option values="A2">A2</option>
									  <option values="A3">A3</option>
									  <option values="A4">A4</option>				  
									</html:select>
								  </td>
								</tr>
												
						</TABLE>-->
								
					</fieldset>
							
				</TD>
							
				</TR>
				
			</TABLE>
				
  </div>
</html:form>
<br>

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
			changeActionAndSubmit(se.form, "deleteAnalysisModel.do");
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
			changeActionAndSubmit(se.form, "closeAnalysisModel.do")
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
			changeActionAndSubmit(se.form, "openAnalysisModel.do");
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
			changeActionAndSubmit(se.form, "compactAnalysisModel.do")
		}
	}


	function updateDetails() {

			document.body.style.cursor = 'wait';
			processActiveURL('tcgmAjax.do?cascadingCmd=ModelInfo&cascadingVal='+document.mngAnalysisModelsForm.modelSelected.value+'&cascadingSecVal=ANALYSIS','divModelDetail');
			document.body.style.cursor = "default";
		}
function showClosedModelDetails(stat) {
		document.mngAnalysisModelsForm.showAnalModel.value = stat;
		document.mngAnalysisModelsForm.action = 'mngAnalysisModels.do';
		document.mngAnalysisModelsForm.submit();
		}	

</script>

<%@ include file="/include/footer.jsf" %>
