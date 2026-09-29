<!DOCTYPE html>
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
            height: 45px;
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
            font-size: 16px;
            padding: 0 15px;
            display: flex;
            align-items: center;
            height: 100%;
            box-shadow: 2px 0px 5px rgba(0,0,0,0.2);
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

        /* Cascading Level-3 Menu Positions */
        .dropdown-item {
            position: relative;
        }

        .dropdown-item .submenu {
            top: 0;
            left: 100%;
        }

        /* Hover States for Menu Visibility Triggers */
        .menu-item:hover > .dropdown-menu,
        .dropdown-item:hover > .submenu {
            display: block;
        }

        /* Unified Link Formatting for Submenus */
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

        /* Divider items matching legacy array layout definitions */
        .menu-separator {
            border-top: 1px solid var(--primary-blue);
            margin: 4px 0;
            height: 0;
            overflow: hidden;
        }

        /* Log Out Anchor Action Element */
        .logout-btn {
            background-color: var(--primary-blue);
            color: #ffffff;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
            padding: 0 20px;
            height: 100%;
            display: flex;
            align-items: center;
            border-radius: 0 0 0 15px;
            cursor: pointer;
        }

        .logout-btn:hover {
            background-color: #063455;
        }
    </style>
</head>
<body>

    <!-- Unified Master Menu Application Architecture -->
    <nav class="nav-container">
        <div class="nav-left-group">
            <div class="logo-box">TCGM</div>
            
            <ul class="menu-bar">
                <!-- Level 1: mainmenu Framework Definitions -->

                <!-- Main Menu Option 1: App Security (Triggers 'Admin' Configurations) -->
                <li class="menu-item">
                    <a class="menu-link" onclick="navigate('ActiveDirSearch.action?cmd=appview')">App<br>Security</a>
                    <ul class="dropdown-menu" style="width: 107px;">
                        <li><a onclick="navigate('ActiveDirSearch.action?cmd=appview')">Add User</a></li>
                        <li><a onclick="navigate('userMaint.action')">User Deletion</a></li>
                    </ul>
                </li>

                <!-- Main Menu Option 2: Reports Security (Triggers 'AppAdmin' Configurations) -->
                <li class="menu-item">
                    <a class="menu-link" onclick="navigate('ActiveDirSearch.action?cmd=view')">Reports<br>Security</a>
                    <ul class="dropdown-menu" style="width: 180px;">
                        <li><a onclick="navigate('ActiveDirSearch.action?cmd=view')">Add User</a></li>
                        <li><a onclick="navigate('rptUserMaint.action?cmd=filter')">User Search / Delete</a></li>
                        <li><a onclick="navigate('rptUserMaint.action?cmd=burst')">Add Division /Area / Sector / Affiliate</a></li>
                        <li><a onclick="navigate('rptUserMaint.action?cmd=usersView')">Cognos User List</a></li>
                        <li><a onclick="navigate('rptUserMaint.action?cmd=datausersView')">Database User List</a></li>
                        <li><a onclick="navigate('fileUpload.action')">FileUpload</a></li>
                        <li><a onclick="navigate('deleteFile.action?cmd=dir')">DeleteFile</a></li>
                    </ul>
                </li>

                <!-- Main Menu Option 3: Model Mgmt Sub-tier (With Level-3 Cascades) -->
                <li class="menu-item">
                    <a class="menu-link">Model<br>Mgmt</a>
                    <ul class="dropdown-menu" style="width: 120px;">
                        <!-- Cascade Trigger: ModelMaintenance -->
                        <li class="dropdown-item">
                            <a class="has-submenu">Maintenance &raquo;</a>
                            <ul class="submenu" style="width: 170px;">
                                <li><a onclick="navigate('asrMaintenance.action')">ASR Data</a></li>
                                <li><a onclick="navigate('asrTranMaintenance.action')">ASR Maintenance</a></li>
                                <li><a onclick="navigate('bpcsMaint.action')">BPC Data</a></li>
                                <li><a onclick="navigate('bpcsTranMaint.action')">BPC Maintenance</a></li>
                                <li><a onclick="navigate('bpcRevMaint.action')">BPC Revision Data</a></li>
                                <li><a onclick="navigate('bpcRevTranMaint.action')">BPC Revision Maint</a></li>
                                <li><a onclick="navigate('bpcExMaint.action')">BPC Exception Data</a></li>
                                <li><a onclick="navigate('bpcExTranMaint.action')">BPC Exception Maint</a></li>
                                <li><a onclick="navigate('notesMaint.action')">Notes Data</a></li>
                                <li><a onclick="navigate('notesTranMaint.action')">Notes Maintenance</a></li>
                            </ul>
                        </li>
                <!-- Cascade Trigger: RateMgmt -->
                <li class="dropdown-item">
                    <a class="has-submenu">Rate Models &raquo;</a>
                    <ul class="submenu" style="width: 170px;">
                        <li><a onclick="navigate('openMngRateSets.action')">Select</a></li>
                        <li><a onclick="navigate('rateDataMaint.action')">Rate Data</a></li>
                        <li><a onclick="navigate('rateDataTranMaint.action')">Rate Maint</a></li>
                    </ul>
                </li>
                <li class="menu-separator"></li>
                <li><a onclick="navigate('mngFactorModels.action')">Select Factor Model</a></li>
            </ul>
        </li>

        <!-- Main Menu Option 4: Production Parameters Configuration -->
        <li class="menu-item">
            <a class="menu-link">Production</a>
            <ul class="dropdown-menu" style="width: 170px;">
                <li><a onclick="navigate('mngFactors.action')">Factors</a></li>
                <li><a onclick="navigate('mngAnalysisModels.action')">Factor Analysis</a></li>
                <li><a onclick="navigate('mngPerpetualModels.action')">Perpetual</a></li>
                <li><a onclick="navigate('mngCostExchModels.action')">Mgn Bill Exch Flex</a></li>
                <li><a onclick="navigate('miscAnalysis.action')">Misc. Analysis</a></li>
                <li><a onclick="navigate('miscReports.action')">Misc. Reports</a></li>
            </ul>
        </li>

        <!-- Main Menu Option 5: Job Scheduling Actions -->
        <li class="menu-item">
            <a class="menu-link">Job Que</a>
            <ul class="dropdown-menu" style="width: 140px;">
                <li><a onclick="navigate('processMgmt.action')">Manage Jobs</a></li>
                <li><a onclick="navigate('mngReportJobs.action')">Job Reports</a></li>
            </ul>
        </li>

        <!-- Main Menu Option 6: Import / Export File Utilities -->
        <li class="menu-item">
            <a class="menu-link">Import/Export</a>
            <ul class="dropdown-menu" style="width: 140px;">
                <li><a onclick="navigate('mngUnits.action')">Unit Data</a></li>
                <li><a onclick="navigate('affBpcMaint.action')">Affiliate BPC Import</a></li>
                <li><a onclick="navigate('dataTransfers.action')">Data Transfers</a></li>
                <li><a onclick="navigate('mngDataFeedLog.action')">Transfer Log</a></li>
            </ul>
        </li>

        <!-- Main Menu Option 7: Root Return -->
        <li class="menu-item">
            <a class="menu-link" onclick="navigate('main.action')">Main<br>Menu</a>
        </li>
    </ul>
</div>

<div>
    <a onclick="navigate('logout.action')" class="logout-btn">Log Out</a>
</div>
</nav>

<script type="text/javascript">
/**
 * Global Interceptor Dispatcher for Navigation Actions
 * Replaces legacy .do hooks with modern Struts2 endpoint targets
 */
function navigate(targetActionEndpoint) {
    console.log("Routing execution frame to interceptor map: " + targetActionEndpoint);
    window.location.href = targetActionEndpoint;
}
</script>
</body>
</html>
                        