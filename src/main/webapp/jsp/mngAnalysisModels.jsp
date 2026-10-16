<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="abbott" uri="/WEB-INF/taglib/abbott.tld"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%!String pageTitle = "Factor Analysis Model Management";%>


<%@ include file="/include/header.jsf"%>
<s:set var="TCGMUser" value="#session['TCGMUser']" scope="page" />


<style type="text/css">
.dataDisp {
	font-family: Arial, Helvetica, sans-serif !important;
	font-size: 14px !important;
	font-weight: normal !important;
	color: #000000 !important;
	text-align: left !important;
	line-height: 1.4 !important;
	padding: 6px 8px !important;
	display: block !important;
}

.dataDisp b, .dataDisp strong {
	font-family: Arial, Helvetica, sans-serif !important;
	font-size: 14px !important;
	font-weight: bold !important;
	color: #1A365D !important;
}

fieldset {
	border: 1px solid #B0C4DE;
	border-radius: 4px;
	background-color: #FFFFFF;
	padding: 12px;
	margin-bottom: 15px;
	box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
}

.tableHeading {
	background-color: #A4C2E6;
	color: #1A365D;
	font-family: Arial, sans-serif;
	font-weight: bold;
	font-size: 13px;
	padding: 6px 10px;
	border-bottom: 1px solid #7FA2CE;
}

.btn-html {
	display: inline-block;
	width: 210px;
	box-sizing: border-box;
	background: linear-gradient(to bottom, #FFE866 0%, #FFD700 100%);
	color: #000000;
	font-family: "Arial", sans-serif;
	font-weight: bold;
	font-size: 12px;
	font-style: italic;
	text-align: center;
	padding: 5px 8px;
	margin: 3px 0;
	border: 1px solid #C4A700;
	border-radius: 4px;
	cursor: pointer;
	box-shadow: 0px 3px 0px #B0B0B0, 0px 4px 3px rgba(0, 0, 0, 0.3);
	transition: transform 0.05s ease, box-shadow 0.05s ease;
}

.btn-html:hover {
	background: linear-gradient(to bottom, #FFF099 0%, #FFE033 100%);
}

.btn-html:active {
	transform: translateY(2px);
	box-shadow: 0px 1px 0px #B0B0B0, 0px 2px 1px rgba(0, 0, 0, 0.2);
}

.btn-close, .btn-delete, .btn-create, .btn-calculate, .btn-open {
	width: 140px;
	font-size: 11px;
}

select#modelSelected {
	font-family: Arial, sans-serif;
	font-size: 12px;
	border: 1px solid #7F9DB9;
	padding: 2px;
	background-color: #FFFFFF;
}
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<c:set var="pageTitle" value="Factor Analysis Model Management"
		scope="request" />
	<%@ include file="/include/masthead.jsf"%>


	<s:form id="mngAnalysisModelsForm" name="mngAnalysisModelsForm"
		action="mngAnalysisModels" method="post" theme="simple">
		<div id="divToHide">
			<div id="strutsActionErrorsContainer"
				style="display: block; text-align: center;">
				<%@ include file="/include/errorDisplay.jsf"%>
			</div>

			<%@ include file="/include/jobOptions.jsf"%>

			<s:hidden name="showAnalModel" id="showAnalModel" />

			<TABLE class="tableCommand">
				<s:if test='showAnalModel == "open"'>
					<TR>
						<TD valign="top">
							<fieldset style="text-align: center;">
								<legend class="commandOptionLabel"></legend>
								<TABLE>
									<tr>
										<td colspan="3" class="tableHeading" width="50%">Factor
											Analysis Models</td>
										<td class="tableHeading" width="50%">Model Details</td>
									</tr>
									<tr>
										<td width="20">&nbsp;</td>
										<td width="150"><s:select size="6" name="modelSelected"
												id="modelSelected" list="models" listKey="modelId"
												listValue="name" onchange="updateDetails()" /> <BR>&nbsp;
											<abbott:securePage
												userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
												requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
												comparisonType="=">
												<a class="CmdSm"
													href='javascript:showClosedModelDetails("closed")'>Show
													Closed Model</a>
											</abbott:securePage></td>
										<td width="80" align="left">
											<button type="button" class="btn-html btn-close"
												onclick="closeModel(document.getElementById('modelSelected'))">Close
												Model</button> <br> <br>
											<button type="button" class="btn-html btn-delete"
												onclick="deleteModel(document.getElementById('modelSelected'))">Delete
												Model</button> <br> <br>
											<button type="button" class="btn-html btn-create"
												onclick="location.href='createAnalysisModel.action'">Create
												New Model</button> <br> <br>
											<button type="button" class="btn-html btn-calculate"
												onclick="addJob('ANL_CALC')">Calculate Analysis</button>
										</td>
										<td width="330"><div id="divModelDetail" class="dataDisp"></div></td>
									</tr>
								</TABLE>
							</fieldset>
						</TD>
					</TR>
				</s:if>
				<s:if test='showAnalModel == "closed"'>
					<TR>
						<TD valign="top">
							<fieldset style="text-align: center;">
								<legend class="commandOptionLabel"></legend>
								<TABLE>
									<tr>
										<td colspan="3" class="tableHeading" width="50%">Closed
											Factor Analysis Models</td>
										<td class="tableHeading" width="50%">Model Details</td>
									</tr>
									<tr>
										<td width="20">&nbsp;</td>
										<td width="150"><s:select size="6" name="modelSelected"
												id="modelSelected" list="models" listKey="modelId"
												listValue="name" onchange="updateDetails()" /> <BR>&nbsp;
											<abbott:securePage
												userAccessLevel="${sessionScope.TCGMUser.role.accessLevel}"
												requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>"
												comparisonType="=">
												<a class="CmdSm"
													href='javascript:showClosedModelDetails("open")'>Show
													Open Model</a>
											</abbott:securePage></td>
										<td width="80" align="left" valign="top">
											<button type="button" class="btn-html btn-open"
												onclick="openModel(document.getElementById('modelSelected'))">Open
												Model</button> <br>
										</td>
										<td width="330"><div id="divModelDetail" class="dataDisp"></div></td>
									</tr>
								</TABLE>
							</fieldset>
						</TD>
					</TR>
				</s:if>
			</TABLE>

			<br> <br>
			<TABLE class="tableCommand">
				<TR>
					<TD valign="top">
						<fieldset style="text-align: center;">
							<legend class="commandOptionLabel"></legend>
							<TABLE>
								<tr>
									<td colspan="3" class="tableHeading" width="50%">HQ
										Reports</td>
								</tr>
								<tr valign="top">
									<td width="20">&nbsp;</td>
									<td width="50">
										<table width="100" cellpadding="4" cellspacing="2">
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_EXSUMMRY')">Sales Extended
														Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_WSUMMRY')">Sales World
														Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_XSEC_SUM')">Sales Sector
														Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_ARSUMMRY')">Sales Area
														Region Smry</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_SAMPEX')">Sample Extended
														Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_SAMPW')">Sample World Smry</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_SAMSEC_SUM')">Sample Sector
														Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_SAMPAR')">Sample Area Region
														Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_PUSRCSUM')">Extended Summary
														by Src</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_PUSUMMRY')">Per Unit Summary</button></td>
											</tr>
											<tr>
												<td width="225"><button type="button" class="btn-html"
														onclick="addJob('ANL_REJECTS')">Unable To Analyze</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="addJob('ANL_DETAIL')">Detail Analysis</button></td>
											</tr>
										</table>
									</td>
							</TABLE>
						</fieldset>
					</TD>
					<TD valign="top">
						<fieldset style="text-align: center;">
							<legend class="commandOptionLabel"></legend>
							<TABLE>
								<tr>
									<td colspan="3" class="tableHeading" width="50%">Publish
										Reports</td>
								</tr>
								<tr>
									<td width="20">&nbsp;</td>
									<td width="238">
										<table border="0">
											<tr>
												<td><button type="button" class="btn-html"
														onclick="confirmAndAddJob('ANL_PSUM_PUB')">Pub
														Per Unit Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="confirmAndAddJob('ANL_XTSM_400')">Pub
														Sales Ext Summary</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="confirmAndAddJob('ANL_PSUM_400')">Pub
														Sales Sector Smry</button></td>
											</tr>
											<tr>
												<td><button type="button" class="btn-html"
														onclick="confirmAndAddJob('ANL_PSSES_AREA')">Pub
														Sales Samples Ext Smry Area</button></td>
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
									<td colspan="3" class="tableHeading" width="50%">Misc
										Reports</td>
								</tr>
								<tr>
									<td><button type="button" class="btn-html"
											onclick="changeCmdAndSubmit('FETCH_TEST_HEADERS')">Fetch
											Test Header Labels</button></td>
								</tr>
								<tr>
									<td><button type="button" class="btn-html"
											onclick="changeCmdAndSubmit('FETCH_PROD_HEADERS')">Fetch
											Production Labels</button></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
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
		var detailArray = <s:property value="modelDetailArrayString" escapeHtml="false" />;
		function deleteModel(se) {
			if (!se || se.value == 0) {
				alert("You must select a model to delete.");
				return;
			}
			var modelName = se.options[se.selectedIndex].text;
			if (confirm("Are you sure that you would like to delete "
					+ modelName
					+ "?\nThis will delete all attributes and can not be undone.")) {
				var form = document.getElementById('mngAnalysisModelsForm');
				var cmdElements = form.elements['cmd'];
				if (cmdElements) {
					if (cmdElements.length) {
						for (var i = cmdElements.length - 1; i >= 0; i--) {
							cmdElements[i].value = 'DELETE_MODEL';
						}
					} else {
						cmdElements.value = 'DELETE_MODEL';
					}
				}

				form.action = "deleteAnalysisModel.action";
				form.submit();
			}
		}

		function closeModel(se) {
			if (!se || se.value == 0) {
				alert("You must select a model to close.");
				return;
			}
			var modelName = se.options[se.selectedIndex].text;
			if (confirm("Are you sure that you would like to close "
					+ modelName + "?")) {
				var form = document.getElementById('mngAnalysisModelsForm');
				var cmdElements = form.elements['cmd'];
				if (cmdElements) {
					if (cmdElements.length) {
						for (var i = cmdElements.length - 1; i >= 0; i--) {
							cmdElements[i].value = 'CLOSE_MODEL';
						}
					} else {
						cmdElements.value = 'CLOSE_MODEL';
					}
				}

				form.action = "closeAnalysisModel.action";
				form.submit();
			}
		}

		function openModel(se) {
			if (!se || se.value == 0) {
				alert("You must select a model to open.");
				return;
			}
			var modelName = se.options[se.selectedIndex].text;
			if (confirm("Are you sure that you would like to open " + modelName
					+ "?")) {
				var form = document.getElementById('mngAnalysisModelsForm');

				var cmdElements = form.elements['cmd'];
				if (cmdElements) {
					if (cmdElements.length) {
						for (var i = cmdElements.length - 1; i >= 0; i--) {
							cmdElements[i].value = 'OPEN_MODEL';
						}
					} else {
						cmdElements.value = 'OPEN_MODEL';
					}
				}

				form.action = "mngAnalysisModels.action";
				form.submit();
			}
		}

		function updateDetails() {
			var selectEl = document.getElementById('modelSelected');
			var detailDiv = document.getElementById('divModelDetail');

			if (selectEl && detailDiv) {
				var selectedVal = selectEl.value;

				if (selectedVal && selectedVal != 0) {
					document.body.style.cursor = 'wait';
					fetch('tcgmAjax.action?cascadingCmd=ModelInfo&cascadingVal=' + selectedVal + '&cascadingSecVal=ANALYSIS')
						.then(function(response) {
							if (!response.ok) {
								throw new Error('Network response was not ok');
							}
							return response.text();
						})
						.then(function(htmlResult) {
							detailDiv.innerHTML = htmlResult;
						})
						.catch(function(error) {
							console.error('Error fetching model specific details:', error);
							detailDiv.innerHTML = "<span style='color:red;'>Error loading model details.</span>";
						})
						.finally(function() {
							document.body.style.cursor = 'default';
						});
				} else {
					detailDiv.innerHTML = "";
				}
			}
		}

		function showClosedModelDetails(stat) {
			document.getElementById('showAnalModel').value = stat;
			var form = document.getElementById('mngAnalysisModelsForm');
			form.action = 'mngAnalysisModels.action';
			form.submit();
		}

		function addJob(jobName) {
			var form = document.getElementById('mngAnalysisModelsForm');

			var selectEl = document.getElementById('modelSelected');
			if (!selectEl || selectEl.value === "" || selectEl.value == 0) {
				var errorDiv = document
						.getElementById('strutsActionErrorsContainer');
				if (errorDiv) {
					errorDiv.innerHTML = "<div style='color: #FF0000; font-family: Arial, Helvetica, sans-serif; font-size: 13px; font-weight: bold; text-align: left; margin: 10px 0;'>• A model must be selected before using this command.</div>";
					errorDiv.style.display = "block";
				} else {
					alert("A model must be selected before using this command.");
				}
				return;
			}

			if (form) {
				var cmdElements = form.elements['cmd'];
				if (cmdElements) {
					if (cmdElements.length) {
						for (var i = cmdElements.length - 1; i >= 0; i--) {
							cmdElements[i].value = 'ADD_JOB';
						}
					} else {
						cmdElements.value = 'ADD_JOB';
					}
				}

				var jobElements = form.elements['jobName'];
				if (jobElements) {
					if (jobElements.length) {
						for (var j = jobElements.length - 1; j >= 0; j--) {
							jobElements[j].value = jobName;
						}
					} else {
						jobElements.value = jobName;
					}
				}

				form.submit();
			}
		}

		function confirmAndAddJob(jobName) {
			var selectEl = document.getElementById('modelSelected');
			if (!selectEl || selectEl.value === "" || selectEl.value == 0) {
				addJob(jobName);
				return;
			}
			var modelName = selectEl.options[selectEl.selectedIndex].text;
			if (confirm("Are you sure you want to run this publish report for "
					+ modelName + "?")) {
				addJob(jobName);
			}
		}

		function changeCmdAndSubmit(cmdName) {
			var form = document.getElementById('mngAnalysisModelsForm');
			if (form) {
				var cmdElements = form.elements['cmd'];
				if (cmdElements) {
					if (cmdElements.length) {
						for (var i = cmdElements.length - 1; i >= 0; i--) {
							cmdElements[i].value = cmdName;
						}
					} else {
						cmdElements.value = cmdName;
					}
				}
				form.submit();
			}
		}
	</script>
	<%@ include file="/include/footer.jsf"%>