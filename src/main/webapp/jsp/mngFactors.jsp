<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%!String pageTitle = "Calculate and Manage Factors";%>

<%@ include file="/include/header.jsf"%>

<head>
<style>
/* Restored the yellow-to-gold corporate gradients from the original application */
.btn-fctr {
	width: 100%;
	height: 24px;
	background: linear-gradient(to bottom, #ffe169, #f7c11e);
	color: #002266;
	border: 1px solid #cca329;
	font-family: sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	cursor: pointer;
	padding: 0 10px;
	margin: 2px 0;
	border-radius: 1px;
	box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.3);
	vertical-align: middle;
	white-space: nowrap;
	text-align: center;
}

.btn-fctr:hover {
	background: linear-gradient(to bottom, #ffd533, #e0aa0b);
}

.btn-fixed-width {
	width: 150px !important;
}

.textarea-stamp {
	font-family: sans-serif;
	font-size: 11px;
	color: #333;
	background-color: #f5f5f5;
	border: 1px solid #ccc;
	resize: none;
	vertical-align: middle;
	margin-left: 5px;
}
</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<c:set var="pageTitle" value="Calculate and Manage Factors" scope="request" />
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>
	<div id="divToHide">

<s:form id="mngFactorsForm" action="mngFactors" method="post" theme="simple">
    <s:hidden name="cmd" id="cmd" />
    <s:hidden name="jobName" id="jobName" />
    <s:checkbox name="chkRestrict" id="chkRestrict" style="display:none;" />


			<%@ include file="/include/jobOptions.jsf"%>

			<TABLE class="tableCommand" border="0" cellpadding="5"
				cellspacing="0">
				<TR>
					<TD valign="top">
						<fieldset style="text-align: center; padding: 10px;">
							<TABLE border="0" cellpadding="3" cellspacing="2">
								<tr>
									<td height="0" colspan="3" bgcolor="#b4d8f4">
										<p class="tableHeading" style="margin: 4px;">Factor
											Settings</p>
									</td>
								</tr>
								<tr>
									<td width="130" class="commandOptionLabel" align="left">Plan
										Units</td>
									<td><s:select name="planUnitsSelected" list="unitSets"
											listKey="datasetTableId" listValue="datasetNameLogStamp"
											headerKey="0" headerValue="<none>" cssClass="commandOption"
											style="width: 450px;" /></td>
								</tr>
								<tr>
									<td class="commandOptionLabel" align="left">Actual Units</td>
									<td><s:select name="actualUnitsSelected" list="unitSets"
											listKey="datasetTableId" listValue="datasetNameLogStamp"
											headerKey="0" headerValue="<none>" cssClass="commandOption"
											style="width: 450px;" /></td>
								</tr>
								<tr>
									<td class="commandOptionLabel" align="left">Factor Rate</td>
									<td><s:select name="selCostRate" list="rateSets"
											listKey="datasetTableId" listValue="datasetName" headerKey=""
											headerValue="<none>" cssClass="commandOption"
											style="width: 150px;" /></td>
								</tr>
								<tr>
									<td class="commandOptionLabel" align="left">Std Cost Rate</td>
									<td><s:select name="selFactorRate" list="rateSets"
											listKey="datasetTableId" listValue="datasetName" headerKey=""
											headerValue="<none>" cssClass="commandOption"
											style="width: 150px;" /></td>
								</tr>
								<tr>
									<td class="commandOptionLabel" align="left">Revision Rate</td>
									<td><s:select name="selRevisionRate" list="rateSets"
											listKey="datasetTableId" listValue="datasetName" headerKey=""
											headerValue="<none>" cssClass="commandOption"
											style="width: 150px;" /></td>
								</tr>
								<tr>
									<td class="commandOptionLabel" valign="top" align="left">Memo:</td>
									<td><s:textarea name="modelLongDesc" cols="68" rows="7"
											cssClass="commandOption"
											style="width: 450px; font-family:sans-serif;" /></td>
								</tr>
								<tr>
									<td colspan="2" align="left" style="padding-top: 10px;">
										<button type="button" class="btn-fctr" style="width: 140px;"
											onclick="checkDefault()">Save Factor Parms</button>
										<button type="button" class="btn-fctr"
											style="width: 160px; margin-left: 10px;"
											onclick="addJobDirect('SAVE_FACTORS_13')">Save
											Period 13 Factors</button> <s:textfield
											name="lastExSavePeriod13FactorsDt" disabled="true"
											readonly="true" cssClass="textarea-stamp"
											style="width: 200px; height:20px;" />
									</td>
								</tr>
							</TABLE>
						</fieldset>
					</TD>
					<!-- RIGHT PANEL: Factor Job Queue Management Buttons & Routing -->
					<TD valign="top">
						<fieldset style="text-align: center; padding: 10px; height: 100%;">
							<table border="0" cellpadding="3" cellspacing="0">
								<tr>
									<td colspan="2" bgcolor="#b4d8f4">
										<p class="tableHeading" style="margin: 4px;">Factor Jobs</p>
									</td>
								</tr>
								<tr>
									<td valign="top" style="padding-top: 10px;">
										<TABLE border="0" cellpadding="2" cellspacing="0">
											<tr>
												<td><button type="button"
														class="btn-fctr btn-fixed-width"
														onclick="addJobDirect('APPLY_MAINTENANCE')">Apply
														Maintenance</button></td>
											</tr>
											<tr>
												<td><button type="button"
														class="btn-fctr btn-fixed-width"
														onclick="addJobDirect('BUILD_ASR_TREE')">Build
														Tree</button></td>
											</tr>
											<tr>
												<td><button type="button"
														class="btn-fctr btn-fixed-width" onclick="checkCCS(1)">Calculate
														Factors</button></td>
											</tr>
											<tr>
												<td><button type="button"
														class="btn-fctr btn-fixed-width" onclick="checkCCS(2)">Resend
														Factors</button></td>
											</tr>
											<tr>
												<td><button type="button"
														class="btn-fctr btn-fixed-width" onclick="checkCCS(3)">Send
														All/Sel Factors</button></td>
											</tr>
										</TABLE>
									</td>
									<td valign="top" style="padding-left: 10px; padding-top: 10px;">
										<fieldset style="padding: 5px; text-align: left;">
											<table border="0" cellpadding="2" cellspacing="0">
												<tr>
													<td align="center" style="border-bottom: 1px solid #999;"><u>Send
															To</u></td>
												</tr>
												<tr>
													<td>SCR <s:checkbox name="rgmSystemSelected" /></td>
												</tr>
												<tr>
													<td>RTC <s:checkbox name="rtcSystemSelected" /></td>
												</tr>
												<tr>
													<td>RBB <s:checkbox name="rbbSystemSelected" /></td>
												</tr>
												<tr>
													<td>RBL <s:checkbox name="rblSystemSelected" /></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
							</table>
						</fieldset>
					</TD>
				</TR>

				<!-- LOWER LAYOUT ROW: Comprehensive Reports Grid & Exports Block -->
				<TR>
					<!-- Reports Layout Sub-Grid Matrix -->
					<TD valign="top">
						<fieldset style="padding: 10px;">
							<table border="0" cellpadding="3" cellspacing="4" width="100%">
								<tr>
									<td colspan="3" bgcolor="#b4d8f4"><p class="tableHeading"
											style="margin: 4px;">Reports</p></td>
								</tr>
								<!-- Row 1 -->
								<tr>
									<td width="33%"><button type="button" class="btn-fctr"
											onclick="addJobDirect('ASR_TREE')">ASR Tree</button></td>
									<td width="33%"><button type="button" class="btn-fctr"
											onclick="addJobDirect('BP_VS_TPSS_RPT1_PRE')">BP vs
											TPSS Comparison</button></td>
									<td width="34%"><button type="button" class="btn-fctr"
											onclick="addJobDirect('AUDIT_FACTORS')">Factor Audit</button></td>
								</tr>
								<!-- Row 2 -->
								<tr>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('BLNDD_SUMMRY')">Blended
											Factor Summary</button></td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobWithRestrictions('PRINT_DUB_SUP')">Dubious
											Sup Aff Usages</button></td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('PRINT_INVCUR')">Invalid
											Currency Codes</button></td>
								</tr>
								<!-- Row 3 -->
								<tr>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('PRINT_BP_FRZ')">BPlCost
											Freeze Cost Only</button></td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('DUP_RPT01')">Dup Reporting
											Products</button></td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('PRINT_MISM')">Mismatch ASR vs
											BPlCost</button></td>
								</tr>
								<!-- Row 4 -->
								<tr>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('PRINT_BPCOST')">BPlCost Set</button></td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('PRINT_EXCEPT')">Exception Set</button></td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('MISS_FACTOR')">Missing
											Factors</button></td>
								</tr>
								<!-- Row 5 -->
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td><button type="button" class="btn-fctr"
											onclick="addJobDirect('PRINT_VAL_AD')">Negative
											Value Added</button></td>
								</tr>
							</table>
						</fieldset>
					</TD>

					<!-- SIDE PANEL: Exports Data Extraction Handling -->
					<TD valign="top">
						<fieldset style="padding: 10px; height: 100%;">
							<table border="0" cellpadding="3" cellspacing="2" width="100%">
								<tr>
									<td bgcolor="#b4d8f4"><p class="tableHeading"
											style="margin: 4px;">Exports</p></td>
								</tr>
								<tr>
									<td align="center" style="padding-top: 10px;">
										<button type="button" class="btn-fctr btn-fixed-width"
											onclick="addJobDirect('SEND_CCSH')">Send to CCS</button>
									</td>
								</tr>
								<tr>
									<td align="center" style="padding-top: 5px;"><s:select
											name="ccsType"
											list="#{'A':'Actual', 'P':'Prelim', 'F':'Final'}"
											cssClass="commandOption" style="width: 120px;" /></td>
								</tr>
							</table>
						</fieldset>
					</TD>
				</TR>
			</TABLE>
		</s:form>
	</div>

	<script type="text/javascript">
		function deleteModel(model) {
			if (confirm("Are you sure that you would like to delete "
					+ model
					+ "?\nThis will delete all attributes and can not be undone.")) {
				window.location.href = "deleteModel.action?model=" + model;
			}
		}

		function addJobDirect(jobNameValue) {
		    document.getElementById("cmd").value = "ADD_JOB";
		    document.getElementById("jobName").value = jobNameValue;
		    var visualCheckbox = document.getElementById("chkRestrictOutput"); 
		    
		    if (visualCheckbox) {
		        document.getElementById("chkRestrict").checked = visualCheckbox.checked;
		    } else {
		        document.getElementById("chkRestrict").checked = false;
		    }
		    
		    document.getElementById("mngFactorsForm").submit();
		}

		function addJobWithRestrictions(jobNameValue) {
		    document.getElementById("cmd").value = "ADD_JOB";
		    document.getElementById("jobName").value = jobNameValue;
		    document.getElementById("chkRestrict").checked = true;
		    document.getElementById("mngFactorsForm").submit();
		}


		function checkCCS(x) {
			if (x == 1)
				addJobDirect("CALCULATE_FACTORS");
			else if (x == 2) {
				if (confirm("Are you sure you want to add this SEND_FACTORS job?"))
					addJobDirect('SEND_FACTORS');
			} else if (x == 3) {
				if (confirm("Are you sure you want to add this SEND_ALL_FACTORS job?"))
					addJobDirect('SEND_ALL_FACTORS');
			}
		}

		function checkDefault() {
			var form = document.getElementById("mngFactorsForm");
			var msg = "";
			var flag = true;

			if (form.elements["planUnitsSelected"].value == "0") {
				flag = false;
				msg += "Please Select a Plan Units.\n";
			}
			if (form.elements["actualUnitsSelected"].value == "0") {
				flag = false;
				msg += "Please Select an Actual Units.\n";
			}
			if (form.elements["selCostRate"].value == "") {
				flag = false;
				msg += "Please Select a Factor Rate.\n";
			}
			if (form.elements["selFactorRate"].value == "") {
				flag = false;
				msg += "Please Select a Std Cost Rate.\n";
			}
			if (form.elements["selRevisionRate"].value == "") {
				flag = false;
				msg += "Please Select a Revision Rate.\n";
			}

			if (flag) {
				document.getElementById("cmd").value = 'SET_FACTOR_MODEL_PARMS';
				form.submit();
			} else {
				msg += "\nAre you sure you want to proceed... \n";
				if (confirm(msg)) {
					document.getElementById("cmd").value = 'SET_FACTOR_MODEL_PARMS';
					form.submit();
				}
			}
		}
		function compModel() {
			var form = document.getElementById("mngFactorsForm");
			document.getElementById("cmd").value = 'COMP_MODEL';
			form.submit();
		}
	</script>
	<%@ include file="/include/footer.jsf"%>