<%! String pageTitle="Notes Data Advanced Filter";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
 <%@ include file="/include/masthead.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<nested:form method="post" name="notesForm" type="abbott.ai.tcgm.action.form.NotesForm" action="/notesMaint.do" scope="session">

		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />

<table width="690" cellspacing="1">
	<nested:nest>
		<tr>
			<td width="86">&nbsp;</td>
			<td colspan="5"  class="tableHeading">Enter Advanced Filter Values</td>
		</tr>
		<tr>
			<td height="23" class=right ></td>
			<td width="31" height="23" class=right ></td> 
			<td width="145" class="tableEntry" >Restriction</td>
			<td class="tableEntry" >Value</td>
			<td>
				
				<a href="javascript:changeCmdAndSubmit(document.notesForm,'advFilter');" >
				<img src="images/btnFilter.png" alt="Filter" /></a>
			</td>
		</tr>
		
		<nested:nest property="searchObject">
			<div class="hidden"><nested:text property="tranAdvFilter" value="true" /></div>
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
	
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Rpt Aff </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="rptAff" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>		 	

    	<nested:nest property="rptProduct">
		<tr>
			  <td height="20" class="commandOption">Rpt Prod</td><td height="20"></td>			  
			  <td width="145" class="commandOption"> </td>
			  <td width="184">  
			  </td>
			  <td width="142">&nbsp;</td>
		 </tr>		
			
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Inv Code </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="invCode" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">List </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="list" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Label </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="label" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Size </td>
			 <td width="184">  
			<nested:textarea name="reportRestriction" property="size" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Pack </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="pack" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		</nested:nest>	
			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Notes </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="note" rows="2" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		
	</nested:nest>
		 <tr>
			<td height="23" class=right ></td>
			<td width="31" height="23" class=right ></td> 
			<td width="145" class="tableEntry" ></td>
			<td class="tableEntry" ></td>
			<td class="tableEntry" ></td>
			<td>
				
				<a href="javascript:changeCmdAndSubmit(document.notesForm,'advFilter');" >
				<img src="images/btnFilter.png" alt="Filter" /></a>
			</td>
		</tr>		 
	</nested:nest>
</table>
</nested:form>
<p>&nbsp; </p>
<p>&nbsp;</p>

<%@ include file="/include/footer.jsf" %>