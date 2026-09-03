<%@ page errorPage="/jsp/exception.jsp" %>
<%@ page import="abbott.ai.tcgm.entities.Role" %>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/taglib/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/c-rt.tld" prefix="c-rt" %>
<%@ taglib uri="/WEB-INF/taglib/abbott.tld" prefix="abbott" %>
<jsp:useBean id="TCGMConstants" class="abbott.ai.tcgm.TCGMConstants" scope="session" />
<jsp:useBean id="DBConst" class="abbott.ai.tcgm.data.DBConst" scope="session" />

<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />

<bean:define id="TCGMUser" name="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<bean:define id="mform" name="mainForm" scope="session" type="abbott.ai.tcgm.action.form.MainForm" />


<html:html>
<head>


    <title>TCGM System - Main Menu</title>
<script language="JavaScript" src="include/common.js" type=text/javascript></script>
<script language="JavaScript">
function checkUrl(src,dest){
var xSrc=src;

	if(xSrc.indexOf('.do')!=-1){

		if(dest.indexOf('.do')!=-1){
			xSrc=xSrc.substring(0,xSrc.lastIndexOf('/')+1)+dest;					
		}
		else{
		
			xSrc=xSrc.substring(0,xSrc.lastIndexOf('/')+1)+'jsp/'+dest;
		}
	}else if(xSrc.indexOf('.jsp')!=-1){	
		if(dest.indexOf('.do')!=-1){
			
			xSrc=xSrc.substring(0,xSrc.lastIndexOf('/')-3)+dest;					
			//window.location.href=x;
		}
		else{			
			xSrc=xSrc.substring(0,xSrc.lastIndexOf('/')+1)+dest;
		}
		
	}

	return xSrc;
}
function callReport(action){
var caller=checkUrl(window.location.href,action);
window.open(caller,'','location=no,toolbar=yes,resizable,left=100,top=15,height=550,width=750,scrollbars=yes');
  
}
function callFile(action,filename,dir){
var caller=checkUrl(window.location.href,action);
  window.open(caller+'?dir='+dir+'&fileName='+filename,'','location=no,toolbar=yes,resizable,left=100,top=15,height=550,width=750,scrollbars=yes');
}	

</script>
</head>
  

<body>
<html:form name="mainForm" type="abbott.ai.tcgm.action.form.MainForm" action="/login.do">


    <table style="text-align:center; width:100%; font-size:large; font-weight:bold; color:Navy;">
<tr>
        <td>
            <table style="width:65%;">
                <tr>
                    <td align="left" width="109">
                       <table style="width:30%;">
                            <!--<img src="images/signature.gif" />-->
                       </table>
                    </td>
               
                    <td align="left" width="454">
                        <table style="width:80%;text-align:center; font-size:large; font-weight:bold; color:Navy;">
                            <tr><td align="center">Trading Company Gross Margin</td></tr>
                            <tr><td align="center">Main Menu</td></tr>
                        </table>
                    </td>
               </tr> 
               <tr><td>&nbsp;</td></tr>
               <tr>
               <td colspan="2">

				<marquee SCROLLAMOUNT=3 onMouseover="this.scrollAmount=0" onMouseout="this.scrollAmount=3" colorRed
				             direction="left" 
				             width="800" 
				             ><font face="Arial" size=3 color=Red><b><%= mform.getBulletinMessage()%></b></font></marquee>

               </td>
               </tr>  
              
            </table>
        </td>
    </tr>     
    <tr>
    <td>
    <%@ include file="/include/errorDisplay.jsf" %>
    </td>
    </tr>
      <tr>
      <td>
         <table style = "width:65%">
          <%
            for(int i=0;i<mform.getMessageList().size();i++){
                 %>
            <tr>
           	<td colspan="2">
				<font face="Arial" size=2 color=Blue><b><%= mform.getMessageList().get(i)%></b></font>
           </td>
           </tr>  
                 <% 
                  }
               %>
          <tr><td>&nbsp;</td></tr>

         <tr>
             <td align="left" style=" font-size:small; font-weight:bold; color:Navy;" >
                 TCGM Links
             </td>
         </tr>
         <tr>
            <td align = "center">
                <hr/>
            </td>
         </tr>
         <tr>
            <td align="left">
                <table style = "font-size:small;" width="100%">
                
                 <tr>
                    <td align="left" style=" font-size:small; font-weight:bold; color:Navy;">
                        <% 
						if ((TCGMUser.getRole().getAccessLevel()) == (Role.Administrator.getAccessLevel())) { %>
							<a href="userMaint.do">Link to TCGM</a>
				    <% if (!TCGMUser.isRptAccess()) { %>
					</td>
					<td align="right" style=" font-size:small; font-weight:bold; color:Navy;">
	                        <a href="#" onclick="javascript:callReport('reportView.jsp');">Link to Reports</a>
	                        <%}%>
						<% } else  if ((TCGMUser.getRole().getAccessLevel()) == (Role.Operator.getAccessLevel())) { %>
							<a href="mngFactorModels.do">Link to TCGM</a>
						<% } else  if ((TCGMUser.getRole().getAccessLevel()) == (Role.Analyst.getAccessLevel())) { %>
							<a href="mngFactorModels.do">Link to TCGM</a>	
						<% } else  if ((TCGMUser.getRole().getAccessLevel()) == (Role.RptAdmin.getAccessLevel())) { %>
							<a href="ActiveDirSearch.do?cmd=view">Link to TCGM</a>
				    <% if (!TCGMUser.isRptAccess()) { %>
					</td>
					<td align="right" style=" font-size:small; font-weight:bold; color:Navy;">
	                        <a href="#" onclick="javascript:callReport('reportView.jsp');">Link to Reports</a>
	                        <%}%>
						<% } else  if ((TCGMUser.getRole().getAccessLevel()) == (Role.Query.getAccessLevel())) { %>
							<a href="mngFactorModels.do">Link to TCGM</a>
						<% }%>
                    </td>
                    <td align="right" style=" font-size:small; font-weight:bold; color:Navy;">
                    	 <% 
						if (TCGMUser.isRptAccess()) { %>
	                        <a href="#" onclick="javascript:callReport('reportView.jsp');">Link to Reports</a>
	                    <%}%>
                    </td>
                  </tr>                
                  
                </table>
            </td>
         </tr>
         <tr>
            <td align = "center">
                <hr/>
            </td>
         </tr>
         <tr style="height:15px"></tr>
         <tr>
             <td align="left" style=" font-size:small; font-weight:bold; color:Navy;" >
                 File Folders
             </td>
         </tr>
		 <tr>
            <td align = "center">
                <hr/>
            </td>
         </tr>
        	</tr>  
               <%
               if(mform.getCmd().equalsIgnoreCase("") || mform.getCmd().equalsIgnoreCase("dir")){
                  for(int i=0;i<mform.getDirList().size();i++){
                 %>
	                <tr>
	               	<td colspan="2">
	
					<b><a href="main.do?cmd=file&dirName=<%= mform.getDirList().get(i)%>"  style="color:Blue;text-decoration:none"><img src="images/folder.gif" border="0"><font size="small"> <%= mform.getDirList().get(i)%></font></img></a></b>
	
	               </td>
	               </tr>  
                 <% 
                  } %>
                   <tr>
		            <td align = "center">
		                <hr/>
		            </td>
		         </tr>
                <%}else{ 
                 for(int i=0;i<mform.getFileList().size();i++){ 
               %>
               <tr>
               	<td colspan="2">
					<b><a href='#' onclick="javascript:callFile('fileView.jsp','<%= mform.getFileList().get(i)%>','<%= mform.getDirName()%>');"><%= mform.getFileList().get(i)%></a></b>
               </td>
               </tr> 
             <%
             }
             %>
         <tr>
            <td align = "center">
                <hr/>
            </td>
         </tr>
 				<tr><td colspan="2"><br></td></tr>

              		<tr>
		               	<td colspan="2">		
							<b><a href="main.do?cmd=dir">Back to Dir</a></b>		
		               </td>
	               </tr>
             <%
            	} 
             %>
		<tr style="height:15px"></tr>
		<tr><td colspan="2"><br></td></tr>
         <tr>
             <td align="right" style=" font-size:small; font-weight:bold; color:Navy;">
                 <a href="logout.do" style="color:Navy">Logout</a>
             </td>
         </tr>
         <tr style="height:15px"></tr>
      </table>
        </td>
      </tr>
  </table>
</html:form>
</body>
</html:html>