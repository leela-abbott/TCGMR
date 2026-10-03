<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TCGM Navigation Framework</title>
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
				<li class="menu-item"><a class="menu-link">App Security</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li><a onclick="navigate('ActiveDirSearch.action?cmd=appview')">Add User</a></li>
						<li><a onclick="navigate('userMaint.action')">User Deletion</a></li>
					</ul>
				</li>

				<li class="menu-item"><a class="menu-link">Reports Security</a>
					<ul class="dropdown-menu" style="min-width: 100%; width: max-content;">
						<li><a onclick="navigate('ActiveDirSearch.action?cmd=view')">Add User</a></li>
						<li><a onclick="navigate('rptUserMaint.action?cmd=filter')">User Search / Delete</a></li>
						<li><a onclick="navigate('rptUserMaint.action?cmd=burst')">Add Division /Area / Sector / Affiliate</a></li>
						<li><a onclick="navigate('rptUserMaint.action?cmd=usersView')">Cognos User List</a></li>
						<li><a onclick="navigate('rptUserMaint.action?cmd=datausersView')">Database User List</a></li>
						<li><a onclick="navigate('fileUpload.action')">FileUpload</a></li>
						<li><a onclick="navigate('deleteFile.action?cmd=dir')">DeleteFile</a></li>
					</ul>
				</li>

				<li class="menu-item"><a class="menu-link" onclick="navigate('main.action')">Main Menu</a></li>
			</ul>
		</div>

		<!-- Container grouping the layout structures seamlessly on the right side of the navbar -->
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
