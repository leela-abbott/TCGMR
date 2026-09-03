<%! String pageTitle = "File Upload"; %>
<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<SCRIPT type="text/javascript" language="JAVASCRIPT">
function setCursor(){
	document.body.style.cursor = "default";
}
function doCall(){

	document.body.style.cursor = 'wait';
	document.forms[0].Cancel.disabled=true;
	document.forms[0].Upload.disabled=true;
	document.forms[0].cmd.value='Upload';
	return true;

}
function doCall(abc){
	if(abc=='Upload'){
		document.forms[0].cmd.value='Upload';
		document.forms[0].submit(); 
	}
	if(abc=='Create'){
		document.forms[0].cmd.value='Create';
		document.forms[0].submit(); 
	}
}
</SCRIPT>

<body bgcolor="white" onload="javascript:setCursor();">
<html:form action="/fileUpload" name="fileUploadForm" type="abbott.ai.tcgm.action.form.FileUploadForm" method="post" enctype="multipart/form-data" onsubmit="return doCall();">
<html:hidden property="cmd" />

	<TABLE width="800" align="center" border=0>
		<TR>
			<TD align="right" class="commandOptionLabel" width = "100">File Name</TD>
			<TD align="left" >
			<html:file property="theFile"  value=""/> 
			</TD>
			<TD align="left" class="commandOptionLabel">
				<bean:define id="directories" name="fileUploadForm" property="dirs" type="java.util.HashMap"/>
				<html:select  property="strDirectory" > 
				<option value="root"> Root</option>
				<html:options collection="directories"  property="key" labelProperty="value"/>
				</html:select>
			</TD>
		</TR>
		<TR>
			<TD align="center" colspan="2" class="commandOptionLabel">			
				<html:button property="Upload" value="Upload File" onclick="javascript:doCall('Upload');">Upload File</html:button>			
				<html:button property="Cancel" value="Cancel" onclick ="javascript:checkFilterDirtyFlag('mainMenu.do');">Cancel</html:button>
			</TD>
			<td rowspan=3 width=400></td>
		</TR>
		<TR> <TD colspan=3>&nbsp;</TD>
		</TR>
		<TR>
			<td  class="commandOptionLabel" align="right">Directory Name</td>
			<TD align="left" class="commandOptionLabel">						
	    	  <html:text property="dirName" name="fileUploadForm" size="30" styleClass="commandOption" maxlength="30"/>			
			</TD>
		</TR>	
		<TR>
			<TD align="center" colspan="2" class="commandOptionLabel">	
				<html:button property="Create"  value="Create Directory" onclick="javascript:doCall('Create');">Create Directory</html:button>
			</TD>
		</TR>
	
	</TABLE>
</html:form>
<%@ include file="/include/footer.jsf" %>
