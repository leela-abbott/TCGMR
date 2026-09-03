<%! String pageTitle="Bpc Ex Maintenance Advanced Filter";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
 <%@ include file="/include/masthead.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<nested:form method="post" name="bpXTrnFrm" type="abbott.ai.tcgm.action.form.BpcExTranForm" action="/bpcExTranMaint.do" scope="session">

		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />

<table width="690" cellspacing="1">
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
				
				<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'advFilter');" >
				<img src="images/btnFilter.png" alt="Filter" /></a>
			</td>
		</tr>
		
		<nested:nest property="searchObject">
			<div class="hidden"><nested:text property="tranAdvFilter" value="true" /></div>
			<nested:nest property="bpcEx">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
		</nested:nest>
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Act Code </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="actionCode" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		<nested:nest property="bpcEx">	
<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">End Aff </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="endAff" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		<nested:nest property="endProduct">
		<tr>
			  <td height="20" class="commandOption">End Prod</td><td height="20"></td>			  
			  <td width="145" class="commandOption"> </td>
			  <td width="184">  
			  </td>
			  <td width="142">&nbsp;</td>
		 </tr>		
			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Inv Code </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="invCode" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">List </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="list" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Label </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="label" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Size </td>
			 <td width="184">  
			<nested:textarea name="reportRestriction" property="size" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Pack </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="pack" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		</nested:nest>		
		
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Rpt Aff </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="rptAff" rows="1" cols="50" styleClass="commandOption" />	 </td>
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
			<nested:textarea name="reportRestriction" property="invCode" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">List </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="list" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Label </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="label" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Size </td>
			 <td width="184">  
			<nested:textarea name="reportRestriction" property="size" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Pack </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="pack" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		</nested:nest>		 
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Supp Aff </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="supAff" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>				
		<nested:nest property="supProduct">
		<tr>
			  <td height="20" class="commandOption">Sup Prod</td><td height="20"></td>			  
			  <td width="145" class="commandOption"> </td>
			  <td width="184">  
			  </td>
			  <td width="142">&nbsp;</td>
		 </tr>		
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Inv Code </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="invCode" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>	
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">List </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="list" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Label </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="label" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Size </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="size" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>			
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Pack </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="pack" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>

		</nested:nest>
		<tr><td colspan=4>&nbsp;</td></tr>
		
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Freeze Cost</td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="freezeCost" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Beg Period</td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="begPeriod" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">End Period</td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="endPeriod" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>		 
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Bill Price </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="billPrice" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">BP Cur Code</td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="bpCurCode" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>		 
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Cost Price</td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="costPrice" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		 <tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Cost Cur Code</td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="costCurCode" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>
		 		 
		 
	</nested:nest>
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">Pub Flag </td>
			  <td width="184">  
			<nested:textarea name="reportRestriction" property="publishFlag" rows="1" cols="50" styleClass="commandOption" />	 </td>
			  <td width="142">&nbsp;</td>
		 </tr>	
		<tr>
			  <td height="24"></td><td height="24"></td>			  
			  <td width="145" class="commandOption">User ID </td>
			  <td width="184">  
			  <abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
			<%=TCGMUser.getUserid()%>
			</abbott:securePage>
			<!--
			*	Added by Uday on 02/04/2006 to provide the user(Analyst)
			* the option to use the maintenance records of any user. Start
			-->
			<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
			<html:select property="userSelected" styleClass="commandOption">
			<html:option value="ALL">ALL</html:option>
			<html:options name="TCGMUser" property="userlist" /></html:select> 
			</abbott:securePage>
			<!--
			*	Added by Uday on 02/04/2006 to provide the user(Analyst)
			* the option to use the maintenance records of any user. End
			-->			
			</td>
			<td width="142">&nbsp;</td>
		 </tr>	
		 <tr>
			<td height="23" class=right ></td>
			<td width="31" height="23" class=right ></td> 
			<td width="145" class="tableEntry" ></td>
			<td class="tableEntry" ></td>
			<td>
				
				<a href="javascript:changeCmdAndSubmit(document.bpXTrnFrm,'advFilter');" >
				<img src="images/btnFilter.png" alt="Filter" /></a>
			</td>
		</tr>		 
	</nested:nest>
</table>
</nested:form>
<p>&nbsp; </p>
<p>&nbsp;</p>

<%@ include file="/include/footer.jsf" %>