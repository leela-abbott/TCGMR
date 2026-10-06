<%! String pageTitle="Select or Create Model";%>
<%@ include file="/include/header.jsf" %>
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- Added for -->
<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser"  scope="session" type="abbott.ai.tcgm.entities.User" />
<!-- end -->

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>TCGM Factor model</title>
    <script type="text/javascript" src="include/common.js"></script>
   <script type="text/javascript" language=javascript>
	function getForm() {
		return document.getElementById('mngFactorModelsForm');
	}

	function deleteModel(modelid, modelname) {
		if ( confirm("Are you sure that you would like to delete " + modelname + "?\nThis will delete all attributes and can not be undone.") ) {
			if(confirm("ARE YOU SURE to delete " + modelname + "?") ){
				var form = getForm();
				document.getElementById('modelSelected').value = modelid;
				changeActionAndSubmit(form, "deleteFactorModel.action");
			}
		}
	}

	function closeModel(modelid, modelname) {
		if ( confirm("Are you sure that you would like to close " + modelname + "?") ) {
			var form = getForm();
			document.getElementById('modelSelected').value = modelid;
			document.getElementById('showModels').value = 'open';
			changeActionAndSubmit(form, "closeModel.action");
		}
	}

	function openModel(modelid, modelname) {
		
		if ( confirm("Are you sure that you would like to open " + modelname + "?") ) {
			var form = getForm();
			document.getElementById('modelSelected').value = modelid;
			document.getElementById('showModels').value = 'closed';
			changeActionAndSubmit(form, "openModel.action");
			
			
		}
	}

	function compactModel(modelid, modelname) {
		if ( confirm("Are you sure that you would like to compact " + modelname + "?\nAll temporary data and reports will be deleted.") ) {
			var form = getForm();
			document.getElementById('modelSelected').value = modelid;
			changeActionAndSubmit(form, "compactModel.do");
		}
	}

	function selectModel(modelid, modelname) {
		var form = getForm();
		document.getElementById('modelSelected').value = modelid;
		changeActionAndSubmit(form, "updateModelSelection.do");
	}

	<%//Sridevi.K 06/05/2005 New script for fixing the empty description field while creating a model starts here.%>
	function createModel() {
		
		var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
		var check = true;
		var createForm = document.getElementById('createFactorModelForm');
		for (var i = 0; i < createForm.elements['createModelName'].value.length; i++) {
			if (iChars.indexOf(createForm.elements['createModelName'].value.charAt(i)) != -1) {
				alert ("The Model name you eneterd has special characters. \n Please remove them and try again.");
				check = false;
			}
		}
		if ( emptyAlert('Description', createForm.elements['createModelDesc']) && check) {
			createForm.submit();
		}
	}
	<%//Sridevi.K New script ends here 06/05/2005%>

	function showClosedModelDetails(stat) {
		var form = getForm();
		document.getElementById('showModels').value = stat;
		form.action = 'mngFactorModels.do';
		form.submit();
	}

</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<s:actionerror />
<s:if test="hasActionErrors()">
    <div class="error">
        <s:actionerror />
    </div>
</s:if>

<s:if test="hasFieldErrors()">
    <div class="error">
        <s:fielderror />
    </div>
</s:if>

<s:if test="hasActionMessages()">
    <div class="message">
        <s:actionmessage />
    </div>
</s:if>



<s:form name="mngFactorModelsForm" id="mngFactorModelsForm" action="" type="abbott.ai.tcgm.action.form.MngFactorModelsForm">

  <c:set var="monthListNumber" value="${monthListNumber}" />
  <table width="684" cellpadding="2">
  
	<tr>
	  <td width="24">&nbsp;</td>
	  <td colspan="5" class="tableHeading">Select Existing Model</td>
	</tr>

	<tr> 
	  <td width="24">&nbsp;</td>
	  <td width="87" class="tableEntry">Model Actions</td>
	  <td width="49" class="tableEntry">Name</td>
	  <td width="46" class="tableEntry" >Cycle</td>
	  <td width="55" class="tableEntry" >Year</td>
	  <td width="383" class="tableEntry">Description</td>
	</tr>
	
	<%-- AAAA: <%=TCGMUser.getRole().getAccessLevel()%>
	BBB  : <%=Role.Analyst.getAccessLevel()%> --%>
	
	
	
	<c:if test="${showModels == 'open'}">
	
		<c:forEach var="models" items="${requestScope.mngFactorModelsForm.models}">
		  <tr>
			<td height="23" colspan=2 class=right >&nbsp;
				<!-- <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
					<a class="CmdSm" href='javascript:compactModel(<c:out value="${models.modelId}" />, "<c:out value="${models.name}" />")' >Compact</a>
				</abbott:securePage> -->
				<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
					<a class="CmdSm" href='javascript:closeModel(<c:out value="${models.modelId}" />, "<c:out value="${models.name}" />")' >Close</a>
				</abbott:securePage>
				<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
					&nbsp;&nbsp;&nbsp;&nbsp;<a class="CmdSm" href='javascript:deleteModel(<c:out value="${models.modelId}" />, "<c:out value="${models.name}" />")' >Del</a>
				</abbott:securePage>
			</td>
			<td >
				<a href='javascript:selectModel(<c:out value="${models.modelId}" />)' >
			  <c:out value="${models.name}" />
			  </a>
			</td>
			<td class="commandOptionLabel"><c:out value="${models.modelCycleLongName}" /></td>
			<td class="commandOptionLabel"><c:out value="${models.modelYear}" /></td>
			<td class="commandOptionLabel"><c:out value="${models.desc}" /></td>
		  </tr>
	     </c:forEach>
		
		<td height="23" colspan=3 class=right >&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("closed")' >Show Closed Models</a>
			</abbott:securePage>
		</td>
		
	</c:if>	
	<c:if test="${showModels != 'open'}">
	
	<c:forEach var="closedModels" items="${requestScope.mngFactorModelsForm.closedModels}">
	  <tr>
		<td height="23" colspan=2 class=right >&nbsp;
			<!-- <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
				<a class="CmdSm" href='javascript:compactModel(<c:out value="${closedModels.modelId}" />, "<c:out value="${closedModels.name}" />")' >Compact</a>
			</abbott:securePage> -->
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
			
			
			<%-- <a class="CmdSm" href="#" onclick="openModel(<c:out value="${closedModels.modelId}" />, '<c:out value="${closedModels.name}" />'); return false;"> OpenAA1</a> --%>
			
				<a class="CmdSm" href='javascript:openModel(<c:out value="${closedModels.modelId}" />, "<c:out value="${closedModels.name}" />")' >Open</a>
			</abbott:securePage>
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				&nbsp;&nbsp;&nbsp;&nbsp;<a class="CmdSm" href='javascript:deleteModel(<c:out value="${closedModels.modelId}" />, "<c:out value="${closedModels.name}" />")' >Del</a>
			</abbott:securePage>
		</td>
		<td class="commandOptionLabel"> <c:out value="${closedModels.name}" /></td>
		<td class="commandOptionLabel"><c:out value="${closedModels.modelCycleLongName}" /></td>
		<td class="commandOptionLabel"><c:out value="${closedModels.modelYear}" /></td>
		<td class="commandOptionLabel"><c:out value="${closedModels.desc}" /></td>
	  </tr>
	  
		</c:forEach>

		<td height="23" colspan=3 class=right >&nbsp;
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
				<a class="CmdSm" href='javascript:showClosedModelDetails("open")' >Show Open Models</a>
			</abbott:securePage>
		</td>

		</c:if>
		
  </table>
  <input type="hidden" id="modelSelected" name="modelSelected" value="" />
  <!-- <input type="hidden" id="showModels" name="showModels" value="open" /> -->
  <s:hidden id="showModels" name="showModels" />
</s:form>

<br>

<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="=">
 	
  <s:form name="createFactorModelForm" id="createFactorModelForm" action="createFactorModel.action" type="abbott.ai.tcgm.action.form.CreateFactorModelForm" >
  <c:set var="factorModels" value="${factorModels}" />
<c:if test="${showModels == 'open'}">

<!-- createModel.jsp -->
    
    <!-- Alignment -->
    
    
    <table width="598" class="tableCommand">

    <!-- Heading -->
    <tr>
        <td colspan="6" nowrap class="tableHeading">
            Create New Model
        </td>
    </tr>

    <!-- Name / Cycle / Year -->
    <tr>
        <td width="89" nowrap align="right" class="commandOptionLabel">
            Name:
        </td>

        <td width="260" nowrap>
            <s:textfield
                name="createModelName"
                size="30"
                maxlength="30"
                cssClass="commandOption"
                theme="simple"/>
        </td>

        <td width="60" nowrap align="right" class="commandOptionLabel">
            Cycle:
        </td>

        <td width="150" nowrap align="left">
            <s:select
                name="createModelCycle"
                cssClass="commandOption"
                theme="simple"
                list="#{'ACT':'Actual', 'PLN':'Plan', 'UPD':'Updated Plan', 'SIM':'Simulation', 'INV1':'April Inv', 'INV2':'Sept Inv', 'INV3':'Dec Inv'}"/>
                
        </td>

        <td width="50" nowrap align="right" class="commandOptionLabel">
            Year:
        </td>

        <td width="80" nowrap align="left">
            <s:textfield
                name="createModelYear"
                size="4"
                maxlength="4"
                cssClass="commandOption"
                theme="simple"/>
        </td>
    </tr>


    <!-- Description -->
    <tr>
        <td width="89"
            height="22"
            align="right"
            nowrap
            class="commandOptionLabel">
            Description:
        </td>

        <td colspan="5" nowrap>
            <s:textfield
                name="createModelDesc"
                size="80"
                maxlength="80"
                cssClass="commandOption"
                theme="simple"/>
        </td>
    </tr>


    <!-- Copy Options -->
    <tr>

        <!-- Blue left side -->
        <td width="89"
            height="94"
            nowrap
            align="right"
            valign="middle"
            class="tableEntry"
            style="padding:2px;">
            <b>Copy Options</b>
        </td>

        <!-- Copy Options controls -->
        <td colspan="4" valign="top" nowrap>

            <table width="100%" cellpadding="2" cellspacing="0">

                <!-- Factor Model List -->
                <c:set var="fmList"
                       value="${requestScope.createFactorModelForm.factorModels}" />

                <!-- Copy From -->
                <tr>
                    <td colspan="3"
                        nowrap
                        align="left"
                        class="commandOptionLabel">

                        Copy From:&nbsp;

                        <s:select
                            name="selSourceModelId"
                            cssClass="commandOption"
                            theme="simple"
                            list="#attr.fmList"
                            listKey="modelId"
                            listValue="name"
                            headerKey="none"
                            headerValue="<none>"/>
                            
                    </td>
                </tr>


                <!-- Freeze Costs / BPC Period -->
                <tr>

                    <td width="390"
                        height="25"
                        nowrap
                        valign="middle"
                        class="commandOptionLabel">

                        <s:checkbox
                            name="createModelClearFreezeCosts"
                            cssClass="commandOption"
                            theme="simple"/>

                        Clear Freeze Costs
                    </td>


                    <c:set var="monthListNumber"
                           value="${requestScope.createFactorModelForm.monthListNumber}" />

                    <td width="120"
                        nowrap
                        align="right"
                        class="commandOptionLabel">

                        BP/C Period&nbsp;

                    </td>

                    <td width="100"
                        nowrap
                        align="left">

                        <s:select
                            name="createModelKeepBpcsPeriod"
                            cssClass="commandOption"
                            theme="simple"
                            list="#attr.monthListNumber"
                            listKey="value"
                            listValue="label"
                            headerKey="0"
                            headerValue="< don't >"/>
                    </td>

                </tr>


                <!-- Clear BP/C -->
                <tr>
                    <td colspan="3"
                        height="25"
                        nowrap
                        class="commandOptionLabel">

                        <s:checkbox
                            name="createModelClearBPCS"
                            cssClass="commandOption"
                            theme="simple"/>

                        Clear BP/C and BP/C Exceptions
                    </td>
                </tr>

            </table>

        </td>


        <!-- Create New Model button -->
        <td><a href="javascript:createModel()" ><img height="20" src="images/btnCreateNewModel.png" border=0></a></td>

    </tr>

</table>

	
	 </c:if>
  </s:form>
	 
  </abbott:securePage>


<%@ include file="/include/footer.jsf" %>
