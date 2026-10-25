<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="abbott.ai.tcgm.entities.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%!String pageTitle = "Miscellaneous Analysis";%>

<%
String jbStatus = "";
if (session.getAttribute("jobStatus") != null) {
	jbStatus = (String) session.getAttribute("jobStatus");
}
%>

<%@ include file="/include/header.jsf"%>


<style type="text/css">
/* Target the body to cover unclassed text elements */
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	font-size: 11px;
	color: #003399; /* Changes base text color to production blue */
}

/* Replicated Yellow Button Style from the previous fix */
.tcgm-btn {
	background: #FFF066;
	background: linear-gradient(to bottom, #FFFFFF 0%, #FFEA33 25%, #E6C300 100%);
	color: #000000; /* Button text stays black for readability */
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	font-style: italic;
	padding: 4px 12px;
	display: inline-block;
	cursor: pointer;
	text-align: center;
	box-sizing: border-box;
	white-space: normal;
	border-top: 1px solid #FFF699;
	border-left: 1px solid #FFF699;
	border-right: 2px solid #A38A00;
	border-bottom: 2px solid #806C00;
	border-radius: 2px;
	box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.15);
}

.tcgm-btn:active {
	background: linear-gradient(to bottom, #E6C300 0%, #FFEA33 70%, #FFFFFF 100%);
	border-top: 2px solid #806C00;
	border-left: 2px solid #806C00;
	border-right: 1px solid #FFF699;
	border-bottom: 1px solid #FFF699;
	box-shadow: inset 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.tcgm-btn:hover {
	background: linear-gradient(to bottom, #FFFFFF 0%, #FFF04D 25%, #F5D000 100%);
}

/* Modified to force all form labels and structural sections to use production blue text */
.commandOption {
	font-family: Arial, sans-serif;
	font-size: 11px;
	color: #003399 !important; /* Forces the deep dark blue text color */
	font-weight: bold;         /* Matches production label layout styling */
}

/* Keeps field headers looking crisp and clean */
.tableHeading {
	font-family: Arial, sans-serif;
	font-size: 12px;
	font-weight: bold;
	background-color: #B4D3FC;
	color: #002266; /* Bold header dark text blue */
	padding: 4px;
}
</style>


<body onload="init();" style="margin: 0; padding: 0;">
	<c:set var="pageTitle" value="Miscellaneous Analysis" scope="request" />
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form id="miscAnalysisForm" name="miscAnalysisForm"
		action="miscAnalysis.action" namespace="/" method="POST"
		theme="simple">
		<div id="divToHide">
			<%@ include file="/include/jobOptions.jsf"%>

			<!-- ================= SECTION 1: Deferred / Earned Margin Settings ================= -->
			<fieldset style="margin-left: 50px; width: 685px; text-align: left;">
				<table width="685" align="left" cellpadding="2" cellspacing="0">
					<tr>
						<td colspan="7" class="tableHeading">Deferred / Earned Margin
							Settings</td>
					</tr>
					<tr valign="top">
						<td width="27" height="23"></td>
						<td width="111" align="right" class="commandOption">Units</td>
						<td width="156"><s:select name="selExUnitSet"
								id="selExUnitSet" list="unitSets" listKey="datasetTableId"
								listValue="datasetNameLogStamp" cssClass="commandOption"
								onchange="callFunc();" /></td>
						<td width="65" align="right" class="commandOption">Period</td>
						<td width="114"><s:select name="selExPeriod" id="selExPeriod"
								list="monthListNumber" headerKey="13" headerValue="13"
								cssClass="commandOption" /></td>
						<td width="73" align="right" class="commandOption"></td>
						<td width="89"></td>
					</tr>

					<tr valign="top">
						<td width="27" height="23"></td>
						<td align="left" class="commandOption" colspan="4">Category&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:select name="selExSalesType" id="selExSalesType"
								list="salesType" listKey="key" listValue="value"
								cssClass="commandOption" />
						</td>
					</tr>
					<tr valign="top">
						<td width="27" height="23"></td>
						<td align="left" class="commandOption" colspan="4">Sales
							Id&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:select name="selSalesList" id="selSalesList" list="salesList"
								listKey="key" listValue="value" multiple="true"
								cssStyle="width: 50px;" cssClass="commandOption" /> <span
							style="color: red; font-size: 11px;">&nbsp;&nbsp;*Some of
								the Sales Id will not be available for the selected Category.</span>
						</td>
					</tr>
					<tr valign="top">
						<td width="27" height="23"></td>
						<td align="left" class="commandOption" colspan="4">Report
							Name Suffix&nbsp;&nbsp;&nbsp;&nbsp; <s:textfield
								name="reportNameSuf" size="30" cssClass="commandOption"
								maxlength="20" />
						</td>
					</tr>
					<tr valign="top">
						<td height="23"></td>
						<td colspan="6" align="left">
							<table cellpadding="4" cellspacing="2">
								<tr>
									<td width="300" valign="top">
										<button type="button" class="tcgm-btn" onclick="autoRunP34()"
											style="width: auto; min-width: 175px;">Calculate
											Deferred Margin</button> <br /> <input type="checkbox" name="sendP34"
										id="sendP34" /> <s:hidden name="autoRun" id="autoRun"
											value="N" /> <span style="color: red; font-size: 11px;">*Autorun
											P34 Volume to CCS.</span>
									</td>
									<td width="300" valign="top">
										<%
										if ("Y".equals(jbStatus)) {
										%>
										<button type="button" class="tcgm-btn"
											onclick="addJob(document.forms[0], 'SEND_P34')">Send
											P34 Volume to CCS</button> <%
 }
 %>&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>

			<!-- ================= SECTION 2: Hedge - Currency Exposure Settings ================= -->
			<fieldset style="margin-left: 50px; width: 685px; text-align: left;">
				<table width="685" align="left" cellpadding="2" cellspacing="0">
					<tr>
						<td colspan="7" class="tableHeading">Hedge - Currency
							Exposure Settings</td>
					</tr>
					<tr>
						<td width="27" height="23"></td>
						<td width="111" align="right" class="commandOption">Units</td>
						<td width="156"><s:select name="selHedExUnitSet"
								id="selHedExUnitSet" list="unitSets" listKey="datasetTableId"
								listValue="datasetNameLogStamp" cssClass="commandOption"
								onchange="callHedFunc();" /></td>
						<td width="65" align="right" class="commandOption">Period</td>
						<td width="114"><s:select name="selHedExPeriod"
								list="monthListNumber" headerKey="13" headerValue="13"
								cssClass="commandOption" /></td>
						<td width="73" align="right" class="commandOption">&nbsp;</td>
						<td width="89">&nbsp;</td>
					</tr>
					<tr>
						<td width="27" height="23"></td>
						<td width="111" align="right" class="commandOption">&nbsp;</td>
						<td width="156">&nbsp;</td>
						<td width="65" align="right" class="commandOption">End Period</td>
						<td width="114"><s:select name="selHedExEndPeriod"
								list="monthListNumber" headerKey="-1" headerValue="none"
								cssClass="commandOption" /></td>
						<td width="73" align="right" class="commandOption">&nbsp;</td>
						<td width="89">&nbsp;</td>
					</tr>
					<tr valign="top">
						<td width="27" height="23"></td>
						<td align="left" class="commandOption" colspan="4">Category&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:select name="selHedExSalesType" id="selHedExSalesType"
								list="salesType" listKey="key" listValue="value"
								cssClass="commandOption" />
						</td>
					</tr>
					<tr valign="top">
						<td width="27" height="23"></td>
						<td align="left" class="commandOption" colspan="4">Sales
							Id&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:select name="selHedSalesList" id="selHedSalesList"
								list="salesList" listKey="key" listValue="value" multiple="true"
								cssStyle="width: 156px; height: 65px;" cssClass="commandOption" />
							<span style="color: red; font-size: 11px;">&nbsp;&nbsp;*Some
								of the Sales Id will not be available for the selected Category.</span>
						</td>
					</tr>
					<tr valign="top">
						<td width="27" height="23"></td>
						<td align="left" class="commandOption" colspan="4">Report
							Name Suffix&nbsp;&nbsp;&nbsp;&nbsp; <s:textfield
								name="hedgereportNameSuf" size="30" cssClass="commandOption"
								maxlength="20" />
						</td>
					</tr>
					<tr>
						<td height="23"></td>
						<td colspan="6" align="left">
							<table cellpadding="4" cellspacing="2">
								<tr>
									<td width="300">
										<button type="button" class="tcgm-btn"
											onclick="addJob(document.forms[0], 'XHG_ANALYSIS')"
											style="width: 290px; text-align: center;">Exposure
											by Currency - Analysis, Detail and Summary Report</button>
									</td>
									<td width="300">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>

			<!-- ================= SECTION 3: Standard Cost Settings ================= -->
			<fieldset style="margin-left: 50px; width: 685px; text-align: left;">
				<table width="685" align="left" cellpadding="2" cellspacing="0">
					<tr>
						<td colspan="7" class="tableHeading">Standard Cost Settings</td>
					</tr>
					<tr>
						<td width="101" align="right" class="commandOption">Units</td>
						<td width="124"><s:select name="selScUnitSet" list="unitSets"
								listKey="datasetTableId" listValue="datasetNameLogStamp"
								cssClass="commandOption" /></td>
						<td width="122" align="right" class="commandOption">Rate Set</td>
						<td align="left" colspan="3"><s:select name="selScRateSet"
								list="rateSets" listKey="datasetTableId" listValue="datasetName"
								cssClass="commandOption" /></td>
					</tr>
					<tr>
						<td height="23" align="right" class="commandOption">Rate
							Period</td>
						<td width="48" height="23" align="left"><s:select
								name="selScRatePeriod" list="monthListNumber" headerKey="13"
								headerValue="13" cssClass="commandOption" /></td>
						<td height="23" align="right" class="commandOption">Sales ID</td>
						<td width="150" height="23" align="left"><s:select
								name="selScSalesId" list="#{'1':'Sample', '3':'Sales'}"
								cssClass="commandOption" /></td>
					</tr>
					<tr>
						<td height="66"></td>
						<td colspan="6" align="left">
							<table>
								<tr>
									<td width="300">
										<button type="button" class="tcgm-btn"
											onclick="addJob(document.forms[0], 'STD_VA_CST')"
											style="width: auto; min-width: 175px;">Std Val Added
											Cost of Sales</button>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<br> <br>
		</div>
	</s:form>

	<script type="text/javascript">
		function callFunc() {
			retrieveURL('./tcgmAjax.do?cascadingCmd=SalesId&cascadingVal='
					+ document.getElementById('selExUnitSet').value,
					'miscAnalysisForm', 'selSalesList');
		}
		function callHedFunc() {
			retrieveURL('./tcgmAjax.do?cascadingCmd=SalesId&cascadingVal='
					+ document.getElementById('selHedExUnitSet').value,
					'miscAnalysisForm', 'selHedSalesList');
		}
		function autoRunP34() {
			if (document.getElementById('sendP34').checked) {
				document.getElementById('autoRun').value = 'Y';
			} else {
				document.getElementById('autoRun').value = 'N';
			}
			addJob(document.forms[0], 'DFRD_MARGIN');
		}
	</script>

	<s:include value="/include/footer.jsf" />
</body>
</html>

