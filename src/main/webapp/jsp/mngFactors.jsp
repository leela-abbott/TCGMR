<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%!String pageTitle = "Calculate and Manage Factors";%>

<%@ include file="/include/header.jsf"%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>
	<div id="divToHide">

		<s:form id="mngFactorsForm" action="mngFactors" method="post"
			theme="simple">
			<%-- Command flag controls --%>
			<s:hidden name="cmd" id="cmd" />
			<s:hidden name="jobName" id="jobName" />

			<%@ include file="/include/jobOptions.jsf"%>

			<TABLE class="tableCommand">
				<TR>
					<TD valign="top">
						<fieldset style="text-align: center;">
							<legend class="commandOptionLabel"></legend>
							<TABLE>
								<tr>
									<td height="0" colspan="5" bgcolor="#b4d8f4">
										<p class="tableHeading">Factor Settings</p>
									</td>
								</tr>
								<tr>
									<td width="130" height="0" class="commandOptionLabel">Plan
										Units</td>
									<td height="0"><s:select name="planUnitsSelected"
											list="unitSets" listKey="datasetTableId"
											listValue="datasetNameLogStamp" headerKey="0"
											headerValue="<none>" cssClass="commandOption" /></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td height="0" class="commandOptionLabel">Actual Units</td>
									<td height="0"><s:select name="actualUnitsSelected"
											list="unitSets" listKey="datasetTableId"
											listValue="datasetNameLogStamp" headerKey="0"
											headerValue="<none>" cssClass="commandOption" /></td>
								</tr>
								<tr>
									<td height="0" class="commandOptionLabel">Factor Rate</td>
									<td height="0"><s:select name="selCostRate"
											list="rateSets" listKey="datasetTableId"
											listValue="datasetName" headerKey="" headerValue="<none>"
											cssClass="commandOption" /></td>
								</tr>
								<tr>
									<td height="0" class="commandOptionLabel">Std Cost Rate</td>
									<td height="0"><s:select name="selFactorRate"
											list="rateSets" listKey="datasetTableId"
											listValue="datasetName" headerKey="" headerValue="<none>"
											cssClass="commandOption" /></td>
								</tr>
								<tr>
									<td height="0" class="commandOptionLabel">Revision Rate</td>
									<td width="73" height="0"><s:select name="selRevisionRate"
											list="rateSets" listKey="datasetTableId"
											listValue="datasetName" headerKey="" headerValue="<none>"
											cssClass="commandOption" /></td>
								</tr>
								<tr>
									<td class="commandOptionLabel">Memo:</td>
									<td width="242"><s:textarea name="modelLongDesc" cols="68"
											rows="7" onkeyup="return checkLength(this, 425);"
											cssClass="commandOption" /></td>
								</tr>
								<tr>
									<td><a href="javascript:checkDefault()"><button
												type="button"
												style="width: 160px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Save
												Factor Parms</button></a></td>
									<td><a href="javascript:addJobDirect('SAVE_FACTORS_13')"><button
												type="button"
												style="width: 180px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Save
												Period 13 Factors</button></a> <s:textarea name="lastExSavePeriod13FactorsDt"
											disabled="true" readonly="true" /></td>
								</tr>
							</TABLE>
						</fieldset>
					</TD>

					<TD valign="top">
						<fieldset style="width: 70px; text-align: center;">
							<legend class="commandOptionLabel"></legend>
							<table>
								<tr>
									<td height="0" colspan="4" bgcolor="#b4d8f4">
										<p class="tableHeading">Factor Jobs</p>
									</td>
								</tr>
								<tr>
									<td>
										<TABLE>
											<tr>
												<td height="0">&nbsp;</td>
											</tr>
											<tr>
												<td colspan="2"><a
													href="javascript:addJobDirect('APPLY_MAINTENANCE')"><button
															type="button"
															style="width: 150px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 5px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Apply
															Maintenance</button></a></td>
											</tr>
											<tr>
												<td colspan="2"><a
													href="javascript:addJobDirect('BUILD_ASR_TREE')"><button
															type="button"
															style="width: 150px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 5px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Build
															Tree</button></a></td>
											</tr>
											<tr>
												<td colspan="2"><a href="javascript:checkCCS(1)"><button
															type="button" name="btnCalcFactors"
															style="width: 150px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 5px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Calculate
															Factors</button></a></td>
											</tr>
											<tr>
												<td colspan="2"><a href="javascript:checkCCS(2)"><button
															type="button"
															style="width: 150px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 5px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Resend
															Factors</button></a></td>
											</tr>
											<tr>
												<td colspan="2"><a href="javascript:checkCCS(3)"><button
															type="button"
															style="width: 150px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 5px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Send
															All/Sel Factors</button></a></td>
											</tr>
											<tr>
												<td class="commandOptionLabel">&nbsp;</td>
											</tr>
										</TABLE>
									</td>
									<td>
										<fieldset style="width: 70px; text-align: center;">
											<legend class="commandOptionLabel"></legend>
											<table>
												<tr>
													<td class="mntCenter" colspan="2"><u>Send To</u></td>
												</tr>
												<tr>
													<td class="mntCenter">SCR<s:checkbox
															name="rgmSystemSelected" />
												</tr>
												<tr>
													<td class="mntCenter">RTC<s:checkbox
															name="rtcSystemSelected" />
													</td>
												</tr>
												<tr>
													<td class="mntCenter">RBB<s:checkbox
															name="rbbSystemSelected" />
													</td>
												</tr>
												<tr>
													<td class="mntCenter">RBL<s:checkbox
															name="rblSystemSelected" />
													</td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
							</table>
						</fieldset>
					</TD>
				</TR>

				<tr>
					<td>
						<table cellpadding="4" cellspacing="2">
							<tr>
								<td>
									<fieldset style="width: 70px; text-align: center;">
										<legend class="commandOptionLabel"></legend>
										<table cellpadding="4" cellspacing="2">
											<tr>
												<td colspan="3" bgcolor="#b4d8f4"><p
														class="tableHeading">Reports</p></td>
											</tr>
											<tr>
												<td width="250"><a
													href="javascript:addJobDirect('ASR_TREE')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">ASR
															Tree</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('BP_VS_TPSS_RPT1_PRE')"><button
															type="button"
																														style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">BP
															vs TPSS Comparison</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('AUDIT_FACTORS')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Factor
															Audit</button></a></td>
											</tr>
											<tr>
												<td width="250"><a
													href="javascript:addJobDirect('BLNDD_SUMMRY')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Blended
															Factor Summary</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_DUB_SUP')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Dubious
															Sup Aff Usages</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_INVCUR')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Invalid
															Currency Codes</button></a></td>
											</tr>
											<tr>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_BP_FRZ')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">BPlCost
															Freeze Cost Only</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('DUP_RPT01')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Dup
															Reporting Products</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_MISM')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Mismatch
															ASR vs BPlCost</button></a></td>
											</tr>
											<tr>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_BPCOST')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">BPlCost
															Set</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_EXCEPT')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Exception
															Set</button></a></td>
												<td width="250"><a
													href="javascript:addJobDirect('MISS_FACTOR')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Missing
															Factors</button></a></td>
											</tr>
											<tr>
												<td width="250">&nbsp;</td>
												<td width="250">&nbsp;</td>
												<td width="250"><a
													href="javascript:addJobDirect('PRINT_VAL_AD')"><button
															type="button"
															style="width: 100%; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 10px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Negative
															Value Added</button></a></td>
											</tr>
										</table>
									</fieldset>
								</td>
								<td valign="top">
									<fieldset style="width: 70px; text-align: center;">
										<legend class="commandOptionLabel"></legend>
										<table cellpadding="4" cellspacing="2">
											<tr>
												<td colspan="2" bgcolor="#b4d8f4"><p
														class="tableHeading">Exports</p></td>
											</tr>
											<tr>
												<td width="130" height="0" class="commandOptionLabel">
													<a href="javascript:addJobDirect('SEND_CCSH')"><button
															type="button"
															style="width: 110px; height: 24px; background: linear-gradient(to bottom, #ffe169, #f7c11e); color: #002266; border: 1px solid #cca329; font-family: sans-serif; font-size: 11px; font-weight: bold; font-style: italic; cursor: pointer; padding: 0 5px; margin: 0; border-radius: 1px; box-shadow: 0px 2px 3px rgba(0,0,0,0.3); vertical-align: middle; white-space: nowrap;">Send
															to CCS</button></a><br> <%-- Modernized static map select rendering for Struts 7 --%>
													<s:select name="ccsType"
														list="#{'A':'Actual', 'P':'Prelim', 'F':'Final'}"
														cssClass="commandOption" />
												</td>
											</tr>
										</table>
									</fieldset>
								</td>
							</tr>
						</table>
					</td>
					<td></td>
				</tr>
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
			var form = document.getElementById("mngFactorsForm");
			document.getElementById("cmd").value = "ADD_JOB";
			document.getElementById("jobName").value = jobNameValue;
			form.submit();
		}

		function checkCCS(x) {
			if (x == 1) {
				addJobDirect("CALCULATE_FACTORS");
			} else if (x == 2) {
				if (confirm("Are you sure you want to add this SEND_FACTORS job?")) {
					addJobDirect('SEND_FACTORS');
				}
			} else if (x == 3) {
				if (confirm("Are you sure you want to add this SEND_ALL_FACTORS job?")) {
					addJobDirect('SEND_ALL_FACTORS');
				}
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
				msg += "Please Select a Actual Units.\n";
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