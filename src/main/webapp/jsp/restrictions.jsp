<%! String pageTitle="Report Restrictions";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
 <%@ include file="/include/masthead.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>
<html:form name="restrictionForm" action="" type="abbott.ai.tcgm.action.form.TCGMProductionForm" scope="session" >

<nested:define id="operations"  property="operations" />
	<table width="690" border="0" cellpadding="2">
		<tr>
			<td width="86">&nbsp;</td>
			<td colspan="5"  class="tableHeading">Enter Job/Report Restrictions</td>
		</tr>
		<tr>
			<td height="23" class=right ></td>
			<td width="31" height="23" class=right ></td> 
			<td width="145" class="tableEntry" >Restriction</td>
			<td class="tableEntry" >Operation</td>
			<td class="tableEntry" >Value</td>
			<td>
				<a href="javascript:cancelAddJob(document.forms[0])" >
					<img border="0"  src="images/btnCancel.png" >
				</a>
				<a href="javascript:submitRestrictions(document.forms[0])">
					<img border="0" src="images/btnAddJob.png" >
				</a>
			</td>
		</tr>

		<nested:nest property="restrictions" >
			<tr>
			  <td height="24"></td>
			  <td height="24" ></td>			  
			  <td width="145" class="commandOption"> 
				Rpt Affiliate(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="rptAffLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>
			<tr>
			  <td height="24"></td>
			  <td height="24"></td>			  
			  <td width="145" class="commandOption"> 
				Sup Affiliate(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="supAffLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>

			<tr>
			  <td height="24"></td>
			  <td height="24" ></td>			  
			  <td width="145" class="commandOption"> 
				End Affiliate(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="endAffLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>

			<tr>
			  <td height="24"></td>
			  <td height="24"></td>			  
			  <td width="145" class="commandOption"> 
				Rpt List Number(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="rptListLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>
			<tr>
			  <td height="24"></td>
			  <td height="24"></td>			  
			  <td width="145" class="commandOption"> 
				Sup List Number(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="supListLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>
			<tr>
			  <td height="24"></td>
			  <td height="24"></td>			  
			  <td width="145" class="commandOption"> 
				End List Number(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="endListLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>
			<tr>
			  <td height="24" colspan="5" align="center" class="commandOption"><font color="red">This Option is only for Factor Details Report</font></td>
			</tr>
			<tr>
			  <td height="24"></td>
			  <td height="24"></td>			  
			  <td width="145" class="commandOption"> 
				Group Id(s)
              </td>
			  <td class="commandOption" >
			  	IN
			  </td>
			  <td width="184">  
    		  	<nested:textarea name="reportRestriction" property="groupIdLimiterValues" rows="4" cols="25" styleClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>															
		</nested:nest>
		
		<tr>
			<td colspan=6 align=right>&nbsp;</td>
		</tr>
	</table>
	
<html:hidden property="formHandler" /><html:hidden property="cmd" />
<div class="hidden"><html:checkbox property="restrictionEntered" /></div>
</html:form>

<p>&nbsp; </p>
<p>&nbsp;</p>

<%@ include file="/include/footer.jsf" %>