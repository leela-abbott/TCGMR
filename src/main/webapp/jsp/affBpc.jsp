<%! String pageTitle = "Affiliate BPC Data from Network"; %>
<a name="FilterView"></a>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst" scope="session" class="abbott.ai.tcgm.data.DBConst" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/maintNav.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>	
	<s:form method="post" name="affBpcForm" action="affBpcMaint.action"  enctype="multipart/form-data" theme="simple">
		<s:hidden name="cmd" />	
		<!-- Upload Section -->
		<table width="780" cellspacing="0">
			<tr>
				<td colspan="11">
					<font style="font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: normal; color: #0A4876; text-indent: 4pt; vertical-align: bottom;">
						Affiliate BPC Upload
					</font>
					<br/>
					<font style="font-family: Lucida Console, fixedsys, Courier New, Courier, monospace; font-size: 9pt; font-weight: bold; color: #0A4876; text-indent: 0pt; text-align: left;">
						Actual(S)<input type="radio" name="cycle" value="S"/> 
						Prelim(P)<input type="radio" name="cycle" value="P"/>
						Final(F)<input type="radio" name="cycle" value="F"/> 
						Other(X)<input type="radio" name="cycle" value="X"/>
					</font>
					<br/>
				</td>
			</tr>
			<tr>
				<td colspan="11" align="right">
					<s:file name="theFile" theme="simple" />
					<a href="javascript:setUpload();">
						<img src="images/btnUpload.png" alt="Upload" /></a>
				</td>
			</tr>
		</table>
		<hr />
		<!-- Filter Section -->
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>				
				<td rowspan="2">BP<br>Cur<br>Code</td>
				<td rowspan="2">Cost<br></td>				
				<td rowspan="2">Cost<br>Cur<br>Code</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>InvCode</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<s:hidden name="sortObject.sortColumn" />
			<s:hidden name="sortObject.sortOrder" />
			<s:hidden name="searchObject.modelId" />			
			<s:hidden name="searchObject.datasetTableId" /> 				
			<s:hidden name="searchObject.affiliateGroup" />
			<s:hidden name="searchObject.cycleId" />
			<% String submitFilter = "submitFilter(document.affBpcForm,'filter', event);"; %>	
			<tr class="oddRowCenter">				
				<td>
					<s:textfield name="searchObject.rptAff" 
								 maxlength="4" 
								 size="4" 
								 cssClass="fltrWidth4" 
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 4, event);" 
								 onblur="checkPadLeft(this,'0',4);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.supAff" 
								 maxlength="4" 
								 size="4" 
								 cssClass="fltrWidth4" 
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 4, event);" 
								 onblur="checkPadLeft(this,'0',4);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.supProduct.invCode" 
								 maxlength="1" 
								 size="1" 
								 cssClass="fltrWidth1" 
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 1, event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.supProduct.list" 
								 maxlength="6" 
								 cssClass="fltrWidth6"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 6, event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.supProduct.label" 
								 maxlength="3" 
								 cssClass="fltrWidth3"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 3, event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.supProduct.size" 
								 maxlength="3" 
								 cssClass="fltrWidth3"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 3, event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.supProduct.pack" 
								 maxlength="4" 
								 cssClass="fltrWidth4"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 4, event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.billPrice" 
								 maxlength="15" 
								 cssClass="fltrWidth15"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 15, event);"
								 theme="simple" />
				</td>					
				<td>
					<s:textfield name="searchObject.bpCurCode" 
								 maxlength="5" 
								 cssClass="fltrWidth5"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 5, event);"
								 theme="simple" />
				</td>
				<td>
					<s:textfield name="searchObject.costPrice" 
								 maxlength="15" 
								 cssClass="fltrWidth15"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 15, event);"
								 theme="simple" />
				</td>					
				<td>
					<s:textfield name="searchObject.costCurCode" 
								 maxlength="5" 
								 cssClass="fltrWidth5"
								 onchange="makeFilterDirty('pagingDiv','red','bold');" 
								 onkeydown="%{submitFilter}"
								 onkeyup="return autoTab(this, 5, event);"
								 theme="simple" />
				</td>
			</tr>
			<tr class="evenRowCenter">
				<td colspan="15" class="right">
					<a href="javascript:changeCmdAndSubmit(document.affBpcForm,'filter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.affBpcForm,'clearfilter');">
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>
		<hr />
		<!-- Paging Section -->		
		<div name="navigation" id="navigation" class="hidden">
			<%@ include file="/include/affBpcPaging.jsf" %>
		</div>
		<!-- Data Grid Headers -->
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">			
				<td rowspan="2">Transmit<br>Aff<br></td>	
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_RPT_AFF%>');">
						Rpt<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_RPT_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>						
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_AFF%>');">
						Sup<br>Aff<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_AFF">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>															
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_BILL_PRICE%>');">
						Bill<br>Price
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_BILL_PRICE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>								
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_BP_CUR_CD%>');">
						BP<br>Cur<br>Cd
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_BP_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_COST_PRICE%>');">
						Cost<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_COST_PRICE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>			
				<td rowspan="2">
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_COST_CUR_CD%>');">
						Cost<br>Cur<br>Cd
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_COST_CUR_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" 
						   onClick="return toggleSelectAll('affBpcListItem','selected','<s:property value="affBpcListSize"/>');" />
				</td>				
			</tr>

					
					
					<!--1st Part end -->
					
					
			<tr class="mntTblHdng">
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_INV_CD%>');">
						Inv Cd<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_INV_CD">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_LIST%>');">
						List<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LIST">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_LABEL%>');">
						Label<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_LABEL">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_SIZE%>');">
						Size<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_SIZE">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
				<td>
					<a class="mntSort" href="javascript:chgSrtSub(document.affBpcForm,'<%=DBConst.COL_SUP_PACK%>');">
						Pack<br>
						<s:if test="sortObject.sortColumn == @abbott.ai.tcgm.data.DBConst@COL_SUP_PACK">
							<img alt="<s:property value='sortObject.sortImgAltTxt'/>" 
								 src="<s:property value='sortObject.sortImg'/>" align="center" />
						</s:if>
					</a>
				</td>
			</tr>
			<!-- Data Rows -->
			<s:if test="affBpcListSize != 0">
				<s:iterator value="affBpcList" var="affBpcBean" status="affBpcStatus">
					<%
						Integer currentIndex = (Integer) pageContext.getAttribute("affBpcStatus.index");
						int rowNumber = (currentIndex != null) ? currentIndex.intValue() : 0;
					%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">	
						<td class="mntCenter">
							<s:property value="#affBpcBean.affiliate" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.rptAff" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.supAff" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.supProduct.invCode" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.supProduct.list" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.supProduct.label" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.supProduct.size" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.supProduct.pack" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.billPrice" />
						</td>	
						<td class="mntCenter">
							<s:property value="#affBpcBean.bpCurCode" />
						</td>
						<td class="mntCenter">
							<s:property value="#affBpcBean.costPrice" />
						</td>				
						<td class="mntCenter">			
							<s:property value="#affBpcBean.costCurCode" />
						</td>
						<td class="mntCenter">
							<s:checkbox name="affBpcListItem[%{#affBpcStatus.index}].selected" 
										theme="simple" />
						</td>						
					</abbott:row>
				</s:iterator>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/affBpcBtmPaging.jsf" %>
				</div>
			</s:if>
		</table>
		<s:if test="affBpcListSize == 0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</s:if>
		<hr />
	</s:form>
	<!-- JavaScript Section -->
	<script language="JavaScript1.2" type="text/javascript">
		setFocus('searchObject.rptAff');
		showObj('navigation');		
		function setUpload() {
			var flag = false;
			if ((document.forms[0].cycle[0].checked || 
				 document.forms[0].cycle[1].checked || 
				 document.forms[0].cycle[2].checked || 
				 document.forms[0].cycle[3].checked)) {
				if (validateFile(document.forms[0].theFile.value)) {
					flag = true;
				} else {
					alert("Invalid File"); 
					flag = false;
				}
			} else {
				alert("Please Select a Plan");
				flag = false;
			}
			if (flag) {
				javascript:chgActCmdSubmit(document.affBpcForm, 'upload', 'affBpcUpload.do');
			}
		}
		function validateFile(fileName) {
			var dotPosition = fileName.indexOf(".");
			var alertMessage = "";
			if (dotPosition > 0) {
				var extension = fileName.substring(dotPosition + 1).toLowerCase();
				if (!(extension == 'xls' || extension == 'xlsx')) {
					alertMessage = alertMessage + "Invalid File Name\n";
				}
			} else {
				alertMessage = alertMessage + "Invalid File\n";
			}
			if (alertMessage.length > 0) {
				return false;
			} else {
				return true;
			}
		}
	</script>
<%@ include file="/include/footer.jsf" %>
</body>