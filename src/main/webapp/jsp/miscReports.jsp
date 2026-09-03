<%@ page import="abbott.ai.tcgm.entities.User" %>
<%! String pageTitle="Miscellaneous Reports";%>

<%@ include file="/include/header.jsf" %>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>
<html:form name="miscReportsForm" action="miscReports" type="abbott.ai.tcgm.action.form.MiscReportsForm" scope="session" >
<bean:define id="unitSets" name="miscReportsForm" property="unitSets" />
<nested:define id="monthListNumber" property="monthListNumber" />
<nested:define id="yearList" property="yearList" />
<nested:define id="factorModels" property="factorModels" />

  <div id="divToHide">
  	<%@ include file="/include/jobOptions.jsf" %> 
	<table class="tableCommand" >
	  <tr>
	    <td colspan="2"  class="tableHeading">Misc Jobs and Reports</td>
      </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td align="left"  >
          <table cellspacing="4" >
            <tr>
              <td width="225">
			  <a href="javascript:deleteAsrPartial()"><img src="images/btnDeleteASRofPartialTrees.png" border=0></a></td>
              <td width="225" >
			  <a href="javascript:addJob(document.forms[0], 'BPC_PRT0CST')"><img src="images/btnBPCwithAllZeroCosts.png" border=0></a></a>
			</td>
              <td width="225" >
			  	</td>
            </tr>
            <tr>
              <td width="225">
				<a href="javascript:deleteAsr()"><img src="images/btnDeleteASRwoBPCMatch.png" border=0></a></td>
              <td width="225">
				<a href="javascript:addJob(document.forms[0], 'BPC_PRT0BP')"><img src="images/btnBPCwithAllZeroPrices.png" border=0></a></td>
              <td width="225"></td>
            </tr>
            <tr>
              <td width="225"><a href="javascript:deleteBpc()"><img src="images/btnDeleteBPCwoASRMatch.png" border=0></a>
				</td>
              <td width="225"><a href="javascript:addJob(document.forms[0], 'DELETE_AUDIT')"><img src="images/btnDeleteAuditSets.png" border=0></a>				</td>
              <td width="225">			 	</td>				
            </tr>


            <tr valign="top">
              <td width="225" height="50">
				</td>
              <td width="225">&nbsp;</td>
              <td width="225">&nbsp;</td>			  
            </tr>

            <tr valign="top">
              <td width="225">
			  	  	<div class="commandOption" style="border:solid blue 0px;padding:0 px;">
					<a href="javascript:addJob(document.forms[0], 'BPC_0PLNVSIM')"><img src="images/btnZeroBPCComparedWModel.png" border=0></a>
					<br>
                  Comparison Model:

                  <nested:select property="selComparisonModel">

                    <html:options property="modelId" labelProperty="name" collection="factorModels" styleClass="commandOption" />
                	</nested:select>
                  <br>
					
					</div></td>
              <td width="275">
				<a href="javascript:addJob(document.forms[0], 'ROUTE_EXCEPT')"><img src="images/btnRouteExceptions.png" border=0></a><br>
				<div class="commandOption" align="left" style="margin-right:10px;" >
				Starting D.5.6 Period:
                  <nested:select property="selRoutingStartPeriod" styleClass="commandOption" >
				    <html:option value="13">13</html:option>
                    <html:options collection="monthListNumber" labelProperty="label" property="value" />
                </nested:select>
                  <nested:select property="selRoutingStartYear" styleClass="commandOption" >
                    <html:options collection="yearList" labelProperty="label" property="value" />
                  </nested:select>
                  <br>
				Ending D.5.6 Period:
                  <nested:select property="selRoutingEndPeriod" styleClass="commandOption" >
				    <html:option value="13">13</html:option>
                    <html:options collection="monthListNumber" labelProperty="label" property="value" />
                </nested:select>
                  <nested:select property="selRoutingEndYear" styleClass="commandOption" >
                    <html:options collection="yearList" labelProperty="label" property="value" />
                </nested:select>
<!-- 9-19-03 Beginning of Routing Year Units Addition -->				
                  <br>
				Beginning Routing Year Units
                  <br>
				<html:select property="begRouteUnitsSelected" styleClass="commandOption" >
        			<option value="">&lt;none&gt;</option>
            		<html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
        		</html:select>				  
                  <br>
				Ending Routing Year Units
                  <br>
				<html:select property="endRouteUnitsSelected" styleClass="commandOption" >
        			<option value="">&lt;none&gt;</option>
            		<html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
        		</html:select>				  
<!-- 9-19-03 Ending of Routing Year Units Addition -->			
                </div>
			  </td>
              <td width="225">
			  <a href="javascript:addJob(document.forms[0], 'BPC_ANALYS')"><img src="images/btnInterCoTransferAnalysis.png" border=0></a>
			  	<div class="commandOption" style="margin-right:80px;">Period:
				  <nested:select property="interCoTransferPeriod"  styleClass="commandOption" >
				    <html:option value="13">13</html:option>
                    <html:options collection="monthListNumber" labelProperty="label" property="value" />
				  </nested:select>
				  <br>
				    Actual Units
        			<nested:select property="actualUnitsSelected" styleClass="commandOption">
		            	<option value="0">&lt;none&gt;</option>
        		    	<html:options property="datasetTableId" labelProperty="datasetNameLogStamp" collection="unitSets" />
		            </nested:select> 
				  <!-- 11-20-05 Filename parm added -->
        			<!-- 03/20/2006 Veerendra Hiding the text field and passing the Hidden Param by default -->
        			<!-- <p>Tree File Name: <br> -->
        			<nested:text property="treeFilename" maxlength="8" styleClass="hidden" value="TCGMICTA" /> 
        			</div>
			  </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
</html:form>
<script language=javascript>
function deleteAsrPartial(){
			if ( confirm("Do You Really Wanted to Delete ASR of Partial Trees Record?") )
			{		
				javascript:addJob(document.forms[0], 'ASR_DLT_PART')
			}
}
function deleteAsr(){
			if ( confirm("Do You Really Wanted to Delete ASR w/o BP/C Records?") )
			{		
				javascript:addJob(document.forms[0], 'ASR_DELETE')
			}
}
function deleteBpc(){
			if ( confirm("Do You Really Wanted to Delete BP/C w/o ASR Records?") )
			{		
				javascript:addJob(document.forms[0], 'BPC_DELETE')
			}
}
</script>
<%@ include file="/include/footer.jsf" %>
