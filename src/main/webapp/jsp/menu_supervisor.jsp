<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TCGM Navigation Framework - Supervisor</title>
<style>
:root {
	--primary-blue: #0A4876;
	--light-blue: #B4D8F4;
	--yellow-accent: #FFDF31;
	--border-color: #B4D8F4;
	--font-size: 11px;
}

body {
	font-family: "Arial", sans-serif;
	margin: 0;
	padding: 0;
	background-color: #ffffff;
}

/* Main Navigation Bar Layout */
.nav-container {
	background-color: var(--light-blue);
	border-bottom: 1px solid var(--border-color);
	display: flex;
	align-items: center;
	justify-content: space-between;
	height: 32px;
	padding: 0;
	position: relative;
	z-index: 1000;
}

.nav-left-group {
	display: flex;
	align-items: center;
	height: 100%;
}

/* Logo Identifier Box */
.logo-box {
	background-color: var(--primary-blue);
	color: var(--yellow-accent);
	font-weight: bold;
	font-size: 14px;
	padding: 0 15px;
	display: flex;
	align-items: center;
	height: 100%;
	box-shadow: 2px 0px 5px rgba(0, 0, 0, 0.2);
}

/* Horizontal Main Bar Structural Layout */
.menu-bar {
	display: flex;
	list-style: none;
	margin: 0;
	padding: 0;
	height: 100%;
}

.menu-item {
	position: relative;
	height: 100%;
}

.menu-link {
	display: flex;
	align-items: center;
	padding: 0 12px;
	height: 100%;
	color: var(--primary-blue);
	text-decoration: none;
	font-size: var(--font-size);
	font-weight: bold;
	cursor: pointer;
	text-align: left;
	line-height: 1.2;
	white-space: nowrap;
}

.menu-link:hover {
	color: var(--primary-blue);
	background-color: var(--yellow-accent);
}

/* Secondary Flyout Dropdown Structural Contexts */
.dropdown-menu, .submenu {
	display: none;
	position: absolute;
	background-color: var(--light-blue);
	border: 1px solid var(--border-color);
	list-style: none;
	margin: 0;
	padding: 0;
	box-shadow: 3px 3px 5px rgba(119, 119, 119, 0.5);
}

.dropdown-menu {
	top: 100%;
	left: 0;
}

.dropdown-item {
	position: relative;
}

.dropdown-item .submenu {
	top: 0;
	left: 100%;
}

.menu-item:hover>.dropdown-menu, .dropdown-item:hover>.submenu {
	display: block;
}

.dropdown-menu a {
	display: block;
	padding: 6px 12px;
	color: var(--primary-blue);
	text-decoration: none;
	font-size: var(--font-size);
	font-weight: bold;
	white-space: nowrap;
}

.dropdown-menu a:hover {
	color: var(--primary-blue);
	background-color: var(--yellow-accent);
}

.menu-separator {
	border-top: 1px solid var(--primary-blue);
	margin: 4px 0;
	height: 0;
	overflow: hidden;
}

.nav-right-group {
	display: flex;
	align-items: center;
	height: 100%;
	margin-right: 10px;
}

.user-context-header-panel {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    margin-right: 15px;
    font-family: "Arial", sans-serif;
    color: var(--primary-blue);
    font-weight: bold;
    text-align: right;
    line-height: 1.2;
}

.currUserDisp {
    font-size: 11px;
    white-space: nowrap;
    font-weight: bold !important;
}

.currModelDisp {
    font-size: 10px;
    white-space: normal;
    word-break: break-word;
    max-width: 400px;
    color: rgb(213, 57, 73) !important;
}

.logout-btn {
	background-color: var(--primary-blue);
	color: #ffffff;
	text-decoration: none;
	font-size: 11px;
	font-weight: bold;
	padding: 4px 15px;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	height: 22px;
	border-radius: 15px;
	cursor: pointer;
	white-space: nowrap;
	box-sizing: border-box;
}

.logout-btn:hover {
	background-color: #063455;
}
</style>
</head>
<body>

	<nav class="nav-container">
		<div class="nav-left-group">
			<div class="logo-box">TCGM</div>

			<ul class="menu-bar">
				<!-- Maintenance Section -->
				<li class="menu-item"><a class="menu-link">Maintenance</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li class="dropdown-item"><a class="menu-link">Maintenance &raquo;</a>
							<ul class="submenu">
								<li class="dropdown-item"><a class="menu-link">ASR &raquo;</a>
									<ul class="submenu">
										<li><a onclick="navigate('asrMaintenance.action')">Data</a></li>
										<li><a onclick="navigate('asrTranMaintenance.action')">Maintenance</a></li>
									</ul>
								</li>
								<li class="dropdown-item"><a class="menu-link">BPC &raquo;</a>
									<ul class="submenu">
										<li><a onclick="navigate('bpcsMaint.action')">Data</a></li>
										<li><a onclick="navigate('bpcsTranMaint.action')">Maintenance</a></li>
									</ul>
								</li>
								<li class="dropdown-item"><a class="menu-link">BPC Rev &raquo;</a>
									<ul class="submenu">
										<li><a onclick="navigate('bpcRevMaint.action')">Data</a></li>
										<li><a onclick="navigate('bpcRevTranMaint.action')">Maintenance</a></li>
									</ul>
								</li>
								<li class="dropdown-item"><a class="menu-link">BPC Exc &raquo;</a>
									<ul class="submenu">
										<li><a onclick="navigate('bpcExMaint.action')">Data</a></li>
										<li><a onclick="navigate('bpcExTranMaint.action')">Maintenance</a></li>
									</ul>
								</li>
								<li class="dropdown-item"><a class="menu-link">Notes &raquo;</a>
									<ul class="submenu">
										<li><a onclick="navigate('notesMaint.action')">Data</a></li>
										<li><a onclick="navigate('notesTranMaint.action')">Maintenance</a></li>
									</ul>
								</li>
							</ul>
						</li>
						<li class="dropdown-item"><a class="menu-link">Rate Models &raquo;</a>
							<ul class="submenu">
								<li><a onclick="navigate('openMngRateSets.action')">Select</a></li>
								<li><a onclick="navigate('rateDataMaint.action')">Rate Data</a></li>
								<li><a onclick="navigate('rateDataTranMaint.action')">Rate Maint</a></li>
							</ul>
						</li>
						<li class="menu-separator"></li>
						<li><a onclick="navigate('mngFactorModels.action')">Select Factor Model</a></li>
					</ul>
				</li>

				<!-- Production Section -->
				<li class="menu-item"><a class="menu-link">Production</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li><a onclick="navigate('mngFactors.action')">Factors</a></li>
						<li><a onclick="navigate('mngAnalysisModels.action')">Factor Analysis</a></li>
						<li><a onclick="navigate('mngPerpetualModels.action')">Perpetual</a></li>
						<li><a onclick="navigate('mngCostExchModels.action')">Mgn Bill Exch Flex</a></li>
						<li><a onclick="navigate('miscAnalysis.action')">Misc. Analysis</a></li>
						<li><a onclick="navigate('miscReports.action')">Misc. Reports</a></li>
					</ul>
				</li>

				<!-- Job Que Section -->
				<li class="menu-item"><a class="menu-link">Job Que</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li><a onclick="navigate('processMgmt.action')">Manage Jobs</a></li>
						<li><a onclick="navigate('mngReportJobs.action')">Job Reports</a></li>
					</ul>
				</li>

				<!-- Import/Export Section -->
				<li class="menu-item"><a class="menu-link">Import/Export</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li><a onclick="navigate('mngUnits.action')">Unit Data</a></li>
						<li><a onclick="navigate('affBpcMaint.action')">Affiliate BPC Import</a></li>
						<li><a onclick="navigate('affAsrMaint.action')">Affiliate ASR Usage</a></li>
						<li><a onclick="navigate('dataTransfers.action')">Data Transfers</a></li>
						<li><a onclick="navigate('mngDataFeedLog.action')">Transfer Log</a></li>
						<li><a onclick="navigate('rptUserMaint.action?cmd=activeaff')">Active Affiliate</a></li>
					</ul>
				</li>

				<!-- Admin Section -->
				<li class="menu-item"><a class="menu-link">Admin</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li><a onclick="navigate('currencyCodeMaint.action')">Currency Codes</a></li>
						<li><a onclick="navigate('affCstCurMaint.action')">Aff Curr Maint</a></li>
						<li><a onclick="navigate('salesTypeMaint.action')">Sales Type Maint</a></li>
						<li><a onclick="navigate('mngDaemons.action')">Manage Daemons</a></li>
						<li><a onclick="navigate('affAreaMaint.action')">Aff Area Unwanted Division</a></li>
						<li><a onclick="navigate('fileUpload.action')">FileUpload</a></li>
						<li><a onclick="navigate('deleteFile.action?cmd=dir')">DeleteFile</a></li>
						<li><a onclick="navigate('rptUserMaint.action?cmd=filter')">Report Search</a></li>
					</ul>
				</li>

				<!-- Main Menu Link -->
				<li class="menu-item"><a class="menu-link" onclick="navigate('main.action')">Main Menu</a></li>
			</ul>
		</div>

		<!-- Container grouping right-side profile contextual properties -->
		<div class="nav-right-group">
			<div class="user-context-header-panel">
				<div class="currUserDisp">
					<c:out value="${sessionScope.TCGMUser.fullName}" />
				</div>
				<div class="currModelDisp">
					Factor Model:&nbsp;<c:out value="${sessionScope.TCGMState.currentModelName}" />
				</div>
			</div>
			<a onclick="navigate('logout.action')" class="logout-btn">Log Out</a>
		</div>
	</nav>

	<script type="text/javascript">
		function navigate(targetActionEndpoint) {
			console.log("Routing execution frame to interceptor map: " + targetActionEndpoint);
			window.location.href = targetActionEndpoint;
		}
	</script>
</body>
</html>
