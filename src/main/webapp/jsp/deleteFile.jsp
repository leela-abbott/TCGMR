<%! String pageTitle = "Delete File"; %>
<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>

<SCRIPT type="text/javascript" language="JAVASCRIPT">
function setCursor(){
	document.body.style.cursor = "default";
}
function doCall(){
var cnt=document.getElementById("hidVal");
var incr=0;
var fname="";
	  for(i=0;i<cnt.value;i++){
	 		if(document.getElementById("files"+i).checked){
	 			incr++;
	 			fname=document.getElementById("files"+i).value;
	 		}
	 	}
	 if(incr>0){	
	 	document.forms[0].fileName.value=fname;
		document.forms[0].cmd.value='deletefile';
		return true;
	}else{
	  	alert("Please Select a File to be Deleted");
	  	return false;
	}

}
</SCRIPT>
<bean:define id="mform" name="deleteFileForm" scope="session" type="abbott.ai.tcgm.action.form.DeleteFileForm" />
<body bgcolor="white" onload="javascript:setCursor();">
<html:form action="/deleteFile" name="deleteFileForm" type="abbott.ai.tcgm.action.form.DeleteFileForm" onsubmit="return doCall();">
<html:hidden property="cmd" />
<html:hidden property="fileName" />

	<TABLE width="600"  border=0>
		
	<nested:notEqual property="cmd" value="dir">
		<tr >
				<td width=200></td>
				<td class="fltrTblHdngLeft"></td>
				<td class="fltrTblHdngLeft">File Name</td>				
		</tr>
		<nested:notEqual property="fileListSize" value="0">
				<% int rowNumber=0; %>
				<c:forEach var="affCodeBean" items="${deleteFileForm.fileListDisplay}"  varStatus="affCodeStatus">	               			
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">
						<td width=200></td>	
						<td class="mntLeft" width="20">

							<input type="radio" name="file" value='<c:out value="${affCodeBean.key}" />' id="files<%= rowNumber%>" />
						</td>		
						<td class="mntLeft" width="200">
							<c:out value="${affCodeBean.value}" />
						</td>						
					</abbott:row>
			  		<% rowNumber++; %>			
				</c:forEach>
				<input type="hidden" id="hidVal" name="hidVal" value="<%=rowNumber %>" />	
					<tr></tr>
		<TR>
			<td width=200></td>
			<TD align="center" colspan="2" class="commandOptionLabel">			
				<html:submit property="Delete">Delete File</html:submit>			
			</TD>
		</TR>
		<tr></tr>
				
	</nested:notEqual>
		<tr>
			<td width=200></td>
           	<td colspan="2" align="left">		
				<b><a href="deleteFile.do?cmd=dir">Back to Dir</a></b>		
           </td>
       </tr>		
	</nested:notEqual>
	<nested:notEqual property="cmd" value="file">
		<tr >
				<td width=200></td>
				<td class="fltrTblHdngLeft"></td>
				<td class="fltrTblHdngLeft">Directory Name</td>				
		</tr>

		 <%
           if(mform.getCmd().equalsIgnoreCase("") || mform.getCmd().equalsIgnoreCase("dir")){
              for(int i=0;i<mform.getDirList().size();i++){
             %>
                <tr>
                <td width=200></td>
               	<td colspan="2">

				<b><a href="deleteFile.do?cmd=file&dirName=<%= mform.getDirList().get(i)%>"  style="color:Blue;text-decoration:none"><img src="images/folder.gif" border="0"><font size="small"> <%= mform.getDirList().get(i)%></font></img></a></b>

               </td>
               </tr>  
             <% 
              }
            }%>
							
	</nested:notEqual>
			
	
	</TABLE>
</html:form>
<%@ include file="/include/footer.jsf" %>
