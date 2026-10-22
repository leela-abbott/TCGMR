<!-- Although we are using the Model framework and defining Cost Exchange as a Model, 
     we call it a Dataset to avoid confusing the user. -->  

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle="Margin Billed Exchange Flex"; %>

<%@ include file="/include/header.jsf" %>

<style type="text/css">
/* Replicates production yellow 3D look without static image assets */
.prod-action-btn {
	display: inline-block;
	width: 145px;
	box-sizing: border-box;
	background: #FFD700;
	color: #000000;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	text-align: center;
	text-decoration: none;
	padding: 3px 12px;
	margin-bottom: 8px;
	border-top: 2px solid #FFFDF0;
	border-left: 2px solid #FFFDF0;
	border-right: 2px solid #9A7B00;
	border-bottom: 2px solid #9A7B00;
	box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
	cursor: pointer;
}

.prod-action-btn:active {
	border-top: 2px solid #9A7B00;
	border-left: 2px solid #9A7B00;
	border-right: 2px solid #FFFDF0;
	border-bottom: 2px solid #FFFDF0;
}

.prod-action-btn:hover {
	background: #FFE240;
	text-decoration: none;
	color: #000000;
}

.hidden {
	display: none;
}
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<c:set var="pageTitle" value="Margin Billed Exchange Flex" scope="request" />
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<div id="divToHide">
<s:form id="mngCostExchModelsForm" name="mngCostExchModelsForm" action="mngCostExchModels" method="POST">
	<%@ include file="/include/jobOptions.jsf" %>
	<s:hidden name="showCostModel" id="showCostModel" />
	<s:hidden name="cmd" id="hiddenCmd" />
	<s:hidden name="jobName" id="hiddenJobName" />

	<table style="margin-left:50px; text-indent: 3px; width: 675px; border:0px; cellpadding:0; cellspacing:0;">
		<tr>
			<%-- Safe evaluation of showCostModel string comparisons using Struts 2 OGNL expressions --%>
			<s:if test="showCostModel == null || showCostModel.equals('open')">
				<td valign="top">
					<table style="width: 675px; border:0px; cellpadding:0; cellspacing:0;">
						<tr>
							<td colspan="3" class="tableHeading" style="width: 280px;">Margin Billed Exchange Flex</td>
							<td class="tableHeading">Dataset Details</td>
						</tr> 
						<tr>
							<td width="20">&nbsp;</td>
							<td width="104" valign="top"> 
								<!-- Fixed: Directly targets selection index lookup -->
								<s:select size="6" name="modelSelected" id="modelSelected"
										  list="models" listKey="modelId" listValue="name" 
										  onclick="updateDetails(this.selectedIndex)" theme="simple" style="width: 170px; margin-top: 5px;" />
								<br>&nbsp;
								<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' style="color: red; font-size: 10px;">Show Closed Model</a>
							</td>
							<td width="156" valign="top" style="padding-top: 5px; padding-left: 10px;">
								<button type="button" class="prod-action-btn" onclick="deleteModel(document.getElementById('modelSelected'))">Delete Dataset</button>
								<button type="button" class="prod-action-btn" onclick="closeModel(document.getElementById('modelSelected'))">Close Model</button>
								<a href="createCostExchModel.action" class="prod-action-btn" style="line-height: 12px; display: inline-block;">Create New Dataset</a>
								<button type="button" class="prod-action-btn" onclick="submitJob('CXCHG_FLEX')">Flex Exch Rates</button>
								<button type="button" class="prod-action-btn" onclick="submitJob('CXC_ANL04E')">Calc Cur Exposure</button>
							</td>
							<td valign="top"><div id="divModelDetail" class="dataDisp" style="padding-top: 5px;"></div></td>
						</tr>
					</table>
				</td>
			</s:if>
			
			<s:if test="showCostModel != null && showCostModel.equals('closed')">
				<td valign="top">
					<table style="width: 675px; border:0px; cellpadding:0; cellspacing:0;">
						<tr>
							<td colspan="3" class="tableHeading" style="width: 280px;">Closed Margin Billed Exchange Flex</td>
							<td class="tableHeading">Dataset Details</td>
						</tr> 
						<tr>
							<td width="20">&nbsp;</td>
							<td width="104" valign="top"> 
								<!-- Fixed: Directly targets selection index lookup -->
								<s:select size="6" name="modelSelected" id="modelSelected"
										  list="models" listKey="modelId" listValue="name" 
										  onclick="updateDetails(this.selectedIndex)" theme="simple" style="width: 170px; margin-top: 5px;" />
								<br>&nbsp;
								<a class="CmdSm" href='javascript:showClosedModelDetails("open")' style="color: red; font-size: 10px;">Show Open Model</a>
							</td>
							<td width="156" valign="top" style="padding-top: 5px; padding-left: 10px;">
								<button type="button" class="prod-action-btn" onclick="openModel(document.getElementById('modelSelected'))">Open Model</button>        
							</td>
							<td valign="top"><div id="divModelDetail" class="dataDisp" style="padding-top: 5px;"></div></td>
						</tr>
					</table>
				</td>
			</s:if>
		</tr>
	</table>

	<table style="margin-left: 50px; text-indent: 3px; width: 675px; border: 0px; cellpadding: 0; cellspacing: 0; margin-top: 15px;">
		<tr valign="top">	    
			<td>
				<table width="175" style="border: 0px; cellpadding: 0; cellspacing: 0;">
					<tr>
						<td colspan="2" class="tableHeading">Reports</td>  	      
					</tr>
					<tr>
						<td style="padding-top: 5px;">
							<button type="button" class="prod-action-btn" onclick="submitJob('CXCHG_CSTSL1')">Detail By Rpt Aff</button>
							<br>
							<s:select name="selDtlMetric" cssClass="commandOption" list="{'Cost','Sales'}" theme="simple" style="margin-bottom: 8px; width: 65px;" />
						</td>
					</tr>
					<tr>
						<td>
							<button type="button" class="prod-action-btn" onclick="submitJob('CXCHG_CSMP2')">CSM - Aff\Line\Grp</button>
						</td>
					</tr>
				</table>
			</td>
			<td width="20"></td>
			<td width="116">&nbsp;</td>
			<td width="177">&nbsp;</td>
		</tr>
	</table>
</s:form>
</div>
<br>
<p>&nbsp;</p>

<script type="text/javascript">
	// Fixed: Inject the runtime details array securely using native Struts tags
	var modelDetailArray = <s:property value="modelDetailArrayString" escapeHtml="false"/>;

	function updateDetails(selectedIndex) {
		// Verify array bounds safely to ensure it loads flawlessly without null/undefined pointers
		if (modelDetailArray && selectedIndex !== undefined && selectedIndex >= 0 && selectedIndex < modelDetailArray.length) {
			document.getElementById('divModelDetail').innerHTML = modelDetailArray[selectedIndex];
		} else {
			document.getElementById('divModelDetail').innerHTML = "<i>No details available for the selected model.</i>";
		}
	}

	function deleteModel(se) {
		if (!se || se.value == "" || se.value == 0) {
			alert("You must select a model to delete.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;
		if (confirm("Are you sure that you would like to delete " + modelName + "?\nThis will delete all attributes and can not be undone.")) {
			var form = document.getElementById('mngCostExchModelsForm');
			form.action = "deleteCostExchModel.action";
			form.submit();
		}
	}

	function closeModel(se) {
		if (!se || se.value == "" || se.value == 0) {
			alert("You must select a model to close.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;
		if (confirm("Are you sure that you would like to close " + modelName + "?")) {
			var form = document.getElementById('mngCostExchModelsForm');
			form.action = "closeCostExchModel.action";
			form.submit();
		}
	}

	function openModel(se) {
		if (!se || se.value == "" || se.value == 0) {
			alert("You must select a model to open.");
			return;
		}
		var modelName = se.options[se.selectedIndex].text;
		if (confirm("Are you sure that you would like to open " + modelName + "?")) {
			var form = document.getElementById('mngCostExchModelsForm');
			form.action = "openCostModel.action";
			form.submit();
		}
	}

	function submitJob(jobDefinitionName) {
		var se = document.getElementById('modelSelected');
		if (!se || se.value == "" || se.value == 0) {
			alert("You must select a model.");
			return;
		}
		document.getElementById('hiddenCmd').value = "ADD_JOB";
		document.getElementById('hiddenJobName').value = jobDefinitionName;
		var form = document.getElementById('mngCostExchModelsForm');
		form.action = "mngCostExchModels.action";
		form.submit();
	}

	function showClosedModelDetails(stat) {
		document.getElementById('showCostModel').value = stat;
		var form = document.getElementById('mngCostExchModelsForm');
		form.action = 'mngCostExchModels.action';
		form.submit();
	}
</script>

<%@ include file="/include/footer.jsf" %>
