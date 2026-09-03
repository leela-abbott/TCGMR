<%! String pageTitle = "Affiliate BPC Data from Network"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="affBpcForm" scope="session" class="abbott.ai.tcgm.action.form.AffBpcForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<a name="top"></a>
	<table width="734" height="87" border="0" cellpadding="0" cellspacing="0" background="images/masthead.png">
		<tr>
			<td width="72" height="55">&nbsp;
			</td>
			<td class="pageHeading" nowrap>
				<%=pageTitle%>
			</td>
			<td width="225" nowrap valign="top">
  	          <div class="logoutLink"  ><a href="logout.do"><img width=80 height=25 src="images/transparent.gif"></a></div><br><br>
  	          <div class="currUserDisp"><bean:write name="TCGMUser" property="fullName" ignore="true" scope="session" /></div>
			</td>
		</tr>
		<tr>
			<td colspan="4" height="15">&nbsp;</td>
		</tr>
	</table>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>	
	<nested:form method="post" name="affBpcForm" type="abbott.ai.tcgm.action.form.AffBpcForm" action="/affBpcMaint.do" scope="session" enctype="multipart/form-data">
		<nested:hidden property="cmd" />	

		<table width="780" cellspacing="0" >
			<tr><td colspan="11">
<FONT style="font-family: Arial, Helvetica, sans-serif;font-size: 24px;font-weight: normal;color: #0A4876;text-indent: 4pt;vertical-align: bottom;">Affiliate BPC Upload</FONT>
<br/>
<FONT style="font-family: Lucida Console, fixedsys, Courier New, Courier, monospace;font-size: 9pt;font-weight: bold;	color: #0A4876;	text-indent: 0pt;text-align: left;">
Actual(S)<input type="radio" name="cycle" value="S"/> Prelim(P)<input type="radio" name="cycle" value="P"/>Final(F)<input type="radio" name="cycle" value="F"/> Other(X)<input type="radio" name="cycle" value="X"/><br/></td></tr>
<tr><td colspan="11" align="right">
<html:file property="theFile"  value=""/>
</FONT>
<a href="javascript:setUpload();" ><img src="images/btnUpload.png" alt="Filter" /></a></td></tr>
</table><hr />
<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">
					Rpt<br>
					Aff
				</td>
			 	<td rowspan="2">
					Sup<br>
					Aff
				</td>
				<td colspan="5">
					Sup Prod
				</td>
				<td rowspan="2">
					Bill<br>
					Price
				</td>				
				<td rowspan="2">
					BP<br>
					Cur<br>
					Code
				</td>
				<td rowspan="2">
					Cost<br>
				</td>				
				<td rowspan="2">
					Cost<br>
					Cur<br>
					Code
				</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>
					InvCode
				</td>
				<td>
					List
				</td>
				<td>
					Label
				</td>
				<td>
					Size
				</td>
				<td>
					Pack
				</td>
			</tr>
			<% //Sridevi.K changed the existing code to fix to toggled between the ascending and descending order of the data %>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>		
			<% String submitFilter = "submitFilter(document.affBpcForm,'filter', event);"; %>	
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />			
				<nested:hidden property="datasetTableId" /> 				
				<nested:hidden property="affiliateGroup" />
				<nested:hidden property="cycleId" />
				<tr class="oddRowCenter">				
					<td>
						<nested:text property="rptAff" maxlength="4" size="4" styleClass="fltrWidth4" 
							onchange="makeFilterDirty('pagingDiv','red','bold');" 
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 4, event);" 
							onblur="checkPadLeft(this,'0',4);" />
					</td>
					<td>
						<nested:text property="supAff" maxlength="4" size="4" styleClass="fltrWidth4" 
							onchange="makeFilterDirty('pagingDiv','red','bold');" 
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 4, event);" 
							onblur="checkPadLeft(this,'0',4);" />
					</td>
					<nested:nest property="supProduct">
						<td>
							<nested:text property="invCode" maxlength="1" size="1" styleClass="fltrWidth1" 
								onchange="makeFilterDirty('pagingDiv','red','bold');" 
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');" 
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 6, event);" />
						</td>
						<td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');" 
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');" 
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');" 
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 4, event);" />
						</td>
					</nested:nest>
					<td>
						<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth15"
							onchange="makeFilterDirty('pagingDiv','red','bold');" 
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 15, event);" />
					</td>					
					<td>
						<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');" 
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 5, event);" />
					</td>
					<td>
						<nested:text property="costPrice" maxlength="15" styleClass="fltrWidth15"
							onchange="makeFilterDirty('pagingDiv','red','bold');" 
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 15, event);" />
					</td>					
					<td>
						<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');" 
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 5, event);" />
					</td>
				</tr>
			</nested:nest>
			<tr class="evenRowCenter">
				<td colspan="15" class="right">
					<a href="javascript:changeCmdAndSubmit(document.affBpcForm,'filter');" >
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.affBpcForm,'clearfilter');" >
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>
		
		<hr />
		<!-- End of Filter Headings & Filter Input Fields -->
		
		<!-- This section keeps track of record counts -->		
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/affBpcPaging.jsf" %></div>
		<!-- End Record Counts Section -->

		<!-- Start Headings for Row Data -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">			
				<td rowspan="2">			
						Transmit<br>
						Aff<br>
				</td>	
				<td rowspan="2">			
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>						
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Sup<br>Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_AFF%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>															
				</td>
				<td colspan="5">
					Sup Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_BILL_PRICE%>');" >
						Bill<br>Price
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_BILL_PRICE%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>								
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_BP_CUR_CD%>');" >
						BP<br>Cur<br>Cd
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_BP_CUR_CD%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_COST_PRICE%>');" >
						Cost<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_COST_PRICE%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>			
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_COST_CUR_CD%>');" >
						Cost<br>Cur<br>Cd
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_COST_CUR_CD%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				<% //Sridevi.Kalidindi: Changed to select and deselect all the rows %>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('affBpcListItem','selected','<%=affBpcForm.getAffBpcListSize()%>');" />
				</td>				
			</tr>
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_INV_CD%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LIST%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LABEL%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_SIZE%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_PACK%>" >
							<img alt="<%=affBpcForm.getSortObject().getSortImgAltTxt()%>" src="<%=affBpcForm.getSortObject().getSortImg()%>" align="center" />
						</nested:equal>
					</a>
				</td>
		</tr>
<!-- End Headings for Row Data Subf -->

<!-- Start Row Data to Subf Portion -->
	
	<nested:notEqual property="affBpcListSize" value="0">
		
	<% //Sridevi.K : New code for repacing the nested iterate tag to display the Asr data.%>							
	<% //<nested:iterate property="affBpcList" type="abbott.ai.tcgm.entities.AffBpc"> %>
	
	<% int rowNumber=0; %>
	<%//Sridevi.K used the JSTL c:forEach tag to loop through affBpcList %>		
		<c:forEach items="${sessionScope.affBpcForm.affBpcList}" >		
		
			<% //Sridevi.K 7/5/2005 rowNumber attribute is added to Abbott custom %>
			<% //This a custom tag to give some alternate special color %>
			<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>">	
			<%//Sridevi.K End..%>
			
			<% // define the common part of the property tag of html in another string %>
			<% String affBpcListItemArray = "affBpcListItem[" + rowNumber +"]."; %>	
			
			<% //Incriment the row number %>
			<% rowNumber++ ; %>
	
			<% // tmpProperty is given a null to set its values compatible to the property%>
			<% String tmpProperty = "" ; %>		
			
			<% //tmpProperty is initialized here as per the column affiliate %>
			<% tmpProperty = affBpcListItemArray + "affiliate" ; %>
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>
							
     		<% //tmpProperty is initialized here as per the column rptAff %>
			<% tmpProperty = affBpcListItemArray + "rptAff" ; %>
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>
			
			<% //tmpProperty is initialized here as per the column supAff %>
			<% tmpProperty = affBpcListItemArray + "supAff" ; %>
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>
			
			<% //tmpProperty is initialized here as per the column supProduct.invCode %>
			<% tmpProperty = affBpcListItemArray + "supProduct.invCode" ; %>
				<td class="mntCenter">
					<nested:write property="<%= tmpProperty %>" />
				</td>
				
			<% //tmpProperty is initialized here as per the column supProduct.list%>
			<% tmpProperty = affBpcListItemArray + "supProduct.list" ; %>
				<td class="mntCenter">
					<nested:write property="<%= tmpProperty %>" />
				</td>
					
			<% //tmpProperty is initialized here as per the column supProduct.label%>
			<% tmpProperty = affBpcListItemArray + "supProduct.label" ; %>
		 	<td class="mntCenter">
					<nested:write property="<%= tmpProperty %>" />
			</td>
				
			<% //tmpProperty is initialized here as per the column supProduct.size%>
			<% tmpProperty = affBpcListItemArray + "supProduct.size" ; %>	
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>
			
			<% //tmpProperty is initialized here as per the column supProduct.pack%>
			<% tmpProperty = affBpcListItemArray + "supProduct.pack" ; %>	
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>
			
			<% //tmpProperty is initialized here as per the column billPrice%>
			<% tmpProperty = affBpcListItemArray + "billPrice" ; %>	
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>	
			
			<% //tmpProperty is initialized here as per the column bpCurCode%>
			<% tmpProperty = affBpcListItemArray + "bpCurCode" ; %>		
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>
			
			<% //tmpProperty is initialized here as per the column costPrice%>
			<% tmpProperty = affBpcListItemArray + "costPrice" ; %>	
			<td class="mntCenter">
				<nested:write property="<%= tmpProperty %>" />
			</td>				
			
			<% //tmpProperty is initialized here as per the column costCurCode%>
			<% tmpProperty = affBpcListItemArray + "costCurCode" ; %>	
			<td class="mntCenter">			
				<nested:write property="<%= tmpProperty %>" />
			</td>
			
			<% //tmpProperty is initialized here as per the column selected%>
			<% tmpProperty = affBpcListItemArray + "selected" ; %>	
			<td class="mntCenter">
				<nested:checkbox property="<%= tmpProperty %>" />
			</td>						
		  </abbott:row>
	   </c:forEach>
	   <% //Sridevi.K Code to replace nested iterate ends here %>
	   			<div name="navigation" id="navigation" class="hidden">
	   				<%@ include file="/include/affBpcBtmPaging.jsf" %>
	   			</div>
	   
	</nested:notEqual>	
</table>
<!-- End Row Data to Subf Portion -->

		<nested:equal property="affBpcListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		setFocus('searchObject.rptAff');
        showObj('navigation');		
function setUpload()
{
var flag=false;
   if((document.forms[0].cycle[0].checked || document.forms[0].cycle[1].checked || document.forms[0].cycle[2].checked || document.forms[0].cycle[3].checked)){

		if(validateFile(document.forms[0].theFile.value)){
		     flag= true;
			}
			else{alert("Invalid File"); flag= false; }
    }else{
       alert("Please Select a Plan");
        flag= false;
	}
if(flag){
javascript:chgActCmdSubmit(document.affBpcForm,'upload','affBpcUpload.do');
}
 }

function validateFile(fileName){
	var dotPosition = fileName.indexOf(".");
	var alertMessage="";
	if(dotPosition > 0 ){
		if(!(fileName.substring(dotPosition+1)=='xls'||fileName.substring(dotPosition+1)=='XLS' || fileName.substring(dotPosition+1)=='xlsx'||fileName.substring(dotPosition+1)=='XLSX')){
			alertMessage=alertMessage+"Invalid File Name\n";
		}
	}else{
		alertMessage=alertMessage+"Invalid File\n";
	}
	if(alertMessage.length > 0){
		return false;
	}else {
		return true;
	}
}
	</script>
<%@ include file="/include/footer.jsf" %>