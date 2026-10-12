<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%! String pageTitle="Report Restrictions";%>
<%@ include file="/include/header.jsf" %>
<c:set var="pageTitle" value="Report Restrictions" scope="request" />
<head>
<style>
    .btn-prod {
        background-color: #FFC107; 
        border: 1px solid #757575;  
        border-radius: 3px;         
        color: #212121;             
        font-family: Arial, sans-serif;
        font-size: 11px;            
        font-weight: bold;
        padding: 2px 10px;          
        min-width: 65px;            
        cursor: pointer;
        display: block;             
        margin-bottom: 3px;         
        text-align: center;
    }
    .btn-prod:hover {
        background-color: #FFA000; 
    }
    .hidden {
        display: none;
    }
</style>
</head>

<script type="text/javascript">
function submitRestrictions(myForm) {
    myForm.restrictionEntered.checked=true;
    myForm.action=myForm.formHandler.value;
    myForm.submit();
	}

function cancelAddJob(myForm) {
	myForm.cmd.value= 'CANCEL';
	myForm.action=myForm.formHandler.value;
	myForm.submit();
	}
</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
 <%@ include file="/include/masthead.jsf" %>
 <%@ include file="/include/errorDisplay.jsf" %>

<s:form id="restrictionForm" action="" method="post" theme="simple">

	<table width="690" border="0" cellpadding="2">
		<tr>
			<td width="86">&nbsp;</td>
			<td colspan="5"  class="tableHeading">Enter Job/Report Restrictions</td>
		</tr>
		<tr>
			<td height="23" class="right" ></td>
			<td width="31" height="23" class="right" ></td> 
			<td width="145" class="tableEntry" >Restriction</td>
			<td class="tableEntry" >Operation</td>
			<td class="tableEntry" >Value</td>
			<td>
				<!-- Image elements converted directly to styled HTML elements -->
				<button type="button" class="btn-prod" onclick="cancelAddJob(document.forms[0])">Cancel</button>
				<button type="button" class="btn-prod" onclick="submitRestrictions(document.forms[0])">Add Job</button>
			</td>
		</tr>

		<s:iterator value="restrictions" status="stat">
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
    		  	<s:textarea name="restrictions[%{#stat.index}].rptAffLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
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
    		  	<s:textarea name="restrictions[%{#stat.index}].supAffLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
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
    		  	<s:textarea name="restrictions[%{#stat.index}].endAffLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
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
    		  	<s:textarea name="restrictions[%{#stat.index}].rptListLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
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
    		  	<s:textarea name="restrictions[%{#stat.index}].supListLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
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
    		  	<s:textarea name="restrictions[%{#stat.index}].endListLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
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
    		  	<s:textarea name="restrictions[%{#stat.index}].groupIdLimiterValues" rows="4" cols="25" cssClass="commandOption" />				
			  </td>
			  <td width="142">&nbsp;</td>
			</tr>															
		</s:iterator>
		
		<tr>
			<td colspan="6" align="right">&nbsp;</td>
		</tr>
	</table>
	
<s:hidden name="formHandler" />
<s:hidden name="cmd" />
<div class="hidden"><s:checkbox name="restrictionEntered" fieldValue="true" /></div>
</s:form>

<p>&nbsp; </p>
<p>&nbsp;</p>

<%@ include file="/include/footer.jsf" %>