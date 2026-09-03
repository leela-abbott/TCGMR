<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Calculate and Manage Factors";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin= "0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<div id="divToHide">

<html:form name="mngFactorsForm" action="/mngFactors" type="abbott.ai.tcgm.action.form.MngFactorsForm" scope="session">
<bean:define id="unitSets" name="mngFactorsForm" property="unitSets" />
<bean:define id="rateSets" name="mngFactorsForm" property="rateSets" />
<bean:define id="ccs" name="mngFactorsForm" property="ccsSystemSelected" />
<bean:define id="cType" name="mngFactorsForm" property="ccsType" />
<nested:define id="exportSystemList" property="exportSystemList" />
<nested:define id="monthListNumber" property="monthListNumber" />

<%@ include file="/include/jobOptions.jsf" %>
<% 
String disabled = "" ; 
String checkA = "" ; 
String checkF = "" ; 
String checkP = "" ; 

%>
<c:if test="${ccs ne true}" >     	
 	   <%
 	   disabled = "disabled";
 	   %>     	
</c:if>
<c:if test="${ccs eq true}" >     	
	<c:if test="${cType eq 'A'}" > 
 	   <%
 	   checkA = "checked";
 	   %>     	
 	</c:if>
 	<c:if test="${cType eq 'P'}" > 
 	   <%
 	   checkP = "checked";
 	   %>     	
 	</c:if>
 	<c:if test="${cType eq 'F'}" > 
 	   <%
 	   checkF = "checked";
 	   %>     	
 	</c:if>   
</c:if>



     <TABLE class="tableCommand">

		<TR>
			<TD valign="top">
			<fieldset style="text-align:center;"><legend class="commandOptionLabel"></legend>
			<TABLE><tr><td height="0" colspan="5" bgcolor="#b4d8f4" > <p class="tableHeading">Factor Settings</p></td></tr>
			  <tr>				 
		         <td width="130" height="0" class="commandOptionLabel">Plan Units</td>
	        	 <td height="0">
			        	<html:select property="planUnitsSelected" styleClass="commandOption" >
			            <option value="0">&lt;none&gt;</option>
			            <html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
			        	</html:select> 
				</td>
<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	
        		<td height="0" class="commandOptionLabel">Actual Units</td>
		        <td height="0"> 
		        	<html:select property="actualUnitsSelected" styleClass="commandOption">
		            <option value="0">&lt;none&gt;</option>
		            <html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
		            </html:select> 
				</td>	
			  </tr>
			  <tr>
				
	        	<td height="0" class="commandOptionLabel">Factor Rate</td>
	        	<td height="0"> 
		        	<html:select property="selCostRate" styleClass="commandOption">
		            <option value="">&lt;none&gt;</option>
		            <html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
		          </html:select>
		         </td>
			  </tr>
			  <tr>
			  	
		        <td height="0" class="commandOptionLabel">Std Cost Rate</td>
		        <td height="0"><html:select property="selFactorRate" styleClass="commandOption">
		            <option value="">&lt;none&gt;</option>
		            <html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
		          </html:select>
				</td>
			  </tr>
			  <tr>
			  	
		        <td height="0" class="commandOptionLabel">Revision Rate</td>
		        <td width="73" height="0">
					<html:select property="selRevisionRate" styleClass="commandOption">
		            <option value="">&lt;none&gt;</option>
		            <html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
		         	</html:select>
		    	</td>
			  </tr>
			  <tr>
				
				  <td  class="commandOptionLabel"> Memo: </td>
				  <td  width="242" >
				      <html:textarea property="modelLongDesc" cols="68" rows="7" onkeyup="return checkLength(this, 425);" styleClass="commandOption" />
				  </td>
			  </tr>
			  <tr>		        
				<td><a href="javascript:checkDefault()"><img src="images/btnSetFactorParms.png" width="150" height="20" border="0"></a></td>		        
				<td><a href="javascript:addJob(document.forms[0], 'SAVE_FACTORS_13')"><img src="images/btnSaveFactors13.png" width="200" height="20" border="0"></a>
		  		<html:textarea property="lastExSavePeriod13FactorsDt" disabled="true"  readonly="true" /></td>  
      		</tr>	
			</TABLE>
			</fieldset>
			</TD>
			<TD valign="top">
			<fieldset style="width:70px;text-align:center;"><legend class="commandOptionLabel"></legend>
			<table>
			<tr>
				<td height="0" colspan="4" bgcolor="#b4d8f4">
				<p class="tableHeading">Factor Jobs</p>
				</td>
			</tr>
			<tr><td>
			<TABLE><tr>
				<td height="0">
				&nbsp;
				</td>
			</tr>
			<tr>
	        	<td colspan="2"><a href="javascript:addJob(document.forms[0], 'APPLY_MAINTENANCE')" ><img src="images/btnApplyMaintenance.png" width="150" height="20" border="0"></a></td>
	        	<!--<td>&nbsp;</td>-->
			</tr>
			<tr>
        		<td colspan="2"><a href="javascript:addJob(document.forms[0], 'BUILD_ASR_TREE')"><img src="images/btnBuildTree.png" width="150" height="20" border="0"></a></td>
        		<!--<td>&nbsp;</td>-->
			</tr>
			<tr>
        		<td colspan="2"><a href='javascript:checkCCS(1)'><img border="0" height="20" src="images/btnCalcFactors.png" name="btnCalcFactors" width="150" ></a></td>
      			
			  </tr>
			  <tr>
        		<td colspan="2"><a href="javascript:checkCCS(2)"><img src="images/btnSendFactors.png" width="150" height="20" border="0"></a></td>
        		<!--<td width="143">*</td>-->
			  </tr>
			  <tr>
		        <td colspan="2"><a href="javascript:checkCCS(3)"><img src="images/btnSendAllFactors.png" width="150" height="20" border="0"></a></td>
		        <!--<td width="143">*</td>-->
			  </tr>
			  <tr>
				  <td  class="commandOptionLabel">&nbsp;</td>
				 <!-- <td colspan="7"  width="242" >&nbsp;</td>-->
			  </tr>
		
			</TABLE>
			</td>
			<td>
			<fieldset style="width:70px;text-align:center;"><legend class="commandOptionLabel"></legend>
				<table>
				<tr>
				<td class="mntCenter" colspan="2"><u>Send To</u></td>
				</tr>
				<tr>				        		
      			<td class="mntCenter"> 
					SCR<nested:checkbox property="rgmSystemSelected"/>
					<nested:hidden property="rgmSystemSelected" value="false"/>
				</td>
				<!--  <td class="mntLeft"> 
					CCS<nested:checkbox property="ccsSystemSelected" onclick="javascript:changeCCS();" styleId="ccs" />
					<nested:hidden property="ccsSystemSelected" value="false"/>					
				</td>	 -->			
				</tr>
				<tr>
		      	<td class="mntCenter"> 		 			
					RTC<nested:checkbox property="rtcSystemSelected" />
					<input type="hidden" name="rtcSystemSelected" value="false"/>
				</td>
				<!-- <td class="mntCenter"> 
					Actual<input type="radio" name="ccsType" value="A" <%=disabled%> <%=checkA%>/> -->
					
				</td>
				</tr>
				<tr>
		      	<td class="mntCenter"> 		 			
					RBB<nested:checkbox property="rbbSystemSelected" />
					<input type="hidden" name="rbbSystemSelected" value="false"/>
				</td>
				<!--<td class="mntCenter"> 
					Prelim<input type="radio" name="ccsType" value="P" <%=disabled%> <%=checkP%>/> -->
					
				</td> 
				</tr>
				<tr>
		      	<td class="mntCenter"> 					
					RBL<nested:checkbox property="rblSystemSelected" />
					<input type="hidden" name="rblSystemSelected" value="false"/>
				</td>
				<!--<td class="mntCenter"> 
					Final <input type="radio" name="ccsType" value="F" <%=disabled%> <%=checkF%>/> -->
					
				</td>
			  </tr>
				</table>
			</fieldset>	
			</td>
			</tr>
			</table>
			</fieldset>
			</TD>
		</TR>
		<tr>
		<td>

	<table cellpadding=4 cellspacing=2>
	<tr><td>
	<fieldset style="width:70px;text-align:center;">   
    <legend class="commandOptionLabel"></legend>
	<table cellpadding=4 cellspacing=2>
			 <tr>
        	<td colspan="3" bgcolor="#b4d8f4" ><p class="tableHeading">Reports</p></td>
      		</tr>

            <tr>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'ASR_TREE')"><img src="images/btnASRTree.png" width="200" height="20" border="0"></a></td>
              <!-- <td width="250"><a href="javascript:compModel()"><img src="images/btnComparisonModel.png" width="200" height="20" border="0"></a></td>-->
			 <!-- <td width="250"><a href="javascript:addJob(document.forms[0], 'SET_COMP_MODEL')"><img src="images/btnComparisonModel.png" width="200" height="20" border="0"></a></td> -->
			 <td width="250"><a href="javascript:addJob(document.forms[0], 'BP_VS_TPSS_RPT1_PRE')"><img src="images/btnBPvsTPSS.png" width="200" height="20" border="0"></a></td>

			 <td width="250"><a href="javascript:addJob(document.forms[0], 'AUDIT_FACTORS')"><img src="images/btnFactorAudit.png" border=0></a></td>
			</tr>

            <tr>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'BLNDD_SUMMRY')"><img src="images/btnBlendedFactorSummary.png" border=0></a></td>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_DUB_SUP')"><img src="images/btnDubiousSupAffUsages.png" border=0></a></td>
	      	  <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_INVCUR')"><img src="images/btnInvalidCurrencyCodes.png" border=0></a></td>	
            </tr>

	        <tr>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_BP_FRZ')"><img src="images/btnBPCostFreezeCostOnly.png" border=0></a></td>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'DUP_RPT01')"><img src="images/btnDupReportingProducts.png" width="200" height="20" border="0"></a></td>
	     	  <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_MISM')"><img src="images/btnMismatchASRvsBPCost.png" border=0></a></td>
            </tr>

	    	<tr>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_BPCOST')"><img src="images/btnBPCostSet.png" width="200" height="20" border="0"></a></td>
              <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_EXCEPT')"><img src="images/btnExceptionSet.png" width="200" height="20" border="0"></a></td>
	      	  <td width="250"><a href="javascript:addJob(document.forms[0], 'MISS_FACTOR')"><img src="images/btnMissingFactors.png" border=0></a></td>	
            </tr>

	    	<tr>
              <td width="250">&nbsp;</td>              
              <td width="250">&nbsp;</td>
	          <td width="250"><a href="javascript:addJob(document.forms[0], 'PRINT_VAL_AD')"><img src="images/btnNegativeValueAdded.png" border=0></a></td>	
            </tr> 
      </table>
      </fieldset>
      </td>
      <td valign="top">
      	<fieldset style="width:70px;text-align:center;">   
    	<legend class="commandOptionLabel"></legend>
		<table cellpadding=4 cellspacing=2>
			 <tr>
              	<td colspan="2" bgcolor="#b4d8f4" ><p class="tableHeading">Exports</p></td>
      		</tr>
            <tr>
 			 <td width="130" height="0" class="commandOptionLabel"><a href="javascript:addJob(document.forms[0], 'SEND_CCSH')"><img src="images/btnSendToCCS.png" width="100" height="20" border="0"></a><br>

        	      	<html:select property="ccsType" styleClass="commandOption" >
		            <option value="A">Actual</option>
		            <option value="P">Prelim</option>
		            <option value="F">Final</option>		            		            
		           	</html:select> 
							</td>
            </tr>
           
      </table>
      </fieldset>
      </td>
      </tr>
      </table>

		</td>
		<td>
		</td>
		</tr>
	
</TABLE>
   
</html:form>
</div>
<script language=javascript>
    function deleteModel(model) {
        if ( confirm("Are you sure that you would like to delete " + model + "?\nThis will delete all attributes and can not be undone.") ) {
            location.href="deleteModel.do?model=" + model
        }
    }  
   function changeCCS(){   
    if(document.getElementById("ccs").checked){
      document.forms[0].ccsType[0].disabled = false;
      document.forms[0].ccsType[1].disabled = false;
      document.forms[0].ccsType[2].disabled = false;
    }else{
      document.forms[0].ccsType[0].checked = false;	
      document.forms[0].ccsType[0].disabled = true;
      document.forms[0].ccsType[1].checked = false;	
      document.forms[0].ccsType[1].disabled = true;
      document.forms[0].ccsType[2].checked = false;	
      document.forms[0].ccsType[2].disabled = true;   
    }
   
   }   
   function checkCCS(x){   
  // var flag=false;
   // if(document.getElementById("ccs").checked){
   //   if((document.forms[0].ccsType[0].checked || document.forms[0].ccsType[1].checked || document.forms[0].ccsType[2].checked )){
   //    flag= true;
   //   }else{
   //     alert("Please Select a Plan");
   //     flag= false;
   //   }
 //   }else{
   //   flag= true;   
   // }

	 //  if(flag){
		    if(x==1){
		    	javascript:addJob(document.forms[0], "CALCULATE_FACTORS");
		    }else if(x==2){
		    	javascript:confirmAndAddJob(document.forms[0], 'SEND_FACTORS');
		    } 
		    else if(x==3){
		    	javascript:confirmAndAddJob(document.forms[0], 'SEND_ALL_FACTORS');
		    }
	   //}
   }
   function checkDefault(){
   var msg="";
   var flag = true;

	   	if(document.forms[0].planUnitsSelected.value=="0"){
	   		flag = false;
	   	  	msg=msg+"Please Select a Plan Units.\n";
	   	}
	   	if(document.forms[0].actualUnitsSelected.value=="0"){
	   		flag = false;
	   		msg=msg+"Please Select a Actual Units.\n";
	   	}
	   	if(document.forms[0].selCostRate.value==""){
	   		flag = false;
	   		msg=msg+"Please Select a Factor Rate.\n";
	   	}
	   	if(document.forms[0].selFactorRate.value==""){
	   		flag = false;
	   		msg=msg+"Please Select a Std Cost Rate.\n";
	   	}
	   	if(document.forms[0].selRevisionRate.value==""){
	   		flag = false;
	   		msg=msg+"Please Select a Revision Rate.\n";
	   	}
	   	
	   	
     	if (flag)
		{
		  	javascript:changeCmdAndSubmit(document.forms[0], 'SET_FACTOR_MODEL_PARMS')
	  	}
	  	else
	  	{
	  	 msg=msg+"\nAre you sure you want to proceed... \n";
           if ( confirm(msg) )
			{		
				javascript:changeCmdAndSubmit(document.forms[0], 'SET_FACTOR_MODEL_PARMS')
			}	  	
	  			
	  	}
   }

function compModel(){
javascript:changeCmdAndSubmit(document.forms[0], 'COMP_MODEL')
}
</script>
<%@ include file="/include/footer.jsf" %>
