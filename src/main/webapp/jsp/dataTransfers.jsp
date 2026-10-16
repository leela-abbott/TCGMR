<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%! String pageTitle="Data Transfer and Report Publication"; %>
<%@ include file="/include/header.jsf"%>

<style type="text/css">
/* Table heading sections matching old JSP header blue coloring bar layout exactly */
.tableHeadingCustom {
	background-color: #A9C6EB;
	color: #0F3A82;
	font-family: Arial, sans-serif;
	font-size: 12px;
	font-weight: bold;
	padding: 4px 8px;
	border-bottom: 1px solid #7F9DB9;
}

.tableCommandCustom {
	width: 100%;
	margin-top: 10px;
	margin-bottom: 15px;
	border-collapse: collapse;
}

/* Form Ingress Controls styling matches the standard input appearance */
.app-input, .commandOption {
	border: 1px solid #7F9DB9;
	padding: 2px 4px;
	font-family: Arial, sans-serif;
	font-size: 12px;
	color: #000;
	box-sizing: border-box;
}

.dropdownWide {
	width: 580px;
	font-family: Arial, sans-serif;
	font-size: 11px;
}

/* Recreated pill-shaped styling based precisely on the old interface reference */
.app-btn {
	background: linear-gradient(to bottom, #FFE869 0%, #F5D312 40%, #E6C200 100%);
	border: 1px solid #B09500;
	border-radius: 1px;
	color: #0F3A82;
	font-family: Arial, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	padding: 3px 0;
	width: 140px;
	/* Forces uniform wide width matching the legacy graphics dimensions */
	cursor: pointer;
	box-shadow: 2px 2px 3px rgba(0, 0, 0, 0.4);
	display: inline-block;
	text-align: center;
	text-decoration: none;
	white-space: nowrap;
}

.app-btn:hover {
	background: linear-gradient(to bottom, #FFF094 0%, #FADF41 40%, #F5D312 100%);
}

.app-btn:active {
	background: #E6C200;
	box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.5);
}

/* Horizontal alignment rows targeting publication parameters explicitly */
.btn-row-container {
	display: flex;
	flex-direction: row;
	flex-wrap: nowrap;
	gap: 12px;
	align-items: center;
	margin-left: 20px;
}

.report-row-layout {
	display: flex;
	align-items: center;
	margin-bottom: 6px;
}

.report-label-width {
	width: 90px;
	font-family: Arial, sans-serif;
	font-size: 11px;
	font-weight: bold;
	color: #0F3A82;
	line-height: 13px;
}

#dataTransfersForm {
	padding: 50px;
}
</style>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
	onload="if(typeof init === 'function') init();">
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form id="dataTransfersForm" name="dataTransfersForm"
		action="dataTransfers" method="post" enctype="multipart/form-data"
		theme="simple">

		<s:hidden name="cmd" id="cmd" />
		<s:hidden name="jobName" id="jobName" />
		<s:hidden name="treeFilename" id="treeFilename" value="INBOUND1" />

		<div id="divToHide">
			<%@ include file="/include/jobOptions.jsf"%>
			<table class="tableCommandCustom">
				<tr>
					<td class="tableHeadingCustom">Transfer Jobs</td>
				</tr>
				<tr>
					<td align="left" style="padding: 10px 0 10px 30px;">
						<table cellpadding="4" cellspacing="2">
							<tr>
								<td>
									<div class="commandOption" style="margin-right: 80px;">
										Period:
										<s:select name="sendTreeToMVSPeriod" list="monthListNumber"
											listKey="value" listValue="label" headerKey="13"
											headerValue="13" cssClass="commandOption" theme="simple" />
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="btn-row-container" style="margin-left: 0;">
										<button type="button" class="app-btn"
											onclick="confirmAndAddJob(document.dataTransfersForm, 'SEND_TREE_TO_MVS')">Send
											Tree To MVS</button>
										<button type="button" class="app-btn"
											onclick="confirmAndAddJob(document.dataTransfersForm, 'SEND_PRICING')">Send
											Pricing Data</button>
									</div>
								</td>
								<td width="325" height="45" class="hidden">
									&nbsp;&nbsp;Tree File Name:&nbsp;&nbsp; <s:textfield
										name="treeFilename"
										value="%{treeFilename != null ? treeFilename : 'INBOUND1'}"
										maxlength="8" cssClass="hidden" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table class="tableCommandCustom">
				<tr>
					<td class="tableHeadingCustom">Report Publication</td>
				</tr>
				<tr>
					<td align="left" style="padding: 10px 0 10px 30px;">
						<div style="padding: 5px 0;">
							<!-- Factor Inquiry Grid Row Layout Definition -->
							<div class="report-row-layout">
								<div class="report-label-width">
									Factor<br>Inquiry
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_FCT_RPT')">Generate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'UNGEN_FCT_RPT')">UnGenerate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'PUB_FCT_RPT')">Publish</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'UNPUB_FCT_RPT')">UnPublish</button>
								</div>
							</div>

							<!-- Factor Report Row Layout -->
							<div class="report-row-layout">
								<div class="report-label-width">
									Factor<br>Report
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_FCT_REPORT')">Generate
										Report</button>
								</div>
							</div>

							<!-- Net Cost Inquiry Row Layout -->
							<div class="report-row-layout">
								<div class="report-label-width">
									Net Cost<br>Inquiry
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_NET_COST')">Generate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'UNGEN_NET_COST')">UnGenerate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'PUB_NET_COST')">Publish</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'UNPUB_NET_COST')">UnPublish</button>
								</div>
							</div>

							<!-- Net Cost Report Row Layout -->
							<div class="report-row-layout">
								<div class="report-label-width">
									Net Cost<br>Report
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_NET_COST_RPT')">Generate
										Report</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'NETCOST_RPT_PUB_PRE')">Publish
										Report</button>
								</div>
							</div>

							<!-- Factor Changes Row Layout -->
							<div class="report-row-layout">
								<div class="report-label-width">
									Factor<br>Changes
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_FCT_CHGS_RPT')">Generate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'PUB_FCT_CHGS')">Publish</button>
								</div>
							</div>

							<!-- Factor Summary Row Layout -->
							<div class="report-row-layout">
								<div class="report-label-width">
									Factor<br>Summary
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_FACTR_SUMMRY')">Generate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'PUB_FCT_SMRY')">Publish</button>
								</div>
							</div>

							<!-- TCGM Changes Row Layout -->
							<div class="report-row-layout">
								<div class="report-label-width">
									TCGM<br>Changes
								</div>
								<div class="btn-row-container">
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'GEN_TCGM_CHGS_RPT')">Generate</button>
									<button type="button" class="app-btn"
										onclick="addJob(document.dataTransfersForm, 'PUB_TCGM_CHGS')">Publish</button>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>

			<table class="tableCommandCustom">
				<tr>
					<td colspan="2" style="padding: 10px 0 10px 30px;">
						<table cellpadding="4" cellspacing="2">
							<tr>
								<td class="commandOptionLabel"
									style="width: 90px; color: #0F3A82; font-weight: bold; font-family: Arial; font-size: 11px;">Current
									Units:</td>
								<td>
									<!-- listKey points to datasetTableId and listValue points to datasetNameLogStamp matching old logic exactly -->
									<s:select name="currUnitsSelected" list="unitSets"
										listKey="datasetTableId" listValue="datasetNameLogStamp"
										cssClass="app-input dropdownWide" theme="simple" />
								</td>
							</tr>
							<tr>
								<td class="commandOptionLabel"
									style="width: 90px; color: #0F3A82; font-weight: bold; font-family: Arial; font-size: 11px;">Saved
									Units:</td>
								<td><s:select name="savedUnitsSelected" list="savedSets"
										listKey="datasetTableId" listValue="datasetNameLogStamp"
										cssClass="app-input dropdownWide" theme="simple" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tableHeadingCustom">TPSS Upload</td>
				</tr>
				<tr>
					<td style="padding: 15px 0 15px 30px;">
						<table cellpadding="4" cellspacing="2">
							<tr>
								<td><s:file name="upload" id="uploadFileControl"
										cssClass="app-input" theme="simple" />
									<button type="button" class="app-btn"
										style="width: 100px; font-style: normal; margin-left: 10px;"
										onclick="setUpload()">Upload</button></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</s:form>

	<script type="text/javascript">
function setUpload() {
    var fileControl = document.getElementById("uploadFileControl");
    var fileName = fileControl ? fileControl.value : "";
    
    if (validateFile(fileName)) {
        var form = document.dataTransfersForm;
        form.cmd.value = "upload";
        form.action = "dataTransfers.action";
        form.submit();
    } else {
        alert("Invalid File Name");
    }
}

function validateFile(fileName) {
    if (!fileName) {
        return false;
    }
    var dotPosition = fileName.lastIndexOf(".");
    var alertMessage = "";
    if (dotPosition > 0) {
        var ext = fileName.substring(dotPosition + 1).toLowerCase();
        if (ext !== 'txt') {
            alertMessage = alertMessage + "Invalid File Name\n";
        }
    } else {
        alertMessage = alertMessage + "Invalid File\n";
    }
    if (alertMessage.length > 0) {
        return false;
    } else {
        return true;
    }
}
</script>
	<%@ include file="/include/footer.jsf"%>