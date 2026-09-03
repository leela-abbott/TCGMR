<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Miscellaneous Analysis";%>
<%
String jbStatus="";
jbStatus=(String)request.getSession(false).getAttribute("jobStatus");
 %>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:init()">

<%@ include file="/include/masthead.jsf" %>


<%@ include file="/include/errorDisplay.jsf" %>
<html:form name="miscAnalysisForm" action="miscAnalysis" type="abbott.ai.tcgm.action.form.MiscAnalysisForm" scope="session" >
<nested:define id="monthListNumber" property="monthListNumber" />
<nested:define id="unitSets" property="unitSets" />
<nested:define id="rateSets" property="rateSets" />
<nested:define id="factorModels" property="factorModels" />
<nested:define id="salesType" property="salesType" />
<nested:define id="salesList" property="salesList" />
  <div id="divToHide">
	<%@ include file="/include/jobOptions.jsf" %>
<fieldset style="margin-left:50px;width:685;text-align:left;"><legend class="commandOptionLabel"></legend>
	<table width="685" align="left">
	  <tr >
		<td colspan="7"  class="tableHeading">Deferred / Earned Margin Settings</td>
	  </tr>
	  <tr valign="top"> 
		<td width="27" height="23" class=right ></td>
		<td width="111" align="right" class="commandOption" >Units</td>
		<td width="156">
		  <nested:select property="selExUnitSet" styleClass="commandOption" onchange="callFunc();" styleId="selExUnitSet">
			<html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
		</nested:select></td>
		<td width="65" align="right" class="commandOption" >Period</td>
		<td width="114">
		  <nested:select property="selExPeriod" styleClass="commandOption"  styleId="selExPeriod">
		    <option>13</option>
			<html:options collection="monthListNumber" labelProperty="label" property="value" />
		</nested:select> </td>
		<td width="73" align="right"  class="commandOption" ></td>
		<td width="89">
		
		  </td>
	  </tr>
	  
	  <tr valign="top">
     	<td width="27" height="23" class=right ></td>
      	<td align="left" class="commandOption" colspan="4">Category&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<nested:select property="selExSalesType" styleClass="commandOption"  styleId="selExSalesType">
		<!--  	<option value="ALL">ALL</option>-->
		<html:options collection="salesType" property="key" labelProperty="value"/>				
		</nested:select>		

		</td>
    </tr>
	<tr valign="top">
     	<td width="27" height="23" class=right ></td>
      	<td align="left" class="commandOption" colspan="4">		Sales Id&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<nested:select property="selSalesList" styleClass="commandOption"  styleId="selSalesList" multiple="true" style="width=50px;">
		<html:options collection="salesList" property="key" labelProperty="value"/>				
		</nested:select><font color="red" size="1">&nbsp;&nbsp;*Some of the Sales Id will not be available for the selected Category.</font></td>
    </tr>  
	<tr valign="top">
     	<td width="27" height="23" class=right ></td>
      	<td align="left" class="commandOption" colspan="4">Report Name Suffix&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="reportNameSuf" name="miscAnalysisForm" size="30" styleClass="commandOption" maxlength="20"/></td>
    </tr>   
	  <tr valign="top">
		<td height="23" class=right ></td>
		<td colspan="6" align="left" >
		  <table cellpadding=4 cellspacing=2>
			<tr>
			  <td width="300" valign="top">
				<a href="javascript:autoRunP34()"><img src="images/btnCalculateDeferredMargin.png" border=0></a>
					<br/><input type="checkbox" name="sendP34" id="sendP34" />
<input type="hidden" name="autoRun" value="N"/>
					<font color="red" size="1">*Autorun P34 Volume to CCS.</font>
					</td>
			  <td width="300" valign="top">
				<% if(jbStatus.equals("Y")){ %>
				<a href="javascript:addJob(document.forms[0], 'SEND_P34')"><img src="images/btnSendP34VolumetoCCS.png" border=0></a>				
				<%} %>&nbsp;</td></tr>
		<!-- 	
			<tr>
			  <td width="300">
				<a href="javascript:addJob(document.forms[0], 'DFRD_RPT_SRC')"><img src="images/btnDeferredMarginDetailSrc.png" border=0></a></td>
			  <td width="300">
				<a href="javascript:addJob(document.forms[0], 'DFRD_RPT_SUM_COS')"><img src="images/btnDeferredMarginSummaryCos.png" border=0></a></td>
			</tr>
			
			<tr>
			  <td width="300">
				<a href="javascript:addJob(document.forms[0], 'DFRD_RPT_SUM_SRC')"><img src="images/btnDeferredMarginSummarySrc.png" border=0></a></td>
			  <td width="300"></td>
			  </tr>
			 -->			  
			<!-- <tr>
			  <td width="300">
					<a href="javascript:addJob(document.forms[0], 'DFRD_RPT02')"><img src="images/btnDfrdMargSmryCosSrc.png" border=0></a>&nbsp;&nbsp;
				  <nested:select property="dmSummaryOption" styleClass="commandOption" >
					<option>COS</option>
					<option>Src</option>
				  </nested:select>
			   </td>
			  <td width="300"></td>
			</tr> 
			<tr></tr>
			<tr>
			   <td width="300">
				<a href="javascript:addJob(document.forms[0], 'KGA_REPORTS')"><img src="images/btnKGAReports.png" border=0></a>
				<nested:select property="kgaReportOption" styleClass="commandOption" >
					<option>All</option>
					<option>Base</option>
					<option>Only 082</option>
				  </nested:select>
				</td>
				 <td width="300"></td>
			</tr> -->
		  </table></td>
	  </tr>
	</table>
	</fieldset>
<fieldset style="margin-left:50px;width:685;text-align:left;"><legend class="commandOptionLabel"></legend>	
	<table width="685" align="left">
	  <tr>
		<td colspan="7"  class="tableHeading">Hedge - Currency Exposure Settings</td>
	  </tr>
	  <tr> 
		<td width="27" height="23" class=right ></td>
		<td width="111" align="right" class="commandOption" >Units</td>
		<td width="156">
		  <nested:select property="selHedExUnitSet" styleClass="commandOption" onchange="callHedFunc();" styleId="selHedExUnitSet">
			<html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
		</nested:select></td>
		<td width="65" align="right" class="commandOption" >Period</td>
		<td width="114">
		  <nested:select property="selHedExPeriod" styleClass="commandOption" >
		    <option>13</option>
			<html:options collection="monthListNumber" labelProperty="label" property="value" />
		</nested:select> </td>
		<%-- <td width="73" align="right"  class="commandOption" >Sales ID</td>
		<td width="89">
		<nested:select property="selExSalesId" styleClass="commandOption"  >
			<option>B</option>
			<option>1</option>
			<option>3</option>
		</nested:select>
		  </td> --%>
	  </tr>
	    <tr> 
		<td width="27" height="23" class=right ></td>
		<td width="111" align="right" class="commandOption" >&nbsp;</td>
		<td width="156">
			&nbsp;
		</td>
		<td width="65" align="right" class="commandOption" >End Period</td>
		<td width="114">
		  <nested:select property="selHedExEndPeriod" styleClass="commandOption" >		    
	        <option value="-1">none</option>
			<html:options collection="monthListNumber" labelProperty="label" property="value" />
		</nested:select> </td>
		<td width="73" align="right"  class="commandOption" >&nbsp;</td>
		<td width="89">
			&nbsp;
		  </td>
	  </tr>
	  <!--Category/Sales Id start -->
		<tr valign="top">
		    <td width="27" height="23" class=right ></td>
		      	<td align="left" class="commandOption" colspan="4">Category&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<nested:select property="selHedExSalesType" styleClass="commandOption"  styleId="selHedExSalesType">
				<!--  	<option value="ALL">ALL</option>-->
				<html:options collection="salesType" property="key" labelProperty="value"/>				
				</nested:select>		
		
		    </td>
		</tr>
		<tr valign="top">
		    <td width="27" height="23" class=right ></td>
		      	<td align="left" class="commandOption" colspan="4">		Sales Id&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<nested:select property="selHedSalesList" styleClass="commandOption"  styleId="selHedSalesList" multiple="true" style="width=50px;">
				<html:options collection="salesList" property="key" labelProperty="value"/>				
				</nested:select><font color="red" size="1">&nbsp;&nbsp;*Some of the Sales Id will not be available for the selected Category.</font></td>
		</tr>
	  <!--Category/Sales Id end -->
	  
	  <tr valign="top">
     	<td width="27" height="23" class=right ></td>
      	<td align="left" class="commandOption" colspan="4">Report Name Suffix&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="hedgereportNameSuf" name="miscAnalysisForm" size="30" styleClass="commandOption" maxlength="20"/></td>
     </tr>  
	  
	  <tr>
		<td height="23" class=right ></td>
		<td colspan="6" align="left" >
		  <table cellpadding=4 cellspacing=2>	
			<tr>
			<td width="300">
				<a href="javascript:addJob(document.forms[0], 'XHG_ANALYSIS')"><img src="images/btnMarginByCurrencyAnalysis.png" border=0></a>
				</td> <td width="300">&nbsp; </td>
				</tr>
				<tr>
			  <td width="300">
     			  <!--  <a href="javascript:addJob(document.forms[0], 'XHG_EXP_DTL')"><img src="images/btnMarginByCurrencyDetail.png" border=0></a>-->

				</td>
                <td width="300">
        		<!-- 	<a href="javascript:addJob(document.forms[0], 'XHG_EXPOSURE')"><img src="images/btnMarginByCurrencySmry.png" border=0></a>-->
				</td>
			</tr>			  
			
		  </table></td>
	  </tr>
	</table>
	</fieldset>	
	<fieldset style="margin-left:50px;width:685;text-align:left;"><legend class="commandOptionLabel"></legend>
	<table width="685" align="left" >
	  <tr>
		<td colspan="7"  class="tableHeading">Standard Cost Settings</td>
	  </tr>
	  <tr>

		<td width="101" align="right" class="commandOption" > Units</td>
		<td width="124">
		  <nested:select property="selScUnitSet" styleClass="commandOption" >
			<html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
		</nested:select></td>
		<td width="122" align="right" class="commandOption" >Rate Set</td>
		<td align="left" colspan=3 >
		  <nested:select property="selScRateSet" styleClass="commandOption" >
			<html:options property="datasetTableId" labelProperty="datasetName" collection="rateSets" />
		</nested:select> </td>
	  </tr>
	  <tr>
		
		

		<td height="23" align="right" class="commandOption" >Rate Period</td>
		<td width="48" height="23" align="left" >
			<nested:select property="selScRatePeriod" styleClass="commandOption" >
			<html:option value="13">13</html:option>			
			<html:options collection="monthListNumber" labelProperty="label" property="value" />
			</nested:select>
		</td>
		<td  height="23" align="right" class="commandOption" >Sales ID</td>
		<td width="150" height="23" align="left" >
			<nested:select property="selScSalesId" styleClass="commandOption">
			<html:option value="1">Sample</html:option>
			<html:option value="3">Sales</html:option>
			</nested:select>
		</td>
	  </tr>
	  <tr>
		<td height="66" class=right ></td>
		<td colspan="6" align="left"  >
		  <table >
			<tr>
			  <td width="300">
				<a href="javascript:addJob(document.forms[0], 'STD_VA_CST')"><img src="images/btnStdValAddedCostofSales.png" border=0></a></td>
			  <!-- <td width="300">
				<a href="javascript:addJob(document.forms[0], 'SND_NET_ANL')"><img src="images/btnSendNetAnlysisCCS.png" border=0></a></td>-->
			</tr>
		  </table></td>
	  </tr>
	</table>
	</fieldset>
	<br><br>
	</div>
</html:form>
<SCRIPT type="text/javascript" language="JAVASCRIPT">
function callFunc(){
retrieveURL('./tcgmAjax.do?cascadingCmd=SalesId&cascadingVal='+document.getElementById('selExUnitSet').value,'miscAnalysisForm','selSalesList');
}
//Adding for Hedge Category/Sales ID dropdown
function callHedFunc(){
	retrieveURL('./tcgmAjax.do?cascadingCmd=SalesId&cascadingVal='+document.getElementById('selHedExUnitSet').value,'miscAnalysisForm','selHedSalesList');
	}

function autoRunP34(){
	if(document.getElementById('sendP34').checked)
	{
		document.forms[0].autoRun.value='Y';
	}else{
		document.forms[0].autoRun.value='N';
	}
	javascript:addJob(document.forms[0], 'DFRD_MARGIN');
}

</SCRIPT>
<%@ include file="/include/footer.jsf" %>
