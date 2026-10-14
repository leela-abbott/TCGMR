<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Factor Analysis Model Management";%>

<%@ include file="/include/header.jsf" %>
<%
abbott.ai.tcgm.entities.User TCGMUser = (abbott.ai.tcgm.entities.User) session.getAttribute("TCGMUser");
%>

<style type="text/css">
    /* Layout Panels & Container Elements */
    fieldset {
        border: 1px solid #B0C4DE;
        border-radius: 4px;
        background-color: #FFFFFF;
        padding: 12px;
        margin-bottom: 15px;
        box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
    }

    .tableHeading {
        background-color: #A4C2E6; /* Light steel-blue background from image */
        color: #1A365D;            /* Dark corporate text tone */
        font-family: Arial, sans-serif;
        font-weight: bold;
        font-size: 13px;
        padding: 6px 10px;
        border-bottom: 1px solid #7FA2CE;
    }

    /* Base Semantic HTML Buttons Transformation */
    .btn-html {
        display: inline-block;
        width: 210px;               /* Fixed uniform column layout structure */
        box-sizing: border-box;
        background: linear-gradient(to bottom, #FFE866 0%, #FFD700 100%); /* Yellow gradient */
        color: #000000;
        font-family: "Arial", sans-serif;
        font-weight: bold;
        font-size: 12px;
        font-style: italic;         /* Slanted typographic look from image */
        text-align: center;
        padding: 5px 8px;
        margin: 3px 0;
        border: 1px solid #C4A700;  /* Dark yellow/gold rim border */
        border-radius: 4px;         /* Beveled edge aesthetics */
        cursor: pointer;
        
        /* Replicating the distinct drop shadow seen under the UI labels */
        box-shadow: 0px 3px 0px #B0B0B0, 0px 4px 3px rgba(0, 0, 0, 0.3);
        transition: transform 0.05s ease, box-shadow 0.05s ease;
    }

    /* Hover & Interactive States */
    .btn-html:hover {
        background: linear-gradient(to bottom, #FFF099 0%, #FFE033 100%);
    }

    .btn-html:active {
        transform: translateY(2px); /* Mimics pressing into the layout mesh */
        box-shadow: 0px 1px 0px #B0B0B0, 0px 2px 1px rgba(0, 0, 0, 0.2);
    }

    /* Contextual Button Dimension Overrides */
    .btn-close, .btn-delete, .btn-create, .btn-calculate, .btn-open {
        width: 140px;               /* Narrower layout grid footprint for control columns */
        font-size: 11px;
    }

    /* Selection Box Framework */
    select#modelSelected {
        font-family: Arial, sans-serif;
        font-size: 12px;
        border: 1px solid #7F9DB9;
        padding: 2px;
        background-color: #FFFFFF;
    }
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead1.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %> 

<s:form id="mngAnalysisModelsForm" name="mngAnalysisModelsForm" action="mngAnalysisModels" method="post" theme="simple">
<div id="divToHide">
<%@ include file="/include/jobOptions.jsf" %>

<%-- FIX: Changed from s:textfield to s:hidden to hide the unstyled 'open' input box completely --%>
<s:hidden name="showAnalModel" id="showAnalModel" />

<TABLE class="tableCommand">
<s:if test="mngAnalysisModelsForm.showAnalModel =='open'">
		<TR>
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
				<TABLE>
				
					<tr>
					  <td colspan="3" class="tableHeading" width="50%">Factor Analysis Models</td>
					  <td class="tableHeading" width="50%">Model Details</td>
					</tr>
					
					<tr>
					  <td width="20">&nbsp; </td>
					  <td width="150">
						  <s:select size="6" 
						  			name="modelSelected" 
									id="modelSelected" 
									list="models" 
									listKey="modelId" 
									listValue="name" 
									onchange="updateDetails()" />
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' >Show Closed Model</a>
			</abbott:securePage>
						 </td>
					  <td width="80" align="left">
						 <button type="button" class="btn-html btn-close" onclick="closeModel(document.getElementById('modelSelected'))">Close Model</button><br><br>
						 <button type="button" class="btn-html btn-delete" onclick="deleteModel(document.getElementById('modelSelected'))">Delete Model</button><br><br>
						 <button type="button" class="btn-html btn-create" onclick="location.href='createAnalysisModel.action'">Create New Model</button><br><br>
						 <button type="button" class="btn-html btn-calculate" onclick="addJob(document.forms, 'ANL_CALC')">Calculate Analysis</button>
					  </td>	  			  		
					  <td width="330" ><div id="divModelDetail" class="dataDisp"></div></td>
					</tr>
	
				</TABLE>
				
				</fieldset>
			
			</TD>
		</TR>
</s:if>

<s:if test="showAnalModel == 'closed'">
		<TR>
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
				<TABLE>
				
					<tr>
					  <td colspan="3" class="tableHeading" width="50%">Closed Factor Analysis Models</td>
					  <td class="tableHeading" width="50%">Model Details</td>
					</tr>
					
					<tr>
					  <td width="20">&nbsp; </td>
					  <td width="150">
						  <s:select size="6" 
						  			name="modelSelected" 
									id="modelSelected" 
									list="models" 
									listKey="modelId" 
									listValue="name" 
									onchange="updateDetails()" />
<BR>
&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("open")' >Show Open Model</a>
			</abbott:securePage>
						 </td>
					  <td width="80" align="left" valign="top"> 
						 <button type="button" class="btn-html btn-open" onclick="openModel(document.getElementById('modelSelected'))">Open Model</button><br>						
					  </td>	  			  		
					  <td width="330" ><div id="divModelDetail" class="dataDisp"></div></td>
					</tr>
	
				</TABLE>
				
				</fieldset>
			
			</TD>
		</TR>
</s:if>
</TABLE>

	<br><br>
	
	<TABLE class="tableCommand">

		<TR>
			<TD valign="top">
				<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
				<TABLE>
					<tr>
					  <td colspan="3" class="tableHeading" width="50%">HQ Reports</td>
					</tr>
					
					<tr valign="top">
						  <td width="20">&nbsp;</td>
						  <td width="50" >
							<table width="100" cellpadding=4 cellspacing=2>
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_EXSUMMRY')">Sales Extended Summary</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_WSUMMRY')">Sales World Summary</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_XSEC_SUM')">Sales Sector Summary</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_ARSUMMRY')">Sales Area Region Smry</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_SAMPEX')">Sample Extended Summary</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_SAMPW')">Sample World Smry</button></td>
								  </tr>
								  
								   <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_SAMSEC_SUM')">Sample Sector Summary</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_SAMPAR')">Sample Area Region Summary</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_PUSRCSUM')">Extended Summary by Src</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_PUSUMMRY')">Per Unit Summary</button></td>
								  </tr>
						
								  <tr>
									<td width=225><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_REJECTS')">Unable To Analyze</button></td>
								  </tr>
								  
								  <tr>
									<td><button type="button" class="btn-html" onclick="addJob(document.forms, 'ANL_DETAIL')">Detail Analysis</button></td>
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
								  <td colspan="3" class="tableHeading" width="50%">Publish Reports</td>
							</tr>
							
							<tr>
								<td width="20">&nbsp;</td>
								<td width="238">
		 	 
									<table border="0">
										  <tr>
											<td><button type="button" class="btn-html" onclick="confirmAndAddJob(document.forms, 'ANL_PSUM_PUB')">Pub Per Unit Summary</button></td>
										  </tr>
										  
										  <tr>
											<td><button type="button" class="btn-html" onclick="confirmAndAddJob(document.forms, 'ANL_XTSM_400')">Pub Sales Ext Summary</button></td>
										  </tr>
										  
										  <tr>
											<td><button type="button" class="btn-html" onclick="confirmAndAddJob(document.forms, 'ANL_PSUM_400')">Pub Sales Sector Smry</button></td>
										  </tr>
										  
										  <tr>
										  											<td><button type="button" class="btn-html" onclick="confirmAndAddJob(document.forms, 'ANL_PSSES_AREA')">Pub Sales Samples Ext Smry Area</button></td>
										  </tr>
										  <tr>
											<td><br></td>
										  </tr>		  
									</table> 
								</td> 
											
							</tr>
								
					</TABLE>
				
					<TABLE>
							<tr>
								  <td colspan="3" class="tableHeading" width="50%">Misc Reports</td>
							</tr>
							
							<tr>
								<td><button type="button" class="btn-html" onclick="changeCmdAndSubmit(document.forms, 'FETCH_TEST_HEADERS')">Fetch Test Header Labels</button></td>
							</tr>
							<tr>
								<td><button type="button" class="btn-html" onclick="changeCmdAndSubmit(document.forms, 'FETCH_PROD_HEADERS')">Fetch Production Labels</button></td>
							</tr>
							  
							<tr> <td> &nbsp; </td></tr>
							<tr> <td> &nbsp; </td></tr>
					</TABLE>
				</fieldset>
							
			</TD>
							
				</TR>
				
			</TABLE>
				
  </div>
</s:form>
<br>

<p>&nbsp;</p>

<script language="javascript">
	function deleteModel(se) 
	{
		if (!se || se.value == 0) 
		{
			alert ("You must select a model to delete.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;
		if ( confirm("Are you sure that you would like to delete " + modelName + "?\nThis will delete all attributes and can not be undone.") ) 
		{
			changeActionAndSubmit(document.getElementById('mngAnalysisModelsForm'), "deleteAnalysisModel.action");
		}
	}

	function closeModel(se)
	{
		if (!se || se.value == 0) 
		{
			alert ("You must select a model to close.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;
		if ( confirm("Are you sure that you would like to close " + modelName + "?") ) 
		{
			changeActionAndSubmit(document.getElementById('mngAnalysisModelsForm'), "closeAnalysisModel.action");
		}
	}

	function openModel(se) 
	{
		if (!se || se.value == 0) 
		{
			alert ("You must select a model to open.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;
		if ( confirm("Are you sure that you would like to open " + modelName + "?") ) 
		{
			changeActionAndSubmit(document.getElementById('mngAnalysisModelsForm'), "openAnalysisModel.action");
		}
	}

	function compactModel(se)
	{
		if (!se || se.value == 0) 
		{
			alert ("You must select a model to compact.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;	
		if ( confirm("Are you sure that you would like to compact " + modelName + "?\nAll temporary data and reports will be deleted.") ) 
		{
			changeActionAndSubmit(document.getElementById('mngAnalysisModelsForm'), "compactAnalysisModel.action");
		}
	}

	function updateDetails() {
		document.body.style.cursor = 'wait';
		var selectedVal = document.getElementById('modelSelected').value;
		processActiveURL('tcgmAjax.action?cascadingCmd=ModelInfo&cascadingVal='+selectedVal+'&cascadingSecVal=ANALYSIS','divModelDetail');
		document.body.style.cursor = "default";
	}

	function showClosedModelDetails(stat) {
		document.getElementById('showAnalModel').value = stat;
		var form = document.getElementById('mngAnalysisModelsForm');
		form.action = 'mngAnalysisModels.action';
		form.submit();
	}	
</script>

<%@ include file="/include/footer.jsf" %>
										  
