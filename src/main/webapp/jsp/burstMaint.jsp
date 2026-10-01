<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%! String pageTitle = "Add Division / Area / Sector / Affiliate"; %>

<head>
    <style type="text/css">
        /* Modern corporate button design matching the provided sample image */
        input[type="button"] {
            display: inline-block;
            padding: 6px 22px;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 13px;
            font-weight: bold;
            font-style: italic;
            color: #0b2545 !important;
            text-decoration: none;
            background: linear-gradient(to bottom, #ffe47c 0%, #ffc124 100%);
            border: 1px solid #cca11f;
            border-radius: 6px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.15), inset 0 1px 0 rgba(255,255,255,0.4);
            cursor: pointer;
            transition: all 0.1s ease-in-out;
        }
        input[type="button"]:hover {
            background: linear-gradient(to bottom, #ffed96 0%, #ffd455 100%);
            border-color: #b88f14;
        }
        input[type="button"]:active {
            background: #ffcd3c;
            box-shadow: inset 0 1px 3px rgba(0,0,0,0.2);
        }
        
        /* Clean medium-sized select field styling matching the provided sample image */
        select {
            border: 1px solid #cccccc;
            background-color: #ffffff;
            padding: 6px 10px;
            width: 240px;
            height: 32px;
            font-family: Arial, sans-serif;
            font-size: 13px;
            color: #333333;
            border-radius: 4px;
            box-sizing: border-box;
        }
        select:focus {
            border-color: #ffcd3c;
            outline: none;
        }
        
        /* Layout spacing adjustments */
        .commandOptionLabel {
            font-family: Arial, sans-serif;
            font-size: 13px;
            font-weight: bold;
            color: #333333;
            padding-right: 15px;
        }
        table {
            margin-top: 30px;
            border-collapse: separate;
            border-spacing: 0 12px;
        }
    </style>

    <script type="text/javascript">
        function disableList() {   
            // Unused legacy template placeholder logic preserved from original source
        }

        function saveForm(form, cmd, actionName) {
            if (document.getElementById("role").value == '-1') {
                alert('Please select Role');
                return false;
            }

            var userForm = document.getElementById("userForm");

            if (document.getElementById("role").value == 'D') {
                if (document.getElementById("division").value == '-1') {
                    alert('Please select Division');
                    return false;
                }
                userForm.elements["categoryid"].value = 'D';
                userForm.elements["categoryname"].value = document.getElementById("division").value;

            } else if (document.getElementById("role").value == 'Area') {
                if (document.getElementById("division").value == '-1') {
                    alert('Please select Division');
                    return false;
                } else if (document.getElementById("areaCode").value == '-1') {
                    alert('Please select Area');
                    return false;
                }
                userForm.elements["categoryid"].value = document.getElementById("areaCode").value;
                userForm.elements["categoryname"].value = getSelectedText(document.getElementById("areaCode")).substring(5);
                
            } else if (document.getElementById("role").value == 'Affiliate') {
                if (document.getElementById("division").value == '-1') {
                    alert('Please select Division');
                    return false;
                } else if (document.getElementById("affCode").value == '-1') {
                    alert('Please select Affiliate');
                    return false;
                }
                userForm.elements["categoryid"].value = document.getElementById("affCode").value;
                userForm.elements["categoryname"].value = getSelectedText(document.getElementById("affCode")).substring(7);
                
            } else if (document.getElementById("role").value == 'Sector') {
                if (document.getElementById("division").value == '-1') {
                    alert('Please select Division');
                    return false;
                } else if (document.getElementById("secCode").value == '-1') {
                    alert('Please select Sector');
                    return false;
                }
                userForm.elements["categoryid"].value = document.getElementById("secCode").value;
                userForm.elements["categoryname"].value = getSelectedText(document.getElementById("secCode")).substring(9);
            }
            
            userForm.elements["divisionCode"].value = document.getElementById("division").value;
            chgActCmdSubmit(cmd);
        }
        function enableList() {
            var roleVal = document.getElementById("role").value;
            var divVal = document.getElementById("division").value;

            if (roleVal == '-1' || divVal == '-1') {
                document.getElementById("code").innerHTML = "Affiliate / Sector / Area #";
                document.getElementById("affCode").style.display = 'none';
                document.getElementById("secCode").style.display = 'none';
                document.getElementById("areaCode").style.display = 'none';
                
            } else if (roleVal == 'Area') {
                document.getElementById("code").innerHTML = "Area Code";
                document.getElementById("affCode").style.display = 'none';
                document.getElementById("secCode").style.display = 'none';
                document.getElementById("areaCode").style.display = '';
                retrieveURL('./tcgmAjax.action?cascadingCmd=Area&cascadingVal=' + divVal, 'userForm', 'areaCode');
                
            } else if (roleVal == 'Affiliate') {
                document.getElementById("code").innerHTML = "Affiliate Code";
                document.getElementById("affCode").style.display = '';
                document.getElementById("secCode").style.display = 'none';
                document.getElementById("areaCode").style.display = 'none';
                retrieveURL('./tcgmAjax.action?cascadingCmd=Country&cascadingVal=' + divVal, 'userForm', 'affCode');
                
            } else if (roleVal == 'Sector') {
                document.getElementById("code").innerHTML = "Sector Code";
                document.getElementById("affCode").style.display = 'none';
                document.getElementById("secCode").style.display = '';
                document.getElementById("areaCode").style.display = 'none';
                retrieveURL('./tcgmAjax.action?cascadingCmd=Sector&cascadingVal=' + divVal, 'userForm', 'secCode');
                
            } else if (roleVal == 'D') {
                document.getElementById("code").innerHTML = "Affiliate / Sector / Area #";
                try { document.getElementById("affCode").style.display = 'none'; } catch(err) {}
                try { document.getElementById("secCode").style.display = 'none'; } catch(err1) {}
                try { document.getElementById("areaCode").style.display = 'none'; } catch(err2) {}
            }
        }   

        function loadAreaDesc() {
            if (document.getElementById("role").value == '-1' || document.getElementById("division").value == '-1' || document.getElementById("areaCode").value == '-1') {
                document.getElementById("catName").value = '';
            } else {
                document.getElementById("catName").value = getSelectedText(document.getElementById("areaCode")).substring(5);
            }
        }

        function loadSectorDesc() {
            if (document.getElementById("role").value == '-1' || document.getElementById("division").value == '-1' || document.getElementById("secCode").value == '-1') {
                document.getElementById("catName").value = '';
            } else {
                document.getElementById("catName").value = getSelectedText(document.getElementById("secCode")).substring(9);
            }
        }

        function loadAffiliateDesc() {
            if (document.getElementById("role").value == '-1' || document.getElementById("division").value == '-1' || document.getElementById("affCode").value == '-1') {
                document.getElementById("catName").value = '';
            } else {
                document.getElementById("catName").value = getSelectedText(document.getElementById("affCode")).substring(7);
            }
        }

        function getSelectedText(slctBox) {
            return slctBox[slctBox.selectedIndex].text;
        }

        function clearFields() {
            document.getElementById("role").value = '-1';
            enableList();
        }   

        function loadBurst(form, cmd, actionName) {
            if (confirm("Are you sure that you would like to Load the Burst Tables? ")) {
                document.body.style.cursor = 'wait';
                document.getElementById("saveButton").disabled = true;
                document.getElementById("loadButton").disabled = true;
                chgActCmdSubmit(cmd);
            }
        }

        function chgActCmdSubmit(cmdValue) {
            document.getElementById("cmd").value = cmdValue;
            var form = document.getElementById("userForm");
            form.action = "rptUserMaint.action";
            form.submit();
        }
    </script>
</head>
        <body style="margin: 0;" onload="enableList();">
	<%@ include file="/include/header.jsf"%>
	<%@ include file="/include/masthead.jsf"%>
	<%@ include file="/include/errorDisplay.jsf"%>

	<s:form method="post" name="userForm" id="userForm"
		action="rptUserMaint" theme="simple">
		<s:hidden name="cmd" id="cmd" />
		<s:hidden name="selDesc" id="selDesc" />
		<s:hidden name="divisionCode" id="divisionCode" />
		<s:hidden name="categoryid" id="categoryid" />
		<s:hidden name="categoryname" id="categoryname" />

		<table width="650" align="center" border="0">
			<tr>
				<td class="commandOptionLabel" width="245" align="right"><strong><span
						class="mntLeft">Role</span></strong></td>
				<td width="405"><select name="rptUser.role" id="role"
					onchange="enableList();">
						<option value="-1">Select One</option>
						<option value="D">Division</option>
						<option value="Area">Area</option>
						<option value="Sector">Sector</option>
						<option value="Affiliate">Affiliate</option>
				</select></td>
			</tr>
			<tr>
				<td class="commandOptionLabel" width="245" align="right"><strong><span
						class="mntLeft">Division</span></strong></td>
				<td width="405"><select name="rptUser.division" id="division"
					onchange="enableList();">
						<option value="-1">Select One</option>
						<c:forEach items="${rptUser['div']}" var="divItem">
							<option value="${divItem.value}">${divItem.key}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="commandOptionLabel" align="right"><strong><span
						class="mntLeft"><label id="code">Affiliate / Sector
								/ Area #</label></span></strong></td>
				<td style="vertical-align: middle;"><select
					name="rptUser.areaCode" id="areaCode" style="display: none;">
						<option value="-1">Select One</option>
						<c:forEach items="${rptUser.areas}" var="areaItem">
							<option value="${areaItem.value}">${areaItem.key}</option>
						</c:forEach>
				</select> <select name="rptUser.secCode" id="secCode" style="display: none;">
						<option value="-1">Select One</option>
						<c:forEach items="${rptUser.sectors}" var="secItem">
							<option value="${secItem.value}">${secItem.key}</option>
						</c:forEach>
				</select> <select name="rptUser.affCode" id="affCode" style="display: none;">
						<option value="-1">Select One</option>
						<c:forEach items="${rptUser.affiliates}" var="affItem">
							<option value="${affItem.value}">${affItem.key}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button"
					name="saveButton" id="saveButton" value="Save"
					onClick="saveForm(this, 'burstmaint', 'rptUserMaint.action');">
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><font color="red" size="2">By choosing
						the "Load Burst Tables" button below, the system will remove and
						re-build the burst tables in TCGM based on the latest hierarchy.
						It will not create the Cognos groups.</font></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button"
					name="loadButton" id="loadButton" value="Load Burst Tables"
					onClick="loadBurst(this, 'loadBurst', 'rptUserMaint.action');">
				</td>
			</tr>
		</table>
		<input type="hidden" name="cmd2" value="creation">
	</s:form>
	<br>
	<%@ include file="/include/footer.jsf"%>
</body>
</html>